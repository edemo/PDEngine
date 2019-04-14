package org.rulez.demokracia.pdengine;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

import javax.xml.ws.WebServiceContext;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.rulez.demokracia.pdengine.annotations.TestedBehaviour;
import org.rulez.demokracia.pdengine.annotations.TestedFeature;
import org.rulez.demokracia.pdengine.annotations.TestedOperation;
import org.rulez.demokracia.pdengine.testhelpers.CreatedDefaultVoteRegistry;

@TestedFeature("Supporting functionality")
@TestedOperation("Session management")
public class SessionTest extends CreatedDefaultVoteRegistry {

  @TestedBehaviour(
    "username and assurances can be obtained from the VoteManager"
  )
  @Test
  public void the_user_name_is_obtainable_from_the_voteManager() {
    assertEquals(TEST_USER_NAME, voteManager.getWsUserName());
  }

  @TestedBehaviour(
    "username and assurances can be obtained from the VoteManager"
  )
  @Test
  public void user_assurances_are_checkable_from_the_voteManager() {
    assertTrue(voteManager.hasAssurance(ASSURANCE_NAME));
  }

  @TestedBehaviour("Hibernate session is obtainable from the VoteManager")
  @Test
  public void
      you_get_the_same_hibernate_session_by_calling_getDBSession_on_DBSessionManager() {
    final Session session1 = DBSessionManagerUtils.getDBSession();
    final Session session2 = DBSessionManagerUtils.getDBSession();
    assertEquals(session1, session2);
  }

  @TestedBehaviour(
    "VoteManager can be obtained through the IVoteManager interface with a WebServiceContext"
  )
  @Test
  public void two_voteManagers_for_the_same_session_context_are_the_same() {
    final WebServiceContext wsContext = mock(WebServiceContext.class);
    final IVoteManager voteManager1 = IVoteManager.getVoteManager(wsContext);
    final IVoteManager voteManager2 = IVoteManager.getVoteManager(wsContext);
    assertEquals(voteManager1, voteManager2);
  }

  @TestedBehaviour("Hibernate session is obtainable from the VoteManager")
  @Test
  public void database_session_is_closed_when_DBSessionManager_is_closed() {
    final Session session = DBSessionManagerUtils.getDBSession();
    DBSessionManagerUtils.close();
    assertThrows(() -> {
      session.beginTransaction();
    })
        .assertException(IllegalStateException.class);
  }

  @TestedBehaviour("Hibernate session is obtainable from the VoteManager")
  @Test
  public void database_factory_is_closed_when_DBSessionManager_is_closed() {
    final Session session = DBSessionManagerUtils.getDBSession();
    final SessionFactory sessionFactory = session.getSessionFactory();
    DBSessionManagerUtils.close();
    assertThrows(() -> {
      sessionFactory.createEntityManager();
    })
        .assertException(IllegalStateException.class);
  }

  @TestedBehaviour("Hibernate session is obtainable from the VoteManager")
  @Test
  public void you_get_a_new_hibernate_session_by_closing_DBSessionManager() {
    final Session session1 = DBSessionManagerUtils.getDBSession();
    DBSessionManagerUtils.close();
    final Session session2 = DBSessionManagerUtils.getDBSession();
    assertNotEquals(session1, session2);
  }

}
