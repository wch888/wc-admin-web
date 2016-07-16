package com.wc.web.controller.wallet;

import com.wc.common.db.PageInfo;
import com.wc.common.db.PagerControl;
import com.wc.user.bean.Wallet;
import com.wc.user.service.WalletService;
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
@RequestMapping(value = "/wallet")
public class WalletController {

    @Autowired
    private WalletService walletService;

    @RequestMapping(value = "/list")
    public String list(Model model, HttpServletRequest request) {
        PageInfo pi = WebUtils.getPageInfo(request);
        Wallet query = new Wallet();
        PagerControl pc = walletService.pageWithMember(query, pi);
        model.addAttribute("pc", pc);
        return "/wallet/wallet/list";
    }

    @RequestMapping(value = "/addView")
    public String addView(HttpServletRequest request, HttpServletResponse res) {

        return "/wallet/wallet/add";
    }

    @RequestMapping(value = "/updateView")
    public String updateView(HttpServletRequest request, HttpServletResponse res) {
        int id = ServletRequestUtils.getIntParameter(request, "id", 0);
        if (id <= 0) {
            return StageHelper.failForward("参数提交错误", res);
        }
        Wallet wallet = walletService.getById(id);
        request.setAttribute("bean", wallet);
        return "/wallet/wallet/update";
    }

    @RequestMapping(value = "/update")
    public String update(HttpServletRequest request, HttpServletResponse res) {
        long id = ServletRequestUtils.getLongParameter(request, "id", 0);
        if (id <= 0) {
            return StageHelper.failForward("参数提交错误", res);
        }
        int userId = ServletRequestUtils.getIntParameter(request, "userId", 0);
        int integration = ServletRequestUtils.getIntParameter(request, "integration", 0);
        float money = ServletRequestUtils.getFloatParameter(request, "money", 0.00f);
        walletService.addIntegration(userId, integration);
        walletService.addIntegration(userId, new BigDecimal(money));
        return StageHelper.successForward("保存成功", res);
    }

    @RequestMapping(value = "/del")
    public String del(HttpServletRequest request, HttpServletResponse res) {
        long id = ServletRequestUtils.getLongParameter(request, "id", 0);
        if (id <= 0) {
            return StageHelper.failForward("参数提交错误", res);
        }
//        walletService.delete(id);
        return StageHelper.successForward("保存成功", res);
    }
}
