package org.rulez.demokracia.pdengine;

import org.junit.Test;
import org.rulez.demokracia.pdengine.annotations.tested_behaviour;
import org.rulez.demokracia.pdengine.annotations.tested_feature;
import org.rulez.demokracia.pdengine.annotations.tested_operation;
import org.rulez.demokracia.pdengine.exception.ReportedException;
import org.rulez.demokracia.pdengine.testhelpers.CreatedDefaultVoteRegistry;

public class VoteShowValidationTest extends CreatedDefaultVoteRegistry {

	public VoteShowValidationTest() {
		super();
	}

	@tested_feature("Manage votes")
	@tested_operation("show vote")
	@tested_behaviour("validates inputs")
	@Test
	public void invalid_voteId_is_rejected() throws ReportedException {
		String invalidvoteId = RandomUtils.createRandomKey();
		assertThrows(
			() -> voteManager.showVote(invalidvoteId, adminInfo.adminKey)
		).assertMessageIs(String.format("illegal voteId: %s",invalidvoteId));
	}
	
	@tested_feature("Manage votes")
	@tested_operation("show vote")
	@tested_behaviour("validates inputs")
	@Test
	public void invalid_adminKey_is_rejected() throws ReportedException {
		String invalidAdminKey = RandomUtils.createRandomKey();
		assertThrows(
			() -> voteManager.showVote(adminInfo.voteId, invalidAdminKey)
		).assertMessageIs(String.format("Illegal adminKey: %s",invalidAdminKey));
	}
}