-- MySQL dump 10.13  Distrib 5.7.11, for osx10.11 (x86_64)
--
-- Host: localhost    Database: puz
-- ------------------------------------------------------
-- Server version	5.7.11

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `challenges`
--

DROP TABLE IF EXISTS `challenges`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `challenges` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` varchar(255) NOT NULL,
  `meta` text NOT NULL,
  `geom` geometry NOT NULL,
  `points` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  SPATIAL KEY `i_geomidx` (`geom`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `challenges`
--

LOCK TABLES `challenges` WRITE;
/*!40000 ALTER TABLE `challenges` DISABLE KEYS */;
INSERT INTO `challenges` VALUES (3,'COFFEE','{\"question\":\"Find the coffee cup\",\"image_url\":\"http://i.imgur.com/1sXgcHc.jpg\",\"object\":[23,16,265,295]}','\0\0\0\0\0\0\0Mqpl<\ÃI@\0\0\0°\ÛÁ¿',20),(4,'COFFEE','{\"question\":\"Find the amber traffic light!\",\"image_url\":\"http://i.imgur.com/eN296Tx.jpg\",\"object\":[21,17,345,304]}','\0\0\0\0\0\0\0Ó¶¦%\ÃI@\0\0\0°º°Á¿',50),(5,'COFFEE','{\"question\":\"Find the green pillow!\",\"image_url\":\"https://i.imgur.com/JnctNp4.png\",\"object\":[45,24,395,393]}','\0\0\0\0\0\0\0\r\Ï)‚H\ÃI@\0\0\0\0E\ÖÁ¿',40),(6,'COFFEE','{\"question\":\"Find the !\",\"image_url\":\"http://i.imgur.com/rmO51Ke.jpg\",\"object\":[125,27,182,217]}','\0\0\0\0\0\0\0\Â\ßN\ÙL\ÃI@\0\0\0À\Ø\ëÁ¿',20),(7,'RIDDLE2','{\"question\":\"WHAT IS THIS??\",\"image_url\":\"http://i.imgur.com/FmD9AZu.png\",\"answers\":[\"chocolate\"]}','\0\0\0\0\0\0\0Mqpl<\ÃI@\0\0\0\0E\ÖÁ¿',9999),(11,'RIDDLE2','{\"question\":\"You\'re near UCL Hospital. Can you answer this question on medicine? What does the field of paediatrics focus on?\",\"image_url\":\"http://i.imgur.com/CWHT9mK.png\",\"answers\":[\"children\",\"child medicine\",\"kids\",\"Children\",\"Child medicine\",\"Kids\"]}','\0\0\0\0\0\0\0ˆFw;\ÃI@\ÛO\Æø0{Á¿',50),(12,'RIDDLE2','{\"question\":\"Ooh, sushi! Yummy! Can you tell me in which city it is believed the california roll was created?\",\"image_url\":\"http://i.imgur.com/HTmYxS7.png\",\"answers\":[\"Los Angeles\",\"LA\",\"la\"]} ','\0\0\0\0\0\0\0k›\âqQ\ÃI@¸ðÀ\0\ÂÁ¿',75),(13,'RIDDLE2','{\"question\":\"How about dem cmptrs, aye? Time for a pseudo-code puzzle. What would the following code print?\\na = 0\\nb = a\\na = 42%10\\nprint (a+b)\",\"image_url\":\"http://i.imgur.com/t8J49tr.png\",\"answers\":[\"2\"]}','\0\0\0\0\0\0\0[A\Ó\ÃI@þE>‘Á¿',50);
/*!40000 ALTER TABLE `challenges` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_challenges`
--

DROP TABLE IF EXISTS `user_challenges`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_challenges` (
  `user_id` int(11) NOT NULL,
  `challenge_id` int(11) NOT NULL,
  `completed` int(11) DEFAULT '0',
  `attempts` int(11) DEFAULT '0',
  `score_delta` int(11) DEFAULT '0',
  PRIMARY KEY (`user_id`,`challenge_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_challenges`
--

LOCK TABLES `user_challenges` WRITE;
/*!40000 ALTER TABLE `user_challenges` DISABLE KEYS */;
INSERT INTO `user_challenges` VALUES (1,3,0,1,20),(1,4,0,1,50),(1,5,0,1,40),(1,6,0,1,20),(1,7,0,1,9999),(1,11,0,1,50),(1,12,0,1,75),(1,13,0,1,50);
/*!40000 ALTER TABLE `user_challenges` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `token` varchar(255) DEFAULT NULL,
  `score` int(11) NOT NULL DEFAULT '0',
  `health` int(11) NOT NULL DEFAULT '50',
  `health_total` int(11) NOT NULL DEFAULT '50',
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`),
  UNIQUE KEY `token` (`token`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'jamie','e0469addd8d57a3623494096dabc19bebca1a038c9da696940b3f853d106a6ecfa5bd60ce8e72884efa3bd92b930da178fd616f40facad654212d7c2f8817dd4','thisisatesttoken',15469,50,50),(2,'aaa',NULL,NULL,12000,50,50),(3,'bbb',NULL,NULL,9000,50,50),(4,'ccc',NULL,NULL,5000,50,50);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'puz'
--
/*!50003 DROP FUNCTION IF EXISTS `slc` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = latin1 */ ;
/*!50003 SET character_set_results = latin1 */ ;
/*!50003 SET collation_connection  = latin1_swedish_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` FUNCTION `slc`(lat1 double, lon1 double, lat2 double, lon2 double) RETURNS double
RETURN 6371 * acos(cos(radians(lat1)) * cos(radians(lat2)) * cos(radians(lon2) - radians(lon1)) + sin(radians(lat1)) * sin(radians(lat2))) ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-03-12 21:09:17
