package org.rulez.demokracia.pdengine;

import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.rulez.demokracia.pdengine.dataobjects.Choice;
import org.rulez.demokracia.pdengine.dataobjects.Vote;
import org.rulez.demokracia.pdengine.dataobjects.VoteAdminInfo;
import org.rulez.demokracia.pdengine.exception.ReportedException;
import org.rulez.demokracia.pdengine.persistence.HibernateUtil;

public class VoteRegistry implements IVoteManager {
	private Session session;
	private SessionFactory sessionFactory;

	public VoteRegistry() {
		sessionFactory = HibernateUtil.getSessionFactory();
		session = sessionFactory.openSession();
	}
	
	public void close() {
		session.close();
		sessionFactory.close();
	}

	@Override
	public VoteAdminInfo createVote(
			String voteName,
			Set<String> neededAssurances,
			Set<String> countedAssurances,
			boolean isClosed,
			int minEndorsements) throws ReportedException {

        Validate.checkVoteName(voteName);

		VoteAdminInfo admininfo = new VoteAdminInfo();
		Vote vote = new Vote (
				voteName,
				Validate.checkAssurances(neededAssurances, "needed"),
				Validate.checkAssurances(countedAssurances, "counted"),
				isClosed,
				minEndorsements);
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
