package org.rulez.demokracia.pdengine;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.rulez.demokracia.pdengine.dataobjects.Pair;

public class VoteResultComposerImpl implements VoteResultComposer {

  private WinnerCalculator winnerCalculator;
  private final Set<String> ignoredSet;

  public VoteResultComposerImpl() {
    winnerCalculator = new WinnerCalculatorImpl();
    ignoredSet = new HashSet<>();
  }

  @Override
  public List<VoteResult> composeResult(final BeatTable beatTable) {
    final List<VoteResult> result = new ArrayList<>();
    final HashSet<String> keyCollection =
        new HashSet<>(beatTable.getKeyCollection());
    while (!ignoredSet.equals(keyCollection)) {
      final List<String> winners =
          winnerCalculator.calculateWinners(beatTable, ignoredSet);

      result.add(createVoteResult(beatTable, winners));
      ignoredSet.addAll(winners);
    }
    return result;
  }

  private VoteResult
      createVoteResult(final BeatTable beatTable, final List<String> winners) {
    return new VoteResult(winners, getBeats(winners, beatTable));
  }

  private Map<String, Map<String, Pair>>
      getBeats(final List<String> choices, final BeatTable beatTable) {
    final Map<String, Map<String, Pair>> result = new ConcurrentHashMap<>();
    choices.stream()
        .forEach(c -> result.put(c, getBeatsForChoice(c, beatTable)));
    return result;
  }

  private Map<String, Pair>
      getBeatsForChoice(final String choice, final BeatTable beatTable) {
    final Pair zeroPair = new Pair(0, 0);
    final Map<String, Pair> result = new ConcurrentHashMap<>();
    for (final String row : beatTable.getKeyCollection()) {
      final Pair beat = beatTable.getElement(row, choice);
      if (!zeroPair.equals(beat))
        result.put(row, beat);
    }
    return result;
  }

  @Override
  public void setWinnerCalculator(final WinnerCalculator winnerCalculator) {
    this.winnerCalculator = winnerCalculator;
  }
}
