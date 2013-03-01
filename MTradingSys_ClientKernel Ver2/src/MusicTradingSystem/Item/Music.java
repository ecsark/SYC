//////////////////////////////////////////////////////
///sever sync necessary
//////////////////////////////////////////////////////
package MusicTradingSystem.Item;

import java.io.Serializable;

import Framework.Item.*;

public class Music extends Item implements Serializable
{
	private static final long serialVersionUID = 7742606420706509464L;
	protected long originMusicId;
	public Music(Music c)
	{
		super(c);
		originMusicId = 0;
	}
	
	public Music(long id, String name, boolean isAuction, long originMusicId)
	{
		super(id, name, isAuction);
		this.originMusicId = originMusicId;
	}
	
	public long getOriginMusicId()
	{
		return originMusicId;
	}
	public void setOriginMusicId(long id)
	{
		originMusicId = id;
	}
}
