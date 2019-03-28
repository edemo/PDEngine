package org.rulez.demokracia.pdengine;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Entity;

import org.rulez.demokracia.pdengine.dataobjects.VoteEntity;
import org.rulez.demokracia.pdengine.dataobjects.VoteParameters;

import com.google.gson.JsonObject;

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

	public JsonObject toJson() {
		return new VoteJSONSerializer().fromVote(this);
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

	public List<CastVote> filterVotes(final String assurance) {
		return new VoteFilter().filterVotes(votesCast, assurance);
	}
}
