//////////////////////////////////////////////////////
///sever sync necessary
//////////////////////////////////////////////////////
package MusicTradingSystem.Kernel;

import Framework.Kernel.User;
import MusicTradingSystem.Item.ChorusList;
import MusicTradingSystem.Item.MelodyList;

public class MusicUser extends User 
{
	private ChorusList itemList;
	private MelodyList melodyList;
	private UserInfo userInfo;
	
	public MusicUser(MusicUser u)
	{
		super(u);
		itemList = new ChorusList(u.itemList);
		melodyList = new MelodyList(u.melodyList);
		userInfo = new UserInfo(u.userInfo);
	}
}


class UserInfo
{
	protected static int gender;
	
	public UserInfo(UserInfo uInf)
	{
		
	}
}