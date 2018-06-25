-- 组织机构表
DROP TABLE IF EXISTS `org`;
CREATE TABLE `org` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '组织机构ID',
  `name` varchar(255) NOT NULL COMMENT '名称',
  `parent_id` int(11) NOT NULL DEFAULT '0' COMMENT '组织机构父ID',
  `children_num` int(11) NOT NULL DEFAULT '0' COMMENT '辖下组织机构数量',
  `depth` int(11) NOT NULL DEFAULT '1' COMMENT '当前组织机构层级',
  `seq` int(11) NOT NULL DEFAULT '0' COMMENT '索引',
  `description` text COMMENT '备注',
  `code` varchar(255) NOT NULL DEFAULT '' COMMENT '机构编码',
  `org_code` varchar(255) NOT NULL DEFAULT '0' COMMENT '内部编码',
  `created_user_id` int(11) NOT NULL COMMENT '创建用户ID',
  `created_time` int(11) unsigned NOT NULL COMMENT '创建时间',
  `last_upd_user_id` int(11) NOT NULL DEFAULT '0' COMMENT '最后修改人',
  `last_upd_time` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '最后更新时间',
  `sync_id` int(11) NOT NULL DEFAULT '0' COMMENT '同步部门ID',
  PRIMARY KEY (`id`),
  UNIQUE KEY `org_code` (`org_code`),
  UNIQUE KEY `code` (`code`)
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
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `email` varchar(128) COMMENT '用户邮箱',
  `mobile` varchar(32) NOT NULL DEFAULT '' COMMENT '手机',
  `password` varchar(80) NOT NULL COMMENT '用户密码',
  `pay_password` varchar(80) NOT NULL DEFAULT '' COMMENT '支付密码',
  `locale` varchar(20) DEFAULT NULL,
  `uri` varchar(64) NOT NULL DEFAULT '' COMMENT '用户URI',
  `username` varchar(64) NOT NULL COMMENT '用户名',
  `truename` varchar(255) NOT NULL DEFAULT '' COMMENT '真实姓名',
  `title` varchar(255) NOT NULL DEFAULT '' COMMENT '头衔',
  `tags` varchar(255) NOT NULL DEFAULT '' COMMENT '标签',
  `type` varchar(32) NOT NULL COMMENT 'default默认为网站注册, weibo新浪微薄登录',
  `point` int(11) NOT NULL DEFAULT '0' COMMENT '积分',
  `coin` int(11) NOT NULL DEFAULT '0' COMMENT '金币',
  `small_avatar` varchar(255) NOT NULL DEFAULT '' COMMENT '小头像',
  `medium_avatar` varchar(255) NOT NULL DEFAULT '' COMMENT '中头像',
  `large_avatar` varchar(255) NOT NULL DEFAULT '' COMMENT '大头像',
  `email_verified` tinyint(1) NOT NULL DEFAULT '0' COMMENT '邮箱是否为已验证',
  `setup` tinyint(4) NOT NULL DEFAULT '1' COMMENT '是否初始化设置的，未初始化的可以设置邮箱、用户名。',
  `roles` varchar(255) NOT NULL COMMENT '用户角色',
  `promoted` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '是否为推荐',
  `promoted_seq` int(10) unsigned NOT NULL DEFAULT '0',
  `promoted_time` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '推荐时间',
  `locked` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '是否被禁止',
  `lock_deadline` int(10) NOT NULL DEFAULT '0' COMMENT '帐号锁定期限',
  `consecutive_password_error_times` int(11) NOT NULL DEFAULT '0' COMMENT '帐号密码错误次数',
  `last_password_fail_time` int(10) NOT NULL DEFAULT '0'  comment '最后一次密码失败时间',
   last_password_reset_time int(10) unsigned default '0' not null comment '最后一次密码重置时间',
  `login_time` int(11) NOT NULL DEFAULT '0' COMMENT '最后登录时间',
  `login_ip` varchar(64) NOT NULL DEFAULT '' COMMENT '最后登录IP',
  `login_session_id` varchar(255) NOT NULL DEFAULT '' COMMENT '最后登录会话ID',
  `approval_time` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '实名认证时间',
  `approval_status` enum('unapprove','approving','approved','approve_fail') NOT NULL DEFAULT 'unapprove' COMMENT '实名认证状态',
  `new_message_num` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '未读私信数',
  `new_notification_num` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '未读消息数',
  `created_ip` varchar(64) NOT NULL DEFAULT '' COMMENT '注册IP',
  `created_time` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '注册时间',
  `updated_time` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '最后更新时间',
  `invite_code` varchar(255) DEFAULT NULL COMMENT '邀请码',
  `registered_way` varchar(64) NOT NULL DEFAULT '' COMMENT '注册设备来源(web/ios/android)',
  `pwd_init` tinyint(1) NOT NULL DEFAULT '0' COMMENT '初始化密码',
  `post_id` int(11) NOT NULL DEFAULT '0' COMMENT '岗位id',
  `read_guide` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否阅读新手引导',
  `org_ids` varchar(128) NOT NULL DEFAULT '0' COMMENT '部门ID',
  `org_codes` varchar(255) NOT NULL DEFAULT '0' COMMENT '部门ID',
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`),
  KEY `mobile_index` (`mobile`),
  KEY `updatedTime` (`updated_time`),
  KEY `user_type_index` (`type`),
  KEY `promoted` (`promoted`),
  KEY `email_index` (`email`)
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
  `post_id` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '岗位ID',
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
--   "password": "qwer0asdf",
--   "email": "kkii_55@163.com"
-- }
INSERT INTO user (id, email, mobile, password, pay_password, locale, uri, username, title, tags, type, point, coin, small_avatar, medium_avatar, large_avatar, email_verified, setup, roles, promoted, promoted_seq, promoted_time, locked, lock_deadline, consecutive_password_error_times, last_password_fail_time, last_password_reset_time, login_time, login_ip, login_session_id, approval_time, approval_status, new_message_num, new_notification_num, created_ip, created_time, updated_time, invite_code, registered_way, pwd_init, post_id, read_guide, org_ids, org_codes) VALUES (1, 'kkii_55@163.com', '+86 18601941970', '8abd3be86d4e203be838c1cef5d5a0ba699e9fb114d15aaa29e43fa40c3bf50307ff25ef47acf510', '', NULL, '', 'admin', '', '', 'default', 0, 0, '', '', '', 0, 1, 'ROLE_SUPER_ADMIN', 0, 0, 0, 0, 0, 0, 0, 0, 0, '', '', 0, 'unapprove', 0, 0, '', 1517466271, 0, NULL, '', 0, 0, 0, 0, 0);

-- 角色数据



INSERT INTO  role (id, name, code, data, created_time, created_user_id,last_upd_user_id ,last_upd_time) VALUES (3, '匿名用户', 'ROLE_ANONYMOUS', '["auth_login","auth_register"]', 1516342692, 1, 1,1516342692);
INSERT INTO  role (id, name, code, data, created_time, created_user_id,last_upd_user_id ,last_upd_time) VALUES (2, '学员', 'ROLE_USER', '["auth_login","auth_register","auth_refresh"]', 1516342692, 1, 1,1516342692);
INSERT INTO  role (id, name, code, data, created_time, created_user_id,last_upd_user_id ,last_upd_time) VALUES (1, '超级管理员', 'ROLE_SUPER_ADMIN', '["auth_login","auth_register","auth_refresh"]', 1516342692, 1, 1,1516342692);
INSERT INTO  role (id, name, code, data, created_time, created_user_id,last_upd_user_id ,last_upd_time) VALUES (4, '讲师', 'ROLE_TEACHER', '["auth_login","auth_register","auth_refresh"]', 1516342692, 1, 1,1516342692);
INSERT INTO  role (id, name, code, data, created_time, created_user_id,last_upd_user_id ,last_upd_time) VALUES (5, '培训管理员', 'ROLE_TRAINING_ADMIN', '["auth_login","auth_register","auth_refresh"]', 1516342692, 1, 1,1516342692);
INSERT INTO  role (id, name, code, data, created_time, created_user_id,last_upd_user_id ,last_upd_time) VALUES (6, '部门管理员', 'ROLE_DEPARTMENT_ADMIN', '["auth_login","auth_register","auth_refresh"]', 1516342692, 1, 1,1516342692);



-- 组织架构数据
INSERT INTO org(`id`,`name`,`parent_id`,`children_num`,`depth`,`seq`,`description`,`code`,`org_code`,`created_user_id`,`created_time`,`last_upd_user_id`,`last_upd_time`,`sync_id`) VALUES ('1','全站','0','0','1','1','全站所有组织部门','BANK','1.','1','1516342692','1','1516586945','0');

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
ALTER TABLE user add index org_codes_index (org_codes);
ALTER TABLE user add index org_post_id_index (post_id);

