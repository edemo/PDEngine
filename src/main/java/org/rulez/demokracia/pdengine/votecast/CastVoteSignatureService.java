package org.rulez.demokracia.pdengine.votecast;

import org.springframework.stereotype.Service;

@Service
public interface CastVoteSignatureService {

  void signCastVote(CastVote castVote);

}
