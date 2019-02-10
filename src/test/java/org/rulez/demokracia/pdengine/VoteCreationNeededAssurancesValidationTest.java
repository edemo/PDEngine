package org.rulez.demokracia.pdengine;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.rulez.demokracia.pdengine.annotations.tested_behaviour;
import org.rulez.demokracia.pdengine.annotations.tested_feature;
import org.rulez.demokracia.pdengine.annotations.tested_operation;
import org.rulez.demokracia.pdengine.exception.ReportedException;
import org.rulez.demokracia.pdengine.testhelpers.CreatedDefaultVoteRegistry;

public class VoteCreationNeededAssurancesValidationTest extends CreatedDefaultVoteRegistry {

	public VoteCreationNeededAssurancesValidationTest() {
		super();
	}

	@tested_feature("Manage votes")
	@tested_operation("create vote")
	@tested_behaviour("formally validates all inputs")
	@Test
	public void neededAssurances_is_checked_not_to_contain_strings_longer_than_255() throws ReportedException {
	    int length = 255;
	    String str255 = createLongString(length);
	
	    neededAssurances.add(str255);
	
	    createAVote();
	    String str256 = str255 + "w";
	    neededAssurances.add(str256);
		assertThrows(
				() -> createAVote()
			).assertMessageIs("string too long: needed assurance name");
	}

	@tested_feature("Manage votes")
	@tested_operation("create vote")
	@tested_behaviour("formally validates all inputs")
	@Test
	public void neededAssurances_is_checked_not_to_contain_strings_shorter_than_3() throws ReportedException {
	    neededAssurances.add("aaa");
	
	    createAVote();
	    neededAssurances.add("aa");
		assertThrows(
				() -> createAVote()
			).assertMessageIs("string too short: needed assurance name");
	}

	@tested_feature("Manage votes")
	@tested_operation("create vote")
	@tested_behaviour("formally validates all inputs")
	@Test
	public void needed_assurances_should_not_contain_space() {
	    neededAssurances.add("This contains space");
	
		assertThrows(
				() -> createAVote()
			).assertMessageIs("invalid characters in needed assurance name");
	}

	@tested_feature("Manage votes")
	@tested_operation("create vote")
	@tested_behaviour("formally validates all inputs")
	@Test
	public void needed_assurances_should_not_contain_tab() {
	    neededAssurances.add("thiscontainstab\t");
		assertThrows(
				() -> createAVote()
			).assertMessageIs("invalid characters in needed assurance name");
	}

	@tested_feature("Manage votes")
	@tested_operation("create vote")
	@tested_behaviour("formally validates all inputs")
	@Test
	public void needed_assurances_should_not_contain_empty_string() {
	    neededAssurances.add("");
		assertThrows(
				() -> createAVote()
			).assertMessageIs("string too short: needed assurance name");
	}

	@tested_feature("Manage votes")
	@tested_operation("create vote")
	@tested_behaviour("formally validates all inputs")
	@Test
	public void needed_assurances_should_not_be_null() {
	    neededAssurances.add(null);
		assertThrows(
				() -> createAVote()
			).assertMessageIs("needed assurance name is null");
	}

	@tested_feature("Manage votes")
	@tested_operation("create vote")
	@tested_behaviour("formally validates all inputs")
	@Test
	public void needed_assurances_can_contain_local_characters() throws ReportedException {
	    neededAssurances.add("ThisConatinsLocaCharséűáőúöüóíÉÁŰŐÚÖÜÓÍ");
	    
	    createAVote();
	    assertEquals(voteName, voteManager.getVote(adminInfo.voteId).name);
	
	}

}