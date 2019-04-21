package org.rulez.demokracia.pdengine.voteCalculator;

import java.util.List;

import org.rulez.demokracia.pdengine.beattable.BeatTable;
import org.springframework.stereotype.Service;

@Service
public interface VoteResultComposer {

	List<VoteResult> composeResult(BeatTable beatTable);

	void setWinnerCalculator(WinnerCalculatorService winnerCalculator);

}
