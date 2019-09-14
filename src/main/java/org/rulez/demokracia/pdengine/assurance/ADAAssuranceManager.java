package org.rulez.demokracia.pdengine.assurance;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class ADAAssuranceManager implements AssuranceManager {

  @Override
  public List<String> getAssurances(final String proxyId) {
    throw new UnsupportedOperationException();
  }

}
