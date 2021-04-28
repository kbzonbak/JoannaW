package bbr.b2b.regional.logistic.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * Checks for invalid characters
 * in email addresses
 */
public class EmailValidation {

	public static boolean isValid(String email) throws Exception {
		// Set the email pattern string
		Pattern p = Pattern.compile(".+@.+\\.[a-z]+");
		// Match the given string with the pattern
		Matcher m = p.matcher(email);
		// check whether match is found
		boolean matchFound = m.matches();
		if (matchFound)
			return true;
		else
			return false;
	}
}
