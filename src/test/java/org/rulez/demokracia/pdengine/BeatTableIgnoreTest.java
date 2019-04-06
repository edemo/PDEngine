package org.rulez.demokracia.pdengine;

import static org.junit.Assert.*;
import java.util.Arrays;
import java.util.Collection;
import org.junit.Before;
import org.junit.Test;
import org.rulez.demokracia.pdengine.annotations.TestedBehaviour;
import org.rulez.demokracia.pdengine.annotations.TestedFeature;
import org.rulez.demokracia.pdengine.annotations.TestedOperation;
import org.rulez.demokracia.pdengine.testhelpers.CreatedComputedVote;

import jersey.repackaged.com.google.common.collect.Sets;

@TestedFeature("Vote")
@TestedOperation("calculate winners")
@TestedBehaviour("only choices not in ignoredChoices are considered")
public class BeatTableIgnoreTest extends CreatedComputedVote {

	private BeatTableIgnore beatTableIgnore;
	private BeatTable beatTable;

	@Override
	@Before
	public void setUp() {
		super.setUp();
		beatTable = computedVote.getBeatPathTable();
		beatTableIgnore = new BeatTableIgnoreImpl();
	}

	@Test
	public void calculate_winners_returns_none_of_the_ignored_choices() {
		Collection<String> ignoredChoices = Arrays.asList("A", "B");
		BeatTable ignoredBeatTable = beatTableIgnore.ignoreChoices(beatTable, ignoredChoices);

		assertIntersectionIsEmpty(ignoredChoices, ignoredBeatTable.getKeyCollection());
	}

	@Test
	public void calculate_winners_returns_the_not_ignored_choices() {
		Collection<String> ignoredChoices = Arrays.asList("A", "B");
		BeatTable ignoredBeatTable = beatTableIgnore.ignoreChoices(beatTable, ignoredChoices);

		assertEquals(Sets.newHashSet("C", "D"), Sets.newHashSet(ignoredBeatTable.getKeyCollection()));
	}

	private void assertIntersectionIsEmpty(final Collection<String> collection1, final Collection<String> collection2) {
		assertTrue(Sets.intersection(Sets.newHashSet(collection1), Sets.newHashSet(collection2)).isEmpty());
	}
}
