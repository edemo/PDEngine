package org.rulez.demokracia.pdengine.testhelpers;

import static org.junit.Assert.assertTrue;

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
	
	protected Pair beats1, beats2, beats3, beats4, beats5;
	
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
		
		beats1 = new Pair(150, 22);
		beats2 = new Pair(100, 40);
		beats3 = new Pair(150, 40);
		beats4 = new Pair(100, 22);
		beats5 = new Pair(150, 10);
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
	
	protected void getAndAssertResult(Choice first, Choice second) {
		result = beatTable.getElement(first, second);
		assertTrue(result.winning == pair.winning && result.losing == pair.losing);	
	}
	
	protected void setResult(Pair first, Pair second) {
		result = beatTable.compareBeats(first, second);
	}
}
