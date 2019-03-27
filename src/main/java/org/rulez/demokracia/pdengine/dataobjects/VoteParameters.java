package org.rulez.demokracia.pdengine.dataobjects;

import java.io.Serializable;

import javax.persistence.Entity;

@Entity
public class VoteParameters implements Serializable{

	private static final long serialVersionUID = 1L;

	public int minEndorsements;
	public boolean canAddin;
	public boolean canEndorse;
	public boolean canVote;
	public boolean canView;
	public boolean canUpdate;

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

	public VoteParameters() {
		// TODO Auto-generated constructor stub
	}
}