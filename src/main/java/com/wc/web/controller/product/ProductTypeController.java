package com.wc.web.controller.product;

import com.wc.base.service.SettingService;
import com.wc.product.bean.Product;
import com.wc.product.bean.ProductType;
import com.wc.product.service.ProductService;
import com.wc.product.service.ProductTypeService;
import com.wc.util.JsonResult;
import com.wc.web.until.StageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 商品户型
 */
@Controller
@RequestMapping(value = "/product/type")
public class ProductTypeController {

    @Autowired
    private ProductTypeService productTypeService;
    @Autowired
    private ProductService productService;
    @Autowired
    private SettingService settingService;

    @RequestMapping(value = "/list")
    public String list(Model model, @RequestParam long pid) {
        Product product = productService.getById(pid);
        List<ProductType> list = productTypeService.list(pid);
        model.addAttribute("list", list);
        model.addAttribute("product", product);
        model.addAttribute("houseType",settingService.getCondition(SettingService.HOUSE_TYPE));
        return "/product/type/upload_type";
    }

    @RequestMapping(value = "/add")
    @ResponseBody
    public JsonResult add(HttpServletRequest request, HttpServletResponse res,
                          @RequestParam long id, @RequestParam String url) {
        if (id <= 0) {
            return new JsonResult(false, "参数错误");
        }
        ProductType bean = new ProductType();
        bean.setPid(id);
        bean.setImg(url);

        productTypeService.add(bean);
        return new JsonResult(true).setData(bean);
    }

    @RequestMapping(value = "/update")
    @ResponseBody
    public JsonResult update(HttpServletRequest request, HttpServletResponse res,
                             @RequestParam long id, @RequestParam String description,
                             @RequestParam String title, @RequestParam Integer type,
                             @RequestParam String building) {
        if (id <= 0) {
            return new JsonResult(false, "参数错误");
        }
        ProductType bean = new ProductType();
        bean.setId(id);
        bean.setDescription(description);
        bean.setTitle(title);
        bean.setType(type);
        bean.setBuilding(building);
        productTypeService.update(bean);
        return new JsonResult(true, "保存成功");
    }

    @RequestMapping(value = "/del")
    public String del(HttpServletRequest request, HttpServletResponse res) {
        long id = ServletRequestUtils.getLongParameter(request, "id", 0);
        if (id <= 0) {
            return StageHelper.failForward("参数提交错误", res);
        }
        productTypeService.delete(id);
        return StageHelper.successForward("保存成功", res);
    }
}
