package org.rulez.demokracia.pdengine;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.rulez.demokracia.pdengine.annotations.tested_behaviour;
import org.rulez.demokracia.pdengine.annotations.tested_feature;
import org.rulez.demokracia.pdengine.annotations.tested_operation;
import org.rulez.demokracia.pdengine.dataobjects.VoteAdminInfo;
import org.rulez.demokracia.pdengine.exception.ReportedException;
import org.rulez.demokracia.pdengine.testhelpers.CreatedDefaultVoteRegistry;

public class VoteCreationNameValidationTest extends CreatedDefaultVoteRegistry {

	public VoteCreationNameValidationTest() {
		super();
	}

	@tested_feature("Manage votes")
	@tested_operation("create vote")
	@tested_behaviour("formally validates all inputs")
	@Test
	public void vote_name_can_contain_spaces() throws ReportedException {
		voteName = "This contains spaces";
		VoteAdminInfo voteadm = createAVote();
		assertEquals(voteName, voteManager.getVote(voteadm.voteId).name);
		
	}

	@tested_feature("Manage votes")
	@tested_operation("create vote")
	@tested_behaviour("formally validates all inputs")
	@Test
	public void vote_name_cannot_be_null() throws ReportedException {
		voteName = null;
		assertThrows(
			() -> createAVote()
		).assertMessageIs("vote name is null");
	}

	@tested_feature("Manage votes")
	@tested_operation("create vote")
	@tested_behaviour("formally validates all inputs")
	@Test
	public void vote_name_cannot_contain_tabs() {
		voteName = "thiscontainstab\t";
		assertThrows(
				() -> createAVote()
			).assertMessageIs("invalid characters in vote name");
	}

	@tested_feature("Manage votes")
	@tested_operation("create vote")
	@tested_behaviour("formally validates all inputs")
	@Test
	public void votename_max_length_is_255_characters() throws ReportedException {
		int length = 255;
		String str255 = createLongString(length);
		voteName = str255;
	
		createAVote();
		voteName = str255+"w";
		assertThrows(
				() -> createAVote()
			).assertMessageIs("string too long: vote name");
	}

	@tested_feature("Manage votes")
	@tested_operation("create vote")
	@tested_behaviour("formally validates all inputs")
	@Test
	public void minimum_vote_name_length_is_3() throws ReportedException {
		voteName = "aaa";
		createAVote();
		voteName = "aa";
		assertThrows(
				() -> createAVote()
			).assertMessageIs("string too short: vote name");
	}

	@tested_feature("Manage votes")
	@tested_operation("create vote")
	@tested_behaviour("formally validates all inputs")
	@Test
	public void votename_can_contain_local_characters() throws ReportedException {
		voteName = "ThisConatinsLocaCharséűáőúöüóíÉÁŰŐÚÖÜÓÍ";
	
		VoteAdminInfo secondVote = createAVote();
	
		assertEquals(voteName, voteManager.getVote(secondVote.voteId).name);
	
	}

}