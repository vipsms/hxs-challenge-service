package com.eeduspace.challenge.controller;

import com.eeduspace.challenge.convert.UserConvert;
import com.eeduspace.challenge.model.OrderItemModel;
import com.eeduspace.challenge.model.OrderModel;
import com.eeduspace.challenge.persist.po.Order;
import com.eeduspace.challenge.persist.po.OrderItem;
import com.eeduspace.challenge.responsecode.BaseResponse;
import com.eeduspace.challenge.responsecode.ResponseCode;
import com.eeduspace.challenge.service.OrderItemService;
import com.eeduspace.challenge.service.OrderService;
import com.google.gson.Gson;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**  
 * Author: zz
 * Date: 2016/7/18
 * Description:订单
 */
@Controller
@RequestMapping("/order")
public class OrderController {
	private final Logger logger = LoggerFactory.getLogger(OrderController.class);
	private Gson gson=new Gson();
	@Inject
	private OrderService orderService;
	@Inject
	private OrderItemService orderItemService;
	
	
	/**
	 * 根据订单号 ，第三方流水号
	 * 查询订单
	 */
	@RequestMapping(value="/list",method=RequestMethod.POST)
	@ResponseBody
	public BaseResponse getOrederAllList(@RequestParam("requestId") String requestId){
        BaseResponse baseResponse = new BaseResponse(requestId);
        List<OrderItem> list = new ArrayList<OrderItem>();
		try {
			List<Order> orderList = orderService.findOrderAllList();
			 baseResponse.setResult(orderList);
		     baseResponse.setMessage("success");
			 return baseResponse;
		} catch (Exception e) {
			 logger.error("OrederList  error:", e);
			 return BaseResponse.setResponse(baseResponse, ResponseCode.SERVICE_ERROR.toString());
		}
	}
	/**
	 * 根据订单号 ，第三方流水号
	 * 查询订单
	 */
	@RequestMapping(value="/details_order",method=RequestMethod.POST)
	@ResponseBody
	public BaseResponse getOrederList(@RequestParam("requestId") String requestId,@RequestBody String requestBody){
        logger.debug("orderModel:{}",requestBody);
		OrderModel orderModel= gson.fromJson(requestBody, OrderModel.class);
        BaseResponse baseResponse = new BaseResponse(requestId);
        List<OrderItem> list = new ArrayList<OrderItem>();
		try {
			if(StringUtils.isBlank(String.valueOf(orderModel.getOrderSn()))){
				logger.error("details_order Exception：requestId："+ baseResponse.getRequestId()+","+ ResponseCode.PARAMETER_MISS.toString()+".orderSn");
	            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_INVALID.toString(), "orderSn");
            }
			Order order = orderService.findOrder(orderModel);
			if(StringUtils.isBlank(order.getUuid())){
	            logger.error("details_order Exception：requestId："+ baseResponse.getRequestId()+","+ ResponseCode.RESOURCE_NOTFOUND.toString()+".order");
	            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_INVALID.toString(), "order");
	         }
//			 OrderModel model = UserConvert.fromOrderToOrderModel(order);
//			 List<OrderItemModel> findOrderItemList = orderItemService.findOrderItemList(order.getUuid());
//			 logger.debug("list:{}",gson.toJson(findOrderItemList));
			 baseResponse.setResult(order);
		     baseResponse.setMessage("success");
			 return baseResponse;
		} catch (Exception e) {
			 logger.error("details_order  error:", e);
			 return BaseResponse.setResponse(baseResponse, ResponseCode.SERVICE_ERROR.toString());
		}
	}
	/**
	 * 根据订单号 
	 * 查询订单详情
	 */
	@RequestMapping(value="/details_orderItem",method=RequestMethod.POST)
	@ResponseBody
	public BaseResponse getOrderDetails(@RequestParam("requestId") String requestId,@RequestBody String requestBody){
        logger.debug("orderModel:{}",requestBody);
		OrderModel orderModel= gson.fromJson(requestBody, OrderModel.class);
        BaseResponse baseResponse = new BaseResponse(requestId);
		try {
			if(StringUtils.isBlank(orderModel.getUuid())){
				logger.error("details_orderItem Exception：requestId："+ baseResponse.getRequestId()+","+ ResponseCode.PARAMETER_MISS.toString()+".uuid");
	            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_INVALID.toString(), "orderSn");
            }
			 List<OrderItemModel> findOrderItemList = orderItemService.findOrderItemList(orderModel.getUuid());
			 logger.debug("findOrderItemList:{}",gson.toJson(findOrderItemList));
			 baseResponse.setResult(findOrderItemList);
		     baseResponse.setMessage("success");
			 return baseResponse;
		} catch (Exception e) {
			 logger.error("details_orderItem  error:", e);
			 return BaseResponse.setResponse(baseResponse, ResponseCode.SERVICE_ERROR.toString());
		}
	}
	/**
	 * 保存订单信息
	 * 保存订单详情
	 */
	@RequestMapping(value="/save",method=RequestMethod.POST)
	@ResponseBody
	public BaseResponse orederSave(@RequestParam("requestId") String requestId,@RequestBody String requestBody){
        logger.debug("orderModel:{}",requestBody);
		OrderModel orderModel= gson.fromJson(requestBody, OrderModel.class);
		logger.debug("orderModel:{}",gson.toJson(orderModel));
        BaseResponse baseResponse = new BaseResponse(requestId);
		try {
			Order order=orderService.saveOreder(orderModel);
			if(StringUtils.isBlank(order.getUuid())){
	            logger.error("saveOrder Exception：requestId："+ baseResponse.getRequestId()+","+ ResponseCode.RESOURCE_NOTFOUND.toString()+".order");
	            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_INVALID.toString(), "order");
	         }
			logger.debug("order:{}",gson.toJson(order));
			List<OrderItemModel> saveOrderItem = orderItemService.saveOrderItem(orderModel,order);
			if(saveOrderItem.size()==0){
				 logger.error("saveOrder Exception：requestId："+ baseResponse.getRequestId()+","+ ResponseCode.RESOURCE_NOTFOUND.toString()+".orderItim");
		         return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_INVALID.toString(), "orderItim");
		        }
			 OrderModel model = UserConvert.fromOrderToOrderModel(order);
			 // model.setList(saveOrderItem);
			 baseResponse.setResult(model);
			 baseResponse.setMessage("success");
			 return baseResponse;
		} catch (Exception e) {
			 logger.error("SaveOrder  error:", e);
			 return BaseResponse.setResponse(baseResponse, ResponseCode.SERVICE_ERROR.toString());
			 }
	}
	/**
	 * 修改订单信息
	 */
	@RequestMapping(value="/update",method=RequestMethod.POST)
	@ResponseBody
	public BaseResponse orederUpdate(@RequestParam("requestId") String requestId,@RequestBody String requestBody){
        logger.debug("orderModel:{}",requestBody);
		OrderModel orderModel= gson.fromJson(requestBody, OrderModel.class);
        BaseResponse baseResponse = new BaseResponse(requestId);
		try {
			      Order order = orderService.findOrder(orderModel);
			      if(null==order){
			            logger.error("updateOrder Exception：requestId："+ baseResponse.getRequestId()+","+ ResponseCode.RESOURCE_NOTFOUND.toString()+".order");
			            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_INVALID.toString(), "order");
			         }
			     orderService.updateOreder(orderModel,order);
			     baseResponse.setMessage("success");
			     return baseResponse;
		} catch (Exception e) {
			logger.error("updateOrder  error:", e);
	         return BaseResponse.setResponse(baseResponse, ResponseCode.SERVICE_ERROR.toString()); 
		}
	}
}
