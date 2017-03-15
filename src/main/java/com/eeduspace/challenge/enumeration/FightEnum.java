package com.eeduspace.challenge.enumeration;

/**
 * 战斗类型枚举
 * 
 * @author zhuchaowei 2016年7月7日 Description
 */
public enum FightEnum {
	BATTLE("对战", "battle", 0), CHALLENGE("挑战", "challenge", 1);
	private final String desc;
	private final String code;
	private final int value;

	FightEnum(String desc, String code, int value) {
		this.desc = desc;
		this.code = code;
		this.value = value;
	}

	public static int getValue(String en) {
		for (FightEnum type : FightEnum.values()) {
			if (type.toString().equals(en)) {
				return type.getValue();
			}
		}
		return 0;
	}
	public static FightEnum getEnum(String value){
		for (FightEnum an : FightEnum.values()) {
            if (an.toString().equals(value)){
                return an;
            }
        }
		return null;
	}
	public int getValue() {
		return value;
	}
}
