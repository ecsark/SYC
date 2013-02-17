//////////////////////////////////////////////////////
///sever sync necessary
//////////////////////////////////////////////////////
package MusicTradingSystem.Item;

import Framework.Item.Item;

/**ATTENTION: this class is a ReadOnly class**/
public class Melody extends Item
{
	Melody(Melody m)
	{
		super(m);
	}
	
	Melody(String id, String name)
	{
		super(id, name, false);
	}
	
	@Override
	public void setAuction(){}
	@Override
	public void resetAuction(){}
}
