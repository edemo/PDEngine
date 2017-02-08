package org.rulez.demokracia.Persistency;

import java.util.Map;

public interface PersistentMap extends Map<String,String> {

	void commit();
	
}
