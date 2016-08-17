package com.candao.member.dao.impl;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.Configuration;
import org.mybatis.spring.SqlSessionTemplate;

import com.candao.member.dao.DaoSupport;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.github.miemiedev.mybatis.paginator.domain.Paginator;

public class SqlSessionDaoSupport implements DaoSupport {
	private SqlSessionTemplate sqlSession;
	
	 

	public SqlSessionDaoSupport(SqlSessionTemplate sqlSession) {   
		this.sqlSession = sqlSession;
	}
 
   
	@Override
	public int insertOnce(String statement, Object parameter){
 
		return sqlSession.insert(statement, parameter);
	}
	
	@Override
	public int insert(String statement, Object parameter) {
		
//		SqlSession  sqlSessionCus = getSqlSesion();
		return sqlSession.insert(statement, parameter);
	}

	@Override
	public int update(String statement, Object parameter) {
//		SqlSession  sqlSessionCus = getSqlSesion();
		return sqlSession.update(statement, parameter);
	}

	@Override
	public <K, V, T> T get(String statement, Map<K, V> parameter) {
		
//		SqlSession  sqlSessionCus = getSqlSesion();
		return sqlSession.selectOne(statement, parameter);
	}

	@Override
	public <K, V> Map<K, V> findOne(String statement, Map<K, V> parameter) {
//		SqlSession  sqlSessionCus = getSqlSesion();
		return sqlSession.selectOne(statement, parameter);
	}

	@Override
	public <K, V> int delete(String statement, Map<K, V> parameter) {
		
//		SqlSession  sqlSessionCus = getSqlSesion();
		return sqlSession.delete(statement, parameter);
	}

	@Override
	public <E, K, V> List<E> find(String statement, Map<K, V> parameter) {
		
//		SqlSession  sqlSessionCus = getSqlSesion();
		return sqlSession.selectList(statement, parameter);
	}
	
	
	@Override
	public <E, K, V> List<E> findAndFooter(String statement, Map<K, V> parameter) {
		
//		SqlSession  sqlSessionCus = getSqlSesion();
		
		List<E> find =  sqlSession.selectList(statement, parameter);
		PageList<E> footer = pageFooter(statement+"Footer",  parameter,  1,  10);
		find.addAll(footer);
		return find;
	}
	
	@Override
	public <E> List<E> find(String statement) {
		return sqlSession.selectList(statement);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <E, K, V> Page<E> page(String pageStatement, Map<K, V> parameter, int current, int pagesize) {
		
//		SqlSession  sqlSessionCus = getSqlSesion();
		PageBounds pageBounds =null;
		PageList<E> result=null;
		if(current<=0||pagesize<=0){
			List<E> noPageList =  sqlSession.selectList(pageStatement, parameter);
			
			Paginator paginator = new Paginator(1, noPageList.size(), noPageList.size());
			result =new PageList<>(paginator);
		
			result.addAll(noPageList);
		}else{
			 pageBounds = new PageBounds(current, pagesize);
			 result = (PageList<E>) sqlSession.selectList(pageStatement, parameter, pageBounds);
		}
		Paginator paginator = result.getPaginator();
		return new PageContainer<E, K, V>(paginator.getTotalCount(), paginator.getLimit(), paginator.getPage(), result, parameter);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <E, K, V> Page<E> pageAndFooter(String pageStatement, Map<K, V> parameter, int current, int pagesize) {
		
//		SqlSession  sqlSessionCus = getSqlSesion();
		
		PageBounds pageBounds = new PageBounds(current, pagesize);
		PageList<E> result = (PageList<E>) sqlSession.selectList(pageStatement, parameter, pageBounds);
		PageList<E> pageFooter = pageFooter(pageStatement+"Footer",  parameter,  current,  pagesize);

		Paginator paginator = result.getPaginator();
		return new PageContainer<E, K, V>(paginator.getTotalCount(), paginator.getLimit(), paginator.getPage(), result, parameter,pageFooter);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <E, K, V> PageList<E> pageFooter(String pageStatement, Map<K, V> parameter, int current, int pagesize) {
		
//		SqlSession  sqlSessionCus = getSqlSesion();
		
		PageBounds pageBounds = new PageBounds(1, pagesize);
		PageList<E> pageFooter = (PageList<E>) sqlSession.selectList(pageStatement, parameter, pageBounds);
		
		
		return pageFooter;
	}
	
	@Override
	public Connection getConnection() {
		return sqlSession.getConnection();
	}

	@Override
	public Configuration getConfiguration() {
		return sqlSession.getConfiguration();
	}

	@Override
	public SqlSessionTemplate getSqlSessionTemplate() {
		return sqlSession;
	}

	@Override
	public <T> T findOne(String statement) {
		
		 return sqlSession.selectOne(statement);
	}
 
	public <T> T findOne(String statement,T t){
		return sqlSession.selectOne(statement, t);
	}


	@Override
	public int countRecord(String statement, Map<String, Object> parameter) {
		List<Object> recordList = sqlSession.selectList(statement, parameter);
		if(recordList != null ){
			return recordList.size();
		}
		return 0;
	}


	@Override
	public Integer find(String statement, Object obj) {
		// TODO Auto-generated method stub
		return sqlSession.selectOne(statement,obj);
	}
 
}
