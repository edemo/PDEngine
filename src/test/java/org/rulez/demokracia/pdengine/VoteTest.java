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

	@Before
	public void setUp() {
		super.setUp();
	}

	@TestedBehaviour("deletes the ballot with ballotId, so only one vote is possible with a ballot")
	@Test
	public void cast_vote_deletes_ballot_if_canVote_is_true() {
		String ballot = voteManager.obtainBallot(adminInfo.voteId, adminInfo.adminKey);
		List<RankedChoice> theCastVote = new ArrayList<>();
		Vote vote = getTheVote();
		vote.canVote = true;
		voteManager.castVote(adminInfo.voteId, ballot, theCastVote);
		assertFalse(vote.ballots.contains(ballot));		
	}

	@TestedBehaviour("works only if canVote is true")
	@Test
	public void cast_vote_does_not_delete_ballot_if_canVote_is_false() {
		String ballot = voteManager.obtainBallot(adminInfo.voteId, adminInfo.adminKey);
		List<RankedChoice> theCastVote = new ArrayList<>();
		Vote vote = getTheVote();
		vote.canVote = false;

		assertThrows(() -> voteManager.castVote(adminInfo.voteId, ballot, theCastVote)
				).assertException(ReportedException.class)
				 .assertMessageIs("This issue cannot be voted on yet");
	}

	@TestedBehaviour("validates inputs")
	@Test
	public void cast_vote_checks_vote_id() {
		String ballot = voteManager.obtainBallot(adminInfo.voteId, adminInfo.adminKey);
		List<RankedChoice> theCastVote = new ArrayList<>();
		Vote vote = getTheVote();
		vote.canVote = true;
		String wrongVoteId = "wrong_ID";

		assertThrows(() -> voteManager.castVote(wrongVoteId, ballot, theCastVote)
				).assertException(ReportedException.class)
				 .assertMessageIs("illegal voteId");
	}

	@TestedBehaviour("validates inputs")
	@Test
	public void cast_vote_checks_ballot() {
		String wrongBallot = RandomUtils.createRandomKey();
		List<RankedChoice> theCastVote = new ArrayList<>();
		Vote vote = getTheVote();
		vote.canVote = true;

		assertThrows(() -> voteManager.castVote(adminInfo.voteId, wrongBallot, theCastVote)
				).assertException(ReportedException.class)
				 .assertMessageIs("Illegal ballot");
	}

	@TestedBehaviour("validates inputs")
	@Test
	public void cast_vote_checks_the_cast_if_choiceids_are_valid() {
		String ballot = voteManager.obtainBallot(adminInfo.voteId, adminInfo.adminKey);
		List<RankedChoice> theCastVote = new ArrayList<>();
		Vote vote = getTheVote();
		vote.canVote = true;	

		String wrongChoiceId = RandomUtils.createRandomKey();
		int goodRank = 42;
		RankedChoice rankedChoice = new RankedChoice(wrongChoiceId, goodRank);
		theCastVote.add(rankedChoice);

		assertThrows(() -> voteManager.castVote(adminInfo.voteId, ballot, theCastVote)
				).assertException(ReportedException.class)
				 .assertMessageIs("Invalid choiceId");
	}

	@TestedBehaviour("validates inputs")
	@Test
	public void cast_vote_checks_the_cast_if_ranks_are_nonnegative() {
		String ballot = voteManager.obtainBallot(adminInfo.voteId, adminInfo.adminKey);
		List<RankedChoice> theCastVote = new ArrayList<>();
		Vote vote = getTheVote();
		vote.canVote = true;

		String choiceId = vote.addChoice("valid_choice","userke");
		int wrongRank = -1;
		RankedChoice rankedChoice = new RankedChoice(choiceId, wrongRank);
		theCastVote.add(rankedChoice);

		assertThrows(() -> voteManager.castVote(adminInfo.voteId, ballot, theCastVote)
				).assertException(ReportedException.class)
				 .assertMessageIs("Invalid rank");
	}
	
	@TestedBehaviour("deletes the ballot with ballotId, so only one vote is possible with a ballot")
	@Test
	public void cast_vote_deletes_ballot_if_it_gets_a_proper_not_empty_cast() {
		String ballot = voteManager.obtainBallot(adminInfo.voteId, adminInfo.adminKey);
		List<RankedChoice> theCastVote = new ArrayList<>();
		Vote vote = getTheVote();
		vote.canVote = true;

		String choiceId = vote.addChoice("valid_choice","userke");
		int firstgoodrank = 0;
		RankedChoice rankedChoice = new RankedChoice(choiceId, firstgoodrank);
		theCastVote.add(rankedChoice);
		voteManager.castVote(adminInfo.voteId, ballot, theCastVote);
		assertFalse(vote.ballots.contains(ballot));	
	}
	
	
	@TestedBehaviour("if updatable is true, only authenticated users can vote")
	@Test
	public void cannot_cast_a_user_if_canUpdate_is_true_but_not_logged_in() {
		String ballot = voteManager.obtainBallot(adminInfo.voteId, adminInfo.adminKey);
		List<RankedChoice> theCastVote = new ArrayList<>();
		Vote vote = getTheVote();
		vote.canVote = true;
		vote.canUpdate = true;
		setupUnauthenticatedMockWsContext();
		
		String choiceId = vote.addChoice("valid_choice","userke");
		int firstGoodRank = 0;
		RankedChoice rankedChoice = new RankedChoice(choiceId, firstGoodRank);
		theCastVote.add(rankedChoice);
		
		assertThrows(() ->  voteManager.castVote(adminInfo.voteId, ballot, theCastVote)
				).assertException(ReportedException.class)
		 		 .assertMessageIs("canUpdate is true but the user is not authenticated");
		
	}
	
}
