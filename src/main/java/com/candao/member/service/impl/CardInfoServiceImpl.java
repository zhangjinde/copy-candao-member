package com.candao.member.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.candao.member.dao.TCardAccountDAO;
import com.candao.member.dao.TDealDetailDAO;
import com.candao.member.dao.TDealMasterDAO;
import com.candao.member.dao.TMemberServiceDAO;
import com.candao.member.dao.TbCardInfoDao;
import com.candao.member.dao.impl.Page;
import com.candao.member.model.TCardAccount;
import com.candao.member.model.TDealDetail;
import com.candao.member.model.TDealMaster;
import com.candao.member.model.TMemberService;
import com.candao.member.model.TbCardInfo;
import com.candao.member.service.CardAccountService;
import com.candao.member.service.CardInfoService;
import com.candao.member.utils.Constant;
import com.candao.member.utils.SessionUtils;

@Service
public class CardInfoServiceImpl implements CardInfoService {
	@Autowired
	private TbCardInfoDao tbCardInfoDao;

	@Autowired
	private TMemberServiceDAO tMemberServiceDao;

	@Autowired
	private CardAccountService cardAccountService;

	@Autowired
	private TDealMasterDAO tDealMasterDAO;

	@Autowired
	private TDealDetailDAO tDealDetailDAO;

	@Autowired
	private TCardAccountDAO tCardAccountDAO;

	@Override
	public Page<Map<String, Object>> grid(Map<String, Object> params, int current, int pagesize) {
		params.put("tenant_id", SessionUtils.get(Constant.CURRENT_TENANT_ID));
		return tbCardInfoDao.page(params, current, pagesize);
	}

	public List<Map<String, Object>> find(Map<String, Object> params) {
		return tbCardInfoDao.find(params);
	}

	@Override
	public boolean save(TbCardInfo tbTenantCard) {
		return tbCardInfoDao.insert(tbTenantCard) > 0;
	}

	@Override
	public TbCardInfo findByParams(Map<String, Object> params) {
		return tbCardInfoDao.get(params);
	}
	
	@Override
    public TbCardInfo findActualByParams(Map<String, Object> params) {
        return tbCardInfoDao.getActualCard(params);
    }

	@Override
	public TbCardInfo findById2(String id) {
		return tbCardInfoDao.findOne(id);
	}

	@Override
	public boolean update(TbCardInfo tbTenantCard) {
		return tbCardInfoDao.update(tbTenantCard) > 0;
	}

	@Override
	public boolean deleteByParams(Map<String, Object> params) {
		 String cardno = String.valueOf(params.get("cardno"));

         //添加交易明细（因为是注销，没涉及到其他交易，所以没有向交易主表添加数据） change by 2016-07-09
         TDealDetail tDealDetailCash = new TDealDetail();
         tDealDetailCash.setCard_no(cardno);
         tDealDetailCash.setDeal_no("");
         tDealDetailCash.setDeal_addr("");
         tDealDetailCash.setDeal_type("12");//会员注销退款
         tDealDetailCash.setAmount(-1 * Double.valueOf(params.get("moneyValue").toString()));
         tDealDetailCash.setSlip_no("");
         tDealDetailCash.setDeal_user("Test");
         tDealDetailDAO.add(tDealDetailCash);
         
         //更新账号信息  change by 2016-07-09
         TCardAccount cardAccount = new TCardAccount();
         cardAccount.setCard_no(cardno);
         cardAccount.setValue(0);
         cardAccount.setPoint(0);
         tCardAccountDAO.update(cardAccount);
         
		return tbCardInfoDao.delete(params) > 0;
	}

	public int findMaxID() {
		return tbCardInfoDao.findMaxID();
	}

	@Override
	public String getCardStatusByCardNo(String CardNo) {
		// TODO Auto-generated method stub

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("cardno", CardNo);
		String status = null;

		TbCardInfo tbCardInfo = tbCardInfoDao.get(params);
		if (tbCardInfo != null) {
			status = String.valueOf(tbCardInfo.getStatus());
		}
		return status;
	}

