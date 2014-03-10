package edu.cornell.cs5300s14.project1a.model;

import java.util.concurrent.ConcurrentHashMap;

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
}
