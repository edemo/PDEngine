package org.rulez.demokracia.pdengine;

import static org.junit.Assert.assertEquals;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;
import org.rulez.demokracia.pdengine.annotations.tested_behaviour;
import org.rulez.demokracia.pdengine.annotations.tested_feature;
import org.rulez.demokracia.pdengine.annotations.tested_operation;
import org.rulez.demokracia.pdengine.exception.ReportedException;
import org.rulez.demokracia.pdengine.testhelpers.CreatedDefaultVoteRegistry;

public class VoteShowValidationTest extends CreatedDefaultVoteRegistry {

	Vote vote;
	String voteId;
	String adminKey;
	
	public void setUp() throws ReportedException {
		super.setUp();
		vote = voteManager.getVote(adminInfo.voteId);
		voteId = adminInfo.voteId;
		adminKey = adminInfo.adminKey;
	}

	@tested_feature("Manage votes")
	@tested_operation("show vote")
	@tested_behaviour("validates inputs")
	@Test
	public void invalid_voteId_is_rejected() throws ReportedException {
		String invalidvoteId = RandomUtils.createRandomKey();
		assertThrows(
			() -> voteManager.showVote(invalidvoteId, adminInfo.adminKey)
		).assertMessageIs(String.format("illegal voteId: %s",invalidvoteId));
	}
	
	@tested_feature("Manage votes")
	@tested_operation("show vote")
	@tested_behaviour("validates inputs")
	@Test
	public void invalid_adminKey_is_rejected() throws ReportedException {
		String invalidAdminKey = RandomUtils.createRandomKey();
		assertThrows(
			() -> voteManager.showVote(adminInfo.voteId, invalidAdminKey)
		).assertMessageIs(String.format("Illegal adminKey: %s",invalidAdminKey));
	}
	
	@tested_feature("Manage votes")
	@tested_operation("show vote")
	@tested_behaviour("returns the vote in json")
	@Test
	public void proper_voteId_and_adminKey_check_name() throws ReportedException {
		JSONObject result = voteManager.showVote(voteId, adminKey);
		
		assertEquals(result.get("name"), vote.name);
	}
	
	@tested_feature("Manage votes")
	@tested_operation("show vote")
	@tested_behaviour("returns the vote in json")
	@Test
	public void proper_voteId_and_adminKey_check_canAddIn() throws ReportedException {
		JSONObject result = voteManager.showVote(voteId, adminKey);
		
		assertEquals(result.get("canAddIn"), vote.canAddin);
	}

	@tested_feature("Manage votes")
	@tested_operation("show vote")
	@tested_behaviour("returns the vote in json")
	@Test
	public void proper_voteId_and_adminKey_check_creationTime() throws ReportedException {
		JSONObject result = voteManager.showVote(voteId, adminKey);
		
		assertEquals(result.get("creationTime"), vote.creationTime);
	}
	
	@tested_feature("Manage votes")
	@tested_operation("show vote")
	@tested_behaviour("returns the vote in json")
	@Test
	public void proper_voteId_and_adminKey_check_choices() throws ReportedException {
		JSONObject result = voteManager.showVote(voteId, adminKey);
		JSONObject jsonObject = new JSONObject(vote.choices);

		assertEquals(result.get("choices").toString(), jsonObject.toString());
	}
	
	@tested_feature("Manage votes")
	@tested_operation("show vote")
	@tested_behaviour("returns the vote in json")
	@Test
	public void proper_voteId_and_adminKey_check_canEndorse() throws ReportedException {
		JSONObject result = voteManager.showVote(voteId, adminKey);
		
		assertEquals(result.get("canEndorse"), vote.canEndorse);
	}
	
	@tested_feature("Manage votes")
	@tested_operation("show vote")
	@tested_behaviour("returns the vote in json")
	@Test
	public void proper_voteId_and_adminKey_check_countedAssurances() throws ReportedException {
		JSONObject result = voteManager.showVote(voteId, adminKey);
		JSONArray jsonArray = new JSONArray(vote.countedAssurances);
		
		assertEquals(result.get("countedAssurances").toString(), jsonArray.toString());
	}
	
	@tested_feature("Manage votes")
	@tested_operation("show vote")
	@tested_behaviour("returns the vote in json")
	@Test
	public void proper_voteId_and_adminKey_check_neededAssurances() throws ReportedException {
		JSONObject result = voteManager.showVote(voteId, adminKey);
		JSONArray jsonArray = new JSONArray(vote.neededAssurances);
		
		
		assertEquals(result.get("neededAssurances").toString(), jsonArray.toString());
	}
	
	@tested_feature("Manage votes")
	@tested_operation("show vote")
	@tested_behaviour("returns the vote in json")
	@Test
	public void proper_voteId_and_adminKey_check_minEndorsements() throws ReportedException {
		JSONObject result = voteManager.showVote(voteId, adminKey);
		
		assertEquals(result.get("minEndorsements"), vote.minEndorsements);
	}
	
	@tested_feature("Manage votes")
	@tested_operation("show vote")
	@tested_behaviour("returns the vote in json")
	@Test
	public void proper_voteId_and_adminKey_check_id() throws ReportedException {
		JSONObject result = voteManager.showVote(voteId, adminKey);
		
		assertEquals(result.get("id"), vote.id);
	}
	
	@tested_feature("Manage votes")
	@tested_operation("show vote")
	@tested_behaviour("returns the vote in json")
	@Test
	public void proper_voteId_and_adminKey_check_canView() throws ReportedException {
		JSONObject result = voteManager.showVote(voteId, adminKey);
		
		assertEquals(result.get("canView"), vote.canView);
	}
	
	@tested_feature("Manage votes")
	@tested_operation("show vote")
	@tested_behaviour("returns the vote in json")
	@Test
	public void proper_voteId_and_adminKey_check_canVote() throws ReportedException {
		JSONObject result = voteManager.showVote(voteId, adminKey);
		
		assertEquals(result.get("canVote"), vote.canVote);
	}
	
}