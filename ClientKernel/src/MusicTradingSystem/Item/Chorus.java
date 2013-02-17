//////////////////////////////////////////////////////
///sever sync necessary
//////////////////////////////////////////////////////
package MusicTradingSystem.Item;

import java.io.Serializable;

import Framework.Item.*;

public class Chorus extends Item implements Serializable
{
	private final String melodyId;
	
	public Chorus(Chorus c)
	{
		super(c);
		melodyId = c.melodyId;
	}
	
	Chorus(String id, String name, String melodyId, boolean isAuction)
	{
		super(id, name, isAuction);
		this.melodyId = melodyId;
	}
	
	public String getMelodyId()
	{
		return melodyId;
	}
}
