package org.rulez.demokracia.types;

import static org.junit.Assert.*;
import static org.rulez.demokracia.types.testhelpers.MatrixSerializationTestHelper.*;
import static org.rulez.demokracia.types.testhelpers.MatrixTestHelper.*;
import java.io.ByteArrayOutputStream;
import org.junit.Before;
import org.junit.Test;
import org.rulez.demokracia.pdengine.beattable.Matrix;

public class MatrixSerializationTest {

  private static final int MATRIX_SIZE = 100;
  private static final String COLUMN_KEY = labelFor(3);
  private static final String ROW_KEY = labelFor(5);
  private static final Integer VALUE = 42;
  private ByteArrayOutputStream outputStream;
  private Matrix<String, Integer> theMatrix;

  @Before
  public void setUp() {
    outputStream = new ByteArrayOutputStream();
    theMatrix = buildMatrix(100);
  }

  @Test
  public void matrix_can_be_serialized_and_deserialized() throws Exception {
    serializeMatrix(outputStream, theMatrix);
    Matrix<String, Integer> myMatrix = deserializeMatrix(outputStream.toByteArray());
    assertTrue(myMatrix.getKeyCollection().containsAll(buildLabels(MATRIX_SIZE)));
  }

  @Test
  public void matrix_with_an_element_can_be_deserialized() throws Exception {
    theMatrix.setElement(COLUMN_KEY, ROW_KEY, VALUE);
    serializeMatrix(outputStream, theMatrix);
    Matrix<String, Integer> myMatrix = deserializeMatrix(outputStream.toByteArray());
    assertEquals(myMatrix.getElement(COLUMN_KEY, ROW_KEY), VALUE);
  }

  @Test
  public void a_full_matrix_can_be_deserialized() throws Exception {
    int matrixSize = 100;
    Matrix<String, Integer> myMatrix = buildMatrix(matrixSize);
    assertMatrixIsCorrect(myMatrix);
  }

  private void assertMatrixIsCorrect(final Matrix<String, Integer> matrix) {
    int matrixSize = matrix.getKeyCollection().size();
    for (int row = 0; row < matrixSize; row++)
      for (int col = 0; col < matrixSize; col++)
        assertEquals((Integer) (row * matrixSize + col),
            matrix.getElement(labelFor(col), labelFor(row)));
  }

}
