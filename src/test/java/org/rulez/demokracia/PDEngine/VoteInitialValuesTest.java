package org.rulez.demokracia.PDEngine;

import static org.junit.Assert.*;

import org.junit.Test;
import org.rulez.demokracia.PDEngine.annotations.tested_behaviour;
import org.rulez.demokracia.PDEngine.annotations.tested_feature;
import org.rulez.demokracia.PDEngine.annotations.tested_operation;
import org.rulez.demokracia.PDEngine.testhelpers.CreatedDefaultVoteRegistry;

@tested_feature("Manage votes")
@tested_operation("create vote")
@tested_behaviour("The vote initially can only be modified with the adminkey")
public class VoteInitialValuesTest extends CreatedDefaultVoteRegistry {


	@Test
	public void canAddin_is_false_in_the_created_vote() {
		assertEquals(voteManager.getVote(adminInfo.voteId).canAddin, false);
	}

	@Test
	public void canEndorse_is_false_in_the_created_vote() {
		assertEquals(voteManager.getVote(adminInfo.voteId).canEndorse, false);
	}

	@Test
	public void canVote_is_false_in_the_created_vote() {
		assertEquals(voteManager.getVote(adminInfo.voteId).canVote, false);
	}

	@Test
	public void canView_is_false_in_the_created_vote() {
		assertEquals(voteManager.getVote(adminInfo.voteId).canView, false);
	}
}
