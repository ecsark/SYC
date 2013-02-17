//////////////////////////////////////////////////////
///sever sync necessary
//////////////////////////////////////////////////////
package Framework.Item;

public class Item 
{
	protected final String id;
	protected final String name;
	protected boolean isAuction;
	
	public Item(Item i)
	{
		this.id = i.id;
		this.name = i.name;
		isAuction = i.isAuction;
	}
	public Item(String id, String name, boolean isAuction)
	{
		this.id = id;
		this.name = name;
		this.isAuction = isAuction;
	}
	
	public String getId()
	{
		return id;
	}
	
	public String getName()
	{
		return name;
	}
	
	public boolean isAuction()
	{
		return isAuction;
	}
	
	public void setAuction()
	{
		isAuction = true;
	}
	
	public void resetAuction()
	{
		isAuction = false;
	}
	
	@Override
	public boolean equals(Object o)
	{
		if(o == null)
			return false;
		else if(((Item)o).id.equals(this.id))
			return true;
		return false;
	}
}
