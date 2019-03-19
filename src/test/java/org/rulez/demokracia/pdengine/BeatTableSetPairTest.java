package org.rulez.demokracia.pdengine;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.rulez.demokracia.pdengine.BeatTable.Direction;
import org.rulez.demokracia.pdengine.annotations.TestedBehaviour;
import org.rulez.demokracia.pdengine.annotations.TestedFeature;
import org.rulez.demokracia.pdengine.annotations.TestedOperation;
import org.rulez.demokracia.pdengine.dataobjects.Pair;
import org.rulez.demokracia.pdengine.exception.IsNullException;
import org.rulez.demokracia.pdengine.exception.ReportedException;
import org.rulez.demokracia.pdengine.testhelpers.CreatedDefaultChoice;

public class BeatTableSetPairTest extends CreatedDefaultChoice {

	@Before
	public void setUp() throws ReportedException {
		super.setUp();
	}

	@TestedFeature("Supporting functionality")
	@TestedOperation("BeatTable")
	@TestedBehaviour("the pair related to a and b can be set")
	@Test
	public void setPair_with_not_defined_inputs() {
		BeatTable beatTable = new BeatTable();

		assertThrows(() -> beatTable.setPair(null, null, null)
				).assertMessageIs("Pair is null");
	}
	
	@TestedFeature("Supporting functionality")
	@TestedOperation("BeatTable")
	@TestedBehaviour("the pair related to a and b can be set")
	@Test
	public void setPair_with_not_defined_choices() {
		BeatTable beatTable = new BeatTable();
		Pair pair = new Pair(1, 2);
		
		assertThrows(() -> beatTable.setPair(null, null, pair)
				).assertMessageIs("Illegal choice");
	}
	
	@TestedFeature("Supporting functionality")
	@TestedOperation("BeatTable")
	@TestedBehaviour("the pair related to a and b can be set")
	@Test
	public void setPair_with_not_proper_matrix_so_it_inserts_the_new_values() throws IsNullException {
		BeatTable beatTable = new BeatTable();
		Pair pair = new Pair(1, 2);
		Choice choice1 = new Choice("name1", "userName1");
		Choice choice2 = new Choice("name2", "userName2");
		
		beatTable.setPair(choice1, choice2, pair);
		
		int value1 = beatTable.table.get(choice1).get(choice2);
		int value2 = beatTable.table.get(choice2).get(choice1);
		
		assertTrue(value1 == 1 && value2 == 2);
		
	}
	
	@TestedFeature("Supporting functionality")
	@TestedOperation("BeatTable")
	@TestedBehaviour("the pair related to a and b can be set")
	@Test
	public void setPair_with_proper_matrix_so_it_inserts_and_modify_the_values() throws IsNullException {
		BeatTable beatTable = new BeatTable();
		Pair pair = new Pair(1, 2);
		Choice choice1 = new Choice("name1", "userName1");
		Choice choice2 = new Choice("name2", "userName2");
		
		HashMap<Choice, Integer> value = new HashMap<Choice, Integer>();
		value.put(choice2, 13);
		
		beatTable.table.put(choice1, value);
		
		beatTable.setPair(choice1, choice2, pair);
		
		int value1 = beatTable.table.get(choice1).get(choice2);
		int value2 = beatTable.table.get(choice2).get(choice1);
		
		assertTrue(value1 == 1 && value2 == 2);
		
	}
	
	
	@TestedFeature("Supporting functionality")
	@TestedOperation("BeatTable")
	@TestedBehaviour("the pair related to a and b can be set")
	@Test
	public void setPair_with_proper_matrix_so_it_modify_the_existing_values() throws IsNullException {
		BeatTable beatTable = new BeatTable();
		Pair pair = new Pair(1, 2);
		Choice choice1 = new Choice("name1", "userName1");
		Choice choice2 = new Choice("name2", "userName2");
		Choice choice3 = new Choice("name3", "userName3");
		
		HashMap<Choice, Integer> value12 = new HashMap<Choice, Integer>();
		value12.put(choice2, 14);
		HashMap<Choice, Integer> value13 = new HashMap<Choice, Integer>();
		value13.put(choice3, 13);
		HashMap<Choice, Integer> value21 = new HashMap<Choice, Integer>();
		value21.put(choice1, 12);
		HashMap<Choice, Integer> value23 = new HashMap<Choice, Integer>();
		value23.put(choice3, 11);
		HashMap<Choice, Integer> value32 = new HashMap<Choice, Integer>();
		value32.put(choice2, 10);
		
		beatTable.table.put(choice1, value12);
		beatTable.table.put(choice1, value13);
		beatTable.table.put(choice2, value21);
		beatTable.table.put(choice2, value23);
		beatTable.table.put(choice3, value32);
		
		beatTable.setPair(choice1, choice3, pair);
		
		int value1 = beatTable.table.get(choice1).get(choice3);
		int value2 = beatTable.table.get(choice3).get(choice1);
		
		assertTrue(value1 == 1 && value2 == 2);
		
	}
}
