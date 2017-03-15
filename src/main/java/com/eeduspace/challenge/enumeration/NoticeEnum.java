package com.eeduspace.challenge.enumeration;

/**
 * Author: dingran
 * Date: 2016/8/1
 * Description:
 */
public class NoticeEnum {

    public enum NoticeType{
        SYSTEM_NOTICE("系统通知", "system_notice"),
        FRIEND_APPLY_NOTICE("好友添加申请","friend_apply_notice"),
        FRIEND_REPLY_NOTICE_NO("拒绝了您的好友请求","friend_reply_notice"),
        FRIEND_REPLY_NOTICE_YES("同意了您的好友请求","friend_reply_notice"),
        RANK_NOTICE("排行榜通知","rank_notice"),
        BATTLE_NOTICE("对战通知","battle_notice"),
        TASK_NOTICE("任务通知","task_notice"),
        OTHER_NOTICE("其它通知","other_notice");
        private final String desc;
        private final String code;
        NoticeType(String desc, String code) {
            this.desc = desc;
            this.code = code;
        }
        public String getCode() {
            return code;
        }

        public String getDesc() {
            return desc;
        }

    }
    public enum Type{
        ALIAS("别名推送", "ALIAS"),
        TAG("便签推送","TAG"),
        ALL("群发","ALL");
        private final String desc;
        private final String code;
        Type(String desc, String code) {
            this.desc = desc;
            this.code = code;
        }
        public String getCode() {
            return code;
        }

        public String getDesc() {
            return desc;
        }

    }
    public enum ReadState{
        NOREAD("未读", "no_read", 0),
        READ("已读","read", 1);
        private final String desc;
        private final String code;
        private final int value;
        ReadState(String desc, String code, int value) {
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
