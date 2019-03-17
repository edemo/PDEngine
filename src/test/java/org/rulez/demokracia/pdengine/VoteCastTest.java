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
		voteManager.castVote(adminInfo.voteId, ballot, theCastVote);
		CastVote voteCast = new CastVote("test_user_in_ws_context", theCastVote, "Secret");
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
		CastVote voteCast = new CastVote("test_user_in_ws_context", theCastVote, "Secret");
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
		
		vote.votesCast.clear();
		vote.addCastVote("test_user_in_ws_context", theCastVote, "OtherSecret");
		vote.addCastVote("dummy1", theCastVote, "OtherSecret");
		vote.addCastVote("dummy2", theCastVote, "OtherSecret");
		vote.addCastVote("dummy3", theCastVote, "OtherSecret");
		vote.addCastVote("dummy4", theCastVote, "OtherSecret");
		vote.addCastVote("dummy5", theCastVote, "OtherSecret");
		vote.addCastVote("dummy6", theCastVote, "OtherSecret");
		vote.addCastVote("dummy7", theCastVote, "OtherSecret");
		
		voteManager.castVote(adminInfo.voteId, ballot, theCastVote);
		
		assertFalse(vote.votesCast.get(7).secretId.equals("OtherSecret"));
	}
	
	@tested_feature("Vote")
	@tested_operation("Cast vote")
	@tested_behaviour("if there was a cast vote from the same user, the old one is deleted")
	@Test
	public void cast_vote_records_the_vote_with_same_user_votesCast_when_same_user_is_in_the_bottom_of_the_list() {
		String ballot = voteManager.obtainBallot(adminInfo.voteId, adminInfo.adminKey);
		List<RankedChoice> theCastVote = new ArrayList<RankedChoice>();
		Vote vote = getTheVote();
		vote.canVote = true;
		
		vote.votesCast.clear();
		vote.addCastVote("dummy1", theCastVote, "OtherSecret");
		vote.addCastVote("dummy2", theCastVote, "OtherSecret");
		vote.addCastVote("dummy3", theCastVote, "OtherSecret");
		vote.addCastVote("dummy4", theCastVote, "OtherSecret");
		vote.addCastVote("dummy5", theCastVote, "OtherSecret");
		vote.addCastVote("dummy6", theCastVote, "OtherSecret");
		vote.addCastVote("dummy7", theCastVote, "OtherSecret");
		vote.addCastVote("test_user_in_ws_context", theCastVote, "OtherSecret");
		
		voteManager.castVote(adminInfo.voteId, ballot, theCastVote);

		assertFalse(vote.votesCast.get(7).secretId.equals("OtherSecret"));
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
		
		vote.votesCast.clear();
		vote.addCastVote("dummy1", theCastVote, "OtherSecret");
		vote.addCastVote("dummy2", theCastVote, "OtherSecret");
		vote.addCastVote("dummy3", theCastVote, "OtherSecret");
		vote.addCastVote("test_user_in_ws_context", theCastVote, "OtherSecret");
		vote.addCastVote("dummy4", theCastVote, "OtherSecret");
		vote.addCastVote("dummy5", theCastVote, "OtherSecret");
		vote.addCastVote("dummy6", theCastVote, "OtherSecret");
		vote.addCastVote("dummy7", theCastVote, "OtherSecret");
		
		voteManager.castVote(adminInfo.voteId, ballot, theCastVote);

		assertFalse(vote.votesCast.get(7).secretId.equals("OtherSecret"));
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
		
		vote.votesCast.clear();
		vote.addCastVote("OtherUser1", theCastVote, "OtherSecret");
		vote.addCastVote("OtherUser2", theCastVote, "OtherSecret");
		vote.addCastVote("OtherUser3", theCastVote, "OtherSecret");
		vote.addCastVote("OtherUser4", theCastVote, "OtherSecret");
		
		voteManager.castVote(adminInfo.voteId, ballot, theCastVote);
		
		assertFalse(vote.votesCast.get(4).secretId.equals("OtherSecret"));
	}
}
