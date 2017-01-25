package org.rulez.demokracia.PDEngine;

import static org.junit.Assert.*;

import org.junit.Test;
import org.rulez.demokracia.PDEngine.annotations.tested_behaviour;
import org.rulez.demokracia.PDEngine.annotations.tested_feature;
import org.rulez.demokracia.PDEngine.annotations.tested_operation;

public class VoteInitialValuesTest {

	@Test
	@tested_feature("Manage votes")
	@tested_operation("create vote")
	@tested_behaviour("The vote initially can only be modified with the adminkey")
	public void test() {
		fail("Not yet implemented");
	}

}
