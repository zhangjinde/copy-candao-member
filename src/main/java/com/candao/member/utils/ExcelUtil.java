package com.candao.member.utils;

import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;

public class ExcelUtil {

	/**
	 * 
	 * @param tableHead
	 *            表格描述信息
	 * @param fileName
	 *            文件名称
	 * @param mes
	 *            描述
	 * @param datatime
	 *            时间段
	 * @param dealMes
	 *            交易消费
	 * @param dataList
	 *            数据集合
	 * @return
	 */
	public static HSSFWorkbook createExl(String tableHead[], String fileName, String mes, String datatime,
			String dealMes, List<List<String>> dataList) {
		// 生成表格信息
		HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
		HSSFCellStyle style = hssfWorkbook.createCellStyle(); // 样式对象
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 水平

		HSSFSheet sheet = hssfWorkbook.createSheet();
		sheet.setDefaultColumnWidth(20);
		// 为了工作表能支持中文，设置字符编码为UTF_16
		hssfWorkbook.setSheetName(0, fileName);

		// 计算下一次 目标位置
		int inxRow = 0;
		// 夸行跨列设置
		CellRangeAddress cra = null;
		// 开始默认三列为固有的表示信息
		for (int i = 0; i < 3; i++) {

			inxRow = i * 2 + i;
			Row row = sheet.createRow(inxRow);
			// 固有头信息表示,默认夸三列
			cra = new CellRangeAddress(inxRow, inxRow + 2, 0, tableHead.length - 1);
			// 在sheet增加合并单元格操作
			sheet.addMergedRegion(cra);

			Cell cell = row.createCell(0);
			String inputMes = i == 0 ? mes : i == 1 ? datatime : dealMes;
			cell.setCellValue(inputMes);
			cell.setCellStyle(style);

		}
		// 表格显示数据
		HSSFRow row = sheet.createRow(inxRow += 3);
		for (int i = 0; i < tableHead.length; i++) {
			HSSFCell cell = row.createCell(i);
			cell.setCellValue(tableHead[i]);
			cell.setCellStyle(style);
		}
		// 填充表格数据
		for (int i = 0; i < dataList.size() && dataList != null; i++) {
			// 外部循环创建行或展示数据个数
			HSSFRow rowData = sheet.createRow(inxRow + i + 1);
			List<String> oneData = dataList.get(i);
			for (int j = 0; j < tableHead.length; j++) {
				// 内部循环创建列，获取展示数据具体消息
				HSSFCell cell = rowData.createCell(j);
				cell.setCellValue(j == 0 ? String.valueOf(i + 1) : oneData.get(j - 1));
				cell.setCellStyle(style);
			}

		}
		return hssfWorkbook;
	}

	/**
	 * 创建excel文档，
	 * 
	 * @param list
	 *            数据
	 * @param keys
	 *            list中map的key数组集合
	 * @param columnNames
	 *            excel的列名
	 */
	@SuppressWarnings("deprecation")
	public static Workbook createWorkBook(List<Map<String, Object>> list, String[] keys, String columnNames[],
			String title) {
		// 创建excel工作簿
		Workbook wb = new HSSFWorkbook();
		// 创建第一个sheet（页），并命名
		HSSFSheet sheet = (HSSFSheet) wb.createSheet("会员信息");
		// 手动设置列宽。第一个参数表示要为第几列设；，第二个参数表示列的宽度，n为列高的像素数。
		for (int i = 0; i < keys.length; i++) {
			sheet.setColumnWidth((short) i, (short) (35.7 * 150));
		}
//		sheet.addMergedRegion(new Region(0, (short) 0, 0, (short) 16));// new Region(rowFrom,colFrom,rowTo,colTo));指定合并区域
		// 创建第一行
//		Row rowTitle = sheet.createRow((short) 0);
//		Cell cell1 = rowTitle.createCell(0);
//		cell1.setCellValue(title);

		// 创建两种单元格格式
		CellStyle cs = wb.createCellStyle();
		CellStyle cs2 = wb.createCellStyle();

		// 创建两种字体
		Font f = wb.createFont();
		Font f2 = wb.createFont();

		// 创建第一种字体样式（用于列名）
		f.setFontHeightInPoints((short) 10);
		f.setColor(IndexedColors.BLACK.getIndex());
		f.setBoldweight(Font.BOLDWEIGHT_BOLD);

		// 创建第二种字体样式（用于值）
		f2.setFontHeightInPoints((short) 10);
		f2.setColor(IndexedColors.BLACK.getIndex());

		// Font f3=wb.createFont();
		// f3.setFontHeightInPoints((short) 10);
		// f3.setColor(IndexedColors.RED.getIndex());

		// 设置第一种单元格的样式（用于列名）
		cs.setFont(f);
		cs.setBorderLeft(CellStyle.BORDER_THIN);
		cs.setBorderRight(CellStyle.BORDER_THIN);
		cs.setBorderTop(CellStyle.BORDER_THIN);
		cs.setBorderBottom(CellStyle.BORDER_THIN);
		cs.setAlignment(CellStyle.ALIGN_CENTER);

		// 设置第二种单元格的样式（用于值）
		cs2.setFont(f2);
		cs2.setBorderLeft(CellStyle.BORDER_THIN);
		cs2.setBorderRight(CellStyle.BORDER_THIN);
		cs2.setBorderTop(CellStyle.BORDER_THIN);
		cs2.setBorderBottom(CellStyle.BORDER_THIN);
		cs2.setAlignment(CellStyle.ALIGN_CENTER);
		
		//创建第一行 列名
		Row row = sheet.createRow((short) 0);
		// 设置列名
		for (int i = 0; i < columnNames.length; i++) {
			Cell cell = row.createCell(i);
			cell.setCellValue(columnNames[i]);
			cell.setCellStyle(cs);
		}
		// 设置每行每列的值
		for (short i = 1; i < list.size() + 1; i++) {
			// Row 行,Cell 方格 , Row 和 Cell 都是从0开始计数的
			// 创建一行，在页sheet上
			Row row1 = sheet.createRow((short) i);
			// 在row行上创建一个方格
			for (short j = 0; j < keys.length; j++) {
				Cell cell = row1.createCell(j);
				cell.setCellValue(list.get(i - 1).get(keys[j]) == null ? " " : list.get(i - 1).get(keys[j]).toString());
				cell.setCellStyle(cs2);
			}
		}
		return wb;
	}
	
