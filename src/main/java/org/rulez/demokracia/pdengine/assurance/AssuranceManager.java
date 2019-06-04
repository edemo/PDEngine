package org.rulez.demokracia.pdengine.assurance;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public interface AssuranceManager {

  List<String> getAssurances(String proxyId);
}
