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
		assertEquals(savedVoteId, savedVote.getId());
		assertEquals(savedAdminKey, savedVote.getAdminKey());
		assertEquals(savedNeededAssurances, savedVote.getNeededAssurances());
		assertEquals(savedCountedAssurances, savedVote.getCountedAssurances());
		assertEquals(savedIsPrivate, savedVote.isPrivate());
		assertEquals(savedCreationTime, savedVote.getCreationTime());
	}

}