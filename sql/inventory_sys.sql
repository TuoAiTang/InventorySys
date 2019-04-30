# Host: 127.0.0.1  (Version: 5.7.21-log)
# Date: 2019-04-29 23:39:36
# Generator: MySQL-Front 5.3  (Build 4.269)

/*!40101 SET NAMES utf8 */;

#
# Structure for table "alloc_request"
#

DROP TABLE IF EXISTS `alloc_request`;
CREATE TABLE `alloc_request` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `offline_name` varchar(20) DEFAULT NULL,
  `goods_name` varchar(20) DEFAULT NULL,
  `amount` int(11) DEFAULT NULL,
  `date` datetime DEFAULT NULL,
  `status` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;

#
# Data for table "alloc_request"
#

INSERT INTO `alloc_request` VALUES (1,'西直门','Apple',120,'2018-12-03 00:00:00.000000','已调拨，待收货'),(2,'西直门','Apple',120,'2018-12-03 00:00:00.000000','已调拨，待收货'),(3,'西直门','Apple',120,'2018-12-03 00:00:00.000000','已调拨，待收货'),(4,'西直门','Apple',120,'2018-12-03 00:00:00.000000','已调拨，待收货'),(5,'西直门','Apple',120,'2018-12-03 00:00:00.000000','已调拨，待收货'),(6,'西直门','Apple',120,'2018-12-03 00:00:00.000000','已调拨，待收货'),(7,'西直门','Apple',120,'2018-12-03 00:00:00.000000','申请中，等待补货'),(8,'西直门','Apple',120,'2018-12-03 00:00:00.000000','申请中，等待补货'),(9,'西直门','Apple',120,'2018-12-03 00:00:00.000000','申请中，等待补货'),(10,'东单','Orange',2,'2019-04-29 17:42:20.000000','加入发货队列'),(11,'东单','Orange',2,'2019-04-29 17:46:40.000000','加入发货队列'),(12,'东单','Orange',2,'2019-04-29 17:47:36.000000','已签收'),(13,'东单','Orange',1,'2019-04-29 17:48:21.000000','已调拨，待收货'),(14,'东单','Apple',5,'2019-04-29 18:56:49.000000','已签收');

#
# Structure for table "customer_order"
#

DROP TABLE IF EXISTS `customer_order`;
CREATE TABLE `customer_order` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `goods_name` varchar(20) DEFAULT NULL,
  `amount` int(11) DEFAULT NULL,
  `date` datetime DEFAULT NULL,
  `money` double DEFAULT NULL,
  `status` varchar(100) DEFAULT NULL,
  `user_name` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;

#
# Data for table "customer_order"
#

INSERT INTO `customer_order` VALUES (1,'Banana',15,'2019-04-26 19:45:01.000000',300,'已付款','123@qq.com'),(2,'Banana',19,'2019-04-26 20:00:09.000000',380,'已付款','123@qq.com'),(3,'Banana',3,'2019-04-26 20:02:25.000000',60,'已付款','123@qq.com'),(4,'Banana',5,'2019-04-26 20:17:46.000000',100,'已付款','123@qq.com'),(5,'Apple',123,'2019-04-27 13:33:34.000000',430.5,'已付款','123@qq.com'),(6,'Apple',145,'2019-04-27 13:38:50.000000',507.5,'已付款','123@qq.com'),(7,'Banana',999,'2019-04-27 14:05:08.000000',19980,'已付款','123@qq.com'),(8,'Banana',2,'2019-04-27 14:06:05.000000',40,'已付款','123@qq.com'),(9,'Apple',666,'2019-04-27 14:08:45.000000',2331,'已付款','123@qq.com'),(10,'Banana',30,'2019-04-27 16:16:43.000000',600,'已发货，待签收','123@qq.com'),(11,'Banana',100,'2019-04-27 16:18:32.000000',2000,'已发货，待签收','123@qq.com'),(12,'Banana',20,'2019-04-27 16:23:08.000000',400,'已发货，待签收','123@qq.com'),(13,'Orange',5,'2019-04-27 16:28:02.000000',60,'已发货，待签收','123@qq.com'),(14,'Orange',30,'2019-04-27 16:30:14.000000',360,'已发货，待签收','123@qq.com'),(15,'Banana',5,'2019-04-27 16:44:05.000000',100,'已发货，待签收','123@qq.com'),(16,'Banana',5,'2019-04-27 16:44:39.000000',100,'已发货，待签收','123@qq.com'),(17,'Apple',1,'2019-04-27 16:48:10.000000',2.5,'已发货，待签收','123@qq.com'),(18,'Apple',100,'2019-04-27 17:20:08.000000',250,'已发货，待签收','123@qq.com'),(19,'Apple',50,'2019-04-27 17:20:14.000000',125,'已付款，正在补货','123@qq.com'),(20,'Orange',32,'2019-04-27 17:20:31.000000',384,'已发货，待签收','123@qq.com'),(21,'Straberry',20,'2019-04-29 17:00:58.000000',60,'已付款，待发货','123@qq.com');

