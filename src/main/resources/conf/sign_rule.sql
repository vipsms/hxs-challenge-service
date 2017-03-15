/*
Navicat MySQL Data Transfer

Source Server         : 192.168.1.12
Source Server Version : 50629
Source Host           : 192.168.1.12:3006
Source Database       : hxs_challenge

Target Server Type    : MYSQL
Target Server Version : 50629
File Encoding         : 65001

Date: 2016-07-22 11:11:21
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `sign_rule`
-- ----------------------------
DROP TABLE IF EXISTS `sign_rule`;
CREATE TABLE `sign_rule` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `uuid` varchar(32) NOT NULL,
  `sign_times` double NOT NULL,
  `reward_points` double NOT NULL,
  `create_date` datetime DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `vip_reward_points` double(10,0) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_4we9qpfgygfk6nxpyn3yu3r5f` (`sign_times`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sign_rule
-- ----------------------------
INSERT INTO `sign_rule` (id,uuid,sign_times,reward_points,create_date,update_date,vip_reward_points) VALUES ('1', 'b0428dc1b57f452b8558eaf4c86ca410', '1', '5', '2016-07-21 15:04:20', '2016-07-21 15:04:23', '15');
INSERT INTO `sign_rule` (id,uuid,sign_times,reward_points,create_date,update_date,vip_reward_points) VALUES ('2', 'b0428dc1b57f452b8558eaf4c86ca411', '2', '10', '2016-07-21 15:04:58', '2016-07-21 15:05:00', '20');
INSERT INTO `sign_rule` (id,uuid,sign_times,reward_points,create_date,update_date,vip_reward_points) VALUES ('3', 'b0428dc1b57f452b8558eaf4c86ca412', '3', '15', '2016-07-21 15:07:41', '2016-07-21 15:07:45', '25');
INSERT INTO `sign_rule` (id,uuid,sign_times,reward_points,create_date,update_date,vip_reward_points) VALUES ('4', 'b0428dc1b57f452b8558eaf4c86ca413', '4', '20', '2016-07-21 15:07:48', '2016-07-21 15:07:51', '30');
INSERT INTO `sign_rule` (id,uuid,sign_times,reward_points,create_date,update_date,vip_reward_points) VALUES ('5', 'b0428dc1b57f452b8558eaf4c86ca414', '5', '25', '2016-07-21 15:07:54', '2016-07-21 15:07:57', '35');
INSERT INTO `sign_rule` (id,uuid,sign_times,reward_points,create_date,update_date,vip_reward_points) VALUES ('6', 'b0428dc1b57f452b8558eaf4c86ca415', '6', '30', '2016-07-21 15:07:59', '2016-07-21 15:08:02', '40');
INSERT INTO `sign_rule` (id,uuid,sign_times,reward_points,create_date,update_date,vip_reward_points) VALUES ('8', 'b0428dc1b57f452b8558eaf4c86ca416', '7', '35', '2016-07-21 15:08:04', '2016-07-21 15:08:07', '45');
