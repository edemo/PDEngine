package org.rulez.demokracia.pdengine;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;
import org.rulez.demokracia.pdengine.annotations.tested_behaviour;
import org.rulez.demokracia.pdengine.annotations.tested_feature;
import org.rulez.demokracia.pdengine.annotations.tested_operation;
import org.rulez.demokracia.pdengine.dataobjects.VoteEntity;
import org.rulez.demokracia.pdengine.exception.ReportedException;
import org.rulez.demokracia.pdengine.testhelpers.CreatedDefaultVoteRegistry;

public class VoteModificationInvariantsObservedTest extends CreatedDefaultVoteRegistry {

	public VoteModificationInvariantsObservedTest() {
		super();
	}
	
	public VoteEntity savedVote;
	public String savedVoteId;
	public String savedAdminKey;
	public ArrayList<String> savedNeededAssurances;
	public ArrayList<String> savedCountedAssurances;
	public boolean savedIsPrivate;
	public long savedCreationTime;

	protected void saveInvariables(VoteEntity vote) {
		savedVote = vote;
		savedVoteId = vote.id;
		savedAdminKey=vote.adminKey;
		savedNeededAssurances = new ArrayList<String>(vote.neededAssurances);
		savedCountedAssurances = new ArrayList<String>(vote.countedAssurances);
		savedIsPrivate = vote.isPrivate;
		savedCreationTime = vote.creationTime;
	}
	
	public void checkInvariables(Vote vote) {
		assertEquals(savedVoteId, vote.id);
		assertEquals(savedAdminKey, vote.adminKey);
		assertEquals(savedNeededAssurances,vote.neededAssurances);
		assertEquals(savedCountedAssurances,vote.countedAssurances);
		assertEquals(savedIsPrivate, vote.isPrivate);
		assertEquals(savedCreationTime, vote.creationTime);
	}

	@tested_feature("Manage votes")
	@tested_operation("modify vote")
	@tested_behaviour("vote invariants")
	@Test
	public void vote_invariants_are_observerd_in_modify_vote() throws ReportedException {
		Vote vote = getTheVote();
		saveInvariables(vote);
		voteManager.modifyVote(savedVoteId, savedAdminKey, "modifiedVoteName");
		vote = voteManager.getVote(adminInfo.voteId);
		checkInvariables(vote);
	}
}
