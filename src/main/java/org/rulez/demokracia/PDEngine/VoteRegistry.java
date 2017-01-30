package org.rulez.demokracia.PDEngine;

import java.io.IOException;
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
			int minEndorsements) throws ReportedException {

        checkVoteName(voteName);

		VoteAdminInfo admininfo = new VoteAdminInfo();
		Vote vote = new Vote (voteName, neededAssurances, countedAssurances, isClosed, minEndorsements);
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

    public static void checkVoteName(String voteName) throws ReportedException {


        if (voteName.length() < 3) {
            ReportedException e = new ReportedException("Vote name is too short!");

            throw e;
        }

        if (voteName.length() > 255) {
            ReportedException e = new ReportedException("Vote name is too long!");

            throw e;
        }

        if (!voteName.matches("(\\d|\\w)+")) {
            //[^\W\d_] or [a-zA-Z].

            ReportedException e = new ReportedException("Wrong characters in the vote name!");

            throw e;
        }

    }

}
