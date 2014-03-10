package edu.cornell.cs5300s14.project1a.util;

import java.util.UUID;
import javax.servlet.http.Cookie;

public class ServletUtilities {
  public static final String DOCTYPE =
    "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.0 Transitional//EN\">";

  public static String headWithTitle(String title) {
    return(DOCTYPE + "\n" +
           "<HTML>\n" +
           "<HEAD><TITLE>" + title + "</TITLE></HEAD>\n");
  }

  public static String getCookieValue(Cookie[] cookies,
                                      String cookieName,
                                      String defaultValue) {
	if(cookies == null) return defaultValue;
	for(int i=0; i<cookies.length; i++) {
	  Cookie cookie = cookies[i];
	  if (cookieName.equals(cookie.getName()))
	    return(cookie.getValue());
	}
    return(defaultValue);
  }
  
  public static String generateSessionId() {
	    return UUID.randomUUID().toString();
  }
  public static String createUpdateVersion(String sPrevVersion){
	  Integer iVersion = 1;
	  if(sPrevVersion != null){
		  Integer iPrevVersion = Integer.parseInt(sPrevVersion);
		  iVersion = iPrevVersion + 1;
	  }
	  return iVersion.toString();
  }
}