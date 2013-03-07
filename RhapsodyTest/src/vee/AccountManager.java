package vee;

public class AccountManager {
	
	/**
	 * Define the Representation of Invalid ID
	 */
	public static final long NULL_ID = 0L;
	
	/**
	 * Define the Root ID (Min ID)
	 */
	private static final long ROOT_ID = 10000L;
	
	/**
	 * Get a Currently Available New ID
	 */
	private static long GetAvailableId()
	{
		String hsql = "select max(t.id) from Account as t";
		Object obj = HibernateSession.UniqueQueryResult(hsql);
		long maxid = (obj == null? ROOT_ID: (long) obj);
		return maxid + 1;
	}
	
	/**
	 * Check Conflict Name
	 */
	private static boolean NameConflict(String name)
	{
		String hsql = "from Account where name = '" + name + "'";
		try {
			Object obj = HibernateSession.UniqueQueryResult(hsql);
			return obj == null? false: true;
		} catch(Exception e) {
			return true;
		}
	}
	
	/**
	 * Create a New Account for a User
	 * @return ID of the Newly Created Account
	 * 			(On Error, 0L)
	 */
	public static long CreateNewAccount(String name, String password)
	{
		long id = GetAvailableId();
		if (id == NULL_ID) {
			System.out.println("No ID available");
			return NULL_ID;
		}
		try {
			if (NameConflict(name) == true) {
				System.out.println("Name Conflict");
				throw new Exception("[CreateAccount]Name Conflict");
			}
			Account acc = new Account(id, name, password, Account.STATE_OFFLINE);
			HibernateSession.StartSession().save(acc);
			HibernateSession.FinishSession();
		} catch(Exception e) {
			System.out.println(e.getMessage());
			return NULL_ID;
		}
		return id;
	}

	/**
	 * Fetch an account from database by ID
	 */
	public static Account FetchAccount(long id)
	{
		String hsql = "from Account where id = " + id;
		Object obj = HibernateSession.UniqueQueryResult(hsql);
		return obj != null? (Account) obj: null;
	}
	
	/**
	 * Fetch an account from database by Name
	 */
	public static Account FetchAccount(String name)
	{
		String hsql = "from Account where name = '" + name + "'";
		Object obj = HibernateSession.UniqueQueryResult(hsql);
		return obj != null? (Account) obj: null;
	}	
	
	/**
	 * A User (Specified by ID) Login, Providing its Password
	 */
	public static int Login(long id, String password)
	{
		try {
			Account acc = FetchAccount(id);
			if (password.equals(acc.getPassword()) == false) {
				return 1;
			}
			acc.setState(Account.STATE_ONLINE);
			HibernateUpdateAccount(acc);
		} catch(Exception e) {
			return 2;
		}
		return 0;
	}
	
	/**
	 * A User (Specified by Name) Login, Providing its Password
	 */
	public static int Login(String name, String password)
	{
		try {
			Account acc = FetchAccount(name);
			if (password.equals(acc.getPassword()) == false) {
				return 1;
			}
			acc.setState(Account.STATE_ONLINE);
			HibernateUpdateAccount(acc);
		} catch(Exception e) {
			return 2;
		}
		return 0;
	}
	
	/**
	 * A User (Specified by ID) Logout
	 */
	public static int Logout(long id)
	{
		try {
			Account acc = FetchAccount(id);
			acc.setState(Account.STATE_OFFLINE);
			HibernateUpdateAccount(acc);
		} catch(Exception e) {
			return 1;
		}
		return 0;
	}
	
	/**
	 * Change a user's name
	 */
	public static int ChangeUserName(long id, String name) {
		Account acc = FetchAccount(id);
		if (acc == null) {
			return 1; // No such ID
		}
		acc.setName(name);
		HibernateUpdateAccount(acc);
		return 0;
	}
	
	/**
	 * Change a User's Password
	 */
	public static int ChangePassword(long id, String password, String newone)
	{
		try {
			Account acc = FetchAccount(id);
			if (acc == null) {
				throw new Exception("No such Account");
			}
			if (password.equals(acc.getPassword()) == false) {
				throw new Exception("Wrong Password");
			}
			acc.setPassword(newone);
			HibernateUpdateAccount(acc);
		} catch(Exception e) {
			return 1;
		}
		return 0;
	}
	
	/**
	 * Update an Account
	 */
	private static void HibernateUpdateAccount(Account acc)
	{
		HibernateSession.StartSession().update(acc);
		HibernateSession.FinishSession();
	}

	
}
