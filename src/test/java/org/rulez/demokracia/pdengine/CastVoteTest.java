package org.rulez.demokracia.pdengine;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.rulez.demokracia.pdengine.annotations.tested_behaviour;
import org.rulez.demokracia.pdengine.annotations.tested_feature;
import org.rulez.demokracia.pdengine.annotations.tested_operation;
import org.rulez.demokracia.pdengine.dataobjects.CastVote;
import org.rulez.demokracia.pdengine.exception.ReportedException;
import org.rulez.demokracia.pdengine.testhelpers.CreatedDefaultChoice;

public class CastVoteTest extends CreatedDefaultChoice {

	@Before
	public void setUp() throws ReportedException {
		super.setUp();
	}

	@tested_feature("Supporting functionality")
	@tested_operation("CastVote")
	@tested_behaviour("The preferences described by a cast vote can be obtained")
	@Test
	public void the_preferences_can_be_obtained_when_they_are_empty() {
		List<RankedChoice> theCastVote = new ArrayList<RankedChoice>();
		CastVote castVote = new CastVote("test_user_in_ws_context", theCastVote);
		List<RankedChoice> preferences = castVote.getPreferences();
		assertEquals(new ArrayList<>(), preferences);
	}
	
	@tested_feature("Supporting functionality")
	@tested_operation("CastVote")
	@tested_behaviour("The preferences described by a cast vote can be obtained")
	@Test
	public void the_preferences_can_be_obtained_when_they_contain_choices() {
		List<RankedChoice> theCastVote = new ArrayList<RankedChoice>();
		theCastVote.add(new RankedChoice("1", 1));
		CastVote castVote = new CastVote("test_user_in_ws_context", theCastVote);
		List<RankedChoice> preferences = castVote.getPreferences();
		assertEquals(theCastVote, preferences);
	}
}
