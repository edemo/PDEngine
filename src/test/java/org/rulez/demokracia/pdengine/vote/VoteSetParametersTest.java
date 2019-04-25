package org.rulez.demokracia.pdengine.vote;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.rulez.demokracia.pdengine.RandomUtils;
import org.rulez.demokracia.pdengine.annotations.TestedBehaviour;
import org.rulez.demokracia.pdengine.annotations.TestedFeature;
import org.rulez.demokracia.pdengine.annotations.TestedOperation;
import org.rulez.demokracia.pdengine.dataobjects.VoteAdminInfo;
import org.rulez.demokracia.pdengine.dataobjects.VoteParameters;
import org.rulez.demokracia.pdengine.testhelpers.ThrowableTester;

@TestedFeature("Manage votes")
@TestedOperation("set vote parameters")
@RunWith(MockitoJUnitRunner.class)
public class VoteSetParametersTest extends ThrowableTester {

  private static final String VALIDATES_INPUTS = "validates inputs";

  private static final String SETS_THE_PARAMETERS_OF_THE_VOTE =
      "sets the parameters of the vote";

  private static final String VOTE_INVARIANTS = "vote invariants";

  @InjectMocks
  private VoteServiceImpl voteService;

  @Mock
  private VoteRepository voteRepository;

  private Vote vote;
  private String originVoteId;
  private String originAdminKey;
  private List<String> originNeededAssurances;
  private List<String> originCountedAssurances;
  private Boolean originIsPrivate;
  private Boolean originCanUpdate;
  private long originCreationTime;
  private VoteParameters voteParameters;

  @Before
  public void setUp() {
    vote = new Vote("name", Set.of(), Set.of(), false, 1);

    when(voteRepository.findById(vote.getId())).thenReturn(Optional.of(vote));

    saveOriginalValues();
    setVoteParameters();

    voteService.setVoteParameters(
        new VoteAdminInfo(vote.getId(), vote.getAdminKey()), voteParameters
    );
  }

  private void setVoteParameters() {
    voteParameters = new VoteParameters();
    voteParameters.setMinEndorsements(0);
    voteParameters.setAddinable(true);
    voteParameters.setEndorsable(true);
    voteParameters.setVotable(true);
    voteParameters.setViewable(true);
  }

  private void saveOriginalValues() {
    originVoteId = vote.getId();
    originAdminKey = vote.getAdminKey();
    originNeededAssurances = new ArrayList<>(vote.getNeededAssurances());
    originCountedAssurances = new ArrayList<>(vote.getCountedAssurances());
    originIsPrivate = vote.isPrivate();
    originCreationTime = vote.getCreationTime();
    originCanUpdate = vote.getParameters().isUpdatable();
  }

  @TestedBehaviour(VALIDATES_INPUTS)
  @Test
  public void invalid_voteId_is_rejected() {
    String invalidVoteId = RandomUtils.createRandomKey();
    assertThrows(
        () -> {
          voteService.setVoteParameters(
              new VoteAdminInfo(invalidVoteId, vote.getAdminKey()), voteParameters
          );
        }
    ).assertMessageIs("illegal voteId");
  }

  @TestedBehaviour(VALIDATES_INPUTS)
  @Test
  public void invalid_adminKey_is_rejected() {
    String invalidAdminKey = RandomUtils.createRandomKey();
    assertThrows(
        () -> voteService.setVoteParameters(
            new VoteAdminInfo(vote.getId(), invalidAdminKey), voteParameters
        )
    ).assertMessageIs("Illegal adminKey");
  }

  @TestedBehaviour(VALIDATES_INPUTS)
  @Test
  public void invalid_minEndorsements_is_rejected() {
    int invalidMinEndorsements = -2;
    voteParameters.setMinEndorsements(invalidMinEndorsements);
    assertThrows(
        () -> voteService.setVoteParameters(
            new VoteAdminInfo(vote.getId(), vote.getAdminKey()), voteParameters
        )
    ).assertMessageIs("Illegal minEndorsements");
  }

  @TestedBehaviour(SETS_THE_PARAMETERS_OF_THE_VOTE)
  @Test
  public void
      setVoteParameters_sets_the_minEndorsement_parameter_of_the_vote() {
    assertEquals(
        voteParameters.getMinEndorsements(),
        vote.getParameters().getMinEndorsements()
    );
  }

  @TestedBehaviour(SETS_THE_PARAMETERS_OF_THE_VOTE)
  @Test
  public void setVoteParameters_sets_the_canAddIn_parameter_of_the_vote() {
    assertEquals(true, vote.getParameters().isAddinable());
  }

  @TestedBehaviour(SETS_THE_PARAMETERS_OF_THE_VOTE)
  @Test
  public void setVoteParameters_sets_the_canEndorse_parameter_of_the_vote() {
    assertEquals(true, vote.getParameters().isEndorsable());
  }

  @TestedBehaviour(SETS_THE_PARAMETERS_OF_THE_VOTE)
  @Test
  public void setVoteParameters_sets_the_canVote_parameter_of_the_vote() {
    assertEquals(true, vote.getParameters().isVotable());
  }

  @TestedBehaviour(SETS_THE_PARAMETERS_OF_THE_VOTE)
  @Test
  public void setVoteParameters_sets_the_canView_parameter_of_the_vote() {
    assertEquals(true, vote.getParameters().isViewable());
  }

  @TestedBehaviour(VOTE_INVARIANTS)
  @Test
  public void setVoteParameters_does_not_overwrite_vote_id_value() {
    assertEquals(originVoteId, vote.getId());
  }

  @TestedBehaviour(VOTE_INVARIANTS)
  @Test
  public void setVoteParameters_does_not_overwrite_admin_key_value() {
    assertEquals(originAdminKey, vote.getAdminKey());
  }

  @TestedBehaviour(VOTE_INVARIANTS)
  @Test
  public void setVoteParameters_does_not_overwrite_neededAssurances_value() {
    assertEquals(originNeededAssurances, vote.getNeededAssurances());
  }

  @TestedBehaviour(VOTE_INVARIANTS)
  @Test
  public void setVoteParameters_does_not_overwrite_countedAssurances_value() {
    assertEquals(originCountedAssurances, vote.getCountedAssurances());
  }

  @TestedBehaviour(VOTE_INVARIANTS)
  @Test
  public void setVoteParameters_does_not_overwrite_isPrivate_value() {
    assertEquals(originIsPrivate, vote.isPrivate());
  }

  @TestedBehaviour(VOTE_INVARIANTS)
  @Test
  public void setVoteParameters_does_not_overwrite_creationTime_value() {
    assertEquals(originCreationTime, vote.getCreationTime());
  }

  @TestedBehaviour("updatable is a vote invariant")
  @Test
  public void setVoteParameters_does_not_overwrite_canUpdate_value() {
    assertEquals(originCanUpdate, vote.getParameters().isUpdatable());
  }
}
