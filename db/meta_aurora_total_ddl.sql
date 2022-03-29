/*
SQLyog Ultimate v12.08 (64 bit)
MySQL - 5.7.12-log : Database - aurora_meta
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`aurora_meta` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `aurora_meta`;

/*Table structure for table `meta_author` */

DROP TABLE IF EXISTS `meta_author`;

CREATE TABLE `meta_author` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `author_name` varchar(90) NOT NULL COMMENT '作者名',
  `category_id` int(11) DEFAULT NULL COMMENT '品类ID',
  `nationality` varchar(60) DEFAULT '' COMMENT '国籍',
  `era` varchar(30) DEFAULT '' COMMENT '年代',
  `avatar` varchar(255) NOT NULL DEFAULT '' COMMENT '主图URL',
  `bucket_name` varchar(128) NOT NULL DEFAULT '' COMMENT 'oss bucket',
  `file_name` varchar(255) NOT NULL DEFAULT '' COMMENT 'fileName',
  `path` varchar(255) NOT NULL DEFAULT '' COMMENT 'path',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1875 DEFAULT CHARSET=utf8mb4 COMMENT='作者元数据表';

/*Table structure for table `meta_author_introduction` */

DROP TABLE IF EXISTS `meta_author_introduction`;

CREATE TABLE `meta_author_introduction` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `author_id` int(11) NOT NULL COMMENT '作者ID',
  `introduction` text NOT NULL COMMENT '简介内容',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1875 DEFAULT CHARSET=utf8mb4 COMMENT='作者简介数据表';

/*Table structure for table `meta_category` */

DROP TABLE IF EXISTS `meta_category`;

CREATE TABLE `meta_category` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `category_name` varchar(60) NOT NULL COMMENT '品类名',
  `category_type` tinyint(4) NOT NULL COMMENT '品类类型：0-spu品类；1-作者品类',
  `parent_id` int(11) DEFAULT '0' COMMENT '父品类ID',
  `last_category` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否是叶子类目：0-不是；1-是',
  `category_level` tinyint(4) NOT NULL COMMENT '品类级别',
  `trace_source` varchar(128) NOT NULL DEFAULT '' COMMENT '溯源站点',
  `trace_source_title` varchar(60) DEFAULT NULL COMMENT '溯源站点名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8mb4 COMMENT='品类元数据表';

/*Table structure for table `meta_sentence_content` */

DROP TABLE IF EXISTS `meta_sentence_content`;

CREATE TABLE `meta_sentence_content` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `sentence_id` bigint(20) NOT NULL COMMENT '句子信息ID',
  `content` text NOT NULL COMMENT '句子内容',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=286512 DEFAULT CHARSET=utf8mb4 COMMENT='句子文本内容元数据表';

/*Table structure for table `meta_sentence_info` */

DROP TABLE IF EXISTS `meta_sentence_info`;

CREATE TABLE `meta_sentence_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `spu_id` bigint(20) DEFAULT NULL COMMENT 'spuID',
  `author_id` int(11) DEFAULT NULL COMMENT '作者ID',
  `like_number` int(11) NOT NULL DEFAULT '0' COMMENT '喜欢数量',
  `comment_number` int(11) NOT NULL DEFAULT '0' COMMENT '评论数量',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=286514 DEFAULT CHARSET=utf8mb4 COMMENT='句子信息元数据表';

/*Table structure for table `meta_spu_info` */

DROP TABLE IF EXISTS `meta_spu_info`;

CREATE TABLE `meta_spu_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `spu_name` varchar(60) NOT NULL COMMENT 'spu名',
  `category_id` int(11) DEFAULT NULL COMMENT '所属品类',
  `author_id` int(11) DEFAULT NULL COMMENT '所属作者',
  `avatar` varchar(255) DEFAULT '' COMMENT '主图url',
  `bucket_name` varchar(128) NOT NULL DEFAULT '' COMMENT 'oss bucket',
  `file_name` varchar(255) NOT NULL DEFAULT '' COMMENT 'fileName',
  `path` varchar(255) NOT NULL DEFAULT '' COMMENT 'path',
  PRIMARY KEY (`id`),
  KEY `meta_spu_info_spu_name_index` (`spu_name`)
) ENGINE=InnoDB AUTO_INCREMENT=25339 DEFAULT CHARSET=utf8mb4 COMMENT='spu信息元数据表';

/*Table structure for table `meta_spu_introduction` */

DROP TABLE IF EXISTS `meta_spu_introduction`;

CREATE TABLE `meta_spu_introduction` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `spu_id` bigint(20) NOT NULL COMMENT 'spu信息主键',
  `introduction` text NOT NULL COMMENT '简介内容',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=25339 DEFAULT CHARSET=utf8mb4 COMMENT='spu简介元数据表';

/*Table structure for table `meta_tag` */

DROP TABLE IF EXISTS `meta_tag`;

CREATE TABLE `meta_tag` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `tag_name` varchar(30) NOT NULL COMMENT '标签名',
  `tag_classify` tinyint(4) NOT NULL DEFAULT '0' COMMENT '标签分类：0-其他分类标签；1-感觉分类标签；2-长度分类标签',
  `trace_source` varchar(128) NOT NULL DEFAULT '' COMMENT '溯源站点',
  `trace_source_title` varchar(60) DEFAULT NULL COMMENT '溯源站点名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='标签元数据表';

/*Table structure for table `meta_tag_sentence_relation` */

DROP TABLE IF EXISTS `meta_tag_sentence_relation`;

CREATE TABLE `meta_tag_sentence_relation` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `tag_id` int(11) NOT NULL COMMENT '标签ID',
  `sentence_content` text NOT NULL COMMENT '句子内容',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='标签句子关系元数据表';

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
