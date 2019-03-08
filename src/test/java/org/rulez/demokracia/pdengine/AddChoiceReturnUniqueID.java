package org.rulez.demokracia.pdengine;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.rulez.demokracia.pdengine.annotations.tested_behaviour;
import org.rulez.demokracia.pdengine.annotations.tested_feature;
import org.rulez.demokracia.pdengine.annotations.tested_operation;
import org.rulez.demokracia.pdengine.exception.ReportedException;
import org.rulez.demokracia.pdengine.testhelpers.CreatedDefaultChoice;

@tested_feature("Manage votes")
@tested_operation("Add choice")
@tested_behaviour("returns a unique choice id")
public class AddChoiceReturnUniqueID extends CreatedDefaultChoice{

	@Before
	public void setUp() throws ReportedException {
		super.setUp();
	}

	@Test
	public void choice_ids_are_unique() {
		Set<String> existingIds = new HashSet<String>();
		for(int i=0;i<100;i++) {
			String myChoiceId = addMyChoice();
			assertFalse(existingIds.contains(myChoiceId));
			existingIds.add(myChoiceId);
		}
	}
}


