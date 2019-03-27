package org.rulez.demokracia.pdengine;

import org.junit.Before;
import org.junit.Test;
import org.rulez.demokracia.pdengine.annotations.TestedBehaviour;
import org.rulez.demokracia.pdengine.annotations.TestedFeature;
import org.rulez.demokracia.pdengine.annotations.TestedOperation;
import org.rulez.demokracia.pdengine.exception.ReportedException;
import org.rulez.demokracia.pdengine.testhelpers.CreatedBeatTable;

@TestedFeature("Unimplemented")
@TestedOperation("Unimplemented")
@TestedBehaviour("Unimplemented")
public class BeatTableCompareBeatsTieTest extends CreatedBeatTable {

	@Before
	public void setUp() throws ReportedException {
		super.setUp();
	}
	
	@Test
	public void compareBeats_is_not_yet_implemented_when_the_result_is_tie() {
		assertThrows(() -> beatTable.compareBeats(beats1, beats1)
				).assertMessageIs("Can not decide");
	}
}