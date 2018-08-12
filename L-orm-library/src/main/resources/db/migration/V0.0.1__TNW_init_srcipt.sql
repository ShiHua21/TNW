-- 组织机构表
DROP TABLE IF EXISTS `org`;
CREATE TABLE `org` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '组织机构ID',
  `parent_id` int(11) NOT NULL DEFAULT '0' COMMENT '组织机构父ID',
  `children_num` int(11) NOT NULL DEFAULT '0' COMMENT '辖下组织机构数量',
  `depth` int(11) NOT NULL DEFAULT '1' COMMENT '当前组织机构层级',
  `seq` int(11) NOT NULL DEFAULT '0' COMMENT '索引',
  `description` text COMMENT '备注',
  `branch_no` varchar(255) NOT NULL DEFAULT '' COMMENT '机构编码',
  `org_code` varchar(255) NOT NULL DEFAULT '0' COMMENT '内部编码',
  `branch_nm` varchar(255) NOT NULL COMMENT '机构名称',
  `created_user_id` int(11) NOT NULL COMMENT '创建用户ID',
  `created_time` int(11) unsigned NOT NULL COMMENT '创建时间',
  `last_upd_user_id` int(11) NOT NULL DEFAULT '0' COMMENT '最后修改人',
  `last_upd_time` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '最后更新时间',
  `sync_id` int(11) NOT NULL DEFAULT '0' COMMENT '同步部门ID',
  `tx_mt` varchar(8) NOT NULL DEFAULT '' COMMENT '数据日期',
  `tx_dt` varchar(8) NOT NULL DEFAULT '' COMMENT '导入时间',
  `branch_nm_shot` varchar(255) NOT NULL DEFAULT '' COMMENT '机构名称缩写',
  `branch_typ` varchar(8) NOT NULL DEFAULT '' COMMENT '机构类型',
  `lead_branch_no` varchar(32) NOT NULL DEFAULT '' COMMENT '管辖行号',
  `address` varchar(255) NOT NULL DEFAULT '' COMMENT '地址',
  `longitude` varchar(255) NOT NULL DEFAULT '' COMMENT '经度',
  `practice_dt` varchar(255) NOT NULL DEFAULT '' COMMENT '开业时间',
  `business_tm` varchar(255) NOT NULL DEFAULT '' COMMENT '营业时间',
  `decorate_dt` varchar(255) NOT NULL DEFAULT '' COMMENT '装修时间',
  `branch_area` varchar(255) NOT NULL DEFAULT '' COMMENT '网点面积',
  `employees_no` varchar(255) NOT NULL DEFAULT '' COMMENT '员工数',
  `parking_no` varchar(255) NOT NULL DEFAULT '' COMMENT '停车位个数',
  `self_help_equipment` varchar(255) NOT NULL DEFAULT '' COMMENT '',
  `property_rights_typ` varchar(255) NOT NULL DEFAULT '' COMMENT '网点性质',
  `business_typ` varchar(255) NOT NULL DEFAULT '' COMMENT '业务范围',
  `electronic_equipment` varchar(255) NOT NULL DEFAULT '' COMMENT '电子设备',
  `tel_no` varchar(255) NOT NULL DEFAULT '' COMMENT '网点电话',
  `branch_lead` varchar(255) NOT NULL DEFAULT '' COMMENT '网点负责人',
  `yitiji` varchar(32) NOT NULL DEFAULT '' COMMENT '一体机',
  `qukuanji` varchar(255) NOT NULL DEFAULT '' COMMENT '取款机',
  `branch_pic` varchar(1000) NOT NULL DEFAULT '' COMMENT '图片',
  `branch_special` varchar(255) NOT NULL DEFAULT '' COMMENT '特殊网点',
  `enabled` varchar(8) NOT NULL DEFAULT '' COMMENT '',
  `increacenum` varchar(20) NOT NULL DEFAULT '' COMMENT '',
  `branch_typ1` varchar(20) NOT NULL DEFAULT '' COMMENT '',
  `area_typ` varchar(20) NOT NULL DEFAULT '' COMMENT '区域',
  `temporarily` varchar(20) NOT NULL DEFAULT '' COMMENT '',
  `dmeg` varchar(20) NOT NULL DEFAULT '' COMMENT '',
  `branch_lead_no` varchar(20) NOT NULL DEFAULT '' COMMENT '网点负责人编号',
  `flag` varchar(20) NOT NULL DEFAULT '' COMMENT '区域划分标志（本地C,4 |Y,1|Z,3|异地Y,2 ）',
  PRIMARY KEY (`id`),
  UNIQUE KEY `org_code` (`org_code`),
  UNIQUE KEY `branch_no` (`branch_no`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='组织机构';


-- 用户组织机构关系
DROP TABLE IF EXISTS `user_org`;
CREATE TABLE `user_org` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` int(10) unsigned NOT NULL COMMENT '用户ID',
  `org_id` int(10) unsigned NOT NULL DEFAULT '1' COMMENT '组织机构id',
  `org_code` varchar(255) NOT NULL DEFAULT '1.' COMMENT '组织机构内部编码',
  `created_user_id` int(11) NOT NULL COMMENT '创建用户ID',
  `created_time` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '创建时间',
  `last_upd_user_id` int(11) NOT NULL DEFAULT '0' COMMENT '最后修改人',
  `last_upd_time` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '最后更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COMMENT='用户组织机构关系';

-- 用户表
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `luuserid` varchar(37) DEFAULT '' NOT NULL  COMMENT '用户ID--工号',
  `luusername` varchar(45) DEFAULT '' NOT NULL COMMENT '用户名',
  `luemail` varchar(67) DEFAULT '' COMMENT '用户邮箱',
  `lupwd` varchar(80) DEFAULT '' NOT NULL COMMENT '用户密码',
  `luoldpwd` varchar(80) DEFAULT '' NOT NULL COMMENT 'old密码',
  `lumobile` varchar(90) NOT NULL DEFAULT '' COMMENT '手机',
     `luphone` varchar(45) NOT NULL DEFAULT '' COMMENT '电话',
     `luworkercode` varchar(20) NOT NULL DEFAULT '' COMMENT '登录名',
     `luusertype` varchar(1) NOT NULL DEFAULT '' COMMENT '用户类型',
  `luregtime` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '注册时间',
  `lulastlogtime` int(11) NOT NULL DEFAULT '0' COMMENT '最后登录时间',
  `lulastlogip` varchar(64) NOT NULL DEFAULT '' COMMENT '最后登录IP',
  `luheadpic` varchar(255) NOT NULL DEFAULT '' COMMENT '头像',
  `position_id` VARCHAR(50) NOT NULL DEFAULT '0' COMMENT '岗位id',
  `position_name` varchar(255) NOT NULL COMMENT '岗位名称',
  `branch_no` varchar(50)  NOT NULL DEFAULT '0' COMMENT '机构号',
  `branch_nm` varchar(255) NOT NULL COMMENT '机构名称',
   pointpraise_no varchar(8) DEFAULT "0" COMMENT '点赞数',
   message_no varchar(8) DEFAULT "0" COMMENT '留言数',
   comment_no varchar(8) DEFAULT "0" COMMENT '评论数',
  `lubrowsertype` varchar(32) DEFAULT '' NOT NULL COMMENT '',
  `luflag` varchar(255) NOT NULL DEFAULT '' COMMENT '标签',
  `luactive` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '是否被禁止',
     `lusex` varchar(3) NOT NULL DEFAULT '' COMMENT '性别',
     `lulogintoken` varchar(255) NOT NULL DEFAULT '' COMMENT '登录token',
     `token` varchar(255) NOT NULL DEFAULT '' COMMENT 'token',
     `luenabled` varchar(1) NOT NULL DEFAULT '' COMMENT '启用',
     `lubrowserpath` varchar(255) NOT NULL DEFAULT '' COMMENT '网址',
     `luhomepage` varchar(150) NOT NULL DEFAULT '' COMMENT '主页',
     `lucorpid` varchar(20) NOT NULL DEFAULT '' COMMENT '公司id',
     `lushopseq` int(11) NOT NULL DEFAULT '0' ,

`luorgid` varchar(255) DEFAULT '' NOT NULL COMMENT '用户角色',
`lumodiuserid` varchar(255) DEFAULT '' NOT NULL COMMENT '用户权限',
`organization_id` varchar(50) NOT NULL DEFAULT '0' COMMENT '所属行',
     `lupagerows` int(11) NOT NULL DEFAULT '0' COMMENT '岗位id',
   lugxtime int(10) unsigned default '0' not null ,

  `last_password_fail_time` int(10) NOT NULL DEFAULT '0'  comment '最后一次密码失败时间',
   last_password_reset_time int(10) unsigned default '0' not null comment '最后一次密码重置时间',
  `approval_status` enum('unapprove','approving','approved','approve_fail') NOT NULL DEFAULT 'unapprove' COMMENT '实名认证状态',
     `updated_time` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '最后更新时间',

  PRIMARY KEY (`luuserid`),
  UNIQUE KEY `luusername` (`luusername`),
  KEY `lumobile_index` (`lumobile`),
  KEY `updatedTime` (`updated_time`),
  KEY `luemail_index` (`luemail`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COMMENT='用户表';


-- 用户属性表
DROP TABLE IF EXISTS `user_profile`;
CREATE TABLE `user_profile` (
  `id` int(10) unsigned NOT NULL COMMENT '用户ID',
  `idcard` varchar(24) NOT NULL DEFAULT '' COMMENT '身份证号码',
  `gender` enum('male','female','secret') NOT NULL DEFAULT 'secret' COMMENT '性别',
  `iam` varchar(255) NOT NULL DEFAULT '' COMMENT '我是谁',
  `birthday` date DEFAULT NULL COMMENT '生日',
  `city` varchar(64) NOT NULL DEFAULT '' COMMENT '城市',
 -- `mobile` varchar(32) NOT NULL DEFAULT '' COMMENT '手机',
  `qq` varchar(32) NOT NULL DEFAULT '' COMMENT 'QQ',
  `signature` text COMMENT '签名',
  `about` text COMMENT '自我介绍',
  `company` varchar(255) NOT NULL DEFAULT '' COMMENT '公司',
  `job` varchar(255) NOT NULL DEFAULT '' COMMENT '工作',
  `school` varchar(255) NOT NULL DEFAULT '' COMMENT '学校',
  `class` varchar(255) NOT NULL DEFAULT '' COMMENT '班级',
  `weibo` varchar(255) NOT NULL DEFAULT '' COMMENT '微博',
  `weixin` varchar(255) NOT NULL DEFAULT '' COMMENT '微信',
  `is_QQ_Public` int(11) NOT NULL DEFAULT '0' COMMENT 'QQ号是否公开',
  `is_Weixin_Public` int(11) NOT NULL DEFAULT '0' COMMENT '微信是否公开',
  `is_Weibo_Public` int(11) NOT NULL DEFAULT '0' COMMENT '微博是否公开',
  `site` varchar(255) NOT NULL DEFAULT '' COMMENT '网站',
  `field1_int` int(11) DEFAULT NULL,
  `field2_int` int(11) DEFAULT NULL,
  `field3_int` int(11) DEFAULT NULL,
  `field4_int` int(11) DEFAULT NULL,
  `field5_int` int(11) DEFAULT NULL,
  `field1_date` date DEFAULT NULL,
  `field2_date` date DEFAULT NULL,
  `field3_date` date DEFAULT NULL,
  `field4_date` date DEFAULT NULL,
  `field5_date` date DEFAULT NULL,
  `field1_float` float(10,2) DEFAULT NULL,
  `field2_float` float(10,2) DEFAULT NULL,
  `field3_float` float(10,2) DEFAULT NULL,
  `field4_float` float(10,2) DEFAULT NULL,
  `field5_float` float(10,2) DEFAULT NULL,
  `Field1_varchar` varchar(1024) DEFAULT NULL,
  `Field2_varchar` varchar(1024) DEFAULT NULL,
  `Field3_varchar` varchar(1024) DEFAULT NULL,
  `Field4_varchar` varchar(1024) DEFAULT NULL,
  `Field5_varchar` varchar(1024) DEFAULT NULL,
  `Field6_varchar` varchar(1024) DEFAULT NULL,
  `Field7_varchar` varchar(1024) DEFAULT NULL,
  `Field8_varchar` varchar(1024) DEFAULT NULL,
  `Field9_varchar` varchar(1024) DEFAULT NULL,
  `Field10_varchar` varchar(1024) DEFAULT NULL,
  `Field1_text` text,
  `Field2_text` text,
  `Field3_text` text,
  `Field4_text` text,
  `Field5_text` text,
  `Field6_text` text,
  `Field7_text` text,
  `Field8_text` text,
  `Field9_text` text,
  `Field10_text` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户属性表';


-- 岗位分组
DROP TABLE IF EXISTS `post_group`;
CREATE TABLE `post_group` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(255) NOT NULL COMMENT '岗位分组名称',
  `seq` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '排序序号',
  `created_user_id` int(11) NOT NULL COMMENT '创建用户ID',
  `created_time` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '创建时间',
  `last_upd_user_id` int(11) NOT NULL DEFAULT '0' COMMENT '最后修改人',
  `last_upd_time` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '最后更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='岗位分组';


-- 岗位
DROP TABLE IF EXISTS `post`;
CREATE TABLE `post` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(255) NOT NULL COMMENT '岗位名称',
  `group_id` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '分组ID',
  `seq` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '排序序号',
  `code` varchar(255) NOT NULL DEFAULT '' COMMENT '岗位编码',
  `created_user_id` int(11) NOT NULL COMMENT '创建用户ID',
  `created_time` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '创建时间',
  `last_upd_user_id` int(11) NOT NULL DEFAULT '0' COMMENT '最后修改人',
  `last_upd_time` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '最后更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='岗位';


-- 岗位成员
DROP TABLE IF EXISTS `post_member`;
CREATE TABLE `post_member` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `position_id` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '岗位ID',
  `user_id` int(11) unsigned NOT NULL COMMENT '员工ID',
  `created_user_id` int(11) NOT NULL COMMENT '创建用户ID',
  `created_time` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '创建时间',
  `last_upd_user_id` int(11) NOT NULL DEFAULT '0' COMMENT '最后修改人',
  `last_upd_time` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '最后更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='岗位成员';


-- 用户组
DROP TABLE IF EXISTS `user_group`;
CREATE TABLE `user_group` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户组ID',
  `name` varchar(255) NOT NULL COMMENT '名称',
  `description` text COMMENT '描述',
  `code` varchar(255) NOT NULL DEFAULT '' COMMENT '用户组编码',
  `created_user_id` int(11) NOT NULL COMMENT '创建用户ID',
  `created_time` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '创建时间',
  `last_upd_user_id` int(11) NOT NULL DEFAULT '0' COMMENT '最后修改人',
  `last_upd_time` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '最后更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `code` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COMMENT='用户组';

-- 用户组成员
DROP TABLE IF EXISTS `user_group_member`;
CREATE TABLE `user_group_member` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `group_id` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '用户组ID',
  `member_id` int(11) unsigned NOT NULL COMMENT '成员类型ID',
  `member_type` varchar(32) NOT NULL COMMENT '成员类型',
  `created_user_id` int(11) NOT NULL COMMENT '创建用户ID',
  `created_time` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '创建时间',
  `last_upd_user_id` int(11) NOT NULL DEFAULT '0' COMMENT '最后修改人',
  `last_upd_time` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '最后更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='用户组成员';

-- 角色表
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(32) NOT NULL COMMENT '权限名称',
  `code` varchar(32) NOT NULL COMMENT '权限代码',
  `data` text COMMENT '权限配置(json数组)',
  `created_user_id` int(11) NOT NULL COMMENT '创建用户ID',
  `created_time` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '创建时间',
  `last_upd_user_id` int(11) NOT NULL DEFAULT '0' COMMENT '最后修改人',
  `last_upd_time` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '最后更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='角色表';

-- web_url资源表(tree)
DROP TABLE IF EXISTS `web_url_resource`;
CREATE TABLE `web_url_resource` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `url` varchar(255) NOT NULL COMMENT '地址',
  `code` varchar(255) NOT NULL COMMENT '编码',
  `description` varchar(32) NOT NULL COMMENT '功能描述',
  `parent_id` int(11) NOT NULL DEFAULT '0' COMMENT '父ID',
  `children_num` int(11) NOT NULL DEFAULT '0' COMMENT '子节点数量',
  `depth` int(11) NOT NULL DEFAULT '1' COMMENT 'tree层级',
  `seq` int(11) NOT NULL DEFAULT '0' COMMENT '索引',
  `type` enum('URL','CATALOG_NAME') default 'CATALOG_NAME' not null comment '资源类型',
  `created_time` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '创建时间',
  `created_user_id` int(10) unsigned NOT NULL COMMENT '创建用户ID',
  `updated_time` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '更新时间',
  `updated_user_id` int(10) unsigned NOT NULL COMMENT '更新用户ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='web_url资源表';
create index web_url_resource_type_index on `web_url_resource` (`type`);
create index web_url_resource_code_index on `web_url_resource` (`code`);

-- 系统操作日志表
DROP TABLE IF EXISTS `log`;
CREATE TABLE `log` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '系统日志ID',
  `user_id` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '操作人ID',
  `module` varchar(32) NOT NULL COMMENT '日志所属模块',
  `action` varchar(32) NOT NULL COMMENT '日志所属操作类型',
  `message` text NOT NULL COMMENT '日志内容',
  `data` text COMMENT '日志数据',
  `ip` varchar(255) NOT NULL COMMENT '日志记录IP',
  `created_time` int(10) unsigned NOT NULL COMMENT '日志发生时间',
  `level` char(10) NOT NULL COMMENT '日志等级',
  PRIMARY KEY (`id`),
  KEY `userId` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='系统操作日志表';

-- 任务表
DROP TABLE IF EXISTS `tasks`;
create table `tasks`
(
	id int(10) unsigned auto_increment primary key,
	name varchar(255) not null comment '任务名称',
	begin_time int(10) unsigned default '0' not null comment '开始时间',
	end_time int(10) unsigned default '0' not null comment '结束时间',
  elapsed bigint(20) DEFAULT '0' comment '执行耗时,单位:毫秒',
  trigged_user_name  varchar(64) not null comment '触发执行的用户(排程触发时为:scheduled)',
  status enum('success', 'failure', 'running','nothing') default 'nothing' not null comment '执行状态',
  result longtext null comment '执行结果(json格式)'
);
create index tasks_status_index on `tasks` (`status`);
create index tasks_trigged_user_name_index on `tasks` (`trigged_user_name`);
create index tasks_name_index on `tasks` (`name`);
ALTER TABLE `tasks` COMMENT '任务表';

-- 用户名序号
CREATE TABLE `username_sequence` (
  `id` bigint(20) unsigned NOT NULL auto_increment,
  `replace_key` char(10) NOT NULL default '',
  PRIMARY KEY (`id`),
  UNIQUE KEY `replace_key` (`replace_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=10001;


-- 用户数据
-- { 管理员
--   "username": "admin",
--   "lupwd": "qwer0asdf",
--   "luemail": "kkii_55@163.com"
-- }
--INSERT INTO user (luuserid, luemail, lumobile, lupwd,  locale, uri, luusername, title, tags, type, point,  small_avatar, medium_avatar, large_avatar, luemail_verified, setup, roles, promoted, promoted_seq, promoted_time, locked, lock_deadline, consecutive_password_error_times, last_password_fail_time, last_password_reset_time, login_time, login_ip, login_session_id, approval_time, approval_status, new_message_num, new_notification_num, created_ip, created_time, updated_time,  registered_way, pwd_init, position_id, org_ids, org_codes) VALUES (1, '18501937174@163.com', '+86 15313633563', '8abd3be86d4e203be838c1cef5d5a0ba699e9fb114d15aaa29e43fa40c3bf50307ff25ef47acf510', '', NULL, '', 'admin', '', '', 'default', 0, 0, '', '', '', 0, 1, 'ROLE_SUPER_ADMIN', 0, 0, 0, 0, 0, 0, 0, 0, 0, '', '', 0, 'unapprove', 0, 0, '', 1517466271, 0, NULL, '', 0, 0, 0, 0, 0);
--INSERT INTO user (luuserid, luemail, lumobile, lupwd,  locale, uri, luusername, title, tags, type, point,  small_avatar, medium_avatar, large_avatar, luemail_verified, setup, roles, promoted, promoted_seq, promoted_time, locked, lock_deadline, consecutive_password_error_times, last_password_fail_time, last_password_reset_time, login_time, login_ip, login_session_id, approval_time, approval_status, new_message_num, new_notification_num, created_ip, created_time, updated_time,  registered_way, pwd_init, position_id, org_ids, org_codes) VALUES (2, '18501937174@163.com', '+86 15313633563', '91dc6e3c9ce88fc2ccc80feb0fa58790b7dc6c9bb34339ee6f03a6f062b1472ed1710626fa4f2b9f', '', NULL, '', 'test' , '', '', 'default', 0, 0, '', '', '', 0, 1, 'ROLE_USER'       , 0, 0, 0, 0, 0, 0, 0, 0, 0, '', '', 0, 'unapprove', 0, 0, '', 1517466271, 0, NULL, '', 0, 0, 0, 1, 0);

-- 角色数据



INSERT INTO  role (id, name, code, data, created_time, created_user_id,last_upd_user_id ,last_upd_time) VALUES (3, '匿名用户', 'ROLE_ANONYMOUS', '["auth_login","auth_register"]', 1516342692, 1, 1,1516342692);
INSERT INTO  role (id, name, code, data, created_time, created_user_id,last_upd_user_id ,last_upd_time) VALUES (2, '用户', 'ROLE_USER', '["auth_login","auth_register","auth_refresh"]', 1516342692, 1, 1,1516342692);
INSERT INTO  role (id, name, code, data, created_time, created_user_id,last_upd_user_id ,last_upd_time) VALUES (1, '超级管理员', 'ROLE_SUPER_ADMIN', '["auth_login","auth_register","auth_refresh"]', 1516342692, 1, 1,1516342692);
INSERT INTO  role (id, name, code, data, created_time, created_user_id,last_upd_user_id ,last_upd_time) VALUES (4, '部门管理员', 'ROLE_DEPARTMENT_ADMIN', '["auth_login","auth_register","auth_refresh"]', 1516342692, 1, 1,1516342692);



-- 组织架构数据
-- INSERT INTO org(`id`,`name`,`parent_id`,`children_num`,`depth`,`seq`,`description`,`code`,`org_code`,`created_user_id`,`created_time`,`last_upd_user_id`,`last_upd_time`,`sync_id`) VALUES ('1','全站','0','0','1','1','全站所有组织部门','BANK','1.','1','1516342692','1','1516586945','0');

-- INSERT INTO web_url_resource (id,code,url, description, parent_id, children_num, depth, seq, type, created_time, created_user_id, updated_time, updated_user_id) VALUES (1,'','', '***系统', 0, 1, 1, 1, 'CATALOG_NAME', 1516342692, 1, 1516342692, 1);
-- ---- 1)认证
-- INSERT INTO web_url_resource (id,code,url, description, parent_id, children_num, depth, seq, type, created_time, created_user_id, updated_time, updated_user_id) VALUES (2,'','', '认证', 1, 3, 2, 1, 'CATALOG_NAME', 1516342692, 1, 1516342692, 1);
-- INSERT INTO web_url_resource (id,code,url, description, parent_id, children_num, depth, seq, type, created_time, created_user_id, updated_time, updated_user_id) VALUES (3,'auth_login','/auth', '登陆', 2, 0, 3, 1, 'URL', 1516342692, 1, 1516342692, 1);
-- INSERT INTO web_url_resource (id,code,url, description, parent_id, children_num, depth, seq, type, created_time, created_user_id, updated_time, updated_user_id) VALUES (4,'auth_register','/auth/register/**', '注册', 0, 1, 3, 2, 'URL', 1516342692, 1, 1516342692, 1);
-- INSERT INTO web_url_resource (id,code,url, description, parent_id, children_num, depth, seq, type, created_time, created_user_id, updated_time, updated_user_id) VALUES (5,'auth_refresh','/auth/refresh', '刷新令牌', 0, 1, 3, 3, 'URL', 1516342692, 1, 1516342692, 1);
--

