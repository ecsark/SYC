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
	private static final long serialVersionUID = -8585218298022422187L;
	protected final long id;
	protected final long sellerId;
	protected final Item item;
	protected final long price;
	
	public TransactionItem(long id, long sellerId, Item item, long price)
	{
		this.id = id;
		this.sellerId = sellerId;
		this.item = item;
		this.price = price;
	}
    public TransactionItem(TransactionItem trans)
    {
    	this.id = trans.id;
    	this.sellerId = trans.sellerId;
    	this.item = new Item(trans.item);
    	this.price = trans.price;
    }
    public TransactionItem()
    {
    	this.id = 0;
    	this.sellerId = 0L;
    	this.item = new Item();
    	this.price = 0;
    }
    public long getId()
    {
    	return id;
    }
    public long getSellerId()
    {
    	return sellerId;
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
