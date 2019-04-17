package org.rulez.demokracia.pdengine.choice;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;

import org.rulez.demokracia.pdengine.persistence.BaseEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Choice extends BaseEntity {

	private static final long serialVersionUID = 1L;
	private String name;
	private String userName;
	@ElementCollection
	private List<String> endorsers;

	public Choice(final String name, final String userName) {
		this.name = name;
		this.userName = userName;
		this.endorsers = new ArrayList<>();
	}

	public void endorse(final String userName) {
		endorsers.add(userName);
	}
}