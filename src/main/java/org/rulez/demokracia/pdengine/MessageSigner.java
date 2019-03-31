package org.rulez.demokracia.pdengine;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.util.Base64;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.rulez.demokracia.pdengine.exception.ReportedException;

public class MessageSigner {

	private KeyStore keyStore=null;
	private PublicKey pubkey;
	private PrivateKey privkey;
	final private String KEYSTOREPW="changit";
	//final private String KEYSTOREPATH="/home/chris/keystore/keystore.pk12";
	private String keyStorePath;
	final private String KEYALIAS="PDEngineKeys";

    private static class Storage {
        private static final MessageSigner INSTANCE = new MessageSigner();
     }

	private MessageSigner () throws ReportedException {

		try {
			InitialContext context = new InitialContext();
			Context xmlNode = (Context) context.lookup("java:comp/env");
			String keyStorePath = (String) xmlNode.lookup("keyStorePath");
		} catch (NamingException e2) {
			//throw new ReportedException("Cannot reach GlobalNamingResources");

			// running in test environment, use keystore put in ~/keystore
			keyStorePath= System.getenv("HOME") + "/keystore/keystore.pk12";
		}

		try {
			keyStore = KeyStore.getInstance("PKCS12");
		} catch (KeyStoreException e) {
			throw new ReportedException("Cannot handle PKCS12 keystore");
		}

    	char[] keyStorePassword = KEYSTOREPW.toCharArray();
    	try (InputStream keyStoreData = new FileInputStream(keyStorePath)) {

	    	keyStore.load(keyStoreData, keyStorePassword);

			privkey = (PrivateKey) keyStore.getKey(KEYALIAS, keyStorePassword);

	    	Certificate cert = keyStore.getCertificate(KEYALIAS);
	    	pubkey = cert.getPublicKey();

    	} catch ( KeyStoreException e) {
    		throw new ReportedException("Cannot initialize keystore type PKCS12");
    	} catch (FileNotFoundException e1) {
    		throw new ReportedException("Cannot open keystore: " + keyStorePath);
		} catch (IOException e1) {
			throw new ReportedException("IO error on file:" + keyStorePath);
		} catch (NoSuchAlgorithmException e) {
			throw new ReportedException("Unsupported keystore algorithm in keystore file");
		} catch (CertificateException e) {
			throw new ReportedException("Bad certificate in keystore file");
		} catch (UnrecoverableKeyException e) {
			throw new ReportedException("Invalid key in keystore file");
		}
	}

	public static String SignatureOfMessage( byte[] msg ) throws ReportedException {

        Signature sig;
        String signature=null;
        final String algo = "SHA256WithRSA";

		try {
			sig = Signature.getInstance(algo);
	        sig.initSign(Storage.INSTANCE.privkey);
	        sig.update(msg);
	        byte[] signatureBytes = sig.sign();
	        signature = Base64.getEncoder().encodeToString(signatureBytes);
		} catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException e) {
			throw new ReportedException("Cannot compute signature");
		}

		return signature;
	}

	public static boolean VerifyMessage( byte[] msg , String signature) {

		Signature sig;
		boolean result=false;

        try {
        	sig = Signature.getInstance("SHA256WithRSA");
			sig.initVerify(Storage.INSTANCE.pubkey);
	        sig.update(msg);
	        byte[] signatureBytes = Base64.getDecoder().decode(signature);
	        result=sig.verify(signatureBytes);
		} catch (InvalidKeyException | NoSuchAlgorithmException | SignatureException e) {
			throw new ReportedException("Cannot verify signature");
		}

		return result;
	}
}
