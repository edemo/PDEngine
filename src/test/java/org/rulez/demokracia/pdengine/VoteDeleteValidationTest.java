package org.rulez.demokracia.pdengine;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.rulez.demokracia.pdengine.annotations.tested_behaviour;
import org.rulez.demokracia.pdengine.annotations.tested_feature;
import org.rulez.demokracia.pdengine.annotations.tested_operation;
import org.rulez.demokracia.pdengine.dataobjects.VoteAdminInfo;
import org.rulez.demokracia.pdengine.exception.ReportedException;
import org.rulez.demokracia.pdengine.testhelpers.CreatedDefaultVoteRegistry;

public class VoteDeleteValidationTest extends CreatedDefaultVoteRegistry {

	public VoteDeleteValidationTest() {
		super();
	}

	@tested_feature("Manage votes")
	@tested_operation("delete vote")
	@tested_behaviour("validate inputs")
	@Test
	public void invalid_voteId_is_rejected() throws ReportedException {
		String invalidvoteId = RandomUtils.createRandomKey();
		assertThrows(
			() -> voteManager.deleteVote(invalidvoteId, adminInfo.adminKey)
		).assertMessageIs(String.format("illegal voteId: %s",invalidvoteId));
	}


	@tested_feature("Manage votes")
	@tested_operation("delete vote")
	@tested_behaviour("validate inputs")
	@Test
	public void invalid_adminKey_is_rejected() throws ReportedException {
		String invalidAdminKey = RandomUtils.createRandomKey();
		assertThrows(
			() -> voteManager.deleteVote(adminInfo.voteId, invalidAdminKey)
		).assertMessageIs(String.format("Illegal adminKey: %s",invalidAdminKey));
	}
	
	@tested_feature("Manage votes")
	@tested_operation("delete vote")
	@tested_behaviour("validate inputs")
	@Test
	public void proper_voteId_and_adminKey_is_delete_vote() throws ReportedException {
		String voteId = adminInfo.voteId;
		voteManager.deleteVote(voteId, adminInfo.adminKey);
		assertThrows(
			() -> voteManager.getVote(voteId)
		).assertMessageIs(String.format("illegal voteId: %s", voteId));
	}
}