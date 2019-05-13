package org.rulez.demokracia.pdengine.votecast.validation;

import org.rulez.demokracia.pdengine.authentication.AuthenticatedUserService;
import org.rulez.demokracia.pdengine.exception.ReportedException;
import org.rulez.demokracia.pdengine.vote.Vote;

public final class CastVoteValidationUtil {

  private CastVoteValidationUtil() {}

  public static void validateBallot(final String ballot, final Vote vote) {
    if (!vote.getBallots().contains(ballot))
      throw new ReportedException("Illegal ballot");
  }

  public static void checkIfVotingEnabled(final Vote vote) {
    if (!vote.getParameters().isVotable())
      throw new ReportedException("This issue cannot be voted on yet");
  }

  public static void checkIfUpdateConditionsAreConsistent(final Vote vote,
      final AuthenticatedUserService authService) {
    if (vote.getParameters().isUpdatable() && authService.getUserPrincipal() == null)
      throw new ReportedException("canUpdate is true but the user is not authenticated");
  }

}
