package vee;

import java.util.Date;

public class TransactionLogger
{
	
	private static long NULLID = 0L;
	
	
	private static long GetAvailableLogID()
	{
		String hsql =
				"select max(t.tid) " +
				"from Log as t";
		Object obj = HibernateSession.UniqueQueryResult(hsql);
		if (obj == null) {
			return NULLID;
		} else {
			return (long)(obj) + 1L;
		}
	}
	
	public static void Log(long bid, long sid, long mid, Date time, long price)
	{
		long tid = GetAvailableLogID();
		if (tid == NULLID) {
			return;
		}
		TransactionLog log = new TransactionLog();
		log.TransactionID = tid;
		log.BuyerID = bid;
		log.SellerID = sid;
		log.MusicID = mid;
		log.TransactionTime = time;
		log.Price = price;
		HibernateSession.StartSession().save(log);
		HibernateSession.FinishSession();
	}
}
