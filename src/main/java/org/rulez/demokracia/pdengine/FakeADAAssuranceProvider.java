package org.rulez.demokracia.pdengine;

import java.util.List;

public class FakeADAAssuranceProvider implements ADAAssuranceProvider{
	private ADAAssuranceProvider assuranceProvider;

	public FakeADAAssuranceProvider(final FakeADAAssuranceProvider adaAssuranceProvider) {
		assuranceProvider = adaAssuranceProvider;
	}
	
	@Override
	public List<String> getassurancesFor(final String proxyId) {
		return getUser(proxyId).getAssurances();
	}
	
	private User getUser(final String proxyId) {
		User user = new User(proxyId);
		user.assurances.add("TestAssurances");
		return user;
	}
}
