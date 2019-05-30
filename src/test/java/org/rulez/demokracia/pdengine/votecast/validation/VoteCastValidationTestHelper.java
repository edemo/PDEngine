package org.rulez.demokracia.pdengine.votecast.validation;

import java.util.ArrayList;
import java.util.List;
import org.rulez.demokracia.pdengine.choice.Choice;
import org.rulez.demokracia.pdengine.choice.RankedChoice;
import org.rulez.demokracia.pdengine.vote.Vote;

public final class VoteCastValidationTestHelper {

  public static List<RankedChoice> prepareCastVote(final Vote vote, final boolean canVote) {
    final List<RankedChoice> theCastVote = new ArrayList<>();
    vote.getParameters().setVotable(canVote);
    return theCastVote;
  }

  public static void prepareRankedChoice(final List<RankedChoice> theCastVote,
      final String choiceId, final int rank) {
    final RankedChoice rankedChoice = new RankedChoice(choiceId, rank);
    theCastVote.add(rankedChoice);
  }

  public static void newChoiceAndRankedList(final List<RankedChoice> theCastVote,
      final int firstGoodRank, final Vote vote) {
    final Choice choice = new Choice("valid_choice", "userke");
    vote.addChoice(choice);
    prepareRankedChoice(theCastVote, choice.getId(), firstGoodRank);
  }
}
