-- MySQL dump 10.13  Distrib 8.0.43, for Win64 (x86_64)
--
-- Host: localhost    Database: university
-- ------------------------------------------------------
-- Server version	8.0.43

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
-- Table structure for table `books`
--

DROP TABLE IF EXISTS `books`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `books` (
  `id_book` int NOT NULL AUTO_INCREMENT,
  `title` varchar(255) NOT NULL,
  `author` varchar(255) DEFAULT NULL,
  `publication_year` year DEFAULT NULL,
  `category` varchar(100) DEFAULT NULL,
  `isbn` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id_book`),
  UNIQUE KEY `isbn` (`isbn`)
) ENGINE=InnoDB AUTO_INCREMENT=1027 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `books`
--

LOCK TABLES `books` WRITE;
/*!40000 ALTER TABLE `books` DISABLE KEYS */;
INSERT INTO `books` VALUES (1000,'O Lamento da Serpente Marinha','Elara Vargas',1988,'Fantasia','978-85-7608-952-1'),(1001,'Crônicas do Sol Despedaçado','Rui Montenegro',2011,'Ficção Científica','978-65-8604-101-5'),(1002,'A Geometria dos Sonhos','Sofia Alencar',1995,'Realismo Mágico','978-85-7522-824-3'),(1003,'Memórias de uma Cidade Submersa','Diogo Caeiro',2021,'Aventura','978-65-5589-053-2'),(1004,'O Último Alquimista de Lisboa 2','Isabela Bastos 2',1974,'Fantasia Histórica 2','978-85-7827-023-7-2'),(1005,'Sussurros na Biblioteca de Cinzas','Caio Pereira',2004,'Mistério','978-85-3591-484-8'),(1006,'Princípios de Astrofísica Teórica','Dr. Arnaldo Quintela',2018,'Acadêmico','978-65-2500-111-9'),(1007,'O Jardineiro de Estrelas','Lia Faria',1999,'Fábula','978-85-9431-897-6'),(1008,'Ecos do Deserto de Sal','Mateus Barroso',2008,'Suspense','978-85-7442-011-0'),(1009,'A Bússola de Jade','Clara Neves',2015,'Aventura','978-85-7962-386-4'),(1020,'Testando 2','Testando 2',1945,'Testando','21231'),(1021,'AAAA','AAAA',1945,'AAAAA','23131'),(1024,'Testando 2','Eu mesmo',2025,'Como eu Amo Java','213123131231'),(1025,'Testando 3','Eu Mesmo Novamente',2024,'Joao','231889999'),(1026,'Testando','Testando 2',2025,'Testando','AA');
/*!40000 ALTER TABLE `books` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `copies`
--

DROP TABLE IF EXISTS `copies`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `copies` (
  `id_copy` int NOT NULL AUTO_INCREMENT,
  `id_book` int NOT NULL,
  `barcode` varchar(100) NOT NULL,
  `status` varchar(50) NOT NULL,
  `location_code` varchar(50) NOT NULL,
  PRIMARY KEY (`id_copy`),
  UNIQUE KEY `barcode` (`barcode`),
  KEY `id_book` (`id_book`),
  CONSTRAINT `copies_ibfk_1` FOREIGN KEY (`id_book`) REFERENCES `books` (`id_book`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `copies`
--

LOCK TABLES `copies` WRITE;
/*!40000 ALTER TABLE `copies` DISABLE KEYS */;
INSERT INTO `copies` VALUES (1,1004,'Testand33','AVAILABLE','TEST-01321'),(2,1004,'TesteBarcode2','AVAILABLE','TESTE-03'),(3,1020,'BARCODE_TESTE','BORROWED','HIST-02LEFT'),(4,1025,'Testando_Barcode_233','AVAILABLE','HIST-2L');
/*!40000 ALTER TABLE `copies` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fines`
--

DROP TABLE IF EXISTS `fines`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `fines` (
  `id_fine` int NOT NULL AUTO_INCREMENT,
  `id_loan` int NOT NULL,
  `id_user` int NOT NULL,
  `amount` decimal(10,0) NOT NULL,
  `issue_date` date NOT NULL,
  `payment_date` date DEFAULT NULL,
  PRIMARY KEY (`id_fine`),
  UNIQUE KEY `id_loan` (`id_loan`),
  KEY `id_user` (`id_user`),
  CONSTRAINT `fines_ibfk_1` FOREIGN KEY (`id_loan`) REFERENCES `loans` (`id_loan`),
  CONSTRAINT `fines_ibfk_2` FOREIGN KEY (`id_user`) REFERENCES `users` (`id_user`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fines`
--

LOCK TABLES `fines` WRITE;
/*!40000 ALTER TABLE `fines` DISABLE KEYS */;
INSERT INTO `fines` VALUES (1,4,4,3,'2025-10-27','2025-10-27'),(2,5,4,3,'2025-10-27','2025-10-27'),(3,6,4,3,'2025-10-27','2025-10-27');
/*!40000 ALTER TABLE `fines` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `loans`
--

DROP TABLE IF EXISTS `loans`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `loans` (
  `id_loan` int NOT NULL AUTO_INCREMENT,
  `id_user` int NOT NULL,
  `id_copy` int NOT NULL,
  `loan_date` date NOT NULL,
  `expected_return_date` date NOT NULL,
  `actual_return_date` date DEFAULT NULL,
  PRIMARY KEY (`id_loan`),
  KEY `id_user` (`id_user`),
  KEY `id_copy` (`id_copy`),
  CONSTRAINT `loans_ibfk_1` FOREIGN KEY (`id_user`) REFERENCES `users` (`id_user`),
  CONSTRAINT `loans_ibfk_2` FOREIGN KEY (`id_copy`) REFERENCES `copies` (`id_copy`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `loans`
--

LOCK TABLES `loans` WRITE;
/*!40000 ALTER TABLE `loans` DISABLE KEYS */;
INSERT INTO `loans` VALUES (1,4,1,'2025-10-27','2025-11-11','2025-10-28'),(2,4,1,'2025-10-27','2025-11-11','2025-11-12'),(3,4,1,'2025-10-27','2025-11-11','2025-11-12'),(4,4,1,'2025-10-27','2025-11-11','2025-11-12'),(5,4,1,'2025-10-27','2025-11-11','2025-11-12'),(6,4,1,'2025-10-27','2025-11-11','2025-11-12');
/*!40000 ALTER TABLE `loans` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id_user` int NOT NULL AUTO_INCREMENT,
  `user_type` varchar(50) NOT NULL,
  `name` varchar(255) NOT NULL,
  `registration` varchar(50) NOT NULL,
  `email` varchar(255) NOT NULL,
  `password` varchar(72) NOT NULL,
  PRIMARY KEY (`id_user`),
  UNIQUE KEY `registration` (`registration`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'CLERK','Roberto','clerk01','clerk@gmail.com','$2y$12$O/UiH7IjhL9wf4WqdPoY6enu/zRBOwppcTIVEAa4.6veOpSr10g1G'),(3,'CLERK','Roberto2','clerk012','clerk@gmail.com2','$2b$12$p581B9LUxsVsAir03G2Hm.XuGtgFDR9Ck9LyVaTAvRBOwbdiDcWXC'),(4,'STUDENT','Joao','2410123','estudante@gmail.com','$2b$12$qNYlUMPJOWSWL3fA.H.DY.2QDbSybhdnrY/BHHSE0o5hqfVYvhL6y'),(5,'LIBRARIAN','Margarida','librarian01','librarian@gmail.com','$2b$12$c3rUdPEMdFsIStLCMEU5zu9zXc3CNqpX5a5XDSsC0cmdJNxlb9/3G');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-10-27 21:34:51
