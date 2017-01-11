package model;

import java.util.Date;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public abstract class JSON_Exportable {
	public String getAsJSON() {
		Gson gson = new GsonBuilder()
				.registerTypeAdapter(Date.class, new DateTimeSerializer())
				.registerTypeAdapter(Date.class, new DateTimeDeserializer())
				
				.create();
		return gson.toJson(this);
	}
}
