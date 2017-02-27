package org.rulez.demokracia.PDEngine.DataObjects;

import org.rulez.demokracia.PDEngine.RandomUtils;

public class Choice {
	private String name;
	private String user;
	private String choiceId;

	public Choice(String choiceName, String user) {
		name = choiceName;
		choiceId = RandomUtils.createRandomKey();
		this.user = user;
	}

	public String getId() {
		return choiceId;
	}

	public Object getUser() {
		return user;
	}

	public String getName() {
		return name;
	}

}
