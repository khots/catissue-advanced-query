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

DROP TABLE IF EXISTS `association`;

CREATE TABLE `association` (
  `ASSOCIATION_ID` bigint(20) NOT NULL,
  `ASSOCIATION_TYPE` int(8) NOT NULL,
  PRIMARY KEY  (`ASSOCIATION_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `association` */

insert into `association` (`ASSOCIATION_ID`,`ASSOCIATION_TYPE`) values (1,2);
insert into `association` (`ASSOCIATION_ID`,`ASSOCIATION_TYPE`) values (2,2);
insert into `association` (`ASSOCIATION_ID`,`ASSOCIATION_TYPE`) values (3,2);
insert into `association` (`ASSOCIATION_ID`,`ASSOCIATION_TYPE`) values (4,2);
insert into `association` (`ASSOCIATION_ID`,`ASSOCIATION_TYPE`) values (5,2);
insert into `association` (`ASSOCIATION_ID`,`ASSOCIATION_TYPE`) values (6,2);
insert into `association` (`ASSOCIATION_ID`,`ASSOCIATION_TYPE`) values (7,2);
insert into `association` (`ASSOCIATION_ID`,`ASSOCIATION_TYPE`) values (8,2);
insert into `association` (`ASSOCIATION_ID`,`ASSOCIATION_TYPE`) values (9,2);
insert into `association` (`ASSOCIATION_ID`,`ASSOCIATION_TYPE`) values (10,2);
insert into `association` (`ASSOCIATION_ID`,`ASSOCIATION_TYPE`) values (11,2);
insert into `association` (`ASSOCIATION_ID`,`ASSOCIATION_TYPE`) values (12,2);
insert into `association` (`ASSOCIATION_ID`,`ASSOCIATION_TYPE`) values (13,2);
insert into `association` (`ASSOCIATION_ID`,`ASSOCIATION_TYPE`) values (14,2);
insert into `association` (`ASSOCIATION_ID`,`ASSOCIATION_TYPE`) values (15,2);
insert into `association` (`ASSOCIATION_ID`,`ASSOCIATION_TYPE`) values (16,2);

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
  CONSTRAINT `FKA6B0D8BAA0494B1D` FOREIGN KEY (`GRAPH_ID`) REFERENCES `commons_graph` (`IDENTIFIER`),
  CONSTRAINT `FKA6B0D8BAFAEF80D` FOREIGN KEY (`EDGE_ID`) REFERENCES `commons_graph_edge` (`IDENTIFIER`)
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

insert into `csm_group` (`GROUP_ID`,`GROUP_NAME`,`GROUP_DESC`,`UPDATE_DATE`,`APPLICATION_ID`) values (1,'ADMINISTRATOR_GROUP','Group of Administrators','2005-01-01',1);
insert into `csm_group` (`GROUP_ID`,`GROUP_NAME`,`GROUP_DESC`,`UPDATE_DATE`,`APPLICATION_ID`) values (2,'SUPERVISOR_GROUP','Group of Supervisors','2005-01-01',1);
insert into `csm_group` (`GROUP_ID`,`GROUP_NAME`,`GROUP_DESC`,`UPDATE_DATE`,`APPLICATION_ID`) values (3,'TECHNICIAN_GROUP','Group of Technicians','2005-01-01',1);
insert into `csm_group` (`GROUP_ID`,`GROUP_NAME`,`GROUP_DESC`,`UPDATE_DATE`,`APPLICATION_ID`) values (4,'PUBLIC_GROUP','Group of Public Users','2005-01-01',1);

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

insert into `csm_pg_pe` (`PG_PE_ID`,`PROTECTION_GROUP_ID`,`PROTECTION_ELEMENT_ID`,`UPDATE_DATE`) values (16,21,9,NULL);
insert into `csm_pg_pe` (`PG_PE_ID`,`PROTECTION_GROUP_ID`,`PROTECTION_ELEMENT_ID`,`UPDATE_DATE`) values (17,2,10,NULL);
insert into `csm_pg_pe` (`PG_PE_ID`,`PROTECTION_GROUP_ID`,`PROTECTION_ELEMENT_ID`,`UPDATE_DATE`) values (19,2,11,NULL);
insert into `csm_pg_pe` (`PG_PE_ID`,`PROTECTION_GROUP_ID`,`PROTECTION_ELEMENT_ID`,`UPDATE_DATE`) values (20,2,12,NULL);
insert into `csm_pg_pe` (`PG_PE_ID`,`PROTECTION_GROUP_ID`,`PROTECTION_ELEMENT_ID`,`UPDATE_DATE`) values (21,2,13,NULL);
insert into `csm_pg_pe` (`PG_PE_ID`,`PROTECTION_GROUP_ID`,`PROTECTION_ELEMENT_ID`,`UPDATE_DATE`) values (22,2,14,NULL);
insert into `csm_pg_pe` (`PG_PE_ID`,`PROTECTION_GROUP_ID`,`PROTECTION_ELEMENT_ID`,`UPDATE_DATE`) values (23,3,15,NULL);
insert into `csm_pg_pe` (`PG_PE_ID`,`PROTECTION_GROUP_ID`,`PROTECTION_ELEMENT_ID`,`UPDATE_DATE`) values (24,3,16,NULL);
insert into `csm_pg_pe` (`PG_PE_ID`,`PROTECTION_GROUP_ID`,`PROTECTION_ELEMENT_ID`,`UPDATE_DATE`) values (25,3,17,NULL);
insert into `csm_pg_pe` (`PG_PE_ID`,`PROTECTION_GROUP_ID`,`PROTECTION_ELEMENT_ID`,`UPDATE_DATE`) values (26,3,18,NULL);
insert into `csm_pg_pe` (`PG_PE_ID`,`PROTECTION_GROUP_ID`,`PROTECTION_ELEMENT_ID`,`UPDATE_DATE`) values (27,3,19,NULL);
insert into `csm_pg_pe` (`PG_PE_ID`,`PROTECTION_GROUP_ID`,`PROTECTION_ELEMENT_ID`,`UPDATE_DATE`) values (28,1,20,NULL);
insert into `csm_pg_pe` (`PG_PE_ID`,`PROTECTION_GROUP_ID`,`PROTECTION_ELEMENT_ID`,`UPDATE_DATE`) values (29,3,21,NULL);
insert into `csm_pg_pe` (`PG_PE_ID`,`PROTECTION_GROUP_ID`,`PROTECTION_ELEMENT_ID`,`UPDATE_DATE`) values (30,3,22,NULL);
insert into `csm_pg_pe` (`PG_PE_ID`,`PROTECTION_GROUP_ID`,`PROTECTION_ELEMENT_ID`,`UPDATE_DATE`) values (31,3,23,NULL);
insert into `csm_pg_pe` (`PG_PE_ID`,`PROTECTION_GROUP_ID`,`PROTECTION_ELEMENT_ID`,`UPDATE_DATE`) values (32,3,24,NULL);
insert into `csm_pg_pe` (`PG_PE_ID`,`PROTECTION_GROUP_ID`,`PROTECTION_ELEMENT_ID`,`UPDATE_DATE`) values (33,3,25,NULL);
insert into `csm_pg_pe` (`PG_PE_ID`,`PROTECTION_GROUP_ID`,`PROTECTION_ELEMENT_ID`,`UPDATE_DATE`) values (34,3,26,NULL);
insert into `csm_pg_pe` (`PG_PE_ID`,`PROTECTION_GROUP_ID`,`PROTECTION_ELEMENT_ID`,`UPDATE_DATE`) values (35,3,27,NULL);
insert into `csm_pg_pe` (`PG_PE_ID`,`PROTECTION_GROUP_ID`,`PROTECTION_ELEMENT_ID`,`UPDATE_DATE`) values (36,3,28,NULL);
insert into `csm_pg_pe` (`PG_PE_ID`,`PROTECTION_GROUP_ID`,`PROTECTION_ELEMENT_ID`,`UPDATE_DATE`) values (37,3,29,NULL);
insert into `csm_pg_pe` (`PG_PE_ID`,`PROTECTION_GROUP_ID`,`PROTECTION_ELEMENT_ID`,`UPDATE_DATE`) values (38,3,30,NULL);
insert into `csm_pg_pe` (`PG_PE_ID`,`PROTECTION_GROUP_ID`,`PROTECTION_ELEMENT_ID`,`UPDATE_DATE`) values (39,3,31,NULL);
insert into `csm_pg_pe` (`PG_PE_ID`,`PROTECTION_GROUP_ID`,`PROTECTION_ELEMENT_ID`,`UPDATE_DATE`) values (40,3,32,NULL);
insert into `csm_pg_pe` (`PG_PE_ID`,`PROTECTION_GROUP_ID`,`PROTECTION_ELEMENT_ID`,`UPDATE_DATE`) values (41,3,33,NULL);
insert into `csm_pg_pe` (`PG_PE_ID`,`PROTECTION_GROUP_ID`,`PROTECTION_ELEMENT_ID`,`UPDATE_DATE`) values (42,3,34,NULL);
insert into `csm_pg_pe` (`PG_PE_ID`,`PROTECTION_GROUP_ID`,`PROTECTION_ELEMENT_ID`,`UPDATE_DATE`) values (43,3,35,NULL);
insert into `csm_pg_pe` (`PG_PE_ID`,`PROTECTION_GROUP_ID`,`PROTECTION_ELEMENT_ID`,`UPDATE_DATE`) values (44,3,36,NULL);
insert into `csm_pg_pe` (`PG_PE_ID`,`PROTECTION_GROUP_ID`,`PROTECTION_ELEMENT_ID`,`UPDATE_DATE`) values (45,3,37,NULL);
insert into `csm_pg_pe` (`PG_PE_ID`,`PROTECTION_GROUP_ID`,`PROTECTION_ELEMENT_ID`,`UPDATE_DATE`) values (46,3,38,NULL);
insert into `csm_pg_pe` (`PG_PE_ID`,`PROTECTION_GROUP_ID`,`PROTECTION_ELEMENT_ID`,`UPDATE_DATE`) values (47,3,39,NULL);
insert into `csm_pg_pe` (`PG_PE_ID`,`PROTECTION_GROUP_ID`,`PROTECTION_ELEMENT_ID`,`UPDATE_DATE`) values (48,3,40,NULL);
insert into `csm_pg_pe` (`PG_PE_ID`,`PROTECTION_GROUP_ID`,`PROTECTION_ELEMENT_ID`,`UPDATE_DATE`) values (49,1,41,NULL);
insert into `csm_pg_pe` (`PG_PE_ID`,`PROTECTION_GROUP_ID`,`PROTECTION_ELEMENT_ID`,`UPDATE_DATE`) values (50,1,42,NULL);
insert into `csm_pg_pe` (`PG_PE_ID`,`PROTECTION_GROUP_ID`,`PROTECTION_ELEMENT_ID`,`UPDATE_DATE`) values (51,1,43,NULL);
insert into `csm_pg_pe` (`PG_PE_ID`,`PROTECTION_GROUP_ID`,`PROTECTION_ELEMENT_ID`,`UPDATE_DATE`) values (52,1,44,NULL);
insert into `csm_pg_pe` (`PG_PE_ID`,`PROTECTION_GROUP_ID`,`PROTECTION_ELEMENT_ID`,`UPDATE_DATE`) values (53,1,45,NULL);
insert into `csm_pg_pe` (`PG_PE_ID`,`PROTECTION_GROUP_ID`,`PROTECTION_ELEMENT_ID`,`UPDATE_DATE`) values (54,1,46,NULL);
insert into `csm_pg_pe` (`PG_PE_ID`,`PROTECTION_GROUP_ID`,`PROTECTION_ELEMENT_ID`,`UPDATE_DATE`) values (55,1,47,NULL);
insert into `csm_pg_pe` (`PG_PE_ID`,`PROTECTION_GROUP_ID`,`PROTECTION_ELEMENT_ID`,`UPDATE_DATE`) values (56,1,48,NULL);
insert into `csm_pg_pe` (`PG_PE_ID`,`PROTECTION_GROUP_ID`,`PROTECTION_ELEMENT_ID`,`UPDATE_DATE`) values (58,1,50,NULL);
insert into `csm_pg_pe` (`PG_PE_ID`,`PROTECTION_GROUP_ID`,`PROTECTION_ELEMENT_ID`,`UPDATE_DATE`) values (59,1,51,NULL);
insert into `csm_pg_pe` (`PG_PE_ID`,`PROTECTION_GROUP_ID`,`PROTECTION_ELEMENT_ID`,`UPDATE_DATE`) values (60,3,52,NULL);
insert into `csm_pg_pe` (`PG_PE_ID`,`PROTECTION_GROUP_ID`,`PROTECTION_ELEMENT_ID`,`UPDATE_DATE`) values (61,3,53,NULL);
insert into `csm_pg_pe` (`PG_PE_ID`,`PROTECTION_GROUP_ID`,`PROTECTION_ELEMENT_ID`,`UPDATE_DATE`) values (299,1,269,NULL);
insert into `csm_pg_pe` (`PG_PE_ID`,`PROTECTION_GROUP_ID`,`PROTECTION_ELEMENT_ID`,`UPDATE_DATE`) values (302,1,272,NULL);
insert into `csm_pg_pe` (`PG_PE_ID`,`PROTECTION_GROUP_ID`,`PROTECTION_ELEMENT_ID`,`UPDATE_DATE`) values (306,1,276,NULL);
insert into `csm_pg_pe` (`PG_PE_ID`,`PROTECTION_GROUP_ID`,`PROTECTION_ELEMENT_ID`,`UPDATE_DATE`) values (312,3,281,NULL);
insert into `csm_pg_pe` (`PG_PE_ID`,`PROTECTION_GROUP_ID`,`PROTECTION_ELEMENT_ID`,`UPDATE_DATE`) values (330,1,54,NULL);
insert into `csm_pg_pe` (`PG_PE_ID`,`PROTECTION_GROUP_ID`,`PROTECTION_ELEMENT_ID`,`UPDATE_DATE`) values (331,1,55,NULL);
insert into `csm_pg_pe` (`PG_PE_ID`,`PROTECTION_GROUP_ID`,`PROTECTION_ELEMENT_ID`,`UPDATE_DATE`) values (332,1,56,NULL);
insert into `csm_pg_pe` (`PG_PE_ID`,`PROTECTION_GROUP_ID`,`PROTECTION_ELEMENT_ID`,`UPDATE_DATE`) values (335,44,304,'2007-01-04');
insert into `csm_pg_pe` (`PG_PE_ID`,`PROTECTION_GROUP_ID`,`PROTECTION_ELEMENT_ID`,`UPDATE_DATE`) values (336,44,305,'2007-01-04');
insert into `csm_pg_pe` (`PG_PE_ID`,`PROTECTION_GROUP_ID`,`PROTECTION_ELEMENT_ID`,`UPDATE_DATE`) values (337,44,306,'2007-01-04');
insert into `csm_pg_pe` (`PG_PE_ID`,`PROTECTION_GROUP_ID`,`PROTECTION_ELEMENT_ID`,`UPDATE_DATE`) values (338,44,307,'2007-01-04');
insert into `csm_pg_pe` (`PG_PE_ID`,`PROTECTION_GROUP_ID`,`PROTECTION_ELEMENT_ID`,`UPDATE_DATE`) values (339,44,308,'2007-01-04');
insert into `csm_pg_pe` (`PG_PE_ID`,`PROTECTION_GROUP_ID`,`PROTECTION_ELEMENT_ID`,`UPDATE_DATE`) values (340,44,309,'2007-01-04');
insert into `csm_pg_pe` (`PG_PE_ID`,`PROTECTION_GROUP_ID`,`PROTECTION_ELEMENT_ID`,`UPDATE_DATE`) values (341,44,310,'2007-01-04');
insert into `csm_pg_pe` (`PG_PE_ID`,`PROTECTION_GROUP_ID`,`PROTECTION_ELEMENT_ID`,`UPDATE_DATE`) values (342,44,311,'2007-01-04');
insert into `csm_pg_pe` (`PG_PE_ID`,`PROTECTION_GROUP_ID`,`PROTECTION_ELEMENT_ID`,`UPDATE_DATE`) values (343,44,312,'2007-01-04');
insert into `csm_pg_pe` (`PG_PE_ID`,`PROTECTION_GROUP_ID`,`PROTECTION_ELEMENT_ID`,`UPDATE_DATE`) values (344,44,313,'2007-01-04');
insert into `csm_pg_pe` (`PG_PE_ID`,`PROTECTION_GROUP_ID`,`PROTECTION_ELEMENT_ID`,`UPDATE_DATE`) values (348,1,304,'2006-11-27');
insert into `csm_pg_pe` (`PG_PE_ID`,`PROTECTION_GROUP_ID`,`PROTECTION_ELEMENT_ID`,`UPDATE_DATE`) values (349,2,304,'2006-11-27');
insert into `csm_pg_pe` (`PG_PE_ID`,`PROTECTION_GROUP_ID`,`PROTECTION_ELEMENT_ID`,`UPDATE_DATE`) values (350,3,304,'2006-11-27');
insert into `csm_pg_pe` (`PG_PE_ID`,`PROTECTION_GROUP_ID`,`PROTECTION_ELEMENT_ID`,`UPDATE_DATE`) values (351,1,305,'2006-11-27');
insert into `csm_pg_pe` (`PG_PE_ID`,`PROTECTION_GROUP_ID`,`PROTECTION_ELEMENT_ID`,`UPDATE_DATE`) values (352,2,305,'2006-11-27');
insert into `csm_pg_pe` (`PG_PE_ID`,`PROTECTION_GROUP_ID`,`PROTECTION_ELEMENT_ID`,`UPDATE_DATE`) values (353,3,305,'2006-11-27');
insert into `csm_pg_pe` (`PG_PE_ID`,`PROTECTION_GROUP_ID`,`PROTECTION_ELEMENT_ID`,`UPDATE_DATE`) values (354,1,306,'2006-11-27');
insert into `csm_pg_pe` (`PG_PE_ID`,`PROTECTION_GROUP_ID`,`PROTECTION_ELEMENT_ID`,`UPDATE_DATE`) values (355,2,306,'2006-11-27');
insert into `csm_pg_pe` (`PG_PE_ID`,`PROTECTION_GROUP_ID`,`PROTECTION_ELEMENT_ID`,`UPDATE_DATE`) values (356,3,306,'2006-11-27');
insert into `csm_pg_pe` (`PG_PE_ID`,`PROTECTION_GROUP_ID`,`PROTECTION_ELEMENT_ID`,`UPDATE_DATE`) values (357,1,307,'2006-11-27');
insert into `csm_pg_pe` (`PG_PE_ID`,`PROTECTION_GROUP_ID`,`PROTECTION_ELEMENT_ID`,`UPDATE_DATE`) values (358,2,307,'2006-11-27');
insert into `csm_pg_pe` (`PG_PE_ID`,`PROTECTION_GROUP_ID`,`PROTECTION_ELEMENT_ID`,`UPDATE_DATE`) values (359,3,307,'2006-11-27');
insert into `csm_pg_pe` (`PG_PE_ID`,`PROTECTION_GROUP_ID`,`PROTECTION_ELEMENT_ID`,`UPDATE_DATE`) values (360,1,308,'2006-11-27');
insert into `csm_pg_pe` (`PG_PE_ID`,`PROTECTION_GROUP_ID`,`PROTECTION_ELEMENT_ID`,`UPDATE_DATE`) values (361,2,308,'2006-11-27');
insert into `csm_pg_pe` (`PG_PE_ID`,`PROTECTION_GROUP_ID`,`PROTECTION_ELEMENT_ID`,`UPDATE_DATE`) values (362,3,308,'2006-11-27');
insert into `csm_pg_pe` (`PG_PE_ID`,`PROTECTION_GROUP_ID`,`PROTECTION_ELEMENT_ID`,`UPDATE_DATE`) values (363,1,309,'2006-11-27');
insert into `csm_pg_pe` (`PG_PE_ID`,`PROTECTION_GROUP_ID`,`PROTECTION_ELEMENT_ID`,`UPDATE_DATE`) values (364,2,309,'2006-11-27');
insert into `csm_pg_pe` (`PG_PE_ID`,`PROTECTION_GROUP_ID`,`PROTECTION_ELEMENT_ID`,`UPDATE_DATE`) values (365,3,309,'2006-11-27');
insert into `csm_pg_pe` (`PG_PE_ID`,`PROTECTION_GROUP_ID`,`PROTECTION_ELEMENT_ID`,`UPDATE_DATE`) values (366,1,310,'2006-11-27');
insert into `csm_pg_pe` (`PG_PE_ID`,`PROTECTION_GROUP_ID`,`PROTECTION_ELEMENT_ID`,`UPDATE_DATE`) values (367,2,310,'2006-11-27');
insert into `csm_pg_pe` (`PG_PE_ID`,`PROTECTION_GROUP_ID`,`PROTECTION_ELEMENT_ID`,`UPDATE_DATE`) values (368,3,310,'2006-11-27');
insert into `csm_pg_pe` (`PG_PE_ID`,`PROTECTION_GROUP_ID`,`PROTECTION_ELEMENT_ID`,`UPDATE_DATE`) values (369,1,311,'2006-11-27');
insert into `csm_pg_pe` (`PG_PE_ID`,`PROTECTION_GROUP_ID`,`PROTECTION_ELEMENT_ID`,`UPDATE_DATE`) values (370,2,311,'2006-11-27');
insert into `csm_pg_pe` (`PG_PE_ID`,`PROTECTION_GROUP_ID`,`PROTECTION_ELEMENT_ID`,`UPDATE_DATE`) values (371,3,311,'2006-11-27');
insert into `csm_pg_pe` (`PG_PE_ID`,`PROTECTION_GROUP_ID`,`PROTECTION_ELEMENT_ID`,`UPDATE_DATE`) values (372,1,312,'2006-11-27');
insert into `csm_pg_pe` (`PG_PE_ID`,`PROTECTION_GROUP_ID`,`PROTECTION_ELEMENT_ID`,`UPDATE_DATE`) values (373,2,312,'2006-11-27');
insert into `csm_pg_pe` (`PG_PE_ID`,`PROTECTION_GROUP_ID`,`PROTECTION_ELEMENT_ID`,`UPDATE_DATE`) values (374,3,312,'2006-11-27');
insert into `csm_pg_pe` (`PG_PE_ID`,`PROTECTION_GROUP_ID`,`PROTECTION_ELEMENT_ID`,`UPDATE_DATE`) values (375,1,313,'2006-11-27');
insert into `csm_pg_pe` (`PG_PE_ID`,`PROTECTION_GROUP_ID`,`PROTECTION_ELEMENT_ID`,`UPDATE_DATE`) values (376,2,313,'2006-11-27');
insert into `csm_pg_pe` (`PG_PE_ID`,`PROTECTION_GROUP_ID`,`PROTECTION_ELEMENT_ID`,`UPDATE_DATE`) values (377,3,313,'2006-11-27');
insert into `csm_pg_pe` (`PG_PE_ID`,`PROTECTION_GROUP_ID`,`PROTECTION_ELEMENT_ID`,`UPDATE_DATE`) values (378,1,300,'2006-11-27');
insert into `csm_pg_pe` (`PG_PE_ID`,`PROTECTION_GROUP_ID`,`PROTECTION_ELEMENT_ID`,`UPDATE_DATE`) values (379,2,300,'2006-11-27');
insert into `csm_pg_pe` (`PG_PE_ID`,`PROTECTION_GROUP_ID`,`PROTECTION_ELEMENT_ID`,`UPDATE_DATE`) values (380,3,300,'2006-11-27');
insert into `csm_pg_pe` (`PG_PE_ID`,`PROTECTION_GROUP_ID`,`PROTECTION_ELEMENT_ID`,`UPDATE_DATE`) values (381,1,301,'2006-11-27');
insert into `csm_pg_pe` (`PG_PE_ID`,`PROTECTION_GROUP_ID`,`PROTECTION_ELEMENT_ID`,`UPDATE_DATE`) values (382,2,301,'2006-11-27');
insert into `csm_pg_pe` (`PG_PE_ID`,`PROTECTION_GROUP_ID`,`PROTECTION_ELEMENT_ID`,`UPDATE_DATE`) values (383,3,301,'2006-11-27');
insert into `csm_pg_pe` (`PG_PE_ID`,`PROTECTION_GROUP_ID`,`PROTECTION_ELEMENT_ID`,`UPDATE_DATE`) values (384,1,302,'2006-11-27');
insert into `csm_pg_pe` (`PG_PE_ID`,`PROTECTION_GROUP_ID`,`PROTECTION_ELEMENT_ID`,`UPDATE_DATE`) values (385,2,302,'2006-11-27');
insert into `csm_pg_pe` (`PG_PE_ID`,`PROTECTION_GROUP_ID`,`PROTECTION_ELEMENT_ID`,`UPDATE_DATE`) values (386,3,302,'2006-11-27');
insert into `csm_pg_pe` (`PG_PE_ID`,`PROTECTION_GROUP_ID`,`PROTECTION_ELEMENT_ID`,`UPDATE_DATE`) values (387,3,303,'2007-01-18');
insert into `csm_pg_pe` (`PG_PE_ID`,`PROTECTION_GROUP_ID`,`PROTECTION_ELEMENT_ID`,`UPDATE_DATE`) values (388,1,315,'2006-11-27');
insert into `csm_pg_pe` (`PG_PE_ID`,`PROTECTION_GROUP_ID`,`PROTECTION_ELEMENT_ID`,`UPDATE_DATE`) values (389,1,316,'2006-11-27');
insert into `csm_pg_pe` (`PG_PE_ID`,`PROTECTION_GROUP_ID`,`PROTECTION_ELEMENT_ID`,`UPDATE_DATE`) values (390,1,317,'2006-11-27');
insert into `csm_pg_pe` (`PG_PE_ID`,`PROTECTION_GROUP_ID`,`PROTECTION_ELEMENT_ID`,`UPDATE_DATE`) values (391,1,318,'2006-11-27');
insert into `csm_pg_pe` (`PG_PE_ID`,`PROTECTION_GROUP_ID`,`PROTECTION_ELEMENT_ID`,`UPDATE_DATE`) values (392,1,319,'2006-11-27');
insert into `csm_pg_pe` (`PG_PE_ID`,`PROTECTION_GROUP_ID`,`PROTECTION_ELEMENT_ID`,`UPDATE_DATE`) values (399,1,326,'2008-05-28');
insert into `csm_pg_pe` (`PG_PE_ID`,`PROTECTION_GROUP_ID`,`PROTECTION_ELEMENT_ID`,`UPDATE_DATE`) values (400,1,327,'2008-05-28');
insert into `csm_pg_pe` (`PG_PE_ID`,`PROTECTION_GROUP_ID`,`PROTECTION_ELEMENT_ID`,`UPDATE_DATE`) values (401,1,328,'2008-05-28');
insert into `csm_pg_pe` (`PG_PE_ID`,`PROTECTION_GROUP_ID`,`PROTECTION_ELEMENT_ID`,`UPDATE_DATE`) values (402,1,329,'2008-05-28');
insert into `csm_pg_pe` (`PG_PE_ID`,`PROTECTION_GROUP_ID`,`PROTECTION_ELEMENT_ID`,`UPDATE_DATE`) values (403,1,330,'2008-05-28');
insert into `csm_pg_pe` (`PG_PE_ID`,`PROTECTION_GROUP_ID`,`PROTECTION_ELEMENT_ID`,`UPDATE_DATE`) values (404,1,331,'2008-05-28');
insert into `csm_pg_pe` (`PG_PE_ID`,`PROTECTION_GROUP_ID`,`PROTECTION_ELEMENT_ID`,`UPDATE_DATE`) values (405,1,332,'2008-05-28');
insert into `csm_pg_pe` (`PG_PE_ID`,`PROTECTION_GROUP_ID`,`PROTECTION_ELEMENT_ID`,`UPDATE_DATE`) values (406,1,333,'2008-05-28');
insert into `csm_pg_pe` (`PG_PE_ID`,`PROTECTION_GROUP_ID`,`PROTECTION_ELEMENT_ID`,`UPDATE_DATE`) values (407,1,334,'2008-05-28');
insert into `csm_pg_pe` (`PG_PE_ID`,`PROTECTION_GROUP_ID`,`PROTECTION_ELEMENT_ID`,`UPDATE_DATE`) values (408,1,335,'2008-05-28');
insert into `csm_pg_pe` (`PG_PE_ID`,`PROTECTION_GROUP_ID`,`PROTECTION_ELEMENT_ID`,`UPDATE_DATE`) values (409,1,336,'2008-07-22');

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

insert into `csm_privilege` (`PRIVILEGE_ID`,`PRIVILEGE_NAME`,`PRIVILEGE_DESCRIPTION`,`UPDATE_DATE`) values (1,'CREATE','This privilege grants permission to a user to create an entity. This entity can be an object, a database entry, or a resource such as a network connection','2005-08-22');
insert into `csm_privilege` (`PRIVILEGE_ID`,`PRIVILEGE_NAME`,`PRIVILEGE_DESCRIPTION`,`UPDATE_DATE`) values (2,'ACCESS','This privilege allows a user to access a particular resource.  Examples of resources include a network or database connection, socket, module of the application, or even the application itself','2005-08-22');
insert into `csm_privilege` (`PRIVILEGE_ID`,`PRIVILEGE_NAME`,`PRIVILEGE_DESCRIPTION`,`UPDATE_DATE`) values (3,'READ','This privilege permits the user to read data from a file, URL, database, an object, etc. This can be used at an entity level signifying that the user is allowed to read data about a particular entry','2005-08-22');
insert into `csm_privilege` (`PRIVILEGE_ID`,`PRIVILEGE_NAME`,`PRIVILEGE_DESCRIPTION`,`UPDATE_DATE`) values (4,'WRITE','This privilege allows a user to write data to a file, URL, database, an object, etc. This can be used at an entity level signifying that the user is allowed to write data about a particular entity','2005-08-22');
insert into `csm_privilege` (`PRIVILEGE_ID`,`PRIVILEGE_NAME`,`PRIVILEGE_DESCRIPTION`,`UPDATE_DATE`) values (5,'UPDATE','This privilege grants permission at an entity level and signifies that the user is allowed to update data for a particular entity. Entities may include an object, object attribute, database row etc','2005-08-22');
insert into `csm_privilege` (`PRIVILEGE_ID`,`PRIVILEGE_NAME`,`PRIVILEGE_DESCRIPTION`,`UPDATE_DATE`) values (6,'DELETE','This privilege permits a user to delete a logical entity. This entity can be an object, a database entry, a resource such as a network connection, etc','2005-08-22');
insert into `csm_privilege` (`PRIVILEGE_ID`,`PRIVILEGE_NAME`,`PRIVILEGE_DESCRIPTION`,`UPDATE_DATE`) values (7,'EXECUTE','This privilege allows a user to execute a particular resource. The resource can be a method, function, behavior of the application, URL, button etc','2005-08-22');
insert into `csm_privilege` (`PRIVILEGE_ID`,`PRIVILEGE_NAME`,`PRIVILEGE_DESCRIPTION`,`UPDATE_DATE`) values (8,'USE','This privilege allows a user to use a particular resource','2005-08-22');
insert into `csm_privilege` (`PRIVILEGE_ID`,`PRIVILEGE_NAME`,`PRIVILEGE_DESCRIPTION`,`UPDATE_DATE`) values (9,'ASSIGN_READ','This privilege allows a user to assign a read privilege to others','2005-08-22');
insert into `csm_privilege` (`PRIVILEGE_ID`,`PRIVILEGE_NAME`,`PRIVILEGE_DESCRIPTION`,`UPDATE_DATE`) values (10,'ASSIGN_USE','This privilege allows a user to assign a use privilege to others','2005-08-22');
insert into `csm_privilege` (`PRIVILEGE_ID`,`PRIVILEGE_NAME`,`PRIVILEGE_DESCRIPTION`,`UPDATE_DATE`) values (11,'IDENTIFIED_DATA_ACCESS','This privilege allows a user to view identified data of an object','2005-08-22');
insert into `csm_privilege` (`PRIVILEGE_ID`,`PRIVILEGE_NAME`,`PRIVILEGE_DESCRIPTION`,`UPDATE_DATE`) values (12,'READ_DENIED','This privilege doesnt permit the user to read data','2005-08-22');
insert into `csm_privilege` (`PRIVILEGE_ID`,`PRIVILEGE_NAME`,`PRIVILEGE_DESCRIPTION`,`UPDATE_DATE`) values (13,'USER_PROVISIONING','THIS PRIVILEGE GRANTS PERMISSION TO A USER FOR USER CREATION AND ASSIGNING PRIVILEGES TO THAT USER','2008-06-19');
insert into `csm_privilege` (`PRIVILEGE_ID`,`PRIVILEGE_NAME`,`PRIVILEGE_DESCRIPTION`,`UPDATE_DATE`) values (14,'REPOSITORY_ADMINISTRATION','THIS PRIVILEGE GRANTS PERMISSION TO A USER FOR ADD EDIT SITES','2008-06-19');
insert into `csm_privilege` (`PRIVILEGE_ID`,`PRIVILEGE_NAME`,`PRIVILEGE_DESCRIPTION`,`UPDATE_DATE`) values (15,'STORAGE_ADMINISTRATION','THIS PRIVILEGE GRANTS PERMISSION TO A USER FOR ADD EDIT STORAGE TYPES AND CONTAINERS','2008-06-19');
insert into `csm_privilege` (`PRIVILEGE_ID`,`PRIVILEGE_NAME`,`PRIVILEGE_DESCRIPTION`,`UPDATE_DATE`) values (16,'PROTOCOL_ADMINISTRATION','THIS PRIVILEGE GRANTS PERMISSION TO A USER FOR ADD EDIT COLLECTION AS WELL AS DISTRIBUTION PROTOCOLS','2008-06-19');
insert into `csm_privilege` (`PRIVILEGE_ID`,`PRIVILEGE_NAME`,`PRIVILEGE_DESCRIPTION`,`UPDATE_DATE`) values (17,'DEFINE_ANNOTATION','THIS PRIVILEGE GRANTS PERMISSION TO A USER FOR ADD EDIT ANNOTATIONS','2008-06-19');
insert into `csm_privilege` (`PRIVILEGE_ID`,`PRIVILEGE_NAME`,`PRIVILEGE_DESCRIPTION`,`UPDATE_DATE`) values (18,'REGISTRATION','THIS PRIVILEGE GRANTS PERMISSION TO A USER FOR REGISTERING PARTICIPANT AND CONSENTS','2008-06-19');
insert into `csm_privilege` (`PRIVILEGE_ID`,`PRIVILEGE_NAME`,`PRIVILEGE_DESCRIPTION`,`UPDATE_DATE`) values (20,'SPECIMEN_ACCESSION','THIS PRIVILEGE GRANTS PERMISSION TO A USER FOR ADD EDIT SPECIMEN,SPECIMEN COLLECTION GROUP AND CONSENTS','2008-06-19');
insert into `csm_privilege` (`PRIVILEGE_ID`,`PRIVILEGE_NAME`,`PRIVILEGE_DESCRIPTION`,`UPDATE_DATE`) values (21,'DISTRIBUTION','THIS PRIVILEGE GRANTS PERMISSION TO A USER FOR DISTRIBUTION','2008-06-19');
insert into `csm_privilege` (`PRIVILEGE_ID`,`PRIVILEGE_NAME`,`PRIVILEGE_DESCRIPTION`,`UPDATE_DATE`) values (22,'QUERY','THIS PRIVILEGE GRANTS PERMISSION TO A USER FOR QUERY','2008-06-19');
insert into `csm_privilege` (`PRIVILEGE_ID`,`PRIVILEGE_NAME`,`PRIVILEGE_DESCRIPTION`,`UPDATE_DATE`) values (23,'PHI_ACCESS','THIS PRIVILEGE GRANTS PERMISSION TO A USER FOR VIEWING PHI DATA','2008-06-19');
insert into `csm_privilege` (`PRIVILEGE_ID`,`PRIVILEGE_NAME`,`PRIVILEGE_DESCRIPTION`,`UPDATE_DATE`) values (24,'PARTICIPANT_SCG_ANNOTATION','THIS PRIVILEGE GRANTS PERMISSION TO A USER FOR ADD EDIT ANNOTATION TO PARTICIPANT OR SCG','2008-06-19');
insert into `csm_privilege` (`PRIVILEGE_ID`,`PRIVILEGE_NAME`,`PRIVILEGE_DESCRIPTION`,`UPDATE_DATE`) values (25,'SPECIMEN_ANNOTATION','THIS PRIVILEGE GRANTS PERMISSION TO A USER FOR FOR ADD EDIT SPECIMEN ANNOTATION','2008-06-19');
insert into `csm_privilege` (`PRIVILEGE_ID`,`PRIVILEGE_NAME`,`PRIVILEGE_DESCRIPTION`,`UPDATE_DATE`) values (26,'SPECIMEN_PROCESSING','THIS PRIVILEGE GRANTS PERMISSION TO A USER FOR ADD EDIT ALIQUOT, DERIVATIVE, EVENTS','2008-06-19');
insert into `csm_privilege` (`PRIVILEGE_ID`,`PRIVILEGE_NAME`,`PRIVILEGE_DESCRIPTION`,`UPDATE_DATE`) values (27,'SPECIMEN_STORAGE','THIS PRIVILEGE GRANTS PERMISSION TO A USER FOR ADD EDIT STORAGE AND TRANSFER EVENTS','2008-06-19');
insert into `csm_privilege` (`PRIVILEGE_ID`,`PRIVILEGE_NAME`,`PRIVILEGE_DESCRIPTION`,`UPDATE_DATE`) values (28,'GENERAL_SITE_ADMINISTRATION','THIS PRIVILEGE GRANTS PERMISSION TO A USER FOR ADD EDIT SITES','2008-06-19');
insert into `csm_privilege` (`PRIVILEGE_ID`,`PRIVILEGE_NAME`,`PRIVILEGE_DESCRIPTION`,`UPDATE_DATE`) values (29,'GENERAL_ADMINISTRATION','THIS PRIVILEGE GRANTS PERMISSION TO A USER FOR ADD EDIT DEPARTMENT,INSTITUTION,CANCER RESEARCH GROUP','2008-06-19');
insert into `csm_privilege` (`PRIVILEGE_ID`,`PRIVILEGE_NAME`,`PRIVILEGE_DESCRIPTION`,`UPDATE_DATE`) values (30,'SHIPMENT_PROCESSING','THIS PRIVILEGE GRANTS PERMISSION TO A USER TO PROCESS SHIPMENTS','2008-08-20');

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

insert into `csm_protection_element` (`PROTECTION_ELEMENT_ID`,`PROTECTION_ELEMENT_NAME`,`PROTECTION_ELEMENT_DESCRIPTION`,`OBJECT_ID`,`ATTRIBUTE`,`PROTECTION_ELEMENT_TYPE`,`APPLICATION_ID`,`UPDATE_DATE`) values (9,'edu.wustl.catissuecore.domain.User_1','User class','edu.wustl.catissuecore.domain.User_1',NULL,NULL,1,'2005-01-01');
insert into `csm_protection_element` (`PROTECTION_ELEMENT_ID`,`PROTECTION_ELEMENT_NAME`,`PROTECTION_ELEMENT_DESCRIPTION`,`OBJECT_ID`,`ATTRIBUTE`,`PROTECTION_ELEMENT_TYPE`,`APPLICATION_ID`,`UPDATE_DATE`) values (10,'Participant','Participant class','edu.wustl.catissuecore.domain.Participant',NULL,NULL,1,'2005-01-01');
insert into `csm_protection_element` (`PROTECTION_ELEMENT_ID`,`PROTECTION_ELEMENT_NAME`,`PROTECTION_ELEMENT_DESCRIPTION`,`OBJECT_ID`,`ATTRIBUTE`,`PROTECTION_ELEMENT_TYPE`,`APPLICATION_ID`,`UPDATE_DATE`) values (11,'ParticipantMedicalIdentifier','ParticipantMedicalIdentifier class','edu.wustl.catissuecore.domain.ParticipantMedicalIdentifier',NULL,NULL,1,'2005-01-01');
insert into `csm_protection_element` (`PROTECTION_ELEMENT_ID`,`PROTECTION_ELEMENT_NAME`,`PROTECTION_ELEMENT_DESCRIPTION`,`OBJECT_ID`,`ATTRIBUTE`,`PROTECTION_ELEMENT_TYPE`,`APPLICATION_ID`,`UPDATE_DATE`) values (12,'ClinicalReport','ClinicalReport class','edu.wustl.catissuecore.domain.ClinicalReport',NULL,NULL,1,'2005-01-01');
insert into `csm_protection_element` (`PROTECTION_ELEMENT_ID`,`PROTECTION_ELEMENT_NAME`,`PROTECTION_ELEMENT_DESCRIPTION`,`OBJECT_ID`,`ATTRIBUTE`,`PROTECTION_ELEMENT_TYPE`,`APPLICATION_ID`,`UPDATE_DATE`) values (13,'SpecimenCollectionGroup','SpecimenCollectionGroup class','edu.wustl.catissuecore.domain.SpecimenCollectionGroup',NULL,NULL,1,'2005-01-01');
insert into `csm_protection_element` (`PROTECTION_ELEMENT_ID`,`PROTECTION_ELEMENT_NAME`,`PROTECTION_ELEMENT_DESCRIPTION`,`OBJECT_ID`,`ATTRIBUTE`,`PROTECTION_ELEMENT_TYPE`,`APPLICATION_ID`,`UPDATE_DATE`) values (14,'CollectionProtocolRegistration','CollectionProtocolRegistration class','edu.wustl.catissuecore.domain.CollectionProtocolRegistration',NULL,NULL,1,'2005-01-01');
insert into `csm_protection_element` (`PROTECTION_ELEMENT_ID`,`PROTECTION_ELEMENT_NAME`,`PROTECTION_ELEMENT_DESCRIPTION`,`OBJECT_ID`,`ATTRIBUTE`,`PROTECTION_ELEMENT_TYPE`,`APPLICATION_ID`,`UPDATE_DATE`) values (15,'SpecimenCharacteristics','SpecimenCharacteristics class','edu.wustl.catissuecore.domain.SpecimenCharacteristics',NULL,NULL,1,'2005-01-01');
insert into `csm_protection_element` (`PROTECTION_ELEMENT_ID`,`PROTECTION_ELEMENT_NAME`,`PROTECTION_ELEMENT_DESCRIPTION`,`OBJECT_ID`,`ATTRIBUTE`,`PROTECTION_ELEMENT_TYPE`,`APPLICATION_ID`,`UPDATE_DATE`) values (16,'FluidSpecimen','FluidSpecimen class','edu.wustl.catissuecore.domain.FluidSpecimen',NULL,NULL,1,'2005-01-01');
insert into `csm_protection_element` (`PROTECTION_ELEMENT_ID`,`PROTECTION_ELEMENT_NAME`,`PROTECTION_ELEMENT_DESCRIPTION`,`OBJECT_ID`,`ATTRIBUTE`,`PROTECTION_ELEMENT_TYPE`,`APPLICATION_ID`,`UPDATE_DATE`) values (17,'TissueSpecimen','TissueSpecimen class','edu.wustl.catissuecore.domain.TissueSpecimen',NULL,NULL,1,'2005-01-01');
insert into `csm_protection_element` (`PROTECTION_ELEMENT_ID`,`PROTECTION_ELEMENT_NAME`,`PROTECTION_ELEMENT_DESCRIPTION`,`OBJECT_ID`,`ATTRIBUTE`,`PROTECTION_ELEMENT_TYPE`,`APPLICATION_ID`,`UPDATE_DATE`) values (18,'CellSpecimen','CellSpecimen class','edu.wustl.catissuecore.domain.CellSpecimen',NULL,NULL,1,'2005-01-01');
insert into `csm_protection_element` (`PROTECTION_ELEMENT_ID`,`PROTECTION_ELEMENT_NAME`,`PROTECTION_ELEMENT_DESCRIPTION`,`OBJECT_ID`,`ATTRIBUTE`,`PROTECTION_ELEMENT_TYPE`,`APPLICATION_ID`,`UPDATE_DATE`) values (19,'MolecularSpecimen','MolecularSpecimen class','edu.wustl.catissuecore.domain.MolecularSpecimen',NULL,NULL,1,'2005-01-01');
insert into `csm_protection_element` (`PROTECTION_ELEMENT_ID`,`PROTECTION_ELEMENT_NAME`,`PROTECTION_ELEMENT_DESCRIPTION`,`OBJECT_ID`,`ATTRIBUTE`,`PROTECTION_ELEMENT_TYPE`,`APPLICATION_ID`,`UPDATE_DATE`) values (20,'Biohazard','Biohazard class','edu.wustl.catissuecore.domain.Biohazard',NULL,NULL,1,'2005-01-01');
insert into `csm_protection_element` (`PROTECTION_ELEMENT_ID`,`PROTECTION_ELEMENT_NAME`,`PROTECTION_ELEMENT_DESCRIPTION`,`OBJECT_ID`,`ATTRIBUTE`,`PROTECTION_ELEMENT_TYPE`,`APPLICATION_ID`,`UPDATE_DATE`) values (21,'Specimen','Specimen class','edu.wustl.catissuecore.domain.Specimen',NULL,NULL,1,'2005-01-01');
insert into `csm_protection_element` (`PROTECTION_ELEMENT_ID`,`PROTECTION_ELEMENT_NAME`,`PROTECTION_ELEMENT_DESCRIPTION`,`OBJECT_ID`,`ATTRIBUTE`,`PROTECTION_ELEMENT_TYPE`,`APPLICATION_ID`,`UPDATE_DATE`) values (22,'ExternalIdentifier','ExternalIdentifier class','edu.wustl.catissuecore.domain.ExternalIdentifier',NULL,NULL,1,'2005-01-01');
insert into `csm_protection_element` (`PROTECTION_ELEMENT_ID`,`PROTECTION_ELEMENT_NAME`,`PROTECTION_ELEMENT_DESCRIPTION`,`OBJECT_ID`,`ATTRIBUTE`,`PROTECTION_ELEMENT_TYPE`,`APPLICATION_ID`,`UPDATE_DATE`) values (23,'EventParameters','EventParameters class','edu.wustl.catissuecore.domain.EventParameters',NULL,NULL,1,'2005-01-01');
insert into `csm_protection_element` (`PROTECTION_ELEMENT_ID`,`PROTECTION_ELEMENT_NAME`,`PROTECTION_ELEMENT_DESCRIPTION`,`OBJECT_ID`,`ATTRIBUTE`,`PROTECTION_ELEMENT_TYPE`,`APPLICATION_ID`,`UPDATE_DATE`) values (24,'FluidSpecimenReviewEventParameters','FluidSpecimenReviewEventParameters class','edu.wustl.catissuecore.domain.FluidSpecimenReviewEventParameters',NULL,NULL,1,'2005-01-01');
insert into `csm_protection_element` (`PROTECTION_ELEMENT_ID`,`PROTECTION_ELEMENT_NAME`,`PROTECTION_ELEMENT_DESCRIPTION`,`OBJECT_ID`,`ATTRIBUTE`,`PROTECTION_ELEMENT_TYPE`,`APPLICATION_ID`,`UPDATE_DATE`) values (25,'CellSpecimenReviewParameters','CellSpecimenReviewParameters class','edu.wustl.catissuecore.domain.CellSpecimenReviewParameters',NULL,NULL,1,'2005-01-01');
insert into `csm_protection_element` (`PROTECTION_ELEMENT_ID`,`PROTECTION_ELEMENT_NAME`,`PROTECTION_ELEMENT_DESCRIPTION`,`OBJECT_ID`,`ATTRIBUTE`,`PROTECTION_ELEMENT_TYPE`,`APPLICATION_ID`,`UPDATE_DATE`) values (26,'TissueSpecimenReviewEventParameters','TissueSpecimenReviewEventParameters class','edu.wustl.catissuecore.domain.TissueSpecimenReviewEventParameters',NULL,NULL,1,'2005-01-01');
insert into `csm_protection_element` (`PROTECTION_ELEMENT_ID`,`PROTECTION_ELEMENT_NAME`,`PROTECTION_ELEMENT_DESCRIPTION`,`OBJECT_ID`,`ATTRIBUTE`,`PROTECTION_ELEMENT_TYPE`,`APPLICATION_ID`,`UPDATE_DATE`) values (27,'MolecularSpecimenReviewParameters','MolecularSpecimenReviewParameters class','edu.wustl.catissuecore.domain.MolecularSpecimenReviewParameters',NULL,NULL,1,'2005-01-01');
insert into `csm_protection_element` (`PROTECTION_ELEMENT_ID`,`PROTECTION_ELEMENT_NAME`,`PROTECTION_ELEMENT_DESCRIPTION`,`OBJECT_ID`,`ATTRIBUTE`,`PROTECTION_ELEMENT_TYPE`,`APPLICATION_ID`,`UPDATE_DATE`) values (28,'CheckInCheckOutEventParameter','CheckInCheckOutEventParameter class','edu.wustl.catissuecore.domain.CheckInCheckOutEventParameter',NULL,NULL,1,'2005-01-01');
insert into `csm_protection_element` (`PROTECTION_ELEMENT_ID`,`PROTECTION_ELEMENT_NAME`,`PROTECTION_ELEMENT_DESCRIPTION`,`OBJECT_ID`,`ATTRIBUTE`,`PROTECTION_ELEMENT_TYPE`,`APPLICATION_ID`,`UPDATE_DATE`) values (29,'FrozenEventParameters','FrozenEventParameters class','edu.wustl.catissuecore.domain.FrozenEventParameters',NULL,NULL,1,'2005-01-01');
insert into `csm_protection_element` (`PROTECTION_ELEMENT_ID`,`PROTECTION_ELEMENT_NAME`,`PROTECTION_ELEMENT_DESCRIPTION`,`OBJECT_ID`,`ATTRIBUTE`,`PROTECTION_ELEMENT_TYPE`,`APPLICATION_ID`,`UPDATE_DATE`) values (30,'EmbeddedEventParameters','EmbeddedEventParameters class','edu.wustl.catissuecore.domain.EmbeddedEventParameters',NULL,NULL,1,'2005-01-01');
insert into `csm_protection_element` (`PROTECTION_ELEMENT_ID`,`PROTECTION_ELEMENT_NAME`,`PROTECTION_ELEMENT_DESCRIPTION`,`OBJECT_ID`,`ATTRIBUTE`,`PROTECTION_ELEMENT_TYPE`,`APPLICATION_ID`,`UPDATE_DATE`) values (31,'ReviewEventParameters','ReviewEventParameters class','edu.wustl.catissuecore.domain.ReviewEventParameters',NULL,NULL,1,'2005-01-01');
insert into `csm_protection_element` (`PROTECTION_ELEMENT_ID`,`PROTECTION_ELEMENT_NAME`,`PROTECTION_ELEMENT_DESCRIPTION`,`OBJECT_ID`,`ATTRIBUTE`,`PROTECTION_ELEMENT_TYPE`,`APPLICATION_ID`,`UPDATE_DATE`) values (32,'SpunEventParameters','SpunEventParameters class','edu.wustl.catissuecore.domain.SpunEventParameters',NULL,NULL,1,'2005-01-01');
insert into `csm_protection_element` (`PROTECTION_ELEMENT_ID`,`PROTECTION_ELEMENT_NAME`,`PROTECTION_ELEMENT_DESCRIPTION`,`OBJECT_ID`,`ATTRIBUTE`,`PROTECTION_ELEMENT_TYPE`,`APPLICATION_ID`,`UPDATE_DATE`) values (33,'ThawEventParameters','ThawEventParameters class','edu.wustl.catissuecore.domain.ThawEventParameters',NULL,NULL,1,'2005-01-01');
insert into `csm_protection_element` (`PROTECTION_ELEMENT_ID`,`PROTECTION_ELEMENT_NAME`,`PROTECTION_ELEMENT_DESCRIPTION`,`OBJECT_ID`,`ATTRIBUTE`,`PROTECTION_ELEMENT_TYPE`,`APPLICATION_ID`,`UPDATE_DATE`) values (34,'SpecimenEventParameters','SpecimenEventParameters class','edu.wustl.catissuecore.domain.SpecimenEventParameters',NULL,NULL,1,'2005-01-01');
insert into `csm_protection_element` (`PROTECTION_ELEMENT_ID`,`PROTECTION_ELEMENT_NAME`,`PROTECTION_ELEMENT_DESCRIPTION`,`OBJECT_ID`,`ATTRIBUTE`,`PROTECTION_ELEMENT_TYPE`,`APPLICATION_ID`,`UPDATE_DATE`) values (35,'ReceivedEventParameters','ReceivedEventParameters class','edu.wustl.catissuecore.domain.ReceivedEventParameters',NULL,NULL,1,'2005-01-01');
insert into `csm_protection_element` (`PROTECTION_ELEMENT_ID`,`PROTECTION_ELEMENT_NAME`,`PROTECTION_ELEMENT_DESCRIPTION`,`OBJECT_ID`,`ATTRIBUTE`,`PROTECTION_ELEMENT_TYPE`,`APPLICATION_ID`,`UPDATE_DATE`) values (36,'DisposalEventParameters','DisposalEventParameters class','edu.wustl.catissuecore.domain.DisposalEventParameters',NULL,NULL,1,'2005-01-01');
insert into `csm_protection_element` (`PROTECTION_ELEMENT_ID`,`PROTECTION_ELEMENT_NAME`,`PROTECTION_ELEMENT_DESCRIPTION`,`OBJECT_ID`,`ATTRIBUTE`,`PROTECTION_ELEMENT_TYPE`,`APPLICATION_ID`,`UPDATE_DATE`) values (37,'FixedEventParameters','FixedEventParameters class','edu.wustl.catissuecore.domain.FixedEventParameters',NULL,NULL,1,'2005-01-01');
insert into `csm_protection_element` (`PROTECTION_ELEMENT_ID`,`PROTECTION_ELEMENT_NAME`,`PROTECTION_ELEMENT_DESCRIPTION`,`OBJECT_ID`,`ATTRIBUTE`,`PROTECTION_ELEMENT_TYPE`,`APPLICATION_ID`,`UPDATE_DATE`) values (38,'ProcedureEventParameters','ProcedureEventParameters class','edu.wustl.catissuecore.domain.ProcedureEventParameters',NULL,NULL,1,'2005-01-01');
insert into `csm_protection_element` (`PROTECTION_ELEMENT_ID`,`PROTECTION_ELEMENT_NAME`,`PROTECTION_ELEMENT_DESCRIPTION`,`OBJECT_ID`,`ATTRIBUTE`,`PROTECTION_ELEMENT_TYPE`,`APPLICATION_ID`,`UPDATE_DATE`) values (39,'TransferEventParameters','TransferEventParameters class','edu.wustl.catissuecore.domain.TransferEventParameters',NULL,NULL,1,'2005-01-01');
insert into `csm_protection_element` (`PROTECTION_ELEMENT_ID`,`PROTECTION_ELEMENT_NAME`,`PROTECTION_ELEMENT_DESCRIPTION`,`OBJECT_ID`,`ATTRIBUTE`,`PROTECTION_ELEMENT_TYPE`,`APPLICATION_ID`,`UPDATE_DATE`) values (40,'CollectionEventParameters','CollectionEventParameters class','edu.wustl.catissuecore.domain.CollectionEventParameters',NULL,NULL,1,'2005-01-01');
insert into `csm_protection_element` (`PROTECTION_ELEMENT_ID`,`PROTECTION_ELEMENT_NAME`,`PROTECTION_ELEMENT_DESCRIPTION`,`OBJECT_ID`,`ATTRIBUTE`,`PROTECTION_ELEMENT_TYPE`,`APPLICATION_ID`,`UPDATE_DATE`) values (41,'Site','Site class','edu.wustl.catissuecore.domain.Site',NULL,NULL,1,'2005-01-01');
insert into `csm_protection_element` (`PROTECTION_ELEMENT_ID`,`PROTECTION_ELEMENT_NAME`,`PROTECTION_ELEMENT_DESCRIPTION`,`OBJECT_ID`,`ATTRIBUTE`,`PROTECTION_ELEMENT_TYPE`,`APPLICATION_ID`,`UPDATE_DATE`) values (42,'StorageContainer','StorageContainer class','edu.wustl.catissuecore.domain.StorageContainer',NULL,NULL,1,'2005-01-01');
insert into `csm_protection_element` (`PROTECTION_ELEMENT_ID`,`PROTECTION_ELEMENT_NAME`,`PROTECTION_ELEMENT_DESCRIPTION`,`OBJECT_ID`,`ATTRIBUTE`,`PROTECTION_ELEMENT_TYPE`,`APPLICATION_ID`,`UPDATE_DATE`) values (43,'StorageContainerDetails','StorageContainerDetails class','edu.wustl.catissuecore.domain.StorageContainerDetails',NULL,NULL,1,'2005-01-01');
insert into `csm_protection_element` (`PROTECTION_ELEMENT_ID`,`PROTECTION_ELEMENT_NAME`,`PROTECTION_ELEMENT_DESCRIPTION`,`OBJECT_ID`,`ATTRIBUTE`,`PROTECTION_ELEMENT_TYPE`,`APPLICATION_ID`,`UPDATE_DATE`) values (44,'Capacity','Capacity class','edu.wustl.catissuecore.domain.Capacity',NULL,NULL,1,'2005-01-01');
insert into `csm_protection_element` (`PROTECTION_ELEMENT_ID`,`PROTECTION_ELEMENT_NAME`,`PROTECTION_ELEMENT_DESCRIPTION`,`OBJECT_ID`,`ATTRIBUTE`,`PROTECTION_ELEMENT_TYPE`,`APPLICATION_ID`,`UPDATE_DATE`) values (45,'StorageType','StorageType class','edu.wustl.catissuecore.domain.StorageType',NULL,NULL,1,'2005-01-01');
insert into `csm_protection_element` (`PROTECTION_ELEMENT_ID`,`PROTECTION_ELEMENT_NAME`,`PROTECTION_ELEMENT_DESCRIPTION`,`OBJECT_ID`,`ATTRIBUTE`,`PROTECTION_ELEMENT_TYPE`,`APPLICATION_ID`,`UPDATE_DATE`) values (46,'User','User class','edu.wustl.catissuecore.domain.User',NULL,NULL,1,'2005-01-01');
insert into `csm_protection_element` (`PROTECTION_ELEMENT_ID`,`PROTECTION_ELEMENT_NAME`,`PROTECTION_ELEMENT_DESCRIPTION`,`OBJECT_ID`,`ATTRIBUTE`,`PROTECTION_ELEMENT_TYPE`,`APPLICATION_ID`,`UPDATE_DATE`) values (47,'Address','Address class','edu.wustl.catissuecore.domain.Address',NULL,NULL,1,'2005-01-01');
insert into `csm_protection_element` (`PROTECTION_ELEMENT_ID`,`PROTECTION_ELEMENT_NAME`,`PROTECTION_ELEMENT_DESCRIPTION`,`OBJECT_ID`,`ATTRIBUTE`,`PROTECTION_ELEMENT_TYPE`,`APPLICATION_ID`,`UPDATE_DATE`) values (48,'CancerResearchGroup','CancerResearchGroup class','edu.wustl.catissuecore.domain.CancerResearchGroup',NULL,NULL,1,'2005-01-01');
insert into `csm_protection_element` (`PROTECTION_ELEMENT_ID`,`PROTECTION_ELEMENT_NAME`,`PROTECTION_ELEMENT_DESCRIPTION`,`OBJECT_ID`,`ATTRIBUTE`,`PROTECTION_ELEMENT_TYPE`,`APPLICATION_ID`,`UPDATE_DATE`) values (50,'Department','Department class','edu.wustl.catissuecore.domain.Department',NULL,NULL,1,'2005-01-01');
insert into `csm_protection_element` (`PROTECTION_ELEMENT_ID`,`PROTECTION_ELEMENT_NAME`,`PROTECTION_ELEMENT_DESCRIPTION`,`OBJECT_ID`,`ATTRIBUTE`,`PROTECTION_ELEMENT_TYPE`,`APPLICATION_ID`,`UPDATE_DATE`) values (51,'Institution','Institution class','edu.wustl.catissuecore.domain.Institution',NULL,NULL,1,'2005-01-01');
insert into `csm_protection_element` (`PROTECTION_ELEMENT_ID`,`PROTECTION_ELEMENT_NAME`,`PROTECTION_ELEMENT_DESCRIPTION`,`OBJECT_ID`,`ATTRIBUTE`,`PROTECTION_ELEMENT_TYPE`,`APPLICATION_ID`,`UPDATE_DATE`) values (52,'Distribution','Distribution class','edu.wustl.catissuecore.domain.Distribution',NULL,NULL,1,'2005-01-01');
insert into `csm_protection_element` (`PROTECTION_ELEMENT_ID`,`PROTECTION_ELEMENT_NAME`,`PROTECTION_ELEMENT_DESCRIPTION`,`OBJECT_ID`,`ATTRIBUTE`,`PROTECTION_ELEMENT_TYPE`,`APPLICATION_ID`,`UPDATE_DATE`) values (53,'DistributedItem','DistributedItem class','edu.wustl.catissuecore.domain.DistributedItem',NULL,NULL,1,'2005-01-01');
insert into `csm_protection_element` (`PROTECTION_ELEMENT_ID`,`PROTECTION_ELEMENT_NAME`,`PROTECTION_ELEMENT_DESCRIPTION`,`OBJECT_ID`,`ATTRIBUTE`,`PROTECTION_ELEMENT_TYPE`,`APPLICATION_ID`,`UPDATE_DATE`) values (54,'CollectionProtocolEvent','CollectionProtocolEvent class','edu.wustl.catissuecore.domain.CollectionProtocolEvent',NULL,NULL,1,'2005-01-01');
insert into `csm_protection_element` (`PROTECTION_ELEMENT_ID`,`PROTECTION_ELEMENT_NAME`,`PROTECTION_ELEMENT_DESCRIPTION`,`OBJECT_ID`,`ATTRIBUTE`,`PROTECTION_ELEMENT_TYPE`,`APPLICATION_ID`,`UPDATE_DATE`) values (55,'CollectionProtocol','CollectionProtocol class','edu.wustl.catissuecore.domain.CollectionProtocol',NULL,NULL,1,'2005-01-01');
insert into `csm_protection_element` (`PROTECTION_ELEMENT_ID`,`PROTECTION_ELEMENT_NAME`,`PROTECTION_ELEMENT_DESCRIPTION`,`OBJECT_ID`,`ATTRIBUTE`,`PROTECTION_ELEMENT_TYPE`,`APPLICATION_ID`,`UPDATE_DATE`) values (56,'DistributionProtocol','DistributionProtocol class','edu.wustl.catissuecore.domain.DistributionProtocol',NULL,NULL,1,'2005-01-01');
insert into `csm_protection_element` (`PROTECTION_ELEMENT_ID`,`PROTECTION_ELEMENT_NAME`,`PROTECTION_ELEMENT_DESCRIPTION`,`OBJECT_ID`,`ATTRIBUTE`,`PROTECTION_ELEMENT_TYPE`,`APPLICATION_ID`,`UPDATE_DATE`) values (57,'SpecimenProtocol','SpecimenProtocol class','edu.wustl.catissuecore.domain.SpecimenProtocol',NULL,NULL,1,'2005-01-01');
insert into `csm_protection_element` (`PROTECTION_ELEMENT_ID`,`PROTECTION_ELEMENT_NAME`,`PROTECTION_ELEMENT_DESCRIPTION`,`OBJECT_ID`,`ATTRIBUTE`,`PROTECTION_ELEMENT_TYPE`,`APPLICATION_ID`,`UPDATE_DATE`) values (269,'edu.wustl.catissuecore.domain.ReportedProblem','edu.wustl.catissuecore.domain.ReportedProblem','edu.wustl.catissuecore.domain.ReportedProblem',NULL,NULL,1,'2005-08-31');
insert into `csm_protection_element` (`PROTECTION_ELEMENT_ID`,`PROTECTION_ELEMENT_NAME`,`PROTECTION_ELEMENT_DESCRIPTION`,`OBJECT_ID`,`ATTRIBUTE`,`PROTECTION_ELEMENT_TYPE`,`APPLICATION_ID`,`UPDATE_DATE`) values (272,'edu.wustl.catissuecore.domain.SignUpUser','edu.wustl.catissuecore.domain.SignUpUser','edu.wustl.catissuecore.domain.SignUpUser',NULL,NULL,1,'2005-08-31');
insert into `csm_protection_element` (`PROTECTION_ELEMENT_ID`,`PROTECTION_ELEMENT_NAME`,`PROTECTION_ELEMENT_DESCRIPTION`,`OBJECT_ID`,`ATTRIBUTE`,`PROTECTION_ELEMENT_TYPE`,`APPLICATION_ID`,`UPDATE_DATE`) values (276,'SpecimenArrayType','SpecimenArrayType Class','edu.wustl.catissuecore.domain.SpecimenArrayType',NULL,NULL,1,'2006-08-31');
insert into `csm_protection_element` (`PROTECTION_ELEMENT_ID`,`PROTECTION_ELEMENT_NAME`,`PROTECTION_ELEMENT_DESCRIPTION`,`OBJECT_ID`,`ATTRIBUTE`,`PROTECTION_ELEMENT_TYPE`,`APPLICATION_ID`,`UPDATE_DATE`) values (281,'SpecimenArray','SpecimenArray Class','edu.wustl.catissuecore.domain.SpecimenArray',NULL,NULL,1,'2006-08-31');
insert into `csm_protection_element` (`PROTECTION_ELEMENT_ID`,`PROTECTION_ELEMENT_NAME`,`PROTECTION_ELEMENT_DESCRIPTION`,`OBJECT_ID`,`ATTRIBUTE`,`PROTECTION_ELEMENT_TYPE`,`APPLICATION_ID`,`UPDATE_DATE`) values (294,'Local Extensions','Local Extensions class','edu.common.dynamicextensions.domain.integration.EntityMap',NULL,NULL,1,'2007-01-17');
insert into `csm_protection_element` (`PROTECTION_ELEMENT_ID`,`PROTECTION_ELEMENT_NAME`,`PROTECTION_ELEMENT_DESCRIPTION`,`OBJECT_ID`,`ATTRIBUTE`,`PROTECTION_ELEMENT_TYPE`,`APPLICATION_ID`,`UPDATE_DATE`) values (300,'Consent Tier','ConsentTier Object','edu.wustl.catissuecore.domain.ConsentTier',NULL,NULL,1,'2006-11-27');
insert into `csm_protection_element` (`PROTECTION_ELEMENT_ID`,`PROTECTION_ELEMENT_NAME`,`PROTECTION_ELEMENT_DESCRIPTION`,`OBJECT_ID`,`ATTRIBUTE`,`PROTECTION_ELEMENT_TYPE`,`APPLICATION_ID`,`UPDATE_DATE`) values (301,'Consent Tier Response','Consent Tier Response Object','edu.wustl.catissuecore.domain.ConsentTierResponse',NULL,NULL,1,'2006-11-27');
insert into `csm_protection_element` (`PROTECTION_ELEMENT_ID`,`PROTECTION_ELEMENT_NAME`,`PROTECTION_ELEMENT_DESCRIPTION`,`OBJECT_ID`,`ATTRIBUTE`,`PROTECTION_ELEMENT_TYPE`,`APPLICATION_ID`,`UPDATE_DATE`) values (302,'Consent Tier Status','Consent Tier Status Object','edu.wustl.catissuecore.domain.ConsentTierStatus',NULL,NULL,1,'2006-11-27');
insert into `csm_protection_element` (`PROTECTION_ELEMENT_ID`,`PROTECTION_ELEMENT_NAME`,`PROTECTION_ELEMENT_DESCRIPTION`,`OBJECT_ID`,`ATTRIBUTE`,`PROTECTION_ELEMENT_TYPE`,`APPLICATION_ID`,`UPDATE_DATE`) values (303,'ReturnEventParameters','ReturnEventParameters Class','edu.wustl.catissuecore.domain.ReturnEventParameters',NULL,NULL,1,'2007-01-17');
insert into `csm_protection_element` (`PROTECTION_ELEMENT_ID`,`PROTECTION_ELEMENT_NAME`,`PROTECTION_ELEMENT_DESCRIPTION`,`OBJECT_ID`,`ATTRIBUTE`,`PROTECTION_ELEMENT_TYPE`,`APPLICATION_ID`,`UPDATE_DATE`) values (304,'Order','Order Object','edu.wustl.catissuecore.domain.OrderDetails',NULL,NULL,1,'2006-11-27');
insert into `csm_protection_element` (`PROTECTION_ELEMENT_ID`,`PROTECTION_ELEMENT_NAME`,`PROTECTION_ELEMENT_DESCRIPTION`,`OBJECT_ID`,`ATTRIBUTE`,`PROTECTION_ELEMENT_TYPE`,`APPLICATION_ID`,`UPDATE_DATE`) values (305,'OrderItem','OrderItem Object','edu.wustl.catissuecore.domain.OrderItem',NULL,NULL,1,'2006-11-27');
insert into `csm_protection_element` (`PROTECTION_ELEMENT_ID`,`PROTECTION_ELEMENT_NAME`,`PROTECTION_ELEMENT_DESCRIPTION`,`OBJECT_ID`,`ATTRIBUTE`,`PROTECTION_ELEMENT_TYPE`,`APPLICATION_ID`,`UPDATE_DATE`) values (306,'Derived Specimen Order Item','Derived Specimen Order Item Object','edu.wustl.catissuecore.domain.DerivedSpecimenOrderItem',NULL,NULL,1,'2006-11-27');
insert into `csm_protection_element` (`PROTECTION_ELEMENT_ID`,`PROTECTION_ELEMENT_NAME`,`PROTECTION_ELEMENT_DESCRIPTION`,`OBJECT_ID`,`ATTRIBUTE`,`PROTECTION_ELEMENT_TYPE`,`APPLICATION_ID`,`UPDATE_DATE`) values (307,'Existing Specimen Array Order Item','Existing Specimen Array Order Item Object','edu.wustl.catissuecore.domain.ExistingSpecimenArrayOrderItem',NULL,NULL,1,'2006-11-27');
insert into `csm_protection_element` (`PROTECTION_ELEMENT_ID`,`PROTECTION_ELEMENT_NAME`,`PROTECTION_ELEMENT_DESCRIPTION`,`OBJECT_ID`,`ATTRIBUTE`,`PROTECTION_ELEMENT_TYPE`,`APPLICATION_ID`,`UPDATE_DATE`) values (308,'Existing Specimen Order Item','Existing Specimen Order Item Object','edu.wustl.catissuecore.domain.ExistingSpecimenOrderItem',NULL,NULL,1,'2006-11-27');
insert into `csm_protection_element` (`PROTECTION_ELEMENT_ID`,`PROTECTION_ELEMENT_NAME`,`PROTECTION_ELEMENT_DESCRIPTION`,`OBJECT_ID`,`ATTRIBUTE`,`PROTECTION_ELEMENT_TYPE`,`APPLICATION_ID`,`UPDATE_DATE`) values (309,'New Specimen Array Order Item','New Specimen Array Order Item Object','edu.wustl.catissuecore.domain.NewSpecimenArrayOrderItem',NULL,NULL,1,'2006-11-27');
insert into `csm_protection_element` (`PROTECTION_ELEMENT_ID`,`PROTECTION_ELEMENT_NAME`,`PROTECTION_ELEMENT_DESCRIPTION`,`OBJECT_ID`,`ATTRIBUTE`,`PROTECTION_ELEMENT_TYPE`,`APPLICATION_ID`,`UPDATE_DATE`) values (310,'New Specimen Order Item','New Specimen Order Item Object','edu.wustl.catissuecore.domain.NewSpecimenOrderItem',NULL,NULL,1,'2006-11-27');
insert into `csm_protection_element` (`PROTECTION_ELEMENT_ID`,`PROTECTION_ELEMENT_NAME`,`PROTECTION_ELEMENT_DESCRIPTION`,`OBJECT_ID`,`ATTRIBUTE`,`PROTECTION_ELEMENT_TYPE`,`APPLICATION_ID`,`UPDATE_DATE`) values (311,'Pathological Case Order Item','Pathological Case Order Item Object','edu.wustl.catissuecore.domain.PathologicalCaseOrderItem',NULL,NULL,1,'2006-11-27');
insert into `csm_protection_element` (`PROTECTION_ELEMENT_ID`,`PROTECTION_ELEMENT_NAME`,`PROTECTION_ELEMENT_DESCRIPTION`,`OBJECT_ID`,`ATTRIBUTE`,`PROTECTION_ELEMENT_TYPE`,`APPLICATION_ID`,`UPDATE_DATE`) values (312,'Specimen Array Order Item','Specimen Array Order Item Object','edu.wustl.catissuecore.domain.SpecimenArrayOrderItem',NULL,NULL,1,'2006-11-27');
insert into `csm_protection_element` (`PROTECTION_ELEMENT_ID`,`PROTECTION_ELEMENT_NAME`,`PROTECTION_ELEMENT_DESCRIPTION`,`OBJECT_ID`,`ATTRIBUTE`,`PROTECTION_ELEMENT_TYPE`,`APPLICATION_ID`,`UPDATE_DATE`) values (313,'Specimen Order Item','Specimen Order Item Object','edu.wustl.catissuecore.domain.SpecimenOrderItem',NULL,NULL,1,'2006-11-27');
insert into `csm_protection_element` (`PROTECTION_ELEMENT_ID`,`PROTECTION_ELEMENT_NAME`,`PROTECTION_ELEMENT_DESCRIPTION`,`OBJECT_ID`,`ATTRIBUTE`,`PROTECTION_ELEMENT_TYPE`,`APPLICATION_ID`,`UPDATE_DATE`) values (315,'IdentifiedSurgicalPathologyReport','IdentifiedSurgicalPathologyReport Object','edu.wustl.catissuecore.domain.pathology.IdentifiedSurgicalPathologyReport',NULL,NULL,1,'2006-11-27');
insert into `csm_protection_element` (`PROTECTION_ELEMENT_ID`,`PROTECTION_ELEMENT_NAME`,`PROTECTION_ELEMENT_DESCRIPTION`,`OBJECT_ID`,`ATTRIBUTE`,`PROTECTION_ELEMENT_TYPE`,`APPLICATION_ID`,`UPDATE_DATE`) values (316,'DeidentifiedSurgicalPathologyReport','DeidentifiedSurgicalPathologyReport Object','edu.wustl.catissuecore.domain.pathology.DeidentifiedSurgicalPathologyReport',NULL,NULL,1,'2006-11-27');
insert into `csm_protection_element` (`PROTECTION_ELEMENT_ID`,`PROTECTION_ELEMENT_NAME`,`PROTECTION_ELEMENT_DESCRIPTION`,`OBJECT_ID`,`ATTRIBUTE`,`PROTECTION_ELEMENT_TYPE`,`APPLICATION_ID`,`UPDATE_DATE`) values (317,'ReportLoaderQueue','ReportLoaderQueue Object','edu.wustl.catissuecore.domain.pathology.ReportLoaderQueue',NULL,NULL,1,'2006-11-27');
insert into `csm_protection_element` (`PROTECTION_ELEMENT_ID`,`PROTECTION_ELEMENT_NAME`,`PROTECTION_ELEMENT_DESCRIPTION`,`OBJECT_ID`,`ATTRIBUTE`,`PROTECTION_ELEMENT_TYPE`,`APPLICATION_ID`,`UPDATE_DATE`) values (318,'Review Comments','PathologyReportReviewParameter Object','edu.wustl.catissuecore.domain.pathology.PathologyReportReviewParameter',NULL,NULL,1,'2006-11-27');
insert into `csm_protection_element` (`PROTECTION_ELEMENT_ID`,`PROTECTION_ELEMENT_NAME`,`PROTECTION_ELEMENT_DESCRIPTION`,`OBJECT_ID`,`ATTRIBUTE`,`PROTECTION_ELEMENT_TYPE`,`APPLICATION_ID`,`UPDATE_DATE`) values (319,'Quarantine Comments','QuarantineEventParameter Object','edu.wustl.catissuecore.domain.pathology.QuarantineEventParameter',NULL,NULL,1,'2006-11-27');
insert into `csm_protection_element` (`PROTECTION_ELEMENT_ID`,`PROTECTION_ELEMENT_NAME`,`PROTECTION_ELEMENT_DESCRIPTION`,`OBJECT_ID`,`ATTRIBUTE`,`PROTECTION_ELEMENT_TYPE`,`APPLICATION_ID`,`UPDATE_DATE`) values (326,'AbstractPosition','AbstractPosition Object','edu.wustl.catissuecore.domain.AbstractPosition',NULL,NULL,1,'2008-05-28');
insert into `csm_protection_element` (`PROTECTION_ELEMENT_ID`,`PROTECTION_ELEMENT_NAME`,`PROTECTION_ELEMENT_DESCRIPTION`,`OBJECT_ID`,`ATTRIBUTE`,`PROTECTION_ELEMENT_TYPE`,`APPLICATION_ID`,`UPDATE_DATE`) values (327,'SpecimenPosition','SpecimenPosition Object','edu.wustl.catissuecore.domain.SpecimenPosition',NULL,NULL,1,'2008-05-28');
insert into `csm_protection_element` (`PROTECTION_ELEMENT_ID`,`PROTECTION_ELEMENT_NAME`,`PROTECTION_ELEMENT_DESCRIPTION`,`OBJECT_ID`,`ATTRIBUTE`,`PROTECTION_ELEMENT_TYPE`,`APPLICATION_ID`,`UPDATE_DATE`) values (328,'ContainerPosition','ContainerPosition Object','edu.wustl.catissuecore.domain.ContainerPosition',NULL,NULL,1,'2008-05-28');
insert into `csm_protection_element` (`PROTECTION_ELEMENT_ID`,`PROTECTION_ELEMENT_NAME`,`PROTECTION_ELEMENT_DESCRIPTION`,`OBJECT_ID`,`ATTRIBUTE`,`PROTECTION_ELEMENT_TYPE`,`APPLICATION_ID`,`UPDATE_DATE`) values (329,'AbstractSpecimen','AbstractSpecimen Object','edu.wustl.catissuecore.domain.AbstractSpecimen',NULL,NULL,1,'2008-05-28');
insert into `csm_protection_element` (`PROTECTION_ELEMENT_ID`,`PROTECTION_ELEMENT_NAME`,`PROTECTION_ELEMENT_DESCRIPTION`,`OBJECT_ID`,`ATTRIBUTE`,`PROTECTION_ELEMENT_TYPE`,`APPLICATION_ID`,`UPDATE_DATE`) values (330,'SpecimenRequirement','SpecimenRequirement Object','edu.wustl.catissuecore.domain.SpecimenRequirement',NULL,NULL,1,'2008-05-28');
insert into `csm_protection_element` (`PROTECTION_ELEMENT_ID`,`PROTECTION_ELEMENT_NAME`,`PROTECTION_ELEMENT_DESCRIPTION`,`OBJECT_ID`,`ATTRIBUTE`,`PROTECTION_ELEMENT_TYPE`,`APPLICATION_ID`,`UPDATE_DATE`) values (331,'MolecularSpecimenRequirement','MolecularSpecimenRequirement Object','edu.wustl.catissuecore.domain.MolecularSpecimenRequirement',NULL,NULL,1,'2008-05-28');
insert into `csm_protection_element` (`PROTECTION_ELEMENT_ID`,`PROTECTION_ELEMENT_NAME`,`PROTECTION_ELEMENT_DESCRIPTION`,`OBJECT_ID`,`ATTRIBUTE`,`PROTECTION_ELEMENT_TYPE`,`APPLICATION_ID`,`UPDATE_DATE`) values (332,'FluidSpecimenRequirement','FluidSpecimenRequirement Object','edu.wustl.catissuecore.domain.FluidSpecimenRequirement',NULL,NULL,1,'2008-05-28');
insert into `csm_protection_element` (`PROTECTION_ELEMENT_ID`,`PROTECTION_ELEMENT_NAME`,`PROTECTION_ELEMENT_DESCRIPTION`,`OBJECT_ID`,`ATTRIBUTE`,`PROTECTION_ELEMENT_TYPE`,`APPLICATION_ID`,`UPDATE_DATE`) values (333,'CellSpecimenRequirement','CellSpecimenRequirement Object','edu.wustl.catissuecore.domain.CellSpecimenRequirement',NULL,NULL,1,'2008-05-28');
insert into `csm_protection_element` (`PROTECTION_ELEMENT_ID`,`PROTECTION_ELEMENT_NAME`,`PROTECTION_ELEMENT_DESCRIPTION`,`OBJECT_ID`,`ATTRIBUTE`,`PROTECTION_ELEMENT_TYPE`,`APPLICATION_ID`,`UPDATE_DATE`) values (334,'TissueSpecimenRequirement','TissueSpecimenRequirement Object','edu.wustl.catissuecore.domain.TissueSpecimenRequirement',NULL,NULL,1,'2008-05-28');
insert into `csm_protection_element` (`PROTECTION_ELEMENT_ID`,`PROTECTION_ELEMENT_NAME`,`PROTECTION_ELEMENT_DESCRIPTION`,`OBJECT_ID`,`ATTRIBUTE`,`PROTECTION_ELEMENT_TYPE`,`APPLICATION_ID`,`UPDATE_DATE`) values (335,'DistributionSpecimenRequirement','DistributionSpecimenRequirement Object','edu.wustl.catissuecore.domain.DistributionSpecimenRequirement',NULL,NULL,1,'2008-05-28');
insert into `csm_protection_element` (`PROTECTION_ELEMENT_ID`,`PROTECTION_ELEMENT_NAME`,`PROTECTION_ELEMENT_DESCRIPTION`,`OBJECT_ID`,`ATTRIBUTE`,`PROTECTION_ELEMENT_TYPE`,`APPLICATION_ID`,`UPDATE_DATE`) values (336,'ADMIN_PROTECTION_ELEMENT','ADMIN_PROTECTION_ELEMENT Object','ADMIN_PROTECTION_ELEMENT',NULL,NULL,1,'2008-07-22');

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

insert into `csm_protection_group` (`PROTECTION_GROUP_ID`,`PROTECTION_GROUP_NAME`,`PROTECTION_GROUP_DESCRIPTION`,`APPLICATION_ID`,`LARGE_ELEMENT_COUNT_FLAG`,`UPDATE_DATE`,`PARENT_PROTECTION_GROUP_ID`) values (1,'ADMINISTRATOR_PROTECTION_GROUP','Protection elements for class names of objects that belong to Administrative data',1,0,'2005-01-01',NULL);
insert into `csm_protection_group` (`PROTECTION_GROUP_ID`,`PROTECTION_GROUP_NAME`,`PROTECTION_GROUP_DESCRIPTION`,`APPLICATION_ID`,`LARGE_ELEMENT_COUNT_FLAG`,`UPDATE_DATE`,`PARENT_PROTECTION_GROUP_ID`) values (2,'SUPERVISOR_PROTECTION_GROUP','Protection elements for class names of objects that belong to Supervisor\'s data',1,0,'2005-01-01',NULL);
insert into `csm_protection_group` (`PROTECTION_GROUP_ID`,`PROTECTION_GROUP_NAME`,`PROTECTION_GROUP_DESCRIPTION`,`APPLICATION_ID`,`LARGE_ELEMENT_COUNT_FLAG`,`UPDATE_DATE`,`PARENT_PROTECTION_GROUP_ID`) values (3,'TECHNICIAN_PROTECTION_GROUP','Protection elements for class names of objects that belong to Technician data',1,0,'2005-01-01',NULL);
insert into `csm_protection_group` (`PROTECTION_GROUP_ID`,`PROTECTION_GROUP_NAME`,`PROTECTION_GROUP_DESCRIPTION`,`APPLICATION_ID`,`LARGE_ELEMENT_COUNT_FLAG`,`UPDATE_DATE`,`PARENT_PROTECTION_GROUP_ID`) values (17,'SECURED_ADMINISTRATIVE_ACTIONS',NULL,1,0,'2005-01-01',NULL);
insert into `csm_protection_group` (`PROTECTION_GROUP_ID`,`PROTECTION_GROUP_NAME`,`PROTECTION_GROUP_DESCRIPTION`,`APPLICATION_ID`,`LARGE_ELEMENT_COUNT_FLAG`,`UPDATE_DATE`,`PARENT_PROTECTION_GROUP_ID`) values (18,'SECURED_SUPERVISORY_ACTIONS',NULL,1,0,'2005-01-01',NULL);
insert into `csm_protection_group` (`PROTECTION_GROUP_ID`,`PROTECTION_GROUP_NAME`,`PROTECTION_GROUP_DESCRIPTION`,`APPLICATION_ID`,`LARGE_ELEMENT_COUNT_FLAG`,`UPDATE_DATE`,`PARENT_PROTECTION_GROUP_ID`) values (19,'SECURED_TECHNICIAN_ACTIONS',NULL,1,0,'2005-01-01',NULL);
insert into `csm_protection_group` (`PROTECTION_GROUP_ID`,`PROTECTION_GROUP_NAME`,`PROTECTION_GROUP_DESCRIPTION`,`APPLICATION_ID`,`LARGE_ELEMENT_COUNT_FLAG`,`UPDATE_DATE`,`PARENT_PROTECTION_GROUP_ID`) values (20,'PUBLIC_DATA_GROUP',NULL,1,0,'2005-01-01',NULL);
insert into `csm_protection_group` (`PROTECTION_GROUP_ID`,`PROTECTION_GROUP_NAME`,`PROTECTION_GROUP_DESCRIPTION`,`APPLICATION_ID`,`LARGE_ELEMENT_COUNT_FLAG`,`UPDATE_DATE`,`PARENT_PROTECTION_GROUP_ID`) values (21,'ADMINISTRATORS_DATA_GROUP',NULL,1,0,'2005-01-01',NULL);
insert into `csm_protection_group` (`PROTECTION_GROUP_ID`,`PROTECTION_GROUP_NAME`,`PROTECTION_GROUP_DESCRIPTION`,`APPLICATION_ID`,`LARGE_ELEMENT_COUNT_FLAG`,`UPDATE_DATE`,`PARENT_PROTECTION_GROUP_ID`) values (22,'SUPERVISORS_DATA_GROUP',NULL,1,0,'2005-01-01',NULL);
insert into `csm_protection_group` (`PROTECTION_GROUP_ID`,`PROTECTION_GROUP_NAME`,`PROTECTION_GROUP_DESCRIPTION`,`APPLICATION_ID`,`LARGE_ELEMENT_COUNT_FLAG`,`UPDATE_DATE`,`PARENT_PROTECTION_GROUP_ID`) values (23,'TECHNICIANS_DATA_GROUP',NULL,1,0,'2005-01-01',NULL);
insert into `csm_protection_group` (`PROTECTION_GROUP_ID`,`PROTECTION_GROUP_NAME`,`PROTECTION_GROUP_DESCRIPTION`,`APPLICATION_ID`,`LARGE_ELEMENT_COUNT_FLAG`,`UPDATE_DATE`,`PARENT_PROTECTION_GROUP_ID`) values (24,'SECURED_PUBLIC_ACTIONS',NULL,1,0,'2005-01-01',NULL);
insert into `csm_protection_group` (`PROTECTION_GROUP_ID`,`PROTECTION_GROUP_NAME`,`PROTECTION_GROUP_DESCRIPTION`,`APPLICATION_ID`,`LARGE_ELEMENT_COUNT_FLAG`,`UPDATE_DATE`,`PARENT_PROTECTION_GROUP_ID`) values (44,'SCIENTIST_PROTECTION_GROUP',NULL,1,0,'2005-01-01',NULL);

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

insert into `csm_role` (`ROLE_ID`,`ROLE_NAME`,`ROLE_DESCRIPTION`,`APPLICATION_ID`,`ACTIVE_FLAG`,`UPDATE_DATE`) values (1,'Administrator','Role for Administrator',1,0,'2005-01-01');
insert into `csm_role` (`ROLE_ID`,`ROLE_NAME`,`ROLE_DESCRIPTION`,`APPLICATION_ID`,`ACTIVE_FLAG`,`UPDATE_DATE`) values (2,'Supervisor','Role for Supervisor',1,0,'2005-01-01');
insert into `csm_role` (`ROLE_ID`,`ROLE_NAME`,`ROLE_DESCRIPTION`,`APPLICATION_ID`,`ACTIVE_FLAG`,`UPDATE_DATE`) values (3,'Technician','Role for Technician',1,0,'2005-01-01');
insert into `csm_role` (`ROLE_ID`,`ROLE_NAME`,`ROLE_DESCRIPTION`,`APPLICATION_ID`,`ACTIVE_FLAG`,`UPDATE_DATE`) values (4,'PI','Role for Principal Investigator',1,0,'2005-01-01');
insert into `csm_role` (`ROLE_ID`,`ROLE_NAME`,`ROLE_DESCRIPTION`,`APPLICATION_ID`,`ACTIVE_FLAG`,`UPDATE_DATE`) values (5,'READ_ONLY','Read Only Role',1,0,'2005-01-01');
insert into `csm_role` (`ROLE_ID`,`ROLE_NAME`,`ROLE_DESCRIPTION`,`APPLICATION_ID`,`ACTIVE_FLAG`,`UPDATE_DATE`) values (6,'USE_ONLY','Use Only Role',1,0,'2005-01-01');
insert into `csm_role` (`ROLE_ID`,`ROLE_NAME`,`ROLE_DESCRIPTION`,`APPLICATION_ID`,`ACTIVE_FLAG`,`UPDATE_DATE`) values (7,'Scientist','Role for Public',1,0,'2005-01-01');
insert into `csm_role` (`ROLE_ID`,`ROLE_NAME`,`ROLE_DESCRIPTION`,`APPLICATION_ID`,`ACTIVE_FLAG`,`UPDATE_DATE`) values (8,'UPDATE_ONLY','Update Only Role',1,0,'2005-01-01');
insert into `csm_role` (`ROLE_ID`,`ROLE_NAME`,`ROLE_DESCRIPTION`,`APPLICATION_ID`,`ACTIVE_FLAG`,`UPDATE_DATE`) values (9,'EXECUTE_ONLY','Execute Only Role',1,0,'2005-01-01');
insert into `csm_role` (`ROLE_ID`,`ROLE_NAME`,`ROLE_DESCRIPTION`,`APPLICATION_ID`,`ACTIVE_FLAG`,`UPDATE_DATE`) values (10,'READ_DENIED','Read Denied Role',1,0,'2005-01-01');
insert into `csm_role` (`ROLE_ID`,`ROLE_NAME`,`ROLE_DESCRIPTION`,`APPLICATION_ID`,`ACTIVE_FLAG`,`UPDATE_DATE`) values (11,'Coordinator','Role for Coordinator',1,0,'2005-01-01');
insert into `csm_role` (`ROLE_ID`,`ROLE_NAME`,`ROLE_DESCRIPTION`,`APPLICATION_ID`,`ACTIVE_FLAG`,`UPDATE_DATE`) values (12,'CREATE_ONLY','Create only role',1,0,'2005-01-01');
insert into `csm_role` (`ROLE_ID`,`ROLE_NAME`,`ROLE_DESCRIPTION`,`APPLICATION_ID`,`ACTIVE_FLAG`,`UPDATE_DATE`) values (13,'SUPERADMINISTRATOR','SUPER ADMINISTRATOR ROLE',1,0,'2008-06-19');

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

insert into `csm_role_privilege` (`ROLE_PRIVILEGE_ID`,`ROLE_ID`,`PRIVILEGE_ID`,`UPDATE_DATE`) values (1,1,1,'2005-01-01');
insert into `csm_role_privilege` (`ROLE_PRIVILEGE_ID`,`ROLE_ID`,`PRIVILEGE_ID`,`UPDATE_DATE`) values (2,1,3,'2005-01-01');
insert into `csm_role_privilege` (`ROLE_PRIVILEGE_ID`,`ROLE_ID`,`PRIVILEGE_ID`,`UPDATE_DATE`) values (3,1,5,'2005-01-01');
insert into `csm_role_privilege` (`ROLE_PRIVILEGE_ID`,`ROLE_ID`,`PRIVILEGE_ID`,`UPDATE_DATE`) values (4,1,7,'2005-01-01');
insert into `csm_role_privilege` (`ROLE_PRIVILEGE_ID`,`ROLE_ID`,`PRIVILEGE_ID`,`UPDATE_DATE`) values (5,1,8,'2005-01-01');
insert into `csm_role_privilege` (`ROLE_PRIVILEGE_ID`,`ROLE_ID`,`PRIVILEGE_ID`,`UPDATE_DATE`) values (6,1,9,'2005-01-01');
insert into `csm_role_privilege` (`ROLE_PRIVILEGE_ID`,`ROLE_ID`,`PRIVILEGE_ID`,`UPDATE_DATE`) values (7,1,10,'2005-01-01');
insert into `csm_role_privilege` (`ROLE_PRIVILEGE_ID`,`ROLE_ID`,`PRIVILEGE_ID`,`UPDATE_DATE`) values (8,2,3,'2005-01-01');
insert into `csm_role_privilege` (`ROLE_PRIVILEGE_ID`,`ROLE_ID`,`PRIVILEGE_ID`,`UPDATE_DATE`) values (9,2,7,'2005-01-01');
insert into `csm_role_privilege` (`ROLE_PRIVILEGE_ID`,`ROLE_ID`,`PRIVILEGE_ID`,`UPDATE_DATE`) values (11,3,1,'2005-01-01');
insert into `csm_role_privilege` (`ROLE_PRIVILEGE_ID`,`ROLE_ID`,`PRIVILEGE_ID`,`UPDATE_DATE`) values (12,3,3,'2005-01-01');
insert into `csm_role_privilege` (`ROLE_PRIVILEGE_ID`,`ROLE_ID`,`PRIVILEGE_ID`,`UPDATE_DATE`) values (13,3,5,'2005-01-01');
insert into `csm_role_privilege` (`ROLE_PRIVILEGE_ID`,`ROLE_ID`,`PRIVILEGE_ID`,`UPDATE_DATE`) values (14,3,7,'2005-01-01');
insert into `csm_role_privilege` (`ROLE_PRIVILEGE_ID`,`ROLE_ID`,`PRIVILEGE_ID`,`UPDATE_DATE`) values (16,4,3,'2005-01-01');
insert into `csm_role_privilege` (`ROLE_PRIVILEGE_ID`,`ROLE_ID`,`PRIVILEGE_ID`,`UPDATE_DATE`) values (17,4,9,'2005-01-01');
insert into `csm_role_privilege` (`ROLE_PRIVILEGE_ID`,`ROLE_ID`,`PRIVILEGE_ID`,`UPDATE_DATE`) values (18,5,3,'2005-01-01');
insert into `csm_role_privilege` (`ROLE_PRIVILEGE_ID`,`ROLE_ID`,`PRIVILEGE_ID`,`UPDATE_DATE`) values (19,6,8,'2005-01-01');
insert into `csm_role_privilege` (`ROLE_PRIVILEGE_ID`,`ROLE_ID`,`PRIVILEGE_ID`,`UPDATE_DATE`) values (20,8,5,'2005-01-01');
insert into `csm_role_privilege` (`ROLE_PRIVILEGE_ID`,`ROLE_ID`,`PRIVILEGE_ID`,`UPDATE_DATE`) values (21,9,7,'2005-01-01');
insert into `csm_role_privilege` (`ROLE_PRIVILEGE_ID`,`ROLE_ID`,`PRIVILEGE_ID`,`UPDATE_DATE`) values (22,6,3,'2005-01-01');
insert into `csm_role_privilege` (`ROLE_PRIVILEGE_ID`,`ROLE_ID`,`PRIVILEGE_ID`,`UPDATE_DATE`) values (23,8,3,'2005-01-01');
insert into `csm_role_privilege` (`ROLE_PRIVILEGE_ID`,`ROLE_ID`,`PRIVILEGE_ID`,`UPDATE_DATE`) values (24,1,11,'2005-01-01');
insert into `csm_role_privilege` (`ROLE_PRIVILEGE_ID`,`ROLE_ID`,`PRIVILEGE_ID`,`UPDATE_DATE`) values (25,2,11,'2005-01-01');
insert into `csm_role_privilege` (`ROLE_PRIVILEGE_ID`,`ROLE_ID`,`PRIVILEGE_ID`,`UPDATE_DATE`) values (26,4,11,'2005-01-01');
insert into `csm_role_privilege` (`ROLE_PRIVILEGE_ID`,`ROLE_ID`,`PRIVILEGE_ID`,`UPDATE_DATE`) values (27,10,12,'2005-01-01');
insert into `csm_role_privilege` (`ROLE_PRIVILEGE_ID`,`ROLE_ID`,`PRIVILEGE_ID`,`UPDATE_DATE`) values (28,11,3,'2005-01-01');
insert into `csm_role_privilege` (`ROLE_PRIVILEGE_ID`,`ROLE_ID`,`PRIVILEGE_ID`,`UPDATE_DATE`) values (29,11,11,'2005-01-01');
insert into `csm_role_privilege` (`ROLE_PRIVILEGE_ID`,`ROLE_ID`,`PRIVILEGE_ID`,`UPDATE_DATE`) values (30,12,1,'2005-01-01');
insert into `csm_role_privilege` (`ROLE_PRIVILEGE_ID`,`ROLE_ID`,`PRIVILEGE_ID`,`UPDATE_DATE`) values (31,13,13,'2008-06-19');
insert into `csm_role_privilege` (`ROLE_PRIVILEGE_ID`,`ROLE_ID`,`PRIVILEGE_ID`,`UPDATE_DATE`) values (32,13,17,'2008-06-19');
insert into `csm_role_privilege` (`ROLE_PRIVILEGE_ID`,`ROLE_ID`,`PRIVILEGE_ID`,`UPDATE_DATE`) values (33,13,14,'2008-06-19');
insert into `csm_role_privilege` (`ROLE_PRIVILEGE_ID`,`ROLE_ID`,`PRIVILEGE_ID`,`UPDATE_DATE`) values (34,13,15,'2008-06-19');
insert into `csm_role_privilege` (`ROLE_PRIVILEGE_ID`,`ROLE_ID`,`PRIVILEGE_ID`,`UPDATE_DATE`) values (35,13,16,'2008-06-19');
insert into `csm_role_privilege` (`ROLE_PRIVILEGE_ID`,`ROLE_ID`,`PRIVILEGE_ID`,`UPDATE_DATE`) values (36,13,18,'2008-06-19');
insert into `csm_role_privilege` (`ROLE_PRIVILEGE_ID`,`ROLE_ID`,`PRIVILEGE_ID`,`UPDATE_DATE`) values (37,13,20,'2008-06-19');
insert into `csm_role_privilege` (`ROLE_PRIVILEGE_ID`,`ROLE_ID`,`PRIVILEGE_ID`,`UPDATE_DATE`) values (38,13,21,'2008-06-19');
insert into `csm_role_privilege` (`ROLE_PRIVILEGE_ID`,`ROLE_ID`,`PRIVILEGE_ID`,`UPDATE_DATE`) values (39,13,22,'2008-06-19');
insert into `csm_role_privilege` (`ROLE_PRIVILEGE_ID`,`ROLE_ID`,`PRIVILEGE_ID`,`UPDATE_DATE`) values (40,13,23,'2008-06-19');
insert into `csm_role_privilege` (`ROLE_PRIVILEGE_ID`,`ROLE_ID`,`PRIVILEGE_ID`,`UPDATE_DATE`) values (41,13,24,'2008-06-19');
insert into `csm_role_privilege` (`ROLE_PRIVILEGE_ID`,`ROLE_ID`,`PRIVILEGE_ID`,`UPDATE_DATE`) values (42,13,25,'2008-06-19');
insert into `csm_role_privilege` (`ROLE_PRIVILEGE_ID`,`ROLE_ID`,`PRIVILEGE_ID`,`UPDATE_DATE`) values (43,13,26,'2008-06-19');
insert into `csm_role_privilege` (`ROLE_PRIVILEGE_ID`,`ROLE_ID`,`PRIVILEGE_ID`,`UPDATE_DATE`) values (44,13,27,'2008-06-19');
insert into `csm_role_privilege` (`ROLE_PRIVILEGE_ID`,`ROLE_ID`,`PRIVILEGE_ID`,`UPDATE_DATE`) values (45,13,28,'2008-06-19');
insert into `csm_role_privilege` (`ROLE_PRIVILEGE_ID`,`ROLE_ID`,`PRIVILEGE_ID`,`UPDATE_DATE`) values (46,13,29,'2008-06-19');
insert into `csm_role_privilege` (`ROLE_PRIVILEGE_ID`,`ROLE_ID`,`PRIVILEGE_ID`,`UPDATE_DATE`) values (47,1,13,'2008-06-19');
insert into `csm_role_privilege` (`ROLE_PRIVILEGE_ID`,`ROLE_ID`,`PRIVILEGE_ID`,`UPDATE_DATE`) values (48,1,14,'2008-06-19');
insert into `csm_role_privilege` (`ROLE_PRIVILEGE_ID`,`ROLE_ID`,`PRIVILEGE_ID`,`UPDATE_DATE`) values (49,1,15,'2008-06-19');
insert into `csm_role_privilege` (`ROLE_PRIVILEGE_ID`,`ROLE_ID`,`PRIVILEGE_ID`,`UPDATE_DATE`) values (50,1,16,'2008-06-19');
insert into `csm_role_privilege` (`ROLE_PRIVILEGE_ID`,`ROLE_ID`,`PRIVILEGE_ID`,`UPDATE_DATE`) values (51,1,18,'2008-06-19');
insert into `csm_role_privilege` (`ROLE_PRIVILEGE_ID`,`ROLE_ID`,`PRIVILEGE_ID`,`UPDATE_DATE`) values (52,1,20,'2008-06-19');
insert into `csm_role_privilege` (`ROLE_PRIVILEGE_ID`,`ROLE_ID`,`PRIVILEGE_ID`,`UPDATE_DATE`) values (53,1,21,'2008-06-19');
insert into `csm_role_privilege` (`ROLE_PRIVILEGE_ID`,`ROLE_ID`,`PRIVILEGE_ID`,`UPDATE_DATE`) values (54,1,22,'2008-06-19');
insert into `csm_role_privilege` (`ROLE_PRIVILEGE_ID`,`ROLE_ID`,`PRIVILEGE_ID`,`UPDATE_DATE`) values (55,1,23,'2008-06-19');
insert into `csm_role_privilege` (`ROLE_PRIVILEGE_ID`,`ROLE_ID`,`PRIVILEGE_ID`,`UPDATE_DATE`) values (56,1,24,'2008-06-19');
insert into `csm_role_privilege` (`ROLE_PRIVILEGE_ID`,`ROLE_ID`,`PRIVILEGE_ID`,`UPDATE_DATE`) values (57,1,25,'2008-06-19');
insert into `csm_role_privilege` (`ROLE_PRIVILEGE_ID`,`ROLE_ID`,`PRIVILEGE_ID`,`UPDATE_DATE`) values (58,1,26,'2008-06-19');
insert into `csm_role_privilege` (`ROLE_PRIVILEGE_ID`,`ROLE_ID`,`PRIVILEGE_ID`,`UPDATE_DATE`) values (59,1,27,'2008-06-19');
insert into `csm_role_privilege` (`ROLE_PRIVILEGE_ID`,`ROLE_ID`,`PRIVILEGE_ID`,`UPDATE_DATE`) values (60,1,28,'2008-06-19');
insert into `csm_role_privilege` (`ROLE_PRIVILEGE_ID`,`ROLE_ID`,`PRIVILEGE_ID`,`UPDATE_DATE`) values (61,2,18,'2008-06-19');
insert into `csm_role_privilege` (`ROLE_PRIVILEGE_ID`,`ROLE_ID`,`PRIVILEGE_ID`,`UPDATE_DATE`) values (62,2,20,'2008-06-19');
insert into `csm_role_privilege` (`ROLE_PRIVILEGE_ID`,`ROLE_ID`,`PRIVILEGE_ID`,`UPDATE_DATE`) values (63,2,21,'2008-06-19');
insert into `csm_role_privilege` (`ROLE_PRIVILEGE_ID`,`ROLE_ID`,`PRIVILEGE_ID`,`UPDATE_DATE`) values (64,2,22,'2008-06-19');
insert into `csm_role_privilege` (`ROLE_PRIVILEGE_ID`,`ROLE_ID`,`PRIVILEGE_ID`,`UPDATE_DATE`) values (65,2,23,'2008-06-19');
insert into `csm_role_privilege` (`ROLE_PRIVILEGE_ID`,`ROLE_ID`,`PRIVILEGE_ID`,`UPDATE_DATE`) values (66,2,24,'2008-06-19');
insert into `csm_role_privilege` (`ROLE_PRIVILEGE_ID`,`ROLE_ID`,`PRIVILEGE_ID`,`UPDATE_DATE`) values (67,2,25,'2008-06-19');
insert into `csm_role_privilege` (`ROLE_PRIVILEGE_ID`,`ROLE_ID`,`PRIVILEGE_ID`,`UPDATE_DATE`) values (68,2,26,'2008-06-19');
insert into `csm_role_privilege` (`ROLE_PRIVILEGE_ID`,`ROLE_ID`,`PRIVILEGE_ID`,`UPDATE_DATE`) values (69,2,27,'2008-06-19');
insert into `csm_role_privilege` (`ROLE_PRIVILEGE_ID`,`ROLE_ID`,`PRIVILEGE_ID`,`UPDATE_DATE`) values (70,3,21,'2008-06-19');
insert into `csm_role_privilege` (`ROLE_PRIVILEGE_ID`,`ROLE_ID`,`PRIVILEGE_ID`,`UPDATE_DATE`) values (71,3,22,'2008-06-19');
insert into `csm_role_privilege` (`ROLE_PRIVILEGE_ID`,`ROLE_ID`,`PRIVILEGE_ID`,`UPDATE_DATE`) values (72,3,25,'2008-06-19');
insert into `csm_role_privilege` (`ROLE_PRIVILEGE_ID`,`ROLE_ID`,`PRIVILEGE_ID`,`UPDATE_DATE`) values (73,3,26,'2008-06-19');
insert into `csm_role_privilege` (`ROLE_PRIVILEGE_ID`,`ROLE_ID`,`PRIVILEGE_ID`,`UPDATE_DATE`) values (74,3,27,'2008-06-19');
insert into `csm_role_privilege` (`ROLE_PRIVILEGE_ID`,`ROLE_ID`,`PRIVILEGE_ID`,`UPDATE_DATE`) values (75,7,22,'2008-06-19');
insert into `csm_role_privilege` (`ROLE_PRIVILEGE_ID`,`ROLE_ID`,`PRIVILEGE_ID`,`UPDATE_DATE`) values (76,1,29,'2008-07-22');
insert into `csm_role_privilege` (`ROLE_PRIVILEGE_ID`,`ROLE_ID`,`PRIVILEGE_ID`,`UPDATE_DATE`) values (77,4,18,'2008-06-19');
insert into `csm_role_privilege` (`ROLE_PRIVILEGE_ID`,`ROLE_ID`,`PRIVILEGE_ID`,`UPDATE_DATE`) values (78,4,26,'2008-06-19');
insert into `csm_role_privilege` (`ROLE_PRIVILEGE_ID`,`ROLE_ID`,`PRIVILEGE_ID`,`UPDATE_DATE`) values (79,4,21,'2008-06-19');
insert into `csm_role_privilege` (`ROLE_PRIVILEGE_ID`,`ROLE_ID`,`PRIVILEGE_ID`,`UPDATE_DATE`) values (80,4,23,'2008-06-19');
insert into `csm_role_privilege` (`ROLE_PRIVILEGE_ID`,`ROLE_ID`,`PRIVILEGE_ID`,`UPDATE_DATE`) values (81,4,24,'2008-06-19');
insert into `csm_role_privilege` (`ROLE_PRIVILEGE_ID`,`ROLE_ID`,`PRIVILEGE_ID`,`UPDATE_DATE`) values (82,4,25,'2008-06-19');
insert into `csm_role_privilege` (`ROLE_PRIVILEGE_ID`,`ROLE_ID`,`PRIVILEGE_ID`,`UPDATE_DATE`) values (83,11,18,'2008-06-19');
insert into `csm_role_privilege` (`ROLE_PRIVILEGE_ID`,`ROLE_ID`,`PRIVILEGE_ID`,`UPDATE_DATE`) values (84,11,26,'2008-06-19');
insert into `csm_role_privilege` (`ROLE_PRIVILEGE_ID`,`ROLE_ID`,`PRIVILEGE_ID`,`UPDATE_DATE`) values (85,11,21,'2008-06-19');
insert into `csm_role_privilege` (`ROLE_PRIVILEGE_ID`,`ROLE_ID`,`PRIVILEGE_ID`,`UPDATE_DATE`) values (86,11,23,'2008-06-19');
insert into `csm_role_privilege` (`ROLE_PRIVILEGE_ID`,`ROLE_ID`,`PRIVILEGE_ID`,`UPDATE_DATE`) values (87,11,24,'2008-06-19');
insert into `csm_role_privilege` (`ROLE_PRIVILEGE_ID`,`ROLE_ID`,`PRIVILEGE_ID`,`UPDATE_DATE`) values (88,11,25,'2008-06-19');
insert into `csm_role_privilege` (`ROLE_PRIVILEGE_ID`,`ROLE_ID`,`PRIVILEGE_ID`,`UPDATE_DATE`) values (89,1,30,'2008-08-20');
insert into `csm_role_privilege` (`ROLE_PRIVILEGE_ID`,`ROLE_ID`,`PRIVILEGE_ID`,`UPDATE_DATE`) values (90,2,30,'2008-08-20');

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

insert into `csm_user_group_role_pg` (`USER_GROUP_ROLE_PG_ID`,`USER_ID`,`GROUP_ID`,`ROLE_ID`,`PROTECTION_GROUP_ID`,`UPDATE_DATE`) values (3,NULL,4,5,1,'2005-01-01');
insert into `csm_user_group_role_pg` (`USER_GROUP_ROLE_PG_ID`,`USER_ID`,`GROUP_ID`,`ROLE_ID`,`PROTECTION_GROUP_ID`,`UPDATE_DATE`) values (4,NULL,1,1,1,'2005-01-01');
insert into `csm_user_group_role_pg` (`USER_GROUP_ROLE_PG_ID`,`USER_ID`,`GROUP_ID`,`ROLE_ID`,`PROTECTION_GROUP_ID`,`UPDATE_DATE`) values (5,NULL,1,1,2,'2005-01-01');
insert into `csm_user_group_role_pg` (`USER_GROUP_ROLE_PG_ID`,`USER_ID`,`GROUP_ID`,`ROLE_ID`,`PROTECTION_GROUP_ID`,`UPDATE_DATE`) values (6,NULL,1,1,3,'2005-01-01');
insert into `csm_user_group_role_pg` (`USER_GROUP_ROLE_PG_ID`,`USER_ID`,`GROUP_ID`,`ROLE_ID`,`PROTECTION_GROUP_ID`,`UPDATE_DATE`) values (20,NULL,1,1,17,'2005-01-01');
insert into `csm_user_group_role_pg` (`USER_GROUP_ROLE_PG_ID`,`USER_ID`,`GROUP_ID`,`ROLE_ID`,`PROTECTION_GROUP_ID`,`UPDATE_DATE`) values (21,NULL,1,1,18,'2005-01-01');
insert into `csm_user_group_role_pg` (`USER_GROUP_ROLE_PG_ID`,`USER_ID`,`GROUP_ID`,`ROLE_ID`,`PROTECTION_GROUP_ID`,`UPDATE_DATE`) values (22,NULL,1,1,19,'2005-01-01');
insert into `csm_user_group_role_pg` (`USER_GROUP_ROLE_PG_ID`,`USER_ID`,`GROUP_ID`,`ROLE_ID`,`PROTECTION_GROUP_ID`,`UPDATE_DATE`) values (23,NULL,1,1,20,'2005-01-01');
insert into `csm_user_group_role_pg` (`USER_GROUP_ROLE_PG_ID`,`USER_ID`,`GROUP_ID`,`ROLE_ID`,`PROTECTION_GROUP_ID`,`UPDATE_DATE`) values (24,NULL,1,1,21,'2005-01-01');
insert into `csm_user_group_role_pg` (`USER_GROUP_ROLE_PG_ID`,`USER_ID`,`GROUP_ID`,`ROLE_ID`,`PROTECTION_GROUP_ID`,`UPDATE_DATE`) values (25,NULL,1,1,22,'2005-01-01');
insert into `csm_user_group_role_pg` (`USER_GROUP_ROLE_PG_ID`,`USER_ID`,`GROUP_ID`,`ROLE_ID`,`PROTECTION_GROUP_ID`,`UPDATE_DATE`) values (26,NULL,1,1,23,'2005-01-01');
insert into `csm_user_group_role_pg` (`USER_GROUP_ROLE_PG_ID`,`USER_ID`,`GROUP_ID`,`ROLE_ID`,`PROTECTION_GROUP_ID`,`UPDATE_DATE`) values (27,NULL,2,1,2,'2005-01-01');
insert into `csm_user_group_role_pg` (`USER_GROUP_ROLE_PG_ID`,`USER_ID`,`GROUP_ID`,`ROLE_ID`,`PROTECTION_GROUP_ID`,`UPDATE_DATE`) values (28,NULL,2,1,3,'2005-01-01');
insert into `csm_user_group_role_pg` (`USER_GROUP_ROLE_PG_ID`,`USER_ID`,`GROUP_ID`,`ROLE_ID`,`PROTECTION_GROUP_ID`,`UPDATE_DATE`) values (33,NULL,2,1,22,'2005-01-01');
insert into `csm_user_group_role_pg` (`USER_GROUP_ROLE_PG_ID`,`USER_ID`,`GROUP_ID`,`ROLE_ID`,`PROTECTION_GROUP_ID`,`UPDATE_DATE`) values (34,NULL,2,1,23,'2005-01-01');
insert into `csm_user_group_role_pg` (`USER_GROUP_ROLE_PG_ID`,`USER_ID`,`GROUP_ID`,`ROLE_ID`,`PROTECTION_GROUP_ID`,`UPDATE_DATE`) values (35,NULL,2,2,1,'2005-01-01');
insert into `csm_user_group_role_pg` (`USER_GROUP_ROLE_PG_ID`,`USER_ID`,`GROUP_ID`,`ROLE_ID`,`PROTECTION_GROUP_ID`,`UPDATE_DATE`) values (40,NULL,2,2,18,'2005-01-01');
insert into `csm_user_group_role_pg` (`USER_GROUP_ROLE_PG_ID`,`USER_ID`,`GROUP_ID`,`ROLE_ID`,`PROTECTION_GROUP_ID`,`UPDATE_DATE`) values (41,NULL,2,2,19,'2005-01-01');
insert into `csm_user_group_role_pg` (`USER_GROUP_ROLE_PG_ID`,`USER_ID`,`GROUP_ID`,`ROLE_ID`,`PROTECTION_GROUP_ID`,`UPDATE_DATE`) values (46,NULL,2,2,21,'2005-01-01');
insert into `csm_user_group_role_pg` (`USER_GROUP_ROLE_PG_ID`,`USER_ID`,`GROUP_ID`,`ROLE_ID`,`PROTECTION_GROUP_ID`,`UPDATE_DATE`) values (47,NULL,3,3,3,'2005-01-01');
insert into `csm_user_group_role_pg` (`USER_GROUP_ROLE_PG_ID`,`USER_ID`,`GROUP_ID`,`ROLE_ID`,`PROTECTION_GROUP_ID`,`UPDATE_DATE`) values (50,NULL,3,3,19,'2005-01-01');
insert into `csm_user_group_role_pg` (`USER_GROUP_ROLE_PG_ID`,`USER_ID`,`GROUP_ID`,`ROLE_ID`,`PROTECTION_GROUP_ID`,`UPDATE_DATE`) values (51,NULL,3,5,1,'2005-01-01');
insert into `csm_user_group_role_pg` (`USER_GROUP_ROLE_PG_ID`,`USER_ID`,`GROUP_ID`,`ROLE_ID`,`PROTECTION_GROUP_ID`,`UPDATE_DATE`) values (52,NULL,3,5,2,'2005-01-01');
insert into `csm_user_group_role_pg` (`USER_GROUP_ROLE_PG_ID`,`USER_ID`,`GROUP_ID`,`ROLE_ID`,`PROTECTION_GROUP_ID`,`UPDATE_DATE`) values (56,NULL,3,5,21,'2005-01-01');
insert into `csm_user_group_role_pg` (`USER_GROUP_ROLE_PG_ID`,`USER_ID`,`GROUP_ID`,`ROLE_ID`,`PROTECTION_GROUP_ID`,`UPDATE_DATE`) values (57,NULL,3,5,22,'2005-01-01');
insert into `csm_user_group_role_pg` (`USER_GROUP_ROLE_PG_ID`,`USER_ID`,`GROUP_ID`,`ROLE_ID`,`PROTECTION_GROUP_ID`,`UPDATE_DATE`) values (61,NULL,4,5,2,'2005-01-01');
insert into `csm_user_group_role_pg` (`USER_GROUP_ROLE_PG_ID`,`USER_ID`,`GROUP_ID`,`ROLE_ID`,`PROTECTION_GROUP_ID`,`UPDATE_DATE`) values (62,NULL,4,5,3,'2005-01-01');
insert into `csm_user_group_role_pg` (`USER_GROUP_ROLE_PG_ID`,`USER_ID`,`GROUP_ID`,`ROLE_ID`,`PROTECTION_GROUP_ID`,`UPDATE_DATE`) values (69,NULL,4,5,20,'2005-01-01');
insert into `csm_user_group_role_pg` (`USER_GROUP_ROLE_PG_ID`,`USER_ID`,`GROUP_ID`,`ROLE_ID`,`PROTECTION_GROUP_ID`,`UPDATE_DATE`) values (70,NULL,1,1,24,'2005-08-24');
insert into `csm_user_group_role_pg` (`USER_GROUP_ROLE_PG_ID`,`USER_ID`,`GROUP_ID`,`ROLE_ID`,`PROTECTION_GROUP_ID`,`UPDATE_DATE`) values (71,NULL,2,2,24,'2005-08-24');
insert into `csm_user_group_role_pg` (`USER_GROUP_ROLE_PG_ID`,`USER_ID`,`GROUP_ID`,`ROLE_ID`,`PROTECTION_GROUP_ID`,`UPDATE_DATE`) values (72,NULL,3,3,24,'2005-08-24');
insert into `csm_user_group_role_pg` (`USER_GROUP_ROLE_PG_ID`,`USER_ID`,`GROUP_ID`,`ROLE_ID`,`PROTECTION_GROUP_ID`,`UPDATE_DATE`) values (73,NULL,4,9,24,'2005-08-24');
insert into `csm_user_group_role_pg` (`USER_GROUP_ROLE_PG_ID`,`USER_ID`,`GROUP_ID`,`ROLE_ID`,`PROTECTION_GROUP_ID`,`UPDATE_DATE`) values (74,NULL,2,2,20,'2006-01-18');
insert into `csm_user_group_role_pg` (`USER_GROUP_ROLE_PG_ID`,`USER_ID`,`GROUP_ID`,`ROLE_ID`,`PROTECTION_GROUP_ID`,`UPDATE_DATE`) values (75,NULL,3,3,20,'2005-08-24');
insert into `csm_user_group_role_pg` (`USER_GROUP_ROLE_PG_ID`,`USER_ID`,`GROUP_ID`,`ROLE_ID`,`PROTECTION_GROUP_ID`,`UPDATE_DATE`) values (102,NULL,4,12,44,'2005-01-01');

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

/*Table structure for table `de_as_113` */

DROP TABLE IF EXISTS `de_as_113`;

CREATE TABLE `de_as_113` (
  `IDENTIFIER` int(38) NOT NULL,
  `DE_E_S_114` int(38) default NULL,
  `DE_E_T_115` int(38) default NULL,
  PRIMARY KEY  (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `de_as_113` */

/*Table structure for table `de_as_123` */

DROP TABLE IF EXISTS `de_as_123`;

CREATE TABLE `de_as_123` (
  `IDENTIFIER` int(38) NOT NULL,
  `DE_E_S_124` int(38) default NULL,
  `DE_E_T_125` int(38) default NULL,
  PRIMARY KEY  (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `de_as_123` */

/*Table structure for table `de_as_138` */

DROP TABLE IF EXISTS `de_as_138`;

CREATE TABLE `de_as_138` (
  `IDENTIFIER` int(38) NOT NULL,
  `DE_E_S_139` int(38) default NULL,
  `DE_E_T_140` int(38) default NULL,
  PRIMARY KEY  (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `de_as_138` */

/*Table structure for table `de_as_168` */

DROP TABLE IF EXISTS `de_as_168`;

CREATE TABLE `de_as_168` (
  `IDENTIFIER` int(38) NOT NULL,
  `DE_E_S_169` int(38) default NULL,
  `DE_E_T_170` int(38) default NULL,
  PRIMARY KEY  (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `de_as_168` */

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

/*Table structure for table `de_e_1` */

DROP TABLE IF EXISTS `de_e_1`;

CREATE TABLE `de_e_1` (
  `ACTIVITY_STATUS` text,
  `IDENTIFIER` bigint(38) NOT NULL,
  `DE_AT_5` text,
  `DE_AT_6` text,
  `DE_AT_7` text,
  `DE_AT_8` text,
  `DE_E_S_149` int(38) default NULL,
  `DE_E_S_164` int(38) default NULL,
  `DE_E_S_179` int(38) default NULL,
  `DE_E_T_130` int(38) default NULL,
  PRIMARY KEY  (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `de_e_1` */

/*Table structure for table `de_e_13` */

DROP TABLE IF EXISTS `de_e_13`;

CREATE TABLE `de_e_13` (
  `ACTIVITY_STATUS` text,
  `IDENTIFIER` bigint(38) NOT NULL,
  `DE_AT_17` text,
  `DE_AT_18` text,
  `DE_AT_19` text,
  `DE_AT_20` text,
  `DE_AT_21` text,
  `DE_AT_22` text,
  `DE_AT_23` text,
  `DE_E_T_110` int(38) default NULL,
  PRIMARY KEY  (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `de_e_13` */

/*Table structure for table `de_e_24` */

DROP TABLE IF EXISTS `de_e_24`;

CREATE TABLE `de_e_24` (
  `ACTIVITY_STATUS` text,
  `IDENTIFIER` bigint(38) NOT NULL,
  `DE_AT_28` text,
  PRIMARY KEY  (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `de_e_24` */

/*Table structure for table `de_e_29` */

DROP TABLE IF EXISTS `de_e_29`;

CREATE TABLE `de_e_29` (
  `ACTIVITY_STATUS` text,
  `IDENTIFIER` bigint(38) NOT NULL,
  `DE_E_S_99` int(38) default NULL,
  `DE_E_S_174` int(38) default NULL,
  PRIMARY KEY  (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `de_e_29` */

/*Table structure for table `de_e_33` */

DROP TABLE IF EXISTS `de_e_33`;

CREATE TABLE `de_e_33` (
  `ACTIVITY_STATUS` text,
  `IDENTIFIER` bigint(38) NOT NULL,
  `DE_AT_37` text,
  `DE_AT_38` text,
  PRIMARY KEY  (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `de_e_33` */

/*Table structure for table `de_e_39` */

DROP TABLE IF EXISTS `de_e_39`;

CREATE TABLE `de_e_39` (
  `ACTIVITY_STATUS` text,
  `DE_AT_49` date default NULL,
  `DE_AT_48` date default NULL,
  `DE_AT_47` text,
  `DE_AT_46` text,
  `DE_AT_45` text,
  `DE_AT_44` date default NULL,
  `DE_AT_43` date default NULL,
  `IDENTIFIER` bigint(38) NOT NULL,
  `DE_E_T_105` int(38) default NULL,
  `DE_E_S_119` int(38) default NULL,
  `DE_E_S_134` int(38) default NULL,
  `DE_E_S_144` int(38) default NULL,
  `DE_E_S_154` int(38) default NULL,
  `DE_E_S_159` int(38) default NULL,
  PRIMARY KEY  (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `de_e_39` */

/*Table structure for table `de_e_50` */

DROP TABLE IF EXISTS `de_e_50`;

CREATE TABLE `de_e_50` (
  `ACTIVITY_STATUS` text,
  `IDENTIFIER` bigint(38) NOT NULL,
  PRIMARY KEY  (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `de_e_50` */

/*Table structure for table `de_e_54` */

DROP TABLE IF EXISTS `de_e_54`;

CREATE TABLE `de_e_54` (
  `ACTIVITY_STATUS` text,
  `IDENTIFIER` bigint(38) NOT NULL,
  PRIMARY KEY  (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `de_e_54` */

/*Table structure for table `de_e_58` */

DROP TABLE IF EXISTS `de_e_58`;

CREATE TABLE `de_e_58` (
  `ACTIVITY_STATUS` text,
  `IDENTIFIER` bigint(38) NOT NULL,
  PRIMARY KEY  (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `de_e_58` */

/*Table structure for table `de_e_62` */

DROP TABLE IF EXISTS `de_e_62`;

CREATE TABLE `de_e_62` (
  `ACTIVITY_STATUS` text,
  `IDENTIFIER` bigint(38) NOT NULL,
  PRIMARY KEY  (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `de_e_62` */

/*Table structure for table `de_e_66` */

DROP TABLE IF EXISTS `de_e_66`;

CREATE TABLE `de_e_66` (
  `ACTIVITY_STATUS` text,
  `IDENTIFIER` bigint(38) NOT NULL,
  PRIMARY KEY  (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `de_e_66` */

/*Table structure for table `de_e_70` */

DROP TABLE IF EXISTS `de_e_70`;

CREATE TABLE `de_e_70` (
  `ACTIVITY_STATUS` text,
  `IDENTIFIER` bigint(38) NOT NULL,
  PRIMARY KEY  (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `de_e_70` */

/*Table structure for table `de_e_74` */

DROP TABLE IF EXISTS `de_e_74`;

CREATE TABLE `de_e_74` (
  `ACTIVITY_STATUS` text,
  `IDENTIFIER` bigint(38) NOT NULL,
  PRIMARY KEY  (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `de_e_74` */

/*Table structure for table `de_e_78` */

DROP TABLE IF EXISTS `de_e_78`;

CREATE TABLE `de_e_78` (
  `ACTIVITY_STATUS` text,
  `IDENTIFIER` bigint(38) NOT NULL,
  PRIMARY KEY  (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `de_e_78` */

/*Table structure for table `de_e_82` */

DROP TABLE IF EXISTS `de_e_82`;

CREATE TABLE `de_e_82` (
  `ACTIVITY_STATUS` text,
  `IDENTIFIER` bigint(38) NOT NULL,
  PRIMARY KEY  (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `de_e_82` */

/*Table structure for table `de_e_86` */

DROP TABLE IF EXISTS `de_e_86`;

CREATE TABLE `de_e_86` (
  `ACTIVITY_STATUS` text,
  `IDENTIFIER` bigint(38) NOT NULL,
  PRIMARY KEY  (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `de_e_86` */

/*Table structure for table `de_e_9` */

DROP TABLE IF EXISTS `de_e_9`;

CREATE TABLE `de_e_9` (
  `ACTIVITY_STATUS` text,
  `IDENTIFIER` bigint(38) NOT NULL,
  PRIMARY KEY  (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `de_e_9` */

/*Table structure for table `de_e_90` */

DROP TABLE IF EXISTS `de_e_90`;

CREATE TABLE `de_e_90` (
  `ACTIVITY_STATUS` text,
  `IDENTIFIER` bigint(38) NOT NULL,
  PRIMARY KEY  (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `de_e_90` */

/*Table structure for table `de_e_94` */

DROP TABLE IF EXISTS `de_e_94`;

CREATE TABLE `de_e_94` (
  `ACTIVITY_STATUS` text,
  `IDENTIFIER` bigint(38) NOT NULL,
  PRIMARY KEY  (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `de_e_94` */

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
  CONSTRAINT `FK9EB9020A40F198C2` FOREIGN KEY (`IDENTIFIER`) REFERENCES `dyextn_control` (`IDENTIFIER`),
  CONSTRAINT `FK9EB9020A69935DD6` FOREIGN KEY (`CONTAINER_ID`) REFERENCES `dyextn_container` (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `dyextn_abstr_contain_ctr` */

/*Table structure for table `dyextn_abstract_entity` */

DROP TABLE IF EXISTS `dyextn_abstract_entity`;

CREATE TABLE `dyextn_abstract_entity` (
  `id` bigint(20) NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `FKA4A164E3D3027A30` (`id`),
  CONSTRAINT `FKA4A164E3D3027A30` FOREIGN KEY (`id`) REFERENCES `dyextn_abstract_metadata` (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `dyextn_abstract_entity` */

insert into `dyextn_abstract_entity` (`id`) values (2);
insert into `dyextn_abstract_entity` (`id`) values (4);
insert into `dyextn_abstract_entity` (`id`) values (6);
insert into `dyextn_abstract_entity` (`id`) values (8);
insert into `dyextn_abstract_entity` (`id`) values (10);
insert into `dyextn_abstract_entity` (`id`) values (12);
insert into `dyextn_abstract_entity` (`id`) values (14);
insert into `dyextn_abstract_entity` (`id`) values (17);
insert into `dyextn_abstract_entity` (`id`) values (25);
insert into `dyextn_abstract_entity` (`id`) values (27);
insert into `dyextn_abstract_entity` (`id`) values (36);
insert into `dyextn_abstract_entity` (`id`) values (37);
insert into `dyextn_abstract_entity` (`id`) values (39);
insert into `dyextn_abstract_entity` (`id`) values (42);
insert into `dyextn_abstract_entity` (`id`) values (45);
insert into `dyextn_abstract_entity` (`id`) values (48);
insert into `dyextn_abstract_entity` (`id`) values (61);
insert into `dyextn_abstract_entity` (`id`) values (67);
insert into `dyextn_abstract_entity` (`id`) values (76);

/*Table structure for table `dyextn_abstract_metadata` */

DROP TABLE IF EXISTS `dyextn_abstract_metadata`;

CREATE TABLE `dyextn_abstract_metadata` (
  `IDENTIFIER` bigint(20) NOT NULL auto_increment,
  `CREATED_DATE` date default NULL,
  `DESCRIPTION` text,
  `LAST_UPDATED` date default NULL,
  `NAME` text,
  `PUBLIC_ID` varchar(255) default NULL,
  PRIMARY KEY  (`IDENTIFIER`)
) ENGINE=InnoDB AUTO_INCREMENT=78 DEFAULT CHARSET=latin1;

/*Data for the table `dyextn_abstract_metadata` */

insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (1,NULL,'cider',NULL,'cider',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (2,'2008-11-07','cider-ActiveUpiFlag','2008-11-07','ActiveUpiFlag',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (3,NULL,NULL,NULL,'id',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (4,'2008-11-07','cider-State','2008-11-07','State',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (5,NULL,NULL,NULL,'id',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (6,'2008-11-07','cider-PhoneType','2008-11-07','PhoneType',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (7,NULL,NULL,NULL,'id',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (8,'2008-11-07','cider-RelationToPerson','2008-11-07','RelationToPerson',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (9,NULL,NULL,NULL,'id',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (10,'2008-11-07','cider-EthnicOrigin','2008-11-07','EthnicOrigin',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (11,NULL,NULL,NULL,'id',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (12,'2008-11-07','cider-AddressType','2008-11-07','AddressType',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (13,NULL,NULL,NULL,'id',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (14,'2008-11-07','cider-Address','2008-11-07','Address',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (15,NULL,NULL,NULL,'AssociationName_3',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (16,NULL,NULL,NULL,'AssociationName_2',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (17,'2008-11-07','cider-Country','2008-11-07','Country',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (18,NULL,NULL,NULL,'id',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (19,NULL,NULL,NULL,'AssociationName_1',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (20,NULL,NULL,NULL,'postalCode',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (21,NULL,NULL,NULL,'city',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (22,NULL,NULL,NULL,'line2',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (23,NULL,NULL,NULL,'line1',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (24,NULL,NULL,NULL,'id',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (25,'2008-11-07','cider-MaritalStatus','2008-11-07','MaritalStatus',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (26,NULL,NULL,NULL,'id',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (27,'2008-11-07','cider-PersonName','2008-11-07','PersonName',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (28,NULL,NULL,NULL,'suffix',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (29,NULL,NULL,NULL,'prefix',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (30,NULL,NULL,NULL,'degree',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (31,NULL,NULL,NULL,'lastNameCompressed',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (32,NULL,NULL,NULL,'lastName',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (33,NULL,NULL,NULL,'middleName',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (34,NULL,NULL,NULL,'firstName',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (35,NULL,NULL,NULL,'id',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (36,'2008-11-07','cider-Person','2008-11-07','Person',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (37,'2008-11-07','cider-Demographics','2008-11-07','Demographics',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (38,NULL,NULL,NULL,'AssociationName_11',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (39,'2008-11-07','cider-Gender','2008-11-07','Gender',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (40,NULL,NULL,NULL,'id',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (41,NULL,NULL,NULL,'AssociationName_10',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (42,'2008-11-07','cider-AdvancedDirectiveExists','2008-11-07','AdvancedDirectiveExists',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (43,NULL,NULL,NULL,'id',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (44,NULL,NULL,NULL,'AssociationName_9',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (45,'2008-11-07','cider-Religion','2008-11-07','Religion',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (46,NULL,NULL,NULL,'id',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (47,NULL,NULL,NULL,'AssociationName_8',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (48,'2008-11-07','cider-Race','2008-11-07','Race',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (49,NULL,NULL,NULL,'id',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (50,NULL,NULL,NULL,'AssociationName_7',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (51,NULL,NULL,NULL,'AssociationName_6',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (52,NULL,NULL,NULL,'id',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (53,NULL,NULL,NULL,'dateOfBirth',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (54,NULL,NULL,NULL,'dateOfDeath',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (55,NULL,NULL,NULL,'mothersMaidenName',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (56,NULL,NULL,NULL,'placeOfBirth',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (57,NULL,NULL,NULL,'socialSecurityNumber',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (58,NULL,NULL,NULL,'effectiveStartTimeStamp',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (59,NULL,NULL,NULL,'effectiveEndTimeStamp',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (60,NULL,NULL,NULL,'AssociationName_1',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (61,'2008-11-07','cider-AssociatedPerson','2008-11-07','AssociatedPerson',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (62,NULL,NULL,NULL,'AssociationName_2',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (63,NULL,NULL,NULL,'AssociationName_1',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (64,NULL,NULL,NULL,'id',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (65,NULL,NULL,NULL,'AssociationName_2',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (66,NULL,NULL,NULL,'AssociationName_3',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (67,'2008-11-07','cider-Phone','2008-11-07','Phone',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (68,NULL,NULL,NULL,'type',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (69,NULL,NULL,NULL,'number',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (70,NULL,NULL,NULL,'id',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (71,NULL,NULL,NULL,'AssociationName_4',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (72,NULL,NULL,NULL,'AssociationName_5',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (73,NULL,NULL,NULL,'AssociationName_1',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (74,NULL,NULL,NULL,'personUpi',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (75,NULL,NULL,NULL,'id',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (76,'2008-11-07','cider-ResearchOptOut','2008-11-07','ResearchOptOut',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (77,NULL,NULL,NULL,'id',NULL);

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
  CONSTRAINT `FKD12FD38235D6E973` FOREIGN KEY (`DISPLAY_ATTRIBUTE_ID`) REFERENCES `dyextn_primitive_attribute` (`IDENTIFIER`),
  CONSTRAINT `FKD12FD3827FD29CDD` FOREIGN KEY (`SELECT_CONTROL_ID`) REFERENCES `dyextn_select_control` (`IDENTIFIER`)
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
  CONSTRAINT `FK104684242BD842F0` FOREIGN KEY (`TARGET_ROLE_ID`) REFERENCES `dyextn_role` (`IDENTIFIER`),
  CONSTRAINT `FK1046842439780F7A` FOREIGN KEY (`SOURCE_ROLE_ID`) REFERENCES `dyextn_role` (`IDENTIFIER`),
  CONSTRAINT `FK1046842440738A50` FOREIGN KEY (`TARGET_ENTITY_ID`) REFERENCES `dyextn_entity` (`IDENTIFIER`),
  CONSTRAINT `FK104684246D19A21F` FOREIGN KEY (`IDENTIFIER`) REFERENCES `dyextn_attribute` (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `dyextn_association` */

insert into `dyextn_association` (`IDENTIFIER`,`IS_COLLECTION`,`DIRECTION`,`TARGET_ENTITY_ID`,`SOURCE_ROLE_ID`,`TARGET_ROLE_ID`,`IS_SYSTEM_GENERATED`) values (15,'\0','SRC_DESTINATION',4,1,2,'\0');
insert into `dyextn_association` (`IDENTIFIER`,`IS_COLLECTION`,`DIRECTION`,`TARGET_ENTITY_ID`,`SOURCE_ROLE_ID`,`TARGET_ROLE_ID`,`IS_SYSTEM_GENERATED`) values (16,'\0','SRC_DESTINATION',12,3,4,'\0');
insert into `dyextn_association` (`IDENTIFIER`,`IS_COLLECTION`,`DIRECTION`,`TARGET_ENTITY_ID`,`SOURCE_ROLE_ID`,`TARGET_ROLE_ID`,`IS_SYSTEM_GENERATED`) values (19,'\0','SRC_DESTINATION',17,5,6,'\0');
insert into `dyextn_association` (`IDENTIFIER`,`IS_COLLECTION`,`DIRECTION`,`TARGET_ENTITY_ID`,`SOURCE_ROLE_ID`,`TARGET_ROLE_ID`,`IS_SYSTEM_GENERATED`) values (38,'\0','SRC_DESTINATION',10,7,8,'\0');
insert into `dyextn_association` (`IDENTIFIER`,`IS_COLLECTION`,`DIRECTION`,`TARGET_ENTITY_ID`,`SOURCE_ROLE_ID`,`TARGET_ROLE_ID`,`IS_SYSTEM_GENERATED`) values (41,'\0','SRC_DESTINATION',39,9,10,'\0');
insert into `dyextn_association` (`IDENTIFIER`,`IS_COLLECTION`,`DIRECTION`,`TARGET_ENTITY_ID`,`SOURCE_ROLE_ID`,`TARGET_ROLE_ID`,`IS_SYSTEM_GENERATED`) values (44,'\0','SRC_DESTINATION',42,11,12,'\0');
insert into `dyextn_association` (`IDENTIFIER`,`IS_COLLECTION`,`DIRECTION`,`TARGET_ENTITY_ID`,`SOURCE_ROLE_ID`,`TARGET_ROLE_ID`,`IS_SYSTEM_GENERATED`) values (47,'\0','SRC_DESTINATION',45,13,14,'\0');
insert into `dyextn_association` (`IDENTIFIER`,`IS_COLLECTION`,`DIRECTION`,`TARGET_ENTITY_ID`,`SOURCE_ROLE_ID`,`TARGET_ROLE_ID`,`IS_SYSTEM_GENERATED`) values (50,'\0','SRC_DESTINATION',48,15,16,'\0');
insert into `dyextn_association` (`IDENTIFIER`,`IS_COLLECTION`,`DIRECTION`,`TARGET_ENTITY_ID`,`SOURCE_ROLE_ID`,`TARGET_ROLE_ID`,`IS_SYSTEM_GENERATED`) values (51,'\0','SRC_DESTINATION',25,17,18,'\0');
insert into `dyextn_association` (`IDENTIFIER`,`IS_COLLECTION`,`DIRECTION`,`TARGET_ENTITY_ID`,`SOURCE_ROLE_ID`,`TARGET_ROLE_ID`,`IS_SYSTEM_GENERATED`) values (60,'\0','SRC_DESTINATION',27,19,20,'\0');
insert into `dyextn_association` (`IDENTIFIER`,`IS_COLLECTION`,`DIRECTION`,`TARGET_ENTITY_ID`,`SOURCE_ROLE_ID`,`TARGET_ROLE_ID`,`IS_SYSTEM_GENERATED`) values (62,'\0','SRC_DESTINATION',8,21,22,'\0');
insert into `dyextn_association` (`IDENTIFIER`,`IS_COLLECTION`,`DIRECTION`,`TARGET_ENTITY_ID`,`SOURCE_ROLE_ID`,`TARGET_ROLE_ID`,`IS_SYSTEM_GENERATED`) values (63,'\0','SRC_DESTINATION',36,23,24,'\0');
insert into `dyextn_association` (`IDENTIFIER`,`IS_COLLECTION`,`DIRECTION`,`TARGET_ENTITY_ID`,`SOURCE_ROLE_ID`,`TARGET_ROLE_ID`,`IS_SYSTEM_GENERATED`) values (65,'\0','SRC_DESTINATION',61,25,26,'\0');
insert into `dyextn_association` (`IDENTIFIER`,`IS_COLLECTION`,`DIRECTION`,`TARGET_ENTITY_ID`,`SOURCE_ROLE_ID`,`TARGET_ROLE_ID`,`IS_SYSTEM_GENERATED`) values (66,'\0','SRC_DESTINATION',61,27,28,'\0');
insert into `dyextn_association` (`IDENTIFIER`,`IS_COLLECTION`,`DIRECTION`,`TARGET_ENTITY_ID`,`SOURCE_ROLE_ID`,`TARGET_ROLE_ID`,`IS_SYSTEM_GENERATED`) values (71,'\0','SRC_DESTINATION',67,29,30,'\0');
insert into `dyextn_association` (`IDENTIFIER`,`IS_COLLECTION`,`DIRECTION`,`TARGET_ENTITY_ID`,`SOURCE_ROLE_ID`,`TARGET_ROLE_ID`,`IS_SYSTEM_GENERATED`) values (72,'\0','SRC_DESTINATION',14,31,32,'\0');
insert into `dyextn_association` (`IDENTIFIER`,`IS_COLLECTION`,`DIRECTION`,`TARGET_ENTITY_ID`,`SOURCE_ROLE_ID`,`TARGET_ROLE_ID`,`IS_SYSTEM_GENERATED`) values (73,'\0','SRC_DESTINATION',37,33,34,'\0');

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

insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (3,2);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (5,4);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (7,6);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (9,8);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (11,10);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (13,12);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (15,14);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (16,14);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (18,17);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (19,14);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (20,14);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (21,14);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (22,14);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (23,14);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (24,14);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (26,25);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (28,27);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (29,27);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (30,27);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (31,27);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (32,27);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (33,27);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (34,27);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (35,27);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (38,37);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (40,39);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (41,37);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (43,42);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (44,37);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (46,45);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (47,37);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (49,48);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (50,37);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (51,37);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (52,37);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (53,37);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (54,37);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (55,37);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (56,37);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (57,37);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (58,37);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (59,37);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (60,37);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (62,61);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (63,61);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (64,61);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (65,37);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (66,37);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (68,67);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (69,67);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (70,67);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (71,37);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (72,37);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (73,36);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (74,36);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (75,36);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (77,76);

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
  CONSTRAINT `FK9B20ED914AC41F7E` FOREIGN KEY (`ENTITY_ID`) REFERENCES `dyextn_entity` (`IDENTIFIER`),
  CONSTRAINT `FK9B20ED914DC2CD16` FOREIGN KEY (`ATTRIBUTE_ID`) REFERENCES `dyextn_primitive_attribute` (`IDENTIFIER`)
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

insert into `dyextn_attribute_type_info` (`IDENTIFIER`,`PRIMITIVE_ATTRIBUTE_ID`) values (1,3);
insert into `dyextn_attribute_type_info` (`IDENTIFIER`,`PRIMITIVE_ATTRIBUTE_ID`) values (2,5);
insert into `dyextn_attribute_type_info` (`IDENTIFIER`,`PRIMITIVE_ATTRIBUTE_ID`) values (3,7);
insert into `dyextn_attribute_type_info` (`IDENTIFIER`,`PRIMITIVE_ATTRIBUTE_ID`) values (4,9);
insert into `dyextn_attribute_type_info` (`IDENTIFIER`,`PRIMITIVE_ATTRIBUTE_ID`) values (5,11);
insert into `dyextn_attribute_type_info` (`IDENTIFIER`,`PRIMITIVE_ATTRIBUTE_ID`) values (6,13);
insert into `dyextn_attribute_type_info` (`IDENTIFIER`,`PRIMITIVE_ATTRIBUTE_ID`) values (7,18);
insert into `dyextn_attribute_type_info` (`IDENTIFIER`,`PRIMITIVE_ATTRIBUTE_ID`) values (8,20);
insert into `dyextn_attribute_type_info` (`IDENTIFIER`,`PRIMITIVE_ATTRIBUTE_ID`) values (9,21);
insert into `dyextn_attribute_type_info` (`IDENTIFIER`,`PRIMITIVE_ATTRIBUTE_ID`) values (10,22);
insert into `dyextn_attribute_type_info` (`IDENTIFIER`,`PRIMITIVE_ATTRIBUTE_ID`) values (11,23);
insert into `dyextn_attribute_type_info` (`IDENTIFIER`,`PRIMITIVE_ATTRIBUTE_ID`) values (12,24);
insert into `dyextn_attribute_type_info` (`IDENTIFIER`,`PRIMITIVE_ATTRIBUTE_ID`) values (13,26);
insert into `dyextn_attribute_type_info` (`IDENTIFIER`,`PRIMITIVE_ATTRIBUTE_ID`) values (14,28);
insert into `dyextn_attribute_type_info` (`IDENTIFIER`,`PRIMITIVE_ATTRIBUTE_ID`) values (15,29);
insert into `dyextn_attribute_type_info` (`IDENTIFIER`,`PRIMITIVE_ATTRIBUTE_ID`) values (16,30);
insert into `dyextn_attribute_type_info` (`IDENTIFIER`,`PRIMITIVE_ATTRIBUTE_ID`) values (17,31);
insert into `dyextn_attribute_type_info` (`IDENTIFIER`,`PRIMITIVE_ATTRIBUTE_ID`) values (18,32);
insert into `dyextn_attribute_type_info` (`IDENTIFIER`,`PRIMITIVE_ATTRIBUTE_ID`) values (19,33);
insert into `dyextn_attribute_type_info` (`IDENTIFIER`,`PRIMITIVE_ATTRIBUTE_ID`) values (20,34);
insert into `dyextn_attribute_type_info` (`IDENTIFIER`,`PRIMITIVE_ATTRIBUTE_ID`) values (21,35);
insert into `dyextn_attribute_type_info` (`IDENTIFIER`,`PRIMITIVE_ATTRIBUTE_ID`) values (22,40);
insert into `dyextn_attribute_type_info` (`IDENTIFIER`,`PRIMITIVE_ATTRIBUTE_ID`) values (23,43);
insert into `dyextn_attribute_type_info` (`IDENTIFIER`,`PRIMITIVE_ATTRIBUTE_ID`) values (24,46);
insert into `dyextn_attribute_type_info` (`IDENTIFIER`,`PRIMITIVE_ATTRIBUTE_ID`) values (25,49);
insert into `dyextn_attribute_type_info` (`IDENTIFIER`,`PRIMITIVE_ATTRIBUTE_ID`) values (26,52);
insert into `dyextn_attribute_type_info` (`IDENTIFIER`,`PRIMITIVE_ATTRIBUTE_ID`) values (27,53);
insert into `dyextn_attribute_type_info` (`IDENTIFIER`,`PRIMITIVE_ATTRIBUTE_ID`) values (28,54);
insert into `dyextn_attribute_type_info` (`IDENTIFIER`,`PRIMITIVE_ATTRIBUTE_ID`) values (29,55);
insert into `dyextn_attribute_type_info` (`IDENTIFIER`,`PRIMITIVE_ATTRIBUTE_ID`) values (30,56);
insert into `dyextn_attribute_type_info` (`IDENTIFIER`,`PRIMITIVE_ATTRIBUTE_ID`) values (31,57);
insert into `dyextn_attribute_type_info` (`IDENTIFIER`,`PRIMITIVE_ATTRIBUTE_ID`) values (32,58);
insert into `dyextn_attribute_type_info` (`IDENTIFIER`,`PRIMITIVE_ATTRIBUTE_ID`) values (33,59);
insert into `dyextn_attribute_type_info` (`IDENTIFIER`,`PRIMITIVE_ATTRIBUTE_ID`) values (34,64);
insert into `dyextn_attribute_type_info` (`IDENTIFIER`,`PRIMITIVE_ATTRIBUTE_ID`) values (35,68);
insert into `dyextn_attribute_type_info` (`IDENTIFIER`,`PRIMITIVE_ATTRIBUTE_ID`) values (36,69);
insert into `dyextn_attribute_type_info` (`IDENTIFIER`,`PRIMITIVE_ATTRIBUTE_ID`) values (37,70);
insert into `dyextn_attribute_type_info` (`IDENTIFIER`,`PRIMITIVE_ATTRIBUTE_ID`) values (38,74);
insert into `dyextn_attribute_type_info` (`IDENTIFIER`,`PRIMITIVE_ATTRIBUTE_ID`) values (39,75);
insert into `dyextn_attribute_type_info` (`IDENTIFIER`,`PRIMITIVE_ATTRIBUTE_ID`) values (40,77);

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

insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (3);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (5);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (7);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (9);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (11);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (13);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (15);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (16);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (18);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (19);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (20);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (21);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (22);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (23);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (24);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (26);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (28);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (29);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (30);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (31);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (32);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (33);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (34);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (35);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (38);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (40);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (41);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (43);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (44);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (46);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (47);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (49);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (50);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (51);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (52);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (53);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (54);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (55);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (56);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (57);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (58);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (59);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (60);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (62);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (63);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (64);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (65);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (66);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (68);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (69);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (70);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (71);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (72);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (73);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (74);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (75);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (77);

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
  CONSTRAINT `FKD33DE81B54A9F59D` FOREIGN KEY (`ROOT_CATEGORY_ELEMENT`) REFERENCES `dyextn_category_entity` (`IDENTIFIER`),
  CONSTRAINT `FKD33DE81B728B19BE` FOREIGN KEY (`IDENTIFIER`) REFERENCES `dyextn_abstract_metadata` (`IDENTIFIER`),
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
  CONSTRAINT `FKEF3B77585CC8694E` FOREIGN KEY (`IDENTIFIER`) REFERENCES `dyextn_base_abstract_attribute` (`IDENTIFIER`),
  CONSTRAINT `FKEF3B77587769A811` FOREIGN KEY (`ABSTRACT_ATTRIBUTE_ID`) REFERENCES `dyextn_attribute` (`IDENTIFIER`),
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
  CONSTRAINT `FK9F2DA8874AC41F7E` FOREIGN KEY (`ENTITY_ID`) REFERENCES `dyextn_entity` (`IDENTIFIER`),
  CONSTRAINT `FK9F2DA887743AC3F2` FOREIGN KEY (`IDENTIFIER`) REFERENCES `dyextn_abstract_entity` (`id`),
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
  CONSTRAINT `FK8FCE2B3F1333996E` FOREIGN KEY (`PRIMITIVE_ATTRIBUTE_ID`) REFERENCES `dyextn_primitive_attribute` (`IDENTIFIER`),
  CONSTRAINT `FK8FCE2B3F3AB6A1D3` FOREIGN KEY (`IDENTIFIER`) REFERENCES `dyextn_database_properties` (`IDENTIFIER`),
  CONSTRAINT `FK8FCE2B3F67F8B59` FOREIGN KEY (`CATEGORY_ATTRIBUTE_ID`) REFERENCES `dyextn_category_attribute` (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `dyextn_column_properties` */

insert into `dyextn_column_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`CATEGORY_ATTRIBUTE_ID`,`PRIMITIVE_ATTRIBUTE_ID`) values (2,NULL,NULL,3);
insert into `dyextn_column_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`CATEGORY_ATTRIBUTE_ID`,`PRIMITIVE_ATTRIBUTE_ID`) values (4,NULL,NULL,5);
insert into `dyextn_column_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`CATEGORY_ATTRIBUTE_ID`,`PRIMITIVE_ATTRIBUTE_ID`) values (6,NULL,NULL,7);
insert into `dyextn_column_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`CATEGORY_ATTRIBUTE_ID`,`PRIMITIVE_ATTRIBUTE_ID`) values (8,NULL,NULL,9);
insert into `dyextn_column_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`CATEGORY_ATTRIBUTE_ID`,`PRIMITIVE_ATTRIBUTE_ID`) values (10,NULL,NULL,11);
insert into `dyextn_column_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`CATEGORY_ATTRIBUTE_ID`,`PRIMITIVE_ATTRIBUTE_ID`) values (12,NULL,NULL,13);
insert into `dyextn_column_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`CATEGORY_ATTRIBUTE_ID`,`PRIMITIVE_ATTRIBUTE_ID`) values (17,NULL,NULL,18);
insert into `dyextn_column_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`CATEGORY_ATTRIBUTE_ID`,`PRIMITIVE_ATTRIBUTE_ID`) values (19,NULL,NULL,20);
insert into `dyextn_column_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`CATEGORY_ATTRIBUTE_ID`,`PRIMITIVE_ATTRIBUTE_ID`) values (20,NULL,NULL,21);
insert into `dyextn_column_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`CATEGORY_ATTRIBUTE_ID`,`PRIMITIVE_ATTRIBUTE_ID`) values (21,NULL,NULL,22);
insert into `dyextn_column_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`CATEGORY_ATTRIBUTE_ID`,`PRIMITIVE_ATTRIBUTE_ID`) values (22,NULL,NULL,23);
insert into `dyextn_column_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`CATEGORY_ATTRIBUTE_ID`,`PRIMITIVE_ATTRIBUTE_ID`) values (23,NULL,NULL,24);
insert into `dyextn_column_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`CATEGORY_ATTRIBUTE_ID`,`PRIMITIVE_ATTRIBUTE_ID`) values (25,NULL,NULL,26);
insert into `dyextn_column_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`CATEGORY_ATTRIBUTE_ID`,`PRIMITIVE_ATTRIBUTE_ID`) values (27,NULL,NULL,28);
insert into `dyextn_column_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`CATEGORY_ATTRIBUTE_ID`,`PRIMITIVE_ATTRIBUTE_ID`) values (28,NULL,NULL,29);
insert into `dyextn_column_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`CATEGORY_ATTRIBUTE_ID`,`PRIMITIVE_ATTRIBUTE_ID`) values (29,NULL,NULL,30);
insert into `dyextn_column_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`CATEGORY_ATTRIBUTE_ID`,`PRIMITIVE_ATTRIBUTE_ID`) values (30,NULL,NULL,31);
insert into `dyextn_column_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`CATEGORY_ATTRIBUTE_ID`,`PRIMITIVE_ATTRIBUTE_ID`) values (31,NULL,NULL,32);
insert into `dyextn_column_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`CATEGORY_ATTRIBUTE_ID`,`PRIMITIVE_ATTRIBUTE_ID`) values (32,NULL,NULL,33);
insert into `dyextn_column_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`CATEGORY_ATTRIBUTE_ID`,`PRIMITIVE_ATTRIBUTE_ID`) values (33,NULL,NULL,34);
insert into `dyextn_column_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`CATEGORY_ATTRIBUTE_ID`,`PRIMITIVE_ATTRIBUTE_ID`) values (34,NULL,NULL,35);
insert into `dyextn_column_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`CATEGORY_ATTRIBUTE_ID`,`PRIMITIVE_ATTRIBUTE_ID`) values (39,NULL,NULL,40);
insert into `dyextn_column_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`CATEGORY_ATTRIBUTE_ID`,`PRIMITIVE_ATTRIBUTE_ID`) values (42,NULL,NULL,43);
insert into `dyextn_column_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`CATEGORY_ATTRIBUTE_ID`,`PRIMITIVE_ATTRIBUTE_ID`) values (45,NULL,NULL,46);
insert into `dyextn_column_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`CATEGORY_ATTRIBUTE_ID`,`PRIMITIVE_ATTRIBUTE_ID`) values (48,NULL,NULL,49);
insert into `dyextn_column_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`CATEGORY_ATTRIBUTE_ID`,`PRIMITIVE_ATTRIBUTE_ID`) values (51,NULL,NULL,52);
insert into `dyextn_column_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`CATEGORY_ATTRIBUTE_ID`,`PRIMITIVE_ATTRIBUTE_ID`) values (52,NULL,NULL,53);
insert into `dyextn_column_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`CATEGORY_ATTRIBUTE_ID`,`PRIMITIVE_ATTRIBUTE_ID`) values (53,NULL,NULL,54);
insert into `dyextn_column_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`CATEGORY_ATTRIBUTE_ID`,`PRIMITIVE_ATTRIBUTE_ID`) values (54,NULL,NULL,55);
insert into `dyextn_column_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`CATEGORY_ATTRIBUTE_ID`,`PRIMITIVE_ATTRIBUTE_ID`) values (55,NULL,NULL,56);
insert into `dyextn_column_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`CATEGORY_ATTRIBUTE_ID`,`PRIMITIVE_ATTRIBUTE_ID`) values (56,NULL,NULL,57);
insert into `dyextn_column_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`CATEGORY_ATTRIBUTE_ID`,`PRIMITIVE_ATTRIBUTE_ID`) values (57,NULL,NULL,58);
insert into `dyextn_column_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`CATEGORY_ATTRIBUTE_ID`,`PRIMITIVE_ATTRIBUTE_ID`) values (58,NULL,NULL,59);
insert into `dyextn_column_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`CATEGORY_ATTRIBUTE_ID`,`PRIMITIVE_ATTRIBUTE_ID`) values (63,NULL,NULL,64);
insert into `dyextn_column_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`CATEGORY_ATTRIBUTE_ID`,`PRIMITIVE_ATTRIBUTE_ID`) values (67,NULL,NULL,68);
insert into `dyextn_column_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`CATEGORY_ATTRIBUTE_ID`,`PRIMITIVE_ATTRIBUTE_ID`) values (68,NULL,NULL,69);
insert into `dyextn_column_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`CATEGORY_ATTRIBUTE_ID`,`PRIMITIVE_ATTRIBUTE_ID`) values (69,NULL,NULL,70);
insert into `dyextn_column_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`CATEGORY_ATTRIBUTE_ID`,`PRIMITIVE_ATTRIBUTE_ID`) values (73,NULL,NULL,74);
insert into `dyextn_column_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`CATEGORY_ATTRIBUTE_ID`,`PRIMITIVE_ATTRIBUTE_ID`) values (74,NULL,NULL,75);
insert into `dyextn_column_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`CATEGORY_ATTRIBUTE_ID`,`PRIMITIVE_ATTRIBUTE_ID`) values (76,NULL,NULL,77);

/*Table structure for table `dyextn_combobox` */

DROP TABLE IF EXISTS `dyextn_combobox`;

CREATE TABLE `dyextn_combobox` (
  `IDENTIFIER` bigint(20) NOT NULL,
  PRIMARY KEY  (`IDENTIFIER`),
  KEY `FKABBC649ABF67AB26` (`IDENTIFIER`),
  CONSTRAINT `FKABBC649ABF67AB26` FOREIGN KEY (`IDENTIFIER`) REFERENCES `dyextn_select_control` (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `dyextn_combobox` */

insert into `dyextn_combobox` (`IDENTIFIER`) values (5);
insert into `dyextn_combobox` (`IDENTIFIER`) values (6);
insert into `dyextn_combobox` (`IDENTIFIER`) values (7);
insert into `dyextn_combobox` (`IDENTIFIER`) values (8);
insert into `dyextn_combobox` (`IDENTIFIER`) values (9);
insert into `dyextn_combobox` (`IDENTIFIER`) values (10);
insert into `dyextn_combobox` (`IDENTIFIER`) values (11);
insert into `dyextn_combobox` (`IDENTIFIER`) values (12);
insert into `dyextn_combobox` (`IDENTIFIER`) values (13);
insert into `dyextn_combobox` (`IDENTIFIER`) values (14);
insert into `dyextn_combobox` (`IDENTIFIER`) values (15);
insert into `dyextn_combobox` (`IDENTIFIER`) values (16);
insert into `dyextn_combobox` (`IDENTIFIER`) values (17);
insert into `dyextn_combobox` (`IDENTIFIER`) values (18);
insert into `dyextn_combobox` (`IDENTIFIER`) values (19);
insert into `dyextn_combobox` (`IDENTIFIER`) values (20);
insert into `dyextn_combobox` (`IDENTIFIER`) values (29);

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

insert into `dyextn_constraint_properties` (`IDENTIFIER`,`SOURCE_ENTITY_KEY`,`TARGET_ENTITY_KEY`,`SRC_CONSTRAINT_NAME`,`TARGET_CONSTRAINT_NAME`,`CATEGORY_ASSOCIATION_ID`,`ASSOCIATION_ID`) values (14,'DE_E_S_179',NULL,'CONSRT_181','CONSRT_182',NULL,15);
insert into `dyextn_constraint_properties` (`IDENTIFIER`,`SOURCE_ENTITY_KEY`,`TARGET_ENTITY_KEY`,`SRC_CONSTRAINT_NAME`,`TARGET_CONSTRAINT_NAME`,`CATEGORY_ASSOCIATION_ID`,`ASSOCIATION_ID`) values (15,'DE_E_S_164',NULL,'CONSRT_166','CONSRT_167',NULL,16);
insert into `dyextn_constraint_properties` (`IDENTIFIER`,`SOURCE_ENTITY_KEY`,`TARGET_ENTITY_KEY`,`SRC_CONSTRAINT_NAME`,`TARGET_CONSTRAINT_NAME`,`CATEGORY_ASSOCIATION_ID`,`ASSOCIATION_ID`) values (18,'DE_E_S_149',NULL,'CONSRT_151','CONSRT_152',NULL,19);
insert into `dyextn_constraint_properties` (`IDENTIFIER`,`SOURCE_ENTITY_KEY`,`TARGET_ENTITY_KEY`,`SRC_CONSTRAINT_NAME`,`TARGET_CONSTRAINT_NAME`,`CATEGORY_ASSOCIATION_ID`,`ASSOCIATION_ID`) values (37,'DE_E_S_169','DE_E_T_170','CONSRT_171','CONSRT_172',NULL,38);
insert into `dyextn_constraint_properties` (`IDENTIFIER`,`SOURCE_ENTITY_KEY`,`TARGET_ENTITY_KEY`,`SRC_CONSTRAINT_NAME`,`TARGET_CONSTRAINT_NAME`,`CATEGORY_ASSOCIATION_ID`,`ASSOCIATION_ID`) values (40,'DE_E_S_159',NULL,'CONSRT_161','CONSRT_162',NULL,41);
insert into `dyextn_constraint_properties` (`IDENTIFIER`,`SOURCE_ENTITY_KEY`,`TARGET_ENTITY_KEY`,`SRC_CONSTRAINT_NAME`,`TARGET_CONSTRAINT_NAME`,`CATEGORY_ASSOCIATION_ID`,`ASSOCIATION_ID`) values (43,'DE_E_S_154',NULL,'CONSRT_156','CONSRT_157',NULL,44);
insert into `dyextn_constraint_properties` (`IDENTIFIER`,`SOURCE_ENTITY_KEY`,`TARGET_ENTITY_KEY`,`SRC_CONSTRAINT_NAME`,`TARGET_CONSTRAINT_NAME`,`CATEGORY_ASSOCIATION_ID`,`ASSOCIATION_ID`) values (46,'DE_E_S_144',NULL,'CONSRT_146','CONSRT_147',NULL,47);
insert into `dyextn_constraint_properties` (`IDENTIFIER`,`SOURCE_ENTITY_KEY`,`TARGET_ENTITY_KEY`,`SRC_CONSTRAINT_NAME`,`TARGET_CONSTRAINT_NAME`,`CATEGORY_ASSOCIATION_ID`,`ASSOCIATION_ID`) values (49,'DE_E_S_139','DE_E_T_140','CONSRT_141','CONSRT_142',NULL,50);
insert into `dyextn_constraint_properties` (`IDENTIFIER`,`SOURCE_ENTITY_KEY`,`TARGET_ENTITY_KEY`,`SRC_CONSTRAINT_NAME`,`TARGET_CONSTRAINT_NAME`,`CATEGORY_ASSOCIATION_ID`,`ASSOCIATION_ID`) values (50,'DE_E_S_134',NULL,'CONSRT_136','CONSRT_137',NULL,51);
insert into `dyextn_constraint_properties` (`IDENTIFIER`,`SOURCE_ENTITY_KEY`,`TARGET_ENTITY_KEY`,`SRC_CONSTRAINT_NAME`,`TARGET_CONSTRAINT_NAME`,`CATEGORY_ASSOCIATION_ID`,`ASSOCIATION_ID`) values (59,NULL,'DE_E_T_110','CONSRT_111','CONSRT_112',NULL,60);
insert into `dyextn_constraint_properties` (`IDENTIFIER`,`SOURCE_ENTITY_KEY`,`TARGET_ENTITY_KEY`,`SRC_CONSTRAINT_NAME`,`TARGET_CONSTRAINT_NAME`,`CATEGORY_ASSOCIATION_ID`,`ASSOCIATION_ID`) values (61,'DE_E_S_174',NULL,'CONSRT_176','CONSRT_177',NULL,62);
insert into `dyextn_constraint_properties` (`IDENTIFIER`,`SOURCE_ENTITY_KEY`,`TARGET_ENTITY_KEY`,`SRC_CONSTRAINT_NAME`,`TARGET_CONSTRAINT_NAME`,`CATEGORY_ASSOCIATION_ID`,`ASSOCIATION_ID`) values (62,'DE_E_S_99',NULL,'CONSRT_101','CONSRT_102',NULL,63);
insert into `dyextn_constraint_properties` (`IDENTIFIER`,`SOURCE_ENTITY_KEY`,`TARGET_ENTITY_KEY`,`SRC_CONSTRAINT_NAME`,`TARGET_CONSTRAINT_NAME`,`CATEGORY_ASSOCIATION_ID`,`ASSOCIATION_ID`) values (64,'DE_E_S_114','DE_E_T_115','CONSRT_116','CONSRT_117',NULL,65);
insert into `dyextn_constraint_properties` (`IDENTIFIER`,`SOURCE_ENTITY_KEY`,`TARGET_ENTITY_KEY`,`SRC_CONSTRAINT_NAME`,`TARGET_CONSTRAINT_NAME`,`CATEGORY_ASSOCIATION_ID`,`ASSOCIATION_ID`) values (65,'DE_E_S_119',NULL,'CONSRT_121','CONSRT_122',NULL,66);
insert into `dyextn_constraint_properties` (`IDENTIFIER`,`SOURCE_ENTITY_KEY`,`TARGET_ENTITY_KEY`,`SRC_CONSTRAINT_NAME`,`TARGET_CONSTRAINT_NAME`,`CATEGORY_ASSOCIATION_ID`,`ASSOCIATION_ID`) values (70,'DE_E_S_124','DE_E_T_125','CONSRT_126','CONSRT_127',NULL,71);
insert into `dyextn_constraint_properties` (`IDENTIFIER`,`SOURCE_ENTITY_KEY`,`TARGET_ENTITY_KEY`,`SRC_CONSTRAINT_NAME`,`TARGET_CONSTRAINT_NAME`,`CATEGORY_ASSOCIATION_ID`,`ASSOCIATION_ID`) values (71,NULL,'DE_E_T_130','CONSRT_131','CONSRT_132',NULL,72);
insert into `dyextn_constraint_properties` (`IDENTIFIER`,`SOURCE_ENTITY_KEY`,`TARGET_ENTITY_KEY`,`SRC_CONSTRAINT_NAME`,`TARGET_CONSTRAINT_NAME`,`CATEGORY_ASSOCIATION_ID`,`ASSOCIATION_ID`) values (72,NULL,'DE_E_T_105','CONSRT_106','CONSRT_107',NULL,73);

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
  CONSTRAINT `FK1EAB84E4178865E` FOREIGN KEY (`VIEW_ID`) REFERENCES `dyextn_view` (`IDENTIFIER`),
  CONSTRAINT `FK1EAB84E41DCC9E63` FOREIGN KEY (`ABSTRACT_ENTITY_ID`) REFERENCES `dyextn_abstract_entity` (`id`),
  CONSTRAINT `FK1EAB84E488C075EF` FOREIGN KEY (`ENTITY_GROUP_ID`) REFERENCES `dyextn_entity_group` (`IDENTIFIER`),
  CONSTRAINT `FK1EAB84E4BF901C84` FOREIGN KEY (`BASE_CONTAINER_ID`) REFERENCES `dyextn_container` (`IDENTIFIER`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=latin1;

/*Data for the table `dyextn_container` */

insert into `dyextn_container` (`IDENTIFIER`,`BUTTON_CSS`,`CAPTION`,`ABSTRACT_ENTITY_ID`,`MAIN_TABLE_CSS`,`REQUIRED_FIELD_INDICATOR`,`REQUIRED_FIELD_WARNING_MESSAGE`,`TITLE_CSS`,`BASE_CONTAINER_ID`,`ADD_CAPTION`,`ENTITY_GROUP_ID`,`VIEW_ID`) values (1,NULL,'ResearchOptOut',76,NULL,'*','indicates required fields.',NULL,NULL,'',1,NULL);
insert into `dyextn_container` (`IDENTIFIER`,`BUTTON_CSS`,`CAPTION`,`ABSTRACT_ENTITY_ID`,`MAIN_TABLE_CSS`,`REQUIRED_FIELD_INDICATOR`,`REQUIRED_FIELD_WARNING_MESSAGE`,`TITLE_CSS`,`BASE_CONTAINER_ID`,`ADD_CAPTION`,`ENTITY_GROUP_ID`,`VIEW_ID`) values (2,NULL,'PhoneType',6,NULL,'*','indicates required fields.',NULL,NULL,'',1,NULL);
insert into `dyextn_container` (`IDENTIFIER`,`BUTTON_CSS`,`CAPTION`,`ABSTRACT_ENTITY_ID`,`MAIN_TABLE_CSS`,`REQUIRED_FIELD_INDICATOR`,`REQUIRED_FIELD_WARNING_MESSAGE`,`TITLE_CSS`,`BASE_CONTAINER_ID`,`ADD_CAPTION`,`ENTITY_GROUP_ID`,`VIEW_ID`) values (3,NULL,'ActiveUpiFlag',2,NULL,'*','indicates required fields.',NULL,NULL,'',1,NULL);
insert into `dyextn_container` (`IDENTIFIER`,`BUTTON_CSS`,`CAPTION`,`ABSTRACT_ENTITY_ID`,`MAIN_TABLE_CSS`,`REQUIRED_FIELD_INDICATOR`,`REQUIRED_FIELD_WARNING_MESSAGE`,`TITLE_CSS`,`BASE_CONTAINER_ID`,`ADD_CAPTION`,`ENTITY_GROUP_ID`,`VIEW_ID`) values (4,NULL,'State',4,NULL,'*','indicates required fields.',NULL,NULL,'',1,NULL);
insert into `dyextn_container` (`IDENTIFIER`,`BUTTON_CSS`,`CAPTION`,`ABSTRACT_ENTITY_ID`,`MAIN_TABLE_CSS`,`REQUIRED_FIELD_INDICATOR`,`REQUIRED_FIELD_WARNING_MESSAGE`,`TITLE_CSS`,`BASE_CONTAINER_ID`,`ADD_CAPTION`,`ENTITY_GROUP_ID`,`VIEW_ID`) values (5,NULL,'Religion',45,NULL,'*','indicates required fields.',NULL,NULL,'',1,NULL);
insert into `dyextn_container` (`IDENTIFIER`,`BUTTON_CSS`,`CAPTION`,`ABSTRACT_ENTITY_ID`,`MAIN_TABLE_CSS`,`REQUIRED_FIELD_INDICATOR`,`REQUIRED_FIELD_WARNING_MESSAGE`,`TITLE_CSS`,`BASE_CONTAINER_ID`,`ADD_CAPTION`,`ENTITY_GROUP_ID`,`VIEW_ID`) values (6,NULL,'RelationToPerson',8,NULL,'*','indicates required fields.',NULL,NULL,'',1,NULL);
insert into `dyextn_container` (`IDENTIFIER`,`BUTTON_CSS`,`CAPTION`,`ABSTRACT_ENTITY_ID`,`MAIN_TABLE_CSS`,`REQUIRED_FIELD_INDICATOR`,`REQUIRED_FIELD_WARNING_MESSAGE`,`TITLE_CSS`,`BASE_CONTAINER_ID`,`ADD_CAPTION`,`ENTITY_GROUP_ID`,`VIEW_ID`) values (7,NULL,'Address',14,NULL,'*','indicates required fields.',NULL,NULL,'',1,NULL);
insert into `dyextn_container` (`IDENTIFIER`,`BUTTON_CSS`,`CAPTION`,`ABSTRACT_ENTITY_ID`,`MAIN_TABLE_CSS`,`REQUIRED_FIELD_INDICATOR`,`REQUIRED_FIELD_WARNING_MESSAGE`,`TITLE_CSS`,`BASE_CONTAINER_ID`,`ADD_CAPTION`,`ENTITY_GROUP_ID`,`VIEW_ID`) values (8,NULL,'AddressType',12,NULL,'*','indicates required fields.',NULL,NULL,'',1,NULL);
insert into `dyextn_container` (`IDENTIFIER`,`BUTTON_CSS`,`CAPTION`,`ABSTRACT_ENTITY_ID`,`MAIN_TABLE_CSS`,`REQUIRED_FIELD_INDICATOR`,`REQUIRED_FIELD_WARNING_MESSAGE`,`TITLE_CSS`,`BASE_CONTAINER_ID`,`ADD_CAPTION`,`ENTITY_GROUP_ID`,`VIEW_ID`) values (9,NULL,'AdvancedDirectiveExists',42,NULL,'*','indicates required fields.',NULL,NULL,'',1,NULL);
insert into `dyextn_container` (`IDENTIFIER`,`BUTTON_CSS`,`CAPTION`,`ABSTRACT_ENTITY_ID`,`MAIN_TABLE_CSS`,`REQUIRED_FIELD_INDICATOR`,`REQUIRED_FIELD_WARNING_MESSAGE`,`TITLE_CSS`,`BASE_CONTAINER_ID`,`ADD_CAPTION`,`ENTITY_GROUP_ID`,`VIEW_ID`) values (10,NULL,'AssociatedPerson',61,NULL,'*','indicates required fields.',NULL,NULL,'',1,NULL);
insert into `dyextn_container` (`IDENTIFIER`,`BUTTON_CSS`,`CAPTION`,`ABSTRACT_ENTITY_ID`,`MAIN_TABLE_CSS`,`REQUIRED_FIELD_INDICATOR`,`REQUIRED_FIELD_WARNING_MESSAGE`,`TITLE_CSS`,`BASE_CONTAINER_ID`,`ADD_CAPTION`,`ENTITY_GROUP_ID`,`VIEW_ID`) values (11,NULL,'Country',17,NULL,'*','indicates required fields.',NULL,NULL,'',1,NULL);
insert into `dyextn_container` (`IDENTIFIER`,`BUTTON_CSS`,`CAPTION`,`ABSTRACT_ENTITY_ID`,`MAIN_TABLE_CSS`,`REQUIRED_FIELD_INDICATOR`,`REQUIRED_FIELD_WARNING_MESSAGE`,`TITLE_CSS`,`BASE_CONTAINER_ID`,`ADD_CAPTION`,`ENTITY_GROUP_ID`,`VIEW_ID`) values (12,NULL,'Demographics',37,NULL,'*','indicates required fields.',NULL,NULL,'',1,NULL);
insert into `dyextn_container` (`IDENTIFIER`,`BUTTON_CSS`,`CAPTION`,`ABSTRACT_ENTITY_ID`,`MAIN_TABLE_CSS`,`REQUIRED_FIELD_INDICATOR`,`REQUIRED_FIELD_WARNING_MESSAGE`,`TITLE_CSS`,`BASE_CONTAINER_ID`,`ADD_CAPTION`,`ENTITY_GROUP_ID`,`VIEW_ID`) values (13,NULL,'EthnicOrigin',10,NULL,'*','indicates required fields.',NULL,NULL,'',1,NULL);
insert into `dyextn_container` (`IDENTIFIER`,`BUTTON_CSS`,`CAPTION`,`ABSTRACT_ENTITY_ID`,`MAIN_TABLE_CSS`,`REQUIRED_FIELD_INDICATOR`,`REQUIRED_FIELD_WARNING_MESSAGE`,`TITLE_CSS`,`BASE_CONTAINER_ID`,`ADD_CAPTION`,`ENTITY_GROUP_ID`,`VIEW_ID`) values (14,NULL,'Gender',39,NULL,'*','indicates required fields.',NULL,NULL,'',1,NULL);
insert into `dyextn_container` (`IDENTIFIER`,`BUTTON_CSS`,`CAPTION`,`ABSTRACT_ENTITY_ID`,`MAIN_TABLE_CSS`,`REQUIRED_FIELD_INDICATOR`,`REQUIRED_FIELD_WARNING_MESSAGE`,`TITLE_CSS`,`BASE_CONTAINER_ID`,`ADD_CAPTION`,`ENTITY_GROUP_ID`,`VIEW_ID`) values (15,NULL,'MaritalStatus',25,NULL,'*','indicates required fields.',NULL,NULL,'',1,NULL);
insert into `dyextn_container` (`IDENTIFIER`,`BUTTON_CSS`,`CAPTION`,`ABSTRACT_ENTITY_ID`,`MAIN_TABLE_CSS`,`REQUIRED_FIELD_INDICATOR`,`REQUIRED_FIELD_WARNING_MESSAGE`,`TITLE_CSS`,`BASE_CONTAINER_ID`,`ADD_CAPTION`,`ENTITY_GROUP_ID`,`VIEW_ID`) values (16,NULL,'Person',36,NULL,'*','indicates required fields.',NULL,NULL,'',1,NULL);
insert into `dyextn_container` (`IDENTIFIER`,`BUTTON_CSS`,`CAPTION`,`ABSTRACT_ENTITY_ID`,`MAIN_TABLE_CSS`,`REQUIRED_FIELD_INDICATOR`,`REQUIRED_FIELD_WARNING_MESSAGE`,`TITLE_CSS`,`BASE_CONTAINER_ID`,`ADD_CAPTION`,`ENTITY_GROUP_ID`,`VIEW_ID`) values (17,NULL,'PersonName',27,NULL,'*','indicates required fields.',NULL,NULL,'',1,NULL);
insert into `dyextn_container` (`IDENTIFIER`,`BUTTON_CSS`,`CAPTION`,`ABSTRACT_ENTITY_ID`,`MAIN_TABLE_CSS`,`REQUIRED_FIELD_INDICATOR`,`REQUIRED_FIELD_WARNING_MESSAGE`,`TITLE_CSS`,`BASE_CONTAINER_ID`,`ADD_CAPTION`,`ENTITY_GROUP_ID`,`VIEW_ID`) values (18,NULL,'Phone',67,NULL,'*','indicates required fields.',NULL,NULL,'',1,NULL);
insert into `dyextn_container` (`IDENTIFIER`,`BUTTON_CSS`,`CAPTION`,`ABSTRACT_ENTITY_ID`,`MAIN_TABLE_CSS`,`REQUIRED_FIELD_INDICATOR`,`REQUIRED_FIELD_WARNING_MESSAGE`,`TITLE_CSS`,`BASE_CONTAINER_ID`,`ADD_CAPTION`,`ENTITY_GROUP_ID`,`VIEW_ID`) values (19,NULL,'Race',48,NULL,'*','indicates required fields.',NULL,NULL,'',1,NULL);

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

insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (1,'line1',NULL,NULL,'line1',7,NULL,7,23,'\0');
insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (2,'line2',NULL,NULL,'line2',6,NULL,7,22,'\0');
insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (3,'city',NULL,NULL,'city',5,NULL,7,21,'\0');
insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (4,'postalCode',NULL,NULL,'postalCode',4,NULL,7,20,'\0');
insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (5,'AssociationName_1',NULL,NULL,'AssociationName_1',3,NULL,7,19,'\0');
insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (6,'AssociationName_2',NULL,NULL,'AssociationName_2',2,NULL,7,16,'\0');
insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (7,'AssociationName_3',NULL,NULL,'AssociationName_3',1,NULL,7,15,'\0');
insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (8,'AssociationName_1',NULL,NULL,'AssociationName_1',2,NULL,10,63,'\0');
insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (9,'AssociationName_2',NULL,NULL,'AssociationName_2',1,NULL,10,62,'\0');
insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (10,'AssociationName_5',NULL,NULL,'AssociationName_5',18,NULL,12,72,'\0');
insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (11,'AssociationName_4',NULL,NULL,'AssociationName_4',17,NULL,12,71,'\0');
insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (12,'AssociationName_3',NULL,NULL,'AssociationName_3',16,NULL,12,66,'\0');
insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (13,'AssociationName_2',NULL,NULL,'AssociationName_2',15,NULL,12,65,'\0');
insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (14,'AssociationName_1',NULL,NULL,'AssociationName_1',14,NULL,12,60,'\0');
insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (15,'AssociationName_11',NULL,NULL,'AssociationName_11',1,NULL,12,38,'\0');
insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (16,'AssociationName_10',NULL,NULL,'AssociationName_10',2,NULL,12,41,'\0');
insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (17,'AssociationName_9',NULL,NULL,'AssociationName_9',3,NULL,12,44,'\0');
insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (18,'AssociationName_8',NULL,NULL,'AssociationName_8',4,NULL,12,47,'\0');
insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (19,'AssociationName_7',NULL,NULL,'AssociationName_7',5,NULL,12,50,'\0');
insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (20,'AssociationName_6',NULL,NULL,'AssociationName_6',6,NULL,12,51,'\0');
insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (21,'dateOfBirth',NULL,NULL,'dateOfBirth',7,NULL,12,53,'\0');
insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (22,'dateOfDeath',NULL,NULL,'dateOfDeath',8,NULL,12,54,'\0');
insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (23,'mothersMaidenName',NULL,NULL,'mothersMaidenName',9,NULL,12,55,'\0');
insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (24,'placeOfBirth',NULL,NULL,'placeOfBirth',10,NULL,12,56,'\0');
insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (25,'socialSecurityNumber',NULL,NULL,'socialSecurityNumber',11,NULL,12,57,'\0');
insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (26,'effectiveStartTimeStamp',NULL,NULL,'effectiveStartTimeStamp',12,NULL,12,58,'\0');
insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (27,'effectiveEndTimeStamp',NULL,NULL,'effectiveEndTimeStamp',13,NULL,12,59,'\0');
insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (28,'personUpi',NULL,NULL,'personUpi',2,NULL,16,74,'\0');
insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (29,'AssociationName_1',NULL,NULL,'AssociationName_1',1,NULL,16,73,'\0');
insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (30,'firstName',NULL,NULL,'firstName',7,NULL,17,34,'\0');
insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (31,'middleName',NULL,NULL,'middleName',6,NULL,17,33,'\0');
insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (32,'lastName',NULL,NULL,'lastName',5,NULL,17,32,'\0');
insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (33,'lastNameCompressed',NULL,NULL,'lastNameCompressed',4,NULL,17,31,'\0');
insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (34,'degree',NULL,NULL,'degree',3,NULL,17,30,'\0');
insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (35,'prefix',NULL,NULL,'prefix',2,NULL,17,29,'\0');
insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (36,'suffix',NULL,NULL,'suffix',1,NULL,17,28,'\0');
insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (37,'number',NULL,NULL,'number',2,NULL,18,69,'\0');
insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (38,'type',NULL,NULL,'type',1,NULL,18,68,'\0');

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
) ENGINE=InnoDB AUTO_INCREMENT=77 DEFAULT CHARSET=latin1;

/*Data for the table `dyextn_database_properties` */

insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (1,'DE_E_94');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (2,'IDENTIFIER');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (3,'DE_E_90');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (4,'IDENTIFIER');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (5,'DE_E_86');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (6,'IDENTIFIER');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (7,'DE_E_82');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (8,'IDENTIFIER');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (9,'DE_E_78');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (10,'IDENTIFIER');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (11,'DE_E_74');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (12,'IDENTIFIER');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (13,'DE_E_1');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (14,'DE_AS_178');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (15,'DE_AS_163');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (16,'DE_E_62');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (17,'IDENTIFIER');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (18,'DE_AS_148');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (19,'DE_AT_8');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (20,'DE_AT_7');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (21,'DE_AT_6');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (22,'DE_AT_5');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (23,'IDENTIFIER');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (24,'DE_E_9');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (25,'IDENTIFIER');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (26,'DE_E_13');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (27,'DE_AT_23');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (28,'DE_AT_22');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (29,'DE_AT_21');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (30,'DE_AT_20');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (31,'DE_AT_19');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (32,'DE_AT_18');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (33,'DE_AT_17');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (34,'IDENTIFIER');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (35,'DE_E_24');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (36,'DE_E_39');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (37,'DE_AS_168');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (38,'DE_E_70');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (39,'IDENTIFIER');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (40,'DE_AS_158');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (41,'DE_E_66');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (42,'IDENTIFIER');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (43,'DE_AS_153');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (44,'DE_E_58');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (45,'IDENTIFIER');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (46,'DE_AS_143');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (47,'DE_E_50');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (48,'IDENTIFIER');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (49,'DE_AS_138');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (50,'DE_AS_133');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (51,'IDENTIFIER');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (52,'DE_AT_43');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (53,'DE_AT_44');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (54,'DE_AT_45');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (55,'DE_AT_46');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (56,'DE_AT_47');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (57,'DE_AT_48');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (58,'DE_AT_49');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (59,'DE_AS_108');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (60,'DE_E_29');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (61,'DE_AS_173');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (62,'DE_AS_98');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (63,'IDENTIFIER');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (64,'DE_AS_113');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (65,'DE_AS_118');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (66,'DE_E_33');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (67,'DE_AT_38');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (68,'DE_AT_37');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (69,'IDENTIFIER');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (70,'DE_AS_123');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (71,'DE_AS_128');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (72,'DE_AS_103');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (73,'DE_AT_28');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (74,'IDENTIFIER');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (75,'DE_E_54');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (76,'IDENTIFIER');

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

insert into `dyextn_date_concept_value` (`IDENTIFIER`,`VALUE`) values (12,NULL);
insert into `dyextn_date_concept_value` (`IDENTIFIER`,`VALUE`) values (13,NULL);
insert into `dyextn_date_concept_value` (`IDENTIFIER`,`VALUE`) values (17,NULL);
insert into `dyextn_date_concept_value` (`IDENTIFIER`,`VALUE`) values (18,NULL);

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

insert into `dyextn_date_type_info` (`IDENTIFIER`,`FORMAT`) values (27,'MM-dd-yyyy');
insert into `dyextn_date_type_info` (`IDENTIFIER`,`FORMAT`) values (28,'MM-dd-yyyy');
insert into `dyextn_date_type_info` (`IDENTIFIER`,`FORMAT`) values (32,'MM-dd-yyyy');
insert into `dyextn_date_type_info` (`IDENTIFIER`,`FORMAT`) values (33,'MM-dd-yyyy');

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

insert into `dyextn_datepicker` (`IDENTIFIER`,`DATE_VALUE_TYPE`) values (21,NULL);
insert into `dyextn_datepicker` (`IDENTIFIER`,`DATE_VALUE_TYPE`) values (22,NULL);
insert into `dyextn_datepicker` (`IDENTIFIER`,`DATE_VALUE_TYPE`) values (26,NULL);
insert into `dyextn_datepicker` (`IDENTIFIER`,`DATE_VALUE_TYPE`) values (27,NULL);

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
  CONSTRAINT `FK8B2436402264D629` FOREIGN KEY (`PARENT_ENTITY_ID`) REFERENCES `dyextn_entity` (`IDENTIFIER`),
  CONSTRAINT `FK8B243640743AC3F2` FOREIGN KEY (`IDENTIFIER`) REFERENCES `dyextn_abstract_entity` (`id`),
  CONSTRAINT `FK8B24364088C075EF` FOREIGN KEY (`ENTITY_GROUP_ID`) REFERENCES `dyextn_entity_group` (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `dyextn_entity` */

insert into `dyextn_entity` (`IDENTIFIER`,`DATA_TABLE_STATE`,`ENTITY_GROUP_ID`,`IS_ABSTRACT`,`PARENT_ENTITY_ID`,`INHERITANCE_STRATEGY`,`DISCRIMINATOR_COLUMN_NAME`,`DISCRIMINATOR_VALUE`) values (2,2,1,'\0',NULL,3,NULL,NULL);
insert into `dyextn_entity` (`IDENTIFIER`,`DATA_TABLE_STATE`,`ENTITY_GROUP_ID`,`IS_ABSTRACT`,`PARENT_ENTITY_ID`,`INHERITANCE_STRATEGY`,`DISCRIMINATOR_COLUMN_NAME`,`DISCRIMINATOR_VALUE`) values (4,2,1,'\0',NULL,3,NULL,NULL);
insert into `dyextn_entity` (`IDENTIFIER`,`DATA_TABLE_STATE`,`ENTITY_GROUP_ID`,`IS_ABSTRACT`,`PARENT_ENTITY_ID`,`INHERITANCE_STRATEGY`,`DISCRIMINATOR_COLUMN_NAME`,`DISCRIMINATOR_VALUE`) values (6,2,1,'\0',NULL,3,NULL,NULL);
insert into `dyextn_entity` (`IDENTIFIER`,`DATA_TABLE_STATE`,`ENTITY_GROUP_ID`,`IS_ABSTRACT`,`PARENT_ENTITY_ID`,`INHERITANCE_STRATEGY`,`DISCRIMINATOR_COLUMN_NAME`,`DISCRIMINATOR_VALUE`) values (8,2,1,'\0',NULL,3,NULL,NULL);
insert into `dyextn_entity` (`IDENTIFIER`,`DATA_TABLE_STATE`,`ENTITY_GROUP_ID`,`IS_ABSTRACT`,`PARENT_ENTITY_ID`,`INHERITANCE_STRATEGY`,`DISCRIMINATOR_COLUMN_NAME`,`DISCRIMINATOR_VALUE`) values (10,2,1,'\0',NULL,3,NULL,NULL);
insert into `dyextn_entity` (`IDENTIFIER`,`DATA_TABLE_STATE`,`ENTITY_GROUP_ID`,`IS_ABSTRACT`,`PARENT_ENTITY_ID`,`INHERITANCE_STRATEGY`,`DISCRIMINATOR_COLUMN_NAME`,`DISCRIMINATOR_VALUE`) values (12,2,1,'\0',NULL,3,NULL,NULL);
insert into `dyextn_entity` (`IDENTIFIER`,`DATA_TABLE_STATE`,`ENTITY_GROUP_ID`,`IS_ABSTRACT`,`PARENT_ENTITY_ID`,`INHERITANCE_STRATEGY`,`DISCRIMINATOR_COLUMN_NAME`,`DISCRIMINATOR_VALUE`) values (14,2,1,'\0',NULL,3,NULL,NULL);
insert into `dyextn_entity` (`IDENTIFIER`,`DATA_TABLE_STATE`,`ENTITY_GROUP_ID`,`IS_ABSTRACT`,`PARENT_ENTITY_ID`,`INHERITANCE_STRATEGY`,`DISCRIMINATOR_COLUMN_NAME`,`DISCRIMINATOR_VALUE`) values (17,2,1,'\0',NULL,3,NULL,NULL);
insert into `dyextn_entity` (`IDENTIFIER`,`DATA_TABLE_STATE`,`ENTITY_GROUP_ID`,`IS_ABSTRACT`,`PARENT_ENTITY_ID`,`INHERITANCE_STRATEGY`,`DISCRIMINATOR_COLUMN_NAME`,`DISCRIMINATOR_VALUE`) values (25,2,1,'\0',NULL,3,NULL,NULL);
insert into `dyextn_entity` (`IDENTIFIER`,`DATA_TABLE_STATE`,`ENTITY_GROUP_ID`,`IS_ABSTRACT`,`PARENT_ENTITY_ID`,`INHERITANCE_STRATEGY`,`DISCRIMINATOR_COLUMN_NAME`,`DISCRIMINATOR_VALUE`) values (27,2,1,'\0',NULL,3,NULL,NULL);
insert into `dyextn_entity` (`IDENTIFIER`,`DATA_TABLE_STATE`,`ENTITY_GROUP_ID`,`IS_ABSTRACT`,`PARENT_ENTITY_ID`,`INHERITANCE_STRATEGY`,`DISCRIMINATOR_COLUMN_NAME`,`DISCRIMINATOR_VALUE`) values (36,2,1,'\0',NULL,3,NULL,NULL);
insert into `dyextn_entity` (`IDENTIFIER`,`DATA_TABLE_STATE`,`ENTITY_GROUP_ID`,`IS_ABSTRACT`,`PARENT_ENTITY_ID`,`INHERITANCE_STRATEGY`,`DISCRIMINATOR_COLUMN_NAME`,`DISCRIMINATOR_VALUE`) values (37,2,1,'\0',NULL,3,NULL,NULL);
insert into `dyextn_entity` (`IDENTIFIER`,`DATA_TABLE_STATE`,`ENTITY_GROUP_ID`,`IS_ABSTRACT`,`PARENT_ENTITY_ID`,`INHERITANCE_STRATEGY`,`DISCRIMINATOR_COLUMN_NAME`,`DISCRIMINATOR_VALUE`) values (39,2,1,'\0',NULL,3,NULL,NULL);
insert into `dyextn_entity` (`IDENTIFIER`,`DATA_TABLE_STATE`,`ENTITY_GROUP_ID`,`IS_ABSTRACT`,`PARENT_ENTITY_ID`,`INHERITANCE_STRATEGY`,`DISCRIMINATOR_COLUMN_NAME`,`DISCRIMINATOR_VALUE`) values (42,2,1,'\0',NULL,3,NULL,NULL);
insert into `dyextn_entity` (`IDENTIFIER`,`DATA_TABLE_STATE`,`ENTITY_GROUP_ID`,`IS_ABSTRACT`,`PARENT_ENTITY_ID`,`INHERITANCE_STRATEGY`,`DISCRIMINATOR_COLUMN_NAME`,`DISCRIMINATOR_VALUE`) values (45,2,1,'\0',NULL,3,NULL,NULL);
insert into `dyextn_entity` (`IDENTIFIER`,`DATA_TABLE_STATE`,`ENTITY_GROUP_ID`,`IS_ABSTRACT`,`PARENT_ENTITY_ID`,`INHERITANCE_STRATEGY`,`DISCRIMINATOR_COLUMN_NAME`,`DISCRIMINATOR_VALUE`) values (48,2,1,'\0',NULL,3,NULL,NULL);
insert into `dyextn_entity` (`IDENTIFIER`,`DATA_TABLE_STATE`,`ENTITY_GROUP_ID`,`IS_ABSTRACT`,`PARENT_ENTITY_ID`,`INHERITANCE_STRATEGY`,`DISCRIMINATOR_COLUMN_NAME`,`DISCRIMINATOR_VALUE`) values (61,2,1,'\0',NULL,3,NULL,NULL);
insert into `dyextn_entity` (`IDENTIFIER`,`DATA_TABLE_STATE`,`ENTITY_GROUP_ID`,`IS_ABSTRACT`,`PARENT_ENTITY_ID`,`INHERITANCE_STRATEGY`,`DISCRIMINATOR_COLUMN_NAME`,`DISCRIMINATOR_VALUE`) values (67,2,1,'\0',NULL,3,NULL,NULL);
insert into `dyextn_entity` (`IDENTIFIER`,`DATA_TABLE_STATE`,`ENTITY_GROUP_ID`,`IS_ABSTRACT`,`PARENT_ENTITY_ID`,`INHERITANCE_STRATEGY`,`DISCRIMINATOR_COLUMN_NAME`,`DISCRIMINATOR_VALUE`) values (76,2,1,'\0',NULL,3,NULL,NULL);

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

insert into `dyextn_entity_group` (`IDENTIFIER`,`LONG_NAME`,`SHORT_NAME`,`VERSION`,`IS_SYSTEM_GENERATED`) values (1,'cider','cider',NULL,'\0');

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

insert into `dyextn_id_generator` (`id`,`next_available_id`) values (1,231);

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

insert into `dyextn_long_type_info` (`IDENTIFIER`) values (1);
insert into `dyextn_long_type_info` (`IDENTIFIER`) values (2);
insert into `dyextn_long_type_info` (`IDENTIFIER`) values (3);
insert into `dyextn_long_type_info` (`IDENTIFIER`) values (4);
insert into `dyextn_long_type_info` (`IDENTIFIER`) values (5);
insert into `dyextn_long_type_info` (`IDENTIFIER`) values (6);
insert into `dyextn_long_type_info` (`IDENTIFIER`) values (7);
insert into `dyextn_long_type_info` (`IDENTIFIER`) values (12);
insert into `dyextn_long_type_info` (`IDENTIFIER`) values (13);
insert into `dyextn_long_type_info` (`IDENTIFIER`) values (21);
insert into `dyextn_long_type_info` (`IDENTIFIER`) values (22);
insert into `dyextn_long_type_info` (`IDENTIFIER`) values (23);
insert into `dyextn_long_type_info` (`IDENTIFIER`) values (24);
insert into `dyextn_long_type_info` (`IDENTIFIER`) values (25);
insert into `dyextn_long_type_info` (`IDENTIFIER`) values (26);
insert into `dyextn_long_type_info` (`IDENTIFIER`) values (34);
insert into `dyextn_long_type_info` (`IDENTIFIER`) values (37);
insert into `dyextn_long_type_info` (`IDENTIFIER`) values (39);
insert into `dyextn_long_type_info` (`IDENTIFIER`) values (40);

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

insert into `dyextn_numeric_type_info` (`IDENTIFIER`,`MEASUREMENT_UNITS`,`DECIMAL_PLACES`,`NO_DIGITS`) values (1,NULL,0,NULL);
insert into `dyextn_numeric_type_info` (`IDENTIFIER`,`MEASUREMENT_UNITS`,`DECIMAL_PLACES`,`NO_DIGITS`) values (2,NULL,0,NULL);
insert into `dyextn_numeric_type_info` (`IDENTIFIER`,`MEASUREMENT_UNITS`,`DECIMAL_PLACES`,`NO_DIGITS`) values (3,NULL,0,NULL);
insert into `dyextn_numeric_type_info` (`IDENTIFIER`,`MEASUREMENT_UNITS`,`DECIMAL_PLACES`,`NO_DIGITS`) values (4,NULL,0,NULL);
insert into `dyextn_numeric_type_info` (`IDENTIFIER`,`MEASUREMENT_UNITS`,`DECIMAL_PLACES`,`NO_DIGITS`) values (5,NULL,0,NULL);
insert into `dyextn_numeric_type_info` (`IDENTIFIER`,`MEASUREMENT_UNITS`,`DECIMAL_PLACES`,`NO_DIGITS`) values (6,NULL,0,NULL);
insert into `dyextn_numeric_type_info` (`IDENTIFIER`,`MEASUREMENT_UNITS`,`DECIMAL_PLACES`,`NO_DIGITS`) values (7,NULL,0,NULL);
insert into `dyextn_numeric_type_info` (`IDENTIFIER`,`MEASUREMENT_UNITS`,`DECIMAL_PLACES`,`NO_DIGITS`) values (12,NULL,0,NULL);
insert into `dyextn_numeric_type_info` (`IDENTIFIER`,`MEASUREMENT_UNITS`,`DECIMAL_PLACES`,`NO_DIGITS`) values (13,NULL,0,NULL);
insert into `dyextn_numeric_type_info` (`IDENTIFIER`,`MEASUREMENT_UNITS`,`DECIMAL_PLACES`,`NO_DIGITS`) values (21,NULL,0,NULL);
insert into `dyextn_numeric_type_info` (`IDENTIFIER`,`MEASUREMENT_UNITS`,`DECIMAL_PLACES`,`NO_DIGITS`) values (22,NULL,0,NULL);
insert into `dyextn_numeric_type_info` (`IDENTIFIER`,`MEASUREMENT_UNITS`,`DECIMAL_PLACES`,`NO_DIGITS`) values (23,NULL,0,NULL);
insert into `dyextn_numeric_type_info` (`IDENTIFIER`,`MEASUREMENT_UNITS`,`DECIMAL_PLACES`,`NO_DIGITS`) values (24,NULL,0,NULL);
insert into `dyextn_numeric_type_info` (`IDENTIFIER`,`MEASUREMENT_UNITS`,`DECIMAL_PLACES`,`NO_DIGITS`) values (25,NULL,0,NULL);
insert into `dyextn_numeric_type_info` (`IDENTIFIER`,`MEASUREMENT_UNITS`,`DECIMAL_PLACES`,`NO_DIGITS`) values (26,NULL,0,NULL);
insert into `dyextn_numeric_type_info` (`IDENTIFIER`,`MEASUREMENT_UNITS`,`DECIMAL_PLACES`,`NO_DIGITS`) values (34,NULL,0,NULL);
insert into `dyextn_numeric_type_info` (`IDENTIFIER`,`MEASUREMENT_UNITS`,`DECIMAL_PLACES`,`NO_DIGITS`) values (37,NULL,0,NULL);
insert into `dyextn_numeric_type_info` (`IDENTIFIER`,`MEASUREMENT_UNITS`,`DECIMAL_PLACES`,`NO_DIGITS`) values (39,NULL,0,NULL);
insert into `dyextn_numeric_type_info` (`IDENTIFIER`,`MEASUREMENT_UNITS`,`DECIMAL_PLACES`,`NO_DIGITS`) values (40,NULL,0,NULL);

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
  CONSTRAINT `FK1E1260A57EE87FF6` FOREIGN KEY (`ASSOCIATION_ID`) REFERENCES `dyextn_association` (`IDENTIFIER`),
  CONSTRAINT `FK1E1260A580C8F93E` FOREIGN KEY (`PATH_ID`) REFERENCES `dyextn_path` (`IDENTIFIER`)
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

insert into `dyextn_permissible_value` (`IDENTIFIER`,`DESCRIPTION`,`CATEGORY_ATTRIBUTE_ID`,`ATTRIBUTE_TYPE_INFO_ID`) values (1,NULL,NULL,8);
insert into `dyextn_permissible_value` (`IDENTIFIER`,`DESCRIPTION`,`CATEGORY_ATTRIBUTE_ID`,`ATTRIBUTE_TYPE_INFO_ID`) values (2,NULL,NULL,9);
insert into `dyextn_permissible_value` (`IDENTIFIER`,`DESCRIPTION`,`CATEGORY_ATTRIBUTE_ID`,`ATTRIBUTE_TYPE_INFO_ID`) values (3,NULL,NULL,10);
insert into `dyextn_permissible_value` (`IDENTIFIER`,`DESCRIPTION`,`CATEGORY_ATTRIBUTE_ID`,`ATTRIBUTE_TYPE_INFO_ID`) values (4,NULL,NULL,11);
insert into `dyextn_permissible_value` (`IDENTIFIER`,`DESCRIPTION`,`CATEGORY_ATTRIBUTE_ID`,`ATTRIBUTE_TYPE_INFO_ID`) values (5,NULL,NULL,14);
insert into `dyextn_permissible_value` (`IDENTIFIER`,`DESCRIPTION`,`CATEGORY_ATTRIBUTE_ID`,`ATTRIBUTE_TYPE_INFO_ID`) values (6,NULL,NULL,15);
insert into `dyextn_permissible_value` (`IDENTIFIER`,`DESCRIPTION`,`CATEGORY_ATTRIBUTE_ID`,`ATTRIBUTE_TYPE_INFO_ID`) values (7,NULL,NULL,16);
insert into `dyextn_permissible_value` (`IDENTIFIER`,`DESCRIPTION`,`CATEGORY_ATTRIBUTE_ID`,`ATTRIBUTE_TYPE_INFO_ID`) values (8,NULL,NULL,17);
insert into `dyextn_permissible_value` (`IDENTIFIER`,`DESCRIPTION`,`CATEGORY_ATTRIBUTE_ID`,`ATTRIBUTE_TYPE_INFO_ID`) values (9,NULL,NULL,18);
insert into `dyextn_permissible_value` (`IDENTIFIER`,`DESCRIPTION`,`CATEGORY_ATTRIBUTE_ID`,`ATTRIBUTE_TYPE_INFO_ID`) values (10,NULL,NULL,19);
insert into `dyextn_permissible_value` (`IDENTIFIER`,`DESCRIPTION`,`CATEGORY_ATTRIBUTE_ID`,`ATTRIBUTE_TYPE_INFO_ID`) values (11,NULL,NULL,20);
insert into `dyextn_permissible_value` (`IDENTIFIER`,`DESCRIPTION`,`CATEGORY_ATTRIBUTE_ID`,`ATTRIBUTE_TYPE_INFO_ID`) values (12,NULL,NULL,27);
insert into `dyextn_permissible_value` (`IDENTIFIER`,`DESCRIPTION`,`CATEGORY_ATTRIBUTE_ID`,`ATTRIBUTE_TYPE_INFO_ID`) values (13,NULL,NULL,28);
insert into `dyextn_permissible_value` (`IDENTIFIER`,`DESCRIPTION`,`CATEGORY_ATTRIBUTE_ID`,`ATTRIBUTE_TYPE_INFO_ID`) values (14,NULL,NULL,29);
insert into `dyextn_permissible_value` (`IDENTIFIER`,`DESCRIPTION`,`CATEGORY_ATTRIBUTE_ID`,`ATTRIBUTE_TYPE_INFO_ID`) values (15,NULL,NULL,30);
insert into `dyextn_permissible_value` (`IDENTIFIER`,`DESCRIPTION`,`CATEGORY_ATTRIBUTE_ID`,`ATTRIBUTE_TYPE_INFO_ID`) values (16,NULL,NULL,31);
insert into `dyextn_permissible_value` (`IDENTIFIER`,`DESCRIPTION`,`CATEGORY_ATTRIBUTE_ID`,`ATTRIBUTE_TYPE_INFO_ID`) values (17,NULL,NULL,32);
insert into `dyextn_permissible_value` (`IDENTIFIER`,`DESCRIPTION`,`CATEGORY_ATTRIBUTE_ID`,`ATTRIBUTE_TYPE_INFO_ID`) values (18,NULL,NULL,33);
insert into `dyextn_permissible_value` (`IDENTIFIER`,`DESCRIPTION`,`CATEGORY_ATTRIBUTE_ID`,`ATTRIBUTE_TYPE_INFO_ID`) values (19,NULL,NULL,35);
insert into `dyextn_permissible_value` (`IDENTIFIER`,`DESCRIPTION`,`CATEGORY_ATTRIBUTE_ID`,`ATTRIBUTE_TYPE_INFO_ID`) values (20,NULL,NULL,36);
insert into `dyextn_permissible_value` (`IDENTIFIER`,`DESCRIPTION`,`CATEGORY_ATTRIBUTE_ID`,`ATTRIBUTE_TYPE_INFO_ID`) values (21,NULL,NULL,38);

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

insert into `dyextn_primitive_attribute` (`IDENTIFIER`,`IS_IDENTIFIED`,`IS_PRIMARY_KEY`,`IS_NULLABLE`) values (3,NULL,'','\0');
insert into `dyextn_primitive_attribute` (`IDENTIFIER`,`IS_IDENTIFIED`,`IS_PRIMARY_KEY`,`IS_NULLABLE`) values (5,NULL,'','\0');
insert into `dyextn_primitive_attribute` (`IDENTIFIER`,`IS_IDENTIFIED`,`IS_PRIMARY_KEY`,`IS_NULLABLE`) values (7,NULL,'','\0');
insert into `dyextn_primitive_attribute` (`IDENTIFIER`,`IS_IDENTIFIED`,`IS_PRIMARY_KEY`,`IS_NULLABLE`) values (9,NULL,'','\0');
insert into `dyextn_primitive_attribute` (`IDENTIFIER`,`IS_IDENTIFIED`,`IS_PRIMARY_KEY`,`IS_NULLABLE`) values (11,NULL,'','\0');
insert into `dyextn_primitive_attribute` (`IDENTIFIER`,`IS_IDENTIFIED`,`IS_PRIMARY_KEY`,`IS_NULLABLE`) values (13,NULL,'','\0');
insert into `dyextn_primitive_attribute` (`IDENTIFIER`,`IS_IDENTIFIED`,`IS_PRIMARY_KEY`,`IS_NULLABLE`) values (18,NULL,'','\0');
insert into `dyextn_primitive_attribute` (`IDENTIFIER`,`IS_IDENTIFIED`,`IS_PRIMARY_KEY`,`IS_NULLABLE`) values (20,'\0','\0','');
insert into `dyextn_primitive_attribute` (`IDENTIFIER`,`IS_IDENTIFIED`,`IS_PRIMARY_KEY`,`IS_NULLABLE`) values (21,'\0','\0','');
insert into `dyextn_primitive_attribute` (`IDENTIFIER`,`IS_IDENTIFIED`,`IS_PRIMARY_KEY`,`IS_NULLABLE`) values (22,'\0','\0','');
insert into `dyextn_primitive_attribute` (`IDENTIFIER`,`IS_IDENTIFIED`,`IS_PRIMARY_KEY`,`IS_NULLABLE`) values (23,'\0','\0','');
insert into `dyextn_primitive_attribute` (`IDENTIFIER`,`IS_IDENTIFIED`,`IS_PRIMARY_KEY`,`IS_NULLABLE`) values (24,NULL,'','\0');
insert into `dyextn_primitive_attribute` (`IDENTIFIER`,`IS_IDENTIFIED`,`IS_PRIMARY_KEY`,`IS_NULLABLE`) values (26,NULL,'','\0');
insert into `dyextn_primitive_attribute` (`IDENTIFIER`,`IS_IDENTIFIED`,`IS_PRIMARY_KEY`,`IS_NULLABLE`) values (28,'\0','\0','');
insert into `dyextn_primitive_attribute` (`IDENTIFIER`,`IS_IDENTIFIED`,`IS_PRIMARY_KEY`,`IS_NULLABLE`) values (29,'\0','\0','');
insert into `dyextn_primitive_attribute` (`IDENTIFIER`,`IS_IDENTIFIED`,`IS_PRIMARY_KEY`,`IS_NULLABLE`) values (30,'\0','\0','');
insert into `dyextn_primitive_attribute` (`IDENTIFIER`,`IS_IDENTIFIED`,`IS_PRIMARY_KEY`,`IS_NULLABLE`) values (31,'\0','\0','');
insert into `dyextn_primitive_attribute` (`IDENTIFIER`,`IS_IDENTIFIED`,`IS_PRIMARY_KEY`,`IS_NULLABLE`) values (32,'\0','\0','');
insert into `dyextn_primitive_attribute` (`IDENTIFIER`,`IS_IDENTIFIED`,`IS_PRIMARY_KEY`,`IS_NULLABLE`) values (33,'\0','\0','');
insert into `dyextn_primitive_attribute` (`IDENTIFIER`,`IS_IDENTIFIED`,`IS_PRIMARY_KEY`,`IS_NULLABLE`) values (34,'\0','\0','');
insert into `dyextn_primitive_attribute` (`IDENTIFIER`,`IS_IDENTIFIED`,`IS_PRIMARY_KEY`,`IS_NULLABLE`) values (35,NULL,'','\0');
insert into `dyextn_primitive_attribute` (`IDENTIFIER`,`IS_IDENTIFIED`,`IS_PRIMARY_KEY`,`IS_NULLABLE`) values (40,NULL,'','\0');
insert into `dyextn_primitive_attribute` (`IDENTIFIER`,`IS_IDENTIFIED`,`IS_PRIMARY_KEY`,`IS_NULLABLE`) values (43,NULL,'','\0');
insert into `dyextn_primitive_attribute` (`IDENTIFIER`,`IS_IDENTIFIED`,`IS_PRIMARY_KEY`,`IS_NULLABLE`) values (46,NULL,'','\0');
insert into `dyextn_primitive_attribute` (`IDENTIFIER`,`IS_IDENTIFIED`,`IS_PRIMARY_KEY`,`IS_NULLABLE`) values (49,NULL,'','\0');
insert into `dyextn_primitive_attribute` (`IDENTIFIER`,`IS_IDENTIFIED`,`IS_PRIMARY_KEY`,`IS_NULLABLE`) values (52,NULL,'','\0');
insert into `dyextn_primitive_attribute` (`IDENTIFIER`,`IS_IDENTIFIED`,`IS_PRIMARY_KEY`,`IS_NULLABLE`) values (53,'\0','\0','');
insert into `dyextn_primitive_attribute` (`IDENTIFIER`,`IS_IDENTIFIED`,`IS_PRIMARY_KEY`,`IS_NULLABLE`) values (54,'\0','\0','');
insert into `dyextn_primitive_attribute` (`IDENTIFIER`,`IS_IDENTIFIED`,`IS_PRIMARY_KEY`,`IS_NULLABLE`) values (55,'\0','\0','');
insert into `dyextn_primitive_attribute` (`IDENTIFIER`,`IS_IDENTIFIED`,`IS_PRIMARY_KEY`,`IS_NULLABLE`) values (56,'\0','\0','');
insert into `dyextn_primitive_attribute` (`IDENTIFIER`,`IS_IDENTIFIED`,`IS_PRIMARY_KEY`,`IS_NULLABLE`) values (57,'\0','\0','');
insert into `dyextn_primitive_attribute` (`IDENTIFIER`,`IS_IDENTIFIED`,`IS_PRIMARY_KEY`,`IS_NULLABLE`) values (58,'\0','\0','');
insert into `dyextn_primitive_attribute` (`IDENTIFIER`,`IS_IDENTIFIED`,`IS_PRIMARY_KEY`,`IS_NULLABLE`) values (59,'\0','\0','');
insert into `dyextn_primitive_attribute` (`IDENTIFIER`,`IS_IDENTIFIED`,`IS_PRIMARY_KEY`,`IS_NULLABLE`) values (64,NULL,'','\0');
insert into `dyextn_primitive_attribute` (`IDENTIFIER`,`IS_IDENTIFIED`,`IS_PRIMARY_KEY`,`IS_NULLABLE`) values (68,'\0','\0','');
insert into `dyextn_primitive_attribute` (`IDENTIFIER`,`IS_IDENTIFIED`,`IS_PRIMARY_KEY`,`IS_NULLABLE`) values (69,'\0','\0','');
insert into `dyextn_primitive_attribute` (`IDENTIFIER`,`IS_IDENTIFIED`,`IS_PRIMARY_KEY`,`IS_NULLABLE`) values (70,NULL,'','\0');
insert into `dyextn_primitive_attribute` (`IDENTIFIER`,`IS_IDENTIFIED`,`IS_PRIMARY_KEY`,`IS_NULLABLE`) values (74,'\0','\0','');
insert into `dyextn_primitive_attribute` (`IDENTIFIER`,`IS_IDENTIFIED`,`IS_PRIMARY_KEY`,`IS_NULLABLE`) values (75,NULL,'','\0');
insert into `dyextn_primitive_attribute` (`IDENTIFIER`,`IS_IDENTIFIED`,`IS_PRIMARY_KEY`,`IS_NULLABLE`) values (77,NULL,'','\0');

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

insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (1,'ASSOCIATION',100,0,NULL);
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (2,'CONTAINTMENT',1,0,'state');
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (3,'ASSOCIATION',100,0,NULL);
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (4,'CONTAINTMENT',1,0,'addressType');
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (5,'ASSOCIATION',100,0,NULL);
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (6,'CONTAINTMENT',1,0,'country');
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (7,'ASSOCIATION',100,0,NULL);
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (8,'CONTAINTMENT',100,0,'ethinicOriginCollection');
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (9,'ASSOCIATION',100,0,NULL);
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (10,'CONTAINTMENT',1,0,'gender');
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (11,'ASSOCIATION',100,0,NULL);
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (12,'CONTAINTMENT',1,0,'advancedDirectiveExists');
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (13,'ASSOCIATION',100,0,NULL);
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (14,'CONTAINTMENT',1,0,'religion');
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (15,'ASSOCIATION',100,0,NULL);
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (16,'CONTAINTMENT',100,0,'raceCollection');
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (17,'ASSOCIATION',100,0,NULL);
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (18,'CONTAINTMENT',1,0,'maritalStatus');
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (19,'ASSOCIATION',1,1,NULL);
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (20,'CONTAINTMENT',1,1,'personName');
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (21,'ASSOCIATION',100,0,NULL);
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (22,'ASSOCIATION',1,1,'relationToPerson');
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (23,'ASSOCIATION',100,0,NULL);
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (24,'ASSOCIATION',1,1,'personUpi');
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (25,'ASSOCIATION',100,0,NULL);
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (26,'CONTAINTMENT',100,0,'associatedPersonCollection');
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (27,'ASSOCIATION',100,0,NULL);
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (28,'ASSOCIATION',1,0,'nextOfKin');
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (29,'ASSOCIATION',100,0,NULL);
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (30,'CONTAINTMENT',100,0,'phoneCollection');
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (31,'ASSOCIATION',1,1,NULL);
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (32,'CONTAINTMENT',100,0,'addressCollection');
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (33,'ASSOCIATION',1,1,NULL);
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (34,'CONTAINTMENT',100,1,'demographicsCollection');

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

insert into `dyextn_rule` (`IDENTIFIER`,`NAME`,`IS_IMPLICIT`,`CATEGORY_ATTR_ID`,`ATTRIBUTE_ID`) values (1,'textLength','',NULL,20);
insert into `dyextn_rule` (`IDENTIFIER`,`NAME`,`IS_IMPLICIT`,`CATEGORY_ATTR_ID`,`ATTRIBUTE_ID`) values (2,'textLength','',NULL,21);
insert into `dyextn_rule` (`IDENTIFIER`,`NAME`,`IS_IMPLICIT`,`CATEGORY_ATTR_ID`,`ATTRIBUTE_ID`) values (3,'textLength','',NULL,22);
insert into `dyextn_rule` (`IDENTIFIER`,`NAME`,`IS_IMPLICIT`,`CATEGORY_ATTR_ID`,`ATTRIBUTE_ID`) values (4,'textLength','',NULL,23);
insert into `dyextn_rule` (`IDENTIFIER`,`NAME`,`IS_IMPLICIT`,`CATEGORY_ATTR_ID`,`ATTRIBUTE_ID`) values (5,'textLength','',NULL,28);
insert into `dyextn_rule` (`IDENTIFIER`,`NAME`,`IS_IMPLICIT`,`CATEGORY_ATTR_ID`,`ATTRIBUTE_ID`) values (6,'textLength','',NULL,29);
insert into `dyextn_rule` (`IDENTIFIER`,`NAME`,`IS_IMPLICIT`,`CATEGORY_ATTR_ID`,`ATTRIBUTE_ID`) values (7,'textLength','',NULL,30);
insert into `dyextn_rule` (`IDENTIFIER`,`NAME`,`IS_IMPLICIT`,`CATEGORY_ATTR_ID`,`ATTRIBUTE_ID`) values (8,'textLength','',NULL,31);
insert into `dyextn_rule` (`IDENTIFIER`,`NAME`,`IS_IMPLICIT`,`CATEGORY_ATTR_ID`,`ATTRIBUTE_ID`) values (9,'textLength','',NULL,32);
insert into `dyextn_rule` (`IDENTIFIER`,`NAME`,`IS_IMPLICIT`,`CATEGORY_ATTR_ID`,`ATTRIBUTE_ID`) values (10,'textLength','',NULL,33);
insert into `dyextn_rule` (`IDENTIFIER`,`NAME`,`IS_IMPLICIT`,`CATEGORY_ATTR_ID`,`ATTRIBUTE_ID`) values (11,'textLength','',NULL,34);
insert into `dyextn_rule` (`IDENTIFIER`,`NAME`,`IS_IMPLICIT`,`CATEGORY_ATTR_ID`,`ATTRIBUTE_ID`) values (12,'date','',NULL,53);
insert into `dyextn_rule` (`IDENTIFIER`,`NAME`,`IS_IMPLICIT`,`CATEGORY_ATTR_ID`,`ATTRIBUTE_ID`) values (13,'date','',NULL,54);
insert into `dyextn_rule` (`IDENTIFIER`,`NAME`,`IS_IMPLICIT`,`CATEGORY_ATTR_ID`,`ATTRIBUTE_ID`) values (14,'textLength','',NULL,55);
insert into `dyextn_rule` (`IDENTIFIER`,`NAME`,`IS_IMPLICIT`,`CATEGORY_ATTR_ID`,`ATTRIBUTE_ID`) values (15,'textLength','',NULL,56);
insert into `dyextn_rule` (`IDENTIFIER`,`NAME`,`IS_IMPLICIT`,`CATEGORY_ATTR_ID`,`ATTRIBUTE_ID`) values (16,'textLength','',NULL,57);
insert into `dyextn_rule` (`IDENTIFIER`,`NAME`,`IS_IMPLICIT`,`CATEGORY_ATTR_ID`,`ATTRIBUTE_ID`) values (17,'date','',NULL,58);
insert into `dyextn_rule` (`IDENTIFIER`,`NAME`,`IS_IMPLICIT`,`CATEGORY_ATTR_ID`,`ATTRIBUTE_ID`) values (18,'date','',NULL,59);
insert into `dyextn_rule` (`IDENTIFIER`,`NAME`,`IS_IMPLICIT`,`CATEGORY_ATTR_ID`,`ATTRIBUTE_ID`) values (19,'textLength','',NULL,68);
insert into `dyextn_rule` (`IDENTIFIER`,`NAME`,`IS_IMPLICIT`,`CATEGORY_ATTR_ID`,`ATTRIBUTE_ID`) values (20,'textLength','',NULL,69);
insert into `dyextn_rule` (`IDENTIFIER`,`NAME`,`IS_IMPLICIT`,`CATEGORY_ATTR_ID`,`ATTRIBUTE_ID`) values (21,'textLength','',NULL,74);

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

insert into `dyextn_select_control` (`IDENTIFIER`,`SEPARATOR_STRING`) values (5,NULL);
insert into `dyextn_select_control` (`IDENTIFIER`,`SEPARATOR_STRING`) values (6,NULL);
insert into `dyextn_select_control` (`IDENTIFIER`,`SEPARATOR_STRING`) values (7,NULL);
insert into `dyextn_select_control` (`IDENTIFIER`,`SEPARATOR_STRING`) values (8,NULL);
insert into `dyextn_select_control` (`IDENTIFIER`,`SEPARATOR_STRING`) values (9,NULL);
insert into `dyextn_select_control` (`IDENTIFIER`,`SEPARATOR_STRING`) values (10,NULL);
insert into `dyextn_select_control` (`IDENTIFIER`,`SEPARATOR_STRING`) values (11,NULL);
insert into `dyextn_select_control` (`IDENTIFIER`,`SEPARATOR_STRING`) values (12,NULL);
insert into `dyextn_select_control` (`IDENTIFIER`,`SEPARATOR_STRING`) values (13,NULL);
insert into `dyextn_select_control` (`IDENTIFIER`,`SEPARATOR_STRING`) values (14,NULL);
insert into `dyextn_select_control` (`IDENTIFIER`,`SEPARATOR_STRING`) values (15,NULL);
insert into `dyextn_select_control` (`IDENTIFIER`,`SEPARATOR_STRING`) values (16,NULL);
insert into `dyextn_select_control` (`IDENTIFIER`,`SEPARATOR_STRING`) values (17,NULL);
insert into `dyextn_select_control` (`IDENTIFIER`,`SEPARATOR_STRING`) values (18,NULL);
insert into `dyextn_select_control` (`IDENTIFIER`,`SEPARATOR_STRING`) values (19,NULL);
insert into `dyextn_select_control` (`IDENTIFIER`,`SEPARATOR_STRING`) values (20,NULL);
insert into `dyextn_select_control` (`IDENTIFIER`,`SEPARATOR_STRING`) values (29,NULL);

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

insert into `dyextn_string_concept_value` (`IDENTIFIER`,`VALUE`) values (1,'');
insert into `dyextn_string_concept_value` (`IDENTIFIER`,`VALUE`) values (2,'');
insert into `dyextn_string_concept_value` (`IDENTIFIER`,`VALUE`) values (3,'');
insert into `dyextn_string_concept_value` (`IDENTIFIER`,`VALUE`) values (4,'');
insert into `dyextn_string_concept_value` (`IDENTIFIER`,`VALUE`) values (5,'');
insert into `dyextn_string_concept_value` (`IDENTIFIER`,`VALUE`) values (6,'');
insert into `dyextn_string_concept_value` (`IDENTIFIER`,`VALUE`) values (7,'');
insert into `dyextn_string_concept_value` (`IDENTIFIER`,`VALUE`) values (8,'');
insert into `dyextn_string_concept_value` (`IDENTIFIER`,`VALUE`) values (9,'');
insert into `dyextn_string_concept_value` (`IDENTIFIER`,`VALUE`) values (10,'');
insert into `dyextn_string_concept_value` (`IDENTIFIER`,`VALUE`) values (11,'');
insert into `dyextn_string_concept_value` (`IDENTIFIER`,`VALUE`) values (14,'');
insert into `dyextn_string_concept_value` (`IDENTIFIER`,`VALUE`) values (15,'');
insert into `dyextn_string_concept_value` (`IDENTIFIER`,`VALUE`) values (16,'');
insert into `dyextn_string_concept_value` (`IDENTIFIER`,`VALUE`) values (19,'');
insert into `dyextn_string_concept_value` (`IDENTIFIER`,`VALUE`) values (20,'');
insert into `dyextn_string_concept_value` (`IDENTIFIER`,`VALUE`) values (21,'');

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

insert into `dyextn_string_type_info` (`IDENTIFIER`,`MAX_SIZE`) values (8,255);
insert into `dyextn_string_type_info` (`IDENTIFIER`,`MAX_SIZE`) values (9,255);
insert into `dyextn_string_type_info` (`IDENTIFIER`,`MAX_SIZE`) values (10,255);
insert into `dyextn_string_type_info` (`IDENTIFIER`,`MAX_SIZE`) values (11,255);
insert into `dyextn_string_type_info` (`IDENTIFIER`,`MAX_SIZE`) values (14,255);
insert into `dyextn_string_type_info` (`IDENTIFIER`,`MAX_SIZE`) values (15,255);
insert into `dyextn_string_type_info` (`IDENTIFIER`,`MAX_SIZE`) values (16,255);
insert into `dyextn_string_type_info` (`IDENTIFIER`,`MAX_SIZE`) values (17,255);
insert into `dyextn_string_type_info` (`IDENTIFIER`,`MAX_SIZE`) values (18,255);
insert into `dyextn_string_type_info` (`IDENTIFIER`,`MAX_SIZE`) values (19,255);
insert into `dyextn_string_type_info` (`IDENTIFIER`,`MAX_SIZE`) values (20,255);
insert into `dyextn_string_type_info` (`IDENTIFIER`,`MAX_SIZE`) values (29,255);
insert into `dyextn_string_type_info` (`IDENTIFIER`,`MAX_SIZE`) values (30,255);
insert into `dyextn_string_type_info` (`IDENTIFIER`,`MAX_SIZE`) values (31,255);
insert into `dyextn_string_type_info` (`IDENTIFIER`,`MAX_SIZE`) values (35,255);
insert into `dyextn_string_type_info` (`IDENTIFIER`,`MAX_SIZE`) values (36,255);
insert into `dyextn_string_type_info` (`IDENTIFIER`,`MAX_SIZE`) values (38,255);

/*Table structure for table `dyextn_table_properties` */

DROP TABLE IF EXISTS `dyextn_table_properties`;

CREATE TABLE `dyextn_table_properties` (
  `IDENTIFIER` bigint(20) NOT NULL,
  `CONSTRAINT_NAME` varchar(255) default NULL,
  `ABSTRACT_ENTITY_ID` bigint(20) default NULL,
  PRIMARY KEY  (`IDENTIFIER`),
  KEY `FKE608E0811DCC9E63` (`ABSTRACT_ENTITY_ID`),
  KEY `FKE608E0813AB6A1D3` (`IDENTIFIER`),
  CONSTRAINT `FKE608E0811DCC9E63` FOREIGN KEY (`ABSTRACT_ENTITY_ID`) REFERENCES `dyextn_abstract_entity` (`id`),
  CONSTRAINT `FKE608E0813AB6A1D3` FOREIGN KEY (`IDENTIFIER`) REFERENCES `dyextn_database_properties` (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `dyextn_table_properties` */

insert into `dyextn_table_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`ABSTRACT_ENTITY_ID`) values (1,'CONSRT_95',2);
insert into `dyextn_table_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`ABSTRACT_ENTITY_ID`) values (3,'CONSRT_91',4);
insert into `dyextn_table_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`ABSTRACT_ENTITY_ID`) values (5,'CONSRT_87',6);
insert into `dyextn_table_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`ABSTRACT_ENTITY_ID`) values (7,'CONSRT_83',8);
insert into `dyextn_table_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`ABSTRACT_ENTITY_ID`) values (9,'CONSRT_79',10);
insert into `dyextn_table_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`ABSTRACT_ENTITY_ID`) values (11,'CONSRT_75',12);
insert into `dyextn_table_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`ABSTRACT_ENTITY_ID`) values (13,'CONSRT_2',14);
insert into `dyextn_table_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`ABSTRACT_ENTITY_ID`) values (16,'CONSRT_63',17);
insert into `dyextn_table_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`ABSTRACT_ENTITY_ID`) values (24,'CONSRT_10',25);
insert into `dyextn_table_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`ABSTRACT_ENTITY_ID`) values (26,'CONSRT_14',27);
insert into `dyextn_table_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`ABSTRACT_ENTITY_ID`) values (35,'CONSRT_25',36);
insert into `dyextn_table_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`ABSTRACT_ENTITY_ID`) values (36,'CONSRT_40',37);
insert into `dyextn_table_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`ABSTRACT_ENTITY_ID`) values (38,'CONSRT_71',39);
insert into `dyextn_table_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`ABSTRACT_ENTITY_ID`) values (41,'CONSRT_67',42);
insert into `dyextn_table_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`ABSTRACT_ENTITY_ID`) values (44,'CONSRT_59',45);
insert into `dyextn_table_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`ABSTRACT_ENTITY_ID`) values (47,'CONSRT_51',48);
insert into `dyextn_table_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`ABSTRACT_ENTITY_ID`) values (60,'CONSRT_30',61);
insert into `dyextn_table_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`ABSTRACT_ENTITY_ID`) values (66,'CONSRT_34',67);
insert into `dyextn_table_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`ABSTRACT_ENTITY_ID`) values (75,'CONSRT_55',76);

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
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

