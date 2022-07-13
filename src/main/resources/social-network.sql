/*
SQLyog Community v13.1.6 (64 bit)
MySQL - 8.0.18 : Database - social-network
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`social-network` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `social-network`;

/*Table structure for table `comment` */

DROP TABLE IF EXISTS `comment`;

CREATE TABLE `comment` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_date` datetime(6) DEFAULT NULL,
  `text` varchar(255) DEFAULT NULL,
  `post_id` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK8kcum44fvpupyw6f5baccx25c` (`user_id`),
  KEY `FKs1slvnkuemjsq2kj4h3vhx7i1` (`post_id`),
  CONSTRAINT `FK8kcum44fvpupyw6f5baccx25c` FOREIGN KEY (`user_id`) REFERENCES `myuser` (`user_id`) ON DELETE CASCADE,
  CONSTRAINT `FKs1slvnkuemjsq2kj4h3vhx7i1` FOREIGN KEY (`post_id`) REFERENCES `post` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `comment` */

insert  into `comment`(`id`,`created_date`,`text`,`post_id`,`user_id`) values 
(1,'2022-06-22 16:18:59.000000','Vrh!',3,1),
(2,'2022-06-22 21:48:38.874418','Dobra objava!',3,1),
(4,'2022-06-22 21:49:34.456836','Solidno.',3,1),
(6,'2022-06-26 00:18:04.068134','Legendoooo!!!',3,5),
(7,'2022-06-26 00:18:09.519630','Legendoooo!!!',3,5),
(8,'2022-06-26 00:18:23.626675','dasdas',3,5),
(9,'2022-06-26 00:28:09.029570','bravo batoo',3,5),
(11,'2022-06-26 00:29:27.856461','Што је бре ово брате мили',43,5),
(12,'2022-06-26 14:40:44.114108','cao legendo',3,5);

/*Table structure for table `following` */

DROP TABLE IF EXISTS `following`;

