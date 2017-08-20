/*
Navicat MySQL Data Transfer

Source Server         : 本地
Source Server Version : 50173
Source Host           : localhost:3306
Source Database       : nfc

Target Server Type    : MYSQL
Target Server Version : 50173
File Encoding         : 65001

Date: 2017-08-20 20:40:58
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `sys_app_user`
-- ----------------------------
DROP TABLE IF EXISTS `sys_app_user`;
CREATE TABLE `sys_app_user` (
  `USER_ID` varchar(100) NOT NULL,
  `USERNAME` varchar(255) DEFAULT NULL,
  `PASSWORD` varchar(255) DEFAULT NULL,
  `NAME` varchar(255) DEFAULT NULL,
  `ROLE_ID` varchar(100) DEFAULT NULL,
  `CARTEAM_ID` varchar(100) DEFAULT NULL COMMENT '车队ID',
  PRIMARY KEY (`USER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_app_user
-- ----------------------------
INSERT INTO `sys_app_user` VALUES ('1d17b69909d040e8aae808d1bba52618', '111', '698d51a19d8a121ce581499d7b701668', '111', 'f944a9df72634249bbcb8cb73b0c9b86', 'deb6642bc65145b9a2433b14885c01cd');
INSERT INTO `sys_app_user` VALUES ('6af560dca86c43cbb77132a59df3e9b3', 'ddd', '77963b7a931377ad4ab5ad6a9cd718aa', 'ddd', 'f944a9df72634249bbcb8cb73b0c9b86', 'deb6642bc65145b9a2433b14885c01cd');
INSERT INTO `sys_app_user` VALUES ('905f493010194d7da2cdc250db224a85', 'AAA', 'e1faffb3e614e6c2fba74296962386b7', 'DDD', 'f944a9df72634249bbcb8cb73b0c9b86', 'deb6642bc65145b9a2433b14885c01cd');
INSERT INTO `sys_app_user` VALUES ('f3f8e8c0917a4cf98dd3165800100055', 'AA', '202cb962ac59075b964b07152d234b70', '33', '55896f5ce3c0494fa6850775a4e29ff6', 'deb6642bc65145b9a2433b14885c01cd');

-- ----------------------------
-- Table structure for `sys_gl_qx`
-- ----------------------------
DROP TABLE IF EXISTS `sys_gl_qx`;
CREATE TABLE `sys_gl_qx` (
  `GL_ID` varchar(100) NOT NULL,
  `ROLE_ID` varchar(100) DEFAULT NULL,
  `FX_QX` int(10) DEFAULT NULL COMMENT '发送邮件权限',
  `FW_QX` int(10) DEFAULT NULL COMMENT '发送短信权限',
  `QX1` int(10) DEFAULT NULL,
  `QX2` int(10) DEFAULT NULL,
  `QX3` int(10) DEFAULT NULL,
  `QX4` int(10) DEFAULT NULL,
  PRIMARY KEY (`GL_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_gl_qx
-- ----------------------------
INSERT INTO `sys_gl_qx` VALUES ('040c44bcf58745ce951d9b2b7f56be82', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `sys_gl_qx` VALUES ('1', '2', '1', '1', '1', '1', '1', '1');
INSERT INTO `sys_gl_qx` VALUES ('2', '1', '0', '1', '1', '1', '1', '1');
INSERT INTO `sys_gl_qx` VALUES ('767a57ff070a40e3935439d73d6a76cd', '1', '0', '0', '0', '0', '0', '0');
INSERT INTO `sys_gl_qx` VALUES ('92bda15b69024fe0a2ed8e1b4785364a', '7', '0', '0', '0', '0', '0', '0');
INSERT INTO `sys_gl_qx` VALUES ('b221d366ba5a45feaa82d3dc49c5f2f4', '040c44bcf58745ce951d9b2b7f56be82', '0', '0', '0', '0', '0', '0');

-- ----------------------------
-- Table structure for `sys_menu`
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `MENU_ID` int(11) NOT NULL,
  `MENU_NAME` varchar(255) DEFAULT NULL,
  `MENU_URL` varchar(255) DEFAULT NULL,
  `PARENT_ID` varchar(100) DEFAULT NULL,
  `MENU_ORDER` varchar(100) DEFAULT NULL,
  `MENU_ICON` varchar(30) DEFAULT NULL,
  `MENU_TYPE` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`MENU_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES ('1', '系统管理', '#', '0', '1', 'icon-calendar', '1');
INSERT INTO `sys_menu` VALUES ('2', '组织管理', 'role.do', '1', '2', null, '1');
INSERT INTO `sys_menu` VALUES ('5', '系统用户', 'user/listUsers.do', '1', '3', null, '1');
INSERT INTO `sys_menu` VALUES ('6', '基础配置', '#', '0', '1', 'icon-desktop', '');
INSERT INTO `sys_menu` VALUES ('7', '轮播图列表', 'carousel_figure/list.do', '6', '1', null, '');
INSERT INTO `sys_menu` VALUES ('8', '类别列表', 'category/list.do', '6', '2', null, '');
INSERT INTO `sys_menu` VALUES ('9', '配送点列表', 'address/list.do', '6', '3', null, '');
INSERT INTO `sys_menu` VALUES ('10', '配送费列表', 'delivery_fee/list.do', '6', '4', null, '');
INSERT INTO `sys_menu` VALUES ('11', '商品管理', '#', '0', '2', 'icon-folder-open', '');
INSERT INTO `sys_menu` VALUES ('12', '订单管理', '#', '0', '3', 'icon-list-alt', '');
INSERT INTO `sys_menu` VALUES ('13', '财务管理', '#', '0', '4', 'icon-edit', '');
INSERT INTO `sys_menu` VALUES ('14', '商品列表', 'lunch/list.do', '11', '1', null, '');
INSERT INTO `sys_menu` VALUES ('15', '劵/红包管理', '#', '0', '5', 'icon-film', '');
INSERT INTO `sys_menu` VALUES ('16', '红包列表', 'redpackage/list.do', '15', '1', null, '');
INSERT INTO `sys_menu` VALUES ('17', '每日菜谱', 'daily_menu/list.do', '11', '2', null, '');
INSERT INTO `sys_menu` VALUES ('18', '会员管理 ', '#', '0', '6', 'icon-comments', '2');
INSERT INTO `sys_menu` VALUES ('19', '会员列表', 'member/list.do', '18', '1', null, '2');
INSERT INTO `sys_menu` VALUES ('20', '预定时间', 'scheduled_time/list.do', '11', '3', null, '');
INSERT INTO `sys_menu` VALUES ('21', '周卡列表', 'weekmeal_card/list.do', '15', '2', null, '');
INSERT INTO `sys_menu` VALUES ('22', '周卡购买记录', 'weekmeal_card_buy_wxmember/list.do', '13', '2', null, '');
INSERT INTO `sys_menu` VALUES ('23', '待配送订单', 'order/list.do?order_status=2', '12', '2', null, '');
INSERT INTO `sys_menu` VALUES ('24', '已完成订单', 'order/list.do?order_status=4', '12', '4', null, '');
INSERT INTO `sys_menu` VALUES ('25', '退款订单', 'order/list.do?order_status=99', '12', '99', null, '');
INSERT INTO `sys_menu` VALUES ('26', '销售明细', 'order/saleHistory.do', '13', '1', null, '');
INSERT INTO `sys_menu` VALUES ('27', '预定订单处理', 'order/list.do?order_status=1', '12', '1', null, '');
INSERT INTO `sys_menu` VALUES ('28', '配送中订单', 'order/list.do?order_status=3', '12', '3', null, '');

-- ----------------------------
-- Table structure for `sys_role`
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `ROLE_ID` varchar(100) NOT NULL,
  `ROLE_NAME` varchar(100) DEFAULT NULL,
  `RIGHTS` varchar(255) DEFAULT NULL,
  `PARENT_ID` varchar(100) DEFAULT NULL,
  `ADD_QX` varchar(255) DEFAULT NULL,
  `DEL_QX` varchar(255) DEFAULT NULL,
  `EDIT_QX` varchar(255) DEFAULT NULL,
  `CHA_QX` varchar(255) DEFAULT NULL,
  `QX_ID` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`ROLE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('1', '系统管理员', '530579430', '0', '1', '1', '1', '1', '1');
INSERT INTO `sys_role` VALUES ('2', '超级管理员', '530579430', '1', '238943974', '238943974', '238943974', '238943974', '2');

-- ----------------------------
-- Table structure for `sys_user`
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `USER_ID` varchar(100) NOT NULL,
  `USERNAME` varchar(255) DEFAULT NULL,
  `PASSWORD` varchar(255) DEFAULT NULL,
  `NAME` varchar(255) DEFAULT NULL,
  `RIGHTS` varchar(255) DEFAULT NULL,
  `ROLE_ID` varchar(100) DEFAULT NULL,
  `LAST_LOGIN` varchar(255) DEFAULT NULL,
  `IP` varchar(100) DEFAULT NULL,
  `STATUS` varchar(32) DEFAULT NULL,
  `BZ` varchar(255) DEFAULT NULL,
  `SKIN` varchar(100) DEFAULT NULL,
  `EMAIL` varchar(32) DEFAULT NULL,
  `NUMBER` varchar(100) DEFAULT NULL,
  `PHONE` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`USER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('1', 'admin', 'e10adc3949ba59abbe56e057f20f883e', '九鱼', '1133671055321055258374707980945218933803269864762743594642571294', '1', '2017-08-20 00:19:38', '127.0.0.1', '0', '超级管理员', 'default', 'admin@main.com', '123', '15260282340');

-- ----------------------------
-- Table structure for `sys_user_qx`
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_qx`;
CREATE TABLE `sys_user_qx` (
  `U_ID` varchar(100) NOT NULL,
  `C1` int(10) DEFAULT NULL,
  `C2` int(10) DEFAULT NULL,
  `C3` int(10) DEFAULT NULL,
  `C4` int(10) DEFAULT NULL,
  `Q1` int(10) DEFAULT NULL,
  `Q2` int(10) DEFAULT NULL,
  `Q3` int(10) DEFAULT NULL,
  `Q4` int(10) DEFAULT NULL,
  PRIMARY KEY (`U_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_user_qx
-- ----------------------------
INSERT INTO `sys_user_qx` VALUES ('040c44bcf58745ce951d9b2b7f56be82', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `sys_user_qx` VALUES ('1', '1', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `sys_user_qx` VALUES ('2', '1', '1', '1', '1', '1', '1', '1', '1');
INSERT INTO `sys_user_qx` VALUES ('767a57ff070a40e3935439d73d6a76cd', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `sys_user_qx` VALUES ('92bda15b69024fe0a2ed8e1b4785364a', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `sys_user_qx` VALUES ('b221d366ba5a45feaa82d3dc49c5f2f4', '0', '0', '0', '0', '0', '0', '0', '0');

-- ----------------------------
-- Table structure for `sys_zidian`
-- ----------------------------
DROP TABLE IF EXISTS `sys_zidian`;
CREATE TABLE `sys_zidian` (
  `ZD_ID` varchar(100) NOT NULL,
  `NAME` varchar(100) DEFAULT NULL,
  `BIANMA` varchar(100) DEFAULT NULL,
  `ORDY_BY` int(10) DEFAULT NULL,
  `PARENT_ID` varchar(100) DEFAULT NULL,
  `JB` int(10) DEFAULT NULL,
  `P_BM` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`ZD_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_zidian
-- ----------------------------
INSERT INTO `sys_zidian` VALUES ('1167610461f444b5ad30e4abfab6cf72', '供货商', 'B', '1', 'ebf64019f3844ad98f16f890fb382306', '2', 'A_B');
INSERT INTO `sys_zidian` VALUES ('1ba1abd122814503b424a43855bc3cf2', '注册用户', 'A_A', '2', 'ebf64019f3844ad98f16f890fb382306', '2', 'A_A_A');
INSERT INTO `sys_zidian` VALUES ('8ffec2cd5d1c4dbab4d620f2a32a353a', '送货员', 'A_C', '3', 'ebf64019f3844ad98f16f890fb382306', '2', 'A_A_C');
INSERT INTO `sys_zidian` VALUES ('ebf64019f3844ad98f16f890fb382306', '用户分类', 'A', '1', '0', '1', 'A');
INSERT INTO `sys_zidian` VALUES ('f937550dd3ca4c5bad65a3b3d18d9fed', '管理员', 'A_D', '4', 'ebf64019f3844ad98f16f890fb382306', '2', 'A_A_D');

-- ----------------------------
-- Table structure for `tb_address`
-- ----------------------------
DROP TABLE IF EXISTS `tb_address`;
CREATE TABLE `tb_address` (
  `address_id` int(11) NOT NULL AUTO_INCREMENT,
  `address_name` varchar(100) DEFAULT NULL,
  `detail_address` varchar(100) DEFAULT NULL,
  `createtime` datetime DEFAULT NULL,
  `updatetime` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`address_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_address
-- ----------------------------
INSERT INTO `tb_address` VALUES ('1', '国泰科技大厦', '名河路了撒大大啊', '2017-07-29 11:19:55', '2017-08-01 11:16:32');

-- ----------------------------
-- Table structure for `tb_carousel_figure`
-- ----------------------------
DROP TABLE IF EXISTS `tb_carousel_figure`;
CREATE TABLE `tb_carousel_figure` (
  `carousel_figure_id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(100) DEFAULT NULL,
  `image_url` varchar(100) DEFAULT NULL,
  `startdate` date DEFAULT NULL,
  `enddate` date DEFAULT NULL,
  `click_number` int(11) DEFAULT NULL,
  `link_type` varchar(4) DEFAULT NULL,
  `link_content` varchar(100) DEFAULT NULL,
  `createtime` datetime DEFAULT NULL,
  `updatetime` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `address_id` varchar(100) DEFAULT '0',
  PRIMARY KEY (`carousel_figure_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_carousel_figure
-- ----------------------------
INSERT INTO `tb_carousel_figure` VALUES ('4', ' 第一张', 'http://localhost/FileSave//carousel/f479d58f6fc74aea824cd43e3102a2af.jpg', '2017-08-02', '2017-08-31', '0', '1', '', '2017-08-02 14:14:55', '2017-08-12 13:33:53', null);
INSERT INTO `tb_carousel_figure` VALUES ('5', ' 第二张', 'http://localhost/FileSave//carousel/4a0eecac645d4ffb8db4abadbbf893b7.jpg', '2017-08-02', '2017-08-28', '0', '2', 'https://www.jiuyuvip.com', '2017-08-02 14:15:23', '2017-08-12 13:33:58', null);
INSERT INTO `tb_carousel_figure` VALUES ('6', ' 第三张', 'http://localhost/FileSave//carousel/678869bf432b4be681855af9fe94aa50.jpg', '2017-08-02', '2017-08-25', '0', '3', '1', '2017-08-02 14:16:03', '2017-08-12 13:34:03', null);

-- ----------------------------
-- Table structure for `tb_category`
-- ----------------------------
DROP TABLE IF EXISTS `tb_category`;
CREATE TABLE `tb_category` (
  `category_id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(100) DEFAULT NULL,
  `image_url` varchar(100) DEFAULT NULL,
  `sort` varchar(4) DEFAULT '10',
  `createtime` datetime DEFAULT NULL,
  `updatetime` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `address_id` varchar(100) DEFAULT '0',
  PRIMARY KEY (`category_id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_category
-- ----------------------------
INSERT INTO `tb_category` VALUES ('1', '精品便当', 'http://localhost/FileSave//category/ee14b49321634f20be7995ef4855b518.png', '0', '2017-07-29 11:01:30', '2017-08-19 12:26:04', null);

-- ----------------------------
-- Table structure for `tb_daily_menu`
-- ----------------------------
DROP TABLE IF EXISTS `tb_daily_menu`;
CREATE TABLE `tb_daily_menu` (
  `daily_menu_id` int(11) NOT NULL AUTO_INCREMENT,
  `day` date DEFAULT NULL,
  `lunch_idstr` varchar(100) DEFAULT NULL,
  `address_id` int(11) DEFAULT NULL,
  `updatetime` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`daily_menu_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_daily_menu
-- ----------------------------
INSERT INTO `tb_daily_menu` VALUES ('3', '2017-08-19', '001,', null, '2017-08-19 15:17:53');
INSERT INTO `tb_daily_menu` VALUES ('4', '2017-08-20', '002,001,', null, '2017-08-19 15:18:28');

-- ----------------------------
-- Table structure for `tb_delivery_fee`
-- ----------------------------
DROP TABLE IF EXISTS `tb_delivery_fee`;
CREATE TABLE `tb_delivery_fee` (
  `delivery_fee_id` int(11) NOT NULL AUTO_INCREMENT,
  `number` int(11) DEFAULT NULL,
  `money` int(11) DEFAULT NULL,
  `createtime` datetime DEFAULT NULL,
  `updatetime` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`delivery_fee_id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_delivery_fee
-- ----------------------------
INSERT INTO `tb_delivery_fee` VALUES ('1', '1', '5', '2017-07-29 14:09:52', '2017-07-29 14:12:53');
INSERT INTO `tb_delivery_fee` VALUES ('2', '2', '6', '2017-07-29 14:09:57', '2017-07-29 14:12:55');
INSERT INTO `tb_delivery_fee` VALUES ('3', '3', '7', '2017-07-29 14:09:52', '2017-07-29 14:12:56');
INSERT INTO `tb_delivery_fee` VALUES ('4', '4', '8', '2017-07-29 14:09:52', '2017-07-29 14:12:56');
INSERT INTO `tb_delivery_fee` VALUES ('5', '5', '9', '2017-07-29 14:09:52', '2017-07-29 14:12:59');
INSERT INTO `tb_delivery_fee` VALUES ('6', '6', '8', '2017-07-29 14:09:52', '2017-07-29 14:13:00');
INSERT INTO `tb_delivery_fee` VALUES ('7', '7', '7', '2017-07-29 14:09:52', '2017-07-29 14:13:01');
INSERT INTO `tb_delivery_fee` VALUES ('8', '8', '6', '2017-07-29 14:09:52', '2017-07-29 14:13:02');
INSERT INTO `tb_delivery_fee` VALUES ('9', '9', '5', '2017-07-29 14:09:52', '2017-07-29 14:13:03');
INSERT INTO `tb_delivery_fee` VALUES ('10', '10', '4', '2017-07-29 14:09:52', '2017-07-29 14:13:04');
INSERT INTO `tb_delivery_fee` VALUES ('11', null, null, '2017-07-31 16:30:23', '2017-07-31 16:30:23');

-- ----------------------------
-- Table structure for `tb_log`
-- ----------------------------
DROP TABLE IF EXISTS `tb_log`;
CREATE TABLE `tb_log` (
  `log_id` int(11) NOT NULL AUTO_INCREMENT,
  `type` varchar(100) DEFAULT NULL,
  `order_id` varchar(100) DEFAULT NULL,
  `message` text,
  `createtime` datetime DEFAULT NULL,
  PRIMARY KEY (`log_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_log
-- ----------------------------

-- ----------------------------
-- Table structure for `tb_lunch`
-- ----------------------------
DROP TABLE IF EXISTS `tb_lunch`;
CREATE TABLE `tb_lunch` (
  `lunch_id` varchar(3) NOT NULL,
  `lunch_name` varchar(100) DEFAULT NULL,
  `show_sale_money` varchar(11) DEFAULT NULL,
  `sale_money` varchar(11) DEFAULT NULL,
  `index_images` varchar(100) DEFAULT NULL,
  `dc_images_small` varchar(100) DEFAULT NULL,
  `dc_images` varchar(100) DEFAULT NULL,
  `yd_images` varchar(100) DEFAULT NULL,
  `is_shelves` varchar(4) DEFAULT NULL,
  `yd_stocknumber` varchar(11) DEFAULT NULL,
  `dc_stocknumber` varchar(11) DEFAULT NULL,
  `sort` varchar(4) DEFAULT NULL,
  `send_integral` varchar(11) DEFAULT NULL,
  `zhucai_text` text,
  `peicai_text` text,
  `shicai_text` text,
  `notuse_text` text,
  `createtime` datetime DEFAULT NULL,
  `updatetime` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `category_id` int(100) NOT NULL DEFAULT '0',
  `dc_version` int(4) DEFAULT '0',
  `yd_version` int(4) DEFAULT '0',
  PRIMARY KEY (`lunch_id`,`category_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_lunch
-- ----------------------------
INSERT INTO `tb_lunch` VALUES ('001', '333', '121', '35', 'http://localhost/FileSave//lunch/3aaf4cdbcbf84c2f8a0e33f4f2eb3e6c.jpg', 'http://localhost/FileSave//lunch/76f7dc8e0d6f492296ca1401389990e3.jpg', 'http://localhost/FileSave//lunch/8ad20ce730f94a88826b16998c145d78.jpg', 'http://localhost/FileSave//lunch/4a8844f60da7401481866cb33c31b887.jpg', '1', null, '7', '0', '0', '222', '222', '2222', '222', '2017-08-19 14:23:06', '2017-08-20 14:55:23', '1', '0', '0');
INSERT INTO `tb_lunch` VALUES ('002', '212', '121', '35', 'http://localhost/FileSave//lunch/d3afc28fede24de18a553d5c115cf41f.jpg', 'http://localhost/FileSave//lunch/cc14589e9c444b039c6a9a1a4c5ec0a2.jpg', 'http://localhost/FileSave//lunch/972309a66d304093803979b231504aad.jpg', 'http://localhost/FileSave//lunch/9a0787a535974e9e968e1f0e40c3b09d.jpg', '1', null, '1', '0', '0', '2112', '323', '12', '412', '2017-08-19 14:57:10', '2017-08-20 14:55:24', '1', '0', '0');

-- ----------------------------
-- Table structure for `tb_order`
-- ----------------------------
DROP TABLE IF EXISTS `tb_order`;
CREATE TABLE `tb_order` (
  `order_id` varchar(100) NOT NULL,
  `looknumber` varchar(100) DEFAULT NULL,
  `allmoney` int(11) DEFAULT NULL,
  `discount_money` int(11) DEFAULT NULL,
  `wxmember_redpackage_id` varchar(100) DEFAULT NULL,
  `wxmember_tihuojuan_idstr` varchar(100) DEFAULT NULL,
  `actual_money` int(11) DEFAULT NULL,
  `use_integral` int(11) DEFAULT NULL,
  `use_wx` int(11) DEFAULT NULL,
  `pay_type` varchar(4) DEFAULT '1',
  `order_status` varchar(4) DEFAULT NULL,
  `send_integral` int(11) DEFAULT NULL,
  `serial_number` varchar(100) DEFAULT NULL,
  `order_type` varchar(4) DEFAULT NULL,
  `reserve_arrival_time` time DEFAULT NULL,
  `reserve_day` date DEFAULT NULL,
  `delivery_time` time DEFAULT NULL,
  `delivery_fee` int(11) DEFAULT NULL,
  `delivery_type` varchar(4) DEFAULT NULL,
  `delivery_status` varchar(4) DEFAULT '0',
  `delivery_operator_name` varchar(100) DEFAULT NULL,
  `createtime` datetime DEFAULT NULL,
  `jiedantime` datetime DEFAULT NULL,
  `overtime` datetime DEFAULT NULL,
  `wxmember_id` varchar(100) DEFAULT NULL,
  `wxmember_address_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_order
-- ----------------------------
INSERT INTO `tb_order` VALUES ('1', '1', '1', '1', '1', '1', '22', '1', '1', null, '99', '1', '1', '1', '00:00:00', '2017-08-01', '10:07:33', '1', '2', '0', null, '2017-08-01 10:07:30', null, '2017-08-19 18:06:31', '1', '1');
INSERT INTO `tb_order` VALUES ('22', '2', '2', '2', '2', '2', '2', '2', '2', null, '4', '2', '2', '2', '10:09:02', '2017-08-01', '10:09:08', '2', '1', '0', null, '2017-08-01 10:09:13', null, '2017-08-19 18:06:29', '1', '1');

-- ----------------------------
-- Table structure for `tb_order_lunch`
-- ----------------------------
DROP TABLE IF EXISTS `tb_order_lunch`;
CREATE TABLE `tb_order_lunch` (
  `order_lunch_id` int(11) NOT NULL AUTO_INCREMENT,
  `order_id` varchar(100) DEFAULT NULL,
  `lunch_id` varchar(100) DEFAULT NULL,
  `shop_number` int(11) DEFAULT NULL,
  PRIMARY KEY (`order_lunch_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_order_lunch
-- ----------------------------
INSERT INTO `tb_order_lunch` VALUES ('1', '1', '1', '1');
INSERT INTO `tb_order_lunch` VALUES ('2', '2', '1', '2');

-- ----------------------------
-- Table structure for `tb_receive_condition`
-- ----------------------------
DROP TABLE IF EXISTS `tb_receive_condition`;
CREATE TABLE `tb_receive_condition` (
  `receive_condition_id` int(11) NOT NULL AUTO_INCREMENT,
  `content` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`receive_condition_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_receive_condition
-- ----------------------------
INSERT INTO `tb_receive_condition` VALUES ('1', '消费一单');
INSERT INTO `tb_receive_condition` VALUES ('2', '消费金额超过100');
INSERT INTO `tb_receive_condition` VALUES ('3', '消费单数超过10单');

-- ----------------------------
-- Table structure for `tb_redpackage`
-- ----------------------------
DROP TABLE IF EXISTS `tb_redpackage`;
CREATE TABLE `tb_redpackage` (
  `redpackage_id` int(11) NOT NULL AUTO_INCREMENT,
  `money` int(11) DEFAULT NULL,
  `address_id` int(11) DEFAULT NULL,
  `receive_condition_idstr` varchar(100) DEFAULT NULL,
  `send_oprator_id` varchar(100) DEFAULT NULL,
  `createtime` datetime DEFAULT NULL,
  `updatetime` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`redpackage_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_redpackage
-- ----------------------------
INSERT INTO `tb_redpackage` VALUES ('1', '2', '10', '1,2,3,', '1', '2017-07-31 11:05:19', '2017-07-31 11:05:19');

-- ----------------------------
-- Table structure for `tb_scheduled_time`
-- ----------------------------
DROP TABLE IF EXISTS `tb_scheduled_time`;
CREATE TABLE `tb_scheduled_time` (
  `scheduled_time_id` int(11) NOT NULL AUTO_INCREMENT,
  `day` date DEFAULT NULL,
  `time_name` varchar(100) DEFAULT NULL,
  `sale_starttime` time DEFAULT NULL,
  `sale_endtime` time DEFAULT NULL,
  `yd_starttime` datetime DEFAULT NULL,
  `yd_endtime` datetime DEFAULT NULL,
  `category_idstr` varchar(100) DEFAULT NULL,
  `updatetime` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`scheduled_time_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_scheduled_time
-- ----------------------------
INSERT INTO `tb_scheduled_time` VALUES ('3', '2017-08-21', '午餐', '00:00:00', '00:00:00', '2017-08-20 00:00:00', '2017-08-20 16:34:38', '1,', '2017-08-20 00:26:27');

-- ----------------------------
-- Table structure for `tb_shopcart`
-- ----------------------------
DROP TABLE IF EXISTS `tb_shopcart`;
CREATE TABLE `tb_shopcart` (
  `shopcart_id` varchar(100) NOT NULL,
  `lunch_id` varchar(100) DEFAULT NULL,
  `shop_number` int(11) DEFAULT NULL,
  `wxmember_id` varchar(100) DEFAULT NULL,
  `createtime` datetime DEFAULT NULL,
  `updatetime` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`shopcart_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_shopcart
-- ----------------------------
INSERT INTO `tb_shopcart` VALUES ('4908720096', '002', '1', '20170820124232907421', '2017-08-20 00:43:19', '2017-08-20 00:43:19');
INSERT INTO `tb_shopcart` VALUES ('6560806481', '002', '1', '20170802115849437584', '2017-08-20 00:38:07', '2017-08-20 00:38:07');

-- ----------------------------
-- Table structure for `tb_w`
-- ----------------------------
DROP TABLE IF EXISTS `tb_w`;
CREATE TABLE `tb_w` (
  `W_ID` varchar(100) NOT NULL,
  `W` varchar(255) DEFAULT NULL COMMENT 'w',
  PRIMARY KEY (`W_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_w
-- ----------------------------
INSERT INTO `tb_w` VALUES ('', '1');

-- ----------------------------
-- Table structure for `tb_weekmeal_card`
-- ----------------------------
DROP TABLE IF EXISTS `tb_weekmeal_card`;
CREATE TABLE `tb_weekmeal_card` (
  `weekmeal_card_id` int(11) NOT NULL AUTO_INCREMENT,
  `sale_money` int(11) DEFAULT NULL,
  `twenty_five_number` int(11) DEFAULT NULL COMMENT '25元用餐劵个数',
  `twenty_eight_number` int(11) DEFAULT NULL,
  `thirty_five_number` int(11) DEFAULT NULL,
  `thirty_eight_number` int(11) DEFAULT NULL,
  `isservice` varchar(4) DEFAULT '0',
  `createtime` datetime DEFAULT NULL,
  `create_oprator_id` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`weekmeal_card_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_weekmeal_card
-- ----------------------------
INSERT INTO `tb_weekmeal_card` VALUES ('1', '2', '2', '1', '1', '1', '1', '2017-07-31 16:31:16', '1');

-- ----------------------------
-- Table structure for `tb_weekmeal_card_buy_wxmember`
-- ----------------------------
DROP TABLE IF EXISTS `tb_weekmeal_card_buy_wxmember`;
CREATE TABLE `tb_weekmeal_card_buy_wxmember` (
  `only_id` varchar(100) NOT NULL,
  `wxmember_id` varchar(100) DEFAULT NULL,
  `weekmeal_card_id` int(11) DEFAULT NULL,
  `pay_status` varchar(4) DEFAULT NULL,
  `serial_number` varchar(100) DEFAULT NULL,
  `createtime` datetime DEFAULT NULL,
  `overtime` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`only_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_weekmeal_card_buy_wxmember
-- ----------------------------
INSERT INTO `tb_weekmeal_card_buy_wxmember` VALUES ('1', '1', '1', '0', '1111', '2017-07-31 16:49:38', '2017-07-31 16:49:41');

-- ----------------------------
-- Table structure for `tb_wxmember`
-- ----------------------------
DROP TABLE IF EXISTS `tb_wxmember`;
CREATE TABLE `tb_wxmember` (
  `wxmember_id` varchar(100) NOT NULL,
  `showlook_id` varchar(100) DEFAULT NULL,
  `image_url` varchar(100) DEFAULT NULL,
  `name` varchar(100) DEFAULT NULL,
  `sex` varchar(4) DEFAULT NULL,
  `open_id` varchar(100) DEFAULT NULL,
  `phone` varchar(11) DEFAULT NULL,
  `province_name` varchar(100) DEFAULT NULL,
  `area_name` varchar(100) DEFAULT NULL,
  `city_name` varchar(100) DEFAULT NULL,
  `lng` varchar(100) DEFAULT NULL,
  `lat` varchar(100) DEFAULT NULL,
  `createtime` datetime DEFAULT NULL,
  `updatetime` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`wxmember_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_wxmember
-- ----------------------------
INSERT INTO `tb_wxmember` VALUES ('1', '2222', null, '2222', '1', '2123131', null, null, null, null, null, null, '2017-07-31 12:01:09', '2017-07-31 12:01:09');
INSERT INTO `tb_wxmember` VALUES ('20170820124232907421', '65862500', null, null, null, 'owD2DwsxdygwHXxNV75kjGT7Wvlw', null, null, null, null, null, null, '2017-08-20 00:42:44', '2017-08-20 00:42:44');

-- ----------------------------
-- Table structure for `tb_wxmember_address`
-- ----------------------------
DROP TABLE IF EXISTS `tb_wxmember_address`;
CREATE TABLE `tb_wxmember_address` (
  `wxmember_address_id` int(11) NOT NULL AUTO_INCREMENT,
  `contacts` varchar(100) DEFAULT NULL,
  `sex` varchar(4) DEFAULT NULL,
  `contacts_number` varchar(11) DEFAULT NULL,
  `corporate_name` varchar(100) DEFAULT NULL,
  `address_id` int(11) DEFAULT NULL,
  `floor_numbe` varchar(100) DEFAULT NULL,
  `wxmember_id` varchar(100) DEFAULT NULL,
  `createtime` datetime DEFAULT NULL,
  `updatetime` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`wxmember_address_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_wxmember_address
-- ----------------------------
INSERT INTO `tb_wxmember_address` VALUES ('1', '1', '1', '1', '1', '1', '1', '20170820124232907421', '2017-08-01 11:16:49', '2017-08-20 12:42:43');
INSERT INTO `tb_wxmember_address` VALUES ('2', '2', '2', '2', '2', '1', '2', '20170820124232907421', '2017-08-20 12:42:16', '2017-08-20 12:42:44');
INSERT INTO `tb_wxmember_address` VALUES ('3', '3', '3', '3', '3', '1', '3', '20170820124232907421', '2017-08-20 12:42:18', '2017-08-20 12:42:44');
INSERT INTO `tb_wxmember_address` VALUES ('4', '4', '4', '4', '4', '1', '4', '20170820124232907421', '2017-08-20 12:42:25', '2017-08-20 12:42:45');

-- ----------------------------
-- Table structure for `tb_wxmember_redpackage`
-- ----------------------------
DROP TABLE IF EXISTS `tb_wxmember_redpackage`;
CREATE TABLE `tb_wxmember_redpackage` (
  `wxmember_redpackage_id` int(11) NOT NULL,
  `wxmember_id` varchar(100) DEFAULT NULL,
  `money` varchar(11) DEFAULT NULL,
  `isuse` varchar(4) DEFAULT '0',
  `redpackage_id` int(11) DEFAULT NULL,
  `createtime` datetime DEFAULT NULL,
  `updatetime` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `isfrozen` varchar(4) DEFAULT '0',
  PRIMARY KEY (`wxmember_redpackage_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_wxmember_redpackage
-- ----------------------------
INSERT INTO `tb_wxmember_redpackage` VALUES ('1', '1', '1', '0', '1', '2017-08-19 17:57:13', '2017-08-19 17:58:32', '0');
INSERT INTO `tb_wxmember_redpackage` VALUES ('2', '20170820124232907421', '2', '0', '1', '2017-08-20 12:39:58', '2017-08-20 12:40:01', '0');
INSERT INTO `tb_wxmember_redpackage` VALUES ('3', '20170820124232907421', '3', '0', '1', '2017-08-20 12:40:23', '2017-08-20 12:40:25', '0');
INSERT INTO `tb_wxmember_redpackage` VALUES ('4', '20170820124232907421', '4', '0', '1', '2017-08-20 12:40:25', '2017-08-20 12:40:27', '0');
INSERT INTO `tb_wxmember_redpackage` VALUES ('5', '20170820124232907421', '5', '0', '1', '2017-08-20 12:40:28', '2017-08-20 12:40:30', '0');

-- ----------------------------
-- Table structure for `tb_wxmember_tihuojuan`
-- ----------------------------
DROP TABLE IF EXISTS `tb_wxmember_tihuojuan`;
CREATE TABLE `tb_wxmember_tihuojuan` (
  `wxmember_tihuojuan_id` int(11) NOT NULL,
  `wxmember_id` varchar(100) DEFAULT NULL,
  `money` int(11) DEFAULT NULL,
  `isuse` varchar(4) DEFAULT '0',
  `weekmeal_card_id` int(11) DEFAULT NULL,
  `createtime` datetime DEFAULT NULL,
  `updatetime` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `isfrozen` varchar(4) DEFAULT '0',
  PRIMARY KEY (`wxmember_tihuojuan_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_wxmember_tihuojuan
-- ----------------------------
INSERT INTO `tb_wxmember_tihuojuan` VALUES ('1', '1', '1', '0', '1', '2017-08-19 17:57:29', '2017-08-19 17:58:32', '0');
INSERT INTO `tb_wxmember_tihuojuan` VALUES ('2', '20170820124232907421', '25', '0', '1', '2017-08-20 12:41:15', '2017-08-20 12:41:17', '0');
INSERT INTO `tb_wxmember_tihuojuan` VALUES ('3', '20170820124232907421', '28', '0', '1', '2017-08-20 12:41:17', '2017-08-20 12:41:19', '0');
INSERT INTO `tb_wxmember_tihuojuan` VALUES ('4', '20170820124232907421', '35', '0', '1', '2017-08-20 12:41:19', '2017-08-20 12:41:21', '0');
INSERT INTO `tb_wxmember_tihuojuan` VALUES ('5', '20170820124232907421', '38', '0', '1', '2017-08-20 12:41:23', '2017-08-20 12:41:24', '0');

-- ----------------------------
-- Table structure for `tb_wxmember_wealth`
-- ----------------------------
DROP TABLE IF EXISTS `tb_wxmember_wealth`;
CREATE TABLE `tb_wxmember_wealth` (
  `wxmember_wealth_id` int(11) NOT NULL AUTO_INCREMENT,
  `wxmember_id` varchar(100) DEFAULT NULL,
  `now_integral` int(11) DEFAULT NULL,
  `before_integral` int(11) DEFAULT NULL,
  `createtime` datetime DEFAULT NULL,
  `updatetime` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`wxmember_wealth_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_wxmember_wealth
-- ----------------------------
INSERT INTO `tb_wxmember_wealth` VALUES ('1', '1', '22', '0', '2017-08-02 11:58:49', '2017-08-20 00:42:17');
INSERT INTO `tb_wxmember_wealth` VALUES ('2', '20170820124232907421', '0', '0', '2017-08-20 00:42:44', '2017-08-20 00:42:44');

-- ----------------------------
-- Table structure for `tb_wxmember_wealthhistory`
-- ----------------------------
DROP TABLE IF EXISTS `tb_wxmember_wealthhistory`;
CREATE TABLE `tb_wxmember_wealthhistory` (
  `wxmember_wealthhistory_id` varchar(100) NOT NULL,
  `wxmember_id` varchar(100) DEFAULT NULL,
  `money` int(11) DEFAULT NULL,
  `isincome` varchar(4) DEFAULT NULL,
  `order_id` varchar(100) DEFAULT NULL,
  `createtime` datetime DEFAULT NULL,
  `updatetime` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`wxmember_wealthhistory_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_wxmember_wealthhistory
-- ----------------------------
