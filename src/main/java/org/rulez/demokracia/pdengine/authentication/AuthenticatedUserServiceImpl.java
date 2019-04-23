package org.rulez.demokracia.pdengine.authentication;

import java.security.Principal;

import org.springframework.stereotype.Service;

@Service
public class AuthenticatedUserServiceImpl implements AuthenticatedUserService {

	@Override
	public String getAuthenticatedUserName() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Principal getUserPrincipal() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean hasAssurance(final String neededAssurance) {
		throw new UnsupportedOperationException();
	}

}
