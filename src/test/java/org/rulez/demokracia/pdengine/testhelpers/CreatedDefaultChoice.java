package org.rulez.demokracia.pdengine.testhelpers;

import org.junit.Before;
import org.rulez.demokracia.pdengine.dataobjects.ChoiceEntity;
import org.rulez.demokracia.pdengine.exception.ReportedException;

public class CreatedDefaultChoice extends CreatedDefaultVoteRegistry {

	protected String choiceId;

	@Before
	public void setUp() throws ReportedException {
		super.setUp();
		addTestChoice();
	}

	protected void addTestChoice() throws ReportedException {
		choiceId = voteManager.addChoice(adminInfo.adminKey, adminInfo.voteId, "choice1", "user");
	}

	protected ChoiceEntity getChoice(final String theChoice) throws ReportedException {
		return voteManager.getChoice(adminInfo.voteId, theChoice);
	}

	protected String addMyChoice() throws ReportedException {
		return voteManager.addChoice(adminInfo.adminKey, adminInfo.voteId, "choice2", null);
	}

	protected void assertValidationFailsWithMessage(final String message) {
		assertThrows(() -> {
			voteManager.endorseChoice(adminInfo.adminKey, adminInfo.voteId, choiceId, "testuserke");
		})
				.assertException(ReportedException.class).assertMessageIs(message);
	}

}