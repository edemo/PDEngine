package org.rulez.demokracia.pdengine;

import static org.junit.Assert.*;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.rulez.demokracia.pdengine.annotations.TestedBehaviour;
import org.rulez.demokracia.pdengine.annotations.TestedFeature;
import org.rulez.demokracia.pdengine.annotations.TestedOperation;
import org.rulez.demokracia.pdengine.testhelpers.CreatedBeatTable;

@TestedFeature("Vote")
@TestedOperation("calculate winners")
@TestedBehaviour("only choices not in ignoredChoices are considered")
public class BeatTableIgnoreTest extends CreatedBeatTable {

	private Collection<String> ignoredChoices;
	private BeatTable ignoredBeatTable;

	@Override
	@Before
	public void setUp() {
		super.setUp();
		createNewBeatTableWithComplexData();
		BeatTableIgnore beatTableIgnore = new BeatTableIgnoreImpl();
		ignoredChoices = List.of("name1", "name2");
		ignoredBeatTable = beatTableIgnore.ignoreChoices(beatTable, ignoredChoices);
	}

	@Test
	public void ignore_choices_returns_none_of_the_ignored_choices() {
		assertIntersectionIsEmpty(ignoredChoices, ignoredBeatTable.getKeyCollection());
	}

	@Test
	public void ignore_choices_returns_the_not_ignored_choices() {
		assertEquals(Set.of("name3"), Set.copyOf(ignoredBeatTable.getKeyCollection()));
	}

	@Test
	public void ignore_choices_copies_every_not_ignored_pairs() {
		assertBeatsEqualsInSubset(beatTable, ignoredBeatTable, Set.of("name3"));
	}

	private void assertBeatsEqualsInSubset(final BeatTable table1, final BeatTable table2,
			final Set<String> choices) {
		for (String choice1 : choices) {
			for (String choice2 : choices) {
				assertEquals(table1.getElement(choice1, choice2), table2.getElement(choice1, choice2));
			}
		}

	}

	private void assertIntersectionIsEmpty(final Collection<String> collection1, final Collection<String> collection2) {
		assertFalse(Set.copyOf(collection1).stream().anyMatch(Set.copyOf(collection2)::contains));
	}
}
