package org.rulez.demokracia.pdengine;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.rulez.demokracia.pdengine.annotations.TestedBehaviour;
import org.rulez.demokracia.pdengine.annotations.TestedFeature;
import org.rulez.demokracia.pdengine.annotations.TestedOperation;
import org.rulez.demokracia.pdengine.dataobjects.VoteAdminInfo;
import org.rulez.demokracia.pdengine.testhelpers.CreatedDefaultVoteRegistry;

@TestedFeature("Manage votes")
@TestedOperation("show vote")
@TestedBehaviour("returns the vote in json")
public class VoteShowTest extends CreatedDefaultVoteRegistry {

	private Vote vote;
	private String voteId;
	private String adminKey;

	@Before
	public void setUp() {
		super.setUp();
		vote = voteManager.getVote(adminInfo.voteId);
		vote.addChoice("choiceName", "user");
		voteManager.addChoice(new VoteAdminInfo(adminInfo.voteId, adminInfo.adminKey), "choiceName", "user");
		voteId = adminInfo.voteId;
		adminKey = adminInfo.adminKey;
	}
	
	@Test
	public void the_name_attribute_contains_the_name_of_the_vote() {
		JSONObject result = voteManager.showVote(new VoteAdminInfo(voteId, adminKey));
		
		assertEquals(result.get("name"), vote.name);
	}
	
	@Test
	public void the_canAddIn_attribute_contains_whether_the_voters_can_add_choices_to_the_vote() {
		JSONObject result = voteManager.showVote(new VoteAdminInfo(voteId, adminKey));
		
		assertEquals(result.get("canAddIn"), vote.canAddin);
	}

	@Test
	public void the_creationTime_attribute_contains_the_creation_time_of_the_vote() {
		JSONObject result = voteManager.showVote(new VoteAdminInfo(voteId, adminKey));
		
		assertEquals(result.get("creationTime"), vote.creationTime);
	}
	
	@Test
	public void the_choices_attribute_contains_the_choices_of_the_vote() {
		JSONObject result = voteManager.showVote(new VoteAdminInfo(voteId, adminKey));
		JSONArray jsonArray = vote.createChoicesJson(vote.choices);
		
		assertEquals(result.getJSONArray("choices").toString(), jsonArray.toString());
	}
	@Test
	public void the_initiator_of_the_choice_is_in_the_json() {
		JSONArray jsonArray = vote.createChoicesJson(vote.choices);
		JSONObject firstChoice = (JSONObject) jsonArray.get(0);
		String initiatorInJson = (String) firstChoice.get("initiator");
		assertEquals("user", initiatorInJson);
	}
	
	@Test
	public void the_canEndorse_attribute_contains_whether_the_voters_endorse_choices_of_the_vote() {
		JSONObject result = voteManager.showVote(new VoteAdminInfo(voteId, adminKey));
		
		assertEquals(result.get("canEndorse"), vote.canEndorse);
	}
	
	@Test
	public void the_countedAssurances_attribute_contains_the_counted_assurances_of_the_vote() {
		JSONObject result = voteManager.showVote(new VoteAdminInfo(voteId, adminKey));
		JSONArray jsonArray = new JSONArray(vote.countedAssurances);
		
		assertEquals(result.get("countedAssurances").toString(), jsonArray.toString());
	}
	
	@Test
	public void the_neededAssurances_attribute_contains_the_needed_assurances_of_the_vote() {
		JSONObject result = voteManager.showVote(new VoteAdminInfo(voteId, adminKey));
		JSONArray jsonArray = new JSONArray(vote.neededAssurances);
		
		
		assertEquals(result.get("neededAssurances").toString(), jsonArray.toString());
	}
	
	@Test
	public void the_minEndorsements_attribute_contains_the_mininimum_endorsements_of_the_vote() {
		JSONObject result = voteManager.showVote(new VoteAdminInfo(voteId, adminKey));
		
		assertEquals(result.get("minEndorsements"), vote.minEndorsements);
	}
	
	@Test
	public void the_id_attribute_contains_the_id_of_the_vote() {
		JSONObject result = voteManager.showVote(new VoteAdminInfo(voteId, adminKey));
		
		assertEquals(result.get("id"), vote.id);
	}
	
	@Test
	public void the_canView_attribute_contains_whether_the_voters_can_view_the_results() {
		JSONObject result = voteManager.showVote(new VoteAdminInfo(voteId, adminKey));
		
		assertEquals(result.get("canView"), vote.canView);
	}
	
	@Test
	public void the_canVote_attribute_contains_whether_the_votes_can_be_cast() {
		JSONObject result = voteManager.showVote(new VoteAdminInfo(voteId, adminKey));
		
		assertEquals(result.get("canVote"), vote.canVote);
	}	
}