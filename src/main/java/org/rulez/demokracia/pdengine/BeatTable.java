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
		table = new HashMap<Choice, HashMap<Choice, Integer>>();
	}

	public int beatInformation(Choice choice1, Choice choice2, Direction direction) {
		// Check: enum's possible values
		int result = 0;

		switch (direction) {
		case DIRECTION_FORWARD:
			result = getBeatValue(choice1, choice2);
			break;
		default:
			result = getBeatValue(choice2, choice1);
		}

		return result;
	}

	public Pair getPair(Choice choice1, Choice choice2) {
		int first = getBeatValue(choice1, choice2);
		int second = getBeatValue(choice2, choice1);

		return new Pair(first, second);
	}

	public void setPair(Choice choice1, Choice choice2, Pair pair) {
		if (pair == null)
			throw new IllegalArgumentException("Illegal pair");

		HashMap<Choice, Integer> key1 = getOrSetBeatKey(choice1);
		HashMap<Choice, Integer> key2 = getOrSetBeatKey(choice2);

		key1.put(choice2, pair.key1);
		key2.put(choice1, pair.key2);
	}

	private HashMap<Choice, Integer> getOrSetBeatKey(Choice choice1) {
		if (choice1 == null)
			throw new IllegalArgumentException("Illegal choice");
		HashMap<Choice, Integer> result = table.get(choice1);

		if (result == null) {
			HashMap<Choice, Integer> key = new HashMap<Choice, Integer>();
			table.put(choice1, key);
		}

		return getBeatKey(choice1);
	}

	private HashMap<Choice, Integer> getBeatKey(Choice choice1) {
		if (table.get(choice1) != null)
			return table.get(choice1);
		throw new IllegalArgumentException("Not existing 2nd level key");
	}

	private int getBeatValue(Choice choice1, Choice choice2) {
		if (choice1 == null || choice2 == null)
			throw new IllegalArgumentException("Not existing 1st level key");
		HashMap<Choice, Integer> key = getBeatKey(choice1);
		if (key.get(choice2) != null)
			return key.get(choice2);
		throw new IllegalArgumentException("Not existing 1st level value");
	}

}
