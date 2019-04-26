package org.rulez.demokracia.pdengine.beattable;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.rulez.demokracia.pdengine.choice.RankedChoice;
import org.rulez.demokracia.pdengine.exception.ReportedException;
import org.rulez.demokracia.pdengine.votecast.CastVote;
import org.springframework.stereotype.Service;

@Service
public class BeatTableServiceImpl implements BeatTableService {

  @Override
  public BeatTable initializeBeatTable(final List<CastVote> castVotes) {
    if (castVotes == null)
      throw new ReportedException("Invalid castVotes");

    final BeatTable result = new BeatTable(collectChoices(castVotes));
    castVotes.forEach(castVote -> calculateBeats(result, castVote));
    return result;
  }

  @Override
  public BeatTable normalize(final BeatTable original) {
    final BeatTable result = new BeatTable(original);
    final Collection<String> keys = result.getKeyCollection();

    for (final String key1 : keys)
      for (final String key2 : keys)
        if (
          original.getElement(key1, key2)
              .compareTo(original.getElement(key2, key1)) <= 0
        )
          result.setElement(key1, key2, getZeroPair());
    return result;
  }

  private Pair getZeroPair() {
    return new Pair(0, 0);
  }

  private void calculateBeats(final BeatTable result, final CastVote castVote) {
    final List<RankedChoice> preferences = castVote.getPreferences();
    for (final RankedChoice column : preferences)
      for (final RankedChoice row : preferences)
        result.setElement(
            column.getChoiceId(), row.getChoiceId(),
            increasePair(
                column, row,
                result.getElement(column.getChoiceId(), row.getChoiceId())
            )
        );
  }

  private Pair increasePair(
      final RankedChoice column, final RankedChoice row, final Pair element
  ) {

    final int winIncrement = column.getRank() < row.getRank() ? 1 : 0;
    final int loseIncrement = column.getRank() > row.getRank() ? 1 : 0;
    final Pair pair = Optional.ofNullable(element).orElse(getZeroPair());
    return new Pair(
        pair.getWinning() + winIncrement,
        pair.getLosing() + loseIncrement
    );
  }

  private Set<String> collectChoices(final List<CastVote> castVotes) {
    return castVotes.stream()
        .map(CastVote::getPreferences)
        .flatMap(List::stream)
        .map(RankedChoice::getChoiceId)
        .collect(Collectors.toSet());
  }

}
