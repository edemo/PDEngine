package org.rulez.demokracia.pdengine;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.rulez.demokracia.pdengine.annotations.tested_behaviour;
import org.rulez.demokracia.pdengine.annotations.tested_feature;
import org.rulez.demokracia.pdengine.annotations.tested_operation;
import org.rulez.demokracia.pdengine.exception.ReportedException;
import org.rulez.demokracia.pdengine.testhelpers.CreatedDefaultChoice;

public class UserTest extends CreatedDefaultChoice {

	@Before
	public void setUp() throws ReportedException {
		super.setUp();
	}

	@tested_feature("Supporting functionality")
	@tested_operation("User")
	@tested_behaviour("the user has a proxy id")
	@Test
	public void check_proxy_id() {
		User user = new User("Test");
		assertEquals("Test", user.proxyId);		
	}
	
	@tested_feature("Supporting functionality")
	@tested_operation("User")
	@tested_behaviour("the user has a list of assurances")
	@Test
	public void check_assurances() {
		User user = new User("Test");
		assertTrue(user.assurances instanceof List);		
	}
}
