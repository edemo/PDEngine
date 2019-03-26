package org.rulez.demokracia.pdengine.dataobjects;

import org.rulez.demokracia.pdengine.persistence.BaseEntity;

public class VoteParameters extends BaseEntity {

	private static final long serialVersionUID = 1L;

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