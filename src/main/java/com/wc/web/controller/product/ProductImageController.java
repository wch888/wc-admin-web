package com.wc.web.controller.product;

import com.wc.product.bean.Product;
import com.wc.product.bean.ProductImage;
import com.wc.product.service.ProductImageService;
import com.wc.product.service.ProductService;
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
 * 商品图片
 */
@Controller
@RequestMapping(value = "/product/img")
public class ProductImageController {

    @Autowired
    private ProductImageService productImageService;
    @Autowired
    private ProductService productService;

    @RequestMapping(value = "/list")
    public String list(Model model, @RequestParam long pid) {
        Product product = productService.getById(pid);
        List<ProductImage> list = productImageService.list(pid);
        model.addAttribute("list", list);
        model.addAttribute("product", product);
        return "/product/img/upload_image";
    }

    @RequestMapping(value = "/add")
    @ResponseBody
    public JsonResult add(HttpServletRequest request, HttpServletResponse res,
                      @RequestParam long id, @RequestParam String url) {
        if (id <= 0) {
            return new JsonResult(false, "参数错误");
        }
        ProductImage image = new ProductImage();
        image.setPid(id);
        image.setImage(url);
        productImageService.add(image);
        return new JsonResult(true).setData(image);
    }

    @RequestMapping(value = "/del")
    public String del(HttpServletRequest request, HttpServletResponse res) {
        long id = ServletRequestUtils.getLongParameter(request, "id", 0);
        if (id <= 0) {
            return StageHelper.failForward("参数提交错误", res);
        }
        productImageService.delete(id);
        return StageHelper.successForward("保存成功", res);
    }

    @RequestMapping(value = "/defaultImg")
    @ResponseBody
    public JsonResult updateDefaultImg(HttpServletRequest request, HttpServletResponse res) {
        long id = ServletRequestUtils.getLongParameter(request, "id", 0);
        if (id <= 0) {
            return new JsonResult(false, "参数提交错误");
        }
        String defaultImg = ServletRequestUtils.getStringParameter(request, "defaultImg", "");
        Product product = new Product();
        product.setId(id);
        product.setDefaultImg(defaultImg);
        productService.update(product);
        return new JsonResult(true, "保存成功");
    }

}
