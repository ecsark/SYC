package Framework.Kernel;


import Framework.Const.Command;
import Framework.Const.ReturnInfo;
import Framework.Item.Item;
import Framework.Item.ItemList;
import Framework.Item.Money;
import Framework.Util.NetTool;
import MusicTradingSystem.Item.MusicList;
import MusicTradingSystem.Item.OriginalMusic;
import MusicTradingSystem.Item.OriginalMusicList;

public class Interface
{
	public static User user = new User();
	public static Auction auction = new Auction();
	public static ItemList itemList = new ItemList();
	public static ItemList originalMusicList = new ItemList();
	
	
	///交易相关
	public static ReturnInfo getTransItemList()
	{
		Object inObj = NetTool.Receive(Command.GET_COMMODITY_LIST, auction.args.toStringArray(), user.getId());
		if(inObj != null)
		{
			auction.list = (TransactionItemList)inObj;
			return ReturnInfo.SUCCESS;
		}
		else{
			return ReturnInfo.UNKNOWN_EXPECTION;
		}
	}
	/*fin*/
	/**ATTENTION: UI must refresh the List & auction UI after this func**/
	public static ReturnInfo makeTransaction(TransactionItem tItem)
	{
		ReturnInfo returnInfo = NetTool.send(Command.BUY, tItem, user.getId());
		//do Local
		if(returnInfo == ReturnInfo.SUCCESS)
			itemList.add(tItem.getItem());
		return returnInfo;
	}
	/*fin*/
	/**ATTENTION: UI must make sure item.isAuction is false**/
	public static ReturnInfo SellItem(TransactionItem tItem)
	{
		ReturnInfo returnInfo = NetTool.send(Command.SELL, tItem, user.id);
		//do Local
		if(returnInfo == ReturnInfo.SUCCESS)
			itemList.searchId(tItem.getItem().getId()).setAuction();
		return returnInfo;
	}
	/*fin*/
	/**ATTENTION: UI must refresh the List & auction UI after this func**/
	public static ReturnInfo cancelSellItem(TransactionItem tItem)
	{
		ReturnInfo returnInfo = NetTool.send(Command.HOLDON, tItem, user.getId());
		if(returnInfo == ReturnInfo.SUCCESS)
			itemList.searchId(tItem.getItem().getId()).resetAuction();
		return returnInfo;
	}
	
	public static ReturnInfo refreshSellingList()
	{
		Object inObj = NetTool.Receive(Command.FETCH_SELLING_LIST, user.getId());
		if(inObj != null)
		{
			auction.sellingList = (TransactionItemList)inObj;
			return ReturnInfo.SUCCESS;
		}
		else{
			return ReturnInfo.UNKNOWN_EXPECTION;
		}
	}
	///奖励
	public static ReturnInfo getReward()
	{
		Object inObj = NetTool.Receive(Command.GET_REWARD, user.id);
		if(inObj != null)
		{
			user.setProperty((long)inObj);
			return ReturnInfo.SUCCESS;
		}
		else {
			return ReturnInfo.UNKNOWN_EXPECTION;
		}
	}
	///=========================================================================
	///User相关
	/*fin*/
	public static ReturnInfo login()
	{
		long id = (long)NetTool.receiveByName(Command.FETCH_ID, user.getName());
		if(id != 0L && id != -1L)
			user.setId(id);
		else if(id == -1L)
			return ReturnInfo.REQUEST_TIMEOUT;
		else 
			return ReturnInfo.UNKNOWN_EXPECTION;
		ReturnInfo returnInfo = NetTool.send(Command.LOGIN, user.getPassword(), user.getId());
		if(returnInfo == ReturnInfo.SUCCESS)
		{
			refreshProperty();
			refreshRank();
		}
		return returnInfo;
	}
	/*fin*/
	public static ReturnInfo logout()
	{
		ReturnInfo returnInfo = NetTool.send(Command.LOGOUT, null, user.getId());
		if(returnInfo == ReturnInfo.SUCCESS)
			user = new User();
			auction = new Auction();
		return returnInfo;
	}
	/*fin*/
	public static ReturnInfo regist()
	{
		ReturnInfo returnInfo = NetTool.send(Command.NEW_ACCOUNT, user, user.getId());		
		return returnInfo;
	}

	/*fin*/
	public static ReturnInfo refreshProperty()
	{
		Object inObj = NetTool.Receive(Command.SHOW_MONEY, user.getId());
		if(inObj != null)
		{
			user.setProperty((long)inObj);
			return ReturnInfo.SUCCESS;
		}
		else {
			return ReturnInfo.UNKNOWN_EXPECTION;
		}
	}
	/*fin*/
	public static ReturnInfo refreshRank()
	{
		Object inObj = NetTool.Receive(Command.SHOW_RANK, user.getId());
		if(inObj != null)
		{
			user.setRank((long)inObj);
			return ReturnInfo.SUCCESS;
		}
		else {
			return ReturnInfo.UNKNOWN_EXPECTION;
		}
	}
	
	public static ReturnInfo refreshItemList()
	{
		Object inObj = NetTool.Receive(Command.FETCH_PROPERTY_LIST, user.getId());
		if(inObj != null)
		{
			itemList = new ItemList((ItemList)inObj);
			return ReturnInfo.SUCCESS;
		}
		else {
			return ReturnInfo.UNKNOWN_EXPECTION;
		}
	}
	
	public static ReturnInfo refreshOriginalMusicList()
	{
		Object inObj = NetTool.Receive(Command.FETCH_ORINGIN_LIST, user.getId());
		long[] list;
		if(inObj != null)
		{
			list = (long[])inObj;
			for (int i = 0; i < list.length; i++) 
			{
				originalMusicList.add(new OriginalMusic(list[i], "test", false));
			}
			return ReturnInfo.SUCCESS;
		}
		else{
			return ReturnInfo.UNKNOWN_EXPECTION;
		}
	}
	/*fin*/
	/**ATTENTION: When the server error occurs, the func also return false**/
	public static boolean nameConfliction()
	{
		
		Object inObj = NetTool.receiveByName(Command.NAME_CONFLICT, user.getName());
		if(inObj != null)
		{
			return (boolean)inObj;
		}
		else{
			return false;
		}
	}	
	
}
