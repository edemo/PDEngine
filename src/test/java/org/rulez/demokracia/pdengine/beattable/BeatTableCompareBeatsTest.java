package org.rulez.demokracia.pdengine.beattable;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import org.junit.Before;
import org.junit.Test;
import org.rulez.demokracia.pdengine.annotations.TestedBehaviour;
import org.rulez.demokracia.pdengine.annotations.TestedFeature;
import org.rulez.demokracia.pdengine.annotations.TestedOperation;
import org.rulez.demokracia.pdengine.testhelpers.ThrowableTester;

@TestedFeature("Schulze method")
@TestedOperation("compare beats")
public class BeatTableCompareBeatsTest extends ThrowableTester {

  private static final String IF_BEATS_ARE_DIFFERENT_THE_BIGGER_WINS = "if beats are different, the bigger wins";
  private static final String IF_BEATS_AND_LOSES_ARE_TIE_GIVE_BACK_THE_FIRST_OPTION =
      "if beats and loses are tie, give back the first option";
  private static final String IF_BEATS_TIE_LOOSES_DECIDE =
      "if beats tie, looses decide";

  private BeatTable beatTable;

  @Before
  public void setUp() {
    beatTable = new BeatTable();
  }

  @TestedBehaviour(IF_BEATS_TIE_LOOSES_DECIDE)
  @Test
  public void compareBeats_gives_back_the_backward_lower_beat1() {
    Pair beat1 = new Pair(2, 2);
    Pair beat2 = new Pair(2, 3);
    assertEquals(beat1, beatTable.compareBeats(beat1, beat2));
  }

  @TestedBehaviour(IF_BEATS_TIE_LOOSES_DECIDE)
  @Test
  public void compareBeats_gives_back_the_backward_lower_beat2() {
    Pair beat1 = new Pair(42, 10);
    Pair beat2 = new Pair(42, 3);
    assertEquals(beat2, beatTable.compareBeats(beat1, beat2));
  }

  @TestedBehaviour(IF_BEATS_AND_LOSES_ARE_TIE_GIVE_BACK_THE_FIRST_OPTION)
  @Test
  public void compareBeats_returns_the_first_beat_when_the_result_is_tie() {
    Pair beat1 = new Pair(4, 3);
    Pair beat2 = new Pair(4, 3);
    assertSame(beat1, beatTable.compareBeats(beat1, beat2));
  }

  @TestedBehaviour(IF_BEATS_ARE_DIFFERENT_THE_BIGGER_WINS)
  @Test
  public void
      compareBeats_throws_an_exception_when_first_input_param_is_not_defined() {
    assertThrows(
        () -> beatTable.compareBeats(
            null,
            new Pair(4, 3)
        )
    ).assertMessageIs("Invalid Pair key");
  }

  @TestedBehaviour(IF_BEATS_ARE_DIFFERENT_THE_BIGGER_WINS)
  @Test
  public void
      compareBeats_throws_an_exception_when_second_input_param_is_not_defined() {
    assertThrows(
        () -> beatTable.compareBeats(new Pair(4, 7),
            null
        )
    ).assertMessageIs("Invalid Pair key");
  }

  @TestedBehaviour(IF_BEATS_ARE_DIFFERENT_THE_BIGGER_WINS)
  @Test
  public void compareBeats_gives_back_the_forward_bigger_beat1() {
    Pair beat1 = new Pair(7, 3);
    Pair beat2 = new Pair(4, 3);
    assertSame(beat1, beatTable.compareBeats(beat1, beat2));
  }

  @TestedBehaviour(IF_BEATS_ARE_DIFFERENT_THE_BIGGER_WINS)
  @Test
  public void compareBeats_gives_back_the_forward_bigger_beat2() {
    Pair beat1 = new Pair(7, 3);
    Pair beat2 = new Pair(8, 3);
    assertSame(beat2, beatTable.compareBeats(beat1, beat2));
  }
}
