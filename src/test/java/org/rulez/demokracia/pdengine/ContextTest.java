package org.rulez.demokracia.pdengine;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import javax.xml.ws.WebServiceContext;

import org.junit.Test;
import org.rulez.demokracia.pdengine.annotations.TestedBehaviour;
import org.rulez.demokracia.pdengine.annotations.TestedFeature;
import org.rulez.demokracia.pdengine.annotations.TestedOperation;
import org.rulez.demokracia.pdengine.testhelpers.CreatedDefaultVoteRegistry;

public class ContextTest extends CreatedDefaultVoteRegistry {

  @TestedFeature("Supporting functionality")
  @TestedOperation("get context parameter")
  @TestedBehaviour("context parameters can be obtained from the VoteManager")
  @Test
  public void obtain_context_parameter_from_the_VoteManager() {
    WebServiceContext wsContext = mock(WebServiceContext.class);
    VoteManager voteManager = new VoteManager(wsContext);
    WebServiceContext wsContextFromVoteManager = voteManager.getWsContext();
    assertEquals(wsContextFromVoteManager, wsContext);
  }

}