-- 1.访问web资源数据
INSERT INTO web_url_resource (id,code,url, description, parent_id, children_num, depth, seq, type, created_time, created_user_id, updated_time, updated_user_id) VALUES (1,'','', '***系统', 0, 1, 1, 1, 'CATALOG_NAME', 1516342692, 1, 1516342692, 1);
---- 1)认证
INSERT INTO web_url_resource (id,code,url, description, parent_id, children_num, depth, seq, type, created_time, created_user_id, updated_time, updated_user_id) VALUES (2,'','', '认证', 1, 3, 2, 1, 'CATALOG_NAME', 1516342692, 1, 1516342692, 1);
INSERT INTO web_url_resource (id,code,url, description, parent_id, children_num, depth, seq, type, created_time, created_user_id, updated_time, updated_user_id) VALUES (3,'auth_login','/v*/auth', '登陆', 2, 0, 3, 1, 'URL', 1516342692, 1, 1516342692, 1);
INSERT INTO web_url_resource (id,code,url, description, parent_id, children_num, depth, seq, type, created_time, created_user_id, updated_time, updated_user_id) VALUES (4,'auth_register','/v*/auth/register/**', '注册', 2, 0, 3, 2, 'URL', 1516342692, 1, 1516342692, 1);
INSERT INTO web_url_resource (id,code,url, description, parent_id, children_num, depth, seq, type, created_time, created_user_id, updated_time, updated_user_id) VALUES (5,'auth_refresh','/v*/auth/refresh', '刷新令牌', 2, 0, 3, 3, 'URL', 1516342692, 1, 1516342692, 1);


