package com.wc.web.controller.user;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.io.PushbackInputStream;
import java.text.DecimalFormat;

public class OrderExcelService {


    private static final Logger logger = LoggerFactory.getLogger(OrderExcelService.class);

    /**
     * 读取一个Excel文件,从第二行开始读取
     *
     * @param in
     * @return
     * @throws Exception
     */
    public static Sheet readExcel(InputStream in) throws Exception {
        Workbook workbook = null;
        if (!in.markSupported()) {
            in = new PushbackInputStream(in, 8);
        }
        if (POIFSFileSystem.hasPOIFSHeader(in)) {
            workbook = new HSSFWorkbook(in);
        } else {
            throw new IllegalArgumentException("你的excel版本目前poi解析不了");
        }
//		else  if (POIXMLDocument.hasOOXMLHeader(in)) {
//			workbook = new XSSFWorkbook(OPCPackage.open(in));
//		}

        DecimalFormat df = new DecimalFormat("#.##");
        // 开始读取
        // 获得一个Sheet
        Sheet sheet = workbook.getSheetAt(0);
        return sheet;
//		if(sheet==null){
//			return ;
//		}
//		//从第一行开始
//		for (int rowNumOfSheet = 1; rowNumOfSheet <= sheet
//				.getLastRowNum(); rowNumOfSheet++) {
//			Row row = sheet.getRow(rowNumOfSheet);
//			if(row==null){
//				continue;
//			}
//
//			logger.debug("第" + rowNumOfSheet + "行   ");
//
//			for (short cellNumOfRow = 0; cellNumOfRow < row
//					.getLastCellNum(); cellNumOfRow++) {
//
//				Cell cell = row.getCell(cellNumOfRow);
//				int cellType=-1;
//				if(cell!=null){
//					cellType = cell.getCellType();
//				}
//				switch (cellType) {
//				case HSSFCell.CELL_TYPE_NUMERIC:// Numberic
//					String strCell=null;
//					if (HSSFDateUtil.isCellDateFormatted(cell)) {
//						Date d = cell.getDateCellValue();
//						strCell = DateUtil.getDateTimeStr(d);
//					} else {
//						strCell = df.format(cell.getNumericCellValue());
//					}
//
//					intr.change(cellNumOfRow,strCell,wmsSheet);
//					break;
//				case 1:
//					strCell = cell.getRichStringCellValue()
//							.getString();
//					intr.change(cellNumOfRow,strCell,wmsSheet);
//					break;
//				default:
//					intr.change(cellNumOfRow,"",wmsSheet);
//				}
//			}

    }


}
