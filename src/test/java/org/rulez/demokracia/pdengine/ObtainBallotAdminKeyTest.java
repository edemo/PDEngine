package org.rulez.demokracia.pdengine;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.rulez.demokracia.pdengine.annotations.TestedBehaviour;
import org.rulez.demokracia.pdengine.annotations.TestedFeature;
import org.rulez.demokracia.pdengine.annotations.TestedOperation;
import org.rulez.demokracia.pdengine.testhelpers.CreatedDefaultChoice;

@TestedFeature("Manage votes")
@TestedOperation("Obtain ballot")
public class ObtainBallotAdminKeyTest extends CreatedDefaultChoice {

  private static final String USER = "user";

  @Before
  public void setUp() {
    super.setUp();
  }

  @TestedBehaviour(
    "if adminKey is anon, the user should have all the neededAssurances"
  )
  @Test
  public void
      if_the_user_does_not_have_all_the_needed_assurances_then_she_cannot_vote() {
    Vote vote = voteManager.getVote(adminInfo.voteId);
    vote.neededAssurances.clear();
    vote.neededAssurances.add("dontCare");
    assertThrows(
        () -> voteManager.obtainBallot(adminInfo.voteId, USER)
    ).assertMessageIs("The user does not have all of the needed assurances.");
  }

  @TestedBehaviour(
    "if adminKey is anon, the user should have all the neededAssurances"
  )
  @Test
  public void
      if_the_user_does_have_all_the_assurances_then_a_ballot_is_served() {
    Vote vote = voteManager.getVote(adminInfo.voteId);
    vote.neededAssurances.clear();
    vote.neededAssurances.add(ASSURANCE_NAME);

    String ballot = voteManager.obtainBallot(adminInfo.voteId, USER);
    assertTrue(ballot instanceof String);
  }

  @TestedBehaviour(
    "if adminKey is anon, the user should have all the neededAssurances"
  )
  @Test
  public void if_neededAssurances_is_empty_then_a_ballot_is_served_to_anyone() {
    Vote vote = voteManager.getVote(adminInfo.voteId);
    vote.neededAssurances.clear();

    String ballot = voteManager.obtainBallot(adminInfo.voteId, USER);
    assertTrue(ballot instanceof String);
  }

  @TestedBehaviour("if adminkey is anon, only one ballot can be issued")
  @Test
  public void
      even_if_the_user_does_have_all_the_assurances_he_cannot_issue_more_than_one_ballot() {
    Vote vote = voteManager.getVote(adminInfo.voteId);
    vote.neededAssurances.clear();
    vote.neededAssurances.add(ASSURANCE_NAME);

    voteManager.obtainBallot(adminInfo.voteId, USER);
    assertThrows(
        () -> voteManager.obtainBallot(adminInfo.voteId, USER)
    ).assertMessageIs("This user already have a ballot.");
  }

  @TestedBehaviour("Admin can obtain more ballots")
  @Test
  public void admin_can_obtain_more_ballots() {
    Vote vote = voteManager.getVote(adminInfo.voteId);
    vote.neededAssurances.clear();
    vote.neededAssurances.add(ASSURANCE_NAME);

    String ballotAdmin1 =
        voteManager.obtainBallot(adminInfo.voteId, adminInfo.adminKey);
    String ballotAdmin2 =
        voteManager.obtainBallot(adminInfo.voteId, adminInfo.adminKey);
    assertNotEquals(ballotAdmin1, ballotAdmin2);
  }

  @TestedBehaviour(
    "if the adminKey is anon and the user is not logged in then no ballots are issued"
  )
  @Test
  public void not_logged_in_user_cannot_issue_any_ballot() {

    setupUnauthenticatedMockWsContext();
    Vote vote = voteManager.getVote(adminInfo.voteId);
    vote.neededAssurances.clear();
    vote.neededAssurances.add(ASSURANCE_NAME);
    assertThrows(
        () -> voteManager.obtainBallot(adminInfo.voteId, USER)
    ).assertMessageIs(
        "Simple user is not authenticated, cannot issue any ballot."
    );
  }
}
