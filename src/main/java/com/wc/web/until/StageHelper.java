package com.wc.web.until;

import com.google.gson.Gson;
import com.wc.user.bean.Member;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class StageHelper {

    public static final String loginUserSessionName = "bkstage_user";

    public static Member getLoginUser(HttpServletRequest request) {
        return (Member) request.getSession().getAttribute(loginUserSessionName);
    }


    /**
     * Dwz 控制当前页面关闭   callbackType
     */
    public final static String DWZ_CLOSE_CURRENT = "closeCurrent";

    /**
     * Dwz 控制当前页面进行跳页   callbackType
     */
    public final static String DWZ_FORWARD = "forward";

    /**
     * { statusCode:${statusCode}, <br/>
     * message:"${message}", <br/>
     * navTabId:"${param.navTabId}", <br/>
     * callbackType:"${param.callbackType}",<br/>
     * forwardUrl:"${param.forwardUrl}${objectId}"<br/>
     * } <br/>
     * <br/>
     * { "statusCode":"状态码200或300", <br/>
     * "message":"提示信息", <br/>
     * "navTabId":"操作成功后需要指定navTab时使用", <br/>
     * "callbackType":"closeCurrent或forward",<br/>
     * "forwardUrl":"callbackType是forward时使用"<br/>
     * }
     */

    public static String writeDwzSignal(String statusCode, String message, String navTabId,
                                        String callbackType, String forwardUrl, HttpServletResponse response) {
        response.setHeader("Content-Language", "zh-cn");
        response.setHeader("Cache-Control", "no-cache");
        response.setContentType("application/json; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        try {
            response.getWriter().write("{ \"statusCode\":\"" + statusCode + "\"," + "\"message\":\"" + message + "\","
                    + "\"navTabId\":\"" + navTabId + "\"," + "\"callbackType\":\"" + callbackType
                    + "\"," + "\"forwardUrl\":\"" + forwardUrl + "\" }");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String writeString(String string, HttpServletResponse response) {
        response.setHeader("Content-Language", "zh-cn");
        response.setHeader("Cache-Control", "no-cache");
        response.setContentType("application/json; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        try {
            response.getWriter().write(string);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static String writeJson(HttpServletResponse response, Object obj) {
        response.setHeader("Content-Language", "zh-cn");
        response.setHeader("Cache-Control", "no-cache");
        response.setContentType("application/json; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        try {
            response.getWriter().write(new Gson().toJson(obj));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String dwzSuccessClose(String message, String navTabId,
                                         String forwardUrl, HttpServletResponse response) {
        return StageHelper.writeDwzSignal("200", message, navTabId, DWZ_CLOSE_CURRENT, forwardUrl, response);
    }
    public static String dwzSuccessClose(String message, 	String forwardUrl, HttpServletResponse response) {
    	return StageHelper.writeDwzSignal("200", message, "", DWZ_CLOSE_CURRENT, forwardUrl, response);
    }

    public static String dwzFailClose(String message, String navTabId,
                                      String forwardUrl, HttpServletResponse response) {
        return StageHelper.writeDwzSignal("300", message, navTabId, DWZ_CLOSE_CURRENT, forwardUrl, response);
    }

    public static String dwzFailClose(String message, HttpServletResponse response) {
        return StageHelper.writeDwzSignal("300", message, "", DWZ_CLOSE_CURRENT, "", response);
    }

    public static String dwzSuccessForward(String message, String navTabId,
                                           String forwardUrl, HttpServletResponse response) {
        return StageHelper.writeDwzSignal("200", message, navTabId, DWZ_FORWARD, forwardUrl, response);
    }

    public static String dwzFailForward(String message, String navTabId,
                                        String forwardUrl, HttpServletResponse response) {
        return StageHelper.writeDwzSignal("300", message, navTabId, DWZ_FORWARD, forwardUrl, response);
    }

    public static String failForward(String message, HttpServletResponse response) {
        return StageHelper.writeDwzSignal("300", message,"", DWZ_FORWARD, "", response);
    }

    public static String successForward(String message, HttpServletResponse response) {
        return StageHelper.writeDwzSignal("200", message, "", DWZ_FORWARD, "", response);
    }

    public static String successForward(String message,String url, HttpServletResponse response) {
        return StageHelper.writeDwzSignal("200", message, "", DWZ_FORWARD, url, response);
    }


    /**
     * 写入cookie
     *
     * @param response
     * @param cookieName
     * @param cookieValue
     * @param cookiePath
     * @param age
     */
    public static void addWriteCookie(HttpServletResponse response, String cookieName,
                                      String cookieValue, String cookiePath, Integer age) {
        Cookie c = new Cookie(cookieName, cookieValue);
        // c.setDomain(SystemConfig.getProperty("cookie.domain"));
        if (StringUtils.isNotBlank(cookiePath)) {
            c.setPath("/");
        }
        if (null != age) {
            c.setMaxAge(age);
        }
        response.addCookie(c);
    }

}
