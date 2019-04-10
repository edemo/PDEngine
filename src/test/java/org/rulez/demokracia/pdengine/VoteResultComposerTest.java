package org.rulez.demokracia.pdengine;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;
import org.rulez.demokracia.pdengine.annotations.TestedBehaviour;
import org.rulez.demokracia.pdengine.annotations.TestedFeature;
import org.rulez.demokracia.pdengine.annotations.TestedOperation;
import org.rulez.demokracia.pdengine.testhelpers.CreatedVoteResultComposer;

import com.google.common.collect.Sets;

@TestedFeature("Vote")
@TestedOperation("Compute vote results")
@TestedBehaviour("the winners list contains the looses to the first one")
public class VoteResultComposerTest extends CreatedVoteResultComposer {

  private Set<String> choicesReturned;
  private Set<String> keySetOfInitialBeatTable;

  @Before
  @Override
  public void setUp() {
    super.setUp();

    choicesReturned = convertResultToChoiceSet(result);
    keySetOfInitialBeatTable = Sets.newHashSet("A", "B", "C", "D");

  }

  @Test
  public void compute_vote_results_returns_every_choice() {
    assertEquals(keySetOfInitialBeatTable, choicesReturned);
  }

  @Test
  public void compute_vote_results_returns_each_choices_once() {
    final List<String> keyList = result.stream().map(VoteResult::getChoices)
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
    final boolean allMatch =
        voteResult.getBeats().values().stream().allMatch(m -> !m.isEmpty());
    assertTrue(allMatch);
  }

  private Integer getNumberOfBeats(final VoteResult voteResult) {
    return voteResult.getBeats().values().stream().map(m -> m.size())
        .reduce((a, b) -> a + b).get();
  }

  private Set<String> convertResultToChoiceSet(final List<VoteResult> result) {
    return result.stream()
        .map(voteResult -> voteResult.getChoices())
        .flatMap(List::stream)
        .collect(Collectors.toSet());
  }
}
