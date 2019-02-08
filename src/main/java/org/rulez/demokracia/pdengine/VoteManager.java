package org.rulez.demokracia.pdengine;

import java.util.Set;

import org.rulez.demokracia.pdengine.dataobjects.VoteAdminInfo;
import org.rulez.demokracia.pdengine.dataobjects.VoteEntity;
import org.rulez.demokracia.pdengine.exception.ReportedException;

public class VoteManager extends SessionFactoryManager {

	public VoteManager() {
		super();
	}

	public VoteAdminInfo createVote(String voteName, Set<String> neededAssurances, Set<String> countedAssurances, boolean isClosed, int minEndorsements)
			throws ReportedException {

			    Validate.checkVoteName(voteName);

				VoteAdminInfo admininfo = new VoteAdminInfo();
				VoteEntity vote = new Vote (
						voteName,
						Validate.checkAssurances(neededAssurances, "needed"),
						Validate.checkAssurances(countedAssurances, "counted"),
						isClosed,
						minEndorsements);
				admininfo.adminKey = vote.adminKey;
				admininfo.voteId = vote.id;
				session.save(vote);
				return admininfo;
			}

	public Vote getVote(String voteId) {
		return session.get(Vote.class, voteId);
	}

	protected void checkIfVoteIsEndorseable(String voteId) {
		Vote vote = getVote(voteId);
		if(! vote.canEndorse) {
			throw new IllegalArgumentException("user cannot endorse this vote");
		}
	}

}