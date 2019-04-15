package org.rulez.demokracia.pdengine.dataobjects;

public class VoteAdminInfo {

  public String adminKey;
  public String voteId;

  public VoteAdminInfo(final String voteId, final String adminKey) {
    this.voteId = voteId;
    this.adminKey = adminKey;
  }

  public VoteAdminInfo() {
  }
}
