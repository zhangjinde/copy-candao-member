package com.candao.member.controller;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.candao.member.dao.TDiscountRuleDao;
import com.candao.member.dao.impl.Page;
import com.candao.member.model.TDiscountRule;
import com.candao.member.model.TbMemberInfo;
import com.candao.member.service.CardInfoService;
import com.candao.member.service.DealService;
import com.candao.member.service.MemberInfoService;
import com.candao.member.utils.Constant;
import com.candao.member.utils.JacksonJsonMapper;
import com.candao.member.utils.SessionUtils;

/**
 * 会员消费记录查询
 * @author Devil
 * 2015.9.14
 *
 */
@Controller
@RequestMapping("/memberdeal")
public class MemberDealController extends BaseController {
	@Autowired
	private CardInfoService cardInfoService ;
	
	@Autowired
	private MemberInfoService memberInfoService;
	
	@Autowired
	private DealService dealService;
	
	@Autowired
	private TDiscountRuleDao tDiscountRuleDao;
	
	/**
	 * 会员消费记录分页查询
	 * @return
	 */
	@RequestMapping("/searchPage")
	@ResponseBody
	public ModelAndView MemberDealSearch(@RequestParam(value = "current", defaultValue = "1") Integer current,
			String mobile,String name,String birthday,String valuemin,String valuemax,String pointmin,String pointmax){
		ModelAndView mv = new ModelAndView("memberdeal/show");//index");
	    String tenantId = SessionUtils.get(Constant.CURRENT_TENANT_ID).toString();
	    Map<String, Object> param = new HashMap<>();
	    param.put("tenantId", tenantId);
    	List<TDiscountRule> rules = tDiscountRuleDao.getdiscountRuleByParam(param);
 	    HashMap<String, Object> params = setParams(mobile, name, birthday, valuemin, valuemax, pointmin, pointmax);
 	  
 	    Page<Map<String, Object>> page = cardInfoService.grid(params,current, 10);
 	    
 	    Map<String, Object> resultMap = cardInfoService.getAllMemberBalance();
 	   
 	    List<TbMemberInfo> memberInfos = memberInfoService.getAllAvailableMmberByTenantId(tenantId);
 	   
 	    Map<String, Double> countmap = dealService.getStoreCount(tenantId);
 	   
 	    setMav(current, mv, rules, params, page, resultMap, memberInfos, countmap,tenantId);
		return mv;
	}

	private void setMav(Integer current, ModelAndView mv, List<TDiscountRule> rules, HashMap<String, Object> params,
			Page<Map<String, Object>> page, Map<String, Object> resultMap, List<TbMemberInfo> memberInfos,
			Map<String, Double> countmap,String tenantId) {
		mv.addObject("memberCount", memberInfos.size());
		//默认0.0转换为0
		DecimalFormat df = new DecimalFormat("0.00"); // 保留几位小数

		Map<String,Object> newCountmap = new HashMap<>();
		newCountmap.put("cashCount", df.format(countmap.get("cashCount")));
		newCountmap.put("bankCount",df.format(countmap.get("bankCount")));
		newCountmap.put("refundCount", df.format(countmap.get("refundCount")));
		newCountmap.put("presentCount", df.format(countmap.get("presentCount")));
		
		if(countmap.get("cashCount").doubleValue() == 0.0)
			newCountmap.put("cashCount", 0);
		if(countmap.get("bankCount").doubleValue() == 0.0)
			newCountmap.put("bankCount", 0);
		if(countmap.get("refundCount").doubleValue() == 0.0)
			newCountmap.put("refundCount", 0);
		if(countmap.get("presentCount").doubleValue() == 0.0)
			newCountmap.put("presentCount", 0);
 	    mv.addObject("countmap", newCountmap);
 	    mv.addObject("allBalance", resultMap!=null&&!resultMap.isEmpty()? resultMap.get("balance"):0);
 	    mv.addObject("allPoint", resultMap!=null&&!resultMap.isEmpty()? resultMap.get("point"):0);
 	    mv.addObject("datas", page.getRows());
 	    mv.addAllObjects(params);
 	    mv.addObject("current",current);
 	    mv.addObject("total", page.getTotal());
 	    mv.addObject("totalpage", page.getPageCount());
 	    mv.addObject("list", rules);
 	    mv.addObject("tenantId",tenantId);
	}

	/**
	 * 设置参数
	 * @param mobile
	 * @param name
	 * @param birthday
	 * @param valuemin
	 * @param valuemax
	 * @param pointmin
	 * @param pointmax
	 * @return
	 */
	private HashMap<String, Object> setParams(String mobile, String name, String birthday, String valuemin,
			String valuemax, String pointmin, String pointmax) {
		HashMap<String, Object>  params = new HashMap<>();
 	    params.put("mobile", mobile);
 	    params.put("name", name);
 	    params.put("birthday", birthday);
 	    params.put("valuemin", valuemin);
 	    params.put("valuemax", valuemax);
 	    params.put("pointmin", pointmin);
 	    params.put("pointmax", pointmax);
		return params;
	}
	
	/**
	 * 根据会员卡号查询消费记录
	 * @param cardno
	 * @param current
	 * @return
	 */
	@RequestMapping("/findDealDetailByCardno")
	@ResponseBody
	public String findDealDetailByCardno(String cardno,@RequestParam(value = "current", defaultValue = "1") Integer current){
		Map<String, Object> resultMap = new HashMap<>();
		Map<String, Object> map = new HashMap<>();
		map.put("card_no", cardno);
		Page<Map<String, Object>> pagemap = dealService.grid(map, current, 10);
		resultMap.put("detailTotal", pagemap.getTotal());
		resultMap.put("detailTotalpage", pagemap.getPageCount());
		resultMap.put("detailCurrent", current);
		resultMap.put("detailDatas", pagemap.getRows());
		resultMap.put("cardno", cardno);
		return JacksonJsonMapper.objectToJson(resultMap);
	}
	
}
