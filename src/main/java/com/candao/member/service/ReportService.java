package com.candao.member.service;

import java.util.List;
import java.util.Map;

public interface ReportService {

	/**
	 * 分页获取报表列表数据
	 * @param params
	 * @param current
	 * @param pagesize
	 * @return
	 */
	Map<String,Object> grid(Map<String, Object> params, int current, int pagesize);

	/**
	 * 报表汇总
	 * @return
	 */
	Map<String, Object> reportCollect(Map<String, Object> param);

	/**
	 * 获取报表列表数据
	 * @return
	 */
	List<Map<String, Object>> getReportsList(Map<String, Object> param);
}
