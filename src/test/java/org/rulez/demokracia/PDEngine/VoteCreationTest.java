package org.rulez.demokracia.PDEngine;

import static org.junit.Assert.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.rulez.demokracia.PDEngine.DataObjects.VoteAdminInfo;
import org.rulez.demokracia.PDEngine.annotations.tested_behaviour;
import org.rulez.demokracia.PDEngine.annotations.tested_feature;
import org.rulez.demokracia.PDEngine.annotations.tested_operation;

@tested_feature("Manage votes")
@tested_operation("create vote")
@tested_behaviour("Creates a vote")
public class VoteCreationTest extends CreatedDefaultVoteRegistry{

	@Test
	public void create_creates_a_vote_with_the_given_name() {
		assertEquals(VoteRegistry.getByKey(adminInfo.adminKey).name, voteName);
	}

	@Test
	public void create_creates_a_vote_with_neededAssurances() {
		assertEquals(VoteRegistry.getByKey(adminInfo.adminKey).neededAssurances.size(), 0);
	}
	
	@Test
	public void neededAssurances_contains_the_assurances_of_the_input() throws ReportedException {
		List<String> neededAssurances = new ArrayList<>();
		List<String> countedAssurances= new ArrayList<String>();
		boolean isPrivate = true;
		int minEndorsements = 0;
		neededAssurances.add("magyar");
		VoteAdminInfo secondVote = VoteRegistry.create("test2",neededAssurances, countedAssurances, isPrivate, minEndorsements );
		assertEquals(VoteRegistry.getByKey(secondVote.adminKey).neededAssurances.get(0), "magyar");
	}

	@Test
	public void create_creates_a_vote_with_countedAssurances() {
		assertEquals(VoteRegistry.getByKey(adminInfo.adminKey).countedAssurances.size(), 0);
	}
	
	@Test
	public void countedAssurances_contains_the_assurances_of_the_input() throws ReportedException {
		List<String> neededAssurances = new ArrayList<String>();
		List<String> countedAssurances= new ArrayList<String>();
		boolean isPrivate = true;
		int minEndorsements = 0;
		countedAssurances.add("magyar");
		VoteAdminInfo secondVote = VoteRegistry.create("test2",neededAssurances, countedAssurances, isPrivate, minEndorsements );
		assertEquals(VoteRegistry.getByKey(secondVote.adminKey).countedAssurances.get(0), "magyar");
	}
	
	@Test
	public void create_creates_a_vote_with_isPrivate() {
		assertEquals(VoteRegistry.getByKey(adminInfo.adminKey).isPrivate, true);
	}

	@Test
	public void isPrivate_is_the_same_what_is_given_in_create() throws ReportedException {
		List<String> neededAssurances = new ArrayList<String>();
		List<String> countedAssurances= new ArrayList<String>();
		boolean isPrivate = false;
		int minEndorsements = 0;
		VoteAdminInfo secondVote = VoteRegistry.create("test2",neededAssurances, countedAssurances, isPrivate, minEndorsements );
		assertEquals(VoteRegistry.getByKey(secondVote.adminKey).isPrivate, false);
	}

	@Test
	public void create_creates_a_vote_with_voteId() {
		assertNotNull(adminInfo.voteId);
		assertEquals(VoteRegistry.getByKey(adminInfo.adminKey).voteId, adminInfo.voteId);
	}

	@Test
	public void create_creates_a_vote_with_creationTime() throws ReportedException {
		List<String> neededAssurances = new ArrayList<String>();
		List<String> countedAssurances= new ArrayList<String>();
		boolean isPrivate = false;
		int minEndorsements = 0;
		Instant before = Instant.now();
		VoteAdminInfo secondVote = VoteRegistry.create("test2",neededAssurances, countedAssurances, isPrivate, minEndorsements );
		Instant after = Instant.now();
		long creationTime = VoteRegistry.getByKey(secondVote.adminKey).creationTime;
		assertTrue(creationTime >= before.getEpochSecond());
		assertTrue(creationTime <= after.getEpochSecond());
	}

