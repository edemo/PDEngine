package org.rulez.demokracia.pdengine.vote;



import java.util.Collection;
import java.util.List;

import javax.persistence.Entity;

import org.rulez.demokracia.pdengine.Admnistrable;
import org.rulez.demokracia.pdengine.Endorsable;
import org.rulez.demokracia.pdengine.HasBallots;
import org.rulez.demokracia.pdengine.HasChoices;
import org.rulez.demokracia.pdengine.VoteInterface;
import org.rulez.demokracia.pdengine.choice.Choice;
import org.rulez.demokracia.pdengine.exception.ReportedException;
import com.google.gson.JsonObject;

@Entity
public class Vote extends VoteEntity
implements VoteInterface, Admnistrable, HasChoices, HasBallots, Endorsable {

	private static final long serialVersionUID = 1L;

	public Vote(final String voteName, final Collection<String> neededAssurances, final Collection<String> countedAssurances,
			final boolean isPrivate, final int minEndorsements) {
		super(voteName, neededAssurances, countedAssurances, isPrivate, minEndorsements);
	}

	public Vote(final CreateVoteRequest request) {
		super(request.getVoteName(),
				request.getNeededAssurances(),
				request.getCountedAssurances(),
				request.isPrivate(),
				request.getMinEndorsements());
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

	@Override
	public void setId(final String id) {
		throw new UnsupportedOperationException("Id is invariant");
	}

	@Override
	public void setAdminKey(final String adminKey) {
		throw new UnsupportedOperationException("Admin Key is invariant");
	}

	@Override
	public void setNeededAssurances(final List<String> neededAssurances) {
		throw new UnsupportedOperationException("Needed Assurances are invariant");
	}

	@Override
	public void setCountedAssurances(final List<String> countedAssurances) {
		throw new UnsupportedOperationException("Counted Assurances are invariant");
	}

	@Override
	public void setPrivate(final boolean isPrivate) {
		throw new UnsupportedOperationException("IsPrivate is invariant");
	}

	@Override
	public void setCreationTime(final long creationTime) {
		throw new UnsupportedOperationException("Creation Time is invariant");
	}
}
