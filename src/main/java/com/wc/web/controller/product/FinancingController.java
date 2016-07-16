package com.wc.web.controller.product;

import com.wc.common.db.PageInfo;
import com.wc.common.db.PagerControl;
import com.wc.product.bean.FinancingProduct;
import com.wc.product.service.FinancingProductService;
import com.wc.product.service.FinancingUserService;
import com.wc.web.until.StageHelper;
import com.wc.web.until.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;

@Controller
@RequestMapping(value = "/financing")
public class FinancingController {

    @Autowired
    private FinancingProductService financingProductService;
    @Autowired
    private FinancingUserService financingUserService;

    @RequestMapping(value = "/list")
    public String list(Model model, HttpServletRequest request) {
        PageInfo pi = WebUtils.getPageInfo(request);
        PagerControl pc = financingProductService.page(new FinancingProduct(), pi, null);
        model.addAttribute("pc", pc);
        return "/financing/list";
    }


    @RequestMapping(value = "/add")
    public String add(HttpServletRequest request, HttpServletResponse res) {
        FinancingProduct financing = getFinancingProduct(request);
        financingProductService.add(financing);
        return StageHelper.successForward("保存成功","/financing/list", res);

    }

    private FinancingProduct getFinancingProduct(HttpServletRequest request) {
        String title = ServletRequestUtils.getStringParameter(request, "title", "");
        float price = ServletRequestUtils.getFloatParameter(request, "price", 0.00f);
        float rate = ServletRequestUtils.getFloatParameter(request, "rate", 0.00f);
        int type = ServletRequestUtils.getIntParameter(request, "type", 0);
        String time_limit = ServletRequestUtils.getStringParameter(request, "time_limit", "");
        String condition = ServletRequestUtils.getStringParameter(request, "condition", "");
        String content = ServletRequestUtils.getStringParameter(request, "content", "");
        String company = ServletRequestUtils.getStringParameter(request, "company", "");
        String revenueRule = ServletRequestUtils.getStringParameter(request, "revenueRule", "");
        String refund = ServletRequestUtils.getStringParameter(request, "refund", "");

        FinancingProduct financing = new FinancingProduct();
        financing.setContent(content);
        financing.setTitle(title);
        financing.setTimeLimit(time_limit);
        financing.setType((short) type);
        financing.setCondition(condition);
        financing.setRate(new BigDecimal(rate));
        financing.setPrice(new BigDecimal(price));
        financing.setCompany(company);
        financing.setRevenueRule(revenueRule);
        financing.setRefund(refund);
        return financing;
    }

    @RequestMapping(value = "/addView")
    public String addView(HttpServletRequest request, HttpServletResponse res) {

        return "/financing/add";
    }

    @RequestMapping(value = "/updateView")
    public String updateView(HttpServletRequest request, HttpServletResponse res) {
        int id = ServletRequestUtils.getIntParameter(request, "id", 0);
        if (id <= 0) {
            return StageHelper.failForward("参数提交错误", res);
        }
        FinancingProduct bean = financingProductService.getById(id);
        request.setAttribute("bean", bean);
        return "/financing/update";
    }

    @RequestMapping(value = "/update")
    public String update(HttpServletRequest request, HttpServletResponse res) {
        long id = ServletRequestUtils.getLongParameter(request, "id", 0);
        if (id <= 0) {
            return StageHelper.failForward("参数提交错误", res);
        }
        FinancingProduct financing = getFinancingProduct(request);
        financing.setId(id);
        financingProductService.update(financing);
        return StageHelper.successForward("保存成功","/financing/list", res);
    }

    @RequestMapping(value = "/del")
    public String del(HttpServletRequest request, HttpServletResponse res) {
        long id = ServletRequestUtils.getLongParameter(request, "id", 0);
        if (id <= 0) {
            return StageHelper.failForward("参数提交错误", res);
        }
        financingProductService.delete(id);
        return StageHelper.successForward("保存成功", res);
    }
}
