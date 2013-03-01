//////////////////////////////////////////////////////
///sever sync necessary
//////////////////////////////////////////////////////
package Framework.Kernel;

import java.io.Serializable;
import java.util.Vector;

import Framework.Const.ReturnInfo;

/**ATTENTION: this class is a ReadOnly class**/
public class TransactionItemList implements Serializable
{
	private static final long serialVersionUID = -6119271035090942037L;
	protected Vector<TransactionItem> transactionList;
	
	public TransactionItemList()
	{
		transactionList = new Vector<TransactionItem>();
	}
	public TransactionItemList(TransactionItemList tL) 
	{
		transactionList = new Vector<TransactionItem>(tL.transactionList);
	}	
	public int size()
	{
		return transactionList.size();
	}
	public ReturnInfo add(TransactionItem tItem)
	{
		if(transactionList.add(tItem))
			return ReturnInfo.SUCCESS;
		else 			
			return ReturnInfo.UNKNOWN_EXPECTION;
	}
	public TransactionItem get(int index)
	{
		return transactionList.get(index);
	}
	public boolean isEmpty()
	{
		return transactionList.isEmpty();
	}
}
