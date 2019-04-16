package org.rulez.demokracia.pdengine;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.rulez.demokracia.pdengine.annotations.TestedBehaviour;
import org.rulez.demokracia.pdengine.annotations.TestedFeature;
import org.rulez.demokracia.pdengine.annotations.TestedOperation;
import org.rulez.demokracia.pdengine.dataobjects.VoteAdminInfo;
import org.rulez.demokracia.pdengine.exception.ReportedException;
import org.rulez.demokracia.pdengine.testhelpers.CreatedDefaultVoteRegistry;

import com.google.gson.JsonObject;

@TestedFeature("Manage votes")
@TestedOperation("show vote")
@TestedBehaviour(
  "if adminKey is anon, the user should have any of the countedAssurances"
)
public class ForShowVoteTheUserNeedsACountedAssuranceTest
    extends CreatedDefaultVoteRegistry {

  @Test
  public void a_user_with_not_all_assourances_cannot_show_the_vote()
      throws ReportedException {

    Vote vote = getTheVote();
    vote.countedAssurances.add("german");

    assertAssurancesMissing(vote);
  }

  @Test
  public void
      a_user_with_not_all_assourances_cannot_show_the_vote_even_with_more_assurances()
          throws ReportedException {

    Vote vote = getTheVote();
    vote.countedAssurances.add("magyar");
    vote.countedAssurances.add("german");

    assertAssurancesMissing(vote);
  }

  @Test
  public void a_user_with_all_assourances_can_show_the_vote()
      throws ReportedException {

    Vote vote = getTheVote();
    vote.countedAssurances.add("magyar");
    VoteAdminInfo aminInfo = new VoteAdminInfo(vote.id, "user");
    JsonObject voteJson = voteManager.showVote(aminInfo);

    assertNotNull(voteJson);
  }

  private void assertAssurancesMissing(final Vote vote) {
    VoteAdminInfo aminInfo = new VoteAdminInfo(vote.id, "user");

    assertThrows(
        () -> voteManager.showVote(aminInfo)
    ).assertMessageIs("missing assurances");
  }
}
