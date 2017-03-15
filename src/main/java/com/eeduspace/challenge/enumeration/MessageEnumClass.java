package com.eeduspace.challenge.enumeration;

public class MessageEnumClass {
	
	public enum MessageEnum{
		SYSTEMMESSAGE("系统消息", "system_message", 0), 
		FRIENDMESSAGE("添加朋友消息","friend_message", 1),
		OTHERMESSAGE("其它消息","other_message",2);
		private final String desc;
		private final String code;
		private final int value;
		MessageEnum(String desc, String code, int value) {
			this.desc = desc;
			this.code = code;
			this.value = value;
		}
		public String getCode() {
			return code;
		}
	}
	public enum MessageReadStateEnum{
		NOREAD("未读", "no_read", 0), 
		READ("已读","read", 1);
		private final String desc;
		private final String code;
		private final int value;
		MessageReadStateEnum(String desc, String code, int value) {
			this.desc = desc;
			this.code = code;
			this.value = value;
		}
		public String getCode() {
			return code;
		}
		public int getValue() {
			return value;
		}
		
	}
}
