package org.rulez.demokracia.pdengine;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.Entity;

import org.json.JSONArray;
import org.json.JSONObject;
import org.rulez.demokracia.pdengine.dataobjects.CastVote;
import org.rulez.demokracia.pdengine.dataobjects.VoteEntity;
import org.rulez.demokracia.pdengine.dataobjects.VoteParameters;

@Entity
public class Vote extends VoteEntity implements VoteInterface, Admnistrable, HasChoices, HasBallots, Endorseable, Voteable {

	private static final long serialVersionUID = 1L;

	public Vote(final String voteName, final Collection<String> neededAssurances, final Collection<String> countedAssurances,
			final boolean isClosed, final int minEndorsements) {
		super();
		this.name = voteName;
		this.adminKey = RandomUtils.createRandomKey();
		this.neededAssurances = new ArrayList<>(neededAssurances);
		this.countedAssurances = new ArrayList<>(countedAssurances);
		this.isPrivate = isClosed;
		this.parameters = new VoteParameters();
		this.parameters.minEndorsements = minEndorsements;
		this.creationTime = Instant.now().getEpochSecond();
		this.choices = new HashMap<>();
		this.ballots = new ArrayList<>();
		this.votesCast = new ArrayList<>();
		this.recordedBallots = new HashMap<>();
	}

	public JSONObject toJson() {
		JSONObject obj = new JSONObject();
		obj.put("name", this.name);
		obj.put("canAddIn", this.parameters.canAddin);
		obj.put("creationTime", this.creationTime);
		obj.put("choices", createChoicesJson(this.choices));
		obj.put("canEndorse", this.parameters.canEndorse);
		obj.put("countedAssurances", this.countedAssurances);
		obj.put("neededAssurances", this.neededAssurances);
		obj.put("minEndorsements", this.parameters.minEndorsements);
		obj.put("id", this.id);
		obj.put("canView", this.parameters.canView);
		obj.put("canVote", this.parameters.canVote);
		return obj;
	}

	public JSONArray createChoicesJson(final Map<String, Choice> choices) {
		JSONArray array = new JSONArray();

		for (Entry<String, Choice> entry : choices.entrySet()) {
			Choice value = entry.getValue();

			JSONObject obj = choiceAsJson(value);

			array.put(obj);
		}

		return array;
	}

	private JSONObject choiceAsJson(final Choice choice) {
		JSONObject obj = new JSONObject();
		obj.put("initiator", choice.userName);
		obj.put("endorsers", choice.endorsers);
		obj.put("name", choice.name);
		obj.put("id", choice.id);
		return obj;
	}

	@Override
	public VoteParameters getParameters() {
		return this.parameters;
	}

	@Override
	public String getAdminKey() {
		return this.adminKey;
	}

	@Override
	public Map<String, Choice> getChoices() {
		return this.choices;
	}

	@Override
	public Map<String, Integer> getRecordedBallots() {
		return this.recordedBallots;
	}

	@Override
	public List<String> getBallots() {
		return this.ballots;
	}

	@Override
	public List<CastVote> getVotesCast() {
		return this.votesCast;
	}

	@Override
	public String getId() {
		return this.id;
	}

	@Override
	public List<String> getNeededAssurances() {
		return new ArrayList<>(this.neededAssurances);
	}
}
