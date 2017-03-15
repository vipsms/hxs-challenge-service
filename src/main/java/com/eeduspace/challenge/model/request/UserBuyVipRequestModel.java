package com.eeduspace.challenge.model.request;


/**
 *vip包购买
 */
public class UserBuyVipRequestModel {


    private String userCode;
    
    private String vipCode;
    
    private String GradeType;//type 
    
    private String vipDesc;//body
    
    private String vipPrice;
    
    private String ip;
    
    
    
    
    

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getVipCode() {
		return vipCode;
	}

	public void setVipCode(String vipCode) {
		this.vipCode = vipCode;
	}

	public String getGradeType() {
		return GradeType;
	}

	public void setGradeType(String gradeType) {
		GradeType = gradeType;
	}

	public String getVipDesc() {
		return vipDesc;
	}

	public void setVipDesc(String vipDesc) {
		this.vipDesc = vipDesc;
	}

	public String getVipPrice() {
		return vipPrice;
	}

	public void setVipPrice(String vipPrice) {
		this.vipPrice = vipPrice;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}


    
    
    
    
}