	/**
	 * @param tableHead	:表格头信息
	 * @param mes			：描述
	 * @param datatime		： 时间段
	 * @param newCardCount	：新增会员卡总数
	 * @param income		：会员总收入
	 * @param present		：赠送金额 
	 * @param detaile		：详细
	 * @param consumption	：消费金额 
	 * @param count			：消费次数 
	 * @param reposts 		: 报表列表数据
	 * @return
	 */
	public static HSSFWorkbook createReportExl(String tableHead[], String mes, String datatime, 
			String newCardCount, String income, String present, String detaile, 
			String consumption, String count, List<Map<String, Object>> reposts){
		// 生成表格信息
		HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
		HSSFCellStyle style = hssfWorkbook.createCellStyle(); // 样式对象
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 水平

		HSSFSheet sheet = hssfWorkbook.createSheet();
		sheet.setDefaultColumnWidth(15);
		// 为了工作表能支持中文，设置字符编码为UTF_16
		hssfWorkbook.setSheetName(0, "综合报表");
		// 计算下一次 目标位置
		int inxRow = 0;
		// 夸行跨列设置
		CellRangeAddress cra = null;
		// 开始默认三列为固有的表示信息
		for (int i = 0; i < 8; i++) {
			inxRow = i * 2 + i;
			Row row = sheet.createRow(inxRow);
			// 固有头信息表示,默认夸三列
			cra = new CellRangeAddress(inxRow, inxRow + 2, 0, tableHead.length - 1);
			// 在sheet增加合并单元格操作
			sheet.addMergedRegion(cra);

			Cell cell = row.createCell(0);
			String inputMes = "";
			switch (i) {
			case 0:
				inputMes = mes;
				break;
			case 1:
				inputMes = datatime;
				break;
			case 2:
				inputMes = newCardCount;
				break;
			case 3:
				inputMes = income;
				break;
			case 4:
				inputMes = present;
				break;
			case 5:
				inputMes = detaile;
				break;
			case 6:
				inputMes = consumption;
				break;
			case 7:
				inputMes = count;
				break;
			default:
				break;
			}
			cell.setCellValue(inputMes);
			cell.setCellStyle(style);
		}
		// 表格显示数据
		HSSFRow row = sheet.createRow(inxRow += 3);
		for (int i = 0; i < tableHead.length; i++) {
			HSSFCell cell = row.createCell(i);
			cell.setCellValue(tableHead[i]);
			cell.setCellStyle(style);
		}
		// 填充表格数据
		for (int i = 0; i < reposts.size() && reposts != null; i++) {
			// 外部循环创建行或展示数据个数
			HSSFRow rowData = sheet.createRow(inxRow + i + 1);
			Map<String, Object> report = reposts.get(i);
			for(int j = 0; j < report.keySet().size(); j++){
				HSSFCell cell = rowData.createCell(j);
				if(j == 0){
					if(null != report.get("cardno")){
						cell.setCellValue(report.get("cardno").toString());
					}else{
						cell.setCellValue("");
					}
				}else if(j==1){
					if(null != report.get("name")){
						cell.setCellValue(report.get("name").toString());
					}else{
						cell.setCellValue("");
					}
				}else if(j==2){
					if(null != report.get("mobile")){
						cell.setCellValue(report.get("mobile").toString());
					}else{
						cell.setCellValue("");
					}
				}else if(j==3){
					if(null != report.get("discount")){
						cell.setCellValue(report.get("discount").toString());
					}else{
						cell.setCellValue("");
					}
				}else if(j==4){
					cell.setCellValue(report.get("allPoint").toString());
				}else if(j==5){
					cell.setCellValue(report.get("allConsumePoint").toString());
				}else if(j==6){
					cell.setCellValue(report.get("allValue").toString());
				}else if(j==7){
					cell.setCellValue(report.get("allPresent").toString());
				}else if(j==8){
					cell.setCellValue(report.get("allConsumeValue").toString());
				}else if(j==9){
					cell.setCellValue(report.get("countconsume").toString());
				}
				cell.setCellStyle(style);
			}
			
		}
		return hssfWorkbook;
	}
}
