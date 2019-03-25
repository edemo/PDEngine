package org.rulez.demokracia.pdengine;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.rulez.demokracia.pdengine.annotations.TestedBehaviour;
import org.rulez.demokracia.pdengine.annotations.TestedFeature;
import org.rulez.demokracia.pdengine.annotations.TestedOperation;
import org.rulez.demokracia.pdengine.testhelpers.CreatedDefaultChoice;

@TestedFeature("Vote")
@TestedOperation("Cast vote")
@TestedBehaviour("The vote receipt contains the ballot cast and the cast vote identifier")
public class VoteCastReceiptTest extends CreatedDefaultChoice {
	@Before
	public void setUp() {
		super.setUp();
		
		ballot = voteManager.obtainBallot(adminInfo.voteId, adminInfo.adminKey);
		vote = getTheVote();
		vote.canVote = true;
		vote.canUpdate = true;
		vote.votesCast.clear();
	}

	@Test
	public void cast_vote_returns_the_cast_vote_id() {
		CastVote receipt = voteManager.castVote(adminInfo.voteId, ballot, theCastVote);
		assertEquals(vote.votesCast.get(0).secretId, receipt.secretId);
	}

	@Test
	public void cast_vote_returns_the_cast_vote_preferences() {
		CastVote receipt = voteManager.castVote(adminInfo.voteId, ballot, theCastVote);
		assertEquals(vote.votesCast.get(0).preferences.get(0).choiceId, receipt.preferences.get(0).choiceId);
	}

}
