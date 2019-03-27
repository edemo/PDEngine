package org.rulez.demokracia.pdengine;

import java.util.List;

import javax.xml.ws.WebServiceContext;

import org.rulez.demokracia.pdengine.dataobjects.VoteAdminInfo;
import org.rulez.demokracia.pdengine.dataobjects.VoteParameters;
import org.rulez.demokracia.pdengine.exception.ReportedException;

import com.google.gson.JsonObject;

public class VoteRegistry extends ChoiceManager implements IVoteManager {
	public VoteRegistry(final WebServiceContext wsContext) {
		super(wsContext);
	}

	@Override
	public String obtainBallot(final String identifier, final String adminKey) {
		Vote vote = getVote(identifier);
		vote.checkAdminKey(adminKey);

		if (adminKey.equals(vote.adminKey)) {
			vote.increaseRecordedBallots("admin");
		} else if ("user".equals(adminKey)) {
			if (getWsContext().getUserPrincipal() == null)
				throw new IllegalArgumentException("Simple user is not authenticated, cannot issue any ballot.");
			if (!userHasAllAssurance(vote.neededAssurances))
				throw new IllegalArgumentException("The user does not have all of the needed assurances.");
			if (vote.getRecordedBallotsCount(getWsUserName()).intValue() > 0)
				throw new IllegalArgumentException("This user already have a ballot.");

			vote.increaseRecordedBallots(getWsUserName());
		}

		String ballot = RandomUtils.createRandomKey();
		vote.ballots.add(ballot);
		return ballot;
	}

	public boolean userHasAllAssurance(final List<String> neededAssuranceList) {
		for (String neededAssurance : neededAssuranceList) {
			if (!hasAssurance(neededAssurance))
				return false;
		}
		return true;
	}

	@Override
	public CastVote castVote(final String voteId, final String ballot, final List<RankedChoice> theVote) {
		Vote vote = getVote(voteId);

		checkIfVotingEnabled(vote);
		checkIfUpdateConditionsAreConsistent(vote);
		validateBallot(ballot, vote);
		validatePreferences(theVote, vote);

		CastVote receipt;
		if (vote.canUpdate) {
			receipt = vote.addCastVote(getWsUserName(), theVote);
		} else {
			receipt = vote.addCastVote(null, theVote);
		}

		vote.ballots.remove(ballot);
		return receipt;
	}

	private void validatePreferences(final List<RankedChoice> theVote, final Vote vote) {
		for (RankedChoice choice : theVote) {
			validateOnePreference(vote, choice);
		}
	}

	private void validateOnePreference(final Vote vote, final RankedChoice choice) {
		if (!vote.choices.containsKey(choice.choiceId))
			throw new ReportedException("Invalid choiceId");
		if (choice.rank < 0)
			throw new ReportedException("Invalid rank");
	}

	private void validateBallot(final String ballot, final Vote vote) {
		if (!vote.ballots.contains(ballot))
			throw new ReportedException("Illegal ballot");
	}

	private void checkIfUpdateConditionsAreConsistent(final Vote vote) {
		if (vote.canUpdate && getWsContext().getUserPrincipal() == null)
			throw new ReportedException("canUpdate is true but the user is not authenticated");
	}

	private void checkIfVotingEnabled(final Vote vote) {
		if (!vote.voteParameters.canVote)
			throw new ReportedException("This issue cannot be voted on yet");
	}

	@Override
	public void modifyVote(final VoteAdminInfo voteAdminInfo, final String voteName) {
		Vote vote = getVote(voteAdminInfo.voteId);
		vote.checkAdminKey(voteAdminInfo.adminKey);
		ValidationUtil.checkVoteName(voteName);

		if (vote.hasIssuedBallots())
			throw new IllegalArgumentException("The vote cannot be modified there are ballots issued.");

		vote.name = voteName;
	}

	@Override
	public void deleteVote(final VoteAdminInfo adminInfo) {
		Vote vote = getVote(adminInfo.voteId);
		vote.checkAdminKey(adminInfo.adminKey);

		if (vote.hasIssuedBallots())
			throw new IllegalArgumentException("This vote cannot be deleted it has issued ballots.");

		session.remove(vote);
	}

	@Override
	public JsonObject showVote(final VoteAdminInfo adminInfo) {
		Vote vote = getVote(adminInfo.voteId);
		vote.checkAdminKey(adminInfo.adminKey);

		return vote.toJson();
	}

	@Override
	public String deleteChoice(final VoteAdminInfo voteAdminInfo, final String choiceId) {
		Vote vote = getVoteIfModifiable(voteAdminInfo.voteId, voteAdminInfo.adminKey);

		Choice votesChoice = vote.getChoice(choiceId);
		if ("user".equals(voteAdminInfo.adminKey))
			if (votesChoice.userName.equals(getWsUserName()))
				if (vote.voteParameters.canAddin) {
					vote.choices.remove(votesChoice.id);
				} else
					throw new IllegalArgumentException("The adminKey is \"user\" but canAddin is false.");
			else
				throw new IllegalArgumentException(
						"The adminKey is \"user\" but the user is not same with that user who added the choice.");
		else {
			vote.choices.remove(votesChoice.id);
		}
		return "OK";
	}

	@Override
	public void modifyChoice(final VoteAdminInfo adminInfo, final String choiceId, final String choiceName) {
		Vote vote = getVoteIfModifiable(adminInfo.voteId, adminInfo.adminKey);

		Choice votesChoice = vote.getChoice(choiceId);
		if ("user".equals(adminInfo.adminKey)) {
			if (!vote.voteParameters.canAddin)
				throw new ReportedException(
						"Choice modification disallowed: adminKey is user, but canAddin is false");

			if (!votesChoice.userName.equals(getWsUserName()))
				throw new ReportedException(
						"Choice modification disallowed: adminKey is user, "
								+ "and the choice was added by a different user",
								votesChoice.userName);
		}

		votesChoice.name = choiceName;
	}

	@Override
	public void setVoteParameters(final VoteAdminInfo adminInfo, final VoteParameters voteParameters) {
		Vote vote = getVote(adminInfo.voteId);
		vote.checkAdminKey(adminInfo.adminKey);

		if (voteParameters.minEndorsements >= 0) {
			vote.setParameters(voteParameters);
		} else
			throw new ReportedException("Illegal minEndorsements", Integer.toString(voteParameters.minEndorsements));
	}
}
