/*
SQLyog - Free MySQL GUI v5.16
Host - 5.0.45-community-nt : Database - querydb
*********************************************************************
Server version : 5.0.45-community-nt
*/


SET NAMES utf8;
SET SQL_MODE='';
SET FOREIGN_KEY_CHECKS = 0;
SET storage_engine=InnoDB;


/*Table structure for table `association` */

CREATE TABLE `association` (
  `ASSOCIATION_ID` bigint(20) NOT NULL,
  `ASSOCIATION_TYPE` int(8) NOT NULL,
  PRIMARY KEY  (`ASSOCIATION_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `association` */

insert into `association` (`ASSOCIATION_ID`,`ASSOCIATION_TYPE`) values (1,2),(2,2),(3,2),(4,2),(5,2),(6,2),(7,2),(8,2),(9,2),(10,2),(11,2),(12,2),(13,2),(14,2),(15,2),(16,2),(17,2),(18,2),(19,2),(20,2),(21,2),(22,2),(23,2),(24,2),(25,2),(26,2),(27,2),(28,2),(29,2),(30,2),(31,2),(32,2),(33,2),(34,2),(35,2),(36,2),(37,2),(38,2),(39,2),(40,2),(41,2),(42,2),(43,2),(44,2),(45,2),(46,2),(47,2),(48,2),(49,2),(50,2),(51,2),(52,2),(53,2),(54,2),(55,2),(56,2),(57,2),(58,2),(59,2),(60,2),(61,2),(62,2),(63,2),(64,2),(65,2),(66,2),(67,2),(68,2),(69,2),(70,2),(71,2),(72,2),(73,2);

/*Table structure for table `categorial_attribute` */

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

CREATE TABLE `commons_graph` (
  `IDENTIFIER` bigint(20) NOT NULL auto_increment,
  PRIMARY KEY  (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `commons_graph` */

/*Table structure for table `commons_graph_edge` */

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

CREATE TABLE `commons_graph_to_vertices` (
  `GRAPH_ID` bigint(20) NOT NULL,
  `VERTEX_CLASS` varchar(255) default NULL,
  `VERTEX_ID` bigint(20) default NULL,
  KEY `FK2C4412F5A0494B1D` (`GRAPH_ID`),
  CONSTRAINT `FK2C4412F5A0494B1D` FOREIGN KEY (`GRAPH_ID`) REFERENCES `commons_graph` (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `commons_graph_to_vertices` */

/*Table structure for table `csm_application` */

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

insert into `csm_protection_group` (`PROTECTION_GROUP_ID`,`PROTECTION_GROUP_NAME`,`PROTECTION_GROUP_DESCRIPTION`,`APPLICATION_ID`,`LARGE_ELEMENT_COUNT_FLAG`,`UPDATE_DATE`,`PARENT_PROTECTION_GROUP_ID`) values (1,'ADMINISTRATOR_PROTECTION_GROUP','Protection elements for class names of objects that belong to Administrative data',1,0,'2005-01-01',NULL),(2,'SUPERVISOR_PROTECTION_GROUP','Protection elements for class names of objects that belong to Supervisors data',1,0,'2005-01-01',NULL),(3,'TECHNICIAN_PROTECTION_GROUP','Protection elements for class names of objects that belong to Technician data',1,0,'2005-01-01',NULL),(17,'SECURED_ADMINISTRATIVE_ACTIONS',NULL,1,0,'2005-01-01',NULL),(18,'SECURED_SUPERVISORY_ACTIONS',NULL,1,0,'2005-01-01',NULL),(19,'SECURED_TECHNICIAN_ACTIONS',NULL,1,0,'2005-01-01',NULL),(20,'PUBLIC_DATA_GROUP',NULL,1,0,'2005-01-01',NULL),(21,'ADMINISTRATORS_DATA_GROUP',NULL,1,0,'2005-01-01',NULL),(22,'SUPERVISORS_DATA_GROUP',NULL,1,0,'2005-01-01',NULL),(23,'TECHNICIANS_DATA_GROUP',NULL,1,0,'2005-01-01',NULL),(24,'SECURED_PUBLIC_ACTIONS',NULL,1,0,'2005-01-01',NULL),(44,'SCIENTIST_PROTECTION_GROUP',NULL,1,0,'2005-01-01',NULL);

/*Table structure for table `csm_role` */

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

CREATE TABLE `curated_path` (
  `curated_path_Id` bigint(20) NOT NULL default '0',
  `entity_ids` varchar(1000) default NULL,
  `selected` tinyint(1) default NULL,
  PRIMARY KEY  (`curated_path_Id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `curated_path` */

/*Table structure for table `curated_path_to_path` */

CREATE TABLE `curated_path_to_path` (
  `curated_path_Id` bigint(20) NOT NULL default '0',
  `path_id` bigint(20) NOT NULL default '0',
  PRIMARY KEY  (`curated_path_Id`,`path_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `curated_path_to_path` */

/*Table structure for table `de_coll_attr_record_values` */

CREATE TABLE `de_coll_attr_record_values` (
  `IDENTIFIER` bigint(20) NOT NULL auto_increment,
  `RECORD_VALUE` text,
  `COLLECTION_ATTR_RECORD_ID` bigint(20) default NULL,
  PRIMARY KEY  (`IDENTIFIER`),
  KEY `FK847DA57775255CA5` (`COLLECTION_ATTR_RECORD_ID`),
  CONSTRAINT `FK847DA57775255CA5` FOREIGN KEY (`COLLECTION_ATTR_RECORD_ID`) REFERENCES `dyextn_attribute_record` (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `de_coll_attr_record_values` */

/*Table structure for table `de_file_attr_record_values` */

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

CREATE TABLE `dyextn_abstract_entity` (
  `id` bigint(20) NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `FKA4A164E3D3027A30` (`id`),
  CONSTRAINT `FKA4A164E3D3027A30` FOREIGN KEY (`id`) REFERENCES `dyextn_abstract_metadata` (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `dyextn_abstract_entity` */

insert into `dyextn_abstract_entity` (`id`) values (2),(7),(8),(13),(23),(24),(25),(28),(31),(34),(37),(40),(52),(53),(60),(65),(66),(69),(72),(87),(89),(91),(93),(98),(100),(102),(104),(106),(108),(113),(116),(124),(132),(134),(135),(146),(149),(158),(161),(172),(176),(178),(180),(182),(188),(191),(206),(216),(221),(224),(233),(236),(239),(263),(266),(269),(271),(280),(283),(285),(287),(289);

/*Table structure for table `dyextn_abstract_metadata` */

CREATE TABLE `dyextn_abstract_metadata` (
  `IDENTIFIER` bigint(20) NOT NULL auto_increment,
  `CREATED_DATE` date default NULL,
  `DESCRIPTION` text,
  `LAST_UPDATED` date default NULL,
  `NAME` text,
  `PUBLIC_ID` varchar(255) default NULL,
  PRIMARY KEY  (`IDENTIFIER`)
) ENGINE=InnoDB AUTO_INCREMENT=296 DEFAULT CHARSET=latin1;

/*Data for the table `dyextn_abstract_metadata` */

insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (1,NULL,'cider',NULL,'cider',NULL),(2,'2008-11-20','cider-PatientLocation','2008-11-20','PatientLocation',NULL),(3,NULL,NULL,NULL,'bed',NULL),(4,NULL,NULL,NULL,'room',NULL),(5,NULL,NULL,NULL,'location',NULL),(6,NULL,NULL,NULL,'id',NULL),(7,'2008-11-20','cider-Provider','2008-11-20','Provider',NULL),(8,'2008-11-20','cider-MedicalEntitiesDictionary','2008-11-20','MedicalEntitiesDictionary',NULL),(9,NULL,NULL,NULL,'shortName',NULL),(10,NULL,NULL,NULL,'name',NULL),(11,NULL,NULL,NULL,'id',NULL),(12,NULL,NULL,NULL,'AssociationName_3',NULL),(13,'2008-11-20','cider-PersonName','2008-11-20','PersonName',NULL),(14,NULL,NULL,NULL,'suffix',NULL),(15,NULL,NULL,NULL,'prefix',NULL),(16,NULL,NULL,NULL,'degree',NULL),(17,NULL,NULL,NULL,'lastNameCompressed',NULL),(18,NULL,NULL,NULL,'lastName',NULL),(19,NULL,NULL,NULL,'middleName',NULL),(20,NULL,NULL,NULL,'firstName',NULL),(21,NULL,NULL,NULL,'id',NULL),(22,NULL,NULL,NULL,'AssociationName_2',NULL),(23,'2008-11-20','cider-Person','2008-11-20','Person',NULL),(24,'2008-11-20','cider-Demographics','2008-11-20','Demographics',NULL),(25,'2008-11-20','cider-EthnicOrigin','2008-11-20','EthnicOrigin',NULL),(26,NULL,NULL,NULL,'id',NULL),(27,NULL,NULL,NULL,'AssociationName_11',NULL),(28,'2008-11-20','cider-Gender','2008-11-20','Gender',NULL),(29,NULL,NULL,NULL,'id',NULL),(30,NULL,NULL,NULL,'AssociationName_10',NULL),(31,'2008-11-20','cider-AdvancedDirectiveExists','2008-11-20','AdvancedDirectiveExists',NULL),(32,NULL,NULL,NULL,'id',NULL),(33,NULL,NULL,NULL,'AssociationName_9',NULL),(34,'2008-11-20','cider-Religion','2008-11-20','Religion',NULL),(35,NULL,NULL,NULL,'id',NULL),(36,NULL,NULL,NULL,'AssociationName_8',NULL),(37,'2008-11-20','cider-Race','2008-11-20','Race',NULL),(38,NULL,NULL,NULL,'id',NULL),(39,NULL,NULL,NULL,'AssociationName_7',NULL),(40,'2008-11-20','cider-MaritalStatus','2008-11-20','MaritalStatus',NULL),(41,NULL,NULL,NULL,'id',NULL),(42,NULL,NULL,NULL,'AssociationName_6',NULL),(43,NULL,NULL,NULL,'id',NULL),(44,NULL,NULL,NULL,'dateOfBirth',NULL),(45,NULL,NULL,NULL,'dateOfDeath',NULL),(46,NULL,NULL,NULL,'mothersMaidenName',NULL),(47,NULL,NULL,NULL,'placeOfBirth',NULL),(48,NULL,NULL,NULL,'socialSecurityNumber',NULL),(49,NULL,NULL,NULL,'effectiveStartTimeStamp',NULL),(50,NULL,NULL,NULL,'effectiveEndTimeStamp',NULL),(51,NULL,NULL,NULL,'AssociationName_1',NULL),(52,'2008-11-20','cider-AssociatedPerson','2008-11-20','AssociatedPerson',NULL),(53,'2008-11-20','cider-RelationToPerson','2008-11-20','RelationToPerson',NULL),(54,NULL,NULL,NULL,'id',NULL),(55,NULL,NULL,NULL,'AssociationName_2',NULL),(56,NULL,NULL,NULL,'AssociationName_1',NULL),(57,NULL,NULL,NULL,'id',NULL),(58,NULL,NULL,NULL,'AssociationName_2',NULL),(59,NULL,NULL,NULL,'AssociationName_3',NULL),(60,'2008-11-20','cider-Phone','2008-11-20','Phone',NULL),(61,NULL,NULL,NULL,'type',NULL),(62,NULL,NULL,NULL,'number',NULL),(63,NULL,NULL,NULL,'id',NULL),(64,NULL,NULL,NULL,'AssociationName_4',NULL),(65,'2008-11-20','cider-Address','2008-11-20','Address',NULL),(66,'2008-11-20','cider-State','2008-11-20','State',NULL),(67,NULL,NULL,NULL,'id',NULL),(68,NULL,NULL,NULL,'AssociationName_3',NULL),(69,'2008-11-20','cider-AddressType','2008-11-20','AddressType',NULL),(70,NULL,NULL,NULL,'id',NULL),(71,NULL,NULL,NULL,'AssociationName_2',NULL),(72,'2008-11-20','cider-Country','2008-11-20','Country',NULL),(73,NULL,NULL,NULL,'id',NULL),(74,NULL,NULL,NULL,'AssociationName_1',NULL),(75,NULL,NULL,NULL,'postalCode',NULL),(76,NULL,NULL,NULL,'city',NULL),(77,NULL,NULL,NULL,'line2',NULL),(78,NULL,NULL,NULL,'line1',NULL),(79,NULL,NULL,NULL,'id',NULL),(80,NULL,NULL,NULL,'AssociationName_5',NULL),(81,NULL,NULL,NULL,'AssociationName_1',NULL),(82,NULL,NULL,NULL,'personUpi',NULL),(83,NULL,NULL,NULL,'id',NULL),(84,NULL,NULL,NULL,'AssociationName_1',NULL),(85,NULL,NULL,NULL,'facilityProviderId',NULL),(86,NULL,NULL,NULL,'id',NULL),(87,'2008-11-20','cider-ActiveUpiFlag','2008-11-20','ActiveUpiFlag',NULL),(88,NULL,NULL,NULL,'id',NULL),(89,'2008-11-20','cider-VipIndicator','2008-11-20','VipIndicator',NULL),(90,NULL,NULL,NULL,'id',NULL),(91,'2008-11-20','cider-PhoneType','2008-11-20','PhoneType',NULL),(92,NULL,NULL,NULL,'id',NULL),(93,'2008-11-20','cider-NormalRange','2008-11-20','NormalRange',NULL),(94,NULL,NULL,NULL,'rangeHigh',NULL),(95,NULL,NULL,NULL,'rangeLow',NULL),(96,NULL,NULL,NULL,'range',NULL),(97,NULL,NULL,NULL,'id',NULL),(98,'2008-11-20','cider-LaboratoryProcedureType','2008-11-20','LaboratoryProcedureType',NULL),(99,NULL,NULL,NULL,'id',NULL),(100,'2008-11-20','cider-DiagnosticRelatedGroup','2008-11-20','DiagnosticRelatedGroup',NULL),(101,NULL,NULL,NULL,'id',NULL),(102,'2008-11-20','cider-Status','2008-11-20','Status',NULL),(103,NULL,NULL,NULL,'id',NULL),(104,'2008-11-20','cider-FacilityDischargeDisposition','2008-11-20','FacilityDischargeDisposition',NULL),(105,NULL,NULL,NULL,'id',NULL),(106,'2008-11-20','cider-LaboratoryProcedure','2008-11-20','LaboratoryProcedure',NULL),(107,NULL,NULL,NULL,'AssociationName_5',NULL),(108,'2008-11-20','cider-Facility','2008-11-20','Facility',NULL),(109,NULL,NULL,NULL,'initials',NULL),(110,NULL,NULL,NULL,'id',NULL),(111,NULL,NULL,NULL,'AssociationName_4',NULL),(112,NULL,NULL,NULL,'AssociationName_3',NULL),(113,'2008-11-20','cider-Application','2008-11-20','Application',NULL),(114,NULL,NULL,NULL,'id',NULL),(115,NULL,NULL,NULL,'AssociationName_2',NULL),(116,'2008-11-20','cider-LaboratoryProcedureDetails','2008-11-20','LaboratoryProcedureDetails',NULL),(117,NULL,NULL,NULL,'AssociationName_8',NULL),(118,NULL,NULL,NULL,'id',NULL),(119,NULL,NULL,NULL,'effectiveStartTimeStamp',NULL),(120,NULL,NULL,NULL,'effectiveEndTimeStamp',NULL),(121,NULL,NULL,NULL,'ageAtProcedure',NULL),(122,NULL,NULL,NULL,'procedureComment',NULL),(123,NULL,NULL,NULL,'procedureId',NULL),(124,'2008-11-20','cider-MedicalRecordNumber','2008-11-20','MedicalRecordNumber',NULL),(125,NULL,NULL,NULL,'AssociationName_1',NULL),(126,NULL,NULL,NULL,'Type',NULL),(127,NULL,NULL,NULL,'Number',NULL),(128,NULL,NULL,NULL,'id',NULL),(129,NULL,NULL,NULL,'AssociationName_1',NULL),(130,NULL,NULL,NULL,'AssociationName_2',NULL),(131,NULL,NULL,NULL,'AssociationName_3',NULL),(132,'2008-11-20','cider-LaboratoryResult','2008-11-20','LaboratoryResult',NULL),(133,NULL,NULL,NULL,'AssociationName_4',NULL),(134,'2008-11-20','cider-ResultValue','2008-11-20','ResultValue',NULL),(135,'2008-11-20','cider-Result','2008-11-20','Result',NULL),(136,NULL,NULL,NULL,'AssociationName_1',NULL),(137,NULL,NULL,NULL,'units',NULL),(138,NULL,NULL,NULL,'resultHigh',NULL),(139,NULL,NULL,NULL,'resultLow',NULL),(140,NULL,NULL,NULL,'resultString',NULL),(141,NULL,NULL,NULL,'id',NULL),(142,NULL,NULL,NULL,'AssociationName_2',NULL),(143,NULL,NULL,NULL,'AssociationName_1',NULL),(144,NULL,NULL,NULL,'id',NULL),(145,NULL,NULL,NULL,'AssociationName_3',NULL),(146,'2008-11-20','cider-AbnormalFlag','2008-11-20','AbnormalFlag',NULL),(147,NULL,NULL,NULL,'id',NULL),(148,NULL,NULL,NULL,'AssociationName_2',NULL),(149,'2008-11-20','cider-LaboratoryTestType','2008-11-20','LaboratoryTestType',NULL),(150,NULL,NULL,NULL,'standardUnitOfMeasure',NULL),(151,NULL,NULL,NULL,'id',NULL),(152,NULL,NULL,NULL,'AssociationName_1',NULL),(153,NULL,NULL,NULL,'resultTimeStamp',NULL),(154,NULL,NULL,NULL,'testSynonym',NULL),(155,NULL,NULL,NULL,'resultComments',NULL),(156,NULL,NULL,NULL,'id',NULL),(157,NULL,NULL,NULL,'AssociationName_4',NULL),(158,'2008-11-20','cider-SpecimanType','2008-11-20','SpecimanType',NULL),(159,NULL,NULL,NULL,'id',NULL),(160,NULL,NULL,NULL,'AssociationName_5',NULL),(161,'2008-11-20','cider-PatientAccountNumber','2008-11-20','PatientAccountNumber',NULL),(162,NULL,NULL,NULL,'AssociationName_1',NULL),(163,NULL,NULL,NULL,'patientAccountNumber',NULL),(164,NULL,NULL,NULL,'id',NULL),(165,NULL,NULL,NULL,'AssociationName_6',NULL),(166,NULL,NULL,NULL,'AssociationName_7',NULL),(167,NULL,NULL,NULL,'AssociationName_1',NULL),(168,NULL,NULL,NULL,'procedureSynonym',NULL),(169,NULL,NULL,NULL,'patientAccountNumber',NULL),(170,NULL,NULL,NULL,'accessionNumber',NULL),(171,NULL,NULL,NULL,'id',NULL),(172,'2008-11-20','cider-Units','2008-11-20','Units',NULL),(173,NULL,NULL,NULL,'normalizedUnits',NULL),(174,NULL,NULL,NULL,'sourceUnits',NULL),(175,NULL,NULL,NULL,'id',NULL),(176,'2008-11-20','cider-ProcedureCode','2008-11-20','ProcedureCode',NULL),(177,NULL,NULL,NULL,'id',NULL),(178,'2008-11-20','cider-Icd9ProcedureCode','2008-11-20','Icd9ProcedureCode',NULL),(179,NULL,NULL,NULL,'id',NULL),(180,'2008-11-20','cider-DiagnosisType','2008-11-20','DiagnosisType',NULL),(181,NULL,NULL,NULL,'id',NULL),(182,'2008-11-20','cider-EncounterDetails','2008-11-20','EncounterDetails',NULL),(183,NULL,NULL,NULL,'AssociationName_32',NULL),(184,NULL,NULL,NULL,'AssociationName_31',NULL),(185,NULL,NULL,NULL,'AssociationName_30',NULL),(186,NULL,NULL,NULL,'AssociationName_29',NULL),(187,NULL,NULL,NULL,'AssociationName_28',NULL),(188,'2008-11-20','cider-FinancialClass','2008-11-20','FinancialClass',NULL),(189,NULL,NULL,NULL,'id',NULL),(190,NULL,NULL,NULL,'AssociationName_27',NULL),(191,'2008-11-20','cider-Insurance','2008-11-20','Insurance',NULL),(192,NULL,NULL,NULL,'AssociationName_2',NULL),(193,NULL,NULL,NULL,'AssociationName_1',NULL),(194,NULL,NULL,NULL,'preAdmitCert',NULL),(195,NULL,NULL,NULL,'policyName',NULL),(196,NULL,NULL,NULL,'insuredGroupEmpName',NULL),(197,NULL,NULL,NULL,'insuredGroupEmpId',NULL),(198,NULL,NULL,NULL,'groupName',NULL),(199,NULL,NULL,NULL,'groupNumber',NULL),(200,NULL,NULL,NULL,'companyId',NULL),(201,NULL,NULL,NULL,'planId',NULL),(202,NULL,NULL,NULL,'sequence',NULL),(203,NULL,NULL,NULL,'id',NULL),(204,NULL,NULL,NULL,'AssociationName_26',NULL),(205,NULL,NULL,NULL,'AssociationName_25',NULL),(206,'2008-11-20','cider-ClinicalTrial','2008-11-20','ClinicalTrial',NULL),(207,NULL,NULL,NULL,'AssociationName_1',NULL),(208,NULL,NULL,NULL,'protocolId',NULL),(209,NULL,NULL,NULL,'status',NULL),(210,NULL,NULL,NULL,'endTimeStamp',NULL),(211,NULL,NULL,NULL,'startTimeStamp',NULL),(212,NULL,NULL,NULL,'trailName',NULL),(213,NULL,NULL,NULL,'trialId',NULL),(214,NULL,NULL,NULL,'id',NULL),(215,NULL,NULL,NULL,'AssociationName_24',NULL),(216,'2008-11-20','cider-Service','2008-11-20','Service',NULL),(217,NULL,NULL,NULL,'id',NULL),(218,NULL,NULL,NULL,'AssociationName_23',NULL),(219,NULL,NULL,NULL,'AssociationName_22',NULL),(220,NULL,NULL,NULL,'AssociationName_21',NULL),(221,'2008-11-20','cider-Procedure','2008-11-20','Procedure',NULL),(222,NULL,NULL,NULL,'AssociationName_5',NULL),(223,NULL,NULL,NULL,'AssociationName_4',NULL),(224,'2008-11-20','cider-ProcedureType','2008-11-20','ProcedureType',NULL),(225,NULL,NULL,NULL,'id',NULL),(226,NULL,NULL,NULL,'AssociationName_3',NULL),(227,NULL,NULL,NULL,'AssociationName_2',NULL),(228,NULL,NULL,NULL,'AssociationName_1',NULL),(229,NULL,NULL,NULL,'sequence',NULL),(230,NULL,NULL,NULL,'date',NULL),(231,NULL,NULL,NULL,'id',NULL),(232,NULL,NULL,NULL,'AssociationName_20',NULL),(233,'2008-11-20','cider-HipaaNotified','2008-11-20','HipaaNotified',NULL),(234,NULL,NULL,NULL,'id',NULL),(235,NULL,NULL,NULL,'AssociationName_19',NULL),(236,'2008-11-20','cider-InfectionControlCode','2008-11-20','InfectionControlCode',NULL),(237,NULL,NULL,NULL,'id',NULL),(238,NULL,NULL,NULL,'AssociationName_18',NULL),(239,'2008-11-20','cider-DischargeDisposition','2008-11-20','DischargeDisposition',NULL),(240,NULL,NULL,NULL,'id',NULL),(241,NULL,NULL,NULL,'AssociationName_17',NULL),(242,NULL,NULL,NULL,'AssociationName_4',NULL),(243,NULL,NULL,NULL,'AssociationName_3',NULL),(244,NULL,NULL,NULL,'AssociationName_2',NULL),(245,NULL,NULL,NULL,'AssociationName_1',NULL),(246,NULL,NULL,NULL,'admittingService',NULL),(247,NULL,NULL,NULL,'dischargeService',NULL),(248,NULL,NULL,NULL,'teachingTeam',NULL),(249,NULL,NULL,NULL,'dischargeTimeStamp',NULL),(250,NULL,NULL,NULL,'registrationTimeStamp',NULL),(251,NULL,NULL,NULL,'ageAtEncounter',NULL),(252,NULL,NULL,NULL,'effectingEndTimeStamp',NULL),(253,NULL,NULL,NULL,'effectingStartTimeStamp',NULL),(254,NULL,NULL,NULL,'id',NULL),(255,NULL,NULL,NULL,'AssociationName_5',NULL),(256,NULL,NULL,NULL,'AssociationName_6',NULL),(257,NULL,NULL,NULL,'AssociationName_7',NULL),(258,NULL,NULL,NULL,'AssociationName_8',NULL),(259,NULL,NULL,NULL,'AssociationName_9',NULL),(260,NULL,NULL,NULL,'AssociationName_10',NULL),(261,NULL,NULL,NULL,'AssociationName_11',NULL),(262,NULL,NULL,NULL,'AssociationName_12',NULL),(263,'2008-11-20','cider-PatientClass','2008-11-20','PatientClass',NULL),(264,NULL,NULL,NULL,'id',NULL),(265,NULL,NULL,NULL,'AssociationName_13',NULL),(266,'2008-11-20','cider-PatientType','2008-11-20','PatientType',NULL),(267,NULL,NULL,NULL,'id',NULL),(268,NULL,NULL,NULL,'AssociationName_14',NULL),(269,'2008-11-20','cider-Diagnosis','2008-11-20','Diagnosis',NULL),(270,NULL,NULL,NULL,'AssociationName_4',NULL),(271,'2008-11-20','cider-DiagnosisCode','2008-11-20','DiagnosisCode',NULL),(272,NULL,NULL,NULL,'id',NULL),(273,NULL,NULL,NULL,'AssociationName_3',NULL),(274,NULL,NULL,NULL,'AssociationName_2',NULL),(275,NULL,NULL,NULL,'AssociationName_1',NULL),(276,NULL,NULL,NULL,'sequence',NULL),(277,NULL,NULL,NULL,'description',NULL),(278,NULL,NULL,NULL,'id',NULL),(279,NULL,NULL,NULL,'AssociationName_15',NULL),(280,'2008-11-20','cider-OptOutIndicator','2008-11-20','OptOutIndicator',NULL),(281,NULL,NULL,NULL,'id',NULL),(282,NULL,NULL,NULL,'AssociationName_16',NULL),(283,'2008-11-20','cider-CptProcedureCode','2008-11-20','CptProcedureCode',NULL),(284,NULL,NULL,NULL,'id',NULL),(285,'2008-11-20','cider-ResearchOptOut','2008-11-20','ResearchOptOut',NULL),(286,NULL,NULL,NULL,'id',NULL),(287,'2008-11-20','cider-MrnType','2008-11-20','MrnType',NULL),(288,NULL,NULL,NULL,'id',NULL),(289,'2008-11-20','cider-Encounter','2008-11-20','Encounter',NULL),(290,NULL,NULL,NULL,'AssociationName_4',NULL),(291,NULL,NULL,NULL,'AssociationName_3',NULL),(292,NULL,NULL,NULL,'AssociationName_2',NULL),(293,NULL,NULL,NULL,'AssociationName_1',NULL),(294,NULL,NULL,NULL,'patientAccountNumber',NULL),(295,NULL,NULL,NULL,'id',NULL);

/*Table structure for table `dyextn_asso_display_attr` */

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

insert into `dyextn_association` (`IDENTIFIER`,`IS_COLLECTION`,`DIRECTION`,`TARGET_ENTITY_ID`,`SOURCE_ROLE_ID`,`TARGET_ROLE_ID`,`IS_SYSTEM_GENERATED`) values (12,'\0','SRC_DESTINATION',8,1,2,'\0'),(22,'\0','SRC_DESTINATION',13,3,4,'\0'),(27,'\0','SRC_DESTINATION',25,5,6,'\0'),(30,'\0','SRC_DESTINATION',28,7,8,'\0'),(33,'\0','SRC_DESTINATION',31,9,10,'\0'),(36,'\0','SRC_DESTINATION',34,11,12,'\0'),(39,'\0','SRC_DESTINATION',37,13,14,'\0'),(42,'\0','SRC_DESTINATION',40,15,16,'\0'),(51,'\0','SRC_DESTINATION',13,17,18,'\0'),(55,'\0','SRC_DESTINATION',53,19,20,'\0'),(56,'\0','SRC_DESTINATION',23,21,22,'\0'),(58,'\0','SRC_DESTINATION',52,23,24,'\0'),(59,'\0','SRC_DESTINATION',52,25,26,'\0'),(64,'\0','SRC_DESTINATION',60,27,28,'\0'),(68,'\0','SRC_DESTINATION',66,29,30,'\0'),(71,'\0','SRC_DESTINATION',69,31,32,'\0'),(74,'\0','SRC_DESTINATION',72,33,34,'\0'),(80,'\0','SRC_DESTINATION',65,35,36,'\0'),(81,'\0','SRC_DESTINATION',24,37,38,'\0'),(84,'\0','SRC_DESTINATION',23,39,40,'\0'),(107,'\0','SRC_DESTINATION',98,41,42,'\0'),(111,'\0','SRC_DESTINATION',108,43,44,'\0'),(112,'\0','SRC_DESTINATION',23,45,46,'\0'),(115,'\0','SRC_DESTINATION',113,47,48,'\0'),(117,'\0','SRC_DESTINATION',102,49,50,'\0'),(125,'\0','SRC_DESTINATION',108,51,52,'\0'),(129,'\0','SRC_DESTINATION',124,53,54,'\0'),(130,'\0','SRC_DESTINATION',8,55,56,'\0'),(131,'\0','SRC_DESTINATION',8,57,58,'\0'),(133,'\0','SRC_DESTINATION',102,59,60,'\0'),(136,'\0','SRC_DESTINATION',93,61,62,'\0'),(142,'\0','SRC_DESTINATION',135,63,64,'\0'),(143,'\0','SRC_DESTINATION',135,65,66,'\0'),(145,'\0','SRC_DESTINATION',134,67,68,'\0'),(148,'\0','SRC_DESTINATION',146,69,70,'\0'),(152,'\0','SRC_DESTINATION',149,71,72,'\0'),(157,'\0','SRC_DESTINATION',132,73,74,'\0'),(160,'\0','SRC_DESTINATION',158,75,76,'\0'),(162,'\0','SRC_DESTINATION',108,77,78,'\0'),(165,'\0','SRC_DESTINATION',161,79,80,'\0'),(166,'\0','SRC_DESTINATION',108,81,82,'\0'),(167,'\0','SRC_DESTINATION',116,83,84,'\0'),(183,'\0','SRC_DESTINATION',2,85,86,'\0'),(184,'\0','SRC_DESTINATION',7,87,88,'\0'),(185,'\0','SRC_DESTINATION',89,89,90,'\0'),(186,'\0','SRC_DESTINATION',100,91,92,'\0'),(187,'\0','SRC_DESTINATION',104,93,94,'\0'),(190,'\0','SRC_DESTINATION',188,95,96,'\0'),(192,'\0','SRC_DESTINATION',8,97,98,'\0'),(193,'\0','SRC_DESTINATION',13,99,100,'\0'),(204,'\0','SRC_DESTINATION',191,101,102,'\0'),(205,'\0','SRC_DESTINATION',108,103,104,'\0'),(207,'\0','SRC_DESTINATION',13,105,106,'\0'),(215,'\0','SRC_DESTINATION',206,107,108,'\0'),(218,'\0','SRC_DESTINATION',216,109,110,'\0'),(219,'\0','SRC_DESTINATION',216,111,112,'\0'),(220,'\0','SRC_DESTINATION',161,113,114,'\0'),(222,'\0','SRC_DESTINATION',7,115,116,'\0'),(223,'\0','SRC_DESTINATION',176,117,118,'\0'),(226,'\0','SRC_DESTINATION',224,119,120,'\0'),(227,'\0','SRC_DESTINATION',8,121,122,'\0'),(228,'\0','SRC_DESTINATION',8,123,124,'\0'),(232,'\0','SRC_DESTINATION',221,125,126,'\0'),(235,'\0','SRC_DESTINATION',233,127,128,'\0'),(238,'\0','SRC_DESTINATION',236,129,130,'\0'),(241,'\0','SRC_DESTINATION',239,131,132,'\0'),(242,'\0','SRC_DESTINATION',8,133,134,'\0'),(243,'\0','SRC_DESTINATION',8,135,136,'\0'),(244,'\0','SRC_DESTINATION',8,137,138,'\0'),(245,'\0','SRC_DESTINATION',8,139,140,'\0'),(255,'\0','SRC_DESTINATION',8,141,142,'\0'),(256,'\0','SRC_DESTINATION',8,143,144,'\0'),(257,'\0','SRC_DESTINATION',8,145,146,'\0'),(258,'\0','SRC_DESTINATION',8,147,148,'\0'),(259,'\0','SRC_DESTINATION',8,149,150,'\0'),(260,'\0','SRC_DESTINATION',8,151,152,'\0'),(261,'\0','SRC_DESTINATION',8,153,154,'\0'),(262,'\0','SRC_DESTINATION',8,155,156,'\0'),(265,'\0','SRC_DESTINATION',263,157,158,'\0'),(268,'\0','SRC_DESTINATION',266,159,160,'\0'),(270,'\0','SRC_DESTINATION',180,161,162,'\0'),(273,'\0','SRC_DESTINATION',271,163,164,'\0'),(274,'\0','SRC_DESTINATION',8,165,166,'\0'),(275,'\0','SRC_DESTINATION',8,167,168,'\0'),(279,'\0','SRC_DESTINATION',269,169,170,'\0'),(282,'\0','SRC_DESTINATION',280,171,172,'\0'),(290,'\0','SRC_DESTINATION',182,173,174,'\0'),(291,'\0','SRC_DESTINATION',23,175,176,'\0'),(292,'\0','SRC_DESTINATION',124,177,178,'\0'),(293,'\0','SRC_DESTINATION',108,179,180,'\0');

/*Table structure for table `dyextn_attribute` */

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

insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (3,2),(4,2),(5,2),(6,2),(9,8),(10,8),(11,8),(12,7),(14,13),(15,13),(16,13),(17,13),(18,13),(19,13),(20,13),(21,13),(22,7),(26,25),(27,24),(29,28),(30,24),(32,31),(33,24),(35,34),(36,24),(38,37),(39,24),(41,40),(42,24),(43,24),(44,24),(45,24),(46,24),(47,24),(48,24),(49,24),(50,24),(51,24),(54,53),(55,52),(56,52),(57,52),(58,24),(59,24),(61,60),(62,60),(63,60),(64,24),(67,66),(68,65),(70,69),(71,65),(73,72),(74,65),(75,65),(76,65),(77,65),(78,65),(79,65),(80,24),(81,23),(82,23),(83,23),(84,7),(85,7),(86,7),(88,87),(90,89),(92,91),(94,93),(95,93),(96,93),(97,93),(99,98),(101,100),(103,102),(105,104),(107,106),(109,108),(110,108),(111,106),(112,106),(114,113),(115,106),(117,116),(118,116),(119,116),(120,116),(121,116),(122,116),(123,116),(125,124),(126,124),(127,124),(128,124),(129,116),(130,116),(131,116),(133,132),(136,135),(137,135),(138,135),(139,135),(140,135),(141,135),(142,134),(143,134),(144,134),(145,132),(147,146),(148,132),(150,149),(151,149),(152,132),(153,132),(154,132),(155,132),(156,132),(157,116),(159,158),(160,116),(162,161),(163,161),(164,161),(165,116),(166,116),(167,106),(168,106),(169,106),(170,106),(171,106),(173,172),(174,172),(175,172),(177,176),(179,178),(181,180),(183,182),(184,182),(185,182),(186,182),(187,182),(189,188),(190,182),(192,191),(193,191),(194,191),(195,191),(196,191),(197,191),(198,191),(199,191),(200,191),(201,191),(202,191),(203,191),(204,182),(205,182),(207,206),(208,206),(209,206),(210,206),(211,206),(212,206),(213,206),(214,206),(215,182),(217,216),(218,182),(219,182),(220,182),(222,221),(223,221),(225,224),(226,221),(227,221),(228,221),(229,221),(230,221),(231,221),(232,182),(234,233),(235,182),(237,236),(238,182),(240,239),(241,182),(242,182),(243,182),(244,182),(245,182),(246,182),(247,182),(248,182),(249,182),(250,182),(251,182),(252,182),(253,182),(254,182),(255,182),(256,182),(257,182),(258,182),(259,182),(260,182),(261,182),(262,182),(264,263),(265,182),(267,266),(268,182),(270,269),(272,271),(273,269),(274,269),(275,269),(276,269),(277,269),(278,269),(279,182),(281,280),(282,182),(284,283),(286,285),(288,287),(290,289),(291,289),(292,289),(293,289),(294,289),(295,289);

/*Table structure for table `dyextn_attribute_record` */

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

CREATE TABLE `dyextn_attribute_type_info` (
  `IDENTIFIER` bigint(20) NOT NULL auto_increment,
  `PRIMITIVE_ATTRIBUTE_ID` bigint(20) default NULL,
  PRIMARY KEY  (`IDENTIFIER`),
  KEY `FK62596D531333996E` (`PRIMITIVE_ATTRIBUTE_ID`),
  CONSTRAINT `FK62596D531333996E` FOREIGN KEY (`PRIMITIVE_ATTRIBUTE_ID`) REFERENCES `dyextn_primitive_attribute` (`IDENTIFIER`)
) ENGINE=InnoDB AUTO_INCREMENT=143 DEFAULT CHARSET=latin1;

/*Data for the table `dyextn_attribute_type_info` */

insert into `dyextn_attribute_type_info` (`IDENTIFIER`,`PRIMITIVE_ATTRIBUTE_ID`) values (1,3),(2,4),(3,5),(4,6),(5,9),(6,10),(7,11),(8,14),(9,15),(10,16),(11,17),(12,18),(13,19),(14,20),(15,21),(16,26),(17,29),(18,32),(19,35),(20,38),(21,41),(22,43),(23,44),(24,45),(25,46),(26,47),(27,48),(28,49),(29,50),(30,54),(31,57),(32,61),(33,62),(34,63),(35,67),(36,70),(37,73),(38,75),(39,76),(40,77),(41,78),(42,79),(43,82),(44,83),(45,85),(46,86),(47,88),(48,90),(49,92),(50,94),(51,95),(52,96),(53,97),(54,99),(55,101),(56,103),(57,105),(58,109),(59,110),(60,114),(61,118),(62,119),(63,120),(64,121),(65,122),(66,123),(67,126),(68,127),(69,128),(70,137),(71,138),(72,139),(73,140),(74,141),(75,144),(76,147),(77,150),(78,151),(79,153),(80,154),(81,155),(82,156),(83,159),(84,163),(85,164),(86,168),(87,169),(88,170),(89,171),(90,173),(91,174),(92,175),(93,177),(94,179),(95,181),(96,189),(97,194),(98,195),(99,196),(100,197),(101,198),(102,199),(103,200),(104,201),(105,202),(106,203),(107,208),(108,209),(109,210),(110,211),(111,212),(112,213),(113,214),(114,217),(115,225),(116,229),(117,230),(118,231),(119,234),(120,237),(121,240),(122,246),(123,247),(124,248),(125,249),(126,250),(127,251),(128,252),(129,253),(130,254),(131,264),(132,267),(133,272),(134,276),(135,277),(136,278),(137,281),(138,284),(139,286),(140,288),(141,294),(142,295);

/*Table structure for table `dyextn_barr_concept_value` */

CREATE TABLE `dyextn_barr_concept_value` (
  `IDENTIFIER` bigint(20) NOT NULL,
  PRIMARY KEY  (`IDENTIFIER`),
  KEY `FK89D27DF74641D513` (`IDENTIFIER`),
  CONSTRAINT `FK89D27DF74641D513` FOREIGN KEY (`IDENTIFIER`) REFERENCES `dyextn_permissible_value` (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `dyextn_barr_concept_value` */

/*Table structure for table `dyextn_base_abstract_attribute` */

CREATE TABLE `dyextn_base_abstract_attribute` (
  `IDENTIFIER` bigint(20) NOT NULL,
  PRIMARY KEY  (`IDENTIFIER`),
  KEY `FK14BA6610728B19BE` (`IDENTIFIER`),
  CONSTRAINT `FK14BA6610728B19BE` FOREIGN KEY (`IDENTIFIER`) REFERENCES `dyextn_abstract_metadata` (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `dyextn_base_abstract_attribute` */

insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (3),(4),(5),(6),(9),(10),(11),(12),(14),(15),(16),(17),(18),(19),(20),(21),(22),(26),(27),(29),(30),(32),(33),(35),(36),(38),(39),(41),(42),(43),(44),(45),(46),(47),(48),(49),(50),(51),(54),(55),(56),(57),(58),(59),(61),(62),(63),(64),(67),(68),(70),(71),(73),(74),(75),(76),(77),(78),(79),(80),(81),(82),(83),(84),(85),(86),(88),(90),(92),(94),(95),(96),(97),(99),(101),(103),(105),(107),(109),(110),(111),(112),(114),(115),(117),(118),(119),(120),(121),(122),(123),(125),(126),(127),(128),(129),(130),(131),(133),(136),(137),(138),(139),(140),(141),(142),(143),(144),(145),(147),(148),(150),(151),(152),(153),(154),(155),(156),(157),(159),(160),(162),(163),(164),(165),(166),(167),(168),(169),(170),(171),(173),(174),(175),(177),(179),(181),(183),(184),(185),(186),(187),(189),(190),(192),(193),(194),(195),(196),(197),(198),(199),(200),(201),(202),(203),(204),(205),(207),(208),(209),(210),(211),(212),(213),(214),(215),(217),(218),(219),(220),(222),(223),(225),(226),(227),(228),(229),(230),(231),(232),(234),(235),(237),(238),(240),(241),(242),(243),(244),(245),(246),(247),(248),(249),(250),(251),(252),(253),(254),(255),(256),(257),(258),(259),(260),(261),(262),(264),(265),(267),(268),(270),(272),(273),(274),(275),(276),(277),(278),(279),(281),(282),(284),(286),(288),(290),(291),(292),(293),(294),(295);

/*Table structure for table `dyextn_boolean_concept_value` */

CREATE TABLE `dyextn_boolean_concept_value` (
  `IDENTIFIER` bigint(20) NOT NULL,
  `VALUE` bit(1) default NULL,
  PRIMARY KEY  (`IDENTIFIER`),
  KEY `FK57B6C4A64641D513` (`IDENTIFIER`),
  CONSTRAINT `FK57B6C4A64641D513` FOREIGN KEY (`IDENTIFIER`) REFERENCES `dyextn_permissible_value` (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `dyextn_boolean_concept_value` */

/*Table structure for table `dyextn_boolean_type_info` */

CREATE TABLE `dyextn_boolean_type_info` (
  `IDENTIFIER` bigint(20) NOT NULL,
  PRIMARY KEY  (`IDENTIFIER`),
  KEY `FK28F1809FE5294FA3` (`IDENTIFIER`),
  CONSTRAINT `FK28F1809FE5294FA3` FOREIGN KEY (`IDENTIFIER`) REFERENCES `dyextn_attribute_type_info` (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `dyextn_boolean_type_info` */

/*Table structure for table `dyextn_byte_array_type_info` */

CREATE TABLE `dyextn_byte_array_type_info` (
  `IDENTIFIER` bigint(20) NOT NULL,
  `CONTENT_TYPE` varchar(255) default NULL,
  PRIMARY KEY  (`IDENTIFIER`),
  KEY `FK18BDA73E5294FA3` (`IDENTIFIER`),
  CONSTRAINT `FK18BDA73E5294FA3` FOREIGN KEY (`IDENTIFIER`) REFERENCES `dyextn_attribute_type_info` (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `dyextn_byte_array_type_info` */

/*Table structure for table `dyextn_cadsr_value_domain_info` */

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

CREATE TABLE `dyextn_cadsrde` (
  `IDENTIFIER` bigint(20) NOT NULL,
  `PUBLIC_ID` varchar(255) default NULL,
  PRIMARY KEY  (`IDENTIFIER`),
  KEY `FK588A250953CC4A77` (`IDENTIFIER`),
  CONSTRAINT `FK588A250953CC4A77` FOREIGN KEY (`IDENTIFIER`) REFERENCES `dyextn_data_element` (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `dyextn_cadsrde` */

/*Table structure for table `dyextn_cat_asso_ctl` */

CREATE TABLE `dyextn_cat_asso_ctl` (
  `IDENTIFIER` bigint(20) NOT NULL,
  PRIMARY KEY  (`IDENTIFIER`),
  KEY `FK281FAB50C45A8CFA` (`IDENTIFIER`),
  CONSTRAINT `FK281FAB50C45A8CFA` FOREIGN KEY (`IDENTIFIER`) REFERENCES `dyextn_abstr_contain_ctr` (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `dyextn_cat_asso_ctl` */

/*Table structure for table `dyextn_category` */

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

CREATE TABLE `dyextn_check_box` (
  `IDENTIFIER` bigint(20) NOT NULL,
  PRIMARY KEY  (`IDENTIFIER`),
  KEY `FK4EFF925740F198C2` (`IDENTIFIER`),
  CONSTRAINT `FK4EFF925740F198C2` FOREIGN KEY (`IDENTIFIER`) REFERENCES `dyextn_control` (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `dyextn_check_box` */

/*Table structure for table `dyextn_column_properties` */

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

insert into `dyextn_column_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`CATEGORY_ATTRIBUTE_ID`,`PRIMITIVE_ATTRIBUTE_ID`) values (2,NULL,NULL,3),(3,NULL,NULL,4),(4,NULL,NULL,5),(5,NULL,NULL,6),(8,NULL,NULL,9),(9,NULL,NULL,10),(10,NULL,NULL,11),(13,NULL,NULL,14),(14,NULL,NULL,15),(15,NULL,NULL,16),(16,NULL,NULL,17),(17,NULL,NULL,18),(18,NULL,NULL,19),(19,NULL,NULL,20),(20,NULL,NULL,21),(25,NULL,NULL,26),(28,NULL,NULL,29),(31,NULL,NULL,32),(34,NULL,NULL,35),(37,NULL,NULL,38),(40,NULL,NULL,41),(42,NULL,NULL,43),(43,NULL,NULL,44),(44,NULL,NULL,45),(45,NULL,NULL,46),(46,NULL,NULL,47),(47,NULL,NULL,48),(48,NULL,NULL,49),(49,NULL,NULL,50),(53,NULL,NULL,54),(56,NULL,NULL,57),(60,NULL,NULL,61),(61,NULL,NULL,62),(62,NULL,NULL,63),(66,NULL,NULL,67),(69,NULL,NULL,70),(72,NULL,NULL,73),(74,NULL,NULL,75),(75,NULL,NULL,76),(76,NULL,NULL,77),(77,NULL,NULL,78),(78,NULL,NULL,79),(81,NULL,NULL,82),(82,NULL,NULL,83),(84,NULL,NULL,85),(85,NULL,NULL,86),(87,NULL,NULL,88),(89,NULL,NULL,90),(91,NULL,NULL,92),(93,NULL,NULL,94),(94,NULL,NULL,95),(95,NULL,NULL,96),(96,NULL,NULL,97),(98,NULL,NULL,99),(100,NULL,NULL,101),(102,NULL,NULL,103),(104,NULL,NULL,105),(108,NULL,NULL,109),(109,NULL,NULL,110),(113,NULL,NULL,114),(117,NULL,NULL,118),(118,NULL,NULL,119),(119,NULL,NULL,120),(120,NULL,NULL,121),(121,NULL,NULL,122),(122,NULL,NULL,123),(125,NULL,NULL,126),(126,NULL,NULL,127),(127,NULL,NULL,128),(136,NULL,NULL,137),(137,NULL,NULL,138),(138,NULL,NULL,139),(139,NULL,NULL,140),(140,NULL,NULL,141),(143,NULL,NULL,144),(146,NULL,NULL,147),(149,NULL,NULL,150),(150,NULL,NULL,151),(152,NULL,NULL,153),(153,NULL,NULL,154),(154,NULL,NULL,155),(155,NULL,NULL,156),(158,NULL,NULL,159),(162,NULL,NULL,163),(163,NULL,NULL,164),(167,NULL,NULL,168),(168,NULL,NULL,169),(169,NULL,NULL,170),(170,NULL,NULL,171),(172,NULL,NULL,173),(173,NULL,NULL,174),(174,NULL,NULL,175),(176,NULL,NULL,177),(178,NULL,NULL,179),(180,NULL,NULL,181),(188,NULL,NULL,189),(193,NULL,NULL,194),(194,NULL,NULL,195),(195,NULL,NULL,196),(196,NULL,NULL,197),(197,NULL,NULL,198),(198,NULL,NULL,199),(199,NULL,NULL,200),(200,NULL,NULL,201),(201,NULL,NULL,202),(202,NULL,NULL,203),(207,NULL,NULL,208),(208,NULL,NULL,209),(209,NULL,NULL,210),(210,NULL,NULL,211),(211,NULL,NULL,212),(212,NULL,NULL,213),(213,NULL,NULL,214),(216,NULL,NULL,217),(224,NULL,NULL,225),(228,NULL,NULL,229),(229,NULL,NULL,230),(230,NULL,NULL,231),(233,NULL,NULL,234),(236,NULL,NULL,237),(239,NULL,NULL,240),(245,NULL,NULL,246),(246,NULL,NULL,247),(247,NULL,NULL,248),(248,NULL,NULL,249),(249,NULL,NULL,250),(250,NULL,NULL,251),(251,NULL,NULL,252),(252,NULL,NULL,253),(253,NULL,NULL,254),(263,NULL,NULL,264),(266,NULL,NULL,267),(271,NULL,NULL,272),(275,NULL,NULL,276),(276,NULL,NULL,277),(277,NULL,NULL,278),(280,NULL,NULL,281),(283,NULL,NULL,284),(285,NULL,NULL,286),(287,NULL,NULL,288),(293,NULL,NULL,294),(294,NULL,NULL,295);

/*Table structure for table `dyextn_combobox` */

CREATE TABLE `dyextn_combobox` (
  `IDENTIFIER` bigint(20) NOT NULL,
  PRIMARY KEY  (`IDENTIFIER`),
  KEY `FKABBC649ABF67AB26` (`IDENTIFIER`),
  CONSTRAINT `FKABBC649ABF67AB26` FOREIGN KEY (`IDENTIFIER`) REFERENCES `dyextn_select_control` (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `dyextn_combobox` */

insert into `dyextn_combobox` (`IDENTIFIER`) values (5),(6),(11),(17),(22),(23),(24),(25),(32),(33),(34),(35),(36),(37),(38),(39),(40),(41),(42),(43),(44),(45),(54),(65),(66),(67),(68),(69),(70),(71),(72),(73),(74),(75),(76),(77),(78),(79),(80),(84),(85),(86),(87),(88),(89),(90),(91),(92),(93),(94),(95),(96),(97),(98),(99),(100),(101),(102),(103),(118),(119),(125),(126),(127),(128),(129),(131),(132),(133),(137),(138),(139),(140),(141),(142),(148),(149),(150),(151),(152),(153),(154),(158),(159),(160),(161);

/*Table structure for table `dyextn_constraint_properties` */

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

insert into `dyextn_constraint_properties` (`IDENTIFIER`,`SOURCE_ENTITY_KEY`,`TARGET_ENTITY_KEY`,`SRC_CONSTRAINT_NAME`,`TARGET_CONSTRAINT_NAME`,`CATEGORY_ASSOCIATION_ID`,`ASSOCIATION_ID`) values (11,'DE_E_S_770',NULL,'CONSRT_772','CONSRT_773',NULL,12),(21,'DE_E_S_765',NULL,'CONSRT_767','CONSRT_768',NULL,22),(26,'DE_E_S_720','DE_E_T_721','CONSRT_722','CONSRT_723',NULL,27),(29,'DE_E_S_620',NULL,'CONSRT_622','CONSRT_623',NULL,30),(32,'DE_E_S_605',NULL,'CONSRT_607','CONSRT_608',NULL,33),(35,'DE_E_S_595',NULL,'CONSRT_597','CONSRT_598',NULL,36),(38,'DE_E_S_565','DE_E_T_566','CONSRT_567','CONSRT_568',NULL,39),(41,'DE_E_S_545',NULL,'CONSRT_547','CONSRT_548',NULL,42),(50,NULL,'DE_E_T_521','CONSRT_522','CONSRT_523',NULL,51),(54,'DE_E_S_735',NULL,'CONSRT_737','CONSRT_738',NULL,55),(55,'DE_E_S_510',NULL,'CONSRT_512','CONSRT_513',NULL,56),(57,'DE_E_S_525','DE_E_T_526','CONSRT_527','CONSRT_528',NULL,58),(58,'DE_E_S_530',NULL,'CONSRT_532','CONSRT_533',NULL,59),(63,'DE_E_S_535','DE_E_T_536','CONSRT_537','CONSRT_538',NULL,64),(67,'DE_E_S_745',NULL,'CONSRT_747','CONSRT_748',NULL,68),(70,'DE_E_S_670',NULL,'CONSRT_672','CONSRT_673',NULL,71),(73,'DE_E_S_600',NULL,'CONSRT_602','CONSRT_603',NULL,74),(79,NULL,'DE_E_T_541','CONSRT_542','CONSRT_543',NULL,80),(80,NULL,'DE_E_T_516','CONSRT_517','CONSRT_518',NULL,81),(83,'DE_E_S_760',NULL,'CONSRT_762','CONSRT_763',NULL,84),(106,'DE_E_S_725',NULL,'CONSRT_727','CONSRT_728',NULL,107),(110,'DE_E_S_645',NULL,'CONSRT_647','CONSRT_648',NULL,111),(111,'DE_E_S_495',NULL,'CONSRT_497','CONSRT_498',NULL,112),(114,'DE_E_S_490',NULL,'CONSRT_492','CONSRT_493',NULL,115),(116,'DE_E_S_705',NULL,'CONSRT_707','CONSRT_708',NULL,117),(124,'DE_E_S_630',NULL,'CONSRT_632','CONSRT_633',NULL,125),(128,'DE_E_S_445',NULL,'CONSRT_447','CONSRT_448',NULL,129),(129,'DE_E_S_450','DE_E_T_451','CONSRT_452','CONSRT_453',NULL,130),(130,'DE_E_S_455','DE_E_T_456','CONSRT_457','CONSRT_458',NULL,131),(132,'DE_E_S_710',NULL,'CONSRT_712','CONSRT_713',NULL,133),(135,'DE_E_S_730',NULL,'CONSRT_732','CONSRT_733',NULL,136),(141,'DE_E_S_480',NULL,'CONSRT_482','CONSRT_483',NULL,142),(142,'DE_E_S_475',NULL,'CONSRT_477','CONSRT_478',NULL,143),(144,'DE_E_S_470',NULL,'CONSRT_472','CONSRT_473',NULL,145),(147,'DE_E_S_425','DE_E_T_426','CONSRT_427','CONSRT_428',NULL,148),(151,'DE_E_S_420',NULL,'CONSRT_422','CONSRT_423',NULL,152),(156,'DE_E_S_460','DE_E_T_461','CONSRT_462','CONSRT_463',NULL,157),(159,'DE_E_S_465',NULL,'CONSRT_467','CONSRT_468',NULL,160),(161,'DE_E_S_635',NULL,'CONSRT_637','CONSRT_638',NULL,162),(164,'DE_E_S_575',NULL,'CONSRT_577','CONSRT_578',NULL,165),(165,NULL,'DE_E_T_641','CONSRT_642','CONSRT_643',NULL,166),(166,'DE_E_S_485','DE_E_T_486','CONSRT_487','CONSRT_488',NULL,167),(182,NULL,'DE_E_T_776','CONSRT_777','CONSRT_778',NULL,183),(183,'DE_E_S_750','DE_E_T_751','CONSRT_752','CONSRT_753',NULL,184),(184,'DE_E_S_740',NULL,'CONSRT_742','CONSRT_743',NULL,185),(185,'DE_E_S_715',NULL,'CONSRT_717','CONSRT_718',NULL,186),(186,'DE_E_S_700',NULL,'CONSRT_702','CONSRT_703',NULL,187),(189,'DE_E_S_690',NULL,'CONSRT_692','CONSRT_693',NULL,190),(191,'DE_E_S_685',NULL,'CONSRT_687','CONSRT_688',NULL,192),(192,'DE_E_S_680',NULL,'CONSRT_682','CONSRT_683',NULL,193),(203,'DE_E_S_675','DE_E_T_676','CONSRT_677','CONSRT_678',NULL,204),(204,NULL,'DE_E_T_626','CONSRT_627','CONSRT_628',NULL,205),(206,'DE_E_S_615',NULL,'CONSRT_617','CONSRT_618',NULL,207),(214,'DE_E_S_610','DE_E_T_611','CONSRT_612','CONSRT_613',NULL,215),(217,'DE_E_S_590',NULL,'CONSRT_592','CONSRT_593',NULL,218),(218,'DE_E_S_585',NULL,'CONSRT_587','CONSRT_588',NULL,219),(219,NULL,'DE_E_T_581','CONSRT_582','CONSRT_583',NULL,220),(221,NULL,'DE_E_T_756','CONSRT_757','CONSRT_758',NULL,222),(222,'DE_E_S_695','DE_E_T_696','CONSRT_697','CONSRT_698',NULL,223),(225,'DE_E_S_570','DE_E_T_571','CONSRT_572','CONSRT_573',NULL,226),(226,NULL,'DE_E_T_561','CONSRT_562','CONSRT_563',NULL,227),(227,NULL,'DE_E_T_556','CONSRT_557','CONSRT_558',NULL,228),(231,'DE_E_S_550','DE_E_T_551','CONSRT_552','CONSRT_553',NULL,232),(234,'DE_E_S_505',NULL,'CONSRT_507','CONSRT_508',NULL,235),(237,'DE_E_S_500','DE_E_T_501','CONSRT_502','CONSRT_503',NULL,238),(240,'DE_E_S_435',NULL,'CONSRT_437','CONSRT_438',NULL,241),(241,'DE_E_S_345',NULL,'CONSRT_347','CONSRT_348',NULL,242),(242,'DE_E_S_340',NULL,'CONSRT_342','CONSRT_343',NULL,243),(243,'DE_E_S_335',NULL,'CONSRT_337','CONSRT_338',NULL,244),(244,'DE_E_S_330',NULL,'CONSRT_332','CONSRT_333',NULL,245),(254,'DE_E_S_350','DE_E_T_351','CONSRT_352','CONSRT_353',NULL,255),(255,'DE_E_S_355',NULL,'CONSRT_357','CONSRT_358',NULL,256),(256,'DE_E_S_360',NULL,'CONSRT_362','CONSRT_363',NULL,257),(257,'DE_E_S_365',NULL,'CONSRT_367','CONSRT_368',NULL,258),(258,'DE_E_S_370',NULL,'CONSRT_372','CONSRT_373',NULL,259),(259,'DE_E_S_375',NULL,'CONSRT_377','CONSRT_378',NULL,260),(260,'DE_E_S_380',NULL,'CONSRT_382','CONSRT_383',NULL,261),(261,NULL,'DE_E_T_386','CONSRT_387','CONSRT_388',NULL,262),(264,'DE_E_S_390',NULL,'CONSRT_392','CONSRT_393',NULL,265),(267,'DE_E_S_395',NULL,'CONSRT_397','CONSRT_398',NULL,268),(269,'DE_E_S_440','DE_E_T_441','CONSRT_442','CONSRT_443',NULL,270),(272,'DE_E_S_430','DE_E_T_431','CONSRT_432','CONSRT_433',NULL,273),(273,'DE_E_S_410',NULL,'CONSRT_412','CONSRT_413',NULL,274),(274,'DE_E_S_405',NULL,'CONSRT_407','CONSRT_408',NULL,275),(278,'DE_E_S_400','DE_E_T_401','CONSRT_402','CONSRT_403',NULL,279),(281,'DE_E_S_415',NULL,'CONSRT_417','CONSRT_418',NULL,282),(289,'DE_E_S_665','DE_E_T_666','CONSRT_667','CONSRT_668',NULL,290),(290,'DE_E_S_660',NULL,'CONSRT_662','CONSRT_663',NULL,291),(291,'DE_E_S_655',NULL,'CONSRT_657','CONSRT_658',NULL,292),(292,'DE_E_S_650',NULL,'CONSRT_652','CONSRT_653',NULL,293);

/*Table structure for table `dyextn_container` */

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
) ENGINE=InnoDB AUTO_INCREMENT=61 DEFAULT CHARSET=latin1;

/*Data for the table `dyextn_container` */

insert into `dyextn_container` (`IDENTIFIER`,`BUTTON_CSS`,`CAPTION`,`ABSTRACT_ENTITY_ID`,`MAIN_TABLE_CSS`,`REQUIRED_FIELD_INDICATOR`,`REQUIRED_FIELD_WARNING_MESSAGE`,`TITLE_CSS`,`BASE_CONTAINER_ID`,`ADD_CAPTION`,`ENTITY_GROUP_ID`,`VIEW_ID`) values (1,NULL,'MrnType',287,NULL,'*','indicates required fields.',NULL,NULL,'',1,NULL),(2,NULL,'Units',172,NULL,'*','indicates required fields.',NULL,NULL,'',1,NULL),(3,NULL,'MedicalEntitiesDictionary',8,NULL,'*','indicates required fields.',NULL,NULL,'',1,NULL),(4,NULL,'Status',102,NULL,'*','indicates required fields.',NULL,3,'',1,NULL),(5,NULL,'ResultValue',134,NULL,'*','indicates required fields.',NULL,NULL,'',1,NULL),(6,NULL,'Result',135,NULL,'*','indicates required fields.',NULL,NULL,'',1,NULL),(7,NULL,'NormalRange',93,NULL,'*','indicates required fields.',NULL,NULL,'',1,NULL),(8,NULL,'MedicalRecordNumber',124,NULL,'*','indicates required fields.',NULL,NULL,'',1,NULL),(9,NULL,'Facility',108,NULL,'*','indicates required fields.',NULL,3,'',1,NULL),(10,NULL,'SpecimanType',158,NULL,'*','indicates required fields.',NULL,3,'',1,NULL),(11,NULL,'LaboratoryTestType',149,NULL,'*','indicates required fields.',NULL,3,'',1,NULL),(12,NULL,'DischargeDisposition',239,NULL,'*','indicates required fields.',NULL,3,'',1,NULL),(13,NULL,'DiagnosticRelatedGroup',100,NULL,'*','indicates required fields.',NULL,3,'',1,NULL),(14,NULL,'DiagnosisType',180,NULL,'*','indicates required fields.',NULL,3,'',1,NULL),(15,NULL,'DiagnosisCode',271,NULL,'*','indicates required fields.',NULL,3,'',1,NULL),(16,NULL,'Diagnosis',269,NULL,'*','indicates required fields.',NULL,NULL,'',1,NULL),(17,NULL,'ProcedureCode',176,NULL,'*','indicates required fields.',NULL,3,'',1,NULL),(18,NULL,'CptProcedureCode',283,NULL,'*','indicates required fields.',NULL,17,'',1,NULL),(19,NULL,'ClinicalTrial',206,NULL,'*','indicates required fields.',NULL,NULL,'',1,NULL),(20,NULL,'ResearchOptOut',285,NULL,'*','indicates required fields.',NULL,NULL,'',1,NULL),(21,NULL,'PhoneType',91,NULL,'*','indicates required fields.',NULL,NULL,'',1,NULL),(22,NULL,'ActiveUpiFlag',87,NULL,'*','indicates required fields.',NULL,NULL,'',1,NULL),(23,NULL,'State',66,NULL,'*','indicates required fields.',NULL,3,'',1,NULL),(24,NULL,'Religion',34,NULL,'*','indicates required fields.',NULL,3,'',1,NULL),(25,NULL,'AddressType',69,NULL,'*','indicates required fields.',NULL,3,'',1,NULL),(26,NULL,'AdvancedDirectiveExists',31,NULL,'*','indicates required fields.',NULL,3,'',1,NULL),(27,NULL,'AssociatedPerson',52,NULL,'*','indicates required fields.',NULL,NULL,'',1,NULL),(28,NULL,'Country',72,NULL,'*','indicates required fields.',NULL,3,'',1,NULL),(29,NULL,'Demographics',24,NULL,'*','indicates required fields.',NULL,NULL,'',1,NULL),(30,NULL,'EthnicOrigin',25,NULL,'*','indicates required fields.',NULL,3,'',1,NULL),(31,NULL,'Gender',28,NULL,'*','indicates required fields.',NULL,3,'',1,NULL),(32,NULL,'MaritalStatus',40,NULL,'*','indicates required fields.',NULL,3,'',1,NULL),(33,NULL,'Person',23,NULL,'*','indicates required fields.',NULL,NULL,'',1,NULL),(34,NULL,'PersonName',13,NULL,'*','indicates required fields.',NULL,NULL,'',1,NULL),(35,NULL,'Phone',60,NULL,'*','indicates required fields.',NULL,NULL,'',1,NULL),(36,NULL,'Race',37,NULL,'*','indicates required fields.',NULL,3,'',1,NULL),(37,NULL,'RelationToPerson',53,NULL,'*','indicates required fields.',NULL,3,'',1,NULL),(38,NULL,'Encounter',289,NULL,'*','indicates required fields.',NULL,NULL,'',1,NULL),(39,NULL,'EncounterDetails',182,NULL,'*','indicates required fields.',NULL,NULL,'',1,NULL),(40,NULL,'FacilityDischargeDisposition',104,NULL,'*','indicates required fields.',NULL,3,'',1,NULL),(41,NULL,'FinancialClass',188,NULL,'*','indicates required fields.',NULL,3,'',1,NULL),(42,NULL,'HipaaNotified',233,NULL,'*','indicates required fields.',NULL,3,'',1,NULL),(43,NULL,'Icd9ProcedureCode',178,NULL,'*','indicates required fields.',NULL,17,'',1,NULL),(44,NULL,'InfectionControlCode',236,NULL,'*','indicates required fields.',NULL,3,'',1,NULL),(45,NULL,'Insurance',191,NULL,'*','indicates required fields.',NULL,NULL,'',1,NULL),(46,NULL,'OptOutIndicator',280,NULL,'*','indicates required fields.',NULL,3,'',1,NULL),(47,NULL,'PatientClass',263,NULL,'*','indicates required fields.',NULL,3,'',1,NULL),(48,NULL,'PatientLocation',2,NULL,'*','indicates required fields.',NULL,NULL,'',1,NULL),(49,NULL,'PatientType',266,NULL,'*','indicates required fields.',NULL,3,'',1,NULL),(50,NULL,'Procedure',221,NULL,'*','indicates required fields.',NULL,NULL,'',1,NULL),(51,NULL,'ProcedureType',224,NULL,'*','indicates required fields.',NULL,3,'',1,NULL),(52,NULL,'Provider',7,NULL,'*','indicates required fields.',NULL,NULL,'',1,NULL),(53,NULL,'Service',216,NULL,'*','indicates required fields.',NULL,3,'',1,NULL),(54,NULL,'VipIndicator',89,NULL,'*','indicates required fields.',NULL,3,'',1,NULL),(55,NULL,'AbnormalFlag',146,NULL,'*','indicates required fields.',NULL,3,'',1,NULL),(56,NULL,'Application',113,NULL,'*','indicates required fields.',NULL,3,'',1,NULL),(57,NULL,'LaboratoryProcedure',106,NULL,'*','indicates required fields.',NULL,NULL,'',1,NULL),(58,NULL,'LaboratoryProcedureDetails',116,NULL,'*','indicates required fields.',NULL,NULL,'',1,NULL),(59,NULL,'LaboratoryProcedureType',98,NULL,'*','indicates required fields.',NULL,3,'',1,NULL),(60,NULL,'LaboratoryResult',132,NULL,'*','indicates required fields.',NULL,NULL,'',1,NULL);

/*Table structure for table `dyextn_containment_control` */

CREATE TABLE `dyextn_containment_control` (
  `IDENTIFIER` bigint(20) NOT NULL,
  PRIMARY KEY  (`IDENTIFIER`),
  KEY `FK3F9D4AD3C45A8CFA` (`IDENTIFIER`),
  CONSTRAINT `FK3F9D4AD3C45A8CFA` FOREIGN KEY (`IDENTIFIER`) REFERENCES `dyextn_abstr_contain_ctr` (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `dyextn_containment_control` */

/*Table structure for table `dyextn_control` */

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
) ENGINE=InnoDB AUTO_INCREMENT=162 DEFAULT CHARSET=latin1;

/*Data for the table `dyextn_control` */

insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (1,'sourceUnits',NULL,NULL,'sourceUnits',2,NULL,2,174,'\0'),(2,'normalizedUnits',NULL,NULL,'normalizedUnits',1,NULL,2,173,'\0'),(3,'name',NULL,NULL,'name',2,NULL,3,10,'\0'),(4,'shortName',NULL,NULL,'shortName',1,NULL,3,9,'\0'),(5,'AssociationName_1',NULL,NULL,'AssociationName_1',2,NULL,5,143,'\0'),(6,'AssociationName_2',NULL,NULL,'AssociationName_2',1,NULL,5,142,'\0'),(7,'resultString',NULL,NULL,'resultString',5,NULL,6,140,'\0'),(8,'resultLow',NULL,NULL,'resultLow',4,NULL,6,139,'\0'),(9,'resultHigh',NULL,NULL,'resultHigh',3,NULL,6,138,'\0'),(10,'units',NULL,NULL,'units',2,NULL,6,137,'\0'),(11,'AssociationName_1',NULL,NULL,'AssociationName_1',1,NULL,6,136,'\0'),(12,'range',NULL,NULL,'range',3,NULL,7,96,'\0'),(13,'rangeLow',NULL,NULL,'rangeLow',2,NULL,7,95,'\0'),(14,'rangeHigh',NULL,NULL,'rangeHigh',1,NULL,7,94,'\0'),(15,'Number',NULL,NULL,'Number',3,NULL,8,127,'\0'),(16,'Type',NULL,NULL,'Type',2,NULL,8,126,'\0'),(17,'AssociationName_1',NULL,NULL,'AssociationName_1',1,NULL,8,125,'\0'),(18,'initials',NULL,NULL,'initials',1,NULL,9,109,'\0'),(19,'standardUnitOfMeasure',NULL,NULL,'standardUnitOfMeasure',1,NULL,11,150,'\0'),(20,'description',NULL,NULL,'description',6,NULL,16,277,'\0'),(21,'sequence',NULL,NULL,'sequence',5,NULL,16,276,'\0'),(22,'AssociationName_1',NULL,NULL,'AssociationName_1',4,NULL,16,275,'\0'),(23,'AssociationName_2',NULL,NULL,'AssociationName_2',3,NULL,16,274,'\0'),(24,'AssociationName_3',NULL,NULL,'AssociationName_3',2,NULL,16,273,'\0'),(25,'AssociationName_4',NULL,NULL,'AssociationName_4',1,NULL,16,270,'\0'),(26,'trialId',NULL,NULL,'trialId',7,NULL,19,213,'\0'),(27,'trailName',NULL,NULL,'trailName',6,NULL,19,212,'\0'),(28,'startTimeStamp',NULL,NULL,'startTimeStamp',5,NULL,19,211,'\0'),(29,'endTimeStamp',NULL,NULL,'endTimeStamp',4,NULL,19,210,'\0'),(30,'status',NULL,NULL,'status',3,NULL,19,209,'\0'),(31,'protocolId',NULL,NULL,'protocolId',2,NULL,19,208,'\0'),(32,'AssociationName_1',NULL,NULL,'AssociationName_1',1,NULL,19,207,'\0'),(33,'AssociationName_1',NULL,NULL,'AssociationName_1',2,NULL,27,56,'\0'),(34,'AssociationName_2',NULL,NULL,'AssociationName_2',1,NULL,27,55,'\0'),(35,'AssociationName_5',NULL,NULL,'AssociationName_5',18,NULL,29,80,'\0'),(36,'AssociationName_4',NULL,NULL,'AssociationName_4',17,NULL,29,64,'\0'),(37,'AssociationName_3',NULL,NULL,'AssociationName_3',16,NULL,29,59,'\0'),(38,'AssociationName_2',NULL,NULL,'AssociationName_2',15,NULL,29,58,'\0'),(39,'AssociationName_1',NULL,NULL,'AssociationName_1',14,NULL,29,51,'\0'),(40,'AssociationName_11',NULL,NULL,'AssociationName_11',1,NULL,29,27,'\0'),(41,'AssociationName_10',NULL,NULL,'AssociationName_10',2,NULL,29,30,'\0'),(42,'AssociationName_9',NULL,NULL,'AssociationName_9',3,NULL,29,33,'\0'),(43,'AssociationName_8',NULL,NULL,'AssociationName_8',4,NULL,29,36,'\0'),(44,'AssociationName_7',NULL,NULL,'AssociationName_7',5,NULL,29,39,'\0'),(45,'AssociationName_6',NULL,NULL,'AssociationName_6',6,NULL,29,42,'\0'),(46,'dateOfBirth',NULL,NULL,'dateOfBirth',7,NULL,29,44,'\0'),(47,'dateOfDeath',NULL,NULL,'dateOfDeath',8,NULL,29,45,'\0'),(48,'mothersMaidenName',NULL,NULL,'mothersMaidenName',9,NULL,29,46,'\0'),(49,'placeOfBirth',NULL,NULL,'placeOfBirth',10,NULL,29,47,'\0'),(50,'socialSecurityNumber',NULL,NULL,'socialSecurityNumber',11,NULL,29,48,'\0'),(51,'effectiveStartTimeStamp',NULL,NULL,'effectiveStartTimeStamp',12,NULL,29,49,'\0'),(52,'effectiveEndTimeStamp',NULL,NULL,'effectiveEndTimeStamp',13,NULL,29,50,'\0'),(53,'personUpi',NULL,NULL,'personUpi',2,NULL,33,82,'\0'),(54,'AssociationName_1',NULL,NULL,'AssociationName_1',1,NULL,33,81,'\0'),(55,'firstName',NULL,NULL,'firstName',7,NULL,34,20,'\0'),(56,'middleName',NULL,NULL,'middleName',6,NULL,34,19,'\0'),(57,'lastName',NULL,NULL,'lastName',5,NULL,34,18,'\0'),(58,'lastNameCompressed',NULL,NULL,'lastNameCompressed',4,NULL,34,17,'\0'),(59,'degree',NULL,NULL,'degree',3,NULL,34,16,'\0'),(60,'prefix',NULL,NULL,'prefix',2,NULL,34,15,'\0'),(61,'suffix',NULL,NULL,'suffix',1,NULL,34,14,'\0'),(62,'number',NULL,NULL,'number',2,NULL,35,62,'\0'),(63,'type',NULL,NULL,'type',1,NULL,35,61,'\0'),(64,'patientAccountNumber',NULL,NULL,'patientAccountNumber',5,NULL,38,294,'\0'),(65,'AssociationName_1',NULL,NULL,'AssociationName_1',4,NULL,38,293,'\0'),(66,'AssociationName_2',NULL,NULL,'AssociationName_2',3,NULL,38,292,'\0'),(67,'AssociationName_3',NULL,NULL,'AssociationName_3',2,NULL,38,291,'\0'),(68,'AssociationName_4',NULL,NULL,'AssociationName_4',1,NULL,38,290,'\0'),(69,'AssociationName_16',NULL,NULL,'AssociationName_16',40,NULL,39,282,'\0'),(70,'AssociationName_15',NULL,NULL,'AssociationName_15',39,NULL,39,279,'\0'),(71,'AssociationName_14',NULL,NULL,'AssociationName_14',38,NULL,39,268,'\0'),(72,'AssociationName_13',NULL,NULL,'AssociationName_13',37,NULL,39,265,'\0'),(73,'AssociationName_12',NULL,NULL,'AssociationName_12',36,NULL,39,262,'\0'),(74,'AssociationName_11',NULL,NULL,'AssociationName_11',35,NULL,39,261,'\0'),(75,'AssociationName_10',NULL,NULL,'AssociationName_10',34,NULL,39,260,'\0'),(76,'AssociationName_9',NULL,NULL,'AssociationName_9',33,NULL,39,259,'\0'),(77,'AssociationName_8',NULL,NULL,'AssociationName_8',32,NULL,39,258,'\0'),(78,'AssociationName_7',NULL,NULL,'AssociationName_7',31,NULL,39,257,'\0'),(79,'AssociationName_6',NULL,NULL,'AssociationName_6',30,NULL,39,256,'\0'),(80,'AssociationName_5',NULL,NULL,'AssociationName_5',29,NULL,39,255,'\0'),(81,'effectingStartTimeStamp',NULL,NULL,'effectingStartTimeStamp',28,NULL,39,253,'\0'),(82,'effectingEndTimeStamp',NULL,NULL,'effectingEndTimeStamp',27,NULL,39,252,'\0'),(83,'ageAtEncounter',NULL,NULL,'ageAtEncounter',26,NULL,39,251,'\0'),(84,'AssociationName_20',NULL,NULL,'AssociationName_20',13,NULL,39,232,'\0'),(85,'AssociationName_21',NULL,NULL,'AssociationName_21',12,NULL,39,220,'\0'),(86,'AssociationName_22',NULL,NULL,'AssociationName_22',11,NULL,39,219,'\0'),(87,'AssociationName_23',NULL,NULL,'AssociationName_23',10,NULL,39,218,'\0'),(88,'AssociationName_24',NULL,NULL,'AssociationName_24',9,NULL,39,215,'\0'),(89,'AssociationName_25',NULL,NULL,'AssociationName_25',8,NULL,39,205,'\0'),(90,'AssociationName_26',NULL,NULL,'AssociationName_26',7,NULL,39,204,'\0'),(91,'AssociationName_27',NULL,NULL,'AssociationName_27',6,NULL,39,190,'\0'),(92,'AssociationName_28',NULL,NULL,'AssociationName_28',5,NULL,39,187,'\0'),(93,'AssociationName_29',NULL,NULL,'AssociationName_29',4,NULL,39,186,'\0'),(94,'AssociationName_30',NULL,NULL,'AssociationName_30',3,NULL,39,185,'\0'),(95,'AssociationName_31',NULL,NULL,'AssociationName_31',2,NULL,39,184,'\0'),(96,'AssociationName_32',NULL,NULL,'AssociationName_32',1,NULL,39,183,'\0'),(97,'AssociationName_19',NULL,NULL,'AssociationName_19',14,NULL,39,235,'\0'),(98,'AssociationName_18',NULL,NULL,'AssociationName_18',15,NULL,39,238,'\0'),(99,'AssociationName_17',NULL,NULL,'AssociationName_17',16,NULL,39,241,'\0'),(100,'AssociationName_4',NULL,NULL,'AssociationName_4',17,NULL,39,242,'\0'),(101,'AssociationName_3',NULL,NULL,'AssociationName_3',18,NULL,39,243,'\0'),(102,'AssociationName_2',NULL,NULL,'AssociationName_2',19,NULL,39,244,'\0'),(103,'AssociationName_1',NULL,NULL,'AssociationName_1',20,NULL,39,245,'\0'),(104,'admittingService',NULL,NULL,'admittingService',21,NULL,39,246,'\0'),(105,'dischargeService',NULL,NULL,'dischargeService',22,NULL,39,247,'\0'),(106,'teachingTeam',NULL,NULL,'teachingTeam',23,NULL,39,248,'\0'),(107,'dischargeTimeStamp',NULL,NULL,'dischargeTimeStamp',24,NULL,39,249,'\0'),(108,'registrationTimeStamp',NULL,NULL,'registrationTimeStamp',25,NULL,39,250,'\0'),(109,'sequence',NULL,NULL,'sequence',11,NULL,45,202,'\0'),(110,'planId',NULL,NULL,'planId',10,NULL,45,201,'\0'),(111,'companyId',NULL,NULL,'companyId',9,NULL,45,200,'\0'),(112,'groupNumber',NULL,NULL,'groupNumber',8,NULL,45,199,'\0'),(113,'groupName',NULL,NULL,'groupName',7,NULL,45,198,'\0'),(114,'insuredGroupEmpId',NULL,NULL,'insuredGroupEmpId',6,NULL,45,197,'\0'),(115,'insuredGroupEmpName',NULL,NULL,'insuredGroupEmpName',5,NULL,45,196,'\0'),(116,'policyName',NULL,NULL,'policyName',4,NULL,45,195,'\0'),(117,'preAdmitCert',NULL,NULL,'preAdmitCert',3,NULL,45,194,'\0'),(118,'AssociationName_1',NULL,NULL,'AssociationName_1',2,NULL,45,193,'\0'),(119,'AssociationName_2',NULL,NULL,'AssociationName_2',1,NULL,45,192,'\0'),(120,'location',NULL,NULL,'location',3,NULL,48,5,'\0'),(121,'room',NULL,NULL,'room',2,NULL,48,4,'\0'),(122,'bed',NULL,NULL,'bed',1,NULL,48,3,'\0'),(123,'date',NULL,NULL,'date',7,NULL,50,230,'\0'),(124,'sequence',NULL,NULL,'sequence',6,NULL,50,229,'\0'),(125,'AssociationName_1',NULL,NULL,'AssociationName_1',5,NULL,50,228,'\0'),(126,'AssociationName_2',NULL,NULL,'AssociationName_2',4,NULL,50,227,'\0'),(127,'AssociationName_3',NULL,NULL,'AssociationName_3',3,NULL,50,226,'\0'),(128,'AssociationName_4',NULL,NULL,'AssociationName_4',2,NULL,50,223,'\0'),(129,'AssociationName_5',NULL,NULL,'AssociationName_5',1,NULL,50,222,'\0'),(130,'facilityProviderId',NULL,NULL,'facilityProviderId',4,NULL,52,85,'\0'),(131,'AssociationName_1',NULL,NULL,'AssociationName_1',3,NULL,52,84,'\0'),(132,'AssociationName_2',NULL,NULL,'AssociationName_2',2,NULL,52,22,'\0'),(133,'AssociationName_3',NULL,NULL,'AssociationName_3',1,NULL,52,12,'\0'),(134,'accessionNumber',NULL,NULL,'accessionNumber',8,NULL,57,170,'\0'),(135,'patientAccountNumber',NULL,NULL,'patientAccountNumber',7,NULL,57,169,'\0'),(136,'procedureSynonym',NULL,NULL,'procedureSynonym',6,NULL,57,168,'\0'),(137,'AssociationName_1',NULL,NULL,'AssociationName_1',5,NULL,57,167,'\0'),(138,'AssociationName_2',NULL,NULL,'AssociationName_2',4,NULL,57,115,'\0'),(139,'AssociationName_3',NULL,NULL,'AssociationName_3',3,NULL,57,112,'\0'),(140,'AssociationName_4',NULL,NULL,'AssociationName_4',2,NULL,57,111,'\0'),(141,'AssociationName_5',NULL,NULL,'AssociationName_5',1,NULL,57,107,'\0'),(142,'AssociationName_8',NULL,NULL,'AssociationName_8',1,NULL,58,117,'\0'),(143,'effectiveStartTimeStamp',NULL,NULL,'effectiveStartTimeStamp',2,NULL,58,119,'\0'),(144,'effectiveEndTimeStamp',NULL,NULL,'effectiveEndTimeStamp',3,NULL,58,120,'\0'),(145,'ageAtProcedure',NULL,NULL,'ageAtProcedure',4,NULL,58,121,'\0'),(146,'procedureComment',NULL,NULL,'procedureComment',5,NULL,58,122,'\0'),(147,'procedureId',NULL,NULL,'procedureId',6,NULL,58,123,'\0'),(148,'AssociationName_1',NULL,NULL,'AssociationName_1',7,NULL,58,129,'\0'),(149,'AssociationName_2',NULL,NULL,'AssociationName_2',8,NULL,58,130,'\0'),(150,'AssociationName_3',NULL,NULL,'AssociationName_3',9,NULL,58,131,'\0'),(151,'AssociationName_4',NULL,NULL,'AssociationName_4',10,NULL,58,157,'\0'),(152,'AssociationName_5',NULL,NULL,'AssociationName_5',11,NULL,58,160,'\0'),(153,'AssociationName_6',NULL,NULL,'AssociationName_6',12,NULL,58,165,'\0'),(154,'AssociationName_7',NULL,NULL,'AssociationName_7',13,NULL,58,166,'\0'),(155,'resultComments',NULL,NULL,'resultComments',7,NULL,60,155,'\0'),(156,'testSynonym',NULL,NULL,'testSynonym',6,NULL,60,154,'\0'),(157,'resultTimeStamp',NULL,NULL,'resultTimeStamp',5,NULL,60,153,'\0'),(158,'AssociationName_1',NULL,NULL,'AssociationName_1',4,NULL,60,152,'\0'),(159,'AssociationName_2',NULL,NULL,'AssociationName_2',3,NULL,60,148,'\0'),(160,'AssociationName_3',NULL,NULL,'AssociationName_3',2,NULL,60,145,'\0'),(161,'AssociationName_4',NULL,NULL,'AssociationName_4',1,NULL,60,133,'\0');

/*Table structure for table `dyextn_data_element` */

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

CREATE TABLE `dyextn_data_grid` (
  `IDENTIFIER` bigint(20) NOT NULL,
  PRIMARY KEY  (`IDENTIFIER`),
  KEY `FK233EB73E40F198C2` (`IDENTIFIER`),
  CONSTRAINT `FK233EB73E40F198C2` FOREIGN KEY (`IDENTIFIER`) REFERENCES `dyextn_control` (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `dyextn_data_grid` */

/*Table structure for table `dyextn_database_properties` */

CREATE TABLE `dyextn_database_properties` (
  `IDENTIFIER` bigint(20) NOT NULL auto_increment,
  `NAME` varchar(255) default NULL,
  PRIMARY KEY  (`IDENTIFIER`)
) ENGINE=InnoDB AUTO_INCREMENT=295 DEFAULT CHARSET=latin1;

/*Data for the table `dyextn_database_properties` */

insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (1,'DE_E_322'),(2,'DE_AT_328'),(3,'DE_AT_327'),(4,'DE_AT_326'),(5,'IDENTIFIER'),(6,'DE_E_317'),(7,'DE_E_13'),(8,'DE_AT_18'),(9,'DE_AT_17'),(10,'IDENTIFIER'),(11,'DE_AS_769'),(12,'DE_E_126'),(13,'DE_AT_136'),(14,'DE_AT_135'),(15,'DE_AT_134'),(16,'DE_AT_133'),(17,'DE_AT_132'),(18,'DE_AT_131'),(19,'DE_AT_130'),(20,'IDENTIFIER'),(21,'DE_AS_764'),(22,'DE_E_144'),(23,'DE_E_167'),(24,'DE_E_282'),(25,'IDENTIFIER'),(26,'DE_AS_719'),(27,'DE_E_231'),(28,'IDENTIFIER'),(29,'DE_AS_619'),(30,'DE_E_213'),(31,'IDENTIFIER'),(32,'DE_AS_604'),(33,'DE_E_205'),(34,'IDENTIFIER'),(35,'DE_AS_594'),(36,'DE_E_184'),(37,'IDENTIFIER'),(38,'DE_AS_564'),(39,'DE_E_110'),(40,'IDENTIFIER'),(41,'DE_AS_544'),(42,'IDENTIFIER'),(43,'DE_AT_171'),(44,'DE_AT_172'),(45,'DE_AT_173'),(46,'DE_AT_174'),(47,'DE_AT_175'),(48,'DE_AT_176'),(49,'DE_AT_177'),(50,'DE_AS_519'),(51,'DE_E_157'),(52,'DE_E_297'),(53,'IDENTIFIER'),(54,'DE_AS_734'),(55,'DE_AS_509'),(56,'IDENTIFIER'),(57,'DE_AS_524'),(58,'DE_AS_529'),(59,'DE_E_161'),(60,'DE_AT_166'),(61,'DE_AT_165'),(62,'IDENTIFIER'),(63,'DE_AS_534'),(64,'DE_E_23'),(65,'DE_E_309'),(66,'IDENTIFIER'),(67,'DE_AS_744'),(68,'DE_E_245'),(69,'IDENTIFIER'),(70,'DE_AS_669'),(71,'DE_E_209'),(72,'IDENTIFIER'),(73,'DE_AS_599'),(74,'DE_AT_30'),(75,'DE_AT_29'),(76,'DE_AT_28'),(77,'DE_AT_27'),(78,'IDENTIFIER'),(79,'DE_AS_539'),(80,'DE_AS_514'),(81,'DE_AT_148'),(82,'IDENTIFIER'),(83,'DE_AS_759'),(84,'DE_AT_321'),(85,'IDENTIFIER'),(86,'DE_E_313'),(87,'IDENTIFIER'),(88,'DE_E_305'),(89,'IDENTIFIER'),(90,'DE_E_301'),(91,'IDENTIFIER'),(92,'DE_E_290'),(93,'DE_AT_296'),(94,'DE_AT_295'),(95,'DE_AT_294'),(96,'IDENTIFIER'),(97,'DE_E_286'),(98,'IDENTIFIER'),(99,'DE_E_278'),(100,'IDENTIFIER'),(101,'DE_E_274'),(102,'IDENTIFIER'),(103,'DE_E_270'),(104,'IDENTIFIER'),(105,'DE_E_137'),(106,'DE_AS_724'),(107,'DE_E_235'),(108,'DE_AT_239'),(109,'IDENTIFIER'),(110,'DE_AS_644'),(111,'DE_AS_494'),(112,'DE_E_122'),(113,'IDENTIFIER'),(114,'DE_AS_489'),(115,'DE_E_101'),(116,'DE_AS_704'),(117,'IDENTIFIER'),(118,'DE_AT_105'),(119,'DE_AT_106'),(120,'DE_AT_107'),(121,'DE_AT_108'),(122,'DE_AT_109'),(123,'DE_E_73'),(124,'DE_AS_629'),(125,'DE_AT_78'),(126,'DE_AT_77'),(127,'IDENTIFIER'),(128,'DE_AS_444'),(129,'DE_AS_449'),(130,'DE_AS_454'),(131,'DE_E_58'),(132,'DE_AS_709'),(133,'DE_E_118'),(134,'DE_E_83'),(135,'DE_AS_729'),(136,'DE_AT_90'),(137,'DE_AT_89'),(138,'DE_AT_88'),(139,'DE_AT_87'),(140,'IDENTIFIER'),(141,'DE_AS_479'),(142,'DE_AS_474'),(143,'IDENTIFIER'),(144,'DE_AS_469'),(145,'DE_E_45'),(146,'IDENTIFIER'),(147,'DE_AS_424'),(148,'DE_E_53'),(149,'DE_AT_57'),(150,'IDENTIFIER'),(151,'DE_AS_419'),(152,'DE_AT_64'),(153,'DE_AT_63'),(154,'DE_AT_62'),(155,'IDENTIFIER'),(156,'DE_AS_459'),(157,'DE_E_114'),(158,'IDENTIFIER'),(159,'DE_AS_464'),(160,'DE_E_196'),(161,'DE_AS_634'),(162,'DE_AT_200'),(163,'IDENTIFIER'),(164,'DE_AS_574'),(165,'DE_AS_639'),(166,'DE_AS_484'),(167,'DE_AT_143'),(168,'DE_AT_142'),(169,'DE_AT_141'),(170,'IDENTIFIER'),(171,'DE_E_95'),(172,'DE_AT_100'),(173,'DE_AT_99'),(174,'IDENTIFIER'),(175,'DE_E_266'),(176,'IDENTIFIER'),(177,'DE_E_91'),(178,'IDENTIFIER'),(179,'DE_E_79'),(180,'IDENTIFIER'),(181,'DE_E_1'),(182,'DE_AS_774'),(183,'DE_AS_749'),(184,'DE_AS_739'),(185,'DE_AS_714'),(186,'DE_AS_699'),(187,'DE_E_262'),(188,'IDENTIFIER'),(189,'DE_AS_689'),(190,'DE_E_249'),(191,'DE_AS_684'),(192,'DE_AS_679'),(193,'DE_AT_261'),(194,'DE_AT_260'),(195,'DE_AT_259'),(196,'DE_AT_258'),(197,'DE_AT_257'),(198,'DE_AT_256'),(199,'DE_AT_255'),(200,'DE_AT_254'),(201,'DE_AT_253'),(202,'IDENTIFIER'),(203,'DE_AS_674'),(204,'DE_AS_624'),(205,'DE_E_217'),(206,'DE_AS_614'),(207,'DE_AT_226'),(208,'DE_AT_225'),(209,'DE_AT_224'),(210,'DE_AT_223'),(211,'DE_AT_222'),(212,'DE_AT_221'),(213,'IDENTIFIER'),(214,'DE_AS_609'),(215,'DE_E_201'),(216,'IDENTIFIER'),(217,'DE_AS_589'),(218,'DE_AS_584'),(219,'DE_AS_579'),(220,'DE_E_178'),(221,'DE_AS_754'),(222,'DE_AS_694'),(223,'DE_E_192'),(224,'IDENTIFIER'),(225,'DE_AS_569'),(226,'DE_AS_559'),(227,'DE_AS_554'),(228,'DE_AT_183'),(229,'DE_AT_182'),(230,'IDENTIFIER'),(231,'DE_AS_549'),(232,'DE_E_153'),(233,'IDENTIFIER'),(234,'DE_AS_504'),(235,'DE_E_149'),(236,'IDENTIFIER'),(237,'DE_AS_499'),(238,'DE_E_69'),(239,'IDENTIFIER'),(240,'DE_AS_434'),(241,'DE_AS_344'),(242,'DE_AS_339'),(243,'DE_AS_334'),(244,'DE_AS_329'),(245,'DE_AT_12'),(246,'DE_AT_11'),(247,'DE_AT_10'),(248,'DE_AT_9'),(249,'DE_AT_8'),(250,'DE_AT_7'),(251,'DE_AT_6'),(252,'DE_AT_5'),(253,'IDENTIFIER'),(254,'DE_AS_349'),(255,'DE_AS_354'),(256,'DE_AS_359'),(257,'DE_AS_364'),(258,'DE_AS_369'),(259,'DE_AS_374'),(260,'DE_AS_379'),(261,'DE_AS_384'),(262,'DE_E_31'),(263,'IDENTIFIER'),(264,'DE_AS_389'),(265,'DE_E_35'),(266,'IDENTIFIER'),(267,'DE_AS_394'),(268,'DE_E_39'),(269,'DE_AS_439'),(270,'DE_E_65'),(271,'IDENTIFIER'),(272,'DE_AS_429'),(273,'DE_AS_409'),(274,'DE_AS_404'),(275,'DE_AT_44'),(276,'DE_AT_43'),(277,'IDENTIFIER'),(278,'DE_AS_399'),(279,'DE_E_49'),(280,'IDENTIFIER'),(281,'DE_AS_414'),(282,'DE_E_19'),(283,'IDENTIFIER'),(284,'DE_E_188'),(285,'IDENTIFIER'),(286,'DE_E_227'),(287,'IDENTIFIER'),(288,'DE_E_240'),(289,'DE_AS_664'),(290,'DE_AS_659'),(291,'DE_AS_654'),(292,'DE_AS_649'),(293,'DE_AT_244'),(294,'IDENTIFIER');

/*Table structure for table `dyextn_date_concept_value` */

CREATE TABLE `dyextn_date_concept_value` (
  `IDENTIFIER` bigint(20) NOT NULL,
  `VALUE` datetime default NULL,
  PRIMARY KEY  (`IDENTIFIER`),
  KEY `FK45F598A64641D513` (`IDENTIFIER`),
  CONSTRAINT `FK45F598A64641D513` FOREIGN KEY (`IDENTIFIER`) REFERENCES `dyextn_permissible_value` (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `dyextn_date_concept_value` */

insert into `dyextn_date_concept_value` (`IDENTIFIER`,`VALUE`) values (13,NULL),(14,NULL),(18,NULL),(19,NULL),(30,NULL),(31,NULL),(39,NULL),(58,NULL),(59,NULL),(62,NULL),(66,NULL),(67,NULL),(68,NULL),(69,NULL);

/*Table structure for table `dyextn_date_type_info` */

CREATE TABLE `dyextn_date_type_info` (
  `IDENTIFIER` bigint(20) NOT NULL,
  `FORMAT` varchar(255) default NULL,
  PRIMARY KEY  (`IDENTIFIER`),
  KEY `FKFBA549FE5294FA3` (`IDENTIFIER`),
  CONSTRAINT `FKFBA549FE5294FA3` FOREIGN KEY (`IDENTIFIER`) REFERENCES `dyextn_attribute_type_info` (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `dyextn_date_type_info` */

insert into `dyextn_date_type_info` (`IDENTIFIER`,`FORMAT`) values (23,'MM-dd-yyyy'),(24,'MM-dd-yyyy'),(28,'MM-dd-yyyy'),(29,'MM-dd-yyyy'),(62,'MM-dd-yyyy'),(63,'MM-dd-yyyy'),(79,'MM-dd-yyyy'),(109,'MM-dd-yyyy'),(110,'MM-dd-yyyy'),(117,'MM-dd-yyyy'),(125,'MM-dd-yyyy'),(126,'MM-dd-yyyy'),(128,'MM-dd-yyyy'),(129,'MM-dd-yyyy');

/*Table structure for table `dyextn_datepicker` */

CREATE TABLE `dyextn_datepicker` (
  `IDENTIFIER` bigint(20) NOT NULL,
  `DATE_VALUE_TYPE` varchar(255) default NULL,
  PRIMARY KEY  (`IDENTIFIER`),
  KEY `FKFEADD19940F198C2` (`IDENTIFIER`),
  CONSTRAINT `FKFEADD19940F198C2` FOREIGN KEY (`IDENTIFIER`) REFERENCES `dyextn_control` (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `dyextn_datepicker` */

insert into `dyextn_datepicker` (`IDENTIFIER`,`DATE_VALUE_TYPE`) values (28,NULL),(29,NULL),(46,NULL),(47,NULL),(51,NULL),(52,NULL),(81,NULL),(82,NULL),(107,NULL),(108,NULL),(123,NULL),(143,NULL),(144,NULL),(157,NULL);

/*Table structure for table `dyextn_double_concept_value` */

CREATE TABLE `dyextn_double_concept_value` (
  `IDENTIFIER` bigint(20) NOT NULL,
  `VALUE` double default NULL,
  PRIMARY KEY  (`IDENTIFIER`),
  KEY `FKB94E64494641D513` (`IDENTIFIER`),
  CONSTRAINT `FKB94E64494641D513` FOREIGN KEY (`IDENTIFIER`) REFERENCES `dyextn_permissible_value` (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `dyextn_double_concept_value` */

/*Table structure for table `dyextn_double_type_info` */

CREATE TABLE `dyextn_double_type_info` (
  `IDENTIFIER` bigint(20) NOT NULL,
  PRIMARY KEY  (`IDENTIFIER`),
  KEY `FKC83869C2BA4AE008` (`IDENTIFIER`),
  CONSTRAINT `FKC83869C2BA4AE008` FOREIGN KEY (`IDENTIFIER`) REFERENCES `dyextn_numeric_type_info` (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `dyextn_double_type_info` */

insert into `dyextn_double_type_info` (`IDENTIFIER`) values (50),(51),(64),(71),(72),(105),(116),(127),(134);

/*Table structure for table `dyextn_entity` */

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

insert into `dyextn_entity` (`IDENTIFIER`,`DATA_TABLE_STATE`,`ENTITY_GROUP_ID`,`IS_ABSTRACT`,`PARENT_ENTITY_ID`,`INHERITANCE_STRATEGY`,`DISCRIMINATOR_COLUMN_NAME`,`DISCRIMINATOR_VALUE`) values (2,2,1,'\0',NULL,3,NULL,NULL),(7,2,1,'\0',NULL,3,NULL,NULL),(8,2,1,'',NULL,3,NULL,NULL),(13,2,1,'\0',NULL,3,NULL,NULL),(23,2,1,'\0',NULL,3,NULL,NULL),(24,2,1,'\0',NULL,3,NULL,NULL),(25,2,1,'\0',8,3,NULL,NULL),(28,2,1,'\0',8,3,NULL,NULL),(31,2,1,'\0',8,3,NULL,NULL),(34,2,1,'\0',8,3,NULL,NULL),(37,2,1,'\0',8,3,NULL,NULL),(40,2,1,'\0',8,3,NULL,NULL),(52,2,1,'\0',NULL,3,NULL,NULL),(53,2,1,'\0',8,3,NULL,NULL),(60,2,1,'\0',NULL,3,NULL,NULL),(65,2,1,'\0',NULL,3,NULL,NULL),(66,2,1,'\0',8,3,NULL,NULL),(69,2,1,'\0',8,3,NULL,NULL),(72,2,1,'\0',8,3,NULL,NULL),(87,2,1,'\0',NULL,3,NULL,NULL),(89,2,1,'\0',8,3,NULL,NULL),(91,2,1,'\0',NULL,3,NULL,NULL),(93,2,1,'\0',NULL,3,NULL,NULL),(98,2,1,'\0',8,3,NULL,NULL),(100,2,1,'\0',8,3,NULL,NULL),(102,2,1,'\0',8,3,NULL,NULL),(104,2,1,'\0',8,3,NULL,NULL),(106,2,1,'\0',NULL,3,NULL,NULL),(108,2,1,'\0',8,3,NULL,NULL),(113,2,1,'\0',8,3,NULL,NULL),(116,2,1,'\0',NULL,3,NULL,NULL),(124,2,1,'\0',NULL,3,NULL,NULL),(132,2,1,'\0',NULL,3,NULL,NULL),(134,2,1,'\0',NULL,3,NULL,NULL),(135,2,1,'\0',NULL,3,NULL,NULL),(146,2,1,'\0',8,3,NULL,NULL),(149,2,1,'\0',8,3,NULL,NULL),(158,2,1,'\0',8,3,NULL,NULL),(161,2,1,'\0',NULL,3,NULL,NULL),(172,2,1,'\0',NULL,3,NULL,NULL),(176,2,1,'\0',8,3,NULL,NULL),(178,2,1,'\0',176,3,NULL,NULL),(180,2,1,'\0',8,3,NULL,NULL),(182,2,1,'\0',NULL,3,NULL,NULL),(188,2,1,'\0',8,3,NULL,NULL),(191,2,1,'\0',NULL,3,NULL,NULL),(206,2,1,'\0',NULL,3,NULL,NULL),(216,2,1,'\0',8,3,NULL,NULL),(221,2,1,'\0',NULL,3,NULL,NULL),(224,2,1,'\0',8,3,NULL,NULL),(233,2,1,'\0',8,3,NULL,NULL),(236,2,1,'\0',8,3,NULL,NULL),(239,2,1,'\0',8,3,NULL,NULL),(263,2,1,'\0',8,3,NULL,NULL),(266,2,1,'\0',8,3,NULL,NULL),(269,2,1,'\0',NULL,3,NULL,NULL),(271,2,1,'\0',8,3,NULL,NULL),(280,2,1,'\0',8,3,NULL,NULL),(283,2,1,'\0',176,3,NULL,NULL),(285,2,1,'\0',NULL,3,NULL,NULL),(287,2,1,'\0',NULL,3,NULL,NULL),(289,2,1,'\0',NULL,3,NULL,NULL);

/*Table structure for table `dyextn_entity_group` */

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

insert into `dyextn_entity_group` (`IDENTIFIER`,`LONG_NAME`,`SHORT_NAME`,`VERSION`,`IS_SYSTEM_GENERATED`) values (1,'cider','cider',NULL,'\0');

/*Table structure for table `dyextn_entity_map` */

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

CREATE TABLE `dyextn_file_type_info` (
  `IDENTIFIER` bigint(20) NOT NULL,
  `MAX_FILE_SIZE` float default NULL,
  PRIMARY KEY  (`IDENTIFIER`),
  KEY `FKA00F0EDE5294FA3` (`IDENTIFIER`),
  CONSTRAINT `FKA00F0EDE5294FA3` FOREIGN KEY (`IDENTIFIER`) REFERENCES `dyextn_attribute_type_info` (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `dyextn_file_type_info` */

/*Table structure for table `dyextn_file_upload` */

CREATE TABLE `dyextn_file_upload` (
  `IDENTIFIER` bigint(20) NOT NULL,
  `NO_OF_COLUMNS` int(11) default NULL,
  PRIMARY KEY  (`IDENTIFIER`),
  KEY `FK2FAD41E740F198C2` (`IDENTIFIER`),
  CONSTRAINT `FK2FAD41E740F198C2` FOREIGN KEY (`IDENTIFIER`) REFERENCES `dyextn_control` (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `dyextn_file_upload` */

/*Table structure for table `dyextn_float_concept_value` */

CREATE TABLE `dyextn_float_concept_value` (
  `IDENTIFIER` bigint(20) NOT NULL,
  `VALUE` float default NULL,
  PRIMARY KEY  (`IDENTIFIER`),
  KEY `FK6785309A4641D513` (`IDENTIFIER`),
  CONSTRAINT `FK6785309A4641D513` FOREIGN KEY (`IDENTIFIER`) REFERENCES `dyextn_permissible_value` (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `dyextn_float_concept_value` */

/*Table structure for table `dyextn_float_type_info` */

CREATE TABLE `dyextn_float_type_info` (
  `IDENTIFIER` bigint(20) NOT NULL,
  PRIMARY KEY  (`IDENTIFIER`),
  KEY `FK7E1C0693BA4AE008` (`IDENTIFIER`),
  CONSTRAINT `FK7E1C0693BA4AE008` FOREIGN KEY (`IDENTIFIER`) REFERENCES `dyextn_numeric_type_info` (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `dyextn_float_type_info` */

/*Table structure for table `dyextn_form_context` */

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

CREATE TABLE `dyextn_id_generator` (
  `id` bigint(20) NOT NULL,
  `next_available_id` bigint(20) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `dyextn_id_generator` */

insert into `dyextn_id_generator` (`id`,`next_available_id`) values (1,779);

/*Table structure for table `dyextn_integer_concept_value` */

CREATE TABLE `dyextn_integer_concept_value` (
  `IDENTIFIER` bigint(20) NOT NULL,
  `VALUE` int(11) default NULL,
  PRIMARY KEY  (`IDENTIFIER`),
  KEY `FKFBA33B3C4641D513` (`IDENTIFIER`),
  CONSTRAINT `FKFBA33B3C4641D513` FOREIGN KEY (`IDENTIFIER`) REFERENCES `dyextn_permissible_value` (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `dyextn_integer_concept_value` */

/*Table structure for table `dyextn_integer_type_info` */

CREATE TABLE `dyextn_integer_type_info` (
  `IDENTIFIER` bigint(20) NOT NULL,
  PRIMARY KEY  (`IDENTIFIER`),
  KEY `FK5F9CB235BA4AE008` (`IDENTIFIER`),
  CONSTRAINT `FK5F9CB235BA4AE008` FOREIGN KEY (`IDENTIFIER`) REFERENCES `dyextn_numeric_type_info` (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `dyextn_integer_type_info` */

/*Table structure for table `dyextn_list_box` */

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

CREATE TABLE `dyextn_long_concept_value` (
  `IDENTIFIER` bigint(20) NOT NULL,
  `VALUE` bigint(20) default NULL,
  PRIMARY KEY  (`IDENTIFIER`),
  KEY `FK3E1A6EF44641D513` (`IDENTIFIER`),
  CONSTRAINT `FK3E1A6EF44641D513` FOREIGN KEY (`IDENTIFIER`) REFERENCES `dyextn_permissible_value` (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `dyextn_long_concept_value` */

/*Table structure for table `dyextn_long_type_info` */

CREATE TABLE `dyextn_long_type_info` (
  `IDENTIFIER` bigint(20) NOT NULL,
  PRIMARY KEY  (`IDENTIFIER`),
  KEY `FK257281EDBA4AE008` (`IDENTIFIER`),
  CONSTRAINT `FK257281EDBA4AE008` FOREIGN KEY (`IDENTIFIER`) REFERENCES `dyextn_numeric_type_info` (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `dyextn_long_type_info` */

insert into `dyextn_long_type_info` (`IDENTIFIER`) values (4),(7),(15),(16),(17),(18),(19),(20),(21),(22),(30),(31),(34),(35),(36),(37),(42),(44),(46),(47),(48),(49),(53),(54),(55),(56),(57),(59),(60),(61),(69),(74),(75),(76),(78),(82),(83),(85),(89),(92),(93),(94),(95),(96),(106),(113),(114),(115),(118),(119),(120),(121),(130),(131),(132),(133),(136),(137),(138),(139),(140),(142);

/*Table structure for table `dyextn_numeric_type_info` */

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

insert into `dyextn_numeric_type_info` (`IDENTIFIER`,`MEASUREMENT_UNITS`,`DECIMAL_PLACES`,`NO_DIGITS`) values (4,NULL,0,NULL),(7,NULL,0,NULL),(15,NULL,0,NULL),(16,NULL,0,NULL),(17,NULL,0,NULL),(18,NULL,0,NULL),(19,NULL,0,NULL),(20,NULL,0,NULL),(21,NULL,0,NULL),(22,NULL,0,NULL),(30,NULL,0,NULL),(31,NULL,0,NULL),(34,NULL,0,NULL),(35,NULL,0,NULL),(36,NULL,0,NULL),(37,NULL,0,NULL),(42,NULL,0,NULL),(44,NULL,0,NULL),(46,NULL,0,NULL),(47,NULL,0,NULL),(48,NULL,0,NULL),(49,NULL,0,NULL),(50,NULL,15,NULL),(51,NULL,15,NULL),(53,NULL,0,NULL),(54,NULL,0,NULL),(55,NULL,0,NULL),(56,NULL,0,NULL),(57,NULL,0,NULL),(59,NULL,0,NULL),(60,NULL,0,NULL),(61,NULL,0,NULL),(64,NULL,15,NULL),(69,NULL,0,NULL),(71,NULL,15,NULL),(72,NULL,15,NULL),(74,NULL,0,NULL),(75,NULL,0,NULL),(76,NULL,0,NULL),(78,NULL,0,NULL),(82,NULL,0,NULL),(83,NULL,0,NULL),(85,NULL,0,NULL),(89,NULL,0,NULL),(92,NULL,0,NULL),(93,NULL,0,NULL),(94,NULL,0,NULL),(95,NULL,0,NULL),(96,NULL,0,NULL),(105,NULL,15,NULL),(106,NULL,0,NULL),(113,NULL,0,NULL),(114,NULL,0,NULL),(115,NULL,0,NULL),(116,NULL,15,NULL),(118,NULL,0,NULL),(119,NULL,0,NULL),(120,NULL,0,NULL),(121,NULL,0,NULL),(127,NULL,15,NULL),(130,NULL,0,NULL),(131,NULL,0,NULL),(132,NULL,0,NULL),(133,NULL,0,NULL),(134,NULL,15,NULL),(136,NULL,0,NULL),(137,NULL,0,NULL),(138,NULL,0,NULL),(139,NULL,0,NULL),(140,NULL,0,NULL),(142,NULL,0,NULL);

/*Table structure for table `dyextn_object_type_info` */

CREATE TABLE `dyextn_object_type_info` (
  `IDENTIFIER` bigint(20) NOT NULL,
  PRIMARY KEY  (`IDENTIFIER`),
  KEY `FK74819FB0E5294FA3` (`IDENTIFIER`),
  CONSTRAINT `FK74819FB0E5294FA3` FOREIGN KEY (`IDENTIFIER`) REFERENCES `dyextn_attribute_type_info` (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `dyextn_object_type_info` */

/*Table structure for table `dyextn_path` */

CREATE TABLE `dyextn_path` (
  `IDENTIFIER` bigint(20) NOT NULL auto_increment,
  `CATEGORY_ENTITY_ID` bigint(20) default NULL,
  PRIMARY KEY  (`IDENTIFIER`),
  KEY `FKC26ADC2854AC01B` (`CATEGORY_ENTITY_ID`),
  CONSTRAINT `FKC26ADC2854AC01B` FOREIGN KEY (`CATEGORY_ENTITY_ID`) REFERENCES `dyextn_category_entity` (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `dyextn_path` */

/*Table structure for table `dyextn_path_asso_rel` */

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
) ENGINE=InnoDB AUTO_INCREMENT=72 DEFAULT CHARSET=latin1;

/*Data for the table `dyextn_permissible_value` */

insert into `dyextn_permissible_value` (`IDENTIFIER`,`DESCRIPTION`,`CATEGORY_ATTRIBUTE_ID`,`ATTRIBUTE_TYPE_INFO_ID`) values (1,NULL,NULL,1),(2,NULL,NULL,2),(3,NULL,NULL,3),(4,NULL,NULL,5),(5,NULL,NULL,6),(6,NULL,NULL,8),(7,NULL,NULL,9),(8,NULL,NULL,10),(9,NULL,NULL,11),(10,NULL,NULL,12),(11,NULL,NULL,13),(12,NULL,NULL,14),(13,NULL,NULL,23),(14,NULL,NULL,24),(15,NULL,NULL,25),(16,NULL,NULL,26),(17,NULL,NULL,27),(18,NULL,NULL,28),(19,NULL,NULL,29),(20,NULL,NULL,32),(21,NULL,NULL,33),(22,NULL,NULL,38),(23,NULL,NULL,39),(24,NULL,NULL,40),(25,NULL,NULL,41),(26,NULL,NULL,43),(27,NULL,NULL,45),(28,NULL,NULL,52),(29,NULL,NULL,58),(30,NULL,NULL,62),(31,NULL,NULL,63),(32,NULL,NULL,65),(33,NULL,NULL,66),(34,NULL,NULL,67),(35,NULL,NULL,68),(36,NULL,NULL,70),(37,NULL,NULL,73),(38,NULL,NULL,77),(39,NULL,NULL,79),(40,NULL,NULL,80),(41,NULL,NULL,81),(42,NULL,NULL,84),(43,NULL,NULL,86),(44,NULL,NULL,87),(45,NULL,NULL,88),(46,NULL,NULL,90),(47,NULL,NULL,91),(48,NULL,NULL,97),(49,NULL,NULL,98),(50,NULL,NULL,99),(51,NULL,NULL,100),(52,NULL,NULL,101),(53,NULL,NULL,102),(54,NULL,NULL,103),(55,NULL,NULL,104),(56,NULL,NULL,107),(57,NULL,NULL,108),(58,NULL,NULL,109),(59,NULL,NULL,110),(60,NULL,NULL,111),(61,NULL,NULL,112),(62,NULL,NULL,117),(63,NULL,NULL,122),(64,NULL,NULL,123),(65,NULL,NULL,124),(66,NULL,NULL,125),(67,NULL,NULL,126),(68,NULL,NULL,128),(69,NULL,NULL,129),(70,NULL,NULL,135),(71,NULL,NULL,141);

/*Table structure for table `dyextn_primitive_attribute` */

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

insert into `dyextn_primitive_attribute` (`IDENTIFIER`,`IS_IDENTIFIED`,`IS_PRIMARY_KEY`,`IS_NULLABLE`) values (3,'\0','\0',''),(4,'\0','\0',''),(5,'\0','\0',''),(6,NULL,'','\0'),(9,'\0','\0',''),(10,'\0','\0',''),(11,NULL,'','\0'),(14,'\0','\0',''),(15,'\0','\0',''),(16,'\0','\0',''),(17,'\0','\0',''),(18,'\0','\0',''),(19,'\0','\0',''),(20,'\0','\0',''),(21,NULL,'','\0'),(26,NULL,'','\0'),(29,NULL,'','\0'),(32,NULL,'','\0'),(35,NULL,'','\0'),(38,NULL,'','\0'),(41,NULL,'','\0'),(43,NULL,'','\0'),(44,'\0','\0',''),(45,'\0','\0',''),(46,'\0','\0',''),(47,'\0','\0',''),(48,'\0','\0',''),(49,'\0','\0',''),(50,'\0','\0',''),(54,NULL,'','\0'),(57,NULL,'','\0'),(61,'\0','\0',''),(62,'\0','\0',''),(63,NULL,'','\0'),(67,NULL,'','\0'),(70,NULL,'','\0'),(73,NULL,'','\0'),(75,'\0','\0',''),(76,'\0','\0',''),(77,'\0','\0',''),(78,'\0','\0',''),(79,NULL,'','\0'),(82,'\0','\0',''),(83,NULL,'','\0'),(85,'\0','\0',''),(86,NULL,'','\0'),(88,NULL,'','\0'),(90,NULL,'','\0'),(92,NULL,'','\0'),(94,NULL,'\0',''),(95,NULL,'\0',''),(96,'\0','\0',''),(97,NULL,'','\0'),(99,NULL,'','\0'),(101,NULL,'','\0'),(103,NULL,'','\0'),(105,NULL,'','\0'),(109,'\0','\0',''),(110,NULL,'','\0'),(114,NULL,'','\0'),(118,NULL,'','\0'),(119,'\0','\0',''),(120,'\0','\0',''),(121,NULL,'\0',''),(122,'\0','\0',''),(123,'\0','\0',''),(126,'\0','\0',''),(127,'\0','\0',''),(128,NULL,'','\0'),(137,'\0','\0',''),(138,NULL,'\0',''),(139,NULL,'\0',''),(140,'\0','\0',''),(141,NULL,'','\0'),(144,NULL,'','\0'),(147,NULL,'','\0'),(150,'\0','\0',''),(151,NULL,'','\0'),(153,'\0','\0',''),(154,'\0','\0',''),(155,'\0','\0',''),(156,NULL,'','\0'),(159,NULL,'','\0'),(163,'\0','\0',''),(164,NULL,'','\0'),(168,'\0','\0',''),(169,'\0','\0',''),(170,'\0','\0',''),(171,NULL,'','\0'),(173,'\0','\0',''),(174,'\0','\0',''),(175,NULL,'','\0'),(177,NULL,'','\0'),(179,NULL,'','\0'),(181,NULL,'','\0'),(189,NULL,'','\0'),(194,'\0','\0',''),(195,'\0','\0',''),(196,'\0','\0',''),(197,'\0','\0',''),(198,'\0','\0',''),(199,'\0','\0',''),(200,'\0','\0',''),(201,'\0','\0',''),(202,NULL,'\0',''),(203,NULL,'','\0'),(208,'\0','\0',''),(209,'\0','\0',''),(210,'\0','\0',''),(211,'\0','\0',''),(212,'\0','\0',''),(213,'\0','\0',''),(214,NULL,'','\0'),(217,NULL,'','\0'),(225,NULL,'','\0'),(229,NULL,'\0',''),(230,'\0','\0',''),(231,NULL,'','\0'),(234,NULL,'','\0'),(237,NULL,'','\0'),(240,NULL,'','\0'),(246,'\0','\0',''),(247,'\0','\0',''),(248,'\0','\0',''),(249,'\0','\0',''),(250,'\0','\0',''),(251,NULL,'\0',''),(252,'\0','\0',''),(253,'\0','\0',''),(254,NULL,'','\0'),(264,NULL,'','\0'),(267,NULL,'','\0'),(272,NULL,'','\0'),(276,NULL,'\0',''),(277,'\0','\0',''),(278,NULL,'','\0'),(281,NULL,'','\0'),(284,NULL,'','\0'),(286,NULL,'','\0'),(288,NULL,'','\0'),(294,'\0','\0',''),(295,NULL,'','\0');

/*Table structure for table `dyextn_radiobutton` */

CREATE TABLE `dyextn_radiobutton` (
  `IDENTIFIER` bigint(20) NOT NULL,
  PRIMARY KEY  (`IDENTIFIER`),
  KEY `FK16F5BA9040F198C2` (`IDENTIFIER`),
  CONSTRAINT `FK16F5BA9040F198C2` FOREIGN KEY (`IDENTIFIER`) REFERENCES `dyextn_control` (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `dyextn_radiobutton` */

/*Table structure for table `dyextn_role` */

CREATE TABLE `dyextn_role` (
  `IDENTIFIER` bigint(20) NOT NULL auto_increment,
  `ASSOCIATION_TYPE` varchar(255) default NULL,
  `MAX_CARDINALITY` int(11) default NULL,
  `MIN_CARDINALITY` int(11) default NULL,
  `NAME` varchar(255) default NULL,
  PRIMARY KEY  (`IDENTIFIER`)
) ENGINE=InnoDB AUTO_INCREMENT=181 DEFAULT CHARSET=latin1;

/*Data for the table `dyextn_role` */

insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (1,'ASSOCIATION',100,0,NULL),(2,'CONTAINTMENT',1,1,'providerType'),(3,'ASSOCIATION',100,0,NULL),(4,'ASSOCIATION',1,0,'personName'),(5,'ASSOCIATION',100,0,NULL),(6,'CONTAINTMENT',100,0,'ethinicOriginCollection'),(7,'ASSOCIATION',100,0,NULL),(8,'CONTAINTMENT',1,0,'gender'),(9,'ASSOCIATION',100,0,NULL),(10,'CONTAINTMENT',1,0,'advancedDirectiveExists'),(11,'ASSOCIATION',100,0,NULL),(12,'CONTAINTMENT',1,0,'religion'),(13,'ASSOCIATION',100,0,NULL),(14,'CONTAINTMENT',100,0,'raceCollection'),(15,'ASSOCIATION',100,0,NULL),(16,'CONTAINTMENT',1,0,'maritalStatus'),(17,'ASSOCIATION',1,1,NULL),(18,'CONTAINTMENT',1,1,'personName'),(19,'ASSOCIATION',100,0,NULL),(20,'ASSOCIATION',1,1,'relationToPerson'),(21,'ASSOCIATION',100,0,NULL),(22,'ASSOCIATION',1,1,'personUpi'),(23,'ASSOCIATION',100,0,NULL),(24,'CONTAINTMENT',100,0,'associatedPersonCollection'),(25,'ASSOCIATION',100,0,NULL),(26,'ASSOCIATION',1,0,'nextOfKin'),(27,'ASSOCIATION',100,0,NULL),(28,'CONTAINTMENT',100,0,'phoneCollection'),(29,'ASSOCIATION',100,0,NULL),(30,'CONTAINTMENT',1,0,'state'),(31,'ASSOCIATION',100,0,NULL),(32,'CONTAINTMENT',1,0,'addressType'),(33,'ASSOCIATION',100,0,NULL),(34,'CONTAINTMENT',1,0,'country'),(35,'ASSOCIATION',1,1,NULL),(36,'CONTAINTMENT',100,0,'addressCollection'),(37,'ASSOCIATION',1,1,NULL),(38,'CONTAINTMENT',100,1,'demographicsCollection'),(39,'ASSOCIATION',100,0,NULL),(40,'ASSOCIATION',1,0,'personUpi'),(41,'ASSOCIATION',100,0,NULL),(42,'ASSOCIATION',1,1,'procedure'),(43,'ASSOCIATION',100,0,NULL),(44,'ASSOCIATION',1,1,'facility'),(45,'ASSOCIATION',100,0,NULL),(46,'ASSOCIATION',1,1,'personUpi'),(47,'ASSOCIATION',100,0,NULL),(48,'ASSOCIATION',1,1,'application'),(49,'ASSOCIATION',100,0,NULL),(50,'ASSOCIATION',1,1,'procedureStatus'),(51,'ASSOCIATION',100,0,NULL),(52,'ASSOCIATION',1,1,'facility'),(53,'ASSOCIATION',100,0,NULL),(54,'CONTAINTMENT',1,0,'medicalRecordNumber'),(55,'ASSOCIATION',100,0,NULL),(56,'ASSOCIATION',100,0,'procedureId'),(57,'ASSOCIATION',100,0,NULL),(58,'ASSOCIATION',100,0,'procedureStatus'),(59,'ASSOCIATION',100,0,NULL),(60,'ASSOCIATION',1,1,'resultStatus'),(61,'ASSOCIATION',100,0,NULL),(62,'ASSOCIATION',1,0,'normalRange'),(63,'ASSOCIATION',100,0,NULL),(64,'ASSOCIATION',1,1,'normalizedResult'),(65,'ASSOCIATION',100,0,NULL),(66,'ASSOCIATION',1,1,'sourceResult'),(67,'ASSOCIATION',100,0,NULL),(68,'ASSOCIATION',1,0,'resultValue'),(69,'ASSOCIATION',100,0,NULL),(70,'ASSOCIATION',100,0,'abnormalFlagCollection'),(71,'ASSOCIATION',100,0,NULL),(72,'ASSOCIATION',1,1,'test'),(73,'ASSOCIATION',100,0,NULL),(74,'ASSOCIATION',100,0,'laboratoryResultCollection'),(75,'ASSOCIATION',100,0,NULL),(76,'ASSOCIATION',1,1,'specimanType'),(77,'ASSOCIATION',100,0,NULL),(78,'ASSOCIATION',1,1,'facility'),(79,'ASSOCIATION',100,0,NULL),(80,'CONTAINTMENT',1,1,'patientAccountNumber'),(81,'ASSOCIATION',1,1,NULL),(82,'CONTAINTMENT',1,1,'performingFacility'),(83,'ASSOCIATION',100,0,NULL),(84,'ASSOCIATION',100,0,'laboratoryProcedureDetailsColletion'),(85,'ASSOCIATION',1,1,NULL),(86,'CONTAINTMENT',1,0,'patientLocation'),(87,'ASSOCIATION',100,0,NULL),(88,'CONTAINTMENT',100,0,'providerCollection'),(89,'ASSOCIATION',100,0,NULL),(90,'ASSOCIATION',1,0,'vipIndicator'),(91,'ASSOCIATION',100,0,NULL),(92,'ASSOCIATION',1,0,'diagnosticRelatedGroup'),(93,'ASSOCIATION',100,0,NULL),(94,'ASSOCIATION',1,0,'facilityDischargeDisposition'),(95,'ASSOCIATION',100,0,NULL),(96,'ASSOCIATION',1,0,'financialClass'),(97,'ASSOCIATION',100,0,NULL),(98,'CONTAINTMENT',1,1,'insuredRelationShipToPatient'),(99,'ASSOCIATION',100,0,NULL),(100,'CONTAINTMENT',1,0,'nameOfInsured'),(101,'ASSOCIATION',100,0,NULL),(102,'CONTAINTMENT',100,1,'insuranceCollection'),(103,'ASSOCIATION',1,1,NULL),(104,'CONTAINTMENT',1,1,'facility'),(105,'ASSOCIATION',100,0,NULL),(106,'ASSOCIATION',1,0,'principalInvestigator'),(107,'ASSOCIATION',100,0,NULL),(108,'CONTAINTMENT',100,0,'clinicalTrailCollection'),(109,'ASSOCIATION',100,0,NULL),(110,'ASSOCIATION',1,0,'dischargeService'),(111,'ASSOCIATION',100,0,NULL),(112,'ASSOCIATION',1,0,'admittingService'),(113,'ASSOCIATION',1,1,NULL),(114,'ASSOCIATION',1,1,'patientAccountNumber'),(115,'ASSOCIATION',1,1,NULL),(116,'CONTAINTMENT',1,1,'provider'),(117,'ASSOCIATION',100,0,NULL),(118,'ASSOCIATION',100,0,'code'),(119,'ASSOCIATION',100,0,NULL),(120,'ASSOCIATION',100,0,'type'),(121,'ASSOCIATION',1,1,NULL),(122,'CONTAINTMENT',1,1,'type'),(123,'ASSOCIATION',1,1,NULL),(124,'CONTAINTMENT',1,1,'code'),(125,'ASSOCIATION',100,0,NULL),(126,'CONTAINTMENT',100,0,'procedureCollection'),(127,'ASSOCIATION',100,0,NULL),(128,'ASSOCIATION',1,0,'hippaNotified'),(129,'ASSOCIATION',100,0,NULL),(130,'ASSOCIATION',100,0,'infectionControlCodeCollection'),(131,'ASSOCIATION',100,0,NULL),(132,'ASSOCIATION',1,0,'dischargeDisposition'),(133,'ASSOCIATION',100,0,NULL),(134,'CONTAINTMENT',1,1,'optOutIndicator'),(135,'ASSOCIATION',100,0,NULL),(136,'CONTAINTMENT',1,1,'hipaaNotified'),(137,'ASSOCIATION',100,0,NULL),(138,'CONTAINTMENT',1,1,'vipIndicator'),(139,'ASSOCIATION',100,0,NULL),(140,'CONTAINTMENT',1,1,'dischargeService'),(141,'ASSOCIATION',100,0,NULL),(142,'CONTAINTMENT',100,0,'infectionControlCodes'),(143,'ASSOCIATION',100,0,NULL),(144,'CONTAINTMENT',1,1,'facilityDischargeDisposition'),(145,'ASSOCIATION',100,0,NULL),(146,'CONTAINTMENT',1,1,'financialClass'),(147,'ASSOCIATION',100,0,NULL),(148,'CONTAINTMENT',1,0,'diagnosticRelatedGroup'),(149,'ASSOCIATION',100,0,NULL),(150,'CONTAINTMENT',1,1,'dischargeDisposition'),(151,'ASSOCIATION',100,0,NULL),(152,'CONTAINTMENT',1,1,'patientType'),(153,'ASSOCIATION',100,0,NULL),(154,'CONTAINTMENT',1,1,'admittingService'),(155,'ASSOCIATION',1,1,NULL),(156,'CONTAINTMENT',1,1,'patientClass'),(157,'ASSOCIATION',100,0,NULL),(158,'ASSOCIATION',1,0,'patientClass'),(159,'ASSOCIATION',100,0,NULL),(160,'ASSOCIATION',1,0,'patientType'),(161,'ASSOCIATION',100,0,NULL),(162,'ASSOCIATION',100,0,'type'),(163,'ASSOCIATION',100,0,NULL),(164,'ASSOCIATION',100,0,'code'),(165,'ASSOCIATION',100,0,NULL),(166,'CONTAINTMENT',1,1,'type'),(167,'ASSOCIATION',100,0,NULL),(168,'CONTAINTMENT',1,1,'code'),(169,'ASSOCIATION',100,0,NULL),(170,'CONTAINTMENT',100,0,'diagnosesCollection'),(171,'ASSOCIATION',100,0,NULL),(172,'ASSOCIATION',1,0,'optOutIndicator'),(173,'ASSOCIATION',100,0,NULL),(174,'ASSOCIATION',100,1,'encounterDetailsCollection'),(175,'ASSOCIATION',100,0,NULL),(176,'ASSOCIATION',1,1,'personUpi'),(177,'ASSOCIATION',100,0,NULL),(178,'ASSOCIATION',1,1,'medicalRecordNumber'),(179,'ASSOCIATION',100,0,NULL),(180,'ASSOCIATION',1,1,'facility');

/*Table structure for table `dyextn_rule` */

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
) ENGINE=InnoDB AUTO_INCREMENT=81 DEFAULT CHARSET=latin1;

/*Data for the table `dyextn_rule` */

insert into `dyextn_rule` (`IDENTIFIER`,`NAME`,`IS_IMPLICIT`,`CATEGORY_ATTR_ID`,`ATTRIBUTE_ID`) values (1,'textLength','',NULL,3),(2,'textLength','',NULL,4),(3,'textLength','',NULL,5),(4,'textLength','',NULL,9),(5,'textLength','',NULL,10),(6,'textLength','',NULL,14),(7,'textLength','',NULL,15),(8,'textLength','',NULL,16),(9,'textLength','',NULL,17),(10,'textLength','',NULL,18),(11,'textLength','',NULL,19),(12,'textLength','',NULL,20),(13,'date','',NULL,44),(14,'date','',NULL,45),(15,'textLength','',NULL,46),(16,'textLength','',NULL,47),(17,'textLength','',NULL,48),(18,'date','',NULL,49),(19,'date','',NULL,50),(20,'textLength','',NULL,61),(21,'textLength','',NULL,62),(22,'textLength','',NULL,75),(23,'textLength','',NULL,76),(24,'textLength','',NULL,77),(25,'textLength','',NULL,78),(26,'textLength','',NULL,82),(27,'textLength','',NULL,85),(28,'number','',NULL,94),(29,'number','',NULL,95),(30,'textLength','',NULL,96),(31,'textLength','',NULL,109),(32,'date','',NULL,119),(33,'date','',NULL,120),(34,'number','',NULL,121),(35,'textLength','',NULL,122),(36,'textLength','',NULL,123),(37,'textLength','',NULL,126),(38,'textLength','',NULL,127),(39,'textLength','',NULL,137),(40,'number','',NULL,138),(41,'number','',NULL,139),(42,'textLength','',NULL,140),(43,'textLength','',NULL,150),(44,'date','',NULL,153),(45,'textLength','',NULL,154),(46,'textLength','',NULL,155),(47,'textLength','',NULL,163),(48,'textLength','',NULL,168),(49,'textLength','',NULL,169),(50,'textLength','',NULL,170),(51,'textLength','',NULL,173),(52,'textLength','',NULL,174),(53,'textLength','',NULL,194),(54,'textLength','',NULL,195),(55,'textLength','',NULL,196),(56,'textLength','',NULL,197),(57,'textLength','',NULL,198),(58,'textLength','',NULL,199),(59,'textLength','',NULL,200),(60,'textLength','',NULL,201),(61,'number','',NULL,202),(62,'textLength','',NULL,208),(63,'textLength','',NULL,209),(64,'date','',NULL,210),(65,'date','',NULL,211),(66,'textLength','',NULL,212),(67,'textLength','',NULL,213),(68,'number','',NULL,229),(69,'date','',NULL,230),(70,'textLength','',NULL,246),(71,'textLength','',NULL,247),(72,'textLength','',NULL,248),(73,'date','',NULL,249),(74,'date','',NULL,250),(75,'number','',NULL,251),(76,'date','',NULL,252),(77,'date','',NULL,253),(78,'number','',NULL,276),(79,'textLength','',NULL,277),(80,'textLength','',NULL,294);

/*Table structure for table `dyextn_rule_parameter` */

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

CREATE TABLE `dyextn_select_control` (
  `IDENTIFIER` bigint(20) NOT NULL,
  `SEPARATOR_STRING` varchar(255) default NULL,
  PRIMARY KEY  (`IDENTIFIER`),
  KEY `FKDFEBB65740F198C2` (`IDENTIFIER`),
  CONSTRAINT `FKDFEBB65740F198C2` FOREIGN KEY (`IDENTIFIER`) REFERENCES `dyextn_control` (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `dyextn_select_control` */

insert into `dyextn_select_control` (`IDENTIFIER`,`SEPARATOR_STRING`) values (5,NULL),(6,NULL),(11,NULL),(17,NULL),(22,NULL),(23,NULL),(24,NULL),(25,NULL),(32,NULL),(33,NULL),(34,NULL),(35,NULL),(36,NULL),(37,NULL),(38,NULL),(39,NULL),(40,NULL),(41,NULL),(42,NULL),(43,NULL),(44,NULL),(45,NULL),(54,NULL),(65,NULL),(66,NULL),(67,NULL),(68,NULL),(69,NULL),(70,NULL),(71,NULL),(72,NULL),(73,NULL),(74,NULL),(75,NULL),(76,NULL),(77,NULL),(78,NULL),(79,NULL),(80,NULL),(84,NULL),(85,NULL),(86,NULL),(87,NULL),(88,NULL),(89,NULL),(90,NULL),(91,NULL),(92,NULL),(93,NULL),(94,NULL),(95,NULL),(96,NULL),(97,NULL),(98,NULL),(99,NULL),(100,NULL),(101,NULL),(102,NULL),(103,NULL),(118,NULL),(119,NULL),(125,NULL),(126,NULL),(127,NULL),(128,NULL),(129,NULL),(131,NULL),(132,NULL),(133,NULL),(137,NULL),(138,NULL),(139,NULL),(140,NULL),(141,NULL),(142,NULL),(148,NULL),(149,NULL),(150,NULL),(151,NULL),(152,NULL),(153,NULL),(154,NULL),(158,NULL),(159,NULL),(160,NULL),(161,NULL);

/*Table structure for table `dyextn_semantic_property` */

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

CREATE TABLE `dyextn_short_concept_value` (
  `IDENTIFIER` bigint(20) NOT NULL,
  `VALUE` smallint(6) default NULL,
  PRIMARY KEY  (`IDENTIFIER`),
  KEY `FKC1945ABA4641D513` (`IDENTIFIER`),
  CONSTRAINT `FKC1945ABA4641D513` FOREIGN KEY (`IDENTIFIER`) REFERENCES `dyextn_permissible_value` (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `dyextn_short_concept_value` */

/*Table structure for table `dyextn_short_type_info` */

CREATE TABLE `dyextn_short_type_info` (
  `IDENTIFIER` bigint(20) NOT NULL,
  PRIMARY KEY  (`IDENTIFIER`),
  KEY `FK99540B3BA4AE008` (`IDENTIFIER`),
  CONSTRAINT `FK99540B3BA4AE008` FOREIGN KEY (`IDENTIFIER`) REFERENCES `dyextn_numeric_type_info` (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `dyextn_short_type_info` */

/*Table structure for table `dyextn_sql_audit` */

CREATE TABLE `dyextn_sql_audit` (
  `IDENTIFIER` bigint(20) NOT NULL auto_increment,
  `AUDIT_DATE` date default NULL,
  `QUERY_EXECUTED` text,
  `USER_ID` bigint(20) default NULL,
  PRIMARY KEY  (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `dyextn_sql_audit` */

/*Table structure for table `dyextn_string_concept_value` */

CREATE TABLE `dyextn_string_concept_value` (
  `IDENTIFIER` bigint(20) NOT NULL,
  `VALUE` varchar(255) default NULL,
  PRIMARY KEY  (`IDENTIFIER`),
  KEY `FKADE7D8894641D513` (`IDENTIFIER`),
  CONSTRAINT `FKADE7D8894641D513` FOREIGN KEY (`IDENTIFIER`) REFERENCES `dyextn_permissible_value` (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `dyextn_string_concept_value` */

insert into `dyextn_string_concept_value` (`IDENTIFIER`,`VALUE`) values (1,''),(2,''),(3,''),(4,''),(5,''),(6,''),(7,''),(8,''),(9,''),(10,''),(11,''),(12,''),(15,''),(16,''),(17,''),(20,''),(21,''),(22,''),(23,''),(24,''),(25,''),(26,''),(27,''),(28,''),(29,''),(32,''),(33,''),(34,''),(35,''),(36,''),(37,''),(38,''),(40,''),(41,''),(42,''),(43,''),(44,''),(45,''),(46,''),(47,''),(48,''),(49,''),(50,''),(51,''),(52,''),(53,''),(54,''),(55,''),(56,''),(57,''),(60,''),(61,''),(63,''),(64,''),(65,''),(70,''),(71,'');

/*Table structure for table `dyextn_string_type_info` */

CREATE TABLE `dyextn_string_type_info` (
  `IDENTIFIER` bigint(20) NOT NULL,
  `MAX_SIZE` int(11) default NULL,
  PRIMARY KEY  (`IDENTIFIER`),
  KEY `FKDA35FE02E5294FA3` (`IDENTIFIER`),
  CONSTRAINT `FKDA35FE02E5294FA3` FOREIGN KEY (`IDENTIFIER`) REFERENCES `dyextn_attribute_type_info` (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `dyextn_string_type_info` */

insert into `dyextn_string_type_info` (`IDENTIFIER`,`MAX_SIZE`) values (1,255),(2,255),(3,255),(5,255),(6,255),(8,255),(9,255),(10,255),(11,255),(12,255),(13,255),(14,255),(25,255),(26,255),(27,255),(32,255),(33,255),(38,255),(39,255),(40,255),(41,255),(43,255),(45,255),(52,255),(58,255),(65,255),(66,255),(67,255),(68,255),(70,255),(73,255),(77,255),(80,255),(81,255),(84,255),(86,255),(87,255),(88,255),(90,255),(91,255),(97,255),(98,255),(99,255),(100,255),(101,255),(102,255),(103,255),(104,255),(107,255),(108,255),(111,255),(112,255),(122,255),(123,255),(124,255),(135,255),(141,255);

/*Table structure for table `dyextn_table_properties` */

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

insert into `dyextn_table_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`ABSTRACT_ENTITY_ID`) values (1,'CONSRT_323',2),(6,'CONSRT_318',7),(7,'CONSRT_14',8),(12,'CONSRT_127',13),(22,'CONSRT_145',23),(23,'CONSRT_168',24),(24,'CONSRT_283',25),(27,'CONSRT_232',28),(30,'CONSRT_214',31),(33,'CONSRT_206',34),(36,'CONSRT_185',37),(39,'CONSRT_111',40),(51,'CONSRT_158',52),(52,'CONSRT_298',53),(59,'CONSRT_162',60),(64,'CONSRT_24',65),(65,'CONSRT_310',66),(68,'CONSRT_246',69),(71,'CONSRT_210',72),(86,'CONSRT_314',87),(88,'CONSRT_306',89),(90,'CONSRT_302',91),(92,'CONSRT_291',93),(97,'CONSRT_287',98),(99,'CONSRT_279',100),(101,'CONSRT_275',102),(103,'CONSRT_271',104),(105,'CONSRT_138',106),(107,'CONSRT_236',108),(112,'CONSRT_123',113),(115,'CONSRT_102',116),(123,'CONSRT_74',124),(131,'CONSRT_59',132),(133,'CONSRT_119',134),(134,'CONSRT_84',135),(145,'CONSRT_46',146),(148,'CONSRT_54',149),(157,'CONSRT_115',158),(160,'CONSRT_197',161),(171,'CONSRT_96',172),(175,'CONSRT_267',176),(177,'CONSRT_92',178),(179,'CONSRT_80',180),(181,'CONSRT_2',182),(187,'CONSRT_263',188),(190,'CONSRT_250',191),(205,'CONSRT_218',206),(215,'CONSRT_202',216),(220,'CONSRT_179',221),(223,'CONSRT_193',224),(232,'CONSRT_154',233),(235,'CONSRT_150',236),(238,'CONSRT_70',239),(262,'CONSRT_32',263),(265,'CONSRT_36',266),(268,'CONSRT_40',269),(270,'CONSRT_66',271),(279,'CONSRT_50',280),(282,'CONSRT_20',283),(284,'CONSRT_189',285),(286,'CONSRT_228',287),(288,'CONSRT_241',289);

/*Table structure for table `dyextn_tagged_value` */

CREATE TABLE `dyextn_tagged_value` (
  `IDENTIFIER` bigint(20) NOT NULL auto_increment,
  `T_KEY` varchar(255) default NULL,
  `T_VALUE` varchar(255) default NULL,
  `ABSTRACT_METADATA_ID` bigint(20) default NULL,
  PRIMARY KEY  (`IDENTIFIER`),
  KEY `FKF79D055B9AEB0CA3` (`ABSTRACT_METADATA_ID`),
  CONSTRAINT `FKF79D055B9AEB0CA3` FOREIGN KEY (`ABSTRACT_METADATA_ID`) REFERENCES `dyextn_abstract_metadata` (`IDENTIFIER`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;

/*Data for the table `dyextn_tagged_value` */

insert into `dyextn_tagged_value` (`IDENTIFIER`,`T_KEY`,`T_VALUE`,`ABSTRACT_METADATA_ID`) values (1,'MetadataEntityGroup','MetadataEntityGroup',1),(2,'caB2BEntityGroup','caB2BEntityGroup',1),(3,'PackageName','edu.wustl.cider.domain',1),(4,'PRIMARY_KEY','personUpi',23),(5,'PRIMARY_KEY','accessionNumber',106),(6,'PRIMARY_KEY','patientAccountNumber',289);

/*Table structure for table `dyextn_textarea` */

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

insert into `dyextn_textfield` (`IDENTIFIER`,`NO_OF_COLUMNS`,`IS_PASSWORD`,`IS_URL`) values (1,20,'\0','\0'),(2,20,'\0','\0'),(3,20,'\0','\0'),(4,20,'\0','\0'),(7,20,'\0','\0'),(8,10,NULL,NULL),(9,10,NULL,NULL),(10,20,'\0','\0'),(12,20,'\0','\0'),(13,10,NULL,NULL),(14,10,NULL,NULL),(15,20,'\0','\0'),(16,20,'\0','\0'),(18,20,'\0','\0'),(19,20,'\0','\0'),(20,20,'\0','\0'),(21,10,NULL,NULL),(26,20,'\0','\0'),(27,20,'\0','\0'),(30,20,'\0','\0'),(31,20,'\0','\0'),(48,20,'\0','\0'),(49,20,'\0','\0'),(50,20,'\0','\0'),(53,20,'\0','\0'),(55,20,'\0','\0'),(56,20,'\0','\0'),(57,20,'\0','\0'),(58,20,'\0','\0'),(59,20,'\0','\0'),(60,20,'\0','\0'),(61,20,'\0','\0'),(62,20,'\0','\0'),(63,20,'\0','\0'),(64,20,'\0','\0'),(83,10,NULL,NULL),(104,20,'\0','\0'),(105,20,'\0','\0'),(106,20,'\0','\0'),(109,10,NULL,NULL),(110,20,'\0','\0'),(111,20,'\0','\0'),(112,20,'\0','\0'),(113,20,'\0','\0'),(114,20,'\0','\0'),(115,20,'\0','\0'),(116,20,'\0','\0'),(117,20,'\0','\0'),(120,20,'\0','\0'),(121,20,'\0','\0'),(122,20,'\0','\0'),(124,10,NULL,NULL),(130,20,'\0','\0'),(134,20,'\0','\0'),(135,20,'\0','\0'),(136,20,'\0','\0'),(145,10,NULL,NULL),(146,20,'\0','\0'),(147,20,'\0','\0'),(155,20,'\0','\0'),(156,20,'\0','\0');

/*Table structure for table `dyextn_userdef_de_value_rel` */

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

CREATE TABLE `dyextn_userdefined_de` (
  `IDENTIFIER` bigint(20) NOT NULL,
  PRIMARY KEY  (`IDENTIFIER`),
  KEY `FK630761FF53CC4A77` (`IDENTIFIER`),
  CONSTRAINT `FK630761FF53CC4A77` FOREIGN KEY (`IDENTIFIER`) REFERENCES `dyextn_data_element` (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `dyextn_userdefined_de` */

/*Table structure for table `dyextn_view` */

CREATE TABLE `dyextn_view` (
  `IDENTIFIER` bigint(20) NOT NULL auto_increment,
  `NAME` varchar(255) default NULL,
  PRIMARY KEY  (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `dyextn_view` */

/*Table structure for table `id_table` */

CREATE TABLE `id_table` (
  `NEXT_ASSOCIATION_ID` bigint(20) NOT NULL,
  PRIMARY KEY  (`NEXT_ASSOCIATION_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `id_table` */

insert into `id_table` (`NEXT_ASSOCIATION_ID`) values (1);

/*Table structure for table `inter_model_association` */

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

CREATE TABLE `intra_model_association` (
  `ASSOCIATION_ID` bigint(20) NOT NULL,
  `DE_ASSOCIATION_ID` bigint(20) NOT NULL,
  PRIMARY KEY  (`ASSOCIATION_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `intra_model_association` */

insert into `intra_model_association` (`ASSOCIATION_ID`,`DE_ASSOCIATION_ID`) values (1,56),(2,81),(3,80),(4,74),(5,71),(6,68),(7,64),(8,59),(9,55),(10,51),(11,42),(12,39),(13,36),(14,33),(15,30),(16,27),(17,207),(18,275),(19,273),(20,270),(21,293),(22,292),(23,125),(24,291),(25,290),(26,282),(27,279),(28,268),(29,265),(30,262),(31,232),(32,228),(33,226),(34,223),(35,222),(36,84),(37,22),(38,12),(39,220),(40,162),(41,219),(42,215),(43,205),(44,204),(45,193),(46,192),(47,190),(48,187),(49,186),(50,185),(51,184),(52,183),(53,235),(54,238),(55,241),(56,167),(57,166),(58,165),(59,160),(60,157),(61,152),(62,148),(63,145),(64,143),(65,136),(66,133),(67,131),(68,129),(69,117),(70,115),(71,112),(72,111),(73,107);

/*Table structure for table `path` */

CREATE TABLE `path` (
  `PATH_ID` bigint(20) NOT NULL,
  `FIRST_ENTITY_ID` bigint(20) default NULL,
  `INTERMEDIATE_PATH` varchar(1000) default NULL,
  `LAST_ENTITY_ID` bigint(20) default NULL,
  PRIMARY KEY  (`PATH_ID`),
  KEY `INDEX1` (`FIRST_ENTITY_ID`,`LAST_ENTITY_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `path` */

insert into `path` (`PATH_ID`,`FIRST_ENTITY_ID`,`INTERMEDIATE_PATH`,`LAST_ENTITY_ID`) values (1,52,'1',23),(2,23,'2',24),(3,24,'3',65),(4,65,'4',72),(5,65,'5',69),(6,65,'6',66),(7,24,'7',60),(8,24,'8',52),(9,52,'9',53),(10,24,'10',13),(11,24,'11',40),(12,24,'12',37),(13,24,'13',34),(14,24,'14',31),(15,24,'15',28),(16,24,'16',25),(17,206,'17',13),(18,269,'18',8),(19,269,'19',271),(20,269,'20',180),(21,289,'21',108),(22,289,'22',124),(23,124,'23',108),(24,289,'24',23),(25,289,'25',182),(26,182,'26',280),(27,182,'27',269),(28,182,'28',266),(29,182,'29',263),(30,182,'30',8),(31,182,'31',221),(32,221,'32',8),(33,221,'33',224),(34,221,'34',176),(35,221,'35',7),(36,7,'36',23),(37,7,'37',13),(38,7,'38',8),(39,182,'39',161),(40,161,'40',108),(41,182,'41',216),(42,182,'42',206),(43,182,'43',108),(44,182,'44',191),(45,191,'45',13),(46,191,'46',8),(47,182,'47',188),(48,182,'48',104),(49,182,'49',100),(50,182,'50',89),(51,182,'51',7),(52,182,'52',2),(53,182,'53',233),(54,182,'54',236),(55,182,'55',239),(56,106,'56',116),(57,116,'57',108),(58,116,'58',161),(59,116,'59',158),(60,116,'60',132),(61,132,'61',149),(62,132,'62',146),(63,132,'63',134),(64,134,'64',135),(65,135,'65',93),(66,132,'66',102),(67,116,'67',8),(68,116,'68',124),(69,116,'69',102),(70,106,'70',113),(71,106,'71',23),(72,106,'72',108),(73,106,'73',98);

/*Table structure for table `query` */

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

CREATE TABLE `query_base_expression` (
  `IDENTIFIER` bigint(20) NOT NULL auto_increment,
  `EXPR_TYPE` varchar(255) NOT NULL,
  PRIMARY KEY  (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `query_base_expression` */

/*Table structure for table `query_baseexpr_to_connectors` */

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

CREATE TABLE `query_condition` (
  `IDENTIFIER` bigint(20) NOT NULL auto_increment,
  `ATTRIBUTE_ID` bigint(20) NOT NULL,
  `RELATIONAL_OPERATOR` varchar(255) default NULL,
  PRIMARY KEY  (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `query_condition` */

/*Table structure for table `query_condition_values` */

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

CREATE TABLE `query_connector` (
  `IDENTIFIER` bigint(20) NOT NULL auto_increment,
  `OPERATOR` varchar(255) default NULL,
  `NESTING_NUMBER` int(11) default NULL,
  PRIMARY KEY  (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `query_connector` */

/*Table structure for table `query_constraint_to_expr` */

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

CREATE TABLE `query_intra_model_association` (
  `IDENTIFIER` bigint(20) NOT NULL,
  `DE_ASSOCIATION_ID` bigint(20) NOT NULL,
  PRIMARY KEY  (`IDENTIFIER`),
  KEY `FKF1EDBDD35F5AB67E` (`IDENTIFIER`),
  CONSTRAINT `FKF1EDBDD35F5AB67E` FOREIGN KEY (`IDENTIFIER`) REFERENCES `query_model_association` (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `query_intra_model_association` */

/*Table structure for table `query_join_graph` */

CREATE TABLE `query_join_graph` (
  `IDENTIFIER` bigint(20) NOT NULL auto_increment,
  `COMMONS_GRAPH_ID` bigint(20) default NULL,
  PRIMARY KEY  (`IDENTIFIER`),
  KEY `FK2B41B5D09DBC4D94` (`COMMONS_GRAPH_ID`),
  CONSTRAINT `FK2B41B5D09DBC4D94` FOREIGN KEY (`COMMONS_GRAPH_ID`) REFERENCES `commons_graph` (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `query_join_graph` */

/*Table structure for table `query_model_association` */

CREATE TABLE `query_model_association` (
  `IDENTIFIER` bigint(20) NOT NULL auto_increment,
  PRIMARY KEY  (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `query_model_association` */

/*Table structure for table `query_operand` */

CREATE TABLE `query_operand` (
  `IDENTIFIER` bigint(20) NOT NULL auto_increment,
  `OPND_TYPE` varchar(255) NOT NULL,
  PRIMARY KEY  (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `query_operand` */

/*Table structure for table `query_output_attribute` */

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

CREATE TABLE `query_parameter` (
  `IDENTIFIER` bigint(20) NOT NULL auto_increment,
  `NAME` varchar(255) default NULL,
  `OBJECT_CLASS` varchar(255) default NULL,
  `OBJECT_ID` bigint(20) default NULL,
  PRIMARY KEY  (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `query_parameter` */

/*Table structure for table `query_parameterized_query` */

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

CREATE TABLE `query_query_entity` (
  `IDENTIFIER` bigint(20) NOT NULL auto_increment,
  `ENTITY_ID` bigint(20) NOT NULL,
  PRIMARY KEY  (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `query_query_entity` */

/*Table structure for table `query_rule_cond` */

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

