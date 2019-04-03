package org.rulez.demokracia.pdengine;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.rulez.demokracia.pdengine.annotations.TestedBehaviour;
import org.rulez.demokracia.pdengine.annotations.TestedFeature;
import org.rulez.demokracia.pdengine.annotations.TestedOperation;
import org.rulez.demokracia.pdengine.testhelpers.CreatedDefaultChoice;

@TestedFeature("Unimplemented")
@TestedOperation("Unimplemented")
@TestedBehaviour("Unimplemented")
public class UnimplementedTests extends CreatedDefaultChoice {
	
	@Test
	public void the_empty_assurances_can_be_obtained() {
		CastVote castVote = new CastVote("test_user_in_ws_context", new ArrayList<>(), new ArrayList<>());
		List<String> assurances = castVote.getAssurances();
		assertTrue(assurances.isEmpty());
	}
	
	@Test
	public void the_not_empty_assurances_can_be_obtained() {
		List<String> assurances = new ArrayList<String> ();
		assurances.add("TestAssurance");
		CastVote castVote = new CastVote("test_user_in_ws_context", new ArrayList<>(), assurances);
		assertTrue(castVote.getAssurances().contains("TestAssurance"));
	}

	@Test
	public void the_calculateWinners_method_is_not_implemented_yet() {
		assertUnimplemented(() -> new WinnerCalculatorImpl().calculateWinners(null));
	}
}
