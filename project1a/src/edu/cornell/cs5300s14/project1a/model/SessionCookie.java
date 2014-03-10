package edu.cornell.cs5300s14.project1a.model;

import javax.servlet.http.Cookie;

public class SessionCookie extends Cookie {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4062007212944345232L;
	private static final String cookieName = "CS5300PROJ1ASESSIONHS634";
	
	private String sessionId;
	private String version;
	private LocationMetadata locationMetadata;

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
