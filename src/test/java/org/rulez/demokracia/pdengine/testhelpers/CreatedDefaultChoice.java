package org.rulez.demokracia.pdengine.testhelpers;

import org.rulez.demokracia.pdengine.dataobjects.Choice;
import org.rulez.demokracia.pdengine.exception.ReportedException;

public class CreatedDefaultChoice extends CreatedDefaultVoteRegistry {

	protected String choiceId;

	public CreatedDefaultChoice() {
		super();
	}

	public void setUp() throws ReportedException {
		super.setUp();
		addTestChoice();
	}

	protected void addTestChoice() {
		choiceId = voteManager.addChoice(adminInfo.getAdminKey(), adminInfo.getVoteId(), "choice1", "user");
	}

	protected Choice getChoice(String theChoice) {
		return voteManager.getChoice(adminInfo.getVoteId(), theChoice);
	}

	protected String addMyChoice() {
		return voteManager.addChoice(adminInfo.getAdminKey(), adminInfo.getVoteId(), "choice2", null);
	}

}