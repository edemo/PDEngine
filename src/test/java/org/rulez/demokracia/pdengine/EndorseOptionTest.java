package org.rulez.demokracia.pdengine;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.rulez.demokracia.pdengine.annotations.tested_behaviour;
import org.rulez.demokracia.pdengine.annotations.tested_feature;
import org.rulez.demokracia.pdengine.annotations.tested_operation;
import org.rulez.demokracia.pdengine.exception.ReportedException;
import org.rulez.demokracia.pdengine.testhelpers.CreatedDefaultChoice;

public class EndorseOptionTest extends CreatedDefaultChoice {

	@Before
	public void setUp() throws ReportedException {
		super.setUp();
	}

	@tested_feature("Vote")
	@tested_operation("Endorse option")
	@tested_behaviour("if adminKey is not user, the userName is registered " + "as endorserName for the choice")
	@Test
	public void endorsement_is_registered() {
		voteManager.endorseChoice("proxyuser", adminInfo.adminKey, adminInfo.voteId, choiceId, "testuser");
		assertTrue(getChoice(choiceId).endorsers.contains("testuser"));
	}

	@tested_feature("Vote")
	@tested_operation("Endorse option")
	@tested_behaviour("if adminKey is 'user', then canEndorse must be true,"
			+ " and the proxy id of the user will be registered as endorserName for the choice")
	@Test
	public void if_adminKey_is_user_and_canEndorse_is_false_then_an_exception_is_thrown() {
		assertThrows(() -> voteManager.endorseChoice("proxyuser", "user", adminInfo.voteId, choiceId, "testuserke"));
		assertException(IllegalArgumentException.class);
	}

	@tested_feature("Vote")
	@tested_operation("Endorse option")
	@tested_behaviour("if adminKey is 'user', then canEndorse must be true,"
			+ " and the proxy id of the user will be registered as endorserName for the choice")
	@Test
	public void if_adminkey_is_user_then_the_proxy_id_is_registered_for_the_vote() {
		setVoteEndorseable();
		voteManager.endorseChoice("proxyuser", "user", adminInfo.voteId, choiceId, "testuserke");
		assertTrue(getChoice(choiceId).endorsers.contains("proxyuser"));
	}

	@tested_feature("Vote")
	@tested_operation("Endorse option")
	@tested_behaviour("validates inputs")
	@Test
	public void vote_id_is_the_id_of_an_existing_vote() {
		String theVoteId = RandomUtils.createRandomKey();
		String adminKey = "user";
		String message = String.format("illegal voteId: %s", theVoteId);
		assertValidationFailsWithMessage(theVoteId, adminKey, message);
	}

	private void assertValidationFailsWithMessage(String theVoteId, String adminKey, String message) {
		assertThrows(() -> {
			voteManager.endorseChoice("proxyuser", adminKey, theVoteId, choiceId, "testuserke");
		})
				.assertException(IllegalArgumentException.class).assertMessageIs(message);
	}

	@tested_feature("Vote")
	@tested_operation("Endorse option")
	@tested_behaviour("validates inputs")
	@Test
	public void invalid_adminkey_is_rejected() {
		String theVoteId = adminInfo.voteId;
		String adminKey = RandomUtils.createRandomKey();
		String message = String.format("Illegal adminKey: %s", adminKey);
		assertValidationFailsWithMessage(theVoteId, adminKey, message);
	}

}
