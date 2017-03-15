package com.eeduspace.challenge.enumeration;

/**
 * 战斗状态类型枚举
 * 
 * @author zhuchaowei 2016年7月7日 Description
 */
public enum FightStateEnum {
	FIGHTING("战斗中", "fighting", 1), FIGHTEND("战斗结束", "fight_end", 2),FIGHTLAUNCH("战斗发起中","fight_launch",0);
	private final String desc;
	private final String code;
	private final int value;

	FightStateEnum(String desc, String code, int value) {
		this.desc = desc;
		this.code = code;
		this.value = value;
	}

	public static int getValue(String en) {
		for (FightStateEnum type : FightStateEnum.values()) {
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
