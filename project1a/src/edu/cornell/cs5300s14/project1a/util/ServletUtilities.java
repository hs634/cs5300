package edu.cornell.cs5300s14.project1a.util;

import java.util.UUID;
import javax.servlet.http.Cookie;

public class ServletUtilities {

	/**
	 * Helper method to find the value of a cookie based on the cookie name.
	 * Return defaultValue if cookie doesn't exist
	 * 
	 * @param cookies
	 * @param cookieName
	 * @param defaultValue
	 * @return
	 */
	public static String getCookieValue(Cookie[] cookies, String cookieName,
			String defaultValue) {
		if (cookies == null)
			return defaultValue;
		for (int i = 0; i < cookies.length; i++) {
			Cookie cookie = cookies[i];
			if (cookieName.equals(cookie.getName()))
				return (cookie.getValue());
		}
		return (defaultValue);
	}

	/**
	 * static method to generate a unique string for session Id using the in build UUID implementation
	 * in java.
	 * @return a unique string
	 */
	public static String generateSessionId() {
		return UUID.randomUUID().toString();
	}

	/**
	 * Helper method to create or increment a previous version number
	 * @param sPrevVersion
	 * @return a new version number in string representation
	 */
	public static String createUpdateVersion(String sPrevVersion) {
		Integer iVersion = 1;
		if (sPrevVersion != null) {
			Integer iPrevVersion = Integer.parseInt(sPrevVersion);
			iVersion = iPrevVersion + 1;
		}
		return iVersion.toString();
	}
}