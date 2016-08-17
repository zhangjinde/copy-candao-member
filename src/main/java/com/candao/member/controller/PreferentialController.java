package com.candao.member.controller;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.candao.member.dao.impl.Page;
import com.candao.member.model.TPreferential;
import com.candao.member.model.TPreferentialInfo;
import com.candao.member.model.TbTenantInfo;
import com.candao.member.service.PreferentialService;
import com.candao.member.service.TenantService;
import com.candao.member.utils.Constant;
import com.candao.member.utils.JSONUtils;
import com.candao.member.utils.JacksonJsonMapper;
import com.candao.member.utils.SessionUtils;

@Controller
@RequestMapping("/preferential")
public class PreferentialController extends BaseController {

    @Autowired
    private PreferentialService preferentialService;

    @Autowired
    private TenantService tenantService;

    @RequestMapping("/index")
    @ResponseBody
    public ModelAndView index() {
        ModelAndView mv = new ModelAndView("preferential/list");
        return mv;
    }

    /**
     * 优惠列表
     * 
     * @return
     */
    @RequestMapping("/list")
    @ResponseBody
    public Object list() {
        ModelAndView mav = new ModelAndView("preferential/list");
        String currentpage = request.getParameter("page");
        if ("".equals(currentpage) || currentpage == null) {
            currentpage = "1";
        }
        Integer current = Integer.parseInt(currentpage);
        Integer pageSize = Integer.valueOf(request.getParameter("rows"));
        String tenantId = SessionUtils.get(Constant.CURRENT_TENANT_ID).toString();
        Map<String, Object> mapquery = new HashMap<String, Object>();
        mapquery.put("pageSize", String.valueOf(pageSize));
        mapquery.put("current", String.valueOf(current));
        mapquery.put("tenantId", String.valueOf(tenantId));
        Page<TPreferential> page = preferentialService.getPreferential(mapquery);
        mav.addObject("datas", page.getRows());
        mav.addObject("current", current);
        mav.addObject("total", page.getTotal());
        mav.addObject("totalpage", page.getPageCount());

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("resultList", page.getRows());
        resultMap.put("page", current);
        resultMap.put("records", page.getTotal());//总记录数
        resultMap.put("total", page.getPageCount());//总页数
        return JSONUtils.mapToJson(resultMap);
    }

    /**
     * 优惠列表
     * 
     * @return
     */
    @RequestMapping("/posPreferentialList")
    @ResponseBody
    public String posPreferentialList(HttpServletRequest request, HttpServletResponse response,
            @RequestBody String jsonString) {
        HashMap<String, Object> paramsJson = JacksonJsonMapper.jsonToObject(jsonString, HashMap.class);
        String currentpage = paramsJson.get("current").toString();
        String pageSizeStr = paramsJson.get("pageSize").toString();
        String branch_id = paramsJson.get("branch_id").toString();
        Integer current = currentpage == null || currentpage.isEmpty() ? 0 : Integer.parseInt(currentpage);
        if ("".equals(currentpage) || currentpage == null) {
            currentpage = "1";
        }
        Integer pageSize = pageSizeStr == null || pageSizeStr.isEmpty() ? 0 : 10;
        String tenantId = "";
        if (branch_id != null && !branch_id.isEmpty()) {
            TbTenantInfo tbTenantInfo = tenantService.findByBranchId(branch_id);
            tenantId = tbTenantInfo.getTenantId();
        } else {
            tenantId = SessionUtils.get(Constant.CURRENT_TENANT_ID).toString();
        }

        Map<String, Object> mapquery = new HashMap<String, Object>();
        mapquery.put("pageSize", String.valueOf(pageSize));
        mapquery.put("status", "0");
        mapquery.put("current", String.valueOf(current));
        mapquery.put("tenantId", String.valueOf(tenantId));
        mapquery.put("branchId", branch_id);
        Page<TPreferential> page = preferentialService.getPreferential(mapquery);

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("datas", page.getRows());
        map.put("current", current == 0 ? 1 : current);
        map.put("total", page.getTotal());
        map.put("totalpage", pageSize == 0 ? 1 : page.getPageCount());
        return JacksonJsonMapper.objectToJson(map);
    }

