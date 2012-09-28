package uk.co.devooght;

import java.util.regex.Pattern;

public class UrlSanitizer {
        public static Pattern hyphen = Pattern.compile("[-]", Pattern.UNICODE_CASE);
        public static Pattern multiHyphen = Pattern.compile("(-{2,})", Pattern.UNICODE_CASE);
	public static Pattern whitespace = Pattern.compile("[\\s]", Pattern.UNICODE_CASE);
	public static Pattern acceptableUrlChars = Pattern.compile("[^a-zA-Z0-9_-]", Pattern.UNICODE_CASE);
	public static Pattern doubleHyphen = Pattern.compile("--", Pattern.UNICODE_CASE);
	public static String sanitize(String val) {
		val = val.trim();
                val = hyphen.matcher(val).replaceAll(" ");
		val = whitespace.matcher(val).replaceAll("-");
		val = multiHyphen.matcher(val).replaceAll("-");
                val = acceptableUrlChars.matcher(val).replaceAll("");
                val = multiHyphen.matcher(val).replaceAll("-");
		return val;
	}
}
