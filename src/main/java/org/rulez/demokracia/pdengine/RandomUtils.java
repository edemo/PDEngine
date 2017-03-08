package org.rulez.demokracia.pdengine;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class RandomUtils {
    static SecureRandom entropySource;

	public static String createRandomKey() {
		initializeEntropySource();
		return Long.toHexString(entropySource.nextLong());
	}

	private static void initializeEntropySource() {
		if(null == entropySource) {
			try {
				entropySource=SecureRandom.getInstance("NativePRNGBlocking");
			} catch (NoSuchAlgorithmException e) {
				System.out.println("no NativePRNGBlocking random implementation");
				System.exit(-1);
			}
		}
	}
}