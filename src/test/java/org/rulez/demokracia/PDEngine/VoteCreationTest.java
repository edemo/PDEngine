package org.rulez.demokracia.PDEngine;

import static org.junit.Assert.*;

import java.time.Instant;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.rulez.demokracia.PDEngine.DataObjects.VoteAdminInfo;
import org.rulez.demokracia.PDEngine.annotations.tested_behaviour;
import org.rulez.demokracia.PDEngine.annotations.tested_feature;
import org.rulez.demokracia.PDEngine.annotations.tested_operation;
import org.rulez.demokracia.PDEngine.exception.ReportedException;
import org.rulez.demokracia.PDEngine.testhelpers.CreatedDefaultVoteRegistry;

@tested_feature("Manage votes")
@tested_operation("create vote")
@tested_behaviour("Creates a vote")
public class VoteCreationTest extends CreatedDefaultVoteRegistry{

	@Before
	public void setUp() throws ReportedException {
		super.setUp();
	}

	private String createLongString(int length) {
		char[] charArray = new char[length];
        Arrays.fill(charArray, 'w');
        String str256 = new String(charArray);
		return str256;
	}

	@Test
	public void create_creates_a_vote_with_the_given_name() {
		assertEquals(voteManager.getVote(adminInfo.voteId).name, voteName);
	}

	@Test
	public void create_creates_a_vote_with_neededAssurances() {
		assertEquals(voteManager.getVote(adminInfo.voteId).neededAssurances.size(), neededAssurances.size());
	}
	
	@Test
	public void neededAssurances_contains_the_assurances_of_the_input() throws ReportedException {
		VoteAdminInfo secondVote = createAVote();
		assertEquals(voteManager.getVote(secondVote.voteId).neededAssurances.get(0), "magyar");
	}

	@Test
	public void create_creates_a_vote_with_countedAssurances() {
		assertEquals(voteManager.getVote(adminInfo.voteId).countedAssurances.size(), 0);
	}
	
	@Test
	public void countedAssurances_contains_the_assurances_of_the_input() throws ReportedException {
		countedAssurances.add("magyar");
		VoteAdminInfo secondVote = createAVote();
		assertEquals(voteManager.getVote(secondVote.voteId).countedAssurances.get(0), "magyar");
	}
	
	@Test
	public void create_creates_a_vote_with_isPrivate() {
		assertEquals(voteManager.getVote(adminInfo.voteId).isPrivate, true);
	}

	@Test
	public void isPrivate_is_the_same_what_is_given_in_create() throws ReportedException {
		isPrivate = false;
		VoteAdminInfo secondVote = createAVote();
		assertEquals(voteManager.getVote(secondVote.voteId).isPrivate, false);
	}

	@Test
	public void create_creates_a_vote_with_voteId() {
		assertNotNull(adminInfo.voteId);
		assertEquals(voteManager.getVote(adminInfo.voteId).id, adminInfo.voteId);
	}

	@Test
	public void create_creates_a_vote_with_creationTime() throws ReportedException {
		Instant before = Instant.now();
		VoteAdminInfo secondVote = createAVote();
		Instant after = Instant.now();
		long creationTime = voteManager.getVote(secondVote.voteId).creationTime;
		assertTrue(creationTime >= before.getEpochSecond());
		assertTrue(creationTime <= after.getEpochSecond());
	}

	@Test
	public void create_creates_a_vote_with_minEndorsements() {
		assertEquals(voteManager.getVote(adminInfo.voteId).minEndorsements, 0);
	}

	@Test
	public void minEndorsements_is_the_same_what_is_given_in_create() throws ReportedException {
		minEndorsements = 42;
		VoteAdminInfo secondVote = createAVote();
		assertEquals(voteManager.getVote(secondVote.voteId).minEndorsements, 42);
	}

