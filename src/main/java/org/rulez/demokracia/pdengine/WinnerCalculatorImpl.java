package org.rulez.demokracia.pdengine;

import java.util.List;
import java.util.stream.Collectors;

import org.rulez.demokracia.pdengine.dataobjects.Pair;

public class WinnerCalculatorImpl implements WinnerCalculator {

	@Override
	public List<String> calculateWinners(final BeatTable beatTable) {
		return beatTable.getKeyCollection()
				.stream()
				.filter(choice -> isWinner(choice, beatTable))
				.collect(Collectors.toList());
	}

	private boolean isWinner(final String choice, final BeatTable beatTable) {
		Pair nonbeatingPair = new Pair(0, 0);

		return beatTable.getKeyCollection()
				.stream()
				.allMatch(otherChoice -> nonbeatingPair.equals(beatTable.getElement(otherChoice, choice)));
	}

}
