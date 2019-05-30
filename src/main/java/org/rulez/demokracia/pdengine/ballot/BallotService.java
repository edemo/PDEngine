package org.rulez.demokracia.pdengine.ballot;

import org.rulez.demokracia.pdengine.vote.Vote;
import org.springframework.stereotype.Service;

@Service
public interface BallotService {

  String obtainBallot(Vote vote, String adminKey);
}
