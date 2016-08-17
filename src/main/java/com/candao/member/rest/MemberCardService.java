package com.candao.member.rest;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.candao.member.dao.TDiscountRuleDao;
import com.candao.member.model.TDiscountRule;
import com.candao.member.model.TMemberCard;
import com.candao.member.model.TbCardInfo;
import com.candao.member.model.TbMemberManager;
import com.candao.member.model.TbTenantInfo;
import com.candao.member.service.CardAccountService;
import com.candao.member.service.CardInfoService;
//github.com/incito/candao-member.git
import com.candao.member.service.MemberInfoService;
import com.candao.member.service.MemberManagerService;
import com.candao.member.service.SystemLogService;
import com.candao.member.service.TbMenberBatchService;
import com.candao.member.service.TenantService;
import com.candao.member.utils.DateUtils;
//github.com/incito/candao-member.git
import com.candao.member.utils.JSONUtils;
import com.candao.member.utils.JacksonJsonMapper;
import com.candao.member.utils.MemberSwtichCardNOUtil;

@Path("/memberCardService")
public class MemberCardService extends BaseSerivce {

    @Autowired
    private MemberInfoService memberInfoService;

    @Autowired
    private TenantService tenantService;

    @Autowired
    private MemberManagerService memberManagerService;

    @Autowired
    private CardAccountService cardAccountService;

    @Autowired
    private TDiscountRuleDao tDiscountRuleDao;

    @Autowired
    private CardInfoService cardInfoService;

    @Autowired
    private TbMenberBatchService tbMenberBatchService;

    @Autowired
    private SystemLogService systemLogService;

    private static Logger log = LoggerFactory.getLogger(MemberCardService.class);

