package org.rulez.demokracia.pdengine;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.rulez.demokracia.pdengine.annotations.TestedBehaviour;
import org.rulez.demokracia.pdengine.annotations.TestedFeature;
import org.rulez.demokracia.pdengine.annotations.TestedOperation;
import org.rulez.demokracia.pdengine.dataobjects.Pair;
import org.rulez.demokracia.pdengine.exception.ReportedException;
import org.rulez.demokracia.pdengine.testhelpers.CreatedBeatTable;

@TestedFeature("Schulze method")
@TestedOperation("normalize beat matrix")
public class BeatTableNormalizationTest extends CreatedBeatTable {

	@Override
	@Before
	public void setUp() throws ReportedException {
		super.setUp();
		createNewBeatTableWithComplexData();
	}

	@TestedBehaviour("the diagonal elements are (0,0)")
	@Test
	public void normalization_sets_the_diagonal_to_0_0() {
		beatTable.normalize();
		beatTable.getKeyCollection().forEach(k -> assertDiagonalElementIsZero(k));
	}

	private void assertDiagonalElementIsZero(final String key) {
		Pair element = beatTable.getElement(key, key);
		assertEquals(new Pair(0, 0), element);
	}
}
