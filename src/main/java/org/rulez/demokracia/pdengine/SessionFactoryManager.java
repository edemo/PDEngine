package org.rulez.demokracia.pdengine;

import javax.xml.ws.WebServiceContext;

import org.hibernate.Session;

public class SessionFactoryManager {

	protected Session session;
	private WebServiceContext wsContext;

	public SessionFactoryManager(WebServiceContext wsContext) {
		super();
		this.wsContext = wsContext;
		session = DBSessionManager.getDBSession();
	}

	public WebServiceContext getWsContext() {
		return wsContext;
	}

	public String getWsUserName() {
		return getWsContext().getUserPrincipal().getName();
	}

	public boolean hasAssurance(String role) {
		return getWsContext().isUserInRole(role);
	}

}