package com.wc.web.controller;

import com.wc.base.service.BasisCityService;
import com.wc.common.db.PageInfo;
import com.wc.common.db.PagerControl;
import com.wc.user.bean.Community;
import com.wc.user.service.CommunityService;
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
@RequestMapping(value = "/community")
public class CommunityController {

    @Autowired
    private CommunityService communityService;
    @Autowired
    private BasisCityService basisCityService;

    @RequestMapping(value = "/list")
    public String list(Model model, HttpServletRequest request) {
        PageInfo pi = WebUtils.getPageInfo(request);
        PagerControl pc = communityService.page(null, pi, null);
        model.addAttribute("pc", pc);
        return "/community/list";
    }


    @RequestMapping(value = "/add")
    public String add(HttpServletRequest request, HttpServletResponse res) {
        Community community = getCommunity(request);
        community.setCreateTime(new Date());
        communityService.add(community);
        return StageHelper.successForward("保存成功", "/community/list",res);

    }

    private Community getCommunity(HttpServletRequest request) {
        String title = ServletRequestUtils.getStringParameter(request, "title", "");
        String phone = ServletRequestUtils.getStringParameter(request, "phone", "");
        long province = ServletRequestUtils.getLongParameter(request, "province", 0);
        long cityId = ServletRequestUtils.getLongParameter(request, "city", 0);
        long area = ServletRequestUtils.getLongParameter(request, "area", 0);
        Community community = new Community();
        community.setCityId(cityId);
        community.setName(title);
        community.setProvinceId(province);
        community.setCityId(cityId);
        community.setAreaId(area);
        community.setPhone(phone);
        return community;
    }

    @RequestMapping(value = "/addView")
    public String addView(HttpServletRequest request, HttpServletResponse res) {
        return "/community/add";
    }

    @RequestMapping(value = "/updateView")
    public String updateView(HttpServletRequest request, HttpServletResponse res) {
        int id = ServletRequestUtils.getIntParameter(request, "id", 0);
        if (id <= 0) {
            return StageHelper.failForward("参数提交错误", res);
        }
        Community community = communityService.getById(id);
        request.setAttribute("bean", community);
        return "/community/update";
    }

    @RequestMapping(value = "/update")
    public String update(HttpServletRequest request, HttpServletResponse res) {
        long id = ServletRequestUtils.getLongParameter(request, "id", 0);
        if (id <= 0) {
            return StageHelper.failForward("参数提交错误", res);
        }
        Community community = getCommunity(request);
        community.setId(id);
        communityService.update(community);
        return StageHelper.successForward("保存成功","/community/list", res);
    }

    @RequestMapping(value = "/del")
    public String del(HttpServletRequest request, HttpServletResponse res) {
        long id = ServletRequestUtils.getLongParameter(request, "id", 0);
        if (id <= 0) {
            return StageHelper.failForward("参数提交错误", res);
        }
        communityService.delete(id);
        return StageHelper.successForward("保存成功", res);
    }
}
