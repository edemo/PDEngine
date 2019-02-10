package org.rulez.demokracia.pdengine;

import java.security.Principal;

import javax.xml.ws.WebServiceContext;

public class EngineSessionContext {

	private WebServiceContext wsContext;

	public EngineSessionContext(WebServiceContext wsContext) {
		this.wsContext = wsContext;
	}

	public Principal getUserPrincipal() {
		return wsContext.getUserPrincipal();
	}

	public boolean isUserInRole(String role) {
		return wsContext.isUserInRole(role);
	}

}
