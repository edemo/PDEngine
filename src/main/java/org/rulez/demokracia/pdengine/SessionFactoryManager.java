package org.rulez.demokracia.pdengine;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.rulez.demokracia.pdengine.persistence.HibernateUtil;

public class SessionFactoryManager {

	protected Session session;
	protected SessionFactory sessionFactory;

	public SessionFactoryManager() {
		super();
		sessionFactory = HibernateUtil.getSessionFactory();
		session = sessionFactory.openSession();
	}

	public void close() {
		session.close();
		sessionFactory.close();
	}

}