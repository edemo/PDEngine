package org.rulez.demokracia.pdengine;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

public class VoteJSONSerializer {

	private final ExclusionStrategy exclusionStrategy = new ExclusionStrategy() {

		@Override
		public boolean shouldSkipField(final FieldAttributes field) {
			return field.getDeclaredClass().equals(Vote.class) && field.getName().equals("adminKey");
		}

		@Override
		public boolean shouldSkipClass(final Class<?> clazz) {
			return false;
		}
	};

	public JsonObject fromVote(final Vote vote) {
		Gson gsonBuilder = new GsonBuilder().addSerializationExclusionStrategy(exclusionStrategy).create();
		return gsonBuilder.toJsonTree(vote).getAsJsonObject();
	}
}
