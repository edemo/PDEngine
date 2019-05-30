package org.rulez.demokracia.pdengine.exception;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class ReportedExceptionTest {

  @Test
  public void testGetMessageReturnsDetailsWithUnaryConstructor() throws Exception {
    ReportedException exception =
        new ReportedException("Houston we have the following problem: {0}");
    assertEquals("Houston we have the following problem: no additional detail has given",
        exception.getMessage());
  }

  @Test
  public void testGetMessageReturnsDetailsWithBinaryConstructor() throws Exception {
    ReportedException exception =
        new ReportedException("Houston we have the following problem: {0}", "We missed the moon");
    assertEquals("Houston we have the following problem: We missed the moon",
        exception.getMessage());
  }
}
