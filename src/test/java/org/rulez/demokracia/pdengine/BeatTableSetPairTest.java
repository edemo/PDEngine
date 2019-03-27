package org.rulez.demokracia.pdengine;

import org.junit.Before;
import org.junit.Test;
import org.rulez.demokracia.pdengine.annotations.TestedBehaviour;
import org.rulez.demokracia.pdengine.annotations.TestedFeature;
import org.rulez.demokracia.pdengine.annotations.TestedOperation;
import org.rulez.demokracia.pdengine.exception.ReportedException;
import org.rulez.demokracia.pdengine.testhelpers.CreatedBeatTable;

@TestedFeature("Supporting functionality")
@TestedOperation("BeatTable")
@TestedBehaviour("the pair related to a and b can be set")
public class BeatTableSetPairTest extends CreatedBeatTable {

	@Before
	public void setUp() throws ReportedException {
		super.setUp();
	}

	@Test
	public void setPair_throws_an_exception_when_inputs_are_not_defined() {
		assertThrows(() -> beatTable.setPair(null, null, null)
				).assertMessageIs("Invalid Pair key");
	}
	
	@Test
	public void setPair_inserts_the_pair_when_there_is_no_pair_in_the_matrix() {
		createNewBeatTableWithData();
		getAndAssertResult(choice1, choice2);
	}
}
