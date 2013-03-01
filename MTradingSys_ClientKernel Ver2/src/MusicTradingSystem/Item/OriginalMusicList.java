package MusicTradingSystem.Item;

import java.util.Vector;

import Framework.Item.ItemList;

public class OriginalMusicList extends ItemList 
{
	protected Vector<OriginalMusic> itemList;
	
	public OriginalMusicList()
	{
		itemList = new Vector<OriginalMusic>();
	}
	
	public OriginalMusicList(OriginalMusicList oML)
	{
		itemList = new Vector<OriginalMusic>(oML.itemList);
	}
}
