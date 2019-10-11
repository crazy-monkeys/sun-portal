/*
 Navicat Premium Data Transfer

 Source Server         : portal-dev
 Source Server Type    : MySQL
 Source Server Version : 50726
 Source Host           : 47.100.106.186
 Source Database       : portal

 Target Server Type    : MySQL
 Target Server Version : 50726
 File Encoding         : utf-8

 Date: 07/07/2019 13:15:19 PM
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `t_resource`
-- ----------------------------
DROP TABLE IF EXISTS `t_resource`;
CREATE TABLE `t_resource` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `active` smallint(6) NOT NULL,
  `create_time` datetime NOT NULL,
  `create_user_id` int(11) NOT NULL,
  `icon_class` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `parent_id` int(11) NOT NULL,
  `resource_desc` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `resource_name` varchar(255) COLLATE utf8_bin NOT NULL,
  `resource_order` int(11) DEFAULT NULL,
  `resource_type` varchar(255) COLLATE utf8_bin NOT NULL COMMENT '1.菜单  2.Api',
  `resource_url` varchar(255) COLLATE utf8_bin NOT NULL,
  `update_time` datetime DEFAULT NULL,
  `update_user_id` int(11) DEFAULT NULL,
  `permission_prefix_url` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
--  Records of `t_resource`
-- ----------------------------
BEGIN;
INSERT INTO `t_resource` VALUES
('1', '1', '2019-06-09 23:08:07', '1', '#', '0', '', '系统管理', '1', '1', '#', '2019-06-09 23:10:00', '1', '/'),
('2', '1', '2019-06-11 00:34:56', '1', '#', '1', '用户管理', '用户管理', '1', '1', '/view/system/users.html', '2019-06-11 00:35:29', '1', '/user/**'),
('3', '1', '2019-06-13 20:16:10', '1', '#', '1', '权限管理', '权限管理', '1', '1', '/view/system/authority.html', '2019-06-13 20:17:08', '1', '/permission/**');
COMMIT;

-- ----------------------------
--  Table structure for `t_role`
-- ----------------------------
DROP TABLE IF EXISTS `t_role`;
CREATE TABLE `t_role` (
  `id` int(11) NOT NULL,
  `active` smallint(6) NOT NULL,
  `create_time` datetime NOT NULL,
  `create_user_id` int(11) NOT NULL,
  `role_desc` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `role_name` varchar(255) COLLATE utf8_bin NOT NULL,
  `update_time` datetime DEFAULT NULL,
  `update_user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
--  Records of `t_role`
-- ----------------------------
BEGIN;
INSERT INTO `t_role` VALUES ('1', '1', '2019-04-20 05:11:45', '1', null, 'ADMIN', '2019-06-23 05:39:59', '1');
INSERT INTO `t_role` VALUES ('2', '1', '2019-04-20 05:11:45', '1', null, 'BASIC_ROLE', '2019-06-23 05:39:59', '1');
COMMIT;

-- ----------------------------
--  Table structure for `t_role_resource`
-- ----------------------------
DROP TABLE IF EXISTS `t_role_resource`;
CREATE TABLE `t_role_resource` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` int(11) NOT NULL,
  `resource_id` int(11) NOT NULL,
  `create_time` datetime NOT NULL,
  `create_id` int(11) NOT NULL,
  `update_time` datetime DEFAULT NULL,
  `update_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `t_role_resource`
-- ----------------------------
BEGIN;
INSERT INTO `t_role_resource` VALUES ('1', '1', '1', '2019-07-07 13:13:10', '1', null, null), ('2', '1', '2', '2019-07-07 13:13:10', '1', null, null), ('3', '1', '3', '2019-07-07 13:13:30', '1', null, null);
COMMIT;

-- ----------------------------
--  Table structure for `t_user`
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `active` smallint(6) NOT NULL,
  `country` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `create_time` datetime NOT NULL,
  `create_user_id` int(11) NOT NULL,
  `email` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `first_name` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `last_login_time` datetime DEFAULT NULL,
  `last_name` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `login_name` varchar(255) COLLATE utf8_bin NOT NULL,
  `login_pwd` varchar(255) COLLATE utf8_bin NOT NULL,
  `phone` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `pwd_invalid_time` datetime NOT NULL,
  `real_name` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `reg_time` datetime NOT NULL,
  `update_time` datetime DEFAULT NULL,
  `update_user_id` int(11) DEFAULT NULL,
  `user_status` int(11) DEFAULT NULL,
  `user_type` int(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
--  Records of `t_user`
-- ----------------------------
BEGIN;
INSERT INTO `t_user` VALUES ('1', '1', null, '2019-04-20 13:58:14', '1', null, null, '2019-07-05 15:07:43', null, 'admin', '{bcrypt}$2a$10$HA8c7xXfB/flekytcElwy.AEaqvbzvR4mZvEGEtbwJ7Yo8y.WN.t6', null, '2020-04-19 13:58:14', 'admin', '2019-04-20 13:58:14', null, null, '1', '0');
COMMIT;

-- ----------------------------
--  Table structure for `t_user_role`
-- ----------------------------
DROP TABLE IF EXISTS `t_user_role`;
CREATE TABLE `t_user_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL,
  `create_time` datetime DEFAULT NULL,
  `create_id` int(11) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `update_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `t_user_role`
-- ----------------------------
BEGIN;
INSERT INTO `t_user_role` VALUES ('1', '1', '1', '2019-04-20 13:58:14', '1', null, null);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
