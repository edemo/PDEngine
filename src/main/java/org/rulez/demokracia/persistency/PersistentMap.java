package org.rulez.demokracia.persistency;

import java.util.Map;

public interface PersistentMap extends Map<String, String> {

  void commit();

}
