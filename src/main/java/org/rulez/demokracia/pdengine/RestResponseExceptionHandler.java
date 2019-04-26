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
import com.google.gson.JsonObject;

@ControllerAdvice
public class RestResponseExceptionHandler
    extends ResponseEntityExceptionHandler {

  @ExceptionHandler({
      ReportedException.class
  })
  protected ResponseEntity<Object> handleBadRequest(
      final RuntimeException exception, final WebRequest request
  ) throws IOException {
    final JsonObject bodyOfResponse = new JsonObject();
    bodyOfResponse.add(
        "error", new Gson().toJsonTree(((ReportedException) exception).toJSON())
    );
    return handleExceptionInternal(
        exception, bodyOfResponse.toString(), new HttpHeaders(),
        HttpStatus.BAD_REQUEST,
        request
    );
  }
}
