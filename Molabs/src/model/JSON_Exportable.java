package model;

import java.util.Date;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public abstract class JSON_Exportable {
	public String getAsJSON() {
		Gson gson = new GsonBuilder()
				.registerTypeAdapter(Date.class, new DateTimeDeserializer())
				.registerTypeAdapter(Date.class, new DateTimeSerializer())
				.create();
		return gson.toJson(this);
	}
}
