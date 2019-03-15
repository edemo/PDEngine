package org.rulez.demokracia.pdengine;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;
import org.rulez.demokracia.pdengine.annotations.tested_behaviour;
import org.rulez.demokracia.pdengine.annotations.tested_feature;
import org.rulez.demokracia.pdengine.annotations.tested_operation;
import org.rulez.demokracia.pdengine.dataobjects.ChoiceEntity;
import org.rulez.demokracia.pdengine.exception.ReportedException;
import org.rulez.demokracia.pdengine.testhelpers.CreatedDefaultVoteRegistry;

public class ChoiceModifyValidationTest extends CreatedDefaultVoteRegistry {
	
	public String voteId, choiceId, adminKey, choice;
	
	public void setUp() throws ReportedException {
		super.setUp();
		
		voteId = adminInfo.voteId;
		choiceId = voteManager.addChoice(adminInfo.adminKey, adminInfo.voteId, "choice1", "user");
		adminKey = adminInfo.adminKey;
		choice = "New choice name";
	}

	@tested_feature("Manage votes")
	@tested_operation("modify vote")
	@tested_behaviour("validates inputs")
	@Test
	public void invalid_voteId_is_rejected() throws ReportedException {
		String invalidVoteId = "invalidVoteId";
		
		assertThrows(
				() -> voteManager.modifyChoice(invalidVoteId, choiceId, adminKey, choice)
			).assertMessageIs(String.format("illegal voteId: %s",invalidVoteId));
	}
	
	@tested_feature("Manage votes")
	@tested_operation("modify vote")
	@tested_behaviour("validates inputs")
	@Test
	public void invalid_choiceId_is_rejected() throws ReportedException {
		String invalidChoiceId = "invalidChoiceId";
		
		assertThrows(
				() -> voteManager.modifyChoice(voteId, invalidChoiceId, adminKey, choice)
			).assertMessageIs(String.format("Illegal choiceId: %s",invalidChoiceId));
	}
	
	@tested_feature("Manage votes")
	@tested_operation("modify vote")
	@tested_behaviour("validates inputs")
	@Test
	public void invalid_adminKey_is_rejected() throws ReportedException {
		String invalidAdminKey = "invalidAdminKey";
		
		assertThrows(
				() -> voteManager.modifyChoice(voteId, choiceId, invalidAdminKey, choice)
			).assertMessageIs(String.format("Illegal adminKey: %s",invalidAdminKey));
	}

	@tested_feature("Manage votes")
	@tested_operation("modify choice")
	@tested_behaviour("modifies the string of the choice")
	@Test
	public void proper_voteId_choiceId_and_adminKey_does_modify_choice() throws ReportedException {
		voteManager.modifyChoice(voteId, choiceId, adminKey, choice);
		
		ChoiceEntity choiceEntity = voteManager.getChoice(voteId, choiceId);
		assertEquals(choiceEntity.name, choice);
	}
	
	
	@tested_feature("Manage votes")
	@tested_operation("modify vote")
	@tested_behaviour("validates inputs")
	@Test
	public void proper_voteId_choiceId_and_adminKey_with_ballot_does_not_modify_choice() throws ReportedException {
		Vote vote = voteManager.getVote(voteId);
		vote.ballots.add("Test Ballot");
		
		assertThrows( () -> voteManager.modifyChoice(voteId, choiceId, adminKey, choice)
				).assertMessageIs("Choice modification disallowed: ballots already issued");
		
		ChoiceEntity choiceEntity = voteManager.getChoice(voteId, choiceId);
		assertNotEquals(choiceEntity.name, choice);
	}
	
	@tested_feature("Manage votes")
	@tested_operation("modify choice")
	@tested_behaviour("if 'user' is used as adminKey, then the user must be the one who added the choice and canAddIn be true")
	@Test
	public void userAdmin_cannot_modify_choice_if_canAddin_is_false() throws ReportedException {
		Vote vote = voteManager.getVote(voteId);
		vote.canAddin=false;
		
		assertThrows( () -> voteManager.modifyChoice(voteId, choiceId, "user", choice)
				).assertMessageIs("Choice modification disallowed: adminKey is user, but canAddin is false");

	}
	
	@tested_feature("Manage votes")
	@tested_operation("modify choice")
	@tested_behaviour("if 'user' is used as adminKey, then the user must be the one who added the choice and canAddIn be true")
	@Test
	public void userAdmin_cannot_modify_choice_if_it_is_not_added_by_other_user() throws ReportedException {
		Vote vote = voteManager.getVote(voteId);
		vote.canAddin=true;
		
		assertThrows( () -> voteManager.modifyChoice(voteId, choiceId, "user", choice)
				).assertMessageIs("Choice modification disallowed: adminKey is user, " +
							       "and the choice was added by a different user: user, me: test_user_in_ws_context");

	}
	
	@tested_feature("Manage votes")
	@tested_operation("modify choice")
	@tested_behaviour("if 'user' is used as adminKey, then the user must be the one who added the choice and canAddIn be true")
	@Test
	public void userAdmin_can_modify_the_choice_if_canAddin_is_true_and_he_is_the_choice_creator() throws ReportedException {
		Vote vote = voteManager.getVote(voteId);
		vote.canAddin=true;
		String me = voteManager.getWsUserName();
		choiceId = voteManager.addChoice(adminInfo.adminKey, adminInfo.voteId, "choice2", me);
		
		voteManager.modifyChoice(voteId, choiceId, "user", choice);
		
		ChoiceEntity choiceEntity = voteManager.getChoice(voteId, choiceId);
		assertEquals(choiceEntity.name, choice);
	}
	
}