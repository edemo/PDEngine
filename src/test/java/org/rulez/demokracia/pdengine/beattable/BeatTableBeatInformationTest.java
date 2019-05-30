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
    assertThrows(() -> createNewBeatTableWithData().beatInformation(null, null, null))
        .assertMessageIs("Invalid direction");
  }

  @Test
  public void beatInformation_gives_back_the_number_of_winnings_from_choice1_to_choice2() {
    final BeatTable beatTable = createNewBeatTableWithData();

    assertEquals(PAIR.getWinning(),
        beatTable.beatInformation(CHOICE1, CHOICE2, Direction.DIRECTION_FORWARD));
  }

  @Test
  public void beatInformation_gives_back_the_number_of_losing_from_choice1_to_choice2() {
    final BeatTable beatTable = createNewBeatTableWithData();

    assertEquals(PAIR.getLosing(),
        beatTable.beatInformation(CHOICE1, CHOICE2, Direction.DIRECTION_BACKWARD));
  }

  @Test
  public void beatInformation_throws_exception_when_getting_with_invalid_row_key() {
    assertThrows(() -> createNewBeatTableWithData().getElement(CHOICE1, "InvalidRow"))
        .assertMessageIs("Invalid row key");
  }

  @Test
  public void beatInformation_throws_exception_when_getting_with_invalid_column_key() {
    assertThrows(() -> createNewBeatTableWithData().getElement("InvalidColumn", CHOICE1))
        .assertMessageIs("Invalid column key");
  }

  @Test
  public void beatInformation_throws_exception_when_setting_with_invalid_row_key() {
    assertThrows(
        () -> createNewBeatTableWithData().setElement(CHOICE1, "InvalidRow", new Pair(0, 0)))
            .assertMessageIs("Invalid row key");
  }

  @Test
  public void beatInformation_throws_exception_when_setting_with_invalid_column_key() {
    assertThrows(
        () -> createNewBeatTableWithData().setElement("InvalidColumn", CHOICE1, new Pair(0, 0)))
            .assertMessageIs("Invalid column key");
  }
}