    /**     * getCardNoByMobile(根据手机号获取用户卡列表) 
     * @param   name
     * @return String    DOM对象    
     * @Exception 异常对象    
     * @since  CodingExample　Ver(编码范例查看) 1.1       
    */
    @Path("/getCardNoByMobile")
    @POST
    @Produces({ MediaType.TEXT_PLAIN,MediaType.APPLICATION_JSON })
    public Response getCardNoByMobile(String jsonString) throws UnsupportedEncodingException {
        HashMap<String, Object> paramsJson = JacksonJsonMapper.jsonToObject(jsonString, HashMap.class);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        Object mobileObj = paramsJson.get("cardno");
        Object branchIdObj = paramsJson.get("branch_id");
        String mobile = "";
        String branchId = "";
        if (mobileObj != null && !mobileObj.toString().isEmpty()) {
            mobile = mobileObj.toString();
        } else {
            resultMap.put("retcode", "1");
            resultMap.put("retInfo", "手机号不能为空");
            resultMap.put("result", "");
            return Response.ok(URLDecoder.decode(JSONUtils.mapToJson(resultMap), "utf-8")).build();
        }
        if (branchIdObj != null && !branchIdObj.toString().isEmpty()) {
            branchId = branchIdObj.toString();
        } else {
            resultMap.put("retcode", "1");
            resultMap.put("retInfo", "门店号不能为空");
            resultMap.put("result", "");
            return Response.ok(URLDecoder.decode(JSONUtils.mapToJson(resultMap), "utf-8")).build();
        }

        log.info("MemberManagerController.getCardNoByMobile,mobile={},branchId={}.", mobile, branchId);
        TbTenantInfo tbTenantInfo = new TbTenantInfo();
        
        Pattern p = Pattern.compile("^1(3[0-9]|4[57]|5[0-9]|7[0678]|8[0-9])\\d{8}$"); 
        Matcher m = p.matcher(mobile); 
        boolean flg = m.matches(); 
        
        if (flg) { //前端传入的是手机号码
            paramsJson.put("mobile", mobile);
            paramsJson.put("cardno", "");
            //分店租户信息
            tbTenantInfo = tenantService.findByBranchId(branchId);
            if (tbTenantInfo == null) {
                Map<String, Object> map = new HashMap<>();
                map.put("retcode", 1);
                map.put("retInfo", "没找到门店所属的租户");
                return Response.ok(URLDecoder.decode(JSONUtils.mapToJson(map), "utf-8")).build();
            }
            paramsJson.put("tenant_id", tbTenantInfo.getTenantId());
        }
        if (!flg) {
            //老版本会员账号,以及实体账号
            tbTenantInfo = tenantService.findByBranchId(branchId);
            String tenantId = tbTenantInfo != null ? tbTenantInfo.getTenantId() : "";
            String newCard_no="";
            if(mobile.contains("CD")){
                newCard_no = MemberSwtichCardNOUtil.getInStance().createCardNo(mobile);
            }else{
                newCard_no = MemberSwtichCardNOUtil.getInStance().createCardNo(branchId + tenantId, mobile);
            }
            paramsJson.put("cardno", newCard_no);
        }
        List<Map<String, Object>> list = memberManagerService.findListByParams(paramsJson);
        if (list == null || list.size() == 0) {
            resultMap.put("retcode", 1);
            resultMap.put("retInfo", "会员账号不存在或密码错误");
            return Response.ok(URLDecoder.decode(JSONUtils.mapToJson(resultMap), "utf-8")).build();
        } else {
            try {
                List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
                String name = list.get(0).get("name") == null ? "" : list.get(0).get("name").toString();
                Integer gender = list.get(0).get("gender") == null ? 0
                        : Integer.valueOf(list.get(0).get("gender").toString());
                String birthday = list.get(0).get("birthday") == null ? ""
                        : DateUtils.dateToString((Date) list.get(0).get("birthday"));
                String createtime = list.get(0).get("createtime") == null ? ""
                        : DateUtils.dateToString((Date) list.get(0).get("createtime"));
                String mobile1 = list.get(0).get("mobile") == null ? "" : list.get(0).get("mobile").toString();
                for (int i = 0; i < list.size(); i++) {
                    Map<String, Object> map1 = new HashMap<String, Object>();
                    map1 = list.get(i);
                    Map<String, Object> resultMap1 = new HashMap<String, Object>();
                    Map<String, Object> map2 = cardAccountService
                            .getBalanceByCardNo(String.valueOf(map1.get("cardno")));
                    Object cardNoObj= map1.get("cardno");
                    String cardNo_change="";
                    if(cardNoObj!=null&&!cardNoObj.toString().isEmpty()){
                        cardNo_change=cardNoObj.toString();
                        if(cardNo_change.contains("CD")){
                            cardNo_change=cardNo_change.substring(cardNo_change.indexOf("CD"));
                        }else{
                            cardNo_change=MemberSwtichCardNOUtil.getCardNo(tbTenantInfo.getTenantId(), branchId, cardNo_change);
                        }
                    }
                    resultMap1.put("MCard", cardNo_change);
                    
                    resultMap1.put("status",
                            map1.get("status") == null ? 0 : Integer.valueOf(map1.get("status").toString()));
                    resultMap1.put("StoreCardBalance", map2.get("value"));
                    resultMap1.put("IntegralOverall", map2.get("point"));
                    resultMap1.put("CouponsOverall", 0.0);
                    resultMap1.put("TicketInfo", "");
                    resultMap1.put("TraceCode", "");
                    resultMap1.put("card_type", map1.get("card_type"));
                    Integer CardLevel = 0;
                    String cardLevelName = "无折扣";
                    String level = (String) map1.get("level");
                    Map<String, Object> param = new HashMap<>();
                    TbTenantInfo tenantInfo = tenantService.findByBranchId(branchId);
                    param.put("level", level);
                    param.put("tenantId", tenantInfo.getTenantId());
                    List<TDiscountRule> rules = tDiscountRuleDao.getdiscountRuleByParam(param);
                    if (rules != null && rules.size() > 0) {
                        CardLevel = Integer.valueOf(rules.get(0).getLevel());
                        cardLevelName = rules.get(0).getDiscount();
                    }
                    resultMap1.put("level", CardLevel);
                    resultMap1.put("level_name", cardLevelName);
                    //resultMap1.put("CardList", list);
                    resultMap1.put("member_avatar", map1.get("member_avatar"));
                    result.add(resultMap1);
                }
                resultMap.put("retcode", "0");
                resultMap.put("retInfo", "获取用户卡列表成功");
                resultMap.put("name", name);
                resultMap.put("gender", gender);
                resultMap.put("birthday", birthday);
                resultMap.put("member_address", "");
                resultMap.put("createtime", createtime);
                resultMap.put("mobile", mobile1);
                resultMap.put("result", result);
            } catch (Exception e) {
                log.error("获取用户卡列表失败", e);
                resultMap.put("retcode", "1");
                resultMap.put("retInfo", "获取用户卡列表失败");
                resultMap.put("result", "");
            }
        }
        return Response.ok(URLDecoder.decode(JSONUtils.mapToJson(resultMap), "utf-8")).build();
    }

