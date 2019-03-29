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

import jersey.repackaged.com.google.common.collect.Sets;

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
		BeatTable actual = new BeatTable();
		actual.computeTransitiveClosure();
		assertTrue(actual.getKeyCollection().isEmpty());
	}

	@Test
	public void transitive_closure_computes_the_shortest_paths_by_pairs() {
		beatTable.computeTransitiveClosure();
		Collection<Choice> keyCollection = beatTable.getKeyCollection();
		for (Choice i : keyCollection) {
			for (Choice j : keyCollection) {
				if (i.equals(j)) {
					continue;
				}
				for (Choice k: keyCollection) {
					if (Sets.newHashSet(i, j).contains(k)) {
						continue;
					}
					Pair greater = beatTable.getElement(i, j);
					Pair less = beatTable.lessBeat(beatTable.getElement(i, k), beatTable.getElement(k, j));
					assertEquals(greater, beatTable.compareBeats(less, greater));
				}
			}
		}
	}

	@Test
	public void less_beat_returns_the_less_beat() {
		Pair pair1 = new Pair(20, 10);
		Pair pair2 = new Pair(10, 10);

		Pair lessBeat = beatTable.lessBeat(pair1, pair2);
		assertEquals(pair2, lessBeat);
	}
}