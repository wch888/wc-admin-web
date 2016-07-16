package com.wc.web.controller.user;

import com.wc.common.db.PageInfo;
import com.wc.common.db.PagerControl;
import com.wc.user.bean.Suggest;
import com.wc.user.service.SuggestService;
import com.wc.web.until.StageHelper;
import com.wc.web.until.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * 投诉建议
 */
@Controller
@RequestMapping(value = "/suggest")
public class SuggestController {

    @Autowired
    private SuggestService suggestService;

    @RequestMapping(value = "/list")
    public String list(Model model, HttpServletRequest request) {
        PageInfo pi = WebUtils.getPageInfo(request);
        PagerControl<Suggest> pc = suggestService.page(null, pi, null);
        for (Suggest suggest : pc.getEntityList()) {
            suggest.setOverTime(suggest.haveOverTime());
        }
        model.addAttribute("pc", pc);

        return "/suggest/list";
    }


    @RequestMapping(value = "/add")
    public String add(HttpServletRequest request, HttpServletResponse res) {
        Suggest suggest = getSuggest(request);
        suggest.setCreateTime(new Date());
        suggestService.add(suggest);
        return StageHelper.successForward("保存成功","/suggest/list", res);

    }

    private Suggest getSuggest(HttpServletRequest request) {
        long userId = ServletRequestUtils.getLongParameter(request, "userId", 0);
        float amount = ServletRequestUtils.getFloatParameter(request, "amount", 0.00f);
        float total = ServletRequestUtils.getFloatParameter(request, "total", 0.00f);
        Suggest suggest = new Suggest();
//        suggest.setAmount(new BigDecimal(amount));
//        suggest.setUserId(userId);
//        suggest.setTotal(new BigDecimal(total));

        return suggest;
    }

    @RequestMapping(value = "/addView")
    public String addView(HttpServletRequest request, HttpServletResponse res) {

        return "/suggest/add";
    }

    @RequestMapping(value = "/updateView")
    public String updateView(HttpServletRequest request, HttpServletResponse res) {
        int id = ServletRequestUtils.getIntParameter(request, "id", 0);
        if (id <= 0) {
            return StageHelper.failForward("参数提交错误", res);
        }
        Suggest suggest = suggestService.getById(id);
        request.setAttribute("bean", suggest);
        return "/suggest/update";
    }

    @RequestMapping(value = "/update")
    public String update(HttpServletRequest request, HttpServletResponse res) {
        long id = ServletRequestUtils.getLongParameter(request, "id", 0);
        if (id <= 0) {
            return StageHelper.failForward("参数提交错误", res);
        }
        Suggest suggest = getSuggest(request);
        suggest.setId(id);
        suggestService.update(suggest);
        return StageHelper.successForward("保存成功","/suggest/list", res);
    }

    @RequestMapping(value = "/del")
    public String del(HttpServletRequest request, HttpServletResponse res) {
        long id = ServletRequestUtils.getLongParameter(request, "id", 0);
        if (id <= 0) {
            return StageHelper.failForward("参数提交错误", res);
        }
        suggestService.delete(id);
        return StageHelper.successForward("保存成功", res);
    }
}
