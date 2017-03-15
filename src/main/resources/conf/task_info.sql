/*
Navicat MySQL Data Transfer

Source Server         : 192.168.1.12
Source Server Version : 50629
Source Host           : 192.168.1.12:3006
Source Database       : hxs_challenge

Target Server Type    : MYSQL
Target Server Version : 50629
File Encoding         : 65001

Date: 2016-07-22 20:01:33
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `task_info`
-- ----------------------------
DROP TABLE IF EXISTS `task_info`;
CREATE TABLE `task_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `uuid` varchar(32) DEFAULT NULL,
  `task_name` varchar(256) DEFAULT NULL,
  `task_type` varchar(256) DEFAULT NULL,
  `task_describe` varchar(256) DEFAULT NULL,
  `concrete_task_uuid` varchar(32) DEFAULT NULL,
  `task_creator` varchar(32) DEFAULT NULL,
  `task_creator_code` varchar(32) DEFAULT NULL,
  `concrete_task_code` varchar(32) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8 COMMENT='任务';

-- ----------------------------
-- Records of task_info
-- ----------------------------
INSERT INTO `task_info` VALUES ('1', '3f10903cdd1646c5934b35a82939640e', '完善用户基本信息', 'novice_task', '完善用户基本信息', '80b804869f1a4500aebe0f8518272251', 'system', 'system', 'perfect_information', '2016-07-22 15:14:25', '2016-07-22 15:14:28');
INSERT INTO `task_info` VALUES ('2', '5g10903cdd1646c5934b35a82939640f', '对战胜利任务', 'novice_task', '用户完成1次快速对战且胜利', '17b804869f1a4500aebe0f8518272254', 'system', 'system', 'battle_victory', '2016-07-22 15:16:07', '2016-07-22 15:16:10');
INSERT INTO `task_info` VALUES ('3', '2f10903cdd1646c5934b35a82939640d', '分享战报任务', 'novice_task', '分享一次战报', 'etb804869f1a4500aebe0f85182722op', 'system', 'system', 'stand_share', '2016-07-22 15:19:46', '2016-07-22 15:19:49');
INSERT INTO `task_info` VALUES ('4', '3810903cdd1646c5934b35a82939640o', '新增好友任务', 'novice_task', '用户添加一次好友', '34b804869f1a4500aebe0f8518272210', 'system', 'system', 'friend_add', '2016-07-22 15:26:50', '2016-07-22 15:19:46');
INSERT INTO `task_info` VALUES ('5', '3h10903cdd1646c5934b35a82939640q', '好友对战任务', 'novice_task', '用户完成一次好友对战', '32b804869f1a4500aebe0f8518272253', 'system', 'system', 'friend_battle', '2016-07-22 15:19:46', '2016-07-22 15:19:46');
INSERT INTO `task_info` VALUES ('6', '5h10903cdd1646c5934b35a8293964we', '挑战任务', 'novice_task', '用户完成一次挑战', '63b804869f1a4500aebe0f8518272255', 'system', 'system', 'challenge_task', '2016-07-22 15:19:46', '2016-07-22 15:19:46');
INSERT INTO `task_info` VALUES ('7', '9o10903cdd1646c5934b35a8293964ty', '购买体力', 'novice_task', '用户使用积分兑换一次挑战体力', '76b804869f1a4500aebe0f8518272214', 'system', 'system', 'buy_physical_power', '2016-07-22 15:19:46', '2016-07-22 15:19:46');
INSERT INTO `task_info` VALUES ('8', '1h10903cdd1646c5934b35a8293964gh', '用户完成一次战斗力测试', 'novice_task', '用户完成一次战斗力测试', '98b804869f1a4500aebe0f8518272213', 'system', 'system', 'fighting_test', '2016-07-22 15:19:46', '2016-07-22 15:19:46');
INSERT INTO `task_info` VALUES ('9', '3310903cdd1646c5934b35a8293964df', '视频学习', 'novice_task', '用户完成一次自我修炼知识点视频学习', '56b804869f1a4500aebe0f8518272211', 'system', 'system', 'learn_video', '2016-07-22 15:19:46', '2016-07-22 15:19:46');
INSERT INTO `task_info` VALUES ('10', '2a10903cdd1646c5934b35a8293964sd', '试题练习', 'novice_task', '用户完成一次自我修炼知识点试题练习', '23b804869f1a4500aebe0f8518272212', 'system', 'system', 'learn_question', '2016-07-22 15:19:46', '2016-07-22 15:19:46');
INSERT INTO `task_info` VALUES ('11', '4510903cdd1646c5934b35a829396400', '对战任务', 'daily_task', '用户完成快速对战', '92b804869f1a4500aebe0f8518272252', 'system', 'system', 'novice', '2016-07-22 15:19:46', '2016-07-22 15:19:46');
INSERT INTO `task_info` VALUES ('12', '45rt903cdd1646c5934b35a8293964qq', '好友对战任务', 'daily_task', '用户完成好友对战', '32b804869f1a4500aebe0f8518272253', 'system', 'system', 'friend_battle', '2016-07-22 15:19:46', '2016-07-22 15:19:46');
INSERT INTO `task_info` VALUES ('13', '4uyg903cdd1646c5934b35a82939643r', '对战胜利任务', 'daily_task', '用户完成对战且胜利', '17b804869f1a4500aebe0f8518272254', 'system', 'system', 'battle_victory', '2016-07-22 15:19:46', '2016-07-22 15:19:46');
INSERT INTO `task_info` VALUES ('14', '2rft903cdd1646c5934b35a8293964ee', '挑战任务', 'daily_task', '用户完成挑战', '63b804869f1a4500aebe0f8518272255', 'system', 'system', 'challenge_task', '2016-07-22 15:19:46', '2016-07-22 15:19:46');
INSERT INTO `task_info` VALUES ('15', '56uj903cdd1646c5934b35a8293964ff', '挑战胜利任务', 'daily_task', '用户完成挑战且胜利', '54b804869f1a4500aebe0f8518272256', 'system', 'system', 'challenge_victory', '2016-07-22 15:19:46', '2016-07-22 15:19:46');
INSERT INTO `task_info` VALUES ('16', '6yhd903cdd1646c5934b35a829396444', '对战战报分享任务', 'daily_task', '用户分享对战战报', '65b804869f1a4500aebe0f8518272257', 'system', 'system', 'battle_stand_share', '2016-07-22 15:19:46', '2016-07-22 15:19:46');
INSERT INTO `task_info` VALUES ('17', 'thfg903cdd1646c5934b35a82939644t', '挑战战报分享任务', 'daily_task', '用户分享挑战战报', '87b804869f1a4500aebe0f8518272258', 'system', 'system', 'challenge_stand_share', '2016-07-22 15:19:46', '2016-07-22 15:19:46');
INSERT INTO `task_info` VALUES ('18', 'er56903cdd1646c5934b35a82939642e', '排行榜分享任务', 'daily_task', '用户分享排行榜', '72b804869f1a4500aebe0f8518272259', 'system', 'system', 'rank_share', '2016-07-22 15:19:46', '2016-07-22 15:19:46');
INSERT INTO `task_info` VALUES ('19', 'fh67903cdd1646c5934b35a8293964rf', '好友添加', 'daily_task', '成功添加好友一名', '34b804869f1a4500aebe0f8518272210', 'system', 'system', 'friend_add', '2016-07-22 15:19:46', '2016-07-22 15:19:46');
INSERT INTO `task_info` VALUES ('20', '45th903cdd1646c5934b35a82939643e', '学习视频任务', 'daily_task', '观看完整视频', '56b804869f1a4500aebe0f8518272211', 'system', 'system', 'learn_video', '2016-07-22 15:19:46', '2016-07-22 15:19:46');
INSERT INTO `task_info` VALUES ('21', 'sf45903cdd1646c5934b35a82939645g', '学习试题任务', 'daily_task', '做知识点练习题', '23b804869f1a4500aebe0f8518272212', 'system', 'system', 'learn_question', '2016-07-22 15:19:46', '2016-07-22 15:19:46');
INSERT INTO `task_info` VALUES ('22', '56hg903cdd1646c5934b35a8293964hj', '对战成长任务', 'growth_task', '完成对战次数', '92b804869f1a4500aebe0f8518272252', 'system', 'system', 'novice', '2016-07-22 15:19:46', '2016-07-22 15:19:46');
INSERT INTO `task_info` VALUES ('23', '45gd903cdd1646c5934b35a8293964dg', '挑战成长任务', 'growth_task', '挑成对战次数', '63b804869f1a4500aebe0f8518272255', 'system', 'system', 'challenge_task', '2016-07-22 15:19:46', '2016-07-22 15:19:46');
INSERT INTO `task_info` VALUES ('24', 'fg76903cdd1646c5934b35a8293964fg', '学习视频任务', 'growth_task', '学习视频', '56b804869f1a4500aebe0f8518272211', 'system', 'system', 'learn_video', '2016-07-22 15:19:46', '2016-07-22 15:19:46');
INSERT INTO `task_info` VALUES ('25', '23ef903cdd1646c5934b35a8293964dg', '学习试题任务', 'growth_task', '学习试题', '23b804869f1a4500aebe0f8518272212', 'system', 'system', 'learn_question', '2016-07-22 15:19:46', '2016-07-22 15:19:46');
INSERT INTO `task_info` VALUES ('26', 'fg09903cdd1646c5934b35a8293964gf', '分享战报任务', 'growth_task', '分享对战战报', '65b804869f1a4500aebe0f8518272257', 'system', 'system', 'battle_stand_share', '2016-07-22 15:19:46', '2016-07-22 15:19:46');
INSERT INTO `task_info` VALUES ('27', 'weop903cdd1646c5934b35a82939645t', '分享排行榜任务', 'growth_task', '分享排行榜', '72b804869f1a4500aebe0f8518272259', 'system', 'system', 'rank_share', '2016-07-22 15:19:46', '2016-07-22 15:19:46');
INSERT INTO `task_info` VALUES ('28', 'l009903cdd1646c5934b35a8293964ui', '对战胜利成长任务', 'growth_task', '对战胜利成长任务', '17b804869f1a4500aebe0f8518272254', 'system', 'system', 'battle_victory', '2016-07-22 15:19:46', '2016-07-22 15:19:46');
INSERT INTO `task_info` VALUES ('29', 'peop903cdd1646c5934b35a829390opl', '挑战胜利成长任务', 'growth_task', '挑战胜利成长任务', '54b804869f1a4500aebe0f8518272256', 'system', 'system', 'challenge_victory', '2016-07-22 15:19:46', '2016-07-22 15:19:46');
