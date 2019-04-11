package org.rulez.demokracia.pdengine;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.rulez.demokracia.pdengine.dataobjects.Pair;

public class WinnerCalculatorImpl implements WinnerCalculator {

  private final BeatTableIgnore beatTableIgnore;

  public WinnerCalculatorImpl() {
    beatTableIgnore = new BeatTableIgnoreImpl();
  }

  @Override
  public List<String> calculateWinners(
      final BeatTable beatTable, final Collection<String> ignoredChoices
  ) {
    final BeatTable ignoredBeatTable =
        beatTableIgnore.ignoreChoices(beatTable, ignoredChoices);
    return ignoredBeatTable.getKeyCollection()
        .stream()
        .filter(choice -> isWinner(choice, ignoredBeatTable))
        .collect(Collectors.toList());
  }

  private boolean isWinner(final String choice, final BeatTable beatTable) {
    final Pair nonbeatingPair = new Pair(0, 0);

    return beatTable.getKeyCollection()
        .stream()
        .allMatch(
            otherChoice -> nonbeatingPair
                .equals(beatTable.getElement(otherChoice, choice))
        );
  }

}
