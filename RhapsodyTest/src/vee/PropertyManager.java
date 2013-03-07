package vee;

import java.util.LinkedList;
import java.util.List;

public class PropertyManager {
	
	/**
	 * Get a Currently Available New ID
	 */
	private static long GetAvailableId()
	{
		String hsql = "select max(t.id) from Property as t";
		Object obj = HibernateSession.UniqueQueryResult(hsql);
		long maxid = (obj == null? 0L: (long) obj);
		return maxid + 1;
	}
	

	/**
	 * Record an event that a user gains a music at specified cost
	 */
	public static int UserGainMusic(long uid, long mid, long cost)
	{
		String hsql = "from Property where uid = " + uid + " and mid = " + mid;
		Object obj = HibernateSession.UniqueQueryResult(hsql);
		if (obj != null) {
			return 1;
		}
		long id = GetAvailableId();
		if (id == 0L) {
			return 1;
		}
		Property pty = new Property(id, uid, mid, cost, cost, Property.STATE_POSSESS);
		HibernateSession.StartSession().save(pty);
		HibernateSession.FinishSession();
		return 0;
	}
	


	/**
	 * Fetch a property record specified by UserID and MusicID from database
	 */
	public static Property FetchProperty(long uid, long mid) {
		String hsql = "from Property where uid = " + uid + " and mid = " + mid;
		Object obj = HibernateSession.UniqueQueryResult(hsql);
		if (obj != null)
			return (Property) obj;
		else
			return null;
	}	

	@SuppressWarnings("unchecked")
	public static List<UserMusicListItem> FetchUserPropertyList(long uid) {
		String hsql =
				"from Property " +
				"where uid = " + uid;
		List<Property> lst = HibernateSession.ListQueryResult(hsql);
		if (lst == null)
			return null;
		List<UserMusicListItem> umlst = new LinkedList<UserMusicListItem>();
		UserMusicListItem itm;
		int sz = lst.size();
		for (int i = 0; i < sz; ++i) {
			long id;
			id = lst.get(i).getMid();
			Music msc = MusicManager.FetchMusic(id);
			itm = new UserMusicListItem();
			itm.MusicID = msc.getId();
			itm.MusicName = msc.getName();
			itm.MusicSourceID = msc.getSrcid();
			itm.Cost = lst.get(i).getCost();
			itm.Price = lst.get(i).getPrice();
			itm.SellState = lst.get(i).getState();
			umlst.add(itm);
		}
		return umlst;
	}
	
	@SuppressWarnings("unchecked")
	public static List<UserMusicListItem> FetchUserSellingPropertyList(long uid) {
		String hsql =
				"from Property " +
				"where uid = " + uid + " and " +
						"state = 1";
		List<Property> lst = HibernateSession.ListQueryResult(hsql);
		if (lst == null)
			return null;
		List<UserMusicListItem> umlst = new LinkedList<UserMusicListItem>();
		UserMusicListItem itm;
		int sz = lst.size();
		for (int i = 0; i < sz; ++i) {
			long id;
			id = lst.get(i).getMid();
			Music msc = MusicManager.FetchMusic(id);
			itm = new UserMusicListItem();
			itm.MusicID = msc.getId();
			itm.MusicName = msc.getName();
			itm.MusicSourceID = msc.getSrcid();
			itm.Cost = lst.get(i).getCost();
			itm.Price = lst.get(i).getPrice();
			itm.SellState = lst.get(i).getState();
			umlst.add(itm);
		}
		return umlst;
	}
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List<CommodityItem> RequireCommodityList(long uid, long prcl, long prch) {
		String hsql =
				"select mu.id, mu.name, pr.uid, pr.price " +
				"from Property as pr, Music as mu " +
				"where pr.uid != " + uid + " and " +
						"pr.price >= " + prcl + " and " +
						"pr.price <= " + prch + " and " +
						"pr.state = " + Property.STATE_SELLING; 
						
		List lst = HibernateSession.ListQueryResult(hsql);
		
		if (lst == null)
			return null;
		else
			return (List<CommodityItem>) lst;
	}
	
	
	@SuppressWarnings("unchecked")
	public static List<CommodityItem> RequireCommodityList(long uid, long srcid, long prcl, long prch)
	{
		String hsql =
				"from Property " +
				"where price >= " + prcl + " and " +
						"price <= " + prch + " and " +
						"uid != " + uid + " and " + 
						"state = 1";
		List<Property> lstpro = HibernateSession.ListQueryResult(hsql);
		List<CommodityItem> lstcom = new LinkedList<CommodityItem>();
		CommodityItem itm;
		int sz = lstpro.size();
		System.out.println("[GetCommodityList] Original List Size = " + sz);
		for (int i = 0; i < sz; ++i) {
			Property pty = lstpro.get(i);
			Music msc = MusicManager.FetchMusic(pty.getMid());
			if (msc.getSrcid() != srcid) {
				continue;
			}
			itm = new CommodityItem();
			itm.PropertyID = pty.getId();
			itm.MusicID = msc.getId();
			itm.MusicName = msc.getName();
			itm.MusicSourceID = msc.getSrcid();
			itm.Price = pty.getPrice();
			itm.UserID = pty.getUid();
			lstcom.add(itm);
		}
		return lstcom;
	}
	
	/**
	 * Update a property record in database
	 */
	public static void UpdateProperty(Property pty) {
		HibernateSession.StartSession().update(pty);
		HibernateSession.FinishSession();
	}
}
