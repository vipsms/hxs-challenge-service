/*
Navicat MySQL Data Transfer

Source Server         : 192.168.1.12
Source Server Version : 50629
Source Host           : 192.168.1.12:3006
Source Database       : hxs_challenge

Target Server Type    : MYSQL
Target Server Version : 50629
File Encoding         : 65001

Date: 2016-07-22 20:01:16
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `task_rule`
-- ----------------------------
DROP TABLE IF EXISTS `task_rule`;
CREATE TABLE `task_rule` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `uuid` varchar(32) DEFAULT NULL,
  `concrete_uuid` varchar(32) DEFAULT NULL,
  `reward_point` double DEFAULT NULL,
  `completion_times` double DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `vip_reward_point` double DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=62 DEFAULT CHARSET=utf8 COMMENT='任务规则';

-- ----------------------------
-- Records of task_rule
-- ----------------------------
INSERT INTO `task_rule` VALUES ('1', '563bb884616346ebb193f5d82961b156', '3f10903cdd1646c5934b35a82939640e', '500', '1', '2016-07-22 15:37:47', '2016-07-22 15:37:47', '500');
INSERT INTO `task_rule` VALUES ('2', 'ty3bb884616346ebb193f5d82961b178', '5g10903cdd1646c5934b35a82939640f', '10', '1', '2016-07-22 15:37:47', '2016-07-22 15:37:47', '10');
INSERT INTO `task_rule` VALUES ('3', '893bb884616346ebb193f5d82961b178', '2f10903cdd1646c5934b35a82939640d', '20', '1', '2016-07-22 15:37:47', '2016-07-22 15:37:47', '20');
INSERT INTO `task_rule` VALUES ('4', '903bb884616346ebb193f5d82961b198', '3810903cdd1646c5934b35a82939640o', '50', '1', '2016-07-22 15:37:47', '2016-07-22 15:37:47', '50');
INSERT INTO `task_rule` VALUES ('5', '793bb884616346ebb193f5d82961b178', '3h10903cdd1646c5934b35a82939640q', '50', '1', '2016-07-22 15:37:47', '2016-07-22 15:37:47', '50');
INSERT INTO `task_rule` VALUES ('6', '463bb884616346ebb193f5d82961b189', '5h10903cdd1646c5934b35a8293964we', '25', '1', '2016-07-22 15:37:47', '2016-07-22 15:37:47', '25');
INSERT INTO `task_rule` VALUES ('7', '373bb884616346ebb193f5d82961b152', '9o10903cdd1646c5934b35a8293964ty', '200', '1', '2016-07-22 15:37:47', '2016-07-22 15:37:47', '200');
INSERT INTO `task_rule` VALUES ('8', '573bb884616346ebb193f5d82961b1ed', '1h10903cdd1646c5934b35a8293964gh', '500', '1', '2016-07-22 15:37:47', '2016-07-22 15:37:47', '500');
INSERT INTO `task_rule` VALUES ('9', '233bb884616346ebb193f5d82961b1rs', '3310903cdd1646c5934b35a8293964df', '10', '1', '2016-07-22 15:37:47', '2016-07-22 15:37:47', '10');
INSERT INTO `task_rule` VALUES ('10', '893bb884616346ebb193f5d82961b1ce', '2a10903cdd1646c5934b35a8293964sd', '10', '1', '2016-07-22 15:37:47', '2016-07-22 15:37:47', '10');
INSERT INTO `task_rule` VALUES ('11', 'mb3bb884616346ebb193f5d82961b147', '5g10903cdd1646c5934b35a82939640f', '50', '5', '2016-07-22 18:53:43', '2016-07-22 18:53:45', '50');
INSERT INTO `task_rule` VALUES ('12', 'mb3bb884616346ebb193f5d82961r5y6', '4510903cdd1646c5934b35a829396400', '10', '1', '2016-07-22 18:53:43', '2016-07-22 18:53:43', '20');
INSERT INTO `task_rule` VALUES ('13', 'mb3bb884616346ebb193f5d82961po07', '45rt903cdd1646c5934b35a8293964qq', '20', '1', '2016-07-22 18:53:43', '2016-07-22 18:53:43', '20');
INSERT INTO `task_rule` VALUES ('14', 'mb3bb884616346ebb193f5d82961rt45', '4uyg903cdd1646c5934b35a82939643r', '10', '1', '2016-07-22 18:53:43', '2016-07-22 18:53:43', '20');
INSERT INTO `task_rule` VALUES ('15', 'mb3bb884616346ebb193f5d8296109l6', '2rft903cdd1646c5934b35a8293964ee', '15', '1', '2016-07-22 18:53:43', '2016-07-22 18:53:43', '30');
INSERT INTO `task_rule` VALUES ('16', 'mb3bb884616346ebb193f5d8296123r5', '56uj903cdd1646c5934b35a8293964ff', '25', '1', '2016-07-22 18:53:43', '2016-07-22 18:53:43', '40');
INSERT INTO `task_rule` VALUES ('17', 'mb3bb884616346ebb193f5d829612r6y', '6yhd903cdd1646c5934b35a829396444', '50', '1', '2016-07-22 18:53:43', '2016-07-22 18:53:43', '50');
INSERT INTO `task_rule` VALUES ('18', 'mb3bb884616346ebb193f5d829618io0', 'thfg903cdd1646c5934b35a82939644t', '50', '1', '2016-07-22 18:53:43', '2016-07-22 18:53:43', '50');
INSERT INTO `task_rule` VALUES ('19', 'mb3bb884616346ebb193f5d829610089', 'er56903cdd1646c5934b35a82939642e', '50', '1', '2016-07-22 18:53:43', '2016-07-22 18:53:43', '70');
INSERT INTO `task_rule` VALUES ('20', 'mb3bb884616346ebb193f5d8296156u7', '45th903cdd1646c5934b35a82939643e', '10', '1', '2016-07-22 18:53:43', '2016-07-22 18:53:43', '10');
INSERT INTO `task_rule` VALUES ('21', 'mb3bb884616346ebb193f5d8296123e7', 'sf45903cdd1646c5934b35a82939645g', '10', '1', '2016-07-22 18:53:43', '2016-07-22 18:53:43', '10');
INSERT INTO `task_rule` VALUES ('22', 'mb3bb884616346ebb193f5d82961jki6', '56hg903cdd1646c5934b35a8293964hj', '50', '10', '2016-07-22 18:53:43', '2016-07-22 18:53:43', '55');
INSERT INTO `task_rule` VALUES ('23', 'mb3bb884616346ebb193f5d82961123e', '56hg903cdd1646c5934b35a8293964hj', '100', '50', '2016-07-22 18:53:43', '2016-07-22 18:53:43', '110');
INSERT INTO `task_rule` VALUES ('24', 'mb3bb884616346ebb193f5d8296144r3', '56hg903cdd1646c5934b35a8293964hj', '220', '100', '2016-07-22 18:53:43', '2016-07-22 18:53:43', '220');
INSERT INTO `task_rule` VALUES ('25', 'mb3bb884616346ebb193f5d8296178u5', '56hg903cdd1646c5934b35a8293964hj', '500', '500', '2016-07-22 18:53:43', '2016-07-22 18:53:43', '550');
INSERT INTO `task_rule` VALUES ('26', 'mb3bb884616346ebb193f5d8296123r5', '56hg903cdd1646c5934b35a8293964hj', '1000', '1000', '2016-07-22 18:53:43', '2016-07-22 18:53:43', '1100');
INSERT INTO `task_rule` VALUES ('27', 'mb3bb884616346ebb193f5d829616y7u', 'l009903cdd1646c5934b35a8293964ui', '75', '10', '2016-07-22 19:19:11', '2016-07-22 18:53:43', '80');
INSERT INTO `task_rule` VALUES ('28', 'mb3bb884616346ebb193f5d8296123r5', 'l009903cdd1646c5934b35a8293964ui', '150', '50', '2016-07-22 18:53:43', '2016-07-22 18:53:43', '165');
INSERT INTO `task_rule` VALUES ('29', 'mb3bb884616346ebb193f5d829617ui8', 'l009903cdd1646c5934b35a8293964ui', '300', '100', '2016-07-22 18:53:43', '2016-07-22 18:53:43', '330');
INSERT INTO `task_rule` VALUES ('30', 'mb3bb884616346ebb193f5d8296112ea', 'l009903cdd1646c5934b35a8293964ui', '750', '500', '2016-07-22 18:53:43', '2016-07-22 18:53:43', '825');
INSERT INTO `task_rule` VALUES ('31', 'mb3bb884616346ebb193f5d82961aaqs', 'l009903cdd1646c5934b35a8293964ui', '1500', '1000', '2016-07-22 18:53:43', '2016-07-22 18:53:43', '1650');
INSERT INTO `task_rule` VALUES ('32', 'mb3bb884616346ebb193f5d82961uj7y', '45gd903cdd1646c5934b35a8293964dg', '50', '10', '2016-07-22 18:53:43', '2016-07-22 18:53:43', '55');
INSERT INTO `task_rule` VALUES ('33', 'mb3bb884616346ebb193f5d829616u7i', '45gd903cdd1646c5934b35a8293964dg', '100', '50', '2016-07-22 18:53:43', '2016-07-22 18:53:43', '110');
INSERT INTO `task_rule` VALUES ('34', 'mb3bb884616346ebb193f5d8296156yu', '45gd903cdd1646c5934b35a8293964dg', '200', '100', '2016-07-22 18:53:43', '2016-07-22 18:53:43', '220');
INSERT INTO `task_rule` VALUES ('35', 'mb3bb884616346ebb193f5d829618ikk', '45gd903cdd1646c5934b35a8293964dg', '500', '500', '2016-07-22 18:53:43', '2016-07-22 18:53:43', '550');
INSERT INTO `task_rule` VALUES ('36', 'mb3bb884616346ebb193f5d82961ik44', '45gd903cdd1646c5934b35a8293964dg', '1000', '1000', '2016-07-22 18:53:43', '2016-07-22 18:53:43', '1100');
INSERT INTO `task_rule` VALUES ('37', 'mb3bb884616346ebb193f5d829615ty6', 'peop903cdd1646c5934b35a829390opl', '75', '10', '2016-07-22 18:53:43', '2016-07-22 18:53:43', '80');
INSERT INTO `task_rule` VALUES ('38', 'mb3bb884616346ebb193f5d829617u09', 'peop903cdd1646c5934b35a829390opl', '150', '50', '2016-07-22 18:53:43', '2016-07-22 18:53:43', '165');
INSERT INTO `task_rule` VALUES ('39', 'mb3bb884616346ebb193f5d82961dfca', 'peop903cdd1646c5934b35a829390opl', '300', '100', '2016-07-22 18:53:43', '2016-07-22 18:53:43', '330');
INSERT INTO `task_rule` VALUES ('40', 'mb3bb884616346ebb193f5d8296122qw', 'peop903cdd1646c5934b35a829390opl', '750', '500', '2016-07-22 18:53:43', '2016-07-22 18:53:43', '825');
INSERT INTO `task_rule` VALUES ('41', 'mb3bb884616346ebb193f5d829611123', 'peop903cdd1646c5934b35a829390opl', '1500', '1000', '2016-07-22 18:53:43', '2016-07-22 18:53:43', '1650');
INSERT INTO `task_rule` VALUES ('42', 'mb3bb884616346ebb193f5d829614rt2', 'fg76903cdd1646c5934b35a8293964fg', '50', '10', '2016-07-22 18:53:43', '2016-07-22 18:53:43', '55');
INSERT INTO `task_rule` VALUES ('43', 'mb3bb884616346ebb193f5d82961bik9', 'fg76903cdd1646c5934b35a8293964fg', '100', '50', '2016-07-22 18:53:43', '2016-07-22 18:53:43', '110');
INSERT INTO `task_rule` VALUES ('44', 'mb3bb884616346ebb193f5d82961lo00', 'fg76903cdd1646c5934b35a8293964fg', '200', '100', '2016-07-22 18:53:43', '2016-07-22 18:53:43', '220');
INSERT INTO `task_rule` VALUES ('45', 'mb3bb884616346ebb193f5d82961kkkk', 'fg76903cdd1646c5934b35a8293964fg', '500', '500', '2016-07-22 18:53:43', '2016-07-22 18:53:43', '550');
INSERT INTO `task_rule` VALUES ('46', 'mb3bb884616346ebb193f5d82961uuuu', 'fg76903cdd1646c5934b35a8293964fg', '1000', '1000', '2016-07-22 18:53:43', '2016-07-22 18:53:43', '1100');
INSERT INTO `task_rule` VALUES ('47', 'mb3bb884616346ebb193f5d829616yt6', '23ef903cdd1646c5934b35a8293964dg', '50', '10', '2016-07-22 18:53:43', '2016-07-22 18:53:43', '55');
INSERT INTO `task_rule` VALUES ('48', 'mb3bb884616346ebb193f5d8296177u6', '23ef903cdd1646c5934b35a8293964dg', '100', '50', '2016-07-22 18:53:43', '2016-07-22 18:53:43', '110');
INSERT INTO `task_rule` VALUES ('49', 'mb3bb884616346ebb193f5d8296188io', '23ef903cdd1646c5934b35a8293964dg', '200', '100', '2016-07-22 18:53:43', '2016-07-22 18:53:43', '220');
INSERT INTO `task_rule` VALUES ('50', 'mb3bb884616346ebb193f5d8296199o9', '23ef903cdd1646c5934b35a8293964dg', '500', '500', '2016-07-22 18:53:43', '2016-07-22 18:53:43', '550');
INSERT INTO `task_rule` VALUES ('51', 'mb3bb884616346ebb193f5d829610998', '23ef903cdd1646c5934b35a8293964dg', '1000', '1000', '2016-07-22 18:53:43', '2016-07-22 18:53:43', '1100');
INSERT INTO `task_rule` VALUES ('52', 'mb3bb884616346ebb193f5d82961hy67', 'fg09903cdd1646c5934b35a8293964gf', '50', '10', '2016-07-22 18:53:43', '2016-07-22 18:53:43', '55');
INSERT INTO `task_rule` VALUES ('53', 'mb3bb884616346ebb193f5d829615t46', 'fg09903cdd1646c5934b35a8293964gf', '100', '50', '2016-07-22 18:53:43', '2016-07-22 18:53:43', '110');
INSERT INTO `task_rule` VALUES ('54', 'mb3bb884616346ebb193f5d829616yu6', 'fg09903cdd1646c5934b35a8293964gf', '200', '100', '2016-07-22 18:53:43', '2016-07-22 18:53:43', '220');
INSERT INTO `task_rule` VALUES ('55', 'mb3bb884616346ebb193f5d829616y65', 'fg09903cdd1646c5934b35a8293964gf', '500', '500', '2016-07-22 18:53:43', '2016-07-22 18:53:43', '550');
INSERT INTO `task_rule` VALUES ('56', 'mb3bb884616346ebb193f5d829616y7u', 'fg09903cdd1646c5934b35a8293964gf', '1000', '1000', '2016-07-22 18:53:43', '2016-07-22 18:53:43', '1100');
INSERT INTO `task_rule` VALUES ('57', 'mb3bb884616346ebb193f5d82961b5g5', 'weop903cdd1646c5934b35a82939645t', '50', '10', '2016-07-22 18:53:43', '2016-07-22 18:53:43', '55');
INSERT INTO `task_rule` VALUES ('58', 'mb3bb884616346ebb193f5d829616y54', 'weop903cdd1646c5934b35a82939645t', '100', '50', '2016-07-22 18:53:43', '2016-07-22 18:53:43', '110');
INSERT INTO `task_rule` VALUES ('59', 'mb3bb884616346ebb193f5d829615543', 'weop903cdd1646c5934b35a82939645t', '200', '100', '2016-07-22 18:53:43', '2016-07-22 18:53:43', '220');
INSERT INTO `task_rule` VALUES ('60', 'mb3bb884616346ebb193f5d82961uy55', 'weop903cdd1646c5934b35a82939645t', '500', '500', '2016-07-22 18:53:43', '2016-07-22 18:53:43', '550');
INSERT INTO `task_rule` VALUES ('61', 'mb3bb884616346ebb193f5d82961by67', 'weop903cdd1646c5934b35a82939645t', '1000', '1000', '2016-07-22 18:53:43', '2016-07-22 18:53:43', '1100');