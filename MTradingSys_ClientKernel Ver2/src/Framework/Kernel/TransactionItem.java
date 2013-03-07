//////////////////////////////////////////////////////
///sever sync necessary
//////////////////////////////////////////////////////
package Framework.Kernel;

import java.io.Serializable;
import Framework.Item.*;
import Framework.Const.Command;
import Framework.Const.ReturnInfo;
import Framework.Util.NetTool;

public class TransactionItem implements Serializable
{
	private static final long serialVersionUID = 1L;
	protected final long id;
	transient protected final String sellerName;
	protected final Item item;
	protected final long price;
	
	public TransactionItem(long id, String sellerName, Item item, long price)
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
    	this.price = trans.price;
    }
    public TransactionItem()
    {
    	this.id = 0;
    	this.sellerName = "";
    	this.item = new Item();
    	this.price = 0;
    }
    public long getId()
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
    public long getPrice()
    {
    	return price;
    }

}
