package org.rulez.demokracia.pdengine.testhelpers;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.security.Principal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.xml.ws.WebServiceContext;

import org.junit.Before;
import org.mockito.MockitoAnnotations;
import org.rulez.demokracia.pdengine.AssuranceManager;
import org.rulez.demokracia.pdengine.IVoteManager;
import org.rulez.demokracia.pdengine.Vote;
import org.rulez.demokracia.pdengine.dataobjects.VoteAdminInfo;
import org.rulez.demokracia.testhelpers.ThrowableTester;

public class CreatedDefaultVoteRegistry extends ThrowableTester {

  public static final String ASSURANCE_NAME = "magyar";
  public static final String TEST_USER_NAME = "test_user_in_ws_context";
  public IVoteManager voteManager;
  public VoteAdminInfo adminInfo;
  public String voteName = "VoteInitialValuesTest";

  protected Set<String> neededAssurances;
  protected Set<String> countedAssurances;
  protected boolean isPrivate;
  protected int minEndorsements;
  protected AssuranceManager fakeAssuranceManager;

  @Before
  public void setUp() {
    final WebServiceContext wsContext = setupMockWsContext();
    fakeAssuranceManager = mock(AssuranceManager.class);
    when(fakeAssuranceManager.getAssurances(TEST_USER_NAME))
        .thenReturn(Arrays.asList("magyar", "német"));
    voteManager = IVoteManager.getVoteManager(wsContext, fakeAssuranceManager);
    neededAssurances = new HashSet<>();
    countedAssurances = new HashSet<>();
    isPrivate = true;
    minEndorsements = 0;
    neededAssurances.add(ASSURANCE_NAME);
    voteName = "testVote";
    adminInfo = createAVote();
    setupInitialContextTest();
  }

  private void setupInitialContextTest() {
    MockitoAnnotations.initMocks(this);
    System.setProperty(
        "java.naming.factory.initial", InitialContextFactoryMock.class.getName()
    );
  }

  private WebServiceContext setupMockWsContext() {
    final WebServiceContext wsContext = mock(WebServiceContext.class);
    final Principal principal = mock(Principal.class);
    when(wsContext.getUserPrincipal()).thenReturn(principal);
    when(principal.getName()).thenReturn(TEST_USER_NAME);
    when(wsContext.isUserInRole(ASSURANCE_NAME)).thenReturn(true);
    when(wsContext.isUserInRole("appmagyar")).thenReturn(true);
    return wsContext;
  }

  public void setupUnauthenticatedMockWsContext() {
    final WebServiceContext wsContext = mock(WebServiceContext.class);
    when(wsContext.getUserPrincipal()).thenReturn(null);
    voteManager = IVoteManager.getVoteManager(wsContext, fakeAssuranceManager);
  }

  protected VoteAdminInfo createAVote() {
    return voteManager.createVote(
        voteName, neededAssurances, countedAssurances, isPrivate,
        minEndorsements
    );
  }

  protected Vote getTheVote() {
    return voteManager.getVote(adminInfo.voteId);
  }

  protected void setVoteEndorseable() {
    final Vote vote = getTheVote();
    vote.parameters.canEndorse = true;
  }

  protected String createLongString(final int length) {
    final char[] charArray = new char[length];
    Arrays.fill(charArray, 'w');
    return new String(charArray);
  }

}
