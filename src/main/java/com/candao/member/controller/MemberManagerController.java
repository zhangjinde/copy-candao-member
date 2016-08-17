package com.candao.member.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.candao.member.dao.TDiscountRuleDao;
import com.candao.member.dao.impl.Page;
import com.candao.member.model.TDiscountRule;
import com.candao.member.model.TMemberCard;
import com.candao.member.model.TbCardInfo;
import com.candao.member.model.TbMemberManager;
import com.candao.member.model.TbTenantCard;
import com.candao.member.model.TbTenantInfo;
import com.candao.member.service.CardAccountService;
import com.candao.member.service.CardInfoService;
import com.candao.member.service.MemberInfoService;
import com.candao.member.service.MemberManagerService;
import com.candao.member.service.SendService;
import com.candao.member.service.TbMenberBatchService;
import com.candao.member.service.TenantCardService;
import com.candao.member.service.TenantService;
import com.candao.member.utils.DateUtils;
import com.candao.member.utils.HttpClientUtils;
import com.candao.member.utils.JSONUtils;
import com.candao.member.utils.JacksonJsonMapper;
import com.candao.member.utils.MemberSwtichCardNOUtil;
import com.candao.member.utils.PropertiesUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import com.candao.member.utils.HttpUtils;

@Controller
@RequestMapping("/memberManager")
public class MemberManagerController extends BaseController {
    @Autowired
    private MemberManagerService memberManagerService;

    @Autowired
    private TenantCardService tenantCardService;

    @Autowired
    private CardInfoService cardInfoService;

    @Autowired
    private SendService sendService;

    @Autowired
    private CardAccountService cardAccountService;

    @Autowired
    private TenantService tenantService;

    @Autowired
    private TDiscountRuleDao tDiscountRuleDao;

    @Autowired
    private TbMenberBatchService tbMenberBatchService;

    @Autowired
    private MemberInfoService memberInfoService;

    private static Logger log = LoggerFactory.getLogger(MemberManagerController.class);

    @RequestMapping("/page")
    @ResponseBody
    public ModelAndView page(@RequestParam Map<String, Object> params, int page, int rows) {
        Page<Map<String, Object>> pageMap = memberManagerService.grid(params, page, rows);
        ModelAndView mav = new ModelAndView();
        mav.addObject("page", pageMap);
        return mav;
    }

