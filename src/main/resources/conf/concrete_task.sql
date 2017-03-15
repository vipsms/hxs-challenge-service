/*
Navicat MySQL Data Transfer

Source Server         : 192.168.1.12
Source Server Version : 50629
Source Host           : 192.168.1.12:3006
Source Database       : hxs_challenge

Target Server Type    : MYSQL
Target Server Version : 50629
File Encoding         : 65001

Date: 2016-07-22 20:01:00
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `concrete_task`
-- ----------------------------
DROP TABLE IF EXISTS `concrete_task`;
CREATE TABLE `concrete_task` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `uuid` varchar(32) DEFAULT NULL,
  `concrete_task_name` varchar(256) DEFAULT NULL,
  `concrete_task_code` varchar(32) DEFAULT NULL,
  `concrete_task_desc` varchar(512) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8 COMMENT='具体任务';

-- ----------------------------
-- Records of concrete_task
-- ----------------------------
INSERT INTO `concrete_task` VALUES ('1', '80b804869f1a4500aebe0f8518272251', '信息完善任务', 'perfect_information', '信息完善任务', '2016-07-22 14:55:28', '2016-07-22 14:55:31');
INSERT INTO `concrete_task` VALUES ('2', '92b804869f1a4500aebe0f8518272252', '对战任务', 'novice', '对战任务', '2016-07-22 14:55:48', '2016-07-22 14:55:50');
INSERT INTO `concrete_task` VALUES ('3', '32b804869f1a4500aebe0f8518272253', '好友对战任务', 'friend_battle', '好友对战任务', '2016-07-22 14:56:03', '2016-07-22 14:56:06');
INSERT INTO `concrete_task` VALUES ('4', '17b804869f1a4500aebe0f8518272254', '对战胜利任务', 'battle_victory', '对战胜利任务', '2016-07-22 14:56:23', '2016-07-22 14:56:25');
INSERT INTO `concrete_task` VALUES ('5', '63b804869f1a4500aebe0f8518272255', '挑战任务', 'challenge_task', '挑战任务', '2016-07-22 14:56:40', '2016-07-22 14:56:43');
INSERT INTO `concrete_task` VALUES ('6', '54b804869f1a4500aebe0f8518272256', '挑战胜利任务', 'challenge_victory', '挑战胜利任务', '2016-07-22 14:56:58', '2016-07-22 14:57:02');
INSERT INTO `concrete_task` VALUES ('7', '65b804869f1a4500aebe0f8518272257', '对战战报分享任务', 'battle_stand_share', '对战战报分享任务', '2016-07-22 14:57:15', '2016-07-22 14:57:17');
INSERT INTO `concrete_task` VALUES ('8', '87b804869f1a4500aebe0f8518272258', '挑战战报分享任务', 'challenge_stand_share', '挑战战报分享任务', '2016-07-22 14:57:31', '2016-07-22 14:57:35');
INSERT INTO `concrete_task` VALUES ('9', '72b804869f1a4500aebe0f8518272259', '排行榜分享任务', 'rank_share', '排行榜分享任务', '2016-07-22 14:57:51', '2016-07-22 14:57:54');
INSERT INTO `concrete_task` VALUES ('10', '34b804869f1a4500aebe0f8518272210', '好友添加任务', 'friend_add', '好友添加任务', '2016-07-22 14:58:07', '2016-07-22 14:58:09');
INSERT INTO `concrete_task` VALUES ('11', '56b804869f1a4500aebe0f8518272211', '学习视频任务', 'learn_video', '学习视频任务', '2016-07-22 14:58:22', '2016-07-22 14:58:25');
INSERT INTO `concrete_task` VALUES ('12', '23b804869f1a4500aebe0f8518272212', '学习试题任务', 'learn_question', '学习试题任务', '2016-07-22 14:58:37', '2016-07-22 14:58:39');
INSERT INTO `concrete_task` VALUES ('13', '98b804869f1a4500aebe0f8518272213', '战斗力测试', 'fighting_test', '战斗力测试', '2016-07-22 15:11:00', '2016-07-22 15:11:04');
INSERT INTO `concrete_task` VALUES ('14', '76b804869f1a4500aebe0f8518272214', '购买体力', 'buy_physical_power', '购买体力', '2016-07-22 15:11:17', '2016-07-22 15:11:20');
INSERT INTO `concrete_task` VALUES ('15', 'etb804869f1a4500aebe0f85182722op', '战报任务分享', 'stand_share', '战报任务分享', '2016-07-22 18:58:31', '2016-07-22 18:58:34');
