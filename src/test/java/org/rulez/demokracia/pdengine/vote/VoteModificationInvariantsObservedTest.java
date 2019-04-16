package org.rulez.demokracia.pdengine.vote;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.rulez.demokracia.pdengine.InvariantTesting;
import org.rulez.demokracia.pdengine.annotations.TestedBehaviour;
import org.rulez.demokracia.pdengine.annotations.TestedFeature;
import org.rulez.demokracia.pdengine.annotations.TestedOperation;
import org.rulez.demokracia.pdengine.dataobjects.VoteAdminInfo;
import org.rulez.demokracia.pdengine.vote.Vote;

@TestedFeature("Manage votes")
@TestedOperation("modify vote")
@TestedBehaviour("vote invariants")
@RunWith(MockitoJUnitRunner.class)
public class VoteModificationInvariantsObservedTest extends InvariantTesting {

	@InjectMocks
	private VoteServiceImpl voteService;

	@Mock
	private VoteRepository voteRepository;


	@Test
	public void vote_invariants_are_observerd_in_modify_vote() {
		Vote existingVote = new Vote("name", Set.of(), Set.of(), false, 1);
		saveInvariables(existingVote);
		when(voteRepository.findById(existingVote.getId())).thenReturn(Optional.of(existingVote));
		voteService.modifyVote(new VoteAdminInfo(existingVote.getId(), existingVote.getAdminKey()), "modifiedVoteName");
		ArgumentCaptor<Vote> captor = ArgumentCaptor.forClass(Vote.class);
		verify(voteRepository).save(captor.capture());
		assertInvariables(captor.getValue());
	}
}
