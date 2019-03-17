package org.rulez.demokracia.pdengine;

import org.junit.Test;
import org.rulez.demokracia.pdengine.annotations.tested_behaviour;
import org.rulez.demokracia.pdengine.annotations.tested_feature;
import org.rulez.demokracia.pdengine.annotations.tested_operation;
import org.rulez.demokracia.pdengine.exception.ReportedException;
import org.rulez.demokracia.pdengine.testhelpers.CreatedDefaultVoteRegistry;

public class VoteModificationValidationTest extends CreatedDefaultVoteRegistry {

	public VoteModificationValidationTest() {
		super();
	}

	@tested_feature("Manage votes")
	@tested_operation("modify vote")
	@tested_behaviour("validates inputs")
	@Test
	public void the_name_is_verified_against_the_same_rules_as_in_vote_creation() throws ReportedException {
		String modifiedVoteName = null;
		assertThrows(
			() -> voteManager.modifyVote(
					adminInfo.voteId, adminInfo.adminKey, modifiedVoteName)
		).assertMessageIs("vote name is null");
	}

	@tested_feature("Manage votes")
	@tested_operation("modify vote")
	@tested_behaviour("validates inputs")
	@Test
	public void invalid_adminKey_is_rejected() throws ReportedException {
		String invalidAdminKey = RandomUtils.createRandomKey();
		assertThrows(
			() -> voteManager.modifyVote(
					adminInfo.voteId, invalidAdminKey, voteName)
		).assertMessageIs(String.format("Illegal adminKey: %s",invalidAdminKey));
	}

	@tested_feature("Manage votes")
	@tested_operation("modify vote")
	@tested_behaviour("validates inputs")
	@Test
	public void invalid_voteId_is_rejected() throws ReportedException {
		String invalidvoteId = RandomUtils.createRandomKey();
		assertThrows(
			() -> voteManager.modifyVote(
					invalidvoteId, adminInfo.adminKey, voteName)
		).assertMessageIs(String.format("illegal voteId: %s",invalidvoteId));
	}
	
	@tested_feature("Manage votes")
	@tested_operation("modify vote")
	@tested_behaviour("The vote cannot be modified if there are ballots issued.")
	@Test
	public void modifyVote_with_ballot_get_an_exception() throws ReportedException {
		String voteId = adminInfo.voteId;
		Vote vote = voteManager.getVote(voteId);
		vote.ballots.add("TestBallots");
		
		assertThrows(
			() -> voteManager.modifyVote(
					voteId, adminInfo.adminKey, voteName)
		).assertMessageIs("The vote cannot be modified there are ballots issued.");
	}

}