package org.rulez.demokracia.pdengine;
import java.util.ArrayList;

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
	public void the_getAssurances_method_is_not_implemented_yet() {
		assertUnimplemented(() -> new CastVote("proxyId", new ArrayList<>()).getAssurances());
	}
}
