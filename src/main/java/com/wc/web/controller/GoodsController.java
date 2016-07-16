package com.wc.web.controller;

import com.wc.common.db.PageInfo;
import com.wc.common.db.PagerControl;
import com.wc.product.bean.Goods;
import com.wc.product.service.GoodsService;
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
@RequestMapping(value = "/goods")
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    @RequestMapping(value = "/list")
    public String list(Model model, HttpServletRequest request) {
        PageInfo pi = WebUtils.getPageInfo(request);
        PagerControl pc = goodsService.page(new Goods(), pi, null);
        model.addAttribute("pc", pc);
        return "/goods/list";
    }


    @RequestMapping(value = "/add")
    public String add(HttpServletRequest request, HttpServletResponse res) {
        Goods goods = getGoods(request);
        goodsService.add(goods);
        return StageHelper.successForward("保存成功","/goods/list", res);

    }

    private Goods getGoods(HttpServletRequest request) {
        String title = ServletRequestUtils.getStringParameter(request, "title", "");
        float price = ServletRequestUtils.getFloatParameter(request, "price", 0.00f);
        String defaultImg = ServletRequestUtils.getStringParameter(request, "defaultImg", "");
        int status = ServletRequestUtils.getIntParameter(request, "status", 1);
        int stock = ServletRequestUtils.getIntParameter(request, "stock", 0);
        String content = ServletRequestUtils.getStringParameter(request, "content", "");

        Goods goods = new Goods();
        goods.setContent(content);
        goods.setTitle(title);
        goods.setDefaultImg(defaultImg);
        goods.setStatus((short) status);
        goods.setStock(stock);
        goods.setPrice(new BigDecimal(price));
        return goods;
    }

    @RequestMapping(value = "/addView")
    public String addView(HttpServletRequest request, HttpServletResponse res) {

        return "/goods/add";
    }

    @RequestMapping(value = "/updateView")
    public String updateView(HttpServletRequest request, HttpServletResponse res) {
        int id = ServletRequestUtils.getIntParameter(request, "id", 0);
        if (id <= 0) {
            return StageHelper.failForward("参数提交错误", res);
        }
        Goods bean = goodsService.getById(id);
        request.setAttribute("bean", bean);
        return "/goods/update";
    }

    @RequestMapping(value = "/update")
    public String update(HttpServletRequest request, HttpServletResponse res) {
        long id = ServletRequestUtils.getLongParameter(request, "id", 0);
        if (id <= 0) {
            return StageHelper.failForward("参数提交错误", res);
        }
        Goods goods = getGoods(request);
        goods.setId(id);
        goodsService.update(goods);
        return StageHelper.successForward("保存成功","/goods/list", res);
    }

    @RequestMapping(value = "/del")
    public String del(HttpServletRequest request, HttpServletResponse res) {
        long id = ServletRequestUtils.getLongParameter(request, "id", 0);
        if (id <= 0) {
            return StageHelper.failForward("参数提交错误", res);
        }
        goodsService.delete(id);
        return StageHelper.successForward("保存成功", res);
    }
}
