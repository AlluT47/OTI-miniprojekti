CREATE DATABASE  IF NOT EXISTS `mokkimanagerpro` ;
USE `mokkimanagerpro`;
-- MySQL dump 10.13  Distrib 8.0.41, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: mokkimanagerpro
-- ------------------------------------------------------
-- Server version	9.2.0

--
-- Table structure for table `asiakas`
--

DROP TABLE IF EXISTS `asiakas`;

CREATE TABLE `asiakas` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nimi` varchar(50) DEFAULT NULL,
  `sposti` varchar(50) DEFAULT NULL,
  `puhelin` varchar(20) DEFAULT NULL,
  `asiakastyyppi` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `sposti` (`sposti`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `asiakas`
--

LOCK TABLES `asiakas` WRITE;
/*!40000 ALTER TABLE `asiakas` DISABLE KEYS */;
INSERT INTO `asiakas` VALUES (1,'Testi Suomalainen','testi.suomalainen@gmail.com','404005000','Yksityishenkilö'),(2,'Yrittäjä Virolainen','yrittaja.virolainen@gmail.com','504005000','Yritys'),(3,'Malli Ruotsalainen','malli.ruotsalainen@gmail.com','445006000','Yksityishenkilö');
/*!40000 ALTER TABLE `asiakas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `laskut`
--

DROP TABLE IF EXISTS `laskut`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `laskut` (
  `id` int NOT NULL AUTO_INCREMENT,
  `varaus_id` int DEFAULT NULL,
  `määrä` decimal(10,2) DEFAULT NULL,
  `eräpäivä` datetime DEFAULT NULL,
  `luontiaika` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `varaus_id` (`varaus_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `laskut`
--

LOCK TABLES `laskut` WRITE;
/*!40000 ALTER TABLE `laskut` DISABLE KEYS */;
INSERT INTO `laskut` VALUES (1,1,875.00,'2025-05-29 23:59:59','2025-05-15 14:04:24');
/*!40000 ALTER TABLE `laskut` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mokki`
--

DROP TABLE IF EXISTS `mokki`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `mokki` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nimi` varchar(50) DEFAULT NULL,
  `osoite` varchar(100) DEFAULT NULL,
  `kuvaus` text,
  `hinta_per_yö` decimal(10,2) DEFAULT NULL,
  `kapasiteetti` int DEFAULT NULL,
  `on_vapaana` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mokki`
--

LOCK TABLES `mokki` WRITE;
/*!40000 ALTER TABLE `mokki` DISABLE KEYS */;
INSERT INTO `mokki` VALUES (1,'Isännän Tupa','Mökkitie 3, 80100 Joensuu','Neljän huoneen mökki järven rannalla',125.00,6,0),(2,'Emännän Tupa','Mökkitie 4 80100 Joensuu','Kolmen huoneen mökki järven rannalla',110.00,4,1),(3,'Talonpojan Pirtti','Mökkitie 6 80100 Joensuu','Pieni yhden huoneen mökki järven rannalla',80.00,2,1);
/*!40000 ALTER TABLE `mokki` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `varaa`
--

DROP TABLE IF EXISTS `varaa`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `varaa` (
  `id` int NOT NULL AUTO_INCREMENT,
  `asiakas_id` int DEFAULT NULL,
  `mökki_id` int DEFAULT NULL,
  `aloitus_päivä` date DEFAULT NULL,
  `lopetus_päivä` date DEFAULT NULL,
  `tila` varchar(50) DEFAULT NULL,
  `varaus_aika` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `asiakas_id` (`asiakas_id`),
  KEY `mökki_id` (`mökki_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `varaa`
--

LOCK TABLES `varaa` WRITE;
/*!40000 ALTER TABLE `varaa` DISABLE KEYS */;
INSERT INTO `varaa` VALUES (1,1,1,'2025-05-15','2025-05-22','Vahvistettu','2025-05-15 14:03:21');
/*!40000 ALTER TABLE `varaa` ENABLE KEYS */;
UNLOCK TABLES;


-- Dump completed on 2025-05-15 14:08:40