    @POST
    @Path("/unCardLoseService")
    @Produces({ MediaType.TEXT_PLAIN,MediaType.APPLICATION_JSON })
    public Response unCardLoseService(String jsonString) throws UnsupportedEncodingException {
        Map<String, Object> map = new HashMap<String, Object>();
        boolean flag = false;
        HashMap params = JacksonJsonMapper.jsonToObject(jsonString, HashMap.class);
        try {
            flag = cardInfoService.updateUnCardLose(params);
            if (flag) {
                map.put("Retcode", 0);
                map.put("RetInfo", "OK");
            } else {
                map.put("Retcode", 1);
                map.put("RetInfo", "解除挂失失败");
            }
        } catch (Exception e) {
            //e.printStackTrace();
            map.put("Retcode", 1);
            map.put("RetInfo", "解除挂失失败");
            HashMap<String, Object> log = new HashMap<String, Object>();
            log.put("cardNo", params.get("cardno").toString());
            log.put("slipNo", "");
            log.put("content", "content: " + e.getMessage());
            systemLogService.saveSystemLog(log);
        }
        return Response.ok(URLDecoder.decode(JacksonJsonMapper.objectToJson(map), "utf-8")).build();
    }

    @POST
    @Path("/cardLoseService")
    @Produces({ MediaType.TEXT_PLAIN,MediaType.APPLICATION_JSON })
    public Response cardLoseService(String jsonString) throws UnsupportedEncodingException {
        Map<String, Object> map = null;
        HashMap params = JacksonJsonMapper.jsonToObject(jsonString, HashMap.class);
        try {
            map = cardInfoService.updateCardLose(params);
            if (map == null) {
                map = new HashMap<String, Object>();
            }
            map.put("Retcode", 0);
            map.put("RetInfo", "OK");
        } catch (Exception e) {
            //e.printStackTrace();
            map = new HashMap<String, Object>();
            map.put("Retcode", 1);
            map.put("RetInfo", "挂失失败");
            HashMap<String, Object> log = new HashMap<String, Object>();
            log.put("cardNo", params.get("cardno").toString());
            log.put("slipNo", "");
            log.put("content", "content: " + e.getMessage());
            systemLogService.saveSystemLog(log);
        }
        return Response.ok(URLDecoder.decode(JacksonJsonMapper.objectToJson(map), "utf-8")).build();
    }

