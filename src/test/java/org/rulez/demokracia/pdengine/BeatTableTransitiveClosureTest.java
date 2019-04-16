package org.rulez.demokracia.pdengine;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.rulez.demokracia.pdengine.annotations.TestedBehaviour;
import org.rulez.demokracia.pdengine.annotations.TestedFeature;
import org.rulez.demokracia.pdengine.annotations.TestedOperation;
import org.rulez.demokracia.pdengine.dataobjects.Pair;
import org.rulez.demokracia.pdengine.exception.ReportedException;
import org.rulez.demokracia.pdengine.testhelpers.CreatedBeatTable;

import com.google.common.collect.Sets;

@TestedFeature("Schulze method")
@TestedOperation("compare beats")
@TestedBehaviour("implements the Floyd-Warshall algorithm")
public class BeatTableTransitiveClosureTest extends CreatedBeatTable {

  @Override
  @Before
  public void setUp() throws ReportedException {
    super.setUp();
    createNewBeatTableWithComplexData();
  }

  @Test
  public void transitive_closure_on_empty_beat_table_results_empty_result() {
    final BeatTable actual = new BeatTable();
    actual.computeTransitiveClosure();
    assertTrue(actual.getKeyCollection().isEmpty());
  }

  @Test
  public void transitive_closure_computes_the_shortest_paths_by_pairs() {
    beatTable.computeTransitiveClosure();
    final Collection<String> keyCollection = beatTable.getKeyCollection();
    for (final String i : keyCollection)
      for (final String j : keyCollection) {
        if (i.equals(j))
          continue;
        assertNoShorterPathBetweenChoices(keyCollection, i, j);
      }
  }

  private void assertNoShorterPathBetweenChoices(
      final Collection<String> keyCollection, final String choice1,
      final String choice2
  ) {
    for (final String k : keyCollection) {
      if (Sets.newHashSet(choice1, choice2).contains(k))
        continue;
      final Pair greater = beatTable.getElement(choice1, choice2);
      final Pair less = beatTable.lessBeat(
          beatTable.getElement(choice1, k), beatTable.getElement(k, choice2)
      );
      assertEquals(greater, beatTable.compareBeats(less, greater));
    }
  }

  @Test
  public void less_beat_returns_the_less_beat() {
    final Pair pair1 = new Pair(20, 10);
    final Pair pair2 = new Pair(10, 10);

    final Pair lessBeat = beatTable.lessBeat(pair1, pair2);
    assertEquals(pair2, lessBeat);
  }
}
