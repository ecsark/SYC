package org.syc.framework.Kernel;


public class Auction 
{
	public TransactionItemList list;
	public TransactionItemList sellingList;
	public searchArgs args;
	
	public Auction()
	{
		list = new TransactionItemList();
		sellingList = new TransactionItemList();
		args = new searchArgs();		
	}
	
	public class searchArgs
	{
		public long musicSrcID;
		public long lowerBound;
		public long upperBound;
		public int pageNum;
		
		public searchArgs()
		{
			musicSrcID = -1L;
			lowerBound = -1L;
			upperBound = -1L;
			pageNum = -1;		
		}
		
		public String[] toStringArray()
		{
			String[] args = {Long.toString(musicSrcID), Long.toString(lowerBound),
					 Long.toString(upperBound), Integer.toString(pageNum)};
			return args;
		}
	}

}

