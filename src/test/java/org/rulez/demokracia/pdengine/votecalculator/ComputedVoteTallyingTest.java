package org.rulez.demokracia.pdengine.votecalculator;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.rulez.demokracia.pdengine.annotations.TestedBehaviour;
import org.rulez.demokracia.pdengine.annotations.TestedFeature;
import org.rulez.demokracia.pdengine.annotations.TestedOperation;

@TestedFeature("Vote")
@TestedOperation("Compute vote results")
@TestedBehaviour("the result contains tallying for each counted assurances")
@RunWith(MockitoJUnitRunner.class)
public class ComputedVoteTallyingTest extends ComputedVoteTestBase {

  @Override
  @Before
  public void setUp() {
    super.setUp();
  }

  @Test
  public void vote_result_contains_tally_for_each_counted_assurances() {
    assertTrue(
        computedVote.getTallying().keySet()
            .containsAll(vote.getCountedAssurances())
    );
  }

  @Test
  public void vote_result_contains_tally_only_for_counted_assurances() {
    assertTrue(
        vote.getCountedAssurances()
            .containsAll(computedVote.getTallying().keySet())
    );
  }
}
