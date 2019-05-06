package org.rulez.demokracia.pdengine.vote;

import org.rulez.demokracia.pdengine.dataobjects.VoteAdminInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VoteController {

	@Autowired
	private VoteService voteService;

	@PostMapping(path = "/vote", produces = "application/json")
	public ResponseEntity<VoteAdminInfo> createVote(@RequestBody final CreateVoteRequest request) {
		VoteAdminInfo adminInfo = voteService.createVote(request);
		return ResponseEntity.ok(adminInfo);
	}
}
