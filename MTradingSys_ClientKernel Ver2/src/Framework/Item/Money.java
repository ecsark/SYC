//////////////////////////////////////////////////////
///sever sync necessary
//////////////////////////////////////////////////////
package Framework.Item;

import java.io.Serializable;
import java.util.Set;

import Framework.Const.ReturnInfo;

public class Money implements Serializable
{
	private long value;
	public Money(Money m)
	{
		value = m.value;
	}
	
	public void set(Money m)
	{
		
	}
}