-- 系统管理,访问权限
INSERT INTO web_url_resource (id,code,url, description, parent_id, children_num, depth, seq, type, created_time, created_user_id, updated_time, updated_user_id) VALUES (6,'','', '后台管理', 1, 1, 2, 2, 'CATALOG_NAME', 1516342692, 1, 1516342692, 1);
INSERT INTO web_url_resource (id,code,url, description, parent_id, children_num, depth, seq, type, created_time, created_user_id, updated_time, updated_user_id) VALUES (7,'','', '用户', 6, 3, 3, 1, 'CATALOG_NAME', 1516342692, 1, 1516342692, 1);
INSERT INTO web_url_resource (id,code,url, description, parent_id, children_num, depth, seq, type, created_time, created_user_id, updated_time, updated_user_id) VALUES (8,'org_manage','/v*/org/**','组织机构管理', 7, 0, 4, 1, 'URL', 1516342692, 1, 1516342692, 1);

INSERT INTO web_url_resource (id,code,url, description, parent_id, children_num, depth, seq, type, created_time, created_user_id, updated_time, updated_user_id) VALUES (9,'','','岗位管理', 7, 2, 4, 1, 'CATALOG_NAME', 1516342692, 1, 1516342692, 1);
INSERT INTO web_url_resource (id,code,url, description, parent_id, children_num, depth, seq, type, created_time, created_user_id, updated_time, updated_user_id) VALUES (10,'post_group_manager','/v*/post/group/**','岗位分组管理', 9, 0, 5, 1, 'URL', 1516342692, 1, 1516342692, 1);
INSERT INTO web_url_resource (id,code,url, description, parent_id, children_num, depth, seq, type, created_time, created_user_id, updated_time, updated_user_id) VALUES (11,'post_manager','/v*/post/**','岗位管理', 9, 2, 5, 2, 'URL', 1516342692, 1, 1516342692, 1);
INSERT INTO web_url_resource (id,code,url, description, parent_id, children_num, depth, seq, type, created_time, created_user_id, updated_time, updated_user_id) VALUES (12,'','','用户管理管理', 7, 3, 4, 2, 'CATALOG_NAME', 1516342692, 1, 1516342692, 1);
INSERT INTO web_url_resource (id,code,url, description, parent_id, children_num, depth, seq, type, created_time, created_user_id, updated_time, updated_user_id) VALUES (13,'user_manage_list','/v*/users**','用户列表', 12, 0, 5, 1, 'URL', 1516342692, 1, 1516342692, 1);
INSERT INTO web_url_resource (id,code,url, description, parent_id, children_num, depth, seq, type, created_time, created_user_id, updated_time, updated_user_id) VALUES (14,'user_manage_set_org','/v*/user/*/setOrg','设置用户组织机构', 12, 0, 5, 2, 'URL', 1516342692, 1, 1516342692, 1);
INSERT INTO web_url_resource (id,code,url, description, parent_id, children_num, depth, seq, type, created_time, created_user_id, updated_time, updated_user_id) VALUES (15,'user_manage_add','/v*/user','新增用户', 12, 0, 5, 3, 'URL', 1516342692, 1, 1516342692, 1);


