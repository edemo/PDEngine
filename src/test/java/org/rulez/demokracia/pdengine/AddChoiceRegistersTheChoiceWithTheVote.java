package org.rulez.demokracia.pdengine;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.rulez.demokracia.pdengine.annotations.tested_behaviour;
import org.rulez.demokracia.pdengine.annotations.tested_feature;
import org.rulez.demokracia.pdengine.annotations.tested_operation;
import org.rulez.demokracia.pdengine.dataobjects.ChoiceEntity;
import org.rulez.demokracia.pdengine.exception.ReportedException;
import org.rulez.demokracia.pdengine.testhelpers.CreatedDefaultChoice;

@tested_feature("Manage votes")
@tested_operation("Add choice")
@tested_behaviour("registers the choice with the vote")
public class AddChoiceRegistersTheChoiceWithTheVote extends CreatedDefaultChoice{

	private ChoiceEntity choice;

	@Before
	public void setUp() throws ReportedException {
		super.setUp();
		String theChoice = choiceId;
		choice = getChoice(theChoice);
	}

	
	@Test
	public void created_choice_is_registered_with_the_vote() {
		assertEquals(
				"choice1",
				choice.name);
	}
}
