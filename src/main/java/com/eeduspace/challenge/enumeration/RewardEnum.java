package com.eeduspace.challenge.enumeration;

/**
 * Author: dingran
 * Date: 2016/7/13
 * Description:奖励 积分兑换枚举
 */
public class RewardEnum {

    private RewardType rewardType;

    private ChangeType changeType;
    /**
     * 奖励分类枚举类型
     * Description
     */
    public enum RewardType{
        STAMINA("体力值","stamina"),
        SIGN("签到","sign"),
        TASK("任务","task"),
        BATTLE("对战","battle"),
        CHALLENGE("挑战","challenge"),
        SHARE("分享","share"),
        FEEDBACK("反馈","feedBack"),
        BUYVIPPACK("购买vip包","vipPack");
        private final String desc;
        private final String taskCode;
        RewardType(String desc,String taskCode){
            this.desc=desc;
            this.taskCode=taskCode;
        }

        public String getTaskCode() {
            return taskCode;
        }

        public String getDesc() {
            return desc;
        }
    }
    /**
     * 积分兑换枚举类型
     * Description
     */
    public enum ChangeType{
        EXCHANGE("兑换","exchange"),
        REWARD("奖励","reward");
        private final String desc;
        private final String taskCode;
        ChangeType(String desc,String taskCode){
            this.desc=desc;
            this.taskCode=taskCode;
        }

        public String getTaskCode() {
            return taskCode;
        }
    }
    public RewardType getRewardType() {
        return rewardType;
    }

    public void setRewardType(RewardType rewardType) {
        this.rewardType = rewardType;
    }

    public ChangeType getChangeType() {
        return changeType;
    }

    public void setChangeType(ChangeType changeType) {
        this.changeType = changeType;
    }
}