    @POST
    @Path("/cardNoByModify")
    @Produces({ MediaType.TEXT_PLAIN,MediaType.APPLICATION_JSON })
    public String cardNoByModify(String jsonString) throws UnsupportedEncodingException{
        //绑定卡信息，需要批量修改所有的相关的数据库信息
        HashMap<String, Object> paramsMap = JacksonJsonMapper.jsonToObject(jsonString, HashMap.class);
        Object cardNO = paramsMap.get("cardno");
        String cardNo = "";
        if (cardNO != null && !cardNO.toString().isEmpty()) {
            cardNo = cardNO.toString();
        } else {
            return getFailure("原卡号不能为空");
        }
        Object branceId = paramsMap.get("branch_id");
        String branceID = "";
        if (branceId != null && !branceId.toString().isEmpty()) {
            branceID = branceId.toString();
        } else {
            return getFailure("门店号不能为空");
        }
        Object new_cardno = paramsMap.get("new_cardno");
        String newCardno = "";
        if (new_cardno != null && !new_cardno.toString().isEmpty()) {
            newCardno = new_cardno.toString();
        } else {
            return getFailure("新卡号不能为空");
        }
        TbTenantInfo tbTenantInfo = tenantService.findByBranchId(branceID);
        if (tbTenantInfo != null) {
            //区分上送卡号为实体卡还是虚拟卡
            if (cardNo.contains("CD")) {
                return getFailure("虚拟卡号不能修改");
            } else {
                cardNo = MemberSwtichCardNOUtil.getInStance()
                        .createCardNo(tbTenantInfo.getBranchId() + tbTenantInfo.getTenantId(), cardNo);
                newCardno = MemberSwtichCardNOUtil.getInStance()
                        .createCardNo(tbTenantInfo.getBranchId() + tbTenantInfo.getTenantId(), newCardno);
            }
        } else {
            return getFailure("该门店不存在,请检查上送门店号是否正确");
        }
        //校验修改为新的卡号是否该门店下已存在
        Map<String, Object> cardParam = new HashMap<String, Object>();
        cardParam.put("cardno", newCardno);
        cardParam.put("branch_id", tbTenantInfo.getBranchId());
        TbCardInfo tbCardInfo = cardInfoService.findActualByParams(cardParam);
        if(tbCardInfo!=null){
            return getFailure("您输入的新卡号已存在，请检查后重新输入！");
        }
        return updateCardInfo(newCardno, cardNo, branceID, tbTenantInfo);
    }

    private String updateCardInfo(String entity_cardNo, String card_no, String branceID, TbTenantInfo tbTenantInfo) {
        Map<String, Object> inputMap = new HashMap<String, Object>();
        inputMap.put("tenantId", tbTenantInfo.getTenantId());
        inputMap.put("branchId", branceID);
        inputMap.put("entityCardNo", entity_cardNo);
        inputMap.put("cardNo", card_no);
        //查询是否存在
        int status = tbMenberBatchService.updateBatchCardNO(inputMap);
        if (status != 0) {
            return getFailure("修改卡号失败");

        } else {
            return getSuccess("修改卡号成功");
        }
    }
    
