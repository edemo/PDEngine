package org.rulez.demokracia.pdengine.choice;

import static org.mockito.Mockito.when;

import org.junit.Before;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.rulez.demokracia.pdengine.authentication.AuthenticatedUserService;
import org.rulez.demokracia.pdengine.testhelpers.ThrowableTester;
import org.rulez.demokracia.pdengine.testhelpers.VariantVote;
import org.rulez.demokracia.pdengine.vote.Vote;
import org.rulez.demokracia.pdengine.vote.VoteService;

public class ChoiceTestBase extends ThrowableTester {

  @InjectMocks
  protected ChoiceServiceImpl choiceService;

  @Mock
  protected VoteService voteService;
  @Mock
  protected AuthenticatedUserService authService;

  protected Vote vote = new VariantVote();

  protected String invalidvoteId;

  @Before
  public void setUp() {
    invalidvoteId = "invalidVoteId";
    when(voteService.getVote(vote.getId())).thenReturn(vote);
  }

}
