package com.eeduspace.challenge.model;

import java.util.Date;
import java.util.List;


public class OrderModel {

	  private Long id;

	    private String uuid;

	    private String orderSn;

	    private String transcationSn;

	    private String orderName;

	    private String payType;

	    private String orderPrice;

	    private String orderType;

	    private Integer isPay;

	    private String userCode;

	    private String isDel;

	    private Date createDate;
	    private Date updateDate;
	    
	    private List<OrderItemModel> list;
	    

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getUuid() {
			return uuid;
		}

		public void setUuid(String uuid) {
			this.uuid = uuid;
		}

		public String getOrderSn() {
			return orderSn;
		}

		public void setOrderSn(String orderSn) {
			this.orderSn = orderSn;
		}

		public String getTranscationSn() {
			return transcationSn;
		}

		public void setTranscationSn(String transcationSn) {
			this.transcationSn = transcationSn;
		}

		public String getOrderName() {
			return orderName;
		}

		public void setOrderName(String orderName) {
			this.orderName = orderName;
		}

		public String getPayType() {
			return payType;
		}

		public void setPayType(String payType) {
			this.payType = payType;
		}

		public String getOrderPrice() {
			return orderPrice;
		}

		public void setOrderPrice(String orderPrice) {
			this.orderPrice = orderPrice;
		}

		public String getOrderType() {
			return orderType;
		}

		public void setOrderType(String orderType) {
			this.orderType = orderType;
		}

		public Integer getIsPay() {
			return isPay;
		}

		public void setIsPay(Integer isPay) {
			this.isPay = isPay;
		}

		public String getUserCode() {
			return userCode;
		}

		public void setUserCode(String userCode) {
			this.userCode = userCode;
		}

		public String getIsDel() {
			return isDel;
		}

		public void setIsDel(String isDel) {
			this.isDel = isDel;
		}

		public List<OrderItemModel> getList() {
			return list;
		}

		public void setList(List<OrderItemModel> list) {
			this.list = list;
		}

		public Date getCreateDate() {
			return createDate;
		}

		public void setCreateDate(Date createDate) {
			this.createDate = createDate;
		}

		public Date getUpdateDate() {
			return updateDate;
		}

		public void setUpdateDate(Date updateDate) {
			this.updateDate = updateDate;
		}
	    
	    
	    

}
