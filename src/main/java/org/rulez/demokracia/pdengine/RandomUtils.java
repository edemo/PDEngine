package org.rulez.demokracia.pdengine;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.logging.Level;
import java.util.logging.Logger;

final public class RandomUtils {
    private static SecureRandom entropySource;
    private static final Logger LOGGER = Logger.getLogger( RandomUtils.class.getName() );

    private RandomUtils() {
    }

	public static String createRandomKey() {
		initializeEntropySource();
		return Long.toHexString(entropySource.nextLong());
	}

	private static void initializeEntropySource() {
		if(null == entropySource) {
			try {
				entropySource=SecureRandom.getInstance("NativePRNGBlocking");
			} catch (NoSuchAlgorithmException e) {
				LOGGER.log(Level.SEVERE, "no NativePRNGBlocking random implementation", e);
				System.exit(-1);
			}
		}
	}
}