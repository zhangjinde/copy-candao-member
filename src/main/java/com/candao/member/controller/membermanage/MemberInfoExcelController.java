package com.candao.member.controller.membermanage;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.candao.member.service.membermanage.MemberManageService;
import com.candao.member.utils.Constant;
import com.candao.member.utils.ExcelUtil;
import com.candao.member.utils.SessionUtils;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/memberbase/meminfoexcel")
public class MemberInfoExcelController {

	@Autowired
	private MemberManageService memberManageService;

	@RequestMapping("exportExcel")  
    public void download(HttpServletRequest request,HttpServletResponse response) throws IOException{
		
		String queryInfo = request.getParameter("id");
		JSONObject object = JSONObject.fromObject(queryInfo);
		Map<String,Object> queryInfoMap = new HashMap<>();
		Iterator it  = object.keys();
		while(it.hasNext()) {  
		   String key = it.next().toString();
		   queryInfoMap.put(key, object.get(key).equals("")?"":object.get(key));
		}  
		String tenantId = SessionUtils.get(Constant.CURRENT_TENANT_ID).toString();
		String fileName=tenantId+"excel文件";
		
		Map<String,Object> param = new HashMap<>();
		param.put("birstarttime", null);
		param.put("birendtime", null);
		param.put("birnorflag", -1);
		if(queryInfoMap.get("birstarttime").toString() != ""){
			int birnorflag = queryInfoMap.get("birstarttime").toString().substring(0, 4).equals(queryInfoMap.get("birendtime").toString().substring(0, 4))?1:0;//在同一年中用ture。or 用 flase
			param.put("birnorflag", birnorflag);//生日判断是否为同一年
			param.put("birstarttime", queryInfoMap.get("birstarttime").toString().substring(5));//保留月份与日期
			param.put("birendtime", queryInfoMap.get("birendtime").toString().substring(5));
		}
		param.put("telepno",queryInfoMap.get("telepno").toString());//电话号码
//		}else if(number != null){
		if(queryInfoMap.get("cardno").toString() != ""){
			param.put("cardno",tenantId+queryInfoMap.get("cardno").toString());//会员卡号
		}
//		}
		
		param.put("tenant_id", tenantId);
		param.put("pointflag", queryInfoMap.get("pointflag").toString());
		
		param.put("name", queryInfoMap.get("name").toString());
		param.put("cardlevel", queryInfoMap.get("cardname").toString());//卡名称
		param.put("cardstat", queryInfoMap.get("cardstat").toString());//卡状态
		param.put("point", queryInfoMap.get("point").toString());//积分
		param.put("pointflag", queryInfoMap.get("pointflag").toString());//积分大于小于等于类型
		param.put("balance", queryInfoMap.get("balance").toString());//余额
		param.put("balanceflag", queryInfoMap.get("balanceflag").toString());//大于小于等于类型
		param.put("totalbalance", queryInfoMap.get("totalbalance").toString());//累计余额
		param.put("totalbalanceflag", queryInfoMap.get("totalbalanceflag").toString());//大于小于等于类型
   	
		
		int tenantIdLenth = tenantId.length();
        List<Map<String,Object>> list = memberManageService.getWriteExcel(param);
        for(Map<String,Object> temp : list){
        	String cardnoTemp = temp.get("memberCardNo").toString();
        	String cardclass = null != temp.get("cardclass") ?temp.get("cardclass").toString():"0";
        	if(cardnoTemp.contains("CD")||cardclass.equals("0")){
        		temp.put("memberCardNo", "");
        		continue;
        	}
        	temp.put("memberCardNo", cardnoTemp.substring(cardnoTemp.indexOf(tenantId)+tenantIdLenth));
        }
        String title = "模版数据填写注意事项：\r\n1、会员卡号、会员卡名称、门店编号为必填字段，不能留空；日期格式必须为：yyyy-MM-dd，如：2004-02-17   \r\n2、会员性别：男 / 女 \r\n3、会员卡状态：0正常、1挂失、2注销";
        String columnNames[]={"会员卡号","客户名称","手机号","性别","出生日期","住址","会员卡名称","累计充值金额","累计赠送金额",
        		"余额","累计积分","当前积分","挂帐/欠款","开卡员工","开卡时间","会员卡状态","门店编号"};//列名
        String keys[] = {"memberCardNo","memberName","phone","sex","birth","address","memberCardName","chargeAmount","presentAmount",
        		"balanceAmount","totalPoint","currentPoint","debt","creatorEmployeeNo","createDate","cardStatus","storeNo"};//map中的key
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            ExcelUtil.createWorkBook(list,keys,columnNames,title).write(os);
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] content = os.toByteArray();
        InputStream is = new ByteArrayInputStream(content);
        // 设置response参数，可以打开下载页面
        response.reset();
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        response.setHeader("Content-Disposition", "attachment;filename="+ new String((fileName + ".xls").getBytes(), "iso-8859-1"));
        ServletOutputStream out = response.getOutputStream();
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {
            bis = new BufferedInputStream(is);
            bos = new BufferedOutputStream(out);
            byte[] buff = new byte[2048];
            int bytesRead;
            // Simple read/write loop.
            while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                bos.write(buff, 0, bytesRead);
            }
        } catch (final IOException e) {
            throw e;
        } finally {
            if (bis != null)
                bis.close();
            if (bos != null)
                bos.close();
        }
    }
}
