package com.candao.member.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.candao.member.service.ReportService;
import com.candao.member.utils.Constant;
import com.candao.member.utils.ExcelUtil;
import com.candao.member.utils.JacksonJsonMapper;
import com.candao.member.utils.SessionUtils;

@Controller
@RequestMapping("/report")
public class ReportController {

	@Autowired
	private ReportService reportService; 
	
	/**
	 * 报表列表数据
	 * @param page
	 * @param rows
	 * @param startTime
	 * @param endTime
	 * @param branchId
	 * @return
	 */
	@RequestMapping("/compositeReport")
    @ResponseBody
	public String compositeReport(@RequestParam(value = "page", defaultValue = "1", required = true) Integer page,
			@RequestParam(value = "rows", defaultValue = "10", required = true) Integer rows,
			@RequestParam(value = "startTime", required = false) String startTime,
            @RequestParam(value = "endTime", required = false) String endTime,
            @RequestParam(value = "branchId", required = false) String branchId){
		Map<String, Object> param = new HashMap<>();
		param.put("startTime", startTime);
		param.put("endTime", endTime);
		param.put("branchId", branchId);
		param.put("tenantId", SessionUtils.get(Constant.CURRENT_TENANT_ID).toString());
		Map<String, Object> result = reportService.grid(param, page, rows);
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("data", result.get("reportdate"));
		resultMap.put("records", result.get("records"));//总记录数
		resultMap.put("total", result.get("total"));
		String resultdata = JacksonJsonMapper.objectToJson(resultMap);
		return resultdata;
	}

	/**
	 * 报表汇总
	 * @param startTime
	 * @param endTime
	 * @param branchId
	 * @return
	 */
	@RequestMapping("/reportCollect")
    @ResponseBody
	public String reportCollect(
			@RequestParam(value = "startTime", required = false) String startTime,
            @RequestParam(value = "endTime", required = false) String endTime,
            @RequestParam(value = "branchId", required = false) String branchId){
		Map<String, Object> param = new HashMap<>();
		param.put("startTime", startTime);
		param.put("endTime", endTime);
		param.put("branchId", branchId);
		param.put("tenantId", SessionUtils.get(Constant.CURRENT_TENANT_ID).toString());
		Map<String, Object> result = reportService.reportCollect(param);
		return JacksonJsonMapper.objectToJson(result);
	}
	
	/**
	 * 导出报表
	 * @param request
	 * @param response
	 */
	@RequestMapping("/exportReport")
    @ResponseBody 
	public void exportReport(HttpServletRequest request, HttpServletResponse response){
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		String branchId = request.getParameter("branchId");
		String tenantId = SessionUtils.get(Constant.CURRENT_TENANT_ID).toString();
		Map<String, Object> param = new HashMap<>();
		param.put("startTime", startTime);
		param.put("endTime", endTime);
		param.put("branchId", branchId);
		param.put("tenantId", tenantId);
		List<Map<String, Object>> reposts = reportService.getReportsList(param);
		Map<String, Object> report = reportService.reportCollect(param);
		String tableHead[] = { "卡号", "客户名称", "手机号", "会员卡名称", "累计获得积分", "累计消费积分", "累计储值金额", "累计赠送金额", "累计消费金额", "累计消费次数" };
		String mes = "综合报表";
		String datatime = "时间: " + startTime + " --- " + endTime;
		String newCardCount = "新增会员卡张数：" + report.get("newcardcount") +"张";
		double newMemberIncome = Double.parseDouble(report.get("newMemberIncome").toString());
		double oldMemaberIncome = Double.parseDouble(report.get("oldMemaberIncome").toString());
		double memberIncome = newMemberIncome + oldMemaberIncome;
		String income = "会员总收入：" + memberIncome +"元    (新会员收入:"+ newMemberIncome +"元    老会员收入：" + oldMemaberIncome +"元)";
		
		double newMemberPresent = Double.parseDouble(report.get("newMemberPresent").toString());
		double oldMemberPresent = Double.parseDouble(report.get("oldMemberPresent").toString());
		double memberPresent = newMemberPresent + oldMemberPresent;
		String present = "赠送金额 : " + memberPresent +"元    (新会员赠送 :" + newMemberPresent +"元    老会员赠送 :" + oldMemberPresent +"元)";
	
		String cash = report.get("cashPay").toString();
		String bank = report.get("bankPay").toString();
		String weChat = report.get("weChatPay").toString();
		String alipay = report.get("alipay").toString();
		String detaile = "详细：(现金收入："+cash+"元    银联收入 :"+bank+"元    微信收入 :"+weChat+"元    支付宝收入 :"+alipay+"元)";
	
		String valueConsumption = report.get("valueConsumption").toString();
		String consumption = "消费金额：" + valueConsumption + "元";
		
		String consumptionCount = report.get("consumptionCount").toString();
		String count = "消费次数：" + consumptionCount +"次";
		
		HSSFWorkbook workbook = ExcelUtil.createReportExl(tableHead, mes, datatime, newCardCount, 
				income, present, detaile, consumption, count, reposts);
		String filename = "report "+startTime+"----"+endTime+".xls";
		response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Type", "text/html;charset=UTF-8");
        response.setHeader("Content-disposition", "attachment;filename="+filename);
        OutputStream ouputStream = null;
        try {
            ouputStream = response.getOutputStream();
            workbook.write(ouputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                ouputStream.flush();
                ouputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
	}
}
