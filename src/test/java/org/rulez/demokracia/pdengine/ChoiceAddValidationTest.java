package org.rulez.demokracia.pdengine;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.rulez.demokracia.pdengine.annotations.tested_behaviour;
import org.rulez.demokracia.pdengine.annotations.tested_feature;
import org.rulez.demokracia.pdengine.annotations.tested_operation;
import org.rulez.demokracia.pdengine.exception.ReportedException;
import org.rulez.demokracia.pdengine.testhelpers.CreatedDefaultVoteRegistry;

public class ChoiceAddValidationTest extends CreatedDefaultVoteRegistry {

	public ChoiceAddValidationTest() {
		super();
	}

	@tested_feature("Manage votes")
	@tested_operation("Add choice")
	@tested_behaviour("validates inputs")
	@Test
	public void invalid_voteId_is_rejected() throws ReportedException {
		String invalidvoteId = RandomUtils.createRandomKey();
		assertThrows(
			() -> voteManager.addChoice(adminInfo.adminKey, invalidvoteId, "choice1", "user")
		).assertMessageIs(String.format("illegal voteId: %s", invalidvoteId));
	}
	
	@tested_feature("Manage votes")
	@tested_operation("Add choice")
	@tested_behaviour("validates inputs")
	@Test
	public void invalid_adminKey_is_rejected() throws ReportedException {
		String invalidAdminKey = "invalidAdminKey";
		assertThrows(
			() -> voteManager.addChoice(invalidAdminKey, adminInfo.voteId, "choice1", "user")
		).assertMessageIs(String.format("Illegal adminKey: %s", invalidAdminKey));
	}

	@tested_feature("Manage votes")
	@tested_operation("Add choice")
	@tested_behaviour("No choice can be added if there are ballots issued for the vote.")
	@Test
	public void no_choice_can_be_added_there_are_issued_ballots() throws ReportedException {
		String voteId = adminInfo.voteId;
		Vote vote = voteManager.getVote(voteId);
		vote.ballots.add("TestBallots");
		
		assertThrows(
			() -> voteManager.addChoice(adminInfo.adminKey, voteId, "choice1", "user")
		).assertMessageIs("No choice can be added because there are ballots issued for the vote.");
	}
}