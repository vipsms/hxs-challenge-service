package com.eeduspace.challenge.service;

import java.util.List;

import com.eeduspace.challenge.model.OrderItemModel;
import com.eeduspace.challenge.model.OrderModel;
import com.eeduspace.challenge.persist.po.Order;
import com.eeduspace.challenge.persist.po.OrderItem;



/**
 * 订单
 */
public interface OrderItemService extends BaseService<OrderItem>{

	/**
	 *订条目表查询
	 */
	public List<OrderItemModel> findOrderItemList(String orderUuid);
	/**
	 *订条目表保存
	 * @param order 
	 */
	public List<OrderItemModel> saveOrderItem(OrderModel orderModel, Order order);
	
	
}
