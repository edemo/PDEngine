package org.rulez.demokracia.pdengine.tally;

import java.util.List;

import org.rulez.demokracia.pdengine.votecast.CastVote;
import org.springframework.stereotype.Service;

@Service
public interface TallyService {

  Tally calculateTally(String assurance, List<CastVote> castVotes);
}
