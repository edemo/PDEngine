package org.rulez.demokracia.pdengine.testhelpers;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.rulez.demokracia.pdengine.dataobjects.VoteEntity;
import org.rulez.demokracia.testhelpers.ThrowableTester;

public class VoteInvariantCheck extends ThrowableTester {

	public VoteEntity savedVote;
	public String savedVoteId;
	public String savedAdminKey;
	public List<String> savedNeededAssurances;
	public List<String> savedCountedAssurances;
	public boolean savedIsPrivate;
	public long savedCreationTime;

	protected void saveInvariables(final VoteEntity vote) {
		savedVote = vote;
		savedVoteId = vote.id;
		savedAdminKey=vote.adminKey;
		savedNeededAssurances = new ArrayList<>(vote.neededAssurances);
		savedCountedAssurances = new ArrayList<>(vote.countedAssurances);
		savedIsPrivate = vote.isPrivate;
		savedCreationTime = vote.creationTime;
	}

	@After
	public void tearDown() {
		checkInvariables();
	}

	public void checkInvariables() {
		assertEquals(savedVoteId, savedVote.id);
		assertEquals(savedAdminKey, savedVote.adminKey);
		assertEquals(savedNeededAssurances,savedVote.neededAssurances);
		assertEquals(savedCountedAssurances,savedVote.countedAssurances);
		assertEquals(savedIsPrivate, savedVote.isPrivate);
		assertEquals(savedCreationTime, savedVote.creationTime);
	}

}