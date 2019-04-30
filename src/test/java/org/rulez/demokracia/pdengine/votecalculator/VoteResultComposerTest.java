package org.rulez.demokracia.pdengine.votecalculator;

import static org.junit.Assert.*;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.rulez.demokracia.pdengine.annotations.TestedBehaviour;
import org.rulez.demokracia.pdengine.annotations.TestedFeature;
import org.rulez.demokracia.pdengine.annotations.TestedOperation;

@TestedFeature("Vote")
@TestedOperation("Compute vote results")
@TestedBehaviour("the winners list contains the looses to the first one")
@RunWith(MockitoJUnitRunner.class)
public class VoteResultComposerTest extends VoteResultTestBase {

  @Before
  @Override
  public void setUp() {
    super.setUp();
  }

  @Test
  public void compute_vote_results_returns_every_choice() {
    assertEquals(keySetOfInitialBeatTable, choicesReturned);
  }

  @Test
  public void compute_vote_results_returns_each_choices_once() {
    final List<String> keyList = result.stream().map(VoteResult::getWinners)
        .flatMap(List::stream)
        .collect(Collectors.toList());
    assertEquals(keyList.size(), choicesReturned.size());
  }

  @Test
  public void compute_vote_results_assigns_no_beat_to_winners() {
    final int winnersLoses = getNumberOfBeats(result.get(0));
    assertEquals(0, winnersLoses);
  }

  @Test
  public void compute_vote_results_return_nonzero_loses_for_nonwinners() {
    for (final VoteResult choiceMap : result.subList(1, result.size()))
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
}
