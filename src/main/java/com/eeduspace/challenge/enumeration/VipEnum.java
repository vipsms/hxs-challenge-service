package com.eeduspace.challenge.enumeration;

public class VipEnum {
	private VipPackTypeEnum vipPackTypeEnum;

	public enum VipPackTypeEnum {
		ONE_MONTH(30, "VIP包-1个月"), TWO_MONTH(60, "VIP包-2个月"), THREE_MONTH(90, "VIP包-3个月"), ONE_YEAR(
				365, "VIP包-1年"), SIX_MONTH(180, "VIP包-半年"), TWO_YEAR(730, "VIP包-2年");
		private final int value;
		private final String desc;

		public int getValue() {
			return value;
		}

		VipPackTypeEnum(int value, String desc) {
			this.value = value;
			this.desc = desc;
		}

		public static int getValue(String en) {
			for (VipPackTypeEnum type : VipPackTypeEnum.values()) {
				if (type.toString().equals(en)) {
					return type.getValue();
				}
			}
			return 0;
		}
		public static String getDesc(String vipType){
			for (VipPackTypeEnum type : VipPackTypeEnum.values()) {
				if (type.toString().equals(vipType)) {
					return type.getDesc();
				}
			}
			return "其它";
		}
		public String getDesc() {
			return desc;
		}
	}

	public VipPackTypeEnum getVipPackTypeEnum() {
		return vipPackTypeEnum;
	}

	public void setVipPackTypeEnum(VipPackTypeEnum vipPackTypeEnum) {
		this.vipPackTypeEnum = vipPackTypeEnum;
	}
	public static void main(String[] args) {
		System.out.println(VipEnum.VipPackTypeEnum.getValue(VipPackTypeEnum.ONE_MONTH.toString()));
	}
}
