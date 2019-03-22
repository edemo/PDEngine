package org.rulez.demokracia.types;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

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
	public void matrix_can_be_serialized() throws IOException {
	    serializeMatrix();
	}

	@Test
	public void matrix_can_be_deserialized() throws IOException, ClassNotFoundException {
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
		final int N=100;
		int[][] table = buildTable(N);
		ArrayList<String> labels = buildLabels(N);
		Matrix<String, Integer> myMatrix = buildMatrix(N, table, labels);
		assertMatrixIsCorrect(N, table, myMatrix);
	}

	private void assertMatrixIsCorrect(final int N, int[][] table, Matrix<String, Integer> myMatrix) {
		for (int row = 0; row < N; row ++)
			for (int col = 0; col < N; col++)
				assertEquals(
						myMatrix.getElement(
								labelFor(col),
								labelFor(row)),
						(Integer)table[row][col]);
	}

	private Matrix<String, Integer> buildMatrix(final int N, int[][] table, ArrayList<String> labels) {
		Matrix<String, Integer> myMatrix = new MapMatrix<String,Integer>(labels);
		for (int row = 0; row < N; row ++)
			for (int col = 0; col < N; col++)
				myMatrix.setElement(
					labelFor(col),
					labelFor(row),
					table[row][col]);
		return myMatrix;
	}

	private ArrayList<String> buildLabels(final int N) {
		ArrayList<String> labels = new ArrayList<String>();
		for (int row = 0; row < N; row ++)
			labels.add(labelFor(row));
		return labels;
	}

	private int[][] buildTable(final int N) {
		int[][] table = new int[N][N];
		for (int row = 0; row < N; row ++)
		    for (int col = 0; col < N; col++)
		        table[row][col] = row * N + col;
		return table;
	}

	private String labelFor(int col) {
		return String.format("label %d",col);
	}

	private Matrix<String, Integer> deserializeMatrixWithProperChecks() throws IOException, ClassNotFoundException {
		InputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
		ObjectInputStream objectInputStream 
	      = new ObjectInputStream(inputStream);
		Object objectInStream = objectInputStream.readObject();
		if (! (objectInStream instanceof Matrix))
			fail();
		Matrix<?, ?> m = (Matrix<?, ?>) objectInStream;
		Object keyObject = m.getKeyCollection().iterator().next();
		if (! (keyObject instanceof String))
			fail();
		@SuppressWarnings("unchecked")
		Matrix<String,Integer> myMatrix = (Matrix<String, Integer>) m;
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