-- 给超级管理员增加:
-- 机构管理权限
-- 用户管理-用户列表权限
-- 岗位分组权限
-- 岗位权限
-- 用户管理-用户新增
update role a set a.DATA = '["auth_login","auth_register","auth_refresh","org_manage","post_group_manager","post_manager","user_manage_list","user_manage_set_org","user_manage_add"]' where a.id = 1;

ALTER TABLE `user_group_member` MODIFY `member_type` enum('user','org','post') NOT NULL COMMENT '成员类型';


-- 系统管理,访问权限
INSERT INTO web_url_resource (id,code,url, description, parent_id, children_num, depth, seq, type, created_time, created_user_id, updated_time, updated_user_id) VALUES (16,'user_manage_set_post','/v*/user/*/setPost','设置用户岗位信息', 12, 0, 5, 3, 'URL', 1516342692, 1, 1516342692, 1);


-- 用户管理-用户设置岗位
-- 用户管理 新增childnum 设置岗位信息
update web_url_resource a set a.children_num = 4 where a.id = 12;

update role a set a.DATA = '["auth_login","auth_register","auth_refresh","org_manage","post_group_manager","post_manager","user_manage_list","user_manage_set_org","user_manage_add","user_manage_set_post"]' where a.id = 1;


-- 设置批量更新组织机构,岗位权限
INSERT INTO web_url_resource (id,code,url, description, parent_id, children_num, depth, seq, type, created_time, created_user_id, updated_time, updated_user_id) VALUES (17,'user_manage_batch_set_org','/v*/user/setOrg','设置用户组织机构(批量)', 12, 0, 5, 4, 'URL', 1516342692, 1, 1516342692, 1);
INSERT INTO web_url_resource (id,code,url, description, parent_id, children_num, depth, seq, type, created_time, created_user_id, updated_time, updated_user_id) VALUES (18,'user_manage_batch_set_post','/v*/user/setPost','设置用户岗位(批量)', 12, 0, 6, 4, 'URL', 1516342692, 1, 1516342692, 1);



