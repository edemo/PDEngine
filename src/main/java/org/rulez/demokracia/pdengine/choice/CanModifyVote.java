package org.rulez.demokracia.pdengine.choice;

import org.rulez.demokracia.pdengine.dataobjects.VoteAdminInfo;
import org.rulez.demokracia.pdengine.exception.ReportedException;
import org.rulez.demokracia.pdengine.vote.Vote;
import org.rulez.demokracia.pdengine.vote.VoteService;

public interface CanModifyVote {

  default Vote getModifiableVote(
      final VoteAdminInfo voteAdminInfo, final VoteService voteService
  ) {
    final Vote vote = voteService.getVote(voteAdminInfo.getVoteId());
    vote.checkAdminKey(voteAdminInfo.getAdminKey());
    if (vote.hasIssuedBallots())
      throw new ReportedException(
          "Vote modification disallowed: ballots already issued"
      );
    return vote;
  }

  default void checkIfVoteIsAddinable(
      final Vote vote, final RuntimeException exception
  ) {
    if (!vote.getParameters().isAddinable())
      throw exception;
  }

  default void checkIfUserIsTheSame(
      final Choice votesChoice, final String userName,
      final RuntimeException exception
  ) {
    if (!votesChoice.getUserName().equals(userName))
      throw exception;
  }
}
