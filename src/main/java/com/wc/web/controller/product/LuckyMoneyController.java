package com.wc.web.controller.product;

import com.wc.common.db.PageInfo;
import com.wc.common.db.PagerControl;
import com.wc.user.bean.LuckyMoney;
import com.wc.user.bean.LuckyMoneyProduct;
import com.wc.user.service.LuckyMoneyService;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;

@Controller
@RequestMapping(value = "/luckyMoney")
public class LuckyMoneyController {

    private static final Logger logger = LoggerFactory.getLogger(LuckyMoneyController.class);
    @Autowired
    private LuckyMoneyService luckyMoneyService;

    @RequestMapping(value = "/list")
    public String list(Model model, HttpServletRequest request) {
        PageInfo pi = WebUtils.getPageInfo(request);
        PagerControl pc = luckyMoneyService.page(new LuckyMoneyProduct(), pi, "order by id desc");
        model.addAttribute("pc", pc);
        return "/product/luckyMoney/list";
    }

    @RequestMapping(value = "/add")
    public String add(HttpServletRequest request, HttpServletResponse res) {
        LuckyMoney luckyMoney = getLuckyMoney(request);
        luckyMoney.setCreateTime(new Date());
        luckyMoneyService.add(luckyMoney);
        return StageHelper.successForward("保存成功","/luckyMoney/list", res);
    }

    private LuckyMoney getLuckyMoney(HttpServletRequest request) {
        long pid = ServletRequestUtils.getLongParameter(request, "product.id", 0);
        int stock = ServletRequestUtils.getIntParameter(request, "stock", 0);
        float money = ServletRequestUtils.getFloatParameter(request, "money", 0.00f);
        String startTimeStr = ServletRequestUtils.getStringParameter(request, "startTime", "");
        String endTimeStr = ServletRequestUtils.getStringParameter(request, "endTime", "");
        int status = ServletRequestUtils.getIntParameter(request, "status", 1);
        LuckyMoney luckyMoney = new LuckyMoney();
        luckyMoney.setCreateTime(new Date());
        luckyMoney.setStock(stock);
        luckyMoney.setStatus((short) status);
        luckyMoney.setMoney(new BigDecimal(money));
        try {
            Date startTime = DateUtils.parseDate(startTimeStr, ProductController.pattern);
            Date endTime = DateUtils.parseDate(endTimeStr, ProductController.pattern);

            luckyMoney.setStartTime(startTime);
            luckyMoney.setEndTime(endTime);
        } catch (ParseException e) {
            logger.error("", e);
        }
        luckyMoney.setPid(pid);
//        luckyMoney.setStatus((short) status);
        return luckyMoney;
    }

    @RequestMapping(value = "/addView")
    public String addView(HttpServletRequest request, HttpServletResponse res) {

        return "/product/luckyMoney/add";
    }

    @RequestMapping(value = "/updateView")
    public String updateView(HttpServletRequest request, HttpServletResponse res) {
        int id = ServletRequestUtils.getIntParameter(request, "id", 0);
        if (id <= 0) {
            return StageHelper.failForward("参数提交错误", res);
        }
        LuckyMoney luckyMoney = luckyMoneyService.getById(id);
        request.setAttribute("bean", luckyMoney);
        return "/product/luckyMoney/update";
    }

    @RequestMapping(value = "/update")
    public String update(HttpServletRequest request, HttpServletResponse res) {
        long id = ServletRequestUtils.getLongParameter(request, "id", 0);
        if (id <= 0) {
            return StageHelper.failForward("参数提交错误", res);
        }
        LuckyMoney luckyMoney = getLuckyMoney(request);
        luckyMoney.setId(id);
        luckyMoneyService.update(luckyMoney);
        return StageHelper.successForward("保存成功","/luckyMoney/list", res);
    }

    @RequestMapping(value = "/del")
    public String del(HttpServletRequest request, HttpServletResponse res) {
        long id = ServletRequestUtils.getLongParameter(request, "id", 0);
        if (id <= 0) {
            return StageHelper.failForward("参数提交错误", res);
        }
        luckyMoneyService.delete(id);
        return StageHelper.successForward("保存成功", res);
    }
}
