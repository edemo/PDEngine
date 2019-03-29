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

	default Pair getPair(String beats1, String beats2) {
		Pair result = getElement(beats1, beats2);
		if (result == null)
			return new Pair(0, 0);
		return result;
	}

	default void initialize(List<CastVote> castVotes) {
		if (castVotes == null)
			throw new ReportedException("Invalid castVotes");

		for (int h = 0; h < castVotes.size(); h++) {
			CastVote castVote = castVotes.get(h);

			List<RankedChoice> preferences = castVote.getPreferences();

			for (int i = 0; i < preferences.size(); i++) {
				for (int j = i + 1; j < preferences.size(); j++) {
					if (preferences.get(i).rank > preferences.get(j).rank) {

						Pair value1 = getPair(preferences.get(i).choiceId, preferences.get(j).choiceId);
						setElement(preferences.get(i).choiceId, preferences.get(j).choiceId,
								new Pair(value1.winning + 1, value1.losing));

						Pair value2 = getPair(preferences.get(j).choiceId, preferences.get(i).choiceId);
						setElement(preferences.get(j).choiceId, preferences.get(i).choiceId,
								new Pair(value2.winning, value2.losing + 1));
					} else if(preferences.get(i).rank < preferences.get(j).rank){

						Pair value1 = getPair(preferences.get(j).choiceId, preferences.get(i).choiceId);
						setElement(preferences.get(j).choiceId, preferences.get(i).choiceId,
								new Pair(value1.winning + 1, value1.losing));

						Pair value2 = getPair(preferences.get(i).choiceId, preferences.get(j).choiceId);
						setElement(preferences.get(i).choiceId, preferences.get(j).choiceId,
								new Pair(value2.winning, value2.losing + 1));
					} else {
						throw new ReportedException("Invalid ranks");
					}
				}
			}
		}
	}
}
