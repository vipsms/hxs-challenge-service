package com.eeduspace.challenge.persist.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.eeduspace.challenge.util.Order;
import com.eeduspace.challenge.util.Page;
import com.eeduspace.challenge.util.Pageable;



public interface BaseDao<T> {

	/**
	 * <p>存储实体</p>
	 * @param entity  实体
	 */
	public abstract void save(T entity);
	
	/**
	 * <p>批量存储实体</p>
	 * @param list  实体列表
	 */
	public abstract void saveList(List<T> list);

	/**
	 * <p>更新实体</p>
	 * @param entity  实体
	 */
	public abstract void update(T entity);	
	
	/**
	 * <p>根据ID查找实体</p>
	 * @param id  实体编号
	 * @return  如果查到返回实体，否则返回null
	 */
	public abstract T get(Serializable id);

    /**
     * 更加外键查找实体
     * @param value
     * @param type
     * @return
     */
    public T get(String value,String type);
	/**
	 * <p>删除实体</p>
	 * @param entity 实体
	 */
	public abstract void delete(T entity);
	
	
	/**
	 * <p>根据id删除实体</p>
	 * @param id
	 */
	public abstract void delete(Serializable id);

	
	/**
	 * <p>批量删除</p>
	 * @param ids  编号数组
	 */
	public abstract void delete(Serializable...ids);


	/**
	 * <p>删除所有的实体</p>
	 */
	public abstract void delete();
	
	/**
	 * <p>分页查询</p>
	 * @param pager  分页信息
	 * @param queryUtil  查询条件信息
	 */
	public abstract Page<T> findByPager(Pageable pageAble, Map<String, Object> queryMap);
	
	/**
	 * <p>不带条件的分页查询</p>
	 * @param pager  分页信息
	 */
	public abstract Page<T> findByPager(Pageable pageAble);
	
	/**
	 * <p>条件查询</p>
	 * @param queryUtil  查询条件信息
	 * @param order 排序信息
	 */
	public abstract List<T> findByCondition(Map<String, Object> queryMap,Order order);
	
	
	/**
	 * <p>查询所有的</p>
	 * @return List<T>
	 */
	public abstract List<T> getAll();
	
	
}
