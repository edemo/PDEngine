package org.rulez.demokracia.pdengine;

import java.util.List;

public class FakeADAAssuranceProvider implements ADAAssuranceProvider{
	private ADAAssuranceProvider assuranceProvider;

	public FakeADAAssuranceProvider(FakeADAAssuranceProvider adaAssuranceProvider) {
		setAssuranceProvider(adaAssuranceProvider);
	}
	
	public List<String> getassurancesFor(String proxyId) {
		return getUser(proxyId).getAssurances();
	}
	
	private User getUser(String proxyId) {
		User user = new User(proxyId);
		user.assurances.add("TestAssurances");
		return user;
	}

	public ADAAssuranceProvider getAssuranceProvider() {
		return assuranceProvider;
	}

	public void setAssuranceProvider(ADAAssuranceProvider assuranceProvider) {
		this.assuranceProvider = assuranceProvider;
	}
}
