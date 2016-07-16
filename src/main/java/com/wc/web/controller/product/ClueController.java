package com.wc.web.controller.product;

import com.wc.common.db.PageInfo;
import com.wc.common.db.PagerControl;
import com.wc.product.bean.Clue;
import com.wc.product.service.ClueService;
import com.wc.web.until.StageHelper;
import com.wc.web.until.WebUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Controller
@RequestMapping(value = "/clue")
public class ClueController {

    @Autowired
    private ClueService clueService;

    @RequestMapping(value = "/list")
    public String list(Model model, HttpServletRequest request) {
        PageInfo pi = WebUtils.getPageInfo(request);
        String name = ServletRequestUtils.getStringParameter(request, "name", "");
        String mobile = ServletRequestUtils.getStringParameter(request, "mobile", "");
        int status = ServletRequestUtils.getIntParameter(request, "status",0);
        Clue query = new Clue();
        if(StringUtils.isNotBlank(name)){
            query.setName(name);
        }
        if(StringUtils.isNotBlank(mobile)){
            query.setMobile(mobile);
        }
        query.setStatus((short) status);

        PagerControl<Clue> pc = clueService.pageMember(query, pi);
        if(0==status){
            for ( Clue clue : pc.getEntityList()) {
                clue.setOverTime(clue.haveOverTime());
            }
        }
        model.addAttribute("pc", pc);
        model.addAttribute("name",name);
        model.addAttribute("mobile",mobile);
        model.addAttribute("status",status);
        return "/product/clue/list";
    }

    @RequestMapping(value = "/add")
    public String add(HttpServletRequest request, HttpServletResponse res) {
        Clue clue = getClue(request);
        clue.setCreateTime(new Date());
        clueService.add(clue);
        return StageHelper.successForward("保存成功", "/clue/list",res);
    }

    private Clue getClue(HttpServletRequest request) {
        String title = ServletRequestUtils.getStringParameter(request, "title", "");
        String imgUrl = ServletRequestUtils.getStringParameter(request, "imgUrl", "");
        int status = ServletRequestUtils.getIntParameter(request, "status", 1);
        String type = ServletRequestUtils.getStringParameter(request, "type", "");
        String param = ServletRequestUtils.getStringParameter(request, "param", "");
        String tip = ServletRequestUtils.getStringParameter(request, "tip", "");
        Clue clue = new Clue();
        clue.setTip(tip);
        clue.setStatus((short) status);
        return clue;
    }

    @RequestMapping(value = "/addView")
    public String addView(HttpServletRequest request, HttpServletResponse res) {

        return "/product/clue/add";
    }

    @RequestMapping(value = "/updateView")
    public String updateView(HttpServletRequest request, HttpServletResponse res) {
        int id = ServletRequestUtils.getIntParameter(request, "id", 0);
        if (id <= 0) {
            return StageHelper.failForward("参数提交错误", res);
        }
        Clue clue = clueService.getById(id);
        request.setAttribute("bean", clue);
        return "/product/clue/update";
    }

    @RequestMapping(value = "/update")
    public String update(HttpServletRequest request, HttpServletResponse res) {
        long id = ServletRequestUtils.getLongParameter(request, "id", 0);
        if (id <= 0) {
            return StageHelper.failForward("参数提交错误", res);
        }
        Clue clue = getClue(request);
        clue.setId(id);
        clueService.update(clue);
        return StageHelper.successForward("保存成功","/clue/list", res);
    }

    @RequestMapping(value = "/del")
    public String del(HttpServletRequest request, HttpServletResponse res) {
        long id = ServletRequestUtils.getLongParameter(request, "id", 0);
        if (id <= 0) {
            return StageHelper.failForward("参数提交错误", res);
        }
        clueService.delete(id);
        return StageHelper.successForward("保存成功", res);
    }
}
