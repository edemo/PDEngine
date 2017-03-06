package org.rulez.demokracia.PDEngine.DataObjects;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;

import org.rulez.demokracia.PDEngine.RandomUtils;

import org.rulez.demokracia.PDEngine.Persistence.BaseEntity;

@Entity
public class Vote extends BaseEntity {

	private static final long serialVersionUID = 1L;
	public String name;
	@ElementCollection
	public List<String> neededAssurances;
	@ElementCollection
	public List<String> countedAssurances;
	public boolean isPrivate;
	public String adminKey;
	public long creationTime;
	public int minEndorsements;
	public boolean canAddin;
	public boolean canEndorse;
	public boolean canVote;
	public boolean canView;
	@ElementCollection
	private Map<String,Choice> choices;

	public Vote(String voteName, Collection<String> neededAssurances, Collection<String> countedAssurances, boolean isClosed, int minEndorsements)  {


		name = voteName;
		adminKey = RandomUtils.createRandomKey();
		id = RandomUtils.createRandomKey();
		this.neededAssurances = new ArrayList<String>(neededAssurances);
		this.countedAssurances = new ArrayList<String>(countedAssurances);
		isPrivate = isClosed;
		this.minEndorsements = minEndorsements;
		creationTime = Instant.now().getEpochSecond();
		choices = new HashMap<String,Choice>();
	}

	public String addChoice(String choiceName, String user) {
		Choice choice = new Choice(choiceName, user);
		choices.put(choice.getId(), choice);
		return choice.getId();
	}

	public Choice getChoice(String choiceId) {
		return choices.get(choiceId);
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

	public Object getMinEndorsements() {
		return minEndorsements;
	}

	public boolean getCanAddin() {
		return canAddin;
	}

	public boolean getCanEndorse() {
		return canEndorse;
	}

	public Object getCanVote() {
		return canVote;
	}

	public Object getCanView() {
		return canView;
	}

	public void endorseChoice(String adminKey2, String choiceId, String userName) {
		getChoice(choiceId).endorse(userName);
	}



}
