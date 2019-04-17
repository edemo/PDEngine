package org.rulez.demokracia.pdengine.vote;

import static org.rulez.demokracia.pdengine.vote.AssuranceType.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import javax.persistence.Entity;

import org.rulez.demokracia.pdengine.Admnistrable;
import org.rulez.demokracia.pdengine.Endorsable;
import org.rulez.demokracia.pdengine.HasBallots;
import org.rulez.demokracia.pdengine.HasChoices;
import org.rulez.demokracia.pdengine.RandomUtils;
import org.rulez.demokracia.pdengine.ValidationUtil;
import org.rulez.demokracia.pdengine.VoteFilter;
import org.rulez.demokracia.pdengine.VoteInterface;
import org.rulez.demokracia.pdengine.VoteJSONSerializer;
import org.rulez.demokracia.pdengine.choice.Choice;
import org.rulez.demokracia.pdengine.dataobjects.VoteParameters;
import org.rulez.demokracia.pdengine.exception.ReportedException;
import org.rulez.demokracia.pdengine.votecast.CastVote;

import com.google.gson.JsonObject;

@Entity
public class Vote extends VoteEntity
		implements VoteInterface, Admnistrable, HasChoices, HasBallots, Endorsable {

	private static final long serialVersionUID = 1L;

	public Vote(final String voteName, final Collection<String> neededAssurances, final Collection<String> countedAssurances,
			final boolean isPrivate, final int minEndorsements) {
		super();
		this.setName(voteName);
		this.setAdminKey(RandomUtils.createRandomKey());
		this.setNeededAssurances(ValidationUtil.checkAssurances(neededAssurances, NEEDED));
		this.setCountedAssurances(ValidationUtil.checkAssurances(countedAssurances, COUNTED));
		this.setPrivate(isPrivate);
		VoteParameters voteParameters = new VoteParameters();
		voteParameters.setMinEndorsements(minEndorsements);
		this.setParameters(voteParameters);
		this.setCreationTime(Instant.now().getEpochSecond());
		this.setChoices(new HashMap<>());
		this.setBallots(new ArrayList<>());
		this.setVotesCast(new ArrayList<>());
		this.setRecordedBallots(new HashMap<>());
	}

	public Vote(final CreateVoteRequest request) {
		this(request.getVoteName(),
				request.getNeededAssurances(),
				request.getCountedAssurances(),
				request.isPrivate(),
				request.getMinEndorsements());
	}

	public JsonObject toJson() {
		return new VoteJSONSerializer().fromVote(this);
	}

	public List<CastVote> filterVotes(final String assurance) {
		return new VoteFilter().filterVotes(this.getVotesCast(), assurance);
	}

	@Override
	public void addChoice(final Choice choice) {
		getChoices().put(choice.getId(), choice);
	}

	@Override
	public Choice getChoice(final String choiceId) {
		if (!getChoices().containsKey(choiceId)) {
			throw new ReportedException("Illegal choiceId", choiceId);
		}
		return getChoices().get(choiceId);
	}
}
