package org.rulez.demokracia.pdengine.votecast;

import java.security.GeneralSecurityException;
import java.security.PrivateKey;
import java.security.Signature;
import java.util.Base64;

import org.rulez.demokracia.pdengine.KeyProvider;
import org.rulez.demokracia.pdengine.exception.ReportedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public final class MessageSignerServiceImpl implements MessageSignerService {

  @Autowired
  private KeyProvider keyProvider;

  @Value("${server.ssl.key-alias}")
  private String keyAlias;
  @Value("${server.ssl.key-password}")
  private String keyPassword;

  @Override
  public String signatureOfMessage(final String message) {

    try {
      Signature signature = Signature.getInstance("SHA256WithRSA");
      PrivateKey privateKey = keyProvider.getPrivateKey(keyAlias, keyPassword);
      signature.initSign(privateKey);
      signature.update(message.getBytes());
      return Base64.getEncoder().encodeToString(signature.sign());
    } catch (
      GeneralSecurityException e
    ) {
      throw (ReportedException) new ReportedException(
          "Cannot compute signature"
      ).initCause(e);
    }
  }
}
