package org.rulez.demokracia.pdengine.testhelpers;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.rulez.demokracia.pdengine.RankedChoice;
import org.rulez.demokracia.pdengine.Vote;
import org.rulez.demokracia.pdengine.dataobjects.CastVote;
import org.rulez.demokracia.pdengine.dataobjects.ChoiceEntity;
import org.rulez.demokracia.pdengine.dataobjects.VoteAdminInfo;
import org.rulez.demokracia.pdengine.exception.ReportedException;

public class CreatedDefaultChoice extends CreatedDefaultVoteRegistry {

	protected String choiceId;
	protected String ballot;
	public List<RankedChoice> theCastVote;
	public Vote vote;

	@Before
	public void setUp() {
		super.setUp();
		addTestChoice();
		addTestPreference();

	}

	private void addTestPreference() {
		theCastVote = new ArrayList<>();
		theCastVote.add(new RankedChoice(choiceId, 1));
	}

	protected void addTestChoice() {
		choiceId = voteManager.addChoice(new VoteAdminInfo(adminInfo.voteId, adminInfo.adminKey), "choice1", "user");
	}

	protected ChoiceEntity getChoice(final String theChoice) {
		return voteManager.getChoice(adminInfo.voteId, theChoice);
	}

	protected String addMyChoice() {
		return voteManager.addChoice(new VoteAdminInfo(adminInfo.voteId, adminInfo.adminKey), "choice2", null);
	}

	protected void assertValidationFailsWithMessage(final String message) {
		assertThrows(() -> {
			voteManager.endorseChoice(new VoteAdminInfo(adminInfo.voteId, adminInfo.adminKey), choiceId, "testuserke");
		})
				.assertException(ReportedException.class).assertMessageIs(message);
	}

	protected void addSecondDummies() {
		addCastVote("dummy4", theCastVote);
		addCastVote("dummy5", theCastVote);
		addCastVote("dummy6", theCastVote);
		addCastVote("dummy7", theCastVote);
	}

	protected void addfirstDummies() {
		addCastVote("dummy1", theCastVote);
		addCastVote("dummy2", theCastVote);
		addCastVote("dummy3", theCastVote);
	}

	protected void addCastVote(final String proxyId, final List<RankedChoice> theCastVote) {
		vote.votesCast.add(new CastVote(proxyId, theCastVote));
	}

	protected void initializeVoteCastTest() {
		ballot = voteManager.obtainBallot(adminInfo.voteId, adminInfo.adminKey);
		theCastVote = new ArrayList<>();
		vote = getTheVote();
		vote.parameters.canVote = true;
		vote.votesCast.clear();
	}

}