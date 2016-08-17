package com.candao.member.plugin.page;

import org.apache.log4j.Logger;

/**
 * @author mcong
 * @date 2016-01-07
 * @description 分页上下文
 */
public class PageContext<T> extends Page<T>  {

	private static final Logger logger = Logger.getLogger(PageContext.class);

	private static ThreadLocal<PageContext> context = new ThreadLocal<PageContext>();


	public static PageContext getContext()
	{
		PageContext ci = context.get();
		if(ci == null)
		{
			ci = new PageContext();
			context.set(ci);
		}
		return ci;
	}

	public  static void removeContext()
	{
		context.remove();
	}

	protected void initialize() {

		

	}
	


	
	
	

	
}
