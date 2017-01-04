package org.rulez.demokracia.PDEngine;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.rulez.demokracia.PDEngine.DataObjects.VoteAdminInfo;

public class VoteRegistryTest {

	static final String voteName = "Votename";
	private VoteAdminInfo adminInfo;

	@Before
	public void setUp() {
		List<String> neededAssurances = new ArrayList<String>();
		List<String> countedAssurances = new ArrayList<String>();
		boolean isPrivate = true;
		int minEndorsements = 0;
		adminInfo = VoteRegistry.create(voteName,neededAssurances, countedAssurances, isPrivate, minEndorsements);
	}


	@Test
	public void create_creates_a_vote_with_the_given_name() {
		assertEquals(VoteRegistry.getByKey(adminInfo.adminKey).name, voteName);
	}

}
