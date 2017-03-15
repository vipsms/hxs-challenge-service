package com.eeduspace.challenge.enumeration;
/**
 * 任务枚举类
 * @author zhuchaowei
 * 2016年7月7日
 * Description
 */
public class TaskEnum {
	/**
	 * 任务信息分类枚举类型
	 * @author zhuchaowei
	 * 2016年7月7日
	 * Description
	 */
	public enum TaskInfo{
		NOVICE_TASK("新手任务","novice_task"),
		GROWTH_TASK("成长任务","growth_task"),
		DAILY_TASK("每日任务","daily_task");
		private final String desc;
		private final String taskCode;
		TaskInfo(String desc,String taskCode){
			this.desc=desc;
			this.taskCode=taskCode;
		}

        public String getTaskCode() {
            return taskCode;
        }
    }
	/**
	 * 具体任务基础信息枚举
	 * @author zhuchaowei
	 * 2016年7月7日
	 * Description
	 */
	public enum SpecificTasks{
		PERFECT_INFORMATION("信息完善任务","perfect_information"),
		FIGHTING_TEST("战斗力测试","fighting_test "),
		BUY_PHYSICAL_POWER("购买体力","buy_physical_power "),
		BATTLE("对战任务","novice"),
		FRIEND_BATTLE("好友对战任务","friend_battle"),
		BATTLE_VICTORY("对战胜利任务","battle_victory"),
		CHALLENGE("挑战任务","challenge_task"),
		CHALLENGE_VICTORY("挑战胜利任务","challenge_victory"),
		STAND_SHARE("分享战报任务","stand_share"),
		BATTLE_STAND_SHARE("对战战报分享任务","battle_stand_share"),
		CHALLENGE_STAND_SHARE("挑战战报分享任务","challenge_stand_share"),
		RANK_SHARE("排行榜分享任务","rank_share"),
		FRIEND_ADD("好友添加任务","friend_add"),
		LEARN_VIDEO("学习视频任务","learn_video"),
		LEARN_QUESTION("学习试题任务","learn_question");
		private final String desc;
		private final String taskCode;
		SpecificTasks(String desc,String taskCode){
			this.desc=desc;
			this.taskCode=taskCode;
		}
		public String getDesc() {
			return desc;
		}
		public String getTaskCode() {
			return taskCode;
		}
		
	}

    public enum ReceiveState {
        NoReceive(0),//未领取
        IsReceive(1);//已领取
        private final int value;

        public int getValue() {
            return value;
        }

        ReceiveState(int value) {
            this.value = value;
        }
    }
    public enum FinishState{
    	IsFinish(1,"已完成"),//已完成
    	NoFinish(0,"未完成");//未完成
    	private final int value;
    	private final String desc;
    	
    	FinishState(int value,String desc) {
			this.value = value;
			this.desc = desc;
		}

		public int getValue() {
			return value;
		}

		public String getDesc() {
			return desc;
		}
    	
    }
}
