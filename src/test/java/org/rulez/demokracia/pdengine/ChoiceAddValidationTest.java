package org.rulez.demokracia.pdengine;

import org.junit.Test;
import org.rulez.demokracia.pdengine.annotations.TestedBehaviour;
import org.rulez.demokracia.pdengine.annotations.TestedFeature;
import org.rulez.demokracia.pdengine.annotations.TestedOperation;
import org.rulez.demokracia.pdengine.dataobjects.VoteAdminInfo;
import org.rulez.demokracia.pdengine.testhelpers.CreatedDefaultVoteRegistry;
import org.rulez.demokracia.testhelpers.ThrowableTester;

@TestedFeature("Manage votes")
@TestedOperation("Add choice")
public class ChoiceAddValidationTest extends CreatedDefaultVoteRegistry {

  @TestedBehaviour("validates inputs")
  @Test
  public void invalid_voteId_is_rejected() {
    String invalidvoteId = RandomUtils.createRandomKey();
    assertaddChoiceThrowsUp(invalidvoteId, adminInfo.adminKey)
        .assertMessageIs("illegal voteId");
  }

  @TestedBehaviour("validates inputs")
  @Test
  public void invalid_adminKey_is_rejected() {
    assertaddChoiceThrowsUp(adminInfo.voteId, "invalidAdminKey")
        .assertMessageIs("Illegal adminKey");
  }

  @TestedBehaviour(
    "No choice can be added if there are ballots issued for the vote."
  )
  @Test
  public void no_choice_can_be_added_there_are_issued_ballots() {
    Vote vote = voteManager.getVote(adminInfo.voteId);
    vote.ballots.add("TestBallots");
    assertaddChoiceThrowsUp(adminInfo.voteId, adminInfo.adminKey)
        .assertMessageIs("Vote modification disallowed: ballots already issued");
  }

  private ThrowableTester
      assertaddChoiceThrowsUp(final String voteId, final String adminKey) {
    return assertThrows(
        () -> {
          voteManager.addChoice(
              new VoteAdminInfo(voteId, adminKey), "choice1", "user"
          );
        }
    );
  }
}
