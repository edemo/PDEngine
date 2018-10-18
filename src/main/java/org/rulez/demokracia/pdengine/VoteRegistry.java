package org.rulez.demokracia.pdengine;

import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.rulez.demokracia.pdengine.dataobjects.VoteAdminInfo;
import org.rulez.demokracia.pdengine.dataobjects.VoteEntity;
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
	public VoteAdminInfo createVote(String voteName, Set<String> neededAssurances, Set<String> countedAssurances,
			boolean isClosed, int minEndorsements) throws ReportedException {

		Validate.checkVoteName(voteName);

		VoteAdminInfo admininfo = new VoteAdminInfo();
		VoteEntity vote = new Vote(voteName, Validate.checkAssurances(neededAssurances, "needed"),
				Validate.checkAssurances(countedAssurances, "counted"), isClosed, minEndorsements);
		admininfo.adminKey = vote.adminKey;
		admininfo.voteId = vote.id;
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
	public void endorseChoice(String proxyUserName, String adminKey, String voteId, String choiceId,
			String givenUserName) {
		Vote vote = getVote(voteId);
		validateVoteId(voteId, vote);
		validateAdminKey(adminKey, vote);
		Choice choice = getChoice(voteId, choiceId);
		givenUserName = figureOutUserName(proxyUserName, adminKey, givenUserName, vote);
		validateChoiceId(choiceId, choice);
		choice.endorse(givenUserName);
	}

	private void validateChoiceId(String choiceId, Choice choice) {
		if(null == choice) {
			String message = String.format("Illegal choiceId: %s", choiceId);
			throw new IllegalArgumentException(message);
		}
	}

	private String figureOutUserName(String proxyUserName, String adminKey, String givenUserName, Vote vote) {
		if (adminKey.equals("user")) {
			checkIfVoteIsEndorseable(vote);
			givenUserName = proxyUserName;
		}
		return givenUserName;
	}

	private void validateAdminKey(String adminKey, Vote vote) {
		if(!vote.adminKey.equals(adminKey) && !adminKey.equals("user") ) {
			String message = String.format("Illegal adminKey: %s", adminKey);
			throw new IllegalArgumentException(message);
		}
	}

	private void checkIfVoteIsEndorseable(Vote vote) {
		if (!vote.canEndorse) {
			throw new IllegalArgumentException("user cannot endorse this vote");
		}
	}

	private void validateVoteId(String voteId, Vote vote) {
		if (null == vote) {
			throw new IllegalArgumentException(String.format("illegal voteId: %s", voteId));
		}
	}

}
