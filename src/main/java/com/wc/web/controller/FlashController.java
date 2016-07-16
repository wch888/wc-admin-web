package com.wc.web.controller;

import com.wc.base.bean.Flash;
import com.wc.base.service.FlashService;
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
@RequestMapping(value = "/flash")
public class FlashController {

    @Autowired
    private FlashService flashService;

    @RequestMapping(value = "/list")
    public String list(Model model, HttpServletRequest request) {
        PageInfo pi = WebUtils.getPageInfo(request);
        PagerControl pc = flashService.page(new Flash(), pi, "order by id desc");
        model.addAttribute("pc", pc);
        return "/index/flash/list";
    }

    @RequestMapping(value = "/add")
    public String add(HttpServletRequest request, HttpServletResponse res) {
        Flash flash = getFlash(request);
        flash.setCreateTime(new Date());
        flashService.add(flash);
        return StageHelper.successForward("保存成功","/flash/list", res);
    }

    private Flash getFlash(HttpServletRequest request) {
        String title = ServletRequestUtils.getStringParameter(request, "title", "");
        String imgUrl = ServletRequestUtils.getStringParameter(request, "imgUrl", "");
        int status = ServletRequestUtils.getIntParameter(request, "status", 1);
        String type = ServletRequestUtils.getStringParameter(request, "type", "");
        String param = ServletRequestUtils.getStringParameter(request, "param", "");
        Flash flash = new Flash();
        flash.setTitle(title);
        flash.setType(type);
        flash.setImgUrl(imgUrl);
        flash.setParam(param);
        flash.setStatus((short) status);
        return flash;
    }

    @RequestMapping(value = "/addView")
    public String addView(HttpServletRequest request, HttpServletResponse res) {

        return "/index/flash/add";
    }

    @RequestMapping(value = "/updateView")
    public String updateView(HttpServletRequest request, HttpServletResponse res) {
        int id = ServletRequestUtils.getIntParameter(request, "id", 0);
        if (id <= 0) {
            return StageHelper.failForward("参数提交错误", res);
        }
        Flash flash = flashService.getById(id);
        request.setAttribute("bean", flash);
        return "/index/flash/update";
    }

    @RequestMapping(value = "/update")
    public String update(HttpServletRequest request, HttpServletResponse res) {
        long id = ServletRequestUtils.getLongParameter(request, "id", 0);
        if (id <= 0) {
            return StageHelper.failForward("参数提交错误", res);
        }
        Flash flash = getFlash(request);
        flash.setId(id);
        flashService.update(flash);
        return StageHelper.successForward("保存成功", "/flash/list",res);
    }

    @RequestMapping(value = "/del")
    public String del(HttpServletRequest request, HttpServletResponse res) {
        long id = ServletRequestUtils.getLongParameter(request, "id", 0);
        if (id <= 0) {
            return StageHelper.failForward("参数提交错误", res);
        }
        flashService.delete(id);
        return StageHelper.successForward("保存成功", res);
    }
}
