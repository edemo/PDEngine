package org.rulez.demokracia.pdengine;

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

public final class MessageSigner {

	private PublicKey pubkey;
	private PrivateKey privkey;

	private static class Storage {
		private static final MessageSigner INSTANCE = new MessageSigner();
	}

	private KeyStore keyStore;
    private String keyStorePath;
    private String keyAlias;
    private char[] keyStorePassword;

    public static MessageSigner getInstance() {
        return Storage.INSTANCE;
    }

	private MessageSigner () {
		fetchKeyStorePriperties();
		loadKeyStore();
		fetchKeys();
	}

	private void fetchKeyStorePriperties() {
		Context xmlNode;
		try {
			InitialContext context = new InitialContext();
			xmlNode = (Context) context.lookup("java:comp/env");
			keyStorePath = (String) xmlNode.lookup("keyStorePath");
			keyAlias = (String) xmlNode.lookup("keyAlias");
			keyStorePassword = ((String) xmlNode.lookup("keyStorePassphrase")).toCharArray();
		} catch (NamingException e) {
			throw (ReportedException)new ReportedException("Cannot get keystore properties from app server context").initCause(e);
		}
	}

	private void loadKeyStore() {
		try {
			keyStore = KeyStore.getInstance("PKCS12");
    		ClassLoader classloader = Thread.currentThread().getContextClassLoader();
    		InputStream keyStoreData = classloader.getResourceAsStream(keyStorePath);
			keyStore.load(keyStoreData, keyStorePassword);
    	} catch ( KeyStoreException | IOException | NoSuchAlgorithmException | CertificateException e ) {
    		throw (ReportedException)new ReportedException("Keystore initialization error").initCause(e);
    	}
	}

	private void fetchKeys() {
		try {
			privkey = (PrivateKey) keyStore.getKey(keyAlias, keyStorePassword);
			Certificate cert = keyStore.getCertificate(keyAlias);
			pubkey = cert.getPublicKey();
		} catch (UnrecoverableKeyException | KeyStoreException | NoSuchAlgorithmException e) {
			throw (ReportedException)new ReportedException("Error in extracting keys").initCause(e);
		}
	}

	public String signatureOfMessage( final byte[] msg ) {
        Signature sig;
        String signature=null;

		try {
			sig = Signature.getInstance("SHA256WithRSA");
	        sig.initSign(privkey);
	        sig.update(msg);
	        byte[] signatureBytes = sig.sign();
	        signature = Base64.getEncoder().encodeToString(signatureBytes);
		} catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException e) {
			throw (ReportedException)new ReportedException("Cannot compute signature").initCause(e);
		}
		return signature;
	}

	public PublicKey getPublicKey() {
		return pubkey;
	}
}
