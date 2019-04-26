package org.rulez.demokracia.pdengine;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;

import org.junit.Before;
import org.junit.Test;

public class KeyProviderTest {

  private static final String ALJAS = "aljas";
  private static final String PASSWORD123 = "password123";
  private final KeyProvider keyProvider = new KeyProvider();
  private KeyPair keyPair;

  @Before
  public void setUp() throws KeyStoreException, NoSuchAlgorithmException,
      CertificateException, IOException {
    KeyStore keyStore = KeyStore.getInstance("jks");
    keyStore.load(null);
    keyPair = KeyPairGenerator.getInstance("RSA").generateKeyPair();
    Certificate certificate = mock(Certificate.class);
    Certificate[] chain = {
        certificate
    };
    when(certificate.getPublicKey()).thenReturn(keyPair.getPublic());
    keyStore.setKeyEntry(
        ALJAS, keyPair.getPrivate(), PASSWORD123.toCharArray(),
        chain
    );
    keyProvider.setKeyStore(keyStore);
  }

  @Test
  public void test_get_private_key() throws Exception {
    PrivateKey privateKey = keyProvider.getPrivateKey(ALJAS, PASSWORD123);
    assertEquals(keyPair.getPrivate(), privateKey);
  }

  @Test
  public void test_get_public_key() throws Exception {
    PublicKey publicKey = keyProvider.getPublicKey(ALJAS);
    assertEquals(keyPair.getPublic(), publicKey);
  }

}
