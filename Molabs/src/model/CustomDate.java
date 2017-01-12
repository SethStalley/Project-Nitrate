package model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CustomDate {
	public static String dateToString(Date date) {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(date);
	}
}
