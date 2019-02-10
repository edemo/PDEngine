package org.rulez.demokracia.pdengine;

import static org.junit.Assert.*;

import java.security.Principal;

import javax.xml.ws.WebServiceContext;

import org.junit.Before;
import org.junit.Test;
import org.rulez.demokracia.pdengine.exception.ReportedException;
import org.rulez.demokracia.pdengine.testhelpers.CreatedDefaultChoice;

import static org.mockito.Mockito.*;

public class AuthenticationTest extends CreatedDefaultChoice {

	private EngineSessionContext sessionContext;

	@Before
	public void setUp() throws ReportedException {
		WebServiceContext wsContext = mock(WebServiceContext.class);
		Principal principal = mock(Principal.class);
		when(wsContext.getUserPrincipal()).thenReturn(principal);
		when(principal.getName()).thenReturn("userke");
		when(wsContext.isUserInRole("magyar")).thenReturn(true);
		when(wsContext.isUserInRole("appmagyar")).thenReturn(true);
		sessionContext = new EngineSessionContext(wsContext);
		super.setUp();
	}

	@Test
	public void request_test() {
		Principal user = sessionContext .getUserPrincipal();
		assertEquals("userke", user.getName());
		assertTrue(sessionContext.isUserInRole("magyar"));
	}

}
