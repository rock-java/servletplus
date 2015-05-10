package com.rockson.servletplus;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class TimeUtils {
	
	public static SimpleDateFormat GMT_FORMAT= new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss 'GMT'", Locale.UK); 
	public static Date parseGMT(String gmtTime) throws ParseException {
		GMT_FORMAT.setTimeZone(TimeZone.getTimeZone("GMT"));
		return GMT_FORMAT.parse(gmtTime);
	}
	public static String formatGMT(Date date) {
		return GMT_FORMAT.format(date);
	}

}
