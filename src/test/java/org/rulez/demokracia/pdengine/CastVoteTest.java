package org.rulez.demokracia.pdengine;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.rulez.demokracia.pdengine.annotations.TestedBehaviour;
import org.rulez.demokracia.pdengine.annotations.TestedFeature;
import org.rulez.demokracia.pdengine.annotations.TestedOperation;
import org.rulez.demokracia.pdengine.testhelpers.CreatedDefaultChoice;

@TestedFeature("Supporting functionality")
@TestedOperation("CastVote")
public class CastVoteTest extends CreatedDefaultChoice {

	@Override
	@Before
	public void setUp() {
		super.setUp();
		initializeVoteCastTest();
	}

	@TestedBehaviour("The preferences described by a cast vote can be obtained")
	@Test
	public void the_preferences_can_be_obtained_when_they_are_empty() {
		CastVote castVote = new CastVote(TEST_USER_NAME, theCastVote);
		List<RankedChoice> preferences = castVote.getPreferences();
		assertEquals(new ArrayList<>(), preferences);
	}

	@TestedBehaviour("The preferences described by a cast vote can be obtained")
	@Test
	public void the_preferences_can_be_obtained_when_they_contain_choices() {
		theCastVote.add(new RankedChoice("1", 1));
		CastVote castVote = new CastVote(TEST_USER_NAME, theCastVote);
		List<RankedChoice> preferences = castVote.getPreferences();
		assertEquals(theCastVote, preferences);
	}
	
	@TestedBehaviour("The assurances of the voter can be obtained from a cast vote if canupdateis true")
	@Test
	public void the_assurances_of_the_voter_can_be_obtained_from_a_cast_vote_if_canupdate_is_true() {
		vote.parameters.canUpdate = true;
		CastVote castVote = voteManager.castVote(adminInfo.voteId, ballot, theCastVote);
		assertEquals("TestAssurances", castVote.assurances.get(0));
	}
	
	@TestedBehaviour("The assurances of the voter can be obtained from a cast vote if canupdateis true")
	@Test
	public void the_assurances_of_the_voter_is_null_if_canupdate_is_false() {
		vote.parameters.canUpdate = false;
		CastVote castVote = voteManager.castVote(adminInfo.voteId, ballot, theCastVote);
		List<String> assurances = castVote.getAssurances();
		assertTrue(assurances == null);
	}
}
