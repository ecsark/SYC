//////////////////////////////////////////////////////
///sever sync necessary
//////////////////////////////////////////////////////
package Framework.Kernel;

import java.io.Serializable;

import Framework.Item.ItemList;
import Framework.Item.Money;


public class User implements Serializable
{
	protected final String id;
	protected final String name;
	public String password;
	protected Money property;
	protected ItemList itemList;
	
	public User(User u)
	{
		id = u.id;
		name = u.name;
		property = new Money(u.property);
		itemList = new ItemList(u.itemList);
	}
	
	public String getId()
	{
		return id;
	}
	public String getName()
	{
		return name;
	}
	public Money getProperty()
	{
		return new Money(property);
	}
	
}