/*Data for the table `dyextn_tagged_value` */

insert into `dyextn_tagged_value` (`IDENTIFIER`,`T_KEY`,`T_VALUE`,`ABSTRACT_METADATA_ID`) values (1,'MetadataEntityGroup','MetadataEntityGroup',1);
insert into `dyextn_tagged_value` (`IDENTIFIER`,`T_KEY`,`T_VALUE`,`ABSTRACT_METADATA_ID`) values (2,'caB2BEntityGroup','caB2BEntityGroup',1);
insert into `dyextn_tagged_value` (`IDENTIFIER`,`T_KEY`,`T_VALUE`,`ABSTRACT_METADATA_ID`) values (3,'PackageName','edu.wustl.cider.domain.discrete.demographics',1);

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

insert into `dyextn_textfield` (`IDENTIFIER`,`NO_OF_COLUMNS`,`IS_PASSWORD`,`IS_URL`) values (1,20,'\0','\0');
insert into `dyextn_textfield` (`IDENTIFIER`,`NO_OF_COLUMNS`,`IS_PASSWORD`,`IS_URL`) values (2,20,'\0','\0');
insert into `dyextn_textfield` (`IDENTIFIER`,`NO_OF_COLUMNS`,`IS_PASSWORD`,`IS_URL`) values (3,20,'\0','\0');
insert into `dyextn_textfield` (`IDENTIFIER`,`NO_OF_COLUMNS`,`IS_PASSWORD`,`IS_URL`) values (4,20,'\0','\0');
insert into `dyextn_textfield` (`IDENTIFIER`,`NO_OF_COLUMNS`,`IS_PASSWORD`,`IS_URL`) values (23,20,'\0','\0');
insert into `dyextn_textfield` (`IDENTIFIER`,`NO_OF_COLUMNS`,`IS_PASSWORD`,`IS_URL`) values (24,20,'\0','\0');
insert into `dyextn_textfield` (`IDENTIFIER`,`NO_OF_COLUMNS`,`IS_PASSWORD`,`IS_URL`) values (25,20,'\0','\0');
insert into `dyextn_textfield` (`IDENTIFIER`,`NO_OF_COLUMNS`,`IS_PASSWORD`,`IS_URL`) values (28,20,'\0','\0');
insert into `dyextn_textfield` (`IDENTIFIER`,`NO_OF_COLUMNS`,`IS_PASSWORD`,`IS_URL`) values (30,20,'\0','\0');
insert into `dyextn_textfield` (`IDENTIFIER`,`NO_OF_COLUMNS`,`IS_PASSWORD`,`IS_URL`) values (31,20,'\0','\0');
insert into `dyextn_textfield` (`IDENTIFIER`,`NO_OF_COLUMNS`,`IS_PASSWORD`,`IS_URL`) values (32,20,'\0','\0');
insert into `dyextn_textfield` (`IDENTIFIER`,`NO_OF_COLUMNS`,`IS_PASSWORD`,`IS_URL`) values (33,20,'\0','\0');
insert into `dyextn_textfield` (`IDENTIFIER`,`NO_OF_COLUMNS`,`IS_PASSWORD`,`IS_URL`) values (34,20,'\0','\0');
insert into `dyextn_textfield` (`IDENTIFIER`,`NO_OF_COLUMNS`,`IS_PASSWORD`,`IS_URL`) values (35,20,'\0','\0');
insert into `dyextn_textfield` (`IDENTIFIER`,`NO_OF_COLUMNS`,`IS_PASSWORD`,`IS_URL`) values (36,20,'\0','\0');
insert into `dyextn_textfield` (`IDENTIFIER`,`NO_OF_COLUMNS`,`IS_PASSWORD`,`IS_URL`) values (37,20,'\0','\0');
insert into `dyextn_textfield` (`IDENTIFIER`,`NO_OF_COLUMNS`,`IS_PASSWORD`,`IS_URL`) values (38,20,'\0','\0');

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

