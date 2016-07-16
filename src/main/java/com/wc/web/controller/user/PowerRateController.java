package com.wc.web.controller.user;

import com.wc.common.db.PageInfo;
import com.wc.common.db.PagerControl;
import com.wc.user.bean.Member;
import com.wc.user.bean.MemberDetail;
import com.wc.user.bean.PowerRate;
import com.wc.user.service.MemberService;
import com.wc.user.service.PowerRateService;
import com.wc.web.until.StageHelper;
import com.wc.web.until.WebUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;

@Controller
@RequestMapping(value = "/powerRate")
public class PowerRateController {

    @Autowired
    private PowerRateService powerRateService;
    @Autowired
    private MemberService memberService;

    @RequestMapping(value = "/list")
    public String list(Model model, HttpServletRequest request) {
        PageInfo pi = WebUtils.getPageInfo(request);

        Long communityId = ServletRequestUtils.getLongParameter(request, "communityId",0L);
        PowerRate query =  new PowerRate();
        if(communityId>0){
            query.setCommunityId(communityId);
        }
        PagerControl pc = powerRateService.pageWithMember(query, pi, "order by a.id desc");
        model.addAttribute("pc", pc);
        return "/powerRate/list";
    }


    @RequestMapping(value = "/add")
    public String add(HttpServletRequest request, HttpServletResponse res) {
        PowerRate powerRate = null;
        try {
            powerRate = getPowerRate(request);
        } catch (ParseException e) {
            return StageHelper.failForward("保存失败,日期格式错误", res);
        }
        Member member = memberService.getById(powerRate.getUserId());
        if (!member.isHouseHold()) {
            return StageHelper.failForward("用户还不是业主", res);
        }
        MemberDetail detail = memberService.getDetailById(powerRate.getUserId());
        if(null==detail.getCommunityId()){
            return StageHelper.failForward("用户没有加入任何小区", res);
        }
        powerRate.setCommunityId(detail.getCommunityId());
        powerRate.setCreateTime(new Date());
        powerRateService.add(powerRate);
        return StageHelper.successForward("保存成功","/powerRate/list", res);

    }

    private PowerRate getPowerRate(HttpServletRequest request) throws ParseException {
        long userId = ServletRequestUtils.getLongParameter(request, "user.id", 0);
        float amount = ServletRequestUtils.getFloatParameter(request, "amount", 0.00f);
        float level1 = ServletRequestUtils.getFloatParameter(request, "level1", 0.00f);
        float level2 = ServletRequestUtils.getFloatParameter(request, "level2", 0.00f);
        float total = ServletRequestUtils.getFloatParameter(request, "total", 0.00f);
        String payTime = ServletRequestUtils.getStringParameter(request, "payTime", "");
        String month = ServletRequestUtils.getStringParameter(request, "month", "");

        Date date = DateUtils.parseDate(payTime, new String[]{"yyyy-MM","yyyy-MM-dd"});
        PowerRate bean = new PowerRate();
        bean.setAmount(new BigDecimal(amount));
        bean.setUserId(userId);
        bean.setTotal(new BigDecimal(total));
        bean.setPayTime(date);
        Date monthDate = DateUtils.parseDate(month,new String[]{"yyyy-MM","yyyy-MM-dd"});
        bean.setMonth(monthDate);
        bean.setLevel1(new BigDecimal(level1));
        bean.setLevel2(new BigDecimal(level2));
        return bean;
    }

    @RequestMapping(value = "/addView")
    public String addView(HttpServletRequest request, HttpServletResponse res) {

        return "/powerRate/add";
    }

    @RequestMapping(value = "/updateView")
    public String updateView(HttpServletRequest request, HttpServletResponse res) {
        int id = ServletRequestUtils.getIntParameter(request, "id", 0);
        if (id <= 0) {
            return StageHelper.failForward("参数提交错误", res);
        }
        PowerRate powerRate = powerRateService.getById(id);
        Member member = memberService.getById(powerRate.getUserId());
        request.setAttribute("member", member);
        request.setAttribute("bean", powerRate);
        return "/powerRate/update";
    }

    @RequestMapping(value = "/update")
    public String update(HttpServletRequest request, HttpServletResponse res) {
        long id = ServletRequestUtils.getLongParameter(request, "id", 0);
        if (id <= 0) {
            return StageHelper.failForward("参数提交错误", res);
        }
        PowerRate powerRate = null;
        try {
            powerRate = getPowerRate(request);
        } catch (ParseException e) {
            return StageHelper.failForward("保存失败,日期格式错误", res);
        }
        powerRate.setId(id);
        powerRateService.update(powerRate);
        return StageHelper.successForward("保存成功", "/powerRate/list",res);
    }

    @RequestMapping(value = "/del")
    public String del(HttpServletRequest request, HttpServletResponse res) {
        long id = ServletRequestUtils.getLongParameter(request, "id", 0);
        if (id <= 0) {
            return StageHelper.failForward("参数提交错误", res);
        }
        powerRateService.delete(id);
        return StageHelper.successForward("保存成功", res);
    }
}
