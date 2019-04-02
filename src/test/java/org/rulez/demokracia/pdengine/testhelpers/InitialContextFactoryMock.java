package org.rulez.demokracia.pdengine.testhelpers;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.naming.spi.InitialContextFactory;

public  class InitialContextFactoryMock implements InitialContextFactory {

  final String KEYSTORE="/home/chris/keystore/keystore.pk12";
  final String KEYALIAS="PDEngineAlias";
  final String KEYSTOREPASS="changeit";

  @Override
  public Context getInitialContext(Hashtable<?,?> environment) throws NamingException {
    InitialContext mockCtx = mock(InitialContext.class);
    when(mockCtx.lookup("java:comp/env")).thenReturn(mockCtx);
    when(mockCtx.lookup("keyStorePath")).thenReturn(KEYSTORE);
    when(mockCtx.lookup("keyAlias")).thenReturn(KEYALIAS);
    when(mockCtx.lookup("keyStorePassphrase")).thenReturn(KEYSTOREPASS);
    return mockCtx;
  }
}