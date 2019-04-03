package org.rulez.demokracia.pdengine;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.rulez.demokracia.pdengine.dataobjects.Pair;

import jersey.repackaged.com.google.common.collect.Sets;
import jersey.repackaged.com.google.common.collect.Sets.SetView;

public class ComputedVote implements CanCalculateWinners, ComputedVoteInterface, Serializable {

	private static final long serialVersionUID = 1L;
	private BeatTable beatTable;
	private final Vote vote;
	private BeatTable beatPathTable;

	private transient WinnerCalculator winnerCalculator;

	public ComputedVote(final Vote vote) {
		this.vote = vote;
		winnerCalculator = new WinnerCalculatorImpl();
	}

	@Override
	public void computeVote() {
		Set<String> keySet = vote.getVotesCast().stream()
				.map(CastVote::getPreferences)
				.flatMap(List::stream)
				.map(p -> p.choiceId)
				.collect(Collectors.toSet());

		beatTable = new BeatTable(keySet);
		beatTable.initialize(vote.getVotesCast());
		beatPathTable = new BeatTable(beatTable);
		beatPathTable.normalize();
		beatPathTable.computeTransitiveClosure();
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

	private BeatTable ignoreChoices(final List<String> ignoredChoices) {
		SetView<String> activeChoices = Sets.difference(Sets.newHashSet(beatPathTable.getKeyCollection()),
				Sets.newHashSet(ignoredChoices));
		BeatTable filteredBeatTable = new BeatTable(activeChoices);
		for (String key1 : activeChoices) {
			for (String key2 : activeChoices) {
				Pair source = beatTable.getElement(key1, key2);
				if (Objects.nonNull(source))
					filteredBeatTable.setElement(key1, key2, source);
			}
		}
		return filteredBeatTable;
	}
}
