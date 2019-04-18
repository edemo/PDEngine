package org.rulez.demokracia.pdengine.testhelpers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Before;
import org.rulez.demokracia.pdengine.VoteResult;
import org.rulez.demokracia.pdengine.VoteResultComposer;
import org.rulez.demokracia.pdengine.VoteResultComposerImpl;
import org.rulez.demokracia.pdengine.WinnerCalculator;
import org.rulez.demokracia.pdengine.beattable.BeatTable;

public class CreatedVoteResultComposer extends CreatedDefaultCastVoteWithRankedChoices {

	protected VoteResultComposer voteResultComposer;
	protected List<VoteResult> result;

	@Override
	@Before
	public void setUp() {
		super.setUp();
		voteResultComposer = new VoteResultComposerImpl();
		voteResultComposer.setWinnerCalculator(createWinnerCalculatorMock());
		result = voteResultComposer.composeResult(createBeatTable());
	}

	private BeatTable createBeatTable() {
		BeatTable beatTable = new BeatTable(List.of("A", "B", "C", "D"));
		beatTable.initialize(castVote);
		beatTable.normalize();
		beatTable.computeTransitiveClosure();
		return beatTable;
	}

	private WinnerCalculator createWinnerCalculatorMock() {
		WinnerCalculator winnerCalculator = mock(WinnerCalculator.class);
		when(winnerCalculator.calculateWinners(any(), any()))
				.thenReturn(List.of("A", "B"))
				.thenReturn(List.of("C"))
				.thenReturn(List.of("D"));
		return winnerCalculator;
	}

}
