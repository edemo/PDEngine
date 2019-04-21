package org.rulez.demokracia.pdengine.beattable;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.rulez.demokracia.pdengine.annotations.TestedBehaviour;
import org.rulez.demokracia.pdengine.annotations.TestedFeature;
import org.rulez.demokracia.pdengine.annotations.TestedOperation;
import org.rulez.demokracia.pdengine.beattable.BeatTable;
import org.rulez.demokracia.pdengine.beattable.BeatTableServiceImpl;
import org.rulez.demokracia.pdengine.beattable.Pair;
import org.rulez.demokracia.pdengine.choice.RankedChoice;
import org.rulez.demokracia.pdengine.testhelpers.ThrowableTester;
import org.rulez.demokracia.pdengine.votecast.CastVote;


@TestedFeature("Schulze method")
@TestedOperation("compute initial beat matrix")
@TestedBehaviour("the beat matrix contains the beats")
@RunWith(MockitoJUnitRunner.class)
public class BeatTableInitializeTest extends ThrowableTester {

	private static final String FIRST = "A";
	private static final String SECOND = "B";
	private static final String THIRD = "C";
	private static final String FOURTH = "D";

	private List<CastVote> castVotes;

	@InjectMocks
	private BeatTableServiceImpl beatTableService;
	private BeatTable beatTable;

	@Before
	public void setUp() {

		List<RankedChoice> preferences = List.of(new RankedChoice(FIRST, 2),
				new RankedChoice(SECOND, 3),
				new RankedChoice(THIRD, 1),
				new RankedChoice(FOURTH, 1));

		castVotes = List.of(new CastVote("user1", preferences),
				new CastVote("user2", preferences),
				new CastVote("user3", List.of()));

		beatTable = beatTableService.initializeBeatTable(castVotes);
	}

	@Test
	public void initialize_throws_exception_when_param_is_not_defined() {
		assertThrows(() -> beatTableService.initializeBeatTable(null)
				).assertMessageIs("Invalid castVotes");
	}

	@Test
	public void initialize_does_not_modify_the_values_when_there_are_two_same_rank() {
		assertEquals(new Pair(0, 0), beatTable.getElement(THIRD, FOURTH));
	}

	@Test
	public void initialize_sets_the_new_winning_value_FORWARD_when_the_matrix_is_empty() {
		assertEquals(new Pair(2, 0), beatTable.getElement(FIRST, SECOND));
	}

	@Test
	public void initialize_sets_the_new_losing_value_FORWARD_when_the_matrix_is_empty() {
		assertEquals(new Pair(0, 2), beatTable.getElement(SECOND, THIRD));
	}

	@Test
	public void initialize_sets_the_new_winning_value_BACKWARD_when_the_matrix_is_empty1() {
		assertEquals(new Pair(2, 0), beatTable.getElement(THIRD, SECOND));
	}

	@Test
	public void initialize_sets_the_new_losing_value_BACKWARD_when_the_matrix_is_empty() {
		assertEquals(new Pair(0, 2), beatTable.getElement(SECOND, FIRST));
	}
}
