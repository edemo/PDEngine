package org.rulez.demokracia.pdengine;

import java.io.Serializable;

public class RankedChoice implements Serializable {

	private static final long serialVersionUID = 1L;

	public String choiceId;
	public int rank;
	
	public RankedChoice(String choiceId, int rank) {
		this.choiceId=choiceId;
		this.rank=rank;
	}
	
}