    /**
     * 获取所有打印机
     * @param params
     * @return
     */
    @RequestMapping("/find")
    @ResponseBody
    public ModelAndView find(@RequestParam Map<String, Object> params) {
        List<Map<String, Object>> list = memberManagerService.find(params);
        List<Map<String, Object>> lists = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < list.size(); i++) {
            lists.add(list.get(i));
        }
        ModelAndView mav = new ModelAndView();
        mav.addObject("find", list);
        return mav;
    }

    @RequestMapping(value = "/save", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String save(HttpServletRequest request, HttpServletResponse response, @RequestBody String jsonString) {
        Map<String, Object> map = new HashMap<String, Object>();
        @SuppressWarnings("unchecked")
        HashMap<String, Object> paramsJson = JacksonJsonMapper.jsonToObject(jsonString, HashMap.class);

        //		手机号，门店id不能为空的判断
        if (paramsJson.get("mobile") == null || paramsJson.get("mobile").toString().equals("")) {
            map.put("Retcode", 1);
            map.put("RetInfo", "手机号不能为空");
            return JacksonJsonMapper.objectToJson(map);
        }
        if (paramsJson.get("branch_id") == null || paramsJson.get("branch_id").toString().equals("")) {
            map.put("Retcode", 1);
            map.put("RetInfo", "门店ID不能为空");
            return JacksonJsonMapper.objectToJson(map);
        }

        //		分店租户信息
        TbTenantInfo tbTenantInfo = tenantService.findByBranchId(paramsJson.get("branch_id").toString());
        if (tbTenantInfo == null) {
            map.put("Retcode", 1);
            map.put("RetInfo", "没找到门店所属的租户");
            return JacksonJsonMapper.objectToJson(map);
        }

        //		验证手机号是否存在
        /*Map<String, Object> paramsMobile = new HashMap<String, Object>();
        paramsMobile.put("mobile", paramsJson.get("mobile").toString());
        paramsMobile.put("tenant_id", tbTenantInfo.getTenantId());
        TbMemberManager member = memberManagerService.findByParams(paramsMobile);
        if (member != null) {
            map.put("Retcode", 1);
            map.put("RetInfo", "该手机号码已存在.");
            map.put("cardno", "");
            return JacksonJsonMapper.objectToJson(map);
        }*/

        //		会员基本信息
        TbMemberManager tbMemberManager = (TbMemberManager) JacksonJsonMapper.jsonToObject(jsonString,
                TbMemberManager.class);
        tbMemberManager.setStatus('1');
        tbMemberManager.setTenant_id(tbTenantInfo.getTenantId());
        if (tbMemberManager.getBirthday() == null) {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date();
            try {
                date = dateFormat.parse("1900-01-01");
            } catch (ParseException e) {
                e.printStackTrace();
            }
            tbMemberManager.setBirthday(date);
        }
        if (tbMemberManager.getGender() == null) {
            tbMemberManager.setGender('0');
        }

        //		会员卡基本信息
        TbCardInfo tbCardInfo = (TbCardInfo) JacksonJsonMapper.jsonToObject(jsonString, TbCardInfo.class);

        tbCardInfo.setCreatetime(new Date());
        //		卡池信息
        Map<String, Object> params = new HashMap<String, Object>();
        //		params.put("branch_id", paramsJson.get("branch_id").toString());
        //params.put("tenant_id", tbTenantInfo.getTenantId());

        params.put("tenant_id", "-999999");//虚拟卡生成时去除指定租户改为统一控制，定义租户id为-999999表示生成虚拟卡号不区分租户号

        synchronized (this) {
            String card_no = null;
            TbTenantCard tbTenantCard = null;
            //验证是是否是带有卡号注册如果带有卡号注册那么，使用以有的卡号注册
            if (paramsJson.get("cardno") != null && !paramsJson.get("cardno").toString().isEmpty()) {
                String enCardNo = paramsJson.get("cardno").toString();
                //校验上送卡号是否是已被随机生成的
                String parseCardNo = MemberSwtichCardNOUtil.getInStance()
                        .createCardNo( tbTenantInfo.getBranchId()+tbTenantInfo.getTenantId(), enCardNo);
                int count = cardInfoService.findIsExist(tbTenantInfo.getTenantId(), parseCardNo);
                if (count > 0) {
                    HashMap<String, String> result = new HashMap<String, String>();
                    result.put("Retcode", "1");
                    result.put("RetInfo", "该会员卡已存在");
                    result.put("cardno", enCardNo);
                    return JSONUtils.mapToJson(result);
                }
                card_no = enCardNo;
                tbCardInfo.setCardno(parseCardNo); //会员实体卡号
                tbCardInfo.setStatus('1');
                tbCardInfo.setLevel("0");
                tbCardInfo.setCard_type(1);
            } else {
                tbTenantCard = tenantCardService.findByParams(params);
                //					String card_no = tbTenantCard.getCurrent_no(); // 会员卡序号
                Object card_no_seq = tbTenantCard.getCurrent_no(); // 会员卡序号
                if (card_no_seq != null && card_no_seq.toString().length() > 0) {
                    int length = card_no_seq.toString().length();
                    if (length <= 8) {
                        String zero = "";
                        for (int i = 0; i < 8 - length; i++) {
                            zero = zero + "0";
                        }
                        card_no = "CD" + zero + card_no_seq;
                    }
                    tbCardInfo.setCardno(MemberSwtichCardNOUtil.getInStance().createCardNo(card_no)); //会员卡虚拟卡号
                    tbCardInfo.setStatus('1');
                    tbCardInfo.setLevel("0");
                    tbCardInfo.setCard_type(0);//虚拟会员卡
                }
            }

            map = memberManagerService.save(tbMemberManager, tbCardInfo, tbTenantCard);

            if (map != null) {
                map.put("Retcode", 0);
                map.put("RetInfo", "OK");
                map.put("cardno", card_no);
            } else {
                map.put("Retcode", 1);
                map.put("RetInfo", "注册失败");
                map.put("cardno", "");
            }
        }
        return JacksonJsonMapper.objectToJson(map);

    }

    /**
     * 
     * @param request
     * @param response
     * @param jsonString
     * 判断是否当前实体卡是否绑定
     * @return
     */
    @RequestMapping("/byUserTouse")
    @ResponseBody
    public String byUserTouse(HttpServletRequest request, HttpServletResponse response,
            @RequestBody String jsonString) {
        HashMap<String, Object> paramsJson = JacksonJsonMapper.jsonToObject(jsonString, HashMap.class);
        String cardNo = String.valueOf(paramsJson.get("cardno"));
        String branceID = String.valueOf(paramsJson.get("branch_id"));
        TbTenantInfo tbTenantInfo = tenantService.findByBranchId(branceID);
        if (tbTenantInfo == null) {
            return getFailure("当前门店不存在");
        }
        cardNo = MemberSwtichCardNOUtil.getInStance().createCardNo(branceID+tbTenantInfo.getTenantId(), cardNo);
        paramsJson.put("cardno", cardNo);
        //查询卡zhuangt
        TbCardInfo cardInfo = cardInfoService.byUserTouse(paramsJson);
        if (cardInfo == null) {
            return getSuccess();
        } else if (cardInfo.getStatus() == 0) {
            return getFailure("已注销!");
        } else {
            return getFailure("已注册!");
        }
    }

    /**
     * 
     * @param request
     * @param response
     * @param jsonString
     * 实体卡绑定
     * @return
     */
   /* @RequestMapping("/bindingCard")
    @ResponseBody
    public String bindingCard(HttpServletRequest request, HttpServletResponse response,
            @RequestBody String jsonString) {
        //绑定卡信息，需要批量修改所有的相关的数据库信息
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
                    return getFailure("卡号不存在");
                }
            }
        } else {
            //会员卡数<1
            return getFailure("该手机号尚未注册，请先注册后，再进行卡号绑定!");
        }
    }*/

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

    /**     * updateCardInfo(更新所有卡号信息) 
     * @param   name
     * @return String    DOM对象    
     * @Exception 异常对象    
     * @since  CodingExample　Ver(编码范例查看) 1.1       
    */
    private String updateCardInfo(String entity_cardNo, String card_no, String branceID, TbTenantInfo tbTenantInfo) {
        Map<String, Object> inputMap = new HashMap<String, Object>();
        inputMap.put("tenantId", tbTenantInfo.getTenantId());
        inputMap.put("branchId", branceID);
        inputMap.put("entityCardNo", entity_cardNo);
        inputMap.put("cardNo", card_no);
        //查询是否存在
        int status = tbMenberBatchService.updateBatchCardNO(inputMap);
        if (status != 0) {
            return getFailure("激活失败!");

        } else {
            return getSuccess("绑卡成功");
        }
    }

    private Boolean checkCard(List<TMemberCard> memberCards, String branchID, String entity_cardNo) {
        int flag = 0;
        for (int i = 0; i < memberCards.size(); i++) {
            TMemberCard tMemberCard = memberCards.get(i);
            Map<String, Object> cardParam = new HashMap<String, Object>();
            cardParam.put("cardno", tMemberCard.getCardno());
            cardParam.put("branch_id", branchID);
            TbCardInfo tbCardInfo = cardInfoService.findActualByParams(cardParam);
            if (tbCardInfo.getStatus() == '1' && tbCardInfo.getCardno().equals(entity_cardNo)) {
                return false;
            } else {
                flag++;
            }
        }
        if (flag == memberCards.size()) {
            return true;
        } else {
            return false;
        }
    }

    @RequestMapping("/MemberEdit")
    @ResponseBody
    public String memberEdit(HttpServletRequest request, HttpServletResponse response, @RequestBody String jsonString) {
        Map<String, Object> map = new HashMap<String, Object>();
        boolean result = false;
        HashMap paramsJson = JacksonJsonMapper.jsonToObject(jsonString, HashMap.class);
        if (paramsJson.get("mobile") != null && !paramsJson.get("mobile").toString().equals("")
                && paramsJson.get("branch_id") != null && !paramsJson.get("branch_id").toString().equals("")) {
            //分店租户信息
            TbTenantInfo tbTenantInfo = tenantService.findByBranchId(paramsJson.get("branch_id").toString());
            if (tbTenantInfo == null) {
                map.put("Retcode", 1);
                map.put("RetInfo", "没找到门店所属的租户");
                return JacksonJsonMapper.objectToJson(map);
            }
            Map<String, Object> params2 = new HashMap<String, Object>();
            params2.put("mobile", paramsJson.get("mobile").toString());
            params2.put("tenant_id", tbTenantInfo.getTenantId());
            TbMemberManager findMember = memberManagerService.findByParams(params2);
            if (findMember != null) {
                if (paramsJson.get("password") != null && !paramsJson.get("password").toString().equals("")) {
                    findMember.setPassword(paramsJson.get("password").toString());
                }
                if (paramsJson.get("name") != null && !paramsJson.get("name").toString().equals("")) {
                    findMember.setName(paramsJson.get("name").toString());
                }
                if (paramsJson.get("gender") != null && !paramsJson.get("gender").toString().equals("")) {
                    if ("1".equals(paramsJson.get("gender").toString())) {
                        findMember.setGender('1');
                    } else if ("0".equals(paramsJson.get("gender").toString())) {
                        findMember.setGender('0');
                    }
                }
                if (paramsJson.get("birthday") != null && !paramsJson.get("birthday").toString().equals("")) {
                    findMember.setBirthday(DateUtils.parse(paramsJson.get("birthday").toString()));
                }
                if (paramsJson.get("member_address") != null
                        && !paramsJson.get("member_address").toString().equals("")) {
                    findMember.setMember_address(paramsJson.get("member_address").toString());
                }
                if (paramsJson.get("member_avatar") != null && !paramsJson.get("member_avatar").toString().equals("")) {
                    findMember.setMember_avatar(paramsJson.get("member_avatar").toString());
                }
                result = memberManagerService.update(findMember);
            }
        }
        if (result) {
            map.put("Retcode", 0);
            map.put("RetInfo", "OK");
        } else {
            map.put("Retcode", 1);
            map.put("RetInfo", "修改会员失败");
        }
        return JacksonJsonMapper.objectToJson(map);

    }

    /**
     * 名称不能重复验证
     * @param tbMemberManager
     * @return
     */
    @RequestMapping("/validateTbMemberManager")
    @ResponseBody
    public String validateArticle(HttpServletRequest request, HttpServletResponse response,
            @RequestBody String jsonString) {
        TbMemberManager tbMemberManager = (TbMemberManager) JacksonJsonMapper.jsonToObject(jsonString,
                TbMemberManager.class);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        String mobile = tbMemberManager.getMobile();
        //手机号长度校验
        if (mobile == null || mobile.length() != 11) {
            resultMap.put("Retcode", 1);
            resultMap.put("RetInfo", "请输入11位手机号码");
            return JacksonJsonMapper.objectToJson(resultMap);
        }

        TbTenantInfo tbTenantInfo = tenantService.findByBranchId(tbMemberManager.getBranch_id());
        if (tbTenantInfo == null) {
            resultMap.put("Retcode", 1);
            resultMap.put("RetInfo", "没找到门店所属的租户");
            return JacksonJsonMapper.objectToJson(resultMap);
        }

        //手机号是否被使用
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("mobile", mobile);
        params.put("tenant_id", tbTenantInfo.getTenantId());
        List<Map<String, Object>> list = memberManagerService.findNocardByMobile(params);
        if (list != null && list.size() > 0) {
            resultMap.put("Retcode", 1);
            resultMap.put("RetInfo", "该手机号码已注册");
            return JacksonJsonMapper.objectToJson(resultMap);
        }
        resultMap.put("Retcode", 0);
        resultMap.put("RetInfo", "OK");
        return JacksonJsonMapper.objectToJson(resultMap);
    }

    /**
     * 发送会员验证码，使用手机发送
     *注册，修改前验证码发送 change by 2016-07-08 添加短信端返回状态 100 为短信发送成功
     */
    @RequestMapping(value = "/sendAccountByMobile")
    @ResponseBody
    public String sendAccountByMobile(HttpServletRequest request, HttpServletResponse response,
            @RequestBody String jsonString) throws IOException {
        Map<String, Object> jsonMap = (Map<String, Object>) JacksonJsonMapper.jsonToObject(jsonString, Map.class);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            //发送手机短信
            String account = String.valueOf(jsonMap.get("account"));
            String mobile = String.valueOf(jsonMap.get("mobile"));

            String responsedata = sendService.sendAccountBySms(request, response, mobile, account);//status：100为成功
            String valicode = (String) request.getSession().getAttribute("valicode");

            JSONObject json = JSONObject.fromObject(responsedata);

            if (json.get("status") != null && json.get("status").toString().equals("100")) {
                resultMap.put("Retcode", 0);
                resultMap.put("RetInfo", "OK");
                resultMap.put("valicode", valicode);
            } else {
                resultMap.put("Retcode", 1);
                resultMap.put("RetInfo", "失败");
            }

        } catch (Exception e) {
            resultMap.put("Retcode", 1);
            resultMap.put("RetInfo", "失败");
        }
        return JacksonJsonMapper.objectToJson(resultMap);
    }

    /**
     * 查询会员信息
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("/findByParams")
    @ResponseBody
    public String findById(HttpServletRequest request, HttpServletResponse response, @RequestBody String jsonString) {
        Map<String, Object> jsonMap = (Map<String, Object>) JacksonJsonMapper.jsonToObject(jsonString, HashMap.class);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        TbTenantInfo tbTenantInfo = new TbTenantInfo();

        String card_no = jsonMap.get("cardno").toString();
        if (card_no.length() == 11) { //前端传入的是手机号码
            jsonMap.put("mobile", card_no);
            jsonMap.put("cardno", "");
            //如果是根据手机号码查询会员，需要结合租户ID确定会员的唯一
            if (jsonMap.get("branch_id") == null || jsonMap.get("branch_id").toString().equals("")) {
                resultMap.put("Retcode", 1);
                resultMap.put("RetInfo", "缺少门店ID");
                return JacksonJsonMapper.objectToJson(resultMap);
            }
            //分店租户信息

            tbTenantInfo = tenantService.findByBranchId(jsonMap.get("branch_id").toString());

            if (tbTenantInfo == null) {
                Map<String, Object> map = new HashMap<>();
                map.put("Retcode", 1);
                map.put("RetInfo", "没找到门店所属的租户");
                return JacksonJsonMapper.objectToJson(map);
            }
            jsonMap.put("tenant_id", tbTenantInfo.getTenantId());
        }
        if (card_no.length() != 11) {
            //老版本会员账号,以及实体账号
            tbTenantInfo = tenantService.findByBranchId(jsonMap.get("branch_id").toString());
            
            if (tbTenantInfo == null) {
                Map<String, Object> map = new HashMap<>();
                map.put("Retcode", 1);
                map.put("RetInfo", "没找到门店所属的租户");
                return JacksonJsonMapper.objectToJson(map);
            }
            String newCard_no="";
            String tenantId = tbTenantInfo != null ? tbTenantInfo.getTenantId() : "";
            if(card_no.contains("CD")){
            	card_no = card_no.substring(card_no.indexOf("CD"));
                newCard_no= MemberSwtichCardNOUtil.getInStance().createCardNo(card_no);
            }else{
                newCard_no = MemberSwtichCardNOUtil.getInStance().createCardNo(jsonMap.get("branch_id").toString()+tenantId, card_no);
            }
            jsonMap.put("cardno", newCard_no);
            jsonMap.put("tenant_id", tbTenantInfo.getTenantId());
        }
        List<Map<String, Object>> list = memberManagerService.findListByParams(jsonMap);
        if (list == null || list.size() == 0) {
            resultMap.put("Retcode", 1);
            resultMap.put("RetInfo", "会员账号不存在或密码错误");
            return JacksonJsonMapper.objectToJson(resultMap);
        } else {
            Map<String, Object> map1 = new HashMap<String, Object>();
            map1 = list.get(0);
            resultMap.put("Retcode", 0);
            resultMap.put("RetInfo", "会员查询成功");
            Map<String, Object> map2 = cardAccountService.getBalanceByCardNo(String.valueOf(map1.get("cardno")));
            resultMap.put("StoreCardBalance", map2.get("value"));
            resultMap.put("IntegralOverall", map2.get("point"));

            resultMap.put("MCard", map1.get("cardno"));
            resultMap.put("CouponsOverall", "");
            resultMap.put("TicketInfo", "");
            resultMap.put("TraceCode", "");
            resultMap.put("CardType", map1.get("card_type"));

            String CardLevel = "无折扣";
            String level = (String) map1.get("level");
            Map<String, Object> param = new HashMap<>();
            TbTenantInfo tenantInfo = tenantService.findByBranchId(jsonMap.get("branch_id").toString());
            param.put("level", level);
            param.put("tenantId", tenantInfo.getTenantId());
            List<TDiscountRule> rules = tDiscountRuleDao.getdiscountRuleByParam(param);
            if (rules != null && rules.size() > 0) {
                CardLevel = rules.get(0).getDiscount();
            }
            resultMap.put("CardLevel", CardLevel);

            resultMap.put("mobile", map1.get("mobile"));
            resultMap.put("name", map1.get("name"));
            resultMap.put("gender", map1.get("gender"));
            resultMap.put("birthday", map1.get("birthday"));
            resultMap.put("MemberAddress", "");
            resultMap.put("RegDate", map1.get("createtime"));
            resultMap.put("CardList", list);
            resultMap.put("member_avatar", map1.get("member_avatar"));

            return JacksonJsonMapper.objectToJson(resultMap);
        }
    }

    @RequestMapping("/delete")
    @ResponseBody
    public String deleteById(HttpServletRequest request, HttpServletResponse response, @RequestBody String jsonString) {
        Map<String, Object> jsonMap = (Map<String, Object>) JacksonJsonMapper.jsonToObject(jsonString, Map.class);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        boolean a = false;
        boolean b = false;

        if (jsonMap.get("branch_id") != null && !"".equals(jsonMap.get("branch_id")) && jsonMap.get("cardno")!=null && !"".equals(jsonMap.get("cardno"))) {
            String card_no = jsonMap.get("cardno").toString();
            String branchid=jsonMap.get("branch_id").toString();
            TbTenantInfo tbTenantInfo = tenantService.findByBranchId(branchid);
	        if (tbTenantInfo == null) {
	        	resultMap.put("Retcode", 1);
	        	resultMap.put("RetInfo", "没找到门店所属的租户");
	            return JacksonJsonMapper.objectToJson(resultMap);
	        }
            if (card_no.length() == 11) { // 前端传入的是手机号码
                jsonMap.put("mobile", card_no);
                jsonMap.put("cardno", "");
                // 根据门店ID查询租户ID
                jsonMap.put("tenant_id", tbTenantInfo.getTenantId());
                resultMap = memberManagerService.deletelistByParams(jsonMap);
            } else {
            	if(card_no.length()!=30){
            		if(card_no.contains("CD")){
                		card_no=MemberSwtichCardNOUtil.getInStance().createCardNo(card_no);
    				}else{
    					card_no=MemberSwtichCardNOUtil.getInStance().createCardNo(branchid+tbTenantInfo.getTenantId(), card_no);
    				}
            	}
                Map<String, Object> map2 = cardAccountService.getBalanceByCardNo(card_no);
                resultMap.put("StoreCardBalance", map2.get("value"));
                resultMap.put("IntegralOverall", map2.get("point"));
                jsonMap.put("moneyValue", map2.get("value"));
                jsonMap.put("cardno", card_no);
                b = cardInfoService.deleteByParams(jsonMap);
                if (b == false) {
                    resultMap.clear();
                }
            }
        }
        if (resultMap.get("StoreCardBalance") != null || b) {
            resultMap.put("Retcode", 0);
            resultMap.put("RetInfo", "OK");
        } else {
            resultMap.put("Retcode", 1);
            resultMap.put("RetInfo", "失败");
        }
        return JacksonJsonMapper.objectToJson(resultMap);
    }

    /**
     * 
     * @param request
     * @param response
     * @param jsonString
     * 实体卡绑定 create by 2016-07-09
     * @return
     */
    @RequestMapping("/webBindingCard")
    @ResponseBody
    public Object webBindingCard(HttpServletRequest request, HttpServletResponse response,
            @RequestBody String jsonString) {
    	JSONObject json = JSONObject.fromObject(jsonString);
    	JSONArray jsonArray = json.getJSONArray("cardInfo");
    	String mobile = json.get("telepno").toString();
    	List<String> resultFailedList = new ArrayList<>();
    	List<String> resultSuccesList = new ArrayList<>();
    	HashMap<String,Object> resultMap = new HashMap<>();
    	String result = "";
    	for(int i = 0 ;i<jsonArray.size();i++){
    		JSONObject jsontemp = JSONObject.fromObject(jsonArray.get(i));
    		String entity_cardNo = jsontemp.get("cardName").toString();//会员卡号
    		String level = jsontemp.get("menberRank").toString();//会员等级 
    		String branceID = jsontemp.get("memberStores").toString();//会员门店号
    		HashMap<String,Object> temp = new HashMap<>();
    		temp.put("mobile", mobile);
    		temp.put("entity_cardNo", entity_cardNo);
    		temp.put("level", level);
    		temp.put("branch_id", branceID);
    		jsonString = JacksonJsonMapper.objectToJson(temp);
    		String url = PropertiesUtils.getValue("web_service_interface");
    		try{
    			result = HttpClientUtils.callByPost(url+"/rest/memberCardService/bindingCardService", jsonString);//bingCard(request,response,jsonString);//调用绑卡接口
    		}catch(Exception e){
    			resultFailedList.add(entity_cardNo);
    			continue;
    		}
    		JSONObject resultJson = JSONObject.fromObject(result);
    		if(resultJson.get("retcode").toString().equals("1")){//绑定失败时code=1添加卡号并返回该卡号失败
    			resultFailedList.add(entity_cardNo);
    			continue;
    		}
    		resultSuccesList.add(entity_cardNo);
    	}
    	resultMap.put("success", resultSuccesList);//成功的会员号
    	resultMap.put("fail", resultFailedList);//失败的会员号
    	return JSONObject.fromObject(resultMap);
   }
    
    /**
 	 * 
 	 * @param request
 	 * @param response
 	 * @param jsonString
 	 * 实体卡绑定
 	 * @return
 	 */
 	@RequestMapping("/bindingCard")
 	@ResponseBody
 	public String bindingCard(HttpServletRequest request,HttpServletResponse response,@RequestBody String jsonString){
 		//绑定卡信息，需要批量修改所有的相关的数据库信息
 		HashMap<String,Object> paramsMap=JacksonJsonMapper.jsonToObject(jsonString, HashMap.class);
 		String entity_cardNo=String.valueOf(paramsMap.get("entity_cardNo"));
 		String card_no=String.valueOf(paramsMap.get("cardno"));
 		String branceID=String.valueOf(paramsMap.get("branch_id"));
 	
 		TbTenantInfo tbTenantInfo = tenantService.findByBranchId(branceID);
 		entity_cardNo=MemberSwtichCardNOUtil.getInStance().createCardNo(tbTenantInfo.getBranchId()+tbTenantInfo.getTenantId(),entity_cardNo);
 		
 		
 		Map<String, Object> inputMap=new HashMap<String,Object>();
 		inputMap.put("tenantId", tbTenantInfo.getTenantId());
 		inputMap.put("branchId", branceID);
 		inputMap.put("entityCardNo", entity_cardNo);
 		inputMap.put("cardNo", card_no);
 		//查询是否存在
 	   int	status=tbMenberBatchService.updateBatchCardNO(inputMap);
 	   if(status!=0){
 		   return getFailure("激活失败!");
 		
 	   }else{
 		   return getSuccess();
 	   }
}
}
