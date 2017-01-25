package org.rulez.demokracia.PDEngine;

import java.security.SecureRandom;
import java.util.Random;

public class RandomUtils {
    static SecureRandom entropySource = new SecureRandom();
    static Random randomSource = new Random();

	private static boolean entropyNeeded(int i) {
		return (i % 16) == 0;
	}

	private static String mkRandomHexString(int length) {
	    String ret = "";
	    for (int i = 0; i < length; ++i) {
	      if (entropyNeeded(i)) {
	          randomSource.setSeed(entropySource.nextLong());
	      }
	      ret += Integer.toHexString(randomSource.nextInt(16));
	    }
	    return ret;
	}

	public static String createRandomKey() {
		return mkRandomHexString(16);
	}
}