package org.rulez.demokracia.pdengine.testhelpers;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.rulez.demokracia.pdengine.BeatTable;
import org.rulez.demokracia.pdengine.dataobjects.Pair;
import org.rulez.demokracia.testhelpers.ThrowableTester;

public class CreatedBeatTable extends ThrowableTester{
	protected BeatTable beatTable;

	protected List<String> list;
	
	protected Pair pair;
	protected Pair result;
	
	protected String choice1;
	protected String choice2;
	protected String choice3;
	
	protected Pair beats1;
	protected Pair beats2;
	protected Pair beats3;
	protected Pair beats4;
	protected Pair beats5;

	@Before
	public void setUp() {
		beatTable = new BeatTable();
		list = new ArrayList<String>();
		pair = new Pair(4, 5);
		choice1 = "name1";
		choice2 = "name2";
		choice3 = "name3";
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
		beatTable.setElement(choice1, choice2, pair);
	}

	protected void createNewBeatTableWithComplexData() {
		createNewBeatTableWithData();
		beatTable.setElement(choice1, choice2, new Pair(5, 1));
		beatTable.setElement(choice1, choice3, new Pair(4, 2));
		beatTable.setElement(choice2, choice1, new Pair(1, 5));
		beatTable.setElement(choice2, choice3, new Pair(11, 4));
		beatTable.setElement(choice3, choice1, new Pair(2, 4));
		beatTable.setElement(choice3, choice2, new Pair(4, 11));
	}
	
	protected void createNewBeatTableWithEqualData() {
		createNewBeatTableWithData();
		beatTable.setElement(choice1, choice2, new Pair(2, 2));
		beatTable.setElement(choice2, choice1, new Pair(2, 2));
		beatTable.setElement(choice1, choice3, new Pair(4, 1));
		beatTable.setElement(choice3, choice1, new Pair(4, 4));
		beatTable.setElement(choice2, choice3, new Pair(4, 1));
		beatTable.setElement(choice3, choice2, new Pair(4, 4));
	}
	
	protected void setGetElementAsResult(final String first, final String second) {
		result = beatTable.getElement(first, second);	
	}

	protected void setCompareBeatsAsResult(final Pair first, final Pair second) {
		result = beatTable.compareBeats(first, second);
	}
}
