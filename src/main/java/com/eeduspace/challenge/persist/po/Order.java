package com.eeduspace.challenge.persist.po;

import java.util.Date;

public class Order {
    private Long id;
    //唯一标识
    private String uuid;
    //订单号
    private String orderSn;
    //第三方流水号
    private String transcationSn;
    //订单名称
    private String orderName;
    //支付类型  支付宝  微信
    private String payType;
    //价格
    private String orderPrice;
    //订单类型  vip 诊断
    private String orderType;
    //是否支付
    private Integer isPay;
    //用户标识
    private String userCode;
    //是否删除
    private String isDel;
    //创建时间
    private Date createDate;
    //更新时间
    private Date updateDate;

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