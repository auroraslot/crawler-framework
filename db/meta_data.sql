drop database if exists aurora_meta;
create database aurora_meta;
use aurora_meta;

drop table if exists meta_sentence_info;
create table meta_sentence_info
(
  id             bigint primary key auto_increment comment '主键',
  spu_id         bigint  not null comment 'spuID',
  author_id      int     not null comment '作者ID',
  like_number    tinyint not null default 0 comment '喜欢数量',
  comment_number tinyint not null default 0 comment '评论数量'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='句子信息元数据表';

drop table if exists meta_sentence_content;
create table meta_sentence_content
(
  id          bigint primary key auto_increment comment '主键',
  sentence_id bigint not null comment '句子信息ID',
  content     text   not null comment '句子内容'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='句子文本内容元数据表';

drop table if exists meta_spu_info;
create table meta_spu_info
(
  id          bigint primary key auto_increment comment '主键',
  spu_name    varchar(60)  not null comment 'spu名',
  category_id int          not null comment '所属品类',
  author_id   int comment '所属作者',
  avatar      varchar(255)  default '' comment '主图url',
  bucket_name varchar(128) not null default '' comment 'oss bucket',
  file_name varchar(255) not null default '' comment 'fileName',
  path varchar(255) not null default '' comment 'path'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='spu信息元数据表';

drop table if exists meta_spu_introduction;
create table meta_spu_introduction
(
  id           bigint primary key auto_increment comment '主键',
  spu_id       bigint not null comment 'spu信息主键',
  introduction text   not null comment '简介内容'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='spu简介元数据表';

drop table if exists meta_tag;
create table meta_tag
(
  id           int primary key auto_increment comment '主键',
  tag_name     varchar(30) not null comment '标签名',
  tag_classify tinyint     not null default 0 comment '标签分类：0-其他分类标签；1-感觉分类标签；2-长度分类标签',
  trace_source varchar(128) not null default '' comment '溯源站点',
  trace_source_title varchar(60) comment '溯源站点名称'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='标签元数据表';

drop table if exists meta_tag_sentence_relation;
create table meta_tag_sentence_relation
(
  id               bigint primary key auto_increment comment '主键',
  tag_id           int  not null comment '标签ID',
  sentence_content text not null comment '句子内容'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='标签句子关系元数据表';

drop table if exists meta_category;
create table meta_category
(
  id             int primary key auto_increment comment '主键',
  category_name  varchar(60) not null comment '品类名',
  category_type  tinyint     not null comment '品类类型：0-spu品类；1-作者品类',
  parent_id      int                  default 0 comment '父品类ID',
  last_category  tinyint     not null default 0 comment '是否是叶子类目：0-不是；1-是',
  category_level tinyint     not null comment '品类级别',
  trace_source varchar(128) not null default '' comment '溯源站点',
  trace_source_title varchar(60) comment '溯源站点名称'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='品类元数据表';

drop table if exists meta_author;
create table meta_author
(
  id          int primary key auto_increment comment '主键',
  author_name varchar(90)  not null comment '作者名',
  category_id int          not null comment '品类ID',
  nationality varchar(60)  not null default '中国' comment '国籍',
  era         varchar(30)  not null default '' comment '年代',
  avatar      varchar(255) not null default '' comment '主图URL',
  bucket_name varchar(128) not null default '' comment 'oss bucket',
  file_name varchar(255) not null default '' comment 'fileName',
  path varchar(255) not null default '' comment 'path'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='作者元数据表';

drop table if exists meta_author_introduction;
create table meta_author_introduction
(
  id           bigint primary key auto_increment comment '主键',
  author_id    int  not null comment '作者ID',
  introduction text not null comment '简介内容'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='作者简介数据表';