package org.rulez.demokracia.PDEngine;

import java.util.ArrayList;
import java.util.List;

import org.rulez.demokracia.PDEngine.DataObjects.Vote;

class VariantVote extends Vote {

	private static final long serialVersionUID = 1L;

	public VariantVote() {
		super("variant", new ArrayList<String>(), new ArrayList<String>(), false, 3);
	}

	public void setId(String string) {
		id = string;
	}

	public void setAdminKey(String string) {
		adminKey = string;
	}

	public void setNeededAssurances(List<String> badAssurances) {
		neededAssurances = badAssurances;
	}

	public void setCountedAssurances(List<String> badAssurances) {
		countedAssurances = badAssurances;
	}

	public void setisPrivate(boolean b) {
		isPrivate = b;
	}

	public void setCreationTime(long savedCreationTime) {
		this.creationTime = savedCreationTime;
	}
}