package org.rulez.demokracia.pdengine.dataobjects;

import lombok.Data;

@Data
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

