package org.rulez.demokracia.pdengine.testhelpers;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import org.junit.After;
import org.rulez.demokracia.pdengine.dataobjects.VoteEntity;

public class VoteInvariantCheck extends ThrowableTester {

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