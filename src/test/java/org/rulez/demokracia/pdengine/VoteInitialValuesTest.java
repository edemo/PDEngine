package org.rulez.demokracia.pdengine;

import static org.junit.Assert.*;

import org.junit.Test;
import org.rulez.demokracia.pdengine.annotations.tested_behaviour;
import org.rulez.demokracia.pdengine.annotations.tested_feature;
import org.rulez.demokracia.pdengine.annotations.tested_operation;
import org.rulez.demokracia.pdengine.testhelpers.CreatedDefaultVoteRegistry;

@tested_feature("Manage votes")
@tested_operation("create vote")
@tested_behaviour("The vote initially can only be modified with the adminkey")
public class VoteInitialValuesTest extends CreatedDefaultVoteRegistry {


	@Test
	public void canAddin_is_false_in_the_created_vote() {
		assertEquals(false, getTheVote().canAddin);
	}

	@Test
	public void canEndorse_is_false_in_the_created_vote() {
		assertEquals(false, getTheVote().canEndorse);
	}

	@Test
	public void canVote_is_false_in_the_created_vote() {
		assertEquals(false, getTheVote().canVote);
	}

	@Test
	public void canView_is_false_in_the_created_vote() {
		assertEquals(false, getTheVote().canView);
	}
	
	@Test
	public void canUpdate_is_false_in_the_created_vote() {
		assertEquals(false, getTheVote().canUpdate);
	}
}
