CREATE DATABASE  IF NOT EXISTS `mokkimanagerpro`; 
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


--
-- Dumping data for table `asiakas`
--

LOCK TABLES `asiakas` WRITE;

INSERT INTO `asiakas` VALUES (1,'Testi Suomalainen','testi.suomalainen@gmail.com','404005000','Yksityishenkilö'),(2,'Yrittäjä Virolainen','yrittaja.virolainen@gmail.com','504005000','Yritys'),(3,'Malli Ruotsalainen','malli.ruotsalainen@gmail.com','445006000','Yksityishenkilö');

UNLOCK TABLES;

--
-- Table structure for table `laskut`
--

DROP TABLE IF EXISTS `laskut`;

CREATE TABLE `laskut` (
  `id` int NOT NULL AUTO_INCREMENT,
  `varaus_id` int DEFAULT NULL,
  `määrä` decimal(10,2) DEFAULT NULL,
  `eräpäivä` datetime DEFAULT NULL,
  `luontiaika` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `varaus_id` (`varaus_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


--
-- Dumping data for table `laskut`
--

LOCK TABLES `laskut` WRITE;

UNLOCK TABLES;

--
-- Table structure for table `mokki`
--

DROP TABLE IF EXISTS `mokki`;

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


--
-- Dumping data for table `mokki`
--

LOCK TABLES `mokki` WRITE;

INSERT INTO `mokki` VALUES (1,'Isännän Tupa','Mökkitie 3, 80100 Joensuu','Neljän huoneen mökki järven rannalla',125.00,6,1),(2,'Emännän Tupa','Mökkitie 4 80100 Joensuu','Kolmen huoneen mökki järven rannalla',110.00,4,1),(3,'Talonpojan Pirtti','Mökkitie 6 80100 Joensuu','Pieni yhden huoneen mökki järven rannalla',80.00,2,1);

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `varaa`
--

LOCK TABLES `varaa` WRITE;

UNLOCK TABLES;


-- Dump completed on 2025-05-12 19:46:20
