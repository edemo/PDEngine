package org.rulez.demokracia.pdengine;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.rulez.demokracia.pdengine.dataobjects.Pair;

public class ComputedVote implements CanCalculateWinners, ComputedVoteInterface, Serializable {

	private static final long serialVersionUID = 1L;
	private BeatTable beatTable;
	private final Vote vote;
	private BeatTable beatPathTable;
	private Set<String> ignoredSet;
	private final BeatTableIgnore beatTableIgnore;

	private transient WinnerCalculator winnerCalculator;

	public ComputedVote(final Vote vote) {
		this.vote = vote;
		winnerCalculator = new WinnerCalculatorImpl();
		ignoredSet = new HashSet<>();
		beatTableIgnore = new BeatTableIgnoreImpl();
	}

	@Override
	public List<VoteResult> computeVote() {
		Set<String> keySet = computeKeySetFromVoteCast();

		initializeBeatTable(keySet);
		normalizeBeatTable();
		beatPathTable.computeTransitiveClosure();
		return composeResult();
	}

	private List<VoteResult> composeResult() {
		List<VoteResult> result = new ArrayList<>();
		while (!ignoredSet.equals(new HashSet<>(beatTable.getKeyCollection()))) {
			List<String> winners = winnerCalculator.calculateWinners(ignoreChoices(ignoredSet));

			result.add(new VoteResult(winners, getBeats(winners)));
			ignoredSet.addAll(winners);
		}
		return result;
	}

	private Map<String, Map<String, Pair>> getBeats(final List<String> choices) {
		Map<String, Map<String, Pair>> result = new HashMap<>();
		choices.stream().forEach(c -> result.put(c, getBeatsForChoice(c)));
		return result;
	}

	private Map<String, Pair> getBeatsForChoice(final String choice) {
		Pair zeroPair = new Pair(0, 0);
		Map<String, Pair> result = new HashMap<>();
		for (String row : beatPathTable.getKeyCollection()) {
			Pair beat = beatPathTable.getElement(row, choice);
			if (!zeroPair.equals(beat)) {
				result.put(row, beat);
			}
		}
		return result;
	}

	private void normalizeBeatTable() {
		beatPathTable = new BeatTable(beatTable);
		beatPathTable.normalize();
	}

	private void initializeBeatTable(final Set<String> keySet) {
		beatTable = new BeatTable(keySet);
		beatTable.initialize(vote.getVotesCast());
	}

	private Set<String> computeKeySetFromVoteCast() {
		return vote.getVotesCast().stream()
				.map(CastVote::getPreferences)
				.flatMap(List::stream)
				.map(p -> p.choiceId)
				.collect(Collectors.toSet());
	}

	@Override
	public List<String> calculateWinners(final List<String> ignoredChoices) {
		BeatTable filteredBeatTable = ignoreChoices(ignoredChoices);
		return winnerCalculator.calculateWinners(filteredBeatTable);
	}

	public BeatTable getBeatTable() {
		return beatTable;
	}

	public BeatTable getBeatPathTable() {
		return beatPathTable;
	}

	public Vote getVote() {
		return vote;
	}

	public void setWinnerCalculator(final WinnerCalculatorImpl winnerCalculator) {
		this.winnerCalculator = winnerCalculator;
	}

	private BeatTable ignoreChoices(final Collection<String> ignoredChoices) {
		return beatTableIgnore.ignoreChoices(beatPathTable, ignoredChoices);
	}
}
