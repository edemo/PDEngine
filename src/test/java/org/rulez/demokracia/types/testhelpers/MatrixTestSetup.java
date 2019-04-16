package org.rulez.demokracia.types.testhelpers;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Before;
import org.rulez.demokracia.testhelpers.ThrowableTester;
import org.rulez.demokracia.types.MapMatrix;
import org.rulez.demokracia.types.Matrix;

public class MatrixTestSetup extends ThrowableTester {

  protected Matrix<String, Integer> theMatrix;
  protected Collection<String> keyCollection;
  protected String rowKey;
  protected String columnKey;
  protected Integer value;

  @Before
  public void setUp() {
    keyCollection = new ArrayList<>();
    rowKey = "initialRowKey";
    columnKey = "initialColumnKey";
    value = 42;
  }

  protected void updateMatrix() {
    theMatrix = new MapMatrix<>(keyCollection);
  }

}
