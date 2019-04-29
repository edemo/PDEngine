package org.rulez.demokracia.pdengine.votecast;

import static org.mockito.Mockito.when;

import org.apache.catalina.connector.CoyotePrincipal;
import org.junit.Before;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.rulez.demokracia.pdengine.authentication.AuthenticatedUserService;
import org.rulez.demokracia.pdengine.testhelpers.CastVoteTestHelper;
import org.rulez.demokracia.pdengine.testhelpers.ThrowableTester;
import org.rulez.demokracia.pdengine.testhelpers.VariantVote;
import org.rulez.demokracia.pdengine.vote.Vote;
import org.rulez.demokracia.pdengine.vote.VoteService;

public class CastVoteTestBase extends ThrowableTester {

	@InjectMocks
	protected CastVoteServiceImpl castVoteService;

	@Mock
	protected VoteService voteService;
	@Mock
	protected AuthenticatedUserService authService;

	protected Vote vote = new VariantVote();

	protected String ballot = "ballotgyorgy";

	@Before
	public void setUp() {
		when(voteService.getVote(vote.getId())).thenReturn(vote);
		when(authService.getUserPrincipal()).thenReturn(new CoyotePrincipal("name"));
		vote.getParameters().setVotable(true);
		vote.getParameters().setUpdatable(true);
		CastVoteTestHelper.fillVoteWithDummyCastVotes(vote);
		vote.addBallot(ballot);
	}
}
