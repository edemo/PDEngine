package org.rulez.demokracia.pdengine;

public class ChoiceManager extends VoteManager {

	public ChoiceManager() {
		super();
	}

	public String addChoice(String adminKey, String voteId, String choiceName, String user) {
		return getVote(voteId).addChoice(choiceName, user);
	}

	public Choice getChoice(String voteId, String choiceId) {
		return getVote(voteId).getChoice(choiceId);
	}

	public void endorseChoice(String proxyUserName, String adminKey, String voteId, String choiceId, String givenUserName) {
		if(adminKey.equals("user")) {
			checkIfVoteIsEndorseable(voteId);
			givenUserName = proxyUserName;
		}
		getChoice(voteId, choiceId).endorse(givenUserName);
	}

}