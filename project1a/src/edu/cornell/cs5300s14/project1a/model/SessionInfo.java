package edu.cornell.cs5300s14.project1a.model;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import edu.cornell.cs5300s14.project1a.util.ServletUtilities;

public class SessionInfo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6602062922504148262L;
	private static final int maxAge = 3600;
	
	public static int getMaxage() {
		return maxAge;
	}

	private String sessionId;
	private String message;
	private String version;
	private Date createdAt;
	private Date expireAt;
	
	private SessionInfo(){}
	
	private SessionInfo(String sessionId, String message, String version, Date createdAt, Date expireAt){
		this.sessionId = sessionId;
		this.message = message;
		this.version = version;
		this.createdAt = createdAt;
		this.expireAt = expireAt;		
	}
	
	public static SessionInfo getNewSession(String message){
		String sessionId = ServletUtilities.generateSessionId();
		String version = ServletUtilities.createUpdateVersion(null);
		Calendar now = GregorianCalendar.getInstance();
		Date createdAt = now.getTime();
		now.add(GregorianCalendar.SECOND, maxAge);
		return new SessionInfo(sessionId, message, version, createdAt, now.getTime());
	}
	
	public void update(String newMessage){
		if(newMessage!=null){
			this.message = newMessage;
		}
		Calendar now = GregorianCalendar.getInstance();
		now.add(GregorianCalendar.SECOND, maxAge);
		this.expireAt = now.getTime();
		this.version = ServletUtilities.createUpdateVersion(this.version);
	}

	public String getSessionId() {
		return sessionId;
	}
	
	public String getVersion() {
		return version;
	}

	public String getMessage() {
		return message;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public Date getExpireAt() {
		return expireAt;
	}
	
}