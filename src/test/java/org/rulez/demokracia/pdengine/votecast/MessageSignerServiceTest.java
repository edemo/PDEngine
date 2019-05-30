package org.rulez.demokracia.pdengine.votecast;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.Signature;
import java.util.Base64;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.rulez.demokracia.pdengine.KeyProvider;
import org.springframework.test.util.ReflectionTestUtils;

@RunWith(MockitoJUnitRunner.class)
public class MessageSignerServiceTest {

  private static final String SERIAL_PLAN = "SerialPlan";

  @InjectMocks
  private MessageSignerServiceImpl messageSignerService;

  @Mock
  private KeyProvider keyProvider;

  private KeyPair keys;

  @Before
  public void setUp() throws Exception {
    ReflectionTestUtils.setField(messageSignerService, "keyAlias", "alias");
    ReflectionTestUtils.setField(messageSignerService, "keyPassword", "pass");
    keys = KeyPairGenerator.getInstance("RSA").generateKeyPair();
    when(keyProvider.getPrivateKey("alias", "pass")).thenReturn(keys.getPrivate());
  }

  @Test
  public void test_signature_is_valid() throws Exception {
    String signatureOfMessage = messageSignerService.signatureOfMessage(SERIAL_PLAN);
    Signature signature = Signature.getInstance("SHA256WithRSA");
    signature.initVerify(keys.getPublic());
    signature.update(SERIAL_PLAN.getBytes());
    assertTrue(signature.verify(Base64.getDecoder().decode(signatureOfMessage)));
  }

}
