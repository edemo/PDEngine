package org.rulez.demokracia.pdengine.authentication;

import java.security.Principal;

public interface AuthenticatedUserService {

  String getAuthenticatedUserName();

  Principal getUserPrincipal();

  boolean hasAssurance(String neededAssurance);

}
