package org.rulez.demokracia.pdengine;

import java.util.ArrayList;

import javax.persistence.Entity;

import org.rulez.demokracia.pdengine.dataobjects.ChoiceEntity;

@Entity
public class Choice extends ChoiceEntity {

	private static final long serialVersionUID = 1L;

	public Choice(String choiceName, String user) {
		super();
		name = choiceName;
		endorsers = new ArrayList<String>();
		userName = user;
	}

	public void endorse(String userName) {
		endorsers.add(userName);
	}

}
