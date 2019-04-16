package org.rulez.demokracia.pdengine;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.rulez.demokracia.pdengine.annotations.TestedBehaviour;
import org.rulez.demokracia.pdengine.annotations.TestedFeature;
import org.rulez.demokracia.pdengine.annotations.TestedOperation;
import org.rulez.demokracia.pdengine.testhelpers.CreatedDefaultChoice;

public class UserTest extends CreatedDefaultChoice {

  private static final String TEST = "Test";

  @Before
  public void setUp() {
    super.setUp();
  }

  @TestedFeature("Supporting functionality")
  @TestedOperation("User")
  @TestedBehaviour("the user has a proxy id")
  @Test
  public void check_proxy_id() {
    User user = new User(TEST);
    assertEquals(TEST, user.proxyId);
  }

  @TestedFeature("Supporting functionality")
  @TestedOperation("User")
  @TestedBehaviour("the user has a list of assurances")
  @Test
  public void check_empty_assurances() {
    User user = new User(TEST);
    assertTrue(user.assurances instanceof List);
  }

  @TestedFeature("Supporting functionality")
  @TestedOperation("User")
  @TestedBehaviour("the user has a list of assurances")
  @Test
  public void check_assurances() {
    User user = new User(TEST);
    String assurance = "TestAssurance";
    user.assurances.add(assurance);

    assertTrue(user.assurances.get(0).equals(assurance));
  }
}
