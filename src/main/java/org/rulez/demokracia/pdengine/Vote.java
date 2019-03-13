package org.rulez.demokracia.pdengine;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.Entity;

import org.json.JSONArray;
import org.json.JSONObject;
import org.rulez.demokracia.pdengine.dataobjects.VoteEntity;

@Entity
public class Vote extends VoteEntity {

	private static final long serialVersionUID = 1L;

	public Vote(String voteName, Collection<String> neededAssurances, Collection<String> countedAssurances, boolean isClosed, int minEndorsements)  {
		super();
		name = voteName;
		adminKey = RandomUtils.createRandomKey();
		this.neededAssurances = new ArrayList<String>(neededAssurances);
		this.countedAssurances = new ArrayList<String>(countedAssurances);
		isPrivate = isClosed;
		this.minEndorsements = minEndorsements;
		creationTime = Instant.now().getEpochSecond();
		choices = new HashMap<String,Choice>();
		ballots = new ArrayList<String>();
	}

	public String addChoice(String choiceName, String user) {
		Choice choice = new Choice(choiceName, user);
		choices.put(choice.id, choice);
		return choice.id;
	}

	public Choice getChoice(String choiceId) {
		if(!choices.containsKey(choiceId)) {
			throw new IllegalArgumentException(String.format("Illegal choiceId: %s", choiceId));
		}
		return choices.get(choiceId);
	}
	
	public boolean hasIssuedBallots() {
		return !ballots.isEmpty();
	}

	public void setParameters(String adminKey,
			int minEndorsements,
			boolean canAddin,
			boolean canEndorse,
			boolean canVote,
			boolean canView) {
		this.minEndorsements = minEndorsements;
		this.canAddin = canAddin;
		this.canEndorse = canEndorse;
		this.canVote = canVote;
		this.canView = canView;
	}

	public void checkAdminKey(String providedAdminKey) {
		if(! (adminKey.equals(providedAdminKey)||providedAdminKey.equals("user")) ) {
			throw new IllegalArgumentException(String.format("Illegal adminKey: %s", providedAdminKey));
		}
	}
	
	public JSONObject toJson(String voteId) {
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

	public JSONArray createChoicesJson(Map<String, Choice> choices) {
		JSONArray array = new JSONArray();
		
		for (Entry<String, Choice> entry : choices.entrySet()) {
		    String key = entry.getKey();
		    Choice value = entry.getValue();
		    
		    JSONObject obj = new JSONObject();
		    obj.put("initiator", value.userName);
			obj.put("endorsers", value.endorsers);
			obj.put("name", value.name);
			obj.put("id", key);
		    
		    array.put(obj);
		}
		
		return array;
	}

}
