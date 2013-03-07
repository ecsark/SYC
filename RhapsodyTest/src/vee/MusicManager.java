package vee;

public class MusicManager {
	
	/**
	 * Maintain a copy of the current max ID of Music in database
	 */
	private static long lngCurrentMaxId = 0L;
	
	
	private static final int STATE_NORMAL = 0;
	private static final int STATE_FROZEN = -1;
	
	/**
	 * Initialization
	 */
	public static void Initialize() {
		String hsql = "select max(t.id) from Music as t";
		Object obj = HibernateSession.UniqueQueryResult(hsql);
		lngCurrentMaxId = (obj == null? 0L: (long) obj);
	}
	
	/**
	 * Get a currently available new ID.
	 */
	private static long GetAvailableId() {
		return lngCurrentMaxId < Long.MAX_VALUE? ++lngCurrentMaxId: 0L;
	}
	
	/**
	 * Add a new music into database
	 * @return the ID of the newly added Music
	 */
	public static long AddMusic(String name, String url, long srcid) throws Exception {
		long id = GetAvailableId();
		if (id == 0L) {
			throw new Exception("No ID available when adding new Music");
		}
		Music msc = new Music(id, name, url, srcid, STATE_NORMAL);
		HibernateSession.StartSession().save(msc);
		HibernateSession.FinishSession();
		return id;
	}
	
	/**
	 * Freeze a music record in database
	 */
	public static void FrozenMusic(long id) {
		Music msc = FetchMusic(id);
		if (msc != null) {
			msc.setState(STATE_FROZEN);
			UpdateMusic(msc);
		}
	}
	

	/**
	 * Fetch a music record from database
	 */
	public static Music FetchMusic(long id) {
		String hsql = "from Music where id = " + id;
		Object objRst = HibernateSession.StartSession().createQuery(hsql).uniqueResult();
		HibernateSession.FinishSession();
		if (objRst != null)
			return (Music) objRst;
		else
			return null;
	}
	
	/**
	 * Update a music record in database
	 */
	private static void UpdateMusic(Music msc) {
		HibernateSession.StartSession().update(msc);
		HibernateSession.FinishSession();
	}
}
