package org.rulez.demokracia.pdengine.testhelpers;

import java.util.ArrayList;

import org.rulez.demokracia.pdengine.Vote;

public class VariantVote extends Vote {

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

	public void setisPrivate(boolean b) {
		isPrivate = b;
	}

	public void setCreationTime(long savedCreationTime) {
		this.creationTime = savedCreationTime;
	}
}