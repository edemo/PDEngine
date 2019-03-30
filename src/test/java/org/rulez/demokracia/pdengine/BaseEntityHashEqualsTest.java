package org.rulez.demokracia.pdengine;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;
import org.rulez.demokracia.pdengine.annotations.TestedBehaviour;
import org.rulez.demokracia.pdengine.annotations.TestedFeature;
import org.rulez.demokracia.pdengine.annotations.TestedOperation;
import org.rulez.demokracia.pdengine.dataobjects.Pair;

@TestedFeature("Supporting functionality")
@TestedOperation("BaseEntity")
@TestedBehaviour("None of the entities count id into the equals and hashCode.")
public class BaseEntityHashEqualsTest {


	@Test
	public void entities_with_equal_fields_are_equal() {
		Pair pair1 = new Pair(12, 42);
		Pair pair2 = new Pair(12, 42);
		assertNotEquals(pair1.id, pair2.id);
		assertEquals(pair1, pair2);
	}

	@Test
	public void entities_with_different_fields_are_not_equal() {
		Pair pair1 = new Pair(12, 42);
		Pair pair2 = new Pair(12, 69);
		assertNotEquals(pair1, pair2);
	}

	@Test
	public void equal_entities_have_the_same_hash_code() {
		Pair pair1 = new Pair(12, 42);
		Pair pair2 = new Pair(12, 42);
		assertEquals(pair1.hashCode(), pair2.hashCode());
	}
}