package org.rulez.demokracia.pdengine;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.rulez.demokracia.pdengine.annotations.TestedBehaviour;
import org.rulez.demokracia.pdengine.annotations.TestedFeature;
import org.rulez.demokracia.pdengine.annotations.TestedOperation;

public class RandomUtilsTest {

  private static final int NUMRUNS = 1024;

  @TestedFeature("Supporting functionality")
  @TestedOperation("Create random key")
  @TestedBehaviour("random key is string")
  @Test
  public void random_key_is_string() {
    assertTrue(RandomUtils.createRandomKey() instanceof String);
  }

  @TestedFeature("Supporting functionality")
  @TestedOperation("Create random key")
  @TestedBehaviour("random key is random")
  @Test
  public void createRandomKey_return_different_strings_for_each_call() {
    List<String> randoms = new ArrayList<>();
    obtainRandoms(randoms);
    assertRandomsAreDifferent(randoms);
  }

  private void assertRandomsAreDifferent(final List<String> randoms) {
    for (int source = 0; source < NUMRUNS; source++) {
      for (int dest = 0; dest < NUMRUNS; dest++) {
        if (source != dest) {
          assertNotEquals(randoms.get(source), randoms.get(dest));
        }
      }
    }
  }

  private void obtainRandoms(final List<String> randoms) {
    for (int i = 0; i < NUMRUNS; i++) {
      randoms.add(RandomUtils.createRandomKey());
    }
  }

}
