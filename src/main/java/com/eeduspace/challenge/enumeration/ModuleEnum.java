package com.eeduspace.challenge.enumeration;


/**
 * 模块类型枚举
 * @author zhuchaowei
 * 2016年7月7日
 * Description
 */
public enum ModuleEnum {
	USER_CENTER("用户模块","user_center"),
	RESOURCES("资源模块","resources"),
	BATTLE("对战模块","battle"),
	CHALLENGE("挑战模块","challenge"),
	RANK("排行榜模块","rank"),
	COMPETENCE("权限模块","competence"),
	INTEGRAL("积分模块","integral"),
	PAY("支付模块","pay"),
	SHARE("分享模块","share"),
	TUTOR("辅导模块","tutor"),
	DIAGNOSIS("诊断模块","diagnosis"),
	VIPPACK("VIP包模块","vip_pack"),
	ORDER("订单模块","order"),
	MESSAGE("消息模块","message"),
	TASK("任务模块","task");
	/**描述**/
	private String desc;
	/**code**/
	private String code;
	ModuleEnum(String desc,String code){
		this.desc=desc;
		this.code=code;
	}
	public String getDesc() {
		return desc;
	}
	public String getCode() {
		return code;
	}
	public static String toEnumCode(String enumType) {
			for (ModuleEnum an : ModuleEnum.values()) {
				if (an.toString().equals(enumType)) {
					return an.getCode();
				}
			}
			return "";
	}
	
}
