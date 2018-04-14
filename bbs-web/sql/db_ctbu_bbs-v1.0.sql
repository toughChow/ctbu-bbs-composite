/*
SQLyog Ultimate v12.08 (64 bit)
MySQL - 5.6.34-log : Database - db_ctbu_bbs
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
/*Table structure for table `t_pub_auth_menu` */

DROP TABLE IF EXISTS `t_pub_auth_menu`;

CREATE TABLE `t_pub_auth_menu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(255) DEFAULT NULL COMMENT '名称',
  `parent_id` bigint(20) DEFAULT NULL COMMENT '父路径节点id',
  `parent_ids` varchar(255) DEFAULT NULL COMMENT '所有父节点id',
  `permission` varchar(255) DEFAULT NULL COMMENT '权限路径',
  `sort` int(11) DEFAULT NULL COMMENT '页面显示顺序',
  `url` varchar(255) DEFAULT NULL COMMENT '路径',
  `icon` varchar(255) DEFAULT NULL COMMENT '图标',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

/*Data for the table `t_pub_auth_menu` */

LOCK TABLES `t_pub_auth_menu` WRITE;

insert  into `t_pub_auth_menu`(`id`,`name`,`parent_id`,`parent_ids`,`permission`,`sort`,`url`,`icon`) values (1,'根目录',0,'','',1,'/',NULL),(2,'后台管理',1,NULL,'admin',1,'admin',NULL),(3,'用户管理',2,NULL,'users:view',2,'admin/users/','fa fa-user icon-xlarge'),(4,'用户列表',3,NULL,'users:view',3,'admin/users/list',NULL),(5,'用户编辑',4,NULL,'users:edit',0,NULL,NULL),(6,'群组管理',3,NULL,'group:view',3,'admin/users/group',NULL),(7,'群组编辑',4,NULL,'group:edit',0,NULL,NULL),(8,'菜单管理',3,NULL,'menu:view',3,'admin/users/menu',NULL),(9,'菜单编辑',4,NULL,'menu:edit',0,NULL,NULL);

UNLOCK TABLES;

/*Table structure for table `t_pub_collect_page` */

DROP TABLE IF EXISTS `t_pub_collect_page`;

CREATE TABLE `t_pub_collect_page` (
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `post_id` bigint(20) NOT NULL COMMENT '帖子id',
  KEY `FK4cefhygi012oduef8vcs03btc` (`post_id`),
  KEY `FKmy0ha09ge46w7ks7bmdueojdc` (`user_id`),
  CONSTRAINT `FK4cefhygi012oduef8vcs03btc` FOREIGN KEY (`post_id`) REFERENCES `t_pub_post` (`id`),
  CONSTRAINT `FKmy0ha09ge46w7ks7bmdueojdc` FOREIGN KEY (`user_id`) REFERENCES `t_pub_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_pub_collect_page` */

LOCK TABLES `t_pub_collect_page` WRITE;

UNLOCK TABLES;

/*Table structure for table `t_pub_comment` */

DROP TABLE IF EXISTS `t_pub_comment`;

CREATE TABLE `t_pub_comment` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '评论id',
  `comment_time` datetime DEFAULT NULL COMMENT '评论时间',
  `commentor_id` bigint(20) DEFAULT NULL COMMENT '评论者id',
  `content` varchar(255) DEFAULT NULL COMMENT '评论内容',
  `parent_id` bigint(20) DEFAULT NULL COMMENT '父评论节点id',
  `post_id` bigint(20) DEFAULT NULL COMMENT '帖子id',
  `tip_off` int(11) DEFAULT NULL COMMENT '举报次数',
  `upvote` int(11) DEFAULT NULL COMMENT '点赞次数',
  `sequence` int(11) DEFAULT NULL COMMENT '1评论 2回复',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_pub_comment` */

LOCK TABLES `t_pub_comment` WRITE;

UNLOCK TABLES;

/*Table structure for table `t_pub_group` */

DROP TABLE IF EXISTS `t_pub_group`;

