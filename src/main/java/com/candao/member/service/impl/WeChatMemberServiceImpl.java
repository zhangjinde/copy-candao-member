package com.candao.member.service.impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.candao.member.dao.TCardAccountDAO;
import com.candao.member.dao.TMemberCardDao;
import com.candao.member.dao.TMemberInfoDao;
import com.candao.member.dao.TbCardInfoDao;
import com.candao.member.model.TCardAccount;
import com.candao.member.model.TMemberCard;
import com.candao.member.model.TbCardInfo;
import com.candao.member.model.TbMemberInfo;
import com.candao.member.model.TbMemberManager;
import com.candao.member.model.TbTenantCard;
import com.candao.member.service.MemberManagerService;
import com.candao.member.service.SendService;
import com.candao.member.service.TenantCardService;
import com.candao.member.service.WeChatMemberService;
import com.candao.member.utils.AccountUtils;

@Service
public class WeChatMemberServiceImpl implements WeChatMemberService{
	
	@Autowired
	private TMemberInfoDao tMemberInfoDao;
	
	@Autowired
	private TMemberCardDao tMemberCardDao; 
 
	@Autowired
	private TenantCardService tenantCardService;
	
	@Autowired
	private MemberManagerService memberManagerService;
	
	@Autowired
	private TCardAccountDAO tCardAccountDAO;
	
	@Autowired
	private SendService sendService;
	
	@Autowired
	private TbCardInfoDao tbCardInfoDao;
	
	/**
	 * 绑定（登录）
	 * 获取会员卡信息
	 */
	@Override
	public Map<String, Object> binding(Map<String, Object> map) throws Exception {
		String phoneNumber = (String) map.get("phoneNumber");
		String passWord = (String) map.get("passWord");
		String tenantId = (String)map.get("tenantId");
		Map<String, Object> param = new HashMap<>();
		if(phoneNumber.length() == 11){
			param.put("mobile", phoneNumber);
		}else{
			String mobile = getPhoneByCardNo(phoneNumber);
			param.put("mobile", mobile);
		}
		param.put("password", passWord);
		param.put("tenant_id", tenantId);
		TbMemberInfo memberInfo = tMemberInfoDao.getMemberInfoByParam(param);
		Map<String, Object> result = new HashMap<>();
		if(memberInfo == null){
			return result;
		}
		TMemberCard card = tMemberCardDao.getMemberCardByMemberId(memberInfo.getId());
		Map<String, Object> params = new HashMap<>();
		params.put("cardno", card.getCardno());
		TbCardInfo cardInfo = tbCardInfoDao.getCardInfoByParam(params);
		if(cardInfo.getStatus() == '0'){
			return result;
		}
		result.put("Cardno", card.getCardno());
		return result;
	}

	/**
	 * 通过卡号获取手机号
	 * @param CardNo
	 * @return
	 */
	private String getPhoneByCardNo(String CardNo){
		TMemberCard card = tMemberCardDao.getMemberCardByCardNo(CardNo);
		TbMemberInfo memberInfo = tMemberInfoDao.getMemberInfoByMemberId(card.getMember_id());
		return memberInfo.getMobile();
	}

	/**
	 * 注册会员
	 */
	@Override
	@Transactional
	public Map<String, Object> register(Map<String, Object> map,
			HttpServletRequest request,HttpServletResponse response) throws Exception {
		Map<String, Object> result = new HashMap<>();
		String phoneNumber = (String) map.get("phoneNumber");
		String passWord = (String) map.get("passWord");
		String tenantId = (String) map.get("tenantId");
		String verifyCode = (String) map.get("verifyCode");
//		验证码验证
		String code = (String) request.getSession().getAttribute("verifyCode");
		if(verifyCode != null && !"".equals(verifyCode)){
			if(!verifyCode.equals(code)){
				result.put("msg", "验证码错误");
				result.put("code", "1");
				return result;
			}
		}
		
//		手机号验证
		Map<String, Object> param = new HashMap<>();
		param.put("mobile", phoneNumber);
		param.put("tenant_id", tenantId);
		TbMemberInfo memberInfo = tMemberInfoDao.getMemberInfoByParam(param);
		if(memberInfo != null){
			result.put("msg", "注册失败，该手机号已注册");
			result.put("code", "1");
			return result;
		}
		
		TbCardInfo tbCardInfo = new TbCardInfo();
		TbTenantCard tbTenantCard = tenantCardService.findByParams(param);
		String card_no_seq = String.valueOf(tbTenantCard.getCurrent_no()); // 会员卡序号
		String card_no = null;
		if(card_no_seq != null && card_no_seq.length() > 0){
			int length = card_no_seq.length();
			if(length <= 6){
				String zero = "";
				for(int i=0; i<6-length ; i++){
					zero = zero + "0";
				}
				card_no = tenantId + zero + card_no_seq ;
			}
			tbCardInfo.setCardno(card_no); //会员卡虚拟卡号
			tbCardInfo.setStatus('1');
			tbCardInfo.setLevel("0");
		}
		TbMemberManager tbMemberManager = new TbMemberManager();
		tbMemberManager.setMobile(phoneNumber);
		tbMemberManager.setPassword(passWord);
		tbMemberManager.setStatus('1');
		tbMemberManager.setTenant_id(tenantId);
		if(tbMemberManager.getBirthday() == null){
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date date = new Date();
			try {
				date = dateFormat.parse("1900-01-01");
			} catch (ParseException e) {
				e.printStackTrace();
			}
			tbMemberManager.setBirthday(date);
		}
		
		if(tbMemberManager.getGender() == null){
			tbMemberManager.setGender('0');
		}
		result = memberManagerService.save(tbMemberManager, tbCardInfo, tbTenantCard);
		if(result != null){
			result.put("Cardno", card_no);
	    }
		return result;
	}

