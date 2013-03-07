package vee;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.syc.framework.Const.*;
import org.syc.framework.Item.*;
import org.syc.framework.Kernel.*;
import org.syc.music_trading_system.Item.*;


public class Service {
	
	private int m_cmd;
	private long m_id;
	private ObjectInputStream m_ois;
	private ObjectOutputStream m_oos;
	
	
	Service(int cmd, long id, ObjectInputStream ois, ObjectOutputStream oos) {
		m_cmd = cmd;
		m_id = id;
		m_ois = ois;
		m_oos = oos;
	}
	

	public void Serve()
	{
		PrtMsg("[Start Serving]ID=" + m_id + " Command=" + m_cmd);
		
		long mid, price, sellerid, srcid, prcl, prch;
		int idx;
		String str[];
		String name, password;
		
		User user;
		
		switch (m_cmd) {
		
		case Command.LOGIN:
			PrtMsg("[LOGIN] ID =" + m_id + " [..]");
			try {
				password = (String) m_ois.readObject();
				PrtMsg("[Login] password = " + password);
			} catch(Exception e) {
				Send(ReturnInfo.REQUEST_ERROR);
				return;
			}
			Login(password);
			break;
		case Command.LOGOUT:
			PrtMsg("[Logout] ID = " + m_id + " [Start]");
			Logout();
			break;
		case Command.NEW_ACCOUNT:
			try {
				user = (User) m_ois.readObject();
				name = user.getName();
				password = user.getPassword();
				PrtMsg("[ServeNewAccount]Name=" + name + " Password=" + password);
			} catch(Exception e) {
				//e.printStackTrace();
				Send(ReturnInfo.REQUEST_ERROR);
				return;
			}
			NewAccount(name, password);
			break;
		case Command.NAME_CONFLICT:
			try {
				name = (String) m_ois.readObject();
			} catch(Exception e) {
				//e.printStackTrace();
				Send(ReturnInfo.REQUEST_ERROR);
				return;
			}
			PrtMsg("[NAME CONFLICT] name = " + name + " [Start]");
			NameConflict(name);
			break;
			
		case Command.FETCH_ID:
			PrtMsg("[Fetch ID] [Start]");
			try {
				name = (String) m_ois.readObject();
			} catch(Exception e) {
				Send(ReturnInfo.REQUEST_ERROR);
				return;
			}
			FetchUserID(name);
			break;
		case Command.SHOW_MONEY:
			PrtMsg("[Show Money] ID = " + m_id + " [Start]");
			ShowMoney();
			break;
		case Command.SHOW_RANK:
			PrtMsg("[SHOW RANK] ID = " + m_id + " [Start]");
			ShowRank();
			break;
		case Command.FETCH_ORINGIN_LIST:
			PrtMsg("[FETCH ORIGN LIST] ID = " + m_id + " [Start]");
			FetchOrignList();
			break;
		case Command.FETCH_PROPERTY_LIST:
			PrtMsg("[FETCH PROPERTY LIST] ID = " + m_id + " [Start]");
			FetchPropertyList();
			break;
		case Command.FETCH_SELLING_LIST:
			PrtMsg("[FETCH SELLING LIST] ID = " + m_id + " [Start]");
			FetchSellingList();
			break;
			
		case Command.GET_REWARD:
			PrtMsg("[GET REWARD] ID = " + m_id + " [Start]");
			GetReward();
			break;
			
		case Command.SELL:
			PrtMsg("[SELL]..");
			try {
				TransactionItem tsi = (TransactionItem) m_ois.readObject();
				mid = tsi.getItem().getId();
				price = tsi.getPrice();
			} catch(Exception e) {
				Send(ReturnInfo.REQUEST_ERROR);
				return;
			}
			Sell(mid, price);
			break;
		case Command.HOLDON:
			try {
				TransactionItem itm = (TransactionItem) m_ois.readObject();
				mid = (long) itm.getId();
			} catch(Exception e) {
				Send(ReturnInfo.REQUEST_ERROR);
				return;
			}
			HoldOn(mid);
			break;
			
		case Command.BUY:
			try {
				TransactionItem itm = (TransactionItem) m_ois.readObject();
				mid = itm.getItem().getId();
				sellerid = (long) itm.getSellerId();
			} catch(Exception e) {
				Send(ReturnInfo.REQUEST_ERROR);
				return;
			}
			PrtMsg("[BUY] MusicID = " + mid + ", SellerID = " + sellerid + " [..]");
			Buy(mid, sellerid);
			break;
		case Command.GET_COMMODITY_LIST:
			try {
				str = (String[]) m_ois.readObject();
				srcid = Long.valueOf(str[0]);
				prcl = Long.valueOf(str[1]);
				prch = Long.valueOf(str[2]);
				idx = Integer.valueOf(str[3]);
			} catch(Exception e) {
				Send(ReturnInfo.REQUEST_ERROR);
				return;
			}
			GetCommodityList(srcid, prcl, prch, idx);
			break;
			
		case Command.TEST:
			org.syc.music_trading_system.Item.Music music = new org.syc.music_trading_system.Item.Music(123L,"music test", false, 10001L);
			TransactionItem tItem = new TransactionItem(999L, 0, music, 998L);
			try {
				m_oos.writeObject(tItem);
				PrtMsg("send test data!");
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			
		}

		
	}
	
	/**
	 * Login Handler
	 */
	public void Login(String password)
	{
		switch (AccountManager.Login(m_id, password)) {
		case 0:
			Send(ReturnInfo.SUCCESS);
			break;
		case 1:
			Send(ReturnInfo.WRONG_PASSWORD);
			break;
		case 2:
			Send(ReturnInfo.SERVER_ERROR);
			break;
		default:
			Send(ReturnInfo.UNKNOWN_EXPECTION);
		}
	}
	
	/**
	 * Logout Handler
	 */
	public void Logout()
	{
		switch (AccountManager.Logout(m_id)) {
		case 0:
			PrtMsg("[Logout] ID =" + m_id + " [OK]");
			Send(ReturnInfo.SUCCESS);
			break;
		default:
			PrtMsg("[Logout] ID =" + m_id + " [EXC]");
			Send(ReturnInfo.UNKNOWN_EXPECTION);
		}
	}
	
	/**
	 * Create New Account Handler
	 */
	public void NewAccount(String name, String password)
	{
		PrtMsg("[NewAccountHandler]name="+name+" password="+password);
		long id = AccountManager.CreateNewAccount(name, password);
		if (id == AccountManager.NULL_ID) {
			PrtMsg("[AccountManager]CreateNewAccount Fail");
			Send(ReturnInfo.NAME_CONFLICT);
		} else {
			if (MoneyManager.CreateNewAccount(id) != 0) {
				PrtMsg("[MoneyManager]CreateNewAccount Fail");
				Send(ReturnInfo.SERVER_ERROR);
			} else {
				PrtMsg("[NewAccountHandler]CreateNewAccount Succeed, Login");
				Send(ReturnInfo.SUCCESS);
			}
		}
	}
	
	
	/**
	 * Fetch User Information Handler
	 */
	public void FetchUserID(String name)
	{
		Account acc = AccountManager.FetchAccount(name);
		if (acc == null) {
			Send(AccountManager.NULL_ID);
		}
		PrtMsg("[Fetch ID]ID="+ acc.getId()+"[OK]");
		Send(acc.getId());
	}
	
	/**
	 * Check Whether Name Conflict or not Handler
	 */
	public void NameConflict(String name)
	{
		Object obj = AccountManager.FetchAccount(name);
		if (obj == null) {
			Send(false);
		} else {
			Send(true);
		}
	}
	
	/**
	 * Show Money Information Handler
	 */
	public void ShowMoney()
	{
		Money mny = MoneyManager.FetchMoneyInfo(m_id);
		if (mny == null) {
			PrtMsg("[Show Money] - -");
			Send(null);
			return;
		}
		PrtMsg("[Show Money] ID = " + m_id + ", Amount = " + mny.getAmount());
		Send(mny.getAmount());
	}
	
	/**
	 * Show Rank Information Handler
	 */
	public void ShowRank()
	{
		Money mny = MoneyManager.FetchMoneyInfo(m_id);
		if (mny == null) {
			Send(null);
			return;
		}
		long rank = MoneyManager.GetRank(mny.getAmount());
		if (rank == 0L) {
			Send(null);
			return;
		}
		PrtMsg("[Show Rank] ID = " + m_id + ", Rank = " + rank);
		Send(rank);
	}
	
	/**
	 * Fetch Orign Music List Handler
	 */
	public void FetchOrignList()
	{
		List<UserMusicListItem> lst = PropertyManager.FetchUserPropertyList(m_id);
		if (lst == null) {
			PrtMsg("[FetchOrignList] - -");
			Send(null);
		}
		UserMusicListItem itm;
		List<Long> lstsend = new LinkedList<Long>();
		int sz = lst.size();
		for (int i = 0; i < sz; ++i) {
			itm = lst.get(i);
			if (itm.MusicSourceID == 0L) {
				lstsend.add(itm.MusicID);
			}
		}
		sz = lstsend.size();
		long[] arysend = new long[sz];
		for (int i = 0; i < sz; ++i) {
			arysend[i] = lstsend.get(i);
		}
		PrtMsg("[FetchOrignList] [OK]");
		Send(arysend);
	}
	
	/**
	 * Fetch Property List Handler
	 */
	public void FetchPropertyList()
	{
		List<UserMusicListItem> lup = PropertyManager.FetchUserPropertyList(m_id);
		if (lup == null) {
			PrtMsg("[FetchPropertyList] - -");
			Send(null);
			return;
		}
		org.syc.music_trading_system.Item.Music msc;
		ItemList lst = new ItemList();
		UserMusicListItem umli;
		int sz = lup.size();
		for (int i = 0; i < sz; ++i) {
			umli = lup.get(i);
			boolean b = umli.SellState == 0? false: true;
			msc = new org.syc.music_trading_system.Item.Music(umli.MusicID, umli.MusicName, b, umli.MusicSourceID);
			lst.add(msc);
		}
		PrtMsg("[FetchPropertyList] [OK]");
		Send(lst);
	}
	
	/**
	 * Fetch Selling List Handler
	 */
	public void FetchSellingList()
	{
		List<UserMusicListItem> lup = PropertyManager.FetchUserSellingPropertyList(m_id);
		if (lup == null) {
			PrtMsg("[FetchSellingList] - -");
			Send(null);
			return;
		}
		TransactionItemList lst = new TransactionItemList();
		TransactionItem titm;
		UserMusicListItem umli;
		org.syc.music_trading_system.Item.Music msc;
		int sz = lup.size();
		for (int i = 0; i < sz; ++i) {
			umli = lup.get(i);
			boolean b = umli.SellState == 0? false: true;
			msc = new org.syc.music_trading_system.Item.Music(umli.MusicID, umli.MusicName, b, umli.MusicID);
			titm = new TransactionItem(umli.MusicID, m_id, msc, umli.Price);
			
			lst.add(titm);
		}
		PrtMsg("[FetchSellingList] [OK]");
		Send(lst);
	}
	
	
	public void GetReward()
	{
		long amount = -2L;
		MoneyManager.EarnMoney(m_id, amount);
		ShowMoney();
	}
	
	
	/**
	 * Start Selling a Music Handler
	 */
	public void Sell(long mid, long price)
	{
		PrtMsg("[Sell] MusicID = " + mid + ", Price = " + price);
		Property pty = PropertyManager.FetchProperty(m_id, mid);
		if (pty == null) {
			PrtMsg("[Sell] - -");
			Send(ReturnInfo.SERVER_ERROR);
			return;
		}
		pty.setPrice(price);
		pty.setState(Property.STATE_SELLING);
		PropertyManager.UpdateProperty(pty);
		PrtMsg("[Sell] [OK]");
		Send(ReturnInfo.SUCCESS);
	}
	
	/**
	 * Stop Selling a Music Handler
	 */
	public void HoldOn(long mid)
	{
		Property pty = PropertyManager.FetchProperty(m_id, mid);
		if (pty == null) {
			Send(ReturnInfo.SERVER_ERROR);
			return;
		}
		pty.setState(Property.STATE_POSSESS);
		PropertyManager.UpdateProperty(pty);
		Send(ReturnInfo.SUCCESS);
	}
	
	
	/**
	 * Buy a Music Handler
	 */
	public void Buy(long mid, long sellerid)
	{
		Property pty = PropertyManager.FetchProperty(sellerid, mid);
		if (pty == null) {
			Send(ReturnInfo.SERVER_ERROR);
			return;
		}
		if (pty.getState() != Property.STATE_SELLING) {
			Send(ReturnInfo.SERVER_ERROR);
			return;
		}
		long price = pty.getPrice();
		Money buyermoney = MoneyManager.FetchMoneyInfo(m_id);
		if (buyermoney.getAmount() < price) {
			Send(ReturnInfo.NoEnoughMoney);
			return;
		}
		
		//Start Transaction
		MoneyManager.PayMoney(m_id, price);
		MoneyManager.EarnMoney(sellerid, price);
		pty.setUid(m_id);
		pty.setCost(price);
		pty.setState(Property.STATE_POSSESS);
		PropertyManager.UpdateProperty(pty);
		Send(ReturnInfo.SUCCESS);
		
		//Log
		TransactionLogger.Log(m_id, sellerid, mid, new Date(), pty.getPrice());
	}
	
	/**
	 * Commodity List Request Handler
	 */
	public void GetCommodityList(long srcid, long prcl, long prch, int idx)
	{
		final int UNITSIZE = 8;
		PrtMsg("[GetCommodityList] srcid = " + srcid + ", prcl = " + prcl + ", prch = " + prch + ", idx = " + idx + "[..]");

		if (prcl < 0 || prch < 0 || prcl > prch) {
			prcl = 0;
			prch = 1000000000L;
		}
		List<CommodityItem> lstbuf;
		if (srcid < 0) {
			PrtMsg("[GetCommodityList] [No Source ID Specified]");
			lstbuf = PropertyManager.RequireCommodityList(m_id, prcl, prch);
		} else {
			PrtMsg("[GetCommodityList] [Source ID Specified]");
			lstbuf = PropertyManager.RequireCommodityList(m_id, srcid, prcl, prch);
		}
		PrtMsg("[GetCommodityList] srcid = " + srcid + ", prcl = " + prcl + ", prch = " + prch + ", idx = " + idx + "[..]");
		if (lstbuf == null) {
			PrtMsg("[GetCommodityList] - -");
			Send(ReturnInfo.NoCommodityAvailable);
			return;
		}
		
		TransactionItemList lstsend = new TransactionItemList();
		TransactionItem titm;
		CommodityItem citm;
		org.syc.music_trading_system.Item.Music itm;
		int sz = lstbuf.size();
		PrtMsg("[GetCommodityList] Item Count = " + sz);
		if (sz < UNITSIZE) {
			for (int i = 0; i < sz; ++i) {
				citm = lstbuf.get(i);
				itm = new org.syc.music_trading_system.Item.Music(citm.MusicID, citm.MusicName, true, citm.MusicSourceID);
				titm = new TransactionItem(0L, citm.UserID, itm, citm.Price);
				//PrtMsg("[GetCommodityList] " + citm.UserID);
				lstsend.add(titm);
			}
		} else {
			if (idx < 0) {
				Random rnd = new Random();
				idx = (int) ((sz - UNITSIZE) * rnd.nextFloat());
			}
			PrtMsg("[GetCommodityList] Index = " + idx);
			int base = idx * UNITSIZE;
			for (int i = 0; i < UNITSIZE; ++i) {
				citm = lstbuf.get(base + i);
				itm = new org.syc.music_trading_system.Item.Music(citm.MusicID, citm.MusicName, true, citm.MusicSourceID);
				titm = new TransactionItem(0L, citm.UserID, itm, citm.Price);
				lstsend.add(titm);
			}
		}
		PrtMsg("[GetCommodityList] [OK]");
		Send(lstsend);
	}
	
	/*
	public void InformationRefresh()
	{
		LoginData ldt = null;
		Account acc = null;
		Money mny = null;
		List<UserMusicListItem> lup = null;
		acc = AccountManager.FetchAccount(m_id);
		mny = MoneyManager.FetchMoneyInfo(m_id);
		lup = PropertyManager.FetchUserPropertyList(m_id);

		if (acc == null) {
			Send(ReturnInfo.SERVER_ERROR);				
		}
		ldt = new LoginData();
		ldt.UserID = acc.getId();
		ldt.UserName = acc.getName();
		ldt.MoneyAmount = mny.getAmount();
		ldt.MusicList = lup;
		Send(ldt);
	}*/
	
	/**
	 * Send an object to the User being served
	 */
	private void Send(Object obj)
	{
		try {
			m_oos.writeObject(obj);
		} catch(Exception e) {
			PrtMsg("send error");
		}
	}
	
	private void PrtMsg(String msg)
	{
		System.out.println(msg);
	}
}
