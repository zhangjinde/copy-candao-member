package com.candao.member.plugin.page;

import java.util.List;

public class Page<T>  implements java.io.Serializable {


	private static final long serialVersionUID = 1L;

	protected List<T> resultlist;
	protected int pageSize = 15; // 每页默认10条数据
	protected int currentPage = 1; // 当前页
	protected int totalPages = 0; // 总页数
	protected int totalRows = 0; // 总数据数
	protected int pageStartRow = 0; // 每页的起始行数
	protected int pageEndRow = 0; // 每页显示数据的终止行数
	protected boolean pagination=false;   //是否分页
	boolean hasNextPage = false; // 是否有下一页
	boolean hasPreviousPage = false; // 是否有前一页
	protected String pagedView; // 用于页面显示
	
	Object obj;   //参数对象与返回对象
	//List<Object> resultList; // 返回的结果
    //Map<String,Object>  param;      //查询入参	

	public Page(int rows, int pageSize) {
		this.init(rows, pageSize);
	}

	public Page() {

	}

	/**
	 * 初始化分页参数:需要先设置totalRows
	 * 
	 */

	public void init(int rows, int pageSize) {

		this.pageSize = pageSize;

		this.totalRows = rows;

		if ((totalRows % pageSize) == 0) {
			totalPages = totalRows / pageSize;
		} else {
			totalPages = totalRows / pageSize + 1;
		}

	}
	 public void init(int rows, int pageSize,int currentPage ) {

			this.pageSize = pageSize;

			this.totalRows = rows;

			if ((totalRows % pageSize) == 0) {
				totalPages = totalRows / pageSize;
			} else {
				totalPages = totalRows / pageSize + 1;
			}
	        if(currentPage!=0)
				gotoPage(currentPage);	      
	        setPagedView();
		}
	/**
	 * 计算当前页的取值范围：pageStartRow和pageEndRow
	 * 
	 */
	private void calculatePage() {
		if ((currentPage - 1) > 0) {
			hasPreviousPage = true;
		} else {
			hasPreviousPage = false;
		}

		if (currentPage >= totalPages) {
			hasNextPage = false;
		} else {
			hasNextPage = true;
		}

		if (currentPage * pageSize < totalRows) { // 判断是否为最后一页
			pageEndRow = currentPage * pageSize;
			pageStartRow = pageEndRow - pageSize;
		} else {
			pageEndRow = totalRows;
			/*
			*找到访问当前页的开始位置
			* */
//			pageStartRow = pageSize * (totalPages - 1);
			pageStartRow = pageSize * (currentPage -1);
		}
		
	}
	
	/**
	 * 直接跳转到指定页数的页面
	 * 
	 * @param page
	 */
	public void gotoPage(int page) {

		currentPage = page;

		calculatePage();

		// debug1();
	}

	public void debug1() {

		System.out.println("要显示的页面数据已经封装,具体信息如下：");
		String debug = "共有数据数:" + totalRows + "共有页数:" + totalPages + "当前页数为:"
				+ currentPage + "是否有前一页:" + hasPreviousPage + "是否有下一页:"
				+ hasNextPage + "开始行数:" + pageStartRow + "终止行数:" + pageEndRow;
		System.out.println(debug);
	}

	public void setPagedView(String path) {}

	public void setPagedView() {}

	public void setPagedView1() {}

	/**
	 * 前台分页页面导航的显示
	 * 
	 * @return String
	 */
	public void setPagedView1(String path) {}

	public String getPagedView() {
		return pagedView;
	}

	/**
	 * @return
	 */
	public int getCurrentPage() {
		return currentPage;
	}

	/**
	 * @return
	 */
	public boolean isHasNextPage() {
		return hasNextPage;
	}

	/**
	 * @return
	 */
	public boolean isHasPreviousPage() {
		return hasPreviousPage;
	}

	/**
	 * @return
	 */
	public int getPageEndRow() {
		return pageEndRow;
	}

	/**
	 * @return
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * @return
	 */
	public int getPageStartRow() {
		return pageStartRow;
	}

	/**
	 * @return
	 */
	public int getTotalPages() {
		return totalPages;
	}

	/**
	 * @return
	 */
	public int getTotalRows() {
		return totalRows;
	}
	
	/**
	 * @param i
	 */
	public void setTotalPages(int i) {
		totalPages = i;
	}

	/**
	 * @param i
	 */
	public void setCurrentPage(int i) {
		currentPage = i;
	}

	/**
	 * @param b
	 */
	public void setHasNextPage(boolean b) {
		hasNextPage = b;
	}

	/**
	 * @param b
	 */
	public void setHasPreviousPage(boolean b) {
		hasPreviousPage = b;
	}

	/**
	 * @param i
	 */
	public void setPageEndRow(int i) {
		pageEndRow = i;
	}

	/**
	 * @param i
	 */
	public void setPageSize(int i) {
		pageSize = i;
	}

	/**
	 * @param i
	 */
	public void setPageStartRow(int i) {
		pageStartRow = i;
	}

	

	/**
	 * @param i
	 */
	public void setTotalRows(int i) {
		totalRows = i;
	}

	public boolean isPagination() {
		return pagination;
	}

	public void setPagination(boolean pagination) {
		this.pagination = pagination;
	}

	public List<T> getResultlist() {
		return resultlist;
	}

	public void setResultlist(List<T> resultlist) {
		this.resultlist = resultlist;
	}

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}

	

}