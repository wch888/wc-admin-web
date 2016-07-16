package com.wc.web.controller.product;

import com.wc.common.db.PageInfo;
import com.wc.common.db.PagerControl;
import com.wc.product.bean.Bespeak;
import com.wc.product.bean.BespeakProduct;
import com.wc.product.service.BespeakService;
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

@Controller
@RequestMapping(value = "/bespeak")
public class ProductBespeakController {

    @Autowired
    private BespeakService bespeakService;

    @RequestMapping(value = "/list")
    public String list(Model model, HttpServletRequest request) {
        PageInfo pi = WebUtils.getPageInfo(request);
        Long userId = ServletRequestUtils.getLongParameter(request, "userId", 0);
        BespeakProduct query = new BespeakProduct();
        if (userId > 0) {
            query.setAgentId(userId);
        }

        PagerControl pc = bespeakService.pageWithProduct(query, pi, "order by id desc");
        model.addAttribute("pc", pc);
        return "/product/bespeak/list";
    }

    @RequestMapping(value = "/select")
    public String select(Model model, HttpServletRequest request) {
        PageInfo pi = WebUtils.getPageInfo(request);
        Long userId = ServletRequestUtils.getLongParameter(request, "userId", 0);
        BespeakProduct query = new BespeakProduct();
        PagerControl pc = bespeakService.pageWithProduct(query, pi, "order by id desc");
        model.addAttribute("pc", pc);
        return "/product/bespeak/select";
    }

    @RequestMapping(value = "/add")
    public String add(HttpServletRequest request, HttpServletResponse res) {
        Bespeak nav = getBespeak(request);
        nav.setCreateTime(new Date());
        bespeakService.add(nav);
        return StageHelper.successForward("保存成功","/bespeak/list", res);
    }

    private Bespeak getBespeak(HttpServletRequest request) {
        String name = ServletRequestUtils.getStringParameter(request, "name", "");
        String mobile = ServletRequestUtils.getStringParameter(request, "mobile", "");
        Bespeak bespeak = new Bespeak();
        bespeak.setName(name);
        bespeak.setMobile(mobile);
        return bespeak;
    }

    @RequestMapping(value = "/addView")
    public String addView(HttpServletRequest request, HttpServletResponse res) {

        return "/product/bespeak/add";
    }

    @RequestMapping(value = "/updateView")
    public String updateView(HttpServletRequest request, HttpServletResponse res) {
        int id = ServletRequestUtils.getIntParameter(request, "id", 0);
        if (id <= 0) {
            return StageHelper.failForward("参数提交错误", res);
        }
        Bespeak nav = bespeakService.getById(id);
        request.setAttribute("bean", nav);
        return "/product/bespeak/update";
    }

    @RequestMapping(value = "/update")
    public String update(HttpServletRequest request, HttpServletResponse res) {
        long id = ServletRequestUtils.getLongParameter(request, "id", 0);
        if (id <= 0) {
            return StageHelper.failForward("参数提交错误", res);
        }
        Bespeak bespeak = getBespeak(request);
        bespeak.setId(id);
        bespeakService.update(bespeak);
        return StageHelper.successForward("保存成功","/bespeak/list", res);
    }

    @RequestMapping(value = "/del")
    public String del(HttpServletRequest request, HttpServletResponse res) {
        long id = ServletRequestUtils.getLongParameter(request, "id", 0);
        if (id <= 0) {
            return StageHelper.failForward("参数提交错误", res);
        }
        bespeakService.delete(id);
        return StageHelper.successForward("保存成功", res);
    }
}
