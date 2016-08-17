package com.candao.member.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.candao.member.dao.impl.Page;
import com.candao.member.enums.DealTypeEnum;
import com.candao.member.model.TbMemberInfo;
import com.candao.member.model.TbTenantInfo;
import com.candao.member.service.DealService;
import com.candao.member.service.MemberInfoService;
import com.candao.member.service.TenantService;
import com.candao.member.service.membermanage.MemberManageService;
import com.candao.member.utils.Constant;
import com.candao.member.utils.ConvertUtils;
import com.candao.member.utils.DateUtils;
import com.candao.member.utils.DealTypeUtil;
import com.candao.member.utils.ExcelUtil;
import com.candao.member.utils.ExportExcel;
import com.candao.member.utils.JSONUtils;
import com.candao.member.utils.MemberSwtichCardNOUtil;
import com.candao.member.utils.SessionUtils;
import com.candao.member.vo.DealDetailVO;
import com.candao.member.vo.TransInfoVo;

import net.sf.json.JSONObject;

/**
 * 会员消费信息
 * 
 * @author 001
 */

@Controller
@RequestMapping("/consumption")
public class ConsumptionController {

    @Autowired
    private DealService dealService;

    @Autowired
    private MemberInfoService memberInfoService;

    @Autowired
    private MemberManageService memberManageService;
    
    @Autowired
    private TenantService tenantService;
    
    private static Logger log = LoggerFactory.getLogger(ConsumptionController.class);

    @RequestMapping("/index")
    @ResponseBody
    public ModelAndView index(@RequestParam(value = "current", defaultValue = "1") Integer current, String starttime,
            String endtime, @RequestParam(value = "timefalg", defaultValue = "1") String timefalg,
            @RequestParam(value = "falg", defaultValue = "1") String falg) { // falg=1会员卡充值，falg=2会员卡消费
        ModelAndView mav = new ModelAndView("consumption/consumption");
        String tenantId = SessionUtils.get(Constant.CURRENT_TENANT_ID).toString();
        Map<String, Object> timeMap = getTimeMap(timefalg, starttime, endtime);
        Map<String, Object> param = new HashMap<>();
        param.put("falg", falg);
        param.putAll(timeMap);
        param.put("tenantId", tenantId);

        List<TbMemberInfo> memberInfos = memberInfoService.getAllMmberByTenantId(tenantId);
        if (memberInfos.size() == 0) {
            mav.addObject("current", current);
            mav.addObject("starttime", starttime);
            mav.addObject("endtime", endtime);
            mav.addObject("falg", falg);
            mav.addObject("timefalg", timefalg);
            return mav;
        }
        Map<String, Object> map = dealService.getDealDetail(memberInfos, param, current, 10);// 每页显示10条数据
        List<DealDetailVO> dealDetailVOs = (List<DealDetailVO>) map.get("detailVOs");
        Page<Map<String, Object>> page = (Page<Map<String, Object>>) map.get("page");

        for(DealDetailVO temp : dealDetailVOs){
        	String cardno = temp.getCard_no();
        	int cardclass = temp.getCard_class();
        	if(cardclass==0){//虚拟卡
        		temp.setCard_no("-");
        		continue;
        	}
        	if(cardno != null && cardno != ""){
    			temp.setCard_no(cardno.length()==30?cardno.substring(cardno.indexOf(tenantId)+tenantId.length()):cardno);
    		}
        }
        mav.addObject("detail", dealDetailVOs);
        mav.addObject("current", current);
        mav.addObject("total", page.getTotal());
        mav.addObject("totalpage", page.getPageCount());
        mav.addObject("pagesize", 10);
        mav.addAllObjects(timeMap);
        mav.addObject("falg", falg);
        mav.addAllObjects(map);
        mav.addObject("timefalg", timefalg);
        return mav;
    }
    
    @RequestMapping("/transInfo")
    public ModelAndView transInfo(HttpServletRequest request,HttpServletResponse response) {
        log.info("ConsumptionController--transInfo");
        ModelAndView mv = new ModelAndView("consumptionInfo/consumptionInfo");
        return mv;
    }

