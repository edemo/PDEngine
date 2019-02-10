package org.rulez.demokracia.pdengine.testhelpers;

import java.util.Arrays;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.security.Principal;
import java.util.HashSet;
import java.util.Set;

import javax.xml.ws.WebServiceContext;

import org.junit.Before;
import org.rulez.demokracia.pdengine.IVoteManager;
import org.rulez.demokracia.pdengine.Vote;
import org.rulez.demokracia.pdengine.dataobjects.VoteAdminInfo;
import org.rulez.demokracia.pdengine.exception.ReportedException;

public class CreatedDefaultVoteRegistry extends ThrowableTester{

	public IVoteManager voteManager;
	public VoteAdminInfo adminInfo;
	public String voteName = "VoteInitialValuesTest";

	protected Set<String> neededAssurances;
	protected Set<String> countedAssurances;
	protected boolean isPrivate;
	protected int minEndorsements;

	@Before
	public void setUp() throws ReportedException {
		WebServiceContext wsContext = setupMockWsContext();
		voteManager = IVoteManager.getVoteManager(wsContext);
		neededAssurances = new HashSet<>();
		countedAssurances = new HashSet<>();
		isPrivate = true;
		minEndorsements = 0;
		neededAssurances.add("magyar");
        voteName = "testVote";
		adminInfo = createAVote();
	}

	private WebServiceContext setupMockWsContext() {
		WebServiceContext wsContext = mock(WebServiceContext.class);
		Principal principal = mock(Principal.class);
		when(wsContext.getUserPrincipal()).thenReturn(principal);
		when(principal.getName()).thenReturn("test_user_in_ws_context");
		when(wsContext.isUserInRole("magyar")).thenReturn(true);
		when(wsContext.isUserInRole("appmagyar")).thenReturn(true);
		return wsContext;
	}

	protected VoteAdminInfo createAVote() throws ReportedException {
		return voteManager.createVote(voteName, neededAssurances, countedAssurances, isPrivate, minEndorsements );
	}

	protected Vote getTheVote() {
		return voteManager.getVote(adminInfo.voteId);
	}

	protected void setVoteEndorseable() {
		Vote vote = getTheVote();
		vote.canEndorse=true;
	}
	
	protected String createLongString(int length) {
		char[] charArray = new char[length];
	    Arrays.fill(charArray, 'w');
	    String str256 = new String(charArray);
		return str256;
	}


}