CREATE TABLE `t_pub_group` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '菜单id',
  `key_` varchar(255) DEFAULT NULL COMMENT '关键字',
  `name` varchar(255) DEFAULT NULL COMMENT '菜单名字',
  `status` int(11) DEFAULT NULL COMMENT '0可用 1不可用',
  `create_time` datetime DEFAULT NULL,
  `delete_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `user_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_sl7jj89o5hmktdw2u86ihq55r` (`key_`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Data for the table `t_pub_group` */

LOCK TABLES `t_pub_group` WRITE;

insert  into `t_pub_group`(`id`,`key_`,`name`,`status`,`create_time`,`delete_time`,`update_time`,`user_id`) values (1,'blog','1',0,NULL,NULL,NULL,NULL);

UNLOCK TABLES;

/*Table structure for table `t_pub_group_userpolist` */

DROP TABLE IF EXISTS `t_pub_group_userpolist`;

CREATE TABLE `t_pub_group_userpolist` (
  `grouppo_id` bigint(20) NOT NULL,
  `userpolist_id` bigint(20) NOT NULL,
  UNIQUE KEY `UK_91pmrh9rmb9l7nlsdsjoixx7e` (`userpolist_id`),
  CONSTRAINT `FKmdiehcl76tyw4rlct04y805aa` FOREIGN KEY (`userpolist_id`) REFERENCES `t_pub_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_pub_group_userpolist` */

LOCK TABLES `t_pub_group_userpolist` WRITE;

UNLOCK TABLES;

/*Table structure for table `t_pub_plate` */

DROP TABLE IF EXISTS `t_pub_plate`;

CREATE TABLE `t_pub_plate` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '板块id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `plate_creator` varchar(255) DEFAULT NULL COMMENT '板块创建者',
  `plate_name` varchar(255) DEFAULT NULL COMMENT '板块名称',
  `parent_id` bigint(20) DEFAULT NULL COMMENT '父板块id',
  `status` int(11) DEFAULT NULL COMMENT '状态值',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_pub_plate` */

LOCK TABLES `t_pub_plate` WRITE;

UNLOCK TABLES;

/*Table structure for table `t_pub_post` */

DROP TABLE IF EXISTS `t_pub_post`;

CREATE TABLE `t_pub_post` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '帖子id',
  `content` varchar(255) DEFAULT NULL COMMENT '帖子内容',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `is_verified` int(11) DEFAULT NULL COMMENT '是否通过审核 0否 1是',
  `plate_id` bigint(20) DEFAULT NULL COMMENT '板块id',
  `creator_id` bigint(20) DEFAULT NULL COMMENT '创建者id',
  `status` int(11) DEFAULT NULL COMMENT '0审核中 1未审核',
  PRIMARY KEY (`id`),
  KEY `FK187b5fw1qsywvtk6whpcdwa73` (`plate_id`),
  KEY `FKf4smth6xbij0okkpreiheijlu` (`creator_id`),
  CONSTRAINT `FK187b5fw1qsywvtk6whpcdwa73` FOREIGN KEY (`plate_id`) REFERENCES `t_pub_plate` (`id`),
  CONSTRAINT `FKf4smth6xbij0okkpreiheijlu` FOREIGN KEY (`creator_id`) REFERENCES `t_pub_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_pub_post` */

LOCK TABLES `t_pub_post` WRITE;

UNLOCK TABLES;

/*Table structure for table `t_pub_post_type` */

DROP TABLE IF EXISTS `t_pub_post_type`;

CREATE TABLE `t_pub_post_type` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '类型id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `type` int(11) DEFAULT NULL COMMENT '帖子类型',
  `type_creator` int(11) DEFAULT NULL COMMENT '类型创建者',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_pub_post_type` */

LOCK TABLES `t_pub_post_type` WRITE;

UNLOCK TABLES;

/*Table structure for table `t_pub_role` */

DROP TABLE IF EXISTS `t_pub_role`;

CREATE TABLE `t_pub_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '角色id',
  `name` varchar(255) DEFAULT NULL COMMENT '角色名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Data for the table `t_pub_role` */

LOCK TABLES `t_pub_role` WRITE;

insert  into `t_pub_role`(`id`,`name`) values (1,'管理员');

UNLOCK TABLES;

/*Table structure for table `t_pub_role_menu` */

DROP TABLE IF EXISTS `t_pub_role_menu`;

