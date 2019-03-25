package org.rulez.demokracia.pdengine;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.ElementCollection;

import org.rulez.demokracia.pdengine.dataobjects.Pair;

import org.rulez.demokracia.types.MapMatrix;

public class BeatTable {
	@ElementCollection
	public MapMatrix<Choice, Pair> matrix;

	public enum Direction {
		DIRECTION_FORWARD, DIRECTION_BACKWARD
	}

	public BeatTable() {
		this(new ArrayList<Choice>());
	}

	public BeatTable(final Collection<Choice> keyCollection) {
		matrix = new MapMatrix<>(keyCollection);
	}

	public int beatInformation(final Choice choice1, final Choice choice2, final Direction direction) {
		if (direction == null) 
			throw new IllegalArgumentException("Invalid direction");
		
		int result = 0;
		Pair pair = getPair(choice1, choice2);

		if (direction.equals(Direction.DIRECTION_FORWARD))
			result = pair.winning;
		else
			result = pair.losing;

		return result;
	}

	public Pair getPair(final Choice choice1, final Choice choice2) {
		return matrix.getElement(choice1, choice2);
	}

	public void setPair(final Choice choice1, final Choice choice2, final Pair pair) {
		if (pair == null)
			throw new IllegalArgumentException("Invalid Pair key");

		matrix.setElement(choice1, choice2, pair);
	}
	
	public Pair compareBeats(Pair beat1, Pair beat2){
		if (beat1 == null || beat2 == null)
			throw new IllegalArgumentException("Invalid Pair key");
		
		if(beat1.winning > beat2.winning)
			return beat1;
		else if (beat1.winning == beat2.winning)
			if(beat1.losing < beat2.losing)
				return beat1;
			else if(beat1.losing == beat2.losing) 
				throw new IllegalArgumentException("Can not decide");
			else
				return beat2;
		else
			return beat2;
		
	}
}
