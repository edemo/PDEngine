package org.rulez.demokracia.pdengine;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.ElementCollection;

import org.rulez.demokracia.pdengine.dataobjects.Pair;
import org.rulez.demokracia.pdengine.exception.IsNullException;
import org.rulez.demokracia.pdengine.exception.ReportedException;

public class BeatTable {
	@ElementCollection
	public Map<Choice, HashMap<Choice, Integer>> table;

	public enum Direction {
		DIRECTION_FORWARD, DIRECTION_BACKWARD
	}

	public BeatTable() {
		table = new HashMap<>();
	}

	public int beatInformation(Choice choice1, Choice choice2, Direction direction) throws IsNullException {
		if(direction == null)
			throw new IsNullException("Direction");
		
		int result = 0;

		if(direction.equals(Direction.DIRECTION_FORWARD))
			result = getBeatValue(choice1, choice2);
		else
			result = getBeatValue(choice2, choice1);

		return result;
	}

	public Pair getPair(Choice choice1, Choice choice2) throws IsNullException {
		int first = getBeatValue(choice1, choice2);
		int second = getBeatValue(choice2, choice1);

		return new Pair(first, second);
	}

	public void setPair(Choice choice1, Choice choice2, Pair pair) throws IsNullException {
		if (pair == null)
			throw new IsNullException("Pair");

		HashMap<Choice, Integer> key1 = getOrSetBeatKey(choice1);
		HashMap<Choice, Integer> key2 = getOrSetBeatKey(choice2);

		key1.put(choice2, pair.key1);
		key2.put(choice1, pair.key2);
	}

	private HashMap<Choice, Integer> getOrSetBeatKey(Choice choice) {
		if (choice == null)
			throw new IllegalArgumentException("Illegal choice");		
		table.computeIfAbsent(choice, k -> table.put(choice, new HashMap<>()));

		return getBeatKey(choice);
	}

	private HashMap<Choice, Integer> getBeatKey(Choice choice) {
		if (table.get(choice) != null)
			return table.get(choice);
		throw new IllegalArgumentException("Not existing 2nd level key");
	}

	private int getBeatValue(Choice choiceF, Choice choiceS) throws IsNullException {
		if (choiceF == null || choiceS == null)
			throw new IsNullException("Choice");
		HashMap<Choice, Integer> key = getBeatKey(choiceF);
		if (key.get(choiceS) != null)
			return key.get(choiceS);
		throw new IllegalArgumentException("Not existing 1st level value");
	}

}
