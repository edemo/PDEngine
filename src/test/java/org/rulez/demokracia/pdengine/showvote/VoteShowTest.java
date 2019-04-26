package org.rulez.demokracia.pdengine.showvote;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.rulez.demokracia.pdengine.annotations.TestedBehaviour;
import org.rulez.demokracia.pdengine.annotations.TestedFeature;
import org.rulez.demokracia.pdengine.annotations.TestedOperation;
import org.rulez.demokracia.pdengine.choice.Choice;
import org.rulez.demokracia.pdengine.dataobjects.VoteAdminInfo;
import org.rulez.demokracia.pdengine.vote.Vote;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

@TestedFeature("Manage votes")
@TestedOperation("show vote")
@TestedBehaviour("returns the vote in json")
@RunWith(MockitoJUnitRunner.class)
public class VoteShowTest extends ShowVoteTestBase {

  private static final String COUNTED_ASSURANCE = "CountedAssurance";
  private static final String ADMIN_KEY = "adminKey";
  private static final String CAN_VOTE = "votable";
  private static final String CAN_VIEW = "viewable";
  private static final String MIN_ENDORSEMENTS = "minEndorsements";
  private static final String COUNTED_ASSURANCES = "countedAssurances";
  private static final String NEEDED_ASSURANCES = "neededAssurances";
  private static final String CAN_ENDORSE = "endorsable";
  private static final String USER_NAME = "userName";
  private static final String CHOICES = "choices";
  private static final String CREATION_TIME = "creationTime";
  private static final String CAN_ADDIN = "addinable";
  private static final String VOTE_PARAMETERS = "parameters";
  private static final String NAME = "name";
  private static final String USER = "user";
  private static final String CHOICE_NAME = "choiceName";

  private Vote vote;
  private String voteId;
  private String adminKey;

  @Before
  public void setUp() {
    vote = new Vote(
        "votename", List.of("needed1", "needed2"), List.of("counted1", "counted2"), true, 3
    );
    vote.addChoice(new Choice(CHOICE_NAME, USER));
    voteId = vote.getId();
    adminKey = vote.getAdminKey();

    when(voteService.getVoteWithValidatedAdminKey(any())).thenReturn(vote);
  }

  @Test
  public void the_name_attribute_contains_the_name_of_the_vote() {
    JsonObject result = showVoteService.showVote(
        new VoteAdminInfo(
            voteId,
            adminKey
        )
    );

    assertEquals(result.get(NAME).getAsString(), vote.getName());
  }

  @Test
  public void
      the_canAddIn_attribute_contains_whether_the_voters_can_add_choices_to_the_vote() {
    JsonObject result = showVoteService.showVote(
        new VoteAdminInfo(
            voteId,
            adminKey
        )
    );

    assertEquals(
        result.get(VOTE_PARAMETERS).getAsJsonObject().get(CAN_ADDIN).getAsBoolean(),
        vote.getParameters().isAddinable()
    );
  }

  @Test
  public void
      the_creationTime_attribute_contains_the_creation_time_of_the_vote() {
    JsonObject result = showVoteService.showVote(
        new VoteAdminInfo(
            voteId,
            adminKey
        )
    );

    assertEquals(
        result.get(CREATION_TIME).getAsLong(),
        vote.getCreationTime()
    );
  }

  @Test
  public void the_choices_attribute_contains_the_choices_of_the_vote() {
    JsonObject result = showVoteService.showVote(
        new VoteAdminInfo(
            voteId,
            adminKey
        )
    );

    assertEquals(
        vote.getChoices().size(),
        result.get(CHOICES).getAsJsonObject().entrySet().size()
    );
  }

  @Test
  public void the_initiator_of_the_choice_is_in_the_json() {
    JsonObject choicesJson = showVoteService.showVote(
        new VoteAdminInfo(
            voteId,
            adminKey
        )
    ).get(CHOICES).getAsJsonObject();
    assertChoicesJsonContainsAllUserNames(choicesJson, vote.getChoices());
  }

  @Test
  public void
      the_canEndorse_attribute_contains_whether_the_voters_endorse_choices_of_the_vote() {
    JsonObject result = showVoteService.showVote(
        new VoteAdminInfo(
            voteId,
            adminKey
        )
    );

    assertEquals(
        result.get(VOTE_PARAMETERS).getAsJsonObject().get(CAN_ENDORSE).getAsBoolean(),
        vote.getParameters().isEndorsable()
    );
  }

  @Test
  public void
      the_countedAssurances_attribute_contains_the_counted_assurances_of_the_vote() {
    vote.getCountedAssurances().add(COUNTED_ASSURANCE);
    JsonObject result = showVoteService.showVote(
        new VoteAdminInfo(
            voteId,
            adminKey
        )
    );
    JsonArray jsonCountedAssurances =
        result.get(COUNTED_ASSURANCES).getAsJsonArray();

    assertJsonContainsAllElement(
        jsonCountedAssurances,
        vote.getCountedAssurances()
    );
  }

  @Test
  public void
      the_neededAssurances_attribute_contains_the_needed_assurances_of_the_vote() {
    JsonObject result = showVoteService.showVote(
        new VoteAdminInfo(
            voteId,
            adminKey
        )
    );
    JsonArray jsonNeededAssurances =
        result.get(NEEDED_ASSURANCES).getAsJsonArray();

    assertJsonContainsAllElement(
        jsonNeededAssurances,
        vote.getNeededAssurances()
    );
  }

  @Test
  public void
      the_minEndorsements_attribute_contains_the_mininimum_endorsements_of_the_vote() {
    JsonObject result = showVoteService.showVote(
        new VoteAdminInfo(
            voteId,
            adminKey
        )
    );

    assertEquals(
        result.get(VOTE_PARAMETERS).getAsJsonObject().get(MIN_ENDORSEMENTS).getAsInt(),
        vote.getParameters().getMinEndorsements()
    );
  }

  @Test
  public void the_id_attribute_contains_the_id_of_the_vote() {
    JsonObject result = showVoteService.showVote(
        new VoteAdminInfo(
            voteId,
            adminKey
        )
    );

    assertEquals(result.get("id").getAsString(), vote.getId());
  }

  @Test
  public void
      the_canView_attribute_contains_whether_the_voters_can_view_the_results() {
    JsonObject result = showVoteService.showVote(
        new VoteAdminInfo(
            voteId,
            adminKey
        )
    );

    assertEquals(
        result.get(VOTE_PARAMETERS).getAsJsonObject().get(CAN_VIEW).getAsBoolean(),
        vote.getParameters().isViewable()
    );
  }

  @Test
  public void the_canVote_attribute_contains_whether_the_votes_can_be_cast() {
    JsonObject result = showVoteService.showVote(
        new VoteAdminInfo(
            voteId,
            adminKey
        )
    );

    assertEquals(
        result.get(VOTE_PARAMETERS).getAsJsonObject().get(CAN_VOTE).getAsBoolean(),
        vote.getParameters().isVotable()
    );
  }

  @Test
  public void the_adminKey_attribute_should_not_be_contained_by_json() {
    JsonObject result = showVoteService.showVote(
        new VoteAdminInfo(
            voteId,
            adminKey
        )
    );
    JsonElement obj = result.get(ADMIN_KEY);
    assertTrue(Objects.isNull(obj));
  }

  private void assertEntrySetIsNotEmpty(final JsonObject choicesJson) {
    assertFalse(choicesJson.entrySet().isEmpty());
  }

  private void assertJsonContainsAllElement(
      final JsonArray jsonArray, final List<String> elements
  ) {
    GsonBuilder gsonBuilder = new GsonBuilder();
    assertFalse(elements.isEmpty());
    elements.stream().forEach(
        assurance -> assertTrue(
            jsonArray.contains(gsonBuilder.create().toJsonTree(assurance))
        )
    );
  }

  private void assertChoicesJsonContainsAllUserNames(
      final JsonObject choicesJson,
      final Map<String, Choice> choices
  ) {
    assertEntrySetIsNotEmpty(choicesJson);
    choicesJson.entrySet()
        .forEach(
            e -> assertJsonContainsUserName(e.getValue(), choices.get(e.getKey()).getUserName())
        );
  }

  private void assertJsonContainsUserName(
      final JsonElement entry, final String userName
  ) {
    assertEquals(
        entry.getAsJsonObject()
            .get(USER_NAME)
            .getAsString(),
        userName
    );
  }
}
