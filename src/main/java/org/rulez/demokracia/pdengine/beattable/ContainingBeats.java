package org.rulez.demokracia.pdengine.beattable;

import org.rulez.demokracia.pdengine.beattable.BeatTable.Direction;
import org.rulez.demokracia.pdengine.exception.ReportedException;

public interface ContainingBeats extends Matrix<String, Pair> {

	long serialVersionUID = 1L;

	default void checkPair(final Pair pair) {
		if (pair == null)
			throw new ReportedException("Invalid Pair key");
	}


	default int beatInformation(final String choice1, final String choice2, final Direction direction) {
		if (direction == null)
			throw new ReportedException("Invalid direction");

		Pair pair = getElement(choice1, choice2);

		return direction.equals(Direction.DIRECTION_FORWARD) ? pair.getWinning() : pair.getLosing();
	}

	default Pair compareBeats(final Pair beat1, final Pair beat2) {
		checkPair(beat1);
		checkPair(beat2);

		return beat1.compareTo(beat2) >= 0 ? beat1 : beat2;
	}
}
