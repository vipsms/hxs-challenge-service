
package com.eeduspace.challenge.persist.dao.impl;


import org.springframework.stereotype.Repository;

import com.eeduspace.challenge.persist.dao.OrderItemMapper;
import com.eeduspace.challenge.persist.po.OrderItem;
@Repository("OrderItemMapperImpl")
public class OrderItemMapperImpl extends BaseDaoImpl<OrderItem> implements OrderItemMapper{
	/**
	 * 订单条目的保存
	 */
	@Override
	public OrderItem saveOrderItem(OrderItem orderItem) {
		this.save(orderItem);
	    return this.get(orderItem.getId());
	}
}
