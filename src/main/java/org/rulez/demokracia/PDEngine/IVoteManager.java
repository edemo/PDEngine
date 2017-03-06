package org.rulez.demokracia.PDEngine;

import java.util.List;

import org.rulez.demokracia.PDEngine.DataObjects.Choice;
import org.rulez.demokracia.PDEngine.DataObjects.Vote;
import org.rulez.demokracia.PDEngine.DataObjects.VoteAdminInfo;
import org.rulez.demokracia.PDEngine.exception.ReportedException;

public interface IVoteManager {

	static IVoteManager getVoteManager() {
		return VoteManagerRegistry.getVoteManager();
	}
	VoteAdminInfo createVote(String voteName, List<String> neededAssurances, List<String> countedAssurances,
			boolean isPrivate, int minEndorsements) throws ReportedException;

	Vote getVote(String voteId);

	String addChoice(String adminKey, String voteId, String choiceName, String user);

	Choice getChoice(String voteId, String choiceId);

	void endorseChoice(String adminKey, String voteId, String choiceId, String string);

}