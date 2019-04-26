package org.rulez.demokracia.pdengine.votecalculator;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.rulez.demokracia.pdengine.annotations.TestedBehaviour;
import org.rulez.demokracia.pdengine.annotations.TestedFeature;
import org.rulez.demokracia.pdengine.annotations.TestedOperation;
import org.rulez.demokracia.pdengine.testhelpers.BeatTableTestHelper;

@TestedFeature("Vote")
@TestedOperation("Compute vote results")
@TestedBehaviour("the winners list contains the looses to the first one")
@RunWith(MockitoJUnitRunner.class)
public class VoteResultComposerTest {

  @InjectMocks
  private VoteResultComposerImpl voteResultComposer;
  @Mock
  private WinnerCalculatorService winnerCalculatorService;

  private Set<String> choicesReturned;
  private Set<String> keySetOfInitialBeatTable;
  private List<VoteResult> result;

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

  @Test
  public void compute_vote_results_returns_every_choice() {
    assertEquals(keySetOfInitialBeatTable, choicesReturned);
  }

  @Test
  public void compute_vote_results_returns_each_choices_once() {
    List<String> keyList = result.stream().map(VoteResult::getChoices)
        .flatMap(List::stream)
        .collect(Collectors.toList());
    assertEquals(keyList.size(), choicesReturned.size());
  }

  @Test
  public void compute_vote_results_assigns_no_beat_to_winners() {
    int winnersLoses = getNumberOfBeats(result.get(0));
    assertEquals(0, winnersLoses);
  }

  @Test
  public void compute_vote_results_return_nonzero_loses_for_nonwinners() {
    for (VoteResult choiceMap : result.subList(1, result.size()))
      assertEachChoiceHaveBeaten(choiceMap);
  }

  private void assertEachChoiceHaveBeaten(final VoteResult voteResult) {
    assertTrue(
        voteResult.getBeats().values().stream()
            .allMatch(m -> !m.getBeats().isEmpty())
    );
  }

  private Integer getNumberOfBeats(final VoteResult voteResult) {
    return voteResult.getBeats().values().stream().map(m -> m.getBeats().size())
        .reduce((a, b) -> a + b).get();
  }

  private Set<String> convertResultToChoiceSet(final List<VoteResult> result) {
    return result.stream()
        .map(voteResult -> voteResult.getChoices())
        .flatMap(List::stream)
        .collect(Collectors.toSet());
  }
}
