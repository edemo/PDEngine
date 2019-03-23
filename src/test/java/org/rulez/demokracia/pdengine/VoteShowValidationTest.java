package org.rulez.demokracia.pdengine;

import org.junit.Test;
import org.rulez.demokracia.pdengine.annotations.TestedBehaviour;
import org.rulez.demokracia.pdengine.annotations.TestedFeature;
import org.rulez.demokracia.pdengine.annotations.TestedOperation;
import org.rulez.demokracia.pdengine.testhelpers.CreatedDefaultVoteRegistry;

public class VoteShowValidationTest extends CreatedDefaultVoteRegistry {

	@TestedFeature("Manage votes")
	@TestedOperation("show vote")
	@TestedBehaviour("validates inputs")
	@Test
	public void invalid_voteId_is_rejected() {
		String invalidvoteId = RandomUtils.createRandomKey();
		assertThrows(
			() -> voteManager.showVote(invalidvoteId, adminInfo.adminKey)
		).assertMessageIs("illegal voteId");
	}
	
	@TestedFeature("Manage votes")
	@TestedOperation("show vote")
	@TestedBehaviour("validates inputs")
	@Test
	public void invalid_adminKey_is_rejected() {
		String invalidAdminKey = RandomUtils.createRandomKey();
		assertThrows(
			() -> voteManager.showVote(adminInfo.voteId, invalidAdminKey)
		).assertMessageIs("Illegal adminKey");
	}
}