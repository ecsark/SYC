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
	public static MusicList itemList;
	public static OriginalMusicList oML = new OriginalMusicList();
	
	public static ReturnInfo refreshOriginalMusicList()
	{
		Object inObj = NetTool.Receive(Command.FETCH_ORINGIN_LIST, user.id);
		long[] list;
		if(inObj != null)
		{
			list = (long[])inObj;
			for (int i = 0; i < list.length; i++) 
			{
				oML.add(new OriginalMusic(list[i], "test", false));
			}
			return ReturnInfo.SUCCESS;
		}
		else{
			return ReturnInfo.UNKNOWN_EXPECTION;
		}
	}
	
	///交易相关
	public static ReturnInfo getTransItemList()
	{
		Object inObj = NetTool.Receive(Command.GET_COMMODITY_LIST, auction.args.toStringArray(), user.id);
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
	public static ReturnInfo makeTransaction(TransactionItem trans)
	{
		ReturnInfo returnInfo = NetTool.send(Command.BUY, trans, user.id);
		//do Local
		/*if(returnInfo == ReturnInfo.SUCCESS)
			user.itemList.add(trans.item);*/
		return returnInfo;
	}
	/*fin*/
	/**ATTENTION: UI must make sure item.isAuction is false**/
	public static ReturnInfo SellItem(TransactionItem tItem)
	{
		ReturnInfo returnInfo = NetTool.send(Command.SELL, tItem, user.id);
		//do Local
	//	if(returnInfo == ReturnInfo.SUCCESS)
	//		user.itemList.searchId(tItem.getItem().getId()).setAuction();
		return returnInfo;
	}
	/*fin*/
	/**ATTENTION: UI must refresh the List & auction UI after this func**/
	public static ReturnInfo cancelSellItem(TransactionItem tItem)
	{
		ReturnInfo returnInfo = NetTool.send(Command.HOLDON, tItem, user.id);
		//if(returnInfo == ReturnInfo.SUCCESS)
		//	user.itemList.searchId(tItem.getItem().getId()).resetAuction();
		return returnInfo;
	}
	///奖励
	/*public static ReturnInfo getMoney()
	{
		Object inObj = NetTool.Receive(Command.GET_MONEY, user.id);
		if(inObj != null)
		{
			user.setProperty((long)inObj);
			return ReturnInfo.SUCCESS;
		}
		else {
			return ReturnInfo.UNKNOWN_EXPECTION;
		}
	}*/
	///=========================================================================
	///User相关
	/*fin*/
	public static ReturnInfo login()
	{
		long id = NetTool.getId(user.getName());
		System.out.println(id);
		if(id != 0L && id != -1L)
		{
			user.setId(id);
			
		}
		else if(id == -1L)
			return ReturnInfo.REQUEST_TIMEOUT;
		else 
			return ReturnInfo.UNKNOWN_EXPECTION;
		ReturnInfo returnInfo = NetTool.send(Command.LOGIN, user.getPassword(), user.id);
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
		ReturnInfo returnInfo = NetTool.send(Command.LOGOUT, null, user.id);
		if(returnInfo == ReturnInfo.SUCCESS)
			user = new User();
			auction = new Auction();
		return returnInfo;
	}
	/*fin*/
	public static ReturnInfo regist()
	{
		ReturnInfo returnInfo = NetTool.send(Command.NEW_ACCOUNT, user, user.id);		
		return returnInfo;
	}

	/*fin*/
	public static ReturnInfo refreshProperty()
	{
		Object inObj = NetTool.Receive(Command.SHOW_MONEY, user.id);
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
		Object inObj = NetTool.Receive(Command.SHOW_RANK, user.id);
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
		Object inObj = NetTool.Receive(Command.FETCH_PROPERTY_LIST, user.id);
		if(inObj != null)
		{
		//	user.itemList = new ItemList((ItemList)inObj);
			return ReturnInfo.SUCCESS;
		}
		else {
			return ReturnInfo.UNKNOWN_EXPECTION;
		}
	}
	/*fin*/
	/**ATTENTION: When the server error occurs, the func also return false**/
	/*public static boolean nameConfliction()
	{
		Object inObj = NetTool.Receive(Command.NAME_CONFLICT, user.id);
		if(inObj != null)
		{
			return (boolean)inObj;
		}
		else{
			return false;
		}
	}*/
	/*fin*/
	
	
	
}
