/*
 Navicat Premium Data Transfer

 Source Server: localhost-8.0
 Source Server Type : MySQL
 Source Server Version : 80026
 Source Host  : localhost:3306
 Source Schema: seller_system

 Target Server Type : MySQL
 Target Server Version : 80026
 File Encoding: 65001

 Date: 29/07/2022 17:13:07
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for customer
-- ----------------------------
DROP TABLE IF EXISTS `customer`;
CREATE TABLE `customer`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `customer_name` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `area_code` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `maintainer_id` int NOT NULL,
  `creater_id` int NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '客户' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of customer
-- ----------------------------
INSERT INTO `customer` VALUES (1, '北京雾灵山有限公司 ', '京', 1, 6);
INSERT INTO `customer` VALUES (2, '北京慕田峪有限公司 ', '京', 1, 6);
INSERT INTO `customer` VALUES (3, '北京景山有限公司', '京', 1, 5);
INSERT INTO `customer` VALUES (4, '天津盘山有限公司', '津', 1, 7);
INSERT INTO `customer` VALUES (5, '河北燕山有限公司', '冀', 1, 8);
INSERT INTO `customer` VALUES (6, '河北狼牙山有限公司 ', '冀', 1, 8);
INSERT INTO `customer` VALUES (7, '内蒙贺兰山有限公司 ', '蒙', 1, 9);
INSERT INTO `customer` VALUES (8, '内蒙阴山有限公司', '蒙', 1, 9);
INSERT INTO `customer` VALUES (9, '山西五台山有限公司 ', '晋', 1, 10);
INSERT INTO `customer` VALUES (10, '山西恒山有限公司', '晋', 1, 10);

SET FOREIGN_KEY_CHECKS = 1;
