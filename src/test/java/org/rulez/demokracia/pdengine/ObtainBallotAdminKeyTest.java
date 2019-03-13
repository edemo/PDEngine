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
	public void adminKey_is_anon_with_different_needed_and_counted_assurances() {
		assertThrows(
				() -> voteManager.obtainBallot(adminInfo.voteId, "anon")
			).assertMessageIs("The counted and needed assurances are different.");
	}
	
	@tested_feature("Manage votes")
	@tested_operation("Obtain ballot")
	@tested_behaviour("if adminKey is anon, the user should have all the neededAssurances")
	@Test
	public void adminKey_is_anon_with_same_needed_and_counted_assurances() {
		Vote vote = voteManager.getVote(adminInfo.voteId);
		vote.countedAssurances.clear();
		vote.neededAssurances.clear();
		
		vote.countedAssurances.add("1");
		vote.countedAssurances.add("2");
		
		vote.neededAssurances.add("1");
		vote.neededAssurances.add("2");
		
		String ballot = voteManager.obtainBallot(adminInfo.voteId, "anon");
		assertTrue(ballot instanceof String);
	}
}
