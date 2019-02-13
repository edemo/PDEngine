package org.rulez.demokracia.pdengine.testhelpers;

import org.rulez.demokracia.pdengine.dataobjects.ChoiceEntity;
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
		choiceId = voteManager.addChoice(adminInfo.adminKey, adminInfo.voteId, "choice1", "user");
	}

	protected ChoiceEntity getChoice(String theChoice) {
		return voteManager.getChoice(adminInfo.voteId, theChoice);
	}

	protected String addMyChoice() {
		return voteManager.addChoice(adminInfo.adminKey, adminInfo.voteId, "choice2", null);
	}

	protected void assertValidationFailsWithMessage(String message) {
		assertThrows(() -> {
			voteManager.endorseChoice(adminInfo.adminKey, adminInfo.voteId, choiceId, "testuserke");
		})
				.assertException(IllegalArgumentException.class).assertMessageIs(message);
	}

}