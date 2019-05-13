package org.rulez.demokracia.pdengine.votefilter;

import java.util.List;
import org.rulez.demokracia.pdengine.votecast.CastVote;

public interface VoteFilter {

  List<CastVote> filterVotes(List<CastVote> votes, String assurance);

}
