package org.rulez.demokracia.pdengine.testhelpers;

import java.util.ArrayList;

import org.junit.Before;
import org.rulez.demokracia.pdengine.BeatTable;
import org.rulez.demokracia.pdengine.Choice;
import org.rulez.demokracia.pdengine.dataobjects.Pair;
import org.rulez.demokracia.testhelpers.ThrowableTester;

public class CreatedBeatTable extends ThrowableTester{
	protected BeatTable beatTable;
	protected Choice choice1, choice2, choice3;
	protected Pair pair, result;
	protected ArrayList<Choice> list;

	@Before
	public void setUp() {
		beatTable = new BeatTable();
		list = new ArrayList<Choice>();
		pair = new Pair(4, 5);
		choice1 = new Choice("name1", "userName1");
		choice2 = new Choice("name2", "userName2");
		choice3 = new Choice("name3", "userName3");
		list.add(choice1);
		list.add(choice2);
		list.add(choice3);
	}
	
	protected void createNewBeatTableWithData() {
		beatTable = new BeatTable(list);
		beatTable.setPair(choice1, choice2, pair);
	}
	
	protected void createNewBeatTableWithComplexData() {	
		beatTable = new BeatTable(list);
		beatTable.setPair(choice1, choice2, new Pair(14, 1));
		beatTable.setPair(choice1, choice3, new Pair(13, 2));
		beatTable.setPair(choice2, choice1, new Pair(12, 3));
		beatTable.setPair(choice2, choice3, new Pair(11, 4));
		beatTable.setPair(choice1, choice2, new Pair(pair.winning, pair.losing));
	}
	
	protected void compareBeats(Pair firstBeats, Pair secondBeats) {
		result = beatTable.compareBeats(firstBeats, secondBeats);	
	}
}
