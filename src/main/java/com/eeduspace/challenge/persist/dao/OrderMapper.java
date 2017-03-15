package com.eeduspace.challenge.persist.dao;

import com.eeduspace.challenge.persist.po.Order;


public interface OrderMapper extends BaseDao<Order>{

	
	
	public Order saveOreder(Order order);
   
	
	
}