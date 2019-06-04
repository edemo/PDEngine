package org.rulez.demokracia.types;

import static org.junit.Assert.assertTrue;
import java.util.Collection;
import org.junit.Test;
import org.rulez.demokracia.types.testhelpers.MatrixTestSetup;

public class MatrixKeyTest extends MatrixTestSetup {

  @Test
  public void rowKey_should_be_in_keyCollection_for_setElement() {
    updateMatrixWithNewKey(columnKey);
    assertSetElementFailsWith(columnKey, rowKey, "Invalid row key");
  }

  @Test
  public void columnKey_should_be_in_keyCollection_for_setElement() {
    updateMatrixWithNewKey(rowKey);
    assertSetElementFailsWith(columnKey, rowKey, "Invalid column key");
  }

  @Test
  public void rowKey_should_be_in_keyCollection_for_getElement() {
    updateMatrixWithNewKey(columnKey);
    assertGetElementFailsWith(columnKey, rowKey, "Invalid row key");
  }

  @Test
  public void columnKey_should_be_in_keyCollection_for_getElement() {
    updateMatrixWithNewKey(rowKey);
    assertGetElementFailsWith(columnKey, rowKey, "Invalid column key");
  }

  @Test
  public void after_the_matrix_is_created_there_is_no_way_to_change_its_keyCollection() {
    updateMatrixWithNewKey(columnKey);
    keyCollection.add("newRow");
    assertSetElementFailsWith(columnKey, "newRow", "Invalid row key");
  }

  @Test
  public void the_keyCollection_of_the_matrix_can_be_obtained() {
    updateMatrixWithNewKey(rowKey);
    Collection<String> collection = theMatrix.getKeyCollection();
    assertTrue(collection.contains(rowKey));
  }

  @Test
  public void the_keyCollection_of_the_matrix_is_immutable() {
    updateMatrixWithNewKey(rowKey);
    Collection<String> collection = theMatrix.getKeyCollection();
    assertThrows(() -> collection.add("foo"));
  }

  private void updateMatrixWithNewKey(final String key) {
    keyCollection.add(key);
    updateMatrix();
  }

  private void assertSetElementFailsWith(final String columnKey, final String rowKey,
      final String message) {
    assertThrows(() -> {
      theMatrix.setElement(columnKey, rowKey, value);
    }).assertException(IllegalArgumentException.class).assertMessageIs(message);
  }

  private void assertGetElementFailsWith(final String columnKey, final String rowKey,
      final String message) {
    assertThrows(() -> {
      theMatrix.getElement(columnKey, rowKey);
    }).assertException(IllegalArgumentException.class).assertMessageIs(message);
  }
}
