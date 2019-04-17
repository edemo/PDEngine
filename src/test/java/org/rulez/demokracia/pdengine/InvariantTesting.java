package org.rulez.demokracia.pdengine;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.rulez.demokracia.pdengine.vote.Vote;
import org.rulez.demokracia.pdengine.vote.VoteEntity;

public class InvariantTesting {

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
		savedVoteId = vote.getId();
		savedAdminKey = vote.getAdminKey();
		savedNeededAssurances = new ArrayList<>(vote.getNeededAssurances());
		savedCountedAssurances = new ArrayList<>(vote.getCountedAssurances());
		savedIsPrivate = vote.isPrivate();
		savedCreationTime = vote.getCreationTime();
		savedCanUpdate = vote.getParameters().isUpdatable();
	}

	public void assertInvariables(final Vote vote) {
		assertEquals(savedVoteId, vote.getId());
		assertEquals(savedAdminKey, vote.getAdminKey());
		assertEquals(savedNeededAssurances, vote.getNeededAssurances());
		assertEquals(savedCountedAssurances, vote.getCountedAssurances());
		assertEquals(savedIsPrivate, vote.isPrivate());
		assertEquals(savedCreationTime, vote.getCreationTime());
		assertEquals(savedCanUpdate, vote.getParameters().isUpdatable());
	}

}