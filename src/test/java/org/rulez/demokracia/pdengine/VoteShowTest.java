package org.rulez.demokracia.pdengine;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.rulez.demokracia.pdengine.annotations.TestedBehaviour;
import org.rulez.demokracia.pdengine.annotations.TestedFeature;
import org.rulez.demokracia.pdengine.annotations.TestedOperation;
import org.rulez.demokracia.pdengine.dataobjects.VoteAdminInfo;
import org.rulez.demokracia.pdengine.testhelpers.CreatedDefaultVoteRegistry;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

@TestedFeature("Manage votes")
@TestedOperation("show vote")
@TestedBehaviour("returns the vote in json")
public class VoteShowTest extends CreatedDefaultVoteRegistry {

	private Vote vote;
	private String voteId;
	private String adminKey;

	@Override
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
		JsonObject result = voteManager.showVote(new VoteAdminInfo(voteId, adminKey));

		assertEquals(result.get("name").getAsString(), vote.name);
	}

	@Test
	public void the_canAddIn_attribute_contains_whether_the_voters_can_add_choices_to_the_vote() {
		JsonObject result = voteManager.showVote(new VoteAdminInfo(voteId, adminKey));

		assertEquals(result.get("voteParameters").getAsJsonObject().get("canAddin").getAsBoolean(),
				vote.voteParameters.canAddin);
	}

	@Test
	public void the_creationTime_attribute_contains_the_creation_time_of_the_vote() {
		JsonObject result = voteManager.showVote(new VoteAdminInfo(voteId, adminKey));

		assertEquals(result.get("creationTime").getAsLong(), vote.creationTime);
	}

	@Test
	public void the_choices_attribute_contains_the_choices_of_the_vote() {
		JsonObject result = voteManager.showVote(new VoteAdminInfo(voteId, adminKey));

		assertEquals(result.get("choices").getAsJsonArray().size(), 2);
	}
	@Test
	public void the_initiator_of_the_choice_is_in_the_json() {
		JsonArray choicesJson = vote.toJson().get("choices").getAsJsonArray();
		JsonObject firstChoice = choicesJson.get(0).getAsJsonObject();
		String initiatorInJson = firstChoice.get("initiator").getAsString();
		assertEquals("user", initiatorInJson);
	}

	@Test
	public void the_canEndorse_attribute_contains_whether_the_voters_endorse_choices_of_the_vote() {
		JsonObject result = voteManager.showVote(new VoteAdminInfo(voteId, adminKey));

		assertEquals(result.get("voteParameters").getAsJsonObject().get("canEndorse").getAsBoolean(),
				vote.voteParameters.canEndorse);
	}

	@Test
	public void the_countedAssurances_attribute_contains_the_counted_assurances_of_the_vote() {
		JsonObject result = voteManager.showVote(new VoteAdminInfo(voteId, adminKey));
		JsonArray jsonCountedAssurances = result.get("countedAssurances").getAsJsonArray();

		GsonBuilder gsonBuilder = new GsonBuilder();

		vote.countedAssurances.stream().forEach(
				assurance -> assertTrue(jsonCountedAssurances.contains(gsonBuilder.create().toJsonTree(assurance))));
	}

	@Test
	public void the_neededAssurances_attribute_contains_the_needed_assurances_of_the_vote() {
		JsonObject result = voteManager.showVote(new VoteAdminInfo(voteId, adminKey));
		JsonArray jsonNeededAssurances = result.get("neededAssurances").getAsJsonArray();

		GsonBuilder gsonBuilder = new GsonBuilder();

		vote.neededAssurances.stream().forEach(
				assurance -> assertTrue(jsonNeededAssurances.contains(gsonBuilder.create().toJsonTree(assurance))));
	}

	@Test
	public void the_minEndorsements_attribute_contains_the_mininimum_endorsements_of_the_vote() {
		JsonObject result = voteManager.showVote(new VoteAdminInfo(voteId, adminKey));

		assertEquals(result.get("voteParameters").getAsJsonObject().get("minEndorsements").getAsInt(),
				vote.voteParameters.minEndorsements);
	}

	@Test
	public void the_id_attribute_contains_the_id_of_the_vote() {
		JsonObject result = voteManager.showVote(new VoteAdminInfo(voteId, adminKey));

		assertEquals(result.get("id").getAsString(), vote.id);
	}

	@Test
	public void the_canView_attribute_contains_whether_the_voters_can_view_the_results() {
		JsonObject result = voteManager.showVote(new VoteAdminInfo(voteId, adminKey));

		assertEquals(result.get("voteParameters").getAsJsonObject().get("canView").getAsBoolean(),
				vote.voteParameters.canView);
	}

	@Test
	public void the_canVote_attribute_contains_whether_the_votes_can_be_cast() {
		JsonObject result = voteManager.showVote(new VoteAdminInfo(voteId, adminKey));

		assertEquals(result.get("voteParameters").getAsJsonObject().get("canVote").getAsBoolean(),
				vote.voteParameters.canVote);
	}
}