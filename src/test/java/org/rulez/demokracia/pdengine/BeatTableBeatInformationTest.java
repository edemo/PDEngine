package org.rulez.demokracia.pdengine;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.rulez.demokracia.pdengine.BeatTable.Direction;
import org.rulez.demokracia.pdengine.annotations.tested_behaviour;
import org.rulez.demokracia.pdengine.annotations.tested_feature;
import org.rulez.demokracia.pdengine.annotations.tested_operation;
import org.rulez.demokracia.pdengine.exception.ReportedException;
import org.rulez.demokracia.pdengine.testhelpers.CreatedDefaultChoice;

public class BeatTableBeatInformationTest extends CreatedDefaultChoice {

	@Before
	public void setUp() throws ReportedException {
		super.setUp();
	}

	@tested_feature("Supporting functionality")
	@tested_operation("BeatTable")
	@tested_behaviour("the beat information related to a and b can be obtained for forward and backward")
	@Test
	public void beatTable_with_not_defined_inputs() {
		BeatTable beatTable = new BeatTable();

		assertThrows(() -> beatTable.beatInformation(null, null, null))
				.assertException(NullPointerException.class);
	}
	
	@tested_feature("Supporting functionality")
	@tested_operation("BeatTable")
	@tested_behaviour("the beat information related to a and b can be obtained for forward and backward")
	@Test
	public void beatTable_forward_with_not_defined_choices() {
		BeatTable beatTable = new BeatTable();
		
		assertThrows(() -> beatTable.beatInformation(null, null, Direction.DIRECTION_FORWARD)
				).assertException(IllegalArgumentException.class)
				 .assertMessageIs("Not existing 1st level key");
	}
	
	@tested_feature("Supporting functionality")
	@tested_operation("BeatTable")
	@tested_behaviour("the beat information related to a and b can be obtained for forward and backward")
	@Test
	public void beatTable_forward_with_not_proper_matrix() {
		Choice choice1 = new Choice("name", "userName");
		Choice choice2 = null;
		
		BeatTable beatTable = new BeatTable();
		
		beatTable.table.put(choice1, new HashMap<Choice, Integer>());
		
		assertThrows(() -> beatTable.beatInformation(choice1, choice2, Direction.DIRECTION_FORWARD)
				).assertException(IllegalArgumentException.class)
				 .assertMessageIs("Not existing 1st level key");
	}
	
	@tested_feature("Supporting functionality")
	@tested_operation("BeatTable")
	@tested_behaviour("the beat information related to a and b can be obtained for forward and backward")
	@Test
	public void beatTable_forward() {
		Choice choice1 = new Choice("name1", "userName1");
		Choice choice2 = new Choice("name2", "userName2");
		int expectedValue = 1;
		
		HashMap<Choice, Integer> value = new HashMap<Choice, Integer>();
		value.put(choice2, 1);
		
		BeatTable beatTable = new BeatTable();
		beatTable.table.put(choice1, value);
		
		int actualValue = beatTable.beatInformation(choice1, choice2, Direction.DIRECTION_FORWARD);
		
		assertEquals(expectedValue, actualValue);
	}
	
	@tested_feature("Supporting functionality")
	@tested_operation("BeatTable")
	@tested_behaviour("the beat information related to a and b can be obtained for forward and backward")
	@Test
	public void beatTable_backward_with_not_defined_choices() {
		BeatTable beatTable = new BeatTable();
		
		assertThrows(() -> beatTable.beatInformation(null, null, Direction.DIRECTION_BACKWARD)
				).assertException(IllegalArgumentException.class)
				 .assertMessageIs("Not existing 1st level key");
	}
	
	@tested_feature("Supporting functionality")
	@tested_operation("BeatTable")
	@tested_behaviour("the beat information related to a and b can be obtained for forward and backward")
	@Test
	public void beatTable_backward_with_not_proper_matrix() {
		Choice choice2 = new Choice("name", "userName");
		Choice choice1 = null;
		
		BeatTable beatTable = new BeatTable();
		
		beatTable.table.put(choice2, new HashMap<Choice, Integer>());
		
		assertThrows(() -> beatTable.beatInformation(choice1, choice2, Direction.DIRECTION_BACKWARD)
				).assertException(IllegalArgumentException.class)
				 .assertMessageIs("Not existing 1st level key");
	}
	
	@tested_feature("Supporting functionality")
	@tested_operation("BeatTable")
	@tested_behaviour("the beat information related to a and b can be obtained for forward and backward")
	@Test
	public void beatTable_backward() {
		Choice choice2 = new Choice("name1", "userName1");
		Choice choice1 = new Choice("name2", "userName2");
		int expectedValue = 1;
		
		HashMap<Choice, Integer> value = new HashMap<Choice, Integer>();
		value.put(choice1, 1);
		
		BeatTable beatTable = new BeatTable();
		beatTable.table.put(choice2, value);
		
		int actualValue = beatTable.beatInformation(choice1, choice2, Direction.DIRECTION_BACKWARD);
		
		assertEquals(expectedValue, actualValue);
	}
}
