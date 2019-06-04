package org.rulez.demokracia.pdengine;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.Certificate;
import org.junit.Before;
import org.junit.Test;
import org.springframework.core.io.Resource;
import org.springframework.data.util.ReflectionUtils;

public class KeyProviderTest {

  private static final String ALJAS = "aljas";
  private static final String PASSWORD123 = "password123";
  private final KeyProvider keyProvider = new KeyProvider();
  private KeyPair keyPair;

  @Before
  public void setUp() throws IOException, NoSuchFieldException, GeneralSecurityException {
    Resource resource = mock(Resource.class);
    when(resource.getInputStream()).thenReturn(null);
    ReflectionUtils.setField(ReflectionUtils.findRequiredField(KeyProvider.class, "keyStorePath"),
        keyProvider, resource);
    ReflectionUtils.setField(ReflectionUtils.findRequiredField(KeyProvider.class, "keyStorePass"),
        keyProvider, PASSWORD123);
    keyProvider.initKeyStore();
    KeyStore keyStore = keyProvider.getKeyStore();
    keyPair = KeyPairGenerator.getInstance("RSA").generateKeyPair();
    Certificate certificate = mock(Certificate.class);
    Certificate[] chain = {certificate};
    when(certificate.getPublicKey()).thenReturn(keyPair.getPublic());
    keyStore.setKeyEntry(ALJAS, keyPair.getPrivate(), PASSWORD123.toCharArray(), chain);
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
