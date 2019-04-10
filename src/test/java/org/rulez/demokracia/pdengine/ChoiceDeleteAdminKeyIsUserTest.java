package org.rulez.demokracia.pdengine;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.rulez.demokracia.pdengine.annotations.TestedBehaviour;
import org.rulez.demokracia.pdengine.annotations.TestedFeature;
import org.rulez.demokracia.pdengine.annotations.TestedOperation;
import org.rulez.demokracia.pdengine.dataobjects.VoteAdminInfo;
import org.rulez.demokracia.pdengine.testhelpers.CreatedDefaultVoteRegistry;

@TestedFeature("Manage votes")
@TestedOperation("delete choice")
@TestedBehaviour(
  "if \"user\" is used as adminKey, then the user must be the one who added the choice and canAddIn be true"
)
public class ChoiceDeleteAdminKeyIsUserTest extends CreatedDefaultVoteRegistry {

  private static final String USER = "user";
  private static final String CHOICE1 = "choice1";

  @Test
  public void if_canAddin_is_false_then_other_users_cannot_add_choices() {
    final Vote vote = voteManager.getVote(adminInfo.voteId);
    vote.parameters.canAddin = false;
    final String choiceId = voteManager
        .addChoice(new VoteAdminInfo(adminInfo.voteId, adminInfo.adminKey), CHOICE1, TEST_USER_NAME);
    assertThrows(
        () -> voteManager.deleteChoice(new VoteAdminInfo(adminInfo.voteId, USER), choiceId)
    ).assertMessageIs(
        "Choice modification disallowed: adminKey is user, but canAddin is false"
    );
  }

  @Test
  public void
      if_adminKey_is_user_and_the_user_is_not_the_one_who_added_the_choice_then_the_choice_cannot_be_deleted() {
    final Vote vote = voteManager.getVote(adminInfo.voteId);
    vote.parameters.canAddin = true;
    final String choiceId = voteManager.addChoice(
        new VoteAdminInfo(adminInfo.voteId, adminInfo.adminKey), CHOICE1, USER
    );
    assertThrows(
        () -> voteManager.deleteChoice(new VoteAdminInfo(adminInfo.voteId, USER), choiceId)
    ).assertMessageIs(
        "The adminKey is \"user\" but the user is not same with that user who added the choice."
    );
  }

  @Test
  public void
      if_adminKey_is_user_and_canAddin_is_true_then_the_user_who_added_the_choice_is_able_to_delete_it() {
    final String voteId = adminInfo.voteId;
    final Vote vote = voteManager.getVote(voteId);
    vote.parameters.canAddin = true;
    final String choiceId = voteManager.addChoice(
        new VoteAdminInfo(adminInfo.voteId, USER), CHOICE1, TEST_USER_NAME
    );
    voteManager.deleteChoice(new VoteAdminInfo(voteId, USER), choiceId);

    assertThrows(
        () -> voteManager.getChoice(voteId, choiceId)
    ).assertMessageIs("Illegal choiceId");
  }

  @Test
  public void deleteChoice_return_an_OK_if_the_choice_is_deleted() {
    final String voteId = adminInfo.voteId;
    final Vote vote = voteManager.getVote(voteId);
    vote.parameters.canAddin = true;
    final String choiceId = voteManager.addChoice(
        new VoteAdminInfo(adminInfo.voteId, USER), CHOICE1, TEST_USER_NAME
    );
    final String returnValue =
        voteManager.deleteChoice(new VoteAdminInfo(voteId, USER), choiceId);
    assertEquals("OK", returnValue);
  }

}
