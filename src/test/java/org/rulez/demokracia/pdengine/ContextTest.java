package org.rulez.demokracia.pdengine;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import javax.xml.ws.WebServiceContext;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Test;
import org.rulez.demokracia.pdengine.annotations.tested_behaviour;
import org.rulez.demokracia.pdengine.annotations.tested_feature;
import org.rulez.demokracia.pdengine.annotations.tested_operation;
import org.rulez.demokracia.pdengine.exception.ReportedException;
import org.rulez.demokracia.pdengine.testhelpers.CreatedDefaultVoteRegistry;

public class ContextTest extends CreatedDefaultVoteRegistry {


	@Before
	public void setUp() throws ReportedException {
		super.setUp();
	}

	@tested_feature("Supporting functionality")
	@tested_operation("get context parameter")
	@tested_behaviour("context parameters can be obtained from the VoteManager")
	@Test
	public void obtain_context_parameter_from_the_VoteManager() {
		WebServiceContext wsContext = mock(WebServiceContext.class);
		VoteManager voteManager = new VoteManager(wsContext);
		WebServiceContext wsContextFromVoteManager = voteManager.getWsContext();
		assertEquals(wsContextFromVoteManager, wsContext);
	}

}
