package com.eeduspace.challenge.persist.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.log4j.Logger;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import com.eeduspace.challenge.persist.dao.BaseDao;
import com.eeduspace.challenge.util.GenericsUtils;
import com.eeduspace.challenge.util.Order;
import com.eeduspace.challenge.util.Page;
import com.eeduspace.challenge.util.Pageable;


public class BaseDaoImpl<T> implements BaseDao<T> {
	private Class<T> entityClass;
	Logger logger = null;
	
	
	@Autowired
	protected SqlSessionTemplate sessionTemplate;
	
	@SuppressWarnings("unchecked")
	public BaseDaoImpl(){
		entityClass = GenericsUtils.getSuperClassGenricType(this.getClass());
	}

	public void delete(T entity) {
		String CRUD_DELETEBYID = entityClass.getSimpleName()+"_deleteById";
		Assert.notNull(entity);
		Serializable id = (Serializable)GenericsUtils.getFieldValue(entity, "getId");
		Assert.notNull(id);
		sessionTemplate.delete(CRUD_DELETEBYID, id);
		
		//this.logMybatisSql(CRUD_DELETEBYID, id);
	}

	public void delete(Serializable id) {
		String CRUD_DELETEBYID = entityClass.getSimpleName()+"_deleteById";
		Assert.notNull(id);
		sessionTemplate.delete(CRUD_DELETEBYID, id);
		//this.logMybatisSql(CRUD_DELETEBYID, id);
	}

	public void delete(Serializable...ids) {
		String CRUD_BATCH_DELEtEBYID = entityClass.getSimpleName()+"_batch_deleteById";
		Assert.notNull(ids);
		List<Object> list = new ArrayList<Object>();
		for(Serializable id: ids){
			list.add(id);
		}
		sessionTemplate.delete(CRUD_BATCH_DELEtEBYID, list);
		//this.logMybatisSql(CRUD_BATCH_DELEtEBYID, list);
	}

	public void delete() {
		String CRUD_DELETE_ALL = entityClass.getSimpleName()+"_batch_delete_all";
		//this.logMybatisSql(CRUD_DELETE_ALL, null);
		sessionTemplate.delete(CRUD_DELETE_ALL);
	}

	public List<T> findByCondition(Map<String, Object> queryMap,Order order) {
		if(order==null){
			order=new Order();
		}
		String CRUD_FINDBYPAGER = entityClass.getSimpleName()+"_findByPager";
		System.out.println(CRUD_FINDBYPAGER+"--------------------------------------------");
		this.logMybatisSql(CRUD_FINDBYPAGER, queryMap);
		queryMap.put("orderProperty", order.getProperty());
		queryMap.put("orderDirection", order.getDirection());
//		System.out.println(order.getProperty()+"++++++++++++++++"+order.getDirection());
		List<T> list = sessionTemplate.selectList(CRUD_FINDBYPAGER, queryMap);
		this.logMybatisSql(CRUD_FINDBYPAGER, queryMap);
//		System.out.println(list+"=======================");
		return list;
	}

	public Page<T> findByPager(Pageable pageAble, Map<String, Object> queryMap) {
		String CRUD_FINDBYPAGER = entityClass.getSimpleName()+"_findByPager";
		String CRUD_FINDBYPAGER_COUNT = entityClass.getSimpleName()+"_findByPager_count";
		queryMap.put("searchProperty", pageAble.getSearchProperty());
		queryMap.put("searchValue", pageAble.getSearchValue());
		queryMap.put("orderProperty", pageAble.getOrderProperty());
		queryMap.put("orderDirection", pageAble.getOrderDirection());
		queryMap.put("limit", pageAble.getPageSize());
		
		long total = sessionTemplate.selectOne(CRUD_FINDBYPAGER_COUNT, queryMap);
		this.logMybatisSql(CRUD_FINDBYPAGER_COUNT, queryMap);
		long totalPage = 0;
		
		if(total == 0) {
			totalPage = 1;
		}else if(pageAble.getPageSize() == 0) {
			totalPage = 1;
		}else{
			totalPage = total/pageAble.getPageSize();
			if((total%pageAble.getPageSize()) > 0) {
				totalPage++;
			}
		}
		if(pageAble.getPageNumber() > totalPage) {
			pageAble.setPageNumber(totalPage);
		}
		
		if(pageAble.getPageNumber() < 1) {
			pageAble.setPageNumber(1);
		}
		
		queryMap.put("start", (pageAble.getPageNumber()-1)*pageAble.getPageSize());
		this.logMybatisSql(CRUD_FINDBYPAGER, queryMap);
		List<T> list = sessionTemplate.selectList(CRUD_FINDBYPAGER, queryMap);
        
		Page<T> page = new Page<T>(list, total, pageAble);
		return page;
	}

