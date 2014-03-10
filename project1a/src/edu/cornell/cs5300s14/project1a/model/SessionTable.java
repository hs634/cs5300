package edu.cornell.cs5300s14.project1a.model;

import java.util.concurrent.ConcurrentHashMap;
import java.util.Date;
import java.util.Iterator;
import java.util.Map.Entry;

public class SessionTable extends ConcurrentHashMap<String, SessionInfo> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1091168958256533165L;
	
	public SessionInfo createUpdateSession(String sessionId, String newMessage){
		SessionInfo sessionInfo = null;
		if(sessionId == null || this.get(sessionId) == null){
			sessionInfo = SessionInfo.getNewSession(newMessage);
		}else{
			sessionInfo = this.get(sessionId);
			sessionInfo.update(newMessage);
		}
		this.put(sessionInfo.getSessionId(), sessionInfo);
		return sessionInfo;
	}
	
	public void invalidate(String sessionId){
		if(sessionId != null && this.get(sessionId) != null){
			this.remove(sessionId);
		}
	}
	
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