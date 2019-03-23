package org.rulez.demokracia.pdengine;

import static org.junit.Assert.fail;

import org.junit.Test;
import org.rulez.demokracia.pdengine.annotations.TestedBehaviour;
import org.rulez.demokracia.pdengine.annotations.TestedFeature;
import org.rulez.demokracia.pdengine.annotations.TestedOperation;
import org.rulez.demokracia.pdengine.testhelpers.CreatedDefaultVoteRegistry;

public class ForShowVoteTheUserNeedsACountedAssurance extends CreatedDefaultVoteRegistry {
	
	@Test
	@TestedFeature("Manage votes")
	@TestedOperation("show vote")
	@TestedBehaviour("if adminKey is anon, the user should have any of the countedAssurances")
	public void create_creates_a_vote_with_countedAssurances() {
		
		Vote vote = getTheVote();
		vote.adminKey = "user";
		vote.countedAssurances.add(ASSURANCE_NAME);
		if(!("user".equals(vote.adminKey) 
				&& vote.neededAssurances.containsAll(countedAssurances))) {
			fail("anon admin key without countedAssurances");
		}	
	}
}
