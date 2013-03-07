package org.syc.music_trading_system.Item;

import java.io.Serializable;
import java.util.Date;


import org.syc.framework.Item.*;
import org.syc.framework.Kernel.*;

public class ExSellingItem extends TransactionItem implements Serializable
{
	private long puchasePrice;
	private Date putOnTime;
	
	public ExSellingItem()
	{
		super();
		puchasePrice = 0L;
	}
	
	public ExSellingItem(TransactionItem tItem, long pPrice, Date pTime)
	{
		super(tItem);
		this.puchasePrice = pPrice;
		this.putOnTime = pTime;
	}
	
	public long getPuchasePrice()
	{
		return puchasePrice;
	}
	
	public void setPuchasePrice(long pPrice)
	{
		this.puchasePrice = pPrice;
	}
	
	public Date getPutOnTime()
	{
		return putOnTime;
	}
	
	@Override
	public boolean equals(Object o)
	{
		if(o == null)
			return false;
		else if(((ExSellingItem)o).getId() == this.getId())
			return true;
		return false;
	}

}
