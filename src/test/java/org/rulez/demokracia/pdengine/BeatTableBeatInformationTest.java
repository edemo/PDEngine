package org.rulez.demokracia.pdengine;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.rulez.demokracia.pdengine.BeatTable.Direction;
import org.rulez.demokracia.pdengine.annotations.TestedBehaviour;
import org.rulez.demokracia.pdengine.annotations.TestedFeature;
import org.rulez.demokracia.pdengine.annotations.TestedOperation;
import org.rulez.demokracia.pdengine.dataobjects.Pair;
import org.rulez.demokracia.pdengine.exception.ReportedException;
import org.rulez.demokracia.pdengine.testhelpers.CreatedDefaultChoice;

@TestedFeature("Supporting functionality")
@TestedOperation("BeatTable")
@TestedBehaviour("the beat information related to a and b can be obtained for forward and backward")
public class BeatTableBeatInformationTest extends CreatedDefaultChoice {

	@Before
	public void setUp() throws ReportedException {
		super.setUp();
	}

	@Test
	public void beatInformation_throws_an_exception_when_direction_is_not_defined() {
		BeatTable beatTable = new BeatTable();

		assertThrows(() -> beatTable.beatInformation(null, null, null)
				).assertMessageIs("Invalid direction");
	}
	
	@Test
	public void beatInformation_throws_an_exception_when_the_chocies_are_not_defined() {
		BeatTable beatTable = new BeatTable();
		
		assertThrows(() -> beatTable.beatInformation(null, null, Direction.DIRECTION_FORWARD)
				).assertMessageIs("Invalid row key");
	}
	
	@Test
	public void beatInformation_throws_an_exception_when_the_choices_are_not_belong_to_the_matrix() {
		Choice choice1 = new Choice("name", "userName");
		Choice choice2 = null;
		
		BeatTable beatTable = new BeatTable();
		
		assertThrows(() -> beatTable.beatInformation(choice1, choice2, Direction.DIRECTION_FORWARD)
				).assertMessageIs("Invalid row key");
	}
	
	@Test
	public void beatInformation_gives_back_the_number_of_winnings_from_choice1_to_choice2() {
		ArrayList<Choice> list = new ArrayList<Choice>();
		Pair pair = new Pair(1, 2);
		Choice choice1 = new Choice("name1", "userName1");
		Choice choice2 = new Choice("name2", "userName2");
		
		list.add(choice1);
		list.add(choice2);
		
		BeatTable beatTable = new BeatTable(list);
		
		beatTable.setPair(choice1, choice2, pair);
		
		assertEquals(pair.winning, beatTable.beatInformation(choice1, choice2, Direction.DIRECTION_FORWARD));
	}
	
	@Test
	public void beatInformation_gives_back_the_number_of_losing_from_choice1_to_choice2() {
		ArrayList<Choice> list = new ArrayList<Choice>();
		Pair pair = new Pair(1, 2);
		Choice choice1 = new Choice("name1", "userName1");
		Choice choice2 = new Choice("name2", "userName2");
		
		list.add(choice1);
		list.add(choice2);
		
		BeatTable beatTable = new BeatTable(list);
		
		beatTable.setPair(choice1, choice2, pair);
		
		assertEquals(pair.losing, beatTable.beatInformation(choice1, choice2, Direction.DIRECTION_BACKWARD));
	}
}
