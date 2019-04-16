package org.rulez.demokracia.pdengine;

import java.util.HashMap;
import java.util.Map;

import javax.xml.ws.WebServiceContext;

final public class VoteManagerUtils {

  private static Map<WebServiceContext, IVoteManager> registry =
      new HashMap<>();

  public static IVoteManager getVoteManager(
      final WebServiceContext wsContext, final AssuranceManager assuranceManager
  ) {
    if (!registry.containsKey(wsContext))
      registry.put(wsContext, new VoteRegistry(wsContext, assuranceManager));
    return registry.get(wsContext);
  }
}
