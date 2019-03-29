package org.rulez.demokracia.pdengine;

import java.util.List;

import org.rulez.demokracia.pdengine.BeatTable.Direction;
import org.rulez.demokracia.pdengine.dataobjects.Pair;
import org.rulez.demokracia.pdengine.exception.ReportedException;
import org.rulez.demokracia.types.Matrix;

public interface ContainingBeats extends Matrix<String, Pair> {

	long serialVersionUID = 1L;

	default void checkPair(final Pair pair) {
		if (pair == null)
			throw new ReportedException("Invalid Pair key");
	}

	default int beatInformation(final String choice1, final String choice2, final Direction direction) {
		if (direction == null)
			throw new ReportedException("Invalid direction");

		int result = 0;
		Pair pair = getElement(choice1, choice2);

		if (direction.equals(Direction.DIRECTION_FORWARD))
			result = pair.winning;
		else
			result = pair.losing;

		return result;
	}

	default Pair compareBeats(final Pair beat1, final Pair beat2) {
		checkPair(beat1);
		checkPair(beat2);

		if (beat1.winning > beat2.winning)
			return beat1;
		else if (beat1.winning < beat2.winning)
			return beat2;
		else if (beat1.losing < beat2.losing)
			return beat1;
		else if (beat2.losing < beat1.losing)
			return beat2;
		else
			throw new UnsupportedOperationException();
	}

	default void initialize(List<CastVote> castVotes) {
		if (castVotes == null)
			throw new ReportedException("Invalid castVotes");

		for (int castVoteIndex = 0; castVoteIndex < castVotes.size(); castVoteIndex++) {
			CastVote castVote = castVotes.get(castVoteIndex);

			List<RankedChoice> preferences = castVote.getPreferences();

			for (int columnIndex = 0; columnIndex < preferences.size() && preferences.get(columnIndex).choiceId != null; columnIndex++) {
				for (int rowIndex = columnIndex + 1; rowIndex < preferences.size(); rowIndex++) {
					if (preferences.get(columnIndex).rank > preferences.get(rowIndex).rank) {
						increasePairValue(preferences, columnIndex, rowIndex);
					} else if(preferences.get(columnIndex).rank < preferences.get(rowIndex).rank){
						increasePairValue(preferences, rowIndex, columnIndex);
					} else {
						throw new ReportedException("Invalid ranks");
					}
				}
			}
		}
	}
	
	
	default Pair getPair(String beats1, String beats2) {
		Pair result = getElement(beats1, beats2);
		if (result == null)
			return new Pair(0, 0);
		return result;
	}

	default void increasePairValue(List<RankedChoice> preferences, int i, int j) {
		Pair value1 = getPair(preferences.get(i).choiceId, preferences.get(j).choiceId);
		setElement(preferences.get(i).choiceId, preferences.get(j).choiceId,
				new Pair(value1.winning + 1, value1.losing));

		Pair value2 = getPair(preferences.get(j).choiceId, preferences.get(i).choiceId);
		setElement(preferences.get(j).choiceId, preferences.get(i).choiceId,
				new Pair(value2.winning, value2.losing + 1));
	}
}
