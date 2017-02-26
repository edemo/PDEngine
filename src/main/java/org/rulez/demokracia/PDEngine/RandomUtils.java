package org.rulez.demokracia.PDEngine;

import java.security.SecureRandom;
import java.util.Random;

public class RandomUtils {
    static SecureRandom entropySource = new SecureRandom();
    static Random randomSource = new Random();

	public static String createRandomKey() {
		return Long.toHexString(entropySource.nextLong());
	}
}