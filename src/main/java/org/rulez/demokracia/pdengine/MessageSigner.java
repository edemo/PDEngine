package org.rulez.demokracia.pdengine;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
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

	private PublicKey pubkey;
	private PrivateKey privkey;
	private static final String KEYALIAS="PDEngineKeys";

    private static class Storage {
        private static final MessageSigner INSTANCE = new MessageSigner();
     }

	private MessageSigner () {
		KeyStore keyStore;
		String keyStorePath;
		String keyAlias=null;
		Context xmlNode=null;
		char[] keyStorePassword=null;

		try {
			InitialContext context = new InitialContext();
			xmlNode = (Context) context.lookup("java:comp/env");
			keyStorePath = (String) xmlNode.lookup("keyStorePath");
			keyAlias = (String) xmlNode.lookup("keyAlias");
	    	keyStorePassword = ((String) xmlNode.lookup("keyStorePassphrase")).toCharArray();
		} catch (NamingException e) {
			throw new ReportedException("Cannot get keystore properties from app server context:"+ e.toString());
		}

		try {
			keyStore = KeyStore.getInstance("PKCS12");
		} catch (KeyStoreException e) {
			throw new ReportedException("Cannot handle PKCS12 keystore:"+ e.toString());
		}

    	try (InputStream keyStoreData = Files.newInputStream(Paths.get(keyStorePath))) {
	    	keyStore.load(keyStoreData, keyStorePassword);
			privkey = (PrivateKey) keyStore.getKey(KEYALIAS, keyStorePassword);
	    	Certificate cert = keyStore.getCertificate(KEYALIAS);
	    	pubkey = cert.getPublicKey();
    	} catch ( KeyStoreException | IOException | NoSuchAlgorithmException |
    		      CertificateException | UnrecoverableKeyException e ) {
    		throw new ReportedException("Keystore initialization error: "+ e.toString());
    	}
	}

	public static String signatureOfMessage( final byte[] msg ) {
        Signature sig;
        String signature=null;

		try {
			sig = Signature.getInstance("SHA256WithRSA");
	        sig.initSign(Storage.INSTANCE.privkey);
	        sig.update(msg);
	        byte[] signatureBytes = sig.sign();
	        signature = Base64.getEncoder().encodeToString(signatureBytes);
		} catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException e) {
			throw new ReportedException("Cannot compute signature: "+ e.toString());
		}
		return signature;
	}

	public static boolean verifyMessage( final byte[] msg , final String signature) {

		Signature sig;
		boolean result=false;

        try {
        	sig = Signature.getInstance("SHA256WithRSA");
			sig.initVerify(Storage.INSTANCE.pubkey);
	        sig.update(msg);
	        byte[] signatureBytes = Base64.getDecoder().decode(signature);
	        result=sig.verify(signatureBytes);
		} catch (InvalidKeyException | NoSuchAlgorithmException | SignatureException e) {
			throw new ReportedException("Cannot verify signature:"+ e.toString());
		}

		return result;
	}
}
