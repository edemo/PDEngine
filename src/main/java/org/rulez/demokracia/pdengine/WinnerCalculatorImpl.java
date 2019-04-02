package org.rulez.demokracia.pdengine;

import java.util.List;

public class WinnerCalculatorImpl implements WinnerCalculator {

	static WinnerCalculator create() {
		return new WinnerCalculatorImpl();
	}

	@Override
	public List<String> calculateWinners(final BeatTable beatTable) {
		throw new UnsupportedOperationException();
	}

}
