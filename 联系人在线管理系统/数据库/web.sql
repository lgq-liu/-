/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 80025
Source Host           : localhost:3306
Source Database       : web

Target Server Type    : MYSQL
Target Server Version : 80025
File Encoding         : 65001

Date: 2024-05-30 13:07:56
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_contact
-- ----------------------------
DROP TABLE IF EXISTS `t_contact`;
CREATE TABLE `t_contact` (
  `user_id` char(10) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `ct_id` char(10) NOT NULL,
  `ct_name` varchar(10) DEFAULT NULL,
  `ct_ad` varchar(100) DEFAULT NULL,
  `ct_yb` varchar(6) DEFAULT NULL,
  `ct_qq` varchar(11) DEFAULT NULL,
  `ct_wx` varchar(11) DEFAULT NULL,
  `ct_em` varchar(20) DEFAULT NULL,
  `ct_mf` char(2) DEFAULT NULL,
  `ct_birth` varchar(10) DEFAULT NULL,
  `ct_phone` char(11) DEFAULT NULL,
  `ct_delete` int DEFAULT NULL,
  PRIMARY KEY (`ct_id`),
  KEY `t_linkman_user` (`user_id`) USING BTREE,
  CONSTRAINT `t_contact_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`user_id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- ----------------------------
-- Records of t_contact
-- ----------------------------
INSERT INTO `t_contact` VALUES ('10001', '1', '方强', '翻斗花园', '253411', '2038233422', '13243235432', '2038233422@qq.com', '男', '2023-01-27', '13243235432', '1');
INSERT INTO `t_contact` VALUES ('10001', '2', '简微', '苏州护城河旁', '253412', '2038233222', '16523211233', '2038233222@qq.com', '女', '2001-02-20', '13243235432', '0');
INSERT INTO `t_contact` VALUES ('10001', '3', '李全', '竹园', '254311', '2090243312', '16323211233', '2090243312@qq.com', '男', '2023-06-06', '21322132144', '0');
INSERT INTO `t_contact` VALUES ('10001', '4', '曹霁', '翻斗花园', '253412', '2031233422', '13243239431', '2031233422@qq.com', '男', '2006-01-21', '13243235432', '0');
INSERT INTO `t_contact` VALUES ('10001', '5', '常琦', '竹园', '234526', '2091243312', '13843235432', '2091243312@qq.com', '女', '2001-02-28', '21322132144', '0');
INSERT INTO `t_contact` VALUES ('10001', '6', '李涛', '兰园', '234525', '1424213321', '17729382214', '1424213321@qq.com', '男', '2001-02-21', '21322132144', '0');
INSERT INTO `t_contact` VALUES ('10001', '7', '算法', '134', '253833', '2092141241', '12324531243', '2342325324121@qq.com', '男', '2024-05-29', '18632861293', '0');

-- ----------------------------
-- Table structure for t_contact_matter
-- ----------------------------
DROP TABLE IF EXISTS `t_contact_matter`;
CREATE TABLE `t_contact_matter` (
  `ct_id` char(10) DEFAULT NULL,
  `matter_id` varchar(10) NOT NULL,
  `matter_time` datetime(6) DEFAULT NULL,
  `matter` varchar(100) DEFAULT NULL,
  `matter_delete` int DEFAULT NULL,
  PRIMARY KEY (`matter_id`),
  KEY `ct_id` (`ct_id`),
  CONSTRAINT `t_contact_matter_ibfk_1` FOREIGN KEY (`ct_id`) REFERENCES `t_contact` (`ct_id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- ----------------------------
-- Records of t_contact_matter
-- ----------------------------
INSERT INTO `t_contact_matter` VALUES ('1', '1', '2023-06-20 00:00:00.000000', '交作业', '1');
INSERT INTO `t_contact_matter` VALUES ('1', '10', '2023-06-23 00:00:00.000000', '写作业', '1');
INSERT INTO `t_contact_matter` VALUES ('1', '2', '2023-06-21 00:00:00.000000', '一起打乒乓球', '2');
INSERT INTO `t_contact_matter` VALUES ('2', '3', '2023-06-21 00:00:00.000000', '一起吃关东煮', '2');
INSERT INTO `t_contact_matter` VALUES ('1', '4', '2023-06-29 00:00:00.000000', '写暑假作业', '2');
INSERT INTO `t_contact_matter` VALUES (null, '5', '2023-06-22 00:00:00.000000', '一起吃烧烤', '0');
INSERT INTO `t_contact_matter` VALUES ('6', '6', '2023-06-22 00:00:00.000000', '一起吃烧烤', '2');
INSERT INTO `t_contact_matter` VALUES ('5', '7', '2023-06-23 00:00:00.000000', '一起吃烧烤', '0');
INSERT INTO `t_contact_matter` VALUES ('3', '8', '2023-06-29 00:00:00.000000', '交作业', '0');
INSERT INTO `t_contact_matter` VALUES ('1', '9', '2023-06-15 00:00:00.000000', '一起吃烧烤', '0');

-- ----------------------------
-- Table structure for t_contact_picture
-- ----------------------------
DROP TABLE IF EXISTS `t_contact_picture`;
CREATE TABLE `t_contact_picture` (
  `ct_id` char(10) DEFAULT NULL,
  `pic_id` varchar(10) NOT NULL,
  `pic_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY (`pic_id`),
  KEY `ct_id` (`ct_id`),
  CONSTRAINT `t_contact_picture_ibfk_1` FOREIGN KEY (`ct_id`) REFERENCES `t_contact` (`ct_id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- ----------------------------
-- Records of t_contact_picture
-- ----------------------------
INSERT INTO `t_contact_picture` VALUES ('1', '1', 'touxiang5.png');
INSERT INTO `t_contact_picture` VALUES ('2', '2', 'touxiang1.png');
INSERT INTO `t_contact_picture` VALUES ('3', '3', 'touxiang6.png');
INSERT INTO `t_contact_picture` VALUES ('4', '4', 'touxiang6.png');
INSERT INTO `t_contact_picture` VALUES ('5', '5', 'touxiang4.png');
INSERT INTO `t_contact_picture` VALUES ('6', '6', 'default.png');
INSERT INTO `t_contact_picture` VALUES ('7', '7', 'touxiang1.png');

-- ----------------------------
-- Table structure for t_picture
-- ----------------------------
DROP TABLE IF EXISTS `t_picture`;
CREATE TABLE `t_picture` (
  `user_id` char(10) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `pic_id` char(10) NOT NULL,
  `pic_name` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`pic_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `t_picture_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`user_id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- ----------------------------
-- Records of t_picture
-- ----------------------------
INSERT INTO `t_picture` VALUES ('10001', '1', 'touxiang2.png');
INSERT INTO `t_picture` VALUES ('10001', '2', 'touxiang1.png');
INSERT INTO `t_picture` VALUES ('10001', '3', 'touxiang3.png');
INSERT INTO `t_picture` VALUES ('10001', '4', 'touxiang6.png');
INSERT INTO `t_picture` VALUES ('10001', '5', 'touxiang4.png');
INSERT INTO `t_picture` VALUES ('10001', '6', 'default.png');
INSERT INTO `t_picture` VALUES ('10001', '7', 'touxiang1.png');

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `user_id` char(10) NOT NULL,
  `user_password` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES ('10001', '123456');
