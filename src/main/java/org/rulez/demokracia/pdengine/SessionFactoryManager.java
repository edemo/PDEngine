package org.rulez.demokracia.pdengine;

import javax.xml.ws.WebServiceContext;

import org.hibernate.Session;

public class SessionFactoryManager {

  protected Session session;
  private final WebServiceContext wsContext;

  public SessionFactoryManager(final WebServiceContext wsContext) {
    super();
    this.wsContext = wsContext;
    session = DBSessionManagerUtils.getDBSession();
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

}
