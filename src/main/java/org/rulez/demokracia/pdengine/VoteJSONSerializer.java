package org.rulez.demokracia.pdengine;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class VoteJSONSerializer {

	private class ChoiceForSerialization {
		public String id;
		public String initiator;
		public List<String> endorsers;
		public String name;

		public ChoiceForSerialization(final String id, final String initiator, final List<String> endorsers,
				final String name) {
			super();
			this.id = id;
			this.initiator = initiator;
			this.endorsers = endorsers;
			this.name = name;
		}
	}

	private ExclusionStrategy exclusionStrategy = new ExclusionStrategy() {

		@Override
		public boolean shouldSkipField(final FieldAttributes f) {
			return f.getDeclaredClass().equals(Vote.class) && f.getName().equals("adminKey");
		}

		@Override
		public boolean shouldSkipClass(final Class<?> clazz) {
			return false;
		}
	};

	public JsonObject fromVote(final Vote vote) {
		Gson gsonBuilder = new GsonBuilder().addSerializationExclusionStrategy(exclusionStrategy).create();
		JsonElement json = gsonBuilder.toJsonTree(vote);
		json.getAsJsonObject().add("choices", gsonBuilder.toJsonTree(choicesToList(vote.choices)));
		return json.getAsJsonObject();
	}

	private JsonArray choicesToList(final Map<String, Choice> choices) {
		List<ChoiceForSerialization> choiceList = new ArrayList<>();
		choices.forEach((id, choice) -> choiceList
				.add(new ChoiceForSerialization(id, choice.userName, choice.endorsers, choice.name)));
		return new Gson().toJsonTree(choiceList).getAsJsonArray();
	}
}
