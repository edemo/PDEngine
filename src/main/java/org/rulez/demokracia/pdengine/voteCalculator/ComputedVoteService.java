package org.rulez.demokracia.pdengine.voteCalculator;

import org.rulez.demokracia.pdengine.vote.Vote;
import org.springframework.stereotype.Service;

@Service
public interface ComputedVoteService {

	ComputedVote computeVote(Vote vote);
}
