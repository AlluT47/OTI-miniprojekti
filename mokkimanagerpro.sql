-- MySQL dump 10.13  Distrib 8.0.41, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: mokkimanagerpro
-- ------------------------------------------------------
-- Server version	9.2.0

DROP TABLE IF EXISTS `asiakas`;

CREATE TABLE `asiakas` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nimi` varchar(50) DEFAULT NULL,
  `sposti` varchar(50) DEFAULT NULL,
  `puhelin` varchar(20) DEFAULT NULL,
  `asiakastyyppi` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `sposti` (`sposti`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;


LOCK TABLES `asiakas` WRITE;

UNLOCK TABLES;

--
-- Dumping data for table `asiakas`
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
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `laskut`
--

LOCK TABLES `laskut` WRITE;
/*!40000 ALTER TABLE `laskut` DISABLE KEYS */;
/*!40000 ALTER TABLE `laskut` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `maksaa`
--

DROP TABLE IF EXISTS `maksaa`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `maksaa` (
  `id` int NOT NULL AUTO_INCREMENT,
  `lasku_id` int DEFAULT NULL,
  `asiakas_id` int DEFAULT NULL,
  `maksutapa` varchar(50) DEFAULT NULL,
  `maksu_aika` datetime DEFAULT NULL,
  `summa` decimal(10,2) DEFAULT NULL,
  `on_maksettu` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `lasku_id` (`lasku_id`),
  KEY `asiakas_id` (`asiakas_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `maksaa`
--

LOCK TABLES `maksaa` WRITE;

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `mokki`
--

LOCK TABLES `mokki` WRITE;

UNLOCK TABLES;

--
-- Table structure for table `varaa`
--

DROP TABLE IF EXISTS `varaa`;

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


--
-- Dumping data for table `varaa`
--

LOCK TABLES `varaa` WRITE;

UNLOCK TABLES;

-- Dump completed on 2025-04-30 12:44:36
