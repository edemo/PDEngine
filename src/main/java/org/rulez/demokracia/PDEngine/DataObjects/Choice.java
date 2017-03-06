package org.rulez.demokracia.PDEngine.DataObjects;

import java.util.ArrayList;
import java.util.List;

import org.rulez.demokracia.PDEngine.RandomUtils;

public class Choice {
	private String name;
	private String userName;
	private String choiceId;
	public List<String> endorsers;

	public Choice(String choiceName, String user) {
		name = choiceName;
		choiceId = RandomUtils.createRandomKey();
		endorsers = new ArrayList<String>();
		userName = user;
	}

	public String getId() {
		return choiceId;
	}

	public Object getUser() {
		return userName;
	}

	public String getName() {
		return name;
	}

	public void endorse(String userName) {
		endorsers.add(userName);
	}

}
