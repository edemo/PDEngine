package org.rulez.demokracia.PDEngine.testhelpers;

import org.rulez.demokracia.PDEngine.DataObjects.Choice;
import org.rulez.demokracia.PDEngine.exception.ReportedException;

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
		choiceId = voteManager.addChoice(adminInfo.adminKey, adminInfo.voteId, "choice1", "user");
	}

	protected Choice getChoice(String theChoice) {
		return voteManager.getChoice(adminInfo.voteId, theChoice);
	}

	protected String addMyChoice() {
		return voteManager.addChoice(adminInfo.adminKey, adminInfo.voteId, "choice2", null);
	}

}