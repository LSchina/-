/*
 Navicat Premium Data Transfer

 Source Server         : ds
 Source Server Type    : MySQL
 Source Server Version : 80028
 Source Host           : localhost:3306
 Source Schema         : clubs

 Target Server Type    : MySQL
 Target Server Version : 80028
 File Encoding         : 65001

 Date: 29/09/2024 12:22:47
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for activity
-- ----------------------------
DROP TABLE IF EXISTS `activity`;
CREATE TABLE `activity`  (
  `id` bigint NOT NULL,
  `image` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `person` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `num` int NULL DEFAULT 0,
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP,
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of activity
-- ----------------------------
INSERT INTO `activity` VALUES (1840243797255020546, 'http://localhost:9090/image/1f2d7c2a-a179-41f2-b5c5-668faa8c8c61.png', '软工互211、212同学', 0, '2024-09-29 12:14:46', '月圆美梦');
INSERT INTO `activity` VALUES (1840244673902305282, 'http://localhost:9090/image/c6e599f7-24ed-44cd-9b0e-d04aa0fced69.png', '全校同学', 0, '2024-09-29 12:18:15', '秋日情书');

-- ----------------------------
-- Table structure for activity_user
-- ----------------------------
DROP TABLE IF EXISTS `activity_user`;
CREATE TABLE `activity_user`  (
  `id` bigint NOT NULL,
  `user_id` bigint NULL DEFAULT NULL,
  `activity_id` bigint NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of activity_user
-- ----------------------------
INSERT INTO `activity_user` VALUES (1840089945541304322, 1, 1840080805628575745, '2024-09-29 10:57:56');
INSERT INTO `activity_user` VALUES (1840241061214052353, 1, 1840090646174621697, '2024-09-29 12:03:53');

-- ----------------------------
-- Table structure for carousel
-- ----------------------------
DROP TABLE IF EXISTS `carousel`;
CREATE TABLE `carousel`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `image` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `url` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NULL DEFAULT NULL,
  `type` int NULL DEFAULT NULL COMMENT '0:主页 1:登录',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of carousel
-- ----------------------------
INSERT INTO `carousel` VALUES (1, 'http://localhost:9090/image/2d9d38ba-096a-46b6-81ca-32b343148151.png', 'https://element-plus.org/zh-CN/component/menu.html', '2024-09-27 01:00:48', NULL, 1);
INSERT INTO `carousel` VALUES (2, 'http://localhost:9090/image/c4939adf-01d2-4578-bfc5-48d8f5c2fe0c.png', 'https://vant-ui.github.io/vant/#/zh-CN/icon', '2024-09-27 02:01:07', NULL, 1);
INSERT INTO `carousel` VALUES (3, 'http://localhost:9090/image/0b0eaec9-c2af-4be2-877c-356bc7325728.png', 'http://127.0.0.1:5173/#/frontLogin', '2024-09-27 02:01:51', NULL, 1);
INSERT INTO `carousel` VALUES (4, 'http://localhost:9090/image/60679e09-8f9a-4666-8ede-0b472b88cba8.png', 'https://vant-ui.github.io/vant/#/zh-CN/icon', '2024-09-27 02:03:04', NULL, 0);

-- ----------------------------
-- Table structure for community
-- ----------------------------
DROP TABLE IF EXISTS `community`;
CREATE TABLE `community`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NULL DEFAULT NULL,
  `number` int NULL DEFAULT 0,
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `avater` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of community
-- ----------------------------
INSERT INTO `community` VALUES (1, '宿舍管理委员会', '2024-06-18 06:52:47', NULL, 35, '旨在协助学校管理宿舍事务，维护宿舍秩序，促进宿舍文化建设。通常由一群有责任心、组织能力强的学生自愿担任委员会成员，并选出主席或者干事来负责整个委员会的工作', 'http://localhost:9090/image/9348c330-7a74-41cd-96aa-d19d9d9e207f.png');
INSERT INTO `community` VALUES (2, '社团管理委员会', '2024-06-18 06:53:14', NULL, 20, '通过社团管理委员会的建立和运作，可以更好地促进社团活动的发展，提高社团成员的积极性和参与度，推动学校或组织的文化建设和教育教学目标的实现。', 'http://localhost:9090/image/08b6578d-ee7a-4030-b901-6ace14d5caf7.png');
INSERT INTO `community` VALUES (3, 'IT社团', '2024-06-20 00:46:11', NULL, 35, '该社团的宗旨是促进会员们对计算机技术、网络技术、软件开发等方面的学习和交流。', 'http://localhost:9090/image/48249bc3-fdb7-47b2-aaab-ceec01bee336.png');
INSERT INTO `community` VALUES (4, '心理联合委员会', '2024-06-20 00:46:13', NULL, 22, '该委员会的目标是提倡心理健康，倡导心理学知识的普及和正确认知，促进校园心理健康教育，关注学生心理困扰和压力问题。', 'http://localhost:9090/image/2ff352e7-bca4-43b2-af23-5eb0d708c665.png');
INSERT INTO `community` VALUES (5, '音乐社', '2024-06-20 00:46:15', NULL, 48, '该社团致力于提倡健康的生活方式和促进会员们的健身活动。健身社定期组织健身训练课程、健身比赛、健身营和健康讲座等活动，帮助会员们建立健康的体魄和增强身体素质。', 'http://localhost:9090/image/f4d275bc-600e-4955-8882-c6f800102e12.png');
INSERT INTO `community` VALUES (7, '国旗护卫队', '2024-06-20 00:46:17', NULL, 17, '国旗护卫队成员通常会接受严格的训练和考核，学习升降国旗的规范程序和礼仪要求，并具备优秀的纪律、团队合作和责任意识。', 'http://localhost:9090/image/9eff6b89-ac9a-45ba-b570-1ee617513e53.png');
INSERT INTO `community` VALUES (8, '摄影社', '2024-06-20 00:46:19', NULL, 50, '该社团致力于提倡摄影艺术和技术交流，定期举办摄影展览和讲座活动。', 'http://localhost:9090/image/314b5ffa-93be-4313-bd5b-e0a90dc3c8d1.png');
INSERT INTO `community` VALUES (10, '学生会', '2024-09-26 01:02:24', NULL, 25, '我们是学生的声音，是校园的桥梁。团结、奉献、创新是我们的信念。我们致力于倾听每一位同学的心声，推动校园的每一项活动，创造一个充满活力与温暖的学习环境。', 'http://localhost:9090/image/1065e8c6-9613-46e3-91e0-d4c1cc46a0e6.png');

-- ----------------------------
-- Table structure for menu
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu`  (
  `id` bigint NOT NULL,
  `parent_id` int NULL DEFAULT NULL,
  `perms` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of menu
-- ----------------------------
INSERT INTO `menu` VALUES (1, 0, 'stystem:user:list', '2024-06-18 06:56:34', NULL, '用户管理');
INSERT INTO `menu` VALUES (2, 0, 'stystem:community:list', '2024-06-18 06:57:51', NULL, '社团管理');
INSERT INTO `menu` VALUES (3, 0, 'stystem:message:list', '2024-06-18 06:59:55', NULL, '消息管理');
INSERT INTO `menu` VALUES (4, 2, 'stystem:community:query', '2024-06-18 07:00:05', NULL, '查找社团');
INSERT INTO `menu` VALUES (5, 3, 'stystem:message:add', '2024-06-18 07:05:45', NULL, '发布消息');
INSERT INTO `menu` VALUES (6, 1, 'stystem:user:delete', '2024-06-18 07:07:15', NULL, '用户删除');

-- ----------------------------
-- Table structure for message
-- ----------------------------
DROP TABLE IF EXISTS `message`;
CREATE TABLE `message`  (
  `id` bigint NOT NULL,
  `community_id` int NULL DEFAULT NULL,
  `user_id` int NULL DEFAULT NULL,
  `comment` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP,
  `type` tinyint(1) NULL DEFAULT NULL,
  `image` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of message
-- ----------------------------
INSERT INTO `message` VALUES (12, 1, NULL, '宿管会招新啦，你的选择让你的大学四年充满希望', '2024-06-19 22:02:02', 2, 'http://localhost:9090/image/9348c330-7a74-41cd-96aa-d19d9d9e207f.png', '宿舍管理委员会的通知');
INSERT INTO `message` VALUES (13, 2, NULL, '社管会要添加新力量啦，来这里发挥你们的力量吧', '2024-06-19 22:02:08', 2, 'http://localhost:9090/image/9eff6b89-ac9a-45ba-b570-1ee617513e53.png', '社团管理委员会的通知');
INSERT INTO `message` VALUES (1807488581913145345, 1, NULL, '机不可失，失不再来，宿管会进行崭新，欢迎来到宿管会历练下自己', '2024-07-01 02:57:14', 1, 'http://localhost:9090/image/9348c330-7a74-41cd-96aa-d19d9d9e207f.png', '宿管会招新啦');
INSERT INTO `message` VALUES (1807654127329734658, NULL, 1, '每一天都在，徘徊独胆中坚强', '2024-07-01 13:55:03', 0, 'http://localhost:9090/image/70ec1c8c-68f1-4ea8-947b-59c4e6e2fd74.jpg', '开始学习吧，家人们');
INSERT INTO `message` VALUES (1839242545826140161, NULL, 2, 'hhhhhhhh', '2024-09-26 17:56:09', 0, 'http://localhost:9090/image/64a41f9d-6ec2-4d8a-a5ff-207a92a5fcd1.jpg', 'lishuo\r\n');
INSERT INTO `message` VALUES (1839585803622223873, NULL, 3, '1234613', '2024-09-27 16:40:08', 0, 'http://localhost:9090/image/d693fe34-a7aa-4894-aa25-1eb2216981b9.jpg', '1234613');
INSERT INTO `message` VALUES (1839586043125370881, NULL, 4, '111111', '2024-09-27 16:41:05', 0, 'http://localhost:9090/image/5c41b9a5-747a-4a9e-aa7a-586f2b7b0d4d.png', '123465');
INSERT INTO `message` VALUES (1839932888859017218, NULL, 5, '快了个屁，什么都不会', '2024-09-28 15:39:19', 0, 'http://localhost:9090/image/f39adc39-e68c-4fa9-8fb6-e936886809db.jpg', '今日份快乐');

-- ----------------------------
-- Table structure for message_record
-- ----------------------------
DROP TABLE IF EXISTS `message_record`;
CREATE TABLE `message_record`  (
  `id` bigint NOT NULL COMMENT '业务id',
  `message_id` bigint NULL DEFAULT NULL COMMENT '消息id',
  `parent_id` bigint NULL DEFAULT NULL COMMENT '父id，回答消息没有父id，对回答进行评论有父id',
  `user_id` bigint NULL DEFAULT NULL,
  `comment` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP,
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of message_record
-- ----------------------------
INSERT INTO `message_record` VALUES (1839921070027616257, 1839586043125370881, 0, 1, '李硕真的很帅', '2024-09-28 14:52:21', 'lishuo');
INSERT INTO `message_record` VALUES (1839921939091599361, 1839586043125370881, 0, 1, '12346132', '2024-09-28 14:55:49', 'lishuo');
INSERT INTO `message_record` VALUES (1839931981933051905, 1839586043125370881, 0, 1, '李硕真的很帅，马丹丹真的很漂亮', '2024-09-28 15:35:43', 'lishuo');
INSERT INTO `message_record` VALUES (1839932192403226626, 1839586043125370881, 0, 1, '老生首贷：学生证复印件，《国家开发银行高校助学贷款申请表》，经济困难认定表\n高校续贷：《国家开发银行高校助学贷款申请表》。', '2024-09-28 15:36:33', 'lishuo');
INSERT INTO `message_record` VALUES (1839932259906355202, 1839585803622223873, 0, 1, '海南岛哈哈', '2024-09-27 15:36:49', 'lishuo');
INSERT INTO `message_record` VALUES (1839946624017629185, 1839242545826140161, 0, 1, '正常', '2024-09-27 16:33:54', 'lishuo');
INSERT INTO `message_record` VALUES (1840022647308615681, 1839932888859017218, 0, 1, '那也得快乐啊', '2024-09-26 21:35:59', 'lishuo');
INSERT INTO `message_record` VALUES (1840022814673928194, 1839932888859017218, 0, 2, '为什么不快乐呢？', '2024-09-26 21:36:39', 'mdd');
INSERT INTO `message_record` VALUES (1840023032140201985, 1839586043125370881, 0, 2, '李硕确实很傻逼！', '2024-09-26 21:37:31', 'mdd');
INSERT INTO `message_record` VALUES (1840023076364943362, 1839242545826140161, 0, 2, '哈哈哈哈哈哈', '2024-09-25 21:37:42', 'mdd');
INSERT INTO `message_record` VALUES (1840023141615730689, 1839242545826140161, 0, 2, '笑死了，还有一个评论正常的', '2024-09-24 21:37:57', 'mdd');
INSERT INTO `message_record` VALUES (1840023213409632257, 1807654127329734658, 0, 2, '还是你坚强', '2024-09-24 21:38:14', 'mdd');
INSERT INTO `message_record` VALUES (1840023340358631426, 1839585803622223873, 0, 2, '海南岛个屁，假的，哥们', '2024-09-24 22:10:28', 'mdd');
INSERT INTO `message_record` VALUES (1840023340656427009, 1839585803622223873, 0, 2, '又空了，笑死了', '2024-09-24 21:38:45', 'mdd');
INSERT INTO `message_record` VALUES (1840023356724805633, 1839585803622223873, 0, 2, '你好啊，小兄dei', '2024-09-23 21:38:48', 'mdd');
INSERT INTO `message_record` VALUES (1840023978945609729, 1839585803622223873, 0, 2, '66666666', '2024-09-23 21:41:17', 'mdd');
INSERT INTO `message_record` VALUES (1840024403853807618, 1839932888859017218, 0, 1803013908697571330, '这海报不错啊，兄dei', '2024-09-21 21:42:58', '123');
INSERT INTO `message_record` VALUES (1840024550939660289, 1839586043125370881, 0, 1803013908697571330, '你们都是谁啊，我不认识那你们啊，我以为就我自己呢', '2024-09-21 21:43:33', '123');
INSERT INTO `message_record` VALUES (1840024613006970881, 1839242545826140161, 0, 1803013908697571330, '笑死了，哈哈哈哈哈', '2024-09-21 21:43:48', '123');
INSERT INTO `message_record` VALUES (1840027890117349378, 1839932888859017218, 0, 4, '哥们666啊', '2024-09-21 21:56:49', 'user1');
INSERT INTO `message_record` VALUES (1840224811805093890, 1839932888859017218, 0, 1, '别急，人生无常，哈哈哈哈', '2024-09-29 10:59:19', 'lishuo');

-- ----------------------------
-- Table structure for record
-- ----------------------------
DROP TABLE IF EXISTS `record`;
CREATE TABLE `record`  (
  `id` bigint NOT NULL,
  `user_id` bigint NULL DEFAULT NULL,
  `community_id` int NULL DEFAULT NULL,
  `message_id` bigint NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP,
  `status` int NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of record
-- ----------------------------
INSERT INTO `record` VALUES (1807621661248196610, 1803013908697571330, 1, 1807488581913145345, '2024-07-01 12:18:40', 2);
INSERT INTO `record` VALUES (1807653366080335873, 1803013908697571330, 1, 1807488581913145345, '2024-07-01 13:52:01', 2);
INSERT INTO `record` VALUES (1807668417994551298, 1803013908697571330, 1, 1807488581913145345, '2024-07-01 14:51:50', 2);
INSERT INTO `record` VALUES (1807818526111592450, 1803013908697571330, 1, 1807488581913145345, '2024-07-02 00:48:18', 2);
INSERT INTO `record` VALUES (1808394604416884737, 1803013908697571330, 1, 1807488581913145345, '2024-07-03 14:57:26', 2);
INSERT INTO `record` VALUES (1808766545598373889, 1803013908697571330, 1, 1807488581913145345, '2024-07-04 15:35:24', 2);
INSERT INTO `record` VALUES (1840028841519714306, 3, 1, 1807488581913145345, '2024-09-28 22:00:36', 2);
INSERT INTO `record` VALUES (1840239173970190337, 1, 1, 1807488581913145345, '2024-09-29 11:56:23', 2);
INSERT INTO `record` VALUES (1840240430197792770, 2, 1, 1807488581913145345, '2024-09-29 12:01:23', 2);
INSERT INTO `record` VALUES (1840240918846791681, 3, 1, 1807488581913145345, '2024-09-29 12:03:19', 2);

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `id` bigint NOT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NULL DEFAULT NULL,
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES (1, '超级管理员', 'admin', '2024-06-18 06:21:38', NULL, NULL);
INSERT INTO `role` VALUES (2, '社团负责人', 'common', '2024-06-18 06:21:41', NULL, NULL);
INSERT INTO `role` VALUES (3, '普通用户', 'user', '2024-06-18 06:21:43', NULL, NULL);

-- ----------------------------
-- Table structure for role_menu
-- ----------------------------
DROP TABLE IF EXISTS `role_menu`;
CREATE TABLE `role_menu`  (
  `id` bigint NOT NULL,
  `role_id` int NULL DEFAULT NULL,
  `menu_id` int NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role_menu
-- ----------------------------
INSERT INTO `role_menu` VALUES (1, 1, 1);
INSERT INTO `role_menu` VALUES (2, 1, 2);
INSERT INTO `role_menu` VALUES (3, 1, 3);
INSERT INTO `role_menu` VALUES (4, 2, 2);
INSERT INTO `role_menu` VALUES (5, 2, 6);
INSERT INTO `role_menu` VALUES (6, 3, 4);
INSERT INTO `role_menu` VALUES (7, 3, 5);
INSERT INTO `role_menu` VALUES (8, 1, 5);
INSERT INTO `role_menu` VALUES (9, 1, 6);
INSERT INTO `role_menu` VALUES (10, 1, 4);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` bigint NOT NULL,
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `avater` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `student_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `class_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `login_date` datetime NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP,
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `community_id` bigint NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'admin', '$2a$10$ZZUKuPjCyvp/1l1T67VhseEhWC1rXmnHMcDc48L.kCYR1RpfXsWLy', 'http://localhost:9090/image/b24467a2-d945-4079-97dc-63ac0bbbf44c.png', '202120164114', 'RB软工互211', '15139471712', '2024-09-29 12:03:26', '2024-09-29 11:34:18', NULL, 1);
INSERT INTO `user` VALUES (2, 'niuniu', '$2a$10$ZZUKuPjCyvp/1l1T67VhseEhWC1rXmnHMcDc48L.kCYR1RpfXsWLy', 'http://localhost:9090/image/b24467a2-d945-4079-97dc-63ac0bbbf44c.png', '202120164114', 'RB软工互211', '15139471712', '2024-09-29 12:01:38', '2024-06-18 06:35:51', NULL, 1);
INSERT INTO `user` VALUES (3, 'erniu', '$2a$10$ZZUKuPjCyvp/1l1T67VhseEhWC1rXmnHMcDc48L.kCYR1RpfXsWLy', 'https://img.picui.cn/free/2024/06/23/667815d60694e.png', '202119164208', 'RB软工互212', '15515935057', '2024-09-29 12:03:14', '2024-06-18 06:52:30', NULL, NULL);
INSERT INTO `user` VALUES (4, 'user1', '$2a$10$ZZUKuPjCyvp/1l1T67VhseEhWC1rXmnHMcDc48L.kCYR1RpfXsWLy', 'http://localhost:9090/image/2465a74a-e753-49f7-a2e5-7d1e97f72cc8.png', '202120164116', 'RB软工互211', '15699662354', '2024-09-28 21:56:32', '2024-09-28 21:55:12', NULL, NULL);
INSERT INTO `user` VALUES (5, 'user2', '$2a$10$ZZUKuPjCyvp/1l1T67VhseEhWC1rXmnHMcDc48L.kCYR1RpfXsWLy', 'http://localhost:9090/image/c817cde1-2132-479d-9297-684ce4176f65.png', '202120164117', 'RB软工互211', '15699662354', '2024-09-28 21:57:03', '2024-09-28 21:55:12', NULL, NULL);
INSERT INTO `user` VALUES (6, 'user3', '$2a$10$ZZUKuPjCyvp/1l1T67VhseEhWC1rXmnHMcDc48L.kCYR1RpfXsWLy', 'http://localhost:9090/image/b207286e-bd8f-41dc-b6d2-82c7a14a3add.png', '202120164118', 'RB软工互211', '15699662354', '2024-09-28 21:57:16', '2024-09-28 21:55:12', NULL, NULL);
INSERT INTO `user` VALUES (7, 'user4', '$2a$10$ZZUKuPjCyvp/1l1T67VhseEhWC1rXmnHMcDc48L.kCYR1RpfXsWLy', 'http://localhost:9090/image/51918319-9943-48ff-89c2-ef5115bb0498.png', '202120164119', 'RB软工互211', '15699662354', '2024-09-28 21:57:27', '2024-09-28 21:55:12', NULL, NULL);
INSERT INTO `user` VALUES (8, 'user5', '$2a$10$ZZUKuPjCyvp/1l1T67VhseEhWC1rXmnHMcDc48L.kCYR1RpfXsWLy', 'http://localhost:9090/image/f6f29ed7-f532-4d3f-ad14-43559d89f34f.png', '202120164110', 'RB软工互211', '15699662354', '2024-09-28 21:57:37', '2024-09-28 21:55:12', NULL, NULL);
INSERT INTO `user` VALUES (9, 'user6', '$2a$10$ZZUKuPjCyvp/1l1T67VhseEhWC1rXmnHMcDc48L.kCYR1RpfXsWLy', 'http://localhost:9090/image/5ce21136-b25e-41e5-927d-147448467edf.png', '202120164101', 'RB软工互211', '15699662354', '2024-09-28 21:57:48', '2024-09-28 21:55:12', NULL, NULL);
INSERT INTO `user` VALUES (10, 'user7', '$2a$10$ZZUKuPjCyvp/1l1T67VhseEhWC1rXmnHMcDc48L.kCYR1RpfXsWLy', 'http://localhost:9090/image/ec3fc524-a752-44c4-aac7-88e61d3b498b.jpg', '202120164102', 'RB软工互211', '15699662354', '2024-09-28 21:58:04', '2024-09-28 21:55:12', NULL, NULL);
INSERT INTO `user` VALUES (11, 'user8', '$2a$10$ZZUKuPjCyvp/1l1T67VhseEhWC1rXmnHMcDc48L.kCYR1RpfXsWLy', 'http://localhost:9090/image/3d5f6607-0c32-45ec-9d96-b016c8edc0d1.jpg', '202120164103', 'RB软工互211', '15699662354', '2024-09-28 21:58:21', '2024-09-28 21:55:12', NULL, NULL);
INSERT INTO `user` VALUES (12, 'user9', '$2a$10$ZZUKuPjCyvp/1l1T67VhseEhWC1rXmnHMcDc48L.kCYR1RpfXsWLy', 'http://localhost:9090/image/f4b777b2-dd8f-4f40-bddb-8af50adb1a46.png', '202120164104', 'RB软工互211', '15699662354', '2024-09-28 21:58:36', '2024-09-28 21:55:12', NULL, NULL);
INSERT INTO `user` VALUES (13, 'user10', '$2a$10$ZZUKuPjCyvp/1l1T67VhseEhWC1rXmnHMcDc48L.kCYR1RpfXsWLy', 'http://localhost:9090/image/19d86fe3-1872-4723-a044-2736607cac19.png', '202120164105', 'RB软工互211', '15699662354', '2024-09-28 21:58:54', '2024-09-28 21:55:12', NULL, NULL);
INSERT INTO `user` VALUES (1803013908697571330, '123', '$2a$10$vJGSXcubKdtShzyYx5P6p.OwytQdYjQd0vuKDmdWgB9Zmg7bHL6Ty', 'https://fastly.jsdelivr.net/npm/@vant/assets/sand.jpeg', '123456', 'RB软工互212', '123456', '2024-09-28 21:42:36', '2024-06-18 18:36:28', NULL, 1);

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role`  (
  `id` bigint NOT NULL,
  `user_id` bigint NULL DEFAULT NULL,
  `role_id` int NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role` VALUES (1, 1, 1);
INSERT INTO `user_role` VALUES (2, 2, 1);
INSERT INTO `user_role` VALUES (3, 1803013908697571330, 3);
INSERT INTO `user_role` VALUES (4, 3, 3);

SET FOREIGN_KEY_CHECKS = 1;
