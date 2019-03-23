package org.rulez.demokracia.pdengine;

import javax.xml.ws.WebServiceContext;

import org.rulez.demokracia.pdengine.exception.ReportedException;

public class ChoiceManager extends VoteManager {

	public ChoiceManager(final WebServiceContext wsContext) {
		super(wsContext);
	}

	public String addChoice(final String adminKey, final String voteId, final String choiceName, final String user) {
		Vote vote = getVote(voteId);
		vote.checkAdminKey(adminKey);
		if(vote.hasIssuedBallots())
			throw new ReportedException("No choice can be added because there are ballots issued for the vote.");

		return getVote(voteId).addChoice(choiceName, user);
	}

	public Choice getChoice(final String voteId, final String choiceId) {
		return getVote(voteId).getChoice(choiceId);
	}

	public void endorseChoice(final String adminKey, final String voteId, final String choiceId, final String givenUserName) {
		String userName = givenUserName;
		if("user".equals(adminKey)) {
			checkIfVoteIsEndorseable(voteId);
			userName = getWsUserName();
		}
		Vote vote = getVote(voteId);
		vote.checkAdminKey(adminKey);
		vote.getChoice(choiceId).endorse(userName);
	}

}