package org.rulez.demokracia.pdengine;

import javax.xml.ws.WebServiceContext;

import org.rulez.demokracia.pdengine.dataobjects.VoteAdminInfo;
import org.rulez.demokracia.pdengine.exception.ReportedException;
import org.rulez.demokracia.pdengine.vote.Vote;

public class ChoiceManager extends VoteManager {

	public ChoiceManager(final WebServiceContext wsContext) {
		super(wsContext);
	}

	public String addChoice(final VoteAdminInfo voteAdminInfo, final String choiceName, final String user) {
		Vote vote = getVoteIfModifiable(voteAdminInfo.voteId, voteAdminInfo.adminKey);

		return vote.addChoice(choiceName, user);
	}

	public Choice getChoice(final String voteId, final String choiceId) {
		return getVote(voteId).getChoice(choiceId);
	}

	public void endorseChoice(final VoteAdminInfo voteAdminInfo, final String choiceId, final String givenUserName) {
		String userName = givenUserName;
		if("user".equals(voteAdminInfo.adminKey)) {
			checkIfVoteIsEndorseable(voteAdminInfo.voteId);
			userName = getWsUserName();
		}
		Vote vote = getVote(voteAdminInfo.voteId);
		vote.checkAdminKey(voteAdminInfo.adminKey);
		vote.getChoice(choiceId).endorse(userName);
	}

	protected Vote getVoteIfModifiable(final String voteId, final String adminKey) {
		Vote vote = getVote(voteId);
		vote.checkAdminKey(adminKey);
		if (vote.hasIssuedBallots())
			throw new ReportedException("Vote modification disallowed: ballots already issued");
		return vote;
	}

}