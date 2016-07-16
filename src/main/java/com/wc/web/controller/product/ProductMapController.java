package com.wc.web.controller.product;

import com.wc.product.bean.Product;
import com.wc.product.service.ProductService;
import com.wc.product.service.ProductTypeService;
import com.wc.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;

/**
 * 商品户型
 */
@Controller
@RequestMapping(value = "/product/map")
public class ProductMapController {

    @Autowired
    private ProductTypeService productTypeService;
    @Autowired
    private ProductService productService;

    @RequestMapping(value = "/map")
    public String list(Model model, @RequestParam long pid) {
        Product product = productService.getById(pid);
        model.addAttribute("product", product);
        return "/product/map/map";
    }

    @RequestMapping(value = "/update")
    @ResponseBody
    public JsonResult update(HttpServletRequest request, HttpServletResponse res,
                             @RequestParam long id, @RequestParam float lat, @RequestParam float lng) {
        if (id <= 0) {
            return new JsonResult(false, "参数错误");
        }
        Product product = new Product();
        product.setId(id);
        product.setLat(new BigDecimal(lat));
        product.setLng(new BigDecimal(lng));
        productService.update(product);
        return new JsonResult(true, "保存成功");
    }

}
