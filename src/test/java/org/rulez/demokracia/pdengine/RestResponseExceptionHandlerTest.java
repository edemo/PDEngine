package org.rulez.demokracia.pdengine;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import org.junit.Test;
import org.rulez.demokracia.pdengine.exception.ReportedException;
import org.rulez.demokracia.pdengine.exception.TooLongException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.WebRequest;

public class RestResponseExceptionHandlerTest {

  private static final String TEST_MESSAGE = "Test message";
  private final RestResponseExceptionHandler handler = new RestResponseExceptionHandler();
  private final WebRequest request = mock(WebRequest.class);

  @Test
  public void test_should_return_the_exception() throws Exception {
    final ReportedException exception = new TooLongException(TEST_MESSAGE);
    final ResponseEntity<Object> responseEntity = handler.handleBadRequest(exception, request);
    assertEquals(
        "{\"error\":{\"message\":\"string too long: {0}\",\"details\":[\"Test message\"]}}",
        responseEntity.getBody().toString());
  }

}
