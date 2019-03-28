package org.rulez.demokracia.pdengine;

import static org.junit.Assert.*;


import org.junit.Before;
import org.junit.Test;
import org.rulez.demokracia.pdengine.annotations.TestedBehaviour;
import org.rulez.demokracia.pdengine.annotations.TestedFeature;
import org.rulez.demokracia.pdengine.annotations.TestedOperation;
import org.rulez.demokracia.pdengine.dataobjects.Pair;
import org.rulez.demokracia.pdengine.exception.ReportedException;
import org.rulez.demokracia.pdengine.testhelpers.CreatedBeatTableForInitialization;


@TestedFeature("Schulze method")
@TestedOperation("compute initial beat matrix")
@TestedBehaviour("the beat matrix contains the beats")
public class BeatTableInitializeTest extends CreatedBeatTableForInitialization {
	
	@Before
	public void setUp() throws ReportedException {
		super.setUp();
	}
	
	@Test
	public void initialize_throws_exception_when_param_is_not_defined() {
		assertThrows(() -> beatTable.initialize(null)
				).assertMessageIs("Invalid castVotes");
	}

	@Test
	public void initialize_sets_the_new_losing_value_when_the_matrix_is_empty() {
		beatTable.initialize(castVotes);
		Pair result = beatTable.getElement("first", "second");
		assertTrue(result.winning == 0 && result.losing == 2);
	}
	
	@Test
	public void initialize_sets_the_new_winning_value_when_the_matrix_is_empty() {
		beatTable.initialize(castVotes);
		Pair result = beatTable.getElement("second", "third");
		assertTrue(result.winning == 2 && result.losing == 0);
	}
}
