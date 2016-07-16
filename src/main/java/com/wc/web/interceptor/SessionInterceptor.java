package com.wc.web.interceptor;

import com.wc.user.service.MemberService;
import com.wc.web.until.RegLoginUtil;
import com.wc.web.until.StageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class SessionInterceptor extends HandlerInterceptorAdapter {

    private static final Logger logger = LoggerFactory.getLogger(SessionInterceptor.class);
    @Autowired
    private MemberService memberService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 如果session中没有user对象，则创建一个。
        long uid = RegLoginUtil.getSessionUid(request);
        if (uid > 0) {
            return true;//已经登录
        }

        logger.warn("没有登陆");
//        {
//            "statusCode":"301",
//                "message":"\u4f1a\u8bdd\u8d85\u65f6\uff0c\u8bf7\u91cd\u65b0\u767b\u5f55\u3002",
//                "navTabId":"",
//                "callbackType":"",
//                "forwardUrl":""
//        }

        StageHelper.writeDwzSignal("301", "登陆超时", "", "", "", response);
//        response.sendRedirect("/");
        return false;
    }

}
