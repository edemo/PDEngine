package org.rulez.demokracia.pdengine;

import java.io.Serializable;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ComputedVote implements ComputeVoteInterface, Serializable {

	private static final long serialVersionUID = 1L;
	private BeatTable beatTable;
	private Vote vote;

	public ComputedVote(final Vote vote) {
		this.vote = vote;
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
	}

	public BeatTable getBeatTable() {
		return beatTable;
	}
}
