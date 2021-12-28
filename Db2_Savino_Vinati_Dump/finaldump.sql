CREATE DATABASE  IF NOT EXISTS `db2_savino_vinati` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `db2_savino_vinati`;
-- MySQL dump 10.13  Distrib 8.0.27, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: db2_savino_vinati
-- ------------------------------------------------------
-- Server version	8.0.27

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
-- Table structure for table `activationschedule`
--

DROP TABLE IF EXISTS `activationschedule`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `activationschedule` (
  `id` int NOT NULL AUTO_INCREMENT,
  `iduser` int NOT NULL,
  `activationdate` date NOT NULL,
  `deactivationdate` date NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `FK_UserActivationSchedule_idx` (`iduser`),
  CONSTRAINT `FK_UserActivationSchedule` FOREIGN KEY (`iduser`) REFERENCES `user` (`id`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=176 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activationschedule`
--

LOCK TABLES `activationschedule` WRITE;
/*!40000 ALTER TABLE `activationschedule` DISABLE KEYS */;
/*!40000 ALTER TABLE `activationschedule` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `alert`
--

DROP TABLE IF EXISTS `alert`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `alert` (
  `id` int NOT NULL AUTO_INCREMENT,
  `idinsolventuser` int NOT NULL,
  `idorder` int NOT NULL,
  `amount` float NOT NULL,
  `active` tinyint NOT NULL DEFAULT '1',
  `lastrejection` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `FK_AlertUser_idx` (`idinsolventuser`),
  KEY `FK_AlertOrder_idx` (`idorder`),
  CONSTRAINT `FK_AlertOrder` FOREIGN KEY (`idorder`) REFERENCES `order` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_AlertUser` FOREIGN KEY (`idinsolventuser`) REFERENCES `insolventuser` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `alert`
--

LOCK TABLES `alert` WRITE;
/*!40000 ALTER TABLE `alert` DISABLE KEYS */;
/*!40000 ALTER TABLE `alert` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb3 */ ;
/*!50003 SET character_set_results = utf8mb3 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `MV_AlertInsert` AFTER INSERT ON `alert` FOR EACH ROW BEGIN



	IF (NEW.active = 1) THEN



	INSERT INTO mv_alerts (idalert) VALUES (new.id);



    END IF;



END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `MV_AlertUpdate` AFTER UPDATE ON `alert` FOR EACH ROW BEGIN



	IF (OLD.active = 1 AND NEW.active = 0) THEN



	DELETE FROM mv_alerts WHERE idalert = new.id;



    END IF;



END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `insolventuser`
--

DROP TABLE IF EXISTS `insolventuser`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `insolventuser` (
  `id` int NOT NULL,
  `failedpaymentcount` int NOT NULL DEFAULT '0',
  `insolvent` tinyint NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  CONSTRAINT `FK_InsolventUser` FOREIGN KEY (`id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `insolventuser`
--

LOCK TABLES `insolventuser` WRITE;
/*!40000 ALTER TABLE `insolventuser` DISABLE KEYS */;
/*!40000 ALTER TABLE `insolventuser` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `MV_UserBecomesInsolventUpdate` AFTER UPDATE ON `insolventuser` FOR EACH ROW BEGIN

    IF (OLD.insolvent = 0 AND NEW.insolvent= 1) THEN

        INSERT INTO mv_insolventuser(idinsolventuser)

        VALUES(new.id);

	ELSEIF(OLD.insolvent = 1 AND NEW.insolvent = 0) THEN

		DELETE FROM mv_insolventuser WHERE idinsolventuser = new.id;

    END IF;

END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `mv_alerts`
--

DROP TABLE IF EXISTS `mv_alerts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `mv_alerts` (
  `id` int NOT NULL AUTO_INCREMENT,
  `idalert` int NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  UNIQUE KEY `idalert_UNIQUE` (`idalert`),
  CONSTRAINT `FK_idalert` FOREIGN KEY (`idalert`) REFERENCES `alert` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mv_alerts`
--

LOCK TABLES `mv_alerts` WRITE;
/*!40000 ALTER TABLE `mv_alerts` DISABLE KEYS */;
/*!40000 ALTER TABLE `mv_alerts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mv_bestproduct`
--

DROP TABLE IF EXISTS `mv_bestproduct`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `mv_bestproduct` (
  `id` int NOT NULL AUTO_INCREMENT,
  `idoptionalproduct` int NOT NULL,
  `value` float NOT NULL,
  `sales` int NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `FK_MvBestProduct_idx` (`idoptionalproduct`),
  CONSTRAINT `FK_MvBestProduct` FOREIGN KEY (`idoptionalproduct`) REFERENCES `optionalproduct` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mv_bestproduct`
--

LOCK TABLES `mv_bestproduct` WRITE;
/*!40000 ALTER TABLE `mv_bestproduct` DISABLE KEYS */;
/*!40000 ALTER TABLE `mv_bestproduct` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mv_insolventuser`
--

DROP TABLE IF EXISTS `mv_insolventuser`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `mv_insolventuser` (
  `id` int NOT NULL AUTO_INCREMENT,
  `idinsolventuser` int NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  UNIQUE KEY `idinsolventuser_UNIQUE` (`idinsolventuser`),
  CONSTRAINT `FK_MVInsolventUser` FOREIGN KEY (`idinsolventuser`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=52 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mv_insolventuser`
--

LOCK TABLES `mv_insolventuser` WRITE;
/*!40000 ALTER TABLE `mv_insolventuser` DISABLE KEYS */;
/*!40000 ALTER TABLE `mv_insolventuser` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mv_package`
--

DROP TABLE IF EXISTS `mv_package`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `mv_package` (
  `id` int NOT NULL AUTO_INCREMENT,
  `idpackage` int NOT NULL,
  `sales` int DEFAULT NULL,
  `value` float DEFAULT NULL,
  `valuewithproducts` float DEFAULT NULL,
  `avgoptionalproducts` float DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  UNIQUE KEY `idpackage_UNIQUE` (`idpackage`),
  KEY `FL_MVPackage_idx` (`idpackage`),
  CONSTRAINT `FL_MVPackage` FOREIGN KEY (`idpackage`) REFERENCES `servicepackage` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mv_package`
--

LOCK TABLES `mv_package` WRITE;
/*!40000 ALTER TABLE `mv_package` DISABLE KEYS */;
/*!40000 ALTER TABLE `mv_package` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb3 */ ;
/*!50003 SET character_set_results = utf8mb3 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `MV_PackageNoNull` BEFORE INSERT ON `mv_package` FOR EACH ROW BEGIN

	IF(new.sales is null) THEN 

		SET new.sales = 0;

    END IF;

	IF(new.`value` is null) THEN 

		SET new.`value` = 0;

	END IF;

    IF (new.valuewithproducts is null) THEN 

		SET new.valuewithproducts = 0;

	END IF;

    IF(new.avgoptionalproducts is null) THEN



		SET new.avgoptionalproducts = 0;



    END IF;



END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `mv_packageperiod`
--

DROP TABLE IF EXISTS `mv_packageperiod`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `mv_packageperiod` (
  `id` int NOT NULL AUTO_INCREMENT,
  `idpackage` int NOT NULL,
  `idperiod` int NOT NULL,
  `sales` int NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `FK_MVPackagePeriod_idx` (`idpackage`),
  KEY `FK_MVPackagePeriodPeriod_idx` (`idperiod`),
  KEY `FK_MVPackagePeriodVP_idx` (`idperiod`),
  CONSTRAINT `FK_MVPackagePeriod` FOREIGN KEY (`idpackage`) REFERENCES `servicepackage` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_MVPackagePeriodVP` FOREIGN KEY (`idperiod`) REFERENCES `validityperiod` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mv_packageperiod`
--

LOCK TABLES `mv_packageperiod` WRITE;
/*!40000 ALTER TABLE `mv_packageperiod` DISABLE KEYS */;
/*!40000 ALTER TABLE `mv_packageperiod` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mv_suspendedorders`
--

DROP TABLE IF EXISTS `mv_suspendedorders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `mv_suspendedorders` (
  `id` int NOT NULL AUTO_INCREMENT,
  `idorder` int NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  UNIQUE KEY `idorder_UNIQUE` (`idorder`),
  CONSTRAINT `FK_MVSuspendedOrders` FOREIGN KEY (`idorder`) REFERENCES `order` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=116 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mv_suspendedorders`
--

LOCK TABLES `mv_suspendedorders` WRITE;
/*!40000 ALTER TABLE `mv_suspendedorders` DISABLE KEYS */;
/*!40000 ALTER TABLE `mv_suspendedorders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `optionalproduct`
--

DROP TABLE IF EXISTS `optionalproduct`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `optionalproduct` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `monthlyprice` float NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `optionalproduct`
--

LOCK TABLES `optionalproduct` WRITE;
/*!40000 ALTER TABLE `optionalproduct` DISABLE KEYS */;
/*!40000 ALTER TABLE `optionalproduct` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order`
--

DROP TABLE IF EXISTS `order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order` (
  `id` int NOT NULL AUTO_INCREMENT,
  `idservicepackage` int NOT NULL,
  `iduser` int NOT NULL,
  `idvalidityperiod` int NOT NULL,
  `totalvalue` float NOT NULL,
  `startdate` date NOT NULL,
  `datehour` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `paid` tinyint NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `OrderUser_idx` (`iduser`),
  KEY `OrderPackage_idx` (`idservicepackage`),
  KEY `FK_OrderValidityPeriodOfPackage_idx` (`idvalidityperiod`),
  CONSTRAINT `FK_OrderPackage` FOREIGN KEY (`idservicepackage`) REFERENCES `servicepackage` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `FK_OrderUser` FOREIGN KEY (`iduser`) REFERENCES `user` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `FK_OrderValidityPeriodOfPackage` FOREIGN KEY (`idvalidityperiod`) REFERENCES `validityperiod` (`id`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=238 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order`
--

LOCK TABLES `order` WRITE;
/*!40000 ALTER TABLE `order` DISABLE KEYS */;
/*!40000 ALTER TABLE `order` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `MV_SuspendedOrderInsert` AFTER INSERT ON `order` FOR EACH ROW BEGIN

    IF (NEW.paid = 0) THEN

        INSERT INTO mv_suspendedorders(idorder)

        VALUES(new.id);

    END IF;

END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb3 */ ;
/*!50003 SET character_set_results = utf8mb3 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `MV_PackageInsertOrder` AFTER INSERT ON `order` FOR EACH ROW BEGIN



    DECLARE countPI INTEGER;



    DECLARE idpackagePI INTEGER;



    DECLARE salesPI integer;



    DECLARE valuePI float;



    DECLARE valuewithproductsPI float;



    DECLARE avgoptionalproductsPI float;











 SELECT COUNT(*) into countPI



    FROM mv_package



    WHERE new.idservicepackage = mv_package.idpackage;



    



    IF countPI < 1 THEN



    SET idpackagePI = new.idservicepackage;



		INSERT INTO mv_package (idpackage, sales, `value`, valuewithproducts, avgoptionalproducts)



		VALUES(idpackagePI,0, 0,0,0);



    END IF;







SELECT  sp.id as idpackage, COUNT(o.id) as sales, SUM(vp.validityperiod * pp.monthlycost) as `value`, SUM(o.totalvalue) as valuewithproducts, CAST(AVG(a.count)as FLOAT) as avgoptionalproducts







INTO idpackagePI,salesPI,valuePI,valuewithproductsPI,avgoptionalproductsPI



  FROM servicepackage sp



    LEFT JOIN `order` o 



      ON sp.id = o.idservicepackage



        LEFT JOIN validityperiod vp



          ON o.idvalidityperiod = vp.id



            LEFT JOIN packageperiod pp 



              ON vp.id = pp.idvalidityperiod AND o.idservicepackage = pp.idservicepackage



              LEFT JOIN (



                            SELECT op.idorder as idorder, COUNT(op.idorder) as count 



                            FROM orderproduct op



                            GROUP BY idorder    



                ) as a



     ON a.idorder = o.id



     WHERE o.idservicepackage = new.idservicepackage AND o.paid = 1 



  GROUP BY sp.id;



  IF new.paid=1 THEN 



	UPDATE mv_package 



  SET idpackage = idpackagePI, sales = salesPI, `value` = valuePI, valuewithproducts = valuewithproductsPI, avgoptionalproducts = avgoptionalproductsPI



  WHERE mv_package.idpackage = new.idservicepackage;



  END IF;



  END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb3 */ ;
/*!50003 SET character_set_results = utf8mb3 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `MV_PackagePeriodOrderInsert` AFTER INSERT ON `order` FOR EACH ROW BEGIN



  DECLARE countPOI integer;



  DECLARE periodPP integer;



  DECLARE idservicepackagePP integer;



  DECLARE salesPP integer;



  SET idservicepackagePP = new.idservicepackage;



  SET periodPP = new.idvalidityperiod;







   SELECT COUNT(*) into countPOI



    FROM mv_packageperiod mvp



    WHERE mvp.idpackage = idservicepackagePP AND mvp.idperiod = periodPP;



    



    IF countPOI < 1 THEN







  INSERT INTO mv_packageperiod ( idpackage, idperiod, sales) VALUES ( idservicepackagePP, periodPP, 0 );



  END IF;







 SELECT COUNT(o.id) INTO salesPP



  FROM `order` o



	  WHERE o.paid = 1 AND o.idservicepackage = idservicepackagePP AND o.idvalidityperiod = periodPP;



  IF new.paid= 1 THEN



  UPDATE mv_packageperiod mvp SET mvp.sales = salesPP



  WHERE  mvp.idpackage = idservicepackagePP AND mvp.idperiod = periodPP;



  END IF;



END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `MV_SuspendedOrderUpdate` AFTER UPDATE ON `order` FOR EACH ROW BEGIN

    IF (OLD.paid = 0 AND NEW.paid = 1) THEN

        DELETE FROM mv_suspendedorders

        WHERE idorder = old.id;

    END IF;

END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb3 */ ;
/*!50003 SET character_set_results = utf8mb3 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `MV_BestProductUpdate` AFTER UPDATE ON `order` FOR EACH ROW BEGIN



    DECLARE valueBP float;



    DECLARE salesBP integer;



    DECLARE idBP integer;



    



  IF old.paid = 0 AND new.paid = 1 THEN







    SELECT op.idoptionalproduct as idoptionalproduct, SUM(opp.monthlyprice * vp.validityperiod) as `value`, COUNT(op.idorder) as sales



    INTO idBP, valueBP, salesBP



	FROM `order` o



		LEFT JOIN validityperiod vp



			ON o.idvalidityperiod = vp.id



		LEFT JOIN orderproduct op



			ON o.id = op.idorder



		LEFT JOIN optionalproduct opp



			ON op.idoptionalproduct = opp.id



	WHERE o.paid = 1



	GROUP BY opp.id



    ORDER BY `value` DESC



	LIMIT 1;



    



    UPDATE mv_bestproduct



    SET idoptionalproduct = idBP, `value` = valueBP, sales = salesBP;



    END IF;



END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb3 */ ;
/*!50003 SET character_set_results = utf8mb3 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `MV_PackageUpdateOrder` AFTER UPDATE ON `order` FOR EACH ROW BEGIN



    DECLARE idpackagePI integer;



    DECLARE salesPI integer;



    DECLARE valuePI float;



    DECLARE valuewithproductsPI float;



    DECLARE avgoptionalproductsPI float;







IF old.paid = 0 AND new.paid = 1 THEN



SELECT  sp.id as idpackage, COUNT(o.id) as sales,SUM(vp.validityperiod * pp.monthlycost) as `value`, SUM(o.totalvalue) as valuewithproducts,CAST(AVG(a.count)as FLOAT) as avgoptionalproducts



INTO idpackagePI,salesPI,valuePI,valuewithproductsPI,avgoptionalproductsPI



  FROM servicepackage sp



    LEFT JOIN `order` o 



      ON sp.id = o.idservicepackage



        LEFT JOIN validityperiod vp



          ON o.idvalidityperiod = vp.id



            LEFT JOIN packageperiod pp 



              ON vp.id = pp.idvalidityperiod AND o.idservicepackage = pp.idservicepackage



              LEFT JOIN (



                            SELECT op.idorder as idorder, COUNT(op.idorder) as count 



                            FROM orderproduct op



                            GROUP BY idorder    



                ) as a



     ON a.idorder = o.id



     WHERE o.idservicepackage = new.idservicepackage AND o.paid = 1 



  GROUP BY sp.id;



	UPDATE mv_package



  SET idpackage = idpackagePI, sales = salesPI, `value` = valuePI, valuewithproducts = valuewithproductsPI, avgoptionalproducts = avgoptionalproductsPI



  WHERE mv_package.idpackage = new.idservicepackage;



 END IF;



  END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb3 */ ;
/*!50003 SET character_set_results = utf8mb3 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `MV_PackagePeriodOrderUpdate` AFTER UPDATE ON `order` FOR EACH ROW BEGIN



  DECLARE periodPP integer;



  DECLARE idservicepackagePP integer;



  DECLARE salesPP integer;







  IF old.paid = 0 AND new.paid = 1 THEN 







  SET idservicepackagePP = new.idservicepackage;



  SET periodPP = new.idvalidityperiod;



 



 SELECT COUNT(o.id) INTO salesPP



  FROM `order` o



	  WHERE o.paid = 1 AND o.idservicepackage = idservicepackagePP AND o.idvalidityperiod = periodPP;











  UPDATE mv_packageperiod mvp SET mvp.sales = salesPP



  WHERE  mvp.idpackage = idservicepackagePP AND mvp.idperiod= periodPP;







END IF;



END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `orderproduct`
--

DROP TABLE IF EXISTS `orderproduct`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orderproduct` (
  `idorder` int NOT NULL,
  `idoptionalproduct` int NOT NULL,
  PRIMARY KEY (`idorder`,`idoptionalproduct`),
  KEY `FK_OptionalProductOrder_idx` (`idoptionalproduct`),
  CONSTRAINT `FK_OptionalProductOrder` FOREIGN KEY (`idoptionalproduct`) REFERENCES `optionalproduct` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_OrderOptionalProduct` FOREIGN KEY (`idorder`) REFERENCES `order` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orderproduct`
--

LOCK TABLES `orderproduct` WRITE;
/*!40000 ALTER TABLE `orderproduct` DISABLE KEYS */;
/*!40000 ALTER TABLE `orderproduct` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb3 */ ;
/*!50003 SET character_set_results = utf8mb3 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `MV_BestProduct` AFTER INSERT ON `orderproduct` FOR EACH ROW BEGIN

	DECLARE countBP INTEGER;

    DECLARE valueBP float;

    DECLARE salesBP integer;

    DECLARE idBP integer;

    

    

    SELECT COUNT(*) into countBP

    FROM mv_bestproduct;

    

    IF countBP < 1 THEN

		INSERT INTO mv_bestproduct (idoptionalproduct, value, sales)

		VALUES(new.idoptionalproduct, 0, 0);

    END IF;

    

    SELECT op.idoptionalproduct as idoptionalproduct, SUM(opp.monthlyprice * vp.validityperiod) as `value`, COUNT(op.idorder) as sales

    INTO idBP, valueBP, salesBP

	FROM `order` o

		LEFT JOIN validityperiod vp

			ON o.idvalidityperiod = vp.id

		LEFT JOIN orderproduct op

			ON o.id = op.idorder

		LEFT JOIN optionalproduct opp

			ON op.idoptionalproduct = opp.id

	WHERE o.paid = 1

	GROUP BY opp.id

    ORDER BY `value` DESC

	LIMIT 1;

    

    UPDATE mv_bestproduct

    SET idoptionalproduct = idBP, `value` = valueBP, sales = salesBP;

    



END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb3 */ ;
/*!50003 SET character_set_results = utf8mb3 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `MV_PackageOrderProductInsert` AFTER INSERT ON `orderproduct` FOR EACH ROW BEGIN



    DECLARE avgoptionalproductsPI float;



    DECLARE paidPI tinyint;



    DECLARE idpackagePI int;



    SELECT o.idservicepackage, o.paid INTO idpackagePI, paidPI FROM `order` o WHERE o.id = new.idorder;











IF paidPI = 1  THEN



SELECT CAST(AVG(a.count)as FLOAT) as avgoptionalproducts



INTO avgoptionalproductsPI



  FROM servicepackage sp



    LEFT JOIN `order` o 



      ON sp.id = o.idservicepackage



              LEFT JOIN (



                            SELECT op.idorder as idorder, COUNT(op.idorder) as count 



                            FROM orderproduct op



                            GROUP BY idorder    



                ) as a



     ON a.idorder = o.id



     WHERE idpackagePI = o.idservicepackage AND o.paid = 1 



  GROUP BY sp.id;



	UPDATE mv_package



  SET avgoptionalproducts = avgoptionalproductsPI



  WHERE mv_package.idpackage = idpackagePI;



 END IF;



  END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `packageperiod`
--

DROP TABLE IF EXISTS `packageperiod`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `packageperiod` (
  `idservicepackage` int NOT NULL,
  `idvalidityperiod` int NOT NULL,
  `monthlycost` float NOT NULL,
  PRIMARY KEY (`idservicepackage`,`idvalidityperiod`),
  KEY `ValidityPeriod_idx` (`idvalidityperiod`),
  KEY `ServicePackage_idx` (`idservicepackage`),
  CONSTRAINT `FK_ServicePackageValidityPeriod` FOREIGN KEY (`idservicepackage`) REFERENCES `servicepackage` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_ValidityPeriodServicePackage` FOREIGN KEY (`idvalidityperiod`) REFERENCES `validityperiod` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `packageperiod`
--

LOCK TABLES `packageperiod` WRITE;
/*!40000 ALTER TABLE `packageperiod` DISABLE KEYS */;
/*!40000 ALTER TABLE `packageperiod` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb3 */ ;
/*!50003 SET character_set_results = utf8mb3 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `MV_PackagePeriodPackageInsert` AFTER INSERT ON `packageperiod` FOR EACH ROW BEGIN



  DECLARE periodPP integer;



  DECLARE idservicepackagePP integer;



  SET idservicepackagePP = new.idservicepackage;



  



  SET periodPP = new.idvalidityperiod;



 



  INSERT INTO mv_packageperiod (idpackage, idperiod, sales) VALUES (idservicepackagePP,periodPP,0);



END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `packageproduct`
--

DROP TABLE IF EXISTS `packageproduct`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `packageproduct` (
  `idservicepackage` int NOT NULL,
  `idoptionalproduct` int NOT NULL,
  PRIMARY KEY (`idoptionalproduct`,`idservicepackage`),
  KEY `ServicePackage_idx` (`idservicepackage`),
  CONSTRAINT `FK_OptionalProductServicePackage` FOREIGN KEY (`idoptionalproduct`) REFERENCES `optionalproduct` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_ServicePackageOptionalProduct` FOREIGN KEY (`idservicepackage`) REFERENCES `servicepackage` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `packageproduct`
--

LOCK TABLES `packageproduct` WRITE;
/*!40000 ALTER TABLE `packageproduct` DISABLE KEYS */;
/*!40000 ALTER TABLE `packageproduct` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `productschedule`
--

DROP TABLE IF EXISTS `productschedule`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `productschedule` (
  `idactivation` int NOT NULL,
  `idoptionalproduct` int NOT NULL,
  PRIMARY KEY (`idactivation`,`idoptionalproduct`),
  KEY `FK_OptionalProductActivation_idx` (`idoptionalproduct`),
  KEY `FK_ProductActivation_idx` (`idactivation`),
  CONSTRAINT `FK_ActivationOP` FOREIGN KEY (`idactivation`) REFERENCES `activationschedule` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_OptionalProductActivation` FOREIGN KEY (`idoptionalproduct`) REFERENCES `optionalproduct` (`id`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `productschedule`
--

LOCK TABLES `productschedule` WRITE;
/*!40000 ALTER TABLE `productschedule` DISABLE KEYS */;
/*!40000 ALTER TABLE `productschedule` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `service`
--

DROP TABLE IF EXISTS `service`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `service` (
  `id` int NOT NULL AUTO_INCREMENT,
  `idtype` int NOT NULL,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  UNIQUE KEY `name_UNIQUE` (`name`),
  KEY `idtype_idx` (`idtype`),
  CONSTRAINT `FK_Type` FOREIGN KEY (`idtype`) REFERENCES `servicetype` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `service`
--

LOCK TABLES `service` WRITE;
/*!40000 ALTER TABLE `service` DISABLE KEYS */;
INSERT INTO `service` VALUES (1,1,'FixedPhone'),(2,2,'MobilePhone'),(3,3,'FixedInternet'),(4,2,'MobilePhoneOldAndYoung'),(5,4,'MobileInternet'),(6,4,'MobileInternetOldAndYoung');
/*!40000 ALTER TABLE `service` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `serviceinternet`
--

DROP TABLE IF EXISTS `serviceinternet`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `serviceinternet` (
  `id` int NOT NULL,
  `includedgbs` int NOT NULL,
  `feeextragbs` float NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  CONSTRAINT `FK_ServiceInternet` FOREIGN KEY (`id`) REFERENCES `service` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `serviceinternet`
--

LOCK TABLES `serviceinternet` WRITE;
/*!40000 ALTER TABLE `serviceinternet` DISABLE KEYS */;
INSERT INTO `serviceinternet` VALUES (3,1000,0.5),(5,50,2),(6,50,1);
/*!40000 ALTER TABLE `serviceinternet` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `servicemobile`
--

DROP TABLE IF EXISTS `servicemobile`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `servicemobile` (
  `id` int NOT NULL,
  `includedminutes` int NOT NULL,
  `includedsms` int NOT NULL,
  `feeextraminutes` float NOT NULL,
  `feeextrasms` float NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  CONSTRAINT `FK_ServiceMobile` FOREIGN KEY (`id`) REFERENCES `service` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `servicemobile`
--

LOCK TABLES `servicemobile` WRITE;
/*!40000 ALTER TABLE `servicemobile` DISABLE KEYS */;
INSERT INTO `servicemobile` VALUES (2,1000,500,0.1,0.2),(4,500,1000,0.2,0.1);
/*!40000 ALTER TABLE `servicemobile` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `servicepackage`
--

DROP TABLE IF EXISTS `servicepackage`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `servicepackage` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  UNIQUE KEY `name_UNIQUE` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `servicepackage`
--

LOCK TABLES `servicepackage` WRITE;
/*!40000 ALTER TABLE `servicepackage` DISABLE KEYS */;
/*!40000 ALTER TABLE `servicepackage` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb3 */ ;
/*!50003 SET character_set_results = utf8mb3 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `MV_Package` AFTER INSERT ON `servicepackage` FOR EACH ROW BEGIN



	INSERT INTO mv_package (idpackage, sales, `value`, valuewithproducts, avgoptionalproducts) 



VALUES (new.id,null,null,null,null);



  END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `servicepackageservice`
--

DROP TABLE IF EXISTS `servicepackageservice`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `servicepackageservice` (
  `idservicepackage` int NOT NULL,
  `idservice` int NOT NULL,
  PRIMARY KEY (`idservicepackage`,`idservice`),
  KEY `FK_ServicePackageService2_idx` (`idservice`),
  CONSTRAINT `FK_ServicePackageService` FOREIGN KEY (`idservicepackage`) REFERENCES `servicepackage` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_ServicePackageService2` FOREIGN KEY (`idservice`) REFERENCES `service` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `servicepackageservice`
--

LOCK TABLES `servicepackageservice` WRITE;
/*!40000 ALTER TABLE `servicepackageservice` DISABLE KEYS */;
/*!40000 ALTER TABLE `servicepackageservice` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `serviceschedule`
--

DROP TABLE IF EXISTS `serviceschedule`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `serviceschedule` (
  `idactivation` int NOT NULL,
  `idservice` int NOT NULL,
  PRIMARY KEY (`idactivation`,`idservice`),
  KEY `FK_ServiceActivation_idx` (`idservice`),
  KEY `FK_ActivationS_idx` (`idactivation`),
  CONSTRAINT `FK_ActivationS` FOREIGN KEY (`idactivation`) REFERENCES `activationschedule` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_ServiceActivation` FOREIGN KEY (`idservice`) REFERENCES `service` (`id`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `serviceschedule`
--

LOCK TABLES `serviceschedule` WRITE;
/*!40000 ALTER TABLE `serviceschedule` DISABLE KEYS */;
/*!40000 ALTER TABLE `serviceschedule` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `servicetype`
--

DROP TABLE IF EXISTS `servicetype`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `servicetype` (
  `id` int NOT NULL AUTO_INCREMENT,
  `type` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `servicetype`
--

LOCK TABLES `servicetype` WRITE;
/*!40000 ALTER TABLE `servicetype` DISABLE KEYS */;
INSERT INTO `servicetype` VALUES (1,'Fixed Phone'),(2,'Mobile Phone'),(3,'Fixed Internet'),(4,'Mobile Internet');
/*!40000 ALTER TABLE `servicetype` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `test`
--

DROP TABLE IF EXISTS `test`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `test` (
  `id` int NOT NULL AUTO_INCREMENT,
  `testValue` varchar(45) NOT NULL DEFAULT 'Failed',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `test`
--

LOCK TABLES `test` WRITE;
/*!40000 ALTER TABLE `test` DISABLE KEYS */;
/*!40000 ALTER TABLE `test` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `mail` varchar(45) NOT NULL,
  `idusertype` int NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username_UNIQUE` (`username`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  UNIQUE KEY `mail_UNIQUE` (`mail`),
  KEY `FK_UserType_idx` (`idusertype`),
  CONSTRAINT `FK_UserType` FOREIGN KEY (`idusertype`) REFERENCES `usertype` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usertype`
--

DROP TABLE IF EXISTS `usertype`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usertype` (
  `id` int NOT NULL AUTO_INCREMENT,
  `usertype` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  UNIQUE KEY `usertype_UNIQUE` (`usertype`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usertype`
--

LOCK TABLES `usertype` WRITE;
/*!40000 ALTER TABLE `usertype` DISABLE KEYS */;
INSERT INTO `usertype` VALUES (1,'Customer'),(2,'Employee');
/*!40000 ALTER TABLE `usertype` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `validityperiod`
--

DROP TABLE IF EXISTS `validityperiod`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `validityperiod` (
  `id` int NOT NULL AUTO_INCREMENT,
  `validityperiod` int NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `validityperiod`
--

LOCK TABLES `validityperiod` WRITE;
/*!40000 ALTER TABLE `validityperiod` DISABLE KEYS */;
INSERT INTO `validityperiod` VALUES (5,12),(6,24),(7,36),(8,48);
/*!40000 ALTER TABLE `validityperiod` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'db2_savino_vinati'
--

--
-- Dumping routines for database 'db2_savino_vinati'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-12-28 15:52:59
