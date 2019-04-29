package org.rulez.demokracia.pdengine.testhelpers;

import java.util.List;

import org.rulez.demokracia.pdengine.choice.Choice;
import org.rulez.demokracia.pdengine.choice.RankedChoice;
import org.rulez.demokracia.pdengine.vote.Vote;
import org.rulez.demokracia.pdengine.votecast.CastVote;

public class CastVoteTestHelper {

  public static final Choice CHOICE_A = new Choice("A", "user");
  public static final Choice CHOICE_B = new Choice("B", "user");
  public static final List<RankedChoice> RANKED_CHOICES =
      List.of(new RankedChoice(CHOICE_A.getId(), 1));

  public static void fillVoteWithDummyCastVotes(final Vote vote) {
    vote.addChoice(CHOICE_A);
    vote.addChoice(CHOICE_B);
    vote.getVotesCast().add(new CastVote("user1", RANKED_CHOICES));
    vote.getVotesCast().add(new CastVote("user2", RANKED_CHOICES));
    vote.getVotesCast().add(new CastVote(null, RANKED_CHOICES));
    vote.getVotesCast().add(new CastVote("user3", RANKED_CHOICES));
  }

}