	@Test
	public void create_creates_a_vote_with_adminKey() {
		assertNotNull(adminInfo.voteId);
		assertEquals(voteManager.getVote(adminInfo.voteId).adminKey, adminInfo.adminKey);
	}

	@Test
	public void vote_name_cannot_contain_scpaces() throws ReportedException {
		voteName = "This contains spaces";
		assertThrows(
			() -> createAVote()
		).assertMessageIs("invalid characters in vote name");
	}

	@Test
	public void vote_name_cannot_contain_tabs() {
		voteName = "thiscontainstab\t";
		assertThrows(
				() -> createAVote()
			).assertMessageIs("invalid characters in vote name");
	}

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

	@Test
	public void minimum_vote_name_length_is_3() throws ReportedException {
		voteName = "aaa";
		createAVote();
		voteName = "aa";
		assertThrows(
				() -> createAVote()
			).assertMessageIs("string too short: vote name");
	}

	@Test
	public void votename_can_contain_local_characters() throws ReportedException {
		voteName = "ThisConatinsLocaCharséűáőúöüóíÉÁŰŐÚÖÜÓÍ";

		VoteAdminInfo secondVote = createAVote();

		assertEquals(voteManager.getVote(secondVote.voteId).name, voteName);

	}
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

    @Test
    public void neededAssurances_is_checked_not_to_contain_strings_shorter_than_3() throws ReportedException {
        neededAssurances.add("aaa");

        createAVote();
        neededAssurances.add("aa");
		assertThrows(
				() -> createAVote()
			).assertMessageIs("string too short: needed assurance name");
    }

    @Test
    public void needed_assurances_should_not_contain_space() {
        neededAssurances.add("This contains space");

		assertThrows(
				() -> createAVote()
			).assertMessageIs("invalid characters in needed assurance name");
    }

    @Test
    public void needed_assurances_should_not_contain_tab() {
        neededAssurances.add("thiscontainstab\t");
		assertThrows(
				() -> createAVote()
			).assertMessageIs("invalid characters in needed assurance name");
    }

    @Test
    public void needed_assurances_can_contain_local_characters() throws ReportedException {
        neededAssurances.add("ThisConatinsLocaCharséűáőúöüóíÉÁŰŐÚÖÜÓÍ");
        
        createAVote();
        assertEquals(voteManager.getVote(adminInfo.voteId).name, voteName);

    }

	@Test
	public void needed_assurances_cannot_be_duplicates() {
		neededAssurances.add("thisIsSame");
		neededAssurances.add("thisIsSame");

		assertThrows(
				() -> createAVote()
			).assertMessageIs("duplicate needed assurances");
	}

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

    @Test
    public void countedAssurances_is_checked_not_to_contain_strings_shorter_than_3() throws ReportedException {
        countedAssurances.add("aaa");

        createAVote();
        countedAssurances.add("aa");
		assertThrows(
				() -> createAVote()
			).assertMessageIs("string too short: counted assurance name");
    }

    @Test
    public void counted_assurances_should_not_contain_space() {
        countedAssurances.add("This contains space");

		assertThrows(
				() -> createAVote()
			).assertMessageIs("invalid characters in counted assurance name");
    }

    @Test
    public void counted_assurances_should_not_contain_tab() {
        countedAssurances.add("thiscontainstab\t");
		assertThrows(
				() -> createAVote()
			).assertMessageIs("invalid characters in counted assurance name");
    }

    @Test
    public void counted_assurances_can_contain_local_characters() throws ReportedException {
        countedAssurances.add("ThisConatinsLocaCharséűáőúöüóíÉÁŰŐÚÖÜÓÍ");
        
        createAVote();
        assertEquals(voteManager.getVote(adminInfo.voteId).name, voteName);

    }

	@Test
	public void counted_assurances_cannot_be_duplicates() {
		countedAssurances.add("thisIsSame");
		countedAssurances.add("thisIsSame");

		assertThrows(
				() -> createAVote()
			).assertMessageIs("duplicate counted assurances");
	}
}
