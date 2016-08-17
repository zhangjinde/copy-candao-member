package com.candao.member.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.candao.member.model.TbCardInfo;
import com.candao.member.model.TbMemberManager;
import com.candao.member.model.TbTenantCard;
import com.candao.member.model.TbTenantInfo;
import com.candao.member.service.CardInfoService;
import com.candao.member.service.ExcelExcutorService;
import com.candao.member.service.MemberManagerService;
import com.candao.member.service.TenantCardService;
import com.candao.member.service.TenantService;
import com.candao.member.utils.Constant;
import com.candao.member.utils.HibernateValidateUtils;
import com.candao.member.utils.JacksonJsonMapper;
import com.candao.member.utils.MemberSwtichCardNOUtil;
import com.candao.member.utils.ResultMessage;
import com.candao.member.utils.Tools;
import com.candao.member.vo.MemberVo;
import com.candao.member.vo.VipInfoExcelVo;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * excel导入数据
 * 
 * @author snail
 * @updator dengchao
 * @updateTime 2016年6月23日 下午2:34:26
 * @description 新增excel resolve sample
 */
@Controller("excel")
@RequestMapping("/excel")
public class ExcelController {

    private String EXCELFILEPATH = "";

    private String TENANTID = "";

    private String BRANCHID = "";

    @Autowired
    private MemberManagerService memberManagerService;

    @Autowired
    private TenantService tenantService;

    @Autowired
    private TenantCardService tenantCardService;

    @Autowired
    private CardInfoService cardInfoService;
    
    @Autowired
    private ExcelExcutorService excelExcutorService;

    @Value("#{appconfig.excel_import_seq}")
    private Integer excelImportSeq;

    @Value("#{appconfig.thread_num}")
    private Integer threadNum;

    private static Logger log = LoggerFactory.getLogger(ExcelController.class);

    @RequestMapping("/import")
    @ResponseBody
    public ModelAndView importPage() {
        return new ModelAndView("excel/main");
    }

