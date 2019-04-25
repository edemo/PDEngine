package org.rulez.demokracia.pdengine.ballot;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.rulez.demokracia.pdengine.annotations.TestedBehaviour;
import org.rulez.demokracia.pdengine.annotations.TestedFeature;
import org.rulez.demokracia.pdengine.annotations.TestedOperation;
import org.rulez.demokracia.pdengine.exception.ReportedException;
import org.rulez.demokracia.pdengine.vote.Vote;

@TestedFeature("Manage votes")
@TestedOperation("Obtain ballot")
@RunWith(MockitoJUnitRunner.class)
@SuppressWarnings({
    "PMD.UnusedImports", "PMD.UnusedImports"
})
public class ObtainBallotTest extends ObtainBallotTestBase {

  private static final String ANON_USER = "user";
  private Vote vote;

  @Override
  @Before
  public void setUp() {
    super.setUp();
    vote = createVote(List.of(HAVE));
  }

  @TestedBehaviour("creates a new ballot with an id for the vote")
  @Test
  public void obtain_ballot_returns_the_ballot_string() {
    String ballot = ballotService.obtainBallot(vote, vote.getAdminKey());
    assertTrue(ballot instanceof String);
  }

  @TestedBehaviour("creates a new ballot with an id for the vote")
  @Test
  public void two_ballots_are_different() {
    String ballot1 = ballotService.obtainBallot(vote, vote.getAdminKey());
    String ballot2 = ballotService.obtainBallot(vote, vote.getAdminKey());
    assertNotEquals(ballot1, ballot2);
  }

  @TestedBehaviour("validates inputs")
  @Test
  public void adminKey_is_checked() {
    assertThrows(() -> {
      ballotService.obtainBallot(vote, "illegalAdminKey");
    })
        .assertException(ReportedException.class)
        .assertMessageIs("Illegal adminKey");

  }

  @TestedBehaviour("creates a new ballot with an id for the vote")
  @Test
  public void obtainBallot_stores_the_ballot() {
    String ballot = ballotService.obtainBallot(vote, vote.getAdminKey());
    assertTrue(vote.getBallots().contains(ballot));
  }

  @TestedBehaviour(
    "the number of ballots obtained with adminKey are recorded for \"admin\""
  )
  @Test
  public void obtainBallot_increases_recordedBallots_when_adminKey_is_admin() {
    String adminKey = vote.getAdminKey();
    int originalObtainedBallots = vote.getRecordedBallotsCount(adminKey);
    ballotService.obtainBallot(vote, adminKey);
    assertEquals(
        originalObtainedBallots + 1, vote.getRecordedBallotsCount("admin").intValue()
    );
  }

  @TestedBehaviour(
    "the number of ballots obtained with anon adminkey are recorded with the proxy id of the user"
  )
  @Test
  public void obtainBallot_increases_recordedBallots_when_adminKey_is_anon() {
    int originalObtainedBallots = vote.getRecordedBallotsCount(ANON_USER);
    ballotService.obtainBallot(vote, ANON_USER);
    assertEquals(
        originalObtainedBallots + 1, vote.getRecordedBallotsCount(ANON_USER).intValue()
    );
  }
}