	@Test
	public void create_creates_a_vote_with_minEndorsements() {
		assertEquals(VoteRegistry.getByKey(adminInfo.adminKey).minEndorsements, 0);
	}

	@Test
	public void minEndorsements_is_the_same_what_is_given_in_create() throws ReportedException {
		List<String> neededAssurances = new ArrayList<String>();
		List<String> countedAssurances= new ArrayList<String>();
		boolean isPrivate = false;
		int minEndorsements = 42;
		VoteAdminInfo secondVote = VoteRegistry.create("test2",neededAssurances, countedAssurances, isPrivate, minEndorsements );
		assertEquals(VoteRegistry.getByKey(secondVote.adminKey).minEndorsements, 42);
	}

	@Test
	public void create_creates_a_vote_with_adminKey() {
		assertNotNull(adminInfo.adminKey);
		assertEquals(VoteRegistry.getByKey(adminInfo.adminKey).adminKey, adminInfo.adminKey);
	}

// voteName validation tests

	@Test
	public void votename_contains_wrong_characters_input1() throws ReportedException {
		List<String> neededAssurances = new ArrayList<>();
		List<String> countedAssurances= new ArrayList<>();
	boolean isPrivate = true;
		int minEndorsements = 0;
		neededAssurances.add("magyar");
		String wrongVotename = "This contains spaces";

		try {
			VoteAdminInfo secondVote = VoteRegistry.create(wrongVotename,neededAssurances, countedAssurances, isPrivate, minEndorsements );
			fail();
		} catch (ReportedException e) {
			// expected
			assertEquals(e.getMessage(),"Wrong characters in the vote name!");
		}

	}

	@Test
	public void votename_contains_wrong_characters_input2() {
		List<String> neededAssurances = new ArrayList<>();
		List<String> countedAssurances= new ArrayList<String>();
		boolean isPrivate = true;
		int minEndorsements = 0;
		neededAssurances.add("magyar");
		String wrongVotename = "thiscontainstab\t";

		try {
			VoteAdminInfo secondVote = VoteRegistry.create(wrongVotename,neededAssurances, countedAssurances, isPrivate, minEndorsements );
			fail();
		} catch (ReportedException e) {
			// expected
			assertEquals(e.getMessage(),"Wrong characters in the vote name!");
		}

	}

	@Test
	public void votename_to_long_sring_input() {
		List<String> neededAssurances = new ArrayList<>();
		List<String> countedAssurances= new ArrayList<>();
		boolean isPrivate = true;
		int minEndorsements = 0;
		int length = 256;
		char[] charArray = new char[length];
		Arrays.fill(charArray, 'w');
		String str256 = new String(charArray);
		neededAssurances.add("magyar");
 		String wrongVotename = str256;

		try {
			VoteAdminInfo secondVote = VoteRegistry.create(wrongVotename,neededAssurances, countedAssurances, isPrivate, minEndorsements );
			fail();
		} catch (ReportedException e) {
			// expected
			assertEquals(e.getMessage(),"Vote name is too long!");
		}

	}

	@Test
	public void votename_to_short_sring_input() {
		List<String> neededAssurances = new ArrayList<String>();
		List<String> countedAssurances= new ArrayList<String>();
		boolean isPrivate = true;
		int minEndorsements = 0;
		neededAssurances.add("magyar");
		String wrongVotename = "a";

		try {
			VoteAdminInfo secondVote = VoteRegistry.create(wrongVotename,neededAssurances, countedAssurances, isPrivate, minEndorsements );
			fail();
		} catch (ReportedException e) {
			// expected
			assertEquals(e.getMessage(),"Vote name is too short!");
		}

	}

	@Test
	public void votename_local_character_input() throws ReportedException {
		List<String> neededAssurances = new ArrayList<String>();
		List<String> countedAssurances= new ArrayList<String>();
		boolean isPrivate = true;
		int minEndorsements = 0;
		neededAssurances.add("magyar");
		String localVotename = "ThisConatinsLocaCharséűáőúöüóíÉÁŰŐÚÖÜÓÍ";

		VoteAdminInfo secondVote = VoteRegistry.create(localVotename,neededAssurances, countedAssurances, isPrivate, minEndorsements );

		assertEquals(VoteRegistry.getByKey(secondVote.adminKey).name, localVotename);

	}

//neededAssurrance validation tests