    @POST
    @Path("/bindingCardService")
    @Produces({ MediaType.TEXT_PLAIN,MediaType.APPLICATION_JSON })
    public String bindingCardService(String jsonString) {
        HashMap<String, Object> paramsMap = JacksonJsonMapper.jsonToObject(jsonString, HashMap.class);
        Object entity_cardNO = paramsMap.get("entity_cardNo");
        String entity_cardNo = "";
        if (entity_cardNO != null && !entity_cardNO.toString().isEmpty()) {
            entity_cardNo = entity_cardNO.toString();
        } else {
            return getFailure("实体卡不能为空");
        }
        Object branceId = paramsMap.get("branch_id");
        String branceID = "";
        if (branceId != null && !branceId.toString().isEmpty()) {
            branceID = branceId.toString();
        } else {
            return getFailure("门店号不能为空");
        }
        Object phone = paramsMap.get("mobile");
        String mobile = "";
        if (phone != null && !phone.toString().isEmpty()) {
            mobile = phone.toString();
        } else {
            return getFailure("手机号不能为空");
        }
        
        Object Level = paramsMap.get("level");
        String level = "";
        if (Level != null && !Level.toString().isEmpty()) {
            level = Level.toString();
        } else {
            return getFailure("折扣类型不能为空");
        }

        TbTenantInfo tbTenantInfo = tenantService.findByBranchId(branceID);
        if (tbTenantInfo != null) {
            entity_cardNo = MemberSwtichCardNOUtil.getInStance()
                    .createCardNo(tbTenantInfo.getBranchId()+tbTenantInfo.getTenantId(), entity_cardNo);
        } else {
            return getFailure("该门店不存在,请检查上送门店号是否正确");
        }
        Map<String, Object> paramsMobile = new HashMap<String, Object>();
        paramsMobile.put("mobile", mobile);
        paramsMobile.put("tenant_id", tbTenantInfo.getTenantId());
        TbMemberManager member = memberManagerService.findByParams(paramsMobile);
        if(null == member) {
        	return getFailure("该会员不存在");
        }
        List<TMemberCard> memberCards = memberManagerService.getMemberCards(member);
        //该会员卡数>1,执行新增卡逻辑
        if (memberCards.size() > 1) {
            if (!checkCard(memberCards, branceID, entity_cardNo)) {
                return getFailure("该卡号已被绑定");
            } else {
                return insertCardInfo(entity_cardNo,level, branceID, mobile, tbTenantInfo, member);
            }
        } else if (memberCards.size() == 1) {
            if (!checkCard(memberCards, branceID, entity_cardNo)) {
                return getFailure("该卡号已被绑定");
            } else {
                //会员卡数=1
                TMemberCard tMemberCard = memberCards.get(0);
                Map<String, Object> cardParam = new HashMap<String, Object>();
                cardParam.put("cardno", tMemberCard.getCardno());
                cardParam.put("branch_id", branceID);
                TbCardInfo tbCardInfo = cardInfoService.findActualByParams(cardParam);
                if (tbCardInfo != null) {
                    if (tbCardInfo.getCard_type() == 0) {
                        //卡类型为虚拟卡，执行更新操作
                        return updateCardInfo(entity_cardNo, tbCardInfo.getCardno(), branceID, tbTenantInfo);
                    } else {
                        //卡类型为实体卡，执行新增卡逻辑
                        return insertCardInfo(entity_cardNo,level, branceID, mobile, tbTenantInfo, member);
                    }
                } else {
                    return getFailure("该会员卡不属于该门店");
                }
            }
        } else {
            //会员卡数<1
            return getFailure("该手机号尚未注册，请先注册后，再进行卡号绑定!");
        }    	
    }
    
    private Boolean checkCard(List<TMemberCard> memberCards, String branchID, String entity_cardNo) {
        int flag = 0;
        for (int i = 0; i < memberCards.size(); i++) {
            TMemberCard tMemberCard = memberCards.get(i);
            Map<String, Object> cardParam = new HashMap<String, Object>();
            cardParam.put("cardno", tMemberCard.getCardno());
//            cardParam.put("branch_id", branchID);
            TbCardInfo tbCardInfo = cardInfoService.findActualByParams(cardParam);
            if(tbCardInfo != null){
            	if (tbCardInfo.getStatus() == '1' && tbCardInfo.getCardno().equals(entity_cardNo)) {
            		return false;
            	} else {
            		flag++;
            	}
            }
        }
        if (flag == memberCards.size()) {
            return true;
        } else {
            return false;
        }
    }    
    
    /**     * inserCardInfo(inserCardInfo) 
     * @param   name
     * @return String    DOM对象    
     * @Exception 异常对象    
     * @since  CodingExample　Ver(编码范例查看) 1.1       
    */
    private String insertCardInfo(String entity_cardNo,String level, String branceID, String mobile, TbTenantInfo tbTenantInfo,
            TbMemberManager member) {
        TbCardInfo tbCardInfo = new TbCardInfo();
        tbCardInfo.setBranch_id(branceID);
        tbCardInfo.setCard_type(1);
        tbCardInfo.setCardno(entity_cardNo);
        tbCardInfo.setMobile(mobile);
        tbCardInfo.setStatus('1');
        tbCardInfo.setLevel(level);
        tbCardInfo.setTenant_id(Integer.valueOf(tbTenantInfo.getTenantId() == null ? "0" : tbTenantInfo.getTenantId()));
        tbCardInfo.setCreatetime(new Date());
        Map<String, Object> insertResult = memberManagerService.insertCard(member, tbCardInfo);
        String msg = insertResult.get("msg").toString();
        if (msg.equals("success")) {
            return getSuccess("绑卡成功");
        } else {
            return getFailure("绑定新卡出现异常");
        }
    }    
}
