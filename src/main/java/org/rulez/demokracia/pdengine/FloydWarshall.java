package org.rulez.demokracia.pdengine;

import java.util.Collection;

public class FloydWarshall {

  private final TransitiveClosable beatTable;

  public FloydWarshall(final TransitiveClosable beatTable) {
    this.beatTable = beatTable;
  }

  public void computeTransitiveClosure() {
    Collection<String> keyCollection = beatTable.getKeyCollection();
    keyCollection
        .forEach(i -> keyCollection.forEach(j -> computeMinimalRoute(i, j)));
  }

  private void computeMinimalRoute(final String choice1, final String choice2) {
    if (!choice1.equals(choice2)) {
      beatTable.getKeyCollection()
          .forEach(k -> selectShorterPath(choice1, choice2, k));
    }
  }

  private void selectShorterPath(
      final String choice1, final String choice2, final String middleChoice
  ) {
    beatTable.selectShorterPath(choice1, choice2, middleChoice);
  }
}
