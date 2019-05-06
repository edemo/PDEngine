package org.rulez.demokracia.pdengine.votecalculator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.rulez.demokracia.pdengine.beattable.BeatTable;
import org.rulez.demokracia.pdengine.beattable.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VoteResultComposerImpl implements VoteResultComposer {

	@Autowired
	private WinnerCalculatorService winnerCalculator;

	private final Set<String> ignoredSet;

	public VoteResultComposerImpl() {
		winnerCalculator = new WinnerCalculatorServiceImpl();
		ignoredSet = new HashSet<>();
	}

	@Override
	public List<VoteResult> composeResult(final BeatTable beatTable) {
		List<VoteResult> result = new ArrayList<>();
		HashSet<String> keyCollection = new HashSet<>(beatTable.getKeyCollection());
		while (!ignoredSet.equals(keyCollection)) {
			List<String> winners = winnerCalculator.calculateWinners(beatTable, ignoredSet);

			result.add(createVoteResult(beatTable, winners));
			ignoredSet.addAll(winners);
		}
		return result;
	}

	private VoteResult createVoteResult(final BeatTable beatTable, final List<String> winners) {
		return new VoteResult(winners, getBeats(winners, beatTable));
	}

	private Map<String, VoteResultBeat> getBeats(final List<String> choices, final BeatTable beatTable) {
		Map<String, VoteResultBeat> result = new ConcurrentHashMap<>();
		choices.stream().forEach(c -> result.put(c, getBeatsForChoice(c, beatTable)));
		return result;
	}

	private VoteResultBeat getBeatsForChoice(final String choice, final BeatTable beatTable) {
		Pair zeroPair = new Pair(0, 0);
		VoteResultBeat result = new VoteResultBeat();
		for (String row : beatTable.getKeyCollection()) {
			Pair beat = beatTable.getElement(row, choice);
			if (!zeroPair.equals(beat)) {
				result.getBeats().put(row, beat);
			}
		}
		return result;
	}

	@Override
	public void setWinnerCalculator(final WinnerCalculatorService winnerCalculator) {
		this.winnerCalculator = winnerCalculator;
	}
}
