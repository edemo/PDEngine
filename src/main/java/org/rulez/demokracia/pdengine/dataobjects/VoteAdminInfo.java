package org.rulez.demokracia.pdengine.dataobjects;

import lombok.Getter;

@Getter
public class VoteAdminInfo {

  public String adminKey;
  public String voteId;

  public VoteAdminInfo(final String voteId, final String adminKey) {
    this.voteId = voteId;
    this.adminKey = adminKey;
  }

  public VoteAdminInfo() {}

  public boolean isUserAdminKey() {
    return "user".equals(adminKey);
  }
}
