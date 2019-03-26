package org.rulez.demokracia.types;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class MatrixSerializationTests extends MatrixGetterSetterTest {

	private ByteArrayOutputStream outputStream;

	@Before
	public void setUp() {
		super.setUp();
		outputStream = new ByteArrayOutputStream();
	}

	@Test
	public void matrix_can_be_serialized_and_deserialized() throws IOException, ClassNotFoundException {
		serializeMatrix();
		Matrix<String, Integer> myMatrix = deserializeMatrixWithProperChecks();
		assertTrue(myMatrix.getKeyCollection().contains(this.columnKey));
	}

	@Test
	public void matrix_with_an_element_can_be_deserialized() throws IOException, ClassNotFoundException {
		theMatrix.setElement(columnKey, rowKey, value);
		serializeMatrix();
		Matrix<String, Integer> myMatrix = deserializeMatrixWithProperChecks();
		assertEquals(myMatrix.getElement(columnKey, rowKey), value);
	}

	@Test
	public void a_full_matrix_can_be_deserialized() throws IOException, ClassNotFoundException {
		int matrixSize=100;
		int[][] table = buildTable(matrixSize);
		List<String> labels = buildLabels(matrixSize);
		Matrix<String, Integer> myMatrix = buildMatrix(matrixSize, table, labels);
		assertMatrixIsCorrect(matrixSize, table, myMatrix);
	}

	private void assertMatrixIsCorrect(final int matrixSize, final int[][] table, final Matrix<String, Integer> myMatrix) {
		for (int row = 0; row < matrixSize; row ++)
			for (int col = 0; col < matrixSize; col++)
				assertEquals(
						myMatrix.getElement(
								labelFor(col),
								labelFor(row)),
						(Integer)table[row][col]);
	}

	private Matrix<String, Integer> buildMatrix(final int matrixSize, final int[][] table, final List<String> labels) {
		Matrix<String, Integer> myMatrix = new MapMatrix<>(labels);
		for (int row = 0; row < matrixSize; row ++)
			for (int col = 0; col < matrixSize; col++)
				myMatrix.setElement(
					labelFor(col),
					labelFor(row),
					table[row][col]);
		return myMatrix;
	}

	private List<String> buildLabels(final int matrixSize) {
		List<String> labels = new ArrayList<>();
		for (int row = 0; row < matrixSize; row ++)
			labels.add(labelFor(row));
		return labels;
	}

	private int[][] buildTable(final int matrixSize) {
		int[][] table = new int[matrixSize][matrixSize];
		for (int row = 0; row < matrixSize; row ++)
		    for (int col = 0; col < matrixSize; col++)
		        table[row][col] = row * matrixSize + col;
		return table;
	}

	private String labelFor(final int col) {
		return String.format("label %d",col);
	}

	private Matrix<String, Integer> deserializeMatrixWithProperChecks() throws IOException, ClassNotFoundException {
		InputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
		ObjectInputStream objectInputStream 
	      = new ObjectInputStream(inputStream);
		Object objectInStream = objectInputStream.readObject();
		if (! (objectInStream instanceof Matrix))
			fail();
		Matrix<?, ?> newMatrix = (Matrix<?, ?>) objectInStream;
		Object keyObject = newMatrix.getKeyCollection().iterator().next();
		if (! (keyObject instanceof String))
			fail();
		@SuppressWarnings("unchecked")
		Matrix<String,Integer> myMatrix = (Matrix<String, Integer>) newMatrix;
		return myMatrix;
	}

	private void serializeMatrix() throws IOException {
		ObjectOutputStream objectOutputStream 
	      = new ObjectOutputStream(outputStream);
	    objectOutputStream.writeObject(theMatrix);
	    objectOutputStream.flush();
	    objectOutputStream.close();
	}

}
