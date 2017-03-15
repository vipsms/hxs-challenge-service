
package com.eeduspace.challenge.persist.dao.impl;


import org.springframework.stereotype.Repository;

import com.eeduspace.challenge.persist.dao.OrderMapper;
import com.eeduspace.challenge.persist.po.Order;
@Repository("OrderMapperImpl")
public class OrderMapperImpl extends BaseDaoImpl<Order> implements OrderMapper{

	
	/**
	 * 订单信息的保存
	 */
	@Override
	public Order saveOreder(Order order) {
		this.save(order);
	    return this.get(order.getId());
	}


}
