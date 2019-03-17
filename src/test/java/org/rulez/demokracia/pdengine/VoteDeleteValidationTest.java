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
	@tested_behaviour("deletes the vote with all parameters, choices, ballots and votes cast")
	@Test
	public void proper_voteId_and_adminKey_with_ballot_does_not_delete_vote() throws ReportedException {
		String voteId = adminInfo.voteId;
		Vote vote = voteManager.getVote(voteId);
		vote.ballots.add("TestBallot");
		assertThrows(
			() -> voteManager.deleteVote(voteId, adminInfo.adminKey)
		).assertMessageIs("This vote cannot be deleted it has issued ballots.");
	}
	
	@tested_feature("Manage votes")
	@tested_operation("delete vote")
	@tested_behaviour("deletes the vote with all parameters, choices, ballots and votes cast")
	@Test
	public void proper_voteId_and_adminKey_does_delete_vote() throws ReportedException {
		String voteId = adminInfo.voteId;
		voteManager.deleteVote(voteId, adminInfo.adminKey);
		assertThrows(
			() -> voteManager.getVote(voteId)
		).assertMessageIs(String.format("illegal voteId: %s", voteId));
	}
	
	@tested_feature("Manage votes")
	@tested_operation("delete vote")
	@tested_behaviour("A vote cannot be deleted if it have issued ballots.")
	@Test
	public void proper_voteId_and_adminKey_with_issued_ballots_does_not_delete_vote() throws ReportedException {
		String voteId = adminInfo.voteId;
		Vote vote = voteManager.getVote(voteId);
		vote.ballots.add("TestBallots");
		
		assertThrows(
			() -> voteManager.deleteVote(voteId, adminInfo.adminKey)
		).assertMessageIs("This vote cannot be deleted it has issued ballots.");
	}
}