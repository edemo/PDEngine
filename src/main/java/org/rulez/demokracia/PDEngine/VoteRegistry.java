package org.rulez.demokracia.PDEngine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.rulez.demokracia.PDEngine.DataObjects.Choice;
import org.rulez.demokracia.PDEngine.DataObjects.Vote;
import org.rulez.demokracia.PDEngine.DataObjects.VoteAdminInfo;

public class VoteRegistry implements IVoteManager {
	private static Map<String, Vote> votes = new HashMap<String, Vote>();

	@Override
	public VoteAdminInfo createVote(
			String voteName,
			List<String> neededAssurances,
			List<String> countedAssurances,
			boolean isClosed,
			int minEndorsements) {
		VoteAdminInfo admininfo = new VoteAdminInfo();
		Vote vote = new Vote(voteName, neededAssurances, countedAssurances, isClosed, minEndorsements);
		admininfo.adminKey=vote.adminKey;
		admininfo.voteId= vote.voteId;
		votes.put(admininfo.voteId, vote);
		return admininfo;
	}

	@Override
	public Vote getVote(String voteId) {
		return votes.get(voteId);
	}

	@Override
	public String addChoice(String adminKey, String voteId, String choiceName, String user) {
		return getVote(voteId).addChoice(choiceName, user);
	}

	@Override
	public Choice getChoice(String voteId, String choiceId) {
		return getVote(voteId).getChoice(choiceId);
	}

	@Override
	public void endorseChoice(String adminKey, String voteId, String choiceId, String userName) {
		getChoice(voteId, choiceId).endorse(userName);
	}

}
