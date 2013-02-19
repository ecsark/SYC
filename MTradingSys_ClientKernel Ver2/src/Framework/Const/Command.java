//////////////////////////////////////////////////////
///sever sync necessary
//////////////////////////////////////////////////////
package Framework.Const;

public class Command {

	public static int GET_LIST = 0;  //ClientIn: TransactionList & String[] args
	public static int MAKE_TRANSACTION = 0; //ClientOut: TransactionItem
	public static int REGIST_ITEM = 1; //ClientOut: TransactionItem
	public static int GET_MONEY = 1;  //ClientIn: Money
	public static int LOG_IN = 1;  //ClientOut: User
	public static int LOG_OUT = 1;  //ClientOut: Null
	public static int REGIST_USER = 1;  //ClientOut: User
	public static int REFRESH_USER = 1;  //ClientIn: User
	public static int HAS_THE_SAME_NAME = 1;  //ClientIn: boolean
	public static int REFRESH_ITEM_LIST = 1;  //ClientIn: ItemList
	
}
