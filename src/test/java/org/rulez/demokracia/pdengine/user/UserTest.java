package org.rulez.demokracia.pdengine.user;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.rulez.demokracia.pdengine.annotations.TestedBehaviour;
import org.rulez.demokracia.pdengine.annotations.TestedFeature;
import org.rulez.demokracia.pdengine.annotations.TestedOperation;
import org.rulez.demokracia.pdengine.user.User;

public class UserTest {

	private static final String TEST = "Test";

	@TestedFeature("Supporting functionality")
	@TestedOperation("User")
	@TestedBehaviour("the user has a proxy id")
	@Test
	public void check_proxy_id() {
		User user = new User(TEST);
		assertEquals(TEST, user.getProxyId());
	}

	@TestedFeature("Supporting functionality")
	@TestedOperation("User")
	@TestedBehaviour("the user has a list of assurances")
	@Test
	public void check_empty_assurances() {
		User user = new User(TEST);
		assertTrue(user.getAssurances() instanceof List);
	}

	@TestedFeature("Supporting functionality")
	@TestedOperation("User")
	@TestedBehaviour("the user has a list of assurances")
	@Test
	public void check_assurances() {
		User user = new User(TEST);
		String assurance = "TestAssurance";
		user.getAssurances().add(assurance);

		assertTrue(user.getAssurances().get(0).equals(assurance));
	}
}
