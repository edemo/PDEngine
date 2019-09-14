package org.rulez.demokracia.pdengine.votecalculator;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.rulez.demokracia.pdengine.testhelpers.BeatTableTestHelper.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.rulez.demokracia.pdengine.RandomUtils;
import org.rulez.demokracia.pdengine.beattable.BeatTableService;
import org.rulez.demokracia.pdengine.beattable.BeatTableTransitiveClosureService;
import org.rulez.demokracia.pdengine.tally.Tally;
import org.rulez.demokracia.pdengine.tally.TallyService;
import org.rulez.demokracia.pdengine.testhelpers.VoteResultTestHelper;
import org.rulez.demokracia.pdengine.vote.Vote;
import org.rulez.demokracia.pdengine.votecast.CastVote;

public class ComputedVoteTestBase {

  @InjectMocks
  protected ComputedVoteServiceImpl computedVoteService;

  @Mock
  protected BeatTableService beatTableService;

  @Mock
  protected BeatTableTransitiveClosureService beatTableTransitiveClosureService;

  @Mock
  protected VoteResultComposer voteResultComposer;

  @Mock
  protected TallyService tallyService;

  protected ComputedVote computedVote;

  protected Vote vote;

  @Before
  public void setUp() {
    when(beatTableService.initializeBeatTable(any()))
        .thenReturn(createNewBeatTableWithComplexData());
    final List<String> keys = List.of("A", "B", "C", "D");
    when(beatTableService.normalize(any()))
        .thenReturn(createTransitiveClosedBeatTable(keys));
    when(beatTableTransitiveClosureService.computeTransitiveClosure(any()))
        .thenReturn(createTransitiveClosedBeatTable(keys));
    when(voteResultComposer.composeResult(any()))
        .thenReturn(VoteResultTestHelper.createVoteResults());
    when(tallyService.calculateTally(anyString(), any()))
        .then(a -> new Tally((String) a.getArgument(0)));

    vote = new Vote(
        "name", new ArrayList<>(), List.of("hun", "ger", "eng"), false, 1
    );
    vote.setVotesCast(
        List.of(new CastVote(RandomUtils.createRandomKey(), List.of()))
    );
    computedVote = computedVoteService.computeVote(vote);
  }
}
