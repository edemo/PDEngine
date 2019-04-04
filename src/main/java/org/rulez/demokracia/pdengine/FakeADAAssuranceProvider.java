package org.rulez.demokracia.pdengine;

import java.util.ArrayList;
import java.util.List;

public class FakeADAAssuranceProvider {

	public FakeADAAssuranceProvider() {
	}
	
	public List<String> getassurancesFor(String proxyId) {
		List<String> assurances = new ArrayList<String>();
		assurances.add("TestAssurances");
		
		return assurances;
	}
}
