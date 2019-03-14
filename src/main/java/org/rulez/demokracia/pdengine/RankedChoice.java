package org.rulez.demokracia.pdengine;

import javax.persistence.Entity;
import org.rulez.demokracia.pdengine.dataobjects.RankedChoiceEntity;

@Entity
public class RankedChoice extends RankedChoiceEntity {

	private static final long serialVersionUID = 1L;

	public RankedChoice(String choiceId, int rank) {
		super();
		this.choiceId = choiceId;
		this.rank = rank;
	}
}
