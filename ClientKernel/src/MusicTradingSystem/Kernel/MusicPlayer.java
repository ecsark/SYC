package MusicTradingSystem.Kernel;

import Framework.Enum.ReturnInfo;
import MusicTradingSystem.Item.Chorus;
import MusicTradingSystem.Item.Melody;

public class MusicPlayer 
{
	public static ReturnInfo play(Chorus chorus, Melody melody)
	{
		return ReturnInfo.SUCCESS;
	}
	
	public static ReturnInfo play(Melody melody)
	{
		return ReturnInfo.SUCCESS;
	}
	

}
