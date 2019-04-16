package org.rulez.demokracia.pdengine;

import org.junit.Test;
import org.rulez.demokracia.pdengine.annotations.TestedBehaviour;
import org.rulez.demokracia.pdengine.annotations.TestedFeature;
import org.rulez.demokracia.pdengine.annotations.TestedOperation;
import org.rulez.demokracia.pdengine.dataobjects.VoteAdminInfo;
import org.rulez.demokracia.pdengine.testhelpers.CreatedDefaultVoteRegistry;

@TestedFeature("Manage votes")
@TestedOperation("modify vote")
public class VoteModificationValidationTest extends CreatedDefaultVoteRegistry {

  @TestedBehaviour("validates inputs")
  @Test
  public void
      the_name_is_verified_against_the_same_rules_as_in_vote_creation() {
    String modifiedVoteName = null;
    assertThrows(
        () -> voteManager.modifyVote(
            new VoteAdminInfo(adminInfo.voteId, adminInfo.adminKey), modifiedVoteName
        )
    ).assertMessageIs("vote name is null");
  }

  @TestedBehaviour("validates inputs")
  @Test
  public void invalid_adminKey_is_rejected() {
    String invalidAdminKey = RandomUtils.createRandomKey();
    assertThrows(
        () -> voteManager.modifyVote(
            new VoteAdminInfo(adminInfo.voteId, invalidAdminKey), voteName
        )
    ).assertMessageIs("Illegal adminKey");
  }

  @TestedBehaviour("validates inputs")
  @Test
  public void invalid_voteId_is_rejected() {
    String invalidvoteId = RandomUtils.createRandomKey();
    assertThrows(
        () -> voteManager.modifyVote(
            new VoteAdminInfo(invalidvoteId, adminInfo.adminKey), voteName
        )
    ).assertMessageIs("illegal voteId");
  }

  @TestedBehaviour("The vote cannot be modified if there are ballots issued.")
  @Test
  public void modifyVote_with_ballot_get_an_exception() {
    String voteId = adminInfo.voteId;
    Vote vote = voteManager.getVote(voteId);
    vote.ballots.add("TestBallots");

    assertThrows(
        () -> voteManager.modifyVote(
            new VoteAdminInfo(voteId, adminInfo.adminKey), voteName
        )
    ).assertMessageIs("This vote cannot be modified it has issued ballots.");
  }
}
