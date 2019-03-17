package org.rulez.demokracia.pdengine;

import javax.xml.ws.WebServiceContext;

public class ChoiceManager extends VoteManager {

	public ChoiceManager(WebServiceContext wsContext) {
		super(wsContext);
	}

	public String addChoice(String adminKey, String voteId, String choiceName, String user) {
		Vote vote = getVote(voteId);
		vote.checkAdminKey(adminKey);
		if(vote.hasIssuedBallots())
			throw new IllegalArgumentException("No choice can be added because there are ballots issued for the vote.");

		return getVote(voteId).addChoice(choiceName, user);
	}

	public Choice getChoice(String voteId, String choiceId) {
		return getVote(voteId).getChoice(choiceId);
	}

	public void endorseChoice(String adminKey, String voteId, String choiceId, String givenUserName) {
		if(adminKey.equals("user")) {
			checkIfVoteIsEndorseable(voteId);
			givenUserName = getWsUserName();
		}
		Vote vote = getVote(voteId);
		vote.checkAdminKey(adminKey);
		vote.getChoice(choiceId).endorse(givenUserName);
	}

}