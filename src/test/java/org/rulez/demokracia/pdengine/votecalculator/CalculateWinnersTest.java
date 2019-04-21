package org.rulez.demokracia.pdengine.votecalculator;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.rulez.demokracia.pdengine.annotations.TestedBehaviour;
import org.rulez.demokracia.pdengine.annotations.TestedFeature;
import org.rulez.demokracia.pdengine.annotations.TestedOperation;
import org.rulez.demokracia.pdengine.beattable.BeatTable;
import org.rulez.demokracia.pdengine.beattable.BeatTableIgnoreService;
import org.rulez.demokracia.pdengine.beattable.Pair;
import org.rulez.demokracia.pdengine.testhelpers.BeatTableTestHelper;
import org.rulez.demokracia.pdengine.votecalculator.WinnerCalculatorServiceImpl;

@TestedFeature("Vote")
@TestedOperation("calculate winners")
@RunWith(MockitoJUnitRunner.class)
public class CalculateWinnersTest {

	@InjectMocks
	private WinnerCalculatorServiceImpl winnerCalculator;

	@Mock
	private BeatTableIgnoreService beatTableIgnore;

	private BeatTable beatPathTable;

	@Before
	public void setUp() {
		beatPathTable = BeatTableTestHelper.createTransitiveClosedBeatTable();
		when(beatTableIgnore.ignoreChoices(beatPathTable, List.of("A")))
		.thenReturn(BeatTableTestHelper.createTransitiveClosedBeatTable(List.of("B", "C", "D")));
		when(beatTableIgnore.ignoreChoices(beatPathTable, Set.of())).thenReturn(beatPathTable);
	}

	@TestedBehaviour("only choices not in ignoredChoices are considered")
	@Test
	public void calculate_winners_returns_none_of_the_ignored_choices() {
		Collection<String> ignoredChoices = List.of("A");
		List<String> winners = winnerCalculator
				.calculateWinners(beatPathTable, ignoredChoices);
		assertFalse(winners.contains("A"));

	}

	@TestedBehaviour("only choices not in ignoredChoices are considered")
	@Test
	public void calculate_winners_returns_not_ignored_winner() {
		List<String> winners = winnerCalculator.calculateWinners(beatPathTable, List.of("A"));
		assertTrue(winners.contains("B"));
	}

	@TestedBehaviour("all non-beaten candidates are winners")
	@Test
	public void calculate_winners_doesnt_return_beaten_candidates() {
		assertNoWinnerIsBeaten(winnerCalculator.calculateWinners(beatPathTable, Set.of()));
	}

	@TestedBehaviour("all non-beaten candidates are winners")
	@Test
	public void calculate_winners_return_all_non_beaten_candidates() {
		assertNonbeatensAreWinner(winnerCalculator.calculateWinners(beatPathTable, Set.of()));
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
