package org.rulez.demokracia.pdengine.showvote;

import static org.junit.Assert.*;
import java.util.List;
import java.util.Map;
import org.rulez.demokracia.pdengine.choice.Choice;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public final class VoteShowAssertUtil {

  private static final String USER_NAME = "userName";

  public static void assertEntrySetIsNotEmpty(final JsonObject choicesJson) {
    assertFalse(choicesJson.entrySet().isEmpty());
  }

  public static void assertJsonContainsAllElement(final JsonArray jsonArray,
      final List<String> elements) {
    final GsonBuilder gsonBuilder = new GsonBuilder();
    assertFalse(elements.isEmpty());
    elements.stream().forEach(
        assurance -> assertTrue(jsonArray.contains(gsonBuilder.create().toJsonTree(assurance))));
  }

  public static void assertChoicesJsonContainsAllUserNames(final JsonObject choicesJson,
      final Map<String, Choice> choices) {
    assertEntrySetIsNotEmpty(choicesJson);
    choicesJson.entrySet().forEach(
        e -> assertJsonContainsUserName(e.getValue(), choices.get(e.getKey()).getUserName()));
  }

  public static void assertJsonContainsUserName(final JsonElement entry, final String userName) {
    assertEquals(entry.getAsJsonObject().get(USER_NAME).getAsString(), userName);
  }
}