    @RequestMapping("/mainimport")
    @ResponseBody
    public String mainimport(@RequestParam("file") CommonsMultipartFile file, HttpServletRequest request,
            String tenantid, String branchid) {
        TENANTID = tenantid;
        BRANCHID = branchid;
        try {
            if (file != null) {
                String filename1 = file.getOriginalFilename();
                if (filename1 != null) {
                    if (!filename1.endsWith(".xls")) {
                        return JacksonJsonMapper.objectToJson("请传入附件.xls文件");
                    }
                }
            } else {
                return JacksonJsonMapper.objectToJson("请传入附件");
            }
            String filename = file.getName();

            FileInputStream in = (FileInputStream) file.getInputStream();
            int success = 0;
            int fail = 0;
            StringBuffer buf = new StringBuffer();
            Map<String, Object> result = new HashMap<>();
            try {
                HSSFWorkbook hssfWorkbook = new HSSFWorkbook(in);
                List<VipInfoExcelVo> datas = new ArrayList<>();
                for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
                    HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
                    if (hssfSheet == null) {
                        continue;
                    }
                    // 循环行Row
                    for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
                        HSSFRow hssfRow = hssfSheet.getRow(rowNum);
                        if (hssfRow != null) {
                            int temp = 0;
                            VipInfoExcelVo excelVo = new VipInfoExcelVo();
                            excelVo.setPno(getValue(hssfRow.getCell(temp++)));
                            excelVo.setName(getValue(hssfRow.getCell(temp++)));
                            excelVo.setLevel(getValue(hssfRow.getCell(temp++)));
                            excelVo.setType(getValue(hssfRow.getCell(temp++)));
                            excelVo.setStatus(getValue(hssfRow.getCell(temp++)));
                            excelVo.setGlevel(getValue(hssfRow.getCell(temp++)));
                            excelVo.setMobile(getValue(hssfRow.getCell(temp++)));
                            excelVo.setGender(getValue(hssfRow.getCell(temp++)));
                            excelVo.setBirthday(getValue(hssfRow.getCell(temp++)));
                            excelVo.setCmoney(getValue(hssfRow.getCell(temp++)));
                            excelVo.setZsmoney(getValue(hssfRow.getCell(temp++)));
                            excelVo.setValue(getValue(hssfRow.getCell(temp++)));
                            excelVo.setPoint(getValue(hssfRow.getCell(temp++)));
                            datas.add(excelVo);
                        }
                    }
                }
                System.out.println(JacksonJsonMapper.objectToJson(datas));
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                //转换成对象再进行数据库操作，为了代码可读性更高
                for (VipInfoExcelVo excelVo : datas) {
                    TbTenantInfo tbTenantInfo = tenantService.findByBranchId(BRANCHID);
                    if (tbTenantInfo == null) {
                        tbTenantInfo = new TbTenantInfo();
                        tbTenantInfo.setTenantId(TENANTID);
                    }
                    ;
                    TbMemberManager tbMemberManager = new TbMemberManager();
                    tbMemberManager.setStatus('1');
                    tbMemberManager.setTenant_id(tbTenantInfo.getTenantId());
                    tbMemberManager.setBranch_id(BRANCHID);
                    tbMemberManager.setName(excelVo.getName());
                    tbMemberManager.setMobile(excelVo.getMobile());
                    tbMemberManager.setPassword("123456");
                    tbMemberManager.setTenant_id(TENANTID);
                    tbMemberManager.setCreatetime(new Date());
                    String gender = excelVo.getGender();
                    if (gender != null && gender.contains(Constant.SEX_WOMAN)) {
                        tbMemberManager.setGender('1');
                    } else {
                        tbMemberManager.setGender('0');//男的
                    }
                    tbMemberManager.setBirthday(format.parse(excelVo.getBirthday()));

                    TbCardInfo tbCardInfo = new TbCardInfo();
                    tbCardInfo.setCardno(excelVo.getPno());
                    tbCardInfo.setStatus('1');
                    tbCardInfo.setLevel("0");
                    tbCardInfo.setChannel("1");//实体卡
                    tbCardInfo.setCreatetime(new Date());
                    tbCardInfo.setBranch_id(BRANCHID);
                    tbCardInfo.setTenant_id(Integer.parseInt(TENANTID));

                    Map<String, Object> params = new HashMap<String, Object>();
                    params.put("tenant_id", tbTenantInfo.getTenantId());

                    synchronized (this) {
                        int count = cardInfoService.findIsExist(TENANTID, excelVo.getPno());
                        if (count > 0) {//已经导入过了
                            fail++;
                            buf.append("数据库重复啦：租户id：").append(TENANTID).append(",卡号:").append(excelVo.getPno())
                                    .append(";");
                            continue;
                        }
                        TbTenantCard tbTenantCard = tenantCardService.findByParams(params);
                        memberManagerService.save(tbMemberManager, tbCardInfo, tbTenantCard, excelVo);
                        success++;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                return Constant.FAILUREMSG;
            }
            result.put("result", 0);
            result.put("error", buf.toString());
            result.put("success", "成功上传数据" + success);
            return JacksonJsonMapper.objectToJson(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @SuppressWarnings({ "resource" })
    @RequestMapping("/importExcel")
    @ResponseBody
    public String importExcel() {
        int success = 0;
        int fail = 0;
        StringBuffer buf = new StringBuffer();
        Map<String, Object> result = new HashMap<>();
        try {
            InputStream is = new FileInputStream(EXCELFILEPATH);
            HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
            List<VipInfoExcelVo> datas = new ArrayList<>();
            for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
                HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
                if (hssfSheet == null) {
                    continue;
                }
                // 循环行Row
                for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
                    HSSFRow hssfRow = hssfSheet.getRow(rowNum);
                    if (hssfRow != null) {
                        int temp = 0;
                        VipInfoExcelVo excelVo = new VipInfoExcelVo();
                        excelVo.setPno(getValue(hssfRow.getCell(temp++)));
                        excelVo.setName(getValue(hssfRow.getCell(temp++)));
                        excelVo.setLevel(getValue(hssfRow.getCell(temp++)));
                        excelVo.setType(getValue(hssfRow.getCell(temp++)));
                        excelVo.setStatus(getValue(hssfRow.getCell(temp++)));
                        excelVo.setGlevel(getValue(hssfRow.getCell(temp++)));
                        excelVo.setMobile(getValue(hssfRow.getCell(temp++)));
                        excelVo.setGender(getValue(hssfRow.getCell(temp++)));
                        excelVo.setBirthday(getValue(hssfRow.getCell(temp++)));
                        excelVo.setCmoney(getValue(hssfRow.getCell(temp++)));
                        excelVo.setZsmoney(getValue(hssfRow.getCell(temp++)));
                        excelVo.setValue(getValue(hssfRow.getCell(temp++)));
                        excelVo.setPoint(getValue(hssfRow.getCell(temp++)));
                        datas.add(excelVo);
                    }
                }
            }
            System.out.println(JacksonJsonMapper.objectToJson(datas));
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            //转换成对象再进行数据库操作，为了代码可读性更高
            for (VipInfoExcelVo excelVo : datas) {
                TbTenantInfo tbTenantInfo = tenantService.findByBranchId(BRANCHID);
                if (tbTenantInfo == null) {
                    tbTenantInfo = new TbTenantInfo();
                    tbTenantInfo.setTenantId(TENANTID);
                }
                ;
                TbMemberManager tbMemberManager = new TbMemberManager();
                tbMemberManager.setStatus('1');
                tbMemberManager.setTenant_id(tbTenantInfo.getTenantId());
                tbMemberManager.setBranch_id(BRANCHID);
                tbMemberManager.setName(excelVo.getName());
                tbMemberManager.setMobile(excelVo.getMobile());
                tbMemberManager.setPassword("123456");
                tbMemberManager.setTenant_id(TENANTID);
                tbMemberManager.setCreatetime(new Date());
                String gender = excelVo.getGender();
                if (gender != null && gender.contains(Constant.SEX_WOMAN)) {
                    tbMemberManager.setGender('1');
                } else {
                    tbMemberManager.setGender('0');//男的
                }
                tbMemberManager.setBirthday(format.parse(excelVo.getBirthday()));

                TbCardInfo tbCardInfo = new TbCardInfo();
                tbCardInfo.setCardno(excelVo.getPno());
                tbCardInfo.setStatus('1');
                tbCardInfo.setLevel("0");
                tbCardInfo.setChannel("1");//实体卡
                tbCardInfo.setCreatetime(new Date());
                tbCardInfo.setBranch_id(BRANCHID);
                tbCardInfo.setTenant_id(Integer.parseInt(TENANTID));

                Map<String, Object> params = new HashMap<String, Object>();
                params.put("tenant_id", tbTenantInfo.getTenantId());

                synchronized (this) {
                    int count = cardInfoService.findIsExist(TENANTID, excelVo.getPno());
                    if (count > 0) {//已经导入过了
                        fail++;
                        buf.append("数据库重复啦：租户id：").append(TENANTID).append(",卡号:").append(excelVo.getPno()).append(";");
                        continue;
                    }
                    TbTenantCard tbTenantCard = tenantCardService.findByParams(params);
                    memberManagerService.save(tbMemberManager, tbCardInfo, tbTenantCard, excelVo);
                    success++;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Constant.FAILUREMSG;
        }
        result.put("result", 0);
        result.put("error", buf.toString());
        result.put("success", "成功上传数据" + success);
        return JacksonJsonMapper.objectToJson(result);
    }

    @SuppressWarnings({ "static-access" })
    private String getValue(HSSFCell hssfCell) {
        if (hssfCell == null) {
            return "";
        }
        if (hssfCell.getCellType() == hssfCell.CELL_TYPE_BOOLEAN) {
            // 返回布尔类型的值
            return String.valueOf(hssfCell.getBooleanCellValue());
        } else if (hssfCell.getCellType() == hssfCell.CELL_TYPE_NUMERIC) {
            // 返回数值类型的值
            return String.valueOf(hssfCell.getNumericCellValue());
        } else {
            // 返回字符串类型的值
            return String.valueOf(hssfCell.getStringCellValue());
        }
    }

    /**     * resolve(excel resolve 解析) 
     * @param   beginRow
     * @return String    DOM对象    
     * @Exception 异常对象    
     * @since  CodingExample　Ver(编码范例查看) 1.1       
    */
    @RequestMapping(value = "/resolve", method = { RequestMethod.POST, RequestMethod.GET })
    @ResponseBody
    public ResultMessage resolve(MultipartFile Filedata, HttpServletRequest request, Integer beginRow,String tenantId,String cardtype) {
        log.info("ExcelController.beginRow={}.", beginRow);
//        String tenantId = SessionUtils.get(Constant.CURRENT_TENANT_ID).toString();
        //业务模块按需获取结果集按自己的业务规则校验后入库，尚未入库

        ResultMessage resultMessage = excelExcutorService.resolve(Filedata, MemberVo.class, beginRow);
       
        resultMessage = excelImport(resultMessage,tenantId,cardtype);
        return resultMessage;
        
    }
    
    /**
     * @param resultMessage
     * @param TENANTID
     * @param cardtype
     * @return
     */
    public ResultMessage excelImport( ResultMessage resultMessage,String TENANTID,String cardtype){
    	
    	TbTenantInfo tbTenantInfo = new TbTenantInfo();
    	tbTenantInfo.setTenantId(TENANTID);
    	List<TbTenantInfo> storesList = tenantService.getTenantInfoListByTenantId(tbTenantInfo);
    	
    	List<MemberVo> datas = (List<MemberVo>)resultMessage.getSuccessResult();
    
    	List<MemberVo> errorList = (List<MemberVo>)resultMessage.getErrorResult();
	    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	    try{
	     //转换成对象再进行数据库操作，为了代码可读性更高
	     for (MemberVo excelVo : datas) {
//	         TbTenantInfo tbTenantInfo = tenantService.findByBranchId(BRANCHID);
//	         if (tbTenantInfo == null) {
//	             tbTenantInfo = new TbTenantInfo();
//	             tbTenantInfo.setTenantId(tenantId);
//	         }
	    	 
	         if(storesList == null){
	        	 errorList.add(excelVo);
            	 resultMessage.setSuccessNum(Integer.parseInt(resultMessage.getSuccessNum())-1+"");
            	 resultMessage.setErrorNum(Integer.parseInt(resultMessage.getErrorNum())+1+"");
            	 resultMessage.setErrorResult(errorList);
            	 log.error("数据表导入数据失败卡号："+excelVo.getMemberCardNo());
            	 continue;
	         }
	         int storesSum = 0;
	         for(TbTenantInfo stores : storesList){
	        	 if(!stores.getBranchId().equals(excelVo.getStoreNo())){
	        		 ++storesSum;
	        	 }
	         }
	         
	         if(storesSum == storesList.size()){//不在该租户下则报错
        		 errorList.add(excelVo);
            	 resultMessage.setSuccessNum(Integer.parseInt(resultMessage.getSuccessNum())-1+"");
            	 resultMessage.setErrorNum(Integer.parseInt(resultMessage.getErrorNum())+1+"");
            	 resultMessage.setErrorResult(errorList);
            	 log.error("数据表导入数据失败卡号："+excelVo.getMemberCardNo());
            	 continue;
        	 }
	         String cardNo = excelVo.getMemberCardNo();
	         if(cardtype.equals("0")){//虚拟卡
	        	 cardNo = MemberSwtichCardNOUtil.getInStance().createCardNo("CD"+cardNo);
	         }else{//实体卡
				 cardNo = MemberSwtichCardNOUtil.getInStance()
	                        .createCardNo( excelVo.getStoreNo()+TENANTID, cardNo);
	         }
			 
	         TbMemberManager tbMemberManager = new TbMemberManager();
	         tbMemberManager.setStatus('1');
	         tbMemberManager.setTenant_id(TENANTID);
	         tbMemberManager.setBranch_id(excelVo.getStoreNo());
	         tbMemberManager.setName(excelVo.getMemberName());
	         tbMemberManager.setMobile(excelVo.getPhone());
	         tbMemberManager.setMember_address(excelVo.getAddress());
	         tbMemberManager.setPassword("123456");
	         
	         String createmerberDate = excelVo.getCreateDate();
	         if(createmerberDate != null && Tools.isValidDate(createmerberDate)){
	        	 tbMemberManager.setCreatetime(format.parse(createmerberDate));
	         }else{
	        	 tbMemberManager.setCreatetime(new Date());
	         }
	         
	         tbMemberManager.setGender('0');//男的 预防问题写问题默认为男 change by 20160704
	         String gender = excelVo.getSex();
	         if (gender != null && gender.trim().equals("1")) {
	             tbMemberManager.setGender('1');
	         } else {
	             tbMemberManager.setGender('0');//男的
	         }
	         String birth = excelVo.getBirth();
	         tbMemberManager.setBirthday(new Date());
	         if(birth != null && Tools.isValidDate(birth)){
	        	 tbMemberManager.setBirthday(format.parse(excelVo.getBirth()));
	         }
	         TbCardInfo tbCardInfo = new TbCardInfo();
	         tbCardInfo.setCardno(excelVo.getMemberCardNo());
	         tbCardInfo.setStatus(excelVo.getCardStatus().trim().charAt(0));
	         tbCardInfo.setLevel("0");
	         tbCardInfo.setChannel("4");//4渠道方式为导入3，为页面注册
	         if(cardtype.equals("0")){
//	        	 tbCardInfo.setChannel("0");//虚拟卡
	        	 tbCardInfo.setCard_type(0);
	         }else{
//	        	 tbCardInfo.setChannel("1");//实体卡
	        	 tbCardInfo.setCard_type(1);
	         }
	         if(excelVo.getCreateDate() != null && Tools.isValidDate(excelVo.getCreateDate())){
	        	 tbCardInfo.setCreatetime(format.parse(excelVo.getCreateDate()));
	         }
	        
	         tbCardInfo.setBranch_id(excelVo.getStoreNo());
	         tbCardInfo.setTenant_id(Integer.parseInt(TENANTID));
	         tbCardInfo.setCardno(cardNo);
	         String createDate = excelVo.getCreateDate();
	         if(createDate != null && Tools.isValidDate(createDate)){
	        	 tbCardInfo.setCreatetime(format.parse(createDate));
	         }else{
	        	 tbCardInfo.setCreatetime(new Date());
	         }
	         tbCardInfo.setCreateuser(excelVo.getCreatorEmployeeNo());
	
	         Map<String, Object> params = new HashMap<String, Object>();
	         params.put("tenant_id", TENANTID);
	         StringBuffer buf = new StringBuffer();
	         synchronized (this) {
//	             int count = cardInfoService.findIsExist(TENANTID, cardNo);
//	             if (count > 0) {//已经导入过了
//	                 buf.append("数据库重复啦：租户id：").append(TENANTID).append(",卡号:").append(excelVo.getMemberCardNo())
//	                         .append(";");
//	                 log.info(buf.toString());
//	                 continue;
//	             }
	             TbTenantCard tbTenantCard = tenantCardService.findByParams(params);
	             try{
	            	 memberManagerService.save(tbMemberManager, tbCardInfo, tbTenantCard, excelVo,TENANTID,cardNo);
	             }catch(Exception e){
	            	 errorList.add(excelVo);
	            	 resultMessage.setSuccessNum(Integer.parseInt(resultMessage.getSuccessNum())-1+"");
	            	 resultMessage.setErrorNum(Integer.parseInt(resultMessage.getErrorNum())+1+"");
	            	 resultMessage.setErrorResult(errorList);
	            	 log.error("数据表导入数据失败卡号："+cardNo,e);
	            	 continue;
	             }
	         }
     }
	    }catch(Exception e){
	    	e.printStackTrace();
	    }
	    return resultMessage;
    }
    
    @RequestMapping("/changeInfo")
    @ResponseBody
    public ResultMessage importChangeInfoExcel(HttpServletRequest request,HttpServletResponse response,String tenantId,String cardtype) {

    	String result = request.getParameter("editdata"); 
    	JSONArray object = JSONArray.fromObject(result);
    	
    	ResultMessage resultMessage = new ResultMessage();
    	resultMessage.setErrorNum("0");
    	
    	List<MemberVo> successList = new ArrayList<>();
    	List<Object> errorList = new ArrayList<>();
    	
    	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    	
//    	Object objc = object.get;
    	for(int i =0;i<object.size();i++){
    		MemberVo temp = new MemberVo();
    		JSONObject jobject = object.getJSONObject(i);
    		temp.setMemberCardNo(jobject.get("memberCardNo").toString());
    		temp.setMemberName(jobject.get("memberName").toString());
    		temp.setPhone(jobject.get("phone").toString());
    		temp.setSex(jobject.get("sex").toString());
    		temp.setBirth(jobject.get("birth").toString());
    		temp.setAddress(jobject.get("address").toString());
    		temp.setMemberCardName(jobject.get("memberCardName").toString());
    		temp.setChargeAmount(jobject.get("chargeAmount").toString());
    		temp.setPresentAmount(jobject.get("presentAmount").toString());
    		
     		temp.setBalanceAmount(jobject.get("balanceAmount").toString());
    		temp.setTotalPoint(jobject.get("totalPoint").toString());
    		temp.setCurrentPoint(jobject.get("currentPoint").toString());
    		temp.setDebt(jobject.get("debt").toString());
    		temp.setCreatorEmployeeNo(jobject.get("creatorEmployeeNo").toString());
    		temp.setCreateDate(jobject.get("createDate").toString());
    		temp.setCardStatus(jobject.get("cardStatus").toString());
    		temp.setStoreNo(jobject.get("storeNo").toString());
    		
    		HibernateValidateUtils hvu=new HibernateValidateUtils();
            String resultVali="";
            try {
            	resultVali = hvu.validateModel(temp);
            	if(!resultVali.equals("")){
            		log.error("excel表修改保存数据验证失败"+resultVali);
                    errorList.add(temp);
                    resultMessage.setErrorNum(errorList.size()+"");
                    continue;
            	}
            } catch (Exception e1) {
                log.error("excel表修改保存数据验证失败"+resultVali,e1);
                errorList.add(temp);
                resultMessage.setErrorNum(errorList.size()+"");
                continue;
            }
            successList.add(temp);
    	}
    	resultMessage.setSuccessResult(successList);
    	resultMessage.setErrorResult(errorList);
    	resultMessage.setSuccessNum(successList.size()+"");
    	
    	resultMessage = excelImport(resultMessage,tenantId,cardtype);
    	return resultMessage;
    	
    	
//    	return "";
    }
}
