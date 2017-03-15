package com.eeduspace.challenge.service;

import java.text.ParseException;
import java.util.List;

import com.eeduspace.challenge.model.OrderModel;
import com.eeduspace.challenge.persist.po.Order;



/**
 * 订单
 */
public interface OrderService extends BaseService<Order>{



	/**
	 *订单保存
	 */
	public Order saveOreder(OrderModel orderModel);
	/**
	 *订单查询po
	 */
	public Order findOrder(OrderModel orderModel);
	/**
	 *订单修改
	 * @param order 
	 * @throws ParseException 
	 */
	public void updateOreder(OrderModel orderModel, Order order);
	
	/**
	 *订单查询全部
	 */
	public List<Order> findOrderAllList();

	
	
}
