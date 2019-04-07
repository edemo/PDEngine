package org.rulez.demokracia.pdengine;

import java.util.List;

public interface WinnerCalculator {

	List<String> calculateWinners(final BeatTable beatTable);
}
