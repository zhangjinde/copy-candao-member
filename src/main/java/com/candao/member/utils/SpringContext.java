package com.candao.member.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.ChildBeanDefinition;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * 以静态变量保存Spring ApplicationContext, 可在任何代码任何地方任何时候取出ApplicaitonContext.
 * 
 * 
 */
public class SpringContext implements ApplicationContextAware, DisposableBean {

	private static ApplicationContext applicationContext = null;

	private static Logger logger = LoggerFactory.getLogger(SpringContext.class);

	/**
	 * 取得存储在静态变量中的ApplicationContext.
	 */
	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	/**
	 * 从静态变量applicationContext中取得Bean, 自动转型为所赋值对象的类型.
	 */
	public static Object getBean(String name) {
		return applicationContext.getBean(name);
	}

	/**
	 * 从静态变量applicationContext中取得Bean, 自动转型为所赋值对象的类型.
	 */
	public static <T> Object getBean(Class<T> requiredType) {
		return applicationContext.getBean(requiredType);
	}

	/**
	 * 清除SpringContextHolder中的ApplicationContext为Null.
	 */
	public static void clearHolder() {
		logger.debug("清除SpringContextHolder中的ApplicationContext:" + applicationContext);
		applicationContext = null;
	}

	/**
	 * 实现ApplicationContextAware接口, 注入Context到静态变量中.
	 */
	public void setApplicationContext(ApplicationContext applicationContext) {
		logger.debug("注入ApplicationContext到SpringContextHolder:" + applicationContext);
		if (SpringContext.applicationContext != null) {
			logger.warn("SpringContextHolder中的ApplicationContext被覆盖, 原有ApplicationContext为:" + SpringContext.applicationContext);
		}
		SpringContext.applicationContext = applicationContext; // NOSONAR
	}

	/**
	 * 实现DisposableBean接口, 在Context关闭时清理静态变量.
	 */
	public void destroy() throws Exception {
		SpringContext.clearHolder();
	}
	
	public static void registerBean(String beanName,String parentName){
		
		
		 DefaultListableBeanFactory  fcy = (DefaultListableBeanFactory)applicationContext.getAutowireCapableBeanFactory();
		 BeanDefinition beanDefinition  = new ChildBeanDefinition(parentName);
		 fcy.removeBeanDefinition(beanName); 
		 
		 fcy.registerBeanDefinition(beanName, beanDefinition);
		 
		 
	}
 
	 
	    /**
	     * @desc 向spring容器注册bean
	     * @param beanName
	     * @param beanDefinition
	     */
	 public static  void registerBean(String beanName, BeanDefinition beanDefinition) {
	        ConfigurableApplicationContext configurableApplicationContext = (ConfigurableApplicationContext) applicationContext;
	        BeanDefinitionRegistry beanDefinitonRegistry = (BeanDefinitionRegistry) configurableApplicationContext
	                .getBeanFactory();
	        beanDefinitonRegistry.registerBeanDefinition(beanName, beanDefinition);
	    }
	 
 
}
