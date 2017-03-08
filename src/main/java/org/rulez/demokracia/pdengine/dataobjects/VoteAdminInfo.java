package org.rulez.demokracia.pdengine.dataobjects;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class VoteAdminInfo {
	private String adminKey;
	private String voteId;
	public String getAdminKey() {
		return adminKey;
	}
	@XmlElement
	public void setAdminKey(String adminKey) {
		this.adminKey = adminKey;
	}
	public String getVoteId() {
		return voteId;
	}
	@XmlElement
	public void setVoteId(String voteId) {
		this.voteId = voteId;
	}

}

