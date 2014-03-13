package edu.cornell.cs5300s14.project1a.model;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import edu.cornell.cs5300s14.project1a.util.ServletUtilities;

/**
 * @author harsh
 * Class to store the session information on the server side
 */
public class SessionInfo implements Serializable {

	private static final long serialVersionUID = -6602062922504148262L;
	
	/**
	 * maxAge value -- can be modified only here
	 */
	private static final int maxAge = 3600;
	
	/**
	 * initial message to be shown to the user -- only to be modified here 
	 */
	private static String initialMsg = "Hello, User!";
	
	public static int getMaxage() {
		return maxAge;
	}

	private String sessionId; //unique session ID
	private String message;		// session State
	private String version;		// Version
	private Date createdAt;		// created at timestamp
	private Date expireAt;		// expire at timestamp
	
	private SessionInfo(){}
	
	/**
	 * Constructor with argument to initialize a session info object
	 * @param sessionId
	 * @param message
	 * @param version
	 * @param createdAt
	 * @param expireAt
	 */
	private SessionInfo(String sessionId, String message, String version, Date createdAt, Date expireAt){
		this.sessionId = sessionId;
		this.message = message;
		this.version = version;
		this.createdAt = createdAt;
		this.expireAt = expireAt;		
	}
	
	/**
	 * Method to create a new session. It calls the ServletUtilities to generate a unique sessionId
	 * and creates a version with initial value of 1. Also set the created and expire timestamps
	 * @param message
	 * @return
	 */
	public static SessionInfo getNewSession(String message){
		String sessionId = ServletUtilities.generateSessionId();
		String version = ServletUtilities.createUpdateVersion(null);
		Calendar now = GregorianCalendar.getInstance();
		Date createdAt = now.getTime();
		now.add(GregorianCalendar.SECOND, maxAge);
		return new SessionInfo(sessionId, message, version, createdAt, now.getTime());
	}
	
	/**
	 * updates the sessionInfo object. It updates the message if any and then sets the expire
	 * timestamp to the current time and also increments the version
	 * @param newMessage
	 */
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

	public static String getInitialMsg() {
		return initialMsg;
	}

	public static void setInitialMsg(String initialMsg) {
		SessionInfo.initialMsg = initialMsg;
	}
	
}