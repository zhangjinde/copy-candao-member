package com.candao.member.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.candao.member.dao.TCardAccountDAO;
import com.candao.member.dao.TDealDetailDAO;
import com.candao.member.dao.TDealMasterDAO;
import com.candao.member.dao.TMemberCardDao;
import com.candao.member.dao.TbCardInfoDao;
import com.candao.member.dao.TbMemberManagerDao;
import com.candao.member.dao.TbTenantCardDao;
import com.candao.member.dao.impl.Page;
import com.candao.member.model.TCardAccount;
import com.candao.member.model.TDealDetail;
import com.candao.member.model.TDealMaster;
import com.candao.member.model.TMemberCard;
import com.candao.member.model.TbCardInfo;
import com.candao.member.model.TbMemberAndTenantCard;
import com.candao.member.model.TbMemberManager;
import com.candao.member.model.TbTenantCard;
import com.candao.member.service.MemberManagerService;
import com.candao.member.utils.TraceCodeUtil;
import com.candao.member.vo.MemberVo;
import com.candao.member.vo.VipInfoExcelVo;

@Service
public class MemberManagerServiceImpl implements MemberManagerService {

    @Autowired
    private TbMemberManagerDao tbMemberManagerDao;

    @Autowired
    private TbCardInfoDao tbCardInfoDao;

    @Autowired
    private TCardAccountDAO tCardAccountDAO;

    @Autowired
    private TbTenantCardDao tbTenantCardDao;

    @Autowired
    private TDealDetailDAO tDealDetailDAO;

    @Autowired
    private TMemberCardDao tMemberCardDao;
    
	@Autowired
	private TDealMasterDAO tDealMasterDAO;
    
    private static Logger log = LoggerFactory.getLogger(MemberManagerServiceImpl.class);

    @Override
    public Page<Map<String, Object>> grid(Map<String, Object> params, int current, int pagesize) {
        return tbMemberManagerDao.page(params, current, pagesize);
    }

    public List<Map<String, Object>> find(Map<String, Object> params) {
        return tbMemberManagerDao.find(params);
    }

    /*public List<Map<String,Object>> findAll(String id) {
    	// TODO Auto-generated method stub
    	Map<String, Object> params=new HashMap<String, Object>();
    	params.put("id",  id);
    	return tbasicDataDao.find( params);
    }*/
    @Override
    public boolean save(TbMemberManager tbMemberManager) {
        return tbMemberManagerDao.insert(tbMemberManager) > 0;
    }

    @Override
    public TbMemberManager findByParams(Map<String, Object> params) {
        return tbMemberManagerDao.get(params);
    }

    @Override
    public TbMemberManager findById2(String id) {
        return tbMemberManagerDao.findOne(id);
    }

    @Override
    public boolean update(TbMemberManager tbMemberManager) {
        return tbMemberManagerDao.update(tbMemberManager) > 0;
    }

    @Override
    public boolean deleteByParams(Map<String, Object> params) {
        List<Map<String, Object>> mapList = tbCardInfoDao.findListByParams(params);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("listMap", mapList);
        int a = tbCardInfoDao.deleteList(map);
        return a > 0;
    }

