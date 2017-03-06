package org.rulez.demokracia.PDEngine;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.rulez.demokracia.PDEngine.DataObjects.Vote;
import org.rulez.demokracia.PDEngine.testhelpers.ThrowableTester;

public class VoteInvariantCheck extends ThrowableTester {

	public Vote savedVote;
	public String savedVoteId;
	public String savedAdminKey;
	public List<String> savedNeededAssurances;
	public ArrayList<String> savedCountedAssurances;
	public boolean savedIsPrivate;
	public long savedCreationTime;

	protected void saveInvariables(Vote vote) {
		savedVote = vote;
		savedVoteId = vote.id;
		savedAdminKey=vote.adminKey;
		savedNeededAssurances = new ArrayList<String>(vote.neededAssurances);
		savedCountedAssurances = new ArrayList<String>(vote.countedAssurances);
		savedIsPrivate = vote.isPrivate;
		savedCreationTime = vote.creationTime;
	}

	@After
	public void TearDown() {
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

	public VoteInvariantCheck() {
		super();
	}

}