package org.rulez.demokracia.pdengine.voteCalculator;

import java.util.Collection;
import java.util.List;

import org.rulez.demokracia.pdengine.beattable.BeatTable;
import org.springframework.stereotype.Service;

@Service
public interface WinnerCalculatorService {

	List<String> calculateWinners(BeatTable beatTable, Collection<String> ignoredChoices);
}
