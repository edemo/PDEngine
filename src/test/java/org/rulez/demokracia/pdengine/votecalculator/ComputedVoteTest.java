package org.rulez.demokracia.pdengine.votecalculator;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.rulez.demokracia.pdengine.testhelpers.BeatTableTestHelper.*;

import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.rulez.demokracia.pdengine.annotations.TestedBehaviour;
import org.rulez.demokracia.pdengine.annotations.TestedFeature;
import org.rulez.demokracia.pdengine.annotations.TestedOperation;
import org.rulez.demokracia.pdengine.beattable.BeatTable;

@TestedFeature("Vote")
@TestedOperation("Compute vote results")
@RunWith(MockitoJUnitRunner.class)
public class ComputedVoteTest extends ComputedVoteTestBase {

  @TestedBehaviour("compares and stores initial beat matrix")
  @Test
  public void compute_vote_should_create_initial_matrix_with_full_key_set() {
    assertEquals(
        Set.of("name1", "name2", "name3"), Set.copyOf(computedVote.getBeatTable().getKeyCollection())
    );
  }

  @TestedBehaviour("compares and stores initial beat matrix")
  @Test
  public void after_compute_vote_beat_table_should_contain_beat_information() {
    assertBeatTableEquals(
        computedVote.getBeatTable(), createNewBeatTableWithComplexData()
    );
  }

  @TestedBehaviour("calculates and stores beatpath matrix")
  @Test
  public void beat_path_matrix_is_not_the_same_as_initial_matrix() {
    assertNotSame(computedVote.getBeatTable(), computedVote.getBeatPathTable());
  }

  @TestedBehaviour("calculates and stores beatpath matrix")
  @Test
  public void beat_path_matrix_is_normalized() {
    verify(beatTableService).normalize(computedVote.getBeatTable());
  }

  @TestedBehaviour("calculates and stores beatpath matrix")
  @Test
  public void transitive_closure_done_on_beat_path_matrix() {
    assertBeatTableEquals(
        createTransitiveClosedBeatTable(),
        computedVote.getBeatPathTable()
    );
  }

  @TestedBehaviour("calculates and stores vote results")
  @Test
  public void vote_results_stored_in_computed_vote() {
    assertFalse(computedVote.getVoteResults().isEmpty());
  }

  private void assertBeatTableEquals(
      final BeatTable firstBeatTable, final BeatTable secondBeatTable
  ) {
    for (final String choice1 : firstBeatTable.getKeyCollection())
      for (final String choice2 : firstBeatTable.getKeyCollection())
        assertEquals(
            secondBeatTable.getElement(choice1, choice2), firstBeatTable.getElement(choice1, choice2)
        );
  }

  @TestedBehaviour(
    "vote result includes the votes cast with the secret cast vote identifier."
  )
  @Test
  public void
      vote_result_includes_the_votes_cast_with_the_secret_cast_vote_id() {
    final String secretId =
        computedVote.getVote().getVotesCast().get(0).getSecretId();
    assertFalse(secretId.isEmpty());
  }
}
