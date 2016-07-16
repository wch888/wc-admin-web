package com.wc.web.controller.user;

import com.wc.common.db.PageInfo;
import com.wc.common.db.PagerControl;
import com.wc.product.service.FinancingUserService;
import com.wc.user.bean.Member;
import com.wc.user.bean.MemberDetail;
import com.wc.user.bean.PropertyManagementFee;
import com.wc.user.service.MemberService;
import com.wc.user.service.PropertyManagementFeeService;
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

/**
 * 物业费管理
 */
@Controller
@RequestMapping(value = "/propertyFee")
public class PropertyManagementFeeController {

    @Autowired
    private PropertyManagementFeeService propertyManagementFeeService;
    @Autowired
    private FinancingUserService financingUserService;
    @Autowired
    private MemberService memberService;
    @RequestMapping(value = "/list")
    public String list(Model model, HttpServletRequest request) {
        PageInfo pi = WebUtils.getPageInfo(request);
        Long communityId = ServletRequestUtils.getLongParameter(request, "communityId",0L);
        PropertyManagementFee query =  new PropertyManagementFee();
        if(communityId>0){
            query.setCommunityId(communityId);
        }

        PagerControl pc = propertyManagementFeeService.pageWithMember(query, pi, null);
        model.addAttribute("pc", pc);
        return "/propertyFee/list";
    }


    @RequestMapping(value = "/add")
    public String add(HttpServletRequest request, HttpServletResponse res) {
        PropertyManagementFee financing = null;
        try {
            financing = getPropertyManagementFee(request);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        financing.setCreateTime(new Date());
        Member member = memberService.getById(financing.getUserId());
        if (!member.isHouseHold()) {
            return StageHelper.failForward("用户还不是业主", res);
        }
        MemberDetail detail = memberService.getDetailById(financing.getUserId());
        if(null==detail.getCommunityId()){
            return StageHelper.failForward("用户没有加入任何小区", res);
        }
        financing.setCommunityId(detail.getCommunityId());
        propertyManagementFeeService.add(financing);
        return StageHelper.successForward("保存成功","/propertyFee/list", res);

    }

    private PropertyManagementFee getPropertyManagementFee(HttpServletRequest request) throws ParseException {
        long userId = ServletRequestUtils.getLongParameter(request, "user.id", 0);
        float amount = ServletRequestUtils.getFloatParameter(request, "amount", 0.00f);
        float total = ServletRequestUtils.getFloatParameter(request, "total", 0.00f);
        int status = ServletRequestUtils.getIntParameter(request, "status", 0);
        String payTime = ServletRequestUtils.getStringParameter(request, "payTime", "");
        String month = ServletRequestUtils.getStringParameter(request, "month", "");

        PropertyManagementFee bean = new PropertyManagementFee();
        bean.setAmount(new BigDecimal(amount));
        bean.setStatus((short) status);
        Date date = DateUtils.parseDate(payTime, new String[]{"yyyy-MM","yyyy-MM-dd"});
        bean.setPayTime(date);
        Date monthDate = DateUtils.parseDate(month, new String[]{"yyyy-MM","yyyy-MM-dd"});
        bean.setMonth(monthDate);
        bean.setUserId(userId);
        return bean;
    }

    @RequestMapping(value = "/addView")
    public String addView(HttpServletRequest request, HttpServletResponse res) {

        return "/propertyFee/add";
    }

    @RequestMapping(value = "/updateView")
    public String updateView(HttpServletRequest request, HttpServletResponse res) {
        int id = ServletRequestUtils.getIntParameter(request, "id", 0);
        if (id <= 0) {
            return StageHelper.failForward("参数提交错误", res);
        }
        PropertyManagementFee bean = propertyManagementFeeService.getById(id);
        Member member = memberService.getById(bean.getUserId());

        request.setAttribute("bean", bean);
        request.setAttribute("member", member);
        return "/propertyFee/update";
    }

    @RequestMapping(value = "/update")
    public String update(HttpServletRequest request, HttpServletResponse res) {
        long id = ServletRequestUtils.getLongParameter(request, "id", 0);
        if (id <= 0) {
            return StageHelper.failForward("参数提交错误", res);
        }
        PropertyManagementFee bean = null;
        try {
            bean = getPropertyManagementFee(request);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        bean.setId(id);
        propertyManagementFeeService.update(bean);
        return StageHelper.successForward("保存成功","/propertyFee/list", res);
    }

    @RequestMapping(value = "/del")
    public String del(HttpServletRequest request, HttpServletResponse res) {
        long id = ServletRequestUtils.getLongParameter(request, "id", 0);
        if (id <= 0) {
            return StageHelper.failForward("参数提交错误", res);
        }
        propertyManagementFeeService.delete(id);
        return StageHelper.successForward("保存成功", res);
    }
}
