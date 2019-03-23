package org.rulez.demokracia.pdengine;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.Entity;

import org.json.JSONArray;
import org.json.JSONObject;
import org.rulez.demokracia.pdengine.dataobjects.CastVote;
import org.rulez.demokracia.pdengine.dataobjects.VoteEntity;
import org.rulez.demokracia.pdengine.exception.ReportedException;

@Entity
public class Vote extends VoteEntity {

	private static final long serialVersionUID = 1L;

	public Vote(final String voteName, final Collection<String> neededAssurances, final Collection<String> countedAssurances,
			final boolean isClosed, final int minEndorsements) {
		super();
		name = voteName;
		adminKey = RandomUtils.createRandomKey();
		this.neededAssurances = new ArrayList<>(neededAssurances);
		this.countedAssurances = new ArrayList<>(countedAssurances);
		isPrivate = isClosed;
		this.minEndorsements = minEndorsements;
		creationTime = Instant.now().getEpochSecond();
		choices = new HashMap<>();
		ballots = new ArrayList<>();
		votesCast = new ArrayList<>();
		recordedBallots = new HashMap<>();
	}

	public Integer getRecordedBallotsCount(final String userName) {
		return recordedBallots.containsKey(userName) ? recordedBallots.get(userName) : 0;
	}

	public void increaseRecordedBallots(final String key) {
		recordedBallots.put(key, getRecordedBallotsCount(key) + 1);
	}

	public String addChoice(final String choiceName, final String user) {
		Choice choice = new Choice(choiceName, user);
		choices.put(choice.id, choice);
		return choice.id;
	}

	public Choice getChoice(final String choiceId) {
		if (!choices.containsKey(choiceId)) {
			throw new ReportedException("Illegal choiceId", choiceId);
		}
		return choices.get(choiceId);
	}

	public boolean hasIssuedBallots() {
		return !ballots.isEmpty();
	}

	public void setParameters(final int minEndorsements, final boolean canAddin, final boolean canEndorse, final boolean canVote,
			final boolean canView) {
		this.minEndorsements = minEndorsements;
		this.canAddin = canAddin;
		this.canEndorse = canEndorse;
		this.canVote = canVote;
		this.canView = canView;
	}

	public void checkAdminKey(final String providedAdminKey) {
		if (!(adminKey.equals(providedAdminKey) || "user".equals(providedAdminKey))) {
			throw new ReportedException("Illegal adminKey", providedAdminKey);
		}
	}

	public JSONObject toJson(final String voteId) {
		JSONObject obj = new JSONObject();
		obj.put("name", this.name);
		obj.put("canAddIn", this.canAddin);
		obj.put("creationTime", this.creationTime);
		obj.put("choices", createChoicesJson(this.choices));
		obj.put("canEndorse", this.canEndorse);
		obj.put("countedAssurances", this.countedAssurances);
		obj.put("neededAssurances", this.neededAssurances);
		obj.put("minEndorsements", this.minEndorsements);
		obj.put("id", voteId);
		obj.put("canView", this.canView);
		obj.put("canVote", this.canVote);
		return obj;
	}

	public JSONArray createChoicesJson(final Map<String, Choice> choices) {
		JSONArray array = new JSONArray();

		for (Entry<String, Choice> entry : choices.entrySet()) {
			String key = entry.getKey();
			Choice value = entry.getValue();

			JSONObject obj = choiceAsJson(key, value);

			array.put(obj);
		}

		return array;
	}

	private JSONObject choiceAsJson(final String key, final Choice choice) {
		JSONObject obj = new JSONObject();
		obj.put("initiator", choice.userName);
		obj.put("endorsers", choice.endorsers);
		obj.put("name", choice.name);
		obj.put("id", key);
		return obj;
	}

	protected void addCastVote(final String proxyId, final List<RankedChoice> theVote) {
		Iterator<CastVote> listIterator = votesCast.iterator();
		while (listIterator.hasNext()) {
			CastVote element = listIterator.next();

			if (element.proxyId != null && element.proxyId.equals(proxyId))
				listIterator.remove();
		}

		CastVote castVote = new CastVote(proxyId, theVote);
		votesCast.add(castVote);
	}
}