#
# Structure for table "goods"
#

DROP TABLE IF EXISTS `goods`;
CREATE TABLE `goods` (
  `name` varchar(30) DEFAULT NULL,
  `price` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Data for table "goods"
#

INSERT INTO `goods` VALUES ('Banana',20),('Orange',12),('Apple',2.5),('Straberry',3);

#
# Structure for table "inventory_store"
#

DROP TABLE IF EXISTS `inventory_store`;
CREATE TABLE `inventory_store` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `goods_name` varchar(20) DEFAULT NULL,
  `amount` int(11) DEFAULT NULL,
  `offline_name` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

#
# Data for table "inventory_store"
#

INSERT INTO `inventory_store` VALUES (1,'Banana',2000,'西直门'),(2,'Apple',500,'西直门'),(3,'Banana',1500,'五道口'),(4,'Apple',27,'东单'),(5,'Banana',1200,'东单'),(7,'Orange',1,'东单');

#
# Structure for table "inventory_supp"
#

DROP TABLE IF EXISTS `inventory_supp`;
CREATE TABLE `inventory_supp` (
  `goods_name` varchar(20) DEFAULT NULL,
  `amount` int(11) DEFAULT NULL,
  `price` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Data for table "inventory_supp"
#

INSERT INTO `inventory_supp` VALUES ('Apple',3000,2.5),('Banana',1500,3);

#
# Structure for table "inventory_warehouse"
#

DROP TABLE IF EXISTS `inventory_warehouse`;
CREATE TABLE `inventory_warehouse` (
  `goods_name` varchar(20) DEFAULT NULL,
  `amount` int(11) DEFAULT NULL,
  `warehouse_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Data for table "inventory_warehouse"
#

INSERT INTO `inventory_warehouse` VALUES ('Banana',1,2),('Apple',10,1),('Orange',3,3),('Straberry',500,1),('Watermelon',200,3),('Cherry',1,3);

#
# Structure for table "purchase_order"
#

DROP TABLE IF EXISTS `purchase_order`;
CREATE TABLE `purchase_order` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `goods_name` varchar(255) DEFAULT NULL,
  `amount` int(11) DEFAULT NULL,
  `date` datetime DEFAULT NULL,
  `money` double DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;

#
# Data for table "purchase_order"
#

INSERT INTO `purchase_order` VALUES (13,'Apple',100,'2019-12-08 00:00:00.000000',1000,'已签收'),(14,'Apple',200,'2019-12-03 00:00:00.000000',2000,'已签收'),(15,'Apple',1200,'2019-04-28 17:48:31.000000',3000,'已发货，待签收'),(16,'Apple',2000,'2019-04-28 17:51:43.000000',5000,'已发货，待签收'),(17,'Apple',200,'2019-04-28 20:15:16.000000',500,'已发货，待签收');

#
# Structure for table "running_tally"
#

DROP TABLE IF EXISTS `running_tally`;
CREATE TABLE `running_tally` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` int(11) DEFAULT NULL,
  `amount` double DEFAULT NULL,
  `order_id` int(11) DEFAULT NULL,
  `date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;

#
# Data for table "running_tally"
#

INSERT INTO `running_tally` VALUES (1,1,100,4,'2019-04-26 20:17:46.000000'),(2,1,430.5,5,'2019-04-27 13:33:34.000000'),(3,1,507.5,6,'2019-04-27 13:38:50.000000'),(4,1,19980,7,'2019-04-27 14:05:08.000000'),(5,1,40,8,'2019-04-27 14:06:05.000000'),(6,1,2331,9,'2019-04-27 14:08:45.000000'),(7,1,600,10,'2019-04-27 16:16:43.000000'),(8,1,2000,11,'2019-04-27 16:18:32.000000'),(9,1,400,12,'2019-04-27 16:23:08.000000'),(10,1,60,13,'2019-04-27 16:28:02.000000'),(11,1,360,14,'2019-04-27 16:30:14.000000'),(12,1,100,15,'2019-04-27 16:44:05.000000'),(13,1,100,16,'2019-04-27 16:44:39.000000'),(14,1,2.5,17,'2019-04-27 16:48:10.000000'),(15,1,250,18,'2019-04-27 17:20:08.000000'),(16,1,125,19,'2019-04-27 17:20:14.000000'),(17,1,384,20,'2019-04-27 17:20:31.000000'),(18,0,3000,15,'2019-04-28 17:48:31.000000'),(19,0,5000,16,'2019-04-28 17:51:43.000000'),(20,0,500,17,'2019-04-28 20:15:16.000000'),(21,1,60,21,'2019-04-29 17:00:58.000000');

#
# Structure for table "user"
#

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `name` varchar(20) NOT NULL,
  `password` varchar(20) DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Data for table "user"
#

INSERT INTO `user` VALUES ('1','123',4),('123','123',2),('1234','123',6),('12345@qq.com','123',5),('1234@qq.com','123',3),('123@qq.com','123',0),('12@qq.com','123',1),('??????','123',5),('è¥¿ç´é¨','123',5),('东单','123',5);
