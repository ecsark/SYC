package Framework.Kernel;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import Framework.Const.Command;
import Framework.Const.ReturnInfo;
import Framework.Item.Item;
import Framework.Item.Money;
import Framework.Util.NetTool;

public class Handler 
{
	public static int cmd;
	public static String userId; 
	public static ObjectInputStream ois;
	public static ObjectOutputStream oos;
	
	public Handler(int cmd, String userId, ObjectInputStream ois, ObjectOutputStream oos)
	{
		this.cmd = cmd;
		this.userId = userId;
		this.ois = ois;
		this.oos = oos;
	}
	
	public void handle()
	{
		/**Logical code here**/
		 
	}
	public static ReturnInfo getTransItemList()
	{
		/*auction.list = (TransactionItemList)NetTool.Receive(auction.getType, auction.searchArgs, user.id);
		return ReturnInfo.SUCCESS;*/
		
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
	///Ω±¿¯
	public static ReturnInfo getMoney()
	{
		user.property.set((Money)NetTool.Receive(Command.GET_MONEY, user.id));
		return ReturnInfo.SUCCESS;
	}
	///Userœ‡πÿ
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
		user = (User)NetTool.Receive(Command.REFRESH_USER, user.id);
		return ReturnInfo.SUCCESS;
	}
	public static boolean hasTheSameName()
	{
		return (boolean)NetTool.Receive(Command.HAS_THE_SAME_NAME, user.id);
	}
}
