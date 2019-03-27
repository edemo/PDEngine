package org.rulez.demokracia.pdengine;

import org.rulez.demokracia.pdengine.BeatTable.Direction;
import org.rulez.demokracia.pdengine.dataobjects.Pair;
import org.rulez.demokracia.pdengine.exception.ReportedException;
import org.rulez.demokracia.types.Matrix;

public interface ContainingBeats extends Matrix<Choice, Pair>{
	
	long serialVersionUID = 1L;
	default void checkPair(final Pair pair) {
		if (pair == null)
			throw new ReportedException("Invalid Pair key");
	}
	
	default int beatInformation(final Choice choice1, final Choice choice2, final Direction direction) {
		if (direction == null)
			throw new IllegalArgumentException("Invalid direction");
		
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
		else
			if (beat1.losing < beat2.losing)
				return beat1;
			else if (beat2.losing < beat1.losing)
				return beat2;
			else
				throw new IllegalArgumentException("Can not decide");
	}
	
 
}
