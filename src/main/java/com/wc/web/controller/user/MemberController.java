package com.wc.web.controller.user;

import com.jpush.JPushService;
import com.wc.backstage.bean.Role;
import com.wc.backstage.bean.RoleUserKey;
import com.wc.backstage.service.RoleService;
import com.wc.backstage.service.RoleUserService;
import com.wc.common.db.PageInfo;
import com.wc.common.db.PagerControl;
import com.wc.user.bean.Member;
import com.wc.user.bean.MemberDetail;
import com.wc.user.service.MemberService;
import com.wc.util.MD5Util;
import com.wc.web.until.StageHelper;
import com.wc.web.until.WebUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "/member")
public class MemberController {

    @Autowired
    private MemberService memberService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private RoleUserService roleUserService;
    @Autowired
    private IntegrationManger integrationManger;

    @RequestMapping(value = "/list")
    public String list(Model model, HttpServletRequest request) {
        PageInfo pi = WebUtils.getPageInfo(request);
        int type = ServletRequestUtils.getIntParameter(request, "type", 0);
        int broker_type = ServletRequestUtils.getIntParameter(request, "brokerType", 0);

        Member query = new Member();
        if (type > 0) {
            query.setType((short) type);
        }
        if (broker_type > 0) {
            query.setBrokerType((short) broker_type);
        }

        PagerControl pc = memberService.page(query, pi, "order by id desc");
        model.addAttribute("pc", pc);
        return "/member/list";
    }

    @RequestMapping(value = "/verifyList")
    public String verifyList(Model model, HttpServletRequest request) {
        PageInfo pi = WebUtils.getPageInfo(request);
        Member query = new Member();
        query.setType(Member.VERIFY);
        PagerControl pc = memberService.page(query, pi, "order by id desc");
        model.addAttribute("pc", pc);
        return "/member/verify_list";
    }

    @RequestMapping(value = "/agent")
    public String agent(Model model, HttpServletRequest request) {
        PageInfo pi = WebUtils.getPageInfo(request);
        Member query = new Member();
        query.setType(Member.OFFICAL_BROKER);
        PagerControl pc = memberService.page(query, pi, "order by id desc");
        model.addAttribute("pc", pc);
        return "/member/agent/list";
    }

    @RequestMapping(value = "/select")
    public String select(Model model, HttpServletRequest request,
                         @RequestParam(required = false) String mobile,
                         @RequestParam(required = false) Short type,
                         @RequestParam(required = false) Short brokerType) {
        PageInfo pi = WebUtils.getPageInfo(request);
        Member query = new Member();
        if(null!=type&&type!=-9){
            query.setType( type);
        }
        if(null!=brokerType&&brokerType!=-9){
            query.setBrokerType( brokerType);
        }
        if(StringUtils.isNotBlank(mobile)){
            query.setMobile(mobile);
        }


        PagerControl pc = memberService.page(query, pi, "order by id desc");
        model.addAttribute("pc", pc);
        model.addAttribute("type",type);
        model.addAttribute("brokerType",brokerType);
        model.addAttribute("mobile",mobile);
        return "/member/select";
    }

    @RequestMapping(value = "/multiSelect")
    public String multiSelect(Model model, HttpServletRequest request,
                              @RequestParam(required = false) String mobile,
                              @RequestParam(required = false) Short type,
                              @RequestParam(required = false) Short brokerType) {
        PageInfo pi = WebUtils.getPageInfo(request);
        String nickname = request.getParameter("nickname");
        Member query = new Member();
        if(null!=type&&type!=-9){
            query.setType( type);
        }
        if(null!=brokerType&&brokerType!=-9){
            query.setBrokerType( brokerType);
        }
        if(StringUtils.isNotBlank(mobile)){
            query.setMobile(mobile);
        }
        query.setNickname(nickname);
        PagerControl pc = memberService.page(query, pi, "order by id desc");
        model.addAttribute("pc", pc);
        model.addAttribute("type",type);
        model.addAttribute("brokerType",brokerType);
        model.addAttribute("mobile",mobile);
        return "/member/multi_select";
    }


