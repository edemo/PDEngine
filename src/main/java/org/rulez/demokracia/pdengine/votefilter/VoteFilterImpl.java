package org.rulez.demokracia.pdengine.votefilter;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.rulez.demokracia.pdengine.votecast.CastVote;
import org.springframework.stereotype.Component;

@Component
public class VoteFilterImpl implements VoteFilter {

	@Override
	public List<CastVote> filterVotes(final List<CastVote> votes, final String assurance) {
		Function<? super String, List<CastVote>> voteFilter = a -> votes.stream()
				.filter(v -> v.getAssurances().contains(a))
				.collect(Collectors.toList());

		return Optional.ofNullable(assurance)
				.map(voteFilter)
				.orElse(votes);
	}
}
