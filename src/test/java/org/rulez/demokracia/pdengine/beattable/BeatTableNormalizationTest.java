package org.rulez.demokracia.pdengine.beattable;

import static org.junit.Assert.assertEquals;
import static org.rulez.demokracia.pdengine.testhelpers.BeatTableTestHelper.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.rulez.demokracia.pdengine.annotations.TestedBehaviour;
import org.rulez.demokracia.pdengine.annotations.TestedFeature;
import org.rulez.demokracia.pdengine.annotations.TestedOperation;
import org.rulez.demokracia.pdengine.beattable.Pair;
import org.rulez.demokracia.pdengine.beattable.BeatTable.Direction;
import org.rulez.demokracia.pdengine.exception.ReportedException;
import org.rulez.demokracia.pdengine.testhelpers.ThrowableTester;

@TestedFeature("Schulze method")
@TestedOperation("normalize beat matrix")
@RunWith(MockitoJUnitRunner.class)
public class BeatTableNormalizationTest extends ThrowableTester {


	@InjectMocks
	private BeatTableServiceImpl beatTableService;

	private BeatTable beatTable;

	private BeatTable normalizedBeatTable;

	@Before
	public void setUp() throws ReportedException {
		beatTable = createNewBeatTableWithComplexData();
		normalizedBeatTable = beatTableService.normalize(beatTable);
	}

	@TestedBehaviour("the diagonal elements are (0,0)")
	@Test
	public void normalization_sets_the_diagonal_to_0_0() {
		normalizedBeatTable.getKeyCollection().forEach(k -> assertDiagonalElementIsZero(k));
	}

	@TestedBehaviour("the elements corresponding to loosers are (0,0)")
	@Test
	public void normalization_sets_the_looser_to_0_0() {
		Pair element = normalizedBeatTable.getElement(CHOICE3, CHOICE1);
		assertEquals(new Pair(0, 0), element);
	}

	@TestedBehaviour("the elements corresponding to winners contain the number of looses backward")
	@Test
	public void normalization_does_not_modify_the_winners_number_of_looses() {
		int actualResult = normalizedBeatTable.beatInformation(CHOICE1, CHOICE2, Direction.DIRECTION_BACKWARD);
		assertEquals(1, actualResult);
	}

	@TestedBehaviour("the elements corresponding to winners contain the number of wins forward")
	@Test
	public void normalization_does_not_modify_the_winners_number_of_wins() {
		int actualResult = normalizedBeatTable.beatInformation(CHOICE1, CHOICE2, Direction.DIRECTION_FORWARD);
		assertEquals(5, actualResult);
	}

	@TestedBehaviour("the elements for ties are (0,0)")
	@Test
	public void normalization_set_the_ties_to_0_0() {
		setEqualData();
		Pair element = normalizedBeatTable.getElement(CHOICE2, CHOICE1);
		assertEquals(new Pair(0, 0), element);
	}

	@TestedBehaviour("the elements for ties are (0,0)")
	@Test
	public void normalization_set_the_other_part_of_the_ties_to_0_0_too() {
		setEqualData();
		Pair element = normalizedBeatTable.getElement(CHOICE1, CHOICE2);
		assertEquals(new Pair(0, 0), element);
	}

	@TestedBehaviour("the elements for ties are (0,0)")
	@Test
	public void normalization_does_not_modify_the_values_when_the_selected_beats_are_not_ties() {
		setEqualData();
		Pair element = normalizedBeatTable.getElement(CHOICE2, CHOICE3);
		assertEquals(new Pair(4, 1), element);
	}

	private void setEqualData() {
		normalizedBeatTable = beatTableService.normalize(createNewBeatTableWithEqualData());
	}

	private void assertDiagonalElementIsZero(final String key) {
		Pair element = normalizedBeatTable.getElement(key, key);
		assertEquals(new Pair(0, 0), element);
	}
}
