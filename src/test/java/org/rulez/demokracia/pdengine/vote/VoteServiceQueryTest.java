package org.rulez.demokracia.pdengine.vote;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import java.util.Optional;
import java.util.Set;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.rulez.demokracia.pdengine.annotations.TestedBehaviour;
import org.rulez.demokracia.pdengine.annotations.TestedFeature;
import org.rulez.demokracia.pdengine.annotations.TestedOperation;
import org.rulez.demokracia.pdengine.testhelpers.ThrowableTester;

@TestedFeature("Manage votes")
@TestedOperation("create vote")
@TestedBehaviour("Creates a vote")
@RunWith(MockitoJUnitRunner.class)
public class VoteServiceQueryTest extends ThrowableTester {

  private static final String VOTE_ID = "badbeefb1gb00b5";

  @Mock
  private VoteRepository voteRepository;

  @InjectMocks
  private VoteServiceImpl voteService;

  private final Vote vote = new Vote("Vote", Set.of(), Set.of(), false, 2);

  @Test
  public void get_vote_returns_the_vote_from_repository() {
    when(voteRepository.findById(VOTE_ID)).thenReturn(Optional.of(vote));
    assertEquals(vote, voteService.getVote(VOTE_ID));
  }

  @Test
  public void get_vote_throws_exception_when_vote_not_found() {
    when(voteRepository.findById(VOTE_ID)).thenReturn(Optional.empty());
    assertThrows(() -> voteService.getVote(VOTE_ID)).assertMessageIs("illegal voteId");
  }
}
