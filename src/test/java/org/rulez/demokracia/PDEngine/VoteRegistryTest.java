package org.rulez.demokracia.PDEngine;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.rulez.demokracia.PDEngine.DataObjects.Vote;
import org.rulez.demokracia.PDEngine.annotations.tested_behaviour;
import org.rulez.demokracia.PDEngine.annotations.tested_feature;
import org.rulez.demokracia.PDEngine.annotations.tested_operation;
import org.rulez.demokracia.PDEngine.exception.ReportedException;
import org.rulez.demokracia.PDEngine.testhelpers.CreatedDefaultVoteRegistry;

public class VoteRegistryTest extends CreatedDefaultVoteRegistry {

	@Before
	public void setUp() throws ReportedException {
		super.setUp();
	}


	@Test
	@tested_feature("Manage votes")
	@tested_operation("create vote")
	@tested_behaviour("Creates a vote")
	public void create_creates_a_vote_with_the_given_name() {
		Vote vote = voteManager.getVote(adminInfo.getVoteId());
		assertEquals(vote.name, voteName);
	}

	@Test
	@tested_feature("Manage votes")
	@tested_operation("create vote")
	@tested_behaviour("Creates a vote")
	public void a_vote_got_frome_the_registry_two_times_is_the_same() {
		Vote entity1 = voteManager.getVote(adminInfo.getVoteId());
		Vote entity2 = voteManager.getVote(adminInfo.getVoteId());
		assertEquals(entity1, entity2);
	}

}
