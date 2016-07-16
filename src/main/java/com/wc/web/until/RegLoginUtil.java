package com.wc.web.until;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class RegLoginUtil {
    private static final Logger logger = LoggerFactory.getLogger(RegLoginUtil.class);

    //cookie
    // 7天， cookie _mu  的过期时间
    public static final int MU_TIIME = 604800;
    public static final String CHARSET_ENCODE = "UTF-8";
    //session
    public final static String SESSION_UID = "uid";
    public final static String SESSION_USER = "user";
    public final static String JSESSIONID = "jid";


    private RegLoginUtil() {
    }

    public static long getSessionUid(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (session != null) {
            Object obj = session.getAttribute(SESSION_UID);
            if (obj != null) {
                return Long.parseLong(obj + "");
            }
        }
        return -1L;
    }


    public static void setSessionUid(HttpServletRequest request, long uid) {
        HttpSession session = request.getSession();
        if (session != null) {
            session.setAttribute(SESSION_UID, uid);
        }
    }



    public static boolean writeJsessionIdCookie(HttpServletResponse response, String jsessionId) {
        try {
            CookieUtil.setCookieValue(response, JSESSIONID, jsessionId, "/", MU_TIIME);
        } catch (Exception e) {
            logger.error(e.getMessage() + " JSESSIONID:" + jsessionId, e);
            return false;
        }

        return true;
    }

    public static void deleteCookie(HttpServletRequest request, HttpServletResponse response) {
        CookieUtil.deleteCookie(request, response, "/", JSESSIONID);

    }

    public static void deleteSession(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();

        if (session != null) {
            session.removeAttribute(SESSION_UID);
        }
    }


}
