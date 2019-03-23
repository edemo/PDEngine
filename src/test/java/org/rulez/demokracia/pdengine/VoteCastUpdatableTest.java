package org.rulez.demokracia.pdengine;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.rulez.demokracia.pdengine.annotations.TestedBehaviour;
import org.rulez.demokracia.pdengine.annotations.TestedFeature;
import org.rulez.demokracia.pdengine.annotations.TestedOperation;
import org.rulez.demokracia.pdengine.dataobjects.CastVote;
import org.rulez.demokracia.pdengine.exception.ReportedException;
import org.rulez.demokracia.pdengine.testhelpers.CreatedDefaultChoice;

public class VoteCastUpdatableTest extends CreatedDefaultChoice {

	private String ballot;
	private List<RankedChoice> theCastVote;
	private Vote vote;
	
	@Before
	public void setUp() throws ReportedException {
		super.setUp();
		
		ballot = voteManager.obtainBallot(adminInfo.voteId, adminInfo.adminKey);
		theCastVote = new ArrayList<>();
		
		vote = getTheVote();
		vote.canVote = true;
		vote.votesCast.clear();
	}

	@TestedFeature("Vote")
	@TestedOperation("Cast vote")
	@TestedBehaviour("if updatable is true then the cast vote records the proxy id of the user")
	@Test
	public void castVote_records_the_proxy_id_when_updatable_is_true() {
		vote.canUpdate = true;
		voteManager.castVote(adminInfo.voteId, ballot, theCastVote);
		
		assertTrue(vote.votesCast.get(0).proxyId.equals(TEST_USER_NAME));
	}
	
	@TestedFeature("Vote")
	@TestedOperation("Cast vote")
	@TestedBehaviour("If updatable is false then the cast vote is not associated with the voter")
	@Test
	public void castVote_does_not_record_the_proxy_id_when_updatable_is_false() {
		vote.canUpdate = false;
		
		voteManager.castVote(adminInfo.voteId, ballot, theCastVote);
		assertTrue(vote.votesCast.get(0).proxyId == null);
	}
	
	@TestedFeature("Vote")
	@TestedOperation("Cast vote")
	@TestedBehaviour("If updatable is false then the cast vote is not associated with the voter")
	@Test
	public void castVote_does_not_record_the_proxy_id_when_updatable_is_false_and_it_does_not_delete_the_other_not_recorded_proxyIds_votescast() {
		vote.canUpdate = false;
		vote.votesCast.add(new CastVote(TEST_USER_NAME, theCastVote));
		vote.votesCast.add(new CastVote(null, theCastVote));
		vote.votesCast.add(new CastVote(null, theCastVote));
		vote.votesCast.add(new CastVote(null, theCastVote));
		vote.votesCast.add(new CastVote(null, theCastVote));
		vote.votesCast.add(new CastVote(null, theCastVote));
		vote.votesCast.add(new CastVote("dummy6", theCastVote));
		vote.votesCast.add(new CastVote("dummy7", theCastVote));
		
		voteManager.castVote(adminInfo.voteId, ballot, theCastVote);
		
		assertTrue(vote.votesCast.get(8).proxyId == null);
	}
	
}
