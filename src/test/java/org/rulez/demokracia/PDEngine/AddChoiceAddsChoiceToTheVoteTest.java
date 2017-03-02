package org.rulez.demokracia.PDEngine;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.rulez.demokracia.PDEngine.annotations.tested_behaviour;
import org.rulez.demokracia.PDEngine.annotations.tested_feature;
import org.rulez.demokracia.PDEngine.annotations.tested_operation;
import org.rulez.demokracia.PDEngine.testhelpers.CreatedDefaultChoice;

@tested_feature("Manage votes")
@tested_operation("Add choice")
public class AddChoiceAddsChoiceToTheVoteTest extends CreatedDefaultChoice{

	@Before
	public void setUp() {
		super.setUp();
		addTestChoice();
	}

	@tested_behaviour("registers the choice with the vote")
	@Test
	public void created_choice_is_registered_with_the_vote() {
		assertEquals(vote.getChoice(choiceId).getName(), "choice1");
	}

	@tested_behaviour("registers the user with the choice")
	@Test
	public void creating_user_is_registered_with_the_choice() {
		assertEquals(vote.getChoice(choiceId).getUser(), "user");
	}

	@tested_behaviour("registers the user with the choice")
	@Test
	public void if_no_user_then_null_is_Registered() {
		String myChoiceId = vote.addChoice("choice1", null);
		assertEquals(vote.getChoice(myChoiceId).getUser(), null);
	}

	@tested_behaviour("returns a unique choice id")
	@Test
	public void choice_ids_are_unique() {
		Set<String> existingIds = new HashSet<String>();
		for(int i=0;i<100;i++) {
			String myChoiceId = vote.addChoice("choice1", null);
			assertFalse(existingIds.contains(myChoiceId));
			existingIds.add(myChoiceId);
		}
	}
}
