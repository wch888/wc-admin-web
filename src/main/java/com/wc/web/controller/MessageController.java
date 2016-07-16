package com.wc.web.controller;

import com.wc.common.db.PageInfo;
import com.wc.common.db.PagerControl;
import com.wc.user.bean.Member;
import com.wc.user.bean.Message;
import com.wc.user.service.MemberService;
import com.wc.user.service.MessageService;
import com.wc.web.until.RegLoginUtil;
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
@RequestMapping(value = "/message")
public class MessageController {

    @Autowired
    private MessageService messageService;
    @Autowired
    private MemberService memberService;

    @RequestMapping(value = "/list")
    public String list(Model model, HttpServletRequest request) {
        PageInfo pi = WebUtils.getPageInfo(request);
        PagerControl<Message> pc = messageService.page(new Message(), pi);
        model.addAttribute("pc", pc);
        return "/message/list";
    }


    @RequestMapping(value = "/add")
    public String add(HttpServletRequest request, HttpServletResponse res) {
        Message message = getMessage(request);
        messageService.add(message);
        return StageHelper.successForward("保存成功", res);

    }

    private Message getMessage(HttpServletRequest request) {
        String title = ServletRequestUtils.getStringParameter(request, "title", "");
        String content = ServletRequestUtils.getStringParameter(request, "content", "");
        String desc = ServletRequestUtils.getStringParameter(request, "desc", "");
        Message message = new Message();
        message.setContent(content);
        return message;
    }

    @RequestMapping(value = "/addView")
    public String addView(HttpServletRequest request, HttpServletResponse res) {

        return "/message/add";
    }

    @RequestMapping(value = "/updateView")
    public String updateView(HttpServletRequest request, HttpServletResponse res) {
        int id = ServletRequestUtils.getIntParameter(request, "id", 0);
        if (id <= 0) {
            return StageHelper.failForward("参数提交错误", res);
        }
        Message message = messageService.getById(id);
        request.setAttribute("message", message);
        return "/message/update";
    }

    @RequestMapping(value = "/update")
    public String update(HttpServletRequest request, HttpServletResponse res) {
        long id = ServletRequestUtils.getLongParameter(request, "id", 0);
        if (id <= 0) {
            return StageHelper.failForward("参数提交错误", res);
        }
        Message message = getMessage(request);
        message.setId(id);
        messageService.update(message);
        return StageHelper.successForward("保存成功", res);
    }

    @RequestMapping(value = "/del")
    public String del(HttpServletRequest request, HttpServletResponse res) {
        long id = ServletRequestUtils.getLongParameter(request, "id", 0);
        if (id <= 0) {
            return StageHelper.failForward("参数提交错误", res);
        }
        messageService.delete(id);
        return StageHelper.successForward("保存成功", res);
    }

    @RequestMapping(value = "/sendView")
    public String sendView(Model model, HttpServletRequest request, HttpServletResponse res) {
        int userId = ServletRequestUtils.getIntParameter(request, "userId", 0);
        Member member = memberService.getById(userId);
        request.setAttribute("userId", userId);
        request.setAttribute("member", member);
        return "/message/send";
    }

    @RequestMapping(value = "/send")
    public void send(Model model, HttpServletRequest request, HttpServletResponse res) {
        long senderId = RegLoginUtil.getSessionUid(request);
        if (senderId <= 0) {
            StageHelper.successForward("没有登陆", res);
            return;
        }
        long receiverId = ServletRequestUtils.getIntParameter(request, "receiverId", 0);
        String content = ServletRequestUtils.getStringParameter(request, "content", "");
        Message message = new Message();
        message.setContent(content);
        message.setSenderId(senderId);
        message.setReceiveId(receiverId);
        message.setCreateTime(new Date());
        messageService.add(message);

        StageHelper.successForward("发送成功", res);
    }


}
