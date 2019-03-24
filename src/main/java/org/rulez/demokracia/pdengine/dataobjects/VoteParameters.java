package org.rulez.demokracia.pdengine.dataobjects;

public class VoteParameters {
	public int minEndorsements;
	public boolean canAddin;
	public boolean canEndorse;
	public boolean canVote;
	public boolean canView;

	public VoteParameters(
			final int minEndorsements,
			final boolean canAddin,
			final boolean canEndorse,
			final boolean canVote,
			final boolean canView) {
		this.minEndorsements = minEndorsements;
		this.canAddin = canAddin;
		this.canEndorse = canEndorse;
		this.canVote = canVote;
		this.canView = canView;
	}
}