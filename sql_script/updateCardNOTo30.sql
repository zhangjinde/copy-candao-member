/*
Navicat MySQL Data Transfer

Source Server         : 10.66.21.8
Source Server Version : 50625
Source Host           : 10.66.21.8:3306
Source Database       : member

Target Server Type    : MYSQL
Target Server Version : 50625
File Encoding         : 65001

Date: 2016-04-21 03:31:21
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_preferential
-- ----------------------------
grant all privileges on *.* to root@"%" identified by "mysql.candao";
DROP TABLE IF EXISTS `t_preferential`;
CREATE TABLE `t_preferential` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL COMMENT '优惠名称',
  `status` int(11) NOT NULL COMMENT '状态：【0:正常（启用），1:禁用，2:不可用（删除）】',
  `start_time` datetime DEFAULT NULL COMMENT '开始时间',
  `end_time` datetime DEFAULT NULL COMMENT '结束时间',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `description` varchar(500) DEFAULT NULL COMMENT '优惠描述',
  `tenant_id` varchar(20) DEFAULT NULL COMMENT '租户ID',
  `type` int(11) DEFAULT NULL COMMENT '优惠类型（1:充值赠送   2:消费积分）',
  `deal_value` double DEFAULT NULL COMMENT '交易值:根据type字段不同，含义不同(如:type=1,deal_value则表示充值金额type=2,deal_value表示消费金额)',
  `present_value` double DEFAULT NULL COMMENT '优惠的赠送金额，根据type字段不同，含义不同(如:type=1，present_value为充值赠送金额type=2,present_value为消费积分)',
  `rule` int(11) DEFAULT NULL COMMENT '优惠规则（0:无规则  1:按比例多充多送  2:？？）',
  `type_name` varchar(50) DEFAULT NULL COMMENT '优惠类型名称（预留）',
  `weixin_status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '微信是否启用优惠规则',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=62 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_preferential
-- ----------------------------

-- ----------------------------
-- Table structure for t_preferential_info
-- ----------------------------
DROP TABLE IF EXISTS `t_preferential_info`;
CREATE TABLE `t_preferential_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `preferential_id` int(11) NOT NULL COMMENT '对应t_preferential表id',
  `branch_id` varchar(20) DEFAULT NULL COMMENT '门店ID',
  `tenant_id` varchar(20) DEFAULT NULL COMMENT '总店ID',
  `branch_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=204 DEFAULT CHARSET=utf8 COMMENT='优惠信息明细表(哪些门店可用)';

-- ----------------------------
-- Records of t_preferential_info
-- ----------------------------


-- 新增交易类型
-- INSERT INTO `t_dictionary` VALUES ('36', '17', '储值赠送', '18', '1', 'TYPE', '交易类型');
-- 修改交易详情表的字段长度
ALTER TABLE t_card_account MODIFY COLUMN  t_card_account.card_no varchar(50) ;
ALTER TABLE t_card_info MODIFY column t_card_info.cardno VARCHAR(50);
alter table t_deal_master modify column t_deal_master.card_no varchar(50) ;
ALTER TABLE t_deal_detail MODIFY column t_deal_detail.card_no VARCHAR(50);
ALTER TABLE t_member_card MODIFY COLUMN t_member_card.cardno VARCHAR(50);
ALTER TABLE t_member_service MODIFY COLUMN t_member_service.card_no VARCHAR(50);
ALTER TABLE t_system_log MODIFY COLUMN t_system_log.card_no VARCHAR(50);

-- 更新数据库与会员卡号为30位
UPDATE t_card_account AS tca
SET tca.card_no = (
	SELECT
		(
			CASE
			WHEN LOCATE(
				ten.tenantid,
				REPLACE (
					LTRIM(
						REPLACE (tca.card_no, "0", " ")
					),
					" ",
					"0"
				)
			) = 0 THEN
				CONCAT(
					SUBSTRING(
						'000000000000000000000000000000'
						FROM
							1 FOR (
								30 - LENGTH(
									CONCAT(
										ten.tenantid,
										REPLACE (
											LTRIM(
												REPLACE (tca.card_no, "0", " ")
											),
											" ",
											"0"
										)
									)
								)
							)
					),
					CONCAT(
						ten.tenantid,
						REPLACE (
							LTRIM(
								REPLACE (tca.card_no, "0", " ")
							),
							" ",
							"0"
						)
					)
				)
			ELSE
				CONCAT(
					SUBSTRING(
						'000000000000000000000000000000'
						FROM
							1 FOR (30 - LENGTH(tca.card_no))
					),
					tca.card_no
				)
			END
		)
	FROM
		t_tenant_info AS ten,
		t_card_info AS tc
	WHERE
		tc.branch_id = ten.branchid
	AND tc.cardno = tca.card_no LIMIT 1
);

UPDATE t_deal_detail AS tdd
SET tdd.card_no = (
	SELECT
		(
			CASE
			WHEN LOCATE(
				ten.tenantid,
				REPLACE (
					LTRIM(
						REPLACE (tdd.card_no, "0", " ")
					),
					" ",
					"0"
				)
			) = 0 THEN
				CONCAT(
					SUBSTRING(
						'000000000000000000000000000000'
						FROM
							1 FOR (
								30 - LENGTH(
									CONCAT(
										ten.tenantid,
										REPLACE (
											LTRIM(
												REPLACE (tdd.card_no, "0", " ")
											),
											" ",
											"0"
										)
									)
								)
							)
					),
					CONCAT(
						ten.tenantid,
						REPLACE (
							LTRIM(
								REPLACE (tdd.card_no, "0", " ")
							),
							" ",
							"0"
						)
					)
				)
			ELSE
				CONCAT(
					SUBSTRING(
						'000000000000000000000000000000'
						FROM
							1 FOR (30 - LENGTH(tdd.card_no))
					),
					tdd.card_no
				)
			END
		)
	FROM
		t_tenant_info AS ten,
		t_card_info AS tc
	WHERE
		tc.branch_id = ten.branchid
	AND tc.cardno = tdd.card_no  LIMIT 1
);

UPDATE t_deal_master AS tc
SET tc.card_no = (
	SELECT
		(
			CASE
			WHEN LOCATE(
				ten.tenantid,
				REPLACE (
					LTRIM(
						REPLACE (tc.card_no, "0", " ")
					),
					" ",
					"0"
				)
			) = 0 THEN
				CONCAT(
					SUBSTRING(
						'000000000000000000000000000000'
						FROM
							1 FOR (
								30 - LENGTH(
									CONCAT(
										ten.tenantid,
										REPLACE (
											LTRIM(
												REPLACE (tc.card_no, "0", " ")
											),
											" ",
											"0"
										)
									)
								)
							)
					),
					CONCAT(
						ten.tenantid,
						REPLACE (
							LTRIM(
								REPLACE (tc.card_no, "0", " ")
							),
							" ",
							"0"
						)
					)
				)
			ELSE
				CONCAT(
					SUBSTRING(
						'000000000000000000000000000000'
						FROM
							1 FOR (30 - LENGTH(tc.card_no))
					),
					tc.card_no
				)
			END
		)
	FROM
		t_tenant_info AS ten
	WHERE
		tc.branch_id = ten.branchid  LIMIT 1
);
UPDATE t_member_card AS tmc
SET tmc.cardno = (
	SELECT
		(
			CASE
			WHEN LOCATE(
				ten.tenantid,
				REPLACE (
					LTRIM(
						REPLACE (tmc.cardno, "0", " ")
					),
					" ",
					"0"
				)
			) = 0 THEN
				CONCAT(
					SUBSTRING(
						'000000000000000000000000000000'
						FROM
							1 FOR (
								30 - LENGTH(
									CONCAT(
										ten.tenantid,
										REPLACE (
											LTRIM(
												REPLACE (tmc.cardno, "0", " ")
											),
											" ",
											"0"
										)
									)
								)
							)
					),
					CONCAT(
						ten.tenantid,
						REPLACE (
							LTRIM(
								REPLACE (tmc.cardno, "0", " ")
							),
							" ",
							"0"
						)
					)
				)
			ELSE
				CONCAT(
					SUBSTRING(
						'000000000000000000000000000000'
						FROM
							1 FOR (30 - LENGTH(tc.cardno))
					),
					tmc.cardno
				)
			END
		)
	FROM
		t_tenant_info AS ten,
		t_card_info AS tc
	WHERE
		tc.branch_id = ten.branchid
	AND tc.cardno = tmc.cardno  LIMIT 1
); 

UPDATE t_member_service AS tms
SET tms.card_no = (
	SELECT
		(
			CASE
			WHEN LOCATE(
				ten.tenantid,
				REPLACE (
					LTRIM(
						REPLACE (tms.card_no, "0", " ")
					),
					" ",
					"0"
				)
			) = 0 THEN
				CONCAT(
					SUBSTRING(
						'000000000000000000000000000000'
						FROM
							1 FOR (
								30 - LENGTH(
									CONCAT(
										ten.tenantid,
										REPLACE (
											LTRIM(
												REPLACE (tms.card_no, "0", " ")
											),
											" ",
											"0"
										)
									)
								)
							)
					),
					CONCAT(
						ten.tenantid,
						REPLACE (
							LTRIM(
								REPLACE (tms.card_no, "0", " ")
							),
							" ",
							"0"
						)
					)
				)
			ELSE
				CONCAT(
					SUBSTRING(
						'000000000000000000000000000000'
						FROM
							1 FOR (30 - LENGTH(tms.card_no))
					),
					tms.card_no
				)
			END
		)
	FROM
		t_tenant_info AS ten,
		t_card_info AS tc
	WHERE
		tc.branch_id = ten.branchid
	AND tc.cardno = tms.card_no  LIMIT 1
);

UPDATE t_system_log AS tsl
SET tsl.card_no = (
	SELECT
		(
			CASE
			WHEN LOCATE(
				ten.tenantid,
				REPLACE (
					LTRIM(
						REPLACE (tsl.card_no, "0", " ")
					),
					" ",
					"0"
				)
			) = 0 THEN
				CONCAT(
					SUBSTRING(
						'000000000000000000000000000000'
						FROM
							1 FOR (
								30 - LENGTH(
									CONCAT(
										ten.tenantid,
										REPLACE (
											LTRIM(
												REPLACE (tsl.card_no, "0", " ")
											),
											" ",
											"0"
										)
									)
								)
							)
					),
					CONCAT(
						ten.tenantid,
						REPLACE (
							LTRIM(
								REPLACE (tsl.card_no, "0", " ")
							),
							" ",
							"0"
						)
					)
				)
			ELSE
				CONCAT(
					SUBSTRING(
						'000000000000000000000000000000'
						FROM
							1 FOR (30 - LENGTH(tsl.card_no))
					),
					tsl.card_no
				)
			END
		)
	FROM
		t_tenant_info AS ten,
		t_card_info AS tc
	WHERE
		tc.branch_id = ten.branchid
	AND tc.cardno = tsl.card_no  LIMIT 1
);
UPDATE t_card_info AS tc
SET tc.cardno = (
	SELECT
		(
			CASE
			WHEN LOCATE(
				ten.tenantid,
				REPLACE (
					LTRIM(REPLACE(tc.cardno, "0", " ")),
					" ",
					"0"
				)
			) = 0 THEN
				CONCAT(
					SUBSTRING(
						'000000000000000000000000000000'
						FROM
							1 FOR (
								30 - LENGTH(
									CONCAT(
										ten.tenantid,
										REPLACE (
											LTRIM(REPLACE(tc.cardno, "0", " ")),
											" ",
											"0"
										)
									)
								)
							)
					),
					CONCAT(
						ten.tenantid,
						REPLACE (
							LTRIM(REPLACE(tc.cardno, "0", " ")),
							" ",
							"0"
						)
					)
				)
			ELSE
				CONCAT(
					SUBSTRING(
						'000000000000000000000000000000'
						FROM
							1 FOR (30 - LENGTH(tc.cardno))
					),
					tc.cardno
				)
			END
		)
	FROM
		t_tenant_info AS ten
	WHERE
		tc.branch_id = ten.branchid  LIMIT 1
);


-- 新增加绑定实体卡存储过程
DROP PROCEDURE IF EXISTS update_card_entity;
CREATE PROCEDURE update_card_entity(IN `tenantId` varchar(255),IN `branchId` varchar(255),IN `entityCardNo` varchar(255),IN `cardNo` varchar(255))
BEGIN
   UPDATE t_card_account as tca    SET  tca.card_no =`entityCardNo`   WHERE  tca.card_no=`cardNo` ;
   UPDATE t_card_info    as tci    SET  tci.cardno  =`entityCardNo`,tci.card_type=1 WHERE  tci.cardno=`cardNo`  AND  tci.branch_id=`branchId` ;
   UPDATE t_deal_detail  as tdd    SET  tdd.card_no =`entityCardNo`   WHERE  tdd.card_no=`cardNo` ;
   UPDATE t_deal_master  as tdm    SET  tdm.card_no =`entityCardNo`   WHERE  tdm.card_no=`cardNo`  AND tdm.branch_id=`branchId` ;
   UPDATE t_member_card  as tmc    SET  tmc.cardno  =`entityCardNo`   where  tmc.cardno=`cardNo` ;
   UPDATE t_member_service as tms  SET  tms.card_no =`entityCardNo`   WHERE  tms.card_no=`cardNo`  AND tms.branch_id=`branchId` ;
   UPDATE t_system_log      as tsl SET  tsl.card_no =`entityCardNo`   WHERE  tsl.card_no=`cardNo` ;
END
-- 增加索引提高查询速度
 ALTER TABLE t_card_account ADD INDEX  t_c_a_inx (card_no);
 ALTER TABLE t_card_info ADD INDEX  t_c_i_inx(cardno);
 ALTER TABLE t_member_card ADD INDEX  t_m_c_inx(cardno);

