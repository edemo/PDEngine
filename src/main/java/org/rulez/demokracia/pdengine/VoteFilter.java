package org.rulez.demokracia.pdengine;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public class VoteFilter {

	public List<CastVote> filterVotes(final List<CastVote> votes, final String assurance) {
		Function<? super String, List<CastVote>> voteFilter = a -> votes.stream()
				.filter(v -> v.getAssurances().contains(a))
				.collect(Collectors.toList());

		return Optional.ofNullable(assurance)
				.map(voteFilter)
				.orElse(votes);
	}
}
