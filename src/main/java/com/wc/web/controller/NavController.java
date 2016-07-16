package com.wc.web.controller;

import com.wc.base.bean.Nav;
import com.wc.base.service.NavService;
import com.wc.common.db.PageInfo;
import com.wc.common.db.PagerControl;
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
@RequestMapping(value = "/nav")
public class NavController {

    @Autowired
    private NavService navService;

    @RequestMapping(value = "/list")
    public String list(Model model, HttpServletRequest request) {
        PageInfo pi = WebUtils.getPageInfo(request);
        PagerControl pc = navService.page(new Nav(), pi, "order by id desc");
        model.addAttribute("pc", pc);
        return "/index/nav/list";
    }

    @RequestMapping(value = "/add")
    public String add(HttpServletRequest request, HttpServletResponse res) {
        Nav nav = getNav(request);
        nav.setCreateTime(new Date());
        navService.add(nav);
        return StageHelper.successForward("保存成功","/nav/list", res);
    }

    private Nav getNav(HttpServletRequest request) {
        String title = ServletRequestUtils.getStringParameter(request, "title", "");
        String imgUrl = ServletRequestUtils.getStringParameter(request, "imgUrl", "");
        int status = ServletRequestUtils.getIntParameter(request, "status", 1);
        String type = ServletRequestUtils.getStringParameter(request, "type", "");
        String param = ServletRequestUtils.getStringParameter(request, "param", "");
        Nav nav = new Nav();
        nav.setTitle(title);
        nav.setType(type);
        nav.setImgUrl(imgUrl);
        nav.setParam(param);
        nav.setStatus((short) status);
        return nav;
    }

    @RequestMapping(value = "/addView")
    public String addView(HttpServletRequest request, HttpServletResponse res) {

        return "/index/nav/add";
    }

    @RequestMapping(value = "/updateView")
    public String updateView(HttpServletRequest request, HttpServletResponse res) {
        int id = ServletRequestUtils.getIntParameter(request, "id", 0);
        if (id <= 0) {
            return StageHelper.failForward("参数提交错误", res);
        }
        Nav nav = navService.getById(id);
        request.setAttribute("bean", nav);
        return "/index/nav/update";
    }

    @RequestMapping(value = "/update")
    public String update(HttpServletRequest request, HttpServletResponse res) {
        long id = ServletRequestUtils.getLongParameter(request, "id", 0);
        if (id <= 0) {
            return StageHelper.failForward("参数提交错误", res);
        }
        Nav nav = getNav(request);
        nav.setId(id);
        navService.update(nav);
        return StageHelper.successForward("保存成功","/nav/list", res);
    }

    @RequestMapping(value = "/del")
    public String del(HttpServletRequest request, HttpServletResponse res) {
        long id = ServletRequestUtils.getLongParameter(request, "id", 0);
        if (id <= 0) {
            return StageHelper.failForward("参数提交错误", res);
        }
        navService.delete(id);
        return StageHelper.successForward("保存成功", res);
    }
}
