package org.rulez.demokracia.pdengine;

import java.util.HashMap;
import java.util.Map;

import javax.xml.ws.WebServiceContext;

public class VoteManagerRegistry {
	
	private static Map<WebServiceContext,IVoteManager> registry = new HashMap<>();

	private VoteManagerRegistry() {
	}

	public static IVoteManager getVoteManager(WebServiceContext wsContext) {
		if(!registry.containsKey(wsContext)) {
			registry.put(wsContext, new VoteRegistry(wsContext));
		}
		return registry.get(wsContext);
	}
}
