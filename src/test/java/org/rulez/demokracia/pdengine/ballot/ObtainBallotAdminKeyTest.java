package org.rulez.demokracia.pdengine.ballot;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.rulez.demokracia.pdengine.annotations.TestedBehaviour;
import org.rulez.demokracia.pdengine.annotations.TestedFeature;
import org.rulez.demokracia.pdengine.annotations.TestedOperation;
import org.rulez.demokracia.pdengine.vote.Vote;

@TestedFeature("Manage votes")
@TestedOperation("Obtain ballot")
@RunWith(MockitoJUnitRunner.class)
public class ObtainBallotAdminKeyTest extends ObtainBallotTestBase {

	@Override
	@Before
	public void setUp() {
		super.setUp();
		when(authService.hasAssurance(DONT_HAVE)).thenReturn(false);
		when(authService.hasAssurance(HAVE2)).thenReturn(true);
	}

	@TestedBehaviour("if adminKey is anon, the user should have all the neededAssurances")
	@Test
	public void if_the_user_does_not_have_all_the_needed_assurances_then_she_cannot_vote() {
		assertThrows(
				() -> ballotService.obtainBallot(createVote(List.of(DONT_HAVE)), USER)
				).assertMessageIs("The user does not have all of the needed assurances.");
	}

	@TestedBehaviour("if adminKey is anon, the user should have all the neededAssurances")
	@Test
	public void
	if_the_user_does_have_all_the_assurances_then_a_ballot_is_served() {
		String ballot = ballotService.obtainBallot(createVote(List.of(HAVE, HAVE2)), USER);
		assertTrue(ballot instanceof String);
	}

	@TestedBehaviour("if adminKey is anon, the user should have all the neededAssurances")
	@Test
	public void if_neededAssurances_is_empty_then_a_ballot_is_served_to_anyone() {
		String ballot = ballotService.obtainBallot(createVote(List.of()), USER);
		assertTrue(ballot instanceof String);
	}

	@TestedBehaviour("if adminkey is anon, only one ballot can be issued")
	@Test
	public void even_if_the_user_does_have_all_the_assurances_he_cannot_issue_more_than_one_ballot() {
		Vote vote = createVote(List.of(HAVE));

		ballotService.obtainBallot(vote, USER);
		assertThrows(
				() -> ballotService.obtainBallot(vote, USER))
		.assertMessageIs("This user already have a ballot.");
	}

	@TestedBehaviour("Admin can obtain more ballots")
	@Test
	public void admin_can_obtain_more_ballots() {
		Vote vote = createVote(List.of(HAVE));

		String ballotAdmin1 = ballotService.obtainBallot(vote,
				vote.getAdminKey());
		String ballotAdmin2 = ballotService.obtainBallot(vote,
				vote.getAdminKey());
		assertNotEquals(ballotAdmin1, ballotAdmin2);
	}

	@TestedBehaviour("if the adminKey is anon and the user is not logged in then no ballots are issued")
	@Test
	public void not_logged_in_user_cannot_issue_any_ballot() {
		when(authService.getUserPrincipal()).thenReturn(null);

		assertThrows(
				() -> ballotService.obtainBallot(createVote(List.of(HAVE)), USER))
		.assertMessageIs("Simple user is not authenticated, cannot issue any ballot.");
	}
}
