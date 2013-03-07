//////////////////////////////////////////////////////
///sever sync necessary
//////////////////////////////////////////////////////
package MusicTradingSystem.Item;

import java.io.Serializable;
import java.util.Vector;

import Framework.Item.ItemList;

public class MusicList extends ItemList implements Serializable 
{
	private static final long serialVersionUID = 1063745245827961741L;
	private Vector<Music> musicList;
	public MusicList(MusicList mL) 
	{
		musicList = new Vector<Music>(mL.musicList);
	}
}
