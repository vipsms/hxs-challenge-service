package com.eeduspace.challenge.enumeration;


/**
 * 字典项
 */
public enum SystemDictionaryEnum {
	USER_STRENGTH("体力兑换值","user_strength"),
	USER_STAMINA("普通用户每天体力值,每天可兑换次数","user_stamina"),
	USER_STAMINA_VIP("VIP用户每天体力值,每天可兑换次数","user_stamina_vip"),
	USER_FEEDBACK("每个用户第一次反馈奖励积分","user_feed_back"),
	ONE_MONTH("购买vip包一个月奖励积分", "vip_one_month"),
	TWO_MONTH("购买vip包两个月奖励积分", "vip_two_month"), 
	THREE_MONTH("购买vip包三个月奖励积分", "vip_three_month"),
	SIX_MONTH("购买vip包六个月奖励积分", "vip_six_month"), 
	ONE_YEAR("购买vip包一年奖励积分", "vip_one_year"), 
	TWO_YEAR("购买vip包两年奖励积分", "vip_two_year");

	;
	//TODO 待添加操作类型
	/**描述**/
	private String desc;
	/**code**/
	private String code;
	SystemDictionaryEnum(String desc,String code){
		this.desc=desc;
		this.code=code;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	

	 public static String toEnumValue(String enumName) {
			for (SystemDictionaryEnum an : SystemDictionaryEnum.values()) {
				if (an.getDesc().equals(enumName)) {
					return an.getCode();
				}
			}
			return "";
	}
	
	 public static String toEnumCode(String enumTitle) {
			for (SystemDictionaryEnum an : SystemDictionaryEnum.values()) {
				if (an.toString().equals(enumTitle)) {
					return an.getCode();
				}
			}
			return "";
	}
}
