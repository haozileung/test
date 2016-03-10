/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50547
Source Host           : localhost:3306
Source Database       : test

Target Server Type    : MYSQL
Target Server Version : 50547
File Encoding         : 65001

Date: 2016-03-10 23:14:08
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for sys_dic_type
-- ----------------------------
DROP TABLE IF EXISTS `sys_dic_type`;
CREATE TABLE `sys_dic_type` (
  `dicTypeId` bigint(20) NOT NULL AUTO_INCREMENT,
  `parentId` bigint(20) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `remarks` varchar(255) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  PRIMARY KEY (`dicTypeId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sys_dictionary
-- ----------------------------
DROP TABLE IF EXISTS `sys_dictionary`;
CREATE TABLE `sys_dictionary` (
  `dictionaryId` bigint(20) NOT NULL AUTO_INCREMENT,
  `dicTypeId` bigint(20) DEFAULT NULL,
  `code` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `remarks` varchar(255) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  PRIMARY KEY (`dictionaryId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='utf8_general_ci';

-- ----------------------------
-- Table structure for sys_login_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_login_log`;
CREATE TABLE `sys_login_log` (
  `loginLogId` bigint(20) NOT NULL AUTO_INCREMENT,
  `userId` bigint(20) DEFAULT NULL,
  `ipAddress` varchar(255) DEFAULT NULL,
  `loginTime` datetime DEFAULT NULL,
  `result` int(11) DEFAULT NULL,
  `browser` varchar(255) DEFAULT NULL,
  `remarks` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`loginLogId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sys_operate_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_operate_log`;
CREATE TABLE `sys_operate_log` (
  `operateLogId` bigint(20) NOT NULL AUTO_INCREMENT,
  `operationId` bigint(20) DEFAULT NULL,
  `params` text,
  `userId` bigint(20) DEFAULT NULL,
  `ipAddress` varchar(255) DEFAULT NULL,
  `remarks` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`operateLogId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
