//////////////////////////////////////////////////////
///sever sync necessary
//////////////////////////////////////////////////////
package Framework.Item;

import java.util.Vector;

import Framework.Const.ReturnInfo;

public class ItemList 
{
	protected Vector<Item> itemList;
	
	public ItemList(ItemList iL)
	{
		itemList = new Vector<Item>(iL.itemList);
	}
	public ItemList()
	{
		itemList = new Vector<Item>();
	}
	
	public int size()
	{
		return itemList.size();
	}
	public Item get(int index)
	{
		return itemList.get(index);
	}
	public boolean isEmpty()
	{
		return itemList.isEmpty();
	}
	public ReturnInfo add(Item item)
	{
		if(itemList.add(item))
			return ReturnInfo.SUCCESS;
		else 			
			return ReturnInfo.FAIL;
	}
	public ReturnInfo delete(Item item)
	{
		if(itemList.remove(item))
			return ReturnInfo.SUCCESS;
		else
			return ReturnInfo.FAIL;
	}
	public Item searchId(String id)
	{
		Item i = new Item(id, null, false);
		int findIndex = itemList.indexOf(i);
		if( findIndex == -1)
			return null;
		else
			return itemList.get(findIndex);
		}
	/* need complete */
	public ReturnInfo sort(String cmd)
	{
		return ReturnInfo.SUCCESS;
	}
}
