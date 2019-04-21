package org.rulez.demokracia.pdengine.ballot;

import java.util.List;

import org.rulez.demokracia.pdengine.RandomUtils;
import org.rulez.demokracia.pdengine.authentication.AuthenticatedUserService;
import org.rulez.demokracia.pdengine.vote.Vote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BallotServiceImpl implements BallotService {

	@Autowired
	private AuthenticatedUserService authenticatedUserService;

	@Override
	public String obtainBallot(final Vote vote, final String adminKey) {
		vote.checkAdminKey(adminKey);
		if (adminKey.equals(vote.getAdminKey())) {
			vote.increaseRecordedBallots("admin");
		} else {
			checkBallotAsUser(vote);
		}

		String ballot = RandomUtils.createRandomKey();
		vote.addBallot(ballot);
		return ballot;
	}

	private void checkBallotAsUser(final Vote vote) {
		if (authenticatedUserService.getUserPrincipal() == null) {
			throw new IllegalArgumentException("Simple user is not authenticated, cannot issue any ballot.");
		}
		if (!userHasAllAssurance(vote.getNeededAssurances())) {
			throw new IllegalArgumentException("The user does not have all of the needed assurances.");
		}
		String userName = authenticatedUserService.getAuthenticatedUserName();
		if (vote.getRecordedBallotsCount(userName).intValue() > 0) {
			throw new IllegalArgumentException("This user already have a ballot.");
		}

		vote.increaseRecordedBallots(userName);
	}

	public boolean userHasAllAssurance(final List<String> neededAssuranceList) {
		return neededAssuranceList.stream().allMatch(authenticatedUserService::hasAssurance);
	}

}
