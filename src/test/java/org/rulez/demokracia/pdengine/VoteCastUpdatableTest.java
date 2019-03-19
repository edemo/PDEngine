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

	@Before
	public void setUp() throws ReportedException {
		super.setUp();
	}

	@tested_feature("Vote")
	@tested_operation("Cast vote")
	@tested_behaviour("if updatable is true then the cast vote records the proxy id of the user")
	@Test
	public void castVote_records_the_proxy_id_when_updatable_is_true() {
		String ballot = voteManager.obtainBallot(adminInfo.voteId, adminInfo.adminKey);
		List<RankedChoice> theCastVote = new ArrayList<RankedChoice>();
		
		Vote vote = getTheVote();
		vote.canVote = true;
		vote.canUpdate = true;
		vote.votesCast.clear();
		
		voteManager.castVote(adminInfo.voteId, ballot, theCastVote);
		
		assertTrue(vote.votesCast.get(0).proxyId.equals("test_user_in_ws_context"));
	}
	
	@tested_feature("Vote")
	@tested_operation("Cast vote")
	@tested_behaviour("If updatable is false then the cast vote is not associated with the voter")
	@Test
	public void castVote_does_not_record_the_proxy_id_when_updatable_is_false() {
		String ballot = voteManager.obtainBallot(adminInfo.voteId, adminInfo.adminKey);
		List<RankedChoice> theCastVote = new ArrayList<RankedChoice>();
		
		Vote vote = getTheVote();
		vote.canVote = true;
		vote.canUpdate = false;
		vote.votesCast.clear();
		
		voteManager.castVote(adminInfo.voteId, ballot, theCastVote);
		
		assertTrue(vote.votesCast.get(0).proxyId == null);
	}
	
	@tested_feature("Vote")
	@tested_operation("Cast vote")
	@tested_behaviour("If updatable is false then the cast vote is not associated with the voter")
	@Test
	public void castVote_does_not_record_the_proxy_id_when_updatable_is_false_but_it_records_the_other_parameters() {
		String ballot = voteManager.obtainBallot(adminInfo.voteId, adminInfo.adminKey);
		List<RankedChoice> theCastVote = new ArrayList<RankedChoice>();
		
		Vote vote = getTheVote();
		vote.canVote = true;
		vote.canUpdate = false;
		vote.votesCast.clear();
		vote.addCastVote("test_user_in_ws_context", theCastVote, "OtherSecret");
		vote.addCastVote(null, theCastVote, "OtherSecret");
		vote.addCastVote(null, theCastVote, "OtherSecret");
		vote.addCastVote(null, theCastVote, "OtherSecret");
		vote.addCastVote(null, theCastVote, "OtherSecret");
		vote.addCastVote(null, theCastVote, "OtherSecret");
		vote.addCastVote("dummy6", theCastVote, "OtherSecret");
		vote.addCastVote("dummy7", theCastVote, "OtherSecret");
		
		voteManager.castVote(adminInfo.voteId, ballot, theCastVote);
		
		assertTrue(vote.votesCast.get(8).proxyId == null);
	}
}
