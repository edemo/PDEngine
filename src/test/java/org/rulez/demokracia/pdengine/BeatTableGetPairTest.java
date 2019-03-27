package org.rulez.demokracia.pdengine;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.rulez.demokracia.pdengine.annotations.TestedBehaviour;
import org.rulez.demokracia.pdengine.annotations.TestedFeature;
import org.rulez.demokracia.pdengine.annotations.TestedOperation;
import org.rulez.demokracia.pdengine.dataobjects.Pair;
import org.rulez.demokracia.pdengine.exception.ReportedException;
import org.rulez.demokracia.pdengine.testhelpers.CreatedBeatTable;

@TestedFeature("Supporting functionality")
@TestedOperation("BeatTable")
@TestedBehaviour("the pair related to a and b can be obtained")
public class BeatTableGetPairTest extends CreatedBeatTable {
	
	@Before
	public void setUp() throws ReportedException {
		super.setUp();
	}

	@Test
	public void getPair_throws_an_exception_when_inputs_are_not_defined() {
		assertThrows(() -> beatTable.getPair(null, null)
				).assertMessageIs("Invalid row key");
	}
	
	@Test
	public void getPair_gives_back_the_pair_related_to_choice1_and_choice2() {
		createNewBeatTableWithComplexData();
		Pair result = beatTable.matrix.getElement(choice1, choice2);
		
		assertTrue(result.winning == pair.winning && result.losing == pair.losing);	
	}
}