	@Test
	public void neededAssurances_to_long_sring_input() {
        List<String> neededAssurances = new ArrayList<String>();
        List<String> countedAssurances = new ArrayList<String>();

        boolean isPrivate = true;
        int minEndorsements = 0;
        int length = 256;

        char[] charArray = new char[length];
        Arrays.fill(charArray, 'w');
        String str256 = new String(charArray);

        neededAssurances.add(str256);
        String Votename = "magyar";

		try {
			VoteAdminInfo secondVote = VoteRegistry.create(Votename,neededAssurances, countedAssurances, isPrivate, minEndorsements );
			fail();
		} catch (ReportedException e) {
			// expected
			assertEquals(e.getMessage(),"neededAssurance is too long!");
		}

	}

    @Test
    public void neededAssurances_to_short_sring_input() {
        List<String> neededAssurances = new ArrayList<String>();
        List<String> countedAssurances= new ArrayList<String>();
        boolean isPrivate = true;
        int minEndorsements = 0;
        neededAssurances.add("a1");
        String Votename = "magyar";

        try {
            VoteAdminInfo secondVote = VoteRegistry.create(Votename,neededAssurances, countedAssurances, isPrivate, minEndorsements );
            fail();
        } catch (ReportedException e) {
            // expected
            assertEquals(e.getMessage(),"neededAssurance is too short!");
        }

    }

    @Test
    public void neededAssurances_contains_wrong_characters_input1() {
        List<String> neededAssurances = new ArrayList<>();
        List<String> countedAssurances= new ArrayList<>();
        boolean isPrivate = true;
        int minEndorsements = 0;
        neededAssurances.add("This contains space");
        String voteName = "magyar";

        try {
            VoteAdminInfo secondVote = VoteRegistry.create(voteName,neededAssurances, countedAssurances, isPrivate, minEndorsements );
            fail();
        } catch (ReportedException e) {
            // expected
            assertEquals(e.getMessage(),"Wrong characters in the neededAssurance!");
        }

    }

    @Test
    public void neededAssurances_contains_wrong_characters_input2() {
        List<String> neededAssurances = new ArrayList<>();
        List<String> countedAssurances = new ArrayList<>();
        boolean isPrivate = true;
        int minEndorsements = 0;
        neededAssurances.add("thiscontainstab\t");
        String vooteName = "magyar";

        try {
            VoteAdminInfo secondVote = VoteRegistry.create(vooteName,neededAssurances, countedAssurances, isPrivate, minEndorsements );
            fail();
        } catch (ReportedException e) {
            // expected
            assertEquals(e.getMessage(),"Wrong characters in the neededAssurance!");
        }

    }

    @Test
    public void neededAssurance_local_character_input() throws ReportedException {
        List<String> neededAssurances = new ArrayList<String>();
        List<String> countedAssurances= new ArrayList<String>();
        boolean isPrivate = true;
        int minEndorsements = 0;
        neededAssurances.add("ThisConatinsLocaCharséűáőúöüóíÉÁŰŐÚÖÜÓÍ");
        String localVotename = "magyar";

        VoteAdminInfo secondVote = VoteRegistry.create(localVotename,neededAssurances, countedAssurances, isPrivate, minEndorsements );

        assertEquals(VoteRegistry.getByKey(secondVote.adminKey).name, localVotename);

    }


    //countedAssurances validation tests
	@Test
	public void countedAssurances_to_long_sring_input() {
		List<String> neededAssurances = new ArrayList<String>();
		List<String> countedAssurances = new ArrayList<String>();
		boolean isPrivate = true;
		int minEndorsements = 0;
        int length = 256;
        char[] charArray = new char[length];
        Arrays.fill(charArray, 'w');
        String str256 = new String(charArray);
		countedAssurances.add(str256);
		String Votename = "magyar";

	try {
		VoteAdminInfo secondVote = VoteRegistry.create(Votename,neededAssurances, countedAssurances, isPrivate, minEndorsements );
		fail();
	} catch (ReportedException e) {
		// expected
		assertEquals(e.getMessage(),"countedAssurance is too long!");
	}

}

