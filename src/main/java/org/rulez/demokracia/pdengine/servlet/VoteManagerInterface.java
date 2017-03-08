package org.rulez.demokracia.pdengine.servlet;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONObject;
import org.rulez.demokracia.pdengine.IVoteManager;
import org.rulez.demokracia.pdengine.dataobjects.VoteAdminInfo;
import org.rulez.demokracia.pdengine.exception.ReportedException;
import org.rulez.demokracia.pdengine.servlet.requests.CreateVoteRequest;

@Path("vote")
public class VoteManagerInterface {

	@POST
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
	public Response createVote(CreateVoteRequest req) {
		VoteAdminInfo adminInfo;
		try {
			adminInfo = IVoteManager.getVoteManager().createVote(
					req.getVoteName(),
					req.getNeededAssurances(),
					req.getCountedAssurances(),
					req.isPrivate(),
					req.getMinEndorsements());
		} catch (ReportedException e) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("error", e.toJSON());
			jsonObject.put("input", new JSONObject(req));
			String result = jsonObject.toString(1);
			return Response.status(400).entity(result).build();
		}
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("adminKey", adminInfo.getAdminKey());
		jsonObject.put("voteId", adminInfo.getVoteId());
		String result = jsonObject.toString(1);
		return Response.status(200).entity(result).build();
	}
	
	@GET
    @Produces(MediaType.APPLICATION_JSON)
	public Response hello() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("adminKey", "theKey");
		jsonObject.put("voteId", "theId");
		String result = jsonObject.toString(1);
		return Response.status(200).entity(result).build();
	}
}
