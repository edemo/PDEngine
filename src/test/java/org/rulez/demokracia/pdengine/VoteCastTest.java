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
	@tested_behaviour("records cast vote with the vote and user's proxy id")
	@Test
	public void cast_vote_records_the_proxy_id() {
		vote.canUpdate = true;
		voteManager.castVote(adminInfo.voteId, ballot, theCastVote);
		CastVote voteCast = new CastVote("test_user_in_ws_context", theCastVote);
		assertEquals(voteCast.proxyId, vote.votesCast.get(0).proxyId);
	}
	
	@tested_feature("Vote")
	@tested_operation("Cast vote")
	@tested_behaviour("records cast vote with the vote and user's proxy id")
	@Test
	public void voteCast_records_the_preferences() {
		voteManager.castVote(adminInfo.voteId, ballot, theCastVote);
		CastVote voteCast = new CastVote("test_user_in_ws_context", theCastVote);
		assertTrue(voteCast.preferences.containsAll(vote.votesCast.get(0).preferences));
	}
	
	@tested_feature("Vote")
	@tested_operation("Cast vote")
	@tested_behaviour("Cast vote have a secret random identifier")
	@Test
	public void voteCast_records_a_secret_id_with_the_vote_cast() {
		voteManager.castVote(adminInfo.voteId, ballot, theCastVote);
		assertTrue(vote.votesCast.get(0).secretId instanceof String);
	}
	
	@tested_feature("Vote")
	@tested_operation("Cast vote")
	@tested_behaviour("if there was a cast vote from the same user, the old one is deleted")
	@Test
	public void cast_vote_records_the_vote_with_same_user_votesCast_when_same_user_is_in_the_top_of_the_list() {
		vote.canUpdate = true;
		
		addCastVote("test_user_in_ws_context", theCastVote);
		addCastVote("dummy1", theCastVote);
		addCastVote("dummy2", theCastVote);
		addCastVote("dummy3", theCastVote);
		addCastVote("dummy4", theCastVote);
		addCastVote("dummy5", theCastVote);
		addCastVote("dummy6", theCastVote);
		addCastVote("dummy7", theCastVote);
		
		voteManager.castVote(adminInfo.voteId, ballot, theCastVote);
		
		assertTrue(vote.votesCast.get(7).proxyId.equals("test_user_in_ws_context"));
	}
	
	@tested_feature("Vote")
	@tested_operation("Cast vote")
	@tested_behaviour("if there was a cast vote from the same user, the old one is deleted")
	@Test
	public void cast_vote_records_the_vote_with_same_user_votesCast_when_same_user_is_in_the_bottom_of_the_list() {
		List<RankedChoice> theCastVote1 = new ArrayList<RankedChoice>();
		vote.canUpdate = true;
		
		addCastVote("dummy1", theCastVote);
		addCastVote("dummy2", theCastVote);
		addCastVote("dummy3", theCastVote);
		addCastVote("dummy4", theCastVote);
		addCastVote("dummy5", theCastVote);
		addCastVote("dummy6", theCastVote);
		addCastVote("dummy7", theCastVote);
		addCastVote("test_user_in_ws_context", theCastVote1);
		
		voteManager.castVote(adminInfo.voteId, ballot, theCastVote);

		assertTrue(vote.votesCast.get(7).preferences.equals(theCastVote1));
	}
	
	@tested_feature("Vote")
	@tested_operation("Cast vote")
	@tested_behaviour("if there was a cast vote from the same user, the old one is deleted")
	@Test
	public void cast_vote_records_the_vote_with_same_user_votesCast_when_same_user_is_in_the_middle_of_the_list() {
		vote.canUpdate = true;
		
		addCastVote("dummy1", theCastVote);
		addCastVote("dummy2", theCastVote);
		addCastVote("dummy3", theCastVote);
		addCastVote("test_user_in_ws_context", theCastVote);
		addCastVote("dummy4", theCastVote);
		addCastVote("dummy5", theCastVote);
		addCastVote("dummy6", theCastVote);
		addCastVote("dummy7", theCastVote);
		
		voteManager.castVote(adminInfo.voteId, ballot, theCastVote);

		assertTrue(vote.votesCast.get(7).proxyId.equals("test_user_in_ws_context"));
	}
	
	@tested_feature("Vote")
	@tested_operation("Cast vote")
	@tested_behaviour("if there was a cast vote from the same user, the old one is deleted")
	@Test 
	public void cast_vote_records_the_vote_with_same_user_votesCast_when_the_list_does_not_contain_same_user() {
		vote.canUpdate = true;
		
		addCastVote("OtherUser1", theCastVote);
		addCastVote("OtherUser2", theCastVote);
		addCastVote("OtherUser3", theCastVote);
		addCastVote("OtherUser4", theCastVote);
		voteManager.castVote(adminInfo.voteId, ballot, theCastVote);
		
		assertTrue(vote.votesCast.get(4).proxyId.equals("test_user_in_ws_context"));
	}
	
	private void addCastVote(String proxyId, List<RankedChoice> theCastVote) {
		vote.votesCast.add(new CastVote(proxyId, theCastVote));
	}
	
}
