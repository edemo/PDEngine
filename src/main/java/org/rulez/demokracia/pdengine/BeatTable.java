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

	public BeatTable(Collection<Choice> keyCollection) {
		matrix = new MapMatrix<Choice, Pair>(keyCollection);
	}

	public int beatInformation(Choice choice1, Choice choice2, Direction direction) {
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

	public Pair getPair(Choice choice1, Choice choice2) {
		return matrix.getElement(choice1, choice2);
	}

	public void setPair(Choice choice1, Choice choice2, Pair pair) {
		if (pair == null)
			throw new IllegalArgumentException("Invalid Pair key");

		matrix.setElement(choice1, choice2, pair);
	}
}
