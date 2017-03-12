package mk.ukim.finki.finkit.util;

import java.util.Date;

import org.ocpsoft.prettytime.PrettyTime;

public class DateFormatterUtils {
	
	public static String formatDate(Date date) {
		PrettyTime prettyTime = new PrettyTime();
		String formattedDate = prettyTime.format(date);

	  return formattedDate;
	}
	
}
