package com.wc.web.controller.backstage;

import com.wc.base.service.SettingService;
import com.wc.web.until.StageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping(value = "/setting")
public class SettingController {

    @Autowired
    private SettingService settingService;

    @RequestMapping(value = "/list")
    public String list(Model model, HttpServletRequest request) {

        String clueDefault = settingService.getString("clueDefault","0");
        model.addAttribute("clueDefault",clueDefault);
        return "/backstege/setting/list";
    }

    @RequestMapping(value = "/update")
    public String update(HttpServletRequest request, HttpServletResponse res) {
        String clueDefault = ServletRequestUtils.getStringParameter(request, "clueDefault","");
        settingService.update("clueDefault",clueDefault);
        return StageHelper.successForward("保存成功", res);
    }

    @RequestMapping(value = "/integration")
    public String integration(Model model, HttpServletRequest request) {

        String reg = settingService.getString("reg","0");
        String clue_fail = settingService.getString("clue_add","0");
        String share_news = settingService.getString("share_news","0");
        String share_product = settingService.getString("share_product","0");
        String verifyHouseHold = settingService.getString("verifyHouseHold","0");
        String login = settingService.getString("login","0");

        model.addAttribute("reg",reg);
        model.addAttribute("clueFail",clue_fail);
        model.addAttribute("shareNews",share_news);
        model.addAttribute("shareProduct",share_product);
        model.addAttribute("verifyHouseHold",verifyHouseHold);
        model.addAttribute("login",login);

        return "/backstege/setting/integration";
    }

    @RequestMapping(value = "/integration/update")
    public String add(HttpServletRequest request, HttpServletResponse res) {
        String regStr = ServletRequestUtils.getStringParameter(request, "reg","");
        String clueAdd = ServletRequestUtils.getStringParameter(request, "clueAdd","");
        String shareNews = ServletRequestUtils.getStringParameter(request, "shareNews","");
        String shareProduct = ServletRequestUtils.getStringParameter(request, "shareProduct","");
        String verifyHouseHold = ServletRequestUtils.getStringParameter(request, "verifyHouseHold","");
        String login = ServletRequestUtils.getStringParameter(request, "login","");

        settingService.update("reg",regStr);
        settingService.update("clue_add",clueAdd);
        settingService.update("share_news",shareNews);
        settingService.update("share_product",shareProduct);
        settingService.update("verifyHouseHold",verifyHouseHold);
        settingService.update("login",login);


        return StageHelper.successForward("保存成功", res);
    }


}
