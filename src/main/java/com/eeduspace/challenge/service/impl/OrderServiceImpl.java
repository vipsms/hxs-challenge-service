package com.eeduspace.challenge.service.impl;



import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eeduspace.challenge.convert.UserConvert;
import com.eeduspace.challenge.model.OrderModel;
import com.eeduspace.challenge.persist.dao.OrderMapper;
import com.eeduspace.challenge.persist.po.Order;
import com.eeduspace.challenge.service.OrderService;
import com.eeduspace.challenge.util.OrderUtil;

@Service("OrderServiceImpl")
public class OrderServiceImpl extends BaseServiceImpl<Order> implements OrderService{
	@Resource
	private OrderMapper orderMapperImpl;
	@Autowired
	public OrderServiceImpl(OrderMapper questionMapper) {
		this.baseDao = questionMapper;
	}
	/**
	 *订单保存
	 */
	@Override
	public Order saveOreder(OrderModel orderModel) {
		Order order = UserConvert.fromOrderModelToOrder(orderModel);
		String orderSn=OrderUtil.GetOrderNumber("ZD");
		order.setUuid(UUID.randomUUID().toString().replace("-", ""));
		order.setOrderSn(orderSn);
		return orderMapperImpl.saveOreder(order);
		
	}
	/**
	 *订单查询
	 */
	@Override
	public Order findOrder(OrderModel orderModel) {
		Map<String, Object> queryMap=new HashMap<>();
		queryMap.put("uuid",orderModel.getUuid() );
		queryMap.put("orderSn", orderModel.getOrderSn());
		queryMap.put("transcationSn", orderModel.getTranscationSn());
		return this.findByCondition(queryMap, null).get(0);
	}
	/**
	 *订单修改
	 * @return 
	 */
	@Override
	public void updateOreder(OrderModel orderModel, Order order) {
		if (StringUtils.isNotBlank(orderModel.getIsDel())) {
			 order.setIsDel(orderModel.getIsDel());
		}
		if (null!=orderModel.getIsPay()) {
	        order.setIsPay(orderModel.getIsPay());
		}
		if (StringUtils.isNotBlank(orderModel.getOrderName())) {
	        order.setOrderName(orderModel.getOrderName());
		}
		if (StringUtils.isNotBlank(orderModel.getOrderPrice())) {
	        order.setOrderPrice(orderModel.getOrderPrice());
		}
		if (StringUtils.isNotBlank(orderModel.getOrderSn())) {
	        order.setOrderSn(orderModel.getOrderSn());
		}
		if (StringUtils.isNotBlank(orderModel.getOrderType())) {
	        order.setOrderType(orderModel.getOrderType());
		}
		if (StringUtils.isNotBlank(orderModel.getPayType())) {
	        order.setPayType(orderModel.getPayType());
		}
		if (StringUtils.isNotBlank(orderModel.getTranscationSn())) {
	        order.setTranscationSn(orderModel.getTranscationSn());
		}
		if (StringUtils.isNotBlank(orderModel.getUserCode())) {
	        order.setUserCode(orderModel.getUserCode());
		}
        this.update(order);
	}
	/**
	 *订单查询全部
	 */
	@Override
	public List<Order> findOrderAllList() {
		Map<String, Object> queryMap=new HashMap<>();
		return this.findByCondition(queryMap);
	}
	
}
