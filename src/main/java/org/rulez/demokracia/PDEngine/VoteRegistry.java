package org.rulez.demokracia.PDEngine;

import org.rulez.demokracia.PDEngine.DataObjects.Vote;
import org.rulez.demokracia.PDEngine.DataObjects.VoteAdminInfo;
import org.rulez.demokracia.PDEngine.Persistence.HibernateUtil;
import org.rulez.demokracia.PDEngine.exception.DuplicateAssuranceException;
import org.rulez.demokracia.PDEngine.exception.NotValidCharsException;
import org.rulez.demokracia.PDEngine.exception.ReportedException;
import org.rulez.demokracia.PDEngine.exception.TooLongException;
import org.rulez.demokracia.PDEngine.exception.TooShortException;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
			List<String> neededAssurances,
			List<String> countedAssurances,
			boolean isClosed,
			int minEndorsements) throws ReportedException {

        checkVoteName(voteName);
        checkNeededAssurances(neededAssurances);
        checkCountedAssurances(countedAssurances);

		VoteAdminInfo admininfo = new VoteAdminInfo();
		Vote vote = new Vote (voteName, neededAssurances, countedAssurances, isClosed, minEndorsements);
		admininfo.adminKey=vote.adminKey;
		admininfo.voteId= vote.id;
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

    public static void checkVoteName(String voteName) throws ReportedException {
		checkString(voteName, "vote name");
    }

    public static void checkNeededAssurances (List<String> assurance) throws ReportedException {
		checkAssurances(assurance, "needed");
	}

    public static void checkCountedAssurances (List<String> assurance) throws ReportedException {
        checkAssurances(assurance, "counted");
    }

	private static void checkAssurances(List<String> list, String type) throws ReportedException{

		Set<String> uniques = new HashSet<>();

		for (String temp : list) {

			checkString(temp, type+" assurance name");

			if (!uniques.add(temp)) {
				throw new DuplicateAssuranceException(type);
			}
        }
	}

	private static void checkString (String inputString, String description) throws ReportedException{

		Pattern p = Pattern.compile("(\\d|\\w)+", Pattern.UNICODE_CHARACTER_CLASS);

		Matcher m = p.matcher(inputString);

		if (inputString.length() < 3) {
			throw new TooShortException(description);
		}

		if (inputString.length() > 255) {
			throw new TooLongException(description);
		}

		if (!m.matches()) {
			throw new NotValidCharsException(description);
		}

	}

}
