package org.rulez.demokracia.pdengine.votecalculator;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.rulez.demokracia.pdengine.testhelpers.BeatTableTestHelper.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.rulez.demokracia.pdengine.annotations.TestedBehaviour;
import org.rulez.demokracia.pdengine.annotations.TestedFeature;
import org.rulez.demokracia.pdengine.annotations.TestedOperation;
import org.rulez.demokracia.pdengine.beattable.BeatTable;

@TestedFeature("Schulze method")
@TestedOperation("rank candidates")
@TestedBehaviour("calculates winners until all choices are ignored")
@RunWith(MockitoJUnitRunner.class)
public class VoteResultComposerIgnoreTest {

  @InjectMocks
  private VoteResultComposerImpl voteResultComposer;
  @Mock
  private WinnerCalculatorService winnerCalculatorService;

  @Test
  public void
      winner_calculation_ends_in_one_step_when_all_choices_ignored_at_once() {
    when(winnerCalculatorService.calculateWinners(any(), any()))
        .thenReturn(List.of(CHOICE1, CHOICE2, CHOICE3));

    verifyWinnerCalculatorRunnedNTimes(3);
  }

  @Test
  public void
      winner_calculation_ends_in_n_steps_when_choices_ignored_one_by_one() {
    when(winnerCalculatorService.calculateWinners(any(), any()))
        .thenReturn(List.of(CHOICE1))
        .thenReturn(List.of(CHOICE2))
        .thenReturn(List.of(CHOICE3));

    verifyWinnerCalculatorRunnedNTimes(3);
  }

  private void verifyWinnerCalculatorRunnedNTimes(final int timesOfRun) {
    voteResultComposer
        .composeResult(createNewBeatTableWithComplexData());

    verify(winnerCalculatorService, times(timesOfRun))
        .calculateWinners(any(), any());
  }

  @Test
  public void winner_calculation_ends_in_zero_step_when_beat_table_is_empty() {
    voteResultComposer
        .composeResult(new BeatTable());

    verify(winnerCalculatorService, never()).calculateWinners(any(), any());
  }
}
