package org.rulez.demokracia.pdengine.showvote;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.rulez.demokracia.pdengine.authentication.AuthenticatedUserService;
import org.rulez.demokracia.pdengine.testhelpers.ThrowableTester;
import org.rulez.demokracia.pdengine.vote.VoteService;

public class ShowVoteTestBase extends ThrowableTester {

	@InjectMocks
	protected ShowVoteServiceImpl showVoteService;

	@Mock
	protected VoteService voteService;

	@Mock
	protected AuthenticatedUserService authService;
}
