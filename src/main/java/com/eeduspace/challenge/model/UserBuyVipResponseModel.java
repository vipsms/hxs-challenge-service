package com.eeduspace.challenge.model;


/**
 *请求vip购买返回的Model
 *
 */
public class UserBuyVipResponseModel {

	/**微信**/
    private String appid;
    
    private String partnerid;
    
    private String prepayid;
    
    private String packaged; 
    
    private String noncestr;
    
    private String timestamp;
    
    private String sign;
    
    private String outTradeNo;
    
    
    
    
    private String  orderInfo;//支付宝的签名




	public String getAppid() {
		return appid;
	}




	public void setAppid(String appid) {
		this.appid = appid;
	}




	public String getPartnerid() {
		return partnerid;
	}




	public void setPartnerid(String partnerid) {
		this.partnerid = partnerid;
	}




	public String getPrepayid() {
		return prepayid;
	}




	public void setPrepayid(String prepayid) {
		this.prepayid = prepayid;
	}




	public String getPackaged() {
		return packaged;
	}




	public void setPackaged(String packaged) {
		this.packaged = packaged;
	}




	public String getNoncestr() {
		return noncestr;
	}




	public void setNoncestr(String noncestr) {
		this.noncestr = noncestr;
	}




	public String getTimestamp() {
		return timestamp;
	}




	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}




	public String getSign() {
		return sign;
	}




	public void setSign(String sign) {
		this.sign = sign;
	}




	public String getOutTradeNo() {
		return outTradeNo;
	}




	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}




	public String getOrderInfo() {
		return orderInfo;
	}




	public void setOrderInfo(String orderInfo) {
		this.orderInfo = orderInfo;
	}
    
    
    
    
    

    
    
    
    
}
