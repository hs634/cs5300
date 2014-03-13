package edu.cornell.cs5300s14.project1a.model;

import java.util.concurrent.ConcurrentHashMap;
import java.util.Date;
import java.util.Iterator;
import java.util.Map.Entry;

/**
 * @author harsh
 * Global Session table class to store all the user sessions. Extends ConcurrentHashMap to provide 
 * concurrency. Also removes a particular session from the table before updating it. After updation of 
 * the session information, it puts the session object back to its place
 */
public class SessionTable extends ConcurrentHashMap<String, SessionInfo> {

	private static final long serialVersionUID = 1091168958256533165L;
	
	/**
	 * Method to create a new session if no session exists or update a session if it exists. If a 
	 * session exists, it first removes the entry and then updates it. After updation the session entry 
	 * is put back in. This is to make sure there can be no concurrent updates to the session info.
	 * @param sessionId
	 * @param newMessage
	 * @return
	 */
	public SessionInfo createUpdateSession(String sessionId, String newMessage){
		SessionInfo sessionInfo = null;
		//if no session exists
		if(sessionId == null || this.get(sessionId) == null){
			sessionInfo = SessionInfo.getNewSession(newMessage);
		}else{
			//removing element from the session Table so there no synchronization issues
			sessionInfo = this.remove(sessionId);
			if(sessionInfo != null){
				sessionInfo.update(newMessage);
			}
		}
		//put session back to global table
		this.put(sessionInfo.getSessionId(), sessionInfo);
		return sessionInfo;
	}
	
	/**
	 * Invalidates(deletes) a particular session in the table
	 * @param sessionId
	 */
	public void invalidate(String sessionId){
		//if session exists
		if(sessionId != null && this.get(sessionId) != null){
			this.remove(sessionId);
		}
	}
	
	/**
	 * Method to remove all expired sessions. Uses an iterator to loop through the map and find
	 * entries whose "expire at" timestamp is earlier than current time and remove those entries
	 */
	public void removeExpiredSessions(){
		System.out.println("Clearing Expired Sessions Now...");
		Iterator<Entry<String, SessionInfo>> iter = this.entrySet().iterator();
		while(iter.hasNext()){
			Entry<String, SessionInfo> entry = iter.next();
			SessionInfo sessionInfo = entry.getValue();
			if(sessionInfo.getExpireAt().before(new Date())){
				System.out.println("Removing a session now with session Id = " + sessionInfo.getSessionId());
				iter.remove();
			}
		}
	}
}