-- 更新用户管理 子节点数量
update web_url_resource a set a.children_num = 6 where a.id = 12;

-- 给用户admin增加 "user_manage_batch_set_org","user_manage_batch_set_post" 权限
update role a set a.DATA = '["auth_login","auth_register","auth_refresh","org_manage","post_group_manager","post_manager","user_manage_list","user_manage_set_org","user_manage_add","user_manage_set_post","user_manage_batch_set_org","user_manage_batch_set_post"]' where a.id = 1;


-- web_url_resource 增加 wur_code
ALTER TABLE `web_url_resource` ADD COLUMN `wur_code` VARCHAR(50) NOT NULL DEFAULT '0' AFTER `id`;
-- web_url_resource 增加唯一索引 wur_code
ALTER TABLE `web_url_resource` ADD index `wur_code_idx` (`wur_code`) ;

-- web_url_resource 更新 wur_code
update web_url_resource set wur_code = '0.1.' where id = 1;
update web_url_resource set wur_code = '0.1.2.' where id = 2;
update web_url_resource set wur_code = '0.1.2.3.' where id = 3;
update web_url_resource set wur_code = '0.1.2.4.' where id = 4;
update web_url_resource set wur_code = '0.1.2.5.' where id = 5;
update web_url_resource set wur_code = '0.1.6.' where id = 6;
update web_url_resource set wur_code = '0.1.6.7.' where id = 7;
update web_url_resource set wur_code = '0.1.6.7.8.' where id = 8;
update web_url_resource set wur_code = '0.1.6.7.9.' where id = 9;
update web_url_resource set wur_code = '0.1.6.7.9.10.' where id = 10;
update web_url_resource set wur_code = '0.1.6.7.9.11.' where id = 11;
update web_url_resource set wur_code = '0.1.6.7.12.' where id = 12;
update web_url_resource set wur_code = '0.1.6.7.12.13.' where id = 13;
update web_url_resource set wur_code = '0.1.6.7.12.14.' where id = 14;
update web_url_resource set wur_code = '0.1.6.7.12.15.' where id = 15;
update web_url_resource set wur_code = '0.1.6.7.12.16.' where id = 16;
update web_url_resource set wur_code = '0.1.6.7.12.17.' where id = 17;
update web_url_resource set wur_code = '0.1.6.7.12.18.' where id = 18;

