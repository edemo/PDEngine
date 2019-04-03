package org.rulez.demokracia.pdengine.testhelpers;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.naming.spi.InitialContextFactory;

public  class InitialContextFactoryMock implements InitialContextFactory {

  private final static String KEYSTORE="src/main/resources/keystore.pk12";
  private final static String KEYALIAS="PDEngineKeys";
  private final static String KEYSTOREPASS="changeit";

  @Override
  public Context getInitialContext(final Hashtable<?,?> environment) throws NamingException {
    InitialContext mockCtx = mock(InitialContext.class);
    when(mockCtx.lookup("java:comp/env")).thenReturn(mockCtx);
    when(mockCtx.lookup("keyStorePath")).thenReturn(KEYSTORE);
    when(mockCtx.lookup("keyAlias")).thenReturn(KEYALIAS);
    when(mockCtx.lookup("keyStorePassphrase")).thenReturn(KEYSTOREPASS);
    return mockCtx;
  }
}