	/**
	 * 查询会员卡余额
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@Override
	public Map<String, Object> balance(Map<String, Object> map) throws Exception {
		String cardno = (String) map.get("cardno");
		TCardAccount cardAccount = tCardAccountDAO.getByCardNo(cardno);
		Map<String, Object> resultmap = new HashMap<>();
		if(cardAccount != null){
			resultmap.put("balance", cardAccount.getValue());
		}
		return resultmap;
	}

	/**
	 * 查询会员卡信息
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@Override
	public Map<String, Object> getMemberInfo(Map<String, Object> map) throws Exception {
		String cardno = (String) map.get("cardno");
		TMemberCard memberCard = tMemberCardDao.getMemberCardByCardNo(cardno);
		TbMemberInfo memberInfo = tMemberInfoDao.getMemberInfoByMemberId(memberCard.getMember_id());
		Map<String, Object> result = new HashMap<>();
		if(memberInfo != null){
			result.put("id", memberInfo.getId());
			result.put("name", memberInfo.getName());
			result.put("gender", memberInfo.getGender());
			result.put("birthday", memberInfo.getBirthday());
		}
		return result;
	}

	/**
	 * 验证手机号是否被注册
	 */
	@Override
	public TbMemberInfo verifyPhoneNumber(Map<String, Object> map) throws Exception {
		String phoneNumber = (String) map.get("phoneNumber");
		String tenantId = (String) map.get("tenantId");
		Map<String, Object> param = new HashMap<>();
		param.put("mobile", phoneNumber);
		param.put("tenant_id", tenantId);
		TbMemberInfo memberInfo = tMemberInfoDao.getMemberInfoByParam(param);
		return memberInfo;
	}

	/**
	 * 修改会员信息
	 */
	@Override
	public void modifyMember(Map<String, Object> map) throws Exception {
		String cardNo = (String)map.get("cardno");
		String name = (String)map.get("Name");
		String gender = (String)map.get("Gender");
		String Birthday = (String)map.get("Birthday");
		TMemberCard memberCard = tMemberCardDao.getMemberCardByCardNo(cardNo);
		Integer memberId = memberCard.getMember_id();
		TbMemberInfo info = new TbMemberInfo();
		info.setId(memberId);
		info.setName(name);
		info.setGender(gender);
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	    Date date = dateFormat.parse(Birthday); 
	    info.setBirthday(date);
	    tMemberInfoDao.updateMemberInfo(info);
	}

	/**
	 * 验证会员是否为默认信息
	 */
	@Override
	public boolean verifyDefaultInfo(Map<String, Object> map) throws Exception {
		String cardNo = (String)map.get("cardno");
		TMemberCard memberCard = tMemberCardDao.getMemberCardByCardNo(cardNo);
		TbMemberInfo info = tMemberInfoDao.getMemberInfoByMemberId(memberCard.getMember_id());
		String gender = info.getGender();
		Date birthday = info.getBirthday();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
		String str = sdf.format(birthday);  
		boolean flag;
		if(gender != null && gender.equals("0") && str != null && str.equals("1900-01-01")){
			flag = true;
		}else{
			flag = false;
		}
		return flag;
	}

	/**
	 * 修改会员卡密码
	 */
	@Override
	public Map<String, Object> modifyPassword(Map<String, Object> map,
			HttpServletRequest request,HttpServletResponse response) throws Exception {
		Map<String, Object> result = new HashMap<>();
		String phoneNumber = (String) map.get("phoneNumber");
		String passWord = (String) map.get("passWord");
		String tenantId = (String) map.get("tenantId");
		String verifyCode = (String) map.get("verifyCode");
//		TODO:验证码校验
		String code = (String) request.getSession().getAttribute("verifyCode");
		if(!verifyCode.equals(code)){
			result.put("msg", "验证码错误");
			result.put("code", "1");	//失败
			return result;
		}
		Map<String, Object> param = new HashMap<>();
		param.put("mobile", phoneNumber);
		param.put("tenant_id", tenantId);
		TbMemberInfo memberInfo = tMemberInfoDao.getMemberInfoByParam(param);
		if(memberInfo == null){
			result.put("msg", "该手机号没有注册会员");
			result.put("code", "1"); //失败
		}else{
			param.put("id", memberInfo.getId());
			param.put("password", passWord);
			tMemberInfoDao.modifyPassword(param);
			result.put("msg", "修改密码成功");
			result.put("code", "0"); //成功
		}
		return result;
	}

	/**
	 * 发送验证码
	 */
	@Override
	public Map<String, Object> getVerifyCode(Map<String, Object> map,
			HttpServletRequest request,HttpServletResponse response) throws Exception {
		Map<String, Object> resultMap = new HashMap<>();
		String phoneNumber = (String) map.get("phoneNumber");
		String password = AccountUtils.getRandomPassword();
		request.getSession().setAttribute("verifyCode", password);
		sendService.sendValicodeBySms(phoneNumber,password);
		resultMap.put("verifyCode", password);
		return resultMap;
	}
}
