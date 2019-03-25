package org.rulez.demokracia.pdengine;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public interface VoteFilter {

	public default List<CastVote> filterVotes(final List<CastVote> votes, final String assurance) {
		if (Objects.isNull(assurance))
			return votes;

		return votes.stream()
				.filter(v->v.getAssurances().contains(assurance))
				.collect(Collectors.toList());
	}
}
