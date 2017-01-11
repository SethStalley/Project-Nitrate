package model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

public class DateTimeDeserializer implements JsonDeserializer<Date> {

	@Override
	public Date deserialize(JsonElement json, java.lang.reflect.Type typeOfT, JsonDeserializationContext context)
			throws JsonParseException {
		try {
			return new SimpleDateFormat("EEE MMM dd kk:mm:ss z yyyy").parse(json.getAsString());
		} catch (ParseException e) {
			try {
				return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").parse(json.getAsString());
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
		}
		return null;
	}
}