	@Override
	public Map<String, Object> updateCardLose(Map<String, Object> params) {
		HashMap<String, Object> result = null;

		TbCardInfo tbCardInfo = new TbCardInfo();
		tbCardInfo.setCardno(params.get("cardno").toString());
		tbCardInfo.setStatus('2'); // 挂失状态
		tbCardInfoDao.update(tbCardInfo);

		TMemberService tMemberService = new TMemberService();
		tMemberService.setMemberId(0);
		tMemberService.setCardId(0);
		tMemberService.setCardNo(params.get("cardno").toString());
		tMemberService.setTenantId("");
		tMemberService.setService("GSFW");// 挂失服务
		tMemberService.setBranchId(params.get("branch_id").toString());
		tMemberService.setComment(params.get("FMemo").toString());
		tMemberService.setServiceUser("");
		tMemberServiceDao.insert(tMemberService);

		result = (HashMap<String, Object>) cardAccountService.getBalanceByCardNo(params.get("cardno").toString());

		return result;
	}

	@Override
	public boolean updateUnCardLose(Map<String, Object> params) {
		boolean result = true;

		TbCardInfo tbCardInfo = new TbCardInfo();
		tbCardInfo.setCardno(params.get("cardno").toString());
		tbCardInfo.setStatus('1'); // 解除挂失状态，设置为正常状态
		result = result && tbCardInfoDao.update(tbCardInfo) > 0;

		TMemberService tMemberService = new TMemberService();
		tMemberService.setMemberId(0);
		tMemberService.setCardId(0);
		tMemberService.setCardNo(params.get("cardno").toString());
		tMemberService.setTenantId("");
		tMemberService.setService("JCGSFW");// 解除挂失服务
		tMemberService.setBranchId(params.get("branch_id").toString());
		tMemberService.setComment(params.get("FMemo").toString());
		tMemberService.setServiceUser("");
		result = result && tMemberServiceDao.insert(tMemberService) > 0;

		return result;
	}

	@Override
	public List<TDealDetail> findDealDetailByCardno(String cardno, int current) {
		Map<String, Object> map = new HashMap<>();
		map.put("cardno", cardno);
		List<TDealMaster> dealMasters = tDealMasterDAO.findDealDetailByCardno(map);
		List<TDealDetail> dealDetails = new ArrayList<>();
		for (TDealMaster dealMaster : dealMasters) {
			String deal_no = dealMaster.getDeal_no();
			List<TDealDetail> details = tDealDetailDAO.findByDealNo(deal_no);
			dealDetails.addAll(details);
		}
		return dealDetails;
	}

	/**
	 * 查询该租户下所有会员的总余额和总积分
	 */
	@Override
	public Map<String, Object> getAllMemberBalance() {
		Map<String, Object> params = new HashMap<>();
		params.put("tenant_id", SessionUtils.get(Constant.CURRENT_TENANT_ID));
//		List<TbCardInfo> cardInfos = tbCardInfoDao.getAllCardInfo(params);
//		BigDecimal balance = new BigDecimal(0);	//总余额
//		BigDecimal point = new BigDecimal(0);	//总积分
//		StringBuilder cnos = new StringBuilder();
//		for(TbCardInfo cardInfo : cardInfos){
//			cnos.append("'").append(cardInfo.getCardno()).append("'").append(",");
//		}
//		String card_nos = cnos.toString();
//		card_nos = card_nos.substring(0, card_nos.length() - 1);
//		List<TCardAccount> cardAccounts = tCardAccountDAO.getCardAccountByCardnos(card_nos);
//		for(TCardAccount account : cardAccounts){
//			BigDecimal card_value = new BigDecimal(account.getValue());
//			BigDecimal card_point = new BigDecimal(account.getPoint());
//			balance = balance.add(card_value);
//			point = point.add(card_point);
//		}
		Map<String,Object> result = tCardAccountDAO.getSum(params);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("balance", result != null?result.get("balance"):"0");
		resultMap.put("point", result != null ?result.get("point"):"0");
		return resultMap;
	}

	@Override
	public int findIsExist(String tenantid, String pno) {
		return tbCardInfoDao.findIsExist(tenantid, pno);
	}

	@Override
	public TbCardInfo byUserTouse(HashMap<String, Object> paramsJson) {

		return tbCardInfoDao.byUserToUse(paramsJson);
	}

}
