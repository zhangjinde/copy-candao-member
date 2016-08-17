package com.candao.member.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.candao.member.dao.impl.Page;
import com.candao.member.model.TbTenantInfo;
import com.candao.member.service.DataDictionaryService;
import com.candao.member.service.TenantService;
import com.candao.member.utils.JacksonJsonMapper;

@Controller
@RequestMapping("/tenant")
public class TenantController extends BaseController{
	
	@Autowired
	private TenantService tenantService ;
	
	@Autowired
	private DataDictionaryService dataDictionaryService ;
	
	@RequestMapping(value = {"/show", "/"})
	public ModelAndView index(HttpServletRequest request) {
	    return new ModelAndView("tenant/show");
	}
	
	@RequestMapping("/overall")
	public ModelAndView overall(HttpServletRequest request) {
	    return new ModelAndView("tenant/overall");
	}
	
	@RequestMapping("/page")
	@ResponseBody
	public ModelAndView page(int page, int rows,@RequestParam Map<String, Object> param) {
		Page<Map<String, Object>> pageMap = tenantService.grid(param, page, rows);
		ModelAndView mav = new ModelAndView();
		mav.addObject("page", pageMap);
		return mav;
	}
	
	@RequestMapping("/save")
	@ResponseBody
	public String save(@RequestBody TbTenantInfo tbTenantInfo) {
		int id = tbTenantInfo.getId();
		Map<String, Object> map = new HashMap<>();
		try {
			if(id == 0){
				map = tenantService.save(tbTenantInfo);
			}else{
				map = tenantService.update(tbTenantInfo);
			}
		} catch (Exception e) {
			map.put("code", "0");
			map.put("msg", "操作失败");
			e.printStackTrace();
		}
		return JacksonJsonMapper.objectToJson(map);
	}
	
	@RequestMapping("/findById/{id}")
	@ResponseBody
	public ModelAndView findById(@PathVariable(value = "id") String id) {
		TbTenantInfo tbTenantInfo = tenantService.findById(id);
		ModelAndView mav = new ModelAndView();
		mav.addObject("tbTenantInfo", tbTenantInfo);
		return mav;
	}
	
	@RequestMapping("/findByBranchId/{branchid}")
	@ResponseBody
	public ModelAndView findByBranchId(@PathVariable(value = "branchid") String branchid) {
		TbTenantInfo tbTenantInfo = tenantService.findByBranchId(branchid);
		ModelAndView mav = new ModelAndView();
		mav.addObject("tbTenantInfo", tbTenantInfo);
		return mav;
	}
	
	@RequestMapping("/delete/{id}")
	@ResponseBody
	public ModelAndView deleteById(@PathVariable(value = "id") String id) {
		boolean b = tenantService.deleteById(id);
		ModelAndView mav = new ModelAndView();
		if(b){
		    mav.addObject("message", "删除成功");
		}else{
			mav.addObject("message", "删除失败");	
		}
		return mav;
	}
	
	@RequestMapping("/getChannelType")
	@ResponseBody
	public String getChannelType(){
		List<Map<String, Object>> list = null ;
		list = dataDictionaryService.getDatasByType("TENANT_CHANNEL");
		return JacksonJsonMapper.objectToJson(list);
	}

	@RequestMapping("/list")
	public ModelAndView list(@RequestParam(value = "current", defaultValue = "1") Integer current){
		ModelAndView mav = new ModelAndView("tenant/list");
		String search = request.getParameter("search");
		Map<String, Object> map = new HashMap<>();
		map.put("search", search);
		Page<Map<String, Object>> pageMap = tenantService.grid(map, current, 10);
 	    mav.addObject("datas", pageMap.getRows());
 	    mav.addObject("current",current);
 	    mav.addObject("total", pageMap.getTotal());
 	    mav.addObject("totalpage", pageMap.getPageCount());
		return mav;
	}
	
}
