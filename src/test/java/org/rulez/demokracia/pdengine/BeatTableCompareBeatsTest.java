package org.rulez.demokracia.pdengine;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.rulez.demokracia.pdengine.annotations.TestedBehaviour;
import org.rulez.demokracia.pdengine.annotations.TestedFeature;
import org.rulez.demokracia.pdengine.annotations.TestedOperation;
import org.rulez.demokracia.pdengine.exception.ReportedException;
import org.rulez.demokracia.pdengine.testhelpers.CreatedBeatTable;

@TestedFeature("Schulze method")
@TestedOperation("compare beats")
public class BeatTableCompareBeatsTest extends CreatedBeatTable {

	@Before
	public void setUp() throws ReportedException {
		super.setUp();
	}

	@TestedBehaviour("if beats are different, the bigger wins")
	@Test
	public void compareBeats_throws_an_exception_when_first_input_param_is_not_defined() {
		assertThrows(() -> beatTable.compareBeats(beats1, null)
				).assertMessageIs("Invalid Pair key");
	}
	
	@TestedBehaviour("if beats are different, the bigger wins")
	@Test
	public void compareBeats_throws_an_exception_when_second_input_param_is_not_defined() {
		assertThrows(() -> beatTable.compareBeats(null, beats2)
				).assertMessageIs("Invalid Pair key");
	}
	
	@TestedBehaviour("if beats are different, the bigger wins")
	@Test
	public void compareBeats_gives_back_the_forward_bigger_beat1() {	
		setCompareBeatsAsResult(beats1, beats2);
		assertTrue(result.winning == beats1.winning && result.losing == beats1.losing);
	}
	
	@TestedBehaviour("if beats tie, looses decide")
	@Test
	public void compareBeats_gives_back_the_backward_lower_beat1() {
		setCompareBeatsAsResult(beats1, beats3);	
		assertTrue(result.winning == beats1.winning && result.losing == beats1.losing);
	}
	
    @TestedBehaviour("Unimplemented")
	@Test
	public void compareBeats_is_not_yet_implemented_when_the_result_is_tie() {
		assertThrows(() -> beatTable.compareBeats(beats1, beats1)
				).assertMessageIs("Can not decide");
	}
	
	@TestedBehaviour("if beats are different, the bigger wins")
	@Test
	public void compareBeats_gives_back_the_forward_bigger_beat2() {
		setCompareBeatsAsResult(beats4, beats3);
		assertTrue(result.winning == beats3.winning && result.losing == beats3.losing);
	}
	
	@TestedBehaviour("if beats tie, looses decide")
	@Test
	public void compareBeats_gives_back_the_backward_lower_beat2() {
		setCompareBeatsAsResult(beats1, beats5);
		assertTrue(result.winning == beats5.winning && result.losing == beats5.losing);
	}
}