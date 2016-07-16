package com.wc.web.controller.user;

import com.wc.common.db.PageInfo;
import com.wc.common.db.PagerControl;
import com.wc.user.bean.Member;
import com.wc.user.bean.MemberDetail;
import com.wc.user.service.CustomerService;
import com.wc.user.service.MemberService;
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
@RequestMapping(value = "/agent")
public class MemberAgentController {

    @Autowired
    private MemberService memberService;
    @Autowired
    private CustomerService customerService;

    @RequestMapping(value = "/list")
    public String list(Model model, HttpServletRequest request) {
        PageInfo pi = WebUtils.getPageInfo(request);
        String nickname = request.getParameter("nickname");
        String mobile = request.getParameter("mobile");
        Member query = new Member();
        query.setBrokerType(Member.OFFICAL_BROKER);
        query.setMobileLike(mobile);
        query.setNickname(nickname);
        PagerControl pc = memberService.page(query, pi, "order by id desc");
        model.addAttribute("pc", pc);
        return "/member/agent/list";
    }

    @RequestMapping(value = "/multiSelect")
    public String multiSelect(Model model, HttpServletRequest request) {
        PageInfo pi = WebUtils.getPageInfo(request);
        int type = ServletRequestUtils.getIntParameter(request, "type", 0);
        int broker_type = ServletRequestUtils.getIntParameter(request, "brokerType", 0);
        String nickname = request.getParameter("nickname");
        String mobile = request.getParameter("mobile");
        Member query = new Member();
        if (type > 0) {
            query.setType((short) type);
        }
        if (broker_type > 0) {
            query.setBrokerType((short) broker_type);
        }
        query.setMobileLike(mobile);
        query.setNickname(nickname);
        PagerControl pc = memberService.page(query, pi, "order by id desc");
        model.addAttribute("pc", pc);
        return "/member/agent/multi_select";
    }

    @RequestMapping(value = "/add")
    public String add(HttpServletRequest request, HttpServletResponse res) {
        Member member = getMember(request);
        member.setCreateTime(new Date());
        memberService.add(member);
        return StageHelper.successForward("保存成功", "/member/list", res);
    }

    private Member getMember(HttpServletRequest request) {
        long id = ServletRequestUtils.getLongParameter(request, "id", 0);
        String nickname = ServletRequestUtils.getStringParameter(request, "nickname", "");
        Member member = new Member();
        member.setId(id);
        member.setNickname(nickname);
        return member;
    }

    @RequestMapping(value = "/addView")
    public String addView(HttpServletRequest request, HttpServletResponse res) {

        return "/member/agent/add";
    }

    @RequestMapping(value = "/updateView")
    public String updateView(HttpServletRequest request, HttpServletResponse res) {
        Long id = ServletRequestUtils.getLongParameter(request, "id", 0);
        if (id <= 0) {
            return StageHelper.failForward("参数提交错误", res);
        }
        Member member = memberService.getById(id);
        request.setAttribute("bean", member);

        return "/member/agent/update";
    }

    @RequestMapping(value = "/update")
    public String update(HttpServletRequest request, HttpServletResponse res) {
        long id = ServletRequestUtils.getLongParameter(request, "id", 0);
        if (id <= 0) {
            return StageHelper.failForward("参数提交错误", res);
        }

        Member member = getMember(request);
        member.setId(id);
        memberService.update(member);
        return StageHelper.successForward("保存成功", "/member/list", res);
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

        request.setAttribute("bean", member);
        request.setAttribute("detail", detail);
        request.setAttribute("imgs", imgs);

        return "/member/agent/detail";
    }

}
