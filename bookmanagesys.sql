/*
 Navicat Premium Data Transfer

 Source Server         : mysql-8.0数据库
 Source Server Type    : MySQL
 Source Server Version : 80017
 Source Host           : localhost:3306
 Source Schema         : bookmanage

 Target Server Type    : MySQL
 Target Server Version : 80017
 File Encoding         : 65001

 Date: 27/02/2021 22:49:38
*/
/*
 Navicat Premium Data Transfer

 Source Server         : mysql-8.0数据库
 Source Server Type    : MySQL
 Source Server Version : 80017
 Source Host           : localhost:3306
 Source Schema         : bookmanage

 Target Server Type    : MySQL
 Target Server Version : 80017
 File Encoding         : 65001

 Date: 27/02/2021 19:16:26
*/
create database if not exists bookmanagesys;
use bookmanagesys;


DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` bigint AUTO_INCREMENT,
  `username` varchar(20),
  `password` varchar(20),
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci;

insert into user(id,username,password) value(8,'xiaoli','666666');
insert into user(id,username,password) value(9,'zhangyogn','666666');
insert into user(id,username,password) value(10,'zhangming','666666');
insert into user(id,username,password) value(11,'hexu','666666');
insert into user(id,username,password) value(12,'yang','666666');
insert into user(id,username,password) value(13,'wang','666666');
insert into user(id,username,password) value(14,'zhang','666666');
insert into user(id,username,password) value(15,'zhangzetian','666666');
insert into user(id,username,password) value(16,'wangyang','666666');
insert into user(id,username,password) value(17,'郭州乐沙','666666');
insert into user(id,username,password) value(18,'北冥有鱼','666666');


DROP TABLE IF EXISTS `book`;
CREATE TABLE `book`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `bookname` varchar(50),
  `total` int(10),
  `putaway` date ,
  `surplus` int(10),
  PRIMARY KEY (id)
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci;


INSERT INTO `book`(id,bookname,total,putaway,surplus) VALUES (8, '高等数学1', 10, '2018-08-01', 9);
INSERT INTO `book`(id,bookname,total,putaway,surplus) VALUES (9, '高等数学2', 11, '2018-08-01', 11);
INSERT INTO `book`(id,bookname,total,putaway,surplus) VALUES (10, 'Java', 12, '2019-08-10',  10);
INSERT INTO `book`(id,bookname,total,putaway,surplus) VALUES (11, 'java精彩编程200例', 13, '2018-09-08',  10);
INSERT INTO `book`(id,bookname,total,putaway,surplus) VALUES (12, '高  等教 育', 14,  '2018-08-01', 11);
INSERT INTO `book`(id,bookname,total,putaway,surplus) VALUES (13, 'java从入门到精通', 15, '2017-09-08', 8);
INSERT INTO `book`(id,bookname,total,putaway,surplus) VALUES (14, 'java从入门到精通', 15, '2017-09-08', 8);
INSERT INTO `book`(id,bookname,total,putaway,surplus) VALUES (15, '商务英语', 12, '2018-10-08',  10);
INSERT INTO `book`(id,bookname,total,putaway,surplus) VALUES (16, '计算机入门', 13, '2018-11-08',  10);
INSERT INTO `book`(id,bookname,total,putaway,surplus) VALUES (17, '大学英语', 8, '2018-10-08',  1);
INSERT INTO `book`(id,bookname,total,putaway,surplus) VALUES (18, '计算机', 4, '2017-11-08',   1);
INSERT INTO `book`(id,bookname,total,putaway,surplus) VALUES (19, '入门到精通', 2, '2019-09-17',1);
INSERT INTO `book`(id,bookname,total,putaway,surplus) VALUES (20, '高等教育', 2, '2018-08-01', 1);
INSERT INTO `book`(id,bookname,total,putaway,surplus) VALUES (21, 'MTML', 1, '2018-08-01', 1);


DROP TABLE IF EXISTS `borrowrecord`;
CREATE TABLE `borrowrecord`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `bookid` bigint,
  `userid` bigint,
  `borrowdate` date ,
  `backdate` date ,
  `state` varchar(20),
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci;

use bookmanagesys;

INSERT INTO `borrowrecord`(id,bookid,userid,borrowdate,backdate,state) VALUES (10004, 8, 8, '2019-09-01', '2019-10-01','已还' );
INSERT INTO `borrowrecord`(id,bookid,userid,borrowdate,backdate,state) VALUES (10006, 9,9,  '2019-09-01', '2019-10-01', '已还');
INSERT INTO `borrowrecord`(id,bookid,userid,borrowdate,backdate,state) VALUES (10008, 10,10, '2019-09-01', '2019-10-01', '已还');
INSERT INTO `borrowrecord`(id,bookid,userid,borrowdate,backdate,state) VALUES (10010, 11,11, '2019-09-01', '2019-10-01', '已还');
INSERT INTO `borrowrecord`(id,bookid,userid,borrowdate,backdate,state) VALUES (10014, 12,12,'2019-09-01', '2019-10-01', '已还');
INSERT INTO `borrowrecord`(id,bookid,userid,borrowdate,backdate,state) VALUES (10016, 13,13, '2019-09-01', '2019-10-01', '已还');
INSERT INTO `borrowrecord`(id,bookid,userid,borrowdate,backdate,state) VALUES (10018, 14,14, '2019-09-01', '2019-10-01', '已还');
INSERT INTO `borrowrecord`(id,bookid,userid,borrowdate,backdate,state) VALUES (10020, 15,15, '2019-09-01', '2019-10-01', '已还');
INSERT INTO `borrowrecord`(id,bookid,userid,borrowdate,backdate,state) VALUES (10022, 16,16,'2019-09-01', '2019-10-01', '已还');
INSERT INTO `borrowrecord`(id,bookid,userid,borrowdate,backdate,state) VALUES (10024, 17,17, '2019-09-01', '2019-10-01', '已还');
INSERT INTO `borrowrecord`(id,bookid,userid,borrowdate,backdate,state) VALUES (10026, 18,18,'2019-09-01', '2019-10-01', '已还');
