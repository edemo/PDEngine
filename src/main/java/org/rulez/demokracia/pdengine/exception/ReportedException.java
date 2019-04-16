package org.rulez.demokracia.pdengine.exception;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class ReportedException extends RuntimeException {

  private static final long serialVersionUID = 3322550743512295289L;

  private final List<String> additionalDetails = new ArrayList<>();

  public ReportedException(final String message, final String detail) {
    super(message);
    additionalDetails.add(detail);
  }

  public ReportedException(final String message) {
    super(message);
    additionalDetails.add("no additional detail has given");
  }

  @Override
  public String getMessage() {
    return MessageFormat.format(super.getMessage(), additionalDetails.get(0));
  }

  public JsonObject toJSON() {
    JsonObject jsonObject = new JsonObject();
    jsonObject.addProperty("message", super.getMessage());
    jsonObject.add("details", new Gson().toJsonTree(additionalDetails));
    return jsonObject;
  }

}
