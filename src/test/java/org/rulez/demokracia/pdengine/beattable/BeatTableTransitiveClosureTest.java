package org.rulez.demokracia.pdengine.beattable;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.rulez.demokracia.pdengine.testhelpers.BeatTableTestHelper.*;

import java.util.Collection;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.rulez.demokracia.pdengine.annotations.TestedBehaviour;
import org.rulez.demokracia.pdengine.annotations.TestedFeature;
import org.rulez.demokracia.pdengine.annotations.TestedOperation;
import org.rulez.demokracia.pdengine.exception.ReportedException;

@TestedFeature("Schulze method")
@TestedOperation("compare beats")
@TestedBehaviour("implements the Floyd-Warshall algorithm")
@RunWith(MockitoJUnitRunner.class)
public class BeatTableTransitiveClosureTest {

  @InjectMocks
  private BeatTableTransitiveClosureServiceImpl beatTableTransitiveClosureService;
  private BeatTable beatTable;

  @Before
  public void setUp() throws ReportedException {
    beatTable = createNewBeatTableWithComplexData();
  }

  @Test
  public void transitive_closure_on_empty_beat_table_results_empty_result() {
    BeatTable actual = new BeatTable();
    actual = beatTableTransitiveClosureService.computeTransitiveClosure(actual);
    assertTrue(actual.getKeyCollection().isEmpty());
  }

  @Test
  public void transitive_closure_computes_the_shortest_paths_by_pairs() {
    beatTable =
        beatTableTransitiveClosureService.computeTransitiveClosure(beatTable);
    final Collection<String> keyCollection = beatTable.getKeyCollection();
    for (final String i : keyCollection)
      for (final String j : keyCollection) {
        if (i.equals(j))
          continue;
        assertNoShorterPathBetweenChoices(keyCollection, i, j);
      }
  }

  @Test
  public void test_transitive_closure_compute_optimal_route() {
    beatTable =
        beatTableTransitiveClosureService.computeTransitiveClosure(beatTable);
    assertEquals(
        new Pair(5, 1), beatTable.getElement(CHOICE1, CHOICE3)
    );
  }

  private void assertNoShorterPathBetweenChoices(
      final Collection<String> keyCollection, final String choice1,
      final String choice2
  ) {
    for (final String k : keyCollection) {
      if (Set.of(choice1, choice2).contains(k))
        continue;
      final Pair greater = beatTable.getElement(choice1, choice2);
      final Pair less = lessBeat(
          beatTable.getElement(choice1, k), beatTable.getElement(k, choice2)
      );
      assertEquals(greater, beatTable.compareBeats(less, greater));
    }
  }

  private Pair lessBeat(final Pair beat1, final Pair beat2) {
    return beat1.compareTo(beat2) >= 0 ? beat2 : beat1;
  }
}
