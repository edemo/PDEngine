package org.rulez.demokracia.PDEngine;

import static org.junit.Assert.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.rulez.demokracia.PDEngine.DataObjects.VoteAdminInfo;
import org.rulez.demokracia.PDEngine.annotations.tested_behaviour;
import org.rulez.demokracia.PDEngine.annotations.tested_feature;
import org.rulez.demokracia.PDEngine.annotations.tested_operation;

@tested_feature("Manage votes")
@tested_operation("create vote")
@tested_behaviour("Creates a vote")
public class VoteCreationTest {

	static final String voteName = "Votename";
	private VoteAdminInfo adminInfo;

	@Before
	public void setUp() {
		List<String> neededAssurances = new ArrayList<String>();
		List<String> countedAssurances = new ArrayList<String>();
		boolean isPrivate = true;
		int minEndorsements = 0;
		adminInfo = VoteRegistry.create(voteName,neededAssurances, countedAssurances, isPrivate, minEndorsements);
	}

	@Test
	public void create_creates_a_vote_with_the_given_name() {
		assertEquals(VoteRegistry.getByKey(adminInfo.adminKey).name, voteName);
	}

	@Test
	public void create_creates_a_vote_with_neededAssurances() {
		assertEquals(VoteRegistry.getByKey(adminInfo.adminKey).neededAssurances.size(), 0);
	}
	
	@Test
	public void neededAssurances_contains_the_assurances_of_the_input() {
		List<String> neededAssurances = new ArrayList<String>();
		List<String> countedAssurances= new ArrayList<String>();
		boolean isPrivate = true;
		int minEndorsements = 0;
		neededAssurances.add("magyar");
		VoteAdminInfo secondVote = VoteRegistry.create("test2",neededAssurances, countedAssurances, isPrivate, minEndorsements );
		assertEquals(VoteRegistry.getByKey(secondVote.adminKey).neededAssurances.get(0), "magyar");
	}

	@Test
	public void create_creates_a_vote_with_countedAssurances() {
		assertEquals(VoteRegistry.getByKey(adminInfo.adminKey).countedAssurances.size(), 0);
	}
	
	@Test
	public void countedAssurances_contains_the_assurances_of_the_input() {
		List<String> neededAssurances = new ArrayList<String>();
		List<String> countedAssurances= new ArrayList<String>();
		boolean isPrivate = true;
		int minEndorsements = 0;
		countedAssurances.add("magyar");
		VoteAdminInfo secondVote = VoteRegistry.create("test2",neededAssurances, countedAssurances, isPrivate, minEndorsements );
		assertEquals(VoteRegistry.getByKey(secondVote.adminKey).countedAssurances.get(0), "magyar");
	}
	
	@Test
	public void create_creates_a_vote_with_isPrivate() {
		assertEquals(VoteRegistry.getByKey(adminInfo.adminKey).isPrivate, true);
	}

	@Test
	public void isPrivate_is_the_same_what_is_given_in_create() {
		List<String> neededAssurances = new ArrayList<String>();
		List<String> countedAssurances= new ArrayList<String>();
		boolean isPrivate = false;
		int minEndorsements = 0;
		VoteAdminInfo secondVote = VoteRegistry.create("test2",neededAssurances, countedAssurances, isPrivate, minEndorsements );
		assertEquals(VoteRegistry.getByKey(secondVote.adminKey).isPrivate, false);
	}

	@Test
	public void create_creates_a_vote_with_voteId() {
		assertNotNull(adminInfo.voteId);
		assertEquals(VoteRegistry.getByKey(adminInfo.adminKey).voteId, adminInfo.voteId);
	}

	@Test
	public void create_creates_a_vote_with_creationTime() {
		List<String> neededAssurances = new ArrayList<String>();
		List<String> countedAssurances= new ArrayList<String>();
		boolean isPrivate = false;
		int minEndorsements = 0;
		Instant before = Instant.now();
		VoteAdminInfo secondVote = VoteRegistry.create("test2",neededAssurances, countedAssurances, isPrivate, minEndorsements );
		Instant after = Instant.now();
		long creationTime = VoteRegistry.getByKey(secondVote.adminKey).creationTime;
		assertTrue(creationTime >= before.getEpochSecond());
		assertTrue(creationTime <= after.getEpochSecond());
	}

	@Test
	public void create_creates_a_vote_with_minEndorsements() {
		assertEquals(VoteRegistry.getByKey(adminInfo.adminKey).minEndorsements, 0);
	}

	@Test
	public void minEndorsements_is_the_same_what_is_given_in_create() {
		List<String> neededAssurances = new ArrayList<String>();
		List<String> countedAssurances= new ArrayList<String>();
		boolean isPrivate = false;
		int minEndorsements = 42;
		VoteAdminInfo secondVote = VoteRegistry.create("test2",neededAssurances, countedAssurances, isPrivate, minEndorsements );
		assertEquals(VoteRegistry.getByKey(secondVote.adminKey).minEndorsements, 42);
	}

	@Test
	public void create_creates_a_vote_with_adminKey() {
		assertNotNull(adminInfo.adminKey);
		assertEquals(VoteRegistry.getByKey(adminInfo.adminKey).adminKey, adminInfo.adminKey);
	}

}
