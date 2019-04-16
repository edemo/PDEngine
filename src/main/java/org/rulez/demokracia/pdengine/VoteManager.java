package org.rulez.demokracia.pdengine;

import javax.xml.ws.WebServiceContext;

import org.rulez.demokracia.pdengine.exception.ReportedException;
import org.rulez.demokracia.pdengine.vote.Vote;

public class VoteManager extends SessionFactoryManager {

	public VoteManager(final WebServiceContext wsContext) {
		super(wsContext);
	}

	public Vote getVote(final String voteId) {
		Vote vote = session.get(Vote.class, voteId);
		validateVoteId(voteId, vote);
		return vote;
	}

	private void validateVoteId(final String voteId, final Vote vote) {
		if (null == vote) {
			throw new ReportedException("illegal voteId", voteId);
		}
	}


	protected void checkIfVoteIsEndorseable(final String voteId) {
		Vote vote = getVote(voteId);
		if(! vote.isEndorsable()) {
			throw new ReportedException("user cannot endorse this vote");
		}
	}

}