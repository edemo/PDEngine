package org.rulez.demokracia.pdengine.beattable;

import static org.junit.Assert.assertEquals;
import static org.rulez.demokracia.pdengine.testhelpers.BeatTableTestHelper.*;

import org.junit.Test;
import org.rulez.demokracia.pdengine.annotations.TestedBehaviour;
import org.rulez.demokracia.pdengine.annotations.TestedFeature;
import org.rulez.demokracia.pdengine.annotations.TestedOperation;
import org.rulez.demokracia.pdengine.beattable.BeatTable.Direction;
import org.rulez.demokracia.pdengine.testhelpers.ThrowableTester;

@TestedFeature("Supporting functionality")
@TestedOperation("BeatTable")
@TestedBehaviour("the beat information related to a and b can be obtained for forward and backward")
public class BeatTableBeatInformationTest extends ThrowableTester {

	@Test
	public void beatInformation_throws_an_exception_when_direction_is_not_defined() {
		assertThrows(() -> createNewBeatTableWithData().beatInformation(null, null, null)
				).assertMessageIs("Invalid direction");
	}

	@Test
	public void beatInformation_gives_back_the_number_of_winnings_from_choice1_to_choice2() {
		BeatTable beatTable = createNewBeatTableWithData();

		assertEquals(PAIR.getWinning(), beatTable.beatInformation(CHOICE1,
				CHOICE2, Direction.DIRECTION_FORWARD));
	}

	@Test
	public void beatInformation_gives_back_the_number_of_losing_from_choice1_to_choice2() {
		BeatTable beatTable = createNewBeatTableWithData();

		assertEquals(PAIR.getLosing(), beatTable.beatInformation(CHOICE1,
				CHOICE2, Direction.DIRECTION_BACKWARD));
	}
}
