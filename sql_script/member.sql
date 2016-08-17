/*
Navicat MySQL Data Transfer

Source Server         : member
Source Server Version : 50625
Source Host           : 10.10.2.200:3306
Source Database       : member

Target Server Type    : MYSQL
Target Server Version : 50625
File Encoding         : 65001

Date: 2015-12-06 16:08:26
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `t_card_account`
-- ----------------------------
DROP TABLE IF EXISTS `t_card_account`;
CREATE TABLE `t_card_account` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '会员卡账户ID',
  `card_id` int(11) DEFAULT NULL COMMENT '会员卡ID',
  `card_no` varchar(255) DEFAULT NULL COMMENT '会员卡卡号',
  `point` decimal(8,2) DEFAULT NULL COMMENT '积分',
  `present_point` decimal(8,2) DEFAULT NULL COMMENT '赠送积分',
  `value` decimal(8,2) DEFAULT NULL COMMENT '储值',
  `actual_value` decimal(8,2) DEFAULT NULL COMMENT '实际储值',
  `present_value` decimal(8,2) DEFAULT NULL COMMENT '赠送储值',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8 COMMENT='会员卡账户表';

-- ----------------------------
-- Records of t_card_account
-- ----------------------------
INSERT INTO `t_card_account` VALUES ('20', '0', '15', '0.00', '0.00', '0.00', '0.00', '0.00');
INSERT INTO `t_card_account` VALUES ('21', '0', '16', '0.00', '0.00', '0.00', '0.00', '0.00');
INSERT INTO `t_card_account` VALUES ('22', '0', '17', '0.00', '0.00', '0.00', '0.00', '0.00');
INSERT INTO `t_card_account` VALUES ('23', '0', '18', '0.00', '0.00', '0.00', '0.00', '0.00');
INSERT INTO `t_card_account` VALUES ('24', '0', '19', '0.00', '0.00', '0.00', '0.00', '0.00');
INSERT INTO `t_card_account` VALUES ('25', '0', '20', '0.00', '0.00', '0.00', '0.00', '0.00');
INSERT INTO `t_card_account` VALUES ('26', '0', '21', '0.00', '0.00', '0.00', '0.00', '0.00');
INSERT INTO `t_card_account` VALUES ('27', '0', '22', '0.00', '0.00', '0.00', '0.00', '0.00');
INSERT INTO `t_card_account` VALUES ('28', '0', '23', '0.00', '0.00', '0.00', '0.00', '0.00');
INSERT INTO `t_card_account` VALUES ('29', '0', '24', '0.00', '0.00', '0.00', '0.00', '0.00');
INSERT INTO `t_card_account` VALUES ('30', '0', '25', '0.00', '0.00', '0.00', '0.00', '0.00');
INSERT INTO `t_card_account` VALUES ('31', '0', '26', '0.00', '0.00', '0.00', '0.00', '0.00');
INSERT INTO `t_card_account` VALUES ('32', '0', '27', '0.00', '0.00', '0.00', '0.00', '0.00');
INSERT INTO `t_card_account` VALUES ('33', '0', '10001300000000', '0.00', '0.00', '0.00', '0.00', '0.00');
INSERT INTO `t_card_account` VALUES ('34', '0', '28', '0.00', '0.00', '0.00', '0.00', '0.00');
INSERT INTO `t_card_account` VALUES ('35', '0', '29', '0.00', '0.00', '0.00', '0.00', '0.00');
INSERT INTO `t_card_account` VALUES ('36', '0', '30', '0.00', '0.00', '0.00', '0.00', '0.00');
INSERT INTO `t_card_account` VALUES ('37', '0', '100013000001', '0.00', '0.00', '0.00', '0.00', '0.00');
INSERT INTO `t_card_account` VALUES ('38', '0', '100013000002', '0.00', '0.00', '0.00', '0.00', '0.00');
INSERT INTO `t_card_account` VALUES ('39', '0', '100013000003', '0.00', '0.00', '0.00', '0.00', '0.00');
INSERT INTO `t_card_account` VALUES ('40', '0', '100013000004', '10.20', '0.00', '300.00', '300.00', '0.00');
INSERT INTO `t_card_account` VALUES ('41', '0', '100013000005', '0.00', '0.00', '0.00', '0.00', '0.00');
INSERT INTO `t_card_account` VALUES ('42', '0', '100013000006', '0.00', '0.00', '0.00', '0.00', '0.00');
INSERT INTO `t_card_account` VALUES ('43', '0', '100013000007', '0.00', '0.00', '0.00', '0.00', '0.00');
INSERT INTO `t_card_account` VALUES ('44', '0', '100013000008', '0.00', '0.00', '0.00', '0.00', '0.00');
INSERT INTO `t_card_account` VALUES ('45', '0', '100013000009', '0.00', '0.00', '0.00', '0.00', '0.00');
INSERT INTO `t_card_account` VALUES ('46', '0', '100013000010', '0.00', '0.00', '0.00', '0.00', '0.00');
INSERT INTO `t_card_account` VALUES ('47', '0', '100013000011', '0.00', '0.00', '0.00', '0.00', '0.00');
INSERT INTO `t_card_account` VALUES ('48', '0', '100013000012', '152.30', '0.00', '9133.00', '9133.00', '0.00');
INSERT INTO `t_card_account` VALUES ('49', '0', '100013000013', '0.00', '0.00', '0.00', '0.00', '0.00');
INSERT INTO `t_card_account` VALUES ('50', '0', '100013000014', '0.00', '0.00', '0.00', '0.00', '0.00');
INSERT INTO `t_card_account` VALUES ('51', '0', '100013000015', '18.10', '0.00', '9819.00', '9819.00', '0.00');
INSERT INTO `t_card_account` VALUES ('52', '0', '100013000016', '0.00', '0.00', '0.00', '0.00', '0.00');
INSERT INTO `t_card_account` VALUES ('53', '0', '100013000017', '0.00', '0.00', '0.00', '0.00', '0.00');
INSERT INTO `t_card_account` VALUES ('54', '0', '100013000018', '0.00', '0.00', '0.00', '0.00', '0.00');
INSERT INTO `t_card_account` VALUES ('55', '0', '100013000019', '0.00', '0.00', '0.00', '0.00', '0.00');
INSERT INTO `t_card_account` VALUES ('56', '0', '100013000020', '0.00', '0.00', '0.00', '0.00', '0.00');
INSERT INTO `t_card_account` VALUES ('57', '0', '100013000021', '0.00', '0.00', '0.00', '0.00', '0.00');
INSERT INTO `t_card_account` VALUES ('58', '0', '100013000022', '0.00', '0.00', '0.00', '0.00', '0.00');
INSERT INTO `t_card_account` VALUES ('59', '0', '100013000023', '0.00', '0.00', '10000.00', '10000.00', '0.00');
INSERT INTO `t_card_account` VALUES ('60', '0', '100013000024', '0.00', '0.00', '0.00', '0.00', '0.00');
INSERT INTO `t_card_account` VALUES ('61', '0', '100013000025', '0.00', '0.00', '0.00', '0.00', '0.00');
INSERT INTO `t_card_account` VALUES ('62', '0', '100013000026', '0.00', '0.00', '0.00', '0.00', '0.00');
INSERT INTO `t_card_account` VALUES ('63', '0', '100013000027', '0.00', '0.00', '0.00', '0.00', '0.00');
INSERT INTO `t_card_account` VALUES ('64', '0', '100013000028', '0.00', '0.00', '0.00', '0.00', '0.00');
INSERT INTO `t_card_account` VALUES ('65', '0', '100013000029', '0.00', '0.00', '0.00', '0.00', '0.00');
INSERT INTO `t_card_account` VALUES ('66', '0', '100013000030', '0.00', '0.00', '0.00', '0.00', '0.00');
INSERT INTO `t_card_account` VALUES ('67', '0', '100013000031', '0.00', '0.00', '40000.00', '40000.00', '0.00');
INSERT INTO `t_card_account` VALUES ('68', '0', '100013000032', '0.00', '0.00', '0.00', '0.00', '0.00');
INSERT INTO `t_card_account` VALUES ('69', '0', '100013000033', '0.00', '0.00', '0.00', '0.00', '0.00');
INSERT INTO `t_card_account` VALUES ('70', '0', '100013000034', '0.00', '0.00', '0.00', '0.00', '0.00');
INSERT INTO `t_card_account` VALUES ('71', '0', '100013000035', '0.00', '0.00', '0.00', '0.00', '0.00');
INSERT INTO `t_card_account` VALUES ('72', '0', '100013000036', '-0.20', '0.00', '50002.00', '50002.00', '0.00');
INSERT INTO `t_card_account` VALUES ('73', '0', '100013000037', '0.00', '0.00', '0.00', '0.00', '0.00');
INSERT INTO `t_card_account` VALUES ('74', '0', '100013000038', '0.00', '0.00', '0.00', '0.00', '0.00');
INSERT INTO `t_card_account` VALUES ('75', '0', '100013000039', '0.00', '0.00', '0.00', '0.00', '0.00');
INSERT INTO `t_card_account` VALUES ('76', '0', '100013000040', '0.00', '0.00', '50000.00', '50000.00', '0.00');
INSERT INTO `t_card_account` VALUES ('77', '0', '100013000041', '0.00', '0.00', '0.00', '0.00', '0.00');
INSERT INTO `t_card_account` VALUES ('78', '0', '100013000042', '0.00', '0.00', '0.00', '0.00', '0.00');
INSERT INTO `t_card_account` VALUES ('79', '0', '100013000043', '0.00', '0.00', '0.00', '0.00', '0.00');
INSERT INTO `t_card_account` VALUES ('80', '0', '100013000044', '0.00', '0.00', '0.00', '0.00', '0.00');
INSERT INTO `t_card_account` VALUES ('81', '0', '100013000045', '0.00', '0.00', '0.00', '0.00', '0.00');
INSERT INTO `t_card_account` VALUES ('82', '0', '100013000046', '0.00', '0.00', '0.00', '0.00', '0.00');
INSERT INTO `t_card_account` VALUES ('83', '0', '100013000047', '0.00', '0.00', '0.00', '0.00', '0.00');
INSERT INTO `t_card_account` VALUES ('84', '0', '100013000048', '0.00', '0.00', '0.00', '0.00', '0.00');
INSERT INTO `t_card_account` VALUES ('85', '0', '100013000049', '0.00', '0.00', '0.00', '0.00', '0.00');
INSERT INTO `t_card_account` VALUES ('86', '0', '100013000050', '0.00', '0.00', '0.00', '0.00', '0.00');
INSERT INTO `t_card_account` VALUES ('87', '0', '100013000051', '0.00', '0.00', '0.00', '0.00', '0.00');
INSERT INTO `t_card_account` VALUES ('88', '0', '100013000052', '0.00', '0.00', '0.00', '0.00', '0.00');
INSERT INTO `t_card_account` VALUES ('89', '0', '100013000053', '0.00', '0.00', '0.00', '0.00', '0.00');
INSERT INTO `t_card_account` VALUES ('90', '0', '100013000054', '0.00', '0.00', '0.00', '0.00', '0.00');
INSERT INTO `t_card_account` VALUES ('91', '0', '100013000055', '0.00', '0.00', '0.00', '0.00', '0.00');
INSERT INTO `t_card_account` VALUES ('92', '0', '100013000056', '0.00', '0.00', '0.00', '0.00', '0.00');
INSERT INTO `t_card_account` VALUES ('93', '0', '100013000057', '0.00', '0.00', '0.00', '0.00', '0.00');
INSERT INTO `t_card_account` VALUES ('94', '0', '100013000058', '0.00', '0.00', '0.00', '0.00', '0.00');
INSERT INTO `t_card_account` VALUES ('95', '0', '100013000059', '0.00', '0.00', '0.00', '0.00', '0.00');
INSERT INTO `t_card_account` VALUES ('96', '0', '100013000060', '0.00', '0.00', '0.00', '0.00', '0.00');
INSERT INTO `t_card_account` VALUES ('97', '0', '100013000061', '0.00', '0.00', '0.00', '0.00', '0.00');
INSERT INTO `t_card_account` VALUES ('98', '0', '100011000000', '0.00', '0.00', '0.00', '0.00', '0.00');
INSERT INTO `t_card_account` VALUES ('99', '0', '100011000001', '0.00', '0.00', '10000.00', '10000.00', '0.00');

-- ----------------------------
-- Table structure for `t_card_info`
-- ----------------------------
DROP TABLE IF EXISTS `t_card_info`;
CREATE TABLE `t_card_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '会员卡ID',
  `tenant_id` int(11) DEFAULT NULL COMMENT '租户ID',
  `branch_id` varchar(255) DEFAULT NULL COMMENT '分店ID',
  `cardno` varchar(255) DEFAULT NULL COMMENT '卡号',
  `level` varchar(20) DEFAULT NULL COMMENT '等级',
  `channel` varchar(20) DEFAULT NULL COMMENT '渠道',
  `status` char(5) DEFAULT NULL COMMENT '状态',
  `valid_date` datetime DEFAULT NULL COMMENT '有效日期',
  `createtime` datetime DEFAULT NULL COMMENT '创建时间',
  `createuser` varchar(100) DEFAULT NULL COMMENT '创建者',
  `updatetime` datetime DEFAULT NULL COMMENT '更新时间',
  `updateuser` varchar(100) DEFAULT NULL COMMENT '更新者',
  `card_type` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=112 DEFAULT CHARSET=utf8 COMMENT='会员卡信息表';

-- ----------------------------
-- Records of t_card_info
-- ----------------------------
INSERT INTO `t_card_info` VALUES ('33', null, '111', '16', null, '0', '1', null, '2015-10-28 16:21:11', '李晓敏', null, '', null);
INSERT INTO `t_card_info` VALUES ('34', null, '111', '17', null, '0', '1', null, '2015-10-28 16:21:21', '李晓敏', null, '', null);
INSERT INTO `t_card_info` VALUES ('35', null, '111', '18', null, '0', '1', null, '2015-10-28 17:06:06', '李晓敏', null, '', null);
INSERT INTO `t_card_info` VALUES ('36', null, '111', '19', null, '', '1', null, '2015-10-29 09:41:47', '', null, '', null);
INSERT INTO `t_card_info` VALUES ('37', null, '111', '20', null, null, '1', null, '2015-10-29 09:42:26', null, null, null, null);
INSERT INTO `t_card_info` VALUES ('38', null, '111', '21', null, '1', '1', null, '2015-10-29 09:47:16', null, null, null, null);
INSERT INTO `t_card_info` VALUES ('39', null, '111', '22', null, '1', '1', null, '2015-10-29 10:53:07', null, null, null, null);
INSERT INTO `t_card_info` VALUES ('40', null, '111', '23', null, '1', '1', null, '2015-10-29 10:56:48', null, null, null, null);
INSERT INTO `t_card_info` VALUES ('41', null, '111', '24', null, '1', '1', null, '2015-10-29 11:16:23', null, null, null, null);
INSERT INTO `t_card_info` VALUES ('42', null, '586313', '25', null, '1', '1', null, '2015-10-29 11:20:53', null, null, null, null);
INSERT INTO `t_card_info` VALUES ('43', null, '111', '26', null, '1', '1', null, '2015-10-30 09:39:19', null, null, null, null);
INSERT INTO `t_card_info` VALUES ('44', null, '111', '27', null, '1', '1', null, '2015-10-30 10:31:27', null, null, null, null);
INSERT INTO `t_card_info` VALUES ('45', null, '100013', '10001300000000', null, '0', '1', null, '2015-10-30 10:52:44', '李晓敏', null, '', null);
INSERT INTO `t_card_info` VALUES ('46', null, '111', '28', null, '1', '1', null, '2015-10-30 14:19:57', null, null, null, null);
INSERT INTO `t_card_info` VALUES ('47', null, '111', '29', null, '1', '1', null, '2015-10-30 14:37:55', null, null, null, null);
INSERT INTO `t_card_info` VALUES ('48', null, '111', '30', null, '1', '1', null, '2015-10-30 14:42:23', null, null, null, null);
INSERT INTO `t_card_info` VALUES ('49', null, '586313', '100013000001', null, '', '1', null, '2015-11-03 16:21:18', '', null, '', null);
INSERT INTO `t_card_info` VALUES ('50', null, '586313', '100013000002', null, null, '1', null, '2015-11-03 16:22:26', null, null, null, null);
INSERT INTO `t_card_info` VALUES ('51', null, '586313', '100013000003', null, '1', '1', null, '2015-11-03 16:24:31', '', null, '', null);
INSERT INTO `t_card_info` VALUES ('52', null, '586313', '100013000004', null, '0', '0', null, '2015-11-03 16:49:09', '李晓敏', '2015-11-05 16:45:21', '', null);
INSERT INTO `t_card_info` VALUES ('53', null, '586313', '100013000005', null, '0', '1', null, '2015-11-04 16:38:44', '李晓敏', null, '', null);
INSERT INTO `t_card_info` VALUES ('54', null, '586313', '100013000006', null, '1', '1', null, '2015-11-04 16:43:04', null, null, null, null);
INSERT INTO `t_card_info` VALUES ('55', null, '152314', '100013000007', null, '1', '1', null, '2015-11-05 16:49:44', null, null, null, null);
INSERT INTO `t_card_info` VALUES ('56', null, '152314', '100013000008', null, '1', '1', null, '2015-11-06 10:02:03', null, null, null, null);
INSERT INTO `t_card_info` VALUES ('57', null, '152314', '100013000009', null, '1', '1', null, '2015-11-09 17:49:23', null, null, null, null);
INSERT INTO `t_card_info` VALUES ('58', null, '586313', '100013000010', null, '1', '1', null, '2015-11-11 13:06:46', null, null, null, null);
INSERT INTO `t_card_info` VALUES ('59', null, '586313', '100013000011', null, '1', '1', null, '2015-11-12 14:28:02', null, null, null, null);
INSERT INTO `t_card_info` VALUES ('60', null, '586313', '100013000012', null, '0', '1', null, '2015-11-12 15:46:10', '李晓敏', null, '', null);
INSERT INTO `t_card_info` VALUES ('62', null, '586313', '100013000014', null, '1', '1', null, '2015-11-13 10:16:37', null, null, null, null);
INSERT INTO `t_card_info` VALUES ('63', null, '586313', '100013000015', null, '0', '1', null, '2015-11-13 10:17:48', '李晓敏', null, '', null);
INSERT INTO `t_card_info` VALUES ('64', null, '586313', '100013000016', null, '1', '1', null, '2015-11-13 10:24:00', null, null, null, null);
INSERT INTO `t_card_info` VALUES ('68', null, '152314', '100013000020', null, '1', '1', null, '2015-11-17 11:04:48', null, null, null, null);
INSERT INTO `t_card_info` VALUES ('69', null, '152314', '100013000021', null, '1', '1', null, '2015-11-17 11:06:51', null, null, null, null);
INSERT INTO `t_card_info` VALUES ('70', null, '152314', '100013000022', null, '1', '1', null, '2015-11-17 11:15:51', null, null, null, null);
INSERT INTO `t_card_info` VALUES ('71', null, '586313', '100013000023', null, '0', '0', null, '2015-11-17 14:50:13', '0003', null, '', null);
INSERT INTO `t_card_info` VALUES ('72', null, '586313', '100013000024', null, '0', '1', null, '2015-11-17 14:59:03', '0003', null, '', null);
INSERT INTO `t_card_info` VALUES ('73', null, '152314', '100013000025', null, '1', '1', null, '2015-11-17 15:05:20', null, null, null, null);
INSERT INTO `t_card_info` VALUES ('74', null, '586313', '100013000026', null, '0', '1', null, '2015-11-17 15:08:37', '0003', null, '', null);
INSERT INTO `t_card_info` VALUES ('75', null, '586313', '100013000027', null, '0', '1', null, '2015-11-17 15:10:02', '0003', null, '', null);
INSERT INTO `t_card_info` VALUES ('76', null, '586313', '100013000028', null, '0', '1', null, '2015-11-17 15:10:43', '0003', null, '', null);
INSERT INTO `t_card_info` VALUES ('77', null, '152314', '100013000029', null, '1', '1', null, '2015-11-17 15:12:51', null, null, null, null);
INSERT INTO `t_card_info` VALUES ('78', null, '586313', '100013000030', null, '0', '1', null, '2015-11-17 15:15:07', '0003', null, '', null);
INSERT INTO `t_card_info` VALUES ('80', null, '152314', '100013000032', null, '1', '1', null, '2015-11-17 15:26:22', null, null, null, null);
INSERT INTO `t_card_info` VALUES ('81', null, '152314', '100013000033', null, '1', '1', null, '2015-11-17 15:41:57', null, null, null, null);
INSERT INTO `t_card_info` VALUES ('82', null, '152314', '100013000034', null, '1', '1', null, '2015-11-17 15:45:23', null, null, null, null);
INSERT INTO `t_card_info` VALUES ('83', null, '152314', '100013000035', null, '1', '1', null, '2015-11-17 15:55:35', null, null, null, null);
INSERT INTO `t_card_info` VALUES ('85', null, '586313', '100013000037', null, '0', '1', null, '2015-11-19 15:09:28', '李晓敏', null, '', null);
INSERT INTO `t_card_info` VALUES ('86', null, '586313', '100013000038', null, '0', '1', null, '2015-11-19 15:09:42', '李晓敏', null, '', null);
INSERT INTO `t_card_info` VALUES ('87', null, '586313', '100013000039', null, '0', '1', null, '2015-11-20 17:00:38', '李四', null, '', null);
INSERT INTO `t_card_info` VALUES ('88', null, '586313', '100013000040', null, '0', '1', null, '2015-11-20 17:01:13', '李四', null, '', null);
INSERT INTO `t_card_info` VALUES ('89', null, '152314', '100013000041', null, '1', '1', null, '2015-11-23 13:51:21', null, null, null, null);
INSERT INTO `t_card_info` VALUES ('90', null, '152314', '100013000042', null, '1', '1', null, '2015-11-23 14:41:25', null, null, null, null);
INSERT INTO `t_card_info` VALUES ('93', null, '152314', '100013000045', null, '1', '1', null, '2015-11-24 16:47:21', null, null, null, null);
INSERT INTO `t_card_info` VALUES ('95', null, '152314', '100013000047', null, '1', '1', null, '2015-11-24 16:54:43', null, null, null, null);
INSERT INTO `t_card_info` VALUES ('106', null, '152314', '100013000058', null, '1', '1', null, '2015-11-25 16:00:40', null, null, null, null);
INSERT INTO `t_card_info` VALUES ('107', null, '152314', '100013000059', null, '1', '1', null, '2015-11-25 16:07:01', null, null, null, null);
INSERT INTO `t_card_info` VALUES ('108', null, '152314', '100013000060', null, '0', '1', null, '2015-11-25 16:11:42', '李四', null, '', null);
INSERT INTO `t_card_info` VALUES ('109', null, '586313', '100013000061', null, '0', '1', null, '2015-11-26 15:02:01', '李四', null, '', null);
INSERT INTO `t_card_info` VALUES ('110', null, '152314', '100011000000', null, '1', '1', null, '2015-11-26 15:34:01', null, null, null, null);
INSERT INTO `t_card_info` VALUES ('111', null, '152314', '100011000001', null, '0', '1', null, '2015-12-01 15:46:03', '李四', null, '', null);

-- ----------------------------
-- Table structure for `t_deal_detail`
-- ----------------------------
DROP TABLE IF EXISTS `t_deal_detail`;
CREATE TABLE `t_deal_detail` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `deal_no` varchar(255) DEFAULT NULL COMMENT '交易号',
  `card_no` varchar(255) DEFAULT NULL COMMENT '会员卡卡号',
  `deal_addr` varchar(255) DEFAULT NULL COMMENT '交易地点',
  `deal_type` varchar(10) DEFAULT NULL COMMENT '交易类型',
  `amount` decimal(8,2) DEFAULT NULL COMMENT '交易数量',
  `slip_no` varchar(255) DEFAULT NULL COMMENT '票据单号',
  `deal_time` datetime DEFAULT NULL COMMENT '交易时间',
  `deal_user` varchar(100) DEFAULT NULL COMMENT '交易者',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=132 DEFAULT CHARSET=utf8 COMMENT='会员交易表';

-- ----------------------------
-- Records of t_deal_detail
-- ----------------------------
INSERT INTO `t_deal_detail` VALUES ('65', '201511041353157079', '100013000004', '', 'XJXF', '102.00', 'H20151104586313000653', '2015-11-04 13:53:15', 'Test');
INSERT INTO `t_deal_detail` VALUES ('66', '201511041353157079', '100013000004', '', 'XJXFJF', '10.20', 'H20151104586313000653', '2015-11-04 13:53:15', 'Test');
INSERT INTO `t_deal_detail` VALUES ('67', '201511051612069205', '100013000004', '', 'XJCZ', '100.00', '201409240001', '2015-11-05 16:11:49', 'Test');
INSERT INTO `t_deal_detail` VALUES ('68', '201511051616348351', '100013000004', '', 'XJCZ', '100.00', '586313', '2015-11-05 16:16:34', 'Test');
INSERT INTO `t_deal_detail` VALUES ('69', '201511051618359689', '100013000004', '', 'XJCZ', '100.00', '586313', '2015-11-05 16:18:36', 'Test');
INSERT INTO `t_deal_detail` VALUES ('70', '201511121627003255', '100013000012', '', 'XJCZ', '10000.00', '586313', '2015-11-12 16:27:00', 'Test');
INSERT INTO `t_deal_detail` VALUES ('71', '201511121629493181', '100013000012', '', 'CZXF', '478.00', 'H20151112586313000678', '2015-11-12 16:29:49', 'Test');
INSERT INTO `t_deal_detail` VALUES ('72', '201511121629493181', '100013000012', '', 'CZXFJF', '47.80', 'H20151112586313000678', '2015-11-12 16:29:49', 'Test');
INSERT INTO `t_deal_detail` VALUES ('73', '201511121634216207', '100013000012', '', 'CZXF', '134.00', 'H20151111586313000671', '2015-11-12 16:34:21', 'Test');
INSERT INTO `t_deal_detail` VALUES ('74', '201511121634216207', '100013000012', '', 'CZXFJF', '13.40', 'H20151111586313000671', '2015-11-12 16:34:21', 'Test');
INSERT INTO `t_deal_detail` VALUES ('75', '201511121654086137', '100013000012', '', 'CZXF', '28.00', 'H20151112586313000676', '2015-11-12 16:54:08', 'Test');
INSERT INTO `t_deal_detail` VALUES ('76', '201511121654086137', '100013000012', '', 'CZXFJF', '2.80', 'H20151112586313000676', '2015-11-12 16:54:08', 'Test');
INSERT INTO `t_deal_detail` VALUES ('77', '201511121707087027', '100013000012', '', 'CZXF', '181.00', 'H20151112586313000674', '2015-11-12 17:07:08', 'Test');
INSERT INTO `t_deal_detail` VALUES ('78', '201511121707087027', '100013000012', '', 'CZXFJF', '18.10', 'H20151112586313000674', '2015-11-12 17:07:08', 'Test');
INSERT INTO `t_deal_detail` VALUES ('79', '201511121737404514', '100013000012', '', 'CZXF', '16.00', 'H20151104586313000650', '2015-11-12 17:37:40', 'Test');
INSERT INTO `t_deal_detail` VALUES ('80', '201511121737404514', '100013000012', '', 'CZXFJF', '1.60', 'H20151104586313000650', '2015-11-12 17:37:40', 'Test');
INSERT INTO `t_deal_detail` VALUES ('81', '201511131018032032', '100013000015', '', '5', '10000.00', '586313', '2015-11-13 10:18:03', 'Test');
INSERT INTO `t_deal_detail` VALUES ('82', '201511131018389798', '100013000015', '', '2', '181.00', 'H20151112586313000674', '2015-11-13 10:18:39', 'Test');
INSERT INTO `t_deal_detail` VALUES ('83', '201511131018389798', '100013000015', '', '3', '18.10', 'H20151112586313000674', '2015-11-13 10:18:39', 'Test');
INSERT INTO `t_deal_detail` VALUES ('84', '201511131020354960', '100013000015', '', '8', '-181.00', 'H20151112586313000674', '2015-11-13 10:20:35', '');
INSERT INTO `t_deal_detail` VALUES ('85', '201511131020354960', '100013000015', '', '9', '-18.10', 'H20151112586313000674', '2015-11-13 10:20:35', '');
INSERT INTO `t_deal_detail` VALUES ('86', '201511131023538712', '100013000015', '', '2', '181.00', 'H20151112586313000674', '2015-11-13 10:23:53', 'Test');
INSERT INTO `t_deal_detail` VALUES ('87', '201511131023538712', '100013000015', '', '3', '18.10', 'H20151112586313000674', '2015-11-13 10:23:53', 'Test');
INSERT INTO `t_deal_detail` VALUES ('88', '201511131024571804', '100013000015', '', '8', '-181.00', 'H20151112586313000674', '2015-11-13 10:24:57', '');
INSERT INTO `t_deal_detail` VALUES ('89', '201511131024571804', '100013000015', '', '9', '-18.10', 'H20151112586313000674', '2015-11-13 10:24:57', '');
INSERT INTO `t_deal_detail` VALUES ('90', '201511131027072821', '100013000015', '', '2', '181.00', 'H20151112586313000674', '2015-11-13 10:27:07', 'Test');
INSERT INTO `t_deal_detail` VALUES ('91', '201511131027072821', '100013000015', '', '3', '18.10', 'H20151112586313000674', '2015-11-13 10:27:07', 'Test');
INSERT INTO `t_deal_detail` VALUES ('92', '201511171450212288', '100013000023', '', '5', '10000.00', '586313', '2015-11-17 14:50:46', 'Test');
INSERT INTO `t_deal_detail` VALUES ('93', '201511171538316763', '100013000031', '', '5', '10000.00', '586313', '2015-11-17 15:38:56', 'Test');
INSERT INTO `t_deal_detail` VALUES ('94', '201511171539046880', '100013000031', '', '5', '20000.00', '586313', '2015-11-17 15:39:30', 'Test');
INSERT INTO `t_deal_detail` VALUES ('95', '201511171539339915', '100013000031', '', '5', '10000.00', '586313', '2015-11-17 15:39:59', 'Test');
INSERT INTO `t_deal_detail` VALUES ('96', '201511171619585126', '100013000036', '', '5', '50000.00', '586313', '2015-11-17 16:20:23', 'Test');
INSERT INTO `t_deal_detail` VALUES ('97', '201511171622302788', '100013000036', '', '2', '28.00', 'H20151117152314000258', '2015-11-17 16:22:56', 'Test');
INSERT INTO `t_deal_detail` VALUES ('98', '201511171622302788', '100013000036', '', '3', '2.80', 'H20151117152314000258', '2015-11-17 16:22:56', 'Test');
INSERT INTO `t_deal_detail` VALUES ('99', '201511171623368396', '100013000036', '', '8', '-28.00', 'H20151117152314000258', '2015-11-17 16:24:02', '');
INSERT INTO `t_deal_detail` VALUES ('100', '201511171623368396', '100013000036', '', '9', '-2.80', 'H20151117152314000258', '2015-11-17 16:24:02', '');
INSERT INTO `t_deal_detail` VALUES ('101', '201511171631063674', '100013000036', '', '2', '26.00', 'H20151117152314000259', '2015-11-17 16:31:31', 'Test');
INSERT INTO `t_deal_detail` VALUES ('102', '201511171631063674', '100013000036', '', '3', '2.60', 'H20151117152314000259', '2015-11-17 16:31:31', 'Test');
INSERT INTO `t_deal_detail` VALUES ('103', '201511171632464369', '100013000036', '', '2', '28.00', 'H20151117152314000260', '2015-11-17 16:33:12', 'Test');
INSERT INTO `t_deal_detail` VALUES ('104', '201511171632464369', '100013000036', '', '3', '2.80', 'H20151117152314000260', '2015-11-17 16:33:12', 'Test');
INSERT INTO `t_deal_detail` VALUES ('105', '201511171634056495', '100013000036', '', '8', '-28.00', 'H20151117152314000260', '2015-11-17 16:34:31', '');
INSERT INTO `t_deal_detail` VALUES ('106', '201511171634056495', '100013000036', '', '9', '-2.80', 'H20151117152314000260', '2015-11-17 16:34:31', '');
INSERT INTO `t_deal_detail` VALUES ('107', '201511171636102818', '100013000036', '', '8', '-28.00', 'H20151117152314000260', '2015-11-17 16:36:35', '');
INSERT INTO `t_deal_detail` VALUES ('108', '201511171636102818', '100013000036', '', '9', '-2.80', 'H20151117152314000260', '2015-11-17 16:36:35', '');
INSERT INTO `t_deal_detail` VALUES ('109', '201511201701404268', '100013000040', '', '5', '50000.00', '586313', '2015-11-20 17:01:31', 'Test');
INSERT INTO `t_deal_detail` VALUES ('110', '201511231532163005', '100013000012', '', '0', '476.00', 'H20151123152314000153', '2015-11-23 15:32:41', 'Test');
INSERT INTO `t_deal_detail` VALUES ('111', '201511231532163005', '100013000012', '', '1', '47.60', 'H20151123152314000153', '2015-11-23 15:32:41', 'Test');
INSERT INTO `t_deal_detail` VALUES ('112', '201511231533021071', '100013000012', '', '6', '-476.00', 'H20151123152314000153', '2015-11-23 15:33:27', '');
INSERT INTO `t_deal_detail` VALUES ('113', '201511231533021071', '100013000012', '', '7', '-47.60', 'H20151123152314000153', '2015-11-23 15:33:27', '');
INSERT INTO `t_deal_detail` VALUES ('114', '201511231533597602', '100013000012', '', '0', '476.00', 'H20151123152314000153', '2015-11-23 15:34:25', 'Test');
INSERT INTO `t_deal_detail` VALUES ('115', '201511231533597602', '100013000012', '', '1', '47.60', 'H20151123152314000153', '2015-11-23 15:34:25', 'Test');
INSERT INTO `t_deal_detail` VALUES ('116', '201511231559056063', '100013000012', '', '0', '202.00', 'H20151123152314000002', '2015-11-23 15:59:30', 'Test');
INSERT INTO `t_deal_detail` VALUES ('117', '201511231559056063', '100013000012', '', '1', '20.20', 'H20151123152314000002', '2015-11-23 15:59:30', 'Test');
INSERT INTO `t_deal_detail` VALUES ('118', '201511231600182464', '100013000012', '', '0', '48.00', 'H20151123152314000003', '2015-11-23 16:00:44', 'Test');
INSERT INTO `t_deal_detail` VALUES ('119', '201511231600182464', '100013000012', '', '1', '4.80', 'H20151123152314000003', '2015-11-23 16:00:44', 'Test');
INSERT INTO `t_deal_detail` VALUES ('120', '201511231618225146', '100013000012', '', '0', '298.00', 'H20151123152314000007', '2015-11-23 16:18:48', 'Test');
INSERT INTO `t_deal_detail` VALUES ('121', '201511231618225146', '100013000012', '', '1', '29.80', 'H20151123152314000007', '2015-11-23 16:18:48', 'Test');
INSERT INTO `t_deal_detail` VALUES ('122', '201511231619193576', '100013000012', '', '6', '-298.00', 'H20151123152314000007', '2015-11-23 16:19:45', '');
INSERT INTO `t_deal_detail` VALUES ('123', '201511231619193576', '100013000012', '', '7', '-29.80', 'H20151123152314000007', '2015-11-23 16:19:45', '');
INSERT INTO `t_deal_detail` VALUES ('124', '201511231627523278', '100013000012', '', '2', '196.00', 'H20151123152314000008', '2015-11-23 16:28:17', 'Test');
INSERT INTO `t_deal_detail` VALUES ('125', '201511231627523278', '100013000012', '', '3', '19.60', 'H20151123152314000008', '2015-11-23 16:28:17', 'Test');
INSERT INTO `t_deal_detail` VALUES ('126', '201511231628367967', '100013000012', '', '8', '-196.00', 'H20151123152314000008', '2015-11-23 16:29:02', '');
INSERT INTO `t_deal_detail` VALUES ('127', '201511231628367967', '100013000012', '', '9', '-19.60', 'H20151123152314000008', '2015-11-23 16:29:02', '');
INSERT INTO `t_deal_detail` VALUES ('128', '201511231645369857', '100013000012', '', '2', '30.00', 'H20151123152314000009', '2015-11-23 16:46:02', 'Test');
INSERT INTO `t_deal_detail` VALUES ('129', '201511231645369857', '100013000012', '', '3', '3.00', 'H20151123152314000009', '2015-11-23 16:46:02', 'Test');
INSERT INTO `t_deal_detail` VALUES ('130', '201511231645369857', '100013000012', '', '4', '7.00', 'H20151123152314000009', '2015-11-23 16:46:02', 'Test');
INSERT INTO `t_deal_detail` VALUES ('131', '201512011546099915', '100011000001', '', '5', '10000.00', '152314', '2015-12-01 15:46:34', 'Test');

-- ----------------------------
-- Table structure for `t_deal_master`
-- ----------------------------
DROP TABLE IF EXISTS `t_deal_master`;
CREATE TABLE `t_deal_master` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `branch_id` varchar(20) DEFAULT NULL COMMENT '分店号',
  `card_no` varchar(20) DEFAULT NULL COMMENT '会员卡卡号',
  `deal_no` varchar(255) DEFAULT NULL COMMENT '交易号',
  `value` decimal(8,2) DEFAULT NULL COMMENT '储值余额',
  `actual_value` decimal(8,2) DEFAULT NULL COMMENT '实际储值',
  `present_value` decimal(8,2) DEFAULT NULL COMMENT '赠送储值',
  `consume_value` decimal(8,2) DEFAULT NULL COMMENT '消费储值',
  `cash` decimal(8,2) DEFAULT NULL COMMENT '现金消费',
  `point` decimal(8,2) DEFAULT NULL COMMENT '积分余额',
  `present_point` decimal(8,2) DEFAULT NULL COMMENT '赠送积分',
  `consume_point` decimal(8,2) DEFAULT NULL COMMENT '消费积分',
  `slip_no` varchar(255) DEFAULT NULL COMMENT '票据单号',
  `deal_type` varchar(10) DEFAULT NULL COMMENT '交易类型',
  `charge_type` varchar(10) DEFAULT NULL COMMENT '充值类型',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=75 DEFAULT CHARSET=utf8 COMMENT='交易主表';

-- ----------------------------
-- Records of t_deal_master
-- ----------------------------
INSERT INTO `t_deal_master` VALUES ('35', '586313', '100013000004', '201511041353157079', '0.00', '0.00', '0.00', '0.00', '0.00', '10.20', '10.20', '0.00', 'H20151104586313000653', '', '');
INSERT INTO `t_deal_master` VALUES ('36', '586313', '100013000004', '201511051612069205', '100.00', '100.00', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', '201409240001', '0', '0');
INSERT INTO `t_deal_master` VALUES ('37', '586313', '100013000004', '201511051616348351', '200.00', '200.00', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', '586313', '0', '0');
INSERT INTO `t_deal_master` VALUES ('38', '586313', '100013000004', '201511051618359689', '300.00', '300.00', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', '586313', '0', '0');
INSERT INTO `t_deal_master` VALUES ('39', '586313', '100013000012', '201511121627003255', '10000.00', '10000.00', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', '586313', '0', '0');
INSERT INTO `t_deal_master` VALUES ('40', '586313', '100013000012', '201511121629493181', '9522.00', '9522.00', '0.00', '478.00', '0.00', '47.80', '47.80', '0.00', 'H20151112586313000678', '', '');
INSERT INTO `t_deal_master` VALUES ('41', '586313', '100013000012', '201511121634216207', '9388.00', '9388.00', '0.00', '134.00', '0.00', '61.20', '13.40', '0.00', 'H20151111586313000671', '', '');
INSERT INTO `t_deal_master` VALUES ('42', '586313', '100013000012', '201511121654086137', '9360.00', '9360.00', '0.00', '28.00', '0.00', '64.00', '2.80', '0.00', 'H20151112586313000676', '', '');
INSERT INTO `t_deal_master` VALUES ('43', '586313', '100013000012', '201511121707087027', '9179.00', '9179.00', '0.00', '181.00', '0.00', '82.10', '18.10', '0.00', 'H20151112586313000674', '', '');
INSERT INTO `t_deal_master` VALUES ('44', '586313', '100013000012', '201511121737404514', '9163.00', '9163.00', '0.00', '16.00', '0.00', '83.70', '1.60', '0.00', 'H20151104586313000650', '', '');
INSERT INTO `t_deal_master` VALUES ('45', '586313', '100013000012', '201511121751126245', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', 'H20151104586313000650', '', '');
INSERT INTO `t_deal_master` VALUES ('46', '586313', '100013000015', '201511131018032032', '10000.00', '10000.00', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', '586313', '0', '0');
INSERT INTO `t_deal_master` VALUES ('47', '586313', '100013000015', '201511131018389798', '9819.00', '9819.00', '0.00', '181.00', '0.00', '18.10', '18.10', '0.00', 'H20151112586313000674', '', '');
INSERT INTO `t_deal_master` VALUES ('48', '586313', '100013000015', '201511131020354960', '-181.00', '0.00', '0.00', '-181.00', '0.00', '-18.10', '-18.10', '0.00', 'H20151112586313000674', '', '');
INSERT INTO `t_deal_master` VALUES ('49', '586313', '100013000015', '201511131023538712', '9819.00', '9819.00', '0.00', '181.00', '0.00', '18.10', '18.10', '0.00', 'H20151112586313000674', '', '');
INSERT INTO `t_deal_master` VALUES ('50', '586313', '100013000015', '201511131024571804', '-181.00', '0.00', '0.00', '-181.00', '0.00', '-18.10', '-18.10', '0.00', 'H20151112586313000674', '', '');
INSERT INTO `t_deal_master` VALUES ('51', '586313', '100013000015', '201511131027072821', '9819.00', '9819.00', '0.00', '181.00', '0.00', '18.10', '18.10', '0.00', 'H20151112586313000674', '', '');
INSERT INTO `t_deal_master` VALUES ('52', '586313', '100013000023', '201511171450212288', '10000.00', '10000.00', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', '586313', '0', '0');
INSERT INTO `t_deal_master` VALUES ('53', '586313', '100013000031', '201511171538316763', '10000.00', '10000.00', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', '586313', '0', '1');
INSERT INTO `t_deal_master` VALUES ('54', '586313', '100013000031', '201511171539046880', '30000.00', '30000.00', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', '586313', '0', '0');
INSERT INTO `t_deal_master` VALUES ('55', '586313', '100013000031', '201511171539339915', '40000.00', '40000.00', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', '586313', '0', '1');
INSERT INTO `t_deal_master` VALUES ('56', '586313', '100013000036', '201511171619585126', '50000.00', '50000.00', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', '586313', '0', '0');
INSERT INTO `t_deal_master` VALUES ('57', '586313', '100013000036', '201511171622302788', '49972.00', '49972.00', '0.00', '28.00', '0.00', '2.80', '2.80', '0.00', 'H20151117152314000258', '', '');
INSERT INTO `t_deal_master` VALUES ('58', '586313', '100013000036', '201511171623368396', '-28.00', '0.00', '0.00', '-28.00', '0.00', '-2.80', '-2.80', '0.00', 'H20151117152314000258', '', '');
INSERT INTO `t_deal_master` VALUES ('59', '586313', '100013000036', '201511171631063674', '49974.00', '49974.00', '0.00', '26.00', '0.00', '2.60', '2.60', '0.00', 'H20151117152314000259', '', '');
INSERT INTO `t_deal_master` VALUES ('60', '586313', '100013000036', '201511171632464369', '49946.00', '49946.00', '0.00', '28.00', '0.00', '5.40', '2.80', '0.00', 'H20151117152314000260', '', '');
INSERT INTO `t_deal_master` VALUES ('61', '586313', '100013000036', '201511171634056495', '-28.00', '0.00', '0.00', '-28.00', '0.00', '-2.80', '-2.80', '0.00', 'H20151117152314000260', '', '');
INSERT INTO `t_deal_master` VALUES ('62', '586313', '100013000036', '201511171636102818', '-28.00', '0.00', '0.00', '-28.00', '0.00', '-2.80', '-2.80', '0.00', 'H20151117152314000260', '', '');
INSERT INTO `t_deal_master` VALUES ('63', '586313', '100013000040', '201511201701404268', '50000.00', '50000.00', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', '586313', '0', '0');
INSERT INTO `t_deal_master` VALUES ('64', '586313', '100013000012', '201511231532163005', '9163.00', '9163.00', '0.00', '0.00', '0.00', '131.30', '47.60', '0.00', 'H20151123152314000153', '', '');
INSERT INTO `t_deal_master` VALUES ('65', '586313', '100013000012', '201511231533021071', '0.00', '0.00', '0.00', '0.00', '-476.00', '-47.60', '-47.60', '0.00', 'H20151123152314000153', '', '');
INSERT INTO `t_deal_master` VALUES ('66', '586313', '100013000012', '201511231533597602', '9163.00', '9163.00', '0.00', '0.00', '0.00', '131.30', '47.60', '0.00', 'H20151123152314000153', '', '');
INSERT INTO `t_deal_master` VALUES ('67', '586313', '100013000012', '201511231559056063', '9163.00', '9163.00', '0.00', '0.00', '0.00', '151.50', '20.20', '0.00', 'H20151123152314000002', '', '');
INSERT INTO `t_deal_master` VALUES ('68', '586313', '100013000012', '201511231600182464', '9163.00', '9163.00', '0.00', '0.00', '0.00', '156.30', '4.80', '0.00', 'H20151123152314000003', '', '');
INSERT INTO `t_deal_master` VALUES ('69', '586313', '100013000012', '201511231618225146', '9163.00', '9163.00', '0.00', '0.00', '0.00', '186.10', '29.80', '0.00', 'H20151123152314000007', '', '');
INSERT INTO `t_deal_master` VALUES ('70', '586313', '100013000012', '201511231619193576', '0.00', '0.00', '0.00', '0.00', '-298.00', '-29.80', '-29.80', '0.00', 'H20151123152314000007', '', '');
INSERT INTO `t_deal_master` VALUES ('71', '586313', '100013000012', '201511231627523278', '8967.00', '8967.00', '0.00', '196.00', '0.00', '175.90', '19.60', '0.00', 'H20151123152314000008', '', '');
INSERT INTO `t_deal_master` VALUES ('72', '586313', '100013000012', '201511231628367967', '-196.00', '0.00', '0.00', '-196.00', '0.00', '-19.60', '-19.60', '0.00', 'H20151123152314000008', '', '');
INSERT INTO `t_deal_master` VALUES ('73', '586313', '100013000012', '201511231645369857', '9133.00', '9133.00', '0.00', '30.00', '0.00', '152.30', '3.00', '7.00', 'H20151123152314000009', '', '');
INSERT INTO `t_deal_master` VALUES ('74', '152314', '100011000001', '201512011546099915', '10000.00', '10000.00', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', '152314', '0', '0');

-- ----------------------------
-- Table structure for `t_dictionary`
-- ----------------------------
DROP TABLE IF EXISTS `t_dictionary`;
CREATE TABLE `t_dictionary` (
  `dictid` varchar(50) NOT NULL,
  `code_id` varchar(50) DEFAULT NULL,
  `code_desc` varchar(50) DEFAULT NULL,
  `code_sort` int(11) DEFAULT NULL,
  `status` int(11) DEFAULT NULL COMMENT '0 删除，1 存在',
  `type` varchar(50) DEFAULT NULL,
  `typename` varchar(50) NOT NULL,
  PRIMARY KEY (`dictid`),
  UNIQUE KEY `UQ_t_dictionary_dictid` (`dictid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_dictionary
-- ----------------------------
INSERT INTO `t_dictionary` VALUES ('1', '0', '虚拟卡', '1', '1', 'CHANNEL', '渠道');
INSERT INTO `t_dictionary` VALUES ('10', '3', '储值消费积分', '4', '1', 'TYPE', '交易类型');
INSERT INTO `t_dictionary` VALUES ('11', '4', '积分消费', '5', '1', 'TYPE', '交易类型');
INSERT INTO `t_dictionary` VALUES ('12', '5', '现金充值', '6', '1', 'TYPE', '交易类型');
INSERT INTO `t_dictionary` VALUES ('13', '6', '现金消费反结算', '7', '1', 'TYPE', '交易类型');
INSERT INTO `t_dictionary` VALUES ('14', '7', '现金消费积分反结算', '8', '1', 'TYPE', '交易类型');
INSERT INTO `t_dictionary` VALUES ('15', '8', '储值消费反结算', '9', '1', 'TYPE', '交易类型');
INSERT INTO `t_dictionary` VALUES ('16', '9', '储值消费积分反结算', '10', '1', 'TYPE', '交易类型');
INSERT INTO `t_dictionary` VALUES ('17', '10', '积分消费反结算', '11', '1', 'TYPE', '交易类型');
INSERT INTO `t_dictionary` VALUES ('18', '0', '注销', '1', '1', 'STATUS', '会员卡状态');
INSERT INTO `t_dictionary` VALUES ('19', '1', '正常', '2', '1', 'STATUS', '会员卡状态');
INSERT INTO `t_dictionary` VALUES ('2', '1', '实体卡', '2', '1', 'CHANNEL', '渠道');
INSERT INTO `t_dictionary` VALUES ('20', '2', '挂失', '3', '1', 'STATUS', '会员卡状态');
INSERT INTO `t_dictionary` VALUES ('21', '3', '取消挂失', '4', '1', 'STATUS', '会员卡状态');
INSERT INTO `t_dictionary` VALUES ('22', '0', '线下签约', '1', '1', 'TENANT_CHANNEL', '商户渠道类型');
INSERT INTO `t_dictionary` VALUES ('23', '1', '网签', '2', '1', 'TENANT_CHANNEL', '商户渠道类型');
INSERT INTO `t_dictionary` VALUES ('24', '2', '电话', '3', '1', 'TENANT_CHANNEL', '商户渠道类型');
INSERT INTO `t_dictionary` VALUES ('25', '3', '合作签约', '4', '1', 'TENANT_CHANNEL', '商户渠道类型');
INSERT INTO `t_dictionary` VALUES ('3', '2', '微会员', '3', '1', 'CHANNEL', '渠道');
INSERT INTO `t_dictionary` VALUES ('4', '0', '金卡', '1', '1', 'LEVEL', '会员卡等级');
INSERT INTO `t_dictionary` VALUES ('5', '1', '银卡', '2', '1', 'LEVEL', '会员卡等级');
INSERT INTO `t_dictionary` VALUES ('6', '2', '白金卡', '3', '1', 'LEVEL', '会员卡等级');
INSERT INTO `t_dictionary` VALUES ('7', '0', '现金消费', '1', '1', 'TYPE', '交易类型');
INSERT INTO `t_dictionary` VALUES ('8', '1', '现金消费积分', '2', '1', 'TYPE', '交易类型');
INSERT INTO `t_dictionary` VALUES ('9', '2', '储值消费', '3', '1', 'TYPE', '交易类型');

-- ----------------------------
-- Table structure for `t_member_card`
-- ----------------------------
DROP TABLE IF EXISTS `t_member_card`;
CREATE TABLE `t_member_card` (
  `member_id` int(11) NOT NULL COMMENT '会员ID',
  `cardno` varchar(255) NOT NULL COMMENT '会员卡ID',
  PRIMARY KEY (`member_id`,`cardno`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='会员和会员卡关系表';

-- ----------------------------
-- Records of t_member_card
-- ----------------------------
INSERT INTO `t_member_card` VALUES ('1111272', '16');
INSERT INTO `t_member_card` VALUES ('1111273', '17');
INSERT INTO `t_member_card` VALUES ('1111274', '18');
INSERT INTO `t_member_card` VALUES ('1111275', '19');
INSERT INTO `t_member_card` VALUES ('1111276', '20');
INSERT INTO `t_member_card` VALUES ('1111277', '21');
INSERT INTO `t_member_card` VALUES ('1111278', '22');
INSERT INTO `t_member_card` VALUES ('1111279', '23');
INSERT INTO `t_member_card` VALUES ('1111280', '24');
INSERT INTO `t_member_card` VALUES ('1111281', '25');
INSERT INTO `t_member_card` VALUES ('1111282', '26');
INSERT INTO `t_member_card` VALUES ('1111283', '27');
INSERT INTO `t_member_card` VALUES ('1111284', '10001300000000');
INSERT INTO `t_member_card` VALUES ('1111285', '28');
INSERT INTO `t_member_card` VALUES ('1111286', '29');
INSERT INTO `t_member_card` VALUES ('1111287', '30');
INSERT INTO `t_member_card` VALUES ('1111288', '100013000001');
INSERT INTO `t_member_card` VALUES ('1111289', '100013000002');
INSERT INTO `t_member_card` VALUES ('1111290', '100013000003');
INSERT INTO `t_member_card` VALUES ('1111291', '100013000004');
INSERT INTO `t_member_card` VALUES ('1111292', '100013000005');
INSERT INTO `t_member_card` VALUES ('1111293', '100013000006');
INSERT INTO `t_member_card` VALUES ('1111294', '100013000007');
INSERT INTO `t_member_card` VALUES ('1111295', '100013000008');
INSERT INTO `t_member_card` VALUES ('1111296', '100013000009');
INSERT INTO `t_member_card` VALUES ('1111297', '100013000010');
INSERT INTO `t_member_card` VALUES ('1111298', '100013000011');
INSERT INTO `t_member_card` VALUES ('1111299', '100013000012');
INSERT INTO `t_member_card` VALUES ('1111301', '100013000014');
INSERT INTO `t_member_card` VALUES ('1111302', '100013000015');
INSERT INTO `t_member_card` VALUES ('1111303', '100013000016');
INSERT INTO `t_member_card` VALUES ('1111309', '100013000022');
INSERT INTO `t_member_card` VALUES ('1111310', '100013000023');
INSERT INTO `t_member_card` VALUES ('1111311', '100013000024');
INSERT INTO `t_member_card` VALUES ('1111313', '100013000026');
INSERT INTO `t_member_card` VALUES ('1111314', '100013000027');
INSERT INTO `t_member_card` VALUES ('1111315', '100013000028');
INSERT INTO `t_member_card` VALUES ('1111316', '100013000029');
INSERT INTO `t_member_card` VALUES ('1111317', '100013000030');
INSERT INTO `t_member_card` VALUES ('1111319', '100013000032');
INSERT INTO `t_member_card` VALUES ('1111320', '100013000033');
INSERT INTO `t_member_card` VALUES ('1111321', '100013000034');
INSERT INTO `t_member_card` VALUES ('1111322', '100013000035');
INSERT INTO `t_member_card` VALUES ('1111324', '100013000037');
INSERT INTO `t_member_card` VALUES ('1111325', '100013000038');
INSERT INTO `t_member_card` VALUES ('1111326', '100013000039');
INSERT INTO `t_member_card` VALUES ('1111327', '100013000040');
INSERT INTO `t_member_card` VALUES ('1111328', '100013000041');
INSERT INTO `t_member_card` VALUES ('1111329', '100013000042');
INSERT INTO `t_member_card` VALUES ('1111332', '100013000045');
INSERT INTO `t_member_card` VALUES ('1111334', '100013000047');
INSERT INTO `t_member_card` VALUES ('1111345', '100013000058');
INSERT INTO `t_member_card` VALUES ('1111346', '100013000059');
INSERT INTO `t_member_card` VALUES ('1111347', '100013000060');
INSERT INTO `t_member_card` VALUES ('1111348', '100013000061');
INSERT INTO `t_member_card` VALUES ('1111349', '100011000000');
INSERT INTO `t_member_card` VALUES ('1111350', '100011000001');

-- ----------------------------
-- Table structure for `t_member_deal`
-- ----------------------------
DROP TABLE IF EXISTS `t_member_deal`;
CREATE TABLE `t_member_deal` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '会员卡交易ID',
  `card_id` int(11) DEFAULT NULL COMMENT '会员卡ID',
  `deal_addr` varchar(255) DEFAULT NULL COMMENT '交易地点',
  `deal_type` varchar(10) DEFAULT NULL COMMENT '交易类型',
  `point_change` decimal(8,2) DEFAULT NULL COMMENT '积分变化',
  `point` decimal(8,2) DEFAULT NULL COMMENT '积分',
  `value_change` decimal(8,2) DEFAULT NULL COMMENT '储值变化',
  `value` decimal(8,2) DEFAULT NULL COMMENT '储值',
  `slip_no` varchar(255) DEFAULT NULL COMMENT '票据单号',
  `deal_time` datetime DEFAULT NULL COMMENT '交易时间',
  `dealuser` varchar(100) DEFAULT NULL COMMENT '交易者',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='会员交易表';

-- ----------------------------
-- Records of t_member_deal
-- ----------------------------

-- ----------------------------
-- Table structure for `t_member_info`
-- ----------------------------
DROP TABLE IF EXISTS `t_member_info`;
CREATE TABLE `t_member_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '会员ID',
  `name` varchar(100) DEFAULT NULL COMMENT '姓名',
  `mobile` varchar(20) DEFAULT NULL COMMENT '手机号码',
  `gender` char(1) DEFAULT NULL COMMENT '性别',
  `birthday` date DEFAULT NULL COMMENT '出生日期',
  `password` varchar(255) DEFAULT NULL COMMENT '密码',
  `tenant_id` varchar(255) DEFAULT NULL COMMENT '租户ID',
  `branch_id` varchar(255) DEFAULT NULL COMMENT '分店ID',
  `branch_addr` varchar(255) DEFAULT NULL COMMENT '分店地址',
  `branch_name` varchar(50) DEFAULT NULL COMMENT '分店名称',
  `branch_phone` varchar(255) DEFAULT NULL COMMENT '分店联系电话',
  `createtime` datetime DEFAULT NULL COMMENT '创建时间',
  `createuser` varchar(100) DEFAULT NULL COMMENT '创建者',
  `updatetime` datetime DEFAULT NULL COMMENT '更新时间',
  `updateuser` varchar(255) DEFAULT NULL COMMENT '更新者',
  `member_avatar` varchar(255) DEFAULT NULL COMMENT '会员头像路径',
  `status` char(5) DEFAULT NULL COMMENT '0',
  `member_address` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1111351 DEFAULT CHARSET=utf8 COMMENT='会员基础信息表';

-- ----------------------------
-- Records of t_member_info
-- ----------------------------
INSERT INTO `t_member_info` VALUES ('1111272', 'tang', '18625208287', '0', '1982-10-27', '1', null, '111', null, null, null, '2015-10-28 16:21:11', '李晓敏', null, '', '', '1', null);
INSERT INTO `t_member_info` VALUES ('1111273', 'tang', '18625208287', '0', '1982-10-27', '1', null, '111', null, null, null, '2015-10-28 16:21:21', '李晓敏', null, '', '', '1', null);
INSERT INTO `t_member_info` VALUES ('1111275', '', '18012678986', '', null, '123456', null, '111', '', null, null, '2015-10-29 09:41:47', '', null, '', '', '1', null);
INSERT INTO `t_member_info` VALUES ('1111276', null, '18012678986', '', null, '123456', null, '111', null, null, null, '2015-10-29 09:42:26', null, null, null, null, '1', null);
INSERT INTO `t_member_info` VALUES ('1111277', 'xiu', '12563574122', '1', '2004-08-29', '123456', null, '111', null, null, null, '2015-10-29 09:47:16', null, '2015-10-29 14:37:24', null, null, '1', null);
INSERT INTO `t_member_info` VALUES ('1111278', '陆', '14725836914', '1', '2003-10-29', '123456', null, '111', null, null, null, '2015-10-29 10:53:07', null, '2015-10-29 11:12:57', null, null, '1', null);
INSERT INTO `t_member_info` VALUES ('1111279', '秀秀', '12345696358', '1', '2002-02-22', '123456', null, '111', null, null, null, '2015-10-29 10:56:48', null, '2015-10-29 10:57:09', null, null, '1', null);
INSERT INTO `t_member_info` VALUES ('1111280', null, '21536641223', '1', null, '123456', null, '111', null, null, null, '2015-10-29 11:16:23', null, '2015-10-29 11:16:37', null, null, '1', null);
INSERT INTO `t_member_info` VALUES ('1111281', null, '55665441266', '1', null, '111111', null, '586313', null, null, null, '2015-10-29 11:20:53', null, '2015-11-04 16:34:54', null, 'image01/M00/00/02/CkJoC1Y5wzeAZ4RTAAB-M1LGe-c645.jpg', '1', 'image01/M00/00/02/CkJoC1Y5veyAD0axAABgyUGPhlM649.jpg');
INSERT INTO `t_member_info` VALUES ('1111282', '秀秀', '14725836945', '1', '2008-12-26', '123456', null, '111', null, null, null, '2015-10-30 09:39:19', null, '2015-10-30 10:30:46', null, null, '1', null);
INSERT INTO `t_member_info` VALUES ('1111283', null, '15563248662', '0', null, '123456', null, '111', null, null, null, '2015-10-30 10:31:27', null, '2015-10-30 10:31:42', null, null, '1', null);
INSERT INTO `t_member_info` VALUES ('1111284', 'tang', '15950165469', '0', '1982-10-27', '1', null, '100013', null, null, null, '2015-10-30 10:52:44', '李晓敏', null, '', '', '1', null);
INSERT INTO `t_member_info` VALUES ('1111285', 'njg', '12345678908', '', '2003-08-30', '111111', null, '111', null, null, null, '2015-10-30 14:19:57', null, '2015-10-30 14:20:55', null, null, '1', null);
INSERT INTO `t_member_info` VALUES ('1111286', 'niuniu', '12225544111', '1', '2009-02-18', '123456', null, '111', null, null, null, '2015-10-30 14:37:55', null, '2015-10-30 14:38:35', null, null, '1', null);
INSERT INTO `t_member_info` VALUES ('1111287', 'bjju', '12345696369', '1', null, '123456', null, '111', null, null, null, '2015-10-30 14:42:22', null, '2015-10-30 14:50:49', null, null, '1', null);
INSERT INTO `t_member_info` VALUES ('1111288', '', '18550079866', '', null, '123456', '100013', '586313', '', null, null, '2015-11-03 16:21:18', '', null, '', '', '1', null);
INSERT INTO `t_member_info` VALUES ('1111289', null, '18550079867', '', null, '123456', '100013', '586313', null, null, null, '2015-11-03 16:22:26', null, null, null, null, '1', null);
INSERT INTO `t_member_info` VALUES ('1111290', '', '12854422255', '', null, '123456', '100013', '586313', '', null, null, '2015-11-03 16:24:31', '', null, '', '', '1', null);
INSERT INTO `t_member_info` VALUES ('1111291', '1', '18625208281', '0', '1995-11-03', '2', '100013', '586313', null, null, null, '2015-11-03 00:00:00', '李晓敏', '2015-11-05 15:54:06', '', '', '1', null);
INSERT INTO `t_member_info` VALUES ('1111292', '1', '1234554321', '0', '1984-03-10', '1', '100013', '586313', null, null, null, '2015-11-04 16:38:44', '李晓敏', '2015-11-04 16:48:50', '', '', '1', null);
INSERT INTO `t_member_info` VALUES ('1111293', null, '12364456654', '1', null, '111111', '100013', '586313', null, null, null, '2015-11-04 00:00:00', null, '2015-11-05 15:37:38', null, 'image01/M00/00/02/CkJoDVY5xTKAS30DAABk-4CCl-w938.jpg', '1', null);
INSERT INTO `t_member_info` VALUES ('1111294', '猪猪', '12345678901', '1', '2000-03-07', '111111', '100013', '152314', null, null, null, '2015-11-05 00:00:00', null, '2015-11-05 17:13:38', null, 'image01/M00/00/02/CkJoDVY7GE6AR8nxAACIkgjttjk291.jpg', '1', null);
INSERT INTO `t_member_info` VALUES ('1111295', null, '01234567890', '1', null, '123456', '100013', '152314', null, null, null, '2015-11-06 00:00:00', null, '2015-11-06 10:41:19', null, 'image01/M00/00/02/CkJoDVY8E2GAMZLvAACmvwHErP0284.jpg', '1', null);
INSERT INTO `t_member_info` VALUES ('1111296', null, '12345678900', '', null, '123456', '100013', '152314', null, null, null, '2015-11-09 17:49:23', null, null, null, null, '1', null);
INSERT INTO `t_member_info` VALUES ('1111297', null, '12345678911', '1', null, '123456', '100013', '586313', null, null, null, '2015-11-11 00:00:00', null, '2015-11-11 13:07:02', null, 'image01/M00/00/02/CkJoC1ZCzP-AQFxoAAB7aS60GO4389.jpg', '1', null);
INSERT INTO `t_member_info` VALUES ('1111298', null, '14725836900', '', null, '123456', '100013', '586313', null, null, null, '2015-11-12 00:00:00', null, '2015-11-12 14:35:17', null, 'image01/M00/00/03/CkJoC1ZEMzOABOLmAAB6hE_Los4507.jpg', '1', null);
INSERT INTO `t_member_info` VALUES ('1111299', 'tang', '18655961901', '0', '1982-11-12', '', '100013', '586313', null, null, null, '2015-11-12 15:46:10', '李晓敏', null, '', '', '1', null);
INSERT INTO `t_member_info` VALUES ('1111302', 'TANG', '18625208280', '0', '1982-11-13', '', '100013', '586313', null, null, null, '2015-11-13 10:17:48', '李晓敏', null, '', '', '1', null);
INSERT INTO `t_member_info` VALUES ('1111303', null, '15323232323', '', null, '123456', '100013', '586313', null, null, null, '2015-11-13 10:24:00', null, null, null, null, '1', null);
INSERT INTO `t_member_info` VALUES ('1111308', '一一', '15236232356', '0', '2016-05-11', '123456', '100013', '152314', null, null, null, '2015-11-17 00:00:00', null, '2015-11-17 11:09:48', null, 'image01/M00/00/04/CkJoC1ZKmn-AURA0AACXjAdN9U4894.jpg', '1', null);
INSERT INTO `t_member_info` VALUES ('1111310', '沈高云', '15951137182', '0', '1995-11-17', '123456', '100013', '586313', null, null, null, '2015-11-17 00:00:00', '0003', '2015-11-17 14:51:19', '', '', '1', null);
INSERT INTO `t_member_info` VALUES ('1111311', '建军节', '23912662436', '0', '1995-11-17', '', '100013', '586313', null, null, null, '2015-11-17 14:59:03', '0003', null, '', '', '1', null);
INSERT INTO `t_member_info` VALUES ('1111313', 'yyy', '235', '0', '1995-11-17', '', '100013', '586313', null, null, null, '2015-11-17 15:08:37', '0003', null, '', '', '1', null);
INSERT INTO `t_member_info` VALUES ('1111314', '444', '1123123456789', '0', '1995-11-17', '', '100013', '586313', null, null, null, '2015-11-17 15:10:02', '0003', null, '', '', '1', null);
INSERT INTO `t_member_info` VALUES ('1111315', 'UI欧', '就好看了就哭了', '0', '1995-11-17', '', '100013', '586313', null, null, null, '2015-11-17 15:10:43', '0003', null, '', '', '1', null);
INSERT INTO `t_member_info` VALUES ('1111317', 'u8ouiouiouio', '132', '1', '1995-11-17', '', '100013', '586313', null, null, null, '2015-11-17 15:15:07', '0003', null, '', '', '1', null);
INSERT INTO `t_member_info` VALUES ('1111324', 'tang', '18550079860', '0', '1982-10-27', '1', '100013', '586313', null, null, null, '2015-11-19 15:09:28', '李晓敏', null, '', '', '1', null);
INSERT INTO `t_member_info` VALUES ('1111326', '84', '456', '0', '1995-11-20', '', '100013', '586313', null, null, null, '2015-11-20 17:00:38', '李四', null, '', '', '1', null);
INSERT INTO `t_member_info` VALUES ('1111327', 'ko', '13944445555', '0', '1995-11-20', '', '100013', '586313', null, null, null, '2015-11-20 17:01:13', '李四', null, '', '', '1', null);
INSERT INTO `t_member_info` VALUES ('1111328', '哈哈哈哈风华风华姐姐哈哈电话话费话费话费哈哈风华话费话费话费哈哈方号方家大很好很好很好哈哈哈哈风华风华姐姐', '12345685566', '', '2015-11-23', '123444', '100013', '152314', null, null, null, '2015-11-23 00:00:00', null, '2015-11-23 14:29:51', null, null, '1', null);
INSERT INTO `t_member_info` VALUES ('1111329', null, '00000000000', '1', null, '000000', '100013', '152314', null, null, null, '2015-11-23 00:00:00', null, '2015-11-23 17:32:28', null, null, '1', null);
INSERT INTO `t_member_info` VALUES ('1111346', null, '13656263406', '', null, '123456', '100013', '152314', null, null, null, '2015-11-25 00:00:00', null, '2015-11-25 16:51:09', null, 'image01/M00/00/05/CkJoC1ZVdoaALMRRAACDfSThKg0727.jpg', '1', null);
INSERT INTO `t_member_info` VALUES ('1111347', 'yui', '15956565656', '0', '1995-11-25', '123456', '100013', '152314', null, null, null, '2015-11-25 16:11:42', '李四', null, '', '', '1', null);
INSERT INTO `t_member_info` VALUES ('1111348', 'uy', '13912662438', '0', '1995-11-26', '123456', '100013', '586313', null, null, null, '2015-11-26 15:02:01', '李四', null, '', '', '1', null);
INSERT INTO `t_member_info` VALUES ('1111349', null, '18012668986', '', null, '123456', '100011', '152314', null, null, null, '2015-11-26 15:34:01', null, null, null, null, '1', null);
INSERT INTO `t_member_info` VALUES ('1111350', 'hhh', '15936363636', '0', '1995-12-01', '123456', '100011', '152314', null, null, null, '2015-12-01 15:46:03', '李四', null, '', '', '1', null);

-- ----------------------------
-- Table structure for `t_member_rule`
-- ----------------------------
DROP TABLE IF EXISTS `t_member_rule`;
CREATE TABLE `t_member_rule` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '规则ID',
  `tenant_id` int(11) DEFAULT NULL COMMENT '租户ID',
  `type` varchar(255) DEFAULT NULL COMMENT '规则类型',
  `code` varchar(255) DEFAULT NULL COMMENT '规则编码',
  `value` varchar(255) DEFAULT NULL COMMENT '规则值',
  `stauts` varchar(255) DEFAULT NULL COMMENT '状态',
  `createtime` varchar(255) DEFAULT NULL COMMENT '创建时间',
  `createuser` varchar(255) DEFAULT NULL COMMENT '创建者',
  `updatetime` varchar(255) DEFAULT NULL COMMENT '更新时间',
  `updateuser` varchar(255) DEFAULT NULL COMMENT '更新者',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='会员规则表';

-- ----------------------------
-- Records of t_member_rule
-- ----------------------------

-- ----------------------------
-- Table structure for `t_member_service`
-- ----------------------------
DROP TABLE IF EXISTS `t_member_service`;
CREATE TABLE `t_member_service` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '服务历史ID',
  `member_id` int(11) DEFAULT NULL COMMENT '会员ID',
  `card_id` int(11) DEFAULT NULL COMMENT '会员卡ID',
  `card_no` varchar(255) DEFAULT NULL,
  `tenant_id` varchar(255) DEFAULT NULL COMMENT '租户ID',
  `branch_id` varchar(255) DEFAULT NULL COMMENT '分店ID',
  `service` varchar(255) DEFAULT NULL COMMENT '服务',
  `comment` varchar(255) DEFAULT NULL COMMENT '服务说明',
  `servicetime` datetime DEFAULT NULL COMMENT '服务时间',
  `serviceuser` varchar(100) DEFAULT NULL COMMENT '服务员',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COMMENT='会员服务历史表';

-- ----------------------------
-- Records of t_member_service
-- ----------------------------
INSERT INTO `t_member_service` VALUES ('3', '0', '0', '100013000004', '', '586313', 'GSFW', '', '2015-11-05 16:32:48', '');
INSERT INTO `t_member_service` VALUES ('4', '0', '0', '100013000004', '', '586313', 'GSFW', '', '2015-11-05 16:43:22', '');
INSERT INTO `t_member_service` VALUES ('5', '0', '0', '100013000004', '', '586313', 'GSFW', '', '2015-11-05 16:45:21', '');
INSERT INTO `t_member_service` VALUES ('6', '0', '0', '100013000031', '', '586313', 'GSFW', '', '2015-11-17 15:42:08', '');
INSERT INTO `t_member_service` VALUES ('7', '0', '0', '100013000031', '', '586313', 'GSFW', '', '2015-11-17 15:42:52', '');
INSERT INTO `t_member_service` VALUES ('8', '0', '0', '100013000031', '', '586313', 'GSFW', '', '2015-11-17 15:44:28', '');

-- ----------------------------
-- Table structure for `t_system_log`
-- ----------------------------
DROP TABLE IF EXISTS `t_system_log`;
CREATE TABLE `t_system_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `card_no` varchar(255) DEFAULT NULL,
  `slip_no` varchar(255) DEFAULT NULL,
  `content` varchar(255) DEFAULT NULL COMMENT '操作日志内容',
  `operation_time` timestamp NULL DEFAULT NULL COMMENT '操作时间',
  `operator` varchar(100) DEFAULT NULL COMMENT '操作者',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8 COMMENT='操作日志表';

-- ----------------------------
-- Records of t_system_log
-- ----------------------------
INSERT INTO `t_system_log` VALUES ('8', '18625208281', '', 'content: \r\n### Error updating database.  Cause: com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: Yo', '2015-11-04 16:33:11', '');
INSERT INTO `t_system_log` VALUES ('9', '18625208281', '', 'content: \r\n### Error updating database.  Cause: com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: Yo', '2015-11-05 16:18:53', '');
INSERT INTO `t_system_log` VALUES ('10', '18625208281', '', 'content: \r\n### Error updating database.  Cause: com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: Yo', '2015-11-05 16:19:30', '');
INSERT INTO `t_system_log` VALUES ('11', '18625208281', '', 'content: \r\n### Error updating database.  Cause: com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: Yo', '2015-11-05 16:21:12', '');
INSERT INTO `t_system_log` VALUES ('12', '18625208281', '', 'content: \r\n### Error updating database.  Cause: com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: Yo', '2015-11-05 16:23:03', '');
INSERT INTO `t_system_log` VALUES ('13', '100013000004', '', 'content: \r\n### Error updating database.  Cause: com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: Yo', '2015-11-05 16:28:11', '');
INSERT INTO `t_system_log` VALUES ('14', '18625208281', '', 'content: \r\n### Error updating database.  Cause: com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: Yo', '2015-11-05 16:28:48', '');
INSERT INTO `t_system_log` VALUES ('15', '100013000004', '', 'content: \r\n### Error updating database.  Cause: com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: Yo', '2015-11-05 16:31:44', '');

-- ----------------------------
-- Table structure for `t_tenant_card`
-- ----------------------------
DROP TABLE IF EXISTS `t_tenant_card`;
CREATE TABLE `t_tenant_card` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '商户卡ID',
  `tenant_id` int(11) DEFAULT NULL COMMENT '商户ID',
  `start_no` varchar(255) DEFAULT NULL COMMENT '开始序号',
  `end_no` varchar(255) DEFAULT NULL COMMENT '结束序号',
  `current_no` varchar(255) DEFAULT NULL COMMENT '当前序号',
  `status` char(5) DEFAULT NULL COMMENT '状态',
  `createtime` datetime DEFAULT NULL COMMENT '创建时间',
  `createuser` varchar(100) DEFAULT NULL COMMENT '创建者',
  `updatetime` datetime DEFAULT NULL COMMENT '更新时间',
  `updateuser` varchar(100) DEFAULT NULL COMMENT '更新者',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='商户卡信息表';

-- ----------------------------
-- Records of t_tenant_card
-- ----------------------------
INSERT INTO `t_tenant_card` VALUES ('1', '111', null, null, '31', '', null, null, null, null);
INSERT INTO `t_tenant_card` VALUES ('2', '100013', '0', '2000', '62', '', null, null, null, null);
INSERT INTO `t_tenant_card` VALUES ('3', '100011', '0', '2000', '2', '', null, null, null, null);

-- ----------------------------
-- Table structure for `t_tenant_deal`
-- ----------------------------
DROP TABLE IF EXISTS `t_tenant_deal`;
CREATE TABLE `t_tenant_deal` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '商户卡交易ID',
  `card_id` int(11) DEFAULT NULL COMMENT '商户卡ID',
  `type` varchar(10) DEFAULT NULL COMMENT '交易类型',
  `amount` decimal(8,2) DEFAULT NULL COMMENT '交易数量',
  `dealtime` timestamp NULL DEFAULT NULL COMMENT '交易时间',
  `dealuser` varchar(100) DEFAULT NULL COMMENT '交易者',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商户充值交易表';

-- ----------------------------
-- Records of t_tenant_deal
-- ----------------------------

-- ----------------------------
-- Table structure for `t_tenant_info`
-- ----------------------------
DROP TABLE IF EXISTS `t_tenant_info`;
CREATE TABLE `t_tenant_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `tenantid` varchar(255) DEFAULT NULL COMMENT '租户id',
  `branchid` varchar(255) DEFAULT NULL COMMENT '分店id',
  `tenantname` varchar(255) DEFAULT NULL COMMENT '租户名称',
  `tenanttel` varchar(255) DEFAULT NULL COMMENT '租户电话',
  `address` varchar(255) DEFAULT NULL COMMENT '租户地址',
  `tenantstatus` int(11) DEFAULT NULL COMMENT '租户状态 默认是 0 正常 1 不正常或冻结',
  `bizlicence` varchar(255) DEFAULT NULL COMMENT '租户营业执照',
  `registedate` date DEFAULT NULL COMMENT '租户签约日期',
  `expiredate` date DEFAULT NULL COMMENT '租户到期日期',
  `channeltype` int(11) DEFAULT NULL COMMENT '渠道类型 0 线下签约 1 网签 2 电话 3 合作签约   默认 0 ',
  `securitykey` varchar(255) DEFAULT NULL COMMENT '安全码 (用于客户端请求服务的安全验证)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;


CREATE TABLE `t_discount_rule` (
  `id` varchar(255) NOT NULL COMMENT '主键ID',
  `tenant_id` varchar(11) DEFAULT NULL COMMENT '租户ID',
  `discount` varchar(50) DEFAULT NULL COMMENT '对应的折扣（8折，88折等……）',
  `level` int(5) DEFAULT NULL COMMENT '折扣等级',
  `money_max` double(20,0) DEFAULT NULL COMMENT '该折扣金额的上线',
  `money_min` double(20,0) DEFAULT NULL COMMENT '该折扣金额的下线',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `t_preferential` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL COMMENT '优惠名称',
  `status` int(11) NOT NULL COMMENT '状态：【0:正常（启用），1:禁用，2:不可用（删除）】',
  `start_time` datetime NOT NULL COMMENT '开始时间',
  `end_time` datetime NOT NULL COMMENT '结束时间',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `description` varchar(500) DEFAULT NULL COMMENT '优惠描述',
  `tenant_id` varchar(20) DEFAULT NULL COMMENT '租户ID',
  `type` int(11) DEFAULT NULL COMMENT '优惠类型（1:充值赠送   2:消费积分）',
  `deal_value` double DEFAULT NULL COMMENT '交易值:根据type字段不同，含义不同(如:type=1,deal_value则表示充值金额type=2,deal_value表示消费金额)',
  `present_value` double DEFAULT NULL COMMENT '优惠的赠送金额，根据type字段不同，含义不同(如:type=1，present_value为充值赠送金额type=2,present_value为消费积分)',
  `rule` int(11) DEFAULT NULL COMMENT '优惠规则（0:无规则  1:按比例多充多送  2:？？）',
  `type_name` varchar(50) DEFAULT NULL COMMENT '优惠类型名称（预留）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;


CREATE TABLE `t_preferential_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `preferential_id` int(11) NOT NULL COMMENT '对应t_preferential表id',
  `branch_id` varchar(20) DEFAULT NULL COMMENT '门店ID',
  `tenant_id` varchar(20) DEFAULT NULL COMMENT '总店ID',
  `branch_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8 COMMENT='优惠信息明细表(哪些门店可用)';



-- ----------------------------
-- Records of t_tenant_info
-- ----------------------------
INSERT INTO `t_tenant_info` VALUES ('1', '100013', '586313', '新辣道', '12345', '12345', '1', 'tttttttt', null, null, '0', null);
INSERT INTO `t_tenant_info` VALUES ('2', '100011', '152314', '豆捞', '5677777', '777777', '1', 'uuuuuuuu', null, null, '2', null);
INSERT INTO `t_tenant_info` VALUES ('3', '9876', null, '酸菜鱼', '55555', '55555', '1', 'ooooooo', null, null, '3', null);
INSERT INTO `t_tenant_info` VALUES ('4', '2345', null, '2345', '12345', '12345', '1', '12345', '2015-10-14', null, '1', null);
INSERT INTO `t_tenant_info` VALUES ('5', '1234', null, '新辣道', '12345', '12345', '0', '12345', '2015-10-14', null, '0', null);
INSERT INTO `t_tenant_info` VALUES ('6', '1234', null, '新辣道', '12345', '12345', '0', '12345', '2015-10-14', null, '0', null);
