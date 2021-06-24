/*
 Navicat MySQL Data Transfer

 Source Server         : cool
 Source Server Type    : MySQL
 Source Server Version : 80024
 Source Host           : localhost:3306
 Source Schema         : bookshop

 Target Server Type    : MySQL
 Target Server Version : 80024
 File Encoding         : 65001

 Date: 21/05/2021 22:50:46
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for tb_book
-- ----------------------------
DROP TABLE IF EXISTS `tb_book`;
CREATE TABLE `tb_book`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '编号',
  `book_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '小说名',
  `book_author` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '小说作者',
  `book_type` int NOT NULL COMMENT '小说类型',
  `introduction` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '小说介绍',
  `book_image` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '小说图片',
  `pages` int NOT NULL COMMENT '小说章节数',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `book_type`(`book_type`) USING BTREE,
  CONSTRAINT `tb_book_ibfk_1` FOREIGN KEY (`book_type`) REFERENCES `tb_type` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_book
-- ----------------------------
INSERT INTO `tb_book` VALUES (1, '逆天邪神', '火星引力', 11, '掌天毒之珠，承邪神之血，修逆天之力，一代邪神，君临天下！ 琅缳山，绝云崖，沧云大陆四大极恶之地之首。 绝云崖下被称作死神的墓地，无数年间，坠下绝云崖者不计其数，其中甚至有三个力量通天的天王级强者，却从未有人得以生还。 ', 'D:\\fish\\3\\bookshop\\src\\main\\resources\\static\\img\\ntxs.png', 1100);
INSERT INTO `tb_book` VALUES (2, '神墓', '辰东', 12, '心潮澎湃，无限幻想，迎风挥击千层浪，少年不败热血！\r\n\r\n', 'D:\\fish\\3\\bookshop\\src\\main\\resources\\static\\img\\shenmu.png', 768);
INSERT INTO `tb_book` VALUES (3, '盘龙', '我吃西红柿', 11, '魔兽践踏，巨龙咆哮，巫师诅咒，魔法璀璨之光照耀知识灯塔！\r\n\r\n', 'D:\\fish\\3\\bookshop\\src\\main\\resources\\static\\img\\panlong.png', 855);

-- ----------------------------
-- Table structure for tb_type
-- ----------------------------
DROP TABLE IF EXISTS `tb_type`;
CREATE TABLE `tb_type`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '类型id',
  `parent_id` int NULL DEFAULT NULL COMMENT '父级id',
  `type_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '类型名',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 27 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_type
-- ----------------------------
INSERT INTO `tb_type` VALUES (1, 0, '小说');
INSERT INTO `tb_type` VALUES (2, 0, '出版原著');
INSERT INTO `tb_type` VALUES (11, 1, '玄幻魔幻');
INSERT INTO `tb_type` VALUES (12, 1, '都市言情');
INSERT INTO `tb_type` VALUES (13, 1, '武侠奇幻');
INSERT INTO `tb_type` VALUES (14, 1, '军事故事');
INSERT INTO `tb_type` VALUES (15, 1, '游戏体育');
INSERT INTO `tb_type` VALUES (16, 1, '灵异悬疑');
INSERT INTO `tb_type` VALUES (21, 2, '历史人文');
INSERT INTO `tb_type` VALUES (22, 2, '人物传记');
INSERT INTO `tb_type` VALUES (23, 2, '科学读物');
INSERT INTO `tb_type` VALUES (24, 2, '少儿科普');
INSERT INTO `tb_type` VALUES (25, 2, '医学教辅');
INSERT INTO `tb_type` VALUES (26, 2, '外语语言');

-- ----------------------------
-- Table structure for tb_user
-- ----------------------------
DROP TABLE IF EXISTS `tb_user`;
CREATE TABLE `tb_user`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_user
-- ----------------------------
INSERT INTO `tb_user` VALUES (1, '123', '123');
INSERT INTO `tb_user` VALUES (2, '234', '234');
INSERT INTO `tb_user` VALUES (3, '345', '345');

-- ----------------------------
-- Table structure for tb_user_book
-- ----------------------------
DROP TABLE IF EXISTS `tb_user_book`;
CREATE TABLE `tb_user_book`  (
  `uid` int NOT NULL COMMENT '用户id',
  `bid` int NULL DEFAULT NULL COMMENT '书名id',
  INDEX `bid`(`bid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_user_book
-- ----------------------------
INSERT INTO `tb_user_book` VALUES (2, 2);
INSERT INTO `tb_user_book` VALUES (3, 1);
INSERT INTO `tb_user_book` VALUES (1, 2);
INSERT INTO `tb_user_book` VALUES (1, 3);
INSERT INTO `tb_user_book` VALUES (1, 1);

SET FOREIGN_KEY_CHECKS = 1;
