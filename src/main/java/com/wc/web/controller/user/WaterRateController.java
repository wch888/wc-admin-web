package com.wc.web.controller.user;

import com.wc.common.db.PageInfo;
import com.wc.common.db.PagerControl;
import com.wc.user.bean.Community;
import com.wc.user.bean.Member;
import com.wc.user.bean.MemberDetail;
import com.wc.user.bean.WaterRate;
import com.wc.user.service.CommunityService;
import com.wc.user.service.MemberService;
import com.wc.user.service.WaterRateService;
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
@RequestMapping(value = "/waterRate")
public class WaterRateController {

    @Autowired
    private WaterRateService waterRateService;
    @Autowired
    private MemberService memberService;
    @Autowired
    private CommunityService communityService;

    @RequestMapping(value = "/list")
    public String list(Model model, HttpServletRequest request) {
        PageInfo pi = WebUtils.getPageInfo(request);
        Long communityId = ServletRequestUtils.getLongParameter(request, "communityId",0L);
        WaterRate query =  new WaterRate();
        if(communityId>0){
            query.setCommunityId(communityId);
        }
        Community community = communityService.getById(communityId);

        PagerControl pc = waterRateService.pageWithMember(query, pi, "order by w.id desc");
        model.addAttribute("pc", pc);
        model.addAttribute("community", community);
        return "/waterRate/list";
    }


    @RequestMapping(value = "/add")
    public String add(HttpServletRequest request, HttpServletResponse res) {
        WaterRate waterRate = null;
        try {
            waterRate = getWaterRate(request);
        } catch (ParseException e) {
            return StageHelper.failForward("保存失败,日期格式错误", res);
        }
        waterRate.setCreateTime(new Date());
        if (waterRate.getUserId() <= 0) {
            return StageHelper.failForward("保存失败,非法用户", res);
        }
        Member member = memberService.getById(waterRate.getUserId());
        if (!member.isHouseHold()) {
            return StageHelper.failForward("用户还不是业主", res);
        }
        MemberDetail detail = memberService.getDetailById(waterRate.getUserId());
        if(null==detail.getCommunityId()){
            return StageHelper.failForward("用户没有加入任何小区", res);
        }
        waterRate.setCommunityId(detail.getCommunityId());
        waterRateService.add(waterRate);
        return StageHelper.successForward("保存成功","/waterRate/list", res);

    }

    private WaterRate getWaterRate(HttpServletRequest request) throws ParseException {
        long userId = ServletRequestUtils.getLongParameter(request, "user.id", 0);
        float waterAmount = ServletRequestUtils.getFloatParameter(request, "waterAmount", 0.00f);
        float sewageAmount = ServletRequestUtils.getFloatParameter(request, "sewageAmount", 0.00f);
        float resourceAmount = ServletRequestUtils.getFloatParameter(request, "resourceAmount", 0.00f);
        float price = ServletRequestUtils.getFloatParameter(request, "price", 0.00f);
        float other = ServletRequestUtils.getFloatParameter(request, "other", 0.00f);
        float total = ServletRequestUtils.getFloatParameter(request, "total", 0.00f);
        String payTime = ServletRequestUtils.getStringParameter(request, "payTime", "");
        String month = ServletRequestUtils.getStringParameter(request, "month", "");
        int status = ServletRequestUtils.getIntParameter(request, "status", 0);
        WaterRate waterRate = new WaterRate();
        waterRate.setAmount(new BigDecimal(waterAmount+sewageAmount+resourceAmount));
        waterRate.setUserId(userId);
        Date date = DateUtils.parseDate(payTime, new String[]{"yyyy-MM","yyyy-MM-dd"});
        waterRate.setPayTime(date);
        Date monthDate = DateUtils.parseDate(month, new String[]{"yyyy-MM","yyyy-MM-dd"});
        waterRate.setMonth(monthDate);
        waterRate.setTotal(new BigDecimal(total));
        waterRate.setStatus((short) status);
        waterRate.setWaterAmount(new BigDecimal(waterAmount));
        waterRate.setSewageAmount(new BigDecimal(sewageAmount));
        waterRate.setResourceAmount(new BigDecimal(resourceAmount));
        waterRate.setPrice(new BigDecimal(price));
        waterRate.setOther(new BigDecimal(other));
        return waterRate;
    }

    @RequestMapping(value = "/addView")
    public String addView(HttpServletRequest request, HttpServletResponse res) {

        return "/waterRate/add";
    }

    @RequestMapping(value = "/updateView")
    public String updateView(HttpServletRequest request, HttpServletResponse res) {
        int id = ServletRequestUtils.getIntParameter(request, "id", 0);
        if (id <= 0) {
            return StageHelper.failForward("参数提交错误", res);
        }
        WaterRate waterRate = waterRateService.getById(id);
        Member member = memberService.getById(waterRate.getUserId());
        request.setAttribute("bean", waterRate);
        request.setAttribute("member", member);
        return "/waterRate/update";
    }

    @RequestMapping(value = "/update")
    public String update(HttpServletRequest request, HttpServletResponse res) {
        long id = ServletRequestUtils.getLongParameter(request, "id", 0);
        if (id <= 0) {
            return StageHelper.failForward("参数提交错误", res);
        }
        WaterRate waterRate = null;
        try {
            waterRate = getWaterRate(request);
        } catch (ParseException e) {
            return StageHelper.failForward("保存失败,日期格式错误", res);
        }
        waterRate.setId(id);
        waterRateService.update(waterRate);
        return StageHelper.successForward("保存成功","/waterRate/list", res);
    }

    @RequestMapping(value = "/del")
    public String del(HttpServletRequest request, HttpServletResponse res) {
        long id = ServletRequestUtils.getLongParameter(request, "id", 0);
        if (id <= 0) {
            return StageHelper.failForward("参数提交错误", res);
        }
        waterRateService.delete(id);
        return StageHelper.successForward("保存成功", res);
    }
}
