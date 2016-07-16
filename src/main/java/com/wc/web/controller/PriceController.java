package com.wc.web.controller;

import com.wc.base.bean.BasisCity;
import com.wc.base.bean.Price;
import com.wc.base.service.BasisCityService;
import com.wc.base.service.PriceService;
import com.wc.common.db.PageInfo;
import com.wc.common.db.PagerControl;
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
@RequestMapping(value = "/price")
public class PriceController {

    @Autowired
    private PriceService priceService;
    @Autowired
    private BasisCityService basisCityService;

    @RequestMapping(value = "/list")
    public String list(Model model, HttpServletRequest request) {
        PageInfo pi = WebUtils.getPageInfo(request);
        PagerControl<Price> pc = priceService.page(new Price(), pi, "order by id asc");
        for (Price price : pc.getEntityList()) {
            BasisCity city = basisCityService.getById(price.getCityId());
            price.setCity(city.getCityName());
        }
        model.addAttribute("pc", pc);
        return "/price/list";
    }


    @RequestMapping(value = "/add")
    public String add(HttpServletRequest request, HttpServletResponse res) {
        Price price = getPrice(request);
        priceService.add(price);
        return StageHelper.successForward("保存成功","/price/list", res);

    }

    private Price getPrice(HttpServletRequest request) {
        String title = ServletRequestUtils.getStringParameter(request, "title", "");
        long province = ServletRequestUtils.getLongParameter(request, "province", 0);
        long cityId = ServletRequestUtils.getLongParameter(request, "city", 0);
        long area = ServletRequestUtils.getLongParameter(request, "area", 0);
        float min = ServletRequestUtils.getFloatParameter(request, "min", 0.00f);
        float max = ServletRequestUtils.getFloatParameter(request, "max", 0.00f);
        Price price = new Price();
        price.setTitle(title);
        price.setCityId(cityId);
        price.setProvinceId(province);
        price.setCityId(cityId);
        price.setAreaId(area);
        price.setMax(new BigDecimal(max));
        price.setMin(new BigDecimal(min));
        return price;
    }

    @RequestMapping(value = "/addView")
    public String addView(HttpServletRequest request, HttpServletResponse res) {
        return "/price/add";
    }

    @RequestMapping(value = "/updateView")
    public String updateView(HttpServletRequest request, HttpServletResponse res) {
        int id = ServletRequestUtils.getIntParameter(request, "id", 0);
        if (id <= 0) {
            return StageHelper.failForward("参数提交错误", res);
        }
        Price price = priceService.getById(id);
        request.setAttribute("bean", price);
        return "/price/update";
    }

    @RequestMapping(value = "/update")
    public String update(HttpServletRequest request, HttpServletResponse res) {
        long id = ServletRequestUtils.getLongParameter(request, "id", 0);
        if (id <= 0) {
            return StageHelper.failForward("参数提交错误", res);
        }
        Price price = getPrice(request);
        price.setId(id);
        priceService.update(price);
        return StageHelper.successForward("保存成功", "/price/list",res);
    }

    @RequestMapping(value = "/del")
    public String del(HttpServletRequest request, HttpServletResponse res) {
        long id = ServletRequestUtils.getLongParameter(request, "id", 0);
        if (id <= 0) {
            return StageHelper.failForward("参数提交错误", res);
        }
        priceService.delete(id);
        return StageHelper.successForward("保存成功", res);
    }
}
