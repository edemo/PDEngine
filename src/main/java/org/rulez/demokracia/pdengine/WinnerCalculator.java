package org.rulez.demokracia.pdengine;

import java.util.Collection;
import java.util.List;

import org.rulez.demokracia.pdengine.beattable.BeatTable;

public interface WinnerCalculator {

	List<String> calculateWinners(BeatTable beatTable, Collection<String> ignoredChoices);
}
