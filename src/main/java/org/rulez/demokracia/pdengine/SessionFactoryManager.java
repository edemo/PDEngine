package org.rulez.demokracia.pdengine;

import java.util.List;

import javax.xml.ws.WebServiceContext;

import org.hibernate.Session;

public class SessionFactoryManager {

	protected Session session;
	private FakeADAAssuranceProvider fakeADAAssuranceProvider;
	private final WebServiceContext wsContext;

	public SessionFactoryManager(final WebServiceContext wsContext) {
		super();
		this.wsContext = wsContext;
		session = DBSessionManagerUtils.getDBSession();
		fakeADAAssuranceProvider = new FakeADAAssuranceProvider();
	}

	public WebServiceContext getWsContext() {
		return wsContext;
	}

	public String getWsUserName() {
		return getWsContext().getUserPrincipal().getName();
	}

	public boolean hasAssurance(final String role) {
		return getWsContext().isUserInRole(role);
	}
	
	public FakeADAAssuranceProvider getFakeADAAssuranceProvider() {
		return fakeADAAssuranceProvider;
	}
	
	public List<String> getAssurances() {
		return getFakeADAAssuranceProvider().getassurancesFor(getWsUserName());
	}

}