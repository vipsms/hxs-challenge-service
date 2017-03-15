package com.eeduspace.challenge.persist.dao;

import com.eeduspace.challenge.persist.po.OrderItem;


public interface OrderItemMapper extends BaseDao<OrderItem>{

	public OrderItem saveOrderItem(OrderItem orderItem);
   
}