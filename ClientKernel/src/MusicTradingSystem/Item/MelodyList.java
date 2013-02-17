//////////////////////////////////////////////////////
///sever sync necessary
//////////////////////////////////////////////////////
package MusicTradingSystem.Item;

import java.util.Vector;

import Framework.Item.ItemList;

public class MelodyList extends ItemList 
{
	private Vector<Melody> melodyList;
	public MelodyList(MelodyList cL) 
	{
		melodyList = new Vector<Melody>(cL.melodyList);
	}
}
