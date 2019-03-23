package org.rulez.demokracia.pdengine;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.rulez.demokracia.pdengine.persistence.HibernateUtil;

public final class DBSessionManagerUtils {
	private static Session session;
	private static SessionFactory sessionFactory;

	private DBSessionManagerUtils() {	
	}

	public static void close() {
		sessionFactory.close();
		session = null;
	}

	public static Session getDBSession() {
		if( session == null ) {
			sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();			
		}
		return session;
	}

}
