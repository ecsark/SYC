package Framework.Kernel;


import Framework.Const.Command;
import Framework.Const.ReturnInfo;
import Framework.Item.Item;
import Framework.Item.ItemList;
import Framework.Item.Money;
import Framework.Util.NetTool;

public class Interface
{
	public static User user;
	public static Auction auction;
	
	///交易相关
	public static ReturnInfo getTransItemList()
	{
		Object inObj = NetTool.Receive(auction.getType, auction.searchArgs, user.id);
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
		ReturnInfo returnInfo = NetTool.send(Command.MAKE_TRANSACTION, trans, user.id);
		//do Local
		if(returnInfo == ReturnInfo.SUCCESS)
			user.itemList.add(trans.item);
		return returnInfo;
	}
	/*fin*/
	/**ATTENTION: UI must make sure item.isAuction is false**/
	public static ReturnInfo registItem(Item item, User user)
	{
		ReturnInfo returnInfo = NetTool.send(Command.REGIST_ITEM, item, user.id);
		//do Local
		if(returnInfo == ReturnInfo.SUCCESS)
			user.itemList.searchId(item.getId()).setAuction();
		return returnInfo;
	}
	///奖励
	public static ReturnInfo getMoney()
	{
		Object inObj = NetTool.Receive(Command.GET_MONEY, user.id);
		if(inObj != null)
		{
			user.property.set((Money)inObj);
			return ReturnInfo.SUCCESS;
		}
		else {
			return ReturnInfo.UNKNOWN_EXPECTION;
		}
	}
	///=========================================================================
	///User相关
	/*fin*/
	public static  ReturnInfo login()
	{
		ReturnInfo returnInfo = NetTool.send(Command.LOG_IN, user.password, user.id);
		if(returnInfo == ReturnInfo.SUCCESS)
			returnInfo = refreshUser();
		return returnInfo;
	}
	/*fin*/
	public static  ReturnInfo logOut()
	{
		ReturnInfo returnInfo = NetTool.send(Command.LOG_OUT, null, user.id);
		if(returnInfo == ReturnInfo.SUCCESS)
			user = null;
			auction = null;
		return returnInfo;
	}
	/*fin*/
	public static ReturnInfo regist()
	{
		ReturnInfo returnInfo = NetTool.send(Command.REGIST_USER, user, user.id);
		if(returnInfo == ReturnInfo.SUCCESS)
			returnInfo = refreshUser();
		return returnInfo;
	}
	public static ReturnInfo refreshUser()
	{
		Object inObj = NetTool.Receive(Command.REFRESH_USER, user.id);
		if(inObj != null)
		{
			user = (User)inObj;
			return ReturnInfo.SUCCESS;
		}
		else {
			return ReturnInfo.UNKNOWN_EXPECTION;
		}
	}
	/*fin*/
	/**ATTENTION: When the server error occurs, the func also return false**/
	public static boolean hasTheSameName()
	{
		Object inObj = NetTool.Receive(Command.HAS_THE_SAME_NAME, user.id);
		if(inObj != null)
		{
			return (boolean)inObj;
		}
		else{
			return false;
		}
	}
	/*fin*/
	public static ReturnInfo refreshItemList()
	{
		Object inObj = NetTool.Receive(Command.REFRESH_ITEM_LIST, user.id);
		if(inObj != null)
		{
			user.itemList = (ItemList)inObj;
			return ReturnInfo.SUCCESS;
		}
		else{
			return ReturnInfo.UNKNOWN_EXPECTION;
		}
	}
	
	
}
