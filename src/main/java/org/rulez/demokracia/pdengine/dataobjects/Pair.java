package org.rulez.demokracia.pdengine.dataobjects;

import org.rulez.demokracia.pdengine.persistence.BaseEntity;

public class Pair extends BaseEntity {
	
	private static final long serialVersionUID = 1L;
	public int key1;
	public int key2;
	
	public Pair() {
		super();
	}
	
	public Pair(int key1, int key2) {
		super();
		this.key1 = key1;
		this.key2 = key2;
	}

}