	public Page<T> findByPager(Pageable pageAble) {
		String CRUD_FINDBYPAGER = entityClass.getSimpleName()+"_findByPager";
		String CRUD_FINDBYPAGER_COUNT = entityClass.getSimpleName()+"_findByPager_count";
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("searchProperty", pageAble.getSearchProperty());
		queryMap.put("searchValue", pageAble.getSearchValue());
		queryMap.put("orderProperty", pageAble.getOrderProperty());
		queryMap.put("orderDirection", pageAble.getOrderDirection());
		queryMap.put("limit", pageAble.getPageSize());
		
		long total = sessionTemplate.selectOne(CRUD_FINDBYPAGER_COUNT, queryMap);
		this.logMybatisSql(CRUD_FINDBYPAGER_COUNT, queryMap);
		long totalPage = 0;
		
		if(total == 0) {
			totalPage = 1;
		}else if(pageAble.getPageSize() == 0) {
			totalPage = 1;
		}else{
			totalPage = total/pageAble.getPageSize();
			if((total%pageAble.getPageSize()) > 0) {
				totalPage++;
			}
		}
		if(pageAble.getPageNumber() > totalPage) {
			pageAble.setPageNumber(totalPage);
		}
		
		if(pageAble.getPageNumber() < 1) {
			pageAble.setPageNumber(1);
		}
		
		queryMap.put("start", (pageAble.getPageNumber()-1)*pageAble.getPageSize());
		
		List<T> list = sessionTemplate.selectList(CRUD_FINDBYPAGER, queryMap);
		this.logMybatisSql(CRUD_FINDBYPAGER, queryMap);
		
		Page<T> page = new Page<T>(list, total, pageAble);
		
		return page;
	}

	public T get(Serializable id) {
		String CRUD_GETBYID = entityClass.getSimpleName()+"_getById";
		Assert.notNull(id);
		
		//this.logMybatisSql(CRUD_GETBYID, id);
		
		return sessionTemplate.selectOne(CRUD_GETBYID, id);
	}
    public T get(String value,String type) {
        String CRUD_GETBYID = entityClass.getSimpleName()+"_"+type+"_getByValue";

        return sessionTemplate.selectOne(CRUD_GETBYID, value);
    }
	public List<T> getAll() {
		String CRUD_GETALL = entityClass.getSimpleName()+"_getAll";
		
		//this.logMybatisSql(CRUD_GETALL, null);
		
		return sessionTemplate.selectList(CRUD_GETALL);
	}

	public void save(T entity) {
		String CRUD_SAVE = entityClass.getSimpleName()+"_save";
		Assert.notNull(entity);
		sessionTemplate.insert(CRUD_SAVE, entity);
		
		this.logMybatisSql(CRUD_SAVE, entity);
	}

	public void saveList(List<T> list) {
		String CRUD_BATCH_SAVE = entityClass.getSimpleName()+"_save";
		Assert.notNull(list);
		
		for(T t: list){
			sessionTemplate.insert(CRUD_BATCH_SAVE, t);
		}
		this.logMybatisSql(CRUD_BATCH_SAVE, list);
	}

	public void update(T entity) {
		String CRUD_UPDATE = entityClass.getSimpleName()+"_update";
		Assert.notNull(entity);
		sessionTemplate.update(CRUD_UPDATE, entity);
	}
	
	/**打印sql日志*/
	protected void logMybatisSql(String id, Object parameterObject){
		try{
			MappedStatement ms = sessionTemplate.getConfiguration().getMappedStatement(id);
	        BoundSql boundSql = ms.getBoundSql(parameterObject);
	        
	        logger = Logger.getLogger(GenericsUtils.getSuperClassGenricType(entityClass));
	        logger.info(boundSql.getSql().trim().replaceAll("\n", ""));
	        logger.info(boundSql.getParameterObject());
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
}
