package org.rulez.demokracia.persistency;

@FunctionalInterface
public interface PersistenceManager {

	MapRegistry getRegistryFor(String registryIdentifier);

}
