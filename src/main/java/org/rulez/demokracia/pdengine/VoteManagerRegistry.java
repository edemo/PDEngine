package org.rulez.demokracia.pdengine;

public class VoteManagerRegistry {

	static IVoteManager manager;
	public static IVoteManager getVoteManager() {
		if (manager == null) {
			manager = new VoteRegistry();
		}
		return manager;
	}
}
