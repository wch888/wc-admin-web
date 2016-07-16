package com.wc.web.controller;

import com.wc.backstage.bean.RoleMenuKey;
import com.wc.backstage.service.RoleMenuService;
import com.wc.product.bean.Bespeak;
import com.wc.product.bean.Clue;
import com.wc.product.service.BespeakService;
import com.wc.product.service.ClueService;
import com.wc.user.bean.Member;
import com.wc.user.bean.Repair;
import com.wc.user.service.MemberService;
import com.wc.user.service.RepairService;
import com.wc.util.JsonResult;
import com.wc.util.MD5Util;
import com.wc.web.until.RegLoginUtil;
import com.wc.web.until.StageHelper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class IndexController {

    public static final String LOGIN = "/login";
    public static final String INDEX = "/index";

    @Autowired
    private MemberService memberService;

    @Autowired
    private ClueService clueService;
    @Autowired
    private RepairService repairService;
    @Autowired
    private BespeakService bespeakService;
    @Autowired
    private RoleMenuService roleMenuService;

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index(Model model, HttpServletRequest request) {
        long uid = RegLoginUtil.getSessionUid(request);

        if (uid <= 0) {
            return LOGIN;
        }
        List<RoleMenuKey> list = roleMenuService.listByUserId(uid);
        StringBuilder sb = new StringBuilder();
        sb.append(",");
        for (RoleMenuKey roleMenuKey : list) {
            sb.append(roleMenuKey.getMenuId()).append(",");
        }
        Member member = memberService.getById(uid);
        model.addAttribute("menuStr", sb.toString());
        model.addAttribute("member", member);
        return "/index";
    }

    @RequestMapping(value = "/content", method = RequestMethod.GET)
    public String content(Model model, HttpServletRequest request) {
        long uid = RegLoginUtil.getSessionUid(request);
        if (uid <= 0) {
            return LOGIN;
        }
        Member query = new Member();
        query.setType((short) 1);
        int verifyCount = memberService.count(query);
        Clue clue = new Clue();
        clue.setStatus(Clue.CREATE);
        int clueCount = clueService.count(clue);
        Bespeak bespeak = new Bespeak();
        bespeak.setStatus(Bespeak.CREATE);
        int bespeakCount = bespeakService.count(bespeak);
        Repair repair = new Repair();
        repair.setStatus(Repair.WAITTING);
        int repairCount = repairService.count(repair);
        model.addAttribute("verifyCount", verifyCount);
        model.addAttribute("clueCount", clueCount);
        model.addAttribute("bespeakCount", bespeakCount);
        model.addAttribute("repairCount", repairCount);
        return "/content";
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home() {

        return "/login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(Model model, HttpServletRequest req, HttpServletResponse res, @RequestParam String username, @RequestParam String password) {


        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {

            model.addAttribute("result", new JsonResult(false, "用户名或者密码不能为空"));
            return LOGIN;
        }
        Member member = new Member();
        member.setMobile(username);
        String passmd5 = MD5Util.MD5(password + username);
        member.setPassword(passmd5);
        Member loginMember = memberService.get(member);
        if (null == loginMember) {
            model.addAttribute("result", new JsonResult(false, "用户名或者密码错误"));
            return LOGIN;
        }
        //写入cookie
        HttpSession session = req.getSession();
        session.setAttribute(RegLoginUtil.SESSION_UID, loginMember.getId());
        RegLoginUtil.writeJsessionIdCookie(res, session.getId());
        return "redirect:/index";

    }

    @RequestMapping(value = "/loginAjaxView")
    public String loginAjaxView(HttpServletRequest req, HttpServletResponse res) {

        return "/loginAjax";

    }

    @RequestMapping(value = "/loginAjax")
    public String loginAjax(HttpServletRequest req, HttpServletResponse res,
                            @RequestParam String username, @RequestParam String password) {

//    return StageHelper.writeDwzSignal("200", "登陆成功", "", StageHelper.DWZ_CLOSE_CURRENT, "", response);
//    StageHelper.writeDwzSignal("300", "账号密码有误", "", StageHelper.DWZ_FORWARD, "/loginAjax", response);
        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            return StageHelper.writeDwzSignal("300", "账号密码有误", "", StageHelper.DWZ_FORWARD, "/loginAjaxView", res);
        }
        Member member = new Member();
        member.setMobile(username);
        String passmd5 = MD5Util.MD5(password + username);
        member.setPassword(passmd5);
        Member loginMember = memberService.get(member);
        if (null == loginMember) {
            return StageHelper.writeDwzSignal("300", "账号密码有误", "", StageHelper.DWZ_FORWARD, "/loginAjaxView", res);
        }
        //写入cookie
        HttpSession session = req.getSession();
        session.setAttribute(RegLoginUtil.SESSION_UID, loginMember.getId());
        RegLoginUtil.writeJsessionIdCookie(res, session.getId());
        return StageHelper.writeDwzSignal("200", "登陆成功", "", StageHelper.DWZ_CLOSE_CURRENT, "", res);
    }


    @RequestMapping(value = "/loginout")
    public String loginout(HttpServletRequest req, HttpServletResponse res) {

        RegLoginUtil.deleteSession(req, res);
        RegLoginUtil.deleteCookie(req, res);
        return "redirect:/";

    }
}
