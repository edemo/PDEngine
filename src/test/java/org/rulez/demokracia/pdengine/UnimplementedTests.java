package org.rulez.demokracia.pdengine;

import org.junit.Test;
import org.rulez.demokracia.pdengine.annotations.TestedBehaviour;
import org.rulez.demokracia.pdengine.annotations.TestedFeature;
import org.rulez.demokracia.pdengine.annotations.TestedOperation;
import org.rulez.demokracia.testhelpers.ThrowableTester;

@TestedFeature("Unimplemented")
@TestedOperation("Unimplemented")
@TestedBehaviour("Unimplemented")
public class UnimplementedTests extends ThrowableTester {

  @Test
  public void getAssurances_in_ADAAssuranceManager_is_unimplemented() {
    final ADAAssuranceManager a = new ADAAssuranceManager();
    assertUnimplemented(() -> a.getAssurances("UnImplementedTest"));
  }
}
