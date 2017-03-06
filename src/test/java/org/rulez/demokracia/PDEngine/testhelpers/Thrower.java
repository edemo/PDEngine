package org.rulez.demokracia.PDEngine.testhelpers;

@FunctionalInterface
public interface Thrower {
    void throwException() throws Throwable;
}
