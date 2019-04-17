package org.rulez.demokracia.pdengine.votecast;

import java.util.List;

import org.rulez.demokracia.pdengine.choice.RankedChoice;

public interface CastVoteService {

	CastVote castVote(String voteId, String ballot, List<RankedChoice> theVote);

}
