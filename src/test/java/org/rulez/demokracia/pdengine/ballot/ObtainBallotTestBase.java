package org.rulez.demokracia.pdengine.ballot;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.apache.catalina.connector.CoyotePrincipal;
import org.junit.Before;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.rulez.demokracia.pdengine.authentication.AuthenticatedUserService;
import org.rulez.demokracia.pdengine.testhelpers.ThrowableTester;
import org.rulez.demokracia.pdengine.vote.Vote;

public class ObtainBallotTestBase extends ThrowableTester {

  protected static final String DONT_HAVE = "dontHave";
  protected static final String USER = "user";
  protected static final String HAVE = "have";
  protected static final String HAVE2 = "haveToo";

  @InjectMocks
  protected BallotServiceImpl ballotService;

  @Mock
  protected AuthenticatedUserService authService;

  @Before
  public void setUp() {
    when(authService.getAuthenticatedUserName()).thenReturn(USER);
    when(authService.getUserPrincipal()).thenReturn(new CoyotePrincipal(USER));
    when(authService.hasAssurance(HAVE)).thenReturn(true);
  }

  protected Vote createVote(final List<String> neededAssurances) {
    return new Vote(
        "HolVoteHolNemVote", neededAssurances, new ArrayList<>(), false, 1
    );
  }
}
