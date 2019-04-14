package org.rulez.demokracia.pdengine;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.rulez.demokracia.pdengine.annotations.TestedBehaviour;
import org.rulez.demokracia.pdengine.annotations.TestedFeature;
import org.rulez.demokracia.pdengine.annotations.TestedOperation;
import org.rulez.demokracia.pdengine.testhelpers.CreatedDefaultChoice;
import org.rulez.demokracia.testhelpers.ThrowableTester;

@TestedFeature("Supporting functionality")
@TestedOperation("User")
@TestedBehaviour("the user has a proxy id")
public class UnimplementedTests extends ThrowableTester {

	@Test
	public void a() {
		ADAAssuranceManager a = new ADAAssuranceManager();
		assertUnimplemented(() -> a.getAssurances("UnImplementedTest"));		
	}
}
