package org.rulez.demokracia.pdengine;

import java.io.Serializable;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ComputedVote implements ComputedVoteInterface, Serializable {

	private static final long serialVersionUID = 1L;
	private BeatTable beatTable;
	private final Vote vote;
	private BeatTable beatPathTable;
	private final List<String> secretCastVoteIdentifiers;

	public ComputedVote(final Vote vote) {
		this.vote = vote;
		this.secretCastVoteIdentifiers = vote.getVotesCastIdentifiers();
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

	public BeatTable getBeatTable() {
		return beatTable;
	}

	public BeatTable getBeatPathTable() {
		return beatPathTable;
	}
	
	public List<String> getSecretCastVoteIdentifiers() {
		return secretCastVoteIdentifiers;
	}
}
