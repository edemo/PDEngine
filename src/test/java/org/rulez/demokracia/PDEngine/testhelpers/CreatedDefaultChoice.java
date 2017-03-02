package org.rulez.demokracia.PDEngine.testhelpers;

import org.rulez.demokracia.PDEngine.VoteRegistry;
import org.rulez.demokracia.PDEngine.DataObjects.Vote;

public class CreatedDefaultChoice extends CreatedDefaultVoteRegistry {

	protected Vote vote;
	protected String choiceId;

	public CreatedDefaultChoice() {
		super();
	}

	public void setUp() {
		super.setUp();
		this.addTestChoice();
	}

	protected void addTestChoice() {
		vote = VoteRegistry.getByKey(adminInfo.adminKey);
		choiceId = vote.addChoice("choice1", "user");
	}

}