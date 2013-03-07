package vee;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

public class HibernateSession {
	
	private static Session m_session;
	private static SessionFactory m_sessionfactory;
	private static Transaction m_transaction;
	
	public static Session StartSession() {
		Configuration cfg = new Configuration();
		cfg.configure();
		ServiceRegistry  sr = new ServiceRegistryBuilder().applySettings(cfg.getProperties()).buildServiceRegistry(); 
		m_sessionfactory = cfg.buildSessionFactory(sr);
		m_session = m_sessionfactory.openSession();
		m_transaction = m_session.beginTransaction();
		return m_session;
	}
	
	public static void FinishSession() {
		m_transaction.commit();
		m_session.close();
		m_sessionfactory.close();
	}
	
	public static Object UniqueQueryResult(String hsql) {
		Object obj = StartSession().createQuery(hsql).uniqueResult();
		FinishSession();
		return obj;
	}
	
	@SuppressWarnings("rawtypes")
	public static List ListQueryResult(String hsql) {
		List lst = StartSession().createQuery(hsql).list();
		FinishSession();
		return lst;
	}
	
}
