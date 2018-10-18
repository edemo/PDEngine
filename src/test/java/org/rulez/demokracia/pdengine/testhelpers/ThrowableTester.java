package org.rulez.demokracia.pdengine.testhelpers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.apache.commons.lang3.exception.ExceptionUtils;

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

	public ThrowableTester assertMessageIs(String message) {
		assertEquals(message, thrown.getMessage());
		return this;
	}

	public Throwable getException() {
		return thrown;
	}

	public ThrowableTester assertException(Class<? extends RuntimeException> klass) {
		String message = String.format("expected %s but got %s", klass, ExceptionUtils.getStackTrace(thrown));
		assertEquals(message, klass, thrown.getClass());
		return this;
	}
}
