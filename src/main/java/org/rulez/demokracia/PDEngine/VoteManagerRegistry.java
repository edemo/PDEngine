package org.rulez.demokracia.PDEngine;

public class VoteManagerRegistry {

	static IVoteManager manager;
	public static IVoteManager getVoteManager() {
		if (manager == null) {
			manager = new VoteRegistry();
		}
		return manager;
	}
}
