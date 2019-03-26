package org.rulez.demokracia.pdengine;

import java.util.Set;

import javax.xml.ws.WebServiceContext;

import org.rulez.demokracia.pdengine.dataobjects.VoteAdminInfo;
import org.rulez.demokracia.pdengine.dataobjects.VoteEntity;
import org.rulez.demokracia.pdengine.exception.ReportedException;

public class VoteManager extends SessionFactoryManager {

	public VoteManager(final WebServiceContext wsContext) {
		super(wsContext);
	}

	public VoteAdminInfo createVote(final String voteName, final Set<String> neededAssurances,
			final Set<String> countedAssurances, final boolean isClosed, final int minEndorsements)
	{

		ValidationUtil.checkVoteName(voteName);

		VoteAdminInfo admininfo = new VoteAdminInfo();
		VoteEntity vote = new Vote (
				voteName,
				ValidationUtil.checkAssurances(neededAssurances, "needed"),
				ValidationUtil.checkAssurances(countedAssurances, "counted"),
				isClosed,
				minEndorsements);
		admininfo.adminKey = vote.adminKey;
		admininfo.voteId = vote.id;
		session.save(vote);
		return admininfo;
	}

	public Vote getVote(final String voteId) {
		Vote vote = session.get(Vote.class, voteId);
		validateVoteId(voteId, vote);
		return vote;
	}

	private void validateVoteId(final String voteId, final Vote vote) {
		if (null == vote)
			throw new ReportedException("illegal voteId", voteId);
	}


	protected void checkIfVoteIsEndorseable(final String voteId) {
		Vote vote = getVote(voteId);
		if (!vote.voteParameters.canEndorse)
			throw new ReportedException("user cannot endorse this vote");
	}

}