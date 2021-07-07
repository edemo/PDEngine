package org.rulez.demokracia.pdengine.testhelpers;

import static org.junit.Assert.*;

import org.apache.commons.lang3.exception.ExceptionUtils;

public class ThrowableTester {

  private Throwable thrown;

  public ThrowableTester assertThrows(final Thrower thrower) {
    try {
      thrower.throwException();
    } catch (final RuntimeException exception) {// NOPMD
      thrown = exception;
    }
    if (thrown == null)
      fail("no exception thrown");
    return this;
  }

  public void assertNotThrows(final Thrower thrower) {
    try {
      thrower.throwException();
    } catch (final Exception exception) {// NOPMD
      fail(
          "No exception expected, but " +
              exception.getClass().getCanonicalName() + " has been thrown"
      );
    }
  }

  public ThrowableTester assertMessageIs(final String message) {
    assertEquals(message, thrown.getMessage());
    return this;
  }

  public Throwable getException() {
    return thrown;
  }

  public ThrowableTester
      assertException(final Class<? extends RuntimeException> klass) {
    final String message =
        String
            .format(
                "expected %s but got %s", klass,
                ExceptionUtils.getStackTrace(thrown)
            );
    assertEquals(message, klass, thrown.getClass());
    return this;
  }

  public ThrowableTester assertUnimplemented(final Thrower thrower) {
    assertThrows(thrower).assertException(UnsupportedOperationException.class);
    return this;
  }
}
