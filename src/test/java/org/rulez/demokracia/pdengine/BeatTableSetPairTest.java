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
@TestedBehaviour("the pair related to a and b can be set")
public class BeatTableSetPairTest extends CreatedDefaultChoice {

	@Before
	public void setUp() throws ReportedException {
		super.setUp();
	}

	@Test
	public void setPair_throws_an_exception_when_inputs_are_not_defined() {
		BeatTable beatTable = new BeatTable();

		assertThrows(() -> beatTable.setPair(null, null, null)
				).assertMessageIs("Invalid Pair key");
	}
	
	@Test
	public void setPair_inserts_the_pair_when_there_is_no_pair_in_the_matrix() {
		ArrayList<Choice> list = new ArrayList<Choice>();
		Pair pair = new Pair(1, 2);
		Choice choice1 = new Choice("name1", "userName1");
		Choice choice2 = new Choice("name2", "userName2");
		
		list.add(choice1);
		list.add(choice2);
		
		BeatTable beatTable = new BeatTable(list);
		
		beatTable.setPair(choice1, choice2, pair);
		
		Pair result = beatTable.matrix.getElement(choice1, choice2);
		
		assertTrue(result.winning == pair.winning && result.losing == pair.losing);	
	}
	
	@Test
	public void setPair_updates_the_pair_when_there_are_pairs_in_the_matrix() {
		ArrayList<Choice> list = new ArrayList<Choice>();
		Pair pair = new Pair(1, 2);
		Choice choice1 = new Choice("name1", "userName1");
		Choice choice2 = new Choice("name2", "userName2");
		Choice choice3 = new Choice("name3", "userName3");
		
		list.add(choice1);
		list.add(choice2);
		list.add(choice3);
		
		BeatTable beatTable = new BeatTable(list);
		
		beatTable.setPair(choice1, choice2, new Pair(14, 1));
		beatTable.setPair(choice1, choice3, new Pair(13, 2));
		beatTable.setPair(choice2, choice1, new Pair(12, 3));
		beatTable.setPair(choice2, choice3, new Pair(11, 4));
		beatTable.setPair(choice1, choice2, new Pair(pair.winning, pair.losing));
		
		Pair result = beatTable.matrix.getElement(choice1, choice2);
		
		assertTrue(result.winning == pair.winning && result.losing == pair.losing);
	}
}