    @RequestMapping("/transInfoSubmit")
    @ResponseBody
    public Object transInfo(@RequestParam(value = "page", defaultValue = "1", required = true) Integer page,
            @RequestParam(value = "rows", defaultValue = "10", required = true) Integer rows,
            @RequestParam(value = "startTime", required = false) String startTime,
            @RequestParam(value = "endTime", required = false) String endTime,
            @RequestParam(value = "orderId", required = false) String orderId,
            @RequestParam(value = "memberName", required = false) String memberName,
            @RequestParam(value = "memberCardNo", required = false) String memberCardNo,
            @RequestParam(value = "phone", required = false) String phone,
            @RequestParam(value = "branchId", required = false) String branchId,
            @RequestParam(value = "cardType", required = false) String cardType,
            HttpServletRequest request) {
        log.info("ConsumptionController--transInfo page={},rows={},startTime={},endTime={},orderId={},memberName={},memberCardNo={},phone={},branchId={},cardType={}.",
                page,rows,startTime,endTime,orderId,memberName,memberCardNo,phone,branchId,cardType);
        Map<String, Object> param = new HashMap<>();
        String tenantId = SessionUtils.get(Constant.CURRENT_TENANT_ID).toString();
        //UserCacheBean ucb = PermissionCollection.getInstance().getUserCache(request.getSession().getId());
        //String tenantId=ucb.getUser().getAccounts();
        int tenantIdLenth = tenantId.length();
        param.put("startTime", startTime);
        param.put("endTime", endTime);
        param.put("orderId", orderId);
        param.put("memberName", memberName);
        if(memberCardNo != null && memberCardNo != ""){
            /*String s = "00000000000000000000000000000000000000000000";
            s = s.substring(0, 30-(tenantId+memberCardNo).length());
            param.put("memberCardNo", s+tenantId+memberCardNo);*/
            param.put("memberCardNo",tenantId+memberCardNo);
        }else{
            param.put("memberCardNo", "");
        }
        param.put("phone", phone);
        param.put("branchId", branchId);
        param.put("cardType", cardType);
        param.put("tenantId", tenantId);

        Page<Map<String, Object>> resultPage = memberManageService.gridTransInfo(param, page, rows);
        Collection<Map<String, Object>> result = resultPage.getRows();
        Map<String,Object> resultMap = new HashMap<>();
        //处理结果
        for (Map<String, Object> temp : result) {
            String storeFee="0";
            String presentFee="0";
            BigDecimal consumeValue=new BigDecimal(0);
            if(temp.get("consumeValue")!=null){
                consumeValue=(BigDecimal) temp.get("consumeValue");
            }
            if(consumeValue.compareTo(BigDecimal.ZERO)==1){
                BigDecimal totalBalance=new BigDecimal(0);
                BigDecimal zstotalBalance=new BigDecimal(0);
                if(temp.get("totalBalance")!=null){
                    totalBalance=(BigDecimal) temp.get("totalBalance");
                }
                if(temp.get("zstotalBalance")!=null){
                    zstotalBalance=(BigDecimal) temp.get("zstotalBalance");
                }
                BigDecimal total=totalBalance.add(zstotalBalance);
                if(total.compareTo(BigDecimal.ZERO)==1){
                  BigDecimal cardRate=totalBalance.divide(total, 2, BigDecimal.ROUND_HALF_UP);
                  DecimalFormat df = new DecimalFormat("0.00");  
                  storeFee=df.format(consumeValue.multiply(cardRate).doubleValue());
                  Double presentFeeTmp=(consumeValue.doubleValue()-Double.valueOf(storeFee));
                  presentFee=df.format(presentFeeTmp);
                }else{
                    storeFee=consumeValue.toString();
                }
            }
            String dealDate=temp.get("dealTime").toString();
            temp.put("dealTime",dealDate.substring(0, dealDate.lastIndexOf(".")));
            String cardnoTemp =  temp.get("cardNo").toString();
            temp.put("cardNo", MemberSwtichCardNOUtil.getCardNo(tenantId, temp.get("branchId").toString(), cardnoTemp));
            temp.put("totalBalance", storeFee);
            temp.put("zstotalBalance", presentFee);
        }
        resultMap.put("resultList", result);
        resultMap.put("page", page);
        resultMap.put("records", resultPage.getTotal());//总记录数
        resultMap.put("total", resultPage.getPageCount());//总页数
        String jsonReturn=JSONUtils.mapToJson(resultMap);
        return jsonReturn;
    }

