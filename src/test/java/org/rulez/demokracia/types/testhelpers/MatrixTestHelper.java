package org.rulez.demokracia.types.testhelpers;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.rulez.demokracia.pdengine.beattable.MapMatrix;
import org.rulez.demokracia.pdengine.beattable.Matrix;

public final class MatrixTestHelper {

  public static Matrix<String, Integer> buildMatrix(final int matrixSize) {
    Matrix<String, Integer> myMatrix = new MapMatrix<>(buildLabels(matrixSize));
    for (int row = 0; row < matrixSize; row++)
      for (int col = 0; col < matrixSize; col++)
        myMatrix.setElement(
            labelFor(col),
            labelFor(row),
            row * matrixSize + col
        );
    return myMatrix;
  }

  public static List<String> buildLabels(final int matrixSize) {
    return IntStream.range(0, matrixSize).boxed()
        .map(MatrixTestHelper::labelFor).collect(Collectors.toList());
  }

  public static String labelFor(final int col) {
    return String.format("label %d", col);
  }
}
