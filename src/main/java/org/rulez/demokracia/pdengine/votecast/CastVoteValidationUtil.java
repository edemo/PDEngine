package org.rulez.demokracia.pdengine.votecast;

import java.util.List;

import org.rulez.demokracia.pdengine.authentication.AuthenticatedUserService;
import org.rulez.demokracia.pdengine.choice.RankedChoice;
import org.rulez.demokracia.pdengine.exception.ReportedException;
import org.rulez.demokracia.pdengine.vote.Vote;

@SuppressWarnings("PMD.CyclomaticComplexity")
public final class CastVoteValidationUtil {

  private CastVoteValidationUtil() {
  }

  public static void validatePreferences(
      final List<RankedChoice> rankedChoices, final Vote vote
  ) {
    rankedChoices.forEach(choice -> validateOnePreference(vote, choice));
  }

  private static void
      validateOnePreference(final Vote vote, final RankedChoice choice) {
    checkChoicesContainsChoice(vote, choice);
    checkRankIsNotNegative(choice);
  }

  private static void checkRankIsNotNegative(final RankedChoice choice) {
    if (choice.getRank() < 0)
      throw new ReportedException("Invalid rank");
  }

  private static void
      checkChoicesContainsChoice(final Vote vote, final RankedChoice choice) {
    if (!vote.getChoices().containsKey(choice.getChoiceId()))
      throw new ReportedException("Invalid choiceId");
  }

  public static void validateBallot(final String ballot, final Vote vote) {
    if (!vote.getBallots().contains(ballot))
      throw new ReportedException("Illegal ballot");
  }

  public static void checkIfVotingEnabled(final Vote vote) {
    if (!vote.getParameters().isVotable())
      throw new ReportedException("This issue cannot be voted on yet");
  }

  public static void checkIfUpdateConditionsAreConsistent(
      final Vote vote, final AuthenticatedUserService authService
  ) {
    if (
      vote.getParameters().isUpdatable() &&
          authService.getUserPrincipal() == null
    )
      throw new ReportedException(
          "canUpdate is true but the user is not authenticated"
      );
  }

}
