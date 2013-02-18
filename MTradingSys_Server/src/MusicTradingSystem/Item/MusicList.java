//////////////////////////////////////////////////////
///sever sync necessary
//////////////////////////////////////////////////////
package MusicTradingSystem.Item;

import java.io.Serializable;
import java.util.Vector;

import Framework.Item.ItemList;

public class MusicList extends ItemList implements Serializable 
{
	private Vector<Music> musicList;
	public MusicList(MusicList mL) 
	{
		musicList = new Vector<Music>(mL.musicList);
	}
}
