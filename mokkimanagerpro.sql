-- MySQL dump 10.13  Distrib 8.0.41, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: mokkimanagerpro
-- ------------------------------------------------------
-- Server version	9.2.0

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `asiakas`
--

DROP TABLE IF EXISTS `asiakas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
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

--
-- Dumping data for table `asiakas`
--

LOCK TABLES `asiakas` WRITE;
/*!40000 ALTER TABLE `asiakas` DISABLE KEYS */;
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
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `maksaa`
--

LOCK TABLES `maksaa` WRITE;
/*!40000 ALTER TABLE `maksaa` DISABLE KEYS */;
/*!40000 ALTER TABLE `maksaa` ENABLE KEYS */;
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mokki`
--

LOCK TABLES `mokki` WRITE;
/*!40000 ALTER TABLE `mokki` DISABLE KEYS */;
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `varaa`
--

LOCK TABLES `varaa` WRITE;
/*!40000 ALTER TABLE `varaa` DISABLE KEYS */;
/*!40000 ALTER TABLE `varaa` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-04-30 12:44:36