    @Override
    public Map<String, Object> deletelistByParams(Map<String, Object> params) {
        double value = 0;
        double point = 0;
        Map<String, Object> resultMap = new HashMap<String, Object>();
        List<Map<String, Object>> mapList = tbCardInfoDao.findListByParams(params);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("listMap", mapList);
        if (mapList.size() > 0) {
            TCardAccount tCardAccount = null;
            for (Map<String, Object> cardnoMap : mapList) {
                tCardAccount = tCardAccountDAO.getByCardNo(String.valueOf(cardnoMap.get("cardno")));
                value += tCardAccount.getValue();
                point += tCardAccount.getPoint();
            }
            resultMap = new HashMap<String, Object>();
            resultMap.put("StoreCardBalance", value);
            resultMap.put("IntegralOverall", point);

            /**注销会员，往交易明细表添加一条记录，交易类型为注销退款，同时将t_card_account表中对用的卡号的value值改为0**/
            for (Map<String, Object> cardnoMap : mapList) {
                String cardno = String.valueOf(cardnoMap.get("cardno"));

                //				添加交易明细（因为是注销，没涉及到其他交易，所以没有向交易主表添加数据）
                TDealDetail tDealDetailCash = new TDealDetail();
                tDealDetailCash.setCard_no(cardno);
                tDealDetailCash.setDeal_no("");
                tDealDetailCash.setDeal_addr("");
                tDealDetailCash.setDeal_type("12");//会员注销退款
                tDealDetailCash.setAmount(-1 * value);
                tDealDetailCash.setSlip_no("");
                tDealDetailCash.setDeal_user("Test");
                tDealDetailDAO.add(tDealDetailCash);

                //				更新会员卡账户
                TCardAccount cardAccount = new TCardAccount();
                cardAccount.setCard_no(cardno);
                cardAccount.setValue(0);
                cardAccount.setPoint(0);
                tCardAccountDAO.update(cardAccount);
            }
        }
        int a = tbCardInfoDao.deleteList(map);
        if (a == 0) {
            resultMap.clear();
            return resultMap;
        }
        return resultMap;
    }

    public int findMaxID() {
        return tbMemberManagerDao.findMaxID();
    }

    @Override
    public List<Map<String, Object>> findListByParams(Map<String, Object> jsonMap) {
        return tbMemberManagerDao.findListByParams(jsonMap);
    }

    @Override
    public Map<String, Object> save(TbMemberManager tbMemberManager, TbCardInfo tbCardInfo, TbTenantCard tbTenantCard) {
        HashMap<String, Object> result = new HashMap<>();

        //去掉校验统一入库
        Map<String,Object> param=new HashMap<String,Object>();
        param.put("mobile",tbMemberManager.getMobile());
        param.put("tenant_id", tbMemberManager.getTenant_id());
        TbMemberManager tmm=tbMemberManagerDao.get(param);
        if(tmm==null){
        tbMemberManagerDao.insert(tbMemberManager);
        }else{
            tbMemberManager.setId(tmm.getId());
        }
        tbCardInfoDao.insert(tbCardInfo);

        TbMemberAndTenantCard tbMemberAndTenantCard = new TbMemberAndTenantCard();
        tbMemberAndTenantCard.setMember_id(tbMemberManager.getId());
        tbMemberAndTenantCard.setCardno(tbCardInfo.getCardno());
        tbMemberManagerDao.insertMemberCard(tbMemberAndTenantCard);

        TCardAccount tCardAccount = new TCardAccount();
        tCardAccount.setCard_no(tbCardInfo.getCardno());
        tCardAccount.setValue(0);
        tCardAccount.setActual_value(0);
        tCardAccount.setPresent_value(0);
        tCardAccount.setPoint(0);
        tCardAccount.setPresent_point(0);
        tCardAccountDAO.insert(tCardAccount);

        //如果为空表示外部卡信息来源
        if (null != tbTenantCard) {
            String currentno = String.valueOf(tbTenantCard.getCurrent_no());
            int updateno = (Integer.valueOf(currentno) + 1);
            tbTenantCard.setCurrent_no(updateno);
            tbTenantCardDao.update(tbTenantCard);
        }
        return result;

    }

