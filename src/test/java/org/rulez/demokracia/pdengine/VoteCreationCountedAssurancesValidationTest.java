package org.rulez.demokracia.pdengine;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.rulez.demokracia.pdengine.annotations.tested_behaviour;
import org.rulez.demokracia.pdengine.annotations.tested_feature;
import org.rulez.demokracia.pdengine.annotations.tested_operation;
import org.rulez.demokracia.pdengine.dataobjects.VoteAdminInfo;
import org.rulez.demokracia.pdengine.exception.ReportedException;
import org.rulez.demokracia.pdengine.testhelpers.CreatedDefaultVoteRegistry;

public class VoteCreationCountedAssurancesValidationTest extends CreatedDefaultVoteRegistry {

	public VoteCreationCountedAssurancesValidationTest() {
		super();
	}

	@tested_feature("Manage votes")
	@tested_operation("create vote")
	@tested_behaviour("formally validates all inputs")
	@Test
	public void countedAssurances_is_checked_not_to_contain_strings_longer_than_255() throws ReportedException {
	    int length = 255;
	    String str255 = createLongString(length);
	
	    countedAssurances.add(str255);
	
	    createAVote();
	    String str256 = str255 + "w";
	    countedAssurances.add(str256);
		assertThrows(
				() -> createAVote()
			).assertMessageIs("string too long: counted assurance name");
	}

	@tested_feature("Manage votes")
	@tested_operation("create vote")
	@tested_behaviour("formally validates all inputs")
	@Test
	public void countedAssurances_is_checked_not_to_contain_strings_shorter_than_3() throws ReportedException {
	    countedAssurances.add("aaa");
	
	    createAVote();
	    countedAssurances.add("aa");
		assertThrows(
				() -> createAVote()
			).assertMessageIs("string too short: counted assurance name");
	}

	@tested_feature("Manage votes")
	@tested_operation("create vote")
	@tested_behaviour("formally validates all inputs")
	@Test
	public void counted_assurances_should_not_contain_space() {
	    countedAssurances.add("This contains space");
	
		assertThrows(
				() -> createAVote()
			).assertMessageIs("invalid characters in counted assurance name");
	}

	@tested_feature("Manage votes")
	@tested_operation("create vote")
	@tested_behaviour("formally validates all inputs")
	@Test
	public void counted_assurances_should_not_contain_tab() {
	    countedAssurances.add("thiscontainstab\t");
		assertThrows(
				() -> createAVote()
			).assertMessageIs("invalid characters in counted assurance name");
	}

	@tested_feature("Manage votes")
	@tested_operation("create vote")
	@tested_behaviour("formally validates all inputs")
	@Test
	public void counted_assurances_can_contain_local_characters() throws ReportedException {
	    countedAssurances.add("ThisConatinsLocaCharséűáőúöüóíÉÁŰŐÚÖÜÓÍ");
	    
	    createAVote();
	    assertEquals(voteName, voteManager.getVote(adminInfo.voteId).name);
	
	}

	@tested_feature("Manage votes")
	@tested_operation("create vote")
	@tested_behaviour("formally validates all inputs")
	@Test
	public void counted_assurance_can_be_empty() throws ReportedException {
	    countedAssurances.add("");
	    assertEquals(1,countedAssurances.size());
		VoteAdminInfo secondVote = createAVote();
		assertTrue(voteManager.getVote(secondVote.voteId).countedAssurances.contains(null));
	}

}