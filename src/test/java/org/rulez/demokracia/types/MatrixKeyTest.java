package org.rulez.demokracia.types;

import static org.junit.Assert.assertTrue;

import java.util.Collection;

import org.junit.Test;
import org.rulez.demokracia.types.testhelpers.MatrixTestSetup;

public class MatrixKeyTest extends MatrixTestSetup {

	@Test
	public void rowKey_should_be_in_keyCollection_for_setElement() {
		keyCollection.add(columnKey);
		updateMatrix();
		assertThrows(
				() -> theMatrix.setElement(columnKey, rowKey, value))
			.assertException(IllegalArgumentException.class).assertMessageIs("Invalid row key");
	}

	@Test
	public void columnKey_should_be_in_keyCollection_for_setElement() {
		keyCollection.add(rowKey);
		updateMatrix();
		assertThrows(
				() -> theMatrix.setElement(columnKey,rowKey, value))
			.assertException(IllegalArgumentException.class).assertMessageIs("Invalid column key");
	}

	@Test
	public void rowKey_should_be_in_keyCollection_for_getElement() {
		keyCollection.add(columnKey);
		updateMatrix();
		assertThrows(
				() -> theMatrix.getElement(columnKey, rowKey))
			.assertException(IllegalArgumentException.class).assertMessageIs("Invalid row key");
	}

	@Test
	public void columnKey_should_be_in_keyCollection_for_getElement() {
		keyCollection.add(rowKey);
		updateMatrix();
		assertThrows(
				() -> theMatrix.getElement(columnKey,rowKey))
			.assertException(IllegalArgumentException.class).assertMessageIs("Invalid column key");
	}

	@Test
	public void after_the_matrix_is_created_there_is_no_way_to_change_its_keyCollection() {
		keyCollection.add(columnKey);
		updateMatrix();
		String newRowKey = "newRow";
		keyCollection.add(newRowKey);
		assertThrows(
				() -> theMatrix.setElement(columnKey, newRowKey, value))
			.assertException(IllegalArgumentException.class).assertMessageIs("Invalid row key");
	}

	@Test
	public void the_keyCollection_of_the_matrix_can_be_obtained() {
		keyCollection.add(rowKey);
		updateMatrix();
		Collection<String> collection = theMatrix.getKeyCollection();
		assertTrue(collection.contains(rowKey));
	}

	@Test
	public void the_keyCollection_of_the_matrix_is_immutable() {
		keyCollection.add(rowKey);
		updateMatrix();
		Collection<String> collection = theMatrix.getKeyCollection();
		assertThrows(
				() -> collection.add("foo"));
	}

}
