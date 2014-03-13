package edu.cornell.cs5300s14.project1a.model;

import javax.servlet.http.Cookie;

/**
 * @author harsh
 * Class to represent a session cookie implementation. It overrides
 * the built in servlet cookie class and adds custom functionality 
 * like version and locationMetadata
 */
public class SessionCookie extends Cookie {

	private static final long serialVersionUID = 4062007212944345232L;
	/**
	 * cookie Name as a final string
	 */
	private static final String cookieName = "CS5300PROJ1ASESSIONHS634";
	
	private String sessionId;
	private String version;
	private LocationMetadata locationMetadata;

	
	/**
	 * Argument Constructor for creating a new cookie based on the following parameters 
	 * @param sessionId
	 * @param version
	 * @param lm
	 * @param path
	 * @param httpOnly
	 * @param maxAge
	 */
	public SessionCookie(String sessionId, String version, LocationMetadata lm, String path, boolean httpOnly, int maxAge) {
		super(cookieName, sessionId);
		this.sessionId = sessionId;
		this.version = version;
		this.locationMetadata = lm;
		this.setPath(path);
		this.setHttpOnly(httpOnly);
		this.setMaxAge(maxAge);
	}
	
	public String toString(){
		return this.sessionId + "_" + this.version + "_" + this.locationMetadata.toString();
	}
	
	public static String getCookiename() {
		return cookieName;
	}
}
