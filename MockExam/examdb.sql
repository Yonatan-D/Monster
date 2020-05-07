/*
Navicat MySQL Data Transfer

Source Server         : MySQL
Source Server Version : 80016
Source Host           : localhost:3306
Source Database       : examdb

Target Server Type    : MYSQL
Target Server Version : 80016
File Encoding         : 65001

Date: 2019-06-18 20:58:14
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `tb_scoretype`
-- ----------------------------
DROP TABLE IF EXISTS `tb_scoretype`;
CREATE TABLE `tb_scoretype` (
  `typeid` int(11) NOT NULL,
  `typename` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`typeid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of tb_scoretype
-- ----------------------------
INSERT INTO `tb_scoretype` VALUES ('1', '五题快测✏');
INSERT INTO `tb_scoretype` VALUES ('2', '仿真考试⏱');

-- ----------------------------
-- Table structure for `tb_test`
-- ----------------------------
DROP TABLE IF EXISTS `tb_test`;
CREATE TABLE `tb_test` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `quest` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci,
  `a` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci,
  `b` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci,
  `c` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci,
  `d` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci,
  `answer` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci,
  `pic` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=79 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of tb_test
-- ----------------------------
INSERT INTO `tb_test` VALUES ('57', '这个标志是何含义？', '低速行驶', '注意行人', '行人先行', '步行', 'D', 'TEST_IMG/5eb4d75agw1e290kuiok5j.jpg');
INSERT INTO `tb_test` VALUES ('58', '大雾天行车，多鸣喇叭是为了什么？', '引起对方注意，避免发生危险', '催促前车提速，避免发生追尾', '准备超越前车', '催促前车让行', 'A', ' ');
INSERT INTO `tb_test` VALUES ('59', '在没有中心线的道路上发现后车发出超车信号时，如果条件许可如何行驶？', '保持原状态行驶', '加速行驶', '迅速停车让行', '降速靠右让路', 'D', ' ');
INSERT INTO `tb_test` VALUES ('60', '以下哪项行为可构成危险驾驶罪？', '闯红灯', '无证驾驶', '疲劳驾驶', '醉驾', 'D', ' ');
INSERT INTO `tb_test` VALUES ('61', '这个标志是何含义？', '注意行人', '有人行横道', '村庄或集镇', '有小学校', 'C', 'TEST_IMG/5eb4d75agw1e290jnsftrj.jpg');
INSERT INTO `tb_test` VALUES ('62', '如下图所示的交通事故中，有关事故责任认定，正确的说法是什么？', 'B车违反交通信号，所以B负全责', 'B车不得妨碍被放行的车辆，所以B车负全责', '直行车辆不得妨碍左转车辆，所以A车负全责', '右侧方向的车辆具有优先通行权，故B车负全责', 'B', 'TEST_IMG/1282.jpg');
INSERT INTO `tb_test` VALUES ('63', '夜间行车，可选择下列哪个地段超车?', '交叉路口', '窄路窄桥', '路宽车少', '弯道陡坡', 'C', ' ');
INSERT INTO `tb_test` VALUES ('64', '如图所示，在这起交通事故中，以下说法正确的是什么？', 'A车负全部责任', 'B车负全部责任', '都无责任，后果自行承担', '各负一半责任', 'B', 'TEST_IMG/14068.jpg');
INSERT INTO `tb_test` VALUES ('65', '驾驶机动车跨越双实线行驶属于什么行为？', '违章行为', '违法行为', '过失行为', '违规行为', 'B', ' ');
INSERT INTO `tb_test` VALUES ('66', '这个标志是何含义？', '解除3米限宽', '限制高度为3米', '预告宽度为3米', '限制宽度为3米', 'D', 'TEST_IMG/5eb4d75agw1e290khciw4j.jpg');
INSERT INTO `tb_test` VALUES ('67', '驾驶人有下列哪种违法行为一次记12分？', '违反交通信号灯', '使用伪造机动车号牌', '违反禁令标志指示', '拨打、接听手机的', 'B', ' ');
INSERT INTO `tb_test` VALUES ('68', '图中圈内黄色虚线是什么标线？', '路口导向线', '非机动车引导线', '车道连接线', '小型车转弯线', 'A', 'TEST_IMG/5eb4d75agw1e2917rsvmfj.jpg');
INSERT INTO `tb_test` VALUES ('69', '如图所示，在这种情况下通过前方路口，应该怎么行驶?', '减速或停车避让行人', '加速通过', '赶在行人前通过', '靠左侧行驶', 'A', 'TEST_IMG/11126.jpg');
INSERT INTO `tb_test` VALUES ('70', '这个标志是何含义？', '连续上坡', '上陡坡', '下陡坡', '连续下坡', 'D', 'TEST_IMG/5eb4d75agw1e290jemy8nj.jpg');
INSERT INTO `tb_test` VALUES ('71', '机动车在高速公路上发生故障或交通事故无法正常行驶时由什么车拖拽或牵引？', '过路车', '大客车', '同行车', '清障车', 'D', ' ');
INSERT INTO `tb_test` VALUES ('72', '补领机动车驾驶证应到以下哪个地方办理？', '所学驾校', '驾驶证核发地车辆管理所', '派出所', '全国任何地方公安机关交通管理部门', 'B', ' ');
INSERT INTO `tb_test` VALUES ('73', '车辆在交叉路口有优先通行权的，遇有车辆抢行时，应怎样做？', '抢行通过', '提前加速通过', '按优先权规定正常行驶不予避让', '减速避让，必要时停车让行', 'D', ' ');
INSERT INTO `tb_test` VALUES ('74', '这个标志是何含义？', '国道编号', '省道编号', '县道编号', '乡道编号', 'A', 'TEST_IMG/5eb4d75agw1e2916pe0z8j.jpg');
INSERT INTO `tb_test` VALUES ('75', '机动车仪表板上（如图所示）这个符号表示什么？', '一侧车门开启', '行李舱开启', '发动机舱开启', '燃油箱盖开启', 'B', 'TEST_IMG/5eb4d75agw1e291upmq9ej.jpg');
INSERT INTO `tb_test` VALUES ('76', '驾驶人户籍迁出原车辆管理所需要向什么地方的车辆管理所提出申请？', '迁出地', '居住地', '所在地', '迁入地', 'D', ' ');

-- ----------------------------
-- Table structure for `tb_testscore`
-- ----------------------------
DROP TABLE IF EXISTS `tb_testscore`;
CREATE TABLE `tb_testscore` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uid` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `score` int(11) DEFAULT NULL,
  `grade` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `time` datetime DEFAULT NULL,
  `scoretype` int(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_scoretype` (`scoretype`),
  KEY `fk_score_uid` (`uid`),
  CONSTRAINT `fk_score_uid` FOREIGN KEY (`uid`) REFERENCES `tb_user` (`uid`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `fk_scoretype` FOREIGN KEY (`scoretype`) REFERENCES `tb_scoretype` (`typeid`) ON DELETE SET NULL ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=104 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of tb_testscore
-- ----------------------------
INSERT INTO `tb_testscore` VALUES ('1', 'z', '80', 'B', '2019-06-09 21:08:40', '1');
INSERT INTO `tb_testscore` VALUES ('2', 'z', '90', '通过', '2019-06-06 21:17:45', '2');
INSERT INTO `tb_testscore` VALUES ('4', 'z', '0', 'D', '2019-06-09 21:23:31', '1');
INSERT INTO `tb_testscore` VALUES ('5', 'z', '99', '通过', '2019-06-09 21:26:31', '2');
INSERT INTO `tb_testscore` VALUES ('6', '2051', '40', 'D', '2019-06-09 22:06:10', '1');
INSERT INTO `tb_testscore` VALUES ('7', '2051', '100', 'A+', '2019-06-09 22:06:33', '1');
INSERT INTO `tb_testscore` VALUES ('8', '2051', '66', '不通过', '2019-06-09 22:06:37', '2');
INSERT INTO `tb_testscore` VALUES ('9', '3842', '100', 'A+', '2019-06-09 22:25:48', '1');
INSERT INTO `tb_testscore` VALUES ('10', '3842', '99', '通过', '2019-06-09 22:25:53', '2');
INSERT INTO `tb_testscore` VALUES ('11', '5330', '33', '不通过', '2019-06-09 23:13:08', '2');
INSERT INTO `tb_testscore` VALUES ('12', '5330', '40', 'D', '2019-06-09 23:13:23', '1');
INSERT INTO `tb_testscore` VALUES ('13', '5330', '80', 'B', '2019-06-09 23:13:34', '1');
INSERT INTO `tb_testscore` VALUES ('14', 'z', '40', 'D', '2019-06-10 00:21:36', '1');
INSERT INTO `tb_testscore` VALUES ('15', 'z', '60', 'C', '2019-06-10 00:21:45', '1');
INSERT INTO `tb_testscore` VALUES ('16', 'z', '80', 'B', '2019-06-10 00:22:00', '1');
INSERT INTO `tb_testscore` VALUES ('17', 'z', '99', '通过', '2019-06-10 00:22:04', '2');
INSERT INTO `tb_testscore` VALUES ('18', 'z', '40', 'D', '2019-06-10 00:22:20', '1');
INSERT INTO `tb_testscore` VALUES ('19', 'z', '100', 'A+', '2019-06-10 00:22:58', '1');
INSERT INTO `tb_testscore` VALUES ('20', 'z', '40', 'D', '2019-06-10 00:23:07', '1');
INSERT INTO `tb_testscore` VALUES ('21', 'z', '60', 'C', '2019-06-10 00:23:18', '1');
INSERT INTO `tb_testscore` VALUES ('22', 'z', '80', 'B', '2019-06-10 00:23:27', '1');
INSERT INTO `tb_testscore` VALUES ('23', 'z', '99', '通过', '2019-06-10 00:23:31', '2');
INSERT INTO `tb_testscore` VALUES ('24', 'z', '99', '通过', '2019-06-10 00:23:38', '2');
INSERT INTO `tb_testscore` VALUES ('25', 'z', '60', 'C', '2019-06-10 00:23:54', '1');
INSERT INTO `tb_testscore` VALUES ('26', 'z', '60', 'C', '2019-06-10 00:24:03', '1');
INSERT INTO `tb_testscore` VALUES ('27', 'z', '100', 'A+', '2019-06-11 16:16:45', '1');
INSERT INTO `tb_testscore` VALUES ('28', 'z', '99', '通过', '2019-06-11 16:16:49', '2');
INSERT INTO `tb_testscore` VALUES ('29', 'z', '0', '不通过', '2019-06-11 19:58:34', '2');
INSERT INTO `tb_testscore` VALUES ('30', 'z', '0', '不通过', '2019-06-11 20:25:42', '2');
INSERT INTO `tb_testscore` VALUES ('31', 'z', '0', '不通过', '2019-06-11 20:25:47', '2');
INSERT INTO `tb_testscore` VALUES ('32', 'z', '0', '不通过', '2019-06-11 20:25:55', '2');
INSERT INTO `tb_testscore` VALUES ('33', 'z', '0', '不通过', '2019-06-11 20:44:57', '2');
INSERT INTO `tb_testscore` VALUES ('34', 'z', '0', '不通过', '2019-06-11 20:47:30', '2');
INSERT INTO `tb_testscore` VALUES ('35', 'z', '0', '不通过', '2019-06-11 20:48:49', '2');
INSERT INTO `tb_testscore` VALUES ('36', 'z', '0', '不通过', '2019-06-11 20:57:20', '2');
INSERT INTO `tb_testscore` VALUES ('37', 'z', '0', '不通过', '2019-06-11 21:33:33', '2');
INSERT INTO `tb_testscore` VALUES ('38', 'z', '0', '不通过', '2019-06-11 21:41:12', '2');
INSERT INTO `tb_testscore` VALUES ('39', 'z', '0', '不通过', '2019-06-11 22:23:57', '2');
INSERT INTO `tb_testscore` VALUES ('40', 'z', '0', '不通过', '2019-06-11 22:45:54', '2');
INSERT INTO `tb_testscore` VALUES ('41', 'z', '0', 'D', '2019-06-11 23:32:49', '1');
INSERT INTO `tb_testscore` VALUES ('42', 'z', '100', 'A+', '2019-06-11 23:40:19', '1');
INSERT INTO `tb_testscore` VALUES ('43', 'z', '0', '不通过', '2019-06-11 23:40:26', '2');
INSERT INTO `tb_testscore` VALUES ('44', 'z', '0', '不通过', '2019-06-11 23:41:57', '2');
INSERT INTO `tb_testscore` VALUES ('45', 'z', '100', 'A+', '2019-06-11 23:47:11', '1');
INSERT INTO `tb_testscore` VALUES ('46', 'z', '0', '不通过', '2019-06-11 23:47:22', '2');
INSERT INTO `tb_testscore` VALUES ('47', 'z', '0', '不通过', '2019-06-11 23:48:00', '2');
INSERT INTO `tb_testscore` VALUES ('48', 'z', '0', '不通过', '2019-06-12 00:00:46', '2');
INSERT INTO `tb_testscore` VALUES ('49', 'z', '99', '通过', '2019-06-12 00:01:44', '2');
INSERT INTO `tb_testscore` VALUES ('50', 'z', '0', '不通过', '2019-06-12 00:19:17', '2');
INSERT INTO `tb_testscore` VALUES ('51', 'z', '0', '不通过', '2019-06-12 01:38:24', '2');
INSERT INTO `tb_testscore` VALUES ('52', 'z', '30', '不通过', '2019-06-12 11:37:33', '2');
INSERT INTO `tb_testscore` VALUES ('53', 'z', '0', '不通过', '2019-06-12 11:43:14', '2');
INSERT INTO `tb_testscore` VALUES ('54', 'z', '50', '不通过', '2019-06-12 12:33:06', '2');
INSERT INTO `tb_testscore` VALUES ('55', 'z', '30', '不通过', '2019-06-12 12:45:56', '2');
INSERT INTO `tb_testscore` VALUES ('56', 'z', '100', '通过', '2019-06-12 12:52:10', '2');
INSERT INTO `tb_testscore` VALUES ('57', 'z', '0', '不通过', '2019-06-12 12:53:11', '2');
INSERT INTO `tb_testscore` VALUES ('58', 'z', '0', '不通过', '2019-06-12 12:54:43', '2');
INSERT INTO `tb_testscore` VALUES ('59', 'z', '0', '不通过', '2019-06-12 12:54:48', '2');
INSERT INTO `tb_testscore` VALUES ('60', 'z', '0', 'D', '2019-06-12 12:55:06', '1');
INSERT INTO `tb_testscore` VALUES ('61', 'z', '0', '不通过', '2019-06-12 12:56:28', '2');
INSERT INTO `tb_testscore` VALUES ('62', 'z', '0', '不通过', '2019-06-12 12:56:32', '2');
INSERT INTO `tb_testscore` VALUES ('63', 'z', '0', 'D', '2019-06-12 12:56:40', '1');
INSERT INTO `tb_testscore` VALUES ('64', 'z', '0', 'D', '2019-06-12 12:56:49', '1');
INSERT INTO `tb_testscore` VALUES ('65', 'z', '0', 'D', '2019-06-12 13:43:22', '1');
INSERT INTO `tb_testscore` VALUES ('66', 'z', '0', '不通过', '2019-06-12 13:43:27', '2');
INSERT INTO `tb_testscore` VALUES ('67', 'z', '0', '不通过', '2019-06-12 13:43:30', '2');
INSERT INTO `tb_testscore` VALUES ('68', 'z', '0', '不通过', '2019-06-12 13:44:13', '2');
INSERT INTO `tb_testscore` VALUES ('69', 'z', '0', '不通过', '2019-06-12 14:24:14', '2');
INSERT INTO `tb_testscore` VALUES ('70', 'z', '0', 'D', '2019-06-12 16:17:00', '1');
INSERT INTO `tb_testscore` VALUES ('71', 'z', '0', '不通过', '2019-06-12 16:17:04', '2');
INSERT INTO `tb_testscore` VALUES ('72', 'z', '60', 'C', '2019-06-13 00:19:29', '1');
INSERT INTO `tb_testscore` VALUES ('73', 'z', '60', 'C', '2019-06-13 00:20:00', '1');
INSERT INTO `tb_testscore` VALUES ('74', 'z', '80', 'B', '2019-06-13 00:20:27', '1');
INSERT INTO `tb_testscore` VALUES ('75', 'z', '100', 'A+', '2019-06-13 00:21:43', '1');
INSERT INTO `tb_testscore` VALUES ('76', 'z', '80', '不通过', '2019-06-13 00:21:46', '2');
INSERT INTO `tb_testscore` VALUES ('77', 'z', '0', 'D', '2019-06-13 00:36:17', '1');
INSERT INTO `tb_testscore` VALUES ('78', 'z', '20', 'D', '2019-06-13 00:36:42', '1');
INSERT INTO `tb_testscore` VALUES ('79', 'z', '20', 'D', '2019-06-13 00:37:20', '1');
INSERT INTO `tb_testscore` VALUES ('80', 'z', '20', 'D', '2019-06-13 00:37:35', '1');
INSERT INTO `tb_testscore` VALUES ('81', 'z', '20', 'D', '2019-06-13 00:37:50', '1');
INSERT INTO `tb_testscore` VALUES ('82', 'z', '20', 'D', '2019-06-13 00:39:19', '1');
INSERT INTO `tb_testscore` VALUES ('83', 'z', '20', 'D', '2019-06-13 00:41:17', '1');
INSERT INTO `tb_testscore` VALUES ('84', 'z', '0', 'D', '2019-06-13 00:41:19', '1');
INSERT INTO `tb_testscore` VALUES ('85', 'z', '20', 'D', '2019-06-13 00:41:23', '1');
INSERT INTO `tb_testscore` VALUES ('86', 'z', '0', 'D', '2019-06-13 00:41:28', '1');
INSERT INTO `tb_testscore` VALUES ('87', 'z', '0', 'D', '2019-06-13 00:41:34', '1');
INSERT INTO `tb_testscore` VALUES ('88', 'z', '0', 'D', '2019-06-13 00:41:39', '1');
INSERT INTO `tb_testscore` VALUES ('89', 'z', '20', 'D', '2019-06-13 00:41:43', '1');
INSERT INTO `tb_testscore` VALUES ('90', 'z', '0', 'D', '2019-06-13 00:41:48', '1');
INSERT INTO `tb_testscore` VALUES ('91', 'z', '0', 'D', '2019-06-13 00:41:57', '1');
INSERT INTO `tb_testscore` VALUES ('92', 'z', '40', 'D', '2019-06-13 00:42:07', '1');
INSERT INTO `tb_testscore` VALUES ('93', 'z', '40', 'D', '2019-06-13 00:42:18', '1');
INSERT INTO `tb_testscore` VALUES ('94', 'z', '20', 'D', '2019-06-13 00:43:03', '1');
INSERT INTO `tb_testscore` VALUES ('95', 'z', '100', 'A+', '2019-06-13 00:43:51', '1');
INSERT INTO `tb_testscore` VALUES ('96', 'z', '40', 'D', '2019-06-13 00:47:56', '1');
INSERT INTO `tb_testscore` VALUES ('97', 'z', '100', 'A+', '2019-06-13 00:50:46', '1');
INSERT INTO `tb_testscore` VALUES ('98', '1516', '100', 'A+', '2019-06-13 01:00:07', '1');
INSERT INTO `tb_testscore` VALUES ('99', '1516', '100', '通过', '2019-06-13 01:00:27', '2');
INSERT INTO `tb_testscore` VALUES ('100', '1516', '0', '不通过', '2019-06-13 01:01:12', '2');
INSERT INTO `tb_testscore` VALUES ('101', 'z', '50', '不通过', '2019-06-13 16:56:37', '2');
INSERT INTO `tb_testscore` VALUES ('102', 'z', '90', '通过', '2019-06-14 00:18:38', '2');
INSERT INTO `tb_testscore` VALUES ('103', 'z', '20', '不通过', '2019-06-16 13:53:34', '2');

-- ----------------------------
-- Table structure for `tb_user`
-- ----------------------------
DROP TABLE IF EXISTS `tb_user`;
CREATE TABLE `tb_user` (
  `uid` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `pwd` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `name` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `sex` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `email` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `phone` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `registdate` datetime DEFAULT NULL,
  `usertype` int(11) DEFAULT NULL,
  PRIMARY KEY (`uid`),
  UNIQUE KEY `uk_email` (`email`),
  KEY `fk_usertype` (`usertype`),
  CONSTRAINT `fk_usertype` FOREIGN KEY (`usertype`) REFERENCES `tb_usertype` (`typeid`) ON DELETE SET NULL ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of tb_user
-- ----------------------------
INSERT INTO `tb_user` VALUES ('1516', 'kms', 'kms', '男', 'kamisa@email', '', '2019-06-09 23:11:22', '1');
INSERT INTO `tb_user` VALUES ('1776', 'zxcv', 'yasm', '女', 'yasm@phone', '02508999', '2019-06-09 22:03:43', '1');
INSERT INTO `tb_user` VALUES ('2051', 'pkpk', 'penkon', '男', 'penkon@mail', '158080', '2019-06-09 22:05:53', '1');
INSERT INTO `tb_user` VALUES ('2270', 'whos', '金铃', '女', 'jinlin@mail', '15016001710', '2019-06-07 15:27:08', '1');
INSERT INTO `tb_user` VALUES ('2773', '1908', '启程', '女', 'qicheng@qq.com', '', '2019-06-07 10:04:53', '1');
INSERT INTO `tb_user` VALUES ('2816', '00001', 'yeys', '男', 'yeys001@qq.com', '', '2019-06-06 11:36:13', '1');
INSERT INTO `tb_user` VALUES ('3842', 'GHJK', 'LiLi', '男', 'lili111@mail', '', '2019-06-09 22:25:26', '1');
INSERT INTO `tb_user` VALUES ('4012', 'opop', 'han', '女', 'han@3g', '204683', '2019-06-13 15:34:08', '1');
INSERT INTO `tb_user` VALUES ('4417', 'mysql', 'Yonatan', '男', 'LYD109@mail', '123456', '2019-06-06 12:27:56', '1');
INSERT INTO `tb_user` VALUES ('5104', '1111', 'sun', '女', 'sun@mail', '936482', '2019-06-07 10:13:03', '1');
INSERT INTO `tb_user` VALUES ('5330', 'misaka', 'misaka', '女', 'misaka@mail', '02030405', '2019-06-09 23:12:11', '1');
INSERT INTO `tb_user` VALUES ('5480', 'zxcv', 'orian', '男', 'anime@yc.xzy', '', '2019-06-05 16:32:20', '1');
INSERT INTO `tb_user` VALUES ('7366', '0000', 'Jedi', '女', 'Jedi@mail', '', '2019-06-07 10:18:48', '1');
INSERT INTO `tb_user` VALUES ('7571', '1111', '李天明', '男', 'litianming@mail', '', '2019-06-07 10:14:40', '1');
INSERT INTO `tb_user` VALUES ('7735', 'mons', 'monster', '男', 'monster@mail', '88888888', '2019-06-09 22:16:44', '1');
INSERT INTO `tb_user` VALUES ('8418', 'life', 'yasm', '女', 'yasm@create.pt', '0523333', '2019-06-06 10:38:03', '1');
INSERT INTO `tb_user` VALUES ('r', 'r', '李明', null, null, null, '2019-06-18 10:38:46', '2');
INSERT INTO `tb_user` VALUES ('z', 'z', 'Tom', '男', 'tom@cat.mail', '9999', '2019-06-07 10:30:08', '1');

-- ----------------------------
-- Table structure for `tb_usertype`
-- ----------------------------
DROP TABLE IF EXISTS `tb_usertype`;
CREATE TABLE `tb_usertype` (
  `typeid` int(11) NOT NULL,
  `typename` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (`typeid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of tb_usertype
-- ----------------------------
INSERT INTO `tb_usertype` VALUES ('1', '考生');
INSERT INTO `tb_usertype` VALUES ('2', '管理员');
