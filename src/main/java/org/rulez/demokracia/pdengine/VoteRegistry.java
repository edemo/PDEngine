package org.rulez.demokracia.pdengine;

public class VoteRegistry extends ChoiceManager implements IVoteManager {
	public VoteRegistry() {
		super();
	}
	
	@Override
	public String obtainBallot(String id, String adminKey) {
		return RandomUtils.createRandomKey();
	}

}
