//////////////////////////////////////////////////////
///sever sync necessary
//////////////////////////////////////////////////////
package Framework.Kernel;

import java.io.Serializable;
import java.util.Vector;

/**ATTENTION: this class is a ReadOnly class**/
public class TransactionItemList implements Serializable
{
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
	public TransactionItem get(int index)
	{
		return transactionList.get(index);
	}
	public boolean isEmpty()
	{
		return transactionList.isEmpty();
	}
}
