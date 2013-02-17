//////////////////////////////////////////////////////
///sever sync necessary
//////////////////////////////////////////////////////
package Framework.Kernel;

import java.io.Serializable;
import Framework.Item.*;
import Framework.Enum.Command;
import Framework.Enum.ReturnInfo;
import Framework.Util.NetTool;

public class TransactionItem implements Serializable
{
	protected final String id;
	protected final String sellerName;
	protected final Item item;
	protected final Money price;
	
	public TransactionItem(String id, String sellerName, Item item, Money price)
	{
		this.id = id;
		this.sellerName = sellerName;
		this.item = item;
		this.price = price;
	}
    public TransactionItem(TransactionItem trans)
    {
    	this.id = trans.id;
    	this.sellerName = trans.sellerName;
    	this.item = new Item(trans.item);
    	this.price = new Money(trans.price);
    }
    
    public String getId()
    {
    	return id;
    }
    public String getSellerName()
    {
    	return sellerName;
    }
    public Item getItem()
    {
    	return item;
    }

}
