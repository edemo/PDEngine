package org.rulez.demokracia.pdengine;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.rulez.demokracia.pdengine.annotations.TestedBehaviour;
import org.rulez.demokracia.pdengine.annotations.TestedFeature;
import org.rulez.demokracia.pdengine.annotations.TestedOperation;
import org.rulez.demokracia.pdengine.dataobjects.Pair;
import org.rulez.demokracia.pdengine.exception.ReportedException;
import org.rulez.demokracia.pdengine.testhelpers.CreatedBeatTable;

@TestedFeature("Schulze method")
@TestedOperation("compare beats")
public class BeatTableCompareBeatsTest extends CreatedBeatTable {

	@Override
	@Before
	public void setUp() throws ReportedException {
		super.setUp();
	}

	@TestedBehaviour("if beats tie, looses decide")
	@Test
	public void compareBeats_gives_back_the_backward_lower_beat1() {
		setCompareBeatsAsResult(beats1, beats3);
		assertTrue(result.winning == beats1.winning && result.losing == beats1.losing);
	}

	@TestedBehaviour("if beats tie, looses decide")
	@Test
	public void compareBeats_gives_back_the_backward_lower_beat2() {
		setCompareBeatsAsResult(beats1, beats5);
		assertTrue(result.winning == beats5.winning && result.losing == beats5.losing);
	}

	@TestedBehaviour("if beats and loses are tie, give back the first option")
	@Test
	public void compareBeats_returns_the_first_beat_when_the_result_is_tie() {
		Pair beat1 = new Pair(4, 3);
		Pair beat2 = new Pair(4, 3);

		assertSame(beat1, beatTable.compareBeats(beat1, beat2));
	}

	@TestedBehaviour("if beats are different, the bigger wins")
	@Test
	public void compareBeats_throws_an_exception_when_first_input_param_is_not_defined() {
		assertThrows(() -> beatTable.compareBeats(null, beats2)).assertMessageIs("Invalid Pair key");
	}

	@TestedBehaviour("if beats are different, the bigger wins")
	@Test
	public void compareBeats_throws_an_exception_when_second_input_param_is_not_defined() {
		assertThrows(() -> beatTable.compareBeats(beats1, null)).assertMessageIs("Invalid Pair key");
	}

	@TestedBehaviour("if beats are different, the bigger wins")
	@Test
	public void compareBeats_gives_back_the_forward_bigger_beat1() {
		setCompareBeatsAsResult(beats1, beats2);
		assertTrue(result.winning == beats1.winning && result.losing == beats1.losing);
	}

	@TestedBehaviour("if beats are different, the bigger wins")
	@Test
	public void compareBeats_gives_back_the_forward_bigger_beat2() {
		setCompareBeatsAsResult(beats4, beats3);
		assertTrue(result.winning == beats3.winning && result.losing == beats3.losing);
	}
}