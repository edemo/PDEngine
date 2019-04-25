package org.rulez.demokracia.pdengine.authentication;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.rulez.demokracia.pdengine.testhelpers.ThrowableTester;

@RunWith(MockitoJUnitRunner.class)
public class AuthenticatedUserServiceTest extends ThrowableTester {

  @InjectMocks
  private AuthenticatedUserServiceImpl authenticatedUserService;

  @Test
  public void getAuthenticatedUserName_is_unimplemented_yet() throws Exception {
    assertUnimplemented(
        () -> authenticatedUserService.getAuthenticatedUserName()
    );
  }

  @Test
  public void getUserPrincipal_is_unimplemented_yet() throws Exception {
    assertUnimplemented(() -> authenticatedUserService.getUserPrincipal());
  }

  @Test
  public void hasAssurance_is_unimplemented_yet() throws Exception {
    assertUnimplemented(
        () -> authenticatedUserService.hasAssurance("assurance")
    );
  }

}
