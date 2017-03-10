package org.rulez.demokracia.pdengine;

public class VoteManagerRegistry {

	static IVoteManager manager;

	private VoteManagerRegistry() {
	}

	public static IVoteManager getVoteManager() {
		if (manager == null) {
			manager = new VoteRegistry();
		}
		return manager;
	}
}
