package com.wc.web.controller.backstage;

import com.wc.backstage.bean.Role;
import com.wc.backstage.service.RoleService;
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

@Controller
@RequestMapping(value = "/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @RequestMapping(value = "/list")
    public String list(Model model, HttpServletRequest request) {
        PageInfo pi = WebUtils.getPageInfo(request);
        PagerControl pc = roleService.page(new Role(), pi, "order by id desc");
        model.addAttribute("pc", pc);
        return "/backstege/role/list";
    }

    @RequestMapping(value = "/select")
    public String select(Model model, HttpServletRequest request) {
        PageInfo pi = WebUtils.getPageInfo(request);
        PagerControl pc = roleService.page(new Role(), pi, "order by id desc");
        model.addAttribute("pc", pc);
        return "/backstege/role/select";
    }

    @RequestMapping(value = "/add")
    public String add(HttpServletRequest request, HttpServletResponse res) {
        Role role = getRole(request);
        roleService.add(role);
        return StageHelper.successForward("保存成功", res);
    }

    private Role getRole(HttpServletRequest request) {
        String name = ServletRequestUtils.getStringParameter(request, "name", "");
        Role role = new Role();
        role.setName(name);
        return role;
    }

    @RequestMapping(value = "/addView")
    public String addView(HttpServletRequest request, HttpServletResponse res) {

        return "/backstege/role/add";
    }

    @RequestMapping(value = "/updateView")
    public String updateView(HttpServletRequest request, HttpServletResponse res) {
        int id = ServletRequestUtils.getIntParameter(request, "id", 0);
        if (id <= 0) {
            return StageHelper.failForward("参数提交错误", res);
        }
        Role role = roleService.getById(id);
        request.setAttribute("bean", role);
        return "/backstege/role/update";
    }

    @RequestMapping(value = "/update")
    public String update(HttpServletRequest request, HttpServletResponse res) {
        long id = ServletRequestUtils.getLongParameter(request, "id", 0);
        if (id <= 0) {
            return StageHelper.failForward("参数提交错误", res);
        }
        Role role = getRole(request);
        role.setId(id);
        roleService.update(role);
        return StageHelper.successForward("保存成功", res);
    }

    @RequestMapping(value = "/del")
    public String del(HttpServletRequest request, HttpServletResponse res) {
        long id = ServletRequestUtils.getLongParameter(request, "id", 0);
        if (id <= 0) {
            return StageHelper.failForward("参数提交错误", res);
        }
        roleService.delete(id);
        return StageHelper.successForward("保存成功", res);
    }
}
