package org.rulez.demokracia.pdengine;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.rulez.demokracia.pdengine.annotations.TestedBehaviour;
import org.rulez.demokracia.pdengine.annotations.TestedFeature;
import org.rulez.demokracia.pdengine.annotations.TestedOperation;
import org.rulez.demokracia.pdengine.dataobjects.VoteEntity;
import org.rulez.demokracia.pdengine.testhelpers.CreatedDefaultVoteRegistry;

public class VoteModificationInvariantsObservedTest extends CreatedDefaultVoteRegistry {

	public VoteEntity savedVote;
	public String savedVoteId;
	public String savedAdminKey;
	public List<String> savedNeededAssurances;
	public List<String> savedCountedAssurances;
	public boolean savedIsPrivate;
	public boolean savedCanUpdate;
	public long savedCreationTime;

	protected void saveInvariables(final VoteEntity vote) {
		savedVote = vote;
		savedVoteId = vote.id;
		savedAdminKey=vote.adminKey;
		savedNeededAssurances = new ArrayList<>(vote.neededAssurances);
		savedCountedAssurances = new ArrayList<>(vote.countedAssurances);
		savedIsPrivate = vote.isPrivate;
		savedCreationTime = vote.creationTime;
		savedCanUpdate = vote.canUpdate;
	}
	
	public void assertInvariables(final Vote vote) {
		assertEquals(savedVoteId, vote.id);
		assertEquals(savedAdminKey, vote.adminKey);
		assertEquals(savedNeededAssurances,vote.neededAssurances);
		assertEquals(savedCountedAssurances,vote.countedAssurances);
		assertEquals(savedIsPrivate, vote.isPrivate);
		assertEquals(savedCreationTime, vote.creationTime);
		assertEquals(savedCanUpdate, vote.canUpdate);
	}

	@TestedFeature("Manage votes")
	@TestedOperation("modify vote")
	@TestedBehaviour("vote invariants")
	@Test
	public void vote_invariants_are_observerd_in_modify_vote() {
		Vote vote = getTheVote();
		saveInvariables(vote);
		voteManager.modifyVote(savedVoteId, savedAdminKey, "modifiedVoteName");
		vote = voteManager.getVote(adminInfo.voteId);
		assertInvariables(vote);
	}
}
