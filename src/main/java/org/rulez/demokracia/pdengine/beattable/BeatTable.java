package org.rulez.demokracia.pdengine.beattable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.rulez.demokracia.pdengine.votecast.CastVote;

public class BeatTable extends MapMatrix<String, Pair> {

  private static final long serialVersionUID = 1L;

  public BeatTable(final BeatTable beatTable) {
    super(beatTable.getKeyCollection());
    for (final String row : getKeyCollection())
      for (final String col : getKeyCollection()) {
        final Pair sourcePair = beatTable.getElement(col, row);
        if (Objects.nonNull(sourcePair))
          setElement(col, row, createPair(sourcePair));
      }
  }

  @Override
  public Pair getElement(final String columnKey, final String rowKey) {
    return Optional.ofNullable(super.getElement(columnKey, rowKey))
        .orElse(new Pair(0, 0));
  }

  private Pair createPair(final Pair sourcePair) {
    return new Pair(sourcePair.getWinning(), sourcePair.getLosing());
  }

  public enum Direction {
    DIRECTION_FORWARD, DIRECTION_BACKWARD
  }

  public BeatTable() {
    this(new ArrayList<String>());
  }

  public BeatTable(final Collection<String> keyCollection) {
    super(keyCollection);
  }

  public void computeTransitiveClosure() {
    throw new UnsupportedOperationException();

  }

  public void normalize() {
    throw new UnsupportedOperationException();
  }

  public void initialize(final List<CastVote> votesCast) {
    throw new UnsupportedOperationException();

  }
}
