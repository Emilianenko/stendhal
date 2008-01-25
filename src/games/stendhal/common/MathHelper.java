package games.stendhal.common;

/**
 * Helper functions for various mathematical tasks.
 */
public class MathHelper {
	public static long MILLISENCONDS_IN_ONE_MINUTE = 60 * 1000; 
	public static long MILLISENCONDS_IN_ONE_HOUR = 60 * MILLISENCONDS_IN_ONE_MINUTE; 
    public static long MILLISENCONDS_IN_ONE_DAY = 24 * MILLISENCONDS_IN_ONE_HOUR;
    public static long MILLISENCONDS_IN_ONE_WEEK = 7 * MILLISENCONDS_IN_ONE_DAY;
    
	/**
	 * parses an integer safely. returning a default if nothing can be sanely
	 * parsed from it
	 * @param s the string to parse
	 * @param def the default to set
	 * 
	 * @return An integer
	 */
	public static int parseIntDefault(String s, int def) {
		if (s == null) {
			return def;
		}

		int r;
		try {
			r = Integer.parseInt(s);
		} catch (NumberFormatException e) {
			r = def;
		}
		return r;
	}

	/**
	 * parses an integer safely, returning 0 if nothing can be sanely parsed.
	 * from it
	 * @param s to parse
	 * 
	 * @return An integer
	 */
	public static int parseInt(String s) {
		return parseIntDefault(s, 0);
	}

}
