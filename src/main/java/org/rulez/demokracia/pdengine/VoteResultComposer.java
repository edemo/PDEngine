package org.rulez.demokracia.pdengine;

import java.util.List;

public interface VoteResultComposer {

	List<VoteResult> composeResult(BeatTable beatTable);

	void setWinnerCalculator(WinnerCalculator winnerCalculator);

}
