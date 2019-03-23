package org.rulez.demokracia.pdengine;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;
import org.rulez.demokracia.pdengine.annotations.TestedBehaviour;
import org.rulez.demokracia.pdengine.annotations.TestedFeature;
import org.rulez.demokracia.pdengine.annotations.TestedOperation;
import org.rulez.demokracia.pdengine.testhelpers.VoteInvariantCheck;
import org.rulez.demokracia.pdengine.testhelpers.VariantVote;

@TestedFeature("Manage votes")
@TestedOperation("create vote")
@TestedBehaviour("vote invariants")
public class InvariantTest extends VoteInvariantCheck {

	private static final String BAD = "bad";
	private VariantVote vote;

	@Before
	public void setUp() {
		vote = new VariantVote();
		saveInvariables(vote);
	}

	@Test
	public void vote_id_is_invariant() {
		vote.id=BAD;
		assertInvariantViolation();
		vote.id=savedVoteId;
	}

	private void assertInvariantViolation() {
		try {
			checkInvariables();
		} catch(AssertionError e ) {
			return;
		}
		fail("missing invariant violation");
	}

	@Test
	public void adminKey_is_invariant() {
		vote.adminKey=BAD;
		assertInvariantViolation();
		vote.adminKey=savedAdminKey;
	}

	@Test
	public void neededAssurances_is_invariant() {
		ArrayList<String> badAssurances = new ArrayList<>();
		badAssurances.add(BAD);
		vote.neededAssurances = badAssurances;
		assertInvariantViolation();
		vote.neededAssurances = savedNeededAssurances;
	}

	@Test
	public void countedAssurances_is_invariant() {
		ArrayList<String> badAssurances = new ArrayList<>();
		badAssurances.add(BAD);
		vote.countedAssurances = badAssurances;
		assertInvariantViolation();
		vote.countedAssurances = savedCountedAssurances;
	}

	@Test
	public void isPrivate_is_invariant() {
		vote.isPrivate=!savedIsPrivate;
		assertInvariantViolation();
		vote.isPrivate=savedIsPrivate;
	}

	@Test
	public void creationTime_is_invariant() {
		vote.creationTime=0;
		assertInvariantViolation();
		vote.creationTime=savedCreationTime;
	}

}