-- 修复 web_url_resource 数据
update web_url_resource set depth = 5 where id = 18;
update web_url_resource set seq = 1 where id = 13;
update web_url_resource set seq = 2 where id = 14;
update web_url_resource set seq = 3 where id = 15;
update web_url_resource set seq = 4 where id = 16;
update web_url_resource set seq = 5 where id = 17;
update web_url_resource set seq = 6 where id = 18;

--用户表增加 组织机构索引 索引
ALTER TABLE user add index organization_id_index (organization_id);
ALTER TABLE user add index org_position_id_index (position_id);


-- 我的：机构动态表
DROP TABLE IF EXISTS `dw_orgcircle_dynamic`;
CREATE TABLE dw_orgcircle_dynamic
(
    `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '机构动态表id',
    luuserid varchar(37) DEFAULT '' NOT NULL  COMMENT '用户ID--工号' ,
   `luusername` varchar(45) DEFAULT '' NOT NULL COMMENT '用户名',
    branch_no varchar(255) DEFAULT '' COMMENT '机构编码',
    branch_nm varchar(255) DEFAULT '' COMMENT '机构名称',
    tx_tm int(11) NOT NULL DEFAULT '0'  COMMENT '发布时间',
    title varchar(200) DEFAULT '' COMMENT '标题',
    context varchar(2000) DEFAULT '' COMMENT '内容',
    flag varchar(1) DEFAULT '1' COMMENT '发帖评论标识 1：发帖，2：评论或者回复',
    key_fg varchar(30) DEFAULT '' COMMENT '编号',
    pointpraise_no varchar(8) DEFAULT "0" COMMENT '点赞数',
    message_no varchar(8) DEFAULT "0" COMMENT '留言数',
    comment_no varchar(8) DEFAULT "0" COMMENT '评论数',
    pkey_fg varchar(30) DEFAULT '' COMMENT '所属编号 上级编号 只有评论 回复 才有上级',
    picture_adr varchar(1000) DEFAULT '' COMMENT '图片路径',
    ludelete varchar(3) DEFAULT "0" COMMENT '0：可用；1:删除',
    comment varchar(3) DEFAULT "0" COMMENT '0：允许评论；1:不允许评论',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COMMENT='我的动态表';

-- 我的：机构活动表
DROP TABLE IF EXISTS `dw_orgcircle_activity`;
CREATE TABLE dw_orgcircle_activity
(
    `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '机构活动表id',
    luuserid varchar(37) DEFAULT '' NOT NULL  COMMENT '用户ID--工号',
    `luusername` varchar(45) DEFAULT '' NOT NULL COMMENT '用户名',
    `luheadpic` varchar(255) NOT NULL DEFAULT '' COMMENT '用户头像',
    branch_no varchar(255) DEFAULT '' COMMENT '机构编码',
    branch_nm varchar(255) DEFAULT '' COMMENT '机构名称',
    activity_nm varchar(255) DEFAULT '' COMMENT '活动名称',
    activity_ty varchar(255) DEFAULT '' COMMENT '活动种类',
sponsor_nm varchar(255) DEFAULT '' COMMENT '主办方',
person_nm varchar(255) DEFAULT '' COMMENT '负责人',
information varchar(255) DEFAULT '' COMMENT '联系方式',
activity_pe varchar(255) DEFAULT '' COMMENT '活动人数',
activity_ob varchar(255) DEFAULT '' COMMENT '活动对象',
reward varchar(800) DEFAULT '' COMMENT '活动奖励',
    tx_tm int(11) NOT NULL DEFAULT '0'  COMMENT '活动发布时间',
    picture_adr varchar(1000) DEFAULT '' COMMENT '图片路径',
    title varchar(200) DEFAULT '' COMMENT '标题',
    context varchar(2000) DEFAULT '' COMMENT '内容',
    activity_ktm  int(11) NOT NULL DEFAULT '0'  COMMENT '活动开始时间',
    activity_jtm  int(11) NOT NULL DEFAULT '0'  COMMENT '活动结束时间',
    activity_place  varchar(200) NOT NULL DEFAULT ''  COMMENT '活动地点',
    activity_label  varchar(200) NOT NULL DEFAULT ''  COMMENT '活动标签/讲座;理财;团建活动;开门红;其他',
    participate   varchar(3) NOT NULL DEFAULT '0'  COMMENT '是否参与活动/0:未参与,1:已参与',
    participat_agencies  varchar(1000) NOT NULL DEFAULT ''  COMMENT '已参与机构',
    participat_agencies_id  varchar(300) NOT NULL DEFAULT ''  COMMENT '已参与机构id',
    pointpraise_no varchar(8) DEFAULT "0" COMMENT '点赞数',
    message_no varchar(8) DEFAULT "0" COMMENT '留言数',
    comment_no varchar(8) DEFAULT "0" COMMENT '评论数',
    key_fg varchar(30) DEFAULT '' COMMENT '编号',
    pkey_fg varchar(30) DEFAULT '' COMMENT '所属编号 上级编号 只有评论 回复 才有上级',
    flag varchar(1) DEFAULT '' COMMENT '发帖评论标识 1：发帖，2：评论或者回复',
    ludelete varchar(3) DEFAULT "0" COMMENT '0：可用；1:删除',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COMMENT='机构活动表';

-- 我的：已参与活动
DROP TABLE IF EXISTS `dw_old_activity`;
CREATE TABLE dw_old_activity
(
    `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '已参与活动表id',
    luuserid varchar(37) DEFAULT '' NOT NULL  COMMENT '用户ID--工号',
    `luusername` varchar(45) DEFAULT '' NOT NULL COMMENT '用户名',
    `luheadpic` varchar(255) NOT NULL DEFAULT '' COMMENT '用户头像',
    branch_no varchar(255) DEFAULT '' COMMENT '机构编码',
    branch_nm varchar(255) DEFAULT '' COMMENT '机构名称',
    tx_tm int(11) NOT NULL DEFAULT '0'  COMMENT '活动发布时间',
    picture_adr varchar(1000) DEFAULT '' COMMENT '图片路径',
    title varchar(200) DEFAULT '' COMMENT '标题',
    context varchar(2000) DEFAULT '' COMMENT '内容',
    activity_ktm  int(11) NOT NULL DEFAULT '0'  COMMENT '活动开始时间',
    activity_jtm  int(11) NOT NULL DEFAULT '0'  COMMENT '活动结束时间',
    activity_place  varchar(200) NOT NULL DEFAULT ''  COMMENT '活动地点',
    activity_label  varchar(200) NOT NULL DEFAULT ''  COMMENT '活动标签/讲座;理财;团建活动;开门红;其他',
    participate   varchar(3) NOT NULL DEFAULT '0'  COMMENT '是否参与活动/0:未参与,1:已参与',
    participat_agencies  varchar(1000) NOT NULL DEFAULT ''  COMMENT '已参与机构',
    participat_agencies_id  varchar(300) NOT NULL DEFAULT ''  COMMENT '已参与机构id',
    pointpraise_no varchar(8) DEFAULT "0" COMMENT '点赞数',
    message_no varchar(8) DEFAULT "0" COMMENT '留言数',
    comment_no varchar(8) DEFAULT "0" COMMENT '评论数',
    key_fg varchar(30) DEFAULT '' COMMENT '编号',
    pkey_fg varchar(30) DEFAULT '' COMMENT '所属编号 上级编号 只有评论 回复 才有上级',
    flag varchar(1) DEFAULT '' COMMENT '发帖评论标识 1：发帖，2：评论或者回复',
    ludelete varchar(3) DEFAULT "0" COMMENT '0：可用；1:删除',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COMMENT='已参与活动';

--ta留言板
DROP TABLE IF EXISTS `dw_other_message`;
CREATE TABLE dw_other_message
(
    `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '留言板id',
    luuserid varchar(37) DEFAULT '' NOT NULL COMMENT '留言者id',
    `luusername` varchar(45) DEFAULT '' NOT NULL COMMENT '用户名',
    `luheadpic` varchar(255) NOT NULL DEFAULT '' COMMENT '头像',
    `org_code` varchar(255) NOT NULL DEFAULT '0' COMMENT '内部编码',
    `branch_no` varchar(255) NOT NULL DEFAULT '' COMMENT '机构编码',
    `branch_nm` varchar(255) NOT NULL COMMENT '机构名称',
    `position_id` int(11) NOT NULL DEFAULT '0' COMMENT '岗位id',
    `position_nm` varchar(255) NOT NULL COMMENT '岗位名称',
    target_user varchar(37) DEFAULT '' COMMENT '被留言者 工号',
    `tauser_nm` varchar(255) NOT NULL COMMENT '被留言名字',
    `tabranch_nm` varchar(255) NOT NULL COMMENT '被留言机构名称',
    `tabranch_no` varchar(255) NOT NULL DEFAULT '' COMMENT '被留言机构编码',
    tx_tm int(11) NOT NULL DEFAULT '0'  COMMENT '留言时间',
    context varchar(500) DEFAULT '' COMMENT '留言内容',
    context_pc varchar(1000) DEFAULT '' COMMENT '留言图片',
    ludelete varchar(3) DEFAULT "0" COMMENT '0：可用；1:删除',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COMMENT='留言板';


--wo留言板
DROP TABLE IF EXISTS `dw_my_message`;
CREATE TABLE dw_my_message
(
    `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '留言板id',
    luuserid varchar(37) DEFAULT '' NOT NULL COMMENT '留言者id',
    `luusername` varchar(45) DEFAULT '' NOT NULL COMMENT '用户名',
    `luheadpic` varchar(255) NOT NULL DEFAULT '' COMMENT '头像',
    `org_code` varchar(255) NOT NULL DEFAULT '0' COMMENT '内部编码',
    `branch_no` varchar(255) NOT NULL DEFAULT '' COMMENT '机构编码',
    `branch_nm` varchar(255) NOT NULL COMMENT '机构名称',
    `position_id` int(11) NOT NULL DEFAULT '0' COMMENT '岗位id',
    `position_nm` varchar(255) NOT NULL COMMENT '岗位名称',
    target_user varchar(37) DEFAULT '' COMMENT '被留言者 工号',
    `tauser_nm` varchar(255) NOT NULL COMMENT '被留言名字',
    `tabranch_nm` varchar(255) NOT NULL COMMENT '被留言机构名称',
    `tabranch_no` varchar(255) NOT NULL DEFAULT '' COMMENT '被留言机构编码',
    tx_tm int(11) NOT NULL DEFAULT '0'  COMMENT '留言时间',
   context varchar(500) DEFAULT '' COMMENT '留言内容',
    context_pc varchar(1000) DEFAULT '' COMMENT '留言图片',
    ludelete varchar(3) DEFAULT "0" COMMENT '0：可用；1:删除',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COMMENT='留言板';


--动态评论
DROP TABLE IF EXISTS `dw_comment`;
CREATE TABLE dw_comment
(
    `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '评论表id',
    activity_id varchar(37) DEFAULT '' NOT NULL COMMENT '活动表id',
    dynamic_id varchar(37) DEFAULT '' NOT NULL COMMENT '动态表id',
    luuserid varchar(37) DEFAULT '' NOT NULL COMMENT '留言者',
    `luusername` varchar(45) DEFAULT '' NOT NULL COMMENT '用户名',
    `luheadpic` varchar(255) NOT NULL DEFAULT '' COMMENT '头像',
    `org_code` varchar(255) NOT NULL DEFAULT '0' COMMENT '内部编码',
    `branch_no` varchar(255) NOT NULL DEFAULT '' COMMENT '机构编码',
    `position_id` int(11) NOT NULL DEFAULT '0' COMMENT '岗位id',
    `branch_nm` varchar(255) NOT NULL COMMENT '机构名称',
    target_user varchar(37) DEFAULT '' COMMENT '被留言者 工号',
    tx_tm int(11) NOT NULL DEFAULT '0'  COMMENT '留言时间',
    title varchar(200) DEFAULT '' COMMENT '标题',
    context varchar(2000) DEFAULT '' COMMENT '内容',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COMMENT='动态评论';

--动态点赞
DROP TABLE IF EXISTS `dw_fabulous`;
CREATE TABLE dw_fabulous
(
    `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '点赞表id',
    activity_id varchar(37) DEFAULT '' NOT NULL COMMENT '活动表id',
    dynamic_id varchar(37) DEFAULT '' NOT NULL COMMENT '动态表id',
    luuserid varchar(37) DEFAULT '' NOT NULL COMMENT '留言者',
    `luusername` varchar(45) DEFAULT '' NOT NULL COMMENT '用户名',
    `luheadpic` varchar(255) NOT NULL DEFAULT '' COMMENT '头像',
    `org_code` varchar(255) NOT NULL DEFAULT '0' COMMENT '内部编码',
    `branch_no` varchar(255) NOT NULL DEFAULT '' COMMENT '机构编码',
    `position_id` int(11) NOT NULL DEFAULT '0' COMMENT '岗位id',
    `branch_nm` varchar(255) NOT NULL COMMENT '机构名称',
    target_user varchar(37) DEFAULT '' COMMENT '被留言者 工号',
    tx_tm int(11) NOT NULL DEFAULT '0'  COMMENT '留言时间',
    title varchar(200) DEFAULT '' COMMENT '标题',
    context varchar(2000) DEFAULT '' COMMENT '内容',
    good varchar(3) DEFAULT '0' COMMENT '是否点赞 0:不赞,1:赞',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COMMENT='动态点赞';

-- file_store;
CREATE TABLE file_store (
  id int(11) NOT NULL AUTO_INCREMENT COMMENT  'ID',
  group_id int(11) DEFAULT 0 NOT NULL COMMENT '上传文件组ID',
  user_id int(11) DEFAULT 0 NOT NULL COMMENT '上传人ID',
  file_uri VARCHAR(2000) DEFAULT 'nil' NOT NULL COMMENT '文件URI',
  file_mime VARCHAR(255) DEFAULT 'nil' NOT NULL COMMENT '文件MIME',
  status int(3) DEFAULT 0 NOT NULL,
  file_size bigint(18) DEFAULT 0 NOT NULL COMMENT '文件大小',
  created_user_id int(11) DEFAULT 0 NOT NULL COMMENT '创建用户ID',
  created_time int(18) DEFAULT 0 NOT NULL COMMENT '创建时间',
  last_upd_user_id int(11) DEFAULT 0 NOT NULL COMMENT '最后修改人',
  last_upd_time int(18) DEFAULT 0 NOT NULL COMMENT '最后更新时间',
  upload_file_id int(11) NULL COMMENT 'upload_file_id',
  CONSTRAINT pd_file_store_pk PRIMARY KEY(id)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COMMENT='上传文件';

CREATE TABLE file_store_group (
  id int(11) NOT NULL AUTO_INCREMENT  ,
  name VARCHAR(255) NOT NULL COMMENT '上传文件组名称',
  code VARCHAR(255) NOT NULL COMMENT '上传文件组编码',
  group_public tinyint(1) DEFAULT 1 NOT NULL COMMENT '文件组文件是否公开',
  CONSTRAINT pd_file_store_group_pk PRIMARY KEY(id)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COMMENT='文件组';

INSERT INTO file_store_group (id, name, code, group_public) VALUES (1, '默认文件组', 'default', 1);
INSERT INTO file_store_group (id, name, code, group_public) VALUES (2, '缩略图', 'thumb', 1);
INSERT INTO file_store_group (id, name, code, group_public) VALUES (3, '用户', 'user', 1);
INSERT INTO file_store_group (id, name, code, group_public) VALUES (4, '私有文件', 'course_private', 0);
INSERT INTO file_store_group (id, name, code, group_public) VALUES (5, '全局设置文件', 'system', 1);

-- ZHIBAIO
DROP TABLE IF EXISTS `dw_ranking_detail`;
CREATE TABLE `dw_ranking_detail` (
  `TX_MT` varchar(7) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `TX_DT` varchar(10) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SOURCE_NM` varchar(10) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `BRANCH_PARENT_NO` varchar(9) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `BRANCH_NO` varchar(9) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `BRANCH_NM` varchar(120) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `BRANCH_TYP` varchar(3) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `TARGET` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `TARGET_TYP` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `CLASS_TYP` varchar(10) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `INFO` decimal(18,4) DEFAULT NULL,
  `RANKING_ID` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `AREA_RANKING_ID` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `RANKING_CHANGE` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `MARK_NO` decimal(9,4) DEFAULT NULL,
  `ORDER_INDEX` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `DATA_KEY` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

DROP TABLE IF EXISTS `dw_ranking_profit`;
CREATE TABLE `dw_ranking_profit` (
  `TX_MT` varchar(7) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `TX_DT` varchar(10) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SOURCE_NM` varchar(10) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `BRANCH_PARENT_NO` varchar(9) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `BRANCH_NO` varchar(9) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `BRANCH_NM` varchar(120) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `BRANCH_TYP` varchar(22) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `RANKING_ID` int(11) DEFAULT NULL,
  `AREA_RANKING_ID` int(11) DEFAULT NULL,
  `MARK_NO` decimal(9,4) DEFAULT NULL,
  `UPVOTE_NO` int(11) DEFAULT NULL,
  `REPLY_NO` int(11) DEFAULT NULL,
  `DATA_KEY` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `RANKING_CHANGE` int(11) DEFAULT NULL,
  `AREA_TYP` varchar(8) COLLATE utf8mb4_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

DROP TABLE IF EXISTS `dw_ranking_compete`;
CREATE TABLE `dw_ranking_compete` (
  `TX_MT` varchar(7) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `TX_DT` varchar(10) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SOURCE_NM` varchar(10) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `BRANCH_PARENT_NO` varchar(9) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `BRANCH_NO` varchar(9) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `BRANCH_NM` varchar(120) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `BRANCH_TYP` varchar(22) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `RANKING_ID` int(11) DEFAULT NULL,
  `AREA_RANKING_ID` int(11) DEFAULT NULL,
  `MARK_NO` decimal(9,4) DEFAULT NULL,
  `UPVOTE_NO` int(11) DEFAULT NULL,
  `REPLY_NO` int(11) DEFAULT NULL,
  `DATA_KEY` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `RANKING_CHANGE` int(11) DEFAULT NULL,
  `AREA_TYP` varchar(8) COLLATE utf8mb4_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

DROP TABLE IF EXISTS `dw_ranking_comprehensive`;
CREATE TABLE `dw_ranking_comprehensive` (
  `TX_MT` varchar(7) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `TX_DT` varchar(10) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SOURCE_NM` varchar(10) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `BRANCH_PARENT_NO` varchar(9) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `BRANCH_NO` varchar(9) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `BRANCH_NM` varchar(120) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `BRANCH_TYP` varchar(22) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `RANKING_ID` int(11) DEFAULT NULL,
  `AREA_RANKING_ID` int(11) DEFAULT NULL,
  `MARK_NO` decimal(9,4) DEFAULT NULL,
  `UPVOTE_NO` int(11) DEFAULT NULL,
  `REPLY_NO` int(11) DEFAULT NULL,
  `DATA_KEY` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `RANKING_CHANGE` int(11) DEFAULT NULL,
  `AREA_TYP` varchar(8) COLLATE utf8mb4_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