	@Test
	public void countedAssurance_to_short_sring_input() {
		List<String> neededAssurances = new ArrayList<String>();
		List<String> countedAssurances= new ArrayList<String>();
		boolean isPrivate = true;
		int minEndorsements = 0;
		countedAssurances.add("a1");
		String Votename = "magyar";

		try {
			VoteAdminInfo secondVote = VoteRegistry.create(Votename,neededAssurances, countedAssurances, isPrivate, minEndorsements );
			fail();
		} catch (ReportedException e) {
			// expected
			assertEquals(e.getMessage(),"countedAssurance is too short!");
		}
	}
		@Test
		public void countedAssurances_duplicated_input() {
			List<String> neededAssurances = new ArrayList<String>();
			List<String> countedAssurances= new ArrayList<String>();
			boolean isPrivate = true;
			int minEndorsements = 0;
			countedAssurances.add("thisIsSame");
			countedAssurances.add("thisIsSame");
			String Votename = "magyar";

			try {
				VoteAdminInfo secondVote = VoteRegistry.create(Votename,neededAssurances, countedAssurances, isPrivate, minEndorsements );
				fail();
			} catch (ReportedException e) {
				// expected
				assertEquals(e.getMessage(),"countedAssurance contains duplicated values!");
			}


		}

		@Test
		public void neededAssurances_duplicated_input() {
			List<String> neededAssurances = new ArrayList<String>();
			List<String> countedAssurances= new ArrayList<String>();
			boolean isPrivate = true;
			int minEndorsements = 0;
			neededAssurances.add("thisIsSame");
			neededAssurances.add("thisIsSame");
			String Votename = "magyar";

			try {
				VoteAdminInfo secondVote = VoteRegistry.create(Votename,neededAssurances, countedAssurances, isPrivate, minEndorsements );
				fail();
			} catch (ReportedException e) {
				// expected
				assertEquals(e.getMessage(),"neededAssurance contains duplicated values!");
			}

	}

	@Test
	public void countedAssurance_contains_wrong_characters_input1() {
		List<String> neededAssurances = new ArrayList<String>();
		List<String> countedAssurances= new ArrayList<String>();
		boolean isPrivate = true;
		int minEndorsements = 0;
		countedAssurances.add("This contains space");
		String vooteName = "magyar";

		try {
			VoteAdminInfo secondVote = VoteRegistry.create(vooteName,neededAssurances, countedAssurances, isPrivate, minEndorsements );
			fail();
		} catch (ReportedException e) {
			// expected
			assertEquals(e.getMessage(),"Wrong characters in the countedAssurance!");
		}

	}

	@Test
	public void countedAssurance_contains_wrong_characters_input2() {
		List<String> neededAssurances = new ArrayList<String>();
		List<String> countedAssurances= new ArrayList<String>();
		boolean isPrivate = true;
		int minEndorsements = 0;
		countedAssurances.add("thiscontainstab\t");
		String vooteName = "magyar";

		try {
			VoteAdminInfo secondVote = VoteRegistry.create(vooteName,neededAssurances, countedAssurances, isPrivate, minEndorsements );
			fail();
		} catch (ReportedException e) {
			// expected
			assertEquals(e.getMessage(),"Wrong characters in the countedAssurance!");
		}

	}

	@Test
	public void countedAssurance_local_character_input() throws ReportedException {
		List<String> neededAssurances = new ArrayList<String>();
		List<String> countedAssurances= new ArrayList<String>();
		boolean isPrivate = true;
		int minEndorsements = 0;
		countedAssurances.add("ThisConatinsLocaCharséűáőúöüóíÉÁŰŐÚÖÜÓÍ");
		String localVotename = "magyar";

		VoteAdminInfo secondVote = VoteRegistry.create(localVotename,neededAssurances, countedAssurances, isPrivate, minEndorsements );

		assertEquals(VoteRegistry.getByKey(secondVote.adminKey).name, localVotename);

	}




}
