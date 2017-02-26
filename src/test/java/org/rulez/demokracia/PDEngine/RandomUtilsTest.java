package org.rulez.demokracia.PDEngine;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class RandomUtilsTest {

	private static final int NUMRUNS = 1024;

	@Test
	public void createRandomKey_return_different_strings_for_each_call() {
		List<String> randoms = new ArrayList<String>();
		obtainRandoms(randoms);
		assertRandomsAreDifferent(randoms);
	}

	private void assertRandomsAreDifferent(List<String> randoms) {
		for(int source = 0 ; source<NUMRUNS ; source++) {
			for(int dest = 0 ; dest<NUMRUNS ; dest++) {
				if (source != dest) {
					assertNotEquals(randoms.get(source), randoms.get(dest));
				}
			}
		}
	}

	private void obtainRandoms(List<String> randoms) {
		for(int i=0; i<NUMRUNS ; i++) {
			randoms.add(RandomUtils.createRandomKey());
		}
	}

}
