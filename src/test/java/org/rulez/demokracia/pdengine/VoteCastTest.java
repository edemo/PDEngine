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

public class VoteCastTest extends CreatedDefaultChoice {

	@Before
	public void setUp() throws ReportedException {
		super.setUp();
	}

	@tested_feature("Vote")
	@tested_operation("Cast vote")
	@tested_behaviour("records cast vote with the vote and user's proxy id")
	@Test
	public void proxyId_check_when_cast_vote_records_the_vote_and_users_proxy() {
		String ballot = voteManager.obtainBallot(adminInfo.voteId, adminInfo.adminKey);
		List<RankedChoice> theCastVote = new ArrayList<RankedChoice>();
		Vote vote = getTheVote();
		vote.canVote = true;
		vote.votesCast.clear();
		vote.canUpdate = true;
		voteManager.castVote(adminInfo.voteId, ballot, theCastVote);
		CastVote voteCast = new CastVote("test_user_in_ws_context", theCastVote);
		assertEquals(voteCast.proxyId, vote.votesCast.get(0).proxyId);
	}
	
	@tested_feature("Vote")
	@tested_operation("Cast vote")
	@tested_behaviour("records cast vote with the vote and user's proxy id")
	@Test
	public void voteCast_check_when_cast_vote_records_the_vote_and_users_proxy() {
		String ballot = voteManager.obtainBallot(adminInfo.voteId, adminInfo.adminKey);
		List<RankedChoice> theCastVote = new ArrayList<RankedChoice>();
		Vote vote = getTheVote();
		vote.canVote = true;
		vote.votesCast.clear();
		voteManager.castVote(adminInfo.voteId, ballot, theCastVote);
		CastVote voteCast = new CastVote("test_user_in_ws_context", theCastVote);
		assertTrue(voteCast.preferences.containsAll(vote.votesCast.get(0).preferences));
	}
	
	@tested_feature("Vote")
	@tested_operation("Cast vote")
	@tested_behaviour("Cast vote have a secret random identifier")
	@Test
	public void secretId_check_when_cast_vote_records_the_vote_and_users_proxy() {
		String ballot = voteManager.obtainBallot(adminInfo.voteId, adminInfo.adminKey);
		List<RankedChoice> theCastVote = new ArrayList<RankedChoice>();
		Vote vote = getTheVote();
		vote.canVote = true;
		vote.votesCast.clear();
		voteManager.castVote(adminInfo.voteId, ballot, theCastVote);
		assertTrue(vote.votesCast.get(0).secretId instanceof String);
	}
	
	@tested_feature("Vote")
	@tested_operation("Cast vote")
	@tested_behaviour("if there was a cast vote from the same user, the old one is deleted")
	@Test
	public void cast_vote_records_the_vote_with_same_user_votesCast_when_same_user_is_in_the_top_of_the_list() {
		String ballot = voteManager.obtainBallot(adminInfo.voteId, adminInfo.adminKey);
		List<RankedChoice> theCastVote = new ArrayList<RankedChoice>();
		Vote vote = getTheVote();
		vote.canVote = true;
		vote.canUpdate = true;
		
		vote.votesCast.clear();
		vote.votesCast.add(new CastVote("test_user_in_ws_context", theCastVote));
		vote.votesCast.add(new CastVote("dummy1", theCastVote));
		vote.votesCast.add(new CastVote("dummy2", theCastVote));
		vote.votesCast.add(new CastVote("dummy3", theCastVote));
		vote.votesCast.add(new CastVote("dummy4", theCastVote));
		vote.votesCast.add(new CastVote("dummy5", theCastVote));
		vote.votesCast.add(new CastVote("dummy6", theCastVote));
		vote.votesCast.add(new CastVote("dummy7", theCastVote));
		
		voteManager.castVote(adminInfo.voteId, ballot, theCastVote);
		
		assertTrue(vote.votesCast.get(7).proxyId.equals("test_user_in_ws_context"));
	}
	
