package org.rulez.demokracia.pdengine.dataobjects;

import org.rulez.demokracia.pdengine.persistence.BaseEntity;

public class RankedChoiceEntity extends BaseEntity {

	private static final long serialVersionUID = 1L;
	public String choiceId;
	public int rank;
	
	public RankedChoiceEntity() {
		super();
	}
	
	
}
