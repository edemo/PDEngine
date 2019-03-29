package org.rulez.demokracia.pdengine;

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
@TestedBehaviour("if beats and loses are tie, give back the first option")
public class BeatTableCompareBeatsTieTest extends CreatedBeatTable {

	@Override
	@Before
	public void setUp() throws ReportedException {
		super.setUp();
	}

	@Test
	public void compareBeats_returns_the_first_beat_when_the_result_is_tie() {
		Pair beat1 = new Pair(4, 3);
		Pair beat2 = new Pair(4, 3);

		assertTrue(beatTable.compareBeats(beat1, beat2) == beat1);
	}
}