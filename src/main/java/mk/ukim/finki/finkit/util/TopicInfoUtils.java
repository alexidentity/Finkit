package mk.ukim.finki.finkit.util;

import java.util.NavigableMap;
import java.util.TreeMap;
import java.util.Map.Entry;

public class TopicInfoUtils {
	
	private static final NavigableMap<Long, String> suffixes = new TreeMap<Long, String>();
	static {
	  suffixes.put(1_000L, "k+");
	  suffixes.put(1_000_000L, "M+");
	  suffixes.put(1_000_000_000L, "G+");
	  suffixes.put(1_000_000_000_000L, "T+");
	  suffixes.put(1_000_000_000_000_000L, "P+");
	  suffixes.put(1_000_000_000_000_000_000L, "E+");
	}

	public static String formatNumber(long value) {
	  if (value == Long.MIN_VALUE) {
	  	return formatNumber(Long.MIN_VALUE + 1);
  	}
	  
	  if (value < 0) {
	  	return "-" + formatNumber(-value);
	  }
	  
	  if (value < 1000) {
	  	return Long.toString(value);
	  }

	  Entry<Long, String> entry = suffixes.floorEntry(value);
	  Long divideBy = entry.getKey();
	  String suffix = entry.getValue();

	  long truncated = value / (divideBy / 10);
	  boolean hasDecimal = truncated < 100 && (truncated / 10d) != (truncated / 10);
	  
	  return hasDecimal ? (truncated / 10d) + suffix : (truncated / 10) + suffix;
	}
}
