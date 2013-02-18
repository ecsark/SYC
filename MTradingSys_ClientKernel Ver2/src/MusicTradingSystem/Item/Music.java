//////////////////////////////////////////////////////
///sever sync necessary
//////////////////////////////////////////////////////
package MusicTradingSystem.Item;

import java.io.Serializable;

import Framework.Item.*;

public class Music extends Item implements Serializable
{
	public Music(Music c)
	{
		super(c);
	}
	
	Music(String id, String name, boolean isAuction)
	{
		super(id, name, isAuction);
	}
}
