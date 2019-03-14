package org.rulez.demokracia.pdengine;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.rulez.demokracia.pdengine.annotations.tested_behaviour;
import org.rulez.demokracia.pdengine.annotations.tested_feature;
import org.rulez.demokracia.pdengine.annotations.tested_operation;
import org.rulez.demokracia.pdengine.exception.ReportedException;
import org.rulez.demokracia.pdengine.testhelpers.CreatedDefaultChoice;

public class ObtainBallotAdminKeyTest extends CreatedDefaultChoice{


	@Before
	public void setUp() throws ReportedException {
		super.setUp();
	}

	@tested_feature("Manage votes")
	@tested_operation("Obtain ballot")
	@tested_behaviour("if adminKey is anon, the user should have all the neededAssurances")
	@Test
	public void adminKey_is_anon_with_not_the_needed_assurances() {
		Vote vote = voteManager.getVote(adminInfo.voteId);
		vote.neededAssurances.clear();
		vote.neededAssurances.add("dontCare");
		assertThrows(
				() -> voteManager.obtainBallot(adminInfo.voteId, "anon")
			).assertMessageIs("The user does not have all of the needed assurances.");
	}
	
	@tested_feature("Manage votes")
	@tested_operation("Obtain ballot")
	@tested_behaviour("if adminKey is anon, the user should have all the neededAssurances")
	@Test
	public void adminKey_is_anon_with_the_proper_assurances() {
		Vote vote = voteManager.getVote(adminInfo.voteId);
		vote.neededAssurances.clear();
		vote.neededAssurances.add("magyar");
		
		String ballot = voteManager.obtainBallot(adminInfo.voteId, "anon");
		assertTrue(ballot instanceof String);
	}
	
	@tested_feature("Manage votes")
	@tested_operation("Obtain ballot")
	@tested_behaviour("if adminKey is anon, the user should have all the neededAssurances")
	@Test
	public void adminKey_is_anon_with_empty_needed_assurences() {
		Vote vote = voteManager.getVote(adminInfo.voteId);
		vote.neededAssurances.clear();
		
		String ballot = voteManager.obtainBallot(adminInfo.voteId, "anon");
		assertTrue(ballot instanceof String);
	}
}
