package org.rulez.demokracia.pdengine.authentication;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.rulez.demokracia.testhelpers.ThrowableTester;

@RunWith(MockitoJUnitRunner.class)
public class AuthenticatedUserServiceTest extends ThrowableTester {

	@InjectMocks
	AuthenticatedUserServiceImpl authenticatedUserService;

	@Test
	public void getAuthenticatedUserName_is_unimplemented_yet() throws Exception {
		assertUnimplemented(() -> authenticatedUserService.getAuthenticatedUserName());
	}

}
