package com.wc.web.controller;

import com.wc.base.bean.Introduction;
import com.wc.base.service.IntroductionService;
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
@RequestMapping(value = "/introduction")
public class IntroductionController {

    @Autowired
    private IntroductionService introductionService;

    @RequestMapping(value = "/list")
    public String list(Model model, HttpServletRequest request) {
//        return "redirect:/";
        System.out.println("index");
        PageInfo pi = WebUtils.getPageInfo(request);
        PagerControl pc = introductionService.page(null, pi, null);
        model.addAttribute("pc", pc);
        return "/introduction/list";
    }


    @RequestMapping(value = "/add")
    public String add(HttpServletRequest request, HttpServletResponse res) {
        Introduction introduction = getIntroduction(request);
        introduction.setCreateTime(new Date());
        introductionService.add(introduction);

        return StageHelper.successForward("保存成功","/introduction/list", res);

    }

    private Introduction getIntroduction(HttpServletRequest request) {
        String title = ServletRequestUtils.getStringParameter(request, "title", "");
        String thumb = ServletRequestUtils.getStringParameter(request, "thumb", "");
        String content = ServletRequestUtils.getStringParameter(request, "content", "");
        String desc = ServletRequestUtils.getStringParameter(request, "desc", "");
        Introduction introduction = new Introduction();
        introduction.setDescription(desc);
        introduction.setTitle(title);
        introduction.setThumb(thumb);
        introduction.setContent(content);
        introduction.setUpdateTime(new Date());
        return introduction;
    }

    @RequestMapping(value = "/addView")
    public String addView(HttpServletRequest request, HttpServletResponse res) {

        return "/introduction/add";
    }

    @RequestMapping(value = "/updateView")
    public String updateView(HttpServletRequest request, HttpServletResponse res) {
        int id = ServletRequestUtils.getIntParameter(request, "id", 0);
        if (id <= 0) {
            return StageHelper.failForward("参数提交错误", res);
        }
        Introduction introduction = introductionService.getById(id);
        request.setAttribute("bean", introduction);
        return "/introduction/update";
    }

    @RequestMapping(value = "/update")
    public String update(HttpServletRequest request, HttpServletResponse res) {
        long id = ServletRequestUtils.getLongParameter(request, "id", 0);
        if (id <= 0) {
            return StageHelper.failForward("参数提交错误", res);
        }
        Introduction introduction = getIntroduction(request);
        introduction.setId(id);
        introductionService.update(introduction);
        return StageHelper.successForward("保存成功","/introduction/list", res);
    }

    @RequestMapping(value = "/del")
    public String del(HttpServletRequest request, HttpServletResponse res) {
        long id = ServletRequestUtils.getLongParameter(request, "id", 0);
        if (id <= 0) {
            return StageHelper.failForward("参数提交错误", res);
        }
        if (id == 1) {
            return StageHelper.failForward("企业简介不能删除", res);
        }
        introductionService.delete(id);
        return StageHelper.successForward("保存成功", res);
    }
}
