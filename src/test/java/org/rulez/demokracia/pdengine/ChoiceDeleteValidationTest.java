package org.rulez.demokracia.pdengine;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.rulez.demokracia.pdengine.annotations.tested_behaviour;
import org.rulez.demokracia.pdengine.annotations.tested_feature;
import org.rulez.demokracia.pdengine.annotations.tested_operation;
import org.rulez.demokracia.pdengine.exception.ReportedException;
import org.rulez.demokracia.pdengine.testhelpers.CreatedDefaultVoteRegistry;

public class ChoiceDeleteValidationTest extends CreatedDefaultVoteRegistry {

	public ChoiceDeleteValidationTest() {
		super();
	}

	@tested_feature("Manage votes")
	@tested_operation("delete choice")
	@tested_behaviour("validates inputs")
	@Test
	public void invalid_voteId_is_rejected() throws ReportedException {
		String invalidvoteId = RandomUtils.createRandomKey();
		String choiceId = voteManager.addChoice(adminInfo.adminKey, adminInfo.voteId, "choice1", "user");
		assertThrows(
			() -> voteManager.deleteChoice(invalidvoteId, choiceId ,adminInfo.adminKey)
		).assertMessageIs(String.format("illegal voteId: %s", invalidvoteId));
	}

	@tested_feature("Manage votes")
	@tested_operation("delete choice")
	@tested_behaviour("validates inputs")
	@Test
	public void invalid_choiceId_is_rejected() throws ReportedException {
		String invalidChoiceId = "InvalidChoiceId";
		assertThrows(
				() -> voteManager.deleteChoice(adminInfo.voteId, invalidChoiceId, adminInfo.adminKey)
			).assertMessageIs(String.format("Illegal choiceId: %s", invalidChoiceId));
	}
	

	@tested_feature("Manage votes")
	@tested_operation("delete choice")
	@tested_behaviour("validates inputs")
	@Test
	public void invalid_adminKey_is_rejected() throws ReportedException {
		String invalidAdminKey = RandomUtils.createRandomKey();
		String choiceId = voteManager.addChoice(adminInfo.adminKey, adminInfo.voteId, "choice1", "user");
		assertThrows(
			() -> voteManager.deleteChoice(adminInfo.voteId, choiceId, invalidAdminKey)
		).assertMessageIs(String.format("Illegal adminKey: %s", invalidAdminKey));
	}
	
	@tested_feature("Manage votes")
	@tested_operation("delete choice")
	@tested_behaviour("deletes the choice")
	@Test
	public void proper_voteId_choiceId_and_adminKey_does_delete_choice() throws ReportedException {
		String choiceId = voteManager.addChoice(adminInfo.adminKey, adminInfo.voteId, "choice1", "user");
		String voteId = adminInfo.voteId;
		String result = voteManager.deleteChoice(adminInfo.voteId, choiceId, adminInfo.adminKey);
		assertEquals("OK", result);
		
		assertThrows(
			() -> voteManager.getChoice(voteId, choiceId)
		).assertMessageIs(String.format("Illegal choiceId: %s", choiceId));
	}
}