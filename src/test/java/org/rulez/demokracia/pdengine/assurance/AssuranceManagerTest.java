package org.rulez.demokracia.pdengine.assurance;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.rulez.demokracia.pdengine.annotations.TestedBehaviour;
import org.rulez.demokracia.pdengine.annotations.TestedFeature;
import org.rulez.demokracia.pdengine.annotations.TestedOperation;
import org.rulez.demokracia.pdengine.testhelpers.ThrowableTester;

@TestedFeature("Supporting functionality")
@TestedOperation("Assurance management")
@TestedBehaviour("Unimplemented")
@RunWith(MockitoJUnitRunner.class)
public class AssuranceManagerTest extends ThrowableTester {

  private static final String PROXY_ID = "proxyId";

  @InjectMocks
  private ADAAssuranceManager assuranceManager;

  @Test
  public void getAssurances_is_unimplemented_yet() throws Exception {
    assertUnimplemented(
        () -> assuranceManager.getAssurances(PROXY_ID)
    );
  }
}