CREATE TABLE `following` (
  `following_user_id` bigint(20) NOT NULL,
  `followed_user_id` bigint(20) NOT NULL,
  `followed_at` datetime DEFAULT NULL,
  PRIMARY KEY (`followed_user_id`,`following_user_id`),
  UNIQUE KEY `Unique_pracenje` (`following_user_id`,`followed_user_id`),
  CONSTRAINT `following_ibfk_2` FOREIGN KEY (`followed_user_id`) REFERENCES `myuser` (`user_id`) ON DELETE CASCADE,
  CONSTRAINT `following_ibfk_3` FOREIGN KEY (`following_user_id`) REFERENCES `myuser` (`user_id`) ON DELETE CASCADE,
  CONSTRAINT `c1` CHECK ((`following_user_id` <> `followed_user_id`))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `following` */

insert  into `following`(`following_user_id`,`followed_user_id`,`followed_at`) values 
(5,1,'2022-06-27 00:15:49'),
(6,1,'2022-07-09 13:11:26'),
(11,1,'2022-07-03 13:18:37'),
(1,5,'2022-06-25 23:59:40'),
(6,5,'2022-06-19 01:57:52'),
(11,5,'2022-07-03 13:18:36'),
(1,6,'2022-07-13 20:51:10'),
(1,7,'2022-07-13 18:55:03'),
(5,7,'2022-07-09 14:30:19'),
(1,11,'2022-07-13 17:02:17'),
(1,23,'2022-07-13 17:02:30');

/*Table structure for table `message` */

DROP TABLE IF EXISTS `message`;

CREATE TABLE `message` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `content` varchar(255) DEFAULT NULL,
  `from_user_id` bigint(20) NOT NULL,
  `to_user_id` bigint(20) NOT NULL,
  `sent_at` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKtgk95qvnhwlgn2hpeujq88bro` (`from_user_id`),
  KEY `FKtr1mugve3laxicatc86n651ec` (`to_user_id`),
  CONSTRAINT `FKtgk95qvnhwlgn2hpeujq88bro` FOREIGN KEY (`from_user_id`) REFERENCES `myuser` (`user_id`),
  CONSTRAINT `FKtr1mugve3laxicatc86n651ec` FOREIGN KEY (`to_user_id`) REFERENCES `myuser` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=75 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `message` */

/*Table structure for table `myuser` */

DROP TABLE IF EXISTS `myuser`;

CREATE TABLE `myuser` (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `created` datetime DEFAULT NULL,
  `bio` varchar(255) DEFAULT NULL,
  `is_enabled` tinyint(1) DEFAULT NULL,
  `aa` binary(1) DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `Unique_username` (`username`),
  UNIQUE KEY `Unique_email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `myuser` */

insert  into `myuser`(`user_id`,`username`,`email`,`password`,`created`,`bio`,`is_enabled`,`aa`) values 
(1,'uros99','urosvesicpo@gmail.com','$2a$10$ftZr1vqPUKBxTkg1ubHoZecVOvYjxq3bWvYh.v5r3LesQZqvlBTJ6','2022-06-17 19:07:12','Uros Vesic-FON',1,NULL),
(5,'mika','mikamikic@gmail.com','$2a$10$JmV2AommW1r7joTNQusKf.D2o/UB4ZbFiAuXtRWNSzHLP.DwFSdqO','2022-06-17 21:58:41','Mika Mikic-FON',1,NULL),
(6,'pera','pera@gmail.com','$2a$10$2QI4SlxQQccMuz.01ZoZ0er9f51lMn4HdBrV.wMkFNjdLlpQ7JUk6','2022-06-17 22:03:39',NULL,1,NULL),
(7,'aaa','aa@aaa.com','$2a$10$qeXyMnG0aub3Lh8NkkT85u9/4g944xDxWYUAOJD3Mc34MB8JjUNgS','2022-06-19 00:37:46','Sam svoj gazda!',1,NULL),
(8,'novi','noviuser@gmail.com','$2a$10$iXp1DEdCAyO296TsrvDLNuqpglzai/kXdOyYPx2tqrillVtCi2zLO','2022-06-19 00:38:44',NULL,1,NULL),
(10,'caos','novsam@gmai.com','$2a$10$o2pu9FypXItJ5uBW6s5Ij.T1GcpE7moLXrPsdHVDdQBloct4F4Uxa','2022-06-19 20:07:35',NULL,1,NULL),
(11,'zika','zika@zika.com','$2a$10$nT/RxI2aDZg5KBId/TekdeyLR3WJGtrEO68YeFRsVR/lJdrgKnUVW','2022-06-19 20:09:02',NULL,1,NULL),
(23,'uvesic','uvesic999@gmail.com','$2a$10$GQvz5luyL.jyS5n9lTKmKOMT24T8GtclUzJLABsoP/jVTLIo.GQpm','2022-07-11 23:39:29',NULL,1,NULL);

/*Table structure for table `post` */

DROP TABLE IF EXISTS `post`;

CREATE TABLE `post` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `content` longtext,
  `created_date` datetime(6) DEFAULT NULL,
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `topic_id` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `deleteb_by_admin` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `topic_id` (`topic_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `post_ibfk_1` FOREIGN KEY (`topic_id`) REFERENCES `topic` (`id`),
  CONSTRAINT `post_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `myuser` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=51 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `post` */

insert  into `post`(`id`,`content`,`created_date`,`title`,`topic_id`,`user_id`,`deleteb_by_admin`) values 
(3,'<p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Cras erat sem, euismod vitae nisl sit amet, congue condimentum nulla. Morbi risus quam, mattis a massa et, tristique lobortis elit. Suspendisse vulputate, magna in consequat rhoncus, lorem urna imperdiet eros, non mattis augue diam nec lectus. Vivamus lacinia turpis lacus, vitae aliquam metus tempor faucibus. Nulla facilisis lectus non iaculis feugiat. Nam vitae ex vitae urna posuere varius a sit amet mauris. Duis euismod ac neque in tincidunt.</p>','2022-06-20 23:42:59.000000','Lorem1',1,1,NULL),
(4,'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Cras erat sem, euismod vitae nisl sit amet, congue condimentum nulla. Morbi risus quam, mattis a massa et, tristique lobortis elit. Suspendisse vulputate, magna in consequat rhoncus, lorem urna imperdiet eros, non mattis augue diam nec lectus. Vivamus lacinia turpis lacus, vitae aliquam metus tempor faucibus. Nulla facilisis lectus non iaculis feugiat. Nam vitae ex vitae urna posuere varius a sit amet mauris. Duis euismod ac neque in tincidunt.\r\n','2022-06-20 23:43:17.000000','Lorem2',1,1,NULL),
(43,'<p>dasdas</p>','2022-06-24 21:10:44.606375','dasd',2,1,NULL),
(44,'<p>dasdasdas</p>','2022-06-24 21:10:50.914541','dasdasd',2,1,NULL),
(45,'<p>dasdasdas</p>','2022-06-24 21:10:59.458774','dasdas',1,1,'2022-07-11 18:06:50.410000'),
(47,'<p>fsdfsdfs</p>','2022-06-24 22:15:18.054806','fsdf',1,1,NULL),
(48,'<p>Cao ja sam Mika!!Pozdrav za sve pratioce!!</p>','2022-06-26 15:35:29.228594','Pozdrav ekipa!!!',2,5,'2022-07-11 19:22:01.655000'),
(49,'<p>SRBIJA DO TOKIJA</p>','2022-07-03 13:18:29.393207','CAO JA SAM ZIKA',2,11,NULL),
(50,'<p>Moj post</p>','2022-07-13 20:51:34.985973','Perin post',2,6,NULL);

/*Table structure for table `post_report` */

DROP TABLE IF EXISTS `post_report`;

CREATE TABLE `post_report` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL,
  `post_id` bigint(20) DEFAULT NULL,
  `report_type` int(11) DEFAULT NULL,
  `report_status` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `user-post` (`user_id`,`post_id`),
  KEY `FKeyehd7v09u9oxijrfvw1ufof` (`post_id`),
  CONSTRAINT `FKeyehd7v09u9oxijrfvw1ufof` FOREIGN KEY (`post_id`) REFERENCES `post` (`id`),
  CONSTRAINT `FKqf8hfx9o3m0ym1aifmwjmycak` FOREIGN KEY (`user_id`) REFERENCES `myuser` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `post_report` */

insert  into `post_report`(`id`,`user_id`,`post_id`,`report_type`,`report_status`) values 
(7,1,48,2,2),
(8,11,3,2,1),
(9,11,4,2,1),
(10,5,43,2,0),
(16,5,45,2,2);

/*Table structure for table `reaction` */

DROP TABLE IF EXISTS `reaction`;

CREATE TABLE `reaction` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `reaction_type` int(11) DEFAULT NULL,
  `post_id` bigint(20) NOT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `post_id` (`post_id`),
  KEY `reaction_ibfk_6` (`user_id`),
  CONSTRAINT `reaction_ibfk_5` FOREIGN KEY (`post_id`) REFERENCES `post` (`id`),
  CONSTRAINT `reaction_ibfk_6` FOREIGN KEY (`user_id`) REFERENCES `myuser` (`user_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=136 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `reaction` */

insert  into `reaction`(`id`,`reaction_type`,`post_id`,`user_id`) values 
(105,0,48,1),
(106,0,3,5),
(107,0,4,5),
(108,1,43,5),
(109,0,44,5),
(110,1,45,5),
(111,0,47,5),
(112,0,3,11),
(113,0,4,11),
(114,0,43,11),
(115,1,44,11),
(116,1,45,11),
(117,1,47,11),
(118,1,48,11),
(119,0,48,6),
(120,0,3,6),
(121,1,4,6),
(122,0,43,6),
(135,0,49,1);

/*Table structure for table `role` */

DROP TABLE IF EXISTS `role`;

CREATE TABLE `role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `details` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `role` */

insert  into `role`(`id`,`name`,`details`) values 
(1,'USER','Social network registrated user'),
(2,'ADMIN','Administrator of social network');

/*Table structure for table `topic` */

DROP TABLE IF EXISTS `topic`;

CREATE TABLE `topic` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_date` datetime(6) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `user_user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKevuv7p02842lhi77hguyhcgos` (`user_user_id`),
  CONSTRAINT `topic_ibfk_1` FOREIGN KEY (`user_user_id`) REFERENCES `myuser` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `topic` */

insert  into `topic`(`id`,`created_date`,`description`,`name`,`user_user_id`) values 
(1,'2022-06-20 23:42:42.000000','Lorem desc','Lorem',1),
(2,'2022-06-21 02:13:31.000000','Topic2','Topic2',1),
(3,'2022-06-21 16:27:23.154102','java','java',1);

/*Table structure for table `topic_posts` */

DROP TABLE IF EXISTS `topic_posts`;

CREATE TABLE `topic_posts` (
  `topic_id` bigint(20) NOT NULL,
  `posts_id` bigint(20) NOT NULL,
  UNIQUE KEY `UK_s5bd5c2jgcw49x5w4fiqmiagd` (`posts_id`),
  KEY `FKp3croykyaohnl662esakg61yj` (`topic_id`),
  CONSTRAINT `FKitr4seeq9uagrsdo5e13tb8iw` FOREIGN KEY (`posts_id`) REFERENCES `post` (`id`),
  CONSTRAINT `FKp3croykyaohnl662esakg61yj` FOREIGN KEY (`topic_id`) REFERENCES `topic` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `topic_posts` */

/*Table structure for table `user_roles` */

DROP TABLE IF EXISTS `user_roles`;

CREATE TABLE `user_roles` (
  `user_id` bigint(20) NOT NULL,
  `role_id` int(11) NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`),
  KEY `FKrhfovtciq1l558cw6udg0h0d3` (`role_id`),
  CONSTRAINT `FK55itppkw3i07do3h7qoclqd4k` FOREIGN KEY (`user_id`) REFERENCES `myuser` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FKrhfovtciq1l558cw6udg0h0d3` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `user_roles` */

insert  into `user_roles`(`user_id`,`role_id`) values 
(1,1),
(5,1),
(6,1),
(7,1),
(8,1),
(10,1),
(11,1),
(23,1),
(1,2);

/*Table structure for table `verification_token` */

DROP TABLE IF EXISTS `verification_token`;

CREATE TABLE `verification_token` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `token` varchar(255) DEFAULT NULL,
  `user_user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKs6a82vcvosvakyopdhih7s4xk` (`user_user_id`),
  CONSTRAINT `FKs6a82vcvosvakyopdhih7s4xk` FOREIGN KEY (`user_user_id`) REFERENCES `myuser` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `verification_token` */

insert  into `verification_token`(`id`,`token`,`user_user_id`) values 
(6,'3e528d0a-1149-4ea9-b9ae-01db336c1470',23);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
