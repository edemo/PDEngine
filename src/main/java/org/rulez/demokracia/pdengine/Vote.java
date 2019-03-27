package org.rulez.demokracia.pdengine;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.persistence.Entity;

import org.rulez.demokracia.pdengine.dataobjects.VoteEntity;
import org.rulez.demokracia.pdengine.dataobjects.VoteParameters;
import org.rulez.demokracia.pdengine.exception.ReportedException;

import com.google.gson.JsonObject;

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
		voteParameters = new VoteParameters(minEndorsements, false, false, false, false);
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
		if (!choices.containsKey(choiceId))
			throw new ReportedException("Illegal choiceId", choiceId);
		return choices.get(choiceId);
	}

	public boolean hasIssuedBallots() {
		return !ballots.isEmpty();
	}

	public void setParameters(final VoteParameters voteParameters) {
		this.voteParameters = voteParameters;
	}

	public void checkAdminKey(final String providedAdminKey) {
		if (!(adminKey.equals(providedAdminKey) || "user".equals(providedAdminKey)))
			throw new ReportedException("Illegal adminKey", providedAdminKey);
	}

	public JsonObject toJson() {
		return new VoteJSONSerializer().fromVote(this);
	}

	protected CastVote addCastVote(final String proxyId, final List<RankedChoice> theVote) {
		Iterator<CastVote> listIterator = votesCast.iterator();
		while (listIterator.hasNext()) {
			CastVote element = listIterator.next();

			if (element.proxyId != null && element.proxyId.equals(proxyId)) {
				listIterator.remove();
			}
		}

		CastVote castVote = new CastVote(proxyId, theVote);
		votesCast.add(castVote);
		return castVote;
	}

	public List<CastVote> filterVotes(final String assurance) {
		return new VoteFilter().filterVotes(votesCast, assurance);
	}
}
