package org.rulez.demokracia.pdengine;

import java.util.List;

import org.rulez.demokracia.pdengine.beattable.BeatTable;

public interface VoteResultComposer {

	List<VoteResult> composeResult(BeatTable beatTable);

	void setWinnerCalculator(WinnerCalculator winnerCalculator);

}
