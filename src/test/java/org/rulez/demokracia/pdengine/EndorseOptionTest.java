package org.rulez.demokracia.pdengine;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.rulez.demokracia.pdengine.annotations.TestedBehaviour;
import org.rulez.demokracia.pdengine.annotations.TestedFeature;
import org.rulez.demokracia.pdengine.annotations.TestedOperation;
import org.rulez.demokracia.pdengine.dataobjects.VoteAdminInfo;
import org.rulez.demokracia.pdengine.exception.ReportedException;
import org.rulez.demokracia.pdengine.testhelpers.CreatedDefaultChoice;

@TestedFeature("Vote")
@TestedOperation("Endorse option")
public class EndorseOptionTest extends CreatedDefaultChoice {

  @Before
  public void setUp() {
    super.setUp();
  }

  @TestedBehaviour(
    "if adminKey is not user, the userName is registered " +
        "as endorserName for the choice"
  )
  @Test
  public void endorsement_is_registered() {
    voteManager.endorseChoice(
        new VoteAdminInfo(adminInfo.voteId, adminInfo.adminKey), choiceId, "testuser"
    );
    assertTrue(getChoice(choiceId).endorsers.contains("testuser"));
  }

  @TestedBehaviour(
    "if adminKey is 'user', then canEndorse must be true," +
        " and the proxy id of the user will be registered as endorserName for the choice"
  )
  @Test
  public void
      if_adminKey_is_user_and_canEndorse_is_false_then_an_exception_is_thrown() {
    assertThrows(
        () -> voteManager.endorseChoice(
            new VoteAdminInfo(adminInfo.voteId, "user"), choiceId, "testuserke"
        )
    )
        .assertException(ReportedException.class);
  }

  @TestedBehaviour(
    "if adminKey is 'user', then canEndorse must be true," +
        " and the proxy id of the user will be registered as endorserName for the choice"
  )
  @Test
  public void
      if_adminKey_is_user_then_the_proxy_id_is_registered_for_the_vote() {
    setVoteEndorseable();
    voteManager.endorseChoice(
        new VoteAdminInfo(adminInfo.voteId, "user"), choiceId, "testuserke"
    );
    assertTrue(getChoice(choiceId).endorsers.contains(TEST_USER_NAME));
  }

  @TestedBehaviour("validates inputs")
  @Test
  public void voteId_is_the_id_of_an_existing_vote() {
    adminInfo.voteId = RandomUtils.createRandomKey();
    assertValidationFailsWithMessage("illegal voteId");
  }

  @TestedBehaviour("validates inputs")
  @Test
  public void invalid_adminKey_is_rejected() {
    adminInfo.adminKey = RandomUtils.createRandomKey();
    assertValidationFailsWithMessage("Illegal adminKey");
  }

  @TestedBehaviour("validates inputs")
  @Test
  public void invalid_choiceId_is_rejected() {
    choiceId = RandomUtils.createRandomKey();
    assertValidationFailsWithMessage("Illegal choiceId");
  }
}
