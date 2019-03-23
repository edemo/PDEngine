package org.rulez.demokracia.pdengine;

import org.junit.Test;
import org.rulez.demokracia.pdengine.annotations.TestedBehaviour;
import org.rulez.demokracia.pdengine.annotations.TestedFeature;
import org.rulez.demokracia.pdengine.annotations.TestedOperation;
import org.rulez.demokracia.pdengine.testhelpers.CreatedDefaultVoteRegistry;

public class ChoiceAddValidationTest extends CreatedDefaultVoteRegistry {

	@TestedFeature("Manage votes")
	@TestedOperation("Add choice")
	@TestedBehaviour("validates inputs")
	@Test
	public void invalid_voteId_is_rejected() {
		String invalidvoteId = RandomUtils.createRandomKey();
		assertThrows(
			() -> voteManager.addChoice(adminInfo.adminKey, invalidvoteId, "choice1", "user")
		).assertMessageIs("illegal voteId");
	}
	
	@TestedFeature("Manage votes")
	@TestedOperation("Add choice")
	@TestedBehaviour("validates inputs")
	@Test
	public void invalid_adminKey_is_rejected() {
		String invalidAdminKey = "invalidAdminKey";
		assertThrows(
			() -> voteManager.addChoice(invalidAdminKey, adminInfo.voteId, "choice1", "user")
		).assertMessageIs("Illegal adminKey");
	}

	@TestedFeature("Manage votes")
	@TestedOperation("Add choice")
	@TestedBehaviour("No choice can be added if there are ballots issued for the vote.")
	@Test
	public void no_choice_can_be_added_there_are_issued_ballots() {
		String voteId = adminInfo.voteId;
		Vote vote = voteManager.getVote(voteId);
		vote.ballots.add("TestBallots");
		
		assertThrows(
			() -> voteManager.addChoice(adminInfo.adminKey, voteId, "choice1", "user")
		).assertMessageIs("No choice can be added because there are ballots issued for the vote.");
	}
}