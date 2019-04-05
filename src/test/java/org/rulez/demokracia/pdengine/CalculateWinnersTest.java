package org.rulez.demokracia.pdengine;

import static org.junit.Assert.*;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.rulez.demokracia.pdengine.annotations.TestedBehaviour;
import org.rulez.demokracia.pdengine.annotations.TestedFeature;
import org.rulez.demokracia.pdengine.annotations.TestedOperation;
import org.rulez.demokracia.pdengine.dataobjects.Pair;
import org.rulez.demokracia.pdengine.testhelpers.CreatedComputedVote;

@TestedFeature("Vote")
@TestedOperation("calculate winners")
public class CalculateWinnersTest extends CreatedComputedVote {

	private WinnerCalculator winnerCalculator;
	private BeatTable beatPathTable;

	@Override
	@Before
	public void setUp() {
		super.setUp();
		winnerCalculator = new WinnerCalculatorImpl();
		beatPathTable = computedVote.getBeatPathTable();
	}

	@TestedBehaviour("only choices not in ignoredChoices are considered")
	@Test
	public void calculate_winners_returns_none_of_the_ignored_choices() {
		Collection<String> ignoredChoices = Arrays.asList("A");
		List<String> winners = winnerCalculator
				.calculateWinners(beatPathTable, ignoredChoices);
		assertFalse(winners.contains("A"));

	}

	@TestedBehaviour("only choices not in ignoredChoices are considered")
	@Test
	public void calculate_winners_returns_not_ignored_winner() {
		List<String> winners = winnerCalculator.calculateWinners(beatPathTable, Arrays.asList("C"));
		assertTrue(winners.contains("A"));
	}

	@TestedBehaviour("all non-beaten candidates are winners")
	@Test
	public void calculate_winners_doesnt_return_beaten_candidates() {
		assertNoWinnerIsBeaten(winnerCalculator.calculateWinners(beatPathTable, new HashSet<>()));
	}

	@TestedBehaviour("all non-beaten candidates are winners")
	@Test
	public void calculate_winners_return_all_non_beaten_candidates() {
		assertNonbeatensAreWinner(winnerCalculator.calculateWinners(beatPathTable, new HashSet<>()));
	}

	private void assertNoWinnerIsBeaten(final List<String> winners) {
		assertTrue(winners.stream().allMatch(choice -> isAWinner(choice, beatPathTable)));
	}

	private void assertNonbeatensAreWinner(final List<String> winners) {
		assertTrue(beatPathTable.getKeyCollection()
				.stream()
				.filter(choice -> isAWinner(choice, beatPathTable))
				.allMatch(choice -> winners.contains(choice)));
	}

	private boolean isAWinner(final String player1, final BeatTable beatPathTable) {
		for (String player2 : beatPathTable.getKeyCollection()) {
			Pair forward = beatPathTable.getElement(player1, player2);
			Pair backward = beatPathTable.getElement(player2, player1);
			if (!forward.equals(beatPathTable.compareBeats(forward, backward)))
				return false;
		}
		return true;
	}
}