    @SuppressWarnings("rawtypes")
    @RequestMapping("/exportTransInfo")
    @ResponseBody
    public Object exportTransInfo(HttpServletRequest request, HttpServletResponse response) {
        HashMap<String,Object> returnMsg= new HashMap<String,Object>();
        try {
            HashMap<String,Object> param=new HashMap<String,Object>();
            String tenantId = SessionUtils.get(Constant.CURRENT_TENANT_ID).toString();
            int tenantIdLenth = tenantId.length();
            String queryInfo = request.getParameter("id");
            JSONObject object = JSONObject.fromObject(queryInfo);
            Map<String,Object> queryInfoMap = new HashMap<>();
            Iterator it  = object.keys();
            while(it.hasNext()) {  
               String key = it.next().toString();
               queryInfoMap.put(key, object.get(key).equals("")?"":object.get(key));
            }  
            if(queryInfoMap.get("memberCardNo").toString() != ""){
                String s = "00000000000000000000000000000000000000000000";
                s = s.substring(0, 30-(tenantId+queryInfoMap.get("memberCardNo").toString()).length());
                param.put("memberCardNo", tenantId+queryInfoMap.get("memberCardNo").toString());//会员卡号
            }else{
                param.put("memberCardNo", "");
            }
            param.put("startTime", queryInfoMap.get("startTime").toString());
            param.put("endTime", queryInfoMap.get("endTime").toString());
            param.put("orderId", queryInfoMap.get("orderId").toString());
            param.put("memberName", queryInfoMap.get("memberName").toString());
            param.put("phone", queryInfoMap.get("phone").toString());
            param.put("branchId", queryInfoMap.get("branchId").toString());
            param.put("cardType", queryInfoMap.get("cardType").toString());
            param.put("tenantId", tenantId);
            
            //拼接导出时间段名
            String beginName=queryInfoMap.get("startTime").toString();
            String endName=queryInfoMap.get("endTime").toString();
            String fileNamePart="";
            if((!beginName.isEmpty())&&(!endName.isEmpty())){
                fileNamePart=DateUtils.toString(DateUtils.parse(beginName))+"至"+DateUtils.toString(DateUtils.parse(endName));
            }
            String fileName = "消费明细数据"+fileNamePart+".xlsx";
            List<Map<String, Object>> result = memberManageService.getTransInfoList(param);
            List<TransInfoVo> dataList=new ArrayList<TransInfoVo>();
            for (int i = 0; i <result.size(); i++) {
                Map<String, Object> temp=result.get(i);
                String storeFee="0";
                String presentFee="0";
                BigDecimal consumeValue=new BigDecimal(0);
                if(temp.get("consumeValue")!=null){
                    consumeValue=(BigDecimal) temp.get("consumeValue");
                }
                if(consumeValue.compareTo(BigDecimal.ZERO)==1){
                    BigDecimal totalBalance=new BigDecimal(0);
                    BigDecimal zstotalBalance=new BigDecimal(0);
                    if(temp.get("totalBalance")!=null){
                        totalBalance=(BigDecimal) temp.get("totalBalance");
                    }
                    if(temp.get("zstotalBalance")!=null){
                        zstotalBalance=(BigDecimal) temp.get("zstotalBalance");
                    }
                    BigDecimal total=totalBalance.add(zstotalBalance);
                    if(total.compareTo(BigDecimal.ZERO)==1){
                      BigDecimal cardRate=totalBalance.divide(total, 2, BigDecimal.ROUND_HALF_UP);
                      DecimalFormat df = new DecimalFormat("0.00");  
                      storeFee=df.format(consumeValue.multiply(cardRate).doubleValue());
                      Double presentFeeTmp=(consumeValue.doubleValue()-Double.valueOf(storeFee));
                      presentFee=df.format(presentFeeTmp);
                    }else{
                        storeFee=consumeValue.toString();
                    }
                }
                String dealDate=temp.get("dealTime").toString();
                temp.put("dealTime",dealDate.substring(0, dealDate.lastIndexOf(".")));
                String cardnoTemp =  temp.get("cardNo").toString();
                temp.put("cardNo", MemberSwtichCardNOUtil.getCardNo(tenantId, temp.get("branchId").toString(), cardnoTemp));
                temp.put("totalBalance", storeFee);
                temp.put("zstotalBalance", presentFee);
                
                Object cardType=temp.get("cardType");
                if(cardType!=null&&!cardType.toString().isEmpty()){
                    if(cardType.toString().equals("0")){
                        temp.put("cardNo","-");
                    }
                    temp.put("cardType",cardType.toString()=="0"?"虚拟卡":"实体卡");
                }else{
                    temp.put("cardType", "");
                }
                
                TransInfoVo tiv=(TransInfoVo) ConvertUtils.convertMap(TransInfoVo.class, temp);
                tiv.setTotalBalance(BigDecimal.valueOf(Double.valueOf(storeFee)).setScale(2, BigDecimal.ROUND_HALF_UP).toString());
                tiv.setZstotalBalance(BigDecimal.valueOf(Double.valueOf(presentFee)).setScale(2, BigDecimal.ROUND_HALF_UP).toString());
                dataList.add(tiv);
            }
            new ExportExcel("消费明细数据", TransInfoVo.class).setDataList(dataList).write(request,response, fileName).dispose();
            returnMsg.put("message","导出消费明细数据成功");
        } catch (Exception e) {
            log.error("导出数据异常",e);
            returnMsg.put("message","导出消费明细数据异常,请重试");
        }
        return JSONUtils.mapToJson(returnMsg);
    }

