package org.rulez.demokracia.pdengine;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.stubbing.Answer;
import org.rulez.demokracia.pdengine.annotations.TestedBehaviour;
import org.rulez.demokracia.pdengine.annotations.TestedFeature;
import org.rulez.demokracia.pdengine.annotations.TestedOperation;
import org.rulez.demokracia.pdengine.testhelpers.CreatedDefaultCastVoteWithRankedChoices;

@TestedFeature("Vote")
@TestedOperation("calculate winners")
public class CalculateWinnersTest extends CreatedDefaultCastVoteWithRankedChoices {

	private WinnerCalculator winnerCalculator;

	private Answer<?> answerKeyCollection = (invocation) -> {
		BeatTable beatTable = (BeatTable) invocation.getArguments()[0];
		return beatTable.getKeyCollection().stream().collect(Collectors.toList());
	};

	private ComputedVote computedVote;

	@Override
	@Before
	public void setUp() {
		super.setUp();
		getTheVote().votesCast = castVote;
		winnerCalculator = mock(WinnerCalculator.class);
		when(winnerCalculator.calculateWinners(ArgumentMatchers.any(BeatTable.class))).thenAnswer(answerKeyCollection);

		computedVote = new ComputedVote(getTheVote());
		computedVote.computeVote();
		computedVote.setWinnerCalculator(winnerCalculator);
	}

	@TestedBehaviour("only choices not in ignoredChoices are considered")
	@Test
	public void calculate_winners_returns_none_of_the_ignored_choices() {
		List<String> winners = computedVote.calculateWinners(Arrays.asList("A"));
		assertFalse(winners.contains("A"));
	}

	@TestedBehaviour("only choices not in ignoredChoices are considered")
	@Test
	public void calculate_winners_returns_not_ignored_winner() {
		List<String> winners = computedVote.calculateWinners(Arrays.asList("C"));
		assertTrue(winners.contains("A"));
	}


}
