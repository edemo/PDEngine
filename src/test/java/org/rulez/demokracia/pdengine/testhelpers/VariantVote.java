package org.rulez.demokracia.pdengine.testhelpers;

import java.util.ArrayList;

import org.rulez.demokracia.pdengine.vote.Vote;

public class VariantVote extends Vote {

	private static final long serialVersionUID = 1L;

	public VariantVote() {
		super("variant", new ArrayList<String>(), new ArrayList<String>(), false, 3);
	}

}