    @Override
    public void save(TbMemberManager tbMemberManager, TbCardInfo tbCardInfo, TbTenantCard tbTenantCard,
            VipInfoExcelVo excelVo) {
        HashMap<String, Object> result = null;

        tbMemberManagerDao.insert(tbMemberManager);
        tbCardInfoDao.insert(tbCardInfo);

        TbMemberAndTenantCard tbMemberAndTenantCard = new TbMemberAndTenantCard();
        tbMemberAndTenantCard.setMember_id(tbMemberManager.getId());
        tbMemberAndTenantCard.setCardno(tbCardInfo.getCardno());
        tbMemberManagerDao.insertMemberCard(tbMemberAndTenantCard);

        TCardAccount tCardAccount = new TCardAccount();
        tCardAccount.setCard_no(tbCardInfo.getCardno());
        tCardAccount.setValue(Double.valueOf(excelVo.getValue()));
        tCardAccount.setActual_value(Integer.parseInt(excelVo.getCmoney()));
        tCardAccount.setPresent_value(Double.valueOf(excelVo.getZsmoney()));
        tCardAccount.setPoint(Double.valueOf(excelVo.getPoint()));
        tCardAccount.setPresent_point(0);
        tCardAccountDAO.insert(tCardAccount);

        String currentno = String.valueOf(tbTenantCard.getCurrent_no());
        int updateno = (Integer.valueOf(currentno) + 1);
        tbTenantCard.setCurrent_no(updateno);
        tbTenantCardDao.update(tbTenantCard);
    }

    public void save(TbMemberManager tbMemberManager, TbCardInfo tbCardInfo, TbTenantCard tbTenantCard,
            MemberVo excelVo, String TENANTID, String cardNo) throws Exception {

        TCardAccount tCardAccount = new TCardAccount();
        tCardAccount.setCard_no(tbCardInfo.getCardno());
        tCardAccount.setValue(excelVo.getBalanceAmount() != null && !excelVo.getBalanceAmount().equals("")
                ? Double.valueOf(excelVo.getBalanceAmount()) : 0);
        tCardAccount.setActual_value(excelVo.getChargeAmount() != null && !excelVo.getChargeAmount().equals("")
                ? Double.parseDouble(excelVo.getChargeAmount()) : 0);
        tCardAccount.setPresent_value(excelVo.getPresentAmount() != null && !excelVo.getPresentAmount().equals("")
                ? Double.valueOf(excelVo.getPresentAmount()) : 0);
        tCardAccount.setPoint(excelVo.getCurrentPoint() != null && !excelVo.getCurrentPoint().equals("")
                ? Double.valueOf(excelVo.getCurrentPoint()) : 0);
        tCardAccount.setPresent_point(excelVo.getTotalPoint() != null && !excelVo.getTotalPoint().equals("")
                ? Double.valueOf(excelVo.getTotalPoint()) : 0);

        Integer cardInfoId = tbCardInfoDao.findId(TENANTID, cardNo);
        if (cardInfoId != null) {
            HashMap<String, Object> cardInfo = new HashMap<>();
            cardInfo.put("cardno", cardNo);
            Integer memberCardId = tbMemberManagerDao.findMemberCardId(cardInfo);
            tbCardInfoDao.update(tbCardInfo);
            tbMemberManager.setId(memberCardId);
            tbMemberManagerDao.updateImport(tbMemberManager);
            tCardAccountDAO.update(tCardAccount);
            return;
        }
        //一个电话号码对应多个卡号问题，只存储一条客户基本信息 change by 20160709
        Integer id = tbMemberManagerDao.findId(tbMemberManager);
        tbMemberManager.setId(id);
        if(id == null){
        	tbMemberManagerDao.insert(tbMemberManager);
        }

        tbCardInfoDao.insert(tbCardInfo);

//        tbMemberManagerDao.insert(tbMemberManager);
        
        TbMemberAndTenantCard tbMemberAndTenantCard = new TbMemberAndTenantCard();
        tbMemberAndTenantCard.setMember_id(tbMemberManager.getId());
        tbMemberAndTenantCard.setCardno(tbCardInfo.getCardno());
        tbMemberManagerDao.insertMemberCard(tbMemberAndTenantCard);

        tCardAccountDAO.insert(tCardAccount);
        
		/*String dealNo = TraceCodeUtil.generateTraceCode();

		//添加充值交易
		TDealMaster tDealMaster = new TDealMaster();
		tDealMaster.setBranch_id(excelVo.getStoreNo());
		tDealMaster.setCard_no(cardNo);
		tDealMaster.setDeal_no(dealNo);
		tDealMaster.setValue(tCardAccount.getValue());
		tDealMaster.setActual_value(tCardAccount.getActual_value());
		tDealMaster.setPresent_value(tCardAccount.getPresent_value());
		tDealMaster.setConsume_value(0);
		tDealMaster.setSlip_no(excelVo.getStoreNo());
		tDealMaster.setPoint(0);
		tDealMaster.setPresent_point(0);
		tDealMaster.setConsume_point(0);
		tDealMaster.setDeal_type("0");
		tDealMaster.setCharge_type("0");
		tDealMasterDAO.add(tDealMaster);
		
		//添加充值-储值交易
		TDealDetail tDealDetailValue;
		tDealDetailValue = new TDealDetail();
		tDealDetailValue.setCard_no(cardNo);
		tDealDetailValue.setDeal_no(dealNo);
		tDealDetailValue.setDeal_addr("");
//		if(Integer.parseInt((String) valueMap.get("ChargeType")) == 1){
//			tDealDetailValue.setDeal_type("11"); // 银行卡充值
//		}else if(Integer.parseInt((String) valueMap.get("ChargeType")) == 0){
			tDealDetailValue.setDeal_type("5");  // 现金充值
//		}else if(Integer.parseInt((String) valueMap.get("ChargeType")) == 17){
//			tDealDetailValue.setDeal_type("17"); // 储值赠送
//		}
		tDealDetailValue.setAmount(tCardAccount.getActual_value());
		tDealDetailValue.setSlip_no(excelVo.getStoreNo());
		tDealDetailValue.setDeal_user("Test");
		tDealDetailDAO.add(tDealDetailValue);*/
        
        String currentno = String.valueOf(tbTenantCard.getCurrent_no());
        int updateno = (Integer.valueOf(currentno) + 1);
        tbTenantCard.setCurrent_no(updateno);
        tbTenantCardDao.update(tbTenantCard);
    }

