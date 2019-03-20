package org.rulez.demokracia.pdengine;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.ElementCollection;

import org.rulez.demokracia.pdengine.dataobjects.Pair;

public class BeatTable {
	@ElementCollection
	public Map<Choice, HashMap<Choice, Integer>> table;

	public enum Direction {
		DIRECTION_FORWARD, DIRECTION_BACKWARD
	}

	public BeatTable() {
		table = new HashMap<>();
	}

	public int beatInformation(Choice choiceF, Choice choiceS, Direction direction) {
		// Check: enum's possible values
		int result = 0;

		if(direction.equals(Direction.DIRECTION_FORWARD))
			result = getBeatValue(choiceF, choiceS);
		else
			result = getBeatValue(choiceS, choiceF);

		return result;
	}

	public Pair getPair(Choice choiceF, Choice choiceS) {
		int first = getBeatValue(choiceF, choiceS);
		int second = getBeatValue(choiceS, choiceF);

		return new Pair(first, second);
	}

	public void setPair(Choice choiceF, Choice choiceS, Pair pair) {
		if (pair == null)
			throw new IllegalArgumentException("Illegal pair");

		HashMap<Choice, Integer> key1 = getOrSetBeatKey(choiceF);
		HashMap<Choice, Integer> key2 = getOrSetBeatKey(choiceS);

		key1.put(choiceS, pair.key1);
		key2.put(choiceF, pair.key2);
	}

	private HashMap<Choice, Integer> getOrSetBeatKey(Choice choice) {
		if (choice == null)
			throw new IllegalArgumentException("Illegal choice");
		HashMap<Choice, Integer> result = table.get(choice);

		if (result == null) {
			HashMap<Choice, Integer> key = new HashMap<>();
			table.put(choice, key);
		}

		return getBeatKey(choice);
	}

	private HashMap<Choice, Integer> getBeatKey(Choice choice) {
		if (table.get(choice) != null)
			return table.get(choice);
		throw new IllegalArgumentException("Not existing 2nd level key");
	}

	private int getBeatValue(Choice choiceF, Choice choiceS) {
		if (choiceF == null || choiceS == null)
			throw new IllegalArgumentException("Not existing 1st level key");
		HashMap<Choice, Integer> key = getBeatKey(choiceF);
		if (key.get(choiceS) != null)
			return key.get(choiceS);
		throw new IllegalArgumentException("Not existing 1st level value");
	}

}
