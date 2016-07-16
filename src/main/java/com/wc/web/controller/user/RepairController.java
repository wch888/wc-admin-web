package com.wc.web.controller.user;

import com.jpush.JPushService;
import com.wc.common.db.PageInfo;
import com.wc.common.db.PagerControl;
import com.wc.user.bean.Member;
import com.wc.user.bean.MemberDetail;
import com.wc.user.bean.Repair;
import com.wc.user.bean.RepairComment;
import com.wc.user.service.MemberService;
import com.wc.user.service.RepairService;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/repair")
public class RepairController {

    @Autowired
    private RepairService repairService;
    @Autowired
    private MemberService memberService;

    @RequestMapping(value = "/list")
    public String list(Model model, HttpServletRequest request) {
        PageInfo pi = WebUtils.getPageInfo(request);
        Long communityId = ServletRequestUtils.getLongParameter(request, "communityId",0L);
        int status = ServletRequestUtils.getIntParameter(request, "status",0);
        String nickname = ServletRequestUtils.getStringParameter(request, "nickname","");
        String mobile = ServletRequestUtils.getStringParameter(request, "mobile","");
        String address = ServletRequestUtils.getStringParameter(request, "address","");
        Repair query =  new Repair();
        if(communityId>0){
            query.setCommunityId(communityId);
        }
        query.setStatus((short) status);
        if(StringUtils.isNotBlank(nickname)){
            query.setNickname(nickname);
        }
        if(StringUtils.isNotBlank(mobile)){
            query.setMobile(mobile);
        }
        if(StringUtils.isNotBlank(address)){
            query.setAddress(address);
        }

        PagerControl<Repair> pc = repairService.pageWithMember(query, pi);

        for (Repair repair : pc.getEntityList()) {
            if (Repair.WAITTING == repair.getStatus()) {
                repair.setOverTime(repair.haveOverTime());
            }
        }

        model.addAttribute("pc", pc);
        model.addAttribute("status",status);
        model.addAttribute("nickname",nickname);
        model.addAttribute("mobile",mobile);
        model.addAttribute("address",address);
        model.addAttribute("communityId",communityId);

        return "/repair/list";
    }


    @RequestMapping(value = "/add")
    public String add(HttpServletRequest request, HttpServletResponse res) {
        Repair repair = getRepair(request);
        repair.setCreateTime(new Date());
        Member member = memberService.getById(repair.getUserId());
        if (!member.isHouseHold()) {
            return StageHelper.failForward("用户还不是业主", res);
        }
        MemberDetail detail = memberService.getDetailById(repair.getUserId());
        if(null==detail.getCommunityId()){
            return StageHelper.failForward("用户没有加入任何小区", res);
        }
        repair.setCommunityId(detail.getCommunityId());
        repairService.add(repair);
        return StageHelper.successForward("保存成功","/repair/list", res);

    }

    private Repair getRepair(HttpServletRequest request) {
        long followId = ServletRequestUtils.getLongParameter(request, "follow.id", 0);
        String followName = ServletRequestUtils.getStringParameter(request, "follow.nickname", "");

        Repair repair = new Repair();
        repair.setFollowId(followId);
        repair.setFollowName(followName);
        return repair;
    }

    @RequestMapping(value = "/addView")
    public String addView(HttpServletRequest request, HttpServletResponse res) {

        return "/repair/add";
    }

    @RequestMapping(value = "/updateView")
    public String updateView(HttpServletRequest request, HttpServletResponse res) {
        int id = ServletRequestUtils.getIntParameter(request, "id", 0);
        if (id <= 0) {
            return StageHelper.failForward("参数提交错误", res);
        }
        Repair repair = repairService.getById(id);
        RepairComment query = new RepairComment();
        query.setRepairId(repair.getId());
        List<RepairComment> comments = repairService.list(query);
        String[] imgs = new String[]{};
        if (StringUtils.isNotBlank(repair.getImg())) {
            imgs = repair.getImg().split(",");
        }
        Member member = memberService.getById(repair.getUserId());
        MemberDetail detail = memberService.getDetailById(repair.getUserId());
        request.setAttribute("bean", repair);
        request.setAttribute("imgs", imgs);
        request.setAttribute("member", member);
        request.setAttribute("detail", detail);
        request.setAttribute("comments", comments);
        return "/repair/update";
    }

    @RequestMapping(value = "/update")
    public String update(HttpServletRequest request, HttpServletResponse res) {
        long id = ServletRequestUtils.getLongParameter(request, "id", 0);
        if (id <= 0) {
            return StageHelper.failForward("参数提交错误", res);
        }

        Repair repair = getRepair(request);
        repair.setId(id);
        repairService.update(repair);
        return StageHelper.successForward("保存成功","/repair/list", res);
    }

    @RequestMapping(value = "/dealing")
    public String dealing(HttpServletRequest request, HttpServletResponse res) {
        long id = ServletRequestUtils.getLongParameter(request, "id", 0);
        if (id <= 0) {
            return StageHelper.failForward("参数提交错误", res);
        }
        Repair result = repairService.getById(id);
        Repair repair = new Repair();
        repair.setId(id);
        repair.setStatus(Repair.DEALING);
        repair.setFollowTime(new Date());
        Member member = memberService.getById(result.getUserId());

        repairService.update(repair);

        Map<String, String> map = new HashMap<String, String>();
        map.put("type", "repair");
        map.put("id", repair.getId() + "");
        JPushService.getInstance().sendNotificationAudience(member.getPushId(), "你的报修已经分配给处理人", map);

        return StageHelper.successForward("保存成功", "/repair/update", res);
    }

    @RequestMapping(value = "/finish")
    public String finish(HttpServletRequest request, HttpServletResponse res) {
        long id = ServletRequestUtils.getLongParameter(request, "id", 0);
        if (id <= 0) {
            return StageHelper.failForward("参数提交错误", res);
        }
        Repair result = repairService.getById(id);
        Repair repair = new Repair();
        repair.setId(id);
        repair.setStatus(Repair.FINISH);
        repair.setFinishTime(new Date());
        Member member = memberService.getById(result.getUserId());

        repairService.update(repair);

        Map<String, String> map = new HashMap<String, String>();
        map.put("type", "repair");
        map.put("id", repair.getId() + "");
        JPushService.getInstance().sendNotificationAudience(member.getPushId(), "你的报修已经处理完毕", map);

        return StageHelper.successForward("保存成功", "/repair/update", res);
    }

    @RequestMapping(value = "/del")
    public String del(HttpServletRequest request, HttpServletResponse res) {
        long id = ServletRequestUtils.getLongParameter(request, "id", 0);
        if (id <= 0) {
            return StageHelper.failForward("参数提交错误", res);
        }
        repairService.delete(id);
        return StageHelper.successForward("保存成功", res);
    }
}
