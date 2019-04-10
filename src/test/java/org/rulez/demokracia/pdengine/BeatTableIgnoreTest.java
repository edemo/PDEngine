package org.rulez.demokracia.pdengine;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collection;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.rulez.demokracia.pdengine.annotations.TestedBehaviour;
import org.rulez.demokracia.pdengine.annotations.TestedFeature;
import org.rulez.demokracia.pdengine.annotations.TestedOperation;
import org.rulez.demokracia.pdengine.testhelpers.CreatedBeatTable;

import com.google.common.collect.Sets;

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
    final BeatTableIgnore beatTableIgnore = new BeatTableIgnoreImpl();
    ignoredChoices = Arrays.asList("name1", "name2");
    ignoredBeatTable = beatTableIgnore.ignoreChoices(beatTable, ignoredChoices);
  }

  @Test
  public void ignore_choices_returns_none_of_the_ignored_choices() {
    assertIntersectionIsEmpty(
        ignoredChoices, ignoredBeatTable.getKeyCollection()
    );
  }

  @Test
  public void ignore_choices_returns_the_not_ignored_choices() {
    assertEquals(
        Sets.newHashSet("name3"), Sets.newHashSet(ignoredBeatTable.getKeyCollection())
    );
  }

  @Test
  public void ignore_choices_copies_every_not_ignored_pairs() {
    assertBeatsEqualsInSubset(
        beatTable, ignoredBeatTable, Sets.newHashSet("name3")
    );
  }

  private void assertBeatsEqualsInSubset(
      final BeatTable table1, final BeatTable table2,
      final Set<String> choices
  ) {
    for (final String choice1 : choices)
      for (final String choice2 : choices)
        assertEquals(
            table1.getElement(choice1, choice2), table2.getElement(choice1, choice2)
        );

  }

  private void assertIntersectionIsEmpty(
      final Collection<String> collection1, final Collection<String> collection2
  ) {
    assertTrue(
        Sets.intersection(Sets.newHashSet(collection1), Sets.newHashSet(collection2)).isEmpty()
    );
  }
}
