package com.eeduspace.challenge.persist.po;

import java.util.Date;

public class VipPack {
	//主键
    private Long id;
    //唯一标识
    private String uuid;
    //vip类型
    private String vipType;
    //VIP套餐价格 
    private Double vipPrice;
    //VIP是否优惠
    private Byte isDiscount;
    //VIP优惠后价格 
    private Double discountPrice;
    //VIP优惠起止时间 
    private Date discountStartTime;
    //VIP优惠截止时间
    private Date discountEndTime;
    //VIP套餐描述 
    private String vipDesc;
    //操作管理员 
    private String operationManager;
    //创建时间 
    private Date createDate;
    //更新时间 
    private Date updateDate;
    //vip天数
    private Integer vipDay;

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

    public String getVipType() {
        return vipType;
    }

    public void setVipType(String vipType) {
        this.vipType = vipType;
    }

    public Double getVipPrice() {
        return vipPrice;
    }

    public void setVipPrice(Double vipPrice) {
        this.vipPrice = vipPrice;
    }

    public Byte getIsDiscount() {
        return isDiscount;
    }

    public void setIsDiscount(Byte isDiscount) {
        this.isDiscount = isDiscount;
    }

    public Double getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(Double discountPrice) {
        this.discountPrice = discountPrice;
    }

    public Date getDiscountStartTime() {
        return discountStartTime;
    }

    public void setDiscountStartTime(Date discountStartTime) {
        this.discountStartTime = discountStartTime;
    }

    public Date getDiscountEndTime() {
        return discountEndTime;
    }

    public void setDiscountEndTime(Date discountEndTime) {
        this.discountEndTime = discountEndTime;
    }

    public String getVipDesc() {
        return vipDesc;
    }

    public void setVipDesc(String vipDesc) {
        this.vipDesc = vipDesc;
    }

    public String getOperationManager() {
        return operationManager;
    }

    public void setOperationManager(String operationManager) {
        this.operationManager = operationManager;
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

    public Integer getVipDay() {
        return vipDay;
    }

    public void setVipDay(Integer vipDay) {
        this.vipDay = vipDay;
    }
}