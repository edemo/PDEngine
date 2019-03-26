package org.rulez.demokracia.pdengine;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.rulez.demokracia.pdengine.annotations.TestedBehaviour;
import org.rulez.demokracia.pdengine.annotations.TestedFeature;
import org.rulez.demokracia.pdengine.annotations.TestedOperation;
import org.rulez.demokracia.pdengine.dataobjects.Pair;
import org.rulez.demokracia.pdengine.exception.ReportedException;
import org.rulez.demokracia.pdengine.testhelpers.CreatedDefaultChoice;

@TestedFeature("Supporting functionality")
@TestedOperation("BeatTable")
@TestedBehaviour("the pair related to a and b can be obtained")
public class BeatTableGetPairTest extends CreatedDefaultChoice {

	BeatTable beatTable;
	Choice choice1, choice2, choice3;
	Pair pair;
	ArrayList<Choice> list;
	
	@Before
	public void setUp() throws ReportedException {
		super.setUp();
	}

	@Test
	public void getPair_throws_an_exception_when_inputs_are_not_defined() {
		beatTable = new BeatTable();

		assertThrows(() -> beatTable.getPair(null, null)
				).assertMessageIs("Invalid row key");
	}
	
	@Test
	public void getPair_gives_back_the_pair_related_to_choice1_and_choice2() {
		list = new ArrayList<Choice>();
		choice1 = new Choice("name1", "userName1");
		choice2 = new Choice("name2", "userName2");
		choice3 = new Choice("name3", "userName3");
		
		list.add(choice1);
		list.add(choice2);
		list.add(choice3);
		
		beatTable = new BeatTable(list);
		beatTable.matrix.setElement(choice1, choice2, new Pair(14, 0));
		beatTable.matrix.setElement(choice1, choice3, new Pair(13, 1));
		beatTable.matrix.setElement(choice2, choice1, new Pair(12, 2));
		beatTable.matrix.setElement(choice2, choice3, new Pair(0, 0));
		beatTable.matrix.setElement(choice3, choice2, new Pair(10, 4));
		beatTable.matrix.setElement(choice2, choice3, new Pair(11, 3));
		
		Pair pair = beatTable.getPair(choice2, choice3);
		
		assertTrue(pair.winning == 11 && pair.losing == 3);
		
	}
}
