package com.candao.member.service.impl;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.candao.member.dao.TbCardInfoDao;
import com.candao.member.service.CardService;

@Service
public class CardServiceImpl implements CardService {
	
//	@Autowired
	private TbCardInfoDao tCardInfoDAO;

	@Override
	public Map<String,Object> getCardInfoByCardNo(String CardNo) {
		// TODO Auto-generated method stub
		return tCardInfoDAO.getByCardNo(CardNo);
	}

    /* (non-Javadoc)    
     * @see com.candao.member.service.CardService#getCardInfoByParam(java.util.Map)    
     */
    @Override
    public int findIsExist(String tenantid, String pno) {
        return tCardInfoDAO.findIsExist(tenantid, pno);
    }

}
