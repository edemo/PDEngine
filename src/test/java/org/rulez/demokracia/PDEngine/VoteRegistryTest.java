package org.rulez.demokracia.PDEngine;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.rulez.demokracia.PDEngine.DataObjects.Vote;
import org.rulez.demokracia.PDEngine.DataObjects.VoteAdminInfo;
import org.rulez.demokracia.PDEngine.annotations.tested_behaviour;
import org.rulez.demokracia.PDEngine.annotations.tested_feature;
import org.rulez.demokracia.PDEngine.annotations.tested_operation;

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
	@tested_feature("Manage votes")
	@tested_operation("create vote")
	@tested_behaviour("Creates a vote")
	public void create_creates_a_vote_with_the_given_name() {
		assertEquals(VoteRegistry.getByKey(adminInfo.adminKey).name, voteName);
	}

	@Test
	@tested_feature("Manage votes")
	@tested_operation("create vote")
	@tested_behaviour("Creates a vote")
	public void a_vote_got_frome_the_registry_two_times_is_the_same() {
		Vote entity1 = VoteRegistry.getByKey(adminInfo.adminKey);
		Vote entity2 = VoteRegistry.getByKey(adminInfo.adminKey);
		assertEquals(entity1, entity2);
	}

}
