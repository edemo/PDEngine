package org.rulez.demokracia.pdengine;

import java.util.List;

import javax.xml.ws.WebServiceContext;

import org.rulez.demokracia.pdengine.choice.RankedChoice;
import org.rulez.demokracia.pdengine.dataobjects.VoteAdminInfo;
import org.rulez.demokracia.pdengine.exception.ReportedException;
import org.rulez.demokracia.pdengine.vote.Vote;
import org.rulez.demokracia.pdengine.votecast.CastVote;

import com.google.gson.JsonObject;

public class VoteRegistry extends ChoiceManager implements IVoteManager {
	public VoteRegistry(final WebServiceContext wsContext) {
		super(wsContext);
	}

	@Override
	public String obtainBallot(final String identifier, final String adminKey) {
		Vote vote = getVote(identifier);
		vote.checkAdminKey(adminKey);

		if (adminKey.equals(vote.getAdminKey())) {
			vote.increaseRecordedBallots("admin");
		} else {
			if (getWsContext().getUserPrincipal() == null) {
				throw new IllegalArgumentException("Simple user is not authenticated, cannot issue any ballot.");
			}
			if (!userHasAllAssurance(vote.getNeededAssurances())) {
				throw new IllegalArgumentException("The user does not have all of the needed assurances.");
			}
			if (vote.getRecordedBallotsCount(getWsUserName()).intValue() > 0) {
				throw new IllegalArgumentException("This user already have a ballot.");
			}

			vote.increaseRecordedBallots(getWsUserName());
		}

		String ballot = RandomUtils.createRandomKey();
		vote.addBallot(ballot);
		return ballot;
	}

	public boolean userHasAllAssurance(final List<String> neededAssuranceList) {
		for (String neededAssurance : neededAssuranceList) {
			if (!hasAssurance(neededAssurance)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public CastVote castVote(final String voteId, final String ballot, final List<RankedChoice> theVote) {
		throw new UnsupportedOperationException();
	}

	private Vote checkAdminInfo(final VoteAdminInfo adminInfo) {
		Vote vote = getVote(adminInfo.voteId);
		vote.checkAdminKey(adminInfo.adminKey);
		return vote;
	}

	@Override
	public JsonObject showVote(final VoteAdminInfo adminInfo) {
		Vote vote = checkAdminInfo(adminInfo);
		if (!adminInfo.adminKey.equals(vote.getAdminKey())) {
			checkAssurances(vote);
		}

		return vote.toJson();
	}

	private void checkAssurances(final Vote vote) {
		for (String assurance : vote.getCountedAssurances()) {
			if (!this.hasAssurance(assurance)) {
				throw new ReportedException("missing assurances", assurance);
			}
		}
	}

	@Override
	public String deleteChoice(final VoteAdminInfo voteAdminInfo, final String choiceId) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void modifyChoice(final VoteAdminInfo adminInfo, final String choiceId, final String choiceName) {
		throw new UnsupportedOperationException();
	}
}

