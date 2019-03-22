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

public class VoteCastUpdatableTest extends CreatedDefaultChoice {

	String ballot;
	List<RankedChoice> theCastVote;
	Vote vote;
	
	@Before
	public void setUp() throws ReportedException {
		super.setUp();
		
		ballot = voteManager.obtainBallot(adminInfo.voteId, adminInfo.adminKey);
		theCastVote = new ArrayList<RankedChoice>();
		
		vote = getTheVote();
		vote.canVote = true;
		vote.votesCast.clear();
	}

	@tested_feature("Vote")
	@tested_operation("Cast vote")
	@tested_behaviour("if updatable is true then the cast vote records the proxy id of the user")
	@Test
	public void castVote_records_the_proxy_id_when_updatable_is_true() {
		vote.canUpdate = true;
		voteManager.castVote(adminInfo.voteId, ballot, theCastVote);
		
		assertTrue(vote.votesCast.get(0).proxyId.equals("test_user_in_ws_context"));
	}
	
	@tested_feature("Vote")
	@tested_operation("Cast vote")
	@tested_behaviour("If updatable is false then the cast vote is not associated with the voter")
	@Test
	public void castVote_does_not_record_the_proxy_id_when_updatable_is_false() {
		vote.canUpdate = false;
		
		voteManager.castVote(adminInfo.voteId, ballot, theCastVote);
		assertTrue(vote.votesCast.get(0).proxyId == null);
	}
	
	@tested_feature("Vote")
	@tested_operation("Cast vote")
	@tested_behaviour("If updatable is false then the cast vote is not associated with the voter")
	@Test
	public void castVote_does_not_record_the_proxy_id_when_updatable_is_false_and_it_does_not_delete_the_other_not_recorded_proxyIds_votescast() {
		vote.canUpdate = false;
		vote.votesCast.add(new CastVote("test_user_in_ws_context", theCastVote));
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
