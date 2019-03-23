package org.rulez.demokracia.pdengine;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.rulez.demokracia.pdengine.annotations.TestedBehaviour;
import org.rulez.demokracia.pdengine.annotations.TestedFeature;
import org.rulez.demokracia.pdengine.annotations.TestedOperation;
import org.rulez.demokracia.pdengine.dataobjects.ChoiceEntity;
import org.rulez.demokracia.pdengine.dataobjects.VoteAdminInfo;
import org.rulez.demokracia.pdengine.testhelpers.CreatedDefaultVoteRegistry;

@TestedFeature("Manage votes")
@TestedOperation("modify vote")
public class ChoiceModifyValidationTest extends CreatedDefaultVoteRegistry {
	
	private static final String USER = "user";
	public String voteId;
	public String choiceId;
	public String adminKey;
	public String choice;

	@Before
	public void setUp() {
		super.setUp();
		
		voteId = adminInfo.voteId;
		choiceId = voteManager.addChoice(new VoteAdminInfo(adminInfo.voteId, adminInfo.adminKey), "choice1", USER);
		adminKey = adminInfo.adminKey;
		choice = "New choice name";
	}

	@TestedBehaviour("validates inputs")
	@Test
	public void invalid_voteId_is_rejected() {
		String invalidVoteId = "invalidVoteId";
		
		assertThrows(
				() -> voteManager.modifyChoice(new VoteAdminInfo(invalidVoteId, adminKey), choiceId, choice)
			).assertMessageIs("illegal voteId");
	}
	
	@TestedBehaviour("validates inputs")
	@Test
	public void invalid_choiceId_is_rejected() {
		String invalidChoiceId = "invalidChoiceId";
		
		assertThrows(
				() -> voteManager.modifyChoice(new VoteAdminInfo(voteId, adminKey), invalidChoiceId, choice)
			).assertMessageIs("Illegal choiceId");
	}
	
	@TestedBehaviour("validates inputs")
	@Test
	public void invalid_adminKey_is_rejected() {
		String invalidAdminKey = "invalidAdminKey";
		
		assertThrows(
				() -> voteManager.modifyChoice(new VoteAdminInfo(voteId, invalidAdminKey), choiceId, choice)
			).assertMessageIs("Illegal adminKey");
	}

	@TestedBehaviour("modifies the string of the choice")
	@Test
	public void proper_voteId_choiceId_and_adminKey_does_modify_choice() {
		voteManager.modifyChoice(new VoteAdminInfo(voteId, adminKey), choiceId, choice);
		
		ChoiceEntity choiceEntity = voteManager.getChoice(voteId, choiceId);
		assertEquals(choiceEntity.name, choice);
	}
	
	
	@TestedBehaviour("validates inputs")
	@Test
	public void when_ballots_are_already_issued_choices_cannot_be_modified() {
		Vote vote = voteManager.getVote(voteId);
		vote.ballots.add("Test Ballot");
		
		assertThrows( () -> voteManager.modifyChoice(new VoteAdminInfo(voteId, adminKey), choiceId, "something else")
				).assertMessageIs("Vote modification disallowed: ballots already issued");
	}
	
	@TestedBehaviour("if 'user' is used as adminKey, then the user must be the one who added the choice and canAddIn be true")
	@Test
	public void userAdmin_cannot_modify_choice_if_canAddin_is_false() {
		Vote vote = voteManager.getVote(voteId);
		vote.canAddin=false;
		
		assertThrows( () -> voteManager.modifyChoice(new VoteAdminInfo(voteId, USER), choiceId, choice)
				).assertMessageIs("Choice modification disallowed: adminKey is user, but canAddin is false");

	}
	
	@TestedBehaviour("if 'user' is used as adminKey, then the user must be the one who added the choice and canAddIn be true")
	@Test
	public void userAdmin_cannot_modify_choice_if_it_is_not_added_by_other_user() {
		Vote vote = voteManager.getVote(voteId);
		vote.canAddin=true;
		
		assertThrows( () -> voteManager.modifyChoice(new VoteAdminInfo(voteId, USER), choiceId, choice)
				).assertMessageIs("Choice modification disallowed: adminKey is user, " +
							       "and the choice was added by a different user");

	}
	
	@TestedBehaviour("if 'user' is used as adminKey, then the user must be the one who added the choice and canAddIn be true")
	@Test
	public void userAdmin_can_modify_the_choice_if_canAddin_is_true_and_he_is_the_choice_creator() {
		Vote vote = voteManager.getVote(voteId);
		vote.canAddin=true;
		String myName = voteManager.getWsUserName();
		choiceId = voteManager.addChoice(new VoteAdminInfo(adminInfo.voteId, adminInfo.adminKey), "choice2", myName);
		
		voteManager.modifyChoice(new VoteAdminInfo(voteId, USER), choiceId, choice);
		
		ChoiceEntity choiceEntity = voteManager.getChoice(voteId, choiceId);
		assertEquals(choiceEntity.name, choice);
	}
	
}