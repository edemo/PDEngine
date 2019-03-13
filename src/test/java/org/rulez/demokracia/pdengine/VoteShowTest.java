package org.rulez.demokracia.pdengine;

import static org.junit.Assert.assertEquals;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;
import org.rulez.demokracia.pdengine.annotations.tested_behaviour;
import org.rulez.demokracia.pdengine.annotations.tested_feature;
import org.rulez.demokracia.pdengine.annotations.tested_operation;
import org.rulez.demokracia.pdengine.exception.ReportedException;
import org.rulez.demokracia.pdengine.testhelpers.CreatedDefaultVoteRegistry;

public class VoteShowTest extends CreatedDefaultVoteRegistry {

	Vote vote;
	String voteId;
	String adminKey;
	
	public void setUp() throws ReportedException {
		super.setUp();
		vote = voteManager.getVote(adminInfo.voteId);
		vote.addChoice("choiceName", "user");
		voteManager.addChoice(adminInfo.adminKey, adminInfo.voteId, "choiceName", "user");
		voteId = adminInfo.voteId;
		adminKey = adminInfo.adminKey;
	}
	
	@tested_feature("Manage votes")
	@tested_operation("show vote")
	@tested_behaviour("returns the vote in json")
	@Test
	public void the_name_attribute_contains_the_name_of_the_vote() throws ReportedException {
		JSONObject result = voteManager.showVote(voteId, adminKey);
		
		assertEquals(result.get("name"), vote.name);
	}
	
	@tested_feature("Manage votes")
	@tested_operation("show vote")
	@tested_behaviour("returns the vote in json")
	@Test
	public void the_canAddIn_attribute_contains_whether_the_voters_can_add_choices_to_the_vote() throws ReportedException {
		JSONObject result = voteManager.showVote(voteId, adminKey);
		
		assertEquals(result.get("canAddIn"), vote.canAddin);
	}

	@tested_feature("Manage votes")
	@tested_operation("show vote")
	@tested_behaviour("returns the vote in json")
	@Test
	public void the_creationTime_attribute_contains_the_creation_time_of_the_vote() throws ReportedException {
		JSONObject result = voteManager.showVote(voteId, adminKey);
		
		assertEquals(result.get("creationTime"), vote.creationTime);
	}
	
	@tested_feature("Manage votes")
	@tested_operation("show vote")
	@tested_behaviour("returns the vote in json")
	@Test
	public void the_choices_attribute_contains_the_choices_of_the_vote() throws ReportedException {
		JSONObject result = voteManager.showVote(voteId, adminKey);
		JSONArray jsonArray = vote.createChoicesJson(vote.choices);
		
		assertEquals(result.getJSONArray("choices").toString(), jsonArray.toString());
	}
	
	@tested_feature("Manage votes")
	@tested_operation("show vote")
	@tested_behaviour("returns the vote in json")
	@Test
	public void the_canEndorse_attribute_contains_whether_the_voters_endorse_choices_of_the_vote() throws ReportedException {
		JSONObject result = voteManager.showVote(voteId, adminKey);
		
		assertEquals(result.get("canEndorse"), vote.canEndorse);
	}
	
	@tested_feature("Manage votes")
	@tested_operation("show vote")
	@tested_behaviour("returns the vote in json")
	@Test
	public void the_countedAssurances_attribute_contains_the_counted_assurances_of_the_vote() throws ReportedException {
		JSONObject result = voteManager.showVote(voteId, adminKey);
		JSONArray jsonArray = new JSONArray(vote.countedAssurances);
		
		assertEquals(result.get("countedAssurances").toString(), jsonArray.toString());
	}
	
	@tested_feature("Manage votes")
	@tested_operation("show vote")
	@tested_behaviour("returns the vote in json")
	@Test
	public void the_neededAssurances_attribute_contains_the_needed_assurances_of_the_vote() throws ReportedException {
		JSONObject result = voteManager.showVote(voteId, adminKey);
		JSONArray jsonArray = new JSONArray(vote.neededAssurances);
		
		
		assertEquals(result.get("neededAssurances").toString(), jsonArray.toString());
	}
	
	@tested_feature("Manage votes")
	@tested_operation("show vote")
	@tested_behaviour("returns the vote in json")
	@Test
	public void the_minEndorsements_attribute_contains_the_mininimum_endorsements_of_the_vote() throws ReportedException {
		JSONObject result = voteManager.showVote(voteId, adminKey);
		
		assertEquals(result.get("minEndorsements"), vote.minEndorsements);
	}
	
	@tested_feature("Manage votes")
	@tested_operation("show vote")
	@tested_behaviour("returns the vote in json")
	@Test
	public void the_id_attribute_contains_the_id_of_the_vote() throws ReportedException {
		JSONObject result = voteManager.showVote(voteId, adminKey);
		
		assertEquals(result.get("id"), vote.id);
	}
	
	@tested_feature("Manage votes")
	@tested_operation("show vote")
	@tested_behaviour("returns the vote in json")
	@Test
	public void the_canView_attribute_contains_whether_the_voters_can_view_the_results() throws ReportedException {
		JSONObject result = voteManager.showVote(voteId, adminKey);
		
		assertEquals(result.get("canView"), vote.canView);
	}
	
	@tested_feature("Manage votes")
	@tested_operation("show vote")
	@tested_behaviour("returns the vote in json")
	@Test
	public void the_canVote_attribute_contains_whether_the_votes_can_be_cast() throws ReportedException {
		JSONObject result = voteManager.showVote(voteId, adminKey);
		
		assertEquals(result.get("canVote"), vote.canVote);
	}
	
}