    /**
     * 新增或修改优惠
     * 
     * @return
     */
    @RequestMapping("/modify")
    @ResponseBody
    public String modify(@RequestBody TPreferential tPreferential) {
        String tenantId = SessionUtils.get(Constant.CURRENT_TENANT_ID).toString();
        tPreferential.setTenantId(tenantId);
        Integer id = tPreferential.getId();
        // 获取门店信息
        // 优先判断是否有设置微信状态
        if (tPreferential.getWeixinStatus() == 1) {
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("teantid", tenantId);
            List<TPreferential> prbeanList = preferentialService.getPreferentialInfoToWeixin(paramMap);
            if (prbeanList != null && !prbeanList.isEmpty()) {
                for (int i = 0; i < prbeanList.size(); i++) {
                    TPreferential prbean = prbeanList.get(i);
                    Integer preID = prbean.getId();
                    if (preID != tPreferential.getId()) {
                        List<TPreferentialInfo> listTpreferInfo = preferentialService.getTPreferentialInfoByPid(preID);
                        // 获取已经新增的优惠信息
                        Set<String> barachIDset = new HashSet<String>();
                        for (TPreferentialInfo info : listTpreferInfo) {
                            String baranchtempID = info.getBranchId();
                            barachIDset.add(baranchtempID);
                        }
                        List<TPreferentialInfo> inputPerferInfo = tPreferential.getPreferentialInfos();
                        for (TPreferentialInfo tempTpreInfo : inputPerferInfo) {
                            if (barachIDset.contains(tempTpreInfo.getBranchId())) {
                                String mes = String.format("此门店已经有包含微信优惠策略！", prbeanList.get(0).getName());
                                return getFailure(mes);
                            }
                        }
                    }
                }
            }
        }

        Map<String, Object> map = new HashMap<>();
        try {
            if (id == null || "".equals(id)) {
                map = preferentialService.addPreferential(tPreferential);
            } else {
                map = preferentialService.updatePreferential(tPreferential);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return getFailure("");
        }
        if ("0".equals(map.get("code"))) {
            return getSuccess();
        } else {
            return getFailure((String) map.get("msg"));
        }
    }

    /**
     * 跳转详情页
     * 
     * @return
     */
    @RequestMapping("/detail")
    @ResponseBody
    public ModelAndView detail() {
        ModelAndView mav = new ModelAndView("preferential/detail");
        String id = request.getParameter("id");
        Map<String, Object> map = preferentialService.getPreferentialDetail(id);
        mav.addAllObjects(map);
        return mav;
    }

    /**
     * 修改优惠状态
     * 
     * @return
     */
    @RequestMapping("/modifyStatus")
    @ResponseBody
    public String modifyStatus(Integer id, Integer status, String updateStatus) {
        if (id == null || status == null) {
            return getFailure("操作失败");
        }
        preferentialService.modifyStatusById(id, status, updateStatus);
        return getSuccess();
    }

    /***
     * 是否已经存在微信已经生效的优惠策略
     * 
     * @return
     */
    @RequestMapping("/weinxinOpenStauts")
    @ResponseBody
    public String weixinOpenStatus(HttpServletRequest request, HttpServletResponse response,
            @RequestBody String jsonString) {
        HashMap<String, Object> paramsJson = JacksonJsonMapper.jsonToObject(jsonString, HashMap.class);
        String tenantId = SessionUtils.get(Constant.CURRENT_TENANT_ID).toString();
        String branchId = paramsJson.get("branchId").toString();
        String id = paramsJson.get("id").toString();
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("teantid", tenantId);
        List<TPreferential> prbeanList = preferentialService.getPreferentialInfoToWeixin(paramMap);
        if (prbeanList != null && !prbeanList.isEmpty()) {
            TPreferential prbean = prbeanList.get(0);
            Integer preID = prbean.getId();
            if (id == null || !preID.equals(id)) {
                List<TPreferentialInfo> listTpreferInfo = preferentialService.getTPreferentialInfoByPid(preID);
                // 获取已经新增的优惠信息
                Set<String> barachIDset = new HashSet<String>();
                for (TPreferentialInfo info : listTpreferInfo) {
                    String baranchtempID = info.getBranchId();
                    barachIDset.add(baranchtempID);
                }
                if (barachIDset.contains(branchId)) {
                    String mes = String.format("此门店已经有包含微信优惠策略！", prbeanList.get(0).getName());
                    return getFailure(mes);
                }
            }
        }

        return getSuccess();

    }

    /**
     * 获取门店信息
     * 
     * @return
     */
    @RequestMapping("/getBranch")
    @ResponseBody
    public String getBranch(Integer id) {
        String tenantId = SessionUtils.get(Constant.CURRENT_TENANT_ID).toString();
        List<Map<String, Object>> infomap = preferentialService.getBranchByTenantId(tenantId, id);
        return getSuccess(infomap);
    }
}
