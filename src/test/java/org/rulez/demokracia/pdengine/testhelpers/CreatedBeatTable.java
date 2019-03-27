package org.rulez.demokracia.pdengine.testhelpers;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.rulez.demokracia.pdengine.BeatTable;
import org.rulez.demokracia.pdengine.Choice;
import org.rulez.demokracia.pdengine.dataobjects.Pair;
import org.rulez.demokracia.testhelpers.ThrowableTester;

public class CreatedBeatTable extends ThrowableTester{
	protected BeatTable beatTable;
	protected List<Choice> list;
	
	protected Pair pair;
	protected Pair result;
	
	protected Choice choice1;
	protected Choice choice2;
	protected Choice choice3;
	
	protected Pair beats1;
	protected Pair beats2;
	protected Pair beats3;
	protected Pair beats4;
	protected Pair beats5;
	
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
		createNewBeatTableWithData();
		beatTable.setPair(choice1, choice2, new Pair(14, 1));
		beatTable.setPair(choice1, choice3, new Pair(13, 2));
		beatTable.setPair(choice2, choice1, new Pair(12, 3));
		beatTable.setPair(choice2, choice3, new Pair(11, 4));
		beatTable.setPair(choice1, choice2, pair);
	}
	
	protected void setGetElementAsResult(final Choice first, final Choice second) {
		result = beatTable.getElement(first, second);	
	}
	
	protected void setCompareBeatsAsResult(final Pair first, final Pair second) {
		result = beatTable.compareBeats(first, second);
	}
}
