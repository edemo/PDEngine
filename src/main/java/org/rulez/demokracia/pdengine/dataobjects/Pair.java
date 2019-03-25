package org.rulez.demokracia.pdengine.dataobjects;

import org.rulez.demokracia.pdengine.persistence.BaseEntity;

public class Pair extends BaseEntity {
	
	private static final long serialVersionUID = 1L;
	public int winning;
	public int losing;
	
	public Pair() {
		super();
	}
	
	public Pair(int winning, int losing) {
		super();
		this.winning = winning;
		this.losing = losing;
	}

}
