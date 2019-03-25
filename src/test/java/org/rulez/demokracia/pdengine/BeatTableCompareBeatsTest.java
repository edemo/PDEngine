package org.rulez.demokracia.pdengine;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.rulez.demokracia.pdengine.annotations.TestedBehaviour;
import org.rulez.demokracia.pdengine.annotations.TestedFeature;
import org.rulez.demokracia.pdengine.annotations.TestedOperation;
import org.rulez.demokracia.pdengine.dataobjects.Pair;
import org.rulez.demokracia.pdengine.exception.ReportedException;
import org.rulez.demokracia.pdengine.testhelpers.CreatedDefaultChoice;

@TestedFeature("Schulze method")
@TestedOperation("compare beats")
public class BeatTableCompareBeatsTest extends CreatedDefaultChoice {

	Pair beats1, beats2, beats3, beats4, beats5, result;
	BeatTable beatTable;
	
	@Before
	public void setUp() throws ReportedException {
		super.setUp();
		
		beatTable = new BeatTable();
		beats1 = new Pair(150, 22);
		beats2 = new Pair(100, 40);
		beats3 = new Pair(150, 40);
		beats4 = new Pair(100, 22);
		beats5 = new Pair(150, 10);
	}

	@TestedBehaviour("if beats are different, the bigger wins")
	@Test
	public void compareBeats_with_not_defined_first_input() {
		assertThrows(() -> beatTable.compareBeats(beats1, null)
				).assertMessageIs("Invalid Pair key");
	}
	
	@TestedBehaviour("if beats are different, the bigger wins")
	@Test
	public void compareBeats_with_not_defined_second_input() {
		assertThrows(() -> beatTable.compareBeats(null, beats2)
				).assertMessageIs("Invalid Pair key");
	}
	
	@TestedBehaviour("if beats are different, the bigger wins")
	@Test
	public void compareBeats_with_bigger_beat1_forward() {
		result = beatTable.compareBeats(beats1, beats2);
		assertTrue(result.winning == beats1.winning && result.losing == beats1.losing);	
	}
	
	@TestedBehaviour("if beats tie, looses decide")
	@Test
	public void compareBeats_with_equal_forward_and_lower_beat1_backward() {
		result = beatTable.compareBeats(beats1, beats3);
		assertTrue(result.winning == beats1.winning && result.losing == beats1.losing);	
	}
	
	@TestedBehaviour("if beats are different, the bigger wins")
	@Test
	public void compareBeats_with_equal_forward_and_equal_backward_beats() {
		assertThrows(() -> beatTable.compareBeats(beats1, beats1)
				).assertMessageIs("Can not decide");
	}
	
	@TestedBehaviour("if beats are different, the bigger wins")
	@Test
	public void compareBeats_with_bigger_beat2_forward() {
		result = beatTable.compareBeats(beats4, beats3);
		assertTrue(result.winning == beats3.winning && result.losing == beats3.losing);	
	}
	
	@TestedBehaviour("if beats tie, looses decide")
	@Test
	public void compareBeats_with_equal_forward_and_lower_beat2_backward() {
		result = beatTable.compareBeats(beats1, beats5);
		assertTrue(result.winning == beats5.winning && result.losing == beats5.losing);	
	}
}
