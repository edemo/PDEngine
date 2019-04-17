package org.rulez.demokracia.pdengine.votecast;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;
import static org.rulez.demokracia.pdengine.votecast.CastVoteTestHelper.CHOICE_B;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.rulez.demokracia.pdengine.annotations.TestedBehaviour;
import org.rulez.demokracia.pdengine.annotations.TestedFeature;
import org.rulez.demokracia.pdengine.annotations.TestedOperation;
import org.rulez.demokracia.pdengine.choice.RankedChoice;
import org.rulez.demokracia.pdengine.votecast.CastVote;

@RunWith(MockitoJUnitRunner.class)
public class VoteCastUpdatableTest extends CastVoteTestBase {

	private static final String TEST_USER_NAME = "GeorgeSoros";
	private List<RankedChoice> theCastVote;

	@Override
	@Before
	public void setUp() {
		super.setUp();

		theCastVote = List.of(new RankedChoice(CHOICE_B.getId(), 1));
		vote.getVotesCast().clear();
		when(authService.getAuthenticatedUserName()).thenReturn(TEST_USER_NAME);
	}

	@TestedFeature("Vote")
	@TestedOperation("Cast vote")
	@TestedBehaviour("if updatable is true then the cast vote records the proxy id of the user")
	@Test
	public void castVote_records_the_proxy_id_when_updatable_is_true() {
		vote.getParameters().setUpdatable(true);
		castVoteService.castVote(vote.getId(), ballot, theCastVote);

		assertTrue(vote.getVotesCast().get(0).getProxyId().equals(TEST_USER_NAME));
	}

	@TestedFeature("Vote")
	@TestedOperation("Cast vote")
	@TestedBehaviour("If updatable is false then the cast vote is not associated with the voter")
	@Test
	public void castVote_does_not_record_the_proxy_id_when_updatable_is_false() {
		vote.getParameters().setUpdatable(false);

		castVoteService.castVote(vote.getId(), ballot, theCastVote);
		assertTrue(vote.getVotesCast().get(0).getProxyId() == null);
	}

	@TestedFeature("Vote")
	@TestedOperation("Cast vote")
	@TestedBehaviour("If updatable is false then the cast vote is not associated with the voter")
	@Test
	public void castVote_does_not_record_the_proxy_id_when_updatable_is_false_and_it_does_not_delete_the_other_not_recorded_proxyIds_votescast() {
		vote.getParameters().setUpdatable(false);
		addCastVoteWithDefaultPreferencesForUser(TEST_USER_NAME);
		for (Integer i = 0; i < 7; i++) {
			addCastVoteWithDefaultPreferencesForUser("dummy" + i);
		}

		castVoteService.castVote(vote.getId(), ballot, theCastVote);

		assertEquals(null, vote.getVotesCast().get(8).getProxyId());
	}

	private void addCastVoteWithDefaultPreferencesForUser(final String proxyId) {
		vote.getVotesCast().add(new CastVote(proxyId, theCastVote));
	}

}
