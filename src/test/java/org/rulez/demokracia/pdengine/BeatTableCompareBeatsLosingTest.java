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
@TestedBehaviour("if beats tie, looses decide")
public class BeatTableCompareBeatsLosingTest extends CreatedBeatTable {

	@Before
	public void setUp() throws ReportedException {
		super.setUp();
	}

	@Test
	public void compareBeats_gives_back_the_backward_lower_beat1() {
		setCompareBeatsAsResult(beats1, beats3);	
		assertTrue(result.winning == beats1.winning && result.losing == beats1.losing);
	}
	
	@Test
	public void compareBeats_gives_back_the_backward_lower_beat2() {
		setCompareBeatsAsResult(beats1, beats5);
		assertTrue(result.winning == beats5.winning && result.losing == beats5.losing);
	}
}