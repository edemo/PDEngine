package org.rulez.demokracia.pdengine.showvote;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class VoteShowVoteParametersTest extends ShowVoteJSONTestBase {

  private static final String CAN_VOTE = "votable";
  private static final String CAN_VIEW = "viewable";
  private static final String MIN_ENDORSEMENTS = "minEndorsements";
  private static final String CAN_ENDORSE = "endorsable";
  private static final String CAN_ADDIN = "addinable";
  private static final String VOTE_PARAMETERS = "parameters";

  @Before
  @Override
  public void setUp() {
    super.setUp();
  }

  @Test
  public void the_canAddIn_attribute_contains_whether_the_voters_can_add_choices_to_the_vote() {
    assertEquals(result.get(VOTE_PARAMETERS).getAsJsonObject().get(CAN_ADDIN).getAsBoolean(),
        vote.getParameters().isAddinable());
  }

  @Test
  public void the_canEndorse_attribute_contains_whether_the_voters_endorse_choices_of_the_vote() {
    assertEquals(result.get(VOTE_PARAMETERS).getAsJsonObject().get(CAN_ENDORSE).getAsBoolean(),
        vote.getParameters().isEndorsable());
  }

  @Test
  public void the_minEndorsements_attribute_contains_the_mininimum_endorsements_of_the_vote() {
    assertEquals(result.get(VOTE_PARAMETERS).getAsJsonObject().get(MIN_ENDORSEMENTS).getAsInt(),
        vote.getParameters().getMinEndorsements());
  }

  @Test
  public void the_canView_attribute_contains_whether_the_voters_can_view_the_results() {
    assertEquals(result.get(VOTE_PARAMETERS).getAsJsonObject().get(CAN_VIEW).getAsBoolean(),
        vote.getParameters().isViewable());
  }

  @Test
  public void the_canVote_attribute_contains_whether_the_votes_can_be_cast() {
    assertEquals(result.get(VOTE_PARAMETERS).getAsJsonObject().get(CAN_VOTE).getAsBoolean(),
        vote.getParameters().isVotable());
  }

}
