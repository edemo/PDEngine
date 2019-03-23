package org.rulez.demokracia.pdengine;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.rulez.demokracia.pdengine.dataobjects.CastVote;

public class VoteFilter {
	
	private VoteFilter() {
	}

	public static List<CastVote> filterVotes(List<CastVote> votes, String assurance) {
		if (Objects.isNull(assurance)) {
			return votes;
		}
		return votes.stream()
				.filter(v->v.getAssurances().contains(assurance))
				.collect(Collectors.toList());
	}
	

}
