package com.eeduspace.challenge.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eeduspace.challenge.convert.UserConvert;
import com.eeduspace.challenge.model.OrderItemModel;
import com.eeduspace.challenge.model.OrderModel;
import com.eeduspace.challenge.persist.dao.OrderItemMapper;
import com.eeduspace.challenge.persist.po.Order;
import com.eeduspace.challenge.persist.po.OrderItem;
import com.eeduspace.challenge.service.OrderItemService;

@Service("OrderItemServiceImpl")
public class OrderItemServiceImpl extends BaseServiceImpl<OrderItem> implements OrderItemService{
	@Resource
	private OrderItemMapper orderItemMapperImpl;
	@Autowired
	public OrderItemServiceImpl(OrderItemMapper questionMapper) {
		this.baseDao = questionMapper;
	}
	/**
	 *订单条目表查询
	 */
	@Override
	public List<OrderItemModel> findOrderItemList(String orderUuid) {
		List<OrderItemModel> listModel = new ArrayList<OrderItemModel>();
		Map<String, Object> queryMap=new HashMap<>();
		queryMap.put("orderUuid",orderUuid);
		List<OrderItem> list = this.findByCondition(queryMap);
		if (list.size()!=0) {
			for (OrderItem orderItem : list) {
				OrderItemModel fromOrderItemToModel = UserConvert.fromOrderItemToModel(orderItem);
				listModel.add(fromOrderItemToModel);
			}
		}
		return listModel;
		
	}
	/**
	 *订单条目表保存
	 */
	@Override
	public List<OrderItemModel> saveOrderItem(OrderModel orderModel,Order order) {
		
		List<OrderItemModel> itemModels = new ArrayList<OrderItemModel>();
		List<OrderItemModel> list = new ArrayList<OrderItemModel>();
		itemModels = orderModel.getList();
		if (itemModels.size()!=0) {
			for (OrderItemModel orderItemModel : itemModels) {
				OrderItem	orderItem = UserConvert.fromModelToOrderItem(orderItemModel);
				orderItem.setOrderUuid(order.getUuid());
				orderItem.setUuid(UUID.randomUUID().toString().replace("-", ""));
				orderItem = orderItemMapperImpl.saveOrderItem(orderItem);
				list.add(UserConvert.fromOrderItemToModel(orderItem));
			}
		}
		return list;
	}
	
}
