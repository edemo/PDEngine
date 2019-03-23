package org.rulez.demokracia.pdengine;

import org.junit.Test;
import org.rulez.demokracia.pdengine.annotations.TestedBehaviour;
import org.rulez.demokracia.pdengine.annotations.TestedFeature;
import org.rulez.demokracia.pdengine.annotations.TestedOperation;
import org.rulez.demokracia.pdengine.testhelpers.CreatedDefaultVoteRegistry;

@TestedFeature("Manage votes")
@TestedOperation("delete vote")
public class VoteDeleteValidationTest extends CreatedDefaultVoteRegistry {

	@TestedBehaviour("validate inputs")
	@Test
	public void invalid_voteId_is_rejected() {
		String invalidvoteId = RandomUtils.createRandomKey();
		assertThrows(
			() -> voteManager.deleteVote(invalidvoteId, adminInfo.adminKey)
		).assertMessageIs("illegal voteId");
	}


	@TestedBehaviour("validate inputs")
	@Test
	public void invalid_adminKey_is_rejected() {
		String invalidAdminKey = RandomUtils.createRandomKey();
		assertThrows(
			() -> voteManager.deleteVote(adminInfo.voteId, invalidAdminKey)
		).assertMessageIs("Illegal adminKey");
	}
	
	@TestedBehaviour("deletes the vote with all parameters, choices, ballots and votes cast")
	@Test
	public void proper_voteId_and_adminKey_with_ballot_does_not_delete_vote() {
		String voteId = adminInfo.voteId;
		Vote vote = voteManager.getVote(voteId);
		vote.ballots.add("TestBallot");
		assertThrows(
			() -> voteManager.deleteVote(voteId, adminInfo.adminKey)
		).assertMessageIs("This vote cannot be deleted it has issued ballots.");
	}
	
	@TestedBehaviour("deletes the vote with all parameters, choices, ballots and votes cast")
	@Test
	public void proper_voteId_and_adminKey_does_delete_vote() {
		String voteId = adminInfo.voteId;
		voteManager.deleteVote(voteId, adminInfo.adminKey);
		assertThrows(
			() -> voteManager.getVote(voteId)
		).assertMessageIs("illegal voteId");
	}
	
	@TestedBehaviour("A vote cannot be deleted if it have issued ballots.")
	@Test
	public void proper_voteId_and_adminKey_with_issued_ballots_does_not_delete_vote() {
		String voteId = adminInfo.voteId;
		Vote vote = voteManager.getVote(voteId);
		vote.ballots.add("TestBallots");
		
		assertThrows(
			() -> voteManager.deleteVote(voteId, adminInfo.adminKey)
		).assertMessageIs("This vote cannot be deleted it has issued ballots.");
	}
}