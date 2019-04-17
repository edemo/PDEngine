package org.rulez.demokracia.pdengine.votecast;

import static org.junit.Assert.*;
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

@TestedFeature("Vote")
@TestedOperation("Cast vote")
@RunWith(MockitoJUnitRunner.class)
public class VoteCastTest extends CastVoteTestBase {

	private static final String TESZT_ELEK = "TesztElek";
	private List<RankedChoice> rankedChoices = List.of(new RankedChoice(CHOICE_B.getId(), 1));;

	@Override
	@Before
	public void setUp() {
		super.setUp();
		vote.getVotesCast().clear();
	}

	@TestedBehaviour("records cast vote with the vote and user's proxy id")
	@Test
	public void cast_vote_records_the_proxy_id() {
		when(authService.getAuthenticatedUserName()).thenReturn(TESZT_ELEK);
		vote.getParameters().setUpdatable(true);
		castVoteService.castVote(vote.getId(), ballot, rankedChoices);
		CastVote voteCast = new CastVote(TESZT_ELEK, rankedChoices);
		assertEquals(voteCast.getProxyId(), vote.getVotesCast().get(0).getProxyId());
	}

	@TestedBehaviour("records cast vote with the vote and user's proxy id")
	@Test
	public void voteCast_records_the_preferences() {
		castVoteService.castVote(vote.getId(), ballot, rankedChoices);
		CastVote voteCast = new CastVote(TESZT_ELEK, rankedChoices);
		assertTrue(voteCast.getPreferences().containsAll(vote.getVotesCast().get(0).getPreferences()));
	}

	@TestedBehaviour("Cast vote have a secret random identifier")
	@Test
	public void voteCast_records_a_secret_id_with_the_vote_cast() {
		castVoteService.castVote(vote.getId(), ballot, rankedChoices);
		assertTrue(vote.getVotesCast().get(0).getSecretId() instanceof String);
	}

	@TestedBehaviour("cast vote identifier is different from the ballot identifier")
	@Test
	public void voteCast_identifier_is_different_from_the_ballot_identifier()
	{
		CastVote castVote = castVoteService.castVote(vote.getId(), ballot,
				rankedChoices);

		assertNotEquals(ballot, castVote.getSecretId());
	}
}
