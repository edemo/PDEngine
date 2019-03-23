package org.rulez.demokracia.types;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.rulez.demokracia.types.testhelpers.MatrixTestSetup;

public class MatrixGetterSetterTest extends MatrixTestSetup {

	@Before
	public void setUp() {
		super.setUp();
		keyCollection.add(rowKey);
		keyCollection.add(columnKey);
		updateMatrix();
	}

	@Test
	public void setElement_sets_the_element_and_getElement_gets_it() {
		theMatrix.setElement(columnKey,rowKey,value);
		Integer gotValue = theMatrix.getElement(columnKey,rowKey);
		assertEquals(value,gotValue);
	}

	@Test
	public void setElement_sets_the_element_and_getElement_gets_it_for_different_rows() {
		String newKey = addNewKey();
		theMatrix.setElement(columnKey,rowKey,value);
		theMatrix.setElement(columnKey,newKey,1);
		assertEquals(value,theMatrix.getElement(columnKey,rowKey));
	}

	@Test
	public void setElement_sets_the_element_and_getElement_gets_it_for_different_columns() {
		String newKey = addNewKey();
		theMatrix.setElement(columnKey,rowKey,value);
		theMatrix.setElement(newKey,rowKey,1);
		assertEquals(value,theMatrix.getElement(columnKey,rowKey));
	}

	private String addNewKey() {
		String newKey = "newKey";
		keyCollection.add(newKey);
		updateMatrix();
		return newKey;
	}


}
