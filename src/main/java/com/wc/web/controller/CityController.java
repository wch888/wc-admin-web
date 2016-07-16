package com.wc.web.controller;

import com.wc.base.bean.BasisCity;
import com.wc.base.service.BasisCityService;
import com.wc.user.service.CommunityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 区域管理
 */
@Controller
@RequestMapping(value = "/city")
public class CityController {
    private static final Logger logger = LoggerFactory.getLogger(CityController.class);

    @Autowired
    private BasisCityService basisCityService;
    @Autowired
    private CommunityService communityService;


    @RequestMapping(value = "/getByParentId", method = RequestMethod.GET)
    @ResponseBody
    public List<BasisCity> getByParentId(@RequestParam int parentCode) {

        List<BasisCity> list = basisCityService.getAreasByParentId(parentCode);
        return list;
    }

}
