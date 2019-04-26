package org.rulez.demokracia.pdengine;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.UnrecoverableKeyException;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Component
public class KeyProvider {

  @Value("${server.ssl.key-store}")
  private Resource keyStorePath;

  private KeyStore keyStore;

  @Value("${server.ssl.key-store-password}")
  private char[] keystorePass;

  @PostConstruct
  public void initKeyStore() throws GeneralSecurityException, IOException {
    keyStore = KeyStore.getInstance("jks");
    keyStore.load(keyStorePath.getInputStream(), keystorePass);

  }

  public PrivateKey
      getPrivateKey(final String alias, final String password) throws UnrecoverableKeyException, KeyStoreException, NoSuchAlgorithmException {
    return (PrivateKey) keyStore.getKey(alias, password.toCharArray());
  }

  public PublicKey getPublicKey(final String alias) throws KeyStoreException {
    return keyStore.getCertificate(alias).getPublicKey();
  }

}
