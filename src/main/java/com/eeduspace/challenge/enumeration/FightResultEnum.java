package com.eeduspace.challenge.enumeration;

/**
 * 战斗结果类型枚举
 * 
 * @author zhuchaowei 2016年7月7日 Description
 */
public enum FightResultEnum {
	VICTORY("胜利", "victory", 1), 
	DRAW("平局", "draw", 2), 
	FAILURE("失败","failure", 0);
	private final String desc;
	private final String code;
	private final int value;

	FightResultEnum(String desc, String code, int value) {
		this.desc = desc;
		this.code = code;
		this.value = value;
	}

	public static int getValue(String en) {
		for (FightResultEnum type : FightResultEnum.values()) {
			if (type.toString().equals(en)) {
				return type.getValue();
			}
		}
		return 0;
	}

	public int getValue() {
		return value;
	}
}
