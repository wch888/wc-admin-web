package com.wc.web.controller.product;

import com.wc.common.db.PageInfo;
import com.wc.common.db.PagerControl;
import com.wc.user.bean.LuckyMoneyLog;
import com.wc.user.service.LuckyMoneyLogService;
import com.wc.web.until.StageHelper;
import com.wc.web.until.WebUtils;
import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.util.Date;

@Controller
@RequestMapping(value = "/luckyMoneyLog")
public class LuckyMoneyLogController {

    private static final Logger logger = LoggerFactory.getLogger(LuckyMoneyLogController.class);
    @Autowired
    private LuckyMoneyLogService luckyMoneyLogService;

    @RequestMapping(value = "/list")
    public String list(Model model, HttpServletRequest request,
                       @RequestParam(required = false) String userName) {
        PageInfo pi = WebUtils.getPageInfo(request);
        LuckyMoneyLog query = new LuckyMoneyLog();
        query.setUserName(userName);
        PagerControl pc = luckyMoneyLogService.pageWithMember(query, pi);
        model.addAttribute("pc", pc);
        model.addAttribute("userName",userName);
        return "/product/luckyMoneyLog/list";
    }

    @RequestMapping(value = "/add")
    public String add(HttpServletRequest request, HttpServletResponse res) {
        LuckyMoneyLog luckyMoneyLog = getLuckyMoneyLog(request);
        luckyMoneyLog.setCreateTime(new Date());
        luckyMoneyLogService.add(luckyMoneyLog);
        return StageHelper.successForward("保存成功", res);
    }

    private LuckyMoneyLog getLuckyMoneyLog(HttpServletRequest request) {
        long pid = ServletRequestUtils.getLongParameter(request, "product.id", 0);
        int stock = ServletRequestUtils.getIntParameter(request, "stock", 0);
        float money = ServletRequestUtils.getFloatParameter(request, "money", 0.00f);
        String startTimeStr = ServletRequestUtils.getStringParameter(request, "startTime", "");
        String endTimeStr = ServletRequestUtils.getStringParameter(request, "endTime", "");
        LuckyMoneyLog luckyMoneyLog = new LuckyMoneyLog();
        luckyMoneyLog.setCreateTime(new Date());
        try {
            Date startTime = DateUtils.parseDate(startTimeStr, ProductController.pattern);
            Date endTime = DateUtils.parseDate(endTimeStr, ProductController.pattern);

        } catch (ParseException e) {
            logger.error("", e);
        }
        luckyMoneyLog.setPid(pid);
//        luckyMoneyLog.setStatus((short) status);
        return luckyMoneyLog;
    }

    @RequestMapping(value = "/addView")
    public String addView(HttpServletRequest request, HttpServletResponse res) {

        return "/product/luckyMoneyLog/add";
    }

    @RequestMapping(value = "/updateView")
    public String updateView(HttpServletRequest request, HttpServletResponse res) {
        int id = ServletRequestUtils.getIntParameter(request, "id", 0);
        if (id <= 0) {
            return StageHelper.failForward("参数提交错误", res);
        }
        LuckyMoneyLog luckyMoneyLog = luckyMoneyLogService.getById(id);
        request.setAttribute("bean", luckyMoneyLog);
        return "/product/luckyMoneyLog/update";
    }

    @RequestMapping(value = "/update")
    public String update(HttpServletRequest request, HttpServletResponse res) {
        long id = ServletRequestUtils.getLongParameter(request, "id", 0);
        if (id <= 0) {
            return StageHelper.failForward("参数提交错误", res);
        }
        LuckyMoneyLog luckyMoneyLog = getLuckyMoneyLog(request);
        luckyMoneyLog.setId(id);
        luckyMoneyLogService.update(luckyMoneyLog);
        return StageHelper.successForward("保存成功", res);
    }

    @RequestMapping(value = "/del")
    public String del(HttpServletRequest request, HttpServletResponse res) {
        long id = ServletRequestUtils.getLongParameter(request, "id", 0);
        if (id <= 0) {
            return StageHelper.failForward("参数提交错误", res);
        }
        luckyMoneyLogService.delete(id);
        return StageHelper.successForward("保存成功", res);
    }
}