    @RequestMapping("/exportMemberDeal")
    @ResponseBody
    public void exportMemberDeal(HttpServletRequest request, HttpServletResponse response,
            @RequestBody String jsonString) {
        // 開始時間
        String startTime = request.getParameter("starttime");
        // 结束时间
        String endTime = request.getParameter("endtime");
        // 会员充值，还是会员消费//falg=1会员卡充值，falg=2会员卡消费
        String falg = request.getParameter("falg");
        // 当前页
        String current = request.getParameter("current");
        //
        String timefalg = request.getParameter("timefalg");
        String tenantId = SessionUtils.get(Constant.CURRENT_TENANT_ID).toString();
        Map<String, Object> timeMap = getTimeMap(timefalg, startTime, endTime);
        Map<String, Object> param = new HashMap<>();
        param.put("falg", falg);
        param.putAll(timeMap);
        
        param.put("tenantId", tenantId);//租户id change by 20160714修改sql导出excel时，总金额不等页面金额
        
        List<TbMemberInfo> memberInfos = memberInfoService.getAllMmberByTenantId(tenantId);

        Map<String, Object> map = dealService.getDealDetail(memberInfos, param, 0, 0);// 每页显示10条数据
        List<DealDetailVO> dealDetailVOs = (List<DealDetailVO>) map.get("detailVOs");
        // 展示数据
        String tableHead[] = { "序号", "交易时间", "卡号", "手机号", "姓名", "交易号", "交易类型", "交易值", "票据单号" };
        String mes = falg.trim().equals("1") ? "会员卡充值" : "会员卡消费";
        String datatime = "时间:" + startTime + "---" + endTime;
        String dealMes = "";
        //fileName
        String exlFileName = "";
        if (falg.trim().equals("1")) {
            String cardCZ = String.valueOf(map.get("cardCZ"));
            String cashCZ = String.valueOf(map.get("cashCZ"));
            String presentCZ = String.valueOf(map.get("presentCZ"));
            String cancelTK = String.valueOf(map.get("cancelTK"));
            double dealcash = Double.valueOf(cardCZ) + Double.valueOf(cashCZ) + Double.valueOf(presentCZ)
                    + Double.valueOf(cancelTK);
            dealMes = String.format("会员卡充值总额: %s元    (银行卡充值%s元      现金充值:%s元     储值赠送:%s元     会员注销退款:%s元) ", dealcash,
                    map.get("cardCZ"), map.get("cashCZ"), map.get("presentCZ"), map.get("cancelTK"));
            exlFileName = "会员卡充值";
        } else {
            dealMes = String.format("会员卡消费总额:%s元 ", map.get("cardXF"));
            exlFileName = "会员卡消费";
        }
        //获取数据封装
        SimpleDateFormat datafomat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //展示独立性
        List<List<String>> dataList = new ArrayList<List<String>>();
        for (DealDetailVO dealDetailVO : dealDetailVOs) {
            List<String> dataOneList = new ArrayList<String>();
            dataOneList.add(datafomat.format(dealDetailVO.getDeal_time()));
            int cardclass = dealDetailVO.getCard_class();
            String cardno = dealDetailVO.getCard_no();
        	if(cardclass==0){//虚拟卡
        		dealDetailVO.setCard_no("-");
        	}else{
        		dealDetailVO.setCard_no(cardno.length()==30?cardno.substring(cardno.indexOf(tenantId)+tenantId.length()):cardno);
        	}
            dataOneList.add(dealDetailVO.getCard_no());
            dataOneList.add(dealDetailVO.getMemberMobile());
            dataOneList.add(dealDetailVO.getMemberName());
            dataOneList.add(dealDetailVO.getDeal_no());
            dataOneList.add(DealTypeUtil.dealMes(dealDetailVO.getDeal_type()));
            dataOneList.add(String.valueOf(dealDetailVO.getAmount()));
            dataOneList.add(dealDetailVO.getSlip_no().length() > 7 ? dealDetailVO.getSlip_no() : "---");
            dataList.add(dataOneList);
        }

        HSSFWorkbook wb = ExcelUtil.createExl(tableHead, exlFileName, mes, datatime, dealMes, dataList);
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Type", "text/html;charset=UTF-8");
        response.setHeader("Content-disposition", "attachment;filename=member.xls");
        OutputStream ouputStream = null;
        try {
            ouputStream = response.getOutputStream();
            wb.write(ouputStream);
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

    /**
     * 获取查询时间
     * 
     * @param timefalg
     * @return
     */
    private Map<String, Object> getTimeMap(String timefalg, String starttime, String endtime) {
        Map<String, Object> timemap = new HashMap<>();
        if (Integer.parseInt(timefalg) == 1) { // 今天
            timemap.put("starttime", DateUtils.today() + " 00:00:00");
            timemap.put("endtime", DateUtils.today() + " 23:59:59");
        } else if (Integer.parseInt(timefalg) == 2) { // 昨天
            timemap.put("starttime", DateUtils.yesterday() + " 00:00:00");
            timemap.put("endtime", DateUtils.yesterday() + " 23:59:59");
        } else if (Integer.parseInt(timefalg) == 3) { // 本月
            timemap.put("starttime", DateUtils.monthOfFirstDay() + " 00:00:00");
            timemap.put("endtime", DateUtils.monthOfLastDay() + " 23:59:59");
        } else if (Integer.parseInt(timefalg) == 4) { // 上月
            timemap.put("starttime", DateUtils.beforeMonthOfFirstDay() + " 00:00:00");
            timemap.put("endtime", DateUtils.beforeMonthOfLastDay() + " 23:59:59");
        } else {
            timemap.put("starttime", starttime);
            timemap.put("endtime", endtime);
        }
        return timemap;
    }
    
    /**
     * 高级查询获取查询条件信息,会员卡状态
     * 
     */
    @RequestMapping("/findSearchInfo")
    @ResponseBody
    public Object findCardInfo(HttpServletRequest request){
        //UserCacheBean ucb = PermissionCollection.getInstance().getUserCache(request.getSession().getId());
        //String tenantId=ucb.getUser().getAccounts();
        String tenantId = SessionUtils.get(Constant.CURRENT_TENANT_ID).toString();
        Map<String, Object> param = new HashMap<>();
        Map<String, Object> resultMap = new HashMap<>();
        param.put("tenant_id", tenantId);
        Map<String,Object> result = memberManageService.findCardInfo(param);
        //查询租户下门店
        TbTenantInfo tbTenantInfo=new TbTenantInfo();
        tbTenantInfo.setTenantId(tenantId);
        List<TbTenantInfo> branchResult=tenantService.getTenantInfoListByTenantId(tbTenantInfo);
        List<String> branchIdResult=new ArrayList<>();
        for (int i = 0; i < branchResult.size(); i++) {
            branchResult.get(i).getBranchId();
            branchIdResult.add(branchResult.get(i).getBranchId());
        }
        resultMap.put("cardTypeResult", result);
        resultMap.put("branchIdResult", branchIdResult);
        return JSONObject.fromObject(resultMap);
    }
}
