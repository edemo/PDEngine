package org.rulez.demokracia.pdengine;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import javax.xml.ws.WebServiceContext;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Test;
import org.rulez.demokracia.pdengine.exception.ReportedException;
import org.rulez.demokracia.pdengine.testhelpers.CreatedDefaultVoteRegistry;

public class SessionTest extends CreatedDefaultVoteRegistry {


	@Before
	public void setUp() throws ReportedException {
		super.setUp();
	}

	@Test
	public void the_user_name_is_obtainable_from_the_voteManager() {
		assertEquals("test_user_in_ws_context", voteManager.getWsUserName());
	}

	@Test
	public void user_assurances_are_checkable_from_the_voteManager() {
		assertTrue(voteManager.hasAssurance("magyar"));
	}

	@Test
	public void you_get_the_same_hibernate_session_by_calling_getDBSession_on_DBSessionManager() {
		Session session1 = DBSessionManager.getDBSession();
		Session session2 = DBSessionManager.getDBSession();
		assertEquals(session1,session2);
	}
	
	@Test
	public void two_voteManagers_for_the_same_session_context_are_the_same() {
		WebServiceContext wsContext = mock(WebServiceContext.class);
		IVoteManager voteManager1 = IVoteManager.getVoteManager(wsContext);
		IVoteManager voteManager2 = IVoteManager.getVoteManager(wsContext);
		assertEquals(voteManager1, voteManager2);
	}
	
	@Test
	public void database_session_is_closed_when_DBSessionManager_is_closed() {
		Session session = DBSessionManager.getDBSession();
		DBSessionManager.close();
		assertThrows(() -> {
			session.beginTransaction();
		})
				.assertException(IllegalStateException.class);
	}

	@Test
	public void database_factory_is_closed_when_DBSessionManager_is_closed() {
		Session session = DBSessionManager.getDBSession();
		SessionFactory sessionFactory = session.getSessionFactory();
		DBSessionManager.close();
		assertThrows(() -> {
			sessionFactory.createEntityManager();
		})
				.assertException(IllegalStateException.class);
	}

	@Test
	public void you_get_a_new_hibernate_session_by_closing_DBSessionManager() {
		Session session1 = DBSessionManager.getDBSession();
		DBSessionManager.close();
		Session session2 = DBSessionManager.getDBSession();
		assertNotEquals(session1,session2);
	}

}
