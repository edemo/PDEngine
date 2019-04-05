package org.rulez.demokracia.pdengine;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.rulez.demokracia.pdengine.dataobjects.Pair;

public class VoteResultComposerImpl implements VoteResultComposer {

	private WinnerCalculator winnerCalculator;
	private final Set<String> ignoredSet;

	public VoteResultComposerImpl() {
		winnerCalculator = new WinnerCalculatorImpl();
		ignoredSet = new HashSet<>();
	}

	@Override
	public List<VoteResult> composeResult(final BeatTable beatTable) {
		List<VoteResult> result = new ArrayList<>();
		while (!ignoredSet.equals(new HashSet<>(beatTable.getKeyCollection()))) {
			List<String> winners = winnerCalculator.calculateWinners(beatTable, ignoredSet);

			result.add(new VoteResult(winners, getBeats(winners, beatTable)));
			ignoredSet.addAll(winners);
		}
		return result;
	}

	private Map<String, Map<String, Pair>> getBeats(final List<String> choices, final BeatTable beatTable) {
		Map<String, Map<String, Pair>> result = new ConcurrentHashMap<>();
		choices.stream().forEach(c -> result.put(c, getBeatsForChoice(c, beatTable)));
		return result;
	}

	private Map<String, Pair> getBeatsForChoice(final String choice, final BeatTable beatTable) {
		Pair zeroPair = new Pair(0, 0);
		Map<String, Pair> result = new ConcurrentHashMap<>();
		for (String row : beatTable.getKeyCollection()) {
			Pair beat = beatTable.getElement(row, choice);
			if (!zeroPair.equals(beat)) {
				result.put(row, beat);
			}
		}
		return result;
	}
}