	@tested_feature("Vote")
	@tested_operation("Cast vote")
	@tested_behaviour("if there was a cast vote from the same user, the old one is deleted")
	@Test
	public void cast_vote_records_the_vote_with_same_user_votesCast_when_same_user_is_in_the_bottom_of_the_list() {
		String ballot = voteManager.obtainBallot(adminInfo.voteId, adminInfo.adminKey);
		List<RankedChoice> theCastVote = new ArrayList<RankedChoice>();
		List<RankedChoice> theCastVote1 = new ArrayList<RankedChoice>();
		Vote vote = getTheVote();
		vote.canVote = true;
		vote.canUpdate = true;
		
		vote.votesCast.clear();
		vote.votesCast.add(new CastVote("dummy1", theCastVote));
		vote.votesCast.add(new CastVote("dummy2", theCastVote));
		vote.votesCast.add(new CastVote("dummy3", theCastVote));
		vote.votesCast.add(new CastVote("dummy4", theCastVote));
		vote.votesCast.add(new CastVote("dummy5", theCastVote));
		vote.votesCast.add(new CastVote("dummy6", theCastVote));
		vote.votesCast.add(new CastVote("dummy7", theCastVote));
		vote.votesCast.add(new CastVote("test_user_in_ws_context", theCastVote1));
		
		voteManager.castVote(adminInfo.voteId, ballot, theCastVote);

		assertTrue(vote.votesCast.get(7).preferences.equals(theCastVote1));
	}
	
	@tested_feature("Vote")
	@tested_operation("Cast vote")
	@tested_behaviour("if there was a cast vote from the same user, the old one is deleted")
	@Test
	public void cast_vote_records_the_vote_with_same_user_votesCast_when_same_user_is_in_the_middle_of_the_list() {
		String ballot = voteManager.obtainBallot(adminInfo.voteId, adminInfo.adminKey);
		List<RankedChoice> theCastVote = new ArrayList<RankedChoice>();
		Vote vote = getTheVote();
		vote.canVote = true;
		vote.canUpdate = true;
		
		vote.votesCast.clear();
		vote.votesCast.add(new CastVote("dummy1", theCastVote));
		vote.votesCast.add(new CastVote("dummy2", theCastVote));
		vote.votesCast.add(new CastVote("dummy3", theCastVote));
		vote.votesCast.add(new CastVote("test_user_in_ws_context", theCastVote));
		vote.votesCast.add(new CastVote("dummy4", theCastVote));
		vote.votesCast.add(new CastVote("dummy5", theCastVote));
		vote.votesCast.add(new CastVote("dummy6", theCastVote));
		vote.votesCast.add(new CastVote("dummy7", theCastVote));
		
		voteManager.castVote(adminInfo.voteId, ballot, theCastVote);

		assertTrue(vote.votesCast.get(7).proxyId.equals("test_user_in_ws_context"));
	}
	
	@tested_feature("Vote")
	@tested_operation("Cast vote")
	@tested_behaviour("if there was a cast vote from the same user, the old one is deleted")
	@Test 
	public void cast_vote_records_the_vote_with_same_user_votesCast_when_the_list_does_not_contain_same_user() {
		String ballot = voteManager.obtainBallot(adminInfo.voteId, adminInfo.adminKey);
		List<RankedChoice> theCastVote = new ArrayList<RankedChoice>();
		Vote vote = getTheVote();
		vote.canVote = true;
		vote.canUpdate = true;
		
		vote.votesCast.clear();
		vote.votesCast.add(new CastVote("OtherUser1", theCastVote));
		vote.votesCast.add(new CastVote("OtherUser2", theCastVote));
		vote.votesCast.add(new CastVote("OtherUser3", theCastVote));
		vote.votesCast.add(new CastVote("OtherUser4", theCastVote));
		
		voteManager.castVote(adminInfo.voteId, ballot, theCastVote);
		
		System.out.println("ASD: " + vote.votesCast.get(4).proxyId);
		
		assertTrue(vote.votesCast.get(4).proxyId.equals("test_user_in_ws_context"));
	}
}
