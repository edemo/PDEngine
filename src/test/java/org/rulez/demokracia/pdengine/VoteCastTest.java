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
import org.rulez.demokracia.pdengine.testhelpers.CreatedDefaultChoice;

@TestedFeature("Vote")
@TestedOperation("Cast vote")
public class VoteCastTest extends CreatedDefaultChoice {

	private String ballot;
	private List<RankedChoice> theCastVote;
	private Vote vote;

	@Before
	public void setUp() {
		super.setUp();
		
		ballot = voteManager.obtainBallot(adminInfo.voteId, adminInfo.adminKey);
		theCastVote = new ArrayList<>();
		vote = getTheVote();
		vote.canVote = true;
		vote.votesCast.clear();
	}

	@TestedBehaviour("records cast vote with the vote and user's proxy id")
	@Test
	public void cast_vote_records_the_proxy_id() {
		vote.canUpdate = true;
		voteManager.castVote(adminInfo.voteId, ballot, theCastVote);
		CastVote voteCast = new CastVote(TEST_USER_NAME, theCastVote);
		assertEquals(voteCast.proxyId, vote.votesCast.get(0).proxyId);
	}
	
	@TestedBehaviour("records cast vote with the vote and user's proxy id")
	@Test
	public void voteCast_records_the_preferences() {
		voteManager.castVote(adminInfo.voteId, ballot, theCastVote);
		CastVote voteCast = new CastVote(TEST_USER_NAME, theCastVote);
		assertTrue(voteCast.preferences.containsAll(vote.votesCast.get(0).preferences));
	}
	
	@TestedBehaviour("Cast vote have a secret random identifier")
	@Test
	public void voteCast_records_a_secret_id_with_the_vote_cast() {
		voteManager.castVote(adminInfo.voteId, ballot, theCastVote);
		assertTrue(vote.votesCast.get(0).secretId instanceof String);
	}
	
	@TestedBehaviour("if there was a cast vote from the same user, the old one is deleted")
	@Test
	public void cast_vote_records_the_vote_with_same_user_votesCast_when_same_user_is_in_the_top_of_the_list() {
		vote.canUpdate = true;
		
		addCastVote(TEST_USER_NAME, theCastVote);
		addfirstDummies();
		addSecondDummies();
		
		voteManager.castVote(adminInfo.voteId, ballot, theCastVote);
		
		assertTrue(vote.votesCast.get(7).proxyId.equals(TEST_USER_NAME));
	}
	
	@TestedBehaviour("if there was a cast vote from the same user, the old one is deleted")
	@Test
	public void cast_vote_records_the_vote_with_same_user_votesCast_when_same_user_is_in_the_bottom_of_the_list() {
		List<RankedChoice> theCastVote1 = new ArrayList<>();
		vote.canUpdate = true;
		
		addfirstDummies();
		addSecondDummies();
		addCastVote(TEST_USER_NAME, theCastVote1);
		
		voteManager.castVote(adminInfo.voteId, ballot, theCastVote);

		assertTrue(vote.votesCast.get(7).preferences.equals(theCastVote1));
	}
	
	@TestedBehaviour("if there was a cast vote from the same user, the old one is deleted")
	@Test
	public void cast_vote_records_the_vote_with_same_user_votesCast_when_same_user_is_in_the_middle_of_the_list() {
		vote.canUpdate = true;
		
		addfirstDummies();
		addCastVote(TEST_USER_NAME, theCastVote);
		addSecondDummies();
		
		voteManager.castVote(adminInfo.voteId, ballot, theCastVote);

		assertTrue(vote.votesCast.get(7).proxyId.equals(TEST_USER_NAME));
	}

	private void addSecondDummies() {
		addCastVote("dummy4", theCastVote);
		addCastVote("dummy5", theCastVote);
		addCastVote("dummy6", theCastVote);
		addCastVote("dummy7", theCastVote);
	}

	private void addfirstDummies() {
		addCastVote("dummy1", theCastVote);
		addCastVote("dummy2", theCastVote);
		addCastVote("dummy3", theCastVote);
	}
	
	@TestedBehaviour("if there was a cast vote from the same user, the old one is deleted")
	@Test 
	public void cast_vote_records_the_vote_with_same_user_votesCast_when_the_list_does_not_contain_same_user() {
		vote.canUpdate = true;
		
		addCastVote("OtherUser1", theCastVote);
		addCastVote("OtherUser2", theCastVote);
		addCastVote("OtherUser3", theCastVote);
		addCastVote("OtherUser4", theCastVote);
		voteManager.castVote(adminInfo.voteId, ballot, theCastVote);
		
		assertTrue(vote.votesCast.get(4).proxyId.equals(TEST_USER_NAME));
	}
	
	private void addCastVote(final String proxyId, final List<RankedChoice> theCastVote) {
		vote.votesCast.add(new CastVote(proxyId, theCastVote));
	}
	
}
