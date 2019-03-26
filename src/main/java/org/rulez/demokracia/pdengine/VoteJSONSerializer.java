package org.rulez.demokracia.pdengine;

import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.google.gson.Gson;

public class VoteJSONSerializer {

	public JSONObject fromVote(final Vote vote) {
		String json = new Gson().toJson(vote);
		JSONObject jsonObject = new JSONObject(json);
		jsonObject.put("choices", createChoicesJson(vote.choices));
		return jsonObject;
	}

	private JSONArray createChoicesJson(final Map<String, Choice> choices) {
		JSONArray array = new JSONArray();

		choices.forEach((key, choice) -> array.put(choiceAsJson(key, choice)));

		return array;
	}

	private JSONObject choiceAsJson(final String key, final Choice choice) {
		JSONObject obj = new JSONObject();
		obj.put("initiator", choice.userName);
		obj.put("endorsers", choice.endorsers);
		obj.put("name", choice.name);
		obj.put("id", key);
		return obj;
	}
}
