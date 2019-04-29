package org.rulez.demokracia.pdengine.choice;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.rulez.demokracia.pdengine.annotations.TestedBehaviour;
import org.rulez.demokracia.pdengine.annotations.TestedFeature;
import org.rulez.demokracia.pdengine.annotations.TestedOperation;
import org.rulez.demokracia.pdengine.dataobjects.VoteAdminInfo;

@TestedFeature("Manage votes")
@TestedOperation("Add choice")
@RunWith(MockitoJUnitRunner.class)
public class AddChoiceAddsChoiceToTheVoteTest extends ChoiceTestBase {

  private Choice choice;

  @Override
  @Before
  public void setUp() {
    super.setUp();
  }

  @TestedBehaviour("registers the choice with the vote")
  @Test
  public void created_choice_is_registered_with_the_vote() {
    choice = choiceService.addChoice(
        new VoteAdminInfo(vote.getId(), vote.getAdminKey()), "choice1", "user"
    );
    assertEquals(
        "choice1",
        choice.getName()
    );
  }

  @TestedBehaviour("registers the user with the choice")
  @Test
  public void creating_user_is_registered_with_the_choice() {
    choice = choiceService.addChoice(
        new VoteAdminInfo(vote.getId(), vote.getAdminKey()), "choice1", "user"
    );
    assertEquals("user", choice.getUserName());
  }

  @TestedBehaviour("registers the user with the choice")
  @Test
  public void if_no_user_then_null_is_Registered() {
    final Choice newChoice = choiceService.addChoice(
        new VoteAdminInfo(vote.getId(), vote.getAdminKey()), "choice", null
    );
    assertEquals(null, newChoice.getUserName());
  }

  @TestedBehaviour("registers the user with the choice")
  @Test
  public void choice_is_added_to_vote() {
    final Choice newChoice = choiceService.addChoice(
        new VoteAdminInfo(vote.getId(), vote.getAdminKey()), "choice", null
    );
    assertEquals(newChoice, vote.getChoice(newChoice.getId()));
  }

  @TestedBehaviour("returns a unique choice id")
  @Test
  public void choice_ids_are_unique() {
    final VoteAdminInfo adminInfo =
        new VoteAdminInfo(vote.getId(), vote.getAdminKey());
    final Set<String> existingIds = new HashSet<>();
    for (int i = 0; i < 100; i++) {
      final String myChoiceId =
          choiceService.addChoice(adminInfo, "choice" + i, "hyperuser").getId();
      assertFalse(existingIds.contains(myChoiceId));
      existingIds.add(myChoiceId);
    }
  }
}
