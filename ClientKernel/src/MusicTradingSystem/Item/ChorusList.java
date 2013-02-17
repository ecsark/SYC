//////////////////////////////////////////////////////
///sever sync necessary
//////////////////////////////////////////////////////
package MusicTradingSystem.Item;

import java.util.Vector;

import Framework.Item.ItemList;

public class ChorusList extends ItemList 
{
	private Vector<Chorus> chorusList;
	public ChorusList(ChorusList cL) 
	{
		chorusList = new Vector<Chorus>(cL.chorusList);
	}
}
