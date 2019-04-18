package org.rulez.demokracia.pdengine.testhelpers;

import java.util.List;

import org.rulez.demokracia.pdengine.beattable.BeatTable;
import org.rulez.demokracia.pdengine.beattable.Pair;

public class BeatTableTestHelper{

	public static final String CHOICE1 = "name1";
	public static final String CHOICE2 = "name2";
	public static final String CHOICE3 = "name3";

	public static final Pair PAIR = new Pair(4, 5);

	public static BeatTable createNewBeatTableWithData() {
		BeatTable beatTable = initBeatTable();
		beatTable.setElement(CHOICE1, CHOICE2, PAIR);
		return beatTable;
	}

	private static BeatTable initBeatTable() {
		List<String> list = List.of(CHOICE1, CHOICE2, CHOICE3);
		BeatTable beatTable = new BeatTable(list);
		return beatTable;
	}

	public static BeatTable createNewBeatTableWithComplexData() {
		BeatTable beatTable = initBeatTable();
		beatTable.setElement(CHOICE1, CHOICE2, new Pair(5, 1));
		beatTable.setElement(CHOICE1, CHOICE3, new Pair(4, 2));
		beatTable.setElement(CHOICE2, CHOICE1, new Pair(1, 5));
		beatTable.setElement(CHOICE2, CHOICE3, new Pair(11, 4));
		beatTable.setElement(CHOICE3, CHOICE1, new Pair(2, 4));
		beatTable.setElement(CHOICE3, CHOICE2, new Pair(4, 11));
		beatTable.setElement(CHOICE1, CHOICE1, new Pair(0, 0));
		beatTable.setElement(CHOICE2, CHOICE2, new Pair(0, 0));
		beatTable.setElement(CHOICE3, CHOICE3, new Pair(0, 0));
		return beatTable;
	}

	public static BeatTable createNewBeatTableWithEqualData() {
		BeatTable beatTable = createNewBeatTableWithData();
		beatTable.setElement(CHOICE1, CHOICE2, new Pair(2, 2));
		beatTable.setElement(CHOICE2, CHOICE1, new Pair(2, 2));
		beatTable.setElement(CHOICE1, CHOICE3, new Pair(4, 1));
		beatTable.setElement(CHOICE3, CHOICE1, new Pair(4, 4));
		beatTable.setElement(CHOICE2, CHOICE3, new Pair(4, 1));
		beatTable.setElement(CHOICE3, CHOICE2, new Pair(4, 4));
		return beatTable;
	}
}
