package org.rulez.demokracia.pdengine.vote;

public enum AssuranceType {
	NEEDED("needed"), COUNTED("counted");

	public final String name;

	private AssuranceType(final String name) {
		this.name = name;
	}
}
