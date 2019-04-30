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
import org.mockito.stubbing.OngoingStubbing;
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
  private OngoingStubbing<List<String>> whenCalculateWinnersCalled;

  @Test
  public void
      winner_calculation_ends_in_one_step_when_all_choices_ignored_at_once() {
    verifyWinnerCalculatorRunnedNTimes(
        createNewBeatTableWithComplexData(),
        List.of(List.of(CHOICE1, CHOICE2, CHOICE3)), 1
    );
  }

  @Test
  public void
      winner_calculation_ends_in_n_steps_when_choices_ignored_one_by_one() {
    verifyWinnerCalculatorRunnedNTimes(
        createNewBeatTableWithComplexData(),
        List.of(List.of(CHOICE1), List.of(CHOICE2), List.of(CHOICE3)), 3
    );
  }

  @Test
  public void winner_calculation_ends_in_zero_step_when_beat_table_is_empty() {
    verifyWinnerCalculatorRunnedNTimes(new BeatTable(), List.of(), 0);
  }

  private void verifyWinnerCalculatorRunnedNTimes(
      final BeatTable beatTable,
      final List<List<String>> returnedChoices, final int timesOfRun
  ) {
    prepareMocks(returnedChoices);

    voteResultComposer
        .composeResult(beatTable);

    verify(winnerCalculatorService, times(timesOfRun))
        .calculateWinners(any(), any());
  }

  private void prepareMocks(final List<List<String>> returnedChoices) {
    whenCalculateWinnersCalled =
        when(winnerCalculatorService.calculateWinners(any(), any()));

    returnedChoices.forEach(
        choiceList -> whenCalculateWinnersCalled =
            whenCalculateWinnersCalled.thenReturn(choiceList)
    );

    whenCalculateWinnersCalled.thenThrow(
        new AssertionError(
            "Computing vote should stop when all choices are ignored"
        )
    );
  }
}
