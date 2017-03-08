package org.rulez.demokracia.pdengine;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.rulez.demokracia.pdengine.annotations.tested_behaviour;
import org.rulez.demokracia.pdengine.annotations.tested_feature;
import org.rulez.demokracia.pdengine.annotations.tested_operation;

@tested_feature("Manage votes")
@tested_operation("create vote")
@tested_behaviour("vote invariants")
public class InvariantTest extends VoteInvariantCheck {

	private VariantVote vote;

	@Before
	public void Setup() {
		vote = new VariantVote();
		saveInvariables(vote);
	}

	@Test
	public void vote_id_is_invariant() {
		vote.setId("bad");
		assertThrows(() -> checkInvariables());
		vote.setId(savedVoteId);
	}

	@Test
	public void adminKey_is_invariant() {
		vote.setAdminKey("bad");
		assertThrows(() -> checkInvariables());
		vote.setAdminKey(savedAdminKey);
	}

	@Test
	public void neededAssurances_is_invariant() {
		List<String> badAssurances = new ArrayList<String>();
		badAssurances.add("bad");
		vote.setNeededAssurances(badAssurances);
		assertThrows(() -> checkInvariables());
		vote.setNeededAssurances(savedNeededAssurances);
	}

	@Test
	public void countedAssurances_is_invariant() {
		List<String> badAssurances = new ArrayList<String>();
		badAssurances.add("bad");
		vote.setCountedAssurances(badAssurances);
		assertThrows(() -> checkInvariables());
		vote.setCountedAssurances(savedCountedAssurances);
	}

	@Test
	public void isPrivate_is_invariant() {
		vote.setisPrivate(!savedIsPrivate);
		assertThrows(() -> checkInvariables());
		vote.setisPrivate(savedIsPrivate);
	}

	@Test
	public void creationTime_is_invariant() {
		vote.setCreationTime(0);
		assertThrows(() -> checkInvariables());
		vote.setCreationTime(savedCreationTime);
	}

}
