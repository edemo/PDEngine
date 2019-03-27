package org.rulez.demokracia.pdengine;

import static org.junit.Assert.*;

import java.time.Instant;

import org.junit.Before;
import org.junit.Test;
import org.rulez.demokracia.pdengine.annotations.TestedBehaviour;
import org.rulez.demokracia.pdengine.annotations.TestedFeature;
import org.rulez.demokracia.pdengine.annotations.TestedOperation;
import org.rulez.demokracia.pdengine.dataobjects.VoteAdminInfo;
import org.rulez.demokracia.pdengine.testhelpers.CreatedDefaultVoteRegistry;

@TestedFeature("Manage votes")
@TestedOperation("create vote")
@TestedBehaviour("Creates a vote")
public class VoteCreationTest extends CreatedDefaultVoteRegistry{

	@Before
	public void setUp() {
		super.setUp();
	}

	@Test
	public void create_creates_a_vote_with_the_given_name() {
		assertEquals(voteName,voteManager.getVote(adminInfo.voteId).name);
	}

	@Test
	public void create_creates_a_vote_with_neededAssurances() {
		assertEquals(neededAssurances.size(), voteManager.getVote(adminInfo.voteId).neededAssurances.size());
	}
	
	@Test
	public void neededAssurances_contains_the_assurances_of_the_input() {
		VoteAdminInfo secondVote = createAVote();
		assertEquals(ASSURANCE_NAME, voteManager.getVote(secondVote.voteId).neededAssurances.get(0));
	}

	@Test
	public void create_creates_a_vote_with_countedAssurances() {
		assertEquals(0, voteManager.getVote(adminInfo.voteId).countedAssurances.size());
	}
	
	@Test
	public void countedAssurances_contains_the_assurances_of_the_input() {
		countedAssurances.add(ASSURANCE_NAME);
		VoteAdminInfo secondVote = createAVote();
		assertEquals(ASSURANCE_NAME, voteManager.getVote(secondVote.voteId).countedAssurances.get(0));
	}
	
	@Test
	public void create_creates_a_vote_with_isPrivate() {
		assertEquals(true, voteManager.getVote(adminInfo.voteId).isPrivate);
	}

	@Test
	public void isPrivate_is_the_same_what_is_given_in_create() {
		isPrivate = false;
		VoteAdminInfo secondVote = createAVote();
		assertEquals(false, voteManager.getVote(secondVote.voteId).isPrivate);
	}

	@Test
	public void create_creates_a_vote_with_voteId() {
		assertEquals(adminInfo.voteId, voteManager.getVote(adminInfo.voteId).id);
	}

	@Test
	public void create_creates_a_vote_with_creationTime() {
		Instant before = Instant.now();
		VoteAdminInfo secondVote = createAVote();
		Instant after = Instant.now();
		long creationTime = voteManager.getVote(secondVote.voteId).creationTime;
		assertBetweenInstants(creationTime, before, after);
	}

	private void assertBetweenInstants(final long creationTime, final Instant before, final Instant after) {
		assertTrue(creationTime >= before.getEpochSecond());
		assertTrue(creationTime <= after.getEpochSecond());
	}

	@Test
	public void create_creates_a_vote_with_minEndorsements() {
		assertEquals(voteManager.getVote(adminInfo.voteId).parameters.minEndorsements, 0);
	}

	@Test
	public void minEndorsements_is_the_same_what_is_given_in_create() {
		minEndorsements = 42;
		VoteAdminInfo secondVote = createAVote();
		assertEquals(42, voteManager.getVote(secondVote.voteId).parameters.minEndorsements);
	}

	@Test
	public void create_creates_a_vote_with_adminKey() {
		assertEquals(adminInfo.adminKey, voteManager.getVote(adminInfo.voteId).adminKey);
	}
}
