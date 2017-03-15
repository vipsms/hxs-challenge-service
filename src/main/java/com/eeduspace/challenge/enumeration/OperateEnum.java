package com.eeduspace.challenge.enumeration;
/**
 * 模块类型枚举
 * @author zhuchaowei
 * 2016年7月7日
 * Description
 */
public enum OperateEnum {
	USER_LOGIN("用户登录","user_login"),
	USER_REGISTER("用户注册","user_register"),
	CHANGE_PASSWORD("修改密码","change_password"),
	USERINFO_EDIT("用户详细信息修改","userinfo_edit"),
	USER_CHALLENGE("用户挑战","user_challenge"),
	USER_BATTLE("用户对战","user_battle"),
	USER_SHARE("用户分享","user_share"),
	BATTLE_STAND_SHARE("对战战报分享任务","battle_stand_share"),
	CHALLENGE_STAND_SHARE("挑战战报分享任务","challenge_stand_share"),
	RANK_SHARE("排行榜分享任务","rank_share"),
	USER_FEED_BACK("用户分享","user_feed_back"),
	REDEEM("积分兑换","redeem"),
	USER_SIGN("用户签到","user_sign"),
	USER_DIAGNOSIS("用户诊断","user_diagnosis"),
	USER_WATCH_VIDEO("用户观看视频","user_watch_video"),
	USER_LEARN_EXERCISES("用户练习题练一练","user_learn_exercises"),
	BUY_DIAGNOSIS("诊断购买","buy_diagnosis"),
	BUY_VIP("VIP购买","buy_vip");
	//TODO 待添加操作类型
	/**描述**/
	private String desc;
	/**code**/
	private String code;
	OperateEnum(String desc,String code){
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
		for (OperateEnum an : OperateEnum.values()) {
			if (an.toString().equals(enumType)) {
				return an.getCode();
			}
		}
		return "";
    }
}
