package org.rulez.demokracia.pdengine;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;

import org.rulez.demokracia.pdengine.dataobjects.UserEntity;

@Entity
public class User extends UserEntity {

	private static final long serialVersionUID = 1L;

	public User(final String proxyId)  {
		super();
		this.proxyId = proxyId;
		assurances = new ArrayList<>();
	}
	
	public List<String> getAssurances() {
		return assurances;
	}

}
