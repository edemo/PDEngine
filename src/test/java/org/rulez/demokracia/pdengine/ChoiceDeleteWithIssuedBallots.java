package org.rulez.demokracia.pdengine;

import org.junit.Test;
import org.rulez.demokracia.pdengine.annotations.TestedBehaviour;
import org.rulez.demokracia.pdengine.annotations.TestedFeature;
import org.rulez.demokracia.pdengine.annotations.TestedOperation;
import org.rulez.demokracia.pdengine.dataobjects.VoteAdminInfo;
import org.rulez.demokracia.pdengine.testhelpers.CreatedDefaultVoteRegistry;

@TestedFeature("Manage votes")
@TestedOperation("delete choice")
@TestedBehaviour(
  "if the vote has ballots issued, the choice cannot be deleted"
)
public class ChoiceDeleteWithIssuedBallots extends CreatedDefaultVoteRegistry {

  private static final String USER = "user";
  private static final String CHOICE1 = "choice1";

  @Test
  public void
      if_the_vote_has_issued_ballots_then_the_choice_cannot_be_deleted() {
    final String voteId = adminInfo.voteId;
    final String adminKey = adminInfo.adminKey;
    final Vote vote = voteManager.getVote(voteId);
    final String choiceId = voteManager.addChoice(
        new VoteAdminInfo(adminInfo.voteId, adminKey), CHOICE1, USER
    );
    vote.ballots.add("TestBallot");

    assertThrows(
        () -> voteManager.deleteChoice(new VoteAdminInfo(voteId, adminKey), choiceId)
    ).assertMessageIs("Vote modification disallowed: ballots already issued");
  }
}
