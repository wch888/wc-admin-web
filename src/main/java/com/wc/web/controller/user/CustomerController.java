package com.wc.web.controller.user;

import com.wc.backstage.bean.Role;
import com.wc.backstage.bean.RoleUserKey;
import com.wc.backstage.service.RoleService;
import com.wc.backstage.service.RoleUserService;
import com.wc.common.db.PageInfo;
import com.wc.common.db.PagerControl;
import com.wc.user.bean.Customer;
import com.wc.user.service.CustomerService;
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
@RequestMapping(value = "/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private RoleUserService roleUserService;

    @RequestMapping(value = "/list")
    public String list(Model model, HttpServletRequest request) {
        PageInfo pi = WebUtils.getPageInfo(request);
        Long userId = ServletRequestUtils.getLongParameter(request, "userId", 0);
        Customer query = new Customer();
        query.setUserId(userId);
        PagerControl pc = customerService.page(query, pi, "order by id desc");
        model.addAttribute("pc", pc);
        return "/member/customer/list";
    }

    @RequestMapping(value = "/select")
    public String select(Model model, HttpServletRequest request) {
        PageInfo pi = WebUtils.getPageInfo(request);
        Long userId = ServletRequestUtils.getLongParameter(request, "userId", 0);
        Customer query = new Customer();
        query.setUserId(userId);
        PagerControl pc = customerService.page(query, pi, "order by id desc");
        model.addAttribute("pc", pc);
        return "/member/customer/select";
    }


    @RequestMapping(value = "/add")
    public String add(HttpServletRequest request, HttpServletResponse res) {
        Customer customer = getCustomer(request);
        customer.setCreateTime(new Date());
        customerService.add(customer);
        return StageHelper.successForward("保存成功", "/customer/list", res);
    }

    private Customer getCustomer(HttpServletRequest request) {
        long id = ServletRequestUtils.getLongParameter(request, "id", 0);
        String mobile = ServletRequestUtils.getStringParameter(request, "mobile", "");
        String password = ServletRequestUtils.getStringParameter(request, "password", null);
        int type = ServletRequestUtils.getIntParameter(request, "type", 0);
        Customer customer = new Customer();
        customer.setId(id);
        customer.setMobile(mobile);
        return customer;
    }

    @RequestMapping(value = "/addView")
    public String addView(HttpServletRequest request, HttpServletResponse res) {

        return "/customer/add";
    }

    @RequestMapping(value = "/updateView")
    public String updateView(HttpServletRequest request, HttpServletResponse res) {
        int id = ServletRequestUtils.getIntParameter(request, "id", 0);
        if (id <= 0) {
            return StageHelper.failForward("参数提交错误", res);
        }
        Customer customer = customerService.getById(id);
        Role role = roleService.getByUserId(id);
        request.setAttribute("role", role);
        request.setAttribute("bean", customer);

        return "/customer/update";
    }

    @RequestMapping(value = "/update")
    public String update(HttpServletRequest request, HttpServletResponse res) {
        long id = ServletRequestUtils.getLongParameter(request, "id", 0);
        if (id <= 0) {
            return StageHelper.failForward("参数提交错误", res);
        }
        long roleId = ServletRequestUtils.getLongParameter(request, "role.id", 0);
        if (roleId > 0) {
            RoleUserKey key = new RoleUserKey();
            key.setUserId(id);
            key.setRoleId(roleId);
            roleUserService.deleteAndadd(key);
        }

        Customer customer = getCustomer(request);
        customer.setId(id);
        customerService.update(customer);
        return StageHelper.successForward("保存成功", "/customer/list", res);
    }

    @RequestMapping(value = "/detail")
    public String detail(HttpServletRequest request, HttpServletResponse res) {
        int id = ServletRequestUtils.getIntParameter(request, "id", 0);
        if (id <= 0) {
            return StageHelper.failForward("参数提交错误", res);
        }
        Customer customer = customerService.getById(id);
        Role role = roleService.getByUserId(id);

        request.setAttribute("role", role);
        request.setAttribute("bean", customer);

        return "/customer/detail";
    }

    @RequestMapping(value = "/verify")
    public void verify(HttpServletRequest request, HttpServletResponse res) {
        long id = ServletRequestUtils.getLongParameter(request, "id", 0);
        if (id <= 0) {
            StageHelper.failForward("参数提交错误", res);
            return;
        }
        Customer customer = new Customer();
        customer.setId(id);
        customerService.update(customer);
        StageHelper.successForward("保存成功", res);
        return;
    }

    @RequestMapping(value = "/del")
    public String del(HttpServletRequest request, HttpServletResponse res) {
        long id = ServletRequestUtils.getLongParameter(request, "id", 0);
        if (id <= 0) {
            return StageHelper.failForward("参数提交错误", res);
        }
        customerService.delete(id);
        return StageHelper.successForward("保存成功", res);
    }
}
