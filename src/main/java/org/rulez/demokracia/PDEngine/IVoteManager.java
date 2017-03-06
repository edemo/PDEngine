package org.rulez.demokracia.PDEngine;

import java.util.List;

import org.rulez.demokracia.PDEngine.DataObjects.Choice;
import org.rulez.demokracia.PDEngine.DataObjects.Vote;
import org.rulez.demokracia.PDEngine.DataObjects.VoteAdminInfo;

public interface IVoteManager {

	VoteAdminInfo createVote(String voteName, List<String> neededAssurances, List<String> countedAssurances,
			boolean isPrivate, int minEndorsements);

	Vote getVote(String voteId);

	String addChoice(String adminKey, String voteId, String choiceName, String user);

	Choice getChoice(String voteId, String choiceId);

	void endorseChoice(String adminKey, String voteId, String choiceId, String string);

}