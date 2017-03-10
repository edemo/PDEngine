package org.rulez.demokracia.pdengine;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.rulez.demokracia.pdengine.annotations.tested_behaviour;
import org.rulez.demokracia.pdengine.annotations.tested_feature;
import org.rulez.demokracia.pdengine.annotations.tested_operation;
import org.rulez.demokracia.pdengine.dataobjects.VoteEntity;
import org.rulez.demokracia.pdengine.exception.ReportedException;
import org.rulez.demokracia.pdengine.testhelpers.CreatedDefaultVoteRegistry;

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
		VoteEntity vote = voteManager.getVote(adminInfo.voteId);
		assertEquals(vote.name, voteName);
	}

	@Test
	@tested_feature("Manage votes")
	@tested_operation("create vote")
	@tested_behaviour("Creates a vote")
	public void a_vote_got_frome_the_registry_two_times_is_the_same() {
		VoteEntity entity1 = voteManager.getVote(adminInfo.voteId);
		VoteEntity entity2 = voteManager.getVote(adminInfo.voteId);
		assertEquals(entity1, entity2);
	}

}
