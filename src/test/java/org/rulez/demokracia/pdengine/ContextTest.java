package org.rulez.demokracia.pdengine;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

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
    final WebServiceContext wsContext = mock(WebServiceContext.class);
    final VoteManager voteManager = new VoteManager(wsContext);
    final WebServiceContext wsContextFromVoteManager =
        voteManager.getWsContext();
    assertEquals(wsContextFromVoteManager, wsContext);
  }

}
