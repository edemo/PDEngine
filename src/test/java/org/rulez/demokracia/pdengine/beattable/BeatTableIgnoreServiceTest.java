package org.rulez.demokracia.pdengine.beattable;

import static org.junit.Assert.*;
import static org.rulez.demokracia.pdengine.testhelpers.BeatTableTestHelper.*;

import java.util.Collection;
import java.util.List;
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
import org.rulez.demokracia.pdengine.beattable.BeatTableIgnoreServiceImpl;

@TestedFeature("Vote")
@TestedOperation("calculate winners")
@TestedBehaviour("only choices not in ignoredChoices are considered")
@RunWith(MockitoJUnitRunner.class)
public class BeatTableIgnoreServiceTest {

	@InjectMocks
	BeatTableIgnoreServiceImpl beatTableIgnoreService;

	private Collection<String> ignoredChoices;
	private BeatTable ignoredBeatTable;
	private BeatTable beatTable;

	@Before
	public void setUp() {
		beatTable = createNewBeatTableWithComplexData();
		ignoredChoices = List.of(CHOICE1, CHOICE2);
		ignoredBeatTable = beatTableIgnoreService.ignoreChoices(beatTable, ignoredChoices);
	}

	@Test
	public void ignore_choices_returns_none_of_the_ignored_choices() {
		assertIntersectionIsEmpty(ignoredChoices, ignoredBeatTable.getKeyCollection());
	}

	@Test
	public void ignore_choices_returns_the_not_ignored_choices() {
		assertEquals(Set.of(CHOICE3), Set.copyOf(ignoredBeatTable.getKeyCollection()));
	}

	@Test
	public void ignore_choices_copies_every_not_ignored_pairs() {
		assertBeatsEqualsInSubset(beatTable, ignoredBeatTable, Set.of(CHOICE3));
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
