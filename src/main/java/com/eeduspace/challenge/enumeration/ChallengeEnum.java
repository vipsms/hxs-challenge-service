package com.eeduspace.challenge.enumeration;


/**
 * 挑战类型枚举
 * 
 * @author zhuchaowei 2016年7月7日 Description
 */
public enum ChallengeEnum {
	RANKCHALLENGE("排行榜挑战", "rank_challenge",3), FRIENDCHALLENGE("好友挑战", "friend_challenge",4);
	private final String desc;
	private final String code;
	private int value;
	
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	ChallengeEnum(String desc, String code,int value) {
		this.desc = desc;
		this.code = code;
		this.value=value;
	}
	public static int getValue(String en){
   	 for (ChallengeEnum type : ChallengeEnum.values()) {
			if(type.toString().equals(en)){
				return type.getValue(); 
			}
		}
   	 return 0;
    }
}
