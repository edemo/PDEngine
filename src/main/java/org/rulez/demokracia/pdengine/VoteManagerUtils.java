package org.rulez.demokracia.pdengine;

import java.util.HashMap;
import java.util.Map;

import javax.xml.ws.WebServiceContext;

final public class VoteManagerUtils {
	
	private static Map<WebServiceContext,IVoteManager> registry = new HashMap<>();

	private VoteManagerUtils() {
	}

	public static IVoteManager getVoteManager(final WebServiceContext wsContext) {
		if(!registry.containsKey(wsContext)) {
			registry.put(wsContext, new VoteRegistry(wsContext));
		}
		return registry.get(wsContext);
	}
}
