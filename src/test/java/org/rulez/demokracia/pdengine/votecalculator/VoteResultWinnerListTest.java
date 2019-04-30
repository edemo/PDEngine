package org.rulez.demokracia.pdengine.votecalculator;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.rulez.demokracia.pdengine.annotations.TestedBehaviour;
import org.rulez.demokracia.pdengine.annotations.TestedFeature;
import org.rulez.demokracia.pdengine.annotations.TestedOperation;

@TestedFeature("Vote")
@TestedOperation("Compute vote results")
@TestedBehaviour("calculates and stores winner list")
@RunWith(MockitoJUnitRunner.class)
public class VoteResultWinnerListTest extends VoteResultTestBase {

  @Before
  @Override
  public void setUp() {
    super.setUp();
  }

  @Test
  public void vote_result_contains_winners_list() {
    final List<List<String>> expectedWinners =
        List.of(List.of("A", "B"), List.of("C"), List.of("D"));
    assertAllVoteResultContainsWinners(expectedWinners, result);
  }

  private void assertAllVoteResultContainsWinners(
      final List<List<String>> expectedWinners, final List<VoteResult> result
  ) {
    for (int i = 0; i < result.size(); ++i)
      assertTrue(
          result.get(i).getWinners().containsAll(expectedWinners.get(i))
      );
  }

}
