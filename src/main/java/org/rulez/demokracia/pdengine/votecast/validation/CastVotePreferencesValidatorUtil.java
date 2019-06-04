package org.rulez.demokracia.pdengine.votecast.validation;

import java.util.List;
import org.rulez.demokracia.pdengine.choice.RankedChoice;
import org.rulez.demokracia.pdengine.exception.ReportedException;
import org.rulez.demokracia.pdengine.vote.Vote;

public final class CastVotePreferencesValidatorUtil {

  public static void validatePreferences(final List<RankedChoice> rankedChoices, final Vote vote) {
    rankedChoices.forEach(choice -> validateOnePreference(vote, choice));
  }

  private static void validateOnePreference(final Vote vote, final RankedChoice choice) {
    checkChoicesContainsChoice(vote, choice);
    checkRankIsNotNegative(choice);
  }

  private static void checkRankIsNotNegative(final RankedChoice choice) {
    if (choice.getRank() < 0)
      throw new ReportedException("Invalid rank");
  }

  private static void checkChoicesContainsChoice(final Vote vote, final RankedChoice choice) {
    if (!vote.getChoices().containsKey(choice.getChoiceId()))
      throw new ReportedException("Invalid choiceId");
  }
}
