package org.rulez.demokracia.pdengine;

import static org.junit.Assert.*;

import java.time.Instant;

import org.junit.Before;
import org.junit.Test;
import org.rulez.demokracia.pdengine.annotations.tested_behaviour;
import org.rulez.demokracia.pdengine.annotations.tested_feature;
import org.rulez.demokracia.pdengine.annotations.tested_operation;
import org.rulez.demokracia.pdengine.dataobjects.VoteAdminInfo;
import org.rulez.demokracia.pdengine.exception.ReportedException;
import org.rulez.demokracia.pdengine.testhelpers.CreatedDefaultVoteRegistry;

@tested_feature("Manage votes")
@tested_operation("create vote")
@tested_behaviour("Creates a vote")
public class VoteCreationTest extends CreatedDefaultVoteRegistry{

	@Before
	public void setUp() throws ReportedException {
		super.setUp();
	}

	@tested_feature("Manage votes")
	@tested_operation("create vote")
	@tested_behaviour("Creates a vote")
	@Test
	public void create_creates_a_vote_with_the_given_name() {
		assertEquals(voteName,voteManager.getVote(adminInfo.voteId).name);
	}

	@tested_feature("Manage votes")
	@tested_operation("create vote")
	@tested_behaviour("Creates a vote")
	@Test
	public void create_creates_a_vote_with_neededAssurances() {
		assertEquals(neededAssurances.size(), voteManager.getVote(adminInfo.voteId).neededAssurances.size());
	}
	
	@tested_feature("Manage votes")
	@tested_operation("create vote")
	@tested_behaviour("Creates a vote")
	@Test
	public void neededAssurances_contains_the_assurances_of_the_input() throws ReportedException {
		VoteAdminInfo secondVote = createAVote();
		assertEquals("magyar", voteManager.getVote(secondVote.voteId).neededAssurances.get(0));
	}

	@tested_feature("Manage votes")
	@tested_operation("create vote")
	@tested_behaviour("Creates a vote")
	@Test
	public void create_creates_a_vote_with_countedAssurances() {
		assertEquals(0, voteManager.getVote(adminInfo.voteId).countedAssurances.size());
	}
	
	@tested_feature("Manage votes")
	@tested_operation("create vote")
	@tested_behaviour("Creates a vote")
	@Test
	public void countedAssurances_contains_the_assurances_of_the_input() throws ReportedException {
		countedAssurances.add("magyar");
		VoteAdminInfo secondVote = createAVote();
		assertEquals("magyar", voteManager.getVote(secondVote.voteId).countedAssurances.get(0));
	}
	
	@tested_feature("Manage votes")
	@tested_operation("create vote")
	@tested_behaviour("Creates a vote")
	@Test
	public void create_creates_a_vote_with_isPrivate() {
		assertEquals(true, voteManager.getVote(adminInfo.voteId).isPrivate);
	}

	@tested_feature("Manage votes")
	@tested_operation("create vote")
	@tested_behaviour("Creates a vote")
	@Test
	public void isPrivate_is_the_same_what_is_given_in_create() throws ReportedException {
		isPrivate = false;
		VoteAdminInfo secondVote = createAVote();
		assertEquals(false, voteManager.getVote(secondVote.voteId).isPrivate);
	}

	@tested_feature("Manage votes")
	@tested_operation("create vote")
	@tested_behaviour("Creates a vote")
	@Test
	public void create_creates_a_vote_with_voteId() {
		assertNotNull(adminInfo.voteId);
		assertEquals(adminInfo.voteId, voteManager.getVote(adminInfo.voteId).id);
	}

	@tested_feature("Manage votes")
	@tested_operation("create vote")
	@tested_behaviour("Creates a vote")
	@Test
	public void create_creates_a_vote_with_creationTime() throws ReportedException {
		Instant before = Instant.now();
		VoteAdminInfo secondVote = createAVote();
		Instant after = Instant.now();
		long creationTime = voteManager.getVote(secondVote.voteId).creationTime;
		assertTrue(creationTime >= before.getEpochSecond());
		assertTrue(creationTime <= after.getEpochSecond());
	}

	@tested_feature("Manage votes")
	@tested_operation("create vote")
	@tested_behaviour("Creates a vote")
	@Test
	public void create_creates_a_vote_with_minEndorsements() {
		assertEquals(voteManager.getVote(adminInfo.voteId).minEndorsements, 0);
	}

	@tested_feature("Manage votes")
	@tested_operation("create vote")
	@tested_behaviour("Creates a vote")
	@Test
	public void minEndorsements_is_the_same_what_is_given_in_create() throws ReportedException {
		minEndorsements = 42;
		VoteAdminInfo secondVote = createAVote();
		assertEquals(42, voteManager.getVote(secondVote.voteId).minEndorsements);
	}

	@tested_feature("Manage votes")
	@tested_operation("create vote")
	@tested_behaviour("Creates a vote")
	@Test
	public void create_creates_a_vote_with_adminKey() {
		assertNotNull(adminInfo.voteId);
		assertEquals(adminInfo.adminKey, voteManager.getVote(adminInfo.voteId).adminKey);
	}
}
