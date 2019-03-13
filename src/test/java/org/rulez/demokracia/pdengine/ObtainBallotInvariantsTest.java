package org.rulez.demokracia.pdengine;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.ElementCollection;

import org.junit.Before;
import org.junit.Test;
import org.rulez.demokracia.pdengine.annotations.tested_behaviour;
import org.rulez.demokracia.pdengine.annotations.tested_feature;
import org.rulez.demokracia.pdengine.annotations.tested_operation;
import org.rulez.demokracia.pdengine.exception.ReportedException;
import org.rulez.demokracia.pdengine.testhelpers.CreatedDefaultChoice;

public class ObtainBallotInvariantsTest extends CreatedDefaultChoice{

	private Vote vote;
	private String originalVoteId, originalAdminKey;
	private List<String> originalNeededAssurances;
	private List<String> originalCountedAssurances;
	private boolean originalIsPrivate;
	private long originalCreationTime;


	@Before
	public void setUp() throws ReportedException {
		super.setUp();
		originalVoteId = adminInfo.voteId;
		originalAdminKey = adminInfo.adminKey;
		vote = voteManager.getVote(originalVoteId);
		
		originalNeededAssurances = new ArrayList<String>(vote.neededAssurances);
		originalCountedAssurances = new ArrayList<String>(vote.countedAssurances);
		originalIsPrivate = vote.isPrivate;
		originalCreationTime = vote.creationTime;
		
		voteManager.obtainBallot(originalVoteId, originalAdminKey);
	}

	@tested_feature("Manage votes")
	@tested_operation("Obtain ballot")
	@tested_behaviour("vote invariants")
	@Test
	public void check_voteId() {
		assertEquals(originalVoteId, adminInfo.voteId);
	}
	
	@tested_feature("Manage votes")
	@tested_operation("Obtain ballot")
	@tested_behaviour("vote invariants")
	@Test
	public void check_adminKey() {
		assertEquals(originalAdminKey, vote.adminKey);
	}
	
	@tested_feature("Manage votes")
	@tested_operation("Obtain ballot")
	@tested_behaviour("vote invariants")
	@Test
	public void check_neededAssurances() {
		assertEquals(originalNeededAssurances, vote.neededAssurances);
	}
	
	@tested_feature("Manage votes")
	@tested_operation("Obtain ballot")
	@tested_behaviour("vote invariants")
	@Test
	public void check_countedAssurances() {
		assertEquals(originalCountedAssurances, vote.countedAssurances);
	}
	
	@tested_feature("Manage votes")
	@tested_operation("Obtain ballot")
	@tested_behaviour("vote invariants")
	@Test
	public void check_isPrivate() {
		assertEquals(originalIsPrivate, vote.isPrivate);
	}
	
	@tested_feature("Manage votes")
	@tested_operation("Obtain ballot")
	@tested_behaviour("vote invariants")
	@Test
	public void check_creationTime() {
		assertEquals(originalCreationTime, vote.creationTime);
	}
}
