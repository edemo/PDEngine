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

public class BeatTableGetPairTest extends CreatedDefaultChoice {

	@Before
	public void setUp() throws ReportedException {
		super.setUp();
	}

	@TestedFeature("Supporting functionality")
	@TestedOperation("BeatTable")
	@TestedBehaviour("the pair related to a and b can be obtained")
	@Test
	public void getPair_with_not_defined_inputs() {
		BeatTable beatTable = new BeatTable();

		assertThrows(() -> beatTable.getPair(null, null)
				).assertMessageIs("Choice is null");
	}
	
	@TestedFeature("Supporting functionality")
	@TestedOperation("BeatTable")
	@TestedBehaviour("the pair related to a and b can be obtained")
	@Test
	public void getPair_with_empty_matrix() {
		BeatTable beatTable = new BeatTable();
		Choice choice1 = new Choice("name1", "userName1");
		Choice choice2 = new Choice("name2", "userName2");
		
		assertThrows(() -> beatTable.getPair(choice1, choice2)
				).assertMessageIs("Not existing 2nd level key");
	}
	
	@TestedFeature("Supporting functionality")
	@TestedOperation("BeatTable")
	@TestedBehaviour("the pair related to a and b can be obtained")
	@Test
	public void getPair_with_not_proper_matrix() {
		BeatTable beatTable = new BeatTable();
		Choice choice1 = new Choice("name1", "userName1");
		Choice choice2 = new Choice("name2", "userName2");
		
		beatTable.table.put(choice1, new HashMap<Choice, Integer>());
		
		assertThrows(() -> beatTable.getPair(choice1, choice2)
				).assertMessageIs("Not existing 1st level value");
	}
	
	
	@TestedFeature("Supporting functionality")
	@TestedOperation("BeatTable")
	@TestedBehaviour("the pair related to a and b can be obtained")
	@Test
	public void getPair_simple_pass() throws IsNullException {
		BeatTable beatTable = new BeatTable();
		Choice choice1 = new Choice("name1", "userName1");
		Choice choice2 = new Choice("name2", "userName2");
		
		HashMap<Choice, Integer> value12 = new HashMap<Choice, Integer>();
		value12.put(choice2, 14);
		HashMap<Choice, Integer> value21 = new HashMap<Choice, Integer>();
		value21.put(choice1, 12);
		
		beatTable.table.put(choice1, value12);
		beatTable.table.put(choice2, value21);
		
		Pair pair = beatTable.getPair(choice1, choice2);
		
		assertTrue(pair.key1 == 14 && pair.key2 == 12);
		
	}
	
	
	@TestedFeature("Supporting functionality")
	@TestedOperation("BeatTable")
	@TestedBehaviour("the pair related to a and b can be obtained")
	@Test
	public void getPair_complex_pass() throws IsNullException {
		BeatTable beatTable = new BeatTable();
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
		
		Pair pair = beatTable.getPair(choice2, choice3);
		
		assertTrue(pair.key1 == 11 && pair.key2 == 10);
		
	}
}
