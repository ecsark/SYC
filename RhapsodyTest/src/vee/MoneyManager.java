package vee;

public class MoneyManager {
	
	/**
	 * Create a new Bank Account for a new User
	 */
	public static int CreateNewAccount(long id)
	{
		try {
			if (FetchMoneyInfo(id) != null)
				throw new Exception("[CreateBankAccount]ID Existed");
			Money mny = new Money(id, 0L, Money.STATE_NORMAL);
			HibernateSession.StartSession().save(mny);
			HibernateSession.FinishSession();
		} catch(Exception e) {
			return 1;
		}
		return 0;
	}
	
	/**
	 * Fetch a Money record from database
	 */
	public static Money FetchMoneyInfo(long id)
	{
		String hsql = "from Money where id = " + id;
		try {
			Object obj = HibernateSession.UniqueQueryResult(hsql);
			if (obj != null) {
				return (Money) obj;
			} else {
				System.out.println("[FetchMoneyInfo] No such BankAccount");
				return null;
			}
		} catch(Exception e) {
			System.out.println("[FetchMoneyInfo] exc");
			return null;
		}
	}
	
	/**
	 * User specified by id gets a certain amount of money
	 */
	public static int EarnMoney(long id, long amount)
	{
		try {
			Money mny = FetchMoneyInfo(id);
			mny.setAmount(mny.getAmount() + amount);
			UpdateMoneyInfo(mny);
		} catch(Exception e) {
			return 1;
		}
		return 0;
	}
	
	/**
	 * User specified by id loses a certain amount of money
	 */
	public static int PayMoney(long id, long amount)
	{
		try {
			Money mny = FetchMoneyInfo(id);
			if (mny.getAmount() < amount)
				throw new Exception("No Enough Money to Pay");
			mny.setAmount(mny.getAmount() - amount);
			UpdateMoneyInfo(mny);
		} catch(Exception e) {
			return 1;
		}
		return 0;
	}
	
	
	public static long GetRank(long amount)
	{
		String hsql =
				"select count(m.id) " +
				"from Money as m " +
				"where amount >= " + amount;
		Object obj = HibernateSession.UniqueQueryResult(hsql);
		if (obj == null) {
			return 0L;
		} else {
			return (long) obj;
		}
	}
	
	/**
	 * Update Money Information
	 */
	private static void UpdateMoneyInfo(Money mny)
	{
		HibernateSession.StartSession().update(mny);
		HibernateSession.FinishSession();
	}
}
