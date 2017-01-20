package org.rulez.demokracia.PDEngine.DataObjects;

import org.rulez.demokracia.PDEngine.Persistence.BaseEntity;

public class Vote extends BaseEntity {

	private static final long serialVersionUID = 1L;

	public String name;

	public Vote(String voteName) {
		name = voteName;
	}

}