insert into `intra_model_association` (`ASSOCIATION_ID`,`DE_ASSOCIATION_ID`) values (1,19);
insert into `intra_model_association` (`ASSOCIATION_ID`,`DE_ASSOCIATION_ID`) values (2,16);
insert into `intra_model_association` (`ASSOCIATION_ID`,`DE_ASSOCIATION_ID`) values (3,15);
insert into `intra_model_association` (`ASSOCIATION_ID`,`DE_ASSOCIATION_ID`) values (4,63);
insert into `intra_model_association` (`ASSOCIATION_ID`,`DE_ASSOCIATION_ID`) values (5,73);
insert into `intra_model_association` (`ASSOCIATION_ID`,`DE_ASSOCIATION_ID`) values (6,72);
insert into `intra_model_association` (`ASSOCIATION_ID`,`DE_ASSOCIATION_ID`) values (7,71);
insert into `intra_model_association` (`ASSOCIATION_ID`,`DE_ASSOCIATION_ID`) values (8,66);
insert into `intra_model_association` (`ASSOCIATION_ID`,`DE_ASSOCIATION_ID`) values (9,62);
insert into `intra_model_association` (`ASSOCIATION_ID`,`DE_ASSOCIATION_ID`) values (10,60);
insert into `intra_model_association` (`ASSOCIATION_ID`,`DE_ASSOCIATION_ID`) values (11,51);
insert into `intra_model_association` (`ASSOCIATION_ID`,`DE_ASSOCIATION_ID`) values (12,50);
insert into `intra_model_association` (`ASSOCIATION_ID`,`DE_ASSOCIATION_ID`) values (13,47);
insert into `intra_model_association` (`ASSOCIATION_ID`,`DE_ASSOCIATION_ID`) values (14,44);
insert into `intra_model_association` (`ASSOCIATION_ID`,`DE_ASSOCIATION_ID`) values (15,41);
insert into `intra_model_association` (`ASSOCIATION_ID`,`DE_ASSOCIATION_ID`) values (16,38);

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

