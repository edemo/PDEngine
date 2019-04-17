package org.rulez.demokracia.pdengine;

import javax.xml.ws.WebServiceContext;

import org.rulez.demokracia.pdengine.vote.Vote;

public class VoteManager extends SessionFactoryManager {

	public VoteManager(final WebServiceContext wsContext) {
		super(wsContext);
	}

	public Vote getVote(final String voteId) {
		throw new UnsupportedOperationException();
	}

	protected void checkIfVoteIsEndorseable(final String voteId) {
		throw new UnsupportedOperationException();
	}

}