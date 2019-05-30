package org.rulez.demokracia.pdengine.votecast;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.rulez.demokracia.pdengine.annotations.TestedBehaviour;
import org.rulez.demokracia.pdengine.annotations.TestedFeature;
import org.rulez.demokracia.pdengine.annotations.TestedOperation;
import org.rulez.demokracia.pdengine.choice.RankedChoice;

@TestedFeature("Vote")
@TestedOperation("Cast vote")
@TestedBehaviour("the vote receipt is signed by the server")
@RunWith(MockitoJUnitRunner.class)
public class VoteCastSignatureTest {

  private static final String SIGNATURE = "S1GNature";

  @InjectMocks
  private CastVoteSignatureServiceImpl castVoteSignatureService;

  @Mock
  private MessageSignerService messageSignerService;

  private CastVote castVote;

  private String castVoteString;

  private final ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);

  @Before
  public void setUp() {
    castVote = new CastVote("proxyId", List.of(new RankedChoice("choiceId", 1)));

    castVoteString = "proxyId|" + castVote.getSecretId() + "|"
        + castVote.getPreferences().get(0).getId() + "|choiceId|1";
    when(messageSignerService.signatureOfMessage(captor.capture())).thenReturn(SIGNATURE);
    castVoteSignatureService.signCastVote(castVote);
  }

  @Test
  public void test_vote_cast_signature_is_set() {
    assertEquals(SIGNATURE, castVote.getSignature());
  }

  @Test
  public void test_vote_cast_signature_signs_the_appropriate_string() {
    assertEquals(castVoteString, captor.getValue());
  }
}