insert into `path` (`PATH_ID`,`FIRST_ENTITY_ID`,`INTERMEDIATE_PATH`,`LAST_ENTITY_ID`) values (1,14,'1',17);
insert into `path` (`PATH_ID`,`FIRST_ENTITY_ID`,`INTERMEDIATE_PATH`,`LAST_ENTITY_ID`) values (2,14,'2',12);
insert into `path` (`PATH_ID`,`FIRST_ENTITY_ID`,`INTERMEDIATE_PATH`,`LAST_ENTITY_ID`) values (3,14,'3',4);
insert into `path` (`PATH_ID`,`FIRST_ENTITY_ID`,`INTERMEDIATE_PATH`,`LAST_ENTITY_ID`) values (4,61,'4',36);
insert into `path` (`PATH_ID`,`FIRST_ENTITY_ID`,`INTERMEDIATE_PATH`,`LAST_ENTITY_ID`) values (5,36,'5',37);
insert into `path` (`PATH_ID`,`FIRST_ENTITY_ID`,`INTERMEDIATE_PATH`,`LAST_ENTITY_ID`) values (6,37,'6',14);
insert into `path` (`PATH_ID`,`FIRST_ENTITY_ID`,`INTERMEDIATE_PATH`,`LAST_ENTITY_ID`) values (7,37,'7',67);
insert into `path` (`PATH_ID`,`FIRST_ENTITY_ID`,`INTERMEDIATE_PATH`,`LAST_ENTITY_ID`) values (8,37,'8',61);
insert into `path` (`PATH_ID`,`FIRST_ENTITY_ID`,`INTERMEDIATE_PATH`,`LAST_ENTITY_ID`) values (9,61,'9',8);
insert into `path` (`PATH_ID`,`FIRST_ENTITY_ID`,`INTERMEDIATE_PATH`,`LAST_ENTITY_ID`) values (10,37,'10',27);
insert into `path` (`PATH_ID`,`FIRST_ENTITY_ID`,`INTERMEDIATE_PATH`,`LAST_ENTITY_ID`) values (11,37,'11',25);
insert into `path` (`PATH_ID`,`FIRST_ENTITY_ID`,`INTERMEDIATE_PATH`,`LAST_ENTITY_ID`) values (12,37,'12',48);
insert into `path` (`PATH_ID`,`FIRST_ENTITY_ID`,`INTERMEDIATE_PATH`,`LAST_ENTITY_ID`) values (13,37,'13',45);
insert into `path` (`PATH_ID`,`FIRST_ENTITY_ID`,`INTERMEDIATE_PATH`,`LAST_ENTITY_ID`) values (14,37,'14',42);
insert into `path` (`PATH_ID`,`FIRST_ENTITY_ID`,`INTERMEDIATE_PATH`,`LAST_ENTITY_ID`) values (15,37,'15',39);
insert into `path` (`PATH_ID`,`FIRST_ENTITY_ID`,`INTERMEDIATE_PATH`,`LAST_ENTITY_ID`) values (16,37,'16',10);

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
  CONSTRAINT `FK262AEB0B687BE69E` FOREIGN KEY (`IDENTIFIER`) REFERENCES `query_operand` (`IDENTIFIER`),
  CONSTRAINT `FK262AEB0B7223B197` FOREIGN KEY (`IDENTIFIER`) REFERENCES `query_operand` (`IDENTIFIER`),
  CONSTRAINT `FK262AEB0B96C7CE5A` FOREIGN KEY (`IDENTIFIER`) REFERENCES `query_operand` (`IDENTIFIER`),
  CONSTRAINT `FK262AEB0BD006BE44` FOREIGN KEY (`IDENTIFIER`) REFERENCES `query_operand` (`IDENTIFIER`),
  CONSTRAINT `FK262AEB0BD635BD31` FOREIGN KEY (`IDENTIFIER`) REFERENCES `query_operand` (`IDENTIFIER`),
  CONSTRAINT `FK262AEB0BE92C814D` FOREIGN KEY (`EXPRESSION_ID`) REFERENCES `query_base_expression` (`IDENTIFIER`)
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
  CONSTRAINT `FK3F0043482FCE1DA7` FOREIGN KEY (`CONNECTOR_ID`) REFERENCES `query_connector` (`IDENTIFIER`),
  CONSTRAINT `FK3F00434848BA6890` FOREIGN KEY (`BASE_EXPRESSION_ID`) REFERENCES `query_base_expression` (`IDENTIFIER`)
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
  CONSTRAINT `FK2BD705CEA0A5F4C0` FOREIGN KEY (`CONSTRAINT_ID`) REFERENCES `query_constraints` (`IDENTIFIER`),
  CONSTRAINT `FK2BD705CEE92C814D` FOREIGN KEY (`EXPRESSION_ID`) REFERENCES `query_base_expression` (`IDENTIFIER`)
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
  CONSTRAINT `FK1B473A8F40EB75D4` FOREIGN KEY (`IDENTIFIER`) REFERENCES `query_base_expression` (`IDENTIFIER`),
  CONSTRAINT `FK1B473A8F635766D8` FOREIGN KEY (`QUERY_ENTITY_ID`) REFERENCES `query_query_entity` (`IDENTIFIER`)
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
  CONSTRAINT `FKAE90F94D3BC37DCB` FOREIGN KEY (`RHS_TERM_ID`) REFERENCES `query_base_expression` (`IDENTIFIER`),
  CONSTRAINT `FKAE90F94D9A0B7164` FOREIGN KEY (`CUSTOM_FORMULA_ID`) REFERENCES `query_operand` (`IDENTIFIER`)
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
  CONSTRAINT `FK22C9DB75604D4BDA` FOREIGN KEY (`PARAMETERIZED_QUERY_ID`) REFERENCES `query_parameterized_query` (`IDENTIFIER`),
  CONSTRAINT `FK22C9DB75E92C814D` FOREIGN KEY (`EXPRESSION_ID`) REFERENCES `query_base_expression` (`IDENTIFIER`)
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
  CONSTRAINT `FK2BF760E832E875C8` FOREIGN KEY (`IDENTIFIER`) REFERENCES `query_operand` (`IDENTIFIER`),
  CONSTRAINT `FK2BF760E8E92C814D` FOREIGN KEY (`EXPRESSION_ID`) REFERENCES `query_base_expression` (`IDENTIFIER`)
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
  CONSTRAINT `FK8A70E2565E5B9430` FOREIGN KEY (`OUTPUT_TERM_ID`) REFERENCES `query_output_term` (`IDENTIFIER`),
  CONSTRAINT `FK8A70E25691051647` FOREIGN KEY (`QUERY_ID`) REFERENCES `query` (`IDENTIFIER`)
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