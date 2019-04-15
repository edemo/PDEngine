package org.rulez.demokracia.persistency;

@FunctionalInterface
public interface MapRegistry {

  PersistentMap getMapFor(String mapIdentifier);

}
