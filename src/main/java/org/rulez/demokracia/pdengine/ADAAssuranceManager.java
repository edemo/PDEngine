package org.rulez.demokracia.pdengine;

import java.util.List;

public class ADAAssuranceManager implements AssuranceManager{
	
	@Override
	public List<String> getAssurances(final String proxyId) {
		return getUser(proxyId).getAssurances();
	}
	
	private User getUser(final String proxyId) {
		User user = new User(proxyId);
		user.assurances.add("TestAssurances");
		return user;
	}
}
