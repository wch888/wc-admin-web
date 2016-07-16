package com.wc.web.controller.product;

import com.wc.common.db.PageInfo;
import com.wc.common.db.PagerControl;
import com.wc.product.bean.Product;
import com.wc.product.bean.ProductBroker;
import com.wc.product.bean.ProductBrokerKey;
import com.wc.product.bean.ProductDetail;
import com.wc.product.service.ProductBrokerService;
import com.wc.product.service.ProductService;
import com.wc.util.JsonResult;
import com.wc.web.until.StageHelper;
import com.wc.web.until.WebUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping(value = "/product")
public class ProductController {

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);
    public static final String[] pattern = new String[]{"yyyy-MM-dd HH:mm:ss"};
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductBrokerService productBrokerService;

    @RequestMapping(value = "/list")
    public String list(Model model, HttpServletRequest request) {
        System.out.println("index");
        PageInfo pi = WebUtils.getPageInfo(request);
        PagerControl pc = productService.page(new Product(), pi, "order by id desc");
        model.addAttribute("pc", pc);
        return "/product/product/list";
    }

    @RequestMapping(value = "/select")
    public String select(Model model, HttpServletRequest request) {
        PageInfo pi = WebUtils.getPageInfo(request);
        PagerControl pc = productService.page(new Product(), pi, "order by id desc");
        model.addAttribute("pc", pc);
        return "/product/product/select";
    }

    @RequestMapping(value = "/add")
    public String add(HttpServletRequest request, HttpServletResponse res) {
        Product product = getProduct(request);
        product.setCreateTime(new Date());
        ProductDetail detail = getDetail(request);
        productService.add(product,detail);
        return StageHelper.successForward("保存成功","/product/list", res);

    }

    private Product getProduct(HttpServletRequest request) {
        String name = ServletRequestUtils.getStringParameter(request, "name", "");
        float price = ServletRequestUtils.getFloatParameter(request, "price", 0.00f);
        long city = ServletRequestUtils.getLongParameter(request, "city", 0);
        long area = ServletRequestUtils.getLongParameter(request, "area", 0);
        long province = ServletRequestUtils.getLongParameter(request, "province", 0);
        long userId = ServletRequestUtils.getLongParameter(request, "userId", 0);
        int status = ServletRequestUtils.getIntParameter(request, "status", 0);
        String discount = ServletRequestUtils.getStringParameter(request, "discount", "");
        String phone = ServletRequestUtils.getStringParameter(request, "phone", "");
        String service = ServletRequestUtils.getStringParameter(request, "service", "");
        String start_time = ServletRequestUtils.getStringParameter(request, "start_time", "");
        String location = ServletRequestUtils.getStringParameter(request, "location", "");
        String consultant = ServletRequestUtils.getStringParameter(request, "consultant", "");


        Product product = new Product();

        try {
            Date start = DateUtils.parseDate(start_time, pattern);
            product.setStartTime(start);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        product.setPrice(new BigDecimal(price));
        product.setPhone(phone);
        product.setName(name);
        product.setCity(city);
        product.setArea(area);
        product.setProvince(province);
        product.setDiscount(discount);
        product.setService(service);
        product.setStatus((short) status);
        product.setUserId(userId);
        product.setUpdateTime(new Date());
        product.setLocation(location);
        product.setConsultant(consultant);
        return product;
    }

    @RequestMapping(value = "/addView")
    public String addView(HttpServletRequest request, HttpServletResponse res) {

        return "/product/product/add";
    }

    @RequestMapping(value = "/updateView")
    public String updateView(HttpServletRequest request, HttpServletResponse res) {
        long id = ServletRequestUtils.getLongParameter(request, "id", 0);
        if (id <= 0) {
            return StageHelper.failForward("参数提交错误", res);
        }
        Product product = productService.getById(id);
        ProductDetail detail = productService.getDetailById(id);
        ProductBroker query = new ProductBroker();
        query.setPid(id);
        List<ProductBroker> broker = productBrokerService.list(query);
        request.setAttribute("bean", product);
        request.setAttribute("broker", broker);
        request.setAttribute("detail", detail);
        return "/product/product/update";
    }

    @RequestMapping(value = "/update")
    public String update(HttpServletRequest request, HttpServletResponse res) {
        long id = ServletRequestUtils.getLongParameter(request, "id", 0);
        if (id <= 0) {
            return StageHelper.failForward("参数提交错误", res);
        }
        ProductDetail detail = getDetail(request);
        detail.setId(id);
        Product product = getProduct(request);
        product.setId(id);
        productService.update(product);
        productService.update(detail);
        return StageHelper.successForward("保存成功","/product/list",  res);
    }

    private ProductDetail getDetail(HttpServletRequest request) {
        String company = ServletRequestUtils.getStringParameter(request, "company", "");
        String decorate = ServletRequestUtils.getStringParameter(request, "decorate", "");
        String houseCount = ServletRequestUtils.getStringParameter(request, "houseCount", "");
        String plotRatio = ServletRequestUtils.getStringParameter(request, "plotRatio", "");
        String greeningRate = ServletRequestUtils.getStringParameter(request, "greeningRate", "");
        String parkingSpace = ServletRequestUtils.getStringParameter(request, "parkingSpace", "");
        String video = ServletRequestUtils.getStringParameter(request, "video", "");
        String video360 = ServletRequestUtils.getStringParameter(request, "video360", "");

        String age = ServletRequestUtils.getStringParameter(request, "age", "");
        ProductDetail detail = new ProductDetail();
        detail.setCompany(company);
        detail.setDecorate(decorate);
        detail.setHouseCount(houseCount);
        detail.setPlotRatio(plotRatio);
        detail.setGreeningRate(greeningRate);
        detail.setParkingSpace(parkingSpace);
        detail.setVideo(video);
        detail.setAge(age);
        detail.setVideo360(video360);
        return detail;
    }

    @RequestMapping(value = "/del")
    public String del(HttpServletRequest request, HttpServletResponse res) {
        long id = ServletRequestUtils.getLongParameter(request, "id", 0);
        if (id <= 0) {
            return StageHelper.failForward("参数提交错误", res);
        }
        productService.delete(id);
        return StageHelper.successForward("保存成功", res);
    }

    /**
     * 增加经纪人
     *
     * @param request
     * @param res
     * @return
     */
    @RequestMapping(value = "/addBroker")
    @ResponseBody
    public JsonResult addBroker(HttpServletRequest request, HttpServletResponse res) {
        long id = ServletRequestUtils.getLongParameter(request, "pid", 0);
        long userId = ServletRequestUtils.getLongParameter(request, "userId", 0);
        String nickname = ServletRequestUtils.getStringParameter(request, "nickname", "");
        ProductBroker bean = new ProductBroker();
        bean.setPid(id);
        bean.setUserId(userId);
        bean.setNickname(nickname);
        bean.setStatus(ProductBroker.VALID);
        long result = productBrokerService.add(bean);
        if (result < 0) {
            return new JsonResult(false, "已经存在");
        }
        return new JsonResult(true);
    }

    /**
     * 删除经纪人
     *
     * @param request
     * @param res
     * @return
     */
    @RequestMapping(value = "/delBroker")
    @ResponseBody
    public JsonResult delBroker(HttpServletRequest request, HttpServletResponse res) {
        try {
            long id = ServletRequestUtils.getLongParameter(request, "pid", 0);
            String userIdStr = ServletRequestUtils.getStringParameter(request, "userId", "");
            String[] userIdArr = StringUtils.split(userIdStr, ",");
            for (String uid : userIdArr) {
                ProductBrokerKey bean = new ProductBrokerKey();
                bean.setPid(id);
                bean.setUserId(Long.parseLong(uid));
                productBrokerService.delete(bean);
            }
            return new JsonResult(true);
        } catch (Exception e) {
            logger.error("", e);
            return new JsonResult(false, "系统错误");
        }

    }
}
