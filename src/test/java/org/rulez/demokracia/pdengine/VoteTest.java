package org.rulez.demokracia.pdengine;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.rulez.demokracia.pdengine.annotations.TestedBehaviour;
import org.rulez.demokracia.pdengine.annotations.TestedFeature;
import org.rulez.demokracia.pdengine.annotations.TestedOperation;
import org.rulez.demokracia.pdengine.exception.ReportedException;
import org.rulez.demokracia.pdengine.testhelpers.CreatedDefaultChoice;

@TestedFeature("Vote")
@TestedOperation("Cast vote")
public class VoteTest extends CreatedDefaultChoice {

	private static final int LAST_WRONG_RANK = -1;
	private static final int FIRST_GOOD_RANK = 0;
	@Before
	public void setUp() {
		super.setUp();
		theCastVote = prepareCastVote(true);
	}

	@TestedBehaviour("deletes the ballot with ballotId, so only one vote is possible with a ballot")
	@Test
	public void cast_vote_deletes_ballot_if_canVote_is_true() {
		voteManager.castVote(adminInfo.voteId, ballot, theCastVote);
		assertFalse(vote.ballots.contains(ballot));		
	}

	@TestedBehaviour("deletes the ballot with ballotId, so only one vote is possible with a ballot")
	@Test
	public void cast_vote_deletes_ballot_if_it_gets_a_proper_not_empty_cast_vote() {
		newChoiceAndRankedList(theCastVote, FIRST_GOOD_RANK);

		voteManager.castVote(adminInfo.voteId, ballot, theCastVote);
		assertFalse(vote.ballots.contains(ballot));	
	}

	@TestedBehaviour("works only if canVote is true")
	@Test
	public void cast_vote_does_not_delete_ballot_if_canVote_is_false() {
		List<RankedChoice> theCastVote = prepareCastVote(false);

		assertThrows(() -> voteManager.castVote(adminInfo.voteId, ballot, theCastVote)
				).assertException(ReportedException.class)
				 .assertMessageIs("This issue cannot be voted on yet");
	}

	@TestedBehaviour("validates inputs")
	@Test
	public void cast_vote_checks_vote_id() {
		String wrongVoteId = "wrong_ID";

		assertThrows(() -> voteManager.castVote(wrongVoteId, ballot, theCastVote)
				).assertException(ReportedException.class)
				 .assertMessageIs("illegal voteId");
	}

	@TestedBehaviour("validates inputs")
	@Test
	public void cast_vote_checks_ballot() {
		String wrongBallot = RandomUtils.createRandomKey();

		assertThrows(() -> voteManager.castVote(adminInfo.voteId, wrongBallot, theCastVote)
				).assertException(ReportedException.class)
				 .assertMessageIs("Illegal ballot");
	}

	@TestedBehaviour("validates inputs")
	@Test
	public void cast_vote_checks_the_cast_if_choiceids_are_valid() {
		prepareRankedChoice(theCastVote, RandomUtils.createRandomKey(), 42);

		assertThrows(() -> voteManager.castVote(adminInfo.voteId, ballot, theCastVote)
				).assertException(ReportedException.class)
				 .assertMessageIs("Invalid choiceId");
	}

	@TestedBehaviour("validates inputs")
	@Test
	public void cast_vote_checks_the_cast_if_ranks_are_nonnegative() {

		newChoiceAndRankedList(theCastVote, LAST_WRONG_RANK);

		assertThrows(() -> voteManager.castVote(adminInfo.voteId, ballot, theCastVote)
				).assertException(ReportedException.class)
				 .assertMessageIs("Invalid rank");
	}
	
	@TestedBehaviour("if updatable is true, only authenticated users can vote")
	@Test
	public void cannot_cast_a_user_if_canUpdate_is_true_but_not_logged_in() {
		vote.parameters.canUpdate = true;
		setupUnauthenticatedMockWsContext();
		
		newChoiceAndRankedList(theCastVote, FIRST_GOOD_RANK);
		
		assertThrows(() ->  voteManager.castVote(adminInfo.voteId, ballot, theCastVote)
				).assertException(ReportedException.class)
		 		 .assertMessageIs("canUpdate is true but the user is not authenticated");
		
	}

	private List<RankedChoice> prepareCastVote(final boolean canVote) {
		ballot = voteManager.obtainBallot(adminInfo.voteId, adminInfo.adminKey);
		List<RankedChoice> theCastVote = new ArrayList<>();
		vote = getTheVote();
		vote.parameters.canVote = canVote;
		return theCastVote;
	}

	private void prepareRankedChoice(final List<RankedChoice> theCastVote, final String choiceId, final int rank) {
		 RankedChoice rankedChoice = new RankedChoice(choiceId, rank);
		theCastVote.add(rankedChoice);
	}

	private void newChoiceAndRankedList(final List<RankedChoice> theCastVote, final int firstGoodRank) {
		String choiceId = vote.addChoice("valid_choice","userke");
		prepareRankedChoice(theCastVote, choiceId, firstGoodRank);
	}	
}
