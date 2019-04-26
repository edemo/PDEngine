package org.rulez.demokracia.pdengine.beattable;

import java.util.Collection;
import java.util.Set;

import org.springframework.stereotype.Service;

@Service
public class BeatTableTransitiveClosureServiceImpl
    implements BeatTableTransitiveClosureService {

  @Override
  public BeatTable computeTransitiveClosure(final BeatTable beatTable) {
    BeatTable result = new BeatTable(beatTable);
    Collection<String> keyCollection = result.getKeyCollection();
    keyCollection.forEach(
        i -> keyCollection.forEach(j -> computeMinimalRoute(result, i, j))
    );
    return result;
  }

  private void computeMinimalRoute(
      final BeatTable beatTable, final String choice1, final String choice2
  ) {
    if (!choice1.equals(choice2))
      beatTable.getKeyCollection()
          .forEach(k -> selectShorterPath(beatTable, choice1, choice2, k));
  }

  private void selectShorterPath(
      final BeatTable beatTable, final String choice1, final String choice2,
      final String middleChoice
  ) {
    if (!Set.of(choice1, choice2).contains(middleChoice))
      beatTable.setElement(
          choice1, choice2,
          greaterBeat(beatTable.getElement(choice1, choice2),
              lessBeat(beatTable.getElement(choice1, middleChoice),
                  beatTable.getElement(middleChoice, choice2)
              )
          )
      );
  }

  private Pair greaterBeat(final Pair beat1, final Pair beat2) {
    return Integer.signum(beat1.compareTo(beat2)) == 1 ? beat1 : beat2;
  }

  private Pair lessBeat(final Pair beat1, final Pair beat2) {
    return Integer.signum(beat1.compareTo(beat2)) == 1 ? beat2 : beat1;
  }
}
