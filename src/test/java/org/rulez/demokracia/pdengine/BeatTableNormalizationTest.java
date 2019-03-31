package org.rulez.demokracia.pdengine;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.rulez.demokracia.pdengine.BeatTable.Direction;
import org.rulez.demokracia.pdengine.annotations.TestedBehaviour;
import org.rulez.demokracia.pdengine.annotations.TestedFeature;
import org.rulez.demokracia.pdengine.annotations.TestedOperation;
import org.rulez.demokracia.pdengine.dataobjects.Pair;
import org.rulez.demokracia.pdengine.exception.ReportedException;
import org.rulez.demokracia.pdengine.testhelpers.CreatedBeatTable;

@TestedFeature("Schulze method")
@TestedOperation("normalize beat matrix")
public class BeatTableNormalizationTest extends CreatedBeatTable {

	@Override
	@Before
	public void setUp() throws ReportedException {
		super.setUp();
		createNewBeatTableWithComplexData();
	}

	@TestedBehaviour("the diagonal elements are (0,0)")
	@Test
	public void normalization_sets_the_diagonal_to_0_0() {
		beatTable.normalize();
		beatTable.getKeyCollection().forEach(k -> assertDiagonalElementIsZero(k));
	}

	private void assertDiagonalElementIsZero(final String key) {
		Pair element = beatTable.getElement(key, key);
		assertEquals(new Pair(0, 0), element);
	}
	
	@TestedBehaviour("the elements corresponding to loosers are (0,0)")
	@Test
	public void normalization_sets_the_looser_to_0_0() {
        setPair(choice3, choice1);
        assertTrue(pair.winning == 0 && pair.losing == 0);
	}
	
	@TestedBehaviour("the elements corresponding to winners contain the number of looses backward")
	@Test
	public void normalization_does_not_modify_the_winners_number_of_looses() {
		beatTable.normalize();
		int actualResult = beatTable.beatInformation(choice1, choice2, Direction.DIRECTION_BACKWARD);
		assertEquals(1, actualResult);
	}
	
	@TestedBehaviour("the elements corresponding to winners contain the number of wins forward")
	@Test
	public void normalization_does_not_modify_the_winners_number_of_wins() {
		beatTable.normalize();
		int actualResult = beatTable.beatInformation(choice1, choice2, Direction.DIRECTION_FORWARD);
		assertEquals(5, actualResult);
	}
	
	@TestedBehaviour("the elements for ties are (0,0)")
	@Test
	public void normalization_set_the_ties_to_0_0() {
		createNewBeatTableWithEqualData();
		setPair(choice2, choice1);
		assertTrue(pair.winning == 0 && pair.losing == 0);
	}
	
	@TestedBehaviour("the elements for ties are (0,0)")
	@Test
	public void normalization_set_the_other_part_of_the_ties_to_0_0_too() {
		createNewBeatTableWithEqualData();
		setPair(choice1, choice2);
		assertTrue(pair.winning == 0 && pair.losing == 0);
	}
	
	@TestedBehaviour("the elements for ties are (0,0)")
	@Test
	public void normalization_does_not_modify_the_values_when_the_selected_beats_are_not_ties() {
		createNewBeatTableWithEqualData();
		setPair(choice2, choice3);
		assertTrue(pair.winning == 4 && pair.losing == 1);
	}

	private void setPair(String choice1, String choice2) {
		beatTable.normalize();
		pair = beatTable.getPair(choice1, choice2);
	}
}
