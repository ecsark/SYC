package MusicTradingSystem.Item;

import Framework.Item.Item;

public class OriginalMusic extends Item
{
	public OriginalMusic() 
	{
		super();
	}
	public OriginalMusic(long id, String name, boolean isAuction)
	{
		super(id, name, false);
	}
	public OriginalMusic(OriginalMusic oM)
	{
		super(oM);
	}
}
