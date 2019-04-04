package org.rulez.demokracia.pdengine;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;
import java.security.SignatureException;
import java.util.Base64;

import org.junit.Before;
import org.junit.Test;
import org.rulez.demokracia.pdengine.annotations.TestedBehaviour;
import org.rulez.demokracia.pdengine.annotations.TestedFeature;
import org.rulez.demokracia.pdengine.annotations.TestedOperation;
import org.rulez.demokracia.pdengine.exception.ReportedException;
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
	public void cast_vote_signed_by_the_server_and_the_signature_is_valid() {
		CastVote receipt = voteManager.castVote(adminInfo.voteId, ballot, theCastVote);

		Signature sig;
		boolean validity=false;
		try {
    	sig = Signature.getInstance("SHA256WithRSA");
		sig.initVerify(MessageSigner.getPublicKey());
        sig.update((receipt.contentToBeSigned()).getBytes());

        byte[] recepitSignatureBytes = Base64.getDecoder().decode(receipt.signature);
        validity = sig.verify(recepitSignatureBytes);
		}
		catch (InvalidKeyException | NoSuchAlgorithmException | SignatureException e) {
			throw (ReportedException)new ReportedException("Cannot verify signature").initCause(e);
		}
        assertTrue(validity);
	}
}
