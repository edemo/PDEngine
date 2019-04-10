package org.rulez.demokracia.pdengine;

import org.rulez.demokracia.pdengine.dataobjects.Pair;

import com.google.common.collect.Sets;

public interface TransitiveClosable extends ContainingBeats {

  long serialVersionUID = 1L;

  default void computeTransitiveClosure() {
    final FloydWarshall floydWarshall = new FloydWarshall(this);
    floydWarshall.computeTransitiveClosure();
  }

  default void selectShorterPath(
      final String choice1, final String choice2, final String middleChoice
  ) {
    if (!Sets.newHashSet(choice1, choice2).contains(middleChoice))
      setElement(
          choice1, choice2,
          compareBeats(getElement(choice1, choice2),
              lessBeat(getElement(choice1, middleChoice), getElement(middleChoice, choice2))
          )
      );
  }

  default Pair lessBeat(final Pair beat1, final Pair beat2) {
    final Pair greater = compareBeats(beat1, beat2);
    return greater.equals(beat1) ? beat2 : beat1;
  }
}