CREATE TABLE `t_pub_role_menu` (
  `role_id` bigint(20) NOT NULL COMMENT '角色id',
  `menu_id` bigint(20) NOT NULL COMMENT '菜单id',
  KEY `FKl27yxsdkx71528m4ycnrxx32f` (`menu_id`),
  KEY `FK8jhb3anqianpka3mndltknar3` (`role_id`),
  CONSTRAINT `FK8jhb3anqianpka3mndltknar3` FOREIGN KEY (`role_id`) REFERENCES `t_pub_role` (`id`),
  CONSTRAINT `FKl27yxsdkx71528m4ycnrxx32f` FOREIGN KEY (`menu_id`) REFERENCES `t_pub_auth_menu` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_pub_role_menu` */

LOCK TABLES `t_pub_role_menu` WRITE;

insert  into `t_pub_role_menu`(`role_id`,`menu_id`) values (1,1),(1,2),(1,3),(1,4),(1,5),(1,9);

UNLOCK TABLES;

/*Table structure for table `t_pub_user` */

DROP TABLE IF EXISTS `t_pub_user`;

CREATE TABLE `t_pub_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `active_email` int(11) DEFAULT NULL COMMENT '激活邮箱',
  `avatar` varchar(255) DEFAULT NULL COMMENT '头像',
  `created` datetime DEFAULT NULL COMMENT '创建时间',
  `email` varchar(128) DEFAULT NULL COMMENT '邮箱地址',
  `gender` int(11) DEFAULT NULL COMMENT '0女 1男 2保密',
  `is_admin` int(11) DEFAULT NULL COMMENT '0不是 1是',
  `last_login` datetime DEFAULT NULL COMMENT '最后一次登陆时间',
  `mobile` varchar(11) DEFAULT NULL COMMENT '移动电话',
  `nickname` varchar(18) DEFAULT NULL COMMENT '昵称',
  `password` varchar(64) DEFAULT NULL COMMENT '密码',
  `signature` varchar(255) DEFAULT NULL COMMENT '签名',
  `status` int(11) DEFAULT NULL COMMENT '0激活 1锁定',
  `username` varchar(64) DEFAULT NULL COMMENT '用户名',
  `user_ip` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_2qettuyla2tc3awrp7mkkwiwk` (`email`),
  UNIQUE KEY `UK_f04u2xhkn1ljcwealq3ykshfi` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

/*Data for the table `t_pub_user` */

LOCK TABLES `t_pub_user` WRITE;

insert  into `t_pub_user`(`id`,`active_email`,`avatar`,`created`,`email`,`gender`,`is_admin`,`last_login`,`mobile`,`nickname`,`password`,`signature`,`status`,`username`,`user_ip`) values (1,0,'/assets/img/ava/default.png','2018-04-03 10:14:37','toughChow@gmail.com',0,0,'2018-04-14 20:40:15',NULL,NULL,'d033e22ae348aeb5660fc2140aec35850c4da997',NULL,0,'admin',NULL),(2,0,'/assets/img/ava/default.png','2018-04-06 16:25:35',NULL,0,0,'2018-04-12 20:57:13','17623677587',NULL,'c1f0cd9eff02123d2e3215a71b6c8b4b85fa4e80',NULL,0,'tough',NULL),(3,0,'/assets/img/ava/default.png','2018-04-11 22:14:49',NULL,NULL,0,'2018-04-11 22:15:48','17685209637',NULL,'1411678a0b9e25ee2f7c8b2f7ac92b6a74b3f9c5',NULL,0,'laosiji',NULL);

UNLOCK TABLES;

/*Table structure for table `t_pub_user_role` */

DROP TABLE IF EXISTS `t_pub_user_role`;

CREATE TABLE `t_pub_user_role` (
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `role_id` bigint(20) NOT NULL COMMENT '角色id',
  KEY `FKrbf5vc4ghtuki2bkflni91hnd` (`role_id`),
  KEY `FKcbcnrmhhkdon1o3qiewlox1we` (`user_id`),
  CONSTRAINT `FKcbcnrmhhkdon1o3qiewlox1we` FOREIGN KEY (`user_id`) REFERENCES `t_pub_user` (`id`),
  CONSTRAINT `FKrbf5vc4ghtuki2bkflni91hnd` FOREIGN KEY (`role_id`) REFERENCES `t_pub_role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_pub_user_role` */

LOCK TABLES `t_pub_user_role` WRITE;

insert  into `t_pub_user_role`(`user_id`,`role_id`) values (1,1);

UNLOCK TABLES;

/*Table structure for table `t_sys_config` */

DROP TABLE IF EXISTS `t_sys_config`;

CREATE TABLE `t_sys_config` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `key_` varchar(255) DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  `value` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_6prfhv2hwmd57c2dnvqbkdvd2` (`key_`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Data for the table `t_sys_config` */

LOCK TABLES `t_sys_config` WRITE;

insert  into `t_sys_config`(`id`,`key_`,`type`,`value`) values (1,'site_name',0,'CtbuBbs');

UNLOCK TABLES;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