    /* (non-Javadoc)    
     * @see com.candao.member.service.MemberManagerService#getMemberCards(java.lang.String)    
     */
    @Override
    public List<TMemberCard> getMemberCards(TbMemberManager tbMemberManager) {
        String memberId = tbMemberManager.getId().toString();
        List<TMemberCard> cards = tMemberCardDao.selectMemberCardInMemberIds(memberId);
        return cards;
    }

    /* (non-Javadoc)    
     * @see com.candao.member.service.MemberManagerService#insert(com.candao.member.model.TbMemberManager, com.candao.member.model.TbCardInfo, com.candao.member.model.TbTenantCard)    
     */
    @Override
    public Map<String, Object> insertCard(TbMemberManager tbMemberManager, TbCardInfo tbCardInfo) {
        HashMap<String, Object> result = new HashMap<>();
        try {
            tbCardInfoDao.insert(tbCardInfo);
            TbMemberAndTenantCard tbMemberAndTenantCard = new TbMemberAndTenantCard();
            tbMemberAndTenantCard.setMember_id(tbMemberManager.getId());
            tbMemberAndTenantCard.setCardno(tbCardInfo.getCardno());
            tbMemberManagerDao.insertMemberCard(tbMemberAndTenantCard);
            TCardAccount tCardAccount = new TCardAccount();
            tCardAccount.setCard_no(tbCardInfo.getCardno());
            tCardAccount.setValue(0);
            tCardAccount.setActual_value(0);
            tCardAccount.setPresent_value(0);
            tCardAccount.setPoint(0);
            tCardAccount.setPresent_point(0);
            tCardAccountDAO.insert(tCardAccount);
            result.put("msg", "success");
        } catch (Exception e) {
            log.error("insert card error",e);
            result.put("msg", "fail");
        }
        return result;
    }

	@Override
	public List<Map<String, Object>> findNocardByMobile(Map<String, Object> params) {
		return tbMemberManagerDao.findNocardByMobile(params);
	}

}
