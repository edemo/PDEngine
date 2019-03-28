package org.rulez.demokracia.pdengine;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.rulez.demokracia.pdengine.annotations.TestedBehaviour;
import org.rulez.demokracia.pdengine.annotations.TestedFeature;
import org.rulez.demokracia.pdengine.annotations.TestedOperation;
import org.rulez.demokracia.pdengine.testhelpers.CreatedDefaultChoice;

public class VoteCastUpdatableTest extends CreatedDefaultChoice {

	@Override
	@Before
	public void setUp() {
		super.setUp();

		ballot = voteManager.obtainBallot(adminInfo.voteId, adminInfo.adminKey);
		theCastVote = new ArrayList<>();

		vote = getTheVote();
		vote.parameters.canVote = true;
		vote.votesCast.clear();
	}

	@TestedFeature("Vote")
	@TestedOperation("Cast vote")
	@TestedBehaviour("if updatable is true then the cast vote records the proxy id of the user")
	@Test
	public void castVote_records_the_proxy_id_when_updatable_is_true() {
		vote.parameters.canUpdate = true;
		voteManager.castVote(adminInfo.voteId, ballot, theCastVote);

		assertTrue(vote.votesCast.get(0).proxyId.equals(TEST_USER_NAME));
	}

	@TestedFeature("Vote")
	@TestedOperation("Cast vote")
	@TestedBehaviour("If updatable is false then the cast vote is not associated with the voter")
	@Test
	public void castVote_does_not_record_the_proxy_id_when_updatable_is_false() {
		vote.parameters.canUpdate = false;
		
		voteManager.castVote(adminInfo.voteId, ballot, theCastVote);
		assertTrue(vote.votesCast.get(0).proxyId == null);
	}

	@TestedFeature("Vote")
	@TestedOperation("Cast vote")
	@TestedBehaviour("If updatable is false then the cast vote is not associated with the voter")
	@Test
	public void castVote_does_not_record_the_proxy_id_when_updatable_is_false_and_it_does_not_delete_the_other_not_recorded_proxyIds_votescast() {
		vote.parameters.canUpdate = false;
		addCastVoteWithDefaultPreferencesForUser(TEST_USER_NAME);
		for(Integer i=0;i<7;i++) {
			addCastVoteWithDefaultPreferencesForUser("dummy"+ i);
		}

		voteManager.castVote(adminInfo.voteId, ballot, theCastVote);

		assertEquals(null,vote.votesCast.get(8).proxyId
				);
	}

	private void addCastVoteWithDefaultPreferencesForUser(final String proxyId) {
		vote.votesCast.add(new CastVote(proxyId, theCastVote));
	}

}
