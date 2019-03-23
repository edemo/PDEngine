package org.rulez.demokracia.pdengine;

import org.junit.Test;
import org.rulez.demokracia.pdengine.annotations.TestedBehaviour;
import org.rulez.demokracia.pdengine.annotations.TestedFeature;
import org.rulez.demokracia.pdengine.annotations.TestedOperation;
import org.rulez.demokracia.pdengine.exception.ReportedException;
import org.rulez.demokracia.pdengine.testhelpers.CreatedDefaultVoteRegistry;

@TestedFeature("Manage votes")
@TestedOperation("delete choice")
public class ChoiceDeleteValidationTest extends CreatedDefaultVoteRegistry {

	@TestedBehaviour("validates inputs")
	@Test
	public void invalid_voteId_is_rejected() throws ReportedException {
		String invalidvoteId = RandomUtils.createRandomKey();
		String choiceId = voteManager.addChoice(adminInfo.adminKey, adminInfo.voteId, "choice1", "user");
		assertThrows(
			() -> voteManager.deleteChoice(invalidvoteId, choiceId ,adminInfo.adminKey)
		).assertMessageIs("illegal voteId");
	}

	@TestedBehaviour("validates inputs")
	@Test
	public void invalid_choiceId_is_rejected() throws ReportedException {
		String invalidChoiceId = "InvalidChoiceId";
		assertThrows(
				() -> voteManager.deleteChoice(adminInfo.voteId, invalidChoiceId, adminInfo.adminKey)
			).assertMessageIs("Illegal choiceId");
	}
	

	@TestedBehaviour("validates inputs")
	@Test
	public void invalid_adminKey_is_rejected() throws ReportedException {
		String invalidAdminKey = RandomUtils.createRandomKey();
		String choiceId = voteManager.addChoice(adminInfo.adminKey, adminInfo.voteId, "choice1", "user");
		assertThrows(
			() -> voteManager.deleteChoice(adminInfo.voteId, choiceId, invalidAdminKey)
		).assertMessageIs("Illegal adminKey");
	}
	
	@TestedBehaviour("deletes the choice")
	@Test
	public void proper_voteId_choiceId_and_adminKey_does_delete_choice() throws ReportedException {
		String choiceId = voteManager.addChoice(adminInfo.adminKey, adminInfo.voteId, "choice1", "user");
		String voteId = adminInfo.voteId;
		voteManager.deleteChoice(adminInfo.voteId, choiceId, adminInfo.adminKey);
		assertThrows(
			() -> voteManager.getChoice(voteId, choiceId)
		).assertMessageIs("Illegal choiceId");
	}
}