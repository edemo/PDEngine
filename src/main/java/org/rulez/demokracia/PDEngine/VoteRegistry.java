package org.rulez.demokracia.PDEngine;

import org.rulez.demokracia.PDEngine.DataObjects.Vote;
import org.rulez.demokracia.PDEngine.DataObjects.VoteAdminInfo;
import org.rulez.demokracia.PDEngine.Persistence.HibernateUtil;
import org.rulez.demokracia.PDEngine.exception.ReportedException;

import java.util.Set;

import org.hibernate.Session;
import org.rulez.demokracia.PDEngine.DataObjects.Choice;

public class VoteRegistry implements IVoteManager {
	private Session session;

	public VoteRegistry() {
		session = HibernateUtil.getSessionFactory().openSession();
	}
	@Override
	public VoteAdminInfo createVote(
			String voteName,
			Set<String> neededAssurances,
			Set<String> countedAssurances,
			boolean isClosed,
			int minEndorsements) throws ReportedException {

        Validate.checkVoteName(voteName);
        neededAssurances=Validate.checkAssurances(neededAssurances, "needed");
        countedAssurances=Validate.checkAssurances(countedAssurances, "counted");

		VoteAdminInfo admininfo = new VoteAdminInfo();
		Vote vote = new Vote (voteName, neededAssurances, countedAssurances, isClosed, minEndorsements);
		admininfo.setAdminKey(vote.adminKey);
		admininfo.setVoteId(vote.id);
		session.save(vote);
		return admininfo;
	}

	@Override
	public Vote getVote(String voteId) {
		return session.get(Vote.class, voteId);
	}

	@Override
	public String addChoice(String adminKey, String voteId, String choiceName, String user) {
		return getVote(voteId).addChoice(choiceName, user);
	}

	@Override
	public Choice getChoice(String voteId, String choiceId) {
		return getVote(voteId).getChoice(choiceId);
	}

	@Override
	public void endorseChoice(String adminKey, String voteId, String choiceId, String userName) {
		getChoice(voteId, choiceId).endorse(userName);
	}

}
