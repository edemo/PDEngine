package org.rulez.demokracia.pdengine.votecalculator;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.Before;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.rulez.demokracia.pdengine.testhelpers.BeatTableTestHelper;

public class VoteResultTestBase {

  @InjectMocks
  private VoteResultComposerImpl voteResultComposer;
  @Mock
  private WinnerCalculatorService winnerCalculatorService;

  protected Set<String> choicesReturned;
  protected Set<String> keySetOfInitialBeatTable;
  protected List<VoteResult> result;

  @Before
  public void setUp() {
    when(winnerCalculatorService.calculateWinners(any(), any()))
        .thenReturn(List.of("A", "B"))
        .thenReturn(List.of("C"))
        .thenReturn(List.of("D"));

    result = voteResultComposer
        .composeResult(BeatTableTestHelper.createTransitiveClosedBeatTable());
    choicesReturned = convertResultToChoiceSet(result);
    keySetOfInitialBeatTable = Set.of("A", "B", "C", "D");
  }

  private Set<String> convertResultToChoiceSet(final List<VoteResult> result) {
    return result.stream()
        .map(VoteResult::getWinners)
        .flatMap(List::stream)
        .collect(Collectors.toSet());
  }
}
