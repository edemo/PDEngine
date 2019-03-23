package org.rulez.demokracia.pdengine.testhelpers;

import org.junit.Before;
import org.rulez.demokracia.pdengine.dataobjects.ChoiceEntity;
import org.rulez.demokracia.pdengine.dataobjects.VoteAdminInfo;
import org.rulez.demokracia.pdengine.exception.ReportedException;

public class CreatedDefaultChoice extends CreatedDefaultVoteRegistry {

	protected String choiceId;

	@Before
	public void setUp() {
		super.setUp();
		addTestChoice();
	}

	protected void addTestChoice() {
		choiceId = voteManager.addChoice(new VoteAdminInfo(adminInfo.voteId, adminInfo.adminKey), "choice1", "user");
	}

	protected ChoiceEntity getChoice(final String theChoice) {
		return voteManager.getChoice(adminInfo.voteId, theChoice);
	}

	protected String addMyChoice() {
		return voteManager.addChoice(new VoteAdminInfo(adminInfo.voteId, adminInfo.adminKey), "choice2", null);
	}

	protected void assertValidationFailsWithMessage(final String message) {
		assertThrows(() -> {
			voteManager.endorseChoice(new VoteAdminInfo(adminInfo.voteId, adminInfo.adminKey), choiceId, "testuserke");
		})
				.assertException(ReportedException.class).assertMessageIs(message);
	}

}