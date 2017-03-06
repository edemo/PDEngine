package org.rulez.demokracia.PDEngine.testhelpers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class ThrowableTester {

	private Throwable thrown;

	public ThrowableTester() {
		super();
	}

	public ThrowableTester assertThrows(Thrower thrower) {
		thrown = null;
		try {
			thrower.throwException();
		} catch (Throwable exception) {
			thrown = exception;
		}
		if (thrown == null) {
			fail("no exception thrown");			
		}
		return this;
	}

	public void assertMessageIs(String message) {
		assertEquals(message, thrown.getMessage());
	}
}
