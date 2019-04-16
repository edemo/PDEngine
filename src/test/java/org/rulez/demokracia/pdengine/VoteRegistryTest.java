package org.rulez.demokracia.pdengine;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.rulez.demokracia.pdengine.annotations.TestedBehaviour;
import org.rulez.demokracia.pdengine.annotations.TestedFeature;
import org.rulez.demokracia.pdengine.annotations.TestedOperation;
import org.rulez.demokracia.pdengine.dataobjects.VoteEntity;
import org.rulez.demokracia.pdengine.testhelpers.CreatedDefaultVoteRegistry;

public class VoteRegistryTest extends CreatedDefaultVoteRegistry {

	@Override
	@Before
	public void setUp() {
		super.setUp();
	}


	@TestedFeature("Manage votes")
	@TestedOperation("create vote")
	@TestedBehaviour("Creates a vote")
	@Test
	public void create_creates_a_vote_with_the_given_name() {
		VoteEntity vote = voteManager.getVote(adminInfo.voteId);
		assertEquals(vote.getName(), voteName);
	}

	@Test
	@TestedFeature("Manage votes")
	@TestedOperation("create vote")
	@TestedBehaviour("Creates a vote")
	public void a_vote_got_frome_the_registry_two_times_is_the_same() {
		VoteEntity entity1 = voteManager.getVote(adminInfo.voteId);
		VoteEntity entity2 = voteManager.getVote(adminInfo.voteId);
		assertEquals(entity1, entity2);
	}

}
