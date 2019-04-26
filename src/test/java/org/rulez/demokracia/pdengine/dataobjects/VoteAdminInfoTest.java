package org.rulez.demokracia.pdengine.dataobjects;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class VoteAdminInfoTest {

  private static final String ADMIN_KEY = "adminKey";
  private static final String USER = "user";
  private static final String VOTE_ID = "voteId";

  @Test
  public void is_user_returns_false_when_admin_key_is_not_user() {
    final VoteAdminInfo adminInfo = new VoteAdminInfo(VOTE_ID, ADMIN_KEY);
    assertFalse(adminInfo.isUserAdminKey());
  }

  @Test
  public void is_user_returns_true_when_admin_key_is_user() {
    final VoteAdminInfo adminInfo = new VoteAdminInfo(VOTE_ID, USER);
    assertTrue(adminInfo.isUserAdminKey());
  }

}
