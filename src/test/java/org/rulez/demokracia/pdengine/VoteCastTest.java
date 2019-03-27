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
public class VoteCastTest extends CreatedDefaultChoice {

	@Before
	public void setUp() {
		super.setUp();
		initializeVoteCastTest();
	}

	@TestedBehaviour("records cast vote with the vote and user's proxy id")
	@Test
	public void cast_vote_records_the_proxy_id() {
		vote.canUpdate = true;
		voteManager.castVote(adminInfo.voteId, ballot, theCastVote);
		CastVote voteCast = new CastVote(TEST_USER_NAME, theCastVote);
		assertEquals(voteCast.proxyId, vote.votesCast.get(0).proxyId);
	}
	
	@TestedBehaviour("records cast vote with the vote and user's proxy id")
	@Test
	public void voteCast_records_the_preferences() {
		voteManager.castVote(adminInfo.voteId, ballot, theCastVote);
		CastVote voteCast = new CastVote(TEST_USER_NAME, theCastVote);
		assertTrue(voteCast.preferences.containsAll(vote.votesCast.get(0).preferences));
	}
	
	@TestedBehaviour("Cast vote have a secret random identifier")
	@Test
	public void voteCast_records_a_secret_id_with_the_vote_cast() {
		voteManager.castVote(adminInfo.voteId, ballot, theCastVote);
		assertTrue(vote.votesCast.get(0).secretId instanceof String);
	}
	
	@TestedBehaviour("cast vote identifier is different from the ballot identifier")
	@Test
	public void voteCast_identifier_is_different_from_the_ballot_identifier() {
		String ballotId = voteManager.obtainBallot(adminInfo.voteId, adminInfo.adminKey);
		CastVote castVote = voteManager.castVote(adminInfo.voteId, ballot, theCastVote);
		
		assertNotEquals(ballotId, castVote.secretId);
	}
}
