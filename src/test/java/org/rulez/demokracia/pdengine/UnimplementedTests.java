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
	public void a() {
		CastVote castVote = new CastVote("test_user_in_ws_context", new ArrayList<>(), new ArrayList<>());
		List<String> assurances = castVote.getAssurances();
		assertTrue(assurances.isEmpty());
	}
	
	@Test
	public void b() {
		List<String> assurances = new ArrayList<String> ();
		assurances.add("TestAssurance");
		CastVote castVote = new CastVote("test_user_in_ws_context", new ArrayList<>(), assurances);
		assertTrue(castVote.getAssurances().contains("TestAssurance"));
	}
}
