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
	 * Global static session table object
	 */
	private static SessionTable sessionTable = new SessionTable();
    /**
     * constructor of the servlet. A helper method is called which start a daemon thread to clearing
     * session periodically
     */
    public SessionManagementServlet() {
        super();
        //helper method for daemon thread creation and activation
        startSessionCollectedDaemon();
    }

	/**
	 * doGet method to hangle all GET request
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//get the session id from cookies using one of the servlet utilities method. @see ServletUtilities for more info
		String sessionId = ServletUtilities.getCookieValue(request.getCookies(),
												SessionCookie.getCookiename(), null);
		//declare the session object
		SessionInfo sessionInfo = null;
		//declare session cookie object
		SessionCookie sessionCookie = null;
		//if sessionId is not found in cookies
        if (sessionId == null) {
        	//create a new session with initial session state and store it in the global hashtable 
        	sessionInfo = sessionTable.createUpdateSession(sessionId, SessionInfo.getInitialMsg());
        } else {
        	//if the http request type is POST we know that it is an form submit and need to
        	//handle appropriately 
        	if(request.getMethod().equalsIgnoreCase("POST")){
        		String replaceValue = request.getParameter("btn-replace");
        		String refreshValue = request.getParameter("btn-refresh");
        		String logoutValue = request.getParameter("btn-logout");
        		//if the replace button was pressed
        		if(replaceValue!=null && !replaceValue.isEmpty()){
        			//get the msg entered in the textbox
        			String txtMsg = request.getParameter("txt-message");
        			//update the session with new msg and update version and expiration timestamp
    				sessionInfo = sessionTable.createUpdateSession(sessionId, txtMsg);
        		}else if(refreshValue!=null && !refreshValue.isEmpty()){ //refresh button was pressed
        			//update the session version and expiration timestamp
        			sessionInfo = sessionTable.createUpdateSession(sessionId, null);
        		}else if(logoutValue!=null && !logoutValue.isEmpty()){ //logout action
        			//delete the session entry from the table
        			sessionTable.invalidate(sessionId);
        			//set a msg indicating logout state
        			request.setAttribute("message", "You are logged out. Refresh the page to return.");
        			//create a cookie with 0 as maxAge so that it expires as soon as received by the client
        			// which in effect is deleting the cookie
        			sessionCookie = new SessionCookie(sessionId, "-1", null, "/", true, 0);
                	response.addCookie(sessionCookie);
                	//forward the request to jsp
                	request.getRequestDispatcher("index.jsp").forward(request, response);
                	return;
        		}
        	}else{
        		//if request type is GET do the same as refresh action
        		sessionInfo = sessionTable.createUpdateSession(sessionId, null);
        	}
        }
        //if sessionInfo object is present -- check to ensure sanity
        if(sessionInfo != null){
        	//create a new session cookie to be sent to the client
        	sessionCookie = new SessionCookie(sessionInfo.getSessionId(), sessionInfo.getVersion(), new LocationMetadata(), "/", true, SessionInfo.getMaxage());
        	// add cookie to response
        	response.addCookie(sessionCookie);
        	//set the session state (message) attribute to be displayed
        	request.setAttribute("message", sessionInfo.getMessage());
        	//these three attributes are set to facilitate debugging. They are hidden in index.jsp
        	// To see them remove "style='None'" from the last div in the jsp
    		request.setAttribute("sessionId", sessionInfo.getSessionId());
    		request.setAttribute("version", sessionInfo.getVersion());
    		request.setAttribute("expireAt", sessionInfo.getExpireAt().toString());
        }
        //forward the request to index.jsp
		request.getRequestDispatcher("index.jsp").forward(request, response);
	}

	/**
	 * All POST actions are handled in the doGet method so this just has a call to doGet
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	/**
	 * helper method to create a daemon thread
	 */
	private void startSessionCollectedDaemon(){
		// A new thread 
		Thread sessionCollectordaemonThread = new Thread(new Runnable() {
            @Override
            public void run() {
            	//infinite loop
            	while(true){
            		try {
            			//let the thread sleep for 2 minutes
    					Thread.sleep(1000 * 60 * 2);
    				} catch (InterruptedException e) {
    					e.printStackTrace();
    				}
            		//once thread wakes up then check and remove stale session from sessionTable
                    sessionTable.removeExpiredSessions();
            	}
            }
        });
		// set the thread as Daemon so that jvm doesn't wait on it before exiting
        sessionCollectordaemonThread.setDaemon(true);
        //start the thread
        sessionCollectordaemonThread.start();
	}
}