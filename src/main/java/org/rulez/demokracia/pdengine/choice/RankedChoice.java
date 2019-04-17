package org.rulez.demokracia.pdengine.choice;

import javax.persistence.Entity;

@Entity
public class RankedChoice extends RankedChoiceEntity {

	private static final long serialVersionUID = 1L;

	public RankedChoice(final String choiceId, final int rank) {
		super();
		this.choiceId = choiceId;
		this.rank = rank;
	}
}
