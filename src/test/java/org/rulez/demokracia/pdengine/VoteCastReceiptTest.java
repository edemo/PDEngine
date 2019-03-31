package org.rulez.demokracia.pdengine;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.rulez.demokracia.pdengine.annotations.TestedBehaviour;
import org.rulez.demokracia.pdengine.annotations.TestedFeature;
import org.rulez.demokracia.pdengine.annotations.TestedOperation;
import org.rulez.demokracia.pdengine.testhelpers.CreatedDefaultChoice;

@TestedFeature("Vote")
@TestedOperation("Cast vote")
public class VoteCastReceiptTest extends CreatedDefaultChoice {
	@Override
	@Before
	public void setUp() {
		super.setUp();

		ballot = voteManager.obtainBallot(adminInfo.voteId, adminInfo.adminKey);
		vote = getTheVote();
		vote.parameters.canVote = true;
		vote.parameters.canUpdate = true;
		vote.votesCast.clear();
	}

	@TestedBehaviour("The vote receipt contains the cast vote identifier")
	@Test
	public void cast_vote_returns_the_cast_vote_id() {
		CastVote receipt = voteManager.castVote(adminInfo.voteId, ballot, theCastVote);
		assertEquals(vote.votesCast.get(0).secretId, receipt.secretId);
	}

	@TestedBehaviour("The vote receipt contains the ballot cast")
	@Test
	public void cast_vote_returns_the_cast_vote_preferences() {
		CastVote receipt = voteManager.castVote(adminInfo.voteId, ballot, theCastVote);
		assertEquals(vote.votesCast.get(0).preferences.get(0).choiceId, receipt.preferences.get(0).choiceId);
	}

	@TestedBehaviour("the vote receipt is signed by the server")
	@Test
	public void cast_vote_signed_by_the_server() {
		CastVote receipt = voteManager.castVote(adminInfo.voteId, ballot, theCastVote);
		assertTrue(receipt.signature.length() > 0);
	}

	@TestedBehaviour("the vote receipt signature is valid")
	@Test
	public void cast_vote_signature_can_be_verified_by_public_key() {
		CastVote receipt = voteManager.castVote(adminInfo.voteId, ballot, theCastVote);
		assertTrue( MessageSigner.VerifyMessage(receipt.contentToBeSigned().getBytes(), receipt.signature) );
	}
}
