package org.rulez.demokracia.pdengine.dataobjects;

public class Pair extends PairEntity {//NOPMD

  private static final long serialVersionUID = 1L;

  public Pair(final int winning, final int losing) {
    super();
    this.winning = winning;
    this.losing = losing;
  }
}
