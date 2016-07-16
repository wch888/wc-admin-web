package com.wc.web.controller.backstage;

import com.wc.backstage.bean.Role;
import com.wc.backstage.bean.RoleMenuKey;
import com.wc.backstage.service.RoleMenuService;
import com.wc.backstage.service.RoleService;
import com.wc.backstage.service.RoleUserService;
import com.wc.web.until.StageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping(value = "/menu")
public class MenuController {

    @Autowired
    private RoleService roleService;
    @Autowired
    private RoleMenuService roleMenuService;
    @Autowired
    private RoleUserService roleUserService;

    @RequestMapping(value = "/list")
    public String list(Model model, HttpServletRequest request) {
        long id = ServletRequestUtils.getLongParameter(request, "id", 1);
        Role role = roleService.getById(id);
        List<RoleMenuKey> list = roleMenuService.list(id);
        StringBuilder sb = new StringBuilder();
        sb.append(",");
        for (RoleMenuKey roleMenuKey : list) {
            sb.append(roleMenuKey.getMenuId()).append(",");
        }
        model.addAttribute("role", role);
        model.addAttribute("menuStr", sb.toString());
        return "/backstege/menu/list";
    }

    @RequestMapping(value = "/add")
    public String add(HttpServletRequest request, HttpServletResponse res) {
        long roleId = ServletRequestUtils.getLongParameter(request, "roleId", 0L);
        long[] menuIds = ServletRequestUtils.getLongParameters(request, "menuId");
        RoleMenuKey delete = new RoleMenuKey();
        delete.setRoleId(roleId);
        roleMenuService.delete(delete);
        for (long menuId : menuIds) {
            RoleMenuKey key = new RoleMenuKey();
            key.setRoleId(roleId);
            key.setMenuId(menuId);
            roleMenuService.add(key);
        }

        return StageHelper.successForward("保存成功", res);
    }


}
