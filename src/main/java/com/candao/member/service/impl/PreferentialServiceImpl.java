package com.candao.member.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.candao.member.dao.PreferentialDao;
import com.candao.member.dao.TbTenantDao;
import com.candao.member.dao.impl.Page;
import com.candao.member.dao.impl.PageContainer;
import com.candao.member.model.TPreferential;
import com.candao.member.model.TPreferentialInfo;
import com.candao.member.model.TbTenantInfo;
import com.candao.member.service.PreferentialService;

@Service
public class PreferentialServiceImpl implements PreferentialService {

    @Autowired
    private PreferentialDao preferentialDao;

    @Autowired
    private TbTenantDao tbTenantDao;

    /**
     * 列表
     */
    @Override
    public Page<TPreferential> getPreferential(Map<String, Object> parmMap) {
        int tenantId = Integer.valueOf(parmMap.get("tenantId").toString());
        int pageSize = Integer.valueOf(parmMap.get("pageSize").toString());
        int current = Integer.valueOf(parmMap.get("current").toString());
        Object branchId = parmMap.get("branchId");
        Map<String, Object> map = new HashMap<>();
        map.put("tenantId", tenantId);
        int skip = (current - 1) * pageSize;
        skip = skip < 0 ? 0 : skip;
        map.put("current", skip);
        map.put("pageSize", pageSize);
        if (parmMap.containsKey("status")) {
            map.put("status", parmMap.get("status"));
        }
        List<TPreferential> preferentials = preferentialDao.getPreferential(map);
        if (branchId == null) {
            for (TPreferential preferential : preferentials) {
                int id = preferential.getId();
                List<TPreferentialInfo> infos = new ArrayList<>();
                infos = preferentialDao.getInfoByPId(id);
                int count = infos.size();
                preferential.setPreferentialInfos(infos);
                preferential.setCountInfo(count);
            }
            Integer count = preferentialDao.getCount(map);
            Page<TPreferential> prPage = new PageContainer<>(count, pageSize, current, preferentials, map);
            return prPage;
        }else{
            List<TPreferential> preferentialsResult = new ArrayList<TPreferential>();
            for (int i = 0; i < preferentials.size(); i++) {
                TPreferential preferential = preferentials.get(i);
                int id = preferential.getId();
                List<TPreferentialInfo> infos = new ArrayList<>();
                infos = preferentialDao.getInfoByPId(id, branchId.toString());
                if (infos.size() > 0) {
                    int count = infos.size();
                    preferential.setPreferentialInfos(infos);
                    preferential.setCountInfo(count);
                    preferentialsResult.add(preferential);
                }
            }
            Integer count = preferentialDao.getCount(map);
            Page<TPreferential> prPage = new PageContainer<>(count, pageSize, current, preferentialsResult, map);
            return prPage;
        }
    }

    /**
     * 新增优惠
     */
    @Override
    public Map<String, Object> addPreferential(TPreferential tPreferential) {
        Map<String, Object> map = new HashMap<>();
        List<TPreferentialInfo> infos = tPreferential.getPreferentialInfos();
        preferentialDao.addPreferential(tPreferential);
        Integer id = tPreferential.getId();
        for (TPreferentialInfo info : infos) {
            info.setPreferentialId(id);
            preferentialDao.addPreferentialInfo(info);
        }
        map.put("code", "0");

        return map;
    }

    /**
     * 修改优惠
     */
    @Override
    public Map<String, Object> updatePreferential(TPreferential tPreferential) {
        Map<String, Object> map = new HashMap<>();
        List<TPreferentialInfo> infos = tPreferential.getPreferentialInfos();
        preferentialDao.updatePreferential(tPreferential);
        Integer id = tPreferential.getId();
        preferentialDao.deletePreferentialInfo(id);
        for (TPreferentialInfo info : infos) {
            info.setPreferentialId(id);
            preferentialDao.addPreferentialInfo(info);
        }
        map.put("code", "0");
        return map;
    }

    /**
     * 获取门店名称(返回新增或修改的这个优惠，起始时间内还有别的门店在使用优惠)
     * @return
     */
    private List<String> getBranchNames(TPreferential tPreferential) {
        List<TPreferentialInfo> infos = tPreferential.getPreferentialInfos();
        Map<String, Object> param = new HashMap<>();
        param.put("tenantId", tPreferential.getTenantId());
        param.put("id", tPreferential.getId()); //ID不为空的情况下为修改，查询的时候需要排除
        List<TPreferential> preferentials = preferentialDao.findPreferentialByParam(param);

        List<String> branchIds = new ArrayList<>();
        List<String> bNames = new ArrayList<>();
        if (preferentials != null) {
            for (TPreferential preferential : preferentials) {
                Integer id = preferential.getId();
                List<TPreferentialInfo> pinfos = preferentialDao.getTPreferentialInfoByPid(id);
                List<String> bid = new ArrayList<>();
                for (TPreferentialInfo tinfo : pinfos) {
                    bid.add(tinfo.getBranchId());
                }
                branchIds.addAll(bid);
            }
        }
        for (TPreferentialInfo info : infos) {
            String bId = info.getBranchId();
            boolean flag = branchIds.contains(bId);
            if (flag) {
                bNames.add(info.getBranchName());
            }
        }
        return bNames;
    }

    /**
     * 获取详情
     */
    @Override
    public Map<String, Object> getPreferentialDetail(String id) {
        TPreferential preferential = new TPreferential();
        if (id != null) {
            Integer pid = Integer.parseInt(id);
            preferential = preferentialDao.getTPreferentialById(pid);
            List<TPreferentialInfo> infos = new ArrayList<>();
            infos = preferentialDao.getInfoByPId(pid);
            preferential.setPreferentialInfos(infos);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("detail", preferential);
        return map;
    }

    /**
     * 修改优惠状态
     */
    @Override
    public void modifyStatusById(Integer id, Integer status, String updateStatus) {
        preferentialDao.modifyStatusById(id, status, updateStatus);
    }

    /**
     * 获取门店（如果没有传入pid查所有，如果传入pid查对应门店）
     */
    @Override
    public List<Map<String, Object>> getBranchByTenantId(String tenantId, Integer pid) {
        Map<String, Object> params = new HashMap<>();
        params.put("tenantId", tenantId);
        params.put("preferentialId", pid);
        List<Map<String, Object>> infomap = new ArrayList<>();
        if (pid == null) {
            List<TbTenantInfo> tinfos = tbTenantDao.find(params);
            for (TbTenantInfo info : tinfos) {
                Map<String, Object> map = new HashMap<>();
                String branchId = info.getBranchId();
                String branchName = info.getTenantName();
                map.put("branchid", branchId);
                map.put("branchname", branchName);
                infomap.add(map);
            }
        } else {
            List<TPreferentialInfo> infos = preferentialDao.getInfoByPId(pid);
            for (TPreferentialInfo info : infos) {
                Map<String, Object> map = new HashMap<>();
                String branchId = info.getBranchId();
                String branchName = info.getBranchName();
                map.put("branchid", branchId);
                map.put("branchname", branchName);
                infomap.add(map);
            }
        }
        return infomap;
    }

    @Override
    public List<TPreferential> getPreferentialInfoToWeixin(Map<String, Object> paramMap) {

        return preferentialDao.getPreferentialInfoToWeixin(paramMap);
    }

    @Override
    public List<TPreferentialInfo> getTPreferentialInfoByPid(Integer pid) {

        return preferentialDao.getTPreferentialInfoByPid(pid);
    }

}
