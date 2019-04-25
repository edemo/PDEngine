package org.rulez.demokracia.pdengine;

import java.io.IOException;

import org.rulez.demokracia.pdengine.exception.ReportedException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.google.gson.Gson;

import lombok.Data;

@ControllerAdvice
public class RestResponseExceptionHandler
    extends ResponseEntityExceptionHandler {

  @Data
  private class ErrorResponse {

    private Exception error;

    public ErrorResponse(final Exception error) {
      this.error = error;
    }
  }

  @ExceptionHandler({
      ReportedException.class
  })
  protected ResponseEntity<Object> handleBadRequest(
      final RuntimeException exception, final WebRequest request
  ) throws IOException {
    final String bodyOfResponse = new Gson().toJson(new ErrorResponse(exception));
    return handleExceptionInternal(
        exception, bodyOfResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST, request
    );
  }
}
