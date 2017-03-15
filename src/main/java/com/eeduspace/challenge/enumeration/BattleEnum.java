package com.eeduspace.challenge.enumeration;


/**
 * 对战类型枚举
 * 
 * @author zhuchaowei 2016年7月7日 Description
 */
public enum BattleEnum {
	QUICKBATTLE("快速对战", "quick_battle", 0), FRIENDBATTLE("好友对战",
			"friend_battle", 1);
	private final String desc;
	private final String code;
	private final int value;

	BattleEnum(String desc, String code, int value) {
		this.desc = desc;
		this.code = code;
		this.value = value;
	}

	public static int getValue(String en) {
		for (BattleEnum type : BattleEnum.values()) {
			if (type.toString().equals(en)) {
				return type.getValue();
			}
		}
		return 0;
	}
	public static BattleEnum getEnum(String value){
		for (BattleEnum an : BattleEnum.values()) {
            if (an.toString().equals(value)){
                return an;
            }
        }
		return null;
	}
	public static BattleEnum getEnum(int  value){
		for (BattleEnum an : BattleEnum.values()) {
            if (an.getValue()==value){
                return an;
            }
        }
		return null;
	}
	public int getValue() {
		return value;
	}

	public String getCode() {
		return code;
	}
}
