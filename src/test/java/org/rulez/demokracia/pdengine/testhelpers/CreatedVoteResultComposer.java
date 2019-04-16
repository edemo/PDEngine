package org.rulez.demokracia.pdengine.testhelpers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.rulez.demokracia.pdengine.BeatTable;
import org.rulez.demokracia.pdengine.VoteResult;
import org.rulez.demokracia.pdengine.VoteResultComposer;
import org.rulez.demokracia.pdengine.VoteResultComposerImpl;
import org.rulez.demokracia.pdengine.WinnerCalculator;

public class CreatedVoteResultComposer
    extends CreatedDefaultCastVoteWithRankedChoices {

  protected VoteResultComposer voteResultComposer;
  protected List<VoteResult> result;

  @Override
  @Before
  public void setUp() {
    super.setUp();
    voteResultComposer = new VoteResultComposerImpl();
    voteResultComposer.setWinnerCalculator(createWinnerCalculatorMock());
    result = voteResultComposer.composeResult(createBeatTable());
  }

  private BeatTable createBeatTable() {
    BeatTable beatTable = new BeatTable(Arrays.asList("A", "B", "C", "D"));
    beatTable.initialize(castVote);
    beatTable.normalize();
    beatTable.computeTransitiveClosure();
    return beatTable;
  }

  private WinnerCalculator createWinnerCalculatorMock() {
    WinnerCalculator winnerCalculator = mock(WinnerCalculator.class);
    when(winnerCalculator.calculateWinners(any(), any()))
        .thenReturn(Arrays.asList("A", "B"))
        .thenReturn(Arrays.asList("C"))
        .thenReturn(Arrays.asList("D"));
    return winnerCalculator;
  }

}
