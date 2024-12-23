-- MySQL dump 10.13  Distrib 8.0.39, for Win64 (x86_64)
--
-- Host: localhost    Database: rms_db
-- ------------------------------------------------------
-- Server version	8.0.39

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `tbl_invoices`
--

DROP TABLE IF EXISTS `tbl_invoices`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tbl_invoices` (
  `id` int NOT NULL AUTO_INCREMENT,
  `order_id` int DEFAULT NULL,
  `total_amount` decimal(10,2) DEFAULT NULL,
  `invoice_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `order_id` (`order_id`),
  CONSTRAINT `tbl_invoices_ibfk_1` FOREIGN KEY (`order_id`) REFERENCES `tbl_tables` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_invoices`
--

LOCK TABLES `tbl_invoices` WRITE;
/*!40000 ALTER TABLE `tbl_invoices` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_invoices` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_menu`
--

DROP TABLE IF EXISTS `tbl_menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tbl_menu` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `price` decimal(10,2) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_menu`
--

LOCK TABLES `tbl_menu` WRITE;
/*!40000 ALTER TABLE `tbl_menu` DISABLE KEYS */;
INSERT INTO `tbl_menu` VALUES (1,'K├Âfte',180.00),(2,'Tavuk',55.00),(3,'Pilav',35.00),(5,'Makarna',80.00),(6,'F─▒r─▒n Makarna',67.00),(9,'Mercimek ├çorbas─▒',42.00),(10,'Bal─▒k Tava',120.00),(11,'Biftek',150.00),(12,'Hamburger Men├╝',280.00),(14,'Kurba─şa Baca─ş─▒ ├çorbas─▒',1740.00);
/*!40000 ALTER TABLE `tbl_menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_orderdetails`
--

DROP TABLE IF EXISTS `tbl_orderdetails`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tbl_orderdetails` (
  `orderNo` int DEFAULT NULL,
  `order_id` int NOT NULL AUTO_INCREMENT,
  `product_name` varchar(255) COLLATE utf32_turkish_ci DEFAULT NULL,
  `quantity` int DEFAULT NULL,
  `table_no` varchar(45) COLLATE utf32_turkish_ci DEFAULT NULL,
  `product_total_price` decimal(6,0) DEFAULT NULL,
  PRIMARY KEY (`order_id`),
  KEY `product_id` (`product_name`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf32 COLLATE=utf32_turkish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_orderdetails`
--

LOCK TABLES `tbl_orderdetails` WRITE;
/*!40000 ALTER TABLE `tbl_orderdetails` DISABLE KEYS */;
INSERT INTO `tbl_orderdetails` VALUES (1001,13,'Pilav',1,'100',35),(1001,14,'Mercimek ├çorbas─▒',2,'100',84),(1001,15,'Biftek',2,'101',300),(1001,16,'Bal─▒k Tava',1,'101',120),(1001,17,'Makarna',1,'100',80),(1001,18,'Bal─▒k Tava',2,'100',240),(1001,19,'F─▒r─▒n Makarna',1,'100',67),(1001,20,'K├Âfte',2,'100',360),(1001,21,'Mercimek ├çorbas─▒',1,'100',42);
/*!40000 ALTER TABLE `tbl_orderdetails` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_tables`
--

DROP TABLE IF EXISTS `tbl_tables`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tbl_tables` (
  `id` int NOT NULL AUTO_INCREMENT,
  `table_number` varchar(255) NOT NULL,
  `total_price` decimal(10,2) NOT NULL DEFAULT '0.00',
  `customer` tinyint NOT NULL DEFAULT '0',
  `currentOrderNo` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_tables`
--

LOCK TABLES `tbl_tables` WRITE;
/*!40000 ALTER TABLE `tbl_tables` DISABLE KEYS */;
INSERT INTO `tbl_tables` VALUES (1,'100',0.00,1,1001),(2,'101',0.00,1,0),(3,'102',0.00,0,NULL),(4,'103',0.00,0,NULL),(5,'104',0.00,0,NULL),(6,'35',0.00,0,NULL);
/*!40000 ALTER TABLE `tbl_tables` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_users`
--

DROP TABLE IF EXISTS `tbl_users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tbl_users` (
  `id` int NOT NULL AUTO_INCREMENT,
  `userName` varchar(30) NOT NULL,
  `password` varchar(30) NOT NULL,
  `userType` varchar(45) NOT NULL DEFAULT 'user',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_users`
--

LOCK TABLES `tbl_users` WRITE;
/*!40000 ALTER TABLE `tbl_users` DISABLE KEYS */;
INSERT INTO `tbl_users` VALUES (1,'ugur','1234','admin'),(6,'user','user','user');
/*!40000 ALTER TABLE `tbl_users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-12-14 21:59:51
