package org.rulez.demokracia.pdengine.beattable;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.rulez.demokracia.pdengine.testhelpers.BeatTableTestHelper.createNewBeatTableWithComplexData;

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
import org.rulez.demokracia.pdengine.beattable.BeatTable;
import org.rulez.demokracia.pdengine.beattable.Pair;
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
		beatTable = beatTableTransitiveClosureService.computeTransitiveClosure(beatTable);
		Collection<String> keyCollection = beatTable.getKeyCollection();
		for (String i : keyCollection) {
			for (String j : keyCollection) {
				if (i.equals(j)) {
					continue;
				}
				assertNoShorterPathBetweenChoices(keyCollection, i, j);
			}
		}
	}

	private void assertNoShorterPathBetweenChoices(final Collection<String> keyCollection, final String choice1,
			final String choice2) {
		for (String k : keyCollection) {
			if (Set.of(choice1, choice2).contains(k)) {
				continue;
			}
			Pair greater = beatTable.getElement(choice1, choice2);
			Pair less = lessBeat(beatTable.getElement(choice1, k), beatTable.getElement(k, choice2));
			assertEquals(greater, beatTable.compareBeats(less, greater));
		}
	}

	private Pair lessBeat(final Pair beat1, final Pair beat2) {
		return beat1.compareTo(beat2) >= 0 ? beat2 : beat1;
	}
}