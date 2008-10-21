/*
SQLyog - Free MySQL GUI v5.11
Host - 5.0.45-community-nt : Database - test1
*********************************************************************
Server version : 5.0.45-community-nt
*/

SET NAMES utf8;

SET SQL_MODE='';

SET FOREIGN_KEY_CHECKS = 0;

/*Table structure for table `association` */

DROP TABLE IF EXISTS `association`;

CREATE TABLE `association` (
  `ASSOCIATION_ID` bigint(20) NOT NULL,
  `ASSOCIATION_TYPE` int(8) NOT NULL,
  PRIMARY KEY  (`ASSOCIATION_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `association` */

insert into `association` (`ASSOCIATION_ID`,`ASSOCIATION_TYPE`) values (1,2),(2,2),(3,2),(4,2),(5,2),(6,2),(7,2),(8,2),(9,2),(10,2),(11,2),(12,2),(13,2),(14,2),(15,2),(16,2);

/*Table structure for table `categorial_attribute` */

DROP TABLE IF EXISTS `categorial_attribute`;

CREATE TABLE `categorial_attribute` (
  `ID` bigint(20) NOT NULL auto_increment,
  `CATEGORIAL_CLASS_ID` bigint(20) default NULL,
  `DE_CATEGORY_ATTRIBUTE_ID` bigint(20) default NULL,
  `DE_SOURCE_CLASS_ATTRIBUTE_ID` bigint(20) default NULL,
  PRIMARY KEY  (`ID`),
  KEY `FK31F77B56E8CBD948` (`CATEGORIAL_CLASS_ID`),
  CONSTRAINT `FK31F77B56E8CBD948` FOREIGN KEY (`CATEGORIAL_CLASS_ID`) REFERENCES `categorial_class` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `categorial_attribute` */

/*Table structure for table `categorial_class` */

DROP TABLE IF EXISTS `categorial_class`;

CREATE TABLE `categorial_class` (
  `ID` bigint(20) NOT NULL auto_increment,
  `DE_ENTITY_ID` bigint(20) default NULL,
  `PATH_FROM_PARENT_ID` bigint(20) default NULL,
  `PARENT_CATEGORIAL_CLASS_ID` bigint(20) default NULL,
  PRIMARY KEY  (`ID`),
  KEY `FK9651EF32D8D56A33` (`PARENT_CATEGORIAL_CLASS_ID`),
  CONSTRAINT `FK9651EF32D8D56A33` FOREIGN KEY (`PARENT_CATEGORIAL_CLASS_ID`) REFERENCES `categorial_class` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `categorial_class` */

/*Table structure for table `category` */

DROP TABLE IF EXISTS `category`;

CREATE TABLE `category` (
  `ID` bigint(20) NOT NULL auto_increment,
  `DE_ENTITY_ID` bigint(20) default NULL,
  `PARENT_CATEGORY_ID` bigint(20) default NULL,
  `ROOT_CATEGORIAL_CLASS_ID` bigint(20) default NULL,
  PRIMARY KEY  (`ID`),
  UNIQUE KEY `ROOT_CATEGORIAL_CLASS_ID` (`ROOT_CATEGORIAL_CLASS_ID`),
  KEY `FK31A8ACFE2D0F63E7` (`PARENT_CATEGORY_ID`),
  KEY `FK31A8ACFE211D9A6B` (`ROOT_CATEGORIAL_CLASS_ID`),
  CONSTRAINT `FK31A8ACFE211D9A6B` FOREIGN KEY (`ROOT_CATEGORIAL_CLASS_ID`) REFERENCES `categorial_class` (`ID`),
  CONSTRAINT `FK31A8ACFE2D0F63E7` FOREIGN KEY (`PARENT_CATEGORY_ID`) REFERENCES `category` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `category` */

/*Table structure for table `commons_graph` */

DROP TABLE IF EXISTS `commons_graph`;

CREATE TABLE `commons_graph` (
  `IDENTIFIER` bigint(20) NOT NULL auto_increment,
  PRIMARY KEY  (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `commons_graph` */

/*Table structure for table `commons_graph_edge` */

DROP TABLE IF EXISTS `commons_graph_edge`;

CREATE TABLE `commons_graph_edge` (
  `IDENTIFIER` bigint(20) NOT NULL auto_increment,
  `SOURCE_VERTEX_CLASS` varchar(255) default NULL,
  `SOURCE_VERTEX_ID` bigint(20) default NULL,
  `TARGET_VERTEX_CLASS` varchar(255) default NULL,
  `TARGET_VERTEX_ID` bigint(20) default NULL,
  `EDGE_CLASS` varchar(255) default NULL,
  `EDGE_ID` bigint(20) default NULL,
  PRIMARY KEY  (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `commons_graph_edge` */

/*Table structure for table `commons_graph_to_edges` */

DROP TABLE IF EXISTS `commons_graph_to_edges`;

CREATE TABLE `commons_graph_to_edges` (
  `GRAPH_ID` bigint(20) NOT NULL,
  `EDGE_ID` bigint(20) NOT NULL,
  PRIMARY KEY  (`GRAPH_ID`,`EDGE_ID`),
  UNIQUE KEY `EDGE_ID` (`EDGE_ID`),
  KEY `FKA6B0D8BAA0494B1D` (`GRAPH_ID`),
  KEY `FKA6B0D8BAFAEF80D` (`EDGE_ID`),
  CONSTRAINT `FKA6B0D8BAFAEF80D` FOREIGN KEY (`EDGE_ID`) REFERENCES `commons_graph_edge` (`IDENTIFIER`),
  CONSTRAINT `FKA6B0D8BAA0494B1D` FOREIGN KEY (`GRAPH_ID`) REFERENCES `commons_graph` (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `commons_graph_to_edges` */

/*Table structure for table `commons_graph_to_vertices` */

DROP TABLE IF EXISTS `commons_graph_to_vertices`;

CREATE TABLE `commons_graph_to_vertices` (
  `GRAPH_ID` bigint(20) NOT NULL,
  `VERTEX_CLASS` varchar(255) default NULL,
  `VERTEX_ID` bigint(20) default NULL,
  KEY `FK2C4412F5A0494B1D` (`GRAPH_ID`),
  CONSTRAINT `FK2C4412F5A0494B1D` FOREIGN KEY (`GRAPH_ID`) REFERENCES `commons_graph` (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `commons_graph_to_vertices` */

/*Table structure for table `csm_application` */

DROP TABLE IF EXISTS `csm_application`;

CREATE TABLE `csm_application` (
  `APPLICATION_ID` bigint(20) NOT NULL auto_increment,
  `APPLICATION_NAME` varchar(255) NOT NULL default '',
  `APPLICATION_DESCRIPTION` varchar(200) NOT NULL default '',
  `DECLARATIVE_FLAG` tinyint(1) default NULL,
  `ACTIVE_FLAG` tinyint(1) NOT NULL default '0',
  `UPDATE_DATE` date NOT NULL default '0000-00-00',
  `DATABASE_URL` varchar(100) default NULL,
  `DATABASE_USER_NAME` varchar(100) default NULL,
  `DATABASE_PASSWORD` varchar(100) default NULL,
  `DATABASE_DIALECT` varchar(100) default NULL,
  `DATABASE_DRIVER` varchar(100) default NULL,
  PRIMARY KEY  (`APPLICATION_ID`),
  UNIQUE KEY `UQ_APPLICATION_NAME` (`APPLICATION_NAME`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

/*Data for the table `csm_application` */

insert into `csm_application` (`APPLICATION_ID`,`APPLICATION_NAME`,`APPLICATION_DESCRIPTION`,`DECLARATIVE_FLAG`,`ACTIVE_FLAG`,`UPDATE_DATE`,`DATABASE_URL`,`DATABASE_USER_NAME`,`DATABASE_PASSWORD`,`DATABASE_DIALECT`,`DATABASE_DRIVER`) values (1,'catissuecore','caTISSUE Core',0,0,'2005-08-22',NULL,NULL,NULL,NULL,NULL);

/*Table structure for table `csm_group` */

DROP TABLE IF EXISTS `csm_group`;

CREATE TABLE `csm_group` (
  `GROUP_ID` bigint(20) NOT NULL auto_increment,
  `GROUP_NAME` varchar(255) NOT NULL default '',
  `GROUP_DESC` varchar(200) default NULL,
  `UPDATE_DATE` date NOT NULL default '2005-01-01',
  `APPLICATION_ID` bigint(20) NOT NULL default '0',
  PRIMARY KEY  (`GROUP_ID`),
  UNIQUE KEY `UQ_GROUP_GROUP_NAME` (`APPLICATION_ID`,`GROUP_NAME`),
  KEY `idx_APPLICATION_ID` (`APPLICATION_ID`),
  CONSTRAINT `csm_group_ibfk_1` FOREIGN KEY (`APPLICATION_ID`) REFERENCES `csm_application` (`APPLICATION_ID`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

/*Data for the table `csm_group` */

insert into `csm_group` (`GROUP_ID`,`GROUP_NAME`,`GROUP_DESC`,`UPDATE_DATE`,`APPLICATION_ID`) values (1,'ADMINISTRATOR_GROUP','Group of Administrators','2005-01-01',1),(2,'SUPERVISOR_GROUP','Group of Supervisors','2005-01-01',1),(3,'TECHNICIAN_GROUP','Group of Technicians','2005-01-01',1),(4,'PUBLIC_GROUP','Group of Public Users','2005-01-01',1);

/*Table structure for table `csm_pg_pe` */

DROP TABLE IF EXISTS `csm_pg_pe`;

CREATE TABLE `csm_pg_pe` (
  `PG_PE_ID` bigint(20) NOT NULL auto_increment,
  `PROTECTION_GROUP_ID` bigint(20) NOT NULL default '0',
  `PROTECTION_ELEMENT_ID` bigint(20) NOT NULL default '0',
  `UPDATE_DATE` date default NULL,
  PRIMARY KEY  (`PG_PE_ID`),
  UNIQUE KEY `UQ_PROTECTION_GROUP_PROTECTION_ELEMENT_PROTECTION_GROUP_ID` (`PROTECTION_ELEMENT_ID`,`PROTECTION_GROUP_ID`),
  KEY `idx_PROTECTION_ELEMENT_ID` (`PROTECTION_ELEMENT_ID`),
  KEY `idx_PROTECTION_GROUP_ID` (`PROTECTION_GROUP_ID`),
  CONSTRAINT `csm_pg_pe_ibfk_1` FOREIGN KEY (`PROTECTION_ELEMENT_ID`) REFERENCES `csm_protection_element` (`PROTECTION_ELEMENT_ID`) ON DELETE CASCADE,
  CONSTRAINT `csm_pg_pe_ibfk_2` FOREIGN KEY (`PROTECTION_GROUP_ID`) REFERENCES `csm_protection_group` (`PROTECTION_GROUP_ID`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=410 DEFAULT CHARSET=latin1;

/*Data for the table `csm_pg_pe` */

insert into `csm_pg_pe` (`PG_PE_ID`,`PROTECTION_GROUP_ID`,`PROTECTION_ELEMENT_ID`,`UPDATE_DATE`) values (16,21,9,NULL),(17,2,10,NULL),(19,2,11,NULL),(20,2,12,NULL),(21,2,13,NULL),(22,2,14,NULL),(23,3,15,NULL),(24,3,16,NULL),(25,3,17,NULL),(26,3,18,NULL),(27,3,19,NULL),(28,1,20,NULL),(29,3,21,NULL),(30,3,22,NULL),(31,3,23,NULL),(32,3,24,NULL),(33,3,25,NULL),(34,3,26,NULL),(35,3,27,NULL),(36,3,28,NULL),(37,3,29,NULL),(38,3,30,NULL),(39,3,31,NULL),(40,3,32,NULL),(41,3,33,NULL),(42,3,34,NULL),(43,3,35,NULL),(44,3,36,NULL),(45,3,37,NULL),(46,3,38,NULL),(47,3,39,NULL),(48,3,40,NULL),(49,1,41,NULL),(50,1,42,NULL),(51,1,43,NULL),(52,1,44,NULL),(53,1,45,NULL),(54,1,46,NULL),(55,1,47,NULL),(56,1,48,NULL),(58,1,50,NULL),(59,1,51,NULL),(60,3,52,NULL),(61,3,53,NULL),(299,1,269,NULL),(302,1,272,NULL),(306,1,276,NULL),(312,3,281,NULL),(330,1,54,NULL),(331,1,55,NULL),(332,1,56,NULL),(335,44,304,'2007-01-04'),(336,44,305,'2007-01-04'),(337,44,306,'2007-01-04'),(338,44,307,'2007-01-04'),(339,44,308,'2007-01-04'),(340,44,309,'2007-01-04'),(341,44,310,'2007-01-04'),(342,44,311,'2007-01-04'),(343,44,312,'2007-01-04'),(344,44,313,'2007-01-04'),(348,1,304,'2006-11-27'),(349,2,304,'2006-11-27'),(350,3,304,'2006-11-27'),(351,1,305,'2006-11-27'),(352,2,305,'2006-11-27'),(353,3,305,'2006-11-27'),(354,1,306,'2006-11-27'),(355,2,306,'2006-11-27'),(356,3,306,'2006-11-27'),(357,1,307,'2006-11-27'),(358,2,307,'2006-11-27'),(359,3,307,'2006-11-27'),(360,1,308,'2006-11-27'),(361,2,308,'2006-11-27'),(362,3,308,'2006-11-27'),(363,1,309,'2006-11-27'),(364,2,309,'2006-11-27'),(365,3,309,'2006-11-27'),(366,1,310,'2006-11-27'),(367,2,310,'2006-11-27'),(368,3,310,'2006-11-27'),(369,1,311,'2006-11-27'),(370,2,311,'2006-11-27'),(371,3,311,'2006-11-27'),(372,1,312,'2006-11-27'),(373,2,312,'2006-11-27'),(374,3,312,'2006-11-27'),(375,1,313,'2006-11-27'),(376,2,313,'2006-11-27'),(377,3,313,'2006-11-27'),(378,1,300,'2006-11-27'),(379,2,300,'2006-11-27'),(380,3,300,'2006-11-27'),(381,1,301,'2006-11-27'),(382,2,301,'2006-11-27'),(383,3,301,'2006-11-27'),(384,1,302,'2006-11-27'),(385,2,302,'2006-11-27'),(386,3,302,'2006-11-27'),(387,3,303,'2007-01-18'),(388,1,315,'2006-11-27'),(389,1,316,'2006-11-27'),(390,1,317,'2006-11-27'),(391,1,318,'2006-11-27'),(392,1,319,'2006-11-27'),(399,1,326,'2008-05-28'),(400,1,327,'2008-05-28'),(401,1,328,'2008-05-28'),(402,1,329,'2008-05-28'),(403,1,330,'2008-05-28'),(404,1,331,'2008-05-28'),(405,1,332,'2008-05-28'),(406,1,333,'2008-05-28'),(407,1,334,'2008-05-28'),(408,1,335,'2008-05-28'),(409,1,336,'2008-07-22');

/*Table structure for table `csm_privilege` */

DROP TABLE IF EXISTS `csm_privilege`;

CREATE TABLE `csm_privilege` (
  `PRIVILEGE_ID` bigint(20) NOT NULL auto_increment,
  `PRIVILEGE_NAME` varchar(100) NOT NULL default '',
  `PRIVILEGE_DESCRIPTION` varchar(200) default NULL,
  `UPDATE_DATE` date NOT NULL default '0000-00-00',
  PRIMARY KEY  (`PRIVILEGE_ID`),
  UNIQUE KEY `UQ_PRIVILEGE_NAME` (`PRIVILEGE_NAME`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=latin1;

/*Data for the table `csm_privilege` */

insert into `csm_privilege` (`PRIVILEGE_ID`,`PRIVILEGE_NAME`,`PRIVILEGE_DESCRIPTION`,`UPDATE_DATE`) values (1,'CREATE','This privilege grants permission to a user to create an entity. This entity can be an object, a database entry, or a resource such as a network connection','2005-08-22'),(2,'ACCESS','This privilege allows a user to access a particular resource.  Examples of resources include a network or database connection, socket, module of the application, or even the application itself','2005-08-22'),(3,'READ','This privilege permits the user to read data from a file, URL, database, an object, etc. This can be used at an entity level signifying that the user is allowed to read data about a particular entry','2005-08-22'),(4,'WRITE','This privilege allows a user to write data to a file, URL, database, an object, etc. This can be used at an entity level signifying that the user is allowed to write data about a particular entity','2005-08-22'),(5,'UPDATE','This privilege grants permission at an entity level and signifies that the user is allowed to update data for a particular entity. Entities may include an object, object attribute, database row etc','2005-08-22'),(6,'DELETE','This privilege permits a user to delete a logical entity. This entity can be an object, a database entry, a resource such as a network connection, etc','2005-08-22'),(7,'EXECUTE','This privilege allows a user to execute a particular resource. The resource can be a method, function, behavior of the application, URL, button etc','2005-08-22'),(8,'USE','This privilege allows a user to use a particular resource','2005-08-22'),(9,'ASSIGN_READ','This privilege allows a user to assign a read privilege to others','2005-08-22'),(10,'ASSIGN_USE','This privilege allows a user to assign a use privilege to others','2005-08-22'),(11,'IDENTIFIED_DATA_ACCESS','This privilege allows a user to view identified data of an object','2005-08-22'),(12,'READ_DENIED','This privilege doesnt permit the user to read data','2005-08-22'),(13,'USER_PROVISIONING','THIS PRIVILEGE GRANTS PERMISSION TO A USER FOR USER CREATION AND ASSIGNING PRIVILEGES TO THAT USER','2008-06-19'),(14,'REPOSITORY_ADMINISTRATION','THIS PRIVILEGE GRANTS PERMISSION TO A USER FOR ADD EDIT SITES','2008-06-19'),(15,'STORAGE_ADMINISTRATION','THIS PRIVILEGE GRANTS PERMISSION TO A USER FOR ADD EDIT STORAGE TYPES AND CONTAINERS','2008-06-19'),(16,'PROTOCOL_ADMINISTRATION','THIS PRIVILEGE GRANTS PERMISSION TO A USER FOR ADD EDIT COLLECTION AS WELL AS DISTRIBUTION PROTOCOLS','2008-06-19'),(17,'DEFINE_ANNOTATION','THIS PRIVILEGE GRANTS PERMISSION TO A USER FOR ADD EDIT ANNOTATIONS','2008-06-19'),(18,'REGISTRATION','THIS PRIVILEGE GRANTS PERMISSION TO A USER FOR REGISTERING PARTICIPANT AND CONSENTS','2008-06-19'),(20,'SPECIMEN_ACCESSION','THIS PRIVILEGE GRANTS PERMISSION TO A USER FOR ADD EDIT SPECIMEN,SPECIMEN COLLECTION GROUP AND CONSENTS','2008-06-19'),(21,'DISTRIBUTION','THIS PRIVILEGE GRANTS PERMISSION TO A USER FOR DISTRIBUTION','2008-06-19'),(22,'QUERY','THIS PRIVILEGE GRANTS PERMISSION TO A USER FOR QUERY','2008-06-19'),(23,'PHI_ACCESS','THIS PRIVILEGE GRANTS PERMISSION TO A USER FOR VIEWING PHI DATA','2008-06-19'),(24,'PARTICIPANT_SCG_ANNOTATION','THIS PRIVILEGE GRANTS PERMISSION TO A USER FOR ADD EDIT ANNOTATION TO PARTICIPANT OR SCG','2008-06-19'),(25,'SPECIMEN_ANNOTATION','THIS PRIVILEGE GRANTS PERMISSION TO A USER FOR FOR ADD EDIT SPECIMEN ANNOTATION','2008-06-19'),(26,'SPECIMEN_PROCESSING','THIS PRIVILEGE GRANTS PERMISSION TO A USER FOR ADD EDIT ALIQUOT, DERIVATIVE, EVENTS','2008-06-19'),(27,'SPECIMEN_STORAGE','THIS PRIVILEGE GRANTS PERMISSION TO A USER FOR ADD EDIT STORAGE AND TRANSFER EVENTS','2008-06-19'),(28,'GENERAL_SITE_ADMINISTRATION','THIS PRIVILEGE GRANTS PERMISSION TO A USER FOR ADD EDIT SITES','2008-06-19'),(29,'GENERAL_ADMINISTRATION','THIS PRIVILEGE GRANTS PERMISSION TO A USER FOR ADD EDIT DEPARTMENT,INSTITUTION,CANCER RESEARCH GROUP','2008-06-19'),(30,'SHIPMENT_PROCESSING','THIS PRIVILEGE GRANTS PERMISSION TO A USER TO PROCESS SHIPMENTS','2008-08-20');

/*Table structure for table `csm_protection_element` */

DROP TABLE IF EXISTS `csm_protection_element`;

CREATE TABLE `csm_protection_element` (
  `PROTECTION_ELEMENT_ID` bigint(20) NOT NULL auto_increment,
  `PROTECTION_ELEMENT_NAME` varchar(100) NOT NULL default '',
  `PROTECTION_ELEMENT_DESCRIPTION` varchar(200) default NULL,
  `OBJECT_ID` varchar(100) NOT NULL default '',
  `ATTRIBUTE` varchar(100) default NULL,
  `PROTECTION_ELEMENT_TYPE` varchar(100) default NULL,
  `APPLICATION_ID` bigint(20) NOT NULL default '0',
  `UPDATE_DATE` date NOT NULL default '2005-01-01',
  PRIMARY KEY  (`PROTECTION_ELEMENT_ID`),
  UNIQUE KEY `UQ_PE_PE_NAME_ATTRIBUTE_APP_ID` (`OBJECT_ID`,`ATTRIBUTE`,`APPLICATION_ID`),
  UNIQUE KEY `UQ_PE_OBJECT_ID_ATTRIBUTE_APP_ID` (`PROTECTION_ELEMENT_NAME`,`ATTRIBUTE`,`APPLICATION_ID`),
  KEY `idx_APPLICATION_ID` (`APPLICATION_ID`),
  CONSTRAINT `csm_protection_element_ibfk_1` FOREIGN KEY (`APPLICATION_ID`) REFERENCES `csm_application` (`APPLICATION_ID`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=337 DEFAULT CHARSET=latin1;

/*Data for the table `csm_protection_element` */

insert into `csm_protection_element` (`PROTECTION_ELEMENT_ID`,`PROTECTION_ELEMENT_NAME`,`PROTECTION_ELEMENT_DESCRIPTION`,`OBJECT_ID`,`ATTRIBUTE`,`PROTECTION_ELEMENT_TYPE`,`APPLICATION_ID`,`UPDATE_DATE`) values (9,'edu.wustl.catissuecore.domain.User_1','User class','edu.wustl.catissuecore.domain.User_1',NULL,NULL,1,'2005-01-01'),(10,'Participant','Participant class','edu.wustl.catissuecore.domain.Participant',NULL,NULL,1,'2005-01-01'),(11,'ParticipantMedicalIdentifier','ParticipantMedicalIdentifier class','edu.wustl.catissuecore.domain.ParticipantMedicalIdentifier',NULL,NULL,1,'2005-01-01'),(12,'ClinicalReport','ClinicalReport class','edu.wustl.catissuecore.domain.ClinicalReport',NULL,NULL,1,'2005-01-01'),(13,'SpecimenCollectionGroup','SpecimenCollectionGroup class','edu.wustl.catissuecore.domain.SpecimenCollectionGroup',NULL,NULL,1,'2005-01-01'),(14,'CollectionProtocolRegistration','CollectionProtocolRegistration class','edu.wustl.catissuecore.domain.CollectionProtocolRegistration',NULL,NULL,1,'2005-01-01'),(15,'SpecimenCharacteristics','SpecimenCharacteristics class','edu.wustl.catissuecore.domain.SpecimenCharacteristics',NULL,NULL,1,'2005-01-01'),(16,'FluidSpecimen','FluidSpecimen class','edu.wustl.catissuecore.domain.FluidSpecimen',NULL,NULL,1,'2005-01-01'),(17,'TissueSpecimen','TissueSpecimen class','edu.wustl.catissuecore.domain.TissueSpecimen',NULL,NULL,1,'2005-01-01'),(18,'CellSpecimen','CellSpecimen class','edu.wustl.catissuecore.domain.CellSpecimen',NULL,NULL,1,'2005-01-01'),(19,'MolecularSpecimen','MolecularSpecimen class','edu.wustl.catissuecore.domain.MolecularSpecimen',NULL,NULL,1,'2005-01-01'),(20,'Biohazard','Biohazard class','edu.wustl.catissuecore.domain.Biohazard',NULL,NULL,1,'2005-01-01'),(21,'Specimen','Specimen class','edu.wustl.catissuecore.domain.Specimen',NULL,NULL,1,'2005-01-01'),(22,'ExternalIdentifier','ExternalIdentifier class','edu.wustl.catissuecore.domain.ExternalIdentifier',NULL,NULL,1,'2005-01-01'),(23,'EventParameters','EventParameters class','edu.wustl.catissuecore.domain.EventParameters',NULL,NULL,1,'2005-01-01'),(24,'FluidSpecimenReviewEventParameters','FluidSpecimenReviewEventParameters class','edu.wustl.catissuecore.domain.FluidSpecimenReviewEventParameters',NULL,NULL,1,'2005-01-01'),(25,'CellSpecimenReviewParameters','CellSpecimenReviewParameters class','edu.wustl.catissuecore.domain.CellSpecimenReviewParameters',NULL,NULL,1,'2005-01-01'),(26,'TissueSpecimenReviewEventParameters','TissueSpecimenReviewEventParameters class','edu.wustl.catissuecore.domain.TissueSpecimenReviewEventParameters',NULL,NULL,1,'2005-01-01'),(27,'MolecularSpecimenReviewParameters','MolecularSpecimenReviewParameters class','edu.wustl.catissuecore.domain.MolecularSpecimenReviewParameters',NULL,NULL,1,'2005-01-01'),(28,'CheckInCheckOutEventParameter','CheckInCheckOutEventParameter class','edu.wustl.catissuecore.domain.CheckInCheckOutEventParameter',NULL,NULL,1,'2005-01-01'),(29,'FrozenEventParameters','FrozenEventParameters class','edu.wustl.catissuecore.domain.FrozenEventParameters',NULL,NULL,1,'2005-01-01'),(30,'EmbeddedEventParameters','EmbeddedEventParameters class','edu.wustl.catissuecore.domain.EmbeddedEventParameters',NULL,NULL,1,'2005-01-01'),(31,'ReviewEventParameters','ReviewEventParameters class','edu.wustl.catissuecore.domain.ReviewEventParameters',NULL,NULL,1,'2005-01-01'),(32,'SpunEventParameters','SpunEventParameters class','edu.wustl.catissuecore.domain.SpunEventParameters',NULL,NULL,1,'2005-01-01'),(33,'ThawEventParameters','ThawEventParameters class','edu.wustl.catissuecore.domain.ThawEventParameters',NULL,NULL,1,'2005-01-01'),(34,'SpecimenEventParameters','SpecimenEventParameters class','edu.wustl.catissuecore.domain.SpecimenEventParameters',NULL,NULL,1,'2005-01-01'),(35,'ReceivedEventParameters','ReceivedEventParameters class','edu.wustl.catissuecore.domain.ReceivedEventParameters',NULL,NULL,1,'2005-01-01'),(36,'DisposalEventParameters','DisposalEventParameters class','edu.wustl.catissuecore.domain.DisposalEventParameters',NULL,NULL,1,'2005-01-01'),(37,'FixedEventParameters','FixedEventParameters class','edu.wustl.catissuecore.domain.FixedEventParameters',NULL,NULL,1,'2005-01-01'),(38,'ProcedureEventParameters','ProcedureEventParameters class','edu.wustl.catissuecore.domain.ProcedureEventParameters',NULL,NULL,1,'2005-01-01'),(39,'TransferEventParameters','TransferEventParameters class','edu.wustl.catissuecore.domain.TransferEventParameters',NULL,NULL,1,'2005-01-01'),(40,'CollectionEventParameters','CollectionEventParameters class','edu.wustl.catissuecore.domain.CollectionEventParameters',NULL,NULL,1,'2005-01-01'),(41,'Site','Site class','edu.wustl.catissuecore.domain.Site',NULL,NULL,1,'2005-01-01'),(42,'StorageContainer','StorageContainer class','edu.wustl.catissuecore.domain.StorageContainer',NULL,NULL,1,'2005-01-01'),(43,'StorageContainerDetails','StorageContainerDetails class','edu.wustl.catissuecore.domain.StorageContainerDetails',NULL,NULL,1,'2005-01-01'),(44,'Capacity','Capacity class','edu.wustl.catissuecore.domain.Capacity',NULL,NULL,1,'2005-01-01'),(45,'StorageType','StorageType class','edu.wustl.catissuecore.domain.StorageType',NULL,NULL,1,'2005-01-01'),(46,'User','User class','edu.wustl.catissuecore.domain.User',NULL,NULL,1,'2005-01-01'),(47,'Address','Address class','edu.wustl.catissuecore.domain.Address',NULL,NULL,1,'2005-01-01'),(48,'CancerResearchGroup','CancerResearchGroup class','edu.wustl.catissuecore.domain.CancerResearchGroup',NULL,NULL,1,'2005-01-01'),(50,'Department','Department class','edu.wustl.catissuecore.domain.Department',NULL,NULL,1,'2005-01-01'),(51,'Institution','Institution class','edu.wustl.catissuecore.domain.Institution',NULL,NULL,1,'2005-01-01'),(52,'Distribution','Distribution class','edu.wustl.catissuecore.domain.Distribution',NULL,NULL,1,'2005-01-01'),(53,'DistributedItem','DistributedItem class','edu.wustl.catissuecore.domain.DistributedItem',NULL,NULL,1,'2005-01-01'),(54,'CollectionProtocolEvent','CollectionProtocolEvent class','edu.wustl.catissuecore.domain.CollectionProtocolEvent',NULL,NULL,1,'2005-01-01'),(55,'CollectionProtocol','CollectionProtocol class','edu.wustl.catissuecore.domain.CollectionProtocol',NULL,NULL,1,'2005-01-01'),(56,'DistributionProtocol','DistributionProtocol class','edu.wustl.catissuecore.domain.DistributionProtocol',NULL,NULL,1,'2005-01-01'),(57,'SpecimenProtocol','SpecimenProtocol class','edu.wustl.catissuecore.domain.SpecimenProtocol',NULL,NULL,1,'2005-01-01'),(269,'edu.wustl.catissuecore.domain.ReportedProblem','edu.wustl.catissuecore.domain.ReportedProblem','edu.wustl.catissuecore.domain.ReportedProblem',NULL,NULL,1,'2005-08-31'),(272,'edu.wustl.catissuecore.domain.SignUpUser','edu.wustl.catissuecore.domain.SignUpUser','edu.wustl.catissuecore.domain.SignUpUser',NULL,NULL,1,'2005-08-31'),(276,'SpecimenArrayType','SpecimenArrayType Class','edu.wustl.catissuecore.domain.SpecimenArrayType',NULL,NULL,1,'2006-08-31'),(281,'SpecimenArray','SpecimenArray Class','edu.wustl.catissuecore.domain.SpecimenArray',NULL,NULL,1,'2006-08-31'),(294,'Local Extensions','Local Extensions class','edu.common.dynamicextensions.domain.integration.EntityMap',NULL,NULL,1,'2007-01-17'),(300,'Consent Tier','ConsentTier Object','edu.wustl.catissuecore.domain.ConsentTier',NULL,NULL,1,'2006-11-27'),(301,'Consent Tier Response','Consent Tier Response Object','edu.wustl.catissuecore.domain.ConsentTierResponse',NULL,NULL,1,'2006-11-27'),(302,'Consent Tier Status','Consent Tier Status Object','edu.wustl.catissuecore.domain.ConsentTierStatus',NULL,NULL,1,'2006-11-27'),(303,'ReturnEventParameters','ReturnEventParameters Class','edu.wustl.catissuecore.domain.ReturnEventParameters',NULL,NULL,1,'2007-01-17'),(304,'Order','Order Object','edu.wustl.catissuecore.domain.OrderDetails',NULL,NULL,1,'2006-11-27'),(305,'OrderItem','OrderItem Object','edu.wustl.catissuecore.domain.OrderItem',NULL,NULL,1,'2006-11-27'),(306,'Derived Specimen Order Item','Derived Specimen Order Item Object','edu.wustl.catissuecore.domain.DerivedSpecimenOrderItem',NULL,NULL,1,'2006-11-27'),(307,'Existing Specimen Array Order Item','Existing Specimen Array Order Item Object','edu.wustl.catissuecore.domain.ExistingSpecimenArrayOrderItem',NULL,NULL,1,'2006-11-27'),(308,'Existing Specimen Order Item','Existing Specimen Order Item Object','edu.wustl.catissuecore.domain.ExistingSpecimenOrderItem',NULL,NULL,1,'2006-11-27'),(309,'New Specimen Array Order Item','New Specimen Array Order Item Object','edu.wustl.catissuecore.domain.NewSpecimenArrayOrderItem',NULL,NULL,1,'2006-11-27'),(310,'New Specimen Order Item','New Specimen Order Item Object','edu.wustl.catissuecore.domain.NewSpecimenOrderItem',NULL,NULL,1,'2006-11-27'),(311,'Pathological Case Order Item','Pathological Case Order Item Object','edu.wustl.catissuecore.domain.PathologicalCaseOrderItem',NULL,NULL,1,'2006-11-27'),(312,'Specimen Array Order Item','Specimen Array Order Item Object','edu.wustl.catissuecore.domain.SpecimenArrayOrderItem',NULL,NULL,1,'2006-11-27'),(313,'Specimen Order Item','Specimen Order Item Object','edu.wustl.catissuecore.domain.SpecimenOrderItem',NULL,NULL,1,'2006-11-27'),(315,'IdentifiedSurgicalPathologyReport','IdentifiedSurgicalPathologyReport Object','edu.wustl.catissuecore.domain.pathology.IdentifiedSurgicalPathologyReport',NULL,NULL,1,'2006-11-27'),(316,'DeidentifiedSurgicalPathologyReport','DeidentifiedSurgicalPathologyReport Object','edu.wustl.catissuecore.domain.pathology.DeidentifiedSurgicalPathologyReport',NULL,NULL,1,'2006-11-27'),(317,'ReportLoaderQueue','ReportLoaderQueue Object','edu.wustl.catissuecore.domain.pathology.ReportLoaderQueue',NULL,NULL,1,'2006-11-27'),(318,'Review Comments','PathologyReportReviewParameter Object','edu.wustl.catissuecore.domain.pathology.PathologyReportReviewParameter',NULL,NULL,1,'2006-11-27'),(319,'Quarantine Comments','QuarantineEventParameter Object','edu.wustl.catissuecore.domain.pathology.QuarantineEventParameter',NULL,NULL,1,'2006-11-27'),(326,'AbstractPosition','AbstractPosition Object','edu.wustl.catissuecore.domain.AbstractPosition',NULL,NULL,1,'2008-05-28'),(327,'SpecimenPosition','SpecimenPosition Object','edu.wustl.catissuecore.domain.SpecimenPosition',NULL,NULL,1,'2008-05-28'),(328,'ContainerPosition','ContainerPosition Object','edu.wustl.catissuecore.domain.ContainerPosition',NULL,NULL,1,'2008-05-28'),(329,'AbstractSpecimen','AbstractSpecimen Object','edu.wustl.catissuecore.domain.AbstractSpecimen',NULL,NULL,1,'2008-05-28'),(330,'SpecimenRequirement','SpecimenRequirement Object','edu.wustl.catissuecore.domain.SpecimenRequirement',NULL,NULL,1,'2008-05-28'),(331,'MolecularSpecimenRequirement','MolecularSpecimenRequirement Object','edu.wustl.catissuecore.domain.MolecularSpecimenRequirement',NULL,NULL,1,'2008-05-28'),(332,'FluidSpecimenRequirement','FluidSpecimenRequirement Object','edu.wustl.catissuecore.domain.FluidSpecimenRequirement',NULL,NULL,1,'2008-05-28'),(333,'CellSpecimenRequirement','CellSpecimenRequirement Object','edu.wustl.catissuecore.domain.CellSpecimenRequirement',NULL,NULL,1,'2008-05-28'),(334,'TissueSpecimenRequirement','TissueSpecimenRequirement Object','edu.wustl.catissuecore.domain.TissueSpecimenRequirement',NULL,NULL,1,'2008-05-28'),(335,'DistributionSpecimenRequirement','DistributionSpecimenRequirement Object','edu.wustl.catissuecore.domain.DistributionSpecimenRequirement',NULL,NULL,1,'2008-05-28'),(336,'ADMIN_PROTECTION_ELEMENT','ADMIN_PROTECTION_ELEMENT Object','ADMIN_PROTECTION_ELEMENT',NULL,NULL,1,'2008-07-22');

/*Table structure for table `csm_protection_group` */

DROP TABLE IF EXISTS `csm_protection_group`;

CREATE TABLE `csm_protection_group` (
  `PROTECTION_GROUP_ID` bigint(20) NOT NULL auto_increment,
  `PROTECTION_GROUP_NAME` varchar(100) NOT NULL default '',
  `PROTECTION_GROUP_DESCRIPTION` varchar(200) default NULL,
  `APPLICATION_ID` bigint(20) NOT NULL default '0',
  `LARGE_ELEMENT_COUNT_FLAG` tinyint(1) NOT NULL default '0',
  `UPDATE_DATE` date NOT NULL default '2005-01-01',
  `PARENT_PROTECTION_GROUP_ID` bigint(20) default NULL,
  PRIMARY KEY  (`PROTECTION_GROUP_ID`),
  UNIQUE KEY `UQ_PROTECTION_GROUP_PROTECTION_GROUP_NAME` (`APPLICATION_ID`,`PROTECTION_GROUP_NAME`),
  KEY `idx_APPLICATION_ID` (`APPLICATION_ID`),
  KEY `idx_PARENT_PROTECTION_GROUP_ID` (`PARENT_PROTECTION_GROUP_ID`),
  CONSTRAINT `csm_protection_group_ibfk_1` FOREIGN KEY (`APPLICATION_ID`) REFERENCES `csm_application` (`APPLICATION_ID`) ON DELETE CASCADE,
  CONSTRAINT `csm_protection_group_ibfk_2` FOREIGN KEY (`PARENT_PROTECTION_GROUP_ID`) REFERENCES `csm_protection_group` (`PROTECTION_GROUP_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=latin1;

/*Data for the table `csm_protection_group` */

insert into `csm_protection_group` (`PROTECTION_GROUP_ID`,`PROTECTION_GROUP_NAME`,`PROTECTION_GROUP_DESCRIPTION`,`APPLICATION_ID`,`LARGE_ELEMENT_COUNT_FLAG`,`UPDATE_DATE`,`PARENT_PROTECTION_GROUP_ID`) values (1,'ADMINISTRATOR_PROTECTION_GROUP','Protection elements for class names of objects that belong to Administrative data',1,0,'2005-01-01',NULL),(2,'SUPERVISOR_PROTECTION_GROUP','Protection elements for class names of objects that belong to Supervisor\'s data',1,0,'2005-01-01',NULL),(3,'TECHNICIAN_PROTECTION_GROUP','Protection elements for class names of objects that belong to Technician data',1,0,'2005-01-01',NULL),(17,'SECURED_ADMINISTRATIVE_ACTIONS',NULL,1,0,'2005-01-01',NULL),(18,'SECURED_SUPERVISORY_ACTIONS',NULL,1,0,'2005-01-01',NULL),(19,'SECURED_TECHNICIAN_ACTIONS',NULL,1,0,'2005-01-01',NULL),(20,'PUBLIC_DATA_GROUP',NULL,1,0,'2005-01-01',NULL),(21,'ADMINISTRATORS_DATA_GROUP',NULL,1,0,'2005-01-01',NULL),(22,'SUPERVISORS_DATA_GROUP',NULL,1,0,'2005-01-01',NULL),(23,'TECHNICIANS_DATA_GROUP',NULL,1,0,'2005-01-01',NULL),(24,'SECURED_PUBLIC_ACTIONS',NULL,1,0,'2005-01-01',NULL),(44,'SCIENTIST_PROTECTION_GROUP',NULL,1,0,'2005-01-01',NULL);

/*Table structure for table `csm_role` */

DROP TABLE IF EXISTS `csm_role`;

CREATE TABLE `csm_role` (
  `ROLE_ID` bigint(20) NOT NULL auto_increment,
  `ROLE_NAME` varchar(100) NOT NULL default '',
  `ROLE_DESCRIPTION` varchar(200) default NULL,
  `APPLICATION_ID` bigint(20) NOT NULL default '0',
  `ACTIVE_FLAG` tinyint(1) NOT NULL default '0',
  `UPDATE_DATE` date NOT NULL default '2005-01-01',
  PRIMARY KEY  (`ROLE_ID`),
  UNIQUE KEY `UQ_ROLE_ROLE_NAME` (`APPLICATION_ID`,`ROLE_NAME`),
  KEY `idx_APPLICATION_ID` (`APPLICATION_ID`),
  CONSTRAINT `csm_role_ibfk_1` FOREIGN KEY (`APPLICATION_ID`) REFERENCES `csm_application` (`APPLICATION_ID`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=latin1;

/*Data for the table `csm_role` */

insert into `csm_role` (`ROLE_ID`,`ROLE_NAME`,`ROLE_DESCRIPTION`,`APPLICATION_ID`,`ACTIVE_FLAG`,`UPDATE_DATE`) values (1,'Administrator','Role for Administrator',1,0,'2005-01-01'),(2,'Supervisor','Role for Supervisor',1,0,'2005-01-01'),(3,'Technician','Role for Technician',1,0,'2005-01-01'),(4,'PI','Role for Principal Investigator',1,0,'2005-01-01'),(5,'READ_ONLY','Read Only Role',1,0,'2005-01-01'),(6,'USE_ONLY','Use Only Role',1,0,'2005-01-01'),(7,'Scientist','Role for Public',1,0,'2005-01-01'),(8,'UPDATE_ONLY','Update Only Role',1,0,'2005-01-01'),(9,'EXECUTE_ONLY','Execute Only Role',1,0,'2005-01-01'),(10,'READ_DENIED','Read Denied Role',1,0,'2005-01-01'),(11,'Coordinator','Role for Coordinator',1,0,'2005-01-01'),(12,'CREATE_ONLY','Create only role',1,0,'2005-01-01'),(13,'SUPERADMINISTRATOR','SUPER ADMINISTRATOR ROLE',1,0,'2008-06-19');

/*Table structure for table `csm_role_privilege` */

DROP TABLE IF EXISTS `csm_role_privilege`;

CREATE TABLE `csm_role_privilege` (
  `ROLE_PRIVILEGE_ID` bigint(20) NOT NULL auto_increment,
  `ROLE_ID` bigint(20) NOT NULL default '0',
  `PRIVILEGE_ID` bigint(20) NOT NULL default '0',
  `UPDATE_DATE` date NOT NULL default '2005-01-01',
  PRIMARY KEY  (`ROLE_PRIVILEGE_ID`),
  UNIQUE KEY `UQ_ROLE_PRIVILEGE_ROLE_ID` (`PRIVILEGE_ID`,`ROLE_ID`),
  KEY `idx_PRIVILEGE_ID` (`PRIVILEGE_ID`),
  KEY `idx_ROLE_ID` (`ROLE_ID`),
  CONSTRAINT `csm_role_privilege_ibfk_1` FOREIGN KEY (`PRIVILEGE_ID`) REFERENCES `csm_privilege` (`PRIVILEGE_ID`) ON DELETE CASCADE,
  CONSTRAINT `csm_role_privilege_ibfk_2` FOREIGN KEY (`ROLE_ID`) REFERENCES `csm_role` (`ROLE_ID`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=91 DEFAULT CHARSET=latin1;

/*Data for the table `csm_role_privilege` */

insert into `csm_role_privilege` (`ROLE_PRIVILEGE_ID`,`ROLE_ID`,`PRIVILEGE_ID`,`UPDATE_DATE`) values (1,1,1,'2005-01-01'),(2,1,3,'2005-01-01'),(3,1,5,'2005-01-01'),(4,1,7,'2005-01-01'),(5,1,8,'2005-01-01'),(6,1,9,'2005-01-01'),(7,1,10,'2005-01-01'),(8,2,3,'2005-01-01'),(9,2,7,'2005-01-01'),(11,3,1,'2005-01-01'),(12,3,3,'2005-01-01'),(13,3,5,'2005-01-01'),(14,3,7,'2005-01-01'),(16,4,3,'2005-01-01'),(17,4,9,'2005-01-01'),(18,5,3,'2005-01-01'),(19,6,8,'2005-01-01'),(20,8,5,'2005-01-01'),(21,9,7,'2005-01-01'),(22,6,3,'2005-01-01'),(23,8,3,'2005-01-01'),(24,1,11,'2005-01-01'),(25,2,11,'2005-01-01'),(26,4,11,'2005-01-01'),(27,10,12,'2005-01-01'),(28,11,3,'2005-01-01'),(29,11,11,'2005-01-01'),(30,12,1,'2005-01-01'),(31,13,13,'2008-06-19'),(32,13,17,'2008-06-19'),(33,13,14,'2008-06-19'),(34,13,15,'2008-06-19'),(35,13,16,'2008-06-19'),(36,13,18,'2008-06-19'),(37,13,20,'2008-06-19'),(38,13,21,'2008-06-19'),(39,13,22,'2008-06-19'),(40,13,23,'2008-06-19'),(41,13,24,'2008-06-19'),(42,13,25,'2008-06-19'),(43,13,26,'2008-06-19'),(44,13,27,'2008-06-19'),(45,13,28,'2008-06-19'),(46,13,29,'2008-06-19'),(47,1,13,'2008-06-19'),(48,1,14,'2008-06-19'),(49,1,15,'2008-06-19'),(50,1,16,'2008-06-19'),(51,1,18,'2008-06-19'),(52,1,20,'2008-06-19'),(53,1,21,'2008-06-19'),(54,1,22,'2008-06-19'),(55,1,23,'2008-06-19'),(56,1,24,'2008-06-19'),(57,1,25,'2008-06-19'),(58,1,26,'2008-06-19'),(59,1,27,'2008-06-19'),(60,1,28,'2008-06-19'),(61,2,18,'2008-06-19'),(62,2,20,'2008-06-19'),(63,2,21,'2008-06-19'),(64,2,22,'2008-06-19'),(65,2,23,'2008-06-19'),(66,2,24,'2008-06-19'),(67,2,25,'2008-06-19'),(68,2,26,'2008-06-19'),(69,2,27,'2008-06-19'),(70,3,21,'2008-06-19'),(71,3,22,'2008-06-19'),(72,3,25,'2008-06-19'),(73,3,26,'2008-06-19'),(74,3,27,'2008-06-19'),(75,7,22,'2008-06-19'),(76,1,29,'2008-07-22'),(77,4,18,'2008-06-19'),(78,4,26,'2008-06-19'),(79,4,21,'2008-06-19'),(80,4,23,'2008-06-19'),(81,4,24,'2008-06-19'),(82,4,25,'2008-06-19'),(83,11,18,'2008-06-19'),(84,11,26,'2008-06-19'),(85,11,21,'2008-06-19'),(86,11,23,'2008-06-19'),(87,11,24,'2008-06-19'),(88,11,25,'2008-06-19'),(89,1,30,'2008-08-20'),(90,2,30,'2008-08-20');

/*Table structure for table `csm_user` */

DROP TABLE IF EXISTS `csm_user`;

CREATE TABLE `csm_user` (
  `USER_ID` bigint(20) NOT NULL auto_increment,
  `LOGIN_NAME` varchar(100) NOT NULL default '',
  `FIRST_NAME` varchar(100) NOT NULL default '',
  `LAST_NAME` varchar(100) NOT NULL default '',
  `ORGANIZATION` varchar(100) default NULL,
  `DEPARTMENT` varchar(100) default NULL,
  `TITLE` varchar(100) default NULL,
  `PHONE_NUMBER` varchar(15) default NULL,
  `PASSWORD` varchar(100) default NULL,
  `EMAIL_ID` varchar(100) default NULL,
  `START_DATE` date default NULL,
  `END_DATE` date default NULL,
  `UPDATE_DATE` date NOT NULL default '0000-00-00',
  PRIMARY KEY  (`USER_ID`),
  UNIQUE KEY `UQ_LOGIN_NAME` (`LOGIN_NAME`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

/*Data for the table `csm_user` */

insert into `csm_user` (`USER_ID`,`LOGIN_NAME`,`FIRST_NAME`,`LAST_NAME`,`ORGANIZATION`,`DEPARTMENT`,`TITLE`,`PHONE_NUMBER`,`PASSWORD`,`EMAIL_ID`,`START_DATE`,`END_DATE`,`UPDATE_DATE`) values (1,'admin@admin.com','Admin','Admin',NULL,NULL,NULL,NULL,'6c416f576765696c6e63316f326d3365','admin@admin.com','2005-08-30',NULL,'2005-08-30');

/*Table structure for table `csm_user_group` */

DROP TABLE IF EXISTS `csm_user_group`;

CREATE TABLE `csm_user_group` (
  `USER_GROUP_ID` bigint(20) NOT NULL auto_increment,
  `USER_ID` bigint(20) NOT NULL default '0',
  `GROUP_ID` bigint(20) NOT NULL default '0',
  PRIMARY KEY  (`USER_GROUP_ID`),
  KEY `idx_USER_ID` (`USER_ID`),
  KEY `idx_GROUP_ID` (`GROUP_ID`),
  CONSTRAINT `csm_user_group_ibfk_1` FOREIGN KEY (`GROUP_ID`) REFERENCES `csm_group` (`GROUP_ID`) ON DELETE CASCADE,
  CONSTRAINT `csm_user_group_ibfk_2` FOREIGN KEY (`USER_ID`) REFERENCES `csm_user` (`USER_ID`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

/*Data for the table `csm_user_group` */

insert into `csm_user_group` (`USER_GROUP_ID`,`USER_ID`,`GROUP_ID`) values (1,1,1);

/*Table structure for table `csm_user_group_role_pg` */

DROP TABLE IF EXISTS `csm_user_group_role_pg`;

CREATE TABLE `csm_user_group_role_pg` (
  `USER_GROUP_ROLE_PG_ID` bigint(20) NOT NULL auto_increment,
  `USER_ID` bigint(20) default NULL,
  `GROUP_ID` bigint(20) default NULL,
  `ROLE_ID` bigint(20) NOT NULL default '0',
  `PROTECTION_GROUP_ID` bigint(20) NOT NULL default '0',
  `UPDATE_DATE` date NOT NULL default '2005-01-01',
  PRIMARY KEY  (`USER_GROUP_ROLE_PG_ID`),
  KEY `idx_GROUP_ID` (`GROUP_ID`),
  KEY `idx_ROLE_ID` (`ROLE_ID`),
  KEY `idx_PROTECTION_GROUP_ID` (`PROTECTION_GROUP_ID`),
  KEY `idx_USER_ID` (`USER_ID`),
  CONSTRAINT `csm_user_group_role_pg_ibfk_1` FOREIGN KEY (`GROUP_ID`) REFERENCES `csm_group` (`GROUP_ID`) ON DELETE CASCADE,
  CONSTRAINT `csm_user_group_role_pg_ibfk_2` FOREIGN KEY (`PROTECTION_GROUP_ID`) REFERENCES `csm_protection_group` (`PROTECTION_GROUP_ID`) ON DELETE CASCADE,
  CONSTRAINT `csm_user_group_role_pg_ibfk_3` FOREIGN KEY (`ROLE_ID`) REFERENCES `csm_role` (`ROLE_ID`) ON DELETE CASCADE,
  CONSTRAINT `csm_user_group_role_pg_ibfk_4` FOREIGN KEY (`USER_ID`) REFERENCES `csm_user` (`USER_ID`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=103 DEFAULT CHARSET=latin1;

/*Data for the table `csm_user_group_role_pg` */

insert into `csm_user_group_role_pg` (`USER_GROUP_ROLE_PG_ID`,`USER_ID`,`GROUP_ID`,`ROLE_ID`,`PROTECTION_GROUP_ID`,`UPDATE_DATE`) values (3,NULL,4,5,1,'2005-01-01'),(4,NULL,1,1,1,'2005-01-01'),(5,NULL,1,1,2,'2005-01-01'),(6,NULL,1,1,3,'2005-01-01'),(20,NULL,1,1,17,'2005-01-01'),(21,NULL,1,1,18,'2005-01-01'),(22,NULL,1,1,19,'2005-01-01'),(23,NULL,1,1,20,'2005-01-01'),(24,NULL,1,1,21,'2005-01-01'),(25,NULL,1,1,22,'2005-01-01'),(26,NULL,1,1,23,'2005-01-01'),(27,NULL,2,1,2,'2005-01-01'),(28,NULL,2,1,3,'2005-01-01'),(33,NULL,2,1,22,'2005-01-01'),(34,NULL,2,1,23,'2005-01-01'),(35,NULL,2,2,1,'2005-01-01'),(40,NULL,2,2,18,'2005-01-01'),(41,NULL,2,2,19,'2005-01-01'),(46,NULL,2,2,21,'2005-01-01'),(47,NULL,3,3,3,'2005-01-01'),(50,NULL,3,3,19,'2005-01-01'),(51,NULL,3,5,1,'2005-01-01'),(52,NULL,3,5,2,'2005-01-01'),(56,NULL,3,5,21,'2005-01-01'),(57,NULL,3,5,22,'2005-01-01'),(61,NULL,4,5,2,'2005-01-01'),(62,NULL,4,5,3,'2005-01-01'),(69,NULL,4,5,20,'2005-01-01'),(70,NULL,1,1,24,'2005-08-24'),(71,NULL,2,2,24,'2005-08-24'),(72,NULL,3,3,24,'2005-08-24'),(73,NULL,4,9,24,'2005-08-24'),(74,NULL,2,2,20,'2006-01-18'),(75,NULL,3,3,20,'2005-08-24'),(102,NULL,4,12,44,'2005-01-01');

/*Table structure for table `csm_user_pe` */

DROP TABLE IF EXISTS `csm_user_pe`;

CREATE TABLE `csm_user_pe` (
  `USER_PROTECTION_ELEMENT_ID` bigint(20) NOT NULL auto_increment,
  `PROTECTION_ELEMENT_ID` bigint(20) NOT NULL default '0',
  `USER_ID` bigint(20) NOT NULL default '0',
  `UPDATE_DATE` date NOT NULL default '0000-00-00',
  PRIMARY KEY  (`USER_PROTECTION_ELEMENT_ID`),
  UNIQUE KEY `UQ_USER_PROTECTION_ELEMENT_PROTECTION_ELEMENT_ID` (`USER_ID`,`PROTECTION_ELEMENT_ID`),
  KEY `idx_USER_ID` (`USER_ID`),
  KEY `idx_PROTECTION_ELEMENT_ID` (`PROTECTION_ELEMENT_ID`),
  CONSTRAINT `csm_user_pe_ibfk_1` FOREIGN KEY (`USER_ID`) REFERENCES `csm_user` (`USER_ID`) ON DELETE CASCADE,
  CONSTRAINT `csm_user_pe_ibfk_2` FOREIGN KEY (`PROTECTION_ELEMENT_ID`) REFERENCES `csm_protection_element` (`PROTECTION_ELEMENT_ID`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `csm_user_pe` */

/*Table structure for table `curated_path` */

DROP TABLE IF EXISTS `curated_path`;

CREATE TABLE `curated_path` (
  `curated_path_Id` bigint(20) NOT NULL default '0',
  `entity_ids` varchar(1000) default NULL,
  `selected` tinyint(1) default NULL,
  PRIMARY KEY  (`curated_path_Id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `curated_path` */

/*Table structure for table `curated_path_to_path` */

DROP TABLE IF EXISTS `curated_path_to_path`;

CREATE TABLE `curated_path_to_path` (
  `curated_path_Id` bigint(20) NOT NULL default '0',
  `path_id` bigint(20) NOT NULL default '0',
  PRIMARY KEY  (`curated_path_Id`,`path_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `curated_path_to_path` */

/*Table structure for table `de_as_659` */

DROP TABLE IF EXISTS `de_as_659`;

CREATE TABLE `de_as_659` (
  `IDENTIFIER` int(38) NOT NULL,
  `DE_E_S_660` int(38) default NULL,
  `DE_E_T_661` int(38) default NULL,
  PRIMARY KEY  (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `de_as_659` */

/*Table structure for table `de_as_669` */

DROP TABLE IF EXISTS `de_as_669`;

CREATE TABLE `de_as_669` (
  `IDENTIFIER` int(38) NOT NULL,
  `DE_E_S_670` int(38) default NULL,
  `DE_E_T_671` int(38) default NULL,
  PRIMARY KEY  (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `de_as_669` */

/*Table structure for table `de_as_684` */

DROP TABLE IF EXISTS `de_as_684`;

CREATE TABLE `de_as_684` (
  `IDENTIFIER` int(38) NOT NULL,
  `DE_E_S_685` int(38) default NULL,
  `DE_E_T_686` int(38) default NULL,
  PRIMARY KEY  (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `de_as_684` */

/*Table structure for table `de_as_714` */

DROP TABLE IF EXISTS `de_as_714`;

CREATE TABLE `de_as_714` (
  `IDENTIFIER` int(38) NOT NULL,
  `DE_E_S_715` int(38) default NULL,
  `DE_E_T_716` int(38) default NULL,
  PRIMARY KEY  (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `de_as_714` */

/*Table structure for table `de_coll_attr_record_values` */

DROP TABLE IF EXISTS `de_coll_attr_record_values`;

CREATE TABLE `de_coll_attr_record_values` (
  `IDENTIFIER` bigint(20) NOT NULL auto_increment,
  `RECORD_VALUE` text,
  `COLLECTION_ATTR_RECORD_ID` bigint(20) default NULL,
  PRIMARY KEY  (`IDENTIFIER`),
  KEY `FK847DA57775255CA5` (`COLLECTION_ATTR_RECORD_ID`),
  CONSTRAINT `FK847DA57775255CA5` FOREIGN KEY (`COLLECTION_ATTR_RECORD_ID`) REFERENCES `dyextn_attribute_record` (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `de_coll_attr_record_values` */

/*Table structure for table `de_e_547` */

DROP TABLE IF EXISTS `de_e_547`;

CREATE TABLE `de_e_547` (
  `ACTIVITY_STATUS` text,
  `IDENTIFIER` bigint(38) NOT NULL,
  `DE_AT_551` text,
  `DE_AT_552` text,
  `DE_AT_553` text,
  `DE_AT_554` text,
  `DE_E_S_695` int(38) default NULL,
  `DE_E_S_710` int(38) default NULL,
  `DE_E_S_725` int(38) default NULL,
  `DE_E_T_676` int(38) default NULL,
  PRIMARY KEY  (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `de_e_547` */

/*Table structure for table `de_e_555` */

DROP TABLE IF EXISTS `de_e_555`;

CREATE TABLE `de_e_555` (
  `ACTIVITY_STATUS` text,
  `IDENTIFIER` bigint(38) NOT NULL,
  PRIMARY KEY  (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `de_e_555` */

/*Table structure for table `de_e_559` */

DROP TABLE IF EXISTS `de_e_559`;

CREATE TABLE `de_e_559` (
  `ACTIVITY_STATUS` text,
  `IDENTIFIER` bigint(38) NOT NULL,
  `DE_AT_563` text,
  `DE_AT_564` text,
  `DE_AT_565` text,
  `DE_AT_566` text,
  `DE_AT_567` text,
  `DE_AT_568` text,
  `DE_AT_569` text,
  `DE_E_T_656` int(38) default NULL,
  PRIMARY KEY  (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `de_e_559` */

/*Table structure for table `de_e_570` */

DROP TABLE IF EXISTS `de_e_570`;

CREATE TABLE `de_e_570` (
  `ACTIVITY_STATUS` text,
  `IDENTIFIER` bigint(38) NOT NULL,
  `DE_AT_574` text,
  PRIMARY KEY  (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `de_e_570` */

/*Table structure for table `de_e_575` */

DROP TABLE IF EXISTS `de_e_575`;

CREATE TABLE `de_e_575` (
  `ACTIVITY_STATUS` text,
  `IDENTIFIER` bigint(38) NOT NULL,
  `DE_E_S_645` int(38) default NULL,
  `DE_E_S_720` int(38) default NULL,
  PRIMARY KEY  (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `de_e_575` */

/*Table structure for table `de_e_579` */

DROP TABLE IF EXISTS `de_e_579`;

CREATE TABLE `de_e_579` (
  `ACTIVITY_STATUS` text,
  `IDENTIFIER` bigint(38) NOT NULL,
  `DE_AT_583` text,
  `DE_AT_584` text,
  PRIMARY KEY  (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `de_e_579` */

/*Table structure for table `de_e_585` */

DROP TABLE IF EXISTS `de_e_585`;

CREATE TABLE `de_e_585` (
  `ACTIVITY_STATUS` text,
  `DE_AT_595` date default NULL,
  `DE_AT_594` date default NULL,
  `DE_AT_593` text,
  `DE_AT_592` text,
  `DE_AT_591` text,
  `DE_AT_590` date default NULL,
  `DE_AT_589` date default NULL,
  `IDENTIFIER` bigint(38) NOT NULL,
  `DE_E_T_651` int(38) default NULL,
  `DE_E_S_665` int(38) default NULL,
  `DE_E_S_680` int(38) default NULL,
  `DE_E_S_690` int(38) default NULL,
  `DE_E_S_700` int(38) default NULL,
  `DE_E_S_705` int(38) default NULL,
  PRIMARY KEY  (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `de_e_585` */

/*Table structure for table `de_e_596` */

DROP TABLE IF EXISTS `de_e_596`;

CREATE TABLE `de_e_596` (
  `ACTIVITY_STATUS` text,
  `IDENTIFIER` bigint(38) NOT NULL,
  PRIMARY KEY  (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `de_e_596` */

/*Table structure for table `de_e_600` */

DROP TABLE IF EXISTS `de_e_600`;

CREATE TABLE `de_e_600` (
  `ACTIVITY_STATUS` text,
  `IDENTIFIER` bigint(38) NOT NULL,
  PRIMARY KEY  (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `de_e_600` */

/*Table structure for table `de_e_604` */

DROP TABLE IF EXISTS `de_e_604`;

CREATE TABLE `de_e_604` (
  `ACTIVITY_STATUS` text,
  `IDENTIFIER` bigint(38) NOT NULL,
  PRIMARY KEY  (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `de_e_604` */

/*Table structure for table `de_e_608` */

DROP TABLE IF EXISTS `de_e_608`;

CREATE TABLE `de_e_608` (
  `ACTIVITY_STATUS` text,
  `IDENTIFIER` bigint(38) NOT NULL,
  PRIMARY KEY  (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `de_e_608` */

/*Table structure for table `de_e_612` */

DROP TABLE IF EXISTS `de_e_612`;

CREATE TABLE `de_e_612` (
  `ACTIVITY_STATUS` text,
  `IDENTIFIER` bigint(38) NOT NULL,
  PRIMARY KEY  (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `de_e_612` */

/*Table structure for table `de_e_616` */

DROP TABLE IF EXISTS `de_e_616`;

CREATE TABLE `de_e_616` (
  `ACTIVITY_STATUS` text,
  `IDENTIFIER` bigint(38) NOT NULL,
  PRIMARY KEY  (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `de_e_616` */

/*Table structure for table `de_e_620` */

DROP TABLE IF EXISTS `de_e_620`;

CREATE TABLE `de_e_620` (
  `ACTIVITY_STATUS` text,
  `IDENTIFIER` bigint(38) NOT NULL,
  PRIMARY KEY  (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `de_e_620` */

/*Table structure for table `de_e_624` */

DROP TABLE IF EXISTS `de_e_624`;

CREATE TABLE `de_e_624` (
  `ACTIVITY_STATUS` text,
  `IDENTIFIER` bigint(38) NOT NULL,
  PRIMARY KEY  (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `de_e_624` */

/*Table structure for table `de_e_628` */

DROP TABLE IF EXISTS `de_e_628`;

CREATE TABLE `de_e_628` (
  `ACTIVITY_STATUS` text,
  `IDENTIFIER` bigint(38) NOT NULL,
  PRIMARY KEY  (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `de_e_628` */

/*Table structure for table `de_e_632` */

DROP TABLE IF EXISTS `de_e_632`;

CREATE TABLE `de_e_632` (
  `ACTIVITY_STATUS` text,
  `IDENTIFIER` bigint(38) NOT NULL,
  PRIMARY KEY  (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `de_e_632` */

/*Table structure for table `de_e_636` */

DROP TABLE IF EXISTS `de_e_636`;

CREATE TABLE `de_e_636` (
  `ACTIVITY_STATUS` text,
  `IDENTIFIER` bigint(38) NOT NULL,
  PRIMARY KEY  (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `de_e_636` */

/*Table structure for table `de_e_640` */

DROP TABLE IF EXISTS `de_e_640`;

CREATE TABLE `de_e_640` (
  `ACTIVITY_STATUS` text,
  `IDENTIFIER` bigint(38) NOT NULL,
  PRIMARY KEY  (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `de_e_640` */

/*Table structure for table `de_file_attr_record_values` */

DROP TABLE IF EXISTS `de_file_attr_record_values`;

CREATE TABLE `de_file_attr_record_values` (
  `IDENTIFIER` bigint(20) NOT NULL auto_increment,
  `CONTENT_TYPE` varchar(255) default NULL,
  `FILE_CONTENT` longblob,
  `FILE_NAME` varchar(255) default NULL,
  `RECORD_ID` bigint(20) default NULL,
  PRIMARY KEY  (`IDENTIFIER`),
  KEY `FKE68334E74EB991B2` (`RECORD_ID`),
  CONSTRAINT `FKE68334E74EB991B2` FOREIGN KEY (`RECORD_ID`) REFERENCES `dyextn_attribute_record` (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `de_file_attr_record_values` */

/*Table structure for table `de_object_attr_record_values` */

DROP TABLE IF EXISTS `de_object_attr_record_values`;

CREATE TABLE `de_object_attr_record_values` (
  `IDENTIFIER` bigint(20) NOT NULL auto_increment,
  `CLASS_NAME` varchar(255) default NULL,
  `OBJECT_CONTENT` longblob,
  `RECORD_ID` bigint(20) default NULL,
  PRIMARY KEY  (`IDENTIFIER`),
  KEY `FK504EADC44EB991B2` (`RECORD_ID`),
  CONSTRAINT `FK504EADC44EB991B2` FOREIGN KEY (`RECORD_ID`) REFERENCES `dyextn_attribute_record` (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `de_object_attr_record_values` */

/*Table structure for table `dyextn_abstr_contain_ctr` */

DROP TABLE IF EXISTS `dyextn_abstr_contain_ctr`;

CREATE TABLE `dyextn_abstr_contain_ctr` (
  `IDENTIFIER` bigint(20) NOT NULL,
  `CONTAINER_ID` bigint(20) default NULL,
  PRIMARY KEY  (`IDENTIFIER`),
  KEY `FK9EB9020A40F198C2` (`IDENTIFIER`),
  KEY `FK9EB9020A69935DD6` (`CONTAINER_ID`),
  CONSTRAINT `FK9EB9020A69935DD6` FOREIGN KEY (`CONTAINER_ID`) REFERENCES `dyextn_container` (`IDENTIFIER`),
  CONSTRAINT `FK9EB9020A40F198C2` FOREIGN KEY (`IDENTIFIER`) REFERENCES `dyextn_control` (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `dyextn_abstr_contain_ctr` */

/*Table structure for table `dyextn_abstract_entity` */

DROP TABLE IF EXISTS `dyextn_abstract_metadata`;

CREATE TABLE `dyextn_abstract_metadata` (
  `IDENTIFIER` bigint(20) NOT NULL auto_increment,
  `CREATED_DATE` date default NULL,
  `DESCRIPTION` text,
  `LAST_UPDATED` date default NULL,
  `NAME` text,
  `PUBLIC_ID` varchar(255) default NULL,
  PRIMARY KEY  (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (10,'2008-10-21','cider-ActiveUpiFlag',NULL,'cider',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (11,'2008-10-21','cider-ActiveUpiFlag','2008-10-21','ActiveUpiFlag',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (12,NULL,NULL,NULL,'id',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (13,'2008-10-21','cider-State','2008-10-21','State',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (14,NULL,NULL,NULL,'id',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (15,'2008-10-21','cider-PhoneType','2008-10-21','PhoneType',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (16,NULL,NULL,NULL,'id',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (17,'2008-10-21','cider-RelationToPerson','2008-10-21','RelationToPerson',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (18,NULL,NULL,NULL,'id',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (19,'2008-10-21','cider-EthnicOrigin','2008-10-21','EthnicOrigin',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (20,NULL,NULL,NULL,'id',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (21,'2008-10-21','cider-AddressType','2008-10-21','AddressType',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (22,NULL,NULL,NULL,'id',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (23,'2008-10-21','cider-Address','2008-10-21','Address',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (24,NULL,NULL,NULL,'AssociationName_3',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (25,NULL,NULL,NULL,'AssociationName_2',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (26,'2008-10-21','cider-Country','2008-10-21','Country',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (27,NULL,NULL,NULL,'id',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (28,NULL,NULL,NULL,'AssociationName_1',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (29,NULL,NULL,NULL,'postalCode',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (30,NULL,NULL,NULL,'city',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (31,NULL,NULL,NULL,'line2',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (32,NULL,NULL,NULL,'line1',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (33,NULL,NULL,NULL,'id',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (34,'2008-10-21','cider-MaritalStatus','2008-10-21','MaritalStatus',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (35,NULL,NULL,NULL,'id',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (36,'2008-10-21','cider-PersonName','2008-10-21','PersonName',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (37,NULL,NULL,NULL,'suffix',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (38,NULL,NULL,NULL,'prefix',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (39,NULL,NULL,NULL,'degree',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (40,NULL,NULL,NULL,'lastNameCompressed',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (41,NULL,NULL,NULL,'lastName',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (42,NULL,NULL,NULL,'middleName',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (43,NULL,NULL,NULL,'firstName',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (44,NULL,NULL,NULL,'id',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (45,'2008-10-21','cider-Person','2008-10-21','Person',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (46,'2008-10-21','cider-Demographics','2008-10-21','Demographics',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (47,NULL,NULL,NULL,'AssociationName_11',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (48,'2008-10-21','cider-Gender','2008-10-21','Gender',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (49,NULL,NULL,NULL,'id',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (50,NULL,NULL,NULL,'AssociationName_10',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (51,'2008-10-21','cider-AdvancedDirectiveExists','2008-10-21','AdvancedDirectiveExists',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (52,NULL,NULL,NULL,'id',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (53,NULL,NULL,NULL,'AssociationName_9',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (54,'2008-10-21','cider-Religion','2008-10-21','Religion',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (55,NULL,NULL,NULL,'id',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (56,NULL,NULL,NULL,'AssociationName_8',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (57,'2008-10-21','cider-Race','2008-10-21','Race',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (58,NULL,NULL,NULL,'id',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (59,NULL,NULL,NULL,'AssociationName_7',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (60,NULL,NULL,NULL,'AssociationName_6',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (61,NULL,NULL,NULL,'id',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (62,NULL,NULL,NULL,'dateOfBirth',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (63,NULL,NULL,NULL,'dateOfDeath',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (64,NULL,NULL,NULL,'mothersMaidenName',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (65,NULL,NULL,NULL,'placeOfBirth',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (66,NULL,NULL,NULL,'socialSecurityNumber',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (67,NULL,NULL,NULL,'effectiveStartTimeStamp',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (68,NULL,NULL,NULL,'effectiveEndTimeStamp',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (69,NULL,NULL,NULL,'AssociationName_1',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (70,'2008-10-21','cider-AssociatedPerson','2008-10-21','AssociatedPerson',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (71,NULL,NULL,NULL,'AssociationName_2',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (72,NULL,NULL,NULL,'AssociationName_1',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (73,NULL,NULL,NULL,'id',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (74,NULL,NULL,NULL,'AssociationName_2',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (75,NULL,NULL,NULL,'AssociationName_3',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (76,'2008-10-21','cider-Phone','2008-10-21','Phone',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (77,NULL,NULL,NULL,'type',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (78,NULL,NULL,NULL,'number',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (79,NULL,NULL,NULL,'id',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (80,NULL,NULL,NULL,'AssociationName_4',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (81,NULL,NULL,NULL,'AssociationName_5',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (82,NULL,NULL,NULL,'AssociationName_1',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (83,NULL,NULL,NULL,'personUpi',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (84,NULL,NULL,NULL,'id',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (85,'2008-10-21','cider-ResearchOptOut','2008-10-21','ResearchOptOut',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (86,NULL,NULL,NULL,'id',NULL);

DROP TABLE IF EXISTS `dyextn_abstract_entity`;

CREATE TABLE `dyextn_abstract_entity` (
  `id` bigint(20) NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `FKA4A164E3D3027A30` (`id`),
  CONSTRAINT `FKA4A164E3D3027A30` FOREIGN KEY (`id`) REFERENCES `dyextn_abstract_metadata` (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `dyextn_abstract_entity` */

insert into `dyextn_abstract_entity` (`id`) values (11),(13),(15),(17),(19),(21),(23),(26),(34),(36),(45),(46),(48),(51),(54),(57),(70),(76),(85);

/*Table structure for table `dyextn_abstract_metadata` */


/*Data for the table `dyextn_abstract_metadata` */

/*Table structure for table `dyextn_asso_display_attr` */

DROP TABLE IF EXISTS `dyextn_asso_display_attr`;

CREATE TABLE `dyextn_asso_display_attr` (
  `IDENTIFIER` bigint(20) NOT NULL auto_increment,
  `SEQUENCE_NUMBER` int(11) default NULL,
  `DISPLAY_ATTRIBUTE_ID` bigint(20) default NULL,
  `SELECT_CONTROL_ID` bigint(20) default NULL,
  PRIMARY KEY  (`IDENTIFIER`),
  KEY `FKD12FD38235D6E973` (`DISPLAY_ATTRIBUTE_ID`),
  KEY `FKD12FD3827FD29CDD` (`SELECT_CONTROL_ID`),
  CONSTRAINT `FKD12FD3827FD29CDD` FOREIGN KEY (`SELECT_CONTROL_ID`) REFERENCES `dyextn_select_control` (`IDENTIFIER`),
  CONSTRAINT `FKD12FD38235D6E973` FOREIGN KEY (`DISPLAY_ATTRIBUTE_ID`) REFERENCES `dyextn_primitive_attribute` (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `dyextn_asso_display_attr` */

/*Table structure for table `dyextn_association` */

DROP TABLE IF EXISTS `dyextn_association`;

CREATE TABLE `dyextn_association` (
  `IDENTIFIER` bigint(20) NOT NULL,
  `IS_COLLECTION` bit(1) default NULL,
  `DIRECTION` varchar(255) default NULL,
  `TARGET_ENTITY_ID` bigint(20) default NULL,
  `SOURCE_ROLE_ID` bigint(20) default NULL,
  `TARGET_ROLE_ID` bigint(20) default NULL,
  `IS_SYSTEM_GENERATED` bit(1) default NULL,
  PRIMARY KEY  (`IDENTIFIER`),
  KEY `FK1046842440738A50` (`TARGET_ENTITY_ID`),
  KEY `FK104684246D19A21F` (`IDENTIFIER`),
  KEY `FK104684242BD842F0` (`TARGET_ROLE_ID`),
  KEY `FK1046842439780F7A` (`SOURCE_ROLE_ID`),
  CONSTRAINT `FK1046842439780F7A` FOREIGN KEY (`SOURCE_ROLE_ID`) REFERENCES `dyextn_role` (`IDENTIFIER`),
  CONSTRAINT `FK104684242BD842F0` FOREIGN KEY (`TARGET_ROLE_ID`) REFERENCES `dyextn_role` (`IDENTIFIER`),
  CONSTRAINT `FK1046842440738A50` FOREIGN KEY (`TARGET_ENTITY_ID`) REFERENCES `dyextn_entity` (`IDENTIFIER`),
  CONSTRAINT `FK104684246D19A21F` FOREIGN KEY (`IDENTIFIER`) REFERENCES `dyextn_attribute` (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `dyextn_association` */

insert into `dyextn_association` (`IDENTIFIER`,`IS_COLLECTION`,`DIRECTION`,`TARGET_ENTITY_ID`,`SOURCE_ROLE_ID`,`TARGET_ROLE_ID`,`IS_SYSTEM_GENERATED`) values (24,'\0','SRC_DESTINATION',13,1,2,'\0'),(25,'\0','SRC_DESTINATION',21,3,4,'\0'),(28,'\0','SRC_DESTINATION',26,5,6,'\0'),(47,'\0','SRC_DESTINATION',19,7,8,'\0'),(50,'\0','SRC_DESTINATION',48,9,10,'\0'),(53,'\0','SRC_DESTINATION',51,11,12,'\0'),(56,'\0','SRC_DESTINATION',54,13,14,'\0'),(59,'\0','SRC_DESTINATION',57,15,16,'\0'),(60,'\0','SRC_DESTINATION',34,17,18,'\0'),(69,'\0','SRC_DESTINATION',36,19,20,'\0'),(71,'\0','SRC_DESTINATION',17,21,22,'\0'),(72,'\0','SRC_DESTINATION',45,23,24,'\0'),(74,'\0','SRC_DESTINATION',70,25,26,'\0'),(75,'\0','SRC_DESTINATION',70,27,28,'\0'),(80,'\0','SRC_DESTINATION',76,29,30,'\0'),(81,'\0','SRC_DESTINATION',23,31,32,'\0'),(82,'\0','SRC_DESTINATION',46,33,34,'\0');

/*Table structure for table `dyextn_attribute` */

DROP TABLE IF EXISTS `dyextn_attribute`;

CREATE TABLE `dyextn_attribute` (
  `IDENTIFIER` bigint(20) NOT NULL,
  `ENTIY_ID` bigint(20) default NULL,
  PRIMARY KEY  (`IDENTIFIER`),
  KEY `FK37F1E2FFF99EA906` (`ENTIY_ID`),
  KEY `FK37F1E2FF5CC8694E` (`IDENTIFIER`),
  CONSTRAINT `FK37F1E2FF5CC8694E` FOREIGN KEY (`IDENTIFIER`) REFERENCES `dyextn_base_abstract_attribute` (`IDENTIFIER`),
  CONSTRAINT `FK37F1E2FFF99EA906` FOREIGN KEY (`ENTIY_ID`) REFERENCES `dyextn_entity` (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `dyextn_attribute` */

insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (12,11),(14,13),(16,15),(18,17),(20,19),(22,21),(24,23),(25,23),(27,26),(28,23),(29,23),(30,23),(31,23),(32,23),(33,23),(35,34),(37,36),(38,36),(39,36),(40,36),(41,36),(42,36),(43,36),(44,36),(47,46),(49,48),(50,46),(52,51),(53,46),(55,54),(56,46),(58,57),(59,46),(60,46),(61,46),(62,46),(63,46),(64,46),(65,46),(66,46),(67,46),(68,46),(69,46),(71,70),(72,70),(73,70),(74,46),(75,46),(77,76),(78,76),(79,76),(80,46),(81,46),(82,45),(83,45),(84,45),(86,85);

/*Table structure for table `dyextn_attribute_record` */

DROP TABLE IF EXISTS `dyextn_attribute_record`;

CREATE TABLE `dyextn_attribute_record` (
  `IDENTIFIER` bigint(20) NOT NULL auto_increment,
  `ENTITY_ID` bigint(20) default NULL,
  `ATTRIBUTE_ID` bigint(20) default NULL,
  `RECORD_ID` bigint(20) default NULL,
  PRIMARY KEY  (`IDENTIFIER`),
  KEY `FK9B20ED914AC41F7E` (`ENTITY_ID`),
  KEY `FK9B20ED914DC2CD16` (`ATTRIBUTE_ID`),
  CONSTRAINT `FK9B20ED914DC2CD16` FOREIGN KEY (`ATTRIBUTE_ID`) REFERENCES `dyextn_primitive_attribute` (`IDENTIFIER`),
  CONSTRAINT `FK9B20ED914AC41F7E` FOREIGN KEY (`ENTITY_ID`) REFERENCES `dyextn_entity` (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `dyextn_attribute_record` */

/*Table structure for table `dyextn_attribute_type_info` */

DROP TABLE IF EXISTS `dyextn_attribute_type_info`;

CREATE TABLE `dyextn_attribute_type_info` (
  `IDENTIFIER` bigint(20) NOT NULL auto_increment,
  `PRIMITIVE_ATTRIBUTE_ID` bigint(20) default NULL,
  PRIMARY KEY  (`IDENTIFIER`),
  KEY `FK62596D531333996E` (`PRIMITIVE_ATTRIBUTE_ID`),
  CONSTRAINT `FK62596D531333996E` FOREIGN KEY (`PRIMITIVE_ATTRIBUTE_ID`) REFERENCES `dyextn_primitive_attribute` (`IDENTIFIER`)
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=latin1;

/*Data for the table `dyextn_attribute_type_info` */

insert into `dyextn_attribute_type_info` (`IDENTIFIER`,`PRIMITIVE_ATTRIBUTE_ID`) values (1,12),(2,14),(3,16),(4,18),(5,20),(6,22),(7,27),(8,29),(9,30),(10,31),(11,32),(12,33),(13,35),(14,37),(15,38),(16,39),(17,40),(18,41),(19,42),(20,43),(21,44),(22,49),(23,52),(24,55),(25,58),(26,61),(27,62),(28,63),(29,64),(30,65),(31,66),(32,67),(33,68),(34,73),(35,77),(36,78),(37,79),(38,83),(39,84),(40,86);

/*Table structure for table `dyextn_barr_concept_value` */

DROP TABLE IF EXISTS `dyextn_barr_concept_value`;

CREATE TABLE `dyextn_barr_concept_value` (
  `IDENTIFIER` bigint(20) NOT NULL,
  PRIMARY KEY  (`IDENTIFIER`),
  KEY `FK89D27DF74641D513` (`IDENTIFIER`),
  CONSTRAINT `FK89D27DF74641D513` FOREIGN KEY (`IDENTIFIER`) REFERENCES `dyextn_permissible_value` (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `dyextn_barr_concept_value` */

/*Table structure for table `dyextn_base_abstract_attribute` */

DROP TABLE IF EXISTS `dyextn_base_abstract_attribute`;

CREATE TABLE `dyextn_base_abstract_attribute` (
  `IDENTIFIER` bigint(20) NOT NULL,
  PRIMARY KEY  (`IDENTIFIER`),
  KEY `FK14BA6610728B19BE` (`IDENTIFIER`),
  CONSTRAINT `FK14BA6610728B19BE` FOREIGN KEY (`IDENTIFIER`) REFERENCES `dyextn_abstract_metadata` (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `dyextn_base_abstract_attribute` */

insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (12),(14),(16),(18),(20),(22),(24),(25),(27),(28),(29),(30),(31),(32),(33),(35),(37),(38),(39),(40),(41),(42),(43),(44),(47),(49),(50),(52),(53),(55),(56),(58),(59),(60),(61),(62),(63),(64),(65),(66),(67),(68),(69),(71),(72),(73),(74),(75),(77),(78),(79),(80),(81),(82),(83),(84),(86);

/*Table structure for table `dyextn_boolean_concept_value` */

DROP TABLE IF EXISTS `dyextn_boolean_concept_value`;

CREATE TABLE `dyextn_boolean_concept_value` (
  `IDENTIFIER` bigint(20) NOT NULL,
  `VALUE` bit(1) default NULL,
  PRIMARY KEY  (`IDENTIFIER`),
  KEY `FK57B6C4A64641D513` (`IDENTIFIER`),
  CONSTRAINT `FK57B6C4A64641D513` FOREIGN KEY (`IDENTIFIER`) REFERENCES `dyextn_permissible_value` (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `dyextn_boolean_concept_value` */

/*Table structure for table `dyextn_boolean_type_info` */

DROP TABLE IF EXISTS `dyextn_boolean_type_info`;

CREATE TABLE `dyextn_boolean_type_info` (
  `IDENTIFIER` bigint(20) NOT NULL,
  PRIMARY KEY  (`IDENTIFIER`),
  KEY `FK28F1809FE5294FA3` (`IDENTIFIER`),
  CONSTRAINT `FK28F1809FE5294FA3` FOREIGN KEY (`IDENTIFIER`) REFERENCES `dyextn_attribute_type_info` (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `dyextn_boolean_type_info` */

/*Table structure for table `dyextn_byte_array_type_info` */

DROP TABLE IF EXISTS `dyextn_byte_array_type_info`;

CREATE TABLE `dyextn_byte_array_type_info` (
  `IDENTIFIER` bigint(20) NOT NULL,
  `CONTENT_TYPE` varchar(255) default NULL,
  PRIMARY KEY  (`IDENTIFIER`),
  KEY `FK18BDA73E5294FA3` (`IDENTIFIER`),
  CONSTRAINT `FK18BDA73E5294FA3` FOREIGN KEY (`IDENTIFIER`) REFERENCES `dyextn_attribute_type_info` (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `dyextn_byte_array_type_info` */

/*Table structure for table `dyextn_cadsr_value_domain_info` */

DROP TABLE IF EXISTS `dyextn_cadsr_value_domain_info`;

CREATE TABLE `dyextn_cadsr_value_domain_info` (
  `IDENTIFIER` bigint(20) NOT NULL auto_increment,
  `DATATYPE` varchar(255) default NULL,
  `NAME` varchar(255) default NULL,
  `TYPE` varchar(255) default NULL,
  `PRIMITIVE_ATTRIBUTE_ID` bigint(20) default NULL,
  PRIMARY KEY  (`IDENTIFIER`),
  KEY `FK1C9AA3641333996E` (`PRIMITIVE_ATTRIBUTE_ID`),
  CONSTRAINT `FK1C9AA3641333996E` FOREIGN KEY (`PRIMITIVE_ATTRIBUTE_ID`) REFERENCES `dyextn_primitive_attribute` (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `dyextn_cadsr_value_domain_info` */

/*Table structure for table `dyextn_cadsrde` */

DROP TABLE IF EXISTS `dyextn_cadsrde`;

CREATE TABLE `dyextn_cadsrde` (
  `IDENTIFIER` bigint(20) NOT NULL,
  `PUBLIC_ID` varchar(255) default NULL,
  PRIMARY KEY  (`IDENTIFIER`),
  KEY `FK588A250953CC4A77` (`IDENTIFIER`),
  CONSTRAINT `FK588A250953CC4A77` FOREIGN KEY (`IDENTIFIER`) REFERENCES `dyextn_data_element` (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `dyextn_cadsrde` */

/*Table structure for table `dyextn_cat_asso_ctl` */

DROP TABLE IF EXISTS `dyextn_cat_asso_ctl`;

CREATE TABLE `dyextn_cat_asso_ctl` (
  `IDENTIFIER` bigint(20) NOT NULL,
  PRIMARY KEY  (`IDENTIFIER`),
  KEY `FK281FAB50C45A8CFA` (`IDENTIFIER`),
  CONSTRAINT `FK281FAB50C45A8CFA` FOREIGN KEY (`IDENTIFIER`) REFERENCES `dyextn_abstr_contain_ctr` (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `dyextn_cat_asso_ctl` */

/*Table structure for table `dyextn_category` */

DROP TABLE IF EXISTS `dyextn_category`;

CREATE TABLE `dyextn_category` (
  `IDENTIFIER` bigint(20) NOT NULL,
  `ROOT_CATEGORY_ELEMENT` bigint(20) default NULL,
  `CATEGORY_ENTITY_ID` bigint(20) default NULL,
  PRIMARY KEY  (`IDENTIFIER`),
  KEY `FKD33DE81B54A9F59D` (`ROOT_CATEGORY_ELEMENT`),
  KEY `FKD33DE81B854AC01B` (`CATEGORY_ENTITY_ID`),
  KEY `FKD33DE81B728B19BE` (`IDENTIFIER`),
  CONSTRAINT `FKD33DE81B728B19BE` FOREIGN KEY (`IDENTIFIER`) REFERENCES `dyextn_abstract_metadata` (`IDENTIFIER`),
  CONSTRAINT `FKD33DE81B54A9F59D` FOREIGN KEY (`ROOT_CATEGORY_ELEMENT`) REFERENCES `dyextn_category_entity` (`IDENTIFIER`),
  CONSTRAINT `FKD33DE81B854AC01B` FOREIGN KEY (`CATEGORY_ENTITY_ID`) REFERENCES `dyextn_category_entity` (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `dyextn_category` */

/*Table structure for table `dyextn_category_association` */

DROP TABLE IF EXISTS `dyextn_category_association`;

CREATE TABLE `dyextn_category_association` (
  `IDENTIFIER` bigint(20) NOT NULL,
  `CATEGORY_ENTIY_ID` bigint(20) default NULL,
  `CATEGORY_ENTITY_ID` bigint(20) default NULL,
  PRIMARY KEY  (`IDENTIFIER`),
  KEY `FK1B7C663DCAC769C5` (`CATEGORY_ENTIY_ID`),
  KEY `FK1B7C663D854AC01B` (`CATEGORY_ENTITY_ID`),
  KEY `FK1B7C663D5CC8694E` (`IDENTIFIER`),
  CONSTRAINT `FK1B7C663D5CC8694E` FOREIGN KEY (`IDENTIFIER`) REFERENCES `dyextn_base_abstract_attribute` (`IDENTIFIER`),
  CONSTRAINT `FK1B7C663D854AC01B` FOREIGN KEY (`CATEGORY_ENTITY_ID`) REFERENCES `dyextn_category_entity` (`IDENTIFIER`),
  CONSTRAINT `FK1B7C663DCAC769C5` FOREIGN KEY (`CATEGORY_ENTIY_ID`) REFERENCES `dyextn_category_entity` (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `dyextn_category_association` */

/*Table structure for table `dyextn_category_attribute` */

DROP TABLE IF EXISTS `dyextn_category_attribute`;

CREATE TABLE `dyextn_category_attribute` (
  `IDENTIFIER` bigint(20) NOT NULL,
  `ABSTRACT_ATTRIBUTE_ID` bigint(20) default NULL,
  `CATEGORY_ENTIY_ID` bigint(20) default NULL,
  `IS_VISIBLE` bit(1) default NULL,
  `IS_RELATTRIBUTE` bit(1) default NULL,
  `CATEGORY_ENTITY_ID` bigint(20) default NULL,
  PRIMARY KEY  (`IDENTIFIER`),
  KEY `FKEF3B7758CAC769C5` (`CATEGORY_ENTIY_ID`),
  KEY `FKEF3B7758854AC01B` (`CATEGORY_ENTITY_ID`),
  KEY `FKEF3B77585CC8694E` (`IDENTIFIER`),
  KEY `FKEF3B77587769A811` (`ABSTRACT_ATTRIBUTE_ID`),
  CONSTRAINT `FKEF3B77587769A811` FOREIGN KEY (`ABSTRACT_ATTRIBUTE_ID`) REFERENCES `dyextn_attribute` (`IDENTIFIER`),
  CONSTRAINT `FKEF3B77585CC8694E` FOREIGN KEY (`IDENTIFIER`) REFERENCES `dyextn_base_abstract_attribute` (`IDENTIFIER`),
  CONSTRAINT `FKEF3B7758854AC01B` FOREIGN KEY (`CATEGORY_ENTITY_ID`) REFERENCES `dyextn_category_entity` (`IDENTIFIER`),
  CONSTRAINT `FKEF3B7758CAC769C5` FOREIGN KEY (`CATEGORY_ENTIY_ID`) REFERENCES `dyextn_category_entity` (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `dyextn_category_attribute` */

/*Table structure for table `dyextn_category_entity` */

DROP TABLE IF EXISTS `dyextn_category_entity`;

CREATE TABLE `dyextn_category_entity` (
  `IDENTIFIER` bigint(20) NOT NULL,
  `NUMBER_OF_ENTRIES` int(11) default NULL,
  `ENTITY_ID` bigint(20) default NULL,
  `OWN_PARENT_CATEGORY_ENTITY_ID` bigint(20) default NULL,
  `TREE_PARENT_CATEGORY_ENTITY_ID` bigint(20) default NULL,
  `IS_CREATETABLE` bit(1) default NULL,
  `CATEGORY_ASSOCIATION_ID` bigint(20) default NULL,
  `PARENT_CATEGORY_ENTITY_ID` bigint(20) default NULL,
  `REL_ATTR_CAT_ENTITY_ID` bigint(20) default NULL,
  PRIMARY KEY  (`IDENTIFIER`),
  KEY `FK9F2DA8874AC41F7E` (`ENTITY_ID`),
  KEY `FK9F2DA887A52559D0` (`PARENT_CATEGORY_ENTITY_ID`),
  KEY `FK9F2DA887A8103C6F` (`TREE_PARENT_CATEGORY_ENTITY_ID`),
  KEY `FK9F2DA887D06EE657` (`OWN_PARENT_CATEGORY_ENTITY_ID`),
  KEY `FK9F2DA887FB6EB979` (`CATEGORY_ASSOCIATION_ID`),
  KEY `FK9F2DA887F5A32608` (`REL_ATTR_CAT_ENTITY_ID`),
  KEY `FK9F2DA887743AC3F2` (`IDENTIFIER`),
  CONSTRAINT `FK9F2DA887743AC3F2` FOREIGN KEY (`IDENTIFIER`) REFERENCES `dyextn_abstract_entity` (`id`),
  CONSTRAINT `FK9F2DA8874AC41F7E` FOREIGN KEY (`ENTITY_ID`) REFERENCES `dyextn_entity` (`IDENTIFIER`),
  CONSTRAINT `FK9F2DA887A52559D0` FOREIGN KEY (`PARENT_CATEGORY_ENTITY_ID`) REFERENCES `dyextn_category_entity` (`IDENTIFIER`),
  CONSTRAINT `FK9F2DA887A8103C6F` FOREIGN KEY (`TREE_PARENT_CATEGORY_ENTITY_ID`) REFERENCES `dyextn_category_entity` (`IDENTIFIER`),
  CONSTRAINT `FK9F2DA887D06EE657` FOREIGN KEY (`OWN_PARENT_CATEGORY_ENTITY_ID`) REFERENCES `dyextn_category_entity` (`IDENTIFIER`),
  CONSTRAINT `FK9F2DA887F5A32608` FOREIGN KEY (`REL_ATTR_CAT_ENTITY_ID`) REFERENCES `dyextn_category` (`IDENTIFIER`),
  CONSTRAINT `FK9F2DA887FB6EB979` FOREIGN KEY (`CATEGORY_ASSOCIATION_ID`) REFERENCES `dyextn_category_association` (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `dyextn_category_entity` */

/*Table structure for table `dyextn_check_box` */

DROP TABLE IF EXISTS `dyextn_check_box`;

CREATE TABLE `dyextn_check_box` (
  `IDENTIFIER` bigint(20) NOT NULL,
  PRIMARY KEY  (`IDENTIFIER`),
  KEY `FK4EFF925740F198C2` (`IDENTIFIER`),
  CONSTRAINT `FK4EFF925740F198C2` FOREIGN KEY (`IDENTIFIER`) REFERENCES `dyextn_control` (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `dyextn_check_box` */

/*Table structure for table `dyextn_column_properties` */

DROP TABLE IF EXISTS `dyextn_column_properties`;

CREATE TABLE `dyextn_column_properties` (
  `IDENTIFIER` bigint(20) NOT NULL,
  `CONSTRAINT_NAME` varchar(255) default NULL,
  `CATEGORY_ATTRIBUTE_ID` bigint(20) default NULL,
  `PRIMITIVE_ATTRIBUTE_ID` bigint(20) default NULL,
  PRIMARY KEY  (`IDENTIFIER`),
  KEY `FK8FCE2B3F3AB6A1D3` (`IDENTIFIER`),
  KEY `FK8FCE2B3F1333996E` (`PRIMITIVE_ATTRIBUTE_ID`),
  KEY `FK8FCE2B3F67F8B59` (`CATEGORY_ATTRIBUTE_ID`),
  CONSTRAINT `FK8FCE2B3F67F8B59` FOREIGN KEY (`CATEGORY_ATTRIBUTE_ID`) REFERENCES `dyextn_category_attribute` (`IDENTIFIER`),
  CONSTRAINT `FK8FCE2B3F1333996E` FOREIGN KEY (`PRIMITIVE_ATTRIBUTE_ID`) REFERENCES `dyextn_primitive_attribute` (`IDENTIFIER`),
  CONSTRAINT `FK8FCE2B3F3AB6A1D3` FOREIGN KEY (`IDENTIFIER`) REFERENCES `dyextn_database_properties` (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `dyextn_column_properties` */

insert into `dyextn_column_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`CATEGORY_ATTRIBUTE_ID`,`PRIMITIVE_ATTRIBUTE_ID`) values (5,NULL,NULL,12),(7,NULL,NULL,14),(9,NULL,NULL,16),(11,NULL,NULL,18),(13,NULL,NULL,20),(15,NULL,NULL,22),(20,NULL,NULL,27),(22,NULL,NULL,29),(23,NULL,NULL,30),(24,NULL,NULL,31),(25,NULL,NULL,32),(26,NULL,NULL,33),(28,NULL,NULL,35),(30,NULL,NULL,37),(31,NULL,NULL,38),(32,NULL,NULL,39),(33,NULL,NULL,40),(34,NULL,NULL,41),(35,NULL,NULL,42),(36,NULL,NULL,43),(37,NULL,NULL,44),(42,NULL,NULL,49),(45,NULL,NULL,52),(48,NULL,NULL,55),(51,NULL,NULL,58),(54,NULL,NULL,61),(55,NULL,NULL,62),(56,NULL,NULL,63),(57,NULL,NULL,64),(58,NULL,NULL,65),(59,NULL,NULL,66),(60,NULL,NULL,67),(61,NULL,NULL,68),(66,NULL,NULL,73),(70,NULL,NULL,77),(71,NULL,NULL,78),(72,NULL,NULL,79),(76,NULL,NULL,83),(77,NULL,NULL,84),(79,NULL,NULL,86);

/*Table structure for table `dyextn_combobox` */

DROP TABLE IF EXISTS `dyextn_combobox`;

CREATE TABLE `dyextn_combobox` (
  `IDENTIFIER` bigint(20) NOT NULL,
  PRIMARY KEY  (`IDENTIFIER`),
  KEY `FKABBC649ABF67AB26` (`IDENTIFIER`),
  CONSTRAINT `FKABBC649ABF67AB26` FOREIGN KEY (`IDENTIFIER`) REFERENCES `dyextn_select_control` (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `dyextn_combobox` */

insert into `dyextn_combobox` (`IDENTIFIER`) values (5),(6),(7),(8),(9),(10),(11),(12),(13),(14),(15),(16),(17),(18),(19),(20),(29);

/*Table structure for table `dyextn_constraint_properties` */

DROP TABLE IF EXISTS `dyextn_constraint_properties`;

CREATE TABLE `dyextn_constraint_properties` (
  `IDENTIFIER` bigint(20) NOT NULL,
  `SOURCE_ENTITY_KEY` varchar(255) default NULL,
  `TARGET_ENTITY_KEY` varchar(255) default NULL,
  `SRC_CONSTRAINT_NAME` varchar(255) default NULL,
  `TARGET_CONSTRAINT_NAME` varchar(255) default NULL,
  `CATEGORY_ASSOCIATION_ID` bigint(20) default NULL,
  `ASSOCIATION_ID` bigint(20) default NULL,
  PRIMARY KEY  (`IDENTIFIER`),
  KEY `FK82886CD87EE87FF6` (`ASSOCIATION_ID`),
  KEY `FK82886CD8FB6EB979` (`CATEGORY_ASSOCIATION_ID`),
  KEY `FK82886CD83AB6A1D3` (`IDENTIFIER`),
  CONSTRAINT `FK82886CD83AB6A1D3` FOREIGN KEY (`IDENTIFIER`) REFERENCES `dyextn_database_properties` (`IDENTIFIER`),
  CONSTRAINT `FK82886CD87EE87FF6` FOREIGN KEY (`ASSOCIATION_ID`) REFERENCES `dyextn_association` (`IDENTIFIER`),
  CONSTRAINT `FK82886CD8FB6EB979` FOREIGN KEY (`CATEGORY_ASSOCIATION_ID`) REFERENCES `dyextn_category_association` (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `dyextn_constraint_properties` */

insert into `dyextn_constraint_properties` (`IDENTIFIER`,`SOURCE_ENTITY_KEY`,`TARGET_ENTITY_KEY`,`SRC_CONSTRAINT_NAME`,`TARGET_CONSTRAINT_NAME`,`CATEGORY_ASSOCIATION_ID`,`ASSOCIATION_ID`) values (17,'DE_E_S_725',NULL,'CONSRT_727','CONSRT_728',NULL,24),(18,'DE_E_S_710',NULL,'CONSRT_712','CONSRT_713',NULL,25),(21,'DE_E_S_695',NULL,'CONSRT_697','CONSRT_698',NULL,28),(40,'DE_E_S_715','DE_E_T_716','CONSRT_717','CONSRT_718',NULL,47),(43,'DE_E_S_705',NULL,'CONSRT_707','CONSRT_708',NULL,50),(46,'DE_E_S_700',NULL,'CONSRT_702','CONSRT_703',NULL,53),(49,'DE_E_S_690',NULL,'CONSRT_692','CONSRT_693',NULL,56),(52,'DE_E_S_685','DE_E_T_686','CONSRT_687','CONSRT_688',NULL,59),(53,'DE_E_S_680',NULL,'CONSRT_682','CONSRT_683',NULL,60),(62,NULL,'DE_E_T_656','CONSRT_657','CONSRT_658',NULL,69),(64,'DE_E_S_720',NULL,'CONSRT_722','CONSRT_723',NULL,71),(65,'DE_E_S_645',NULL,'CONSRT_647','CONSRT_648',NULL,72),(67,'DE_E_S_660','DE_E_T_661','CONSRT_662','CONSRT_663',NULL,74),(68,'DE_E_S_665',NULL,'CONSRT_667','CONSRT_668',NULL,75),(73,'DE_E_S_670','DE_E_T_671','CONSRT_672','CONSRT_673',NULL,80),(74,NULL,'DE_E_T_676','CONSRT_677','CONSRT_678',NULL,81),(75,NULL,'DE_E_T_651','CONSRT_652','CONSRT_653',NULL,82);

/*Table structure for table `dyextn_container` */

DROP TABLE IF EXISTS `dyextn_container`;

CREATE TABLE `dyextn_container` (
  `IDENTIFIER` bigint(20) NOT NULL auto_increment,
  `BUTTON_CSS` varchar(255) default NULL,
  `CAPTION` varchar(255) default NULL,
  `ABSTRACT_ENTITY_ID` bigint(20) default NULL,
  `MAIN_TABLE_CSS` varchar(255) default NULL,
  `REQUIRED_FIELD_INDICATOR` varchar(255) default NULL,
  `REQUIRED_FIELD_WARNING_MESSAGE` varchar(255) default NULL,
  `TITLE_CSS` varchar(255) default NULL,
  `BASE_CONTAINER_ID` bigint(20) default NULL,
  `ADD_CAPTION` bit(1) default NULL,
  `ENTITY_GROUP_ID` bigint(20) default NULL,
  `VIEW_ID` bigint(20) default NULL,
  PRIMARY KEY  (`IDENTIFIER`),
  KEY `FK1EAB84E4178865E` (`VIEW_ID`),
  KEY `FK1EAB84E4BF901C84` (`BASE_CONTAINER_ID`),
  KEY `FK1EAB84E41DCC9E63` (`ABSTRACT_ENTITY_ID`),
  KEY `FK1EAB84E488C075EF` (`ENTITY_GROUP_ID`),
  CONSTRAINT `FK1EAB84E488C075EF` FOREIGN KEY (`ENTITY_GROUP_ID`) REFERENCES `dyextn_entity_group` (`IDENTIFIER`),
  CONSTRAINT `FK1EAB84E4178865E` FOREIGN KEY (`VIEW_ID`) REFERENCES `dyextn_view` (`IDENTIFIER`),
  CONSTRAINT `FK1EAB84E41DCC9E63` FOREIGN KEY (`ABSTRACT_ENTITY_ID`) REFERENCES `dyextn_abstract_entity` (`id`),
  CONSTRAINT `FK1EAB84E4BF901C84` FOREIGN KEY (`BASE_CONTAINER_ID`) REFERENCES `dyextn_container` (`IDENTIFIER`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=latin1;

/*Data for the table `dyextn_container` */

insert into `dyextn_container` (`IDENTIFIER`,`BUTTON_CSS`,`CAPTION`,`ABSTRACT_ENTITY_ID`,`MAIN_TABLE_CSS`,`REQUIRED_FIELD_INDICATOR`,`REQUIRED_FIELD_WARNING_MESSAGE`,`TITLE_CSS`,`BASE_CONTAINER_ID`,`ADD_CAPTION`,`ENTITY_GROUP_ID`,`VIEW_ID`) values (1,NULL,'ResearchOptOut',85,NULL,'*','indicates required fields.',NULL,NULL,'',10,NULL),(2,NULL,'PhoneType',15,NULL,'*','indicates required fields.',NULL,NULL,'',10,NULL),(3,NULL,'ActiveUpiFlag',11,NULL,'*','indicates required fields.',NULL,NULL,'',10,NULL),(4,NULL,'State',13,NULL,'*','indicates required fields.',NULL,NULL,'',10,NULL),(5,NULL,'Religion',54,NULL,'*','indicates required fields.',NULL,NULL,'',10,NULL),(6,NULL,'RelationToPerson',17,NULL,'*','indicates required fields.',NULL,NULL,'',10,NULL),(7,NULL,'Address',23,NULL,'*','indicates required fields.',NULL,NULL,'',10,NULL),(8,NULL,'AddressType',21,NULL,'*','indicates required fields.',NULL,NULL,'',10,NULL),(9,NULL,'AdvancedDirectiveExists',51,NULL,'*','indicates required fields.',NULL,NULL,'',10,NULL),(10,NULL,'AssociatedPerson',70,NULL,'*','indicates required fields.',NULL,NULL,'',10,NULL),(11,NULL,'Country',26,NULL,'*','indicates required fields.',NULL,NULL,'',10,NULL),(12,NULL,'Demographics',46,NULL,'*','indicates required fields.',NULL,NULL,'',10,NULL),(13,NULL,'EthnicOrigin',19,NULL,'*','indicates required fields.',NULL,NULL,'',10,NULL),(14,NULL,'Gender',48,NULL,'*','indicates required fields.',NULL,NULL,'',10,NULL),(15,NULL,'MaritalStatus',34,NULL,'*','indicates required fields.',NULL,NULL,'',10,NULL),(16,NULL,'Person',45,NULL,'*','indicates required fields.',NULL,NULL,'',10,NULL),(17,NULL,'PersonName',36,NULL,'*','indicates required fields.',NULL,NULL,'',10,NULL),(18,NULL,'Phone',76,NULL,'*','indicates required fields.',NULL,NULL,'',10,NULL),(19,NULL,'Race',57,NULL,'*','indicates required fields.',NULL,NULL,'',10,NULL);

/*Table structure for table `dyextn_containment_control` */

DROP TABLE IF EXISTS `dyextn_containment_control`;

CREATE TABLE `dyextn_containment_control` (
  `IDENTIFIER` bigint(20) NOT NULL,
  PRIMARY KEY  (`IDENTIFIER`),
  KEY `FK3F9D4AD3C45A8CFA` (`IDENTIFIER`),
  CONSTRAINT `FK3F9D4AD3C45A8CFA` FOREIGN KEY (`IDENTIFIER`) REFERENCES `dyextn_abstr_contain_ctr` (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `dyextn_containment_control` */

/*Table structure for table `dyextn_control` */

DROP TABLE IF EXISTS `dyextn_control`;

CREATE TABLE `dyextn_control` (
  `IDENTIFIER` bigint(20) NOT NULL auto_increment,
  `CAPTION` varchar(255) default NULL,
  `CSS_CLASS` varchar(255) default NULL,
  `HIDDEN` bit(1) default NULL,
  `NAME` varchar(255) default NULL,
  `SEQUENCE_NUMBER` int(11) default NULL,
  `TOOLTIP` varchar(255) default NULL,
  `CONTAINER_ID` bigint(20) default NULL,
  `BASE_ABST_ATR_ID` bigint(20) default NULL,
  `READ_ONLY` bit(1) default NULL,
  PRIMARY KEY  (`IDENTIFIER`),
  KEY `FK70FB5E807792A78F` (`BASE_ABST_ATR_ID`),
  KEY `FK70FB5E8069935DD6` (`CONTAINER_ID`),
  CONSTRAINT `FK70FB5E8069935DD6` FOREIGN KEY (`CONTAINER_ID`) REFERENCES `dyextn_container` (`IDENTIFIER`),
  CONSTRAINT `FK70FB5E807792A78F` FOREIGN KEY (`BASE_ABST_ATR_ID`) REFERENCES `dyextn_base_abstract_attribute` (`IDENTIFIER`)
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=latin1;

/*Data for the table `dyextn_control` */

insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (1,'line1',NULL,NULL,'line1',7,NULL,7,32,'\0'),(2,'line2',NULL,NULL,'line2',6,NULL,7,31,'\0'),(3,'city',NULL,NULL,'city',5,NULL,7,30,'\0'),(4,'postalCode',NULL,NULL,'postalCode',4,NULL,7,29,'\0'),(5,'AssociationName_1',NULL,NULL,'AssociationName_1',3,NULL,7,28,'\0'),(6,'AssociationName_2',NULL,NULL,'AssociationName_2',2,NULL,7,25,'\0'),(7,'AssociationName_3',NULL,NULL,'AssociationName_3',1,NULL,7,24,'\0'),(8,'AssociationName_1',NULL,NULL,'AssociationName_1',2,NULL,10,72,'\0'),(9,'AssociationName_2',NULL,NULL,'AssociationName_2',1,NULL,10,71,'\0'),(10,'AssociationName_5',NULL,NULL,'AssociationName_5',18,NULL,12,81,'\0'),(11,'AssociationName_4',NULL,NULL,'AssociationName_4',17,NULL,12,80,'\0'),(12,'AssociationName_3',NULL,NULL,'AssociationName_3',16,NULL,12,75,'\0'),(13,'AssociationName_2',NULL,NULL,'AssociationName_2',15,NULL,12,74,'\0'),(14,'AssociationName_1',NULL,NULL,'AssociationName_1',14,NULL,12,69,'\0'),(15,'AssociationName_11',NULL,NULL,'AssociationName_11',1,NULL,12,47,'\0'),(16,'AssociationName_10',NULL,NULL,'AssociationName_10',2,NULL,12,50,'\0'),(17,'AssociationName_9',NULL,NULL,'AssociationName_9',3,NULL,12,53,'\0'),(18,'AssociationName_8',NULL,NULL,'AssociationName_8',4,NULL,12,56,'\0'),(19,'AssociationName_7',NULL,NULL,'AssociationName_7',5,NULL,12,59,'\0'),(20,'AssociationName_6',NULL,NULL,'AssociationName_6',6,NULL,12,60,'\0'),(21,'dateOfBirth',NULL,NULL,'dateOfBirth',7,NULL,12,62,'\0'),(22,'dateOfDeath',NULL,NULL,'dateOfDeath',8,NULL,12,63,'\0'),(23,'mothersMaidenName',NULL,NULL,'mothersMaidenName',9,NULL,12,64,'\0'),(24,'placeOfBirth',NULL,NULL,'placeOfBirth',10,NULL,12,65,'\0'),(25,'socialSecurityNumber',NULL,NULL,'socialSecurityNumber',11,NULL,12,66,'\0'),(26,'effectiveStartTimeStamp',NULL,NULL,'effectiveStartTimeStamp',12,NULL,12,67,'\0'),(27,'effectiveEndTimeStamp',NULL,NULL,'effectiveEndTimeStamp',13,NULL,12,68,'\0'),(28,'personUpi',NULL,NULL,'personUpi',2,NULL,16,83,'\0'),(29,'AssociationName_1',NULL,NULL,'AssociationName_1',1,NULL,16,82,'\0'),(30,'firstName',NULL,NULL,'firstName',7,NULL,17,43,'\0'),(31,'middleName',NULL,NULL,'middleName',6,NULL,17,42,'\0'),(32,'lastName',NULL,NULL,'lastName',5,NULL,17,41,'\0'),(33,'lastNameCompressed',NULL,NULL,'lastNameCompressed',4,NULL,17,40,'\0'),(34,'degree',NULL,NULL,'degree',3,NULL,17,39,'\0'),(35,'prefix',NULL,NULL,'prefix',2,NULL,17,38,'\0'),(36,'suffix',NULL,NULL,'suffix',1,NULL,17,37,'\0'),(37,'number',NULL,NULL,'number',2,NULL,18,78,'\0'),(38,'type',NULL,NULL,'type',1,NULL,18,77,'\0');

/*Table structure for table `dyextn_data_element` */

DROP TABLE IF EXISTS `dyextn_data_element`;

CREATE TABLE `dyextn_data_element` (
  `IDENTIFIER` bigint(20) NOT NULL auto_increment,
  `CATEGORY_ATTRIBUTE_ID` bigint(20) default NULL,
  `ATTRIBUTE_TYPE_INFO_ID` bigint(20) default NULL,
  PRIMARY KEY  (`IDENTIFIER`),
  KEY `FKB1153E4AA208204` (`ATTRIBUTE_TYPE_INFO_ID`),
  KEY `FKB1153E467F8B59` (`CATEGORY_ATTRIBUTE_ID`),
  CONSTRAINT `FKB1153E467F8B59` FOREIGN KEY (`CATEGORY_ATTRIBUTE_ID`) REFERENCES `dyextn_category_attribute` (`IDENTIFIER`),
  CONSTRAINT `FKB1153E4AA208204` FOREIGN KEY (`ATTRIBUTE_TYPE_INFO_ID`) REFERENCES `dyextn_attribute_type_info` (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `dyextn_data_element` */

/*Table structure for table `dyextn_data_grid` */

DROP TABLE IF EXISTS `dyextn_data_grid`;

CREATE TABLE `dyextn_data_grid` (
  `IDENTIFIER` bigint(20) NOT NULL,
  PRIMARY KEY  (`IDENTIFIER`),
  KEY `FK233EB73E40F198C2` (`IDENTIFIER`),
  CONSTRAINT `FK233EB73E40F198C2` FOREIGN KEY (`IDENTIFIER`) REFERENCES `dyextn_control` (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `dyextn_data_grid` */

/*Table structure for table `dyextn_database_properties` */

DROP TABLE IF EXISTS `dyextn_database_properties`;

CREATE TABLE `dyextn_database_properties` (
  `IDENTIFIER` bigint(20) NOT NULL auto_increment,
  `NAME` varchar(255) default NULL,
  PRIMARY KEY  (`IDENTIFIER`)
) ENGINE=InnoDB AUTO_INCREMENT=80 DEFAULT CHARSET=latin1;

/*Data for the table `dyextn_database_properties` */

insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (4,'DE_E_640'),(5,'IDENTIFIER'),(6,'DE_E_636'),(7,'IDENTIFIER'),(8,'DE_E_632'),(9,'IDENTIFIER'),(10,'DE_E_628'),(11,'IDENTIFIER'),(12,'DE_E_624'),(13,'IDENTIFIER'),(14,'DE_E_620'),(15,'IDENTIFIER'),(16,'DE_E_547'),(17,'DE_AS_724'),(18,'DE_AS_709'),(19,'DE_E_608'),(20,'IDENTIFIER'),(21,'DE_AS_694'),(22,'DE_AT_554'),(23,'DE_AT_553'),(24,'DE_AT_552'),(25,'DE_AT_551'),(26,'IDENTIFIER'),(27,'DE_E_555'),(28,'IDENTIFIER'),(29,'DE_E_559'),(30,'DE_AT_569'),(31,'DE_AT_568'),(32,'DE_AT_567'),(33,'DE_AT_566'),(34,'DE_AT_565'),(35,'DE_AT_564'),(36,'DE_AT_563'),(37,'IDENTIFIER'),(38,'DE_E_570'),(39,'DE_E_585'),(40,'DE_AS_714'),(41,'DE_E_616'),(42,'IDENTIFIER'),(43,'DE_AS_704'),(44,'DE_E_612'),(45,'IDENTIFIER'),(46,'DE_AS_699'),(47,'DE_E_604'),(48,'IDENTIFIER'),(49,'DE_AS_689'),(50,'DE_E_596'),(51,'IDENTIFIER'),(52,'DE_AS_684'),(53,'DE_AS_679'),(54,'IDENTIFIER'),(55,'DE_AT_589'),(56,'DE_AT_590'),(57,'DE_AT_591'),(58,'DE_AT_592'),(59,'DE_AT_593'),(60,'DE_AT_594'),(61,'DE_AT_595'),(62,'DE_AS_654'),(63,'DE_E_575'),(64,'DE_AS_719'),(65,'DE_AS_644'),(66,'IDENTIFIER'),(67,'DE_AS_659'),(68,'DE_AS_664'),(69,'DE_E_579'),(70,'DE_AT_584'),(71,'DE_AT_583'),(72,'IDENTIFIER'),(73,'DE_AS_669'),(74,'DE_AS_674'),(75,'DE_AS_649'),(76,'DE_AT_574'),(77,'IDENTIFIER'),(78,'DE_E_600'),(79,'IDENTIFIER');

/*Table structure for table `dyextn_date_concept_value` */

DROP TABLE IF EXISTS `dyextn_date_concept_value`;

CREATE TABLE `dyextn_date_concept_value` (
  `IDENTIFIER` bigint(20) NOT NULL,
  `VALUE` datetime default NULL,
  PRIMARY KEY  (`IDENTIFIER`),
  KEY `FK45F598A64641D513` (`IDENTIFIER`),
  CONSTRAINT `FK45F598A64641D513` FOREIGN KEY (`IDENTIFIER`) REFERENCES `dyextn_permissible_value` (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `dyextn_date_concept_value` */

insert into `dyextn_date_concept_value` (`IDENTIFIER`,`VALUE`) values (12,NULL),(13,NULL),(17,NULL),(18,NULL);

/*Table structure for table `dyextn_date_type_info` */

DROP TABLE IF EXISTS `dyextn_date_type_info`;

CREATE TABLE `dyextn_date_type_info` (
  `IDENTIFIER` bigint(20) NOT NULL,
  `FORMAT` varchar(255) default NULL,
  PRIMARY KEY  (`IDENTIFIER`),
  KEY `FKFBA549FE5294FA3` (`IDENTIFIER`),
  CONSTRAINT `FKFBA549FE5294FA3` FOREIGN KEY (`IDENTIFIER`) REFERENCES `dyextn_attribute_type_info` (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `dyextn_date_type_info` */

insert into `dyextn_date_type_info` (`IDENTIFIER`,`FORMAT`) values (27,'MM-dd-yyyy'),(28,'MM-dd-yyyy'),(32,'MM-dd-yyyy'),(33,'MM-dd-yyyy');

/*Table structure for table `dyextn_datepicker` */

DROP TABLE IF EXISTS `dyextn_datepicker`;

CREATE TABLE `dyextn_datepicker` (
  `IDENTIFIER` bigint(20) NOT NULL,
  `DATE_VALUE_TYPE` varchar(255) default NULL,
  PRIMARY KEY  (`IDENTIFIER`),
  KEY `FKFEADD19940F198C2` (`IDENTIFIER`),
  CONSTRAINT `FKFEADD19940F198C2` FOREIGN KEY (`IDENTIFIER`) REFERENCES `dyextn_control` (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `dyextn_datepicker` */

insert into `dyextn_datepicker` (`IDENTIFIER`,`DATE_VALUE_TYPE`) values (21,NULL),(22,NULL),(26,NULL),(27,NULL);

/*Table structure for table `dyextn_double_concept_value` */

DROP TABLE IF EXISTS `dyextn_double_concept_value`;

CREATE TABLE `dyextn_double_concept_value` (
  `IDENTIFIER` bigint(20) NOT NULL,
  `VALUE` double default NULL,
  PRIMARY KEY  (`IDENTIFIER`),
  KEY `FKB94E64494641D513` (`IDENTIFIER`),
  CONSTRAINT `FKB94E64494641D513` FOREIGN KEY (`IDENTIFIER`) REFERENCES `dyextn_permissible_value` (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `dyextn_double_concept_value` */

/*Table structure for table `dyextn_double_type_info` */

DROP TABLE IF EXISTS `dyextn_double_type_info`;

CREATE TABLE `dyextn_double_type_info` (
  `IDENTIFIER` bigint(20) NOT NULL,
  PRIMARY KEY  (`IDENTIFIER`),
  KEY `FKC83869C2BA4AE008` (`IDENTIFIER`),
  CONSTRAINT `FKC83869C2BA4AE008` FOREIGN KEY (`IDENTIFIER`) REFERENCES `dyextn_numeric_type_info` (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `dyextn_double_type_info` */

/*Table structure for table `dyextn_entity` */

DROP TABLE IF EXISTS `dyextn_entity`;

CREATE TABLE `dyextn_entity` (
  `IDENTIFIER` bigint(20) NOT NULL,
  `DATA_TABLE_STATE` int(11) default NULL,
  `ENTITY_GROUP_ID` bigint(20) default NULL,
  `IS_ABSTRACT` bit(1) default NULL,
  `PARENT_ENTITY_ID` bigint(20) default NULL,
  `INHERITANCE_STRATEGY` int(11) default NULL,
  `DISCRIMINATOR_COLUMN_NAME` varchar(255) default NULL,
  `DISCRIMINATOR_VALUE` varchar(255) default NULL,
  PRIMARY KEY  (`IDENTIFIER`),
  KEY `FK8B2436402264D629` (`PARENT_ENTITY_ID`),
  KEY `FK8B24364088C075EF` (`ENTITY_GROUP_ID`),
  KEY `FK8B243640743AC3F2` (`IDENTIFIER`),
  CONSTRAINT `FK8B243640743AC3F2` FOREIGN KEY (`IDENTIFIER`) REFERENCES `dyextn_abstract_entity` (`id`),
  CONSTRAINT `FK8B2436402264D629` FOREIGN KEY (`PARENT_ENTITY_ID`) REFERENCES `dyextn_entity` (`IDENTIFIER`),
  CONSTRAINT `FK8B24364088C075EF` FOREIGN KEY (`ENTITY_GROUP_ID`) REFERENCES `dyextn_entity_group` (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `dyextn_entity` */

insert into `dyextn_entity` (`IDENTIFIER`,`DATA_TABLE_STATE`,`ENTITY_GROUP_ID`,`IS_ABSTRACT`,`PARENT_ENTITY_ID`,`INHERITANCE_STRATEGY`,`DISCRIMINATOR_COLUMN_NAME`,`DISCRIMINATOR_VALUE`) values (11,2,10,'\0',NULL,3,NULL,NULL),(13,2,10,'\0',NULL,3,NULL,NULL),(15,2,10,'\0',NULL,3,NULL,NULL),(17,2,10,'\0',NULL,3,NULL,NULL),(19,2,10,'\0',NULL,3,NULL,NULL),(21,2,10,'\0',NULL,3,NULL,NULL),(23,2,10,'\0',NULL,3,NULL,NULL),(26,2,10,'\0',NULL,3,NULL,NULL),(34,2,10,'\0',NULL,3,NULL,NULL),(36,2,10,'\0',NULL,3,NULL,NULL),(45,2,10,'\0',NULL,3,NULL,NULL),(46,2,10,'\0',NULL,3,NULL,NULL),(48,2,10,'\0',NULL,3,NULL,NULL),(51,2,10,'\0',NULL,3,NULL,NULL),(54,2,10,'\0',NULL,3,NULL,NULL),(57,2,10,'\0',NULL,3,NULL,NULL),(70,2,10,'\0',NULL,3,NULL,NULL),(76,2,10,'\0',NULL,3,NULL,NULL),(85,2,10,'\0',NULL,3,NULL,NULL);

/*Table structure for table `dyextn_entity_group` */

DROP TABLE IF EXISTS `dyextn_entity_group`;

CREATE TABLE `dyextn_entity_group` (
  `IDENTIFIER` bigint(20) NOT NULL,
  `LONG_NAME` varchar(255) default NULL,
  `SHORT_NAME` varchar(255) default NULL,
  `VERSION` varchar(255) default NULL,
  `IS_SYSTEM_GENERATED` bit(1) default NULL,
  PRIMARY KEY  (`IDENTIFIER`),
  KEY `FK105DE7A0728B19BE` (`IDENTIFIER`),
  CONSTRAINT `FK105DE7A0728B19BE` FOREIGN KEY (`IDENTIFIER`) REFERENCES `dyextn_abstract_metadata` (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `dyextn_entity_group` */

insert into `dyextn_entity_group` (`IDENTIFIER`,`LONG_NAME`,`SHORT_NAME`,`VERSION`,`IS_SYSTEM_GENERATED`) values (10,'cider','cider',NULL,'\0');

/*Table structure for table `dyextn_entity_map` */

DROP TABLE IF EXISTS `dyextn_entity_map`;

CREATE TABLE `dyextn_entity_map` (
  `IDENTIFIER` bigint(20) NOT NULL auto_increment,
  `CONTAINER_ID` bigint(20) default NULL,
  `STATUS` varchar(10) default NULL,
  `STATIC_ENTITY_ID` bigint(20) default NULL,
  `CREATED_DATE` date default NULL,
  `CREATED_BY` varchar(255) default NULL,
  PRIMARY KEY  (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `dyextn_entity_map` */

/*Table structure for table `dyextn_entity_map_condns` */

DROP TABLE IF EXISTS `dyextn_entity_map_condns`;

CREATE TABLE `dyextn_entity_map_condns` (
  `IDENTIFIER` bigint(20) NOT NULL auto_increment,
  `STATIC_RECORD_ID` bigint(20) default NULL,
  `TYPE_ID` bigint(20) default NULL,
  `FORM_CONTEXT_ID` bigint(20) default NULL,
  PRIMARY KEY  (`IDENTIFIER`),
  KEY `FK2A9D6029CFA08B13` (`FORM_CONTEXT_ID`),
  CONSTRAINT `FK2A9D6029CFA08B13` FOREIGN KEY (`FORM_CONTEXT_ID`) REFERENCES `dyextn_form_context` (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `dyextn_entity_map_condns` */

/*Table structure for table `dyextn_entity_map_record` */

DROP TABLE IF EXISTS `dyextn_entity_map_record`;

CREATE TABLE `dyextn_entity_map_record` (
  `IDENTIFIER` bigint(20) NOT NULL auto_increment,
  `FORM_CONTEXT_ID` bigint(20) default NULL,
  `STATIC_ENTITY_RECORD_ID` bigint(20) default NULL,
  `STATUS` varchar(10) default NULL,
  `DYNAMIC_ENTITY_RECORD_ID` bigint(20) default NULL,
  `MODIFIED_DATE` date default NULL,
  `CREATED_DATE` date default NULL,
  `CREATED_BY` varchar(255) default NULL,
  PRIMARY KEY  (`IDENTIFIER`),
  KEY `FK43A45013CFA08B13` (`FORM_CONTEXT_ID`),
  CONSTRAINT `FK43A45013CFA08B13` FOREIGN KEY (`FORM_CONTEXT_ID`) REFERENCES `dyextn_form_context` (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `dyextn_entity_map_record` */

/*Table structure for table `dyextn_file_extensions` */

DROP TABLE IF EXISTS `dyextn_file_extensions`;

CREATE TABLE `dyextn_file_extensions` (
  `IDENTIFIER` bigint(20) NOT NULL auto_increment,
  `FILE_EXTENSION` varchar(255) default NULL,
  `ATTRIBUTE_ID` bigint(20) default NULL,
  PRIMARY KEY  (`IDENTIFIER`),
  KEY `FKD49834FA56AF0834` (`ATTRIBUTE_ID`),
  CONSTRAINT `FKD49834FA56AF0834` FOREIGN KEY (`ATTRIBUTE_ID`) REFERENCES `dyextn_file_type_info` (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `dyextn_file_extensions` */

/*Table structure for table `dyextn_file_type_info` */

DROP TABLE IF EXISTS `dyextn_file_type_info`;

CREATE TABLE `dyextn_file_type_info` (
  `IDENTIFIER` bigint(20) NOT NULL,
  `MAX_FILE_SIZE` float default NULL,
  PRIMARY KEY  (`IDENTIFIER`),
  KEY `FKA00F0EDE5294FA3` (`IDENTIFIER`),
  CONSTRAINT `FKA00F0EDE5294FA3` FOREIGN KEY (`IDENTIFIER`) REFERENCES `dyextn_attribute_type_info` (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `dyextn_file_type_info` */

/*Table structure for table `dyextn_file_upload` */

DROP TABLE IF EXISTS `dyextn_file_upload`;

CREATE TABLE `dyextn_file_upload` (
  `IDENTIFIER` bigint(20) NOT NULL,
  `NO_OF_COLUMNS` int(11) default NULL,
  PRIMARY KEY  (`IDENTIFIER`),
  KEY `FK2FAD41E740F198C2` (`IDENTIFIER`),
  CONSTRAINT `FK2FAD41E740F198C2` FOREIGN KEY (`IDENTIFIER`) REFERENCES `dyextn_control` (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `dyextn_file_upload` */

/*Table structure for table `dyextn_float_concept_value` */

DROP TABLE IF EXISTS `dyextn_float_concept_value`;

CREATE TABLE `dyextn_float_concept_value` (
  `IDENTIFIER` bigint(20) NOT NULL,
  `VALUE` float default NULL,
  PRIMARY KEY  (`IDENTIFIER`),
  KEY `FK6785309A4641D513` (`IDENTIFIER`),
  CONSTRAINT `FK6785309A4641D513` FOREIGN KEY (`IDENTIFIER`) REFERENCES `dyextn_permissible_value` (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `dyextn_float_concept_value` */

/*Table structure for table `dyextn_float_type_info` */

DROP TABLE IF EXISTS `dyextn_float_type_info`;

CREATE TABLE `dyextn_float_type_info` (
  `IDENTIFIER` bigint(20) NOT NULL,
  PRIMARY KEY  (`IDENTIFIER`),
  KEY `FK7E1C0693BA4AE008` (`IDENTIFIER`),
  CONSTRAINT `FK7E1C0693BA4AE008` FOREIGN KEY (`IDENTIFIER`) REFERENCES `dyextn_numeric_type_info` (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `dyextn_float_type_info` */

/*Table structure for table `dyextn_form_context` */

DROP TABLE IF EXISTS `dyextn_form_context`;

CREATE TABLE `dyextn_form_context` (
  `IDENTIFIER` bigint(20) NOT NULL auto_increment,
  `IS_INFINITE_ENTRY` bit(1) default NULL,
  `ENTITY_MAP_ID` bigint(20) default NULL,
  `STUDY_FORM_LABEL` varchar(255) default NULL,
  `NO_OF_ENTRIES` int(11) default NULL,
  PRIMARY KEY  (`IDENTIFIER`),
  KEY `FKE56CCDB12B784475` (`ENTITY_MAP_ID`),
  CONSTRAINT `FKE56CCDB12B784475` FOREIGN KEY (`ENTITY_MAP_ID`) REFERENCES `dyextn_entity_map` (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `dyextn_form_context` */

/*Table structure for table `dyextn_id_generator` */

DROP TABLE IF EXISTS `dyextn_id_generator`;

CREATE TABLE `dyextn_id_generator` (
  `id` bigint(20) NOT NULL,
  `next_available_id` bigint(20) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `dyextn_id_generator` */

insert into `dyextn_id_generator` (`id`,`next_available_id`) values (1,729);

/*Table structure for table `dyextn_integer_concept_value` */

DROP TABLE IF EXISTS `dyextn_integer_concept_value`;

CREATE TABLE `dyextn_integer_concept_value` (
  `IDENTIFIER` bigint(20) NOT NULL,
  `VALUE` int(11) default NULL,
  PRIMARY KEY  (`IDENTIFIER`),
  KEY `FKFBA33B3C4641D513` (`IDENTIFIER`),
  CONSTRAINT `FKFBA33B3C4641D513` FOREIGN KEY (`IDENTIFIER`) REFERENCES `dyextn_permissible_value` (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `dyextn_integer_concept_value` */

/*Table structure for table `dyextn_integer_type_info` */

DROP TABLE IF EXISTS `dyextn_integer_type_info`;

CREATE TABLE `dyextn_integer_type_info` (
  `IDENTIFIER` bigint(20) NOT NULL,
  PRIMARY KEY  (`IDENTIFIER`),
  KEY `FK5F9CB235BA4AE008` (`IDENTIFIER`),
  CONSTRAINT `FK5F9CB235BA4AE008` FOREIGN KEY (`IDENTIFIER`) REFERENCES `dyextn_numeric_type_info` (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `dyextn_integer_type_info` */

/*Table structure for table `dyextn_list_box` */

DROP TABLE IF EXISTS `dyextn_list_box`;

CREATE TABLE `dyextn_list_box` (
  `IDENTIFIER` bigint(20) NOT NULL,
  `NO_OF_ROWS` int(11) default NULL,
  `MULTISELECT` bit(1) default NULL,
  PRIMARY KEY  (`IDENTIFIER`),
  KEY `FK208395A7BF67AB26` (`IDENTIFIER`),
  CONSTRAINT `FK208395A7BF67AB26` FOREIGN KEY (`IDENTIFIER`) REFERENCES `dyextn_select_control` (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `dyextn_list_box` */

/*Table structure for table `dyextn_long_concept_value` */

DROP TABLE IF EXISTS `dyextn_long_concept_value`;

CREATE TABLE `dyextn_long_concept_value` (
  `IDENTIFIER` bigint(20) NOT NULL,
  `VALUE` bigint(20) default NULL,
  PRIMARY KEY  (`IDENTIFIER`),
  KEY `FK3E1A6EF44641D513` (`IDENTIFIER`),
  CONSTRAINT `FK3E1A6EF44641D513` FOREIGN KEY (`IDENTIFIER`) REFERENCES `dyextn_permissible_value` (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `dyextn_long_concept_value` */

/*Table structure for table `dyextn_long_type_info` */

DROP TABLE IF EXISTS `dyextn_long_type_info`;

CREATE TABLE `dyextn_long_type_info` (
  `IDENTIFIER` bigint(20) NOT NULL,
  PRIMARY KEY  (`IDENTIFIER`),
  KEY `FK257281EDBA4AE008` (`IDENTIFIER`),
  CONSTRAINT `FK257281EDBA4AE008` FOREIGN KEY (`IDENTIFIER`) REFERENCES `dyextn_numeric_type_info` (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `dyextn_long_type_info` */

insert into `dyextn_long_type_info` (`IDENTIFIER`) values (1),(2),(3),(4),(5),(6),(7),(12),(13),(21),(22),(23),(24),(25),(26),(34),(37),(39),(40);

/*Table structure for table `dyextn_numeric_type_info` */

DROP TABLE IF EXISTS `dyextn_numeric_type_info`;

CREATE TABLE `dyextn_numeric_type_info` (
  `IDENTIFIER` bigint(20) NOT NULL,
  `MEASUREMENT_UNITS` varchar(255) default NULL,
  `DECIMAL_PLACES` int(11) default NULL,
  `NO_DIGITS` int(11) default NULL,
  PRIMARY KEY  (`IDENTIFIER`),
  KEY `FK4DEC9544E5294FA3` (`IDENTIFIER`),
  CONSTRAINT `FK4DEC9544E5294FA3` FOREIGN KEY (`IDENTIFIER`) REFERENCES `dyextn_attribute_type_info` (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `dyextn_numeric_type_info` */

insert into `dyextn_numeric_type_info` (`IDENTIFIER`,`MEASUREMENT_UNITS`,`DECIMAL_PLACES`,`NO_DIGITS`) values (1,NULL,0,NULL),(2,NULL,0,NULL),(3,NULL,0,NULL),(4,NULL,0,NULL),(5,NULL,0,NULL),(6,NULL,0,NULL),(7,NULL,0,NULL),(12,NULL,0,NULL),(13,NULL,0,NULL),(21,NULL,0,NULL),(22,NULL,0,NULL),(23,NULL,0,NULL),(24,NULL,0,NULL),(25,NULL,0,NULL),(26,NULL,0,NULL),(34,NULL,0,NULL),(37,NULL,0,NULL),(39,NULL,0,NULL),(40,NULL,0,NULL);

/*Table structure for table `dyextn_object_type_info` */

DROP TABLE IF EXISTS `dyextn_object_type_info`;

CREATE TABLE `dyextn_object_type_info` (
  `IDENTIFIER` bigint(20) NOT NULL,
  PRIMARY KEY  (`IDENTIFIER`),
  KEY `FK74819FB0E5294FA3` (`IDENTIFIER`),
  CONSTRAINT `FK74819FB0E5294FA3` FOREIGN KEY (`IDENTIFIER`) REFERENCES `dyextn_attribute_type_info` (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `dyextn_object_type_info` */

/*Table structure for table `dyextn_path` */

DROP TABLE IF EXISTS `dyextn_path`;

CREATE TABLE `dyextn_path` (
  `IDENTIFIER` bigint(20) NOT NULL auto_increment,
  `CATEGORY_ENTITY_ID` bigint(20) default NULL,
  PRIMARY KEY  (`IDENTIFIER`),
  KEY `FKC26ADC2854AC01B` (`CATEGORY_ENTITY_ID`),
  CONSTRAINT `FKC26ADC2854AC01B` FOREIGN KEY (`CATEGORY_ENTITY_ID`) REFERENCES `dyextn_category_entity` (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `dyextn_path` */

/*Table structure for table `dyextn_path_asso_rel` */

DROP TABLE IF EXISTS `dyextn_path_asso_rel`;

CREATE TABLE `dyextn_path_asso_rel` (
  `IDENTIFIER` bigint(20) NOT NULL auto_increment,
  `PATH_ID` bigint(20) default NULL,
  `ASSOCIATION_ID` bigint(20) default NULL,
  `PATH_SEQUENCE_NUMBER` int(11) default NULL,
  `SRC_INSTANCE_ID` bigint(20) default NULL,
  `TGT_INSTANCE_ID` bigint(20) default NULL,
  PRIMARY KEY  (`IDENTIFIER`),
  KEY `FK1E1260A57EE87FF6` (`ASSOCIATION_ID`),
  KEY `FK1E1260A580C8F93E` (`PATH_ID`),
  CONSTRAINT `FK1E1260A580C8F93E` FOREIGN KEY (`PATH_ID`) REFERENCES `dyextn_path` (`IDENTIFIER`),
  CONSTRAINT `FK1E1260A57EE87FF6` FOREIGN KEY (`ASSOCIATION_ID`) REFERENCES `dyextn_association` (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `dyextn_path_asso_rel` */

/*Table structure for table `dyextn_permissible_value` */

DROP TABLE IF EXISTS `dyextn_permissible_value`;

CREATE TABLE `dyextn_permissible_value` (
  `IDENTIFIER` bigint(20) NOT NULL auto_increment,
  `DESCRIPTION` varchar(255) default NULL,
  `CATEGORY_ATTRIBUTE_ID` bigint(20) default NULL,
  `ATTRIBUTE_TYPE_INFO_ID` bigint(20) default NULL,
  PRIMARY KEY  (`IDENTIFIER`),
  KEY `FK136264E0AA208204` (`ATTRIBUTE_TYPE_INFO_ID`),
  KEY `FK136264E067F8B59` (`CATEGORY_ATTRIBUTE_ID`),
  CONSTRAINT `FK136264E067F8B59` FOREIGN KEY (`CATEGORY_ATTRIBUTE_ID`) REFERENCES `dyextn_category_attribute` (`IDENTIFIER`),
  CONSTRAINT `FK136264E0AA208204` FOREIGN KEY (`ATTRIBUTE_TYPE_INFO_ID`) REFERENCES `dyextn_attribute_type_info` (`IDENTIFIER`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=latin1;

/*Data for the table `dyextn_permissible_value` */

insert into `dyextn_permissible_value` (`IDENTIFIER`,`DESCRIPTION`,`CATEGORY_ATTRIBUTE_ID`,`ATTRIBUTE_TYPE_INFO_ID`) values (1,NULL,NULL,8),(2,NULL,NULL,9),(3,NULL,NULL,10),(4,NULL,NULL,11),(5,NULL,NULL,14),(6,NULL,NULL,15),(7,NULL,NULL,16),(8,NULL,NULL,17),(9,NULL,NULL,18),(10,NULL,NULL,19),(11,NULL,NULL,20),(12,NULL,NULL,27),(13,NULL,NULL,28),(14,NULL,NULL,29),(15,NULL,NULL,30),(16,NULL,NULL,31),(17,NULL,NULL,32),(18,NULL,NULL,33),(19,NULL,NULL,35),(20,NULL,NULL,36),(21,NULL,NULL,38);

/*Table structure for table `dyextn_primitive_attribute` */

DROP TABLE IF EXISTS `dyextn_primitive_attribute`;

CREATE TABLE `dyextn_primitive_attribute` (
  `IDENTIFIER` bigint(20) NOT NULL,
  `IS_IDENTIFIED` bit(1) default NULL,
  `IS_PRIMARY_KEY` bit(1) default NULL,
  `IS_NULLABLE` bit(1) default NULL,
  PRIMARY KEY  (`IDENTIFIER`),
  KEY `FKA9F765C76D19A21F` (`IDENTIFIER`),
  CONSTRAINT `FKA9F765C76D19A21F` FOREIGN KEY (`IDENTIFIER`) REFERENCES `dyextn_attribute` (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `dyextn_primitive_attribute` */

insert into `dyextn_primitive_attribute` (`IDENTIFIER`,`IS_IDENTIFIED`,`IS_PRIMARY_KEY`,`IS_NULLABLE`) values (12,NULL,'','\0'),(14,NULL,'','\0'),(16,NULL,'','\0'),(18,NULL,'','\0'),(20,NULL,'','\0'),(22,NULL,'','\0'),(27,NULL,'','\0'),(29,'\0','\0',''),(30,'\0','\0',''),(31,'\0','\0',''),(32,'\0','\0',''),(33,NULL,'','\0'),(35,NULL,'','\0'),(37,'\0','\0',''),(38,'\0','\0',''),(39,'\0','\0',''),(40,'\0','\0',''),(41,'\0','\0',''),(42,'\0','\0',''),(43,'\0','\0',''),(44,NULL,'','\0'),(49,NULL,'','\0'),(52,NULL,'','\0'),(55,NULL,'','\0'),(58,NULL,'','\0'),(61,NULL,'','\0'),(62,'\0','\0',''),(63,'\0','\0',''),(64,'\0','\0',''),(65,'\0','\0',''),(66,'\0','\0',''),(67,'\0','\0',''),(68,'\0','\0',''),(73,NULL,'','\0'),(77,'\0','\0',''),(78,'\0','\0',''),(79,NULL,'','\0'),(83,'\0','\0',''),(84,NULL,'','\0'),(86,NULL,'','\0');

/*Table structure for table `dyextn_radiobutton` */

DROP TABLE IF EXISTS `dyextn_radiobutton`;

CREATE TABLE `dyextn_radiobutton` (
  `IDENTIFIER` bigint(20) NOT NULL,
  PRIMARY KEY  (`IDENTIFIER`),
  KEY `FK16F5BA9040F198C2` (`IDENTIFIER`),
  CONSTRAINT `FK16F5BA9040F198C2` FOREIGN KEY (`IDENTIFIER`) REFERENCES `dyextn_control` (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `dyextn_radiobutton` */

/*Table structure for table `dyextn_role` */

DROP TABLE IF EXISTS `dyextn_role`;

CREATE TABLE `dyextn_role` (
  `IDENTIFIER` bigint(20) NOT NULL auto_increment,
  `ASSOCIATION_TYPE` varchar(255) default NULL,
  `MAX_CARDINALITY` int(11) default NULL,
  `MIN_CARDINALITY` int(11) default NULL,
  `NAME` varchar(255) default NULL,
  PRIMARY KEY  (`IDENTIFIER`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=latin1;

/*Data for the table `dyextn_role` */

insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (1,'ASSOCIATION',100,0,NULL),(2,'ASSOCIATION',1,0,'state'),(3,'ASSOCIATION',100,0,NULL),(4,'ASSOCIATION',1,0,'addressType'),(5,'ASSOCIATION',100,0,NULL),(6,'ASSOCIATION',1,0,'country'),(7,'ASSOCIATION',100,0,NULL),(8,'ASSOCIATION',100,0,'ethinicOriginCollection'),(9,'ASSOCIATION',100,0,NULL),(10,'ASSOCIATION',1,0,'gender'),(11,'ASSOCIATION',100,0,NULL),(12,'ASSOCIATION',1,0,'advancedDirectiveExists'),(13,'ASSOCIATION',100,0,NULL),(14,'ASSOCIATION',1,0,'religion'),(15,'ASSOCIATION',100,0,NULL),(16,'ASSOCIATION',100,0,'raceCollection'),(17,'ASSOCIATION',100,0,NULL),(18,'ASSOCIATION',1,0,'maritalStatus'),(19,'ASSOCIATION',1,1,NULL),(20,'ASSOCIATION',1,1,'personName'),(21,'ASSOCIATION',100,0,NULL),(22,'ASSOCIATION',1,1,'relationToPerson'),(23,'ASSOCIATION',100,0,NULL),(24,'ASSOCIATION',1,1,'associatedPerson'),(25,'ASSOCIATION',100,0,NULL),(26,'ASSOCIATION',100,0,'associatedPersonCollection'),(27,'ASSOCIATION',100,0,NULL),(28,'ASSOCIATION',1,0,'nextOfKin'),(29,'ASSOCIATION',100,0,NULL),(30,'ASSOCIATION',100,0,'phoneCollection'),(31,'ASSOCIATION',1,1,NULL),(32,'ASSOCIATION',100,0,'addressCollection'),(33,'ASSOCIATION',1,1,NULL),(34,'ASSOCIATION',100,1,'demographicsCollection');

/*Table structure for table `dyextn_rule` */

DROP TABLE IF EXISTS `dyextn_rule`;

CREATE TABLE `dyextn_rule` (
  `IDENTIFIER` bigint(20) NOT NULL auto_increment,
  `NAME` varchar(255) default NULL,
  `IS_IMPLICIT` bit(1) default NULL,
  `CATEGORY_ATTR_ID` bigint(20) default NULL,
  `ATTRIBUTE_ID` bigint(20) default NULL,
  PRIMARY KEY  (`IDENTIFIER`),
  KEY `FKC27E099F8DDEC02` (`CATEGORY_ATTR_ID`),
  KEY `FKC27E09990F96714` (`ATTRIBUTE_ID`),
  CONSTRAINT `FKC27E09990F96714` FOREIGN KEY (`ATTRIBUTE_ID`) REFERENCES `dyextn_attribute` (`IDENTIFIER`),
  CONSTRAINT `FKC27E099F8DDEC02` FOREIGN KEY (`CATEGORY_ATTR_ID`) REFERENCES `dyextn_category_attribute` (`IDENTIFIER`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=latin1;

/*Data for the table `dyextn_rule` */

insert into `dyextn_rule` (`IDENTIFIER`,`NAME`,`IS_IMPLICIT`,`CATEGORY_ATTR_ID`,`ATTRIBUTE_ID`) values (1,'textLength','',NULL,29),(2,'textLength','',NULL,30),(3,'textLength','',NULL,31),(4,'textLength','',NULL,32),(5,'textLength','',NULL,37),(6,'textLength','',NULL,38),(7,'textLength','',NULL,39),(8,'textLength','',NULL,40),(9,'textLength','',NULL,41),(10,'textLength','',NULL,42),(11,'textLength','',NULL,43),(12,'date','',NULL,62),(13,'date','',NULL,63),(14,'textLength','',NULL,64),(15,'textLength','',NULL,65),(16,'textLength','',NULL,66),(17,'date','',NULL,67),(18,'date','',NULL,68),(19,'textLength','',NULL,77),(20,'textLength','',NULL,78),(21,'textLength','',NULL,83);

/*Table structure for table `dyextn_rule_parameter` */

DROP TABLE IF EXISTS `dyextn_rule_parameter`;

CREATE TABLE `dyextn_rule_parameter` (
  `IDENTIFIER` bigint(20) NOT NULL auto_increment,
  `NAME` varchar(255) default NULL,
  `VALUE` varchar(255) default NULL,
  `RULE_ID` bigint(20) default NULL,
  PRIMARY KEY  (`IDENTIFIER`),
  KEY `FK2256736395D4A5AE` (`RULE_ID`),
  CONSTRAINT `FK2256736395D4A5AE` FOREIGN KEY (`RULE_ID`) REFERENCES `dyextn_rule` (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `dyextn_rule_parameter` */

/*Table structure for table `dyextn_select_control` */

DROP TABLE IF EXISTS `dyextn_select_control`;

CREATE TABLE `dyextn_select_control` (
  `IDENTIFIER` bigint(20) NOT NULL,
  `SEPARATOR_STRING` varchar(255) default NULL,
  PRIMARY KEY  (`IDENTIFIER`),
  KEY `FKDFEBB65740F198C2` (`IDENTIFIER`),
  CONSTRAINT `FKDFEBB65740F198C2` FOREIGN KEY (`IDENTIFIER`) REFERENCES `dyextn_control` (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `dyextn_select_control` */

insert into `dyextn_select_control` (`IDENTIFIER`,`SEPARATOR_STRING`) values (5,NULL),(6,NULL),(7,NULL),(8,NULL),(9,NULL),(10,NULL),(11,NULL),(12,NULL),(13,NULL),(14,NULL),(15,NULL),(16,NULL),(17,NULL),(18,NULL),(19,NULL),(20,NULL),(29,NULL);

/*Table structure for table `dyextn_semantic_property` */

DROP TABLE IF EXISTS `dyextn_semantic_property`;

CREATE TABLE `dyextn_semantic_property` (
  `IDENTIFIER` bigint(20) NOT NULL auto_increment,
  `CONCEPT_CODE` varchar(255) default NULL,
  `TERM` varchar(255) default NULL,
  `THESAURAS_NAME` varchar(255) default NULL,
  `SEQUENCE_NUMBER` int(11) default NULL,
  `CONCEPT_DEFINITION` varchar(255) default NULL,
  `ABSTRACT_METADATA_ID` bigint(20) default NULL,
  `ABSTRACT_VALUE_ID` bigint(20) default NULL,
  PRIMARY KEY  (`IDENTIFIER`),
  KEY `FKD2A0B5B19AEB0CA3` (`ABSTRACT_METADATA_ID`),
  KEY `FKD2A0B5B15EB60E90` (`ABSTRACT_VALUE_ID`),
  CONSTRAINT `FKD2A0B5B15EB60E90` FOREIGN KEY (`ABSTRACT_VALUE_ID`) REFERENCES `dyextn_permissible_value` (`IDENTIFIER`),
  CONSTRAINT `FKD2A0B5B19AEB0CA3` FOREIGN KEY (`ABSTRACT_METADATA_ID`) REFERENCES `dyextn_abstract_metadata` (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `dyextn_semantic_property` */

/*Table structure for table `dyextn_short_concept_value` */

DROP TABLE IF EXISTS `dyextn_short_concept_value`;

CREATE TABLE `dyextn_short_concept_value` (
  `IDENTIFIER` bigint(20) NOT NULL,
  `VALUE` smallint(6) default NULL,
  PRIMARY KEY  (`IDENTIFIER`),
  KEY `FKC1945ABA4641D513` (`IDENTIFIER`),
  CONSTRAINT `FKC1945ABA4641D513` FOREIGN KEY (`IDENTIFIER`) REFERENCES `dyextn_permissible_value` (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `dyextn_short_concept_value` */

/*Table structure for table `dyextn_short_type_info` */

DROP TABLE IF EXISTS `dyextn_short_type_info`;

CREATE TABLE `dyextn_short_type_info` (
  `IDENTIFIER` bigint(20) NOT NULL,
  PRIMARY KEY  (`IDENTIFIER`),
  KEY `FK99540B3BA4AE008` (`IDENTIFIER`),
  CONSTRAINT `FK99540B3BA4AE008` FOREIGN KEY (`IDENTIFIER`) REFERENCES `dyextn_numeric_type_info` (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `dyextn_short_type_info` */

/*Table structure for table `dyextn_sql_audit` */

DROP TABLE IF EXISTS `dyextn_sql_audit`;

CREATE TABLE `dyextn_sql_audit` (
  `IDENTIFIER` bigint(20) NOT NULL auto_increment,
  `AUDIT_DATE` date default NULL,
  `QUERY_EXECUTED` text,
  `USER_ID` bigint(20) default NULL,
  PRIMARY KEY  (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `dyextn_sql_audit` */

/*Table structure for table `dyextn_string_concept_value` */

DROP TABLE IF EXISTS `dyextn_string_concept_value`;

CREATE TABLE `dyextn_string_concept_value` (
  `IDENTIFIER` bigint(20) NOT NULL,
  `VALUE` varchar(255) default NULL,
  PRIMARY KEY  (`IDENTIFIER`),
  KEY `FKADE7D8894641D513` (`IDENTIFIER`),
  CONSTRAINT `FKADE7D8894641D513` FOREIGN KEY (`IDENTIFIER`) REFERENCES `dyextn_permissible_value` (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `dyextn_string_concept_value` */

insert into `dyextn_string_concept_value` (`IDENTIFIER`,`VALUE`) values (1,''),(2,''),(3,''),(4,''),(5,''),(6,''),(7,''),(8,''),(9,''),(10,''),(11,''),(14,''),(15,''),(16,''),(19,''),(20,''),(21,'');

/*Table structure for table `dyextn_string_type_info` */

DROP TABLE IF EXISTS `dyextn_string_type_info`;

CREATE TABLE `dyextn_string_type_info` (
  `IDENTIFIER` bigint(20) NOT NULL,
  `MAX_SIZE` int(11) default NULL,
  PRIMARY KEY  (`IDENTIFIER`),
  KEY `FKDA35FE02E5294FA3` (`IDENTIFIER`),
  CONSTRAINT `FKDA35FE02E5294FA3` FOREIGN KEY (`IDENTIFIER`) REFERENCES `dyextn_attribute_type_info` (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `dyextn_string_type_info` */

insert into `dyextn_string_type_info` (`IDENTIFIER`,`MAX_SIZE`) values (8,255),(9,255),(10,255),(11,255),(14,255),(15,255),(16,255),(17,255),(18,255),(19,255),(20,255),(29,255),(30,255),(31,255),(35,255),(36,255),(38,255);

/*Table structure for table `dyextn_table_properties` */

DROP TABLE IF EXISTS `dyextn_table_properties`;

CREATE TABLE `dyextn_table_properties` (
  `IDENTIFIER` bigint(20) NOT NULL,
  `CONSTRAINT_NAME` varchar(255) default NULL,
  `ABSTRACT_ENTITY_ID` bigint(20) default NULL,
  PRIMARY KEY  (`IDENTIFIER`),
  KEY `FKE608E0811DCC9E63` (`ABSTRACT_ENTITY_ID`),
  KEY `FKE608E0813AB6A1D3` (`IDENTIFIER`),
  CONSTRAINT `FKE608E0813AB6A1D3` FOREIGN KEY (`IDENTIFIER`) REFERENCES `dyextn_database_properties` (`IDENTIFIER`),
  CONSTRAINT `FKE608E0811DCC9E63` FOREIGN KEY (`ABSTRACT_ENTITY_ID`) REFERENCES `dyextn_abstract_entity` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `dyextn_table_properties` */

insert into `dyextn_table_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`ABSTRACT_ENTITY_ID`) values (4,'CONSRT_641',11),(6,'CONSRT_637',13),(8,'CONSRT_633',15),(10,'CONSRT_629',17),(12,'CONSRT_625',19),(14,'CONSRT_621',21),(16,'CONSRT_548',23),(19,'CONSRT_609',26),(27,'CONSRT_556',34),(29,'CONSRT_560',36),(38,'CONSRT_571',45),(39,'CONSRT_586',46),(41,'CONSRT_617',48),(44,'CONSRT_613',51),(47,'CONSRT_605',54),(50,'CONSRT_597',57),(63,'CONSRT_576',70),(69,'CONSRT_580',76),(78,'CONSRT_601',85);

/*Table structure for table `dyextn_tagged_value` */

DROP TABLE IF EXISTS `dyextn_tagged_value`;

CREATE TABLE `dyextn_tagged_value` (
  `IDENTIFIER` bigint(20) NOT NULL auto_increment,
  `T_KEY` varchar(255) default NULL,
  `T_VALUE` varchar(255) default NULL,
  `ABSTRACT_METADATA_ID` bigint(20) default NULL,
  PRIMARY KEY  (`IDENTIFIER`),
  KEY `FKF79D055B9AEB0CA3` (`ABSTRACT_METADATA_ID`),
  CONSTRAINT `FKF79D055B9AEB0CA3` FOREIGN KEY (`ABSTRACT_METADATA_ID`) REFERENCES `dyextn_abstract_metadata` (`IDENTIFIER`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=latin1;

/*Data for the table `dyextn_tagged_value` */

insert into `dyextn_tagged_value` (`IDENTIFIER`,`T_KEY`,`T_VALUE`,`ABSTRACT_METADATA_ID`) values (10,'MetadataEntityGroup','MetadataEntityGroup',10),(11,'caB2BEntityGroup','caB2BEntityGroup',10),(12,'PackageName','edu.wustl.cider.domain.discrete.demographics',10);

/*Table structure for table `dyextn_textarea` */

DROP TABLE IF EXISTS `dyextn_textarea`;

CREATE TABLE `dyextn_textarea` (
  `IDENTIFIER` bigint(20) NOT NULL,
  `TEXTAREA_COLUMNS` int(11) default NULL,
  `TEXTAREA_ROWS` int(11) default NULL,
  PRIMARY KEY  (`IDENTIFIER`),
  KEY `FK946EE25740F198C2` (`IDENTIFIER`),
  CONSTRAINT `FK946EE25740F198C2` FOREIGN KEY (`IDENTIFIER`) REFERENCES `dyextn_control` (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `dyextn_textarea` */

/*Table structure for table `dyextn_textfield` */

DROP TABLE IF EXISTS `dyextn_textfield`;

CREATE TABLE `dyextn_textfield` (
  `IDENTIFIER` bigint(20) NOT NULL,
  `NO_OF_COLUMNS` int(11) default NULL,
  `IS_PASSWORD` bit(1) default NULL,
  `IS_URL` bit(1) default NULL,
  PRIMARY KEY  (`IDENTIFIER`),
  KEY `FKF9AFC85040F198C2` (`IDENTIFIER`),
  CONSTRAINT `FKF9AFC85040F198C2` FOREIGN KEY (`IDENTIFIER`) REFERENCES `dyextn_control` (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `dyextn_textfield` */

insert into `dyextn_textfield` (`IDENTIFIER`,`NO_OF_COLUMNS`,`IS_PASSWORD`,`IS_URL`) values (1,20,'\0','\0'),(2,20,'\0','\0'),(3,20,'\0','\0'),(4,20,'\0','\0'),(23,20,'\0','\0'),(24,20,'\0','\0'),(25,20,'\0','\0'),(28,20,'\0','\0'),(30,20,'\0','\0'),(31,20,'\0','\0'),(32,20,'\0','\0'),(33,20,'\0','\0'),(34,20,'\0','\0'),(35,20,'\0','\0'),(36,20,'\0','\0'),(37,20,'\0','\0'),(38,20,'\0','\0');

/*Table structure for table `dyextn_userdef_de_value_rel` */

DROP TABLE IF EXISTS `dyextn_userdef_de_value_rel`;

CREATE TABLE `dyextn_userdef_de_value_rel` (
  `USER_DEF_DE_ID` bigint(20) NOT NULL,
  `PERMISSIBLE_VALUE_ID` bigint(20) NOT NULL,
  PRIMARY KEY  (`USER_DEF_DE_ID`,`PERMISSIBLE_VALUE_ID`),
  KEY `FK3EE58DCF5521B106` (`USER_DEF_DE_ID`),
  KEY `FK3EE58DCF49BDD67` (`PERMISSIBLE_VALUE_ID`),
  CONSTRAINT `FK3EE58DCF49BDD67` FOREIGN KEY (`PERMISSIBLE_VALUE_ID`) REFERENCES `dyextn_permissible_value` (`IDENTIFIER`),
  CONSTRAINT `FK3EE58DCF5521B106` FOREIGN KEY (`USER_DEF_DE_ID`) REFERENCES `dyextn_userdefined_de` (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `dyextn_userdef_de_value_rel` */

/*Table structure for table `dyextn_userdefined_de` */

DROP TABLE IF EXISTS `dyextn_userdefined_de`;

CREATE TABLE `dyextn_userdefined_de` (
  `IDENTIFIER` bigint(20) NOT NULL,
  PRIMARY KEY  (`IDENTIFIER`),
  KEY `FK630761FF53CC4A77` (`IDENTIFIER`),
  CONSTRAINT `FK630761FF53CC4A77` FOREIGN KEY (`IDENTIFIER`) REFERENCES `dyextn_data_element` (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `dyextn_userdefined_de` */

/*Table structure for table `dyextn_view` */

DROP TABLE IF EXISTS `dyextn_view`;

CREATE TABLE `dyextn_view` (
  `IDENTIFIER` bigint(20) NOT NULL auto_increment,
  `NAME` varchar(255) default NULL,
  PRIMARY KEY  (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `dyextn_view` */

/*Table structure for table `id_table` */

DROP TABLE IF EXISTS `id_table`;

CREATE TABLE `id_table` (
  `NEXT_ASSOCIATION_ID` bigint(20) NOT NULL,
  PRIMARY KEY  (`NEXT_ASSOCIATION_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `id_table` */

insert into `id_table` (`NEXT_ASSOCIATION_ID`) values (1);

/*Table structure for table `inter_model_association` */

DROP TABLE IF EXISTS `inter_model_association`;

CREATE TABLE `inter_model_association` (
  `ASSOCIATION_ID` bigint(20) NOT NULL,
  `LEFT_ENTITY_ID` bigint(20) NOT NULL,
  `LEFT_ATTRIBUTE_ID` bigint(20) NOT NULL,
  `RIGHT_ENTITY_ID` bigint(20) NOT NULL,
  `RIGHT_ATTRIBUTE_ID` bigint(20) NOT NULL,
  PRIMARY KEY  (`ASSOCIATION_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `inter_model_association` */

/*Table structure for table `intra_model_association` */

DROP TABLE IF EXISTS `intra_model_association`;

CREATE TABLE `intra_model_association` (
  `ASSOCIATION_ID` bigint(20) NOT NULL,
  `DE_ASSOCIATION_ID` bigint(20) NOT NULL,
  PRIMARY KEY  (`ASSOCIATION_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `intra_model_association` */

insert into `intra_model_association` (`ASSOCIATION_ID`,`DE_ASSOCIATION_ID`) values (1,28),(2,25),(3,24),(4,72),(5,82),(6,81),(7,80),(8,75),(9,71),(10,69),(11,60),(12,59),(13,56),(14,53),(15,50),(16,47);

/*Table structure for table `path` */

DROP TABLE IF EXISTS `path`;

CREATE TABLE `path` (
  `PATH_ID` bigint(20) NOT NULL,
  `FIRST_ENTITY_ID` bigint(20) default NULL,
  `INTERMEDIATE_PATH` varchar(1000) default NULL,
  `LAST_ENTITY_ID` bigint(20) default NULL,
  PRIMARY KEY  (`PATH_ID`),
  KEY `INDEX1` (`FIRST_ENTITY_ID`,`LAST_ENTITY_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `path` */

insert into `path` (`PATH_ID`,`FIRST_ENTITY_ID`,`INTERMEDIATE_PATH`,`LAST_ENTITY_ID`) values (1,23,'1',26),(2,23,'2',21),(3,23,'3',13),(4,70,'4',45),(5,45,'5',46),(6,46,'6',23),(7,46,'7',76),(8,46,'8',70),(9,70,'9',17),(10,46,'10',36),(11,46,'11',34),(12,46,'12',57),(13,46,'13',54),(14,46,'14',51),(15,46,'15',48),(16,46,'16',19);

/*Table structure for table `query` */

DROP TABLE IF EXISTS `query`;

CREATE TABLE `query` (
  `IDENTIFIER` bigint(20) NOT NULL auto_increment,
  `CONSTRAINTS_ID` bigint(20) default NULL,
  PRIMARY KEY  (`IDENTIFIER`),
  UNIQUE KEY `CONSTRAINTS_ID` (`CONSTRAINTS_ID`),
  KEY `FK49D20A89E2FD9C7` (`CONSTRAINTS_ID`),
  CONSTRAINT `FK49D20A89E2FD9C7` FOREIGN KEY (`CONSTRAINTS_ID`) REFERENCES `query_constraints` (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `query` */

/*Table structure for table `query_arithmetic_operand` */

DROP TABLE IF EXISTS `query_arithmetic_operand`;

CREATE TABLE `query_arithmetic_operand` (
  `IDENTIFIER` bigint(20) NOT NULL,
  `LITERAL` varchar(255) default NULL,
  `TERM_TYPE` varchar(255) default NULL,
  `DATE_LITERAL` date default NULL,
  `TIME_INTERVAL` varchar(255) default NULL,
  `DE_ATTRIBUTE_ID` bigint(20) default NULL,
  `EXPRESSION_ID` bigint(20) default NULL,
  PRIMARY KEY  (`IDENTIFIER`),
  KEY `FK262AEB0BD635BD31` (`IDENTIFIER`),
  KEY `FK262AEB0B96C7CE5A` (`IDENTIFIER`),
  KEY `FK262AEB0BD006BE44` (`IDENTIFIER`),
  KEY `FK262AEB0B7223B197` (`IDENTIFIER`),
  KEY `FK262AEB0B687BE69E` (`IDENTIFIER`),
  KEY `FK262AEB0BE92C814D` (`EXPRESSION_ID`),
  CONSTRAINT `FK262AEB0BE92C814D` FOREIGN KEY (`EXPRESSION_ID`) REFERENCES `query_base_expression` (`IDENTIFIER`),
  CONSTRAINT `FK262AEB0B687BE69E` FOREIGN KEY (`IDENTIFIER`) REFERENCES `query_operand` (`IDENTIFIER`),
  CONSTRAINT `FK262AEB0B7223B197` FOREIGN KEY (`IDENTIFIER`) REFERENCES `query_operand` (`IDENTIFIER`),
  CONSTRAINT `FK262AEB0B96C7CE5A` FOREIGN KEY (`IDENTIFIER`) REFERENCES `query_operand` (`IDENTIFIER`),
  CONSTRAINT `FK262AEB0BD006BE44` FOREIGN KEY (`IDENTIFIER`) REFERENCES `query_operand` (`IDENTIFIER`),
  CONSTRAINT `FK262AEB0BD635BD31` FOREIGN KEY (`IDENTIFIER`) REFERENCES `query_operand` (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `query_arithmetic_operand` */

/*Table structure for table `query_base_expr_opnd` */

DROP TABLE IF EXISTS `query_base_expr_opnd`;

CREATE TABLE `query_base_expr_opnd` (
  `BASE_EXPRESSION_ID` bigint(20) NOT NULL,
  `OPERAND_ID` bigint(20) NOT NULL,
  `POSITION` int(11) NOT NULL,
  PRIMARY KEY  (`BASE_EXPRESSION_ID`,`POSITION`),
  KEY `FKAE67EAF0712A4C` (`OPERAND_ID`),
  KEY `FKAE67EA48BA6890` (`BASE_EXPRESSION_ID`),
  CONSTRAINT `FKAE67EA48BA6890` FOREIGN KEY (`BASE_EXPRESSION_ID`) REFERENCES `query_base_expression` (`IDENTIFIER`),
  CONSTRAINT `FKAE67EAF0712A4C` FOREIGN KEY (`OPERAND_ID`) REFERENCES `query_operand` (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `query_base_expr_opnd` */

/*Table structure for table `query_base_expression` */

DROP TABLE IF EXISTS `query_base_expression`;

CREATE TABLE `query_base_expression` (
  `IDENTIFIER` bigint(20) NOT NULL auto_increment,
  `EXPR_TYPE` varchar(255) NOT NULL,
  PRIMARY KEY  (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `query_base_expression` */

/*Table structure for table `query_baseexpr_to_connectors` */

DROP TABLE IF EXISTS `query_baseexpr_to_connectors`;

CREATE TABLE `query_baseexpr_to_connectors` (
  `BASE_EXPRESSION_ID` bigint(20) NOT NULL,
  `CONNECTOR_ID` bigint(20) NOT NULL,
  `POSITION` int(11) NOT NULL,
  PRIMARY KEY  (`BASE_EXPRESSION_ID`,`POSITION`),
  KEY `FK3F0043482FCE1DA7` (`CONNECTOR_ID`),
  KEY `FK3F00434848BA6890` (`BASE_EXPRESSION_ID`),
  CONSTRAINT `FK3F00434848BA6890` FOREIGN KEY (`BASE_EXPRESSION_ID`) REFERENCES `query_base_expression` (`IDENTIFIER`),
  CONSTRAINT `FK3F0043482FCE1DA7` FOREIGN KEY (`CONNECTOR_ID`) REFERENCES `query_connector` (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `query_baseexpr_to_connectors` */

/*Table structure for table `query_condition` */

DROP TABLE IF EXISTS `query_condition`;

CREATE TABLE `query_condition` (
  `IDENTIFIER` bigint(20) NOT NULL auto_increment,
  `ATTRIBUTE_ID` bigint(20) NOT NULL,
  `RELATIONAL_OPERATOR` varchar(255) default NULL,
  PRIMARY KEY  (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `query_condition` */

/*Table structure for table `query_condition_values` */

DROP TABLE IF EXISTS `query_condition_values`;

CREATE TABLE `query_condition_values` (
  `CONDITION_ID` bigint(20) NOT NULL,
  `VALUE` varchar(255) default NULL,
  `POSITION` int(11) NOT NULL,
  PRIMARY KEY  (`CONDITION_ID`,`POSITION`),
  KEY `FK9997379D6458C2E7` (`CONDITION_ID`),
  CONSTRAINT `FK9997379D6458C2E7` FOREIGN KEY (`CONDITION_ID`) REFERENCES `query_condition` (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `query_condition_values` */

/*Table structure for table `query_connector` */

DROP TABLE IF EXISTS `query_connector`;

CREATE TABLE `query_connector` (
  `IDENTIFIER` bigint(20) NOT NULL auto_increment,
  `OPERATOR` varchar(255) default NULL,
  `NESTING_NUMBER` int(11) default NULL,
  PRIMARY KEY  (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `query_connector` */

/*Table structure for table `query_constraint_to_expr` */

DROP TABLE IF EXISTS `query_constraint_to_expr`;

CREATE TABLE `query_constraint_to_expr` (
  `CONSTRAINT_ID` bigint(20) NOT NULL,
  `EXPRESSION_ID` bigint(20) NOT NULL,
  PRIMARY KEY  (`CONSTRAINT_ID`,`EXPRESSION_ID`),
  UNIQUE KEY `EXPRESSION_ID` (`EXPRESSION_ID`),
  KEY `FK2BD705CEA0A5F4C0` (`CONSTRAINT_ID`),
  KEY `FK2BD705CEE92C814D` (`EXPRESSION_ID`),
  CONSTRAINT `FK2BD705CEE92C814D` FOREIGN KEY (`EXPRESSION_ID`) REFERENCES `query_base_expression` (`IDENTIFIER`),
  CONSTRAINT `FK2BD705CEA0A5F4C0` FOREIGN KEY (`CONSTRAINT_ID`) REFERENCES `query_constraints` (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `query_constraint_to_expr` */

/*Table structure for table `query_constraints` */

DROP TABLE IF EXISTS `query_constraints`;

CREATE TABLE `query_constraints` (
  `IDENTIFIER` bigint(20) NOT NULL auto_increment,
  `QUERY_JOIN_GRAPH_ID` bigint(20) default NULL,
  PRIMARY KEY  (`IDENTIFIER`),
  UNIQUE KEY `QUERY_JOIN_GRAPH_ID` (`QUERY_JOIN_GRAPH_ID`),
  KEY `FKE364FCFF1C7EBF3B` (`QUERY_JOIN_GRAPH_ID`),
  CONSTRAINT `FKE364FCFF1C7EBF3B` FOREIGN KEY (`QUERY_JOIN_GRAPH_ID`) REFERENCES `query_join_graph` (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `query_constraints` */

/*Table structure for table `query_custom_formula` */

DROP TABLE IF EXISTS `query_custom_formula`;

CREATE TABLE `query_custom_formula` (
  `IDENTIFIER` bigint(20) NOT NULL,
  `OPERATOR` varchar(255) default NULL,
  `LHS_TERM_ID` bigint(20) default NULL,
  PRIMARY KEY  (`IDENTIFIER`),
  KEY `FK5C0EEAEFBE674D45` (`LHS_TERM_ID`),
  KEY `FK5C0EEAEF12D455EB` (`IDENTIFIER`),
  CONSTRAINT `FK5C0EEAEF12D455EB` FOREIGN KEY (`IDENTIFIER`) REFERENCES `query_operand` (`IDENTIFIER`),
  CONSTRAINT `FK5C0EEAEFBE674D45` FOREIGN KEY (`LHS_TERM_ID`) REFERENCES `query_base_expression` (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `query_custom_formula` */

/*Table structure for table `query_expression` */

DROP TABLE IF EXISTS `query_expression`;

CREATE TABLE `query_expression` (
  `IDENTIFIER` bigint(20) NOT NULL,
  `IS_IN_VIEW` tinyint(1) default NULL,
  `IS_VISIBLE` tinyint(1) default NULL,
  `UI_EXPR_ID` int(11) default NULL,
  `QUERY_ENTITY_ID` bigint(20) default NULL,
  PRIMARY KEY  (`IDENTIFIER`),
  KEY `FK1B473A8F40EB75D4` (`IDENTIFIER`),
  KEY `FK1B473A8F635766D8` (`QUERY_ENTITY_ID`),
  CONSTRAINT `FK1B473A8F635766D8` FOREIGN KEY (`QUERY_ENTITY_ID`) REFERENCES `query_query_entity` (`IDENTIFIER`),
  CONSTRAINT `FK1B473A8F40EB75D4` FOREIGN KEY (`IDENTIFIER`) REFERENCES `query_base_expression` (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `query_expression` */

/*Table structure for table `query_formula_rhs` */

DROP TABLE IF EXISTS `query_formula_rhs`;

CREATE TABLE `query_formula_rhs` (
  `CUSTOM_FORMULA_ID` bigint(20) NOT NULL,
  `RHS_TERM_ID` bigint(20) NOT NULL,
  `POSITION` int(11) NOT NULL,
  PRIMARY KEY  (`CUSTOM_FORMULA_ID`,`POSITION`),
  KEY `FKAE90F94D3BC37DCB` (`RHS_TERM_ID`),
  KEY `FKAE90F94D9A0B7164` (`CUSTOM_FORMULA_ID`),
  CONSTRAINT `FKAE90F94D9A0B7164` FOREIGN KEY (`CUSTOM_FORMULA_ID`) REFERENCES `query_operand` (`IDENTIFIER`),
  CONSTRAINT `FKAE90F94D3BC37DCB` FOREIGN KEY (`RHS_TERM_ID`) REFERENCES `query_base_expression` (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `query_formula_rhs` */

/*Table structure for table `query_inter_model_association` */

DROP TABLE IF EXISTS `query_inter_model_association`;

CREATE TABLE `query_inter_model_association` (
  `IDENTIFIER` bigint(20) NOT NULL,
  `SOURCE_SERVICE_URL` text NOT NULL,
  `TARGET_SERVICE_URL` text NOT NULL,
  `SOURCE_ATTRIBUTE_ID` bigint(20) NOT NULL,
  `TARGET_ATTRIBUTE_ID` bigint(20) NOT NULL,
  PRIMARY KEY  (`IDENTIFIER`),
  KEY `FKD70658D15F5AB67E` (`IDENTIFIER`),
  CONSTRAINT `FKD70658D15F5AB67E` FOREIGN KEY (`IDENTIFIER`) REFERENCES `query_model_association` (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `query_inter_model_association` */

/*Table structure for table `query_intra_model_association` */

DROP TABLE IF EXISTS `query_intra_model_association`;

CREATE TABLE `query_intra_model_association` (
  `IDENTIFIER` bigint(20) NOT NULL,
  `DE_ASSOCIATION_ID` bigint(20) NOT NULL,
  PRIMARY KEY  (`IDENTIFIER`),
  KEY `FKF1EDBDD35F5AB67E` (`IDENTIFIER`),
  CONSTRAINT `FKF1EDBDD35F5AB67E` FOREIGN KEY (`IDENTIFIER`) REFERENCES `query_model_association` (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `query_intra_model_association` */

/*Table structure for table `query_join_graph` */

DROP TABLE IF EXISTS `query_join_graph`;

CREATE TABLE `query_join_graph` (
  `IDENTIFIER` bigint(20) NOT NULL auto_increment,
  `COMMONS_GRAPH_ID` bigint(20) default NULL,
  PRIMARY KEY  (`IDENTIFIER`),
  KEY `FK2B41B5D09DBC4D94` (`COMMONS_GRAPH_ID`),
  CONSTRAINT `FK2B41B5D09DBC4D94` FOREIGN KEY (`COMMONS_GRAPH_ID`) REFERENCES `commons_graph` (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `query_join_graph` */

/*Table structure for table `query_model_association` */

DROP TABLE IF EXISTS `query_model_association`;

CREATE TABLE `query_model_association` (
  `IDENTIFIER` bigint(20) NOT NULL auto_increment,
  PRIMARY KEY  (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `query_model_association` */

/*Table structure for table `query_operand` */

DROP TABLE IF EXISTS `query_operand`;

CREATE TABLE `query_operand` (
  `IDENTIFIER` bigint(20) NOT NULL auto_increment,
  `OPND_TYPE` varchar(255) NOT NULL,
  PRIMARY KEY  (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `query_operand` */

/*Table structure for table `query_output_attribute` */

DROP TABLE IF EXISTS `query_output_attribute`;

CREATE TABLE `query_output_attribute` (
  `IDENTIFIER` bigint(20) NOT NULL auto_increment,
  `EXPRESSION_ID` bigint(20) default NULL,
  `ATTRIBUTE_ID` bigint(20) NOT NULL,
  `PARAMETERIZED_QUERY_ID` bigint(20) default NULL,
  `POSITION` int(11) default NULL,
  PRIMARY KEY  (`IDENTIFIER`),
  KEY `FK22C9DB75604D4BDA` (`PARAMETERIZED_QUERY_ID`),
  KEY `FK22C9DB75E92C814D` (`EXPRESSION_ID`),
  CONSTRAINT `FK22C9DB75E92C814D` FOREIGN KEY (`EXPRESSION_ID`) REFERENCES `query_base_expression` (`IDENTIFIER`),
  CONSTRAINT `FK22C9DB75604D4BDA` FOREIGN KEY (`PARAMETERIZED_QUERY_ID`) REFERENCES `query_parameterized_query` (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `query_output_attribute` */

/*Table structure for table `query_output_term` */

DROP TABLE IF EXISTS `query_output_term`;

CREATE TABLE `query_output_term` (
  `IDENTIFIER` bigint(20) NOT NULL auto_increment,
  `NAME` varchar(255) default NULL,
  `TIME_INTERVAL` varchar(255) default NULL,
  `TERM_ID` bigint(20) default NULL,
  PRIMARY KEY  (`IDENTIFIER`),
  KEY `FK13C8A3D388C86B0D` (`TERM_ID`),
  CONSTRAINT `FK13C8A3D388C86B0D` FOREIGN KEY (`TERM_ID`) REFERENCES `query_base_expression` (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `query_output_term` */

/*Table structure for table `query_parameter` */

DROP TABLE IF EXISTS `query_parameter`;

CREATE TABLE `query_parameter` (
  `IDENTIFIER` bigint(20) NOT NULL auto_increment,
  `NAME` varchar(255) default NULL,
  `OBJECT_CLASS` varchar(255) default NULL,
  `OBJECT_ID` bigint(20) default NULL,
  PRIMARY KEY  (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `query_parameter` */

/*Table structure for table `query_parameterized_query` */

DROP TABLE IF EXISTS `query_parameterized_query`;

CREATE TABLE `query_parameterized_query` (
  `IDENTIFIER` bigint(20) NOT NULL,
  `QUERY_NAME` varchar(255) default NULL,
  `DESCRIPTION` text,
  PRIMARY KEY  (`IDENTIFIER`),
  UNIQUE KEY `QUERY_NAME` (`QUERY_NAME`),
  KEY `FKA272176B76177EFE` (`IDENTIFIER`),
  CONSTRAINT `FKA272176B76177EFE` FOREIGN KEY (`IDENTIFIER`) REFERENCES `query` (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `query_parameterized_query` */

/*Table structure for table `query_query_entity` */

DROP TABLE IF EXISTS `query_query_entity`;

CREATE TABLE `query_query_entity` (
  `IDENTIFIER` bigint(20) NOT NULL auto_increment,
  `ENTITY_ID` bigint(20) NOT NULL,
  PRIMARY KEY  (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `query_query_entity` */

/*Table structure for table `query_rule_cond` */

DROP TABLE IF EXISTS `query_rule_cond`;

CREATE TABLE `query_rule_cond` (
  `RULE_ID` bigint(20) NOT NULL,
  `CONDITION_ID` bigint(20) NOT NULL,
  `POSITION` int(11) NOT NULL,
  PRIMARY KEY  (`RULE_ID`,`POSITION`),
  KEY `FKC32D37AE6458C2E7` (`CONDITION_ID`),
  KEY `FKC32D37AE39F0A10D` (`RULE_ID`),
  CONSTRAINT `FKC32D37AE39F0A10D` FOREIGN KEY (`RULE_ID`) REFERENCES `query_operand` (`IDENTIFIER`),
  CONSTRAINT `FKC32D37AE6458C2E7` FOREIGN KEY (`CONDITION_ID`) REFERENCES `query_condition` (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `query_rule_cond` */

/*Table structure for table `query_subexpr_operand` */

DROP TABLE IF EXISTS `query_subexpr_operand`;

CREATE TABLE `query_subexpr_operand` (
  `IDENTIFIER` bigint(20) NOT NULL,
  `EXPRESSION_ID` bigint(20) default NULL,
  PRIMARY KEY  (`IDENTIFIER`),
  KEY `FK2BF760E832E875C8` (`IDENTIFIER`),
  KEY `FK2BF760E8E92C814D` (`EXPRESSION_ID`),
  CONSTRAINT `FK2BF760E8E92C814D` FOREIGN KEY (`EXPRESSION_ID`) REFERENCES `query_base_expression` (`IDENTIFIER`),
  CONSTRAINT `FK2BF760E832E875C8` FOREIGN KEY (`IDENTIFIER`) REFERENCES `query_operand` (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `query_subexpr_operand` */

/*Table structure for table `query_to_output_terms` */

DROP TABLE IF EXISTS `query_to_output_terms`;

CREATE TABLE `query_to_output_terms` (
  `QUERY_ID` bigint(20) NOT NULL,
  `OUTPUT_TERM_ID` bigint(20) NOT NULL,
  `POSITION` int(11) NOT NULL,
  PRIMARY KEY  (`QUERY_ID`,`POSITION`),
  UNIQUE KEY `OUTPUT_TERM_ID` (`OUTPUT_TERM_ID`),
  KEY `FK8A70E2565E5B9430` (`OUTPUT_TERM_ID`),
  KEY `FK8A70E25691051647` (`QUERY_ID`),
  CONSTRAINT `FK8A70E25691051647` FOREIGN KEY (`QUERY_ID`) REFERENCES `query` (`IDENTIFIER`),
  CONSTRAINT `FK8A70E2565E5B9430` FOREIGN KEY (`OUTPUT_TERM_ID`) REFERENCES `query_output_term` (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `query_to_output_terms` */

/*Table structure for table `query_to_parameters` */

DROP TABLE IF EXISTS `query_to_parameters`;

CREATE TABLE `query_to_parameters` (
  `QUERY_ID` bigint(20) NOT NULL,
  `PARAMETER_ID` bigint(20) NOT NULL,
  `POSITION` int(11) NOT NULL,
  PRIMARY KEY  (`QUERY_ID`,`POSITION`),
  UNIQUE KEY `PARAMETER_ID` (`PARAMETER_ID`),
  KEY `FK8060DAD7F84B9027` (`PARAMETER_ID`),
  KEY `FK8060DAD739F0A314` (`QUERY_ID`),
  CONSTRAINT `FK8060DAD739F0A314` FOREIGN KEY (`QUERY_ID`) REFERENCES `query_parameterized_query` (`IDENTIFIER`),
  CONSTRAINT `FK8060DAD7F84B9027` FOREIGN KEY (`PARAMETER_ID`) REFERENCES `query_parameter` (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `query_to_parameters` */



SET FOREIGN_KEY_CHECKS = 1;

