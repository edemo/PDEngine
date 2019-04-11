package org.rulez.demokracia.pdengine;

import static org.junit.Assert.fail;

import org.junit.Test;
import org.rulez.demokracia.pdengine.annotations.tested_behaviour;
import org.rulez.demokracia.pdengine.annotations.tested_feature;
import org.rulez.demokracia.pdengine.annotations.tested_operation;
import org.rulez.demokracia.pdengine.exception.ReportedException;
import org.rulez.demokracia.pdengine.testhelpers.CreatedDefaultVoteRegistry;

public class ForShowVoteTheUserNeedsACountedAssurance extends CreatedDefaultVoteRegistry {
	
	public ForShowVoteTheUserNeedsACountedAssurance() {
		super();
	}

	@Test
	@tested_feature("Manage votes")
	@tested_operation("show vote")
	@tested_behaviour("if adminKey is anon, the user should have any of the countedAssurances")
	public void create_creates_a_vote_with_countedAssurances() throws ReportedException {
		
		Vote vote = getTheVote();
		vote.adminKey = "user";
		vote.countedAssurances.add("magyar");
		if(!(vote.adminKey.equals("user") 
				&& vote.neededAssurances.containsAll(countedAssurances))) {
			fail("anon admin key without countedAssurances");
		}	
	}
}
