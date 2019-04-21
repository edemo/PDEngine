package org.rulez.demokracia.pdengine.testhelpers;

import java.util.List;

import org.rulez.demokracia.pdengine.choice.Choice;
import org.rulez.demokracia.pdengine.choice.RankedChoice;
import org.rulez.demokracia.pdengine.vote.Vote;
import org.rulez.demokracia.pdengine.votecast.CastVote;

public class CastVoteTestHelper {

	public static Choice CHOICE_A = new Choice("A", "user");
	public static Choice CHOICE_B = new Choice("B", "user");

	public static void fillVoteWithDummyCastVotes(final Vote vote) {
		vote.addChoice(CHOICE_A);
		vote.addChoice(CHOICE_B);
		List<RankedChoice> rankedChoices = List.of(new RankedChoice(CHOICE_A.getId(), 1));
		vote.getVotesCast().add(new CastVote("user1", rankedChoices));
		vote.getVotesCast().add(new CastVote("user2", rankedChoices));
		vote.getVotesCast().add(new CastVote(null, rankedChoices));
		vote.getVotesCast().add(new CastVote("user3", rankedChoices));
	}

}
