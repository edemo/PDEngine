package org.rulez.demokracia.pdengine.testhelpers;

import static org.junit.Assert.assertEquals;


import org.junit.After;
import org.rulez.demokracia.pdengine.InvariantTesting;

public class VoteInvariantCheck extends InvariantTesting {

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