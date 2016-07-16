package com.wc.web.controller.user;

import com.wc.user.bean.Community;
import com.wc.user.bean.Member;
import com.wc.user.bean.MemberDTO;
import com.wc.user.bean.WaterRate;
import com.wc.user.service.CommunityService;
import com.wc.user.service.MemberService;
import com.wc.user.service.WaterRateService;
import com.wc.web.until.Constant;
import com.wc.web.until.PoiExcelUtil;
import com.wc.web.until.StageHelper;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RequestMapping(value = "/waterRateExcel")
@Controller
public class WaterRateExcelController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    final static String exportPath = "/data/www/exportfiles/";
    public static final String PAY="已缴费";
    public static final String NOT_PAY="未缴费";
    @Autowired
    private MemberService memberService;
    @Autowired
    private WaterRateService waterRateService;
    @Autowired
    private CommunityService communityService;

    @RequestMapping(value = "/excel")
    public String excel(Model model, HttpServletRequest request) {
        List<Community> list = communityService.list(null, null);
        model.addAttribute("list", list);
        return "/waterRate/excel";
    }
    /**
     * 导出报表
     *  @param request
     * @param response
     */
    @RequestMapping("/out")
    public void out(HttpServletRequest request, HttpServletResponse response,
                    @RequestParam Long communityId, @RequestParam String month) {


        Community community = communityService.getById(communityId);
        if (null == community) {
            StageHelper.failForward("小区不存在", response);
            return;
        }
        Member query = new Member();
        query.setCommunityId(communityId);
        List<MemberDTO> list = memberService.listWithDetail(query);
        try {
            String name = community.getName() + month + "水费";
//            DateUtils.
            // 创建一个EXCEL
            HSSFWorkbook wb = new HSSFWorkbook();
            // 创建一个SHEET
            HSSFSheet sheet = wb.createSheet(name);
            String[] titles = {"编码", "用户编码", "用户名称", "用户手机",
                    "房号", "应缴月份", "缴费日期", "水费",
                    "用水量", "污水处理费", "水资源费", "单价",
                    "其他费用","缴费状态"};

            PoiExcelUtil.buildTitles(sheet, titles);
            int rowNum = 1;//新行号
            for (int i = 0; i < list.size(); i++) {
                MemberDTO detail = list.get(i);
                if (detail == null) {
                    continue;
                }
                HSSFRow rowdata = sheet.createRow(rowNum);
                // 下面是填充数据
                int k = 0;
                String uuid = detail.getId() + "-" + communityId + "-" + month;
                rowdata.createCell(k++).setCellValue(uuid);
                rowdata.createCell(k++).setCellValue(detail.getId()+"");
                rowdata.createCell(k++).setCellValue(detail.getNickname());
                rowdata.createCell(k++).setCellValue(detail.getMobile());
                rowdata.createCell(k++).setCellValue(detail.getAddress());
                rowdata.createCell(k++).setCellValue(month);
                //7
                rowdata.createCell(k++).setCellValue("");
                rowdata.createCell(k++).setCellValue("");
                rowdata.createCell(k++).setCellValue("");
                //10
                rowdata.createCell(k++).setCellValue("");
                rowdata.createCell(k++).setCellValue("");
                rowdata.createCell(k++).setCellValue("");
                rowdata.createCell(k++).setCellValue("");
                rowdata.createCell(k++).setCellValue("");
                rowNum++;
            }

            write(response, wb);
        } catch (Exception e) {
            logger.error("", e);

        }
    }

    /**
     * 导出报表
     *
     * @param request
     * @param response
     */
    @RequestMapping("/in")
    public void multiUpload(MultipartHttpServletRequest request,
                            HttpServletResponse response) {

        try {

            MultiValueMap<String, MultipartFile> map = request.getMultiFileMap();
            Sheet sheet = null;
            for (String key : map.keySet()) {
                List<MultipartFile> list = map.get(key);
                for (MultipartFile multipartFile : list) {
                    sheet = OrderExcelService.readExcel(multipartFile.getInputStream());
                }
            }
            if (null == sheet) {
                return;
            }
            List<WaterRate> list = new ArrayList<WaterRate>();
            for (int rowNumOfSheet = 1; rowNumOfSheet <= sheet
                    .getLastRowNum(); rowNumOfSheet++) {
                Row row = sheet.getRow(rowNumOfSheet);
                if (row == null) {
                    continue;
                }
                logger.debug("第" + rowNumOfSheet + "行   ");
//               编码", "用户编码", "用户名称", "用户手机", "房号", "应缴月份",
// "缴费日期", "水费", "用水量", "污水处理费", "水资源费", "单价", "其他费用"
                WaterRate waterRate=getWateRate(row);
                list.add(waterRate);
            }
            waterRateService.addOrUpdate(list);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error("", e);
        }
    }

    private WaterRate getWateRate(Row row) {
        int k=0;
        String uuid = row.getCell(k++).getStringCellValue();
        String commuId = StringUtils.substringBetween(uuid,"-","-");
        String idStr = row.getCell(k++).getStringCellValue();
        Long userId = NumberUtils.toLong(idStr);
        String nickname = getString(row.getCell(k++));
        String mobile = getString(row.getCell(k++));
        String address = getString(row.getCell(k++));
        String month =row.getCell(k++).getStringCellValue();
        String payTime =row.getCell(k++).getStringCellValue();
        BigDecimal amount = getBigDecimal(row.getCell(k++));
        BigDecimal count = getBigDecimal(row.getCell(k++));
        BigDecimal gb = getBigDecimal(row.getCell(k++));
        BigDecimal res = getBigDecimal(row.getCell(k++));
        BigDecimal price = getBigDecimal(row.getCell(k++));
        BigDecimal other = getBigDecimal(row.getCell(k++));
        String status = getString(row.getCell(k++));
        WaterRate waterRate = new WaterRate();
        waterRate.setUuid(uuid);
        waterRate.setUserId(userId);
        waterRate.setAmount(amount);
        waterRate.setTotal(count);
        waterRate.setCreateTime(new Date());
        waterRate.setSewageAmount(gb);
        waterRate.setResourceAmount(res);
        waterRate.setPrice(price);
        waterRate.setOther(other);
        waterRate.setCommunityId(Long.parseLong(commuId));
        if(PAY.equals(status)){
            waterRate.setStatus(WaterRate.PAY);
        }else {
            waterRate.setStatus(WaterRate.NOT_PAY);
        }
        try {
            Date monthDate = DateUtils.parseDate(month, Constant.pattern);
            waterRate.setMonth(monthDate);
            Date payDate = DateUtils.parseDate(payTime, Constant.pattern);
            waterRate.setPayTime(payDate);
        } catch (ParseException e) {
            logger.error("", e);
        }

        return waterRate;

    }

    private String getString(Cell cell) {

        if(null==cell|| StringUtils.isBlank(cell.getStringCellValue())){
            return "";
        }else {
            return cell.getStringCellValue();
        }

    }

    private BigDecimal getBigDecimal(Cell cell) {

        if(Cell.CELL_TYPE_NUMERIC==cell.getCellType()){
            if(null==cell){
                return BigDecimal.ZERO;
            }else {
                return new BigDecimal(cell.getNumericCellValue());
            }
        }
        if(Cell.CELL_TYPE_STRING==cell.getCellType()){
            if(null==cell||StringUtils.isBlank(cell.getStringCellValue())){
                return BigDecimal.ZERO;
            }else {
                return new BigDecimal(cell.getStringCellValue());
            }
        }
        return BigDecimal.ZERO;
    }

    private void write(HttpServletResponse response, HSSFWorkbook workbook) {
        // 创建一个新文件
        OutputStream os = null;
        try {
            String name = new Date().getTime() + "";
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-disposition", "attachment;filename=" + name + ".xls");
            os = response.getOutputStream();
            workbook.write(os);

        } catch (IOException e) {
            // TODO Auto-generated catch block
            logger.error("", e);
        } finally {
            try {
                os.flush();
                os.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
    }


}
