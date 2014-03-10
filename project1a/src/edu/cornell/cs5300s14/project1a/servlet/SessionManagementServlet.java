package edu.cornell.cs5300s14.project1a.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.cornell.cs5300s14.project1a.model.LocationMetadata;
import edu.cornell.cs5300s14.project1a.model.SessionCookie;
import edu.cornell.cs5300s14.project1a.model.SessionInfo;
import edu.cornell.cs5300s14.project1a.model.SessionTable;
import edu.cornell.cs5300s14.project1a.util.ServletUtilities;

/**
 * Servlet implementation class SessionManagementServlet
 */
@WebServlet("")
public class SessionManagementServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServletHttpServlet()
     */
	private static SessionTable sessionTable = new SessionTable();
    public SessionManagementServlet() {
        super();
        startSessionCollectedDaemon();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String sessionId = ServletUtilities.getCookieValue(request.getCookies(),
												SessionCookie.getCookiename(), null);
		SessionInfo sessionInfo = null;
		SessionCookie sessionCookie = null;
        if (sessionId == null) {
        	sessionInfo = sessionTable.createUpdateSession(sessionId, SessionInfo.getInitialMsg());
        } else {
        	if(request.getMethod().equalsIgnoreCase("POST")){
        		String replaceValue = request.getParameter("btn-replace");
        		String refreshValue = request.getParameter("btn-refresh");
        		String logoutValue = request.getParameter("btn-logout");
        		if(replaceValue!=null && !replaceValue.isEmpty()){
        			String txtMsg = request.getParameter("txt-message");
    				sessionInfo = sessionTable.createUpdateSession(sessionId, txtMsg);
        		}else if(refreshValue!=null && !refreshValue.isEmpty()){
        			sessionInfo = sessionTable.createUpdateSession(sessionId, null);
        		}else if(logoutValue!=null && !logoutValue.isEmpty()){
        			sessionTable.invalidate(sessionId);
        			sessionCookie = new SessionCookie(sessionId, "0", null, "/", true, 0);
                	response.addCookie(sessionCookie);
                	request.getRequestDispatcher("index.jsp").forward(request, response);
                	return;
        		}
        	}else{
        		sessionInfo = sessionTable.createUpdateSession(sessionId, null);
        	}
        }
        if(sessionInfo != null){
        	sessionCookie = new SessionCookie(sessionInfo.getSessionId(), sessionInfo.getVersion(), new LocationMetadata(), "/", true, SessionInfo.getMaxage());
        	response.addCookie(sessionCookie);
        	request.setAttribute("message", sessionInfo.getMessage());
    		request.setAttribute("sessionId", sessionInfo.getSessionId());
    		request.setAttribute("version", sessionInfo.getVersion());
    		request.setAttribute("expireAt", sessionInfo.getExpireAt().toString());
        }
		request.getRequestDispatcher("index.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	private void startSessionCollectedDaemon(){
		Thread sessionCollectordaemonThread = new Thread(new Runnable() {
            @Override
            public void run() {
            	while(true){
            		try {
    					Thread.sleep(1000);
    				} catch (InterruptedException e) {
    					e.printStackTrace();
    				}
                    sessionTable.removeExpiredSessions();
            	}
            }
        });
        sessionCollectordaemonThread.setDaemon(true);
        sessionCollectordaemonThread.start();
	}
}