    @RequestMapping(value = "/add")
    public String add(HttpServletRequest request, HttpServletResponse res) {
        Member member = getMember(request);
        member.setCreateTime(new Date());
        memberService.add(member);
        return StageHelper.successForward("保存成功","/member/list", res);
    }

    private Member getMember(HttpServletRequest request) {
        long id = ServletRequestUtils.getLongParameter(request, "id", 0);
        String mobile = ServletRequestUtils.getStringParameter(request, "mobile", "");
        String password = ServletRequestUtils.getStringParameter(request, "password", null);
        int type = ServletRequestUtils.getIntParameter(request, "type", 0);
        int brokerType = ServletRequestUtils.getIntParameter(request, "brokerType", 0);
        Member member = new Member();
        member.setId(id);
        member.setMobile(mobile);
        member.setType((short) type);
        member.setBrokerType((short) brokerType);
        if (StringUtils.isNotBlank(password)) {
            String passmd5 = MD5Util.MD5(password + mobile);
            member.setPassword(passmd5);
        }
        return member;
    }

    @RequestMapping(value = "/addView")
    public String addView(HttpServletRequest request, HttpServletResponse res) {

        return "/member/add";
    }

    @RequestMapping(value = "/updateView")
    public String updateView(HttpServletRequest request, HttpServletResponse res) {
        int id = ServletRequestUtils.getIntParameter(request, "id", 0);
        if (id <= 0) {
            return StageHelper.failForward("参数提交错误", res);
        }
        Member member = memberService.getById(id);
        Role role = roleService.getByUserId(id);
        request.setAttribute("role", role);
        request.setAttribute("bean", member);

        return "/member/update";
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

        Member member = getMember(request);
        member.setId(id);
        memberService.update(member);
        return StageHelper.successForward("保存成功", "/member/list",res);
    }

    @RequestMapping(value = "/detail")
    public String detail(HttpServletRequest request, HttpServletResponse res) {
        int id = ServletRequestUtils.getIntParameter(request, "id", 0);
        if (id <= 0) {
            return StageHelper.failForward("参数提交错误", res);
        }
        Member member = memberService.getById(id);
        MemberDetail detail = memberService.getDetailById(id);
        String imgStr = detail.getImg();
        String[] imgs = StringUtils.split(imgStr, ",");
        Role role = roleService.getByUserId(id);

        request.setAttribute("role", role);
        request.setAttribute("bean", member);
        request.setAttribute("detail", detail);
        request.setAttribute("imgs", imgs);

        return "/member/detail";
    }

    @RequestMapping(value = "/verify")
    public void verify(HttpServletRequest request, HttpServletResponse res) {
        long id = ServletRequestUtils.getLongParameter(request, "id", 0);
        if (id <= 0) {
            StageHelper.failForward("参数提交错误", res);
            return;
        }
        Member member = memberService.getById(id);
        if (Member.HOUSEHOLD==member.getType().shortValue()) {
            StageHelper.failForward("你已注册成为业主", res);
            return;
        }
        Member update = new Member();
        update.setId(id);
        //审核通过
        update.setType(Member.HOUSEHOLD);
        memberService.update(update);
        //送积分
        integrationManger.verify(id);

        StageHelper.successForward("保存成功", res);
        Map json = new HashMap<String, Object>();
        json.put("type", "member");
        json.put("title", "你的业主申请已经审核通过");
        json.put("id", id + "");
        JPushService.getInstance().sendNotification("你的业主申请已经审核通过", json);

        return;
    }

    @RequestMapping(value = "/del")
    public String del(HttpServletRequest request, HttpServletResponse res) {
        long id = ServletRequestUtils.getLongParameter(request, "id", 0);
        if (id <= 0) {
            return StageHelper.failForward("参数提交错误", res);
        }
        memberService.delete(id);
        return StageHelper.successForward("保存成功", res);
    }
}
