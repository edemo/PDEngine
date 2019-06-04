package org.rulez.demokracia.pdengine.vote;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import java.util.Set;
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
import org.rulez.demokracia.pdengine.dataobjects.VoteAdminInfo;
import org.rulez.demokracia.pdengine.testhelpers.LongStringGeneratorTestHelper;
import org.rulez.demokracia.pdengine.testhelpers.ThrowableTester;

@TestedFeature("Manage votes")
@TestedOperation("create vote")
@TestedBehaviour("formally validates all inputs")
@RunWith(MockitoJUnitRunner.class)
public class VoteCreationCountedAssurancesValidationTest extends ThrowableTester {

  @InjectMocks
  private VoteServiceImpl voteService;

  @Mock
  private VoteRepository voteRepository;

  private CreateVoteRequest request;
  private final ArgumentCaptor<Vote> voteCaptor = ArgumentCaptor.forClass(Vote.class);

  @Before
  public void setUp() {
    request = new CreateVoteRequest();
    request.setVoteName("Voldemort");
    request.setNeededAssurances(Set.of());
  }

  @Test
  public void countedAssurances_is_checked_not_to_contain_strings_longer_than_255() {
    final String str255 = LongStringGeneratorTestHelper.generate(255);

    createAVote(Set.of(str255));
    final String str256 = str255 + "w";
    assertThrows(() -> createAVote(Set.of(str256)))
        .assertMessageIs("string too long: counted assurance name");
  }

  @Test
  public void countedAssurances_is_checked_not_to_contain_strings_shorter_than_3() {
    createAVote(Set.of("aaa"));
    assertThrows(() -> createAVote(Set.of("aa")))
        .assertMessageIs("string too short: counted assurance name");
  }

  @Test
  public void counted_assurances_should_not_contain_space() {
    assertThrows(() -> createAVote(Set.of("This contains spaces")))
        .assertMessageIs("invalid characters in counted assurance name");
  }

  @Test
  public void counted_assurances_should_not_contain_tab() {
    assertThrows(() -> createAVote(Set.of("thiscontainstab\t")))
        .assertMessageIs("invalid characters in counted assurance name");
  }

  @Test
  public void counted_assurances_can_contain_local_characters() {
    String stringWithLocalCharacters = "ThisConatinsLocaCharséűáőúöüóíÉÁŰŐÚÖÜÓÍ";
    Set<String> countedAssurances = Set.of(stringWithLocalCharacters);
    createAVote(countedAssurances);
    verify(voteRepository).save(voteCaptor.capture());
    assertTrue(voteCaptor.getValue().getCountedAssurances().contains(stringWithLocalCharacters));
  }

  @Test
  public void counted_assurance_can_be_empty() {
    createAVote(Set.of(""));
    verify(voteRepository).save(voteCaptor.capture());
    assertTrue(voteCaptor.getValue().getCountedAssurances().contains(null));
  }

  private VoteAdminInfo createAVote(final Set<String> countedAssurances) {
    request.setCountedAssurances(countedAssurances);
    return voteService.createVote(request);
  }
}
