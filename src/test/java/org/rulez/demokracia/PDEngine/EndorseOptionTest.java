package org.rulez.demokracia.PDEngine;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import org.rulez.demokracia.PDEngine.annotations.tested_behaviour;
import org.rulez.demokracia.PDEngine.annotations.tested_feature;
import org.rulez.demokracia.PDEngine.annotations.tested_operation;
import org.rulez.demokracia.PDEngine.testhelpers.CreatedDefaultChoice;

public class EndorseOptionTest extends CreatedDefaultChoice{

	@Before
	public void setUp() {
		super.setUp();
	}

	@tested_feature("Vote")
	@tested_operation("Endorse option")
	@tested_behaviour("if adminKey is not user, the userName is registered as endorserName for the choice")
	@Test
	public void endorsement_is_registered() {
		String userName="testuser";
		String adminKey=this.adminInfo.adminKey;
		String choiceId = this.choiceId;
		vote.endorseChoice(adminKey, choiceId, userName);
		assertTrue(vote.getChoice(choiceId).endorsers.contains(userName));
	}

}
