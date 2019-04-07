package org.rulez.demokracia.pdengine.servlet;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.ws.WebServiceContext;

import org.rulez.demokracia.pdengine.IVoteManager;
import org.rulez.demokracia.pdengine.RandomUtils;
import org.rulez.demokracia.pdengine.dataobjects.VoteAdminInfo;
import org.rulez.demokracia.pdengine.exception.ReportedException;
import org.rulez.demokracia.pdengine.servlet.requests.CreateVoteRequest;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

@Path("vote")
public class VoteManagerInterface {

	private static final Logger LOGGER = Logger.getLogger( RandomUtils.class.getName() );

	@Resource
	private WebServiceContext wsContext;

	@POST
	@Produces({MediaType.APPLICATION_JSON})
	@Consumes({MediaType.APPLICATION_JSON})
	public Response createVote(final CreateVoteRequest request) {
		VoteAdminInfo adminInfo;
		try {
			adminInfo = IVoteManager.getVoteManager(wsContext).createVote(
					request.voteName,
					request.neededAssurances,
					request.countedAssurances,
					request.isPrivate,
					request.minEndorsements);
		} catch (ReportedException e) {
			JsonObject jsonObject = new JsonObject();
			jsonObject.add("error", e.toJSON());
			jsonObject.add("input", new Gson().toJsonTree(request));
			String result = new Gson().toJson(jsonObject);
			LOGGER.log(Level.FINE, result);
			return Response.status(400).entity(result).build();
		}
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("adminKey", adminInfo.adminKey);
		jsonObject.addProperty("voteId", adminInfo.voteId);
		String result = new Gson().toJson(jsonObject);
		return Response.status(200).entity(result).build();
	}
}
