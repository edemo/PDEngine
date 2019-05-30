package org.rulez.demokracia.pdengine.showvote;

import static org.junit.Assert.*;
import static org.rulez.demokracia.pdengine.showvote.VoteShowAssertUtil.*;
import java.util.Objects;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.rulez.demokracia.pdengine.annotations.TestedBehaviour;
import org.rulez.demokracia.pdengine.annotations.TestedFeature;
import org.rulez.demokracia.pdengine.annotations.TestedOperation;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

@TestedFeature("Manage votes")
@TestedOperation("show vote")
@TestedBehaviour("returns the vote in json")
@RunWith(MockitoJUnitRunner.class)
public class VoteShowTest extends ShowVoteJSONTestBase {

  private static final String COUNTED_ASSURANCE = "CountedAssurance";
  private static final String ADMIN_KEY = "adminKey";
  private static final String COUNTED_ASSURANCES = "countedAssurances";
  private static final String NEEDED_ASSURANCES = "neededAssurances";
  private static final String CHOICES = "choices";
  private static final String CREATION_TIME = "creationTime";
  private static final String NAME = "name";

  @Before
  @Override
  public void setUp() {
    super.setUp();
  }

  @Test
  public void the_name_attribute_contains_the_name_of_the_vote() {
    assertEquals(result.get(NAME).getAsString(), vote.getName());
  }

  @Test
  public void the_creationTime_attribute_contains_the_creation_time_of_the_vote() {
    assertEquals(result.get(CREATION_TIME).getAsLong(), vote.getCreationTime());
  }

  @Test
  public void the_choices_attribute_contains_the_choices_of_the_vote() {
    assertEquals(vote.getChoices().size(), result.get(CHOICES).getAsJsonObject().entrySet().size());
  }

  @Test
  public void the_initiator_of_the_choice_is_in_the_json() {
    final JsonObject choicesJson = result.get(CHOICES).getAsJsonObject();
    assertChoicesJsonContainsAllUserNames(choicesJson, vote.getChoices());
  }

  @Test
  public void the_countedAssurances_attribute_contains_the_counted_assurances_of_the_vote() {
    vote.getCountedAssurances().add(COUNTED_ASSURANCE);

    final JsonArray jsonCountedAssurances = createJson().get(COUNTED_ASSURANCES).getAsJsonArray();

    assertJsonContainsAllElement(jsonCountedAssurances, vote.getCountedAssurances());
  }

  @Test
  public void the_neededAssurances_attribute_contains_the_needed_assurances_of_the_vote() {
    final JsonArray jsonNeededAssurances = result.get(NEEDED_ASSURANCES).getAsJsonArray();

    assertJsonContainsAllElement(jsonNeededAssurances, vote.getNeededAssurances());
  }

  @Test
  public void the_id_attribute_contains_the_id_of_the_vote() {
    assertEquals(result.get("id").getAsString(), vote.getId());
  }

  @Test
  public void the_adminKey_attribute_should_not_be_contained_by_json() {
    final JsonElement obj = result.get(ADMIN_KEY);
    assertTrue(Objects.isNull(obj));
  }

}
