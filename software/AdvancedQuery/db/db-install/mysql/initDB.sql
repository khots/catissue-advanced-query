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
insert into `association` (`ASSOCIATION_ID`,`ASSOCIATION_TYPE`) values (17,2);
insert into `association` (`ASSOCIATION_ID`,`ASSOCIATION_TYPE`) values (18,2);
insert into `association` (`ASSOCIATION_ID`,`ASSOCIATION_TYPE`) values (19,2);
insert into `association` (`ASSOCIATION_ID`,`ASSOCIATION_TYPE`) values (20,2);
insert into `association` (`ASSOCIATION_ID`,`ASSOCIATION_TYPE`) values (21,2);
insert into `association` (`ASSOCIATION_ID`,`ASSOCIATION_TYPE`) values (22,2);
insert into `association` (`ASSOCIATION_ID`,`ASSOCIATION_TYPE`) values (23,2);
insert into `association` (`ASSOCIATION_ID`,`ASSOCIATION_TYPE`) values (24,2);
insert into `association` (`ASSOCIATION_ID`,`ASSOCIATION_TYPE`) values (25,2);
insert into `association` (`ASSOCIATION_ID`,`ASSOCIATION_TYPE`) values (26,2);
insert into `association` (`ASSOCIATION_ID`,`ASSOCIATION_TYPE`) values (27,2);
insert into `association` (`ASSOCIATION_ID`,`ASSOCIATION_TYPE`) values (28,2);
insert into `association` (`ASSOCIATION_ID`,`ASSOCIATION_TYPE`) values (29,2);
insert into `association` (`ASSOCIATION_ID`,`ASSOCIATION_TYPE`) values (30,2);
insert into `association` (`ASSOCIATION_ID`,`ASSOCIATION_TYPE`) values (31,2);
insert into `association` (`ASSOCIATION_ID`,`ASSOCIATION_TYPE`) values (32,2);
insert into `association` (`ASSOCIATION_ID`,`ASSOCIATION_TYPE`) values (33,2);
insert into `association` (`ASSOCIATION_ID`,`ASSOCIATION_TYPE`) values (34,2);
insert into `association` (`ASSOCIATION_ID`,`ASSOCIATION_TYPE`) values (35,2);
insert into `association` (`ASSOCIATION_ID`,`ASSOCIATION_TYPE`) values (36,2);
insert into `association` (`ASSOCIATION_ID`,`ASSOCIATION_TYPE`) values (37,2);
insert into `association` (`ASSOCIATION_ID`,`ASSOCIATION_TYPE`) values (38,2);
insert into `association` (`ASSOCIATION_ID`,`ASSOCIATION_TYPE`) values (39,2);
insert into `association` (`ASSOCIATION_ID`,`ASSOCIATION_TYPE`) values (40,2);
insert into `association` (`ASSOCIATION_ID`,`ASSOCIATION_TYPE`) values (41,2);
insert into `association` (`ASSOCIATION_ID`,`ASSOCIATION_TYPE`) values (42,2);
insert into `association` (`ASSOCIATION_ID`,`ASSOCIATION_TYPE`) values (43,2);
insert into `association` (`ASSOCIATION_ID`,`ASSOCIATION_TYPE`) values (44,2);
insert into `association` (`ASSOCIATION_ID`,`ASSOCIATION_TYPE`) values (45,2);
insert into `association` (`ASSOCIATION_ID`,`ASSOCIATION_TYPE`) values (46,2);
insert into `association` (`ASSOCIATION_ID`,`ASSOCIATION_TYPE`) values (47,2);
insert into `association` (`ASSOCIATION_ID`,`ASSOCIATION_TYPE`) values (48,2);
insert into `association` (`ASSOCIATION_ID`,`ASSOCIATION_TYPE`) values (49,2);
insert into `association` (`ASSOCIATION_ID`,`ASSOCIATION_TYPE`) values (50,2);
insert into `association` (`ASSOCIATION_ID`,`ASSOCIATION_TYPE`) values (51,2);
insert into `association` (`ASSOCIATION_ID`,`ASSOCIATION_TYPE`) values (52,2);
insert into `association` (`ASSOCIATION_ID`,`ASSOCIATION_TYPE`) values (53,2);
insert into `association` (`ASSOCIATION_ID`,`ASSOCIATION_TYPE`) values (54,2);
insert into `association` (`ASSOCIATION_ID`,`ASSOCIATION_TYPE`) values (55,2);
insert into `association` (`ASSOCIATION_ID`,`ASSOCIATION_TYPE`) values (56,2);
insert into `association` (`ASSOCIATION_ID`,`ASSOCIATION_TYPE`) values (57,2);
insert into `association` (`ASSOCIATION_ID`,`ASSOCIATION_TYPE`) values (58,2);
insert into `association` (`ASSOCIATION_ID`,`ASSOCIATION_TYPE`) values (59,2);
insert into `association` (`ASSOCIATION_ID`,`ASSOCIATION_TYPE`) values (60,2);
insert into `association` (`ASSOCIATION_ID`,`ASSOCIATION_TYPE`) values (61,2);
insert into `association` (`ASSOCIATION_ID`,`ASSOCIATION_TYPE`) values (62,2);
insert into `association` (`ASSOCIATION_ID`,`ASSOCIATION_TYPE`) values (63,2);
insert into `association` (`ASSOCIATION_ID`,`ASSOCIATION_TYPE`) values (64,2);
insert into `association` (`ASSOCIATION_ID`,`ASSOCIATION_TYPE`) values (65,2);
insert into `association` (`ASSOCIATION_ID`,`ASSOCIATION_TYPE`) values (66,2);
insert into `association` (`ASSOCIATION_ID`,`ASSOCIATION_TYPE`) values (67,2);
insert into `association` (`ASSOCIATION_ID`,`ASSOCIATION_TYPE`) values (68,2);
insert into `association` (`ASSOCIATION_ID`,`ASSOCIATION_TYPE`) values (69,2);
insert into `association` (`ASSOCIATION_ID`,`ASSOCIATION_TYPE`) values (70,2);
insert into `association` (`ASSOCIATION_ID`,`ASSOCIATION_TYPE`) values (71,2);
insert into `association` (`ASSOCIATION_ID`,`ASSOCIATION_TYPE`) values (72,2);
insert into `association` (`ASSOCIATION_ID`,`ASSOCIATION_TYPE`) values (73,2);

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

insert into `dyextn_abstract_entity` (`id`) values (2);
insert into `dyextn_abstract_entity` (`id`) values (7);
insert into `dyextn_abstract_entity` (`id`) values (8);
insert into `dyextn_abstract_entity` (`id`) values (13);
insert into `dyextn_abstract_entity` (`id`) values (23);
insert into `dyextn_abstract_entity` (`id`) values (24);
insert into `dyextn_abstract_entity` (`id`) values (25);
insert into `dyextn_abstract_entity` (`id`) values (28);
insert into `dyextn_abstract_entity` (`id`) values (31);
insert into `dyextn_abstract_entity` (`id`) values (34);
insert into `dyextn_abstract_entity` (`id`) values (37);
insert into `dyextn_abstract_entity` (`id`) values (40);
insert into `dyextn_abstract_entity` (`id`) values (52);
insert into `dyextn_abstract_entity` (`id`) values (53);
insert into `dyextn_abstract_entity` (`id`) values (60);
insert into `dyextn_abstract_entity` (`id`) values (65);
insert into `dyextn_abstract_entity` (`id`) values (66);
insert into `dyextn_abstract_entity` (`id`) values (69);
insert into `dyextn_abstract_entity` (`id`) values (72);
insert into `dyextn_abstract_entity` (`id`) values (87);
insert into `dyextn_abstract_entity` (`id`) values (89);
insert into `dyextn_abstract_entity` (`id`) values (91);
insert into `dyextn_abstract_entity` (`id`) values (93);
insert into `dyextn_abstract_entity` (`id`) values (98);
insert into `dyextn_abstract_entity` (`id`) values (100);
insert into `dyextn_abstract_entity` (`id`) values (102);
insert into `dyextn_abstract_entity` (`id`) values (104);
insert into `dyextn_abstract_entity` (`id`) values (106);
insert into `dyextn_abstract_entity` (`id`) values (108);
insert into `dyextn_abstract_entity` (`id`) values (113);
insert into `dyextn_abstract_entity` (`id`) values (116);
insert into `dyextn_abstract_entity` (`id`) values (124);
insert into `dyextn_abstract_entity` (`id`) values (132);
insert into `dyextn_abstract_entity` (`id`) values (134);
insert into `dyextn_abstract_entity` (`id`) values (135);
insert into `dyextn_abstract_entity` (`id`) values (146);
insert into `dyextn_abstract_entity` (`id`) values (149);
insert into `dyextn_abstract_entity` (`id`) values (158);
insert into `dyextn_abstract_entity` (`id`) values (161);
insert into `dyextn_abstract_entity` (`id`) values (172);
insert into `dyextn_abstract_entity` (`id`) values (176);
insert into `dyextn_abstract_entity` (`id`) values (178);
insert into `dyextn_abstract_entity` (`id`) values (180);
insert into `dyextn_abstract_entity` (`id`) values (182);
insert into `dyextn_abstract_entity` (`id`) values (188);
insert into `dyextn_abstract_entity` (`id`) values (191);
insert into `dyextn_abstract_entity` (`id`) values (206);
insert into `dyextn_abstract_entity` (`id`) values (216);
insert into `dyextn_abstract_entity` (`id`) values (221);
insert into `dyextn_abstract_entity` (`id`) values (224);
insert into `dyextn_abstract_entity` (`id`) values (233);
insert into `dyextn_abstract_entity` (`id`) values (236);
insert into `dyextn_abstract_entity` (`id`) values (239);
insert into `dyextn_abstract_entity` (`id`) values (263);
insert into `dyextn_abstract_entity` (`id`) values (266);
insert into `dyextn_abstract_entity` (`id`) values (269);
insert into `dyextn_abstract_entity` (`id`) values (271);
insert into `dyextn_abstract_entity` (`id`) values (280);
insert into `dyextn_abstract_entity` (`id`) values (283);
insert into `dyextn_abstract_entity` (`id`) values (285);
insert into `dyextn_abstract_entity` (`id`) values (287);
insert into `dyextn_abstract_entity` (`id`) values (289);

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

insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (1,NULL,'cider',NULL,'cider',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (2,'2008-12-04','cider-PatientLocation','2008-12-04','PatientLocation',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (3,NULL,NULL,NULL,'bed',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (4,NULL,NULL,NULL,'room',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (5,NULL,NULL,NULL,'location',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (6,NULL,NULL,NULL,'id',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (7,'2008-12-04','cider-Provider','2008-12-04','Provider',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (8,'2008-12-04','cider-MedicalEntitiesDictionary','2008-12-04','MedicalEntitiesDictionary',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (9,NULL,NULL,NULL,'shortName',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (10,NULL,NULL,NULL,'name',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (11,NULL,NULL,NULL,'id',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (12,NULL,NULL,NULL,'AssociationName_3',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (13,'2008-12-04','cider-PersonName','2008-12-04','PersonName',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (14,NULL,NULL,NULL,'suffix',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (15,NULL,NULL,NULL,'prefix',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (16,NULL,NULL,NULL,'degree',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (17,NULL,NULL,NULL,'lastNameCompressed',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (18,NULL,NULL,NULL,'lastName',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (19,NULL,NULL,NULL,'middleName',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (20,NULL,NULL,NULL,'firstName',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (21,NULL,NULL,NULL,'id',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (22,NULL,NULL,NULL,'AssociationName_2',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (23,'2008-12-04','cider-Person','2008-12-04','Person',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (24,'2008-12-04','cider-Demographics','2008-12-04','Demographics',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (25,'2008-12-04','cider-EthnicOrigin','2008-12-04','EthnicOrigin',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (26,NULL,NULL,NULL,'id',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (27,NULL,NULL,NULL,'AssociationName_11',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (28,'2008-12-04','cider-Gender','2008-12-04','Gender',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (29,NULL,NULL,NULL,'id',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (30,NULL,NULL,NULL,'AssociationName_10',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (31,'2008-12-04','cider-AdvancedDirectiveExists','2008-12-04','AdvancedDirectiveExists',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (32,NULL,NULL,NULL,'id',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (33,NULL,NULL,NULL,'AssociationName_9',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (34,'2008-12-04','cider-Religion','2008-12-04','Religion',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (35,NULL,NULL,NULL,'id',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (36,NULL,NULL,NULL,'AssociationName_8',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (37,'2008-12-04','cider-Race','2008-12-04','Race',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (38,NULL,NULL,NULL,'id',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (39,NULL,NULL,NULL,'AssociationName_7',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (40,'2008-12-04','cider-MaritalStatus','2008-12-04','MaritalStatus',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (41,NULL,NULL,NULL,'id',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (42,NULL,NULL,NULL,'AssociationName_6',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (43,NULL,NULL,NULL,'id',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (44,NULL,NULL,NULL,'dateOfBirth',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (45,NULL,NULL,NULL,'dateOfDeath',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (46,NULL,NULL,NULL,'mothersMaidenName',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (47,NULL,NULL,NULL,'placeOfBirth',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (48,NULL,NULL,NULL,'socialSecurityNumber',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (49,NULL,NULL,NULL,'effectiveStartTimeStamp',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (50,NULL,NULL,NULL,'effectiveEndTimeStamp',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (51,NULL,NULL,NULL,'AssociationName_1',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (52,'2008-12-04','cider-AssociatedPerson','2008-12-04','AssociatedPerson',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (53,'2008-12-04','cider-RelationToPerson','2008-12-04','RelationToPerson',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (54,NULL,NULL,NULL,'id',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (55,NULL,NULL,NULL,'AssociationName_2',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (56,NULL,NULL,NULL,'AssociationName_1',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (57,NULL,NULL,NULL,'id',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (58,NULL,NULL,NULL,'AssociationName_2',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (59,NULL,NULL,NULL,'AssociationName_3',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (60,'2008-12-04','cider-Phone','2008-12-04','Phone',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (61,NULL,NULL,NULL,'type',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (62,NULL,NULL,NULL,'number',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (63,NULL,NULL,NULL,'id',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (64,NULL,NULL,NULL,'AssociationName_4',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (65,'2008-12-04','cider-Address','2008-12-04','Address',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (66,'2008-12-04','cider-State','2008-12-04','State',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (67,NULL,NULL,NULL,'id',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (68,NULL,NULL,NULL,'AssociationName_3',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (69,'2008-12-04','cider-AddressType','2008-12-04','AddressType',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (70,NULL,NULL,NULL,'id',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (71,NULL,NULL,NULL,'AssociationName_2',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (72,'2008-12-04','cider-Country','2008-12-04','Country',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (73,NULL,NULL,NULL,'id',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (74,NULL,NULL,NULL,'AssociationName_1',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (75,NULL,NULL,NULL,'postalCode',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (76,NULL,NULL,NULL,'city',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (77,NULL,NULL,NULL,'line2',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (78,NULL,NULL,NULL,'line1',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (79,NULL,NULL,NULL,'id',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (80,NULL,NULL,NULL,'AssociationName_5',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (81,NULL,NULL,NULL,'AssociationName_1',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (82,NULL,NULL,NULL,'personUpi',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (83,NULL,NULL,NULL,'id',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (84,NULL,NULL,NULL,'AssociationName_1',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (85,NULL,NULL,NULL,'facilityProviderId',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (86,NULL,NULL,NULL,'id',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (87,'2008-12-04','cider-ActiveUpiFlag','2008-12-04','ActiveUpiFlag',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (88,NULL,NULL,NULL,'id',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (89,'2008-12-04','cider-VipIndicator','2008-12-04','VipIndicator',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (90,NULL,NULL,NULL,'id',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (91,'2008-12-04','cider-PhoneType','2008-12-04','PhoneType',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (92,NULL,NULL,NULL,'id',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (93,'2008-12-04','cider-NormalRange','2008-12-04','NormalRange',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (94,NULL,NULL,NULL,'rangeHigh',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (95,NULL,NULL,NULL,'rangeLow',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (96,NULL,NULL,NULL,'range',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (97,NULL,NULL,NULL,'id',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (98,'2008-12-04','cider-LaboratoryProcedureType','2008-12-04','LaboratoryProcedureType',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (99,NULL,NULL,NULL,'id',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (100,'2008-12-04','cider-DiagnosticRelatedGroup','2008-12-04','DiagnosticRelatedGroup',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (101,NULL,NULL,NULL,'id',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (102,'2008-12-04','cider-Status','2008-12-04','Status',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (103,NULL,NULL,NULL,'id',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (104,'2008-12-04','cider-FacilityDischargeDisposition','2008-12-04','FacilityDischargeDisposition',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (105,NULL,NULL,NULL,'id',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (106,'2008-12-04','cider-LaboratoryProcedure','2008-12-04','LaboratoryProcedure',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (107,NULL,NULL,NULL,'AssociationName_5',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (108,'2008-12-04','cider-Facility','2008-12-04','Facility',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (109,NULL,NULL,NULL,'initials',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (110,NULL,NULL,NULL,'id',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (111,NULL,NULL,NULL,'AssociationName_4',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (112,NULL,NULL,NULL,'AssociationName_3',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (113,'2008-12-04','cider-Application','2008-12-04','Application',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (114,NULL,NULL,NULL,'id',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (115,NULL,NULL,NULL,'AssociationName_2',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (116,'2008-12-04','cider-LaboratoryProcedureDetails','2008-12-04','LaboratoryProcedureDetails',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (117,NULL,NULL,NULL,'AssociationName_8',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (118,NULL,NULL,NULL,'id',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (119,NULL,NULL,NULL,'effectiveStartTimeStamp',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (120,NULL,NULL,NULL,'effectiveEndTimeStamp',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (121,NULL,NULL,NULL,'ageAtProcedure',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (122,NULL,NULL,NULL,'procedureComment',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (123,NULL,NULL,NULL,'procedureId',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (124,'2008-12-04','cider-MedicalRecordNumber','2008-12-04','MedicalRecordNumber',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (125,NULL,NULL,NULL,'AssociationName_1',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (126,NULL,NULL,NULL,'Type',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (127,NULL,NULL,NULL,'Number',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (128,NULL,NULL,NULL,'id',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (129,NULL,NULL,NULL,'AssociationName_1',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (130,NULL,NULL,NULL,'AssociationName_2',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (131,NULL,NULL,NULL,'AssociationName_3',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (132,'2008-12-04','cider-LaboratoryResult','2008-12-04','LaboratoryResult',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (133,NULL,NULL,NULL,'AssociationName_4',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (134,'2008-12-04','cider-ResultValue','2008-12-04','ResultValue',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (135,'2008-12-04','cider-Result','2008-12-04','Result',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (136,NULL,NULL,NULL,'AssociationName_1',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (137,NULL,NULL,NULL,'units',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (138,NULL,NULL,NULL,'resultHigh',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (139,NULL,NULL,NULL,'resultLow',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (140,NULL,NULL,NULL,'resultString',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (141,NULL,NULL,NULL,'id',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (142,NULL,NULL,NULL,'AssociationName_2',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (143,NULL,NULL,NULL,'AssociationName_1',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (144,NULL,NULL,NULL,'id',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (145,NULL,NULL,NULL,'AssociationName_3',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (146,'2008-12-04','cider-AbnormalFlag','2008-12-04','AbnormalFlag',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (147,NULL,NULL,NULL,'id',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (148,NULL,NULL,NULL,'AssociationName_2',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (149,'2008-12-04','cider-LaboratoryTestType','2008-12-04','LaboratoryTestType',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (150,NULL,NULL,NULL,'standardUnitOfMeasure',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (151,NULL,NULL,NULL,'id',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (152,NULL,NULL,NULL,'AssociationName_1',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (153,NULL,NULL,NULL,'resultTimeStamp',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (154,NULL,NULL,NULL,'testSynonym',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (155,NULL,NULL,NULL,'resultComments',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (156,NULL,NULL,NULL,'id',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (157,NULL,NULL,NULL,'AssociationName_4',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (158,'2008-12-04','cider-SpecimanType','2008-12-04','SpecimanType',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (159,NULL,NULL,NULL,'id',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (160,NULL,NULL,NULL,'AssociationName_5',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (161,'2008-12-04','cider-PatientAccountNumber','2008-12-04','PatientAccountNumber',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (162,NULL,NULL,NULL,'AssociationName_1',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (163,NULL,NULL,NULL,'patientAccountNumber',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (164,NULL,NULL,NULL,'id',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (165,NULL,NULL,NULL,'AssociationName_6',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (166,NULL,NULL,NULL,'AssociationName_7',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (167,NULL,NULL,NULL,'AssociationName_1',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (168,NULL,NULL,NULL,'procedureSynonym',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (169,NULL,NULL,NULL,'patientAccountNumber',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (170,NULL,NULL,NULL,'accessionNumber',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (171,NULL,NULL,NULL,'id',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (172,'2008-12-04','cider-Units','2008-12-04','Units',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (173,NULL,NULL,NULL,'normalizedUnits',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (174,NULL,NULL,NULL,'sourceUnits',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (175,NULL,NULL,NULL,'id',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (176,'2008-12-04','cider-ProcedureCode','2008-12-04','ProcedureCode',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (177,NULL,NULL,NULL,'id',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (178,'2008-12-04','cider-Icd9ProcedureCode','2008-12-04','Icd9ProcedureCode',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (179,NULL,NULL,NULL,'id',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (180,'2008-12-04','cider-DiagnosisType','2008-12-04','DiagnosisType',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (181,NULL,NULL,NULL,'id',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (182,'2008-12-04','cider-EncounterDetails','2008-12-04','EncounterDetails',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (183,NULL,NULL,NULL,'AssociationName_32',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (184,NULL,NULL,NULL,'AssociationName_31',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (185,NULL,NULL,NULL,'AssociationName_30',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (186,NULL,NULL,NULL,'AssociationName_29',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (187,NULL,NULL,NULL,'AssociationName_28',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (188,'2008-12-04','cider-FinancialClass','2008-12-04','FinancialClass',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (189,NULL,NULL,NULL,'id',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (190,NULL,NULL,NULL,'AssociationName_27',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (191,'2008-12-04','cider-Insurance','2008-12-04','Insurance',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (192,NULL,NULL,NULL,'AssociationName_2',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (193,NULL,NULL,NULL,'AssociationName_1',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (194,NULL,NULL,NULL,'preAdmitCert',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (195,NULL,NULL,NULL,'policyName',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (196,NULL,NULL,NULL,'insuredGroupEmpName',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (197,NULL,NULL,NULL,'insuredGroupEmpId',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (198,NULL,NULL,NULL,'groupName',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (199,NULL,NULL,NULL,'groupNumber',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (200,NULL,NULL,NULL,'companyId',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (201,NULL,NULL,NULL,'planId',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (202,NULL,NULL,NULL,'sequence',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (203,NULL,NULL,NULL,'id',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (204,NULL,NULL,NULL,'AssociationName_26',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (205,NULL,NULL,NULL,'AssociationName_25',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (206,'2008-12-04','cider-ClinicalTrial','2008-12-04','ClinicalTrial',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (207,NULL,NULL,NULL,'AssociationName_1',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (208,NULL,NULL,NULL,'protocolId',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (209,NULL,NULL,NULL,'status',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (210,NULL,NULL,NULL,'endTimeStamp',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (211,NULL,NULL,NULL,'startTimeStamp',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (212,NULL,NULL,NULL,'trailName',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (213,NULL,NULL,NULL,'trialId',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (214,NULL,NULL,NULL,'id',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (215,NULL,NULL,NULL,'AssociationName_24',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (216,'2008-12-04','cider-Service','2008-12-04','Service',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (217,NULL,NULL,NULL,'id',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (218,NULL,NULL,NULL,'AssociationName_23',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (219,NULL,NULL,NULL,'AssociationName_22',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (220,NULL,NULL,NULL,'AssociationName_21',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (221,'2008-12-04','cider-Procedure','2008-12-04','Procedure',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (222,NULL,NULL,NULL,'AssociationName_5',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (223,NULL,NULL,NULL,'AssociationName_4',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (224,'2008-12-04','cider-ProcedureType','2008-12-04','ProcedureType',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (225,NULL,NULL,NULL,'id',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (226,NULL,NULL,NULL,'AssociationName_3',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (227,NULL,NULL,NULL,'AssociationName_2',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (228,NULL,NULL,NULL,'AssociationName_1',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (229,NULL,NULL,NULL,'sequence',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (230,NULL,NULL,NULL,'date',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (231,NULL,NULL,NULL,'id',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (232,NULL,NULL,NULL,'AssociationName_20',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (233,'2008-12-04','cider-HipaaNotified','2008-12-04','HipaaNotified',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (234,NULL,NULL,NULL,'id',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (235,NULL,NULL,NULL,'AssociationName_19',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (236,'2008-12-04','cider-InfectionControlCode','2008-12-04','InfectionControlCode',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (237,NULL,NULL,NULL,'id',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (238,NULL,NULL,NULL,'AssociationName_18',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (239,'2008-12-04','cider-DischargeDisposition','2008-12-04','DischargeDisposition',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (240,NULL,NULL,NULL,'id',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (241,NULL,NULL,NULL,'AssociationName_17',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (242,NULL,NULL,NULL,'AssociationName_4',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (243,NULL,NULL,NULL,'AssociationName_3',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (244,NULL,NULL,NULL,'AssociationName_2',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (245,NULL,NULL,NULL,'AssociationName_1',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (246,NULL,NULL,NULL,'admittingService',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (247,NULL,NULL,NULL,'dischargeService',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (248,NULL,NULL,NULL,'teachingTeam',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (249,NULL,NULL,NULL,'dischargeTimeStamp',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (250,NULL,NULL,NULL,'registrationTimeStamp',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (251,NULL,NULL,NULL,'ageAtEncounter',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (252,NULL,NULL,NULL,'effectingEndTimeStamp',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (253,NULL,NULL,NULL,'effectingStartTimeStamp',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (254,NULL,NULL,NULL,'id',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (255,NULL,NULL,NULL,'AssociationName_5',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (256,NULL,NULL,NULL,'AssociationName_6',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (257,NULL,NULL,NULL,'AssociationName_7',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (258,NULL,NULL,NULL,'AssociationName_8',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (259,NULL,NULL,NULL,'AssociationName_9',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (260,NULL,NULL,NULL,'AssociationName_10',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (261,NULL,NULL,NULL,'AssociationName_11',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (262,NULL,NULL,NULL,'AssociationName_12',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (263,'2008-12-04','cider-PatientClass','2008-12-04','PatientClass',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (264,NULL,NULL,NULL,'id',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (265,NULL,NULL,NULL,'AssociationName_13',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (266,'2008-12-04','cider-PatientType','2008-12-04','PatientType',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (267,NULL,NULL,NULL,'id',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (268,NULL,NULL,NULL,'AssociationName_14',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (269,'2008-12-04','cider-Diagnosis','2008-12-04','Diagnosis',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (270,NULL,NULL,NULL,'AssociationName_4',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (271,'2008-12-04','cider-DiagnosisCode','2008-12-04','DiagnosisCode',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (272,NULL,NULL,NULL,'id',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (273,NULL,NULL,NULL,'AssociationName_3',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (274,NULL,NULL,NULL,'AssociationName_2',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (275,NULL,NULL,NULL,'AssociationName_1',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (276,NULL,NULL,NULL,'sequence',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (277,NULL,NULL,NULL,'description',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (278,NULL,NULL,NULL,'id',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (279,NULL,NULL,NULL,'AssociationName_15',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (280,'2008-12-04','cider-OptOutIndicator','2008-12-04','OptOutIndicator',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (281,NULL,NULL,NULL,'id',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (282,NULL,NULL,NULL,'AssociationName_16',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (283,'2008-12-04','cider-CptProcedureCode','2008-12-04','CptProcedureCode',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (284,NULL,NULL,NULL,'id',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (285,'2008-12-04','cider-ResearchOptOut','2008-12-04','ResearchOptOut',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (286,NULL,NULL,NULL,'id',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (287,'2008-12-04','cider-MrnType','2008-12-04','MrnType',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (288,NULL,NULL,NULL,'id',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (289,'2008-12-04','cider-Encounter','2008-12-04','Encounter',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (290,NULL,NULL,NULL,'AssociationName_4',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (291,NULL,NULL,NULL,'AssociationName_3',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (292,NULL,NULL,NULL,'AssociationName_2',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (293,NULL,NULL,NULL,'AssociationName_1',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (294,NULL,NULL,NULL,'patientAccountNumber',NULL);
insert into `dyextn_abstract_metadata` (`IDENTIFIER`,`CREATED_DATE`,`DESCRIPTION`,`LAST_UPDATED`,`NAME`,`PUBLIC_ID`) values (295,NULL,NULL,NULL,'id',NULL);

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

insert into `dyextn_association` (`IDENTIFIER`,`IS_COLLECTION`,`DIRECTION`,`TARGET_ENTITY_ID`,`SOURCE_ROLE_ID`,`TARGET_ROLE_ID`,`IS_SYSTEM_GENERATED`) values (12,'\0','SRC_DESTINATION',8,1,2,'\0');
insert into `dyextn_association` (`IDENTIFIER`,`IS_COLLECTION`,`DIRECTION`,`TARGET_ENTITY_ID`,`SOURCE_ROLE_ID`,`TARGET_ROLE_ID`,`IS_SYSTEM_GENERATED`) values (22,'\0','SRC_DESTINATION',13,3,4,'\0');
insert into `dyextn_association` (`IDENTIFIER`,`IS_COLLECTION`,`DIRECTION`,`TARGET_ENTITY_ID`,`SOURCE_ROLE_ID`,`TARGET_ROLE_ID`,`IS_SYSTEM_GENERATED`) values (27,'\0','SRC_DESTINATION',25,5,6,'\0');
insert into `dyextn_association` (`IDENTIFIER`,`IS_COLLECTION`,`DIRECTION`,`TARGET_ENTITY_ID`,`SOURCE_ROLE_ID`,`TARGET_ROLE_ID`,`IS_SYSTEM_GENERATED`) values (30,'\0','SRC_DESTINATION',28,7,8,'\0');
insert into `dyextn_association` (`IDENTIFIER`,`IS_COLLECTION`,`DIRECTION`,`TARGET_ENTITY_ID`,`SOURCE_ROLE_ID`,`TARGET_ROLE_ID`,`IS_SYSTEM_GENERATED`) values (33,'\0','SRC_DESTINATION',31,9,10,'\0');
insert into `dyextn_association` (`IDENTIFIER`,`IS_COLLECTION`,`DIRECTION`,`TARGET_ENTITY_ID`,`SOURCE_ROLE_ID`,`TARGET_ROLE_ID`,`IS_SYSTEM_GENERATED`) values (36,'\0','SRC_DESTINATION',34,11,12,'\0');
insert into `dyextn_association` (`IDENTIFIER`,`IS_COLLECTION`,`DIRECTION`,`TARGET_ENTITY_ID`,`SOURCE_ROLE_ID`,`TARGET_ROLE_ID`,`IS_SYSTEM_GENERATED`) values (39,'\0','SRC_DESTINATION',37,13,14,'\0');
insert into `dyextn_association` (`IDENTIFIER`,`IS_COLLECTION`,`DIRECTION`,`TARGET_ENTITY_ID`,`SOURCE_ROLE_ID`,`TARGET_ROLE_ID`,`IS_SYSTEM_GENERATED`) values (42,'\0','SRC_DESTINATION',40,15,16,'\0');
insert into `dyextn_association` (`IDENTIFIER`,`IS_COLLECTION`,`DIRECTION`,`TARGET_ENTITY_ID`,`SOURCE_ROLE_ID`,`TARGET_ROLE_ID`,`IS_SYSTEM_GENERATED`) values (51,'\0','SRC_DESTINATION',13,17,18,'\0');
insert into `dyextn_association` (`IDENTIFIER`,`IS_COLLECTION`,`DIRECTION`,`TARGET_ENTITY_ID`,`SOURCE_ROLE_ID`,`TARGET_ROLE_ID`,`IS_SYSTEM_GENERATED`) values (55,'\0','SRC_DESTINATION',53,19,20,'\0');
insert into `dyextn_association` (`IDENTIFIER`,`IS_COLLECTION`,`DIRECTION`,`TARGET_ENTITY_ID`,`SOURCE_ROLE_ID`,`TARGET_ROLE_ID`,`IS_SYSTEM_GENERATED`) values (56,'\0','SRC_DESTINATION',23,21,22,'\0');
insert into `dyextn_association` (`IDENTIFIER`,`IS_COLLECTION`,`DIRECTION`,`TARGET_ENTITY_ID`,`SOURCE_ROLE_ID`,`TARGET_ROLE_ID`,`IS_SYSTEM_GENERATED`) values (58,'\0','SRC_DESTINATION',52,23,24,'\0');
insert into `dyextn_association` (`IDENTIFIER`,`IS_COLLECTION`,`DIRECTION`,`TARGET_ENTITY_ID`,`SOURCE_ROLE_ID`,`TARGET_ROLE_ID`,`IS_SYSTEM_GENERATED`) values (59,'\0','SRC_DESTINATION',52,25,26,'\0');
insert into `dyextn_association` (`IDENTIFIER`,`IS_COLLECTION`,`DIRECTION`,`TARGET_ENTITY_ID`,`SOURCE_ROLE_ID`,`TARGET_ROLE_ID`,`IS_SYSTEM_GENERATED`) values (64,'\0','SRC_DESTINATION',60,27,28,'\0');
insert into `dyextn_association` (`IDENTIFIER`,`IS_COLLECTION`,`DIRECTION`,`TARGET_ENTITY_ID`,`SOURCE_ROLE_ID`,`TARGET_ROLE_ID`,`IS_SYSTEM_GENERATED`) values (68,'\0','SRC_DESTINATION',66,29,30,'\0');
insert into `dyextn_association` (`IDENTIFIER`,`IS_COLLECTION`,`DIRECTION`,`TARGET_ENTITY_ID`,`SOURCE_ROLE_ID`,`TARGET_ROLE_ID`,`IS_SYSTEM_GENERATED`) values (71,'\0','SRC_DESTINATION',69,31,32,'\0');
insert into `dyextn_association` (`IDENTIFIER`,`IS_COLLECTION`,`DIRECTION`,`TARGET_ENTITY_ID`,`SOURCE_ROLE_ID`,`TARGET_ROLE_ID`,`IS_SYSTEM_GENERATED`) values (74,'\0','SRC_DESTINATION',72,33,34,'\0');
insert into `dyextn_association` (`IDENTIFIER`,`IS_COLLECTION`,`DIRECTION`,`TARGET_ENTITY_ID`,`SOURCE_ROLE_ID`,`TARGET_ROLE_ID`,`IS_SYSTEM_GENERATED`) values (80,'\0','SRC_DESTINATION',65,35,36,'\0');
insert into `dyextn_association` (`IDENTIFIER`,`IS_COLLECTION`,`DIRECTION`,`TARGET_ENTITY_ID`,`SOURCE_ROLE_ID`,`TARGET_ROLE_ID`,`IS_SYSTEM_GENERATED`) values (81,'\0','SRC_DESTINATION',24,37,38,'\0');
insert into `dyextn_association` (`IDENTIFIER`,`IS_COLLECTION`,`DIRECTION`,`TARGET_ENTITY_ID`,`SOURCE_ROLE_ID`,`TARGET_ROLE_ID`,`IS_SYSTEM_GENERATED`) values (84,'\0','SRC_DESTINATION',23,39,40,'\0');
insert into `dyextn_association` (`IDENTIFIER`,`IS_COLLECTION`,`DIRECTION`,`TARGET_ENTITY_ID`,`SOURCE_ROLE_ID`,`TARGET_ROLE_ID`,`IS_SYSTEM_GENERATED`) values (107,'\0','SRC_DESTINATION',98,41,42,'\0');
insert into `dyextn_association` (`IDENTIFIER`,`IS_COLLECTION`,`DIRECTION`,`TARGET_ENTITY_ID`,`SOURCE_ROLE_ID`,`TARGET_ROLE_ID`,`IS_SYSTEM_GENERATED`) values (111,'\0','SRC_DESTINATION',108,43,44,'\0');
insert into `dyextn_association` (`IDENTIFIER`,`IS_COLLECTION`,`DIRECTION`,`TARGET_ENTITY_ID`,`SOURCE_ROLE_ID`,`TARGET_ROLE_ID`,`IS_SYSTEM_GENERATED`) values (112,'\0','SRC_DESTINATION',23,45,46,'\0');
insert into `dyextn_association` (`IDENTIFIER`,`IS_COLLECTION`,`DIRECTION`,`TARGET_ENTITY_ID`,`SOURCE_ROLE_ID`,`TARGET_ROLE_ID`,`IS_SYSTEM_GENERATED`) values (115,'\0','SRC_DESTINATION',113,47,48,'\0');
insert into `dyextn_association` (`IDENTIFIER`,`IS_COLLECTION`,`DIRECTION`,`TARGET_ENTITY_ID`,`SOURCE_ROLE_ID`,`TARGET_ROLE_ID`,`IS_SYSTEM_GENERATED`) values (117,'\0','SRC_DESTINATION',102,49,50,'\0');
insert into `dyextn_association` (`IDENTIFIER`,`IS_COLLECTION`,`DIRECTION`,`TARGET_ENTITY_ID`,`SOURCE_ROLE_ID`,`TARGET_ROLE_ID`,`IS_SYSTEM_GENERATED`) values (125,'\0','SRC_DESTINATION',108,51,52,'\0');
insert into `dyextn_association` (`IDENTIFIER`,`IS_COLLECTION`,`DIRECTION`,`TARGET_ENTITY_ID`,`SOURCE_ROLE_ID`,`TARGET_ROLE_ID`,`IS_SYSTEM_GENERATED`) values (129,'\0','SRC_DESTINATION',124,53,54,'\0');
insert into `dyextn_association` (`IDENTIFIER`,`IS_COLLECTION`,`DIRECTION`,`TARGET_ENTITY_ID`,`SOURCE_ROLE_ID`,`TARGET_ROLE_ID`,`IS_SYSTEM_GENERATED`) values (130,'\0','SRC_DESTINATION',8,55,56,'\0');
insert into `dyextn_association` (`IDENTIFIER`,`IS_COLLECTION`,`DIRECTION`,`TARGET_ENTITY_ID`,`SOURCE_ROLE_ID`,`TARGET_ROLE_ID`,`IS_SYSTEM_GENERATED`) values (131,'\0','SRC_DESTINATION',8,57,58,'\0');
insert into `dyextn_association` (`IDENTIFIER`,`IS_COLLECTION`,`DIRECTION`,`TARGET_ENTITY_ID`,`SOURCE_ROLE_ID`,`TARGET_ROLE_ID`,`IS_SYSTEM_GENERATED`) values (133,'\0','SRC_DESTINATION',102,59,60,'\0');
insert into `dyextn_association` (`IDENTIFIER`,`IS_COLLECTION`,`DIRECTION`,`TARGET_ENTITY_ID`,`SOURCE_ROLE_ID`,`TARGET_ROLE_ID`,`IS_SYSTEM_GENERATED`) values (136,'\0','SRC_DESTINATION',93,61,62,'\0');
insert into `dyextn_association` (`IDENTIFIER`,`IS_COLLECTION`,`DIRECTION`,`TARGET_ENTITY_ID`,`SOURCE_ROLE_ID`,`TARGET_ROLE_ID`,`IS_SYSTEM_GENERATED`) values (142,'\0','SRC_DESTINATION',135,63,64,'\0');
insert into `dyextn_association` (`IDENTIFIER`,`IS_COLLECTION`,`DIRECTION`,`TARGET_ENTITY_ID`,`SOURCE_ROLE_ID`,`TARGET_ROLE_ID`,`IS_SYSTEM_GENERATED`) values (143,'\0','SRC_DESTINATION',135,65,66,'\0');
insert into `dyextn_association` (`IDENTIFIER`,`IS_COLLECTION`,`DIRECTION`,`TARGET_ENTITY_ID`,`SOURCE_ROLE_ID`,`TARGET_ROLE_ID`,`IS_SYSTEM_GENERATED`) values (145,'\0','SRC_DESTINATION',134,67,68,'\0');
insert into `dyextn_association` (`IDENTIFIER`,`IS_COLLECTION`,`DIRECTION`,`TARGET_ENTITY_ID`,`SOURCE_ROLE_ID`,`TARGET_ROLE_ID`,`IS_SYSTEM_GENERATED`) values (148,'\0','SRC_DESTINATION',146,69,70,'\0');
insert into `dyextn_association` (`IDENTIFIER`,`IS_COLLECTION`,`DIRECTION`,`TARGET_ENTITY_ID`,`SOURCE_ROLE_ID`,`TARGET_ROLE_ID`,`IS_SYSTEM_GENERATED`) values (152,'\0','SRC_DESTINATION',149,71,72,'\0');
insert into `dyextn_association` (`IDENTIFIER`,`IS_COLLECTION`,`DIRECTION`,`TARGET_ENTITY_ID`,`SOURCE_ROLE_ID`,`TARGET_ROLE_ID`,`IS_SYSTEM_GENERATED`) values (157,'\0','SRC_DESTINATION',132,73,74,'\0');
insert into `dyextn_association` (`IDENTIFIER`,`IS_COLLECTION`,`DIRECTION`,`TARGET_ENTITY_ID`,`SOURCE_ROLE_ID`,`TARGET_ROLE_ID`,`IS_SYSTEM_GENERATED`) values (160,'\0','SRC_DESTINATION',158,75,76,'\0');
insert into `dyextn_association` (`IDENTIFIER`,`IS_COLLECTION`,`DIRECTION`,`TARGET_ENTITY_ID`,`SOURCE_ROLE_ID`,`TARGET_ROLE_ID`,`IS_SYSTEM_GENERATED`) values (162,'\0','SRC_DESTINATION',108,77,78,'\0');
insert into `dyextn_association` (`IDENTIFIER`,`IS_COLLECTION`,`DIRECTION`,`TARGET_ENTITY_ID`,`SOURCE_ROLE_ID`,`TARGET_ROLE_ID`,`IS_SYSTEM_GENERATED`) values (165,'\0','SRC_DESTINATION',161,79,80,'\0');
insert into `dyextn_association` (`IDENTIFIER`,`IS_COLLECTION`,`DIRECTION`,`TARGET_ENTITY_ID`,`SOURCE_ROLE_ID`,`TARGET_ROLE_ID`,`IS_SYSTEM_GENERATED`) values (166,'\0','SRC_DESTINATION',108,81,82,'\0');
insert into `dyextn_association` (`IDENTIFIER`,`IS_COLLECTION`,`DIRECTION`,`TARGET_ENTITY_ID`,`SOURCE_ROLE_ID`,`TARGET_ROLE_ID`,`IS_SYSTEM_GENERATED`) values (167,'\0','SRC_DESTINATION',116,83,84,'\0');
insert into `dyextn_association` (`IDENTIFIER`,`IS_COLLECTION`,`DIRECTION`,`TARGET_ENTITY_ID`,`SOURCE_ROLE_ID`,`TARGET_ROLE_ID`,`IS_SYSTEM_GENERATED`) values (183,'\0','SRC_DESTINATION',2,85,86,'\0');
insert into `dyextn_association` (`IDENTIFIER`,`IS_COLLECTION`,`DIRECTION`,`TARGET_ENTITY_ID`,`SOURCE_ROLE_ID`,`TARGET_ROLE_ID`,`IS_SYSTEM_GENERATED`) values (184,'\0','SRC_DESTINATION',7,87,88,'\0');
insert into `dyextn_association` (`IDENTIFIER`,`IS_COLLECTION`,`DIRECTION`,`TARGET_ENTITY_ID`,`SOURCE_ROLE_ID`,`TARGET_ROLE_ID`,`IS_SYSTEM_GENERATED`) values (185,'\0','SRC_DESTINATION',89,89,90,'\0');
insert into `dyextn_association` (`IDENTIFIER`,`IS_COLLECTION`,`DIRECTION`,`TARGET_ENTITY_ID`,`SOURCE_ROLE_ID`,`TARGET_ROLE_ID`,`IS_SYSTEM_GENERATED`) values (186,'\0','SRC_DESTINATION',100,91,92,'\0');
insert into `dyextn_association` (`IDENTIFIER`,`IS_COLLECTION`,`DIRECTION`,`TARGET_ENTITY_ID`,`SOURCE_ROLE_ID`,`TARGET_ROLE_ID`,`IS_SYSTEM_GENERATED`) values (187,'\0','SRC_DESTINATION',104,93,94,'\0');
insert into `dyextn_association` (`IDENTIFIER`,`IS_COLLECTION`,`DIRECTION`,`TARGET_ENTITY_ID`,`SOURCE_ROLE_ID`,`TARGET_ROLE_ID`,`IS_SYSTEM_GENERATED`) values (190,'\0','SRC_DESTINATION',188,95,96,'\0');
insert into `dyextn_association` (`IDENTIFIER`,`IS_COLLECTION`,`DIRECTION`,`TARGET_ENTITY_ID`,`SOURCE_ROLE_ID`,`TARGET_ROLE_ID`,`IS_SYSTEM_GENERATED`) values (192,'\0','SRC_DESTINATION',8,97,98,'\0');
insert into `dyextn_association` (`IDENTIFIER`,`IS_COLLECTION`,`DIRECTION`,`TARGET_ENTITY_ID`,`SOURCE_ROLE_ID`,`TARGET_ROLE_ID`,`IS_SYSTEM_GENERATED`) values (193,'\0','SRC_DESTINATION',13,99,100,'\0');
insert into `dyextn_association` (`IDENTIFIER`,`IS_COLLECTION`,`DIRECTION`,`TARGET_ENTITY_ID`,`SOURCE_ROLE_ID`,`TARGET_ROLE_ID`,`IS_SYSTEM_GENERATED`) values (204,'\0','SRC_DESTINATION',191,101,102,'\0');
insert into `dyextn_association` (`IDENTIFIER`,`IS_COLLECTION`,`DIRECTION`,`TARGET_ENTITY_ID`,`SOURCE_ROLE_ID`,`TARGET_ROLE_ID`,`IS_SYSTEM_GENERATED`) values (205,'\0','SRC_DESTINATION',108,103,104,'\0');
insert into `dyextn_association` (`IDENTIFIER`,`IS_COLLECTION`,`DIRECTION`,`TARGET_ENTITY_ID`,`SOURCE_ROLE_ID`,`TARGET_ROLE_ID`,`IS_SYSTEM_GENERATED`) values (207,'\0','SRC_DESTINATION',13,105,106,'\0');
insert into `dyextn_association` (`IDENTIFIER`,`IS_COLLECTION`,`DIRECTION`,`TARGET_ENTITY_ID`,`SOURCE_ROLE_ID`,`TARGET_ROLE_ID`,`IS_SYSTEM_GENERATED`) values (215,'\0','SRC_DESTINATION',206,107,108,'\0');
insert into `dyextn_association` (`IDENTIFIER`,`IS_COLLECTION`,`DIRECTION`,`TARGET_ENTITY_ID`,`SOURCE_ROLE_ID`,`TARGET_ROLE_ID`,`IS_SYSTEM_GENERATED`) values (218,'\0','SRC_DESTINATION',216,109,110,'\0');
insert into `dyextn_association` (`IDENTIFIER`,`IS_COLLECTION`,`DIRECTION`,`TARGET_ENTITY_ID`,`SOURCE_ROLE_ID`,`TARGET_ROLE_ID`,`IS_SYSTEM_GENERATED`) values (219,'\0','SRC_DESTINATION',216,111,112,'\0');
insert into `dyextn_association` (`IDENTIFIER`,`IS_COLLECTION`,`DIRECTION`,`TARGET_ENTITY_ID`,`SOURCE_ROLE_ID`,`TARGET_ROLE_ID`,`IS_SYSTEM_GENERATED`) values (220,'\0','SRC_DESTINATION',161,113,114,'\0');
insert into `dyextn_association` (`IDENTIFIER`,`IS_COLLECTION`,`DIRECTION`,`TARGET_ENTITY_ID`,`SOURCE_ROLE_ID`,`TARGET_ROLE_ID`,`IS_SYSTEM_GENERATED`) values (222,'\0','SRC_DESTINATION',7,115,116,'\0');
insert into `dyextn_association` (`IDENTIFIER`,`IS_COLLECTION`,`DIRECTION`,`TARGET_ENTITY_ID`,`SOURCE_ROLE_ID`,`TARGET_ROLE_ID`,`IS_SYSTEM_GENERATED`) values (223,'\0','SRC_DESTINATION',176,117,118,'\0');
insert into `dyextn_association` (`IDENTIFIER`,`IS_COLLECTION`,`DIRECTION`,`TARGET_ENTITY_ID`,`SOURCE_ROLE_ID`,`TARGET_ROLE_ID`,`IS_SYSTEM_GENERATED`) values (226,'\0','SRC_DESTINATION',224,119,120,'\0');
insert into `dyextn_association` (`IDENTIFIER`,`IS_COLLECTION`,`DIRECTION`,`TARGET_ENTITY_ID`,`SOURCE_ROLE_ID`,`TARGET_ROLE_ID`,`IS_SYSTEM_GENERATED`) values (227,'\0','SRC_DESTINATION',8,121,122,'\0');
insert into `dyextn_association` (`IDENTIFIER`,`IS_COLLECTION`,`DIRECTION`,`TARGET_ENTITY_ID`,`SOURCE_ROLE_ID`,`TARGET_ROLE_ID`,`IS_SYSTEM_GENERATED`) values (228,'\0','SRC_DESTINATION',8,123,124,'\0');
insert into `dyextn_association` (`IDENTIFIER`,`IS_COLLECTION`,`DIRECTION`,`TARGET_ENTITY_ID`,`SOURCE_ROLE_ID`,`TARGET_ROLE_ID`,`IS_SYSTEM_GENERATED`) values (232,'\0','SRC_DESTINATION',221,125,126,'\0');
insert into `dyextn_association` (`IDENTIFIER`,`IS_COLLECTION`,`DIRECTION`,`TARGET_ENTITY_ID`,`SOURCE_ROLE_ID`,`TARGET_ROLE_ID`,`IS_SYSTEM_GENERATED`) values (235,'\0','SRC_DESTINATION',233,127,128,'\0');
insert into `dyextn_association` (`IDENTIFIER`,`IS_COLLECTION`,`DIRECTION`,`TARGET_ENTITY_ID`,`SOURCE_ROLE_ID`,`TARGET_ROLE_ID`,`IS_SYSTEM_GENERATED`) values (238,'\0','SRC_DESTINATION',236,129,130,'\0');
insert into `dyextn_association` (`IDENTIFIER`,`IS_COLLECTION`,`DIRECTION`,`TARGET_ENTITY_ID`,`SOURCE_ROLE_ID`,`TARGET_ROLE_ID`,`IS_SYSTEM_GENERATED`) values (241,'\0','SRC_DESTINATION',239,131,132,'\0');
insert into `dyextn_association` (`IDENTIFIER`,`IS_COLLECTION`,`DIRECTION`,`TARGET_ENTITY_ID`,`SOURCE_ROLE_ID`,`TARGET_ROLE_ID`,`IS_SYSTEM_GENERATED`) values (242,'\0','SRC_DESTINATION',8,133,134,'\0');
insert into `dyextn_association` (`IDENTIFIER`,`IS_COLLECTION`,`DIRECTION`,`TARGET_ENTITY_ID`,`SOURCE_ROLE_ID`,`TARGET_ROLE_ID`,`IS_SYSTEM_GENERATED`) values (243,'\0','SRC_DESTINATION',8,135,136,'\0');
insert into `dyextn_association` (`IDENTIFIER`,`IS_COLLECTION`,`DIRECTION`,`TARGET_ENTITY_ID`,`SOURCE_ROLE_ID`,`TARGET_ROLE_ID`,`IS_SYSTEM_GENERATED`) values (244,'\0','SRC_DESTINATION',8,137,138,'\0');
insert into `dyextn_association` (`IDENTIFIER`,`IS_COLLECTION`,`DIRECTION`,`TARGET_ENTITY_ID`,`SOURCE_ROLE_ID`,`TARGET_ROLE_ID`,`IS_SYSTEM_GENERATED`) values (245,'\0','SRC_DESTINATION',8,139,140,'\0');
insert into `dyextn_association` (`IDENTIFIER`,`IS_COLLECTION`,`DIRECTION`,`TARGET_ENTITY_ID`,`SOURCE_ROLE_ID`,`TARGET_ROLE_ID`,`IS_SYSTEM_GENERATED`) values (255,'\0','SRC_DESTINATION',8,141,142,'\0');
insert into `dyextn_association` (`IDENTIFIER`,`IS_COLLECTION`,`DIRECTION`,`TARGET_ENTITY_ID`,`SOURCE_ROLE_ID`,`TARGET_ROLE_ID`,`IS_SYSTEM_GENERATED`) values (256,'\0','SRC_DESTINATION',8,143,144,'\0');
insert into `dyextn_association` (`IDENTIFIER`,`IS_COLLECTION`,`DIRECTION`,`TARGET_ENTITY_ID`,`SOURCE_ROLE_ID`,`TARGET_ROLE_ID`,`IS_SYSTEM_GENERATED`) values (257,'\0','SRC_DESTINATION',8,145,146,'\0');
insert into `dyextn_association` (`IDENTIFIER`,`IS_COLLECTION`,`DIRECTION`,`TARGET_ENTITY_ID`,`SOURCE_ROLE_ID`,`TARGET_ROLE_ID`,`IS_SYSTEM_GENERATED`) values (258,'\0','SRC_DESTINATION',8,147,148,'\0');
insert into `dyextn_association` (`IDENTIFIER`,`IS_COLLECTION`,`DIRECTION`,`TARGET_ENTITY_ID`,`SOURCE_ROLE_ID`,`TARGET_ROLE_ID`,`IS_SYSTEM_GENERATED`) values (259,'\0','SRC_DESTINATION',8,149,150,'\0');
insert into `dyextn_association` (`IDENTIFIER`,`IS_COLLECTION`,`DIRECTION`,`TARGET_ENTITY_ID`,`SOURCE_ROLE_ID`,`TARGET_ROLE_ID`,`IS_SYSTEM_GENERATED`) values (260,'\0','SRC_DESTINATION',8,151,152,'\0');
insert into `dyextn_association` (`IDENTIFIER`,`IS_COLLECTION`,`DIRECTION`,`TARGET_ENTITY_ID`,`SOURCE_ROLE_ID`,`TARGET_ROLE_ID`,`IS_SYSTEM_GENERATED`) values (261,'\0','SRC_DESTINATION',8,153,154,'\0');
insert into `dyextn_association` (`IDENTIFIER`,`IS_COLLECTION`,`DIRECTION`,`TARGET_ENTITY_ID`,`SOURCE_ROLE_ID`,`TARGET_ROLE_ID`,`IS_SYSTEM_GENERATED`) values (262,'\0','SRC_DESTINATION',8,155,156,'\0');
insert into `dyextn_association` (`IDENTIFIER`,`IS_COLLECTION`,`DIRECTION`,`TARGET_ENTITY_ID`,`SOURCE_ROLE_ID`,`TARGET_ROLE_ID`,`IS_SYSTEM_GENERATED`) values (265,'\0','SRC_DESTINATION',263,157,158,'\0');
insert into `dyextn_association` (`IDENTIFIER`,`IS_COLLECTION`,`DIRECTION`,`TARGET_ENTITY_ID`,`SOURCE_ROLE_ID`,`TARGET_ROLE_ID`,`IS_SYSTEM_GENERATED`) values (268,'\0','SRC_DESTINATION',266,159,160,'\0');
insert into `dyextn_association` (`IDENTIFIER`,`IS_COLLECTION`,`DIRECTION`,`TARGET_ENTITY_ID`,`SOURCE_ROLE_ID`,`TARGET_ROLE_ID`,`IS_SYSTEM_GENERATED`) values (270,'\0','SRC_DESTINATION',180,161,162,'\0');
insert into `dyextn_association` (`IDENTIFIER`,`IS_COLLECTION`,`DIRECTION`,`TARGET_ENTITY_ID`,`SOURCE_ROLE_ID`,`TARGET_ROLE_ID`,`IS_SYSTEM_GENERATED`) values (273,'\0','SRC_DESTINATION',271,163,164,'\0');
insert into `dyextn_association` (`IDENTIFIER`,`IS_COLLECTION`,`DIRECTION`,`TARGET_ENTITY_ID`,`SOURCE_ROLE_ID`,`TARGET_ROLE_ID`,`IS_SYSTEM_GENERATED`) values (274,'\0','SRC_DESTINATION',8,165,166,'\0');
insert into `dyextn_association` (`IDENTIFIER`,`IS_COLLECTION`,`DIRECTION`,`TARGET_ENTITY_ID`,`SOURCE_ROLE_ID`,`TARGET_ROLE_ID`,`IS_SYSTEM_GENERATED`) values (275,'\0','SRC_DESTINATION',8,167,168,'\0');
insert into `dyextn_association` (`IDENTIFIER`,`IS_COLLECTION`,`DIRECTION`,`TARGET_ENTITY_ID`,`SOURCE_ROLE_ID`,`TARGET_ROLE_ID`,`IS_SYSTEM_GENERATED`) values (279,'\0','SRC_DESTINATION',269,169,170,'\0');
insert into `dyextn_association` (`IDENTIFIER`,`IS_COLLECTION`,`DIRECTION`,`TARGET_ENTITY_ID`,`SOURCE_ROLE_ID`,`TARGET_ROLE_ID`,`IS_SYSTEM_GENERATED`) values (282,'\0','SRC_DESTINATION',280,171,172,'\0');
insert into `dyextn_association` (`IDENTIFIER`,`IS_COLLECTION`,`DIRECTION`,`TARGET_ENTITY_ID`,`SOURCE_ROLE_ID`,`TARGET_ROLE_ID`,`IS_SYSTEM_GENERATED`) values (290,'\0','SRC_DESTINATION',182,173,174,'\0');
insert into `dyextn_association` (`IDENTIFIER`,`IS_COLLECTION`,`DIRECTION`,`TARGET_ENTITY_ID`,`SOURCE_ROLE_ID`,`TARGET_ROLE_ID`,`IS_SYSTEM_GENERATED`) values (291,'\0','SRC_DESTINATION',23,175,176,'\0');
insert into `dyextn_association` (`IDENTIFIER`,`IS_COLLECTION`,`DIRECTION`,`TARGET_ENTITY_ID`,`SOURCE_ROLE_ID`,`TARGET_ROLE_ID`,`IS_SYSTEM_GENERATED`) values (292,'\0','SRC_DESTINATION',124,177,178,'\0');
insert into `dyextn_association` (`IDENTIFIER`,`IS_COLLECTION`,`DIRECTION`,`TARGET_ENTITY_ID`,`SOURCE_ROLE_ID`,`TARGET_ROLE_ID`,`IS_SYSTEM_GENERATED`) values (293,'\0','SRC_DESTINATION',108,179,180,'\0');

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

insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (3,2);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (4,2);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (5,2);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (6,2);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (9,8);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (10,8);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (11,8);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (12,7);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (14,13);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (15,13);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (16,13);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (17,13);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (18,13);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (19,13);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (20,13);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (21,13);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (22,7);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (26,25);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (27,24);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (29,28);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (30,24);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (32,31);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (33,24);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (35,34);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (36,24);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (38,37);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (39,24);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (41,40);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (42,24);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (43,24);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (44,24);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (45,24);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (46,24);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (47,24);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (48,24);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (49,24);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (50,24);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (51,24);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (54,53);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (55,52);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (56,52);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (57,52);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (58,24);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (59,24);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (61,60);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (62,60);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (63,60);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (64,24);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (67,66);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (68,65);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (70,69);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (71,65);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (73,72);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (74,65);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (75,65);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (76,65);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (77,65);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (78,65);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (79,65);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (80,24);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (81,23);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (82,23);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (83,23);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (84,7);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (85,7);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (86,7);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (88,87);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (90,89);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (92,91);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (94,93);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (95,93);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (96,93);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (97,93);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (99,98);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (101,100);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (103,102);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (105,104);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (107,106);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (109,108);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (110,108);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (111,106);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (112,106);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (114,113);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (115,106);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (117,116);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (118,116);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (119,116);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (120,116);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (121,116);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (122,116);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (123,116);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (125,124);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (126,124);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (127,124);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (128,124);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (129,116);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (130,116);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (131,116);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (133,132);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (136,135);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (137,135);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (138,135);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (139,135);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (140,135);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (141,135);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (142,134);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (143,134);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (144,134);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (145,132);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (147,146);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (148,132);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (150,149);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (151,149);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (152,132);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (153,132);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (154,132);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (155,132);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (156,132);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (157,116);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (159,158);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (160,116);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (162,161);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (163,161);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (164,161);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (165,116);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (166,116);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (167,106);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (168,106);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (169,106);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (170,106);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (171,106);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (173,172);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (174,172);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (175,172);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (177,176);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (179,178);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (181,180);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (183,182);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (184,182);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (185,182);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (186,182);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (187,182);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (189,188);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (190,182);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (192,191);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (193,191);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (194,191);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (195,191);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (196,191);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (197,191);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (198,191);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (199,191);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (200,191);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (201,191);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (202,191);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (203,191);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (204,182);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (205,182);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (207,206);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (208,206);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (209,206);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (210,206);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (211,206);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (212,206);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (213,206);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (214,206);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (215,182);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (217,216);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (218,182);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (219,182);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (220,182);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (222,221);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (223,221);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (225,224);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (226,221);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (227,221);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (228,221);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (229,221);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (230,221);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (231,221);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (232,182);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (234,233);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (235,182);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (237,236);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (238,182);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (240,239);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (241,182);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (242,182);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (243,182);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (244,182);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (245,182);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (246,182);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (247,182);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (248,182);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (249,182);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (250,182);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (251,182);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (252,182);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (253,182);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (254,182);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (255,182);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (256,182);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (257,182);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (258,182);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (259,182);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (260,182);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (261,182);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (262,182);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (264,263);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (265,182);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (267,266);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (268,182);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (270,269);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (272,271);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (273,269);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (274,269);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (275,269);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (276,269);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (277,269);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (278,269);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (279,182);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (281,280);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (282,182);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (284,283);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (286,285);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (288,287);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (290,289);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (291,289);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (292,289);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (293,289);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (294,289);
insert into `dyextn_attribute` (`IDENTIFIER`,`ENTIY_ID`) values (295,289);

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

insert into `dyextn_attribute_type_info` (`IDENTIFIER`,`PRIMITIVE_ATTRIBUTE_ID`) values (1,3);
insert into `dyextn_attribute_type_info` (`IDENTIFIER`,`PRIMITIVE_ATTRIBUTE_ID`) values (2,4);
insert into `dyextn_attribute_type_info` (`IDENTIFIER`,`PRIMITIVE_ATTRIBUTE_ID`) values (3,5);
insert into `dyextn_attribute_type_info` (`IDENTIFIER`,`PRIMITIVE_ATTRIBUTE_ID`) values (4,6);
insert into `dyextn_attribute_type_info` (`IDENTIFIER`,`PRIMITIVE_ATTRIBUTE_ID`) values (5,9);
insert into `dyextn_attribute_type_info` (`IDENTIFIER`,`PRIMITIVE_ATTRIBUTE_ID`) values (6,10);
insert into `dyextn_attribute_type_info` (`IDENTIFIER`,`PRIMITIVE_ATTRIBUTE_ID`) values (7,11);
insert into `dyextn_attribute_type_info` (`IDENTIFIER`,`PRIMITIVE_ATTRIBUTE_ID`) values (8,14);
insert into `dyextn_attribute_type_info` (`IDENTIFIER`,`PRIMITIVE_ATTRIBUTE_ID`) values (9,15);
insert into `dyextn_attribute_type_info` (`IDENTIFIER`,`PRIMITIVE_ATTRIBUTE_ID`) values (10,16);
insert into `dyextn_attribute_type_info` (`IDENTIFIER`,`PRIMITIVE_ATTRIBUTE_ID`) values (11,17);
insert into `dyextn_attribute_type_info` (`IDENTIFIER`,`PRIMITIVE_ATTRIBUTE_ID`) values (12,18);
insert into `dyextn_attribute_type_info` (`IDENTIFIER`,`PRIMITIVE_ATTRIBUTE_ID`) values (13,19);
insert into `dyextn_attribute_type_info` (`IDENTIFIER`,`PRIMITIVE_ATTRIBUTE_ID`) values (14,20);
insert into `dyextn_attribute_type_info` (`IDENTIFIER`,`PRIMITIVE_ATTRIBUTE_ID`) values (15,21);
insert into `dyextn_attribute_type_info` (`IDENTIFIER`,`PRIMITIVE_ATTRIBUTE_ID`) values (16,26);
insert into `dyextn_attribute_type_info` (`IDENTIFIER`,`PRIMITIVE_ATTRIBUTE_ID`) values (17,29);
insert into `dyextn_attribute_type_info` (`IDENTIFIER`,`PRIMITIVE_ATTRIBUTE_ID`) values (18,32);
insert into `dyextn_attribute_type_info` (`IDENTIFIER`,`PRIMITIVE_ATTRIBUTE_ID`) values (19,35);
insert into `dyextn_attribute_type_info` (`IDENTIFIER`,`PRIMITIVE_ATTRIBUTE_ID`) values (20,38);
insert into `dyextn_attribute_type_info` (`IDENTIFIER`,`PRIMITIVE_ATTRIBUTE_ID`) values (21,41);
insert into `dyextn_attribute_type_info` (`IDENTIFIER`,`PRIMITIVE_ATTRIBUTE_ID`) values (22,43);
insert into `dyextn_attribute_type_info` (`IDENTIFIER`,`PRIMITIVE_ATTRIBUTE_ID`) values (23,44);
insert into `dyextn_attribute_type_info` (`IDENTIFIER`,`PRIMITIVE_ATTRIBUTE_ID`) values (24,45);
insert into `dyextn_attribute_type_info` (`IDENTIFIER`,`PRIMITIVE_ATTRIBUTE_ID`) values (25,46);
insert into `dyextn_attribute_type_info` (`IDENTIFIER`,`PRIMITIVE_ATTRIBUTE_ID`) values (26,47);
insert into `dyextn_attribute_type_info` (`IDENTIFIER`,`PRIMITIVE_ATTRIBUTE_ID`) values (27,48);
insert into `dyextn_attribute_type_info` (`IDENTIFIER`,`PRIMITIVE_ATTRIBUTE_ID`) values (28,49);
insert into `dyextn_attribute_type_info` (`IDENTIFIER`,`PRIMITIVE_ATTRIBUTE_ID`) values (29,50);
insert into `dyextn_attribute_type_info` (`IDENTIFIER`,`PRIMITIVE_ATTRIBUTE_ID`) values (30,54);
insert into `dyextn_attribute_type_info` (`IDENTIFIER`,`PRIMITIVE_ATTRIBUTE_ID`) values (31,57);
insert into `dyextn_attribute_type_info` (`IDENTIFIER`,`PRIMITIVE_ATTRIBUTE_ID`) values (32,61);
insert into `dyextn_attribute_type_info` (`IDENTIFIER`,`PRIMITIVE_ATTRIBUTE_ID`) values (33,62);
insert into `dyextn_attribute_type_info` (`IDENTIFIER`,`PRIMITIVE_ATTRIBUTE_ID`) values (34,63);
insert into `dyextn_attribute_type_info` (`IDENTIFIER`,`PRIMITIVE_ATTRIBUTE_ID`) values (35,67);
insert into `dyextn_attribute_type_info` (`IDENTIFIER`,`PRIMITIVE_ATTRIBUTE_ID`) values (36,70);
insert into `dyextn_attribute_type_info` (`IDENTIFIER`,`PRIMITIVE_ATTRIBUTE_ID`) values (37,73);
insert into `dyextn_attribute_type_info` (`IDENTIFIER`,`PRIMITIVE_ATTRIBUTE_ID`) values (38,75);
insert into `dyextn_attribute_type_info` (`IDENTIFIER`,`PRIMITIVE_ATTRIBUTE_ID`) values (39,76);
insert into `dyextn_attribute_type_info` (`IDENTIFIER`,`PRIMITIVE_ATTRIBUTE_ID`) values (40,77);
insert into `dyextn_attribute_type_info` (`IDENTIFIER`,`PRIMITIVE_ATTRIBUTE_ID`) values (41,78);
insert into `dyextn_attribute_type_info` (`IDENTIFIER`,`PRIMITIVE_ATTRIBUTE_ID`) values (42,79);
insert into `dyextn_attribute_type_info` (`IDENTIFIER`,`PRIMITIVE_ATTRIBUTE_ID`) values (43,82);
insert into `dyextn_attribute_type_info` (`IDENTIFIER`,`PRIMITIVE_ATTRIBUTE_ID`) values (44,83);
insert into `dyextn_attribute_type_info` (`IDENTIFIER`,`PRIMITIVE_ATTRIBUTE_ID`) values (45,85);
insert into `dyextn_attribute_type_info` (`IDENTIFIER`,`PRIMITIVE_ATTRIBUTE_ID`) values (46,86);
insert into `dyextn_attribute_type_info` (`IDENTIFIER`,`PRIMITIVE_ATTRIBUTE_ID`) values (47,88);
insert into `dyextn_attribute_type_info` (`IDENTIFIER`,`PRIMITIVE_ATTRIBUTE_ID`) values (48,90);
insert into `dyextn_attribute_type_info` (`IDENTIFIER`,`PRIMITIVE_ATTRIBUTE_ID`) values (49,92);
insert into `dyextn_attribute_type_info` (`IDENTIFIER`,`PRIMITIVE_ATTRIBUTE_ID`) values (50,94);
insert into `dyextn_attribute_type_info` (`IDENTIFIER`,`PRIMITIVE_ATTRIBUTE_ID`) values (51,95);
insert into `dyextn_attribute_type_info` (`IDENTIFIER`,`PRIMITIVE_ATTRIBUTE_ID`) values (52,96);
insert into `dyextn_attribute_type_info` (`IDENTIFIER`,`PRIMITIVE_ATTRIBUTE_ID`) values (53,97);
insert into `dyextn_attribute_type_info` (`IDENTIFIER`,`PRIMITIVE_ATTRIBUTE_ID`) values (54,99);
insert into `dyextn_attribute_type_info` (`IDENTIFIER`,`PRIMITIVE_ATTRIBUTE_ID`) values (55,101);
insert into `dyextn_attribute_type_info` (`IDENTIFIER`,`PRIMITIVE_ATTRIBUTE_ID`) values (56,103);
insert into `dyextn_attribute_type_info` (`IDENTIFIER`,`PRIMITIVE_ATTRIBUTE_ID`) values (57,105);
insert into `dyextn_attribute_type_info` (`IDENTIFIER`,`PRIMITIVE_ATTRIBUTE_ID`) values (58,109);
insert into `dyextn_attribute_type_info` (`IDENTIFIER`,`PRIMITIVE_ATTRIBUTE_ID`) values (59,110);
insert into `dyextn_attribute_type_info` (`IDENTIFIER`,`PRIMITIVE_ATTRIBUTE_ID`) values (60,114);
insert into `dyextn_attribute_type_info` (`IDENTIFIER`,`PRIMITIVE_ATTRIBUTE_ID`) values (61,118);
insert into `dyextn_attribute_type_info` (`IDENTIFIER`,`PRIMITIVE_ATTRIBUTE_ID`) values (62,119);
insert into `dyextn_attribute_type_info` (`IDENTIFIER`,`PRIMITIVE_ATTRIBUTE_ID`) values (63,120);
insert into `dyextn_attribute_type_info` (`IDENTIFIER`,`PRIMITIVE_ATTRIBUTE_ID`) values (64,121);
insert into `dyextn_attribute_type_info` (`IDENTIFIER`,`PRIMITIVE_ATTRIBUTE_ID`) values (65,122);
insert into `dyextn_attribute_type_info` (`IDENTIFIER`,`PRIMITIVE_ATTRIBUTE_ID`) values (66,123);
insert into `dyextn_attribute_type_info` (`IDENTIFIER`,`PRIMITIVE_ATTRIBUTE_ID`) values (67,126);
insert into `dyextn_attribute_type_info` (`IDENTIFIER`,`PRIMITIVE_ATTRIBUTE_ID`) values (68,127);
insert into `dyextn_attribute_type_info` (`IDENTIFIER`,`PRIMITIVE_ATTRIBUTE_ID`) values (69,128);
insert into `dyextn_attribute_type_info` (`IDENTIFIER`,`PRIMITIVE_ATTRIBUTE_ID`) values (70,137);
insert into `dyextn_attribute_type_info` (`IDENTIFIER`,`PRIMITIVE_ATTRIBUTE_ID`) values (71,138);
insert into `dyextn_attribute_type_info` (`IDENTIFIER`,`PRIMITIVE_ATTRIBUTE_ID`) values (72,139);
insert into `dyextn_attribute_type_info` (`IDENTIFIER`,`PRIMITIVE_ATTRIBUTE_ID`) values (73,140);
insert into `dyextn_attribute_type_info` (`IDENTIFIER`,`PRIMITIVE_ATTRIBUTE_ID`) values (74,141);
insert into `dyextn_attribute_type_info` (`IDENTIFIER`,`PRIMITIVE_ATTRIBUTE_ID`) values (75,144);
insert into `dyextn_attribute_type_info` (`IDENTIFIER`,`PRIMITIVE_ATTRIBUTE_ID`) values (76,147);
insert into `dyextn_attribute_type_info` (`IDENTIFIER`,`PRIMITIVE_ATTRIBUTE_ID`) values (77,150);
insert into `dyextn_attribute_type_info` (`IDENTIFIER`,`PRIMITIVE_ATTRIBUTE_ID`) values (78,151);
insert into `dyextn_attribute_type_info` (`IDENTIFIER`,`PRIMITIVE_ATTRIBUTE_ID`) values (79,153);
insert into `dyextn_attribute_type_info` (`IDENTIFIER`,`PRIMITIVE_ATTRIBUTE_ID`) values (80,154);
insert into `dyextn_attribute_type_info` (`IDENTIFIER`,`PRIMITIVE_ATTRIBUTE_ID`) values (81,155);
insert into `dyextn_attribute_type_info` (`IDENTIFIER`,`PRIMITIVE_ATTRIBUTE_ID`) values (82,156);
insert into `dyextn_attribute_type_info` (`IDENTIFIER`,`PRIMITIVE_ATTRIBUTE_ID`) values (83,159);
insert into `dyextn_attribute_type_info` (`IDENTIFIER`,`PRIMITIVE_ATTRIBUTE_ID`) values (84,163);
insert into `dyextn_attribute_type_info` (`IDENTIFIER`,`PRIMITIVE_ATTRIBUTE_ID`) values (85,164);
insert into `dyextn_attribute_type_info` (`IDENTIFIER`,`PRIMITIVE_ATTRIBUTE_ID`) values (86,168);
insert into `dyextn_attribute_type_info` (`IDENTIFIER`,`PRIMITIVE_ATTRIBUTE_ID`) values (87,169);
insert into `dyextn_attribute_type_info` (`IDENTIFIER`,`PRIMITIVE_ATTRIBUTE_ID`) values (88,170);
insert into `dyextn_attribute_type_info` (`IDENTIFIER`,`PRIMITIVE_ATTRIBUTE_ID`) values (89,171);
insert into `dyextn_attribute_type_info` (`IDENTIFIER`,`PRIMITIVE_ATTRIBUTE_ID`) values (90,173);
insert into `dyextn_attribute_type_info` (`IDENTIFIER`,`PRIMITIVE_ATTRIBUTE_ID`) values (91,174);
insert into `dyextn_attribute_type_info` (`IDENTIFIER`,`PRIMITIVE_ATTRIBUTE_ID`) values (92,175);
insert into `dyextn_attribute_type_info` (`IDENTIFIER`,`PRIMITIVE_ATTRIBUTE_ID`) values (93,177);
insert into `dyextn_attribute_type_info` (`IDENTIFIER`,`PRIMITIVE_ATTRIBUTE_ID`) values (94,179);
insert into `dyextn_attribute_type_info` (`IDENTIFIER`,`PRIMITIVE_ATTRIBUTE_ID`) values (95,181);
insert into `dyextn_attribute_type_info` (`IDENTIFIER`,`PRIMITIVE_ATTRIBUTE_ID`) values (96,189);
insert into `dyextn_attribute_type_info` (`IDENTIFIER`,`PRIMITIVE_ATTRIBUTE_ID`) values (97,194);
insert into `dyextn_attribute_type_info` (`IDENTIFIER`,`PRIMITIVE_ATTRIBUTE_ID`) values (98,195);
insert into `dyextn_attribute_type_info` (`IDENTIFIER`,`PRIMITIVE_ATTRIBUTE_ID`) values (99,196);
insert into `dyextn_attribute_type_info` (`IDENTIFIER`,`PRIMITIVE_ATTRIBUTE_ID`) values (100,197);
insert into `dyextn_attribute_type_info` (`IDENTIFIER`,`PRIMITIVE_ATTRIBUTE_ID`) values (101,198);
insert into `dyextn_attribute_type_info` (`IDENTIFIER`,`PRIMITIVE_ATTRIBUTE_ID`) values (102,199);
insert into `dyextn_attribute_type_info` (`IDENTIFIER`,`PRIMITIVE_ATTRIBUTE_ID`) values (103,200);
insert into `dyextn_attribute_type_info` (`IDENTIFIER`,`PRIMITIVE_ATTRIBUTE_ID`) values (104,201);
insert into `dyextn_attribute_type_info` (`IDENTIFIER`,`PRIMITIVE_ATTRIBUTE_ID`) values (105,202);
insert into `dyextn_attribute_type_info` (`IDENTIFIER`,`PRIMITIVE_ATTRIBUTE_ID`) values (106,203);
insert into `dyextn_attribute_type_info` (`IDENTIFIER`,`PRIMITIVE_ATTRIBUTE_ID`) values (107,208);
insert into `dyextn_attribute_type_info` (`IDENTIFIER`,`PRIMITIVE_ATTRIBUTE_ID`) values (108,209);
insert into `dyextn_attribute_type_info` (`IDENTIFIER`,`PRIMITIVE_ATTRIBUTE_ID`) values (109,210);
insert into `dyextn_attribute_type_info` (`IDENTIFIER`,`PRIMITIVE_ATTRIBUTE_ID`) values (110,211);
insert into `dyextn_attribute_type_info` (`IDENTIFIER`,`PRIMITIVE_ATTRIBUTE_ID`) values (111,212);
insert into `dyextn_attribute_type_info` (`IDENTIFIER`,`PRIMITIVE_ATTRIBUTE_ID`) values (112,213);
insert into `dyextn_attribute_type_info` (`IDENTIFIER`,`PRIMITIVE_ATTRIBUTE_ID`) values (113,214);
insert into `dyextn_attribute_type_info` (`IDENTIFIER`,`PRIMITIVE_ATTRIBUTE_ID`) values (114,217);
insert into `dyextn_attribute_type_info` (`IDENTIFIER`,`PRIMITIVE_ATTRIBUTE_ID`) values (115,225);
insert into `dyextn_attribute_type_info` (`IDENTIFIER`,`PRIMITIVE_ATTRIBUTE_ID`) values (116,229);
insert into `dyextn_attribute_type_info` (`IDENTIFIER`,`PRIMITIVE_ATTRIBUTE_ID`) values (117,230);
insert into `dyextn_attribute_type_info` (`IDENTIFIER`,`PRIMITIVE_ATTRIBUTE_ID`) values (118,231);
insert into `dyextn_attribute_type_info` (`IDENTIFIER`,`PRIMITIVE_ATTRIBUTE_ID`) values (119,234);
insert into `dyextn_attribute_type_info` (`IDENTIFIER`,`PRIMITIVE_ATTRIBUTE_ID`) values (120,237);
insert into `dyextn_attribute_type_info` (`IDENTIFIER`,`PRIMITIVE_ATTRIBUTE_ID`) values (121,240);
insert into `dyextn_attribute_type_info` (`IDENTIFIER`,`PRIMITIVE_ATTRIBUTE_ID`) values (122,246);
insert into `dyextn_attribute_type_info` (`IDENTIFIER`,`PRIMITIVE_ATTRIBUTE_ID`) values (123,247);
insert into `dyextn_attribute_type_info` (`IDENTIFIER`,`PRIMITIVE_ATTRIBUTE_ID`) values (124,248);
insert into `dyextn_attribute_type_info` (`IDENTIFIER`,`PRIMITIVE_ATTRIBUTE_ID`) values (125,249);
insert into `dyextn_attribute_type_info` (`IDENTIFIER`,`PRIMITIVE_ATTRIBUTE_ID`) values (126,250);
insert into `dyextn_attribute_type_info` (`IDENTIFIER`,`PRIMITIVE_ATTRIBUTE_ID`) values (127,251);
insert into `dyextn_attribute_type_info` (`IDENTIFIER`,`PRIMITIVE_ATTRIBUTE_ID`) values (128,252);
insert into `dyextn_attribute_type_info` (`IDENTIFIER`,`PRIMITIVE_ATTRIBUTE_ID`) values (129,253);
insert into `dyextn_attribute_type_info` (`IDENTIFIER`,`PRIMITIVE_ATTRIBUTE_ID`) values (130,254);
insert into `dyextn_attribute_type_info` (`IDENTIFIER`,`PRIMITIVE_ATTRIBUTE_ID`) values (131,264);
insert into `dyextn_attribute_type_info` (`IDENTIFIER`,`PRIMITIVE_ATTRIBUTE_ID`) values (132,267);
insert into `dyextn_attribute_type_info` (`IDENTIFIER`,`PRIMITIVE_ATTRIBUTE_ID`) values (133,272);
insert into `dyextn_attribute_type_info` (`IDENTIFIER`,`PRIMITIVE_ATTRIBUTE_ID`) values (134,276);
insert into `dyextn_attribute_type_info` (`IDENTIFIER`,`PRIMITIVE_ATTRIBUTE_ID`) values (135,277);
insert into `dyextn_attribute_type_info` (`IDENTIFIER`,`PRIMITIVE_ATTRIBUTE_ID`) values (136,278);
insert into `dyextn_attribute_type_info` (`IDENTIFIER`,`PRIMITIVE_ATTRIBUTE_ID`) values (137,281);
insert into `dyextn_attribute_type_info` (`IDENTIFIER`,`PRIMITIVE_ATTRIBUTE_ID`) values (138,284);
insert into `dyextn_attribute_type_info` (`IDENTIFIER`,`PRIMITIVE_ATTRIBUTE_ID`) values (139,286);
insert into `dyextn_attribute_type_info` (`IDENTIFIER`,`PRIMITIVE_ATTRIBUTE_ID`) values (140,288);
insert into `dyextn_attribute_type_info` (`IDENTIFIER`,`PRIMITIVE_ATTRIBUTE_ID`) values (141,294);
insert into `dyextn_attribute_type_info` (`IDENTIFIER`,`PRIMITIVE_ATTRIBUTE_ID`) values (142,295);

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

insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (3);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (4);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (5);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (6);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (9);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (10);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (11);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (12);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (14);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (15);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (16);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (17);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (18);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (19);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (20);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (21);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (22);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (26);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (27);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (29);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (30);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (32);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (33);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (35);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (36);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (38);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (39);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (41);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (42);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (43);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (44);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (45);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (46);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (47);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (48);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (49);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (50);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (51);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (54);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (55);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (56);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (57);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (58);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (59);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (61);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (62);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (63);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (64);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (67);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (68);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (70);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (71);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (73);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (74);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (75);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (76);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (77);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (78);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (79);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (80);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (81);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (82);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (83);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (84);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (85);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (86);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (88);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (90);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (92);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (94);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (95);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (96);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (97);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (99);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (101);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (103);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (105);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (107);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (109);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (110);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (111);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (112);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (114);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (115);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (117);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (118);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (119);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (120);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (121);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (122);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (123);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (125);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (126);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (127);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (128);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (129);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (130);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (131);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (133);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (136);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (137);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (138);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (139);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (140);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (141);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (142);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (143);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (144);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (145);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (147);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (148);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (150);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (151);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (152);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (153);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (154);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (155);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (156);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (157);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (159);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (160);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (162);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (163);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (164);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (165);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (166);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (167);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (168);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (169);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (170);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (171);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (173);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (174);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (175);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (177);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (179);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (181);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (183);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (184);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (185);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (186);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (187);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (189);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (190);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (192);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (193);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (194);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (195);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (196);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (197);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (198);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (199);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (200);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (201);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (202);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (203);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (204);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (205);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (207);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (208);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (209);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (210);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (211);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (212);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (213);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (214);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (215);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (217);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (218);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (219);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (220);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (222);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (223);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (225);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (226);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (227);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (228);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (229);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (230);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (231);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (232);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (234);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (235);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (237);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (238);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (240);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (241);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (242);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (243);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (244);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (245);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (246);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (247);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (248);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (249);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (250);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (251);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (252);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (253);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (254);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (255);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (256);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (257);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (258);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (259);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (260);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (261);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (262);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (264);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (265);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (267);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (268);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (270);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (272);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (273);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (274);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (275);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (276);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (277);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (278);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (279);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (281);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (282);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (284);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (286);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (288);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (290);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (291);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (292);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (293);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (294);
insert into `dyextn_base_abstract_attribute` (`IDENTIFIER`) values (295);

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

insert into `dyextn_column_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`CATEGORY_ATTRIBUTE_ID`,`PRIMITIVE_ATTRIBUTE_ID`) values (2,NULL,NULL,3);
insert into `dyextn_column_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`CATEGORY_ATTRIBUTE_ID`,`PRIMITIVE_ATTRIBUTE_ID`) values (3,NULL,NULL,4);
insert into `dyextn_column_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`CATEGORY_ATTRIBUTE_ID`,`PRIMITIVE_ATTRIBUTE_ID`) values (4,NULL,NULL,5);
insert into `dyextn_column_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`CATEGORY_ATTRIBUTE_ID`,`PRIMITIVE_ATTRIBUTE_ID`) values (5,NULL,NULL,6);
insert into `dyextn_column_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`CATEGORY_ATTRIBUTE_ID`,`PRIMITIVE_ATTRIBUTE_ID`) values (8,NULL,NULL,9);
insert into `dyextn_column_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`CATEGORY_ATTRIBUTE_ID`,`PRIMITIVE_ATTRIBUTE_ID`) values (9,NULL,NULL,10);
insert into `dyextn_column_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`CATEGORY_ATTRIBUTE_ID`,`PRIMITIVE_ATTRIBUTE_ID`) values (10,NULL,NULL,11);
insert into `dyextn_column_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`CATEGORY_ATTRIBUTE_ID`,`PRIMITIVE_ATTRIBUTE_ID`) values (13,NULL,NULL,14);
insert into `dyextn_column_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`CATEGORY_ATTRIBUTE_ID`,`PRIMITIVE_ATTRIBUTE_ID`) values (14,NULL,NULL,15);
insert into `dyextn_column_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`CATEGORY_ATTRIBUTE_ID`,`PRIMITIVE_ATTRIBUTE_ID`) values (15,NULL,NULL,16);
insert into `dyextn_column_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`CATEGORY_ATTRIBUTE_ID`,`PRIMITIVE_ATTRIBUTE_ID`) values (16,NULL,NULL,17);
insert into `dyextn_column_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`CATEGORY_ATTRIBUTE_ID`,`PRIMITIVE_ATTRIBUTE_ID`) values (17,NULL,NULL,18);
insert into `dyextn_column_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`CATEGORY_ATTRIBUTE_ID`,`PRIMITIVE_ATTRIBUTE_ID`) values (18,NULL,NULL,19);
insert into `dyextn_column_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`CATEGORY_ATTRIBUTE_ID`,`PRIMITIVE_ATTRIBUTE_ID`) values (19,NULL,NULL,20);
insert into `dyextn_column_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`CATEGORY_ATTRIBUTE_ID`,`PRIMITIVE_ATTRIBUTE_ID`) values (20,NULL,NULL,21);
insert into `dyextn_column_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`CATEGORY_ATTRIBUTE_ID`,`PRIMITIVE_ATTRIBUTE_ID`) values (25,NULL,NULL,26);
insert into `dyextn_column_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`CATEGORY_ATTRIBUTE_ID`,`PRIMITIVE_ATTRIBUTE_ID`) values (28,NULL,NULL,29);
insert into `dyextn_column_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`CATEGORY_ATTRIBUTE_ID`,`PRIMITIVE_ATTRIBUTE_ID`) values (31,NULL,NULL,32);
insert into `dyextn_column_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`CATEGORY_ATTRIBUTE_ID`,`PRIMITIVE_ATTRIBUTE_ID`) values (34,NULL,NULL,35);
insert into `dyextn_column_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`CATEGORY_ATTRIBUTE_ID`,`PRIMITIVE_ATTRIBUTE_ID`) values (37,NULL,NULL,38);
insert into `dyextn_column_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`CATEGORY_ATTRIBUTE_ID`,`PRIMITIVE_ATTRIBUTE_ID`) values (40,NULL,NULL,41);
insert into `dyextn_column_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`CATEGORY_ATTRIBUTE_ID`,`PRIMITIVE_ATTRIBUTE_ID`) values (42,NULL,NULL,43);
insert into `dyextn_column_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`CATEGORY_ATTRIBUTE_ID`,`PRIMITIVE_ATTRIBUTE_ID`) values (43,NULL,NULL,44);
insert into `dyextn_column_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`CATEGORY_ATTRIBUTE_ID`,`PRIMITIVE_ATTRIBUTE_ID`) values (44,NULL,NULL,45);
insert into `dyextn_column_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`CATEGORY_ATTRIBUTE_ID`,`PRIMITIVE_ATTRIBUTE_ID`) values (45,NULL,NULL,46);
insert into `dyextn_column_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`CATEGORY_ATTRIBUTE_ID`,`PRIMITIVE_ATTRIBUTE_ID`) values (46,NULL,NULL,47);
insert into `dyextn_column_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`CATEGORY_ATTRIBUTE_ID`,`PRIMITIVE_ATTRIBUTE_ID`) values (47,NULL,NULL,48);
insert into `dyextn_column_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`CATEGORY_ATTRIBUTE_ID`,`PRIMITIVE_ATTRIBUTE_ID`) values (48,NULL,NULL,49);
insert into `dyextn_column_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`CATEGORY_ATTRIBUTE_ID`,`PRIMITIVE_ATTRIBUTE_ID`) values (49,NULL,NULL,50);
insert into `dyextn_column_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`CATEGORY_ATTRIBUTE_ID`,`PRIMITIVE_ATTRIBUTE_ID`) values (53,NULL,NULL,54);
insert into `dyextn_column_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`CATEGORY_ATTRIBUTE_ID`,`PRIMITIVE_ATTRIBUTE_ID`) values (56,NULL,NULL,57);
insert into `dyextn_column_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`CATEGORY_ATTRIBUTE_ID`,`PRIMITIVE_ATTRIBUTE_ID`) values (60,NULL,NULL,61);
insert into `dyextn_column_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`CATEGORY_ATTRIBUTE_ID`,`PRIMITIVE_ATTRIBUTE_ID`) values (61,NULL,NULL,62);
insert into `dyextn_column_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`CATEGORY_ATTRIBUTE_ID`,`PRIMITIVE_ATTRIBUTE_ID`) values (62,NULL,NULL,63);
insert into `dyextn_column_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`CATEGORY_ATTRIBUTE_ID`,`PRIMITIVE_ATTRIBUTE_ID`) values (66,NULL,NULL,67);
insert into `dyextn_column_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`CATEGORY_ATTRIBUTE_ID`,`PRIMITIVE_ATTRIBUTE_ID`) values (69,NULL,NULL,70);
insert into `dyextn_column_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`CATEGORY_ATTRIBUTE_ID`,`PRIMITIVE_ATTRIBUTE_ID`) values (72,NULL,NULL,73);
insert into `dyextn_column_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`CATEGORY_ATTRIBUTE_ID`,`PRIMITIVE_ATTRIBUTE_ID`) values (74,NULL,NULL,75);
insert into `dyextn_column_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`CATEGORY_ATTRIBUTE_ID`,`PRIMITIVE_ATTRIBUTE_ID`) values (75,NULL,NULL,76);
insert into `dyextn_column_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`CATEGORY_ATTRIBUTE_ID`,`PRIMITIVE_ATTRIBUTE_ID`) values (76,NULL,NULL,77);
insert into `dyextn_column_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`CATEGORY_ATTRIBUTE_ID`,`PRIMITIVE_ATTRIBUTE_ID`) values (77,NULL,NULL,78);
insert into `dyextn_column_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`CATEGORY_ATTRIBUTE_ID`,`PRIMITIVE_ATTRIBUTE_ID`) values (78,NULL,NULL,79);
insert into `dyextn_column_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`CATEGORY_ATTRIBUTE_ID`,`PRIMITIVE_ATTRIBUTE_ID`) values (81,NULL,NULL,82);
insert into `dyextn_column_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`CATEGORY_ATTRIBUTE_ID`,`PRIMITIVE_ATTRIBUTE_ID`) values (82,NULL,NULL,83);
insert into `dyextn_column_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`CATEGORY_ATTRIBUTE_ID`,`PRIMITIVE_ATTRIBUTE_ID`) values (84,NULL,NULL,85);
insert into `dyextn_column_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`CATEGORY_ATTRIBUTE_ID`,`PRIMITIVE_ATTRIBUTE_ID`) values (85,NULL,NULL,86);
insert into `dyextn_column_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`CATEGORY_ATTRIBUTE_ID`,`PRIMITIVE_ATTRIBUTE_ID`) values (87,NULL,NULL,88);
insert into `dyextn_column_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`CATEGORY_ATTRIBUTE_ID`,`PRIMITIVE_ATTRIBUTE_ID`) values (89,NULL,NULL,90);
insert into `dyextn_column_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`CATEGORY_ATTRIBUTE_ID`,`PRIMITIVE_ATTRIBUTE_ID`) values (91,NULL,NULL,92);
insert into `dyextn_column_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`CATEGORY_ATTRIBUTE_ID`,`PRIMITIVE_ATTRIBUTE_ID`) values (93,NULL,NULL,94);
insert into `dyextn_column_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`CATEGORY_ATTRIBUTE_ID`,`PRIMITIVE_ATTRIBUTE_ID`) values (94,NULL,NULL,95);
insert into `dyextn_column_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`CATEGORY_ATTRIBUTE_ID`,`PRIMITIVE_ATTRIBUTE_ID`) values (95,NULL,NULL,96);
insert into `dyextn_column_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`CATEGORY_ATTRIBUTE_ID`,`PRIMITIVE_ATTRIBUTE_ID`) values (96,NULL,NULL,97);
insert into `dyextn_column_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`CATEGORY_ATTRIBUTE_ID`,`PRIMITIVE_ATTRIBUTE_ID`) values (98,NULL,NULL,99);
insert into `dyextn_column_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`CATEGORY_ATTRIBUTE_ID`,`PRIMITIVE_ATTRIBUTE_ID`) values (100,NULL,NULL,101);
insert into `dyextn_column_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`CATEGORY_ATTRIBUTE_ID`,`PRIMITIVE_ATTRIBUTE_ID`) values (102,NULL,NULL,103);
insert into `dyextn_column_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`CATEGORY_ATTRIBUTE_ID`,`PRIMITIVE_ATTRIBUTE_ID`) values (104,NULL,NULL,105);
insert into `dyextn_column_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`CATEGORY_ATTRIBUTE_ID`,`PRIMITIVE_ATTRIBUTE_ID`) values (108,NULL,NULL,109);
insert into `dyextn_column_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`CATEGORY_ATTRIBUTE_ID`,`PRIMITIVE_ATTRIBUTE_ID`) values (109,NULL,NULL,110);
insert into `dyextn_column_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`CATEGORY_ATTRIBUTE_ID`,`PRIMITIVE_ATTRIBUTE_ID`) values (113,NULL,NULL,114);
insert into `dyextn_column_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`CATEGORY_ATTRIBUTE_ID`,`PRIMITIVE_ATTRIBUTE_ID`) values (117,NULL,NULL,118);
insert into `dyextn_column_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`CATEGORY_ATTRIBUTE_ID`,`PRIMITIVE_ATTRIBUTE_ID`) values (118,NULL,NULL,119);
insert into `dyextn_column_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`CATEGORY_ATTRIBUTE_ID`,`PRIMITIVE_ATTRIBUTE_ID`) values (119,NULL,NULL,120);
insert into `dyextn_column_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`CATEGORY_ATTRIBUTE_ID`,`PRIMITIVE_ATTRIBUTE_ID`) values (120,NULL,NULL,121);
insert into `dyextn_column_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`CATEGORY_ATTRIBUTE_ID`,`PRIMITIVE_ATTRIBUTE_ID`) values (121,NULL,NULL,122);
insert into `dyextn_column_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`CATEGORY_ATTRIBUTE_ID`,`PRIMITIVE_ATTRIBUTE_ID`) values (122,NULL,NULL,123);
insert into `dyextn_column_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`CATEGORY_ATTRIBUTE_ID`,`PRIMITIVE_ATTRIBUTE_ID`) values (125,NULL,NULL,126);
insert into `dyextn_column_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`CATEGORY_ATTRIBUTE_ID`,`PRIMITIVE_ATTRIBUTE_ID`) values (126,NULL,NULL,127);
insert into `dyextn_column_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`CATEGORY_ATTRIBUTE_ID`,`PRIMITIVE_ATTRIBUTE_ID`) values (127,NULL,NULL,128);
insert into `dyextn_column_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`CATEGORY_ATTRIBUTE_ID`,`PRIMITIVE_ATTRIBUTE_ID`) values (136,NULL,NULL,137);
insert into `dyextn_column_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`CATEGORY_ATTRIBUTE_ID`,`PRIMITIVE_ATTRIBUTE_ID`) values (137,NULL,NULL,138);
insert into `dyextn_column_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`CATEGORY_ATTRIBUTE_ID`,`PRIMITIVE_ATTRIBUTE_ID`) values (138,NULL,NULL,139);
insert into `dyextn_column_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`CATEGORY_ATTRIBUTE_ID`,`PRIMITIVE_ATTRIBUTE_ID`) values (139,NULL,NULL,140);
insert into `dyextn_column_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`CATEGORY_ATTRIBUTE_ID`,`PRIMITIVE_ATTRIBUTE_ID`) values (140,NULL,NULL,141);
insert into `dyextn_column_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`CATEGORY_ATTRIBUTE_ID`,`PRIMITIVE_ATTRIBUTE_ID`) values (143,NULL,NULL,144);
insert into `dyextn_column_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`CATEGORY_ATTRIBUTE_ID`,`PRIMITIVE_ATTRIBUTE_ID`) values (146,NULL,NULL,147);
insert into `dyextn_column_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`CATEGORY_ATTRIBUTE_ID`,`PRIMITIVE_ATTRIBUTE_ID`) values (149,NULL,NULL,150);
insert into `dyextn_column_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`CATEGORY_ATTRIBUTE_ID`,`PRIMITIVE_ATTRIBUTE_ID`) values (150,NULL,NULL,151);
insert into `dyextn_column_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`CATEGORY_ATTRIBUTE_ID`,`PRIMITIVE_ATTRIBUTE_ID`) values (152,NULL,NULL,153);
insert into `dyextn_column_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`CATEGORY_ATTRIBUTE_ID`,`PRIMITIVE_ATTRIBUTE_ID`) values (153,NULL,NULL,154);
insert into `dyextn_column_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`CATEGORY_ATTRIBUTE_ID`,`PRIMITIVE_ATTRIBUTE_ID`) values (154,NULL,NULL,155);
insert into `dyextn_column_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`CATEGORY_ATTRIBUTE_ID`,`PRIMITIVE_ATTRIBUTE_ID`) values (155,NULL,NULL,156);
insert into `dyextn_column_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`CATEGORY_ATTRIBUTE_ID`,`PRIMITIVE_ATTRIBUTE_ID`) values (158,NULL,NULL,159);
insert into `dyextn_column_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`CATEGORY_ATTRIBUTE_ID`,`PRIMITIVE_ATTRIBUTE_ID`) values (162,NULL,NULL,163);
insert into `dyextn_column_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`CATEGORY_ATTRIBUTE_ID`,`PRIMITIVE_ATTRIBUTE_ID`) values (163,NULL,NULL,164);
insert into `dyextn_column_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`CATEGORY_ATTRIBUTE_ID`,`PRIMITIVE_ATTRIBUTE_ID`) values (167,NULL,NULL,168);
insert into `dyextn_column_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`CATEGORY_ATTRIBUTE_ID`,`PRIMITIVE_ATTRIBUTE_ID`) values (168,NULL,NULL,169);
insert into `dyextn_column_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`CATEGORY_ATTRIBUTE_ID`,`PRIMITIVE_ATTRIBUTE_ID`) values (169,NULL,NULL,170);
insert into `dyextn_column_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`CATEGORY_ATTRIBUTE_ID`,`PRIMITIVE_ATTRIBUTE_ID`) values (170,NULL,NULL,171);
insert into `dyextn_column_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`CATEGORY_ATTRIBUTE_ID`,`PRIMITIVE_ATTRIBUTE_ID`) values (172,NULL,NULL,173);
insert into `dyextn_column_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`CATEGORY_ATTRIBUTE_ID`,`PRIMITIVE_ATTRIBUTE_ID`) values (173,NULL,NULL,174);
insert into `dyextn_column_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`CATEGORY_ATTRIBUTE_ID`,`PRIMITIVE_ATTRIBUTE_ID`) values (174,NULL,NULL,175);
insert into `dyextn_column_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`CATEGORY_ATTRIBUTE_ID`,`PRIMITIVE_ATTRIBUTE_ID`) values (176,NULL,NULL,177);
insert into `dyextn_column_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`CATEGORY_ATTRIBUTE_ID`,`PRIMITIVE_ATTRIBUTE_ID`) values (178,NULL,NULL,179);
insert into `dyextn_column_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`CATEGORY_ATTRIBUTE_ID`,`PRIMITIVE_ATTRIBUTE_ID`) values (180,NULL,NULL,181);
insert into `dyextn_column_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`CATEGORY_ATTRIBUTE_ID`,`PRIMITIVE_ATTRIBUTE_ID`) values (188,NULL,NULL,189);
insert into `dyextn_column_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`CATEGORY_ATTRIBUTE_ID`,`PRIMITIVE_ATTRIBUTE_ID`) values (193,NULL,NULL,194);
insert into `dyextn_column_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`CATEGORY_ATTRIBUTE_ID`,`PRIMITIVE_ATTRIBUTE_ID`) values (194,NULL,NULL,195);
insert into `dyextn_column_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`CATEGORY_ATTRIBUTE_ID`,`PRIMITIVE_ATTRIBUTE_ID`) values (195,NULL,NULL,196);
insert into `dyextn_column_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`CATEGORY_ATTRIBUTE_ID`,`PRIMITIVE_ATTRIBUTE_ID`) values (196,NULL,NULL,197);
insert into `dyextn_column_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`CATEGORY_ATTRIBUTE_ID`,`PRIMITIVE_ATTRIBUTE_ID`) values (197,NULL,NULL,198);
insert into `dyextn_column_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`CATEGORY_ATTRIBUTE_ID`,`PRIMITIVE_ATTRIBUTE_ID`) values (198,NULL,NULL,199);
insert into `dyextn_column_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`CATEGORY_ATTRIBUTE_ID`,`PRIMITIVE_ATTRIBUTE_ID`) values (199,NULL,NULL,200);
insert into `dyextn_column_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`CATEGORY_ATTRIBUTE_ID`,`PRIMITIVE_ATTRIBUTE_ID`) values (200,NULL,NULL,201);
insert into `dyextn_column_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`CATEGORY_ATTRIBUTE_ID`,`PRIMITIVE_ATTRIBUTE_ID`) values (201,NULL,NULL,202);
insert into `dyextn_column_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`CATEGORY_ATTRIBUTE_ID`,`PRIMITIVE_ATTRIBUTE_ID`) values (202,NULL,NULL,203);
insert into `dyextn_column_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`CATEGORY_ATTRIBUTE_ID`,`PRIMITIVE_ATTRIBUTE_ID`) values (207,NULL,NULL,208);
insert into `dyextn_column_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`CATEGORY_ATTRIBUTE_ID`,`PRIMITIVE_ATTRIBUTE_ID`) values (208,NULL,NULL,209);
insert into `dyextn_column_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`CATEGORY_ATTRIBUTE_ID`,`PRIMITIVE_ATTRIBUTE_ID`) values (209,NULL,NULL,210);
insert into `dyextn_column_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`CATEGORY_ATTRIBUTE_ID`,`PRIMITIVE_ATTRIBUTE_ID`) values (210,NULL,NULL,211);
insert into `dyextn_column_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`CATEGORY_ATTRIBUTE_ID`,`PRIMITIVE_ATTRIBUTE_ID`) values (211,NULL,NULL,212);
insert into `dyextn_column_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`CATEGORY_ATTRIBUTE_ID`,`PRIMITIVE_ATTRIBUTE_ID`) values (212,NULL,NULL,213);
insert into `dyextn_column_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`CATEGORY_ATTRIBUTE_ID`,`PRIMITIVE_ATTRIBUTE_ID`) values (213,NULL,NULL,214);
insert into `dyextn_column_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`CATEGORY_ATTRIBUTE_ID`,`PRIMITIVE_ATTRIBUTE_ID`) values (216,NULL,NULL,217);
insert into `dyextn_column_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`CATEGORY_ATTRIBUTE_ID`,`PRIMITIVE_ATTRIBUTE_ID`) values (224,NULL,NULL,225);
insert into `dyextn_column_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`CATEGORY_ATTRIBUTE_ID`,`PRIMITIVE_ATTRIBUTE_ID`) values (228,NULL,NULL,229);
insert into `dyextn_column_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`CATEGORY_ATTRIBUTE_ID`,`PRIMITIVE_ATTRIBUTE_ID`) values (229,NULL,NULL,230);
insert into `dyextn_column_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`CATEGORY_ATTRIBUTE_ID`,`PRIMITIVE_ATTRIBUTE_ID`) values (230,NULL,NULL,231);
insert into `dyextn_column_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`CATEGORY_ATTRIBUTE_ID`,`PRIMITIVE_ATTRIBUTE_ID`) values (233,NULL,NULL,234);
insert into `dyextn_column_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`CATEGORY_ATTRIBUTE_ID`,`PRIMITIVE_ATTRIBUTE_ID`) values (236,NULL,NULL,237);
insert into `dyextn_column_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`CATEGORY_ATTRIBUTE_ID`,`PRIMITIVE_ATTRIBUTE_ID`) values (239,NULL,NULL,240);
insert into `dyextn_column_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`CATEGORY_ATTRIBUTE_ID`,`PRIMITIVE_ATTRIBUTE_ID`) values (245,NULL,NULL,246);
insert into `dyextn_column_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`CATEGORY_ATTRIBUTE_ID`,`PRIMITIVE_ATTRIBUTE_ID`) values (246,NULL,NULL,247);
insert into `dyextn_column_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`CATEGORY_ATTRIBUTE_ID`,`PRIMITIVE_ATTRIBUTE_ID`) values (247,NULL,NULL,248);
insert into `dyextn_column_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`CATEGORY_ATTRIBUTE_ID`,`PRIMITIVE_ATTRIBUTE_ID`) values (248,NULL,NULL,249);
insert into `dyextn_column_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`CATEGORY_ATTRIBUTE_ID`,`PRIMITIVE_ATTRIBUTE_ID`) values (249,NULL,NULL,250);
insert into `dyextn_column_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`CATEGORY_ATTRIBUTE_ID`,`PRIMITIVE_ATTRIBUTE_ID`) values (250,NULL,NULL,251);
insert into `dyextn_column_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`CATEGORY_ATTRIBUTE_ID`,`PRIMITIVE_ATTRIBUTE_ID`) values (251,NULL,NULL,252);
insert into `dyextn_column_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`CATEGORY_ATTRIBUTE_ID`,`PRIMITIVE_ATTRIBUTE_ID`) values (252,NULL,NULL,253);
insert into `dyextn_column_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`CATEGORY_ATTRIBUTE_ID`,`PRIMITIVE_ATTRIBUTE_ID`) values (253,NULL,NULL,254);
insert into `dyextn_column_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`CATEGORY_ATTRIBUTE_ID`,`PRIMITIVE_ATTRIBUTE_ID`) values (263,NULL,NULL,264);
insert into `dyextn_column_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`CATEGORY_ATTRIBUTE_ID`,`PRIMITIVE_ATTRIBUTE_ID`) values (266,NULL,NULL,267);
insert into `dyextn_column_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`CATEGORY_ATTRIBUTE_ID`,`PRIMITIVE_ATTRIBUTE_ID`) values (271,NULL,NULL,272);
insert into `dyextn_column_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`CATEGORY_ATTRIBUTE_ID`,`PRIMITIVE_ATTRIBUTE_ID`) values (275,NULL,NULL,276);
insert into `dyextn_column_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`CATEGORY_ATTRIBUTE_ID`,`PRIMITIVE_ATTRIBUTE_ID`) values (276,NULL,NULL,277);
insert into `dyextn_column_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`CATEGORY_ATTRIBUTE_ID`,`PRIMITIVE_ATTRIBUTE_ID`) values (277,NULL,NULL,278);
insert into `dyextn_column_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`CATEGORY_ATTRIBUTE_ID`,`PRIMITIVE_ATTRIBUTE_ID`) values (280,NULL,NULL,281);
insert into `dyextn_column_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`CATEGORY_ATTRIBUTE_ID`,`PRIMITIVE_ATTRIBUTE_ID`) values (283,NULL,NULL,284);
insert into `dyextn_column_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`CATEGORY_ATTRIBUTE_ID`,`PRIMITIVE_ATTRIBUTE_ID`) values (285,NULL,NULL,286);
insert into `dyextn_column_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`CATEGORY_ATTRIBUTE_ID`,`PRIMITIVE_ATTRIBUTE_ID`) values (287,NULL,NULL,288);
insert into `dyextn_column_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`CATEGORY_ATTRIBUTE_ID`,`PRIMITIVE_ATTRIBUTE_ID`) values (293,NULL,NULL,294);
insert into `dyextn_column_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`CATEGORY_ATTRIBUTE_ID`,`PRIMITIVE_ATTRIBUTE_ID`) values (294,NULL,NULL,295);

/*Table structure for table `dyextn_combobox` */

CREATE TABLE `dyextn_combobox` (
  `IDENTIFIER` bigint(20) NOT NULL,
  PRIMARY KEY  (`IDENTIFIER`),
  KEY `FKABBC649ABF67AB26` (`IDENTIFIER`),
  CONSTRAINT `FKABBC649ABF67AB26` FOREIGN KEY (`IDENTIFIER`) REFERENCES `dyextn_select_control` (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `dyextn_combobox` */

insert into `dyextn_combobox` (`IDENTIFIER`) values (5);
insert into `dyextn_combobox` (`IDENTIFIER`) values (6);
insert into `dyextn_combobox` (`IDENTIFIER`) values (11);
insert into `dyextn_combobox` (`IDENTIFIER`) values (17);
insert into `dyextn_combobox` (`IDENTIFIER`) values (22);
insert into `dyextn_combobox` (`IDENTIFIER`) values (23);
insert into `dyextn_combobox` (`IDENTIFIER`) values (24);
insert into `dyextn_combobox` (`IDENTIFIER`) values (25);
insert into `dyextn_combobox` (`IDENTIFIER`) values (32);
insert into `dyextn_combobox` (`IDENTIFIER`) values (33);
insert into `dyextn_combobox` (`IDENTIFIER`) values (34);
insert into `dyextn_combobox` (`IDENTIFIER`) values (35);
insert into `dyextn_combobox` (`IDENTIFIER`) values (36);
insert into `dyextn_combobox` (`IDENTIFIER`) values (37);
insert into `dyextn_combobox` (`IDENTIFIER`) values (38);
insert into `dyextn_combobox` (`IDENTIFIER`) values (39);
insert into `dyextn_combobox` (`IDENTIFIER`) values (40);
insert into `dyextn_combobox` (`IDENTIFIER`) values (41);
insert into `dyextn_combobox` (`IDENTIFIER`) values (42);
insert into `dyextn_combobox` (`IDENTIFIER`) values (43);
insert into `dyextn_combobox` (`IDENTIFIER`) values (44);
insert into `dyextn_combobox` (`IDENTIFIER`) values (45);
insert into `dyextn_combobox` (`IDENTIFIER`) values (54);
insert into `dyextn_combobox` (`IDENTIFIER`) values (65);
insert into `dyextn_combobox` (`IDENTIFIER`) values (66);
insert into `dyextn_combobox` (`IDENTIFIER`) values (67);
insert into `dyextn_combobox` (`IDENTIFIER`) values (68);
insert into `dyextn_combobox` (`IDENTIFIER`) values (69);
insert into `dyextn_combobox` (`IDENTIFIER`) values (70);
insert into `dyextn_combobox` (`IDENTIFIER`) values (71);
insert into `dyextn_combobox` (`IDENTIFIER`) values (72);
insert into `dyextn_combobox` (`IDENTIFIER`) values (73);
insert into `dyextn_combobox` (`IDENTIFIER`) values (74);
insert into `dyextn_combobox` (`IDENTIFIER`) values (75);
insert into `dyextn_combobox` (`IDENTIFIER`) values (76);
insert into `dyextn_combobox` (`IDENTIFIER`) values (77);
insert into `dyextn_combobox` (`IDENTIFIER`) values (78);
insert into `dyextn_combobox` (`IDENTIFIER`) values (79);
insert into `dyextn_combobox` (`IDENTIFIER`) values (80);
insert into `dyextn_combobox` (`IDENTIFIER`) values (84);
insert into `dyextn_combobox` (`IDENTIFIER`) values (85);
insert into `dyextn_combobox` (`IDENTIFIER`) values (86);
insert into `dyextn_combobox` (`IDENTIFIER`) values (87);
insert into `dyextn_combobox` (`IDENTIFIER`) values (88);
insert into `dyextn_combobox` (`IDENTIFIER`) values (89);
insert into `dyextn_combobox` (`IDENTIFIER`) values (90);
insert into `dyextn_combobox` (`IDENTIFIER`) values (91);
insert into `dyextn_combobox` (`IDENTIFIER`) values (92);
insert into `dyextn_combobox` (`IDENTIFIER`) values (93);
insert into `dyextn_combobox` (`IDENTIFIER`) values (94);
insert into `dyextn_combobox` (`IDENTIFIER`) values (95);
insert into `dyextn_combobox` (`IDENTIFIER`) values (96);
insert into `dyextn_combobox` (`IDENTIFIER`) values (97);
insert into `dyextn_combobox` (`IDENTIFIER`) values (98);
insert into `dyextn_combobox` (`IDENTIFIER`) values (99);
insert into `dyextn_combobox` (`IDENTIFIER`) values (100);
insert into `dyextn_combobox` (`IDENTIFIER`) values (101);
insert into `dyextn_combobox` (`IDENTIFIER`) values (102);
insert into `dyextn_combobox` (`IDENTIFIER`) values (103);
insert into `dyextn_combobox` (`IDENTIFIER`) values (118);
insert into `dyextn_combobox` (`IDENTIFIER`) values (119);
insert into `dyextn_combobox` (`IDENTIFIER`) values (125);
insert into `dyextn_combobox` (`IDENTIFIER`) values (126);
insert into `dyextn_combobox` (`IDENTIFIER`) values (127);
insert into `dyextn_combobox` (`IDENTIFIER`) values (128);
insert into `dyextn_combobox` (`IDENTIFIER`) values (129);
insert into `dyextn_combobox` (`IDENTIFIER`) values (131);
insert into `dyextn_combobox` (`IDENTIFIER`) values (132);
insert into `dyextn_combobox` (`IDENTIFIER`) values (133);
insert into `dyextn_combobox` (`IDENTIFIER`) values (137);
insert into `dyextn_combobox` (`IDENTIFIER`) values (138);
insert into `dyextn_combobox` (`IDENTIFIER`) values (139);
insert into `dyextn_combobox` (`IDENTIFIER`) values (140);
insert into `dyextn_combobox` (`IDENTIFIER`) values (141);
insert into `dyextn_combobox` (`IDENTIFIER`) values (142);
insert into `dyextn_combobox` (`IDENTIFIER`) values (148);
insert into `dyextn_combobox` (`IDENTIFIER`) values (149);
insert into `dyextn_combobox` (`IDENTIFIER`) values (150);
insert into `dyextn_combobox` (`IDENTIFIER`) values (151);
insert into `dyextn_combobox` (`IDENTIFIER`) values (152);
insert into `dyextn_combobox` (`IDENTIFIER`) values (153);
insert into `dyextn_combobox` (`IDENTIFIER`) values (154);
insert into `dyextn_combobox` (`IDENTIFIER`) values (158);
insert into `dyextn_combobox` (`IDENTIFIER`) values (159);
insert into `dyextn_combobox` (`IDENTIFIER`) values (160);
insert into `dyextn_combobox` (`IDENTIFIER`) values (161);

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

insert into `dyextn_constraint_properties` (`IDENTIFIER`,`SOURCE_ENTITY_KEY`,`TARGET_ENTITY_KEY`,`SRC_CONSTRAINT_NAME`,`TARGET_CONSTRAINT_NAME`,`CATEGORY_ASSOCIATION_ID`,`ASSOCIATION_ID`) values (11,'DE_E_S_770',NULL,'CONSRT_772','CONSRT_773',NULL,12);
insert into `dyextn_constraint_properties` (`IDENTIFIER`,`SOURCE_ENTITY_KEY`,`TARGET_ENTITY_KEY`,`SRC_CONSTRAINT_NAME`,`TARGET_CONSTRAINT_NAME`,`CATEGORY_ASSOCIATION_ID`,`ASSOCIATION_ID`) values (21,'DE_E_S_765',NULL,'CONSRT_767','CONSRT_768',NULL,22);
insert into `dyextn_constraint_properties` (`IDENTIFIER`,`SOURCE_ENTITY_KEY`,`TARGET_ENTITY_KEY`,`SRC_CONSTRAINT_NAME`,`TARGET_CONSTRAINT_NAME`,`CATEGORY_ASSOCIATION_ID`,`ASSOCIATION_ID`) values (26,'DE_E_S_720','DE_E_T_721','CONSRT_722','CONSRT_723',NULL,27);
insert into `dyextn_constraint_properties` (`IDENTIFIER`,`SOURCE_ENTITY_KEY`,`TARGET_ENTITY_KEY`,`SRC_CONSTRAINT_NAME`,`TARGET_CONSTRAINT_NAME`,`CATEGORY_ASSOCIATION_ID`,`ASSOCIATION_ID`) values (29,'DE_E_S_620',NULL,'CONSRT_622','CONSRT_623',NULL,30);
insert into `dyextn_constraint_properties` (`IDENTIFIER`,`SOURCE_ENTITY_KEY`,`TARGET_ENTITY_KEY`,`SRC_CONSTRAINT_NAME`,`TARGET_CONSTRAINT_NAME`,`CATEGORY_ASSOCIATION_ID`,`ASSOCIATION_ID`) values (32,'DE_E_S_605',NULL,'CONSRT_607','CONSRT_608',NULL,33);
insert into `dyextn_constraint_properties` (`IDENTIFIER`,`SOURCE_ENTITY_KEY`,`TARGET_ENTITY_KEY`,`SRC_CONSTRAINT_NAME`,`TARGET_CONSTRAINT_NAME`,`CATEGORY_ASSOCIATION_ID`,`ASSOCIATION_ID`) values (35,'DE_E_S_595',NULL,'CONSRT_597','CONSRT_598',NULL,36);
insert into `dyextn_constraint_properties` (`IDENTIFIER`,`SOURCE_ENTITY_KEY`,`TARGET_ENTITY_KEY`,`SRC_CONSTRAINT_NAME`,`TARGET_CONSTRAINT_NAME`,`CATEGORY_ASSOCIATION_ID`,`ASSOCIATION_ID`) values (38,'DE_E_S_565','DE_E_T_566','CONSRT_567','CONSRT_568',NULL,39);
insert into `dyextn_constraint_properties` (`IDENTIFIER`,`SOURCE_ENTITY_KEY`,`TARGET_ENTITY_KEY`,`SRC_CONSTRAINT_NAME`,`TARGET_CONSTRAINT_NAME`,`CATEGORY_ASSOCIATION_ID`,`ASSOCIATION_ID`) values (41,'DE_E_S_545',NULL,'CONSRT_547','CONSRT_548',NULL,42);
insert into `dyextn_constraint_properties` (`IDENTIFIER`,`SOURCE_ENTITY_KEY`,`TARGET_ENTITY_KEY`,`SRC_CONSTRAINT_NAME`,`TARGET_CONSTRAINT_NAME`,`CATEGORY_ASSOCIATION_ID`,`ASSOCIATION_ID`) values (50,NULL,'DE_E_T_521','CONSRT_522','CONSRT_523',NULL,51);
insert into `dyextn_constraint_properties` (`IDENTIFIER`,`SOURCE_ENTITY_KEY`,`TARGET_ENTITY_KEY`,`SRC_CONSTRAINT_NAME`,`TARGET_CONSTRAINT_NAME`,`CATEGORY_ASSOCIATION_ID`,`ASSOCIATION_ID`) values (54,'DE_E_S_735',NULL,'CONSRT_737','CONSRT_738',NULL,55);
insert into `dyextn_constraint_properties` (`IDENTIFIER`,`SOURCE_ENTITY_KEY`,`TARGET_ENTITY_KEY`,`SRC_CONSTRAINT_NAME`,`TARGET_CONSTRAINT_NAME`,`CATEGORY_ASSOCIATION_ID`,`ASSOCIATION_ID`) values (55,'DE_E_S_510',NULL,'CONSRT_512','CONSRT_513',NULL,56);
insert into `dyextn_constraint_properties` (`IDENTIFIER`,`SOURCE_ENTITY_KEY`,`TARGET_ENTITY_KEY`,`SRC_CONSTRAINT_NAME`,`TARGET_CONSTRAINT_NAME`,`CATEGORY_ASSOCIATION_ID`,`ASSOCIATION_ID`) values (57,'DE_E_S_525','DE_E_T_526','CONSRT_527','CONSRT_528',NULL,58);
insert into `dyextn_constraint_properties` (`IDENTIFIER`,`SOURCE_ENTITY_KEY`,`TARGET_ENTITY_KEY`,`SRC_CONSTRAINT_NAME`,`TARGET_CONSTRAINT_NAME`,`CATEGORY_ASSOCIATION_ID`,`ASSOCIATION_ID`) values (58,'DE_E_S_530',NULL,'CONSRT_532','CONSRT_533',NULL,59);
insert into `dyextn_constraint_properties` (`IDENTIFIER`,`SOURCE_ENTITY_KEY`,`TARGET_ENTITY_KEY`,`SRC_CONSTRAINT_NAME`,`TARGET_CONSTRAINT_NAME`,`CATEGORY_ASSOCIATION_ID`,`ASSOCIATION_ID`) values (63,'DE_E_S_535','DE_E_T_536','CONSRT_537','CONSRT_538',NULL,64);
insert into `dyextn_constraint_properties` (`IDENTIFIER`,`SOURCE_ENTITY_KEY`,`TARGET_ENTITY_KEY`,`SRC_CONSTRAINT_NAME`,`TARGET_CONSTRAINT_NAME`,`CATEGORY_ASSOCIATION_ID`,`ASSOCIATION_ID`) values (67,'DE_E_S_745',NULL,'CONSRT_747','CONSRT_748',NULL,68);
insert into `dyextn_constraint_properties` (`IDENTIFIER`,`SOURCE_ENTITY_KEY`,`TARGET_ENTITY_KEY`,`SRC_CONSTRAINT_NAME`,`TARGET_CONSTRAINT_NAME`,`CATEGORY_ASSOCIATION_ID`,`ASSOCIATION_ID`) values (70,'DE_E_S_670',NULL,'CONSRT_672','CONSRT_673',NULL,71);
insert into `dyextn_constraint_properties` (`IDENTIFIER`,`SOURCE_ENTITY_KEY`,`TARGET_ENTITY_KEY`,`SRC_CONSTRAINT_NAME`,`TARGET_CONSTRAINT_NAME`,`CATEGORY_ASSOCIATION_ID`,`ASSOCIATION_ID`) values (73,'DE_E_S_600',NULL,'CONSRT_602','CONSRT_603',NULL,74);
insert into `dyextn_constraint_properties` (`IDENTIFIER`,`SOURCE_ENTITY_KEY`,`TARGET_ENTITY_KEY`,`SRC_CONSTRAINT_NAME`,`TARGET_CONSTRAINT_NAME`,`CATEGORY_ASSOCIATION_ID`,`ASSOCIATION_ID`) values (79,NULL,'DE_E_T_541','CONSRT_542','CONSRT_543',NULL,80);
insert into `dyextn_constraint_properties` (`IDENTIFIER`,`SOURCE_ENTITY_KEY`,`TARGET_ENTITY_KEY`,`SRC_CONSTRAINT_NAME`,`TARGET_CONSTRAINT_NAME`,`CATEGORY_ASSOCIATION_ID`,`ASSOCIATION_ID`) values (80,NULL,'DE_E_T_516','CONSRT_517','CONSRT_518',NULL,81);
insert into `dyextn_constraint_properties` (`IDENTIFIER`,`SOURCE_ENTITY_KEY`,`TARGET_ENTITY_KEY`,`SRC_CONSTRAINT_NAME`,`TARGET_CONSTRAINT_NAME`,`CATEGORY_ASSOCIATION_ID`,`ASSOCIATION_ID`) values (83,'DE_E_S_760',NULL,'CONSRT_762','CONSRT_763',NULL,84);
insert into `dyextn_constraint_properties` (`IDENTIFIER`,`SOURCE_ENTITY_KEY`,`TARGET_ENTITY_KEY`,`SRC_CONSTRAINT_NAME`,`TARGET_CONSTRAINT_NAME`,`CATEGORY_ASSOCIATION_ID`,`ASSOCIATION_ID`) values (106,'DE_E_S_725',NULL,'CONSRT_727','CONSRT_728',NULL,107);
insert into `dyextn_constraint_properties` (`IDENTIFIER`,`SOURCE_ENTITY_KEY`,`TARGET_ENTITY_KEY`,`SRC_CONSTRAINT_NAME`,`TARGET_CONSTRAINT_NAME`,`CATEGORY_ASSOCIATION_ID`,`ASSOCIATION_ID`) values (110,'DE_E_S_645',NULL,'CONSRT_647','CONSRT_648',NULL,111);
insert into `dyextn_constraint_properties` (`IDENTIFIER`,`SOURCE_ENTITY_KEY`,`TARGET_ENTITY_KEY`,`SRC_CONSTRAINT_NAME`,`TARGET_CONSTRAINT_NAME`,`CATEGORY_ASSOCIATION_ID`,`ASSOCIATION_ID`) values (111,'DE_E_S_495',NULL,'CONSRT_497','CONSRT_498',NULL,112);
insert into `dyextn_constraint_properties` (`IDENTIFIER`,`SOURCE_ENTITY_KEY`,`TARGET_ENTITY_KEY`,`SRC_CONSTRAINT_NAME`,`TARGET_CONSTRAINT_NAME`,`CATEGORY_ASSOCIATION_ID`,`ASSOCIATION_ID`) values (114,'DE_E_S_490',NULL,'CONSRT_492','CONSRT_493',NULL,115);
insert into `dyextn_constraint_properties` (`IDENTIFIER`,`SOURCE_ENTITY_KEY`,`TARGET_ENTITY_KEY`,`SRC_CONSTRAINT_NAME`,`TARGET_CONSTRAINT_NAME`,`CATEGORY_ASSOCIATION_ID`,`ASSOCIATION_ID`) values (116,'DE_E_S_705',NULL,'CONSRT_707','CONSRT_708',NULL,117);
insert into `dyextn_constraint_properties` (`IDENTIFIER`,`SOURCE_ENTITY_KEY`,`TARGET_ENTITY_KEY`,`SRC_CONSTRAINT_NAME`,`TARGET_CONSTRAINT_NAME`,`CATEGORY_ASSOCIATION_ID`,`ASSOCIATION_ID`) values (124,'DE_E_S_630',NULL,'CONSRT_632','CONSRT_633',NULL,125);
insert into `dyextn_constraint_properties` (`IDENTIFIER`,`SOURCE_ENTITY_KEY`,`TARGET_ENTITY_KEY`,`SRC_CONSTRAINT_NAME`,`TARGET_CONSTRAINT_NAME`,`CATEGORY_ASSOCIATION_ID`,`ASSOCIATION_ID`) values (128,'DE_E_S_445',NULL,'CONSRT_447','CONSRT_448',NULL,129);
insert into `dyextn_constraint_properties` (`IDENTIFIER`,`SOURCE_ENTITY_KEY`,`TARGET_ENTITY_KEY`,`SRC_CONSTRAINT_NAME`,`TARGET_CONSTRAINT_NAME`,`CATEGORY_ASSOCIATION_ID`,`ASSOCIATION_ID`) values (129,'DE_E_S_450','DE_E_T_451','CONSRT_452','CONSRT_453',NULL,130);
insert into `dyextn_constraint_properties` (`IDENTIFIER`,`SOURCE_ENTITY_KEY`,`TARGET_ENTITY_KEY`,`SRC_CONSTRAINT_NAME`,`TARGET_CONSTRAINT_NAME`,`CATEGORY_ASSOCIATION_ID`,`ASSOCIATION_ID`) values (130,'DE_E_S_455','DE_E_T_456','CONSRT_457','CONSRT_458',NULL,131);
insert into `dyextn_constraint_properties` (`IDENTIFIER`,`SOURCE_ENTITY_KEY`,`TARGET_ENTITY_KEY`,`SRC_CONSTRAINT_NAME`,`TARGET_CONSTRAINT_NAME`,`CATEGORY_ASSOCIATION_ID`,`ASSOCIATION_ID`) values (132,'DE_E_S_710',NULL,'CONSRT_712','CONSRT_713',NULL,133);
insert into `dyextn_constraint_properties` (`IDENTIFIER`,`SOURCE_ENTITY_KEY`,`TARGET_ENTITY_KEY`,`SRC_CONSTRAINT_NAME`,`TARGET_CONSTRAINT_NAME`,`CATEGORY_ASSOCIATION_ID`,`ASSOCIATION_ID`) values (135,'DE_E_S_730',NULL,'CONSRT_732','CONSRT_733',NULL,136);
insert into `dyextn_constraint_properties` (`IDENTIFIER`,`SOURCE_ENTITY_KEY`,`TARGET_ENTITY_KEY`,`SRC_CONSTRAINT_NAME`,`TARGET_CONSTRAINT_NAME`,`CATEGORY_ASSOCIATION_ID`,`ASSOCIATION_ID`) values (141,'DE_E_S_480',NULL,'CONSRT_482','CONSRT_483',NULL,142);
insert into `dyextn_constraint_properties` (`IDENTIFIER`,`SOURCE_ENTITY_KEY`,`TARGET_ENTITY_KEY`,`SRC_CONSTRAINT_NAME`,`TARGET_CONSTRAINT_NAME`,`CATEGORY_ASSOCIATION_ID`,`ASSOCIATION_ID`) values (142,'DE_E_S_475',NULL,'CONSRT_477','CONSRT_478',NULL,143);
insert into `dyextn_constraint_properties` (`IDENTIFIER`,`SOURCE_ENTITY_KEY`,`TARGET_ENTITY_KEY`,`SRC_CONSTRAINT_NAME`,`TARGET_CONSTRAINT_NAME`,`CATEGORY_ASSOCIATION_ID`,`ASSOCIATION_ID`) values (144,'DE_E_S_470',NULL,'CONSRT_472','CONSRT_473',NULL,145);
insert into `dyextn_constraint_properties` (`IDENTIFIER`,`SOURCE_ENTITY_KEY`,`TARGET_ENTITY_KEY`,`SRC_CONSTRAINT_NAME`,`TARGET_CONSTRAINT_NAME`,`CATEGORY_ASSOCIATION_ID`,`ASSOCIATION_ID`) values (147,'DE_E_S_425','DE_E_T_426','CONSRT_427','CONSRT_428',NULL,148);
insert into `dyextn_constraint_properties` (`IDENTIFIER`,`SOURCE_ENTITY_KEY`,`TARGET_ENTITY_KEY`,`SRC_CONSTRAINT_NAME`,`TARGET_CONSTRAINT_NAME`,`CATEGORY_ASSOCIATION_ID`,`ASSOCIATION_ID`) values (151,'DE_E_S_420',NULL,'CONSRT_422','CONSRT_423',NULL,152);
insert into `dyextn_constraint_properties` (`IDENTIFIER`,`SOURCE_ENTITY_KEY`,`TARGET_ENTITY_KEY`,`SRC_CONSTRAINT_NAME`,`TARGET_CONSTRAINT_NAME`,`CATEGORY_ASSOCIATION_ID`,`ASSOCIATION_ID`) values (156,'DE_E_S_460','DE_E_T_461','CONSRT_462','CONSRT_463',NULL,157);
insert into `dyextn_constraint_properties` (`IDENTIFIER`,`SOURCE_ENTITY_KEY`,`TARGET_ENTITY_KEY`,`SRC_CONSTRAINT_NAME`,`TARGET_CONSTRAINT_NAME`,`CATEGORY_ASSOCIATION_ID`,`ASSOCIATION_ID`) values (159,'DE_E_S_465',NULL,'CONSRT_467','CONSRT_468',NULL,160);
insert into `dyextn_constraint_properties` (`IDENTIFIER`,`SOURCE_ENTITY_KEY`,`TARGET_ENTITY_KEY`,`SRC_CONSTRAINT_NAME`,`TARGET_CONSTRAINT_NAME`,`CATEGORY_ASSOCIATION_ID`,`ASSOCIATION_ID`) values (161,'DE_E_S_635',NULL,'CONSRT_637','CONSRT_638',NULL,162);
insert into `dyextn_constraint_properties` (`IDENTIFIER`,`SOURCE_ENTITY_KEY`,`TARGET_ENTITY_KEY`,`SRC_CONSTRAINT_NAME`,`TARGET_CONSTRAINT_NAME`,`CATEGORY_ASSOCIATION_ID`,`ASSOCIATION_ID`) values (164,'DE_E_S_575',NULL,'CONSRT_577','CONSRT_578',NULL,165);
insert into `dyextn_constraint_properties` (`IDENTIFIER`,`SOURCE_ENTITY_KEY`,`TARGET_ENTITY_KEY`,`SRC_CONSTRAINT_NAME`,`TARGET_CONSTRAINT_NAME`,`CATEGORY_ASSOCIATION_ID`,`ASSOCIATION_ID`) values (165,NULL,'DE_E_T_641','CONSRT_642','CONSRT_643',NULL,166);
insert into `dyextn_constraint_properties` (`IDENTIFIER`,`SOURCE_ENTITY_KEY`,`TARGET_ENTITY_KEY`,`SRC_CONSTRAINT_NAME`,`TARGET_CONSTRAINT_NAME`,`CATEGORY_ASSOCIATION_ID`,`ASSOCIATION_ID`) values (166,'DE_E_S_485','DE_E_T_486','CONSRT_487','CONSRT_488',NULL,167);
insert into `dyextn_constraint_properties` (`IDENTIFIER`,`SOURCE_ENTITY_KEY`,`TARGET_ENTITY_KEY`,`SRC_CONSTRAINT_NAME`,`TARGET_CONSTRAINT_NAME`,`CATEGORY_ASSOCIATION_ID`,`ASSOCIATION_ID`) values (182,NULL,'DE_E_T_776','CONSRT_777','CONSRT_778',NULL,183);
insert into `dyextn_constraint_properties` (`IDENTIFIER`,`SOURCE_ENTITY_KEY`,`TARGET_ENTITY_KEY`,`SRC_CONSTRAINT_NAME`,`TARGET_CONSTRAINT_NAME`,`CATEGORY_ASSOCIATION_ID`,`ASSOCIATION_ID`) values (183,'DE_E_S_750','DE_E_T_751','CONSRT_752','CONSRT_753',NULL,184);
insert into `dyextn_constraint_properties` (`IDENTIFIER`,`SOURCE_ENTITY_KEY`,`TARGET_ENTITY_KEY`,`SRC_CONSTRAINT_NAME`,`TARGET_CONSTRAINT_NAME`,`CATEGORY_ASSOCIATION_ID`,`ASSOCIATION_ID`) values (184,'DE_E_S_740',NULL,'CONSRT_742','CONSRT_743',NULL,185);
insert into `dyextn_constraint_properties` (`IDENTIFIER`,`SOURCE_ENTITY_KEY`,`TARGET_ENTITY_KEY`,`SRC_CONSTRAINT_NAME`,`TARGET_CONSTRAINT_NAME`,`CATEGORY_ASSOCIATION_ID`,`ASSOCIATION_ID`) values (185,'DE_E_S_715',NULL,'CONSRT_717','CONSRT_718',NULL,186);
insert into `dyextn_constraint_properties` (`IDENTIFIER`,`SOURCE_ENTITY_KEY`,`TARGET_ENTITY_KEY`,`SRC_CONSTRAINT_NAME`,`TARGET_CONSTRAINT_NAME`,`CATEGORY_ASSOCIATION_ID`,`ASSOCIATION_ID`) values (186,'DE_E_S_700',NULL,'CONSRT_702','CONSRT_703',NULL,187);
insert into `dyextn_constraint_properties` (`IDENTIFIER`,`SOURCE_ENTITY_KEY`,`TARGET_ENTITY_KEY`,`SRC_CONSTRAINT_NAME`,`TARGET_CONSTRAINT_NAME`,`CATEGORY_ASSOCIATION_ID`,`ASSOCIATION_ID`) values (189,'DE_E_S_690',NULL,'CONSRT_692','CONSRT_693',NULL,190);
insert into `dyextn_constraint_properties` (`IDENTIFIER`,`SOURCE_ENTITY_KEY`,`TARGET_ENTITY_KEY`,`SRC_CONSTRAINT_NAME`,`TARGET_CONSTRAINT_NAME`,`CATEGORY_ASSOCIATION_ID`,`ASSOCIATION_ID`) values (191,'DE_E_S_685',NULL,'CONSRT_687','CONSRT_688',NULL,192);
insert into `dyextn_constraint_properties` (`IDENTIFIER`,`SOURCE_ENTITY_KEY`,`TARGET_ENTITY_KEY`,`SRC_CONSTRAINT_NAME`,`TARGET_CONSTRAINT_NAME`,`CATEGORY_ASSOCIATION_ID`,`ASSOCIATION_ID`) values (192,'DE_E_S_680',NULL,'CONSRT_682','CONSRT_683',NULL,193);
insert into `dyextn_constraint_properties` (`IDENTIFIER`,`SOURCE_ENTITY_KEY`,`TARGET_ENTITY_KEY`,`SRC_CONSTRAINT_NAME`,`TARGET_CONSTRAINT_NAME`,`CATEGORY_ASSOCIATION_ID`,`ASSOCIATION_ID`) values (203,'DE_E_S_675','DE_E_T_676','CONSRT_677','CONSRT_678',NULL,204);
insert into `dyextn_constraint_properties` (`IDENTIFIER`,`SOURCE_ENTITY_KEY`,`TARGET_ENTITY_KEY`,`SRC_CONSTRAINT_NAME`,`TARGET_CONSTRAINT_NAME`,`CATEGORY_ASSOCIATION_ID`,`ASSOCIATION_ID`) values (204,NULL,'DE_E_T_626','CONSRT_627','CONSRT_628',NULL,205);
insert into `dyextn_constraint_properties` (`IDENTIFIER`,`SOURCE_ENTITY_KEY`,`TARGET_ENTITY_KEY`,`SRC_CONSTRAINT_NAME`,`TARGET_CONSTRAINT_NAME`,`CATEGORY_ASSOCIATION_ID`,`ASSOCIATION_ID`) values (206,'DE_E_S_615',NULL,'CONSRT_617','CONSRT_618',NULL,207);
insert into `dyextn_constraint_properties` (`IDENTIFIER`,`SOURCE_ENTITY_KEY`,`TARGET_ENTITY_KEY`,`SRC_CONSTRAINT_NAME`,`TARGET_CONSTRAINT_NAME`,`CATEGORY_ASSOCIATION_ID`,`ASSOCIATION_ID`) values (214,'DE_E_S_610','DE_E_T_611','CONSRT_612','CONSRT_613',NULL,215);
insert into `dyextn_constraint_properties` (`IDENTIFIER`,`SOURCE_ENTITY_KEY`,`TARGET_ENTITY_KEY`,`SRC_CONSTRAINT_NAME`,`TARGET_CONSTRAINT_NAME`,`CATEGORY_ASSOCIATION_ID`,`ASSOCIATION_ID`) values (217,'DE_E_S_590',NULL,'CONSRT_592','CONSRT_593',NULL,218);
insert into `dyextn_constraint_properties` (`IDENTIFIER`,`SOURCE_ENTITY_KEY`,`TARGET_ENTITY_KEY`,`SRC_CONSTRAINT_NAME`,`TARGET_CONSTRAINT_NAME`,`CATEGORY_ASSOCIATION_ID`,`ASSOCIATION_ID`) values (218,'DE_E_S_585',NULL,'CONSRT_587','CONSRT_588',NULL,219);
insert into `dyextn_constraint_properties` (`IDENTIFIER`,`SOURCE_ENTITY_KEY`,`TARGET_ENTITY_KEY`,`SRC_CONSTRAINT_NAME`,`TARGET_CONSTRAINT_NAME`,`CATEGORY_ASSOCIATION_ID`,`ASSOCIATION_ID`) values (219,NULL,'DE_E_T_581','CONSRT_582','CONSRT_583',NULL,220);
insert into `dyextn_constraint_properties` (`IDENTIFIER`,`SOURCE_ENTITY_KEY`,`TARGET_ENTITY_KEY`,`SRC_CONSTRAINT_NAME`,`TARGET_CONSTRAINT_NAME`,`CATEGORY_ASSOCIATION_ID`,`ASSOCIATION_ID`) values (221,NULL,'DE_E_T_756','CONSRT_757','CONSRT_758',NULL,222);
insert into `dyextn_constraint_properties` (`IDENTIFIER`,`SOURCE_ENTITY_KEY`,`TARGET_ENTITY_KEY`,`SRC_CONSTRAINT_NAME`,`TARGET_CONSTRAINT_NAME`,`CATEGORY_ASSOCIATION_ID`,`ASSOCIATION_ID`) values (222,'DE_E_S_695','DE_E_T_696','CONSRT_697','CONSRT_698',NULL,223);
insert into `dyextn_constraint_properties` (`IDENTIFIER`,`SOURCE_ENTITY_KEY`,`TARGET_ENTITY_KEY`,`SRC_CONSTRAINT_NAME`,`TARGET_CONSTRAINT_NAME`,`CATEGORY_ASSOCIATION_ID`,`ASSOCIATION_ID`) values (225,'DE_E_S_570','DE_E_T_571','CONSRT_572','CONSRT_573',NULL,226);
insert into `dyextn_constraint_properties` (`IDENTIFIER`,`SOURCE_ENTITY_KEY`,`TARGET_ENTITY_KEY`,`SRC_CONSTRAINT_NAME`,`TARGET_CONSTRAINT_NAME`,`CATEGORY_ASSOCIATION_ID`,`ASSOCIATION_ID`) values (226,NULL,'DE_E_T_561','CONSRT_562','CONSRT_563',NULL,227);
insert into `dyextn_constraint_properties` (`IDENTIFIER`,`SOURCE_ENTITY_KEY`,`TARGET_ENTITY_KEY`,`SRC_CONSTRAINT_NAME`,`TARGET_CONSTRAINT_NAME`,`CATEGORY_ASSOCIATION_ID`,`ASSOCIATION_ID`) values (227,NULL,'DE_E_T_556','CONSRT_557','CONSRT_558',NULL,228);
insert into `dyextn_constraint_properties` (`IDENTIFIER`,`SOURCE_ENTITY_KEY`,`TARGET_ENTITY_KEY`,`SRC_CONSTRAINT_NAME`,`TARGET_CONSTRAINT_NAME`,`CATEGORY_ASSOCIATION_ID`,`ASSOCIATION_ID`) values (231,'DE_E_S_550','DE_E_T_551','CONSRT_552','CONSRT_553',NULL,232);
insert into `dyextn_constraint_properties` (`IDENTIFIER`,`SOURCE_ENTITY_KEY`,`TARGET_ENTITY_KEY`,`SRC_CONSTRAINT_NAME`,`TARGET_CONSTRAINT_NAME`,`CATEGORY_ASSOCIATION_ID`,`ASSOCIATION_ID`) values (234,'DE_E_S_505',NULL,'CONSRT_507','CONSRT_508',NULL,235);
insert into `dyextn_constraint_properties` (`IDENTIFIER`,`SOURCE_ENTITY_KEY`,`TARGET_ENTITY_KEY`,`SRC_CONSTRAINT_NAME`,`TARGET_CONSTRAINT_NAME`,`CATEGORY_ASSOCIATION_ID`,`ASSOCIATION_ID`) values (237,'DE_E_S_500','DE_E_T_501','CONSRT_502','CONSRT_503',NULL,238);
insert into `dyextn_constraint_properties` (`IDENTIFIER`,`SOURCE_ENTITY_KEY`,`TARGET_ENTITY_KEY`,`SRC_CONSTRAINT_NAME`,`TARGET_CONSTRAINT_NAME`,`CATEGORY_ASSOCIATION_ID`,`ASSOCIATION_ID`) values (240,'DE_E_S_435',NULL,'CONSRT_437','CONSRT_438',NULL,241);
insert into `dyextn_constraint_properties` (`IDENTIFIER`,`SOURCE_ENTITY_KEY`,`TARGET_ENTITY_KEY`,`SRC_CONSTRAINT_NAME`,`TARGET_CONSTRAINT_NAME`,`CATEGORY_ASSOCIATION_ID`,`ASSOCIATION_ID`) values (241,'DE_E_S_345',NULL,'CONSRT_347','CONSRT_348',NULL,242);
insert into `dyextn_constraint_properties` (`IDENTIFIER`,`SOURCE_ENTITY_KEY`,`TARGET_ENTITY_KEY`,`SRC_CONSTRAINT_NAME`,`TARGET_CONSTRAINT_NAME`,`CATEGORY_ASSOCIATION_ID`,`ASSOCIATION_ID`) values (242,'DE_E_S_340',NULL,'CONSRT_342','CONSRT_343',NULL,243);
insert into `dyextn_constraint_properties` (`IDENTIFIER`,`SOURCE_ENTITY_KEY`,`TARGET_ENTITY_KEY`,`SRC_CONSTRAINT_NAME`,`TARGET_CONSTRAINT_NAME`,`CATEGORY_ASSOCIATION_ID`,`ASSOCIATION_ID`) values (243,'DE_E_S_335',NULL,'CONSRT_337','CONSRT_338',NULL,244);
insert into `dyextn_constraint_properties` (`IDENTIFIER`,`SOURCE_ENTITY_KEY`,`TARGET_ENTITY_KEY`,`SRC_CONSTRAINT_NAME`,`TARGET_CONSTRAINT_NAME`,`CATEGORY_ASSOCIATION_ID`,`ASSOCIATION_ID`) values (244,'DE_E_S_330',NULL,'CONSRT_332','CONSRT_333',NULL,245);
insert into `dyextn_constraint_properties` (`IDENTIFIER`,`SOURCE_ENTITY_KEY`,`TARGET_ENTITY_KEY`,`SRC_CONSTRAINT_NAME`,`TARGET_CONSTRAINT_NAME`,`CATEGORY_ASSOCIATION_ID`,`ASSOCIATION_ID`) values (254,'DE_E_S_350','DE_E_T_351','CONSRT_352','CONSRT_353',NULL,255);
insert into `dyextn_constraint_properties` (`IDENTIFIER`,`SOURCE_ENTITY_KEY`,`TARGET_ENTITY_KEY`,`SRC_CONSTRAINT_NAME`,`TARGET_CONSTRAINT_NAME`,`CATEGORY_ASSOCIATION_ID`,`ASSOCIATION_ID`) values (255,'DE_E_S_355',NULL,'CONSRT_357','CONSRT_358',NULL,256);
insert into `dyextn_constraint_properties` (`IDENTIFIER`,`SOURCE_ENTITY_KEY`,`TARGET_ENTITY_KEY`,`SRC_CONSTRAINT_NAME`,`TARGET_CONSTRAINT_NAME`,`CATEGORY_ASSOCIATION_ID`,`ASSOCIATION_ID`) values (256,'DE_E_S_360',NULL,'CONSRT_362','CONSRT_363',NULL,257);
insert into `dyextn_constraint_properties` (`IDENTIFIER`,`SOURCE_ENTITY_KEY`,`TARGET_ENTITY_KEY`,`SRC_CONSTRAINT_NAME`,`TARGET_CONSTRAINT_NAME`,`CATEGORY_ASSOCIATION_ID`,`ASSOCIATION_ID`) values (257,'DE_E_S_365',NULL,'CONSRT_367','CONSRT_368',NULL,258);
insert into `dyextn_constraint_properties` (`IDENTIFIER`,`SOURCE_ENTITY_KEY`,`TARGET_ENTITY_KEY`,`SRC_CONSTRAINT_NAME`,`TARGET_CONSTRAINT_NAME`,`CATEGORY_ASSOCIATION_ID`,`ASSOCIATION_ID`) values (258,'DE_E_S_370',NULL,'CONSRT_372','CONSRT_373',NULL,259);
insert into `dyextn_constraint_properties` (`IDENTIFIER`,`SOURCE_ENTITY_KEY`,`TARGET_ENTITY_KEY`,`SRC_CONSTRAINT_NAME`,`TARGET_CONSTRAINT_NAME`,`CATEGORY_ASSOCIATION_ID`,`ASSOCIATION_ID`) values (259,'DE_E_S_375',NULL,'CONSRT_377','CONSRT_378',NULL,260);
insert into `dyextn_constraint_properties` (`IDENTIFIER`,`SOURCE_ENTITY_KEY`,`TARGET_ENTITY_KEY`,`SRC_CONSTRAINT_NAME`,`TARGET_CONSTRAINT_NAME`,`CATEGORY_ASSOCIATION_ID`,`ASSOCIATION_ID`) values (260,'DE_E_S_380',NULL,'CONSRT_382','CONSRT_383',NULL,261);
insert into `dyextn_constraint_properties` (`IDENTIFIER`,`SOURCE_ENTITY_KEY`,`TARGET_ENTITY_KEY`,`SRC_CONSTRAINT_NAME`,`TARGET_CONSTRAINT_NAME`,`CATEGORY_ASSOCIATION_ID`,`ASSOCIATION_ID`) values (261,NULL,'DE_E_T_386','CONSRT_387','CONSRT_388',NULL,262);
insert into `dyextn_constraint_properties` (`IDENTIFIER`,`SOURCE_ENTITY_KEY`,`TARGET_ENTITY_KEY`,`SRC_CONSTRAINT_NAME`,`TARGET_CONSTRAINT_NAME`,`CATEGORY_ASSOCIATION_ID`,`ASSOCIATION_ID`) values (264,'DE_E_S_390',NULL,'CONSRT_392','CONSRT_393',NULL,265);
insert into `dyextn_constraint_properties` (`IDENTIFIER`,`SOURCE_ENTITY_KEY`,`TARGET_ENTITY_KEY`,`SRC_CONSTRAINT_NAME`,`TARGET_CONSTRAINT_NAME`,`CATEGORY_ASSOCIATION_ID`,`ASSOCIATION_ID`) values (267,'DE_E_S_395',NULL,'CONSRT_397','CONSRT_398',NULL,268);
insert into `dyextn_constraint_properties` (`IDENTIFIER`,`SOURCE_ENTITY_KEY`,`TARGET_ENTITY_KEY`,`SRC_CONSTRAINT_NAME`,`TARGET_CONSTRAINT_NAME`,`CATEGORY_ASSOCIATION_ID`,`ASSOCIATION_ID`) values (269,'DE_E_S_440','DE_E_T_441','CONSRT_442','CONSRT_443',NULL,270);
insert into `dyextn_constraint_properties` (`IDENTIFIER`,`SOURCE_ENTITY_KEY`,`TARGET_ENTITY_KEY`,`SRC_CONSTRAINT_NAME`,`TARGET_CONSTRAINT_NAME`,`CATEGORY_ASSOCIATION_ID`,`ASSOCIATION_ID`) values (272,'DE_E_S_430','DE_E_T_431','CONSRT_432','CONSRT_433',NULL,273);
insert into `dyextn_constraint_properties` (`IDENTIFIER`,`SOURCE_ENTITY_KEY`,`TARGET_ENTITY_KEY`,`SRC_CONSTRAINT_NAME`,`TARGET_CONSTRAINT_NAME`,`CATEGORY_ASSOCIATION_ID`,`ASSOCIATION_ID`) values (273,'DE_E_S_410',NULL,'CONSRT_412','CONSRT_413',NULL,274);
insert into `dyextn_constraint_properties` (`IDENTIFIER`,`SOURCE_ENTITY_KEY`,`TARGET_ENTITY_KEY`,`SRC_CONSTRAINT_NAME`,`TARGET_CONSTRAINT_NAME`,`CATEGORY_ASSOCIATION_ID`,`ASSOCIATION_ID`) values (274,'DE_E_S_405',NULL,'CONSRT_407','CONSRT_408',NULL,275);
insert into `dyextn_constraint_properties` (`IDENTIFIER`,`SOURCE_ENTITY_KEY`,`TARGET_ENTITY_KEY`,`SRC_CONSTRAINT_NAME`,`TARGET_CONSTRAINT_NAME`,`CATEGORY_ASSOCIATION_ID`,`ASSOCIATION_ID`) values (278,'DE_E_S_400','DE_E_T_401','CONSRT_402','CONSRT_403',NULL,279);
insert into `dyextn_constraint_properties` (`IDENTIFIER`,`SOURCE_ENTITY_KEY`,`TARGET_ENTITY_KEY`,`SRC_CONSTRAINT_NAME`,`TARGET_CONSTRAINT_NAME`,`CATEGORY_ASSOCIATION_ID`,`ASSOCIATION_ID`) values (281,'DE_E_S_415',NULL,'CONSRT_417','CONSRT_418',NULL,282);
insert into `dyextn_constraint_properties` (`IDENTIFIER`,`SOURCE_ENTITY_KEY`,`TARGET_ENTITY_KEY`,`SRC_CONSTRAINT_NAME`,`TARGET_CONSTRAINT_NAME`,`CATEGORY_ASSOCIATION_ID`,`ASSOCIATION_ID`) values (289,'DE_E_S_665','DE_E_T_666','CONSRT_667','CONSRT_668',NULL,290);
insert into `dyextn_constraint_properties` (`IDENTIFIER`,`SOURCE_ENTITY_KEY`,`TARGET_ENTITY_KEY`,`SRC_CONSTRAINT_NAME`,`TARGET_CONSTRAINT_NAME`,`CATEGORY_ASSOCIATION_ID`,`ASSOCIATION_ID`) values (290,'DE_E_S_660',NULL,'CONSRT_662','CONSRT_663',NULL,291);
insert into `dyextn_constraint_properties` (`IDENTIFIER`,`SOURCE_ENTITY_KEY`,`TARGET_ENTITY_KEY`,`SRC_CONSTRAINT_NAME`,`TARGET_CONSTRAINT_NAME`,`CATEGORY_ASSOCIATION_ID`,`ASSOCIATION_ID`) values (291,'DE_E_S_655',NULL,'CONSRT_657','CONSRT_658',NULL,292);
insert into `dyextn_constraint_properties` (`IDENTIFIER`,`SOURCE_ENTITY_KEY`,`TARGET_ENTITY_KEY`,`SRC_CONSTRAINT_NAME`,`TARGET_CONSTRAINT_NAME`,`CATEGORY_ASSOCIATION_ID`,`ASSOCIATION_ID`) values (292,'DE_E_S_650',NULL,'CONSRT_652','CONSRT_653',NULL,293);

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

insert into `dyextn_container` (`IDENTIFIER`,`BUTTON_CSS`,`CAPTION`,`ABSTRACT_ENTITY_ID`,`MAIN_TABLE_CSS`,`REQUIRED_FIELD_INDICATOR`,`REQUIRED_FIELD_WARNING_MESSAGE`,`TITLE_CSS`,`BASE_CONTAINER_ID`,`ADD_CAPTION`,`ENTITY_GROUP_ID`,`VIEW_ID`) values (1,NULL,'MrnType',287,NULL,'*','indicates required fields.',NULL,NULL,'',1,NULL);
insert into `dyextn_container` (`IDENTIFIER`,`BUTTON_CSS`,`CAPTION`,`ABSTRACT_ENTITY_ID`,`MAIN_TABLE_CSS`,`REQUIRED_FIELD_INDICATOR`,`REQUIRED_FIELD_WARNING_MESSAGE`,`TITLE_CSS`,`BASE_CONTAINER_ID`,`ADD_CAPTION`,`ENTITY_GROUP_ID`,`VIEW_ID`) values (2,NULL,'Units',172,NULL,'*','indicates required fields.',NULL,NULL,'',1,NULL);
insert into `dyextn_container` (`IDENTIFIER`,`BUTTON_CSS`,`CAPTION`,`ABSTRACT_ENTITY_ID`,`MAIN_TABLE_CSS`,`REQUIRED_FIELD_INDICATOR`,`REQUIRED_FIELD_WARNING_MESSAGE`,`TITLE_CSS`,`BASE_CONTAINER_ID`,`ADD_CAPTION`,`ENTITY_GROUP_ID`,`VIEW_ID`) values (3,NULL,'MedicalEntitiesDictionary',8,NULL,'*','indicates required fields.',NULL,NULL,'',1,NULL);
insert into `dyextn_container` (`IDENTIFIER`,`BUTTON_CSS`,`CAPTION`,`ABSTRACT_ENTITY_ID`,`MAIN_TABLE_CSS`,`REQUIRED_FIELD_INDICATOR`,`REQUIRED_FIELD_WARNING_MESSAGE`,`TITLE_CSS`,`BASE_CONTAINER_ID`,`ADD_CAPTION`,`ENTITY_GROUP_ID`,`VIEW_ID`) values (4,NULL,'Status',102,NULL,'*','indicates required fields.',NULL,3,'',1,NULL);
insert into `dyextn_container` (`IDENTIFIER`,`BUTTON_CSS`,`CAPTION`,`ABSTRACT_ENTITY_ID`,`MAIN_TABLE_CSS`,`REQUIRED_FIELD_INDICATOR`,`REQUIRED_FIELD_WARNING_MESSAGE`,`TITLE_CSS`,`BASE_CONTAINER_ID`,`ADD_CAPTION`,`ENTITY_GROUP_ID`,`VIEW_ID`) values (5,NULL,'ResultValue',134,NULL,'*','indicates required fields.',NULL,NULL,'',1,NULL);
insert into `dyextn_container` (`IDENTIFIER`,`BUTTON_CSS`,`CAPTION`,`ABSTRACT_ENTITY_ID`,`MAIN_TABLE_CSS`,`REQUIRED_FIELD_INDICATOR`,`REQUIRED_FIELD_WARNING_MESSAGE`,`TITLE_CSS`,`BASE_CONTAINER_ID`,`ADD_CAPTION`,`ENTITY_GROUP_ID`,`VIEW_ID`) values (6,NULL,'Result',135,NULL,'*','indicates required fields.',NULL,NULL,'',1,NULL);
insert into `dyextn_container` (`IDENTIFIER`,`BUTTON_CSS`,`CAPTION`,`ABSTRACT_ENTITY_ID`,`MAIN_TABLE_CSS`,`REQUIRED_FIELD_INDICATOR`,`REQUIRED_FIELD_WARNING_MESSAGE`,`TITLE_CSS`,`BASE_CONTAINER_ID`,`ADD_CAPTION`,`ENTITY_GROUP_ID`,`VIEW_ID`) values (7,NULL,'NormalRange',93,NULL,'*','indicates required fields.',NULL,NULL,'',1,NULL);
insert into `dyextn_container` (`IDENTIFIER`,`BUTTON_CSS`,`CAPTION`,`ABSTRACT_ENTITY_ID`,`MAIN_TABLE_CSS`,`REQUIRED_FIELD_INDICATOR`,`REQUIRED_FIELD_WARNING_MESSAGE`,`TITLE_CSS`,`BASE_CONTAINER_ID`,`ADD_CAPTION`,`ENTITY_GROUP_ID`,`VIEW_ID`) values (8,NULL,'MedicalRecordNumber',124,NULL,'*','indicates required fields.',NULL,NULL,'',1,NULL);
insert into `dyextn_container` (`IDENTIFIER`,`BUTTON_CSS`,`CAPTION`,`ABSTRACT_ENTITY_ID`,`MAIN_TABLE_CSS`,`REQUIRED_FIELD_INDICATOR`,`REQUIRED_FIELD_WARNING_MESSAGE`,`TITLE_CSS`,`BASE_CONTAINER_ID`,`ADD_CAPTION`,`ENTITY_GROUP_ID`,`VIEW_ID`) values (9,NULL,'Facility',108,NULL,'*','indicates required fields.',NULL,3,'',1,NULL);
insert into `dyextn_container` (`IDENTIFIER`,`BUTTON_CSS`,`CAPTION`,`ABSTRACT_ENTITY_ID`,`MAIN_TABLE_CSS`,`REQUIRED_FIELD_INDICATOR`,`REQUIRED_FIELD_WARNING_MESSAGE`,`TITLE_CSS`,`BASE_CONTAINER_ID`,`ADD_CAPTION`,`ENTITY_GROUP_ID`,`VIEW_ID`) values (10,NULL,'SpecimanType',158,NULL,'*','indicates required fields.',NULL,3,'',1,NULL);
insert into `dyextn_container` (`IDENTIFIER`,`BUTTON_CSS`,`CAPTION`,`ABSTRACT_ENTITY_ID`,`MAIN_TABLE_CSS`,`REQUIRED_FIELD_INDICATOR`,`REQUIRED_FIELD_WARNING_MESSAGE`,`TITLE_CSS`,`BASE_CONTAINER_ID`,`ADD_CAPTION`,`ENTITY_GROUP_ID`,`VIEW_ID`) values (11,NULL,'LaboratoryTestType',149,NULL,'*','indicates required fields.',NULL,3,'',1,NULL);
insert into `dyextn_container` (`IDENTIFIER`,`BUTTON_CSS`,`CAPTION`,`ABSTRACT_ENTITY_ID`,`MAIN_TABLE_CSS`,`REQUIRED_FIELD_INDICATOR`,`REQUIRED_FIELD_WARNING_MESSAGE`,`TITLE_CSS`,`BASE_CONTAINER_ID`,`ADD_CAPTION`,`ENTITY_GROUP_ID`,`VIEW_ID`) values (12,NULL,'DischargeDisposition',239,NULL,'*','indicates required fields.',NULL,3,'',1,NULL);
insert into `dyextn_container` (`IDENTIFIER`,`BUTTON_CSS`,`CAPTION`,`ABSTRACT_ENTITY_ID`,`MAIN_TABLE_CSS`,`REQUIRED_FIELD_INDICATOR`,`REQUIRED_FIELD_WARNING_MESSAGE`,`TITLE_CSS`,`BASE_CONTAINER_ID`,`ADD_CAPTION`,`ENTITY_GROUP_ID`,`VIEW_ID`) values (13,NULL,'DiagnosticRelatedGroup',100,NULL,'*','indicates required fields.',NULL,3,'',1,NULL);
insert into `dyextn_container` (`IDENTIFIER`,`BUTTON_CSS`,`CAPTION`,`ABSTRACT_ENTITY_ID`,`MAIN_TABLE_CSS`,`REQUIRED_FIELD_INDICATOR`,`REQUIRED_FIELD_WARNING_MESSAGE`,`TITLE_CSS`,`BASE_CONTAINER_ID`,`ADD_CAPTION`,`ENTITY_GROUP_ID`,`VIEW_ID`) values (14,NULL,'DiagnosisType',180,NULL,'*','indicates required fields.',NULL,3,'',1,NULL);
insert into `dyextn_container` (`IDENTIFIER`,`BUTTON_CSS`,`CAPTION`,`ABSTRACT_ENTITY_ID`,`MAIN_TABLE_CSS`,`REQUIRED_FIELD_INDICATOR`,`REQUIRED_FIELD_WARNING_MESSAGE`,`TITLE_CSS`,`BASE_CONTAINER_ID`,`ADD_CAPTION`,`ENTITY_GROUP_ID`,`VIEW_ID`) values (15,NULL,'DiagnosisCode',271,NULL,'*','indicates required fields.',NULL,3,'',1,NULL);
insert into `dyextn_container` (`IDENTIFIER`,`BUTTON_CSS`,`CAPTION`,`ABSTRACT_ENTITY_ID`,`MAIN_TABLE_CSS`,`REQUIRED_FIELD_INDICATOR`,`REQUIRED_FIELD_WARNING_MESSAGE`,`TITLE_CSS`,`BASE_CONTAINER_ID`,`ADD_CAPTION`,`ENTITY_GROUP_ID`,`VIEW_ID`) values (16,NULL,'Diagnosis',269,NULL,'*','indicates required fields.',NULL,NULL,'',1,NULL);
insert into `dyextn_container` (`IDENTIFIER`,`BUTTON_CSS`,`CAPTION`,`ABSTRACT_ENTITY_ID`,`MAIN_TABLE_CSS`,`REQUIRED_FIELD_INDICATOR`,`REQUIRED_FIELD_WARNING_MESSAGE`,`TITLE_CSS`,`BASE_CONTAINER_ID`,`ADD_CAPTION`,`ENTITY_GROUP_ID`,`VIEW_ID`) values (17,NULL,'ProcedureCode',176,NULL,'*','indicates required fields.',NULL,3,'',1,NULL);
insert into `dyextn_container` (`IDENTIFIER`,`BUTTON_CSS`,`CAPTION`,`ABSTRACT_ENTITY_ID`,`MAIN_TABLE_CSS`,`REQUIRED_FIELD_INDICATOR`,`REQUIRED_FIELD_WARNING_MESSAGE`,`TITLE_CSS`,`BASE_CONTAINER_ID`,`ADD_CAPTION`,`ENTITY_GROUP_ID`,`VIEW_ID`) values (18,NULL,'CptProcedureCode',283,NULL,'*','indicates required fields.',NULL,17,'',1,NULL);
insert into `dyextn_container` (`IDENTIFIER`,`BUTTON_CSS`,`CAPTION`,`ABSTRACT_ENTITY_ID`,`MAIN_TABLE_CSS`,`REQUIRED_FIELD_INDICATOR`,`REQUIRED_FIELD_WARNING_MESSAGE`,`TITLE_CSS`,`BASE_CONTAINER_ID`,`ADD_CAPTION`,`ENTITY_GROUP_ID`,`VIEW_ID`) values (19,NULL,'ClinicalTrial',206,NULL,'*','indicates required fields.',NULL,NULL,'',1,NULL);
insert into `dyextn_container` (`IDENTIFIER`,`BUTTON_CSS`,`CAPTION`,`ABSTRACT_ENTITY_ID`,`MAIN_TABLE_CSS`,`REQUIRED_FIELD_INDICATOR`,`REQUIRED_FIELD_WARNING_MESSAGE`,`TITLE_CSS`,`BASE_CONTAINER_ID`,`ADD_CAPTION`,`ENTITY_GROUP_ID`,`VIEW_ID`) values (20,NULL,'ResearchOptOut',285,NULL,'*','indicates required fields.',NULL,NULL,'',1,NULL);
insert into `dyextn_container` (`IDENTIFIER`,`BUTTON_CSS`,`CAPTION`,`ABSTRACT_ENTITY_ID`,`MAIN_TABLE_CSS`,`REQUIRED_FIELD_INDICATOR`,`REQUIRED_FIELD_WARNING_MESSAGE`,`TITLE_CSS`,`BASE_CONTAINER_ID`,`ADD_CAPTION`,`ENTITY_GROUP_ID`,`VIEW_ID`) values (21,NULL,'PhoneType',91,NULL,'*','indicates required fields.',NULL,NULL,'',1,NULL);
insert into `dyextn_container` (`IDENTIFIER`,`BUTTON_CSS`,`CAPTION`,`ABSTRACT_ENTITY_ID`,`MAIN_TABLE_CSS`,`REQUIRED_FIELD_INDICATOR`,`REQUIRED_FIELD_WARNING_MESSAGE`,`TITLE_CSS`,`BASE_CONTAINER_ID`,`ADD_CAPTION`,`ENTITY_GROUP_ID`,`VIEW_ID`) values (22,NULL,'ActiveUpiFlag',87,NULL,'*','indicates required fields.',NULL,NULL,'',1,NULL);
insert into `dyextn_container` (`IDENTIFIER`,`BUTTON_CSS`,`CAPTION`,`ABSTRACT_ENTITY_ID`,`MAIN_TABLE_CSS`,`REQUIRED_FIELD_INDICATOR`,`REQUIRED_FIELD_WARNING_MESSAGE`,`TITLE_CSS`,`BASE_CONTAINER_ID`,`ADD_CAPTION`,`ENTITY_GROUP_ID`,`VIEW_ID`) values (23,NULL,'State',66,NULL,'*','indicates required fields.',NULL,3,'',1,NULL);
insert into `dyextn_container` (`IDENTIFIER`,`BUTTON_CSS`,`CAPTION`,`ABSTRACT_ENTITY_ID`,`MAIN_TABLE_CSS`,`REQUIRED_FIELD_INDICATOR`,`REQUIRED_FIELD_WARNING_MESSAGE`,`TITLE_CSS`,`BASE_CONTAINER_ID`,`ADD_CAPTION`,`ENTITY_GROUP_ID`,`VIEW_ID`) values (24,NULL,'Religion',34,NULL,'*','indicates required fields.',NULL,3,'',1,NULL);
insert into `dyextn_container` (`IDENTIFIER`,`BUTTON_CSS`,`CAPTION`,`ABSTRACT_ENTITY_ID`,`MAIN_TABLE_CSS`,`REQUIRED_FIELD_INDICATOR`,`REQUIRED_FIELD_WARNING_MESSAGE`,`TITLE_CSS`,`BASE_CONTAINER_ID`,`ADD_CAPTION`,`ENTITY_GROUP_ID`,`VIEW_ID`) values (25,NULL,'AddressType',69,NULL,'*','indicates required fields.',NULL,3,'',1,NULL);
insert into `dyextn_container` (`IDENTIFIER`,`BUTTON_CSS`,`CAPTION`,`ABSTRACT_ENTITY_ID`,`MAIN_TABLE_CSS`,`REQUIRED_FIELD_INDICATOR`,`REQUIRED_FIELD_WARNING_MESSAGE`,`TITLE_CSS`,`BASE_CONTAINER_ID`,`ADD_CAPTION`,`ENTITY_GROUP_ID`,`VIEW_ID`) values (26,NULL,'AdvancedDirectiveExists',31,NULL,'*','indicates required fields.',NULL,3,'',1,NULL);
insert into `dyextn_container` (`IDENTIFIER`,`BUTTON_CSS`,`CAPTION`,`ABSTRACT_ENTITY_ID`,`MAIN_TABLE_CSS`,`REQUIRED_FIELD_INDICATOR`,`REQUIRED_FIELD_WARNING_MESSAGE`,`TITLE_CSS`,`BASE_CONTAINER_ID`,`ADD_CAPTION`,`ENTITY_GROUP_ID`,`VIEW_ID`) values (27,NULL,'AssociatedPerson',52,NULL,'*','indicates required fields.',NULL,NULL,'',1,NULL);
insert into `dyextn_container` (`IDENTIFIER`,`BUTTON_CSS`,`CAPTION`,`ABSTRACT_ENTITY_ID`,`MAIN_TABLE_CSS`,`REQUIRED_FIELD_INDICATOR`,`REQUIRED_FIELD_WARNING_MESSAGE`,`TITLE_CSS`,`BASE_CONTAINER_ID`,`ADD_CAPTION`,`ENTITY_GROUP_ID`,`VIEW_ID`) values (28,NULL,'Country',72,NULL,'*','indicates required fields.',NULL,3,'',1,NULL);
insert into `dyextn_container` (`IDENTIFIER`,`BUTTON_CSS`,`CAPTION`,`ABSTRACT_ENTITY_ID`,`MAIN_TABLE_CSS`,`REQUIRED_FIELD_INDICATOR`,`REQUIRED_FIELD_WARNING_MESSAGE`,`TITLE_CSS`,`BASE_CONTAINER_ID`,`ADD_CAPTION`,`ENTITY_GROUP_ID`,`VIEW_ID`) values (29,NULL,'Demographics',24,NULL,'*','indicates required fields.',NULL,NULL,'',1,NULL);
insert into `dyextn_container` (`IDENTIFIER`,`BUTTON_CSS`,`CAPTION`,`ABSTRACT_ENTITY_ID`,`MAIN_TABLE_CSS`,`REQUIRED_FIELD_INDICATOR`,`REQUIRED_FIELD_WARNING_MESSAGE`,`TITLE_CSS`,`BASE_CONTAINER_ID`,`ADD_CAPTION`,`ENTITY_GROUP_ID`,`VIEW_ID`) values (30,NULL,'EthnicOrigin',25,NULL,'*','indicates required fields.',NULL,3,'',1,NULL);
insert into `dyextn_container` (`IDENTIFIER`,`BUTTON_CSS`,`CAPTION`,`ABSTRACT_ENTITY_ID`,`MAIN_TABLE_CSS`,`REQUIRED_FIELD_INDICATOR`,`REQUIRED_FIELD_WARNING_MESSAGE`,`TITLE_CSS`,`BASE_CONTAINER_ID`,`ADD_CAPTION`,`ENTITY_GROUP_ID`,`VIEW_ID`) values (31,NULL,'Gender',28,NULL,'*','indicates required fields.',NULL,3,'',1,NULL);
insert into `dyextn_container` (`IDENTIFIER`,`BUTTON_CSS`,`CAPTION`,`ABSTRACT_ENTITY_ID`,`MAIN_TABLE_CSS`,`REQUIRED_FIELD_INDICATOR`,`REQUIRED_FIELD_WARNING_MESSAGE`,`TITLE_CSS`,`BASE_CONTAINER_ID`,`ADD_CAPTION`,`ENTITY_GROUP_ID`,`VIEW_ID`) values (32,NULL,'MaritalStatus',40,NULL,'*','indicates required fields.',NULL,3,'',1,NULL);
insert into `dyextn_container` (`IDENTIFIER`,`BUTTON_CSS`,`CAPTION`,`ABSTRACT_ENTITY_ID`,`MAIN_TABLE_CSS`,`REQUIRED_FIELD_INDICATOR`,`REQUIRED_FIELD_WARNING_MESSAGE`,`TITLE_CSS`,`BASE_CONTAINER_ID`,`ADD_CAPTION`,`ENTITY_GROUP_ID`,`VIEW_ID`) values (33,NULL,'Person',23,NULL,'*','indicates required fields.',NULL,NULL,'',1,NULL);
insert into `dyextn_container` (`IDENTIFIER`,`BUTTON_CSS`,`CAPTION`,`ABSTRACT_ENTITY_ID`,`MAIN_TABLE_CSS`,`REQUIRED_FIELD_INDICATOR`,`REQUIRED_FIELD_WARNING_MESSAGE`,`TITLE_CSS`,`BASE_CONTAINER_ID`,`ADD_CAPTION`,`ENTITY_GROUP_ID`,`VIEW_ID`) values (34,NULL,'PersonName',13,NULL,'*','indicates required fields.',NULL,NULL,'',1,NULL);
insert into `dyextn_container` (`IDENTIFIER`,`BUTTON_CSS`,`CAPTION`,`ABSTRACT_ENTITY_ID`,`MAIN_TABLE_CSS`,`REQUIRED_FIELD_INDICATOR`,`REQUIRED_FIELD_WARNING_MESSAGE`,`TITLE_CSS`,`BASE_CONTAINER_ID`,`ADD_CAPTION`,`ENTITY_GROUP_ID`,`VIEW_ID`) values (35,NULL,'Phone',60,NULL,'*','indicates required fields.',NULL,NULL,'',1,NULL);
insert into `dyextn_container` (`IDENTIFIER`,`BUTTON_CSS`,`CAPTION`,`ABSTRACT_ENTITY_ID`,`MAIN_TABLE_CSS`,`REQUIRED_FIELD_INDICATOR`,`REQUIRED_FIELD_WARNING_MESSAGE`,`TITLE_CSS`,`BASE_CONTAINER_ID`,`ADD_CAPTION`,`ENTITY_GROUP_ID`,`VIEW_ID`) values (36,NULL,'Race',37,NULL,'*','indicates required fields.',NULL,3,'',1,NULL);
insert into `dyextn_container` (`IDENTIFIER`,`BUTTON_CSS`,`CAPTION`,`ABSTRACT_ENTITY_ID`,`MAIN_TABLE_CSS`,`REQUIRED_FIELD_INDICATOR`,`REQUIRED_FIELD_WARNING_MESSAGE`,`TITLE_CSS`,`BASE_CONTAINER_ID`,`ADD_CAPTION`,`ENTITY_GROUP_ID`,`VIEW_ID`) values (37,NULL,'RelationToPerson',53,NULL,'*','indicates required fields.',NULL,3,'',1,NULL);
insert into `dyextn_container` (`IDENTIFIER`,`BUTTON_CSS`,`CAPTION`,`ABSTRACT_ENTITY_ID`,`MAIN_TABLE_CSS`,`REQUIRED_FIELD_INDICATOR`,`REQUIRED_FIELD_WARNING_MESSAGE`,`TITLE_CSS`,`BASE_CONTAINER_ID`,`ADD_CAPTION`,`ENTITY_GROUP_ID`,`VIEW_ID`) values (38,NULL,'Encounter',289,NULL,'*','indicates required fields.',NULL,NULL,'',1,NULL);
insert into `dyextn_container` (`IDENTIFIER`,`BUTTON_CSS`,`CAPTION`,`ABSTRACT_ENTITY_ID`,`MAIN_TABLE_CSS`,`REQUIRED_FIELD_INDICATOR`,`REQUIRED_FIELD_WARNING_MESSAGE`,`TITLE_CSS`,`BASE_CONTAINER_ID`,`ADD_CAPTION`,`ENTITY_GROUP_ID`,`VIEW_ID`) values (39,NULL,'EncounterDetails',182,NULL,'*','indicates required fields.',NULL,NULL,'',1,NULL);
insert into `dyextn_container` (`IDENTIFIER`,`BUTTON_CSS`,`CAPTION`,`ABSTRACT_ENTITY_ID`,`MAIN_TABLE_CSS`,`REQUIRED_FIELD_INDICATOR`,`REQUIRED_FIELD_WARNING_MESSAGE`,`TITLE_CSS`,`BASE_CONTAINER_ID`,`ADD_CAPTION`,`ENTITY_GROUP_ID`,`VIEW_ID`) values (40,NULL,'FacilityDischargeDisposition',104,NULL,'*','indicates required fields.',NULL,3,'',1,NULL);
insert into `dyextn_container` (`IDENTIFIER`,`BUTTON_CSS`,`CAPTION`,`ABSTRACT_ENTITY_ID`,`MAIN_TABLE_CSS`,`REQUIRED_FIELD_INDICATOR`,`REQUIRED_FIELD_WARNING_MESSAGE`,`TITLE_CSS`,`BASE_CONTAINER_ID`,`ADD_CAPTION`,`ENTITY_GROUP_ID`,`VIEW_ID`) values (41,NULL,'FinancialClass',188,NULL,'*','indicates required fields.',NULL,3,'',1,NULL);
insert into `dyextn_container` (`IDENTIFIER`,`BUTTON_CSS`,`CAPTION`,`ABSTRACT_ENTITY_ID`,`MAIN_TABLE_CSS`,`REQUIRED_FIELD_INDICATOR`,`REQUIRED_FIELD_WARNING_MESSAGE`,`TITLE_CSS`,`BASE_CONTAINER_ID`,`ADD_CAPTION`,`ENTITY_GROUP_ID`,`VIEW_ID`) values (42,NULL,'HipaaNotified',233,NULL,'*','indicates required fields.',NULL,3,'',1,NULL);
insert into `dyextn_container` (`IDENTIFIER`,`BUTTON_CSS`,`CAPTION`,`ABSTRACT_ENTITY_ID`,`MAIN_TABLE_CSS`,`REQUIRED_FIELD_INDICATOR`,`REQUIRED_FIELD_WARNING_MESSAGE`,`TITLE_CSS`,`BASE_CONTAINER_ID`,`ADD_CAPTION`,`ENTITY_GROUP_ID`,`VIEW_ID`) values (43,NULL,'Icd9ProcedureCode',178,NULL,'*','indicates required fields.',NULL,17,'',1,NULL);
insert into `dyextn_container` (`IDENTIFIER`,`BUTTON_CSS`,`CAPTION`,`ABSTRACT_ENTITY_ID`,`MAIN_TABLE_CSS`,`REQUIRED_FIELD_INDICATOR`,`REQUIRED_FIELD_WARNING_MESSAGE`,`TITLE_CSS`,`BASE_CONTAINER_ID`,`ADD_CAPTION`,`ENTITY_GROUP_ID`,`VIEW_ID`) values (44,NULL,'InfectionControlCode',236,NULL,'*','indicates required fields.',NULL,3,'',1,NULL);
insert into `dyextn_container` (`IDENTIFIER`,`BUTTON_CSS`,`CAPTION`,`ABSTRACT_ENTITY_ID`,`MAIN_TABLE_CSS`,`REQUIRED_FIELD_INDICATOR`,`REQUIRED_FIELD_WARNING_MESSAGE`,`TITLE_CSS`,`BASE_CONTAINER_ID`,`ADD_CAPTION`,`ENTITY_GROUP_ID`,`VIEW_ID`) values (45,NULL,'Insurance',191,NULL,'*','indicates required fields.',NULL,NULL,'',1,NULL);
insert into `dyextn_container` (`IDENTIFIER`,`BUTTON_CSS`,`CAPTION`,`ABSTRACT_ENTITY_ID`,`MAIN_TABLE_CSS`,`REQUIRED_FIELD_INDICATOR`,`REQUIRED_FIELD_WARNING_MESSAGE`,`TITLE_CSS`,`BASE_CONTAINER_ID`,`ADD_CAPTION`,`ENTITY_GROUP_ID`,`VIEW_ID`) values (46,NULL,'OptOutIndicator',280,NULL,'*','indicates required fields.',NULL,3,'',1,NULL);
insert into `dyextn_container` (`IDENTIFIER`,`BUTTON_CSS`,`CAPTION`,`ABSTRACT_ENTITY_ID`,`MAIN_TABLE_CSS`,`REQUIRED_FIELD_INDICATOR`,`REQUIRED_FIELD_WARNING_MESSAGE`,`TITLE_CSS`,`BASE_CONTAINER_ID`,`ADD_CAPTION`,`ENTITY_GROUP_ID`,`VIEW_ID`) values (47,NULL,'PatientClass',263,NULL,'*','indicates required fields.',NULL,3,'',1,NULL);
insert into `dyextn_container` (`IDENTIFIER`,`BUTTON_CSS`,`CAPTION`,`ABSTRACT_ENTITY_ID`,`MAIN_TABLE_CSS`,`REQUIRED_FIELD_INDICATOR`,`REQUIRED_FIELD_WARNING_MESSAGE`,`TITLE_CSS`,`BASE_CONTAINER_ID`,`ADD_CAPTION`,`ENTITY_GROUP_ID`,`VIEW_ID`) values (48,NULL,'PatientLocation',2,NULL,'*','indicates required fields.',NULL,NULL,'',1,NULL);
insert into `dyextn_container` (`IDENTIFIER`,`BUTTON_CSS`,`CAPTION`,`ABSTRACT_ENTITY_ID`,`MAIN_TABLE_CSS`,`REQUIRED_FIELD_INDICATOR`,`REQUIRED_FIELD_WARNING_MESSAGE`,`TITLE_CSS`,`BASE_CONTAINER_ID`,`ADD_CAPTION`,`ENTITY_GROUP_ID`,`VIEW_ID`) values (49,NULL,'PatientType',266,NULL,'*','indicates required fields.',NULL,3,'',1,NULL);
insert into `dyextn_container` (`IDENTIFIER`,`BUTTON_CSS`,`CAPTION`,`ABSTRACT_ENTITY_ID`,`MAIN_TABLE_CSS`,`REQUIRED_FIELD_INDICATOR`,`REQUIRED_FIELD_WARNING_MESSAGE`,`TITLE_CSS`,`BASE_CONTAINER_ID`,`ADD_CAPTION`,`ENTITY_GROUP_ID`,`VIEW_ID`) values (50,NULL,'Procedure',221,NULL,'*','indicates required fields.',NULL,NULL,'',1,NULL);
insert into `dyextn_container` (`IDENTIFIER`,`BUTTON_CSS`,`CAPTION`,`ABSTRACT_ENTITY_ID`,`MAIN_TABLE_CSS`,`REQUIRED_FIELD_INDICATOR`,`REQUIRED_FIELD_WARNING_MESSAGE`,`TITLE_CSS`,`BASE_CONTAINER_ID`,`ADD_CAPTION`,`ENTITY_GROUP_ID`,`VIEW_ID`) values (51,NULL,'ProcedureType',224,NULL,'*','indicates required fields.',NULL,3,'',1,NULL);
insert into `dyextn_container` (`IDENTIFIER`,`BUTTON_CSS`,`CAPTION`,`ABSTRACT_ENTITY_ID`,`MAIN_TABLE_CSS`,`REQUIRED_FIELD_INDICATOR`,`REQUIRED_FIELD_WARNING_MESSAGE`,`TITLE_CSS`,`BASE_CONTAINER_ID`,`ADD_CAPTION`,`ENTITY_GROUP_ID`,`VIEW_ID`) values (52,NULL,'Provider',7,NULL,'*','indicates required fields.',NULL,NULL,'',1,NULL);
insert into `dyextn_container` (`IDENTIFIER`,`BUTTON_CSS`,`CAPTION`,`ABSTRACT_ENTITY_ID`,`MAIN_TABLE_CSS`,`REQUIRED_FIELD_INDICATOR`,`REQUIRED_FIELD_WARNING_MESSAGE`,`TITLE_CSS`,`BASE_CONTAINER_ID`,`ADD_CAPTION`,`ENTITY_GROUP_ID`,`VIEW_ID`) values (53,NULL,'Service',216,NULL,'*','indicates required fields.',NULL,3,'',1,NULL);
insert into `dyextn_container` (`IDENTIFIER`,`BUTTON_CSS`,`CAPTION`,`ABSTRACT_ENTITY_ID`,`MAIN_TABLE_CSS`,`REQUIRED_FIELD_INDICATOR`,`REQUIRED_FIELD_WARNING_MESSAGE`,`TITLE_CSS`,`BASE_CONTAINER_ID`,`ADD_CAPTION`,`ENTITY_GROUP_ID`,`VIEW_ID`) values (54,NULL,'VipIndicator',89,NULL,'*','indicates required fields.',NULL,3,'',1,NULL);
insert into `dyextn_container` (`IDENTIFIER`,`BUTTON_CSS`,`CAPTION`,`ABSTRACT_ENTITY_ID`,`MAIN_TABLE_CSS`,`REQUIRED_FIELD_INDICATOR`,`REQUIRED_FIELD_WARNING_MESSAGE`,`TITLE_CSS`,`BASE_CONTAINER_ID`,`ADD_CAPTION`,`ENTITY_GROUP_ID`,`VIEW_ID`) values (55,NULL,'AbnormalFlag',146,NULL,'*','indicates required fields.',NULL,3,'',1,NULL);
insert into `dyextn_container` (`IDENTIFIER`,`BUTTON_CSS`,`CAPTION`,`ABSTRACT_ENTITY_ID`,`MAIN_TABLE_CSS`,`REQUIRED_FIELD_INDICATOR`,`REQUIRED_FIELD_WARNING_MESSAGE`,`TITLE_CSS`,`BASE_CONTAINER_ID`,`ADD_CAPTION`,`ENTITY_GROUP_ID`,`VIEW_ID`) values (56,NULL,'Application',113,NULL,'*','indicates required fields.',NULL,3,'',1,NULL);
insert into `dyextn_container` (`IDENTIFIER`,`BUTTON_CSS`,`CAPTION`,`ABSTRACT_ENTITY_ID`,`MAIN_TABLE_CSS`,`REQUIRED_FIELD_INDICATOR`,`REQUIRED_FIELD_WARNING_MESSAGE`,`TITLE_CSS`,`BASE_CONTAINER_ID`,`ADD_CAPTION`,`ENTITY_GROUP_ID`,`VIEW_ID`) values (57,NULL,'LaboratoryProcedure',106,NULL,'*','indicates required fields.',NULL,NULL,'',1,NULL);
insert into `dyextn_container` (`IDENTIFIER`,`BUTTON_CSS`,`CAPTION`,`ABSTRACT_ENTITY_ID`,`MAIN_TABLE_CSS`,`REQUIRED_FIELD_INDICATOR`,`REQUIRED_FIELD_WARNING_MESSAGE`,`TITLE_CSS`,`BASE_CONTAINER_ID`,`ADD_CAPTION`,`ENTITY_GROUP_ID`,`VIEW_ID`) values (58,NULL,'LaboratoryProcedureDetails',116,NULL,'*','indicates required fields.',NULL,NULL,'',1,NULL);
insert into `dyextn_container` (`IDENTIFIER`,`BUTTON_CSS`,`CAPTION`,`ABSTRACT_ENTITY_ID`,`MAIN_TABLE_CSS`,`REQUIRED_FIELD_INDICATOR`,`REQUIRED_FIELD_WARNING_MESSAGE`,`TITLE_CSS`,`BASE_CONTAINER_ID`,`ADD_CAPTION`,`ENTITY_GROUP_ID`,`VIEW_ID`) values (59,NULL,'LaboratoryProcedureType',98,NULL,'*','indicates required fields.',NULL,3,'',1,NULL);
insert into `dyextn_container` (`IDENTIFIER`,`BUTTON_CSS`,`CAPTION`,`ABSTRACT_ENTITY_ID`,`MAIN_TABLE_CSS`,`REQUIRED_FIELD_INDICATOR`,`REQUIRED_FIELD_WARNING_MESSAGE`,`TITLE_CSS`,`BASE_CONTAINER_ID`,`ADD_CAPTION`,`ENTITY_GROUP_ID`,`VIEW_ID`) values (60,NULL,'LaboratoryResult',132,NULL,'*','indicates required fields.',NULL,NULL,'',1,NULL);

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

insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (1,'sourceUnits',NULL,NULL,'sourceUnits',2,NULL,2,174,'\0');
insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (2,'normalizedUnits',NULL,NULL,'normalizedUnits',1,NULL,2,173,'\0');
insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (3,'name',NULL,NULL,'name',2,NULL,3,10,'\0');
insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (4,'shortName',NULL,NULL,'shortName',1,NULL,3,9,'\0');
insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (5,'AssociationName_1',NULL,NULL,'AssociationName_1',2,NULL,5,143,'\0');
insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (6,'AssociationName_2',NULL,NULL,'AssociationName_2',1,NULL,5,142,'\0');
insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (7,'resultString',NULL,NULL,'resultString',5,NULL,6,140,'\0');
insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (8,'resultLow',NULL,NULL,'resultLow',4,NULL,6,139,'\0');
insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (9,'resultHigh',NULL,NULL,'resultHigh',3,NULL,6,138,'\0');
insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (10,'units',NULL,NULL,'units',2,NULL,6,137,'\0');
insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (11,'AssociationName_1',NULL,NULL,'AssociationName_1',1,NULL,6,136,'\0');
insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (12,'range',NULL,NULL,'range',3,NULL,7,96,'\0');
insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (13,'rangeLow',NULL,NULL,'rangeLow',2,NULL,7,95,'\0');
insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (14,'rangeHigh',NULL,NULL,'rangeHigh',1,NULL,7,94,'\0');
insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (15,'Number',NULL,NULL,'Number',3,NULL,8,127,'\0');
insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (16,'Type',NULL,NULL,'Type',2,NULL,8,126,'\0');
insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (17,'AssociationName_1',NULL,NULL,'AssociationName_1',1,NULL,8,125,'\0');
insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (18,'initials',NULL,NULL,'initials',1,NULL,9,109,'\0');
insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (19,'standardUnitOfMeasure',NULL,NULL,'standardUnitOfMeasure',1,NULL,11,150,'\0');
insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (20,'description',NULL,NULL,'description',6,NULL,16,277,'\0');
insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (21,'sequence',NULL,NULL,'sequence',5,NULL,16,276,'\0');
insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (22,'AssociationName_1',NULL,NULL,'AssociationName_1',4,NULL,16,275,'\0');
insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (23,'AssociationName_2',NULL,NULL,'AssociationName_2',3,NULL,16,274,'\0');
insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (24,'AssociationName_3',NULL,NULL,'AssociationName_3',2,NULL,16,273,'\0');
insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (25,'AssociationName_4',NULL,NULL,'AssociationName_4',1,NULL,16,270,'\0');
insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (26,'trialId',NULL,NULL,'trialId',7,NULL,19,213,'\0');
insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (27,'trailName',NULL,NULL,'trailName',6,NULL,19,212,'\0');
insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (28,'startTimeStamp',NULL,NULL,'startTimeStamp',5,NULL,19,211,'\0');
insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (29,'endTimeStamp',NULL,NULL,'endTimeStamp',4,NULL,19,210,'\0');
insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (30,'status',NULL,NULL,'status',3,NULL,19,209,'\0');
insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (31,'protocolId',NULL,NULL,'protocolId',2,NULL,19,208,'\0');
insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (32,'AssociationName_1',NULL,NULL,'AssociationName_1',1,NULL,19,207,'\0');
insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (33,'AssociationName_1',NULL,NULL,'AssociationName_1',2,NULL,27,56,'\0');
insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (34,'AssociationName_2',NULL,NULL,'AssociationName_2',1,NULL,27,55,'\0');
insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (35,'AssociationName_5',NULL,NULL,'AssociationName_5',18,NULL,29,80,'\0');
insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (36,'AssociationName_4',NULL,NULL,'AssociationName_4',17,NULL,29,64,'\0');
insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (37,'AssociationName_3',NULL,NULL,'AssociationName_3',16,NULL,29,59,'\0');
insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (38,'AssociationName_2',NULL,NULL,'AssociationName_2',15,NULL,29,58,'\0');
insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (39,'AssociationName_1',NULL,NULL,'AssociationName_1',14,NULL,29,51,'\0');
insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (40,'AssociationName_11',NULL,NULL,'AssociationName_11',1,NULL,29,27,'\0');
insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (41,'AssociationName_10',NULL,NULL,'AssociationName_10',2,NULL,29,30,'\0');
insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (42,'AssociationName_9',NULL,NULL,'AssociationName_9',3,NULL,29,33,'\0');
insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (43,'AssociationName_8',NULL,NULL,'AssociationName_8',4,NULL,29,36,'\0');
insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (44,'AssociationName_7',NULL,NULL,'AssociationName_7',5,NULL,29,39,'\0');
insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (45,'AssociationName_6',NULL,NULL,'AssociationName_6',6,NULL,29,42,'\0');
insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (46,'dateOfBirth',NULL,NULL,'dateOfBirth',7,NULL,29,44,'\0');
insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (47,'dateOfDeath',NULL,NULL,'dateOfDeath',8,NULL,29,45,'\0');
insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (48,'mothersMaidenName',NULL,NULL,'mothersMaidenName',9,NULL,29,46,'\0');
insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (49,'placeOfBirth',NULL,NULL,'placeOfBirth',10,NULL,29,47,'\0');
insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (50,'socialSecurityNumber',NULL,NULL,'socialSecurityNumber',11,NULL,29,48,'\0');
insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (51,'effectiveStartTimeStamp',NULL,NULL,'effectiveStartTimeStamp',12,NULL,29,49,'\0');
insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (52,'effectiveEndTimeStamp',NULL,NULL,'effectiveEndTimeStamp',13,NULL,29,50,'\0');
insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (53,'personUpi',NULL,NULL,'personUpi',2,NULL,33,82,'\0');
insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (54,'AssociationName_1',NULL,NULL,'AssociationName_1',1,NULL,33,81,'\0');
insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (55,'firstName',NULL,NULL,'firstName',7,NULL,34,20,'\0');
insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (56,'middleName',NULL,NULL,'middleName',6,NULL,34,19,'\0');
insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (57,'lastName',NULL,NULL,'lastName',5,NULL,34,18,'\0');
insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (58,'lastNameCompressed',NULL,NULL,'lastNameCompressed',4,NULL,34,17,'\0');
insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (59,'degree',NULL,NULL,'degree',3,NULL,34,16,'\0');
insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (60,'prefix',NULL,NULL,'prefix',2,NULL,34,15,'\0');
insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (61,'suffix',NULL,NULL,'suffix',1,NULL,34,14,'\0');
insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (62,'number',NULL,NULL,'number',2,NULL,35,62,'\0');
insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (63,'type',NULL,NULL,'type',1,NULL,35,61,'\0');
insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (64,'patientAccountNumber',NULL,NULL,'patientAccountNumber',5,NULL,38,294,'\0');
insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (65,'AssociationName_1',NULL,NULL,'AssociationName_1',4,NULL,38,293,'\0');
insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (66,'AssociationName_2',NULL,NULL,'AssociationName_2',3,NULL,38,292,'\0');
insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (67,'AssociationName_3',NULL,NULL,'AssociationName_3',2,NULL,38,291,'\0');
insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (68,'AssociationName_4',NULL,NULL,'AssociationName_4',1,NULL,38,290,'\0');
insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (69,'AssociationName_16',NULL,NULL,'AssociationName_16',40,NULL,39,282,'\0');
insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (70,'AssociationName_15',NULL,NULL,'AssociationName_15',39,NULL,39,279,'\0');
insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (71,'AssociationName_14',NULL,NULL,'AssociationName_14',38,NULL,39,268,'\0');
insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (72,'AssociationName_13',NULL,NULL,'AssociationName_13',37,NULL,39,265,'\0');
insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (73,'AssociationName_12',NULL,NULL,'AssociationName_12',36,NULL,39,262,'\0');
insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (74,'AssociationName_11',NULL,NULL,'AssociationName_11',35,NULL,39,261,'\0');
insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (75,'AssociationName_10',NULL,NULL,'AssociationName_10',34,NULL,39,260,'\0');
insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (76,'AssociationName_9',NULL,NULL,'AssociationName_9',33,NULL,39,259,'\0');
insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (77,'AssociationName_8',NULL,NULL,'AssociationName_8',32,NULL,39,258,'\0');
insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (78,'AssociationName_7',NULL,NULL,'AssociationName_7',31,NULL,39,257,'\0');
insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (79,'AssociationName_6',NULL,NULL,'AssociationName_6',30,NULL,39,256,'\0');
insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (80,'AssociationName_5',NULL,NULL,'AssociationName_5',29,NULL,39,255,'\0');
insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (81,'effectingStartTimeStamp',NULL,NULL,'effectingStartTimeStamp',28,NULL,39,253,'\0');
insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (82,'effectingEndTimeStamp',NULL,NULL,'effectingEndTimeStamp',27,NULL,39,252,'\0');
insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (83,'ageAtEncounter',NULL,NULL,'ageAtEncounter',26,NULL,39,251,'\0');
insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (84,'AssociationName_20',NULL,NULL,'AssociationName_20',13,NULL,39,232,'\0');
insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (85,'AssociationName_21',NULL,NULL,'AssociationName_21',12,NULL,39,220,'\0');
insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (86,'AssociationName_22',NULL,NULL,'AssociationName_22',11,NULL,39,219,'\0');
insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (87,'AssociationName_23',NULL,NULL,'AssociationName_23',10,NULL,39,218,'\0');
insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (88,'AssociationName_24',NULL,NULL,'AssociationName_24',9,NULL,39,215,'\0');
insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (89,'AssociationName_25',NULL,NULL,'AssociationName_25',8,NULL,39,205,'\0');
insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (90,'AssociationName_26',NULL,NULL,'AssociationName_26',7,NULL,39,204,'\0');
insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (91,'AssociationName_27',NULL,NULL,'AssociationName_27',6,NULL,39,190,'\0');
insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (92,'AssociationName_28',NULL,NULL,'AssociationName_28',5,NULL,39,187,'\0');
insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (93,'AssociationName_29',NULL,NULL,'AssociationName_29',4,NULL,39,186,'\0');
insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (94,'AssociationName_30',NULL,NULL,'AssociationName_30',3,NULL,39,185,'\0');
insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (95,'AssociationName_31',NULL,NULL,'AssociationName_31',2,NULL,39,184,'\0');
insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (96,'AssociationName_32',NULL,NULL,'AssociationName_32',1,NULL,39,183,'\0');
insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (97,'AssociationName_19',NULL,NULL,'AssociationName_19',14,NULL,39,235,'\0');
insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (98,'AssociationName_18',NULL,NULL,'AssociationName_18',15,NULL,39,238,'\0');
insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (99,'AssociationName_17',NULL,NULL,'AssociationName_17',16,NULL,39,241,'\0');
insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (100,'AssociationName_4',NULL,NULL,'AssociationName_4',17,NULL,39,242,'\0');
insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (101,'AssociationName_3',NULL,NULL,'AssociationName_3',18,NULL,39,243,'\0');
insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (102,'AssociationName_2',NULL,NULL,'AssociationName_2',19,NULL,39,244,'\0');
insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (103,'AssociationName_1',NULL,NULL,'AssociationName_1',20,NULL,39,245,'\0');
insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (104,'admittingService',NULL,NULL,'admittingService',21,NULL,39,246,'\0');
insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (105,'dischargeService',NULL,NULL,'dischargeService',22,NULL,39,247,'\0');
insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (106,'teachingTeam',NULL,NULL,'teachingTeam',23,NULL,39,248,'\0');
insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (107,'dischargeTimeStamp',NULL,NULL,'dischargeTimeStamp',24,NULL,39,249,'\0');
insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (108,'registrationTimeStamp',NULL,NULL,'registrationTimeStamp',25,NULL,39,250,'\0');
insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (109,'sequence',NULL,NULL,'sequence',11,NULL,45,202,'\0');
insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (110,'planId',NULL,NULL,'planId',10,NULL,45,201,'\0');
insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (111,'companyId',NULL,NULL,'companyId',9,NULL,45,200,'\0');
insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (112,'groupNumber',NULL,NULL,'groupNumber',8,NULL,45,199,'\0');
insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (113,'groupName',NULL,NULL,'groupName',7,NULL,45,198,'\0');
insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (114,'insuredGroupEmpId',NULL,NULL,'insuredGroupEmpId',6,NULL,45,197,'\0');
insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (115,'insuredGroupEmpName',NULL,NULL,'insuredGroupEmpName',5,NULL,45,196,'\0');
insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (116,'policyName',NULL,NULL,'policyName',4,NULL,45,195,'\0');
insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (117,'preAdmitCert',NULL,NULL,'preAdmitCert',3,NULL,45,194,'\0');
insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (118,'AssociationName_1',NULL,NULL,'AssociationName_1',2,NULL,45,193,'\0');
insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (119,'AssociationName_2',NULL,NULL,'AssociationName_2',1,NULL,45,192,'\0');
insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (120,'location',NULL,NULL,'location',3,NULL,48,5,'\0');
insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (121,'room',NULL,NULL,'room',2,NULL,48,4,'\0');
insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (122,'bed',NULL,NULL,'bed',1,NULL,48,3,'\0');
insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (123,'date',NULL,NULL,'date',7,NULL,50,230,'\0');
insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (124,'sequence',NULL,NULL,'sequence',6,NULL,50,229,'\0');
insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (125,'AssociationName_1',NULL,NULL,'AssociationName_1',5,NULL,50,228,'\0');
insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (126,'AssociationName_2',NULL,NULL,'AssociationName_2',4,NULL,50,227,'\0');
insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (127,'AssociationName_3',NULL,NULL,'AssociationName_3',3,NULL,50,226,'\0');
insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (128,'AssociationName_4',NULL,NULL,'AssociationName_4',2,NULL,50,223,'\0');
insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (129,'AssociationName_5',NULL,NULL,'AssociationName_5',1,NULL,50,222,'\0');
insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (130,'facilityProviderId',NULL,NULL,'facilityProviderId',4,NULL,52,85,'\0');
insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (131,'AssociationName_1',NULL,NULL,'AssociationName_1',3,NULL,52,84,'\0');
insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (132,'AssociationName_2',NULL,NULL,'AssociationName_2',2,NULL,52,22,'\0');
insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (133,'AssociationName_3',NULL,NULL,'AssociationName_3',1,NULL,52,12,'\0');
insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (134,'accessionNumber',NULL,NULL,'accessionNumber',8,NULL,57,170,'\0');
insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (135,'patientAccountNumber',NULL,NULL,'patientAccountNumber',7,NULL,57,169,'\0');
insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (136,'procedureSynonym',NULL,NULL,'procedureSynonym',6,NULL,57,168,'\0');
insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (137,'AssociationName_1',NULL,NULL,'AssociationName_1',5,NULL,57,167,'\0');
insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (138,'AssociationName_2',NULL,NULL,'AssociationName_2',4,NULL,57,115,'\0');
insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (139,'AssociationName_3',NULL,NULL,'AssociationName_3',3,NULL,57,112,'\0');
insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (140,'AssociationName_4',NULL,NULL,'AssociationName_4',2,NULL,57,111,'\0');
insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (141,'AssociationName_5',NULL,NULL,'AssociationName_5',1,NULL,57,107,'\0');
insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (142,'AssociationName_8',NULL,NULL,'AssociationName_8',1,NULL,58,117,'\0');
insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (143,'effectiveStartTimeStamp',NULL,NULL,'effectiveStartTimeStamp',2,NULL,58,119,'\0');
insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (144,'effectiveEndTimeStamp',NULL,NULL,'effectiveEndTimeStamp',3,NULL,58,120,'\0');
insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (145,'ageAtProcedure',NULL,NULL,'ageAtProcedure',4,NULL,58,121,'\0');
insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (146,'procedureComment',NULL,NULL,'procedureComment',5,NULL,58,122,'\0');
insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (147,'procedureId',NULL,NULL,'procedureId',6,NULL,58,123,'\0');
insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (148,'AssociationName_1',NULL,NULL,'AssociationName_1',7,NULL,58,129,'\0');
insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (149,'AssociationName_2',NULL,NULL,'AssociationName_2',8,NULL,58,130,'\0');
insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (150,'AssociationName_3',NULL,NULL,'AssociationName_3',9,NULL,58,131,'\0');
insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (151,'AssociationName_4',NULL,NULL,'AssociationName_4',10,NULL,58,157,'\0');
insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (152,'AssociationName_5',NULL,NULL,'AssociationName_5',11,NULL,58,160,'\0');
insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (153,'AssociationName_6',NULL,NULL,'AssociationName_6',12,NULL,58,165,'\0');
insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (154,'AssociationName_7',NULL,NULL,'AssociationName_7',13,NULL,58,166,'\0');
insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (155,'resultComments',NULL,NULL,'resultComments',7,NULL,60,155,'\0');
insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (156,'testSynonym',NULL,NULL,'testSynonym',6,NULL,60,154,'\0');
insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (157,'resultTimeStamp',NULL,NULL,'resultTimeStamp',5,NULL,60,153,'\0');
insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (158,'AssociationName_1',NULL,NULL,'AssociationName_1',4,NULL,60,152,'\0');
insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (159,'AssociationName_2',NULL,NULL,'AssociationName_2',3,NULL,60,148,'\0');
insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (160,'AssociationName_3',NULL,NULL,'AssociationName_3',2,NULL,60,145,'\0');
insert into `dyextn_control` (`IDENTIFIER`,`CAPTION`,`CSS_CLASS`,`HIDDEN`,`NAME`,`SEQUENCE_NUMBER`,`TOOLTIP`,`CONTAINER_ID`,`BASE_ABST_ATR_ID`,`READ_ONLY`) values (161,'AssociationName_4',NULL,NULL,'AssociationName_4',1,NULL,60,133,'\0');

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

insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (1,'DE_E_322');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (2,'DE_AT_328');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (3,'DE_AT_327');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (4,'DE_AT_326');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (5,'IDENTIFIER');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (6,'DE_E_317');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (7,'DE_E_13');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (8,'DE_AT_18');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (9,'DE_AT_17');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (10,'IDENTIFIER');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (11,'DE_AS_769');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (12,'DE_E_126');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (13,'DE_AT_136');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (14,'DE_AT_135');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (15,'DE_AT_134');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (16,'DE_AT_133');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (17,'DE_AT_132');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (18,'DE_AT_131');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (19,'DE_AT_130');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (20,'IDENTIFIER');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (21,'DE_AS_764');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (22,'DE_E_144');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (23,'DE_E_167');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (24,'DE_E_282');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (25,'IDENTIFIER');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (26,'DE_AS_719');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (27,'DE_E_231');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (28,'IDENTIFIER');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (29,'DE_AS_619');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (30,'DE_E_213');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (31,'IDENTIFIER');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (32,'DE_AS_604');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (33,'DE_E_205');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (34,'IDENTIFIER');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (35,'DE_AS_594');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (36,'DE_E_184');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (37,'IDENTIFIER');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (38,'DE_AS_564');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (39,'DE_E_110');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (40,'IDENTIFIER');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (41,'DE_AS_544');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (42,'IDENTIFIER');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (43,'DE_AT_171');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (44,'DE_AT_172');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (45,'DE_AT_173');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (46,'DE_AT_174');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (47,'DE_AT_175');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (48,'DE_AT_176');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (49,'DE_AT_177');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (50,'DE_AS_519');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (51,'DE_E_157');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (52,'DE_E_297');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (53,'IDENTIFIER');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (54,'DE_AS_734');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (55,'DE_AS_509');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (56,'IDENTIFIER');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (57,'DE_AS_524');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (58,'DE_AS_529');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (59,'DE_E_161');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (60,'DE_AT_166');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (61,'DE_AT_165');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (62,'IDENTIFIER');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (63,'DE_AS_534');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (64,'DE_E_23');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (65,'DE_E_309');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (66,'IDENTIFIER');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (67,'DE_AS_744');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (68,'DE_E_245');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (69,'IDENTIFIER');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (70,'DE_AS_669');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (71,'DE_E_209');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (72,'IDENTIFIER');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (73,'DE_AS_599');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (74,'DE_AT_30');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (75,'DE_AT_29');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (76,'DE_AT_28');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (77,'DE_AT_27');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (78,'IDENTIFIER');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (79,'DE_AS_539');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (80,'DE_AS_514');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (81,'DE_AT_148');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (82,'IDENTIFIER');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (83,'DE_AS_759');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (84,'DE_AT_321');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (85,'IDENTIFIER');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (86,'DE_E_313');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (87,'IDENTIFIER');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (88,'DE_E_305');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (89,'IDENTIFIER');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (90,'DE_E_301');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (91,'IDENTIFIER');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (92,'DE_E_290');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (93,'DE_AT_296');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (94,'DE_AT_295');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (95,'DE_AT_294');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (96,'IDENTIFIER');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (97,'DE_E_286');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (98,'IDENTIFIER');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (99,'DE_E_278');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (100,'IDENTIFIER');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (101,'DE_E_274');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (102,'IDENTIFIER');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (103,'DE_E_270');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (104,'IDENTIFIER');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (105,'DE_E_137');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (106,'DE_AS_724');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (107,'DE_E_235');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (108,'DE_AT_239');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (109,'IDENTIFIER');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (110,'DE_AS_644');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (111,'DE_AS_494');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (112,'DE_E_122');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (113,'IDENTIFIER');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (114,'DE_AS_489');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (115,'DE_E_101');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (116,'DE_AS_704');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (117,'IDENTIFIER');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (118,'DE_AT_105');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (119,'DE_AT_106');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (120,'DE_AT_107');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (121,'DE_AT_108');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (122,'DE_AT_109');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (123,'DE_E_73');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (124,'DE_AS_629');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (125,'DE_AT_78');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (126,'DE_AT_77');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (127,'IDENTIFIER');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (128,'DE_AS_444');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (129,'DE_AS_449');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (130,'DE_AS_454');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (131,'DE_E_58');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (132,'DE_AS_709');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (133,'DE_E_118');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (134,'DE_E_83');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (135,'DE_AS_729');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (136,'DE_AT_90');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (137,'DE_AT_89');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (138,'DE_AT_88');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (139,'DE_AT_87');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (140,'IDENTIFIER');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (141,'DE_AS_479');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (142,'DE_AS_474');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (143,'IDENTIFIER');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (144,'DE_AS_469');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (145,'DE_E_45');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (146,'IDENTIFIER');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (147,'DE_AS_424');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (148,'DE_E_53');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (149,'DE_AT_57');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (150,'IDENTIFIER');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (151,'DE_AS_419');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (152,'DE_AT_64');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (153,'DE_AT_63');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (154,'DE_AT_62');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (155,'IDENTIFIER');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (156,'DE_AS_459');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (157,'DE_E_114');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (158,'IDENTIFIER');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (159,'DE_AS_464');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (160,'DE_E_196');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (161,'DE_AS_634');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (162,'DE_AT_200');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (163,'IDENTIFIER');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (164,'DE_AS_574');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (165,'DE_AS_639');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (166,'DE_AS_484');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (167,'DE_AT_143');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (168,'DE_AT_142');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (169,'DE_AT_141');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (170,'IDENTIFIER');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (171,'DE_E_95');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (172,'DE_AT_100');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (173,'DE_AT_99');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (174,'IDENTIFIER');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (175,'DE_E_266');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (176,'IDENTIFIER');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (177,'DE_E_91');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (178,'IDENTIFIER');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (179,'DE_E_79');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (180,'IDENTIFIER');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (181,'DE_E_1');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (182,'DE_AS_774');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (183,'DE_AS_749');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (184,'DE_AS_739');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (185,'DE_AS_714');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (186,'DE_AS_699');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (187,'DE_E_262');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (188,'IDENTIFIER');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (189,'DE_AS_689');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (190,'DE_E_249');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (191,'DE_AS_684');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (192,'DE_AS_679');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (193,'DE_AT_261');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (194,'DE_AT_260');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (195,'DE_AT_259');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (196,'DE_AT_258');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (197,'DE_AT_257');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (198,'DE_AT_256');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (199,'DE_AT_255');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (200,'DE_AT_254');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (201,'DE_AT_253');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (202,'IDENTIFIER');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (203,'DE_AS_674');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (204,'DE_AS_624');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (205,'DE_E_217');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (206,'DE_AS_614');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (207,'DE_AT_226');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (208,'DE_AT_225');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (209,'DE_AT_224');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (210,'DE_AT_223');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (211,'DE_AT_222');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (212,'DE_AT_221');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (213,'IDENTIFIER');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (214,'DE_AS_609');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (215,'DE_E_201');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (216,'IDENTIFIER');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (217,'DE_AS_589');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (218,'DE_AS_584');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (219,'DE_AS_579');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (220,'DE_E_178');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (221,'DE_AS_754');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (222,'DE_AS_694');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (223,'DE_E_192');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (224,'IDENTIFIER');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (225,'DE_AS_569');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (226,'DE_AS_559');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (227,'DE_AS_554');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (228,'DE_AT_183');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (229,'DE_AT_182');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (230,'IDENTIFIER');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (231,'DE_AS_549');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (232,'DE_E_153');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (233,'IDENTIFIER');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (234,'DE_AS_504');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (235,'DE_E_149');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (236,'IDENTIFIER');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (237,'DE_AS_499');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (238,'DE_E_69');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (239,'IDENTIFIER');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (240,'DE_AS_434');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (241,'DE_AS_344');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (242,'DE_AS_339');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (243,'DE_AS_334');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (244,'DE_AS_329');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (245,'DE_AT_12');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (246,'DE_AT_11');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (247,'DE_AT_10');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (248,'DE_AT_9');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (249,'DE_AT_8');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (250,'DE_AT_7');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (251,'DE_AT_6');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (252,'DE_AT_5');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (253,'IDENTIFIER');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (254,'DE_AS_349');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (255,'DE_AS_354');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (256,'DE_AS_359');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (257,'DE_AS_364');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (258,'DE_AS_369');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (259,'DE_AS_374');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (260,'DE_AS_379');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (261,'DE_AS_384');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (262,'DE_E_31');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (263,'IDENTIFIER');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (264,'DE_AS_389');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (265,'DE_E_35');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (266,'IDENTIFIER');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (267,'DE_AS_394');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (268,'DE_E_39');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (269,'DE_AS_439');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (270,'DE_E_65');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (271,'IDENTIFIER');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (272,'DE_AS_429');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (273,'DE_AS_409');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (274,'DE_AS_404');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (275,'DE_AT_44');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (276,'DE_AT_43');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (277,'IDENTIFIER');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (278,'DE_AS_399');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (279,'DE_E_49');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (280,'IDENTIFIER');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (281,'DE_AS_414');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (282,'DE_E_19');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (283,'IDENTIFIER');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (284,'DE_E_188');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (285,'IDENTIFIER');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (286,'DE_E_227');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (287,'IDENTIFIER');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (288,'DE_E_240');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (289,'DE_AS_664');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (290,'DE_AS_659');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (291,'DE_AS_654');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (292,'DE_AS_649');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (293,'DE_AT_244');
insert into `dyextn_database_properties` (`IDENTIFIER`,`NAME`) values (294,'IDENTIFIER');

/*Table structure for table `dyextn_date_concept_value` */

CREATE TABLE `dyextn_date_concept_value` (
  `IDENTIFIER` bigint(20) NOT NULL,
  `VALUE` datetime default NULL,
  PRIMARY KEY  (`IDENTIFIER`),
  KEY `FK45F598A64641D513` (`IDENTIFIER`),
  CONSTRAINT `FK45F598A64641D513` FOREIGN KEY (`IDENTIFIER`) REFERENCES `dyextn_permissible_value` (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `dyextn_date_concept_value` */

insert into `dyextn_date_concept_value` (`IDENTIFIER`,`VALUE`) values (13,NULL);
insert into `dyextn_date_concept_value` (`IDENTIFIER`,`VALUE`) values (14,NULL);
insert into `dyextn_date_concept_value` (`IDENTIFIER`,`VALUE`) values (18,NULL);
insert into `dyextn_date_concept_value` (`IDENTIFIER`,`VALUE`) values (19,NULL);
insert into `dyextn_date_concept_value` (`IDENTIFIER`,`VALUE`) values (30,NULL);
insert into `dyextn_date_concept_value` (`IDENTIFIER`,`VALUE`) values (31,NULL);
insert into `dyextn_date_concept_value` (`IDENTIFIER`,`VALUE`) values (39,NULL);
insert into `dyextn_date_concept_value` (`IDENTIFIER`,`VALUE`) values (58,NULL);
insert into `dyextn_date_concept_value` (`IDENTIFIER`,`VALUE`) values (59,NULL);
insert into `dyextn_date_concept_value` (`IDENTIFIER`,`VALUE`) values (62,NULL);
insert into `dyextn_date_concept_value` (`IDENTIFIER`,`VALUE`) values (66,NULL);
insert into `dyextn_date_concept_value` (`IDENTIFIER`,`VALUE`) values (67,NULL);
insert into `dyextn_date_concept_value` (`IDENTIFIER`,`VALUE`) values (68,NULL);
insert into `dyextn_date_concept_value` (`IDENTIFIER`,`VALUE`) values (69,NULL);

/*Table structure for table `dyextn_date_type_info` */

CREATE TABLE `dyextn_date_type_info` (
  `IDENTIFIER` bigint(20) NOT NULL,
  `FORMAT` varchar(255) default NULL,
  PRIMARY KEY  (`IDENTIFIER`),
  KEY `FKFBA549FE5294FA3` (`IDENTIFIER`),
  CONSTRAINT `FKFBA549FE5294FA3` FOREIGN KEY (`IDENTIFIER`) REFERENCES `dyextn_attribute_type_info` (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `dyextn_date_type_info` */

insert into `dyextn_date_type_info` (`IDENTIFIER`,`FORMAT`) values (23,'MM-dd-yyyy');
insert into `dyextn_date_type_info` (`IDENTIFIER`,`FORMAT`) values (24,'MM-dd-yyyy');
insert into `dyextn_date_type_info` (`IDENTIFIER`,`FORMAT`) values (28,'MM-dd-yyyy');
insert into `dyextn_date_type_info` (`IDENTIFIER`,`FORMAT`) values (29,'MM-dd-yyyy');
insert into `dyextn_date_type_info` (`IDENTIFIER`,`FORMAT`) values (62,'MM-dd-yyyy');
insert into `dyextn_date_type_info` (`IDENTIFIER`,`FORMAT`) values (63,'MM-dd-yyyy');
insert into `dyextn_date_type_info` (`IDENTIFIER`,`FORMAT`) values (79,'MM-dd-yyyy');
insert into `dyextn_date_type_info` (`IDENTIFIER`,`FORMAT`) values (109,'MM-dd-yyyy');
insert into `dyextn_date_type_info` (`IDENTIFIER`,`FORMAT`) values (110,'MM-dd-yyyy');
insert into `dyextn_date_type_info` (`IDENTIFIER`,`FORMAT`) values (117,'MM-dd-yyyy');
insert into `dyextn_date_type_info` (`IDENTIFIER`,`FORMAT`) values (125,'MM-dd-yyyy');
insert into `dyextn_date_type_info` (`IDENTIFIER`,`FORMAT`) values (126,'MM-dd-yyyy');
insert into `dyextn_date_type_info` (`IDENTIFIER`,`FORMAT`) values (128,'MM-dd-yyyy');
insert into `dyextn_date_type_info` (`IDENTIFIER`,`FORMAT`) values (129,'MM-dd-yyyy');

/*Table structure for table `dyextn_datepicker` */

CREATE TABLE `dyextn_datepicker` (
  `IDENTIFIER` bigint(20) NOT NULL,
  `DATE_VALUE_TYPE` varchar(255) default NULL,
  PRIMARY KEY  (`IDENTIFIER`),
  KEY `FKFEADD19940F198C2` (`IDENTIFIER`),
  CONSTRAINT `FKFEADD19940F198C2` FOREIGN KEY (`IDENTIFIER`) REFERENCES `dyextn_control` (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `dyextn_datepicker` */

insert into `dyextn_datepicker` (`IDENTIFIER`,`DATE_VALUE_TYPE`) values (28,NULL);
insert into `dyextn_datepicker` (`IDENTIFIER`,`DATE_VALUE_TYPE`) values (29,NULL);
insert into `dyextn_datepicker` (`IDENTIFIER`,`DATE_VALUE_TYPE`) values (46,NULL);
insert into `dyextn_datepicker` (`IDENTIFIER`,`DATE_VALUE_TYPE`) values (47,NULL);
insert into `dyextn_datepicker` (`IDENTIFIER`,`DATE_VALUE_TYPE`) values (51,NULL);
insert into `dyextn_datepicker` (`IDENTIFIER`,`DATE_VALUE_TYPE`) values (52,NULL);
insert into `dyextn_datepicker` (`IDENTIFIER`,`DATE_VALUE_TYPE`) values (81,NULL);
insert into `dyextn_datepicker` (`IDENTIFIER`,`DATE_VALUE_TYPE`) values (82,NULL);
insert into `dyextn_datepicker` (`IDENTIFIER`,`DATE_VALUE_TYPE`) values (107,NULL);
insert into `dyextn_datepicker` (`IDENTIFIER`,`DATE_VALUE_TYPE`) values (108,NULL);
insert into `dyextn_datepicker` (`IDENTIFIER`,`DATE_VALUE_TYPE`) values (123,NULL);
insert into `dyextn_datepicker` (`IDENTIFIER`,`DATE_VALUE_TYPE`) values (143,NULL);
insert into `dyextn_datepicker` (`IDENTIFIER`,`DATE_VALUE_TYPE`) values (144,NULL);
insert into `dyextn_datepicker` (`IDENTIFIER`,`DATE_VALUE_TYPE`) values (157,NULL);

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

insert into `dyextn_double_type_info` (`IDENTIFIER`) values (50);
insert into `dyextn_double_type_info` (`IDENTIFIER`) values (51);
insert into `dyextn_double_type_info` (`IDENTIFIER`) values (64);
insert into `dyextn_double_type_info` (`IDENTIFIER`) values (71);
insert into `dyextn_double_type_info` (`IDENTIFIER`) values (72);
insert into `dyextn_double_type_info` (`IDENTIFIER`) values (105);
insert into `dyextn_double_type_info` (`IDENTIFIER`) values (116);
insert into `dyextn_double_type_info` (`IDENTIFIER`) values (127);
insert into `dyextn_double_type_info` (`IDENTIFIER`) values (134);

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

insert into `dyextn_entity` (`IDENTIFIER`,`DATA_TABLE_STATE`,`ENTITY_GROUP_ID`,`IS_ABSTRACT`,`PARENT_ENTITY_ID`,`INHERITANCE_STRATEGY`,`DISCRIMINATOR_COLUMN_NAME`,`DISCRIMINATOR_VALUE`) values (2,2,1,'\0',NULL,3,NULL,NULL);
insert into `dyextn_entity` (`IDENTIFIER`,`DATA_TABLE_STATE`,`ENTITY_GROUP_ID`,`IS_ABSTRACT`,`PARENT_ENTITY_ID`,`INHERITANCE_STRATEGY`,`DISCRIMINATOR_COLUMN_NAME`,`DISCRIMINATOR_VALUE`) values (7,2,1,'\0',NULL,3,NULL,NULL);
insert into `dyextn_entity` (`IDENTIFIER`,`DATA_TABLE_STATE`,`ENTITY_GROUP_ID`,`IS_ABSTRACT`,`PARENT_ENTITY_ID`,`INHERITANCE_STRATEGY`,`DISCRIMINATOR_COLUMN_NAME`,`DISCRIMINATOR_VALUE`) values (8,2,1,'',NULL,3,NULL,NULL);
insert into `dyextn_entity` (`IDENTIFIER`,`DATA_TABLE_STATE`,`ENTITY_GROUP_ID`,`IS_ABSTRACT`,`PARENT_ENTITY_ID`,`INHERITANCE_STRATEGY`,`DISCRIMINATOR_COLUMN_NAME`,`DISCRIMINATOR_VALUE`) values (13,2,1,'\0',NULL,3,NULL,NULL);
insert into `dyextn_entity` (`IDENTIFIER`,`DATA_TABLE_STATE`,`ENTITY_GROUP_ID`,`IS_ABSTRACT`,`PARENT_ENTITY_ID`,`INHERITANCE_STRATEGY`,`DISCRIMINATOR_COLUMN_NAME`,`DISCRIMINATOR_VALUE`) values (23,2,1,'\0',NULL,3,NULL,NULL);
insert into `dyextn_entity` (`IDENTIFIER`,`DATA_TABLE_STATE`,`ENTITY_GROUP_ID`,`IS_ABSTRACT`,`PARENT_ENTITY_ID`,`INHERITANCE_STRATEGY`,`DISCRIMINATOR_COLUMN_NAME`,`DISCRIMINATOR_VALUE`) values (24,2,1,'\0',NULL,3,NULL,NULL);
insert into `dyextn_entity` (`IDENTIFIER`,`DATA_TABLE_STATE`,`ENTITY_GROUP_ID`,`IS_ABSTRACT`,`PARENT_ENTITY_ID`,`INHERITANCE_STRATEGY`,`DISCRIMINATOR_COLUMN_NAME`,`DISCRIMINATOR_VALUE`) values (25,2,1,'\0',8,3,NULL,NULL);
insert into `dyextn_entity` (`IDENTIFIER`,`DATA_TABLE_STATE`,`ENTITY_GROUP_ID`,`IS_ABSTRACT`,`PARENT_ENTITY_ID`,`INHERITANCE_STRATEGY`,`DISCRIMINATOR_COLUMN_NAME`,`DISCRIMINATOR_VALUE`) values (28,2,1,'\0',8,3,NULL,NULL);
insert into `dyextn_entity` (`IDENTIFIER`,`DATA_TABLE_STATE`,`ENTITY_GROUP_ID`,`IS_ABSTRACT`,`PARENT_ENTITY_ID`,`INHERITANCE_STRATEGY`,`DISCRIMINATOR_COLUMN_NAME`,`DISCRIMINATOR_VALUE`) values (31,2,1,'\0',8,3,NULL,NULL);
insert into `dyextn_entity` (`IDENTIFIER`,`DATA_TABLE_STATE`,`ENTITY_GROUP_ID`,`IS_ABSTRACT`,`PARENT_ENTITY_ID`,`INHERITANCE_STRATEGY`,`DISCRIMINATOR_COLUMN_NAME`,`DISCRIMINATOR_VALUE`) values (34,2,1,'\0',8,3,NULL,NULL);
insert into `dyextn_entity` (`IDENTIFIER`,`DATA_TABLE_STATE`,`ENTITY_GROUP_ID`,`IS_ABSTRACT`,`PARENT_ENTITY_ID`,`INHERITANCE_STRATEGY`,`DISCRIMINATOR_COLUMN_NAME`,`DISCRIMINATOR_VALUE`) values (37,2,1,'\0',8,3,NULL,NULL);
insert into `dyextn_entity` (`IDENTIFIER`,`DATA_TABLE_STATE`,`ENTITY_GROUP_ID`,`IS_ABSTRACT`,`PARENT_ENTITY_ID`,`INHERITANCE_STRATEGY`,`DISCRIMINATOR_COLUMN_NAME`,`DISCRIMINATOR_VALUE`) values (40,2,1,'\0',8,3,NULL,NULL);
insert into `dyextn_entity` (`IDENTIFIER`,`DATA_TABLE_STATE`,`ENTITY_GROUP_ID`,`IS_ABSTRACT`,`PARENT_ENTITY_ID`,`INHERITANCE_STRATEGY`,`DISCRIMINATOR_COLUMN_NAME`,`DISCRIMINATOR_VALUE`) values (52,2,1,'\0',NULL,3,NULL,NULL);
insert into `dyextn_entity` (`IDENTIFIER`,`DATA_TABLE_STATE`,`ENTITY_GROUP_ID`,`IS_ABSTRACT`,`PARENT_ENTITY_ID`,`INHERITANCE_STRATEGY`,`DISCRIMINATOR_COLUMN_NAME`,`DISCRIMINATOR_VALUE`) values (53,2,1,'\0',8,3,NULL,NULL);
insert into `dyextn_entity` (`IDENTIFIER`,`DATA_TABLE_STATE`,`ENTITY_GROUP_ID`,`IS_ABSTRACT`,`PARENT_ENTITY_ID`,`INHERITANCE_STRATEGY`,`DISCRIMINATOR_COLUMN_NAME`,`DISCRIMINATOR_VALUE`) values (60,2,1,'\0',NULL,3,NULL,NULL);
insert into `dyextn_entity` (`IDENTIFIER`,`DATA_TABLE_STATE`,`ENTITY_GROUP_ID`,`IS_ABSTRACT`,`PARENT_ENTITY_ID`,`INHERITANCE_STRATEGY`,`DISCRIMINATOR_COLUMN_NAME`,`DISCRIMINATOR_VALUE`) values (65,2,1,'\0',NULL,3,NULL,NULL);
insert into `dyextn_entity` (`IDENTIFIER`,`DATA_TABLE_STATE`,`ENTITY_GROUP_ID`,`IS_ABSTRACT`,`PARENT_ENTITY_ID`,`INHERITANCE_STRATEGY`,`DISCRIMINATOR_COLUMN_NAME`,`DISCRIMINATOR_VALUE`) values (66,2,1,'\0',8,3,NULL,NULL);
insert into `dyextn_entity` (`IDENTIFIER`,`DATA_TABLE_STATE`,`ENTITY_GROUP_ID`,`IS_ABSTRACT`,`PARENT_ENTITY_ID`,`INHERITANCE_STRATEGY`,`DISCRIMINATOR_COLUMN_NAME`,`DISCRIMINATOR_VALUE`) values (69,2,1,'\0',8,3,NULL,NULL);
insert into `dyextn_entity` (`IDENTIFIER`,`DATA_TABLE_STATE`,`ENTITY_GROUP_ID`,`IS_ABSTRACT`,`PARENT_ENTITY_ID`,`INHERITANCE_STRATEGY`,`DISCRIMINATOR_COLUMN_NAME`,`DISCRIMINATOR_VALUE`) values (72,2,1,'\0',8,3,NULL,NULL);
insert into `dyextn_entity` (`IDENTIFIER`,`DATA_TABLE_STATE`,`ENTITY_GROUP_ID`,`IS_ABSTRACT`,`PARENT_ENTITY_ID`,`INHERITANCE_STRATEGY`,`DISCRIMINATOR_COLUMN_NAME`,`DISCRIMINATOR_VALUE`) values (87,2,1,'\0',NULL,3,NULL,NULL);
insert into `dyextn_entity` (`IDENTIFIER`,`DATA_TABLE_STATE`,`ENTITY_GROUP_ID`,`IS_ABSTRACT`,`PARENT_ENTITY_ID`,`INHERITANCE_STRATEGY`,`DISCRIMINATOR_COLUMN_NAME`,`DISCRIMINATOR_VALUE`) values (89,2,1,'\0',8,3,NULL,NULL);
insert into `dyextn_entity` (`IDENTIFIER`,`DATA_TABLE_STATE`,`ENTITY_GROUP_ID`,`IS_ABSTRACT`,`PARENT_ENTITY_ID`,`INHERITANCE_STRATEGY`,`DISCRIMINATOR_COLUMN_NAME`,`DISCRIMINATOR_VALUE`) values (91,2,1,'\0',NULL,3,NULL,NULL);
insert into `dyextn_entity` (`IDENTIFIER`,`DATA_TABLE_STATE`,`ENTITY_GROUP_ID`,`IS_ABSTRACT`,`PARENT_ENTITY_ID`,`INHERITANCE_STRATEGY`,`DISCRIMINATOR_COLUMN_NAME`,`DISCRIMINATOR_VALUE`) values (93,2,1,'\0',NULL,3,NULL,NULL);
insert into `dyextn_entity` (`IDENTIFIER`,`DATA_TABLE_STATE`,`ENTITY_GROUP_ID`,`IS_ABSTRACT`,`PARENT_ENTITY_ID`,`INHERITANCE_STRATEGY`,`DISCRIMINATOR_COLUMN_NAME`,`DISCRIMINATOR_VALUE`) values (98,2,1,'\0',8,3,NULL,NULL);
insert into `dyextn_entity` (`IDENTIFIER`,`DATA_TABLE_STATE`,`ENTITY_GROUP_ID`,`IS_ABSTRACT`,`PARENT_ENTITY_ID`,`INHERITANCE_STRATEGY`,`DISCRIMINATOR_COLUMN_NAME`,`DISCRIMINATOR_VALUE`) values (100,2,1,'\0',8,3,NULL,NULL);
insert into `dyextn_entity` (`IDENTIFIER`,`DATA_TABLE_STATE`,`ENTITY_GROUP_ID`,`IS_ABSTRACT`,`PARENT_ENTITY_ID`,`INHERITANCE_STRATEGY`,`DISCRIMINATOR_COLUMN_NAME`,`DISCRIMINATOR_VALUE`) values (102,2,1,'\0',8,3,NULL,NULL);
insert into `dyextn_entity` (`IDENTIFIER`,`DATA_TABLE_STATE`,`ENTITY_GROUP_ID`,`IS_ABSTRACT`,`PARENT_ENTITY_ID`,`INHERITANCE_STRATEGY`,`DISCRIMINATOR_COLUMN_NAME`,`DISCRIMINATOR_VALUE`) values (104,2,1,'\0',8,3,NULL,NULL);
insert into `dyextn_entity` (`IDENTIFIER`,`DATA_TABLE_STATE`,`ENTITY_GROUP_ID`,`IS_ABSTRACT`,`PARENT_ENTITY_ID`,`INHERITANCE_STRATEGY`,`DISCRIMINATOR_COLUMN_NAME`,`DISCRIMINATOR_VALUE`) values (106,2,1,'\0',NULL,3,NULL,NULL);
insert into `dyextn_entity` (`IDENTIFIER`,`DATA_TABLE_STATE`,`ENTITY_GROUP_ID`,`IS_ABSTRACT`,`PARENT_ENTITY_ID`,`INHERITANCE_STRATEGY`,`DISCRIMINATOR_COLUMN_NAME`,`DISCRIMINATOR_VALUE`) values (108,2,1,'\0',8,3,NULL,NULL);
insert into `dyextn_entity` (`IDENTIFIER`,`DATA_TABLE_STATE`,`ENTITY_GROUP_ID`,`IS_ABSTRACT`,`PARENT_ENTITY_ID`,`INHERITANCE_STRATEGY`,`DISCRIMINATOR_COLUMN_NAME`,`DISCRIMINATOR_VALUE`) values (113,2,1,'\0',8,3,NULL,NULL);
insert into `dyextn_entity` (`IDENTIFIER`,`DATA_TABLE_STATE`,`ENTITY_GROUP_ID`,`IS_ABSTRACT`,`PARENT_ENTITY_ID`,`INHERITANCE_STRATEGY`,`DISCRIMINATOR_COLUMN_NAME`,`DISCRIMINATOR_VALUE`) values (116,2,1,'\0',NULL,3,NULL,NULL);
insert into `dyextn_entity` (`IDENTIFIER`,`DATA_TABLE_STATE`,`ENTITY_GROUP_ID`,`IS_ABSTRACT`,`PARENT_ENTITY_ID`,`INHERITANCE_STRATEGY`,`DISCRIMINATOR_COLUMN_NAME`,`DISCRIMINATOR_VALUE`) values (124,2,1,'\0',NULL,3,NULL,NULL);
insert into `dyextn_entity` (`IDENTIFIER`,`DATA_TABLE_STATE`,`ENTITY_GROUP_ID`,`IS_ABSTRACT`,`PARENT_ENTITY_ID`,`INHERITANCE_STRATEGY`,`DISCRIMINATOR_COLUMN_NAME`,`DISCRIMINATOR_VALUE`) values (132,2,1,'\0',NULL,3,NULL,NULL);
insert into `dyextn_entity` (`IDENTIFIER`,`DATA_TABLE_STATE`,`ENTITY_GROUP_ID`,`IS_ABSTRACT`,`PARENT_ENTITY_ID`,`INHERITANCE_STRATEGY`,`DISCRIMINATOR_COLUMN_NAME`,`DISCRIMINATOR_VALUE`) values (134,2,1,'\0',NULL,3,NULL,NULL);
insert into `dyextn_entity` (`IDENTIFIER`,`DATA_TABLE_STATE`,`ENTITY_GROUP_ID`,`IS_ABSTRACT`,`PARENT_ENTITY_ID`,`INHERITANCE_STRATEGY`,`DISCRIMINATOR_COLUMN_NAME`,`DISCRIMINATOR_VALUE`) values (135,2,1,'\0',NULL,3,NULL,NULL);
insert into `dyextn_entity` (`IDENTIFIER`,`DATA_TABLE_STATE`,`ENTITY_GROUP_ID`,`IS_ABSTRACT`,`PARENT_ENTITY_ID`,`INHERITANCE_STRATEGY`,`DISCRIMINATOR_COLUMN_NAME`,`DISCRIMINATOR_VALUE`) values (146,2,1,'\0',8,3,NULL,NULL);
insert into `dyextn_entity` (`IDENTIFIER`,`DATA_TABLE_STATE`,`ENTITY_GROUP_ID`,`IS_ABSTRACT`,`PARENT_ENTITY_ID`,`INHERITANCE_STRATEGY`,`DISCRIMINATOR_COLUMN_NAME`,`DISCRIMINATOR_VALUE`) values (149,2,1,'\0',8,3,NULL,NULL);
insert into `dyextn_entity` (`IDENTIFIER`,`DATA_TABLE_STATE`,`ENTITY_GROUP_ID`,`IS_ABSTRACT`,`PARENT_ENTITY_ID`,`INHERITANCE_STRATEGY`,`DISCRIMINATOR_COLUMN_NAME`,`DISCRIMINATOR_VALUE`) values (158,2,1,'\0',8,3,NULL,NULL);
insert into `dyextn_entity` (`IDENTIFIER`,`DATA_TABLE_STATE`,`ENTITY_GROUP_ID`,`IS_ABSTRACT`,`PARENT_ENTITY_ID`,`INHERITANCE_STRATEGY`,`DISCRIMINATOR_COLUMN_NAME`,`DISCRIMINATOR_VALUE`) values (161,2,1,'\0',NULL,3,NULL,NULL);
insert into `dyextn_entity` (`IDENTIFIER`,`DATA_TABLE_STATE`,`ENTITY_GROUP_ID`,`IS_ABSTRACT`,`PARENT_ENTITY_ID`,`INHERITANCE_STRATEGY`,`DISCRIMINATOR_COLUMN_NAME`,`DISCRIMINATOR_VALUE`) values (172,2,1,'\0',NULL,3,NULL,NULL);
insert into `dyextn_entity` (`IDENTIFIER`,`DATA_TABLE_STATE`,`ENTITY_GROUP_ID`,`IS_ABSTRACT`,`PARENT_ENTITY_ID`,`INHERITANCE_STRATEGY`,`DISCRIMINATOR_COLUMN_NAME`,`DISCRIMINATOR_VALUE`) values (176,2,1,'\0',8,3,NULL,NULL);
insert into `dyextn_entity` (`IDENTIFIER`,`DATA_TABLE_STATE`,`ENTITY_GROUP_ID`,`IS_ABSTRACT`,`PARENT_ENTITY_ID`,`INHERITANCE_STRATEGY`,`DISCRIMINATOR_COLUMN_NAME`,`DISCRIMINATOR_VALUE`) values (178,2,1,'\0',176,3,NULL,NULL);
insert into `dyextn_entity` (`IDENTIFIER`,`DATA_TABLE_STATE`,`ENTITY_GROUP_ID`,`IS_ABSTRACT`,`PARENT_ENTITY_ID`,`INHERITANCE_STRATEGY`,`DISCRIMINATOR_COLUMN_NAME`,`DISCRIMINATOR_VALUE`) values (180,2,1,'\0',8,3,NULL,NULL);
insert into `dyextn_entity` (`IDENTIFIER`,`DATA_TABLE_STATE`,`ENTITY_GROUP_ID`,`IS_ABSTRACT`,`PARENT_ENTITY_ID`,`INHERITANCE_STRATEGY`,`DISCRIMINATOR_COLUMN_NAME`,`DISCRIMINATOR_VALUE`) values (182,2,1,'\0',NULL,3,NULL,NULL);
insert into `dyextn_entity` (`IDENTIFIER`,`DATA_TABLE_STATE`,`ENTITY_GROUP_ID`,`IS_ABSTRACT`,`PARENT_ENTITY_ID`,`INHERITANCE_STRATEGY`,`DISCRIMINATOR_COLUMN_NAME`,`DISCRIMINATOR_VALUE`) values (188,2,1,'\0',8,3,NULL,NULL);
insert into `dyextn_entity` (`IDENTIFIER`,`DATA_TABLE_STATE`,`ENTITY_GROUP_ID`,`IS_ABSTRACT`,`PARENT_ENTITY_ID`,`INHERITANCE_STRATEGY`,`DISCRIMINATOR_COLUMN_NAME`,`DISCRIMINATOR_VALUE`) values (191,2,1,'\0',NULL,3,NULL,NULL);
insert into `dyextn_entity` (`IDENTIFIER`,`DATA_TABLE_STATE`,`ENTITY_GROUP_ID`,`IS_ABSTRACT`,`PARENT_ENTITY_ID`,`INHERITANCE_STRATEGY`,`DISCRIMINATOR_COLUMN_NAME`,`DISCRIMINATOR_VALUE`) values (206,2,1,'\0',NULL,3,NULL,NULL);
insert into `dyextn_entity` (`IDENTIFIER`,`DATA_TABLE_STATE`,`ENTITY_GROUP_ID`,`IS_ABSTRACT`,`PARENT_ENTITY_ID`,`INHERITANCE_STRATEGY`,`DISCRIMINATOR_COLUMN_NAME`,`DISCRIMINATOR_VALUE`) values (216,2,1,'\0',8,3,NULL,NULL);
insert into `dyextn_entity` (`IDENTIFIER`,`DATA_TABLE_STATE`,`ENTITY_GROUP_ID`,`IS_ABSTRACT`,`PARENT_ENTITY_ID`,`INHERITANCE_STRATEGY`,`DISCRIMINATOR_COLUMN_NAME`,`DISCRIMINATOR_VALUE`) values (221,2,1,'\0',NULL,3,NULL,NULL);
insert into `dyextn_entity` (`IDENTIFIER`,`DATA_TABLE_STATE`,`ENTITY_GROUP_ID`,`IS_ABSTRACT`,`PARENT_ENTITY_ID`,`INHERITANCE_STRATEGY`,`DISCRIMINATOR_COLUMN_NAME`,`DISCRIMINATOR_VALUE`) values (224,2,1,'\0',8,3,NULL,NULL);
insert into `dyextn_entity` (`IDENTIFIER`,`DATA_TABLE_STATE`,`ENTITY_GROUP_ID`,`IS_ABSTRACT`,`PARENT_ENTITY_ID`,`INHERITANCE_STRATEGY`,`DISCRIMINATOR_COLUMN_NAME`,`DISCRIMINATOR_VALUE`) values (233,2,1,'\0',8,3,NULL,NULL);
insert into `dyextn_entity` (`IDENTIFIER`,`DATA_TABLE_STATE`,`ENTITY_GROUP_ID`,`IS_ABSTRACT`,`PARENT_ENTITY_ID`,`INHERITANCE_STRATEGY`,`DISCRIMINATOR_COLUMN_NAME`,`DISCRIMINATOR_VALUE`) values (236,2,1,'\0',8,3,NULL,NULL);
insert into `dyextn_entity` (`IDENTIFIER`,`DATA_TABLE_STATE`,`ENTITY_GROUP_ID`,`IS_ABSTRACT`,`PARENT_ENTITY_ID`,`INHERITANCE_STRATEGY`,`DISCRIMINATOR_COLUMN_NAME`,`DISCRIMINATOR_VALUE`) values (239,2,1,'\0',8,3,NULL,NULL);
insert into `dyextn_entity` (`IDENTIFIER`,`DATA_TABLE_STATE`,`ENTITY_GROUP_ID`,`IS_ABSTRACT`,`PARENT_ENTITY_ID`,`INHERITANCE_STRATEGY`,`DISCRIMINATOR_COLUMN_NAME`,`DISCRIMINATOR_VALUE`) values (263,2,1,'\0',8,3,NULL,NULL);
insert into `dyextn_entity` (`IDENTIFIER`,`DATA_TABLE_STATE`,`ENTITY_GROUP_ID`,`IS_ABSTRACT`,`PARENT_ENTITY_ID`,`INHERITANCE_STRATEGY`,`DISCRIMINATOR_COLUMN_NAME`,`DISCRIMINATOR_VALUE`) values (266,2,1,'\0',8,3,NULL,NULL);
insert into `dyextn_entity` (`IDENTIFIER`,`DATA_TABLE_STATE`,`ENTITY_GROUP_ID`,`IS_ABSTRACT`,`PARENT_ENTITY_ID`,`INHERITANCE_STRATEGY`,`DISCRIMINATOR_COLUMN_NAME`,`DISCRIMINATOR_VALUE`) values (269,2,1,'\0',NULL,3,NULL,NULL);
insert into `dyextn_entity` (`IDENTIFIER`,`DATA_TABLE_STATE`,`ENTITY_GROUP_ID`,`IS_ABSTRACT`,`PARENT_ENTITY_ID`,`INHERITANCE_STRATEGY`,`DISCRIMINATOR_COLUMN_NAME`,`DISCRIMINATOR_VALUE`) values (271,2,1,'\0',8,3,NULL,NULL);
insert into `dyextn_entity` (`IDENTIFIER`,`DATA_TABLE_STATE`,`ENTITY_GROUP_ID`,`IS_ABSTRACT`,`PARENT_ENTITY_ID`,`INHERITANCE_STRATEGY`,`DISCRIMINATOR_COLUMN_NAME`,`DISCRIMINATOR_VALUE`) values (280,2,1,'\0',8,3,NULL,NULL);
insert into `dyextn_entity` (`IDENTIFIER`,`DATA_TABLE_STATE`,`ENTITY_GROUP_ID`,`IS_ABSTRACT`,`PARENT_ENTITY_ID`,`INHERITANCE_STRATEGY`,`DISCRIMINATOR_COLUMN_NAME`,`DISCRIMINATOR_VALUE`) values (283,2,1,'\0',176,3,NULL,NULL);
insert into `dyextn_entity` (`IDENTIFIER`,`DATA_TABLE_STATE`,`ENTITY_GROUP_ID`,`IS_ABSTRACT`,`PARENT_ENTITY_ID`,`INHERITANCE_STRATEGY`,`DISCRIMINATOR_COLUMN_NAME`,`DISCRIMINATOR_VALUE`) values (285,2,1,'\0',NULL,3,NULL,NULL);
insert into `dyextn_entity` (`IDENTIFIER`,`DATA_TABLE_STATE`,`ENTITY_GROUP_ID`,`IS_ABSTRACT`,`PARENT_ENTITY_ID`,`INHERITANCE_STRATEGY`,`DISCRIMINATOR_COLUMN_NAME`,`DISCRIMINATOR_VALUE`) values (287,2,1,'\0',NULL,3,NULL,NULL);
insert into `dyextn_entity` (`IDENTIFIER`,`DATA_TABLE_STATE`,`ENTITY_GROUP_ID`,`IS_ABSTRACT`,`PARENT_ENTITY_ID`,`INHERITANCE_STRATEGY`,`DISCRIMINATOR_COLUMN_NAME`,`DISCRIMINATOR_VALUE`) values (289,2,1,'\0',NULL,3,NULL,NULL);

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

insert into `dyextn_long_type_info` (`IDENTIFIER`) values (4);
insert into `dyextn_long_type_info` (`IDENTIFIER`) values (7);
insert into `dyextn_long_type_info` (`IDENTIFIER`) values (15);
insert into `dyextn_long_type_info` (`IDENTIFIER`) values (16);
insert into `dyextn_long_type_info` (`IDENTIFIER`) values (17);
insert into `dyextn_long_type_info` (`IDENTIFIER`) values (18);
insert into `dyextn_long_type_info` (`IDENTIFIER`) values (19);
insert into `dyextn_long_type_info` (`IDENTIFIER`) values (20);
insert into `dyextn_long_type_info` (`IDENTIFIER`) values (21);
insert into `dyextn_long_type_info` (`IDENTIFIER`) values (22);
insert into `dyextn_long_type_info` (`IDENTIFIER`) values (30);
insert into `dyextn_long_type_info` (`IDENTIFIER`) values (31);
insert into `dyextn_long_type_info` (`IDENTIFIER`) values (34);
insert into `dyextn_long_type_info` (`IDENTIFIER`) values (35);
insert into `dyextn_long_type_info` (`IDENTIFIER`) values (36);
insert into `dyextn_long_type_info` (`IDENTIFIER`) values (37);
insert into `dyextn_long_type_info` (`IDENTIFIER`) values (42);
insert into `dyextn_long_type_info` (`IDENTIFIER`) values (44);
insert into `dyextn_long_type_info` (`IDENTIFIER`) values (46);
insert into `dyextn_long_type_info` (`IDENTIFIER`) values (47);
insert into `dyextn_long_type_info` (`IDENTIFIER`) values (48);
insert into `dyextn_long_type_info` (`IDENTIFIER`) values (49);
insert into `dyextn_long_type_info` (`IDENTIFIER`) values (53);
insert into `dyextn_long_type_info` (`IDENTIFIER`) values (54);
insert into `dyextn_long_type_info` (`IDENTIFIER`) values (55);
insert into `dyextn_long_type_info` (`IDENTIFIER`) values (56);
insert into `dyextn_long_type_info` (`IDENTIFIER`) values (57);
insert into `dyextn_long_type_info` (`IDENTIFIER`) values (59);
insert into `dyextn_long_type_info` (`IDENTIFIER`) values (60);
insert into `dyextn_long_type_info` (`IDENTIFIER`) values (61);
insert into `dyextn_long_type_info` (`IDENTIFIER`) values (69);
insert into `dyextn_long_type_info` (`IDENTIFIER`) values (74);
insert into `dyextn_long_type_info` (`IDENTIFIER`) values (75);
insert into `dyextn_long_type_info` (`IDENTIFIER`) values (76);
insert into `dyextn_long_type_info` (`IDENTIFIER`) values (78);
insert into `dyextn_long_type_info` (`IDENTIFIER`) values (82);
insert into `dyextn_long_type_info` (`IDENTIFIER`) values (83);
insert into `dyextn_long_type_info` (`IDENTIFIER`) values (85);
insert into `dyextn_long_type_info` (`IDENTIFIER`) values (89);
insert into `dyextn_long_type_info` (`IDENTIFIER`) values (92);
insert into `dyextn_long_type_info` (`IDENTIFIER`) values (93);
insert into `dyextn_long_type_info` (`IDENTIFIER`) values (94);
insert into `dyextn_long_type_info` (`IDENTIFIER`) values (95);
insert into `dyextn_long_type_info` (`IDENTIFIER`) values (96);
insert into `dyextn_long_type_info` (`IDENTIFIER`) values (106);
insert into `dyextn_long_type_info` (`IDENTIFIER`) values (113);
insert into `dyextn_long_type_info` (`IDENTIFIER`) values (114);
insert into `dyextn_long_type_info` (`IDENTIFIER`) values (115);
insert into `dyextn_long_type_info` (`IDENTIFIER`) values (118);
insert into `dyextn_long_type_info` (`IDENTIFIER`) values (119);
insert into `dyextn_long_type_info` (`IDENTIFIER`) values (120);
insert into `dyextn_long_type_info` (`IDENTIFIER`) values (121);
insert into `dyextn_long_type_info` (`IDENTIFIER`) values (130);
insert into `dyextn_long_type_info` (`IDENTIFIER`) values (131);
insert into `dyextn_long_type_info` (`IDENTIFIER`) values (132);
insert into `dyextn_long_type_info` (`IDENTIFIER`) values (133);
insert into `dyextn_long_type_info` (`IDENTIFIER`) values (136);
insert into `dyextn_long_type_info` (`IDENTIFIER`) values (137);
insert into `dyextn_long_type_info` (`IDENTIFIER`) values (138);
insert into `dyextn_long_type_info` (`IDENTIFIER`) values (139);
insert into `dyextn_long_type_info` (`IDENTIFIER`) values (140);
insert into `dyextn_long_type_info` (`IDENTIFIER`) values (142);

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

insert into `dyextn_numeric_type_info` (`IDENTIFIER`,`MEASUREMENT_UNITS`,`DECIMAL_PLACES`,`NO_DIGITS`) values (4,NULL,0,NULL);
insert into `dyextn_numeric_type_info` (`IDENTIFIER`,`MEASUREMENT_UNITS`,`DECIMAL_PLACES`,`NO_DIGITS`) values (7,NULL,0,NULL);
insert into `dyextn_numeric_type_info` (`IDENTIFIER`,`MEASUREMENT_UNITS`,`DECIMAL_PLACES`,`NO_DIGITS`) values (15,NULL,0,NULL);
insert into `dyextn_numeric_type_info` (`IDENTIFIER`,`MEASUREMENT_UNITS`,`DECIMAL_PLACES`,`NO_DIGITS`) values (16,NULL,0,NULL);
insert into `dyextn_numeric_type_info` (`IDENTIFIER`,`MEASUREMENT_UNITS`,`DECIMAL_PLACES`,`NO_DIGITS`) values (17,NULL,0,NULL);
insert into `dyextn_numeric_type_info` (`IDENTIFIER`,`MEASUREMENT_UNITS`,`DECIMAL_PLACES`,`NO_DIGITS`) values (18,NULL,0,NULL);
insert into `dyextn_numeric_type_info` (`IDENTIFIER`,`MEASUREMENT_UNITS`,`DECIMAL_PLACES`,`NO_DIGITS`) values (19,NULL,0,NULL);
insert into `dyextn_numeric_type_info` (`IDENTIFIER`,`MEASUREMENT_UNITS`,`DECIMAL_PLACES`,`NO_DIGITS`) values (20,NULL,0,NULL);
insert into `dyextn_numeric_type_info` (`IDENTIFIER`,`MEASUREMENT_UNITS`,`DECIMAL_PLACES`,`NO_DIGITS`) values (21,NULL,0,NULL);
insert into `dyextn_numeric_type_info` (`IDENTIFIER`,`MEASUREMENT_UNITS`,`DECIMAL_PLACES`,`NO_DIGITS`) values (22,NULL,0,NULL);
insert into `dyextn_numeric_type_info` (`IDENTIFIER`,`MEASUREMENT_UNITS`,`DECIMAL_PLACES`,`NO_DIGITS`) values (30,NULL,0,NULL);
insert into `dyextn_numeric_type_info` (`IDENTIFIER`,`MEASUREMENT_UNITS`,`DECIMAL_PLACES`,`NO_DIGITS`) values (31,NULL,0,NULL);
insert into `dyextn_numeric_type_info` (`IDENTIFIER`,`MEASUREMENT_UNITS`,`DECIMAL_PLACES`,`NO_DIGITS`) values (34,NULL,0,NULL);
insert into `dyextn_numeric_type_info` (`IDENTIFIER`,`MEASUREMENT_UNITS`,`DECIMAL_PLACES`,`NO_DIGITS`) values (35,NULL,0,NULL);
insert into `dyextn_numeric_type_info` (`IDENTIFIER`,`MEASUREMENT_UNITS`,`DECIMAL_PLACES`,`NO_DIGITS`) values (36,NULL,0,NULL);
insert into `dyextn_numeric_type_info` (`IDENTIFIER`,`MEASUREMENT_UNITS`,`DECIMAL_PLACES`,`NO_DIGITS`) values (37,NULL,0,NULL);
insert into `dyextn_numeric_type_info` (`IDENTIFIER`,`MEASUREMENT_UNITS`,`DECIMAL_PLACES`,`NO_DIGITS`) values (42,NULL,0,NULL);
insert into `dyextn_numeric_type_info` (`IDENTIFIER`,`MEASUREMENT_UNITS`,`DECIMAL_PLACES`,`NO_DIGITS`) values (44,NULL,0,NULL);
insert into `dyextn_numeric_type_info` (`IDENTIFIER`,`MEASUREMENT_UNITS`,`DECIMAL_PLACES`,`NO_DIGITS`) values (46,NULL,0,NULL);
insert into `dyextn_numeric_type_info` (`IDENTIFIER`,`MEASUREMENT_UNITS`,`DECIMAL_PLACES`,`NO_DIGITS`) values (47,NULL,0,NULL);
insert into `dyextn_numeric_type_info` (`IDENTIFIER`,`MEASUREMENT_UNITS`,`DECIMAL_PLACES`,`NO_DIGITS`) values (48,NULL,0,NULL);
insert into `dyextn_numeric_type_info` (`IDENTIFIER`,`MEASUREMENT_UNITS`,`DECIMAL_PLACES`,`NO_DIGITS`) values (49,NULL,0,NULL);
insert into `dyextn_numeric_type_info` (`IDENTIFIER`,`MEASUREMENT_UNITS`,`DECIMAL_PLACES`,`NO_DIGITS`) values (50,NULL,15,NULL);
insert into `dyextn_numeric_type_info` (`IDENTIFIER`,`MEASUREMENT_UNITS`,`DECIMAL_PLACES`,`NO_DIGITS`) values (51,NULL,15,NULL);
insert into `dyextn_numeric_type_info` (`IDENTIFIER`,`MEASUREMENT_UNITS`,`DECIMAL_PLACES`,`NO_DIGITS`) values (53,NULL,0,NULL);
insert into `dyextn_numeric_type_info` (`IDENTIFIER`,`MEASUREMENT_UNITS`,`DECIMAL_PLACES`,`NO_DIGITS`) values (54,NULL,0,NULL);
insert into `dyextn_numeric_type_info` (`IDENTIFIER`,`MEASUREMENT_UNITS`,`DECIMAL_PLACES`,`NO_DIGITS`) values (55,NULL,0,NULL);
insert into `dyextn_numeric_type_info` (`IDENTIFIER`,`MEASUREMENT_UNITS`,`DECIMAL_PLACES`,`NO_DIGITS`) values (56,NULL,0,NULL);
insert into `dyextn_numeric_type_info` (`IDENTIFIER`,`MEASUREMENT_UNITS`,`DECIMAL_PLACES`,`NO_DIGITS`) values (57,NULL,0,NULL);
insert into `dyextn_numeric_type_info` (`IDENTIFIER`,`MEASUREMENT_UNITS`,`DECIMAL_PLACES`,`NO_DIGITS`) values (59,NULL,0,NULL);
insert into `dyextn_numeric_type_info` (`IDENTIFIER`,`MEASUREMENT_UNITS`,`DECIMAL_PLACES`,`NO_DIGITS`) values (60,NULL,0,NULL);
insert into `dyextn_numeric_type_info` (`IDENTIFIER`,`MEASUREMENT_UNITS`,`DECIMAL_PLACES`,`NO_DIGITS`) values (61,NULL,0,NULL);
insert into `dyextn_numeric_type_info` (`IDENTIFIER`,`MEASUREMENT_UNITS`,`DECIMAL_PLACES`,`NO_DIGITS`) values (64,NULL,15,NULL);
insert into `dyextn_numeric_type_info` (`IDENTIFIER`,`MEASUREMENT_UNITS`,`DECIMAL_PLACES`,`NO_DIGITS`) values (69,NULL,0,NULL);
insert into `dyextn_numeric_type_info` (`IDENTIFIER`,`MEASUREMENT_UNITS`,`DECIMAL_PLACES`,`NO_DIGITS`) values (71,NULL,15,NULL);
insert into `dyextn_numeric_type_info` (`IDENTIFIER`,`MEASUREMENT_UNITS`,`DECIMAL_PLACES`,`NO_DIGITS`) values (72,NULL,15,NULL);
insert into `dyextn_numeric_type_info` (`IDENTIFIER`,`MEASUREMENT_UNITS`,`DECIMAL_PLACES`,`NO_DIGITS`) values (74,NULL,0,NULL);
insert into `dyextn_numeric_type_info` (`IDENTIFIER`,`MEASUREMENT_UNITS`,`DECIMAL_PLACES`,`NO_DIGITS`) values (75,NULL,0,NULL);
insert into `dyextn_numeric_type_info` (`IDENTIFIER`,`MEASUREMENT_UNITS`,`DECIMAL_PLACES`,`NO_DIGITS`) values (76,NULL,0,NULL);
insert into `dyextn_numeric_type_info` (`IDENTIFIER`,`MEASUREMENT_UNITS`,`DECIMAL_PLACES`,`NO_DIGITS`) values (78,NULL,0,NULL);
insert into `dyextn_numeric_type_info` (`IDENTIFIER`,`MEASUREMENT_UNITS`,`DECIMAL_PLACES`,`NO_DIGITS`) values (82,NULL,0,NULL);
insert into `dyextn_numeric_type_info` (`IDENTIFIER`,`MEASUREMENT_UNITS`,`DECIMAL_PLACES`,`NO_DIGITS`) values (83,NULL,0,NULL);
insert into `dyextn_numeric_type_info` (`IDENTIFIER`,`MEASUREMENT_UNITS`,`DECIMAL_PLACES`,`NO_DIGITS`) values (85,NULL,0,NULL);
insert into `dyextn_numeric_type_info` (`IDENTIFIER`,`MEASUREMENT_UNITS`,`DECIMAL_PLACES`,`NO_DIGITS`) values (89,NULL,0,NULL);
insert into `dyextn_numeric_type_info` (`IDENTIFIER`,`MEASUREMENT_UNITS`,`DECIMAL_PLACES`,`NO_DIGITS`) values (92,NULL,0,NULL);
insert into `dyextn_numeric_type_info` (`IDENTIFIER`,`MEASUREMENT_UNITS`,`DECIMAL_PLACES`,`NO_DIGITS`) values (93,NULL,0,NULL);
insert into `dyextn_numeric_type_info` (`IDENTIFIER`,`MEASUREMENT_UNITS`,`DECIMAL_PLACES`,`NO_DIGITS`) values (94,NULL,0,NULL);
insert into `dyextn_numeric_type_info` (`IDENTIFIER`,`MEASUREMENT_UNITS`,`DECIMAL_PLACES`,`NO_DIGITS`) values (95,NULL,0,NULL);
insert into `dyextn_numeric_type_info` (`IDENTIFIER`,`MEASUREMENT_UNITS`,`DECIMAL_PLACES`,`NO_DIGITS`) values (96,NULL,0,NULL);
insert into `dyextn_numeric_type_info` (`IDENTIFIER`,`MEASUREMENT_UNITS`,`DECIMAL_PLACES`,`NO_DIGITS`) values (105,NULL,15,NULL);
insert into `dyextn_numeric_type_info` (`IDENTIFIER`,`MEASUREMENT_UNITS`,`DECIMAL_PLACES`,`NO_DIGITS`) values (106,NULL,0,NULL);
insert into `dyextn_numeric_type_info` (`IDENTIFIER`,`MEASUREMENT_UNITS`,`DECIMAL_PLACES`,`NO_DIGITS`) values (113,NULL,0,NULL);
insert into `dyextn_numeric_type_info` (`IDENTIFIER`,`MEASUREMENT_UNITS`,`DECIMAL_PLACES`,`NO_DIGITS`) values (114,NULL,0,NULL);
insert into `dyextn_numeric_type_info` (`IDENTIFIER`,`MEASUREMENT_UNITS`,`DECIMAL_PLACES`,`NO_DIGITS`) values (115,NULL,0,NULL);
insert into `dyextn_numeric_type_info` (`IDENTIFIER`,`MEASUREMENT_UNITS`,`DECIMAL_PLACES`,`NO_DIGITS`) values (116,NULL,15,NULL);
insert into `dyextn_numeric_type_info` (`IDENTIFIER`,`MEASUREMENT_UNITS`,`DECIMAL_PLACES`,`NO_DIGITS`) values (118,NULL,0,NULL);
insert into `dyextn_numeric_type_info` (`IDENTIFIER`,`MEASUREMENT_UNITS`,`DECIMAL_PLACES`,`NO_DIGITS`) values (119,NULL,0,NULL);
insert into `dyextn_numeric_type_info` (`IDENTIFIER`,`MEASUREMENT_UNITS`,`DECIMAL_PLACES`,`NO_DIGITS`) values (120,NULL,0,NULL);
insert into `dyextn_numeric_type_info` (`IDENTIFIER`,`MEASUREMENT_UNITS`,`DECIMAL_PLACES`,`NO_DIGITS`) values (121,NULL,0,NULL);
insert into `dyextn_numeric_type_info` (`IDENTIFIER`,`MEASUREMENT_UNITS`,`DECIMAL_PLACES`,`NO_DIGITS`) values (127,NULL,15,NULL);
insert into `dyextn_numeric_type_info` (`IDENTIFIER`,`MEASUREMENT_UNITS`,`DECIMAL_PLACES`,`NO_DIGITS`) values (130,NULL,0,NULL);
insert into `dyextn_numeric_type_info` (`IDENTIFIER`,`MEASUREMENT_UNITS`,`DECIMAL_PLACES`,`NO_DIGITS`) values (131,NULL,0,NULL);
insert into `dyextn_numeric_type_info` (`IDENTIFIER`,`MEASUREMENT_UNITS`,`DECIMAL_PLACES`,`NO_DIGITS`) values (132,NULL,0,NULL);
insert into `dyextn_numeric_type_info` (`IDENTIFIER`,`MEASUREMENT_UNITS`,`DECIMAL_PLACES`,`NO_DIGITS`) values (133,NULL,0,NULL);
insert into `dyextn_numeric_type_info` (`IDENTIFIER`,`MEASUREMENT_UNITS`,`DECIMAL_PLACES`,`NO_DIGITS`) values (134,NULL,15,NULL);
insert into `dyextn_numeric_type_info` (`IDENTIFIER`,`MEASUREMENT_UNITS`,`DECIMAL_PLACES`,`NO_DIGITS`) values (136,NULL,0,NULL);
insert into `dyextn_numeric_type_info` (`IDENTIFIER`,`MEASUREMENT_UNITS`,`DECIMAL_PLACES`,`NO_DIGITS`) values (137,NULL,0,NULL);
insert into `dyextn_numeric_type_info` (`IDENTIFIER`,`MEASUREMENT_UNITS`,`DECIMAL_PLACES`,`NO_DIGITS`) values (138,NULL,0,NULL);
insert into `dyextn_numeric_type_info` (`IDENTIFIER`,`MEASUREMENT_UNITS`,`DECIMAL_PLACES`,`NO_DIGITS`) values (139,NULL,0,NULL);
insert into `dyextn_numeric_type_info` (`IDENTIFIER`,`MEASUREMENT_UNITS`,`DECIMAL_PLACES`,`NO_DIGITS`) values (140,NULL,0,NULL);
insert into `dyextn_numeric_type_info` (`IDENTIFIER`,`MEASUREMENT_UNITS`,`DECIMAL_PLACES`,`NO_DIGITS`) values (142,NULL,0,NULL);

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

insert into `dyextn_permissible_value` (`IDENTIFIER`,`DESCRIPTION`,`CATEGORY_ATTRIBUTE_ID`,`ATTRIBUTE_TYPE_INFO_ID`) values (1,NULL,NULL,1);
insert into `dyextn_permissible_value` (`IDENTIFIER`,`DESCRIPTION`,`CATEGORY_ATTRIBUTE_ID`,`ATTRIBUTE_TYPE_INFO_ID`) values (2,NULL,NULL,2);
insert into `dyextn_permissible_value` (`IDENTIFIER`,`DESCRIPTION`,`CATEGORY_ATTRIBUTE_ID`,`ATTRIBUTE_TYPE_INFO_ID`) values (3,NULL,NULL,3);
insert into `dyextn_permissible_value` (`IDENTIFIER`,`DESCRIPTION`,`CATEGORY_ATTRIBUTE_ID`,`ATTRIBUTE_TYPE_INFO_ID`) values (4,NULL,NULL,5);
insert into `dyextn_permissible_value` (`IDENTIFIER`,`DESCRIPTION`,`CATEGORY_ATTRIBUTE_ID`,`ATTRIBUTE_TYPE_INFO_ID`) values (5,NULL,NULL,6);
insert into `dyextn_permissible_value` (`IDENTIFIER`,`DESCRIPTION`,`CATEGORY_ATTRIBUTE_ID`,`ATTRIBUTE_TYPE_INFO_ID`) values (6,NULL,NULL,8);
insert into `dyextn_permissible_value` (`IDENTIFIER`,`DESCRIPTION`,`CATEGORY_ATTRIBUTE_ID`,`ATTRIBUTE_TYPE_INFO_ID`) values (7,NULL,NULL,9);
insert into `dyextn_permissible_value` (`IDENTIFIER`,`DESCRIPTION`,`CATEGORY_ATTRIBUTE_ID`,`ATTRIBUTE_TYPE_INFO_ID`) values (8,NULL,NULL,10);
insert into `dyextn_permissible_value` (`IDENTIFIER`,`DESCRIPTION`,`CATEGORY_ATTRIBUTE_ID`,`ATTRIBUTE_TYPE_INFO_ID`) values (9,NULL,NULL,11);
insert into `dyextn_permissible_value` (`IDENTIFIER`,`DESCRIPTION`,`CATEGORY_ATTRIBUTE_ID`,`ATTRIBUTE_TYPE_INFO_ID`) values (10,NULL,NULL,12);
insert into `dyextn_permissible_value` (`IDENTIFIER`,`DESCRIPTION`,`CATEGORY_ATTRIBUTE_ID`,`ATTRIBUTE_TYPE_INFO_ID`) values (11,NULL,NULL,13);
insert into `dyextn_permissible_value` (`IDENTIFIER`,`DESCRIPTION`,`CATEGORY_ATTRIBUTE_ID`,`ATTRIBUTE_TYPE_INFO_ID`) values (12,NULL,NULL,14);
insert into `dyextn_permissible_value` (`IDENTIFIER`,`DESCRIPTION`,`CATEGORY_ATTRIBUTE_ID`,`ATTRIBUTE_TYPE_INFO_ID`) values (13,NULL,NULL,23);
insert into `dyextn_permissible_value` (`IDENTIFIER`,`DESCRIPTION`,`CATEGORY_ATTRIBUTE_ID`,`ATTRIBUTE_TYPE_INFO_ID`) values (14,NULL,NULL,24);
insert into `dyextn_permissible_value` (`IDENTIFIER`,`DESCRIPTION`,`CATEGORY_ATTRIBUTE_ID`,`ATTRIBUTE_TYPE_INFO_ID`) values (15,NULL,NULL,25);
insert into `dyextn_permissible_value` (`IDENTIFIER`,`DESCRIPTION`,`CATEGORY_ATTRIBUTE_ID`,`ATTRIBUTE_TYPE_INFO_ID`) values (16,NULL,NULL,26);
insert into `dyextn_permissible_value` (`IDENTIFIER`,`DESCRIPTION`,`CATEGORY_ATTRIBUTE_ID`,`ATTRIBUTE_TYPE_INFO_ID`) values (17,NULL,NULL,27);
insert into `dyextn_permissible_value` (`IDENTIFIER`,`DESCRIPTION`,`CATEGORY_ATTRIBUTE_ID`,`ATTRIBUTE_TYPE_INFO_ID`) values (18,NULL,NULL,28);
insert into `dyextn_permissible_value` (`IDENTIFIER`,`DESCRIPTION`,`CATEGORY_ATTRIBUTE_ID`,`ATTRIBUTE_TYPE_INFO_ID`) values (19,NULL,NULL,29);
insert into `dyextn_permissible_value` (`IDENTIFIER`,`DESCRIPTION`,`CATEGORY_ATTRIBUTE_ID`,`ATTRIBUTE_TYPE_INFO_ID`) values (20,NULL,NULL,32);
insert into `dyextn_permissible_value` (`IDENTIFIER`,`DESCRIPTION`,`CATEGORY_ATTRIBUTE_ID`,`ATTRIBUTE_TYPE_INFO_ID`) values (21,NULL,NULL,33);
insert into `dyextn_permissible_value` (`IDENTIFIER`,`DESCRIPTION`,`CATEGORY_ATTRIBUTE_ID`,`ATTRIBUTE_TYPE_INFO_ID`) values (22,NULL,NULL,38);
insert into `dyextn_permissible_value` (`IDENTIFIER`,`DESCRIPTION`,`CATEGORY_ATTRIBUTE_ID`,`ATTRIBUTE_TYPE_INFO_ID`) values (23,NULL,NULL,39);
insert into `dyextn_permissible_value` (`IDENTIFIER`,`DESCRIPTION`,`CATEGORY_ATTRIBUTE_ID`,`ATTRIBUTE_TYPE_INFO_ID`) values (24,NULL,NULL,40);
insert into `dyextn_permissible_value` (`IDENTIFIER`,`DESCRIPTION`,`CATEGORY_ATTRIBUTE_ID`,`ATTRIBUTE_TYPE_INFO_ID`) values (25,NULL,NULL,41);
insert into `dyextn_permissible_value` (`IDENTIFIER`,`DESCRIPTION`,`CATEGORY_ATTRIBUTE_ID`,`ATTRIBUTE_TYPE_INFO_ID`) values (26,NULL,NULL,43);
insert into `dyextn_permissible_value` (`IDENTIFIER`,`DESCRIPTION`,`CATEGORY_ATTRIBUTE_ID`,`ATTRIBUTE_TYPE_INFO_ID`) values (27,NULL,NULL,45);
insert into `dyextn_permissible_value` (`IDENTIFIER`,`DESCRIPTION`,`CATEGORY_ATTRIBUTE_ID`,`ATTRIBUTE_TYPE_INFO_ID`) values (28,NULL,NULL,52);
insert into `dyextn_permissible_value` (`IDENTIFIER`,`DESCRIPTION`,`CATEGORY_ATTRIBUTE_ID`,`ATTRIBUTE_TYPE_INFO_ID`) values (29,NULL,NULL,58);
insert into `dyextn_permissible_value` (`IDENTIFIER`,`DESCRIPTION`,`CATEGORY_ATTRIBUTE_ID`,`ATTRIBUTE_TYPE_INFO_ID`) values (30,NULL,NULL,62);
insert into `dyextn_permissible_value` (`IDENTIFIER`,`DESCRIPTION`,`CATEGORY_ATTRIBUTE_ID`,`ATTRIBUTE_TYPE_INFO_ID`) values (31,NULL,NULL,63);
insert into `dyextn_permissible_value` (`IDENTIFIER`,`DESCRIPTION`,`CATEGORY_ATTRIBUTE_ID`,`ATTRIBUTE_TYPE_INFO_ID`) values (32,NULL,NULL,65);
insert into `dyextn_permissible_value` (`IDENTIFIER`,`DESCRIPTION`,`CATEGORY_ATTRIBUTE_ID`,`ATTRIBUTE_TYPE_INFO_ID`) values (33,NULL,NULL,66);
insert into `dyextn_permissible_value` (`IDENTIFIER`,`DESCRIPTION`,`CATEGORY_ATTRIBUTE_ID`,`ATTRIBUTE_TYPE_INFO_ID`) values (34,NULL,NULL,67);
insert into `dyextn_permissible_value` (`IDENTIFIER`,`DESCRIPTION`,`CATEGORY_ATTRIBUTE_ID`,`ATTRIBUTE_TYPE_INFO_ID`) values (35,NULL,NULL,68);
insert into `dyextn_permissible_value` (`IDENTIFIER`,`DESCRIPTION`,`CATEGORY_ATTRIBUTE_ID`,`ATTRIBUTE_TYPE_INFO_ID`) values (36,NULL,NULL,70);
insert into `dyextn_permissible_value` (`IDENTIFIER`,`DESCRIPTION`,`CATEGORY_ATTRIBUTE_ID`,`ATTRIBUTE_TYPE_INFO_ID`) values (37,NULL,NULL,73);
insert into `dyextn_permissible_value` (`IDENTIFIER`,`DESCRIPTION`,`CATEGORY_ATTRIBUTE_ID`,`ATTRIBUTE_TYPE_INFO_ID`) values (38,NULL,NULL,77);
insert into `dyextn_permissible_value` (`IDENTIFIER`,`DESCRIPTION`,`CATEGORY_ATTRIBUTE_ID`,`ATTRIBUTE_TYPE_INFO_ID`) values (39,NULL,NULL,79);
insert into `dyextn_permissible_value` (`IDENTIFIER`,`DESCRIPTION`,`CATEGORY_ATTRIBUTE_ID`,`ATTRIBUTE_TYPE_INFO_ID`) values (40,NULL,NULL,80);
insert into `dyextn_permissible_value` (`IDENTIFIER`,`DESCRIPTION`,`CATEGORY_ATTRIBUTE_ID`,`ATTRIBUTE_TYPE_INFO_ID`) values (41,NULL,NULL,81);
insert into `dyextn_permissible_value` (`IDENTIFIER`,`DESCRIPTION`,`CATEGORY_ATTRIBUTE_ID`,`ATTRIBUTE_TYPE_INFO_ID`) values (42,NULL,NULL,84);
insert into `dyextn_permissible_value` (`IDENTIFIER`,`DESCRIPTION`,`CATEGORY_ATTRIBUTE_ID`,`ATTRIBUTE_TYPE_INFO_ID`) values (43,NULL,NULL,86);
insert into `dyextn_permissible_value` (`IDENTIFIER`,`DESCRIPTION`,`CATEGORY_ATTRIBUTE_ID`,`ATTRIBUTE_TYPE_INFO_ID`) values (44,NULL,NULL,87);
insert into `dyextn_permissible_value` (`IDENTIFIER`,`DESCRIPTION`,`CATEGORY_ATTRIBUTE_ID`,`ATTRIBUTE_TYPE_INFO_ID`) values (45,NULL,NULL,88);
insert into `dyextn_permissible_value` (`IDENTIFIER`,`DESCRIPTION`,`CATEGORY_ATTRIBUTE_ID`,`ATTRIBUTE_TYPE_INFO_ID`) values (46,NULL,NULL,90);
insert into `dyextn_permissible_value` (`IDENTIFIER`,`DESCRIPTION`,`CATEGORY_ATTRIBUTE_ID`,`ATTRIBUTE_TYPE_INFO_ID`) values (47,NULL,NULL,91);
insert into `dyextn_permissible_value` (`IDENTIFIER`,`DESCRIPTION`,`CATEGORY_ATTRIBUTE_ID`,`ATTRIBUTE_TYPE_INFO_ID`) values (48,NULL,NULL,97);
insert into `dyextn_permissible_value` (`IDENTIFIER`,`DESCRIPTION`,`CATEGORY_ATTRIBUTE_ID`,`ATTRIBUTE_TYPE_INFO_ID`) values (49,NULL,NULL,98);
insert into `dyextn_permissible_value` (`IDENTIFIER`,`DESCRIPTION`,`CATEGORY_ATTRIBUTE_ID`,`ATTRIBUTE_TYPE_INFO_ID`) values (50,NULL,NULL,99);
insert into `dyextn_permissible_value` (`IDENTIFIER`,`DESCRIPTION`,`CATEGORY_ATTRIBUTE_ID`,`ATTRIBUTE_TYPE_INFO_ID`) values (51,NULL,NULL,100);
insert into `dyextn_permissible_value` (`IDENTIFIER`,`DESCRIPTION`,`CATEGORY_ATTRIBUTE_ID`,`ATTRIBUTE_TYPE_INFO_ID`) values (52,NULL,NULL,101);
insert into `dyextn_permissible_value` (`IDENTIFIER`,`DESCRIPTION`,`CATEGORY_ATTRIBUTE_ID`,`ATTRIBUTE_TYPE_INFO_ID`) values (53,NULL,NULL,102);
insert into `dyextn_permissible_value` (`IDENTIFIER`,`DESCRIPTION`,`CATEGORY_ATTRIBUTE_ID`,`ATTRIBUTE_TYPE_INFO_ID`) values (54,NULL,NULL,103);
insert into `dyextn_permissible_value` (`IDENTIFIER`,`DESCRIPTION`,`CATEGORY_ATTRIBUTE_ID`,`ATTRIBUTE_TYPE_INFO_ID`) values (55,NULL,NULL,104);
insert into `dyextn_permissible_value` (`IDENTIFIER`,`DESCRIPTION`,`CATEGORY_ATTRIBUTE_ID`,`ATTRIBUTE_TYPE_INFO_ID`) values (56,NULL,NULL,107);
insert into `dyextn_permissible_value` (`IDENTIFIER`,`DESCRIPTION`,`CATEGORY_ATTRIBUTE_ID`,`ATTRIBUTE_TYPE_INFO_ID`) values (57,NULL,NULL,108);
insert into `dyextn_permissible_value` (`IDENTIFIER`,`DESCRIPTION`,`CATEGORY_ATTRIBUTE_ID`,`ATTRIBUTE_TYPE_INFO_ID`) values (58,NULL,NULL,109);
insert into `dyextn_permissible_value` (`IDENTIFIER`,`DESCRIPTION`,`CATEGORY_ATTRIBUTE_ID`,`ATTRIBUTE_TYPE_INFO_ID`) values (59,NULL,NULL,110);
insert into `dyextn_permissible_value` (`IDENTIFIER`,`DESCRIPTION`,`CATEGORY_ATTRIBUTE_ID`,`ATTRIBUTE_TYPE_INFO_ID`) values (60,NULL,NULL,111);
insert into `dyextn_permissible_value` (`IDENTIFIER`,`DESCRIPTION`,`CATEGORY_ATTRIBUTE_ID`,`ATTRIBUTE_TYPE_INFO_ID`) values (61,NULL,NULL,112);
insert into `dyextn_permissible_value` (`IDENTIFIER`,`DESCRIPTION`,`CATEGORY_ATTRIBUTE_ID`,`ATTRIBUTE_TYPE_INFO_ID`) values (62,NULL,NULL,117);
insert into `dyextn_permissible_value` (`IDENTIFIER`,`DESCRIPTION`,`CATEGORY_ATTRIBUTE_ID`,`ATTRIBUTE_TYPE_INFO_ID`) values (63,NULL,NULL,122);
insert into `dyextn_permissible_value` (`IDENTIFIER`,`DESCRIPTION`,`CATEGORY_ATTRIBUTE_ID`,`ATTRIBUTE_TYPE_INFO_ID`) values (64,NULL,NULL,123);
insert into `dyextn_permissible_value` (`IDENTIFIER`,`DESCRIPTION`,`CATEGORY_ATTRIBUTE_ID`,`ATTRIBUTE_TYPE_INFO_ID`) values (65,NULL,NULL,124);
insert into `dyextn_permissible_value` (`IDENTIFIER`,`DESCRIPTION`,`CATEGORY_ATTRIBUTE_ID`,`ATTRIBUTE_TYPE_INFO_ID`) values (66,NULL,NULL,125);
insert into `dyextn_permissible_value` (`IDENTIFIER`,`DESCRIPTION`,`CATEGORY_ATTRIBUTE_ID`,`ATTRIBUTE_TYPE_INFO_ID`) values (67,NULL,NULL,126);
insert into `dyextn_permissible_value` (`IDENTIFIER`,`DESCRIPTION`,`CATEGORY_ATTRIBUTE_ID`,`ATTRIBUTE_TYPE_INFO_ID`) values (68,NULL,NULL,128);
insert into `dyextn_permissible_value` (`IDENTIFIER`,`DESCRIPTION`,`CATEGORY_ATTRIBUTE_ID`,`ATTRIBUTE_TYPE_INFO_ID`) values (69,NULL,NULL,129);
insert into `dyextn_permissible_value` (`IDENTIFIER`,`DESCRIPTION`,`CATEGORY_ATTRIBUTE_ID`,`ATTRIBUTE_TYPE_INFO_ID`) values (70,NULL,NULL,135);
insert into `dyextn_permissible_value` (`IDENTIFIER`,`DESCRIPTION`,`CATEGORY_ATTRIBUTE_ID`,`ATTRIBUTE_TYPE_INFO_ID`) values (71,NULL,NULL,141);

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

insert into `dyextn_primitive_attribute` (`IDENTIFIER`,`IS_IDENTIFIED`,`IS_PRIMARY_KEY`,`IS_NULLABLE`) values (3,'\0','\0','');
insert into `dyextn_primitive_attribute` (`IDENTIFIER`,`IS_IDENTIFIED`,`IS_PRIMARY_KEY`,`IS_NULLABLE`) values (4,'\0','\0','');
insert into `dyextn_primitive_attribute` (`IDENTIFIER`,`IS_IDENTIFIED`,`IS_PRIMARY_KEY`,`IS_NULLABLE`) values (5,'\0','\0','');
insert into `dyextn_primitive_attribute` (`IDENTIFIER`,`IS_IDENTIFIED`,`IS_PRIMARY_KEY`,`IS_NULLABLE`) values (6,NULL,'','\0');
insert into `dyextn_primitive_attribute` (`IDENTIFIER`,`IS_IDENTIFIED`,`IS_PRIMARY_KEY`,`IS_NULLABLE`) values (9,'\0','\0','');
insert into `dyextn_primitive_attribute` (`IDENTIFIER`,`IS_IDENTIFIED`,`IS_PRIMARY_KEY`,`IS_NULLABLE`) values (10,'\0','\0','');
insert into `dyextn_primitive_attribute` (`IDENTIFIER`,`IS_IDENTIFIED`,`IS_PRIMARY_KEY`,`IS_NULLABLE`) values (11,NULL,'','\0');
insert into `dyextn_primitive_attribute` (`IDENTIFIER`,`IS_IDENTIFIED`,`IS_PRIMARY_KEY`,`IS_NULLABLE`) values (14,'\0','\0','');
insert into `dyextn_primitive_attribute` (`IDENTIFIER`,`IS_IDENTIFIED`,`IS_PRIMARY_KEY`,`IS_NULLABLE`) values (15,'\0','\0','');
insert into `dyextn_primitive_attribute` (`IDENTIFIER`,`IS_IDENTIFIED`,`IS_PRIMARY_KEY`,`IS_NULLABLE`) values (16,'\0','\0','');
insert into `dyextn_primitive_attribute` (`IDENTIFIER`,`IS_IDENTIFIED`,`IS_PRIMARY_KEY`,`IS_NULLABLE`) values (17,'\0','\0','');
insert into `dyextn_primitive_attribute` (`IDENTIFIER`,`IS_IDENTIFIED`,`IS_PRIMARY_KEY`,`IS_NULLABLE`) values (18,'\0','\0','');
insert into `dyextn_primitive_attribute` (`IDENTIFIER`,`IS_IDENTIFIED`,`IS_PRIMARY_KEY`,`IS_NULLABLE`) values (19,'\0','\0','');
insert into `dyextn_primitive_attribute` (`IDENTIFIER`,`IS_IDENTIFIED`,`IS_PRIMARY_KEY`,`IS_NULLABLE`) values (20,'\0','\0','');
insert into `dyextn_primitive_attribute` (`IDENTIFIER`,`IS_IDENTIFIED`,`IS_PRIMARY_KEY`,`IS_NULLABLE`) values (21,NULL,'','\0');
insert into `dyextn_primitive_attribute` (`IDENTIFIER`,`IS_IDENTIFIED`,`IS_PRIMARY_KEY`,`IS_NULLABLE`) values (26,NULL,'','\0');
insert into `dyextn_primitive_attribute` (`IDENTIFIER`,`IS_IDENTIFIED`,`IS_PRIMARY_KEY`,`IS_NULLABLE`) values (29,NULL,'','\0');
insert into `dyextn_primitive_attribute` (`IDENTIFIER`,`IS_IDENTIFIED`,`IS_PRIMARY_KEY`,`IS_NULLABLE`) values (32,NULL,'','\0');
insert into `dyextn_primitive_attribute` (`IDENTIFIER`,`IS_IDENTIFIED`,`IS_PRIMARY_KEY`,`IS_NULLABLE`) values (35,NULL,'','\0');
insert into `dyextn_primitive_attribute` (`IDENTIFIER`,`IS_IDENTIFIED`,`IS_PRIMARY_KEY`,`IS_NULLABLE`) values (38,NULL,'','\0');
insert into `dyextn_primitive_attribute` (`IDENTIFIER`,`IS_IDENTIFIED`,`IS_PRIMARY_KEY`,`IS_NULLABLE`) values (41,NULL,'','\0');
insert into `dyextn_primitive_attribute` (`IDENTIFIER`,`IS_IDENTIFIED`,`IS_PRIMARY_KEY`,`IS_NULLABLE`) values (43,NULL,'','\0');
insert into `dyextn_primitive_attribute` (`IDENTIFIER`,`IS_IDENTIFIED`,`IS_PRIMARY_KEY`,`IS_NULLABLE`) values (44,'\0','\0','');
insert into `dyextn_primitive_attribute` (`IDENTIFIER`,`IS_IDENTIFIED`,`IS_PRIMARY_KEY`,`IS_NULLABLE`) values (45,'\0','\0','');
insert into `dyextn_primitive_attribute` (`IDENTIFIER`,`IS_IDENTIFIED`,`IS_PRIMARY_KEY`,`IS_NULLABLE`) values (46,'\0','\0','');
insert into `dyextn_primitive_attribute` (`IDENTIFIER`,`IS_IDENTIFIED`,`IS_PRIMARY_KEY`,`IS_NULLABLE`) values (47,'\0','\0','');
insert into `dyextn_primitive_attribute` (`IDENTIFIER`,`IS_IDENTIFIED`,`IS_PRIMARY_KEY`,`IS_NULLABLE`) values (48,'\0','\0','');
insert into `dyextn_primitive_attribute` (`IDENTIFIER`,`IS_IDENTIFIED`,`IS_PRIMARY_KEY`,`IS_NULLABLE`) values (49,'\0','\0','');
insert into `dyextn_primitive_attribute` (`IDENTIFIER`,`IS_IDENTIFIED`,`IS_PRIMARY_KEY`,`IS_NULLABLE`) values (50,'\0','\0','');
insert into `dyextn_primitive_attribute` (`IDENTIFIER`,`IS_IDENTIFIED`,`IS_PRIMARY_KEY`,`IS_NULLABLE`) values (54,NULL,'','\0');
insert into `dyextn_primitive_attribute` (`IDENTIFIER`,`IS_IDENTIFIED`,`IS_PRIMARY_KEY`,`IS_NULLABLE`) values (57,NULL,'','\0');
insert into `dyextn_primitive_attribute` (`IDENTIFIER`,`IS_IDENTIFIED`,`IS_PRIMARY_KEY`,`IS_NULLABLE`) values (61,'\0','\0','');
insert into `dyextn_primitive_attribute` (`IDENTIFIER`,`IS_IDENTIFIED`,`IS_PRIMARY_KEY`,`IS_NULLABLE`) values (62,'\0','\0','');
insert into `dyextn_primitive_attribute` (`IDENTIFIER`,`IS_IDENTIFIED`,`IS_PRIMARY_KEY`,`IS_NULLABLE`) values (63,NULL,'','\0');
insert into `dyextn_primitive_attribute` (`IDENTIFIER`,`IS_IDENTIFIED`,`IS_PRIMARY_KEY`,`IS_NULLABLE`) values (67,NULL,'','\0');
insert into `dyextn_primitive_attribute` (`IDENTIFIER`,`IS_IDENTIFIED`,`IS_PRIMARY_KEY`,`IS_NULLABLE`) values (70,NULL,'','\0');
insert into `dyextn_primitive_attribute` (`IDENTIFIER`,`IS_IDENTIFIED`,`IS_PRIMARY_KEY`,`IS_NULLABLE`) values (73,NULL,'','\0');
insert into `dyextn_primitive_attribute` (`IDENTIFIER`,`IS_IDENTIFIED`,`IS_PRIMARY_KEY`,`IS_NULLABLE`) values (75,'\0','\0','');
insert into `dyextn_primitive_attribute` (`IDENTIFIER`,`IS_IDENTIFIED`,`IS_PRIMARY_KEY`,`IS_NULLABLE`) values (76,'\0','\0','');
insert into `dyextn_primitive_attribute` (`IDENTIFIER`,`IS_IDENTIFIED`,`IS_PRIMARY_KEY`,`IS_NULLABLE`) values (77,'\0','\0','');
insert into `dyextn_primitive_attribute` (`IDENTIFIER`,`IS_IDENTIFIED`,`IS_PRIMARY_KEY`,`IS_NULLABLE`) values (78,'\0','\0','');
insert into `dyextn_primitive_attribute` (`IDENTIFIER`,`IS_IDENTIFIED`,`IS_PRIMARY_KEY`,`IS_NULLABLE`) values (79,NULL,'','\0');
insert into `dyextn_primitive_attribute` (`IDENTIFIER`,`IS_IDENTIFIED`,`IS_PRIMARY_KEY`,`IS_NULLABLE`) values (82,'\0','\0','');
insert into `dyextn_primitive_attribute` (`IDENTIFIER`,`IS_IDENTIFIED`,`IS_PRIMARY_KEY`,`IS_NULLABLE`) values (83,NULL,'','\0');
insert into `dyextn_primitive_attribute` (`IDENTIFIER`,`IS_IDENTIFIED`,`IS_PRIMARY_KEY`,`IS_NULLABLE`) values (85,'\0','\0','');
insert into `dyextn_primitive_attribute` (`IDENTIFIER`,`IS_IDENTIFIED`,`IS_PRIMARY_KEY`,`IS_NULLABLE`) values (86,NULL,'','\0');
insert into `dyextn_primitive_attribute` (`IDENTIFIER`,`IS_IDENTIFIED`,`IS_PRIMARY_KEY`,`IS_NULLABLE`) values (88,NULL,'','\0');
insert into `dyextn_primitive_attribute` (`IDENTIFIER`,`IS_IDENTIFIED`,`IS_PRIMARY_KEY`,`IS_NULLABLE`) values (90,NULL,'','\0');
insert into `dyextn_primitive_attribute` (`IDENTIFIER`,`IS_IDENTIFIED`,`IS_PRIMARY_KEY`,`IS_NULLABLE`) values (92,NULL,'','\0');
insert into `dyextn_primitive_attribute` (`IDENTIFIER`,`IS_IDENTIFIED`,`IS_PRIMARY_KEY`,`IS_NULLABLE`) values (94,NULL,'\0','');
insert into `dyextn_primitive_attribute` (`IDENTIFIER`,`IS_IDENTIFIED`,`IS_PRIMARY_KEY`,`IS_NULLABLE`) values (95,NULL,'\0','');
insert into `dyextn_primitive_attribute` (`IDENTIFIER`,`IS_IDENTIFIED`,`IS_PRIMARY_KEY`,`IS_NULLABLE`) values (96,'\0','\0','');
insert into `dyextn_primitive_attribute` (`IDENTIFIER`,`IS_IDENTIFIED`,`IS_PRIMARY_KEY`,`IS_NULLABLE`) values (97,NULL,'','\0');
insert into `dyextn_primitive_attribute` (`IDENTIFIER`,`IS_IDENTIFIED`,`IS_PRIMARY_KEY`,`IS_NULLABLE`) values (99,NULL,'','\0');
insert into `dyextn_primitive_attribute` (`IDENTIFIER`,`IS_IDENTIFIED`,`IS_PRIMARY_KEY`,`IS_NULLABLE`) values (101,NULL,'','\0');
insert into `dyextn_primitive_attribute` (`IDENTIFIER`,`IS_IDENTIFIED`,`IS_PRIMARY_KEY`,`IS_NULLABLE`) values (103,NULL,'','\0');
insert into `dyextn_primitive_attribute` (`IDENTIFIER`,`IS_IDENTIFIED`,`IS_PRIMARY_KEY`,`IS_NULLABLE`) values (105,NULL,'','\0');
insert into `dyextn_primitive_attribute` (`IDENTIFIER`,`IS_IDENTIFIED`,`IS_PRIMARY_KEY`,`IS_NULLABLE`) values (109,'\0','\0','');
insert into `dyextn_primitive_attribute` (`IDENTIFIER`,`IS_IDENTIFIED`,`IS_PRIMARY_KEY`,`IS_NULLABLE`) values (110,NULL,'','\0');
insert into `dyextn_primitive_attribute` (`IDENTIFIER`,`IS_IDENTIFIED`,`IS_PRIMARY_KEY`,`IS_NULLABLE`) values (114,NULL,'','\0');
insert into `dyextn_primitive_attribute` (`IDENTIFIER`,`IS_IDENTIFIED`,`IS_PRIMARY_KEY`,`IS_NULLABLE`) values (118,NULL,'','\0');
insert into `dyextn_primitive_attribute` (`IDENTIFIER`,`IS_IDENTIFIED`,`IS_PRIMARY_KEY`,`IS_NULLABLE`) values (119,'\0','\0','');
insert into `dyextn_primitive_attribute` (`IDENTIFIER`,`IS_IDENTIFIED`,`IS_PRIMARY_KEY`,`IS_NULLABLE`) values (120,'\0','\0','');
insert into `dyextn_primitive_attribute` (`IDENTIFIER`,`IS_IDENTIFIED`,`IS_PRIMARY_KEY`,`IS_NULLABLE`) values (121,NULL,'\0','');
insert into `dyextn_primitive_attribute` (`IDENTIFIER`,`IS_IDENTIFIED`,`IS_PRIMARY_KEY`,`IS_NULLABLE`) values (122,'\0','\0','');
insert into `dyextn_primitive_attribute` (`IDENTIFIER`,`IS_IDENTIFIED`,`IS_PRIMARY_KEY`,`IS_NULLABLE`) values (123,'\0','\0','');
insert into `dyextn_primitive_attribute` (`IDENTIFIER`,`IS_IDENTIFIED`,`IS_PRIMARY_KEY`,`IS_NULLABLE`) values (126,'\0','\0','');
insert into `dyextn_primitive_attribute` (`IDENTIFIER`,`IS_IDENTIFIED`,`IS_PRIMARY_KEY`,`IS_NULLABLE`) values (127,'\0','\0','');
insert into `dyextn_primitive_attribute` (`IDENTIFIER`,`IS_IDENTIFIED`,`IS_PRIMARY_KEY`,`IS_NULLABLE`) values (128,NULL,'','\0');
insert into `dyextn_primitive_attribute` (`IDENTIFIER`,`IS_IDENTIFIED`,`IS_PRIMARY_KEY`,`IS_NULLABLE`) values (137,'\0','\0','');
insert into `dyextn_primitive_attribute` (`IDENTIFIER`,`IS_IDENTIFIED`,`IS_PRIMARY_KEY`,`IS_NULLABLE`) values (138,NULL,'\0','');
insert into `dyextn_primitive_attribute` (`IDENTIFIER`,`IS_IDENTIFIED`,`IS_PRIMARY_KEY`,`IS_NULLABLE`) values (139,NULL,'\0','');
insert into `dyextn_primitive_attribute` (`IDENTIFIER`,`IS_IDENTIFIED`,`IS_PRIMARY_KEY`,`IS_NULLABLE`) values (140,'\0','\0','');
insert into `dyextn_primitive_attribute` (`IDENTIFIER`,`IS_IDENTIFIED`,`IS_PRIMARY_KEY`,`IS_NULLABLE`) values (141,NULL,'','\0');
insert into `dyextn_primitive_attribute` (`IDENTIFIER`,`IS_IDENTIFIED`,`IS_PRIMARY_KEY`,`IS_NULLABLE`) values (144,NULL,'','\0');
insert into `dyextn_primitive_attribute` (`IDENTIFIER`,`IS_IDENTIFIED`,`IS_PRIMARY_KEY`,`IS_NULLABLE`) values (147,NULL,'','\0');
insert into `dyextn_primitive_attribute` (`IDENTIFIER`,`IS_IDENTIFIED`,`IS_PRIMARY_KEY`,`IS_NULLABLE`) values (150,'\0','\0','');
insert into `dyextn_primitive_attribute` (`IDENTIFIER`,`IS_IDENTIFIED`,`IS_PRIMARY_KEY`,`IS_NULLABLE`) values (151,NULL,'','\0');
insert into `dyextn_primitive_attribute` (`IDENTIFIER`,`IS_IDENTIFIED`,`IS_PRIMARY_KEY`,`IS_NULLABLE`) values (153,'\0','\0','');
insert into `dyextn_primitive_attribute` (`IDENTIFIER`,`IS_IDENTIFIED`,`IS_PRIMARY_KEY`,`IS_NULLABLE`) values (154,'\0','\0','');
insert into `dyextn_primitive_attribute` (`IDENTIFIER`,`IS_IDENTIFIED`,`IS_PRIMARY_KEY`,`IS_NULLABLE`) values (155,'\0','\0','');
insert into `dyextn_primitive_attribute` (`IDENTIFIER`,`IS_IDENTIFIED`,`IS_PRIMARY_KEY`,`IS_NULLABLE`) values (156,NULL,'','\0');
insert into `dyextn_primitive_attribute` (`IDENTIFIER`,`IS_IDENTIFIED`,`IS_PRIMARY_KEY`,`IS_NULLABLE`) values (159,NULL,'','\0');
insert into `dyextn_primitive_attribute` (`IDENTIFIER`,`IS_IDENTIFIED`,`IS_PRIMARY_KEY`,`IS_NULLABLE`) values (163,'\0','\0','');
insert into `dyextn_primitive_attribute` (`IDENTIFIER`,`IS_IDENTIFIED`,`IS_PRIMARY_KEY`,`IS_NULLABLE`) values (164,NULL,'','\0');
insert into `dyextn_primitive_attribute` (`IDENTIFIER`,`IS_IDENTIFIED`,`IS_PRIMARY_KEY`,`IS_NULLABLE`) values (168,'\0','\0','');
insert into `dyextn_primitive_attribute` (`IDENTIFIER`,`IS_IDENTIFIED`,`IS_PRIMARY_KEY`,`IS_NULLABLE`) values (169,'\0','\0','');
insert into `dyextn_primitive_attribute` (`IDENTIFIER`,`IS_IDENTIFIED`,`IS_PRIMARY_KEY`,`IS_NULLABLE`) values (170,'\0','\0','');
insert into `dyextn_primitive_attribute` (`IDENTIFIER`,`IS_IDENTIFIED`,`IS_PRIMARY_KEY`,`IS_NULLABLE`) values (171,NULL,'','\0');
insert into `dyextn_primitive_attribute` (`IDENTIFIER`,`IS_IDENTIFIED`,`IS_PRIMARY_KEY`,`IS_NULLABLE`) values (173,'\0','\0','');
insert into `dyextn_primitive_attribute` (`IDENTIFIER`,`IS_IDENTIFIED`,`IS_PRIMARY_KEY`,`IS_NULLABLE`) values (174,'\0','\0','');
insert into `dyextn_primitive_attribute` (`IDENTIFIER`,`IS_IDENTIFIED`,`IS_PRIMARY_KEY`,`IS_NULLABLE`) values (175,NULL,'','\0');
insert into `dyextn_primitive_attribute` (`IDENTIFIER`,`IS_IDENTIFIED`,`IS_PRIMARY_KEY`,`IS_NULLABLE`) values (177,NULL,'','\0');
insert into `dyextn_primitive_attribute` (`IDENTIFIER`,`IS_IDENTIFIED`,`IS_PRIMARY_KEY`,`IS_NULLABLE`) values (179,NULL,'','\0');
insert into `dyextn_primitive_attribute` (`IDENTIFIER`,`IS_IDENTIFIED`,`IS_PRIMARY_KEY`,`IS_NULLABLE`) values (181,NULL,'','\0');
insert into `dyextn_primitive_attribute` (`IDENTIFIER`,`IS_IDENTIFIED`,`IS_PRIMARY_KEY`,`IS_NULLABLE`) values (189,NULL,'','\0');
insert into `dyextn_primitive_attribute` (`IDENTIFIER`,`IS_IDENTIFIED`,`IS_PRIMARY_KEY`,`IS_NULLABLE`) values (194,'\0','\0','');
insert into `dyextn_primitive_attribute` (`IDENTIFIER`,`IS_IDENTIFIED`,`IS_PRIMARY_KEY`,`IS_NULLABLE`) values (195,'\0','\0','');
insert into `dyextn_primitive_attribute` (`IDENTIFIER`,`IS_IDENTIFIED`,`IS_PRIMARY_KEY`,`IS_NULLABLE`) values (196,'\0','\0','');
insert into `dyextn_primitive_attribute` (`IDENTIFIER`,`IS_IDENTIFIED`,`IS_PRIMARY_KEY`,`IS_NULLABLE`) values (197,'\0','\0','');
insert into `dyextn_primitive_attribute` (`IDENTIFIER`,`IS_IDENTIFIED`,`IS_PRIMARY_KEY`,`IS_NULLABLE`) values (198,'\0','\0','');
insert into `dyextn_primitive_attribute` (`IDENTIFIER`,`IS_IDENTIFIED`,`IS_PRIMARY_KEY`,`IS_NULLABLE`) values (199,'\0','\0','');
insert into `dyextn_primitive_attribute` (`IDENTIFIER`,`IS_IDENTIFIED`,`IS_PRIMARY_KEY`,`IS_NULLABLE`) values (200,'\0','\0','');
insert into `dyextn_primitive_attribute` (`IDENTIFIER`,`IS_IDENTIFIED`,`IS_PRIMARY_KEY`,`IS_NULLABLE`) values (201,'\0','\0','');
insert into `dyextn_primitive_attribute` (`IDENTIFIER`,`IS_IDENTIFIED`,`IS_PRIMARY_KEY`,`IS_NULLABLE`) values (202,NULL,'\0','');
insert into `dyextn_primitive_attribute` (`IDENTIFIER`,`IS_IDENTIFIED`,`IS_PRIMARY_KEY`,`IS_NULLABLE`) values (203,NULL,'','\0');
insert into `dyextn_primitive_attribute` (`IDENTIFIER`,`IS_IDENTIFIED`,`IS_PRIMARY_KEY`,`IS_NULLABLE`) values (208,'\0','\0','');
insert into `dyextn_primitive_attribute` (`IDENTIFIER`,`IS_IDENTIFIED`,`IS_PRIMARY_KEY`,`IS_NULLABLE`) values (209,'\0','\0','');
insert into `dyextn_primitive_attribute` (`IDENTIFIER`,`IS_IDENTIFIED`,`IS_PRIMARY_KEY`,`IS_NULLABLE`) values (210,'\0','\0','');
insert into `dyextn_primitive_attribute` (`IDENTIFIER`,`IS_IDENTIFIED`,`IS_PRIMARY_KEY`,`IS_NULLABLE`) values (211,'\0','\0','');
insert into `dyextn_primitive_attribute` (`IDENTIFIER`,`IS_IDENTIFIED`,`IS_PRIMARY_KEY`,`IS_NULLABLE`) values (212,'\0','\0','');
insert into `dyextn_primitive_attribute` (`IDENTIFIER`,`IS_IDENTIFIED`,`IS_PRIMARY_KEY`,`IS_NULLABLE`) values (213,'\0','\0','');
insert into `dyextn_primitive_attribute` (`IDENTIFIER`,`IS_IDENTIFIED`,`IS_PRIMARY_KEY`,`IS_NULLABLE`) values (214,NULL,'','\0');
insert into `dyextn_primitive_attribute` (`IDENTIFIER`,`IS_IDENTIFIED`,`IS_PRIMARY_KEY`,`IS_NULLABLE`) values (217,NULL,'','\0');
insert into `dyextn_primitive_attribute` (`IDENTIFIER`,`IS_IDENTIFIED`,`IS_PRIMARY_KEY`,`IS_NULLABLE`) values (225,NULL,'','\0');
insert into `dyextn_primitive_attribute` (`IDENTIFIER`,`IS_IDENTIFIED`,`IS_PRIMARY_KEY`,`IS_NULLABLE`) values (229,NULL,'\0','');
insert into `dyextn_primitive_attribute` (`IDENTIFIER`,`IS_IDENTIFIED`,`IS_PRIMARY_KEY`,`IS_NULLABLE`) values (230,'\0','\0','');
insert into `dyextn_primitive_attribute` (`IDENTIFIER`,`IS_IDENTIFIED`,`IS_PRIMARY_KEY`,`IS_NULLABLE`) values (231,NULL,'','\0');
insert into `dyextn_primitive_attribute` (`IDENTIFIER`,`IS_IDENTIFIED`,`IS_PRIMARY_KEY`,`IS_NULLABLE`) values (234,NULL,'','\0');
insert into `dyextn_primitive_attribute` (`IDENTIFIER`,`IS_IDENTIFIED`,`IS_PRIMARY_KEY`,`IS_NULLABLE`) values (237,NULL,'','\0');
insert into `dyextn_primitive_attribute` (`IDENTIFIER`,`IS_IDENTIFIED`,`IS_PRIMARY_KEY`,`IS_NULLABLE`) values (240,NULL,'','\0');
insert into `dyextn_primitive_attribute` (`IDENTIFIER`,`IS_IDENTIFIED`,`IS_PRIMARY_KEY`,`IS_NULLABLE`) values (246,'\0','\0','');
insert into `dyextn_primitive_attribute` (`IDENTIFIER`,`IS_IDENTIFIED`,`IS_PRIMARY_KEY`,`IS_NULLABLE`) values (247,'\0','\0','');
insert into `dyextn_primitive_attribute` (`IDENTIFIER`,`IS_IDENTIFIED`,`IS_PRIMARY_KEY`,`IS_NULLABLE`) values (248,'\0','\0','');
insert into `dyextn_primitive_attribute` (`IDENTIFIER`,`IS_IDENTIFIED`,`IS_PRIMARY_KEY`,`IS_NULLABLE`) values (249,'\0','\0','');
insert into `dyextn_primitive_attribute` (`IDENTIFIER`,`IS_IDENTIFIED`,`IS_PRIMARY_KEY`,`IS_NULLABLE`) values (250,'\0','\0','');
insert into `dyextn_primitive_attribute` (`IDENTIFIER`,`IS_IDENTIFIED`,`IS_PRIMARY_KEY`,`IS_NULLABLE`) values (251,NULL,'\0','');
insert into `dyextn_primitive_attribute` (`IDENTIFIER`,`IS_IDENTIFIED`,`IS_PRIMARY_KEY`,`IS_NULLABLE`) values (252,'\0','\0','');
insert into `dyextn_primitive_attribute` (`IDENTIFIER`,`IS_IDENTIFIED`,`IS_PRIMARY_KEY`,`IS_NULLABLE`) values (253,'\0','\0','');
insert into `dyextn_primitive_attribute` (`IDENTIFIER`,`IS_IDENTIFIED`,`IS_PRIMARY_KEY`,`IS_NULLABLE`) values (254,NULL,'','\0');
insert into `dyextn_primitive_attribute` (`IDENTIFIER`,`IS_IDENTIFIED`,`IS_PRIMARY_KEY`,`IS_NULLABLE`) values (264,NULL,'','\0');
insert into `dyextn_primitive_attribute` (`IDENTIFIER`,`IS_IDENTIFIED`,`IS_PRIMARY_KEY`,`IS_NULLABLE`) values (267,NULL,'','\0');
insert into `dyextn_primitive_attribute` (`IDENTIFIER`,`IS_IDENTIFIED`,`IS_PRIMARY_KEY`,`IS_NULLABLE`) values (272,NULL,'','\0');
insert into `dyextn_primitive_attribute` (`IDENTIFIER`,`IS_IDENTIFIED`,`IS_PRIMARY_KEY`,`IS_NULLABLE`) values (276,NULL,'\0','');
insert into `dyextn_primitive_attribute` (`IDENTIFIER`,`IS_IDENTIFIED`,`IS_PRIMARY_KEY`,`IS_NULLABLE`) values (277,'\0','\0','');
insert into `dyextn_primitive_attribute` (`IDENTIFIER`,`IS_IDENTIFIED`,`IS_PRIMARY_KEY`,`IS_NULLABLE`) values (278,NULL,'','\0');
insert into `dyextn_primitive_attribute` (`IDENTIFIER`,`IS_IDENTIFIED`,`IS_PRIMARY_KEY`,`IS_NULLABLE`) values (281,NULL,'','\0');
insert into `dyextn_primitive_attribute` (`IDENTIFIER`,`IS_IDENTIFIED`,`IS_PRIMARY_KEY`,`IS_NULLABLE`) values (284,NULL,'','\0');
insert into `dyextn_primitive_attribute` (`IDENTIFIER`,`IS_IDENTIFIED`,`IS_PRIMARY_KEY`,`IS_NULLABLE`) values (286,NULL,'','\0');
insert into `dyextn_primitive_attribute` (`IDENTIFIER`,`IS_IDENTIFIED`,`IS_PRIMARY_KEY`,`IS_NULLABLE`) values (288,NULL,'','\0');
insert into `dyextn_primitive_attribute` (`IDENTIFIER`,`IS_IDENTIFIED`,`IS_PRIMARY_KEY`,`IS_NULLABLE`) values (294,'\0','\0','');
insert into `dyextn_primitive_attribute` (`IDENTIFIER`,`IS_IDENTIFIED`,`IS_PRIMARY_KEY`,`IS_NULLABLE`) values (295,NULL,'','\0');

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

insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (1,'ASSOCIATION',100,0,NULL);
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (2,'CONTAINTMENT',1,1,'providerType');
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (3,'ASSOCIATION',100,0,NULL);
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (4,'ASSOCIATION',1,0,'personName');
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (5,'ASSOCIATION',100,0,NULL);
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (6,'CONTAINTMENT',100,0,'ethinicOriginCollection');
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (7,'ASSOCIATION',100,0,NULL);
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (8,'CONTAINTMENT',1,0,'gender');
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (9,'ASSOCIATION',100,0,NULL);
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (10,'CONTAINTMENT',1,0,'advancedDirectiveExists');
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (11,'ASSOCIATION',100,0,NULL);
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (12,'CONTAINTMENT',1,0,'religion');
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (13,'ASSOCIATION',100,0,NULL);
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (14,'CONTAINTMENT',100,0,'raceCollection');
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (15,'ASSOCIATION',100,0,NULL);
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (16,'CONTAINTMENT',1,0,'maritalStatus');
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (17,'ASSOCIATION',1,1,NULL);
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (18,'CONTAINTMENT',1,1,'personName');
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (19,'ASSOCIATION',100,0,NULL);
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (20,'ASSOCIATION',1,1,'relationToPerson');
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (21,'ASSOCIATION',100,0,NULL);
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (22,'ASSOCIATION',1,1,'personUpi');
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (23,'ASSOCIATION',100,0,NULL);
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (24,'CONTAINTMENT',100,0,'associatedPersonCollection');
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (25,'ASSOCIATION',100,0,NULL);
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (26,'ASSOCIATION',1,0,'nextOfKin');
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (27,'ASSOCIATION',100,0,NULL);
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (28,'CONTAINTMENT',100,0,'phoneCollection');
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (29,'ASSOCIATION',100,0,NULL);
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (30,'CONTAINTMENT',1,0,'state');
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (31,'ASSOCIATION',100,0,NULL);
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (32,'CONTAINTMENT',1,0,'addressType');
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (33,'ASSOCIATION',100,0,NULL);
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (34,'CONTAINTMENT',1,0,'country');
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (35,'ASSOCIATION',1,1,NULL);
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (36,'CONTAINTMENT',100,0,'addressCollection');
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (37,'ASSOCIATION',1,1,NULL);
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (38,'CONTAINTMENT',100,1,'demographicsCollection');
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (39,'ASSOCIATION',100,0,NULL);
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (40,'ASSOCIATION',1,0,'personUpi');
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (41,'ASSOCIATION',100,0,NULL);
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (42,'ASSOCIATION',1,1,'procedure');
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (43,'ASSOCIATION',100,0,NULL);
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (44,'ASSOCIATION',1,1,'facility');
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (45,'ASSOCIATION',100,0,NULL);
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (46,'ASSOCIATION',1,1,'personUpi');
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (47,'ASSOCIATION',100,0,NULL);
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (48,'ASSOCIATION',1,1,'application');
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (49,'ASSOCIATION',100,0,NULL);
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (50,'ASSOCIATION',1,1,'procedureStatus');
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (51,'ASSOCIATION',100,0,NULL);
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (52,'ASSOCIATION',1,1,'facility');
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (53,'ASSOCIATION',100,0,NULL);
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (54,'CONTAINTMENT',1,0,'medicalRecordNumber');
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (55,'ASSOCIATION',100,0,NULL);
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (56,'ASSOCIATION',100,0,'procedureId');
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (57,'ASSOCIATION',100,0,NULL);
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (58,'ASSOCIATION',100,0,'procedureStatus');
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (59,'ASSOCIATION',100,0,NULL);
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (60,'ASSOCIATION',1,1,'resultStatus');
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (61,'ASSOCIATION',100,0,NULL);
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (62,'ASSOCIATION',1,0,'normalRange');
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (63,'ASSOCIATION',100,0,NULL);
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (64,'ASSOCIATION',1,1,'normalizedResult');
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (65,'ASSOCIATION',100,0,NULL);
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (66,'ASSOCIATION',1,1,'sourceResult');
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (67,'ASSOCIATION',100,0,NULL);
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (68,'ASSOCIATION',1,0,'resultValue');
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (69,'ASSOCIATION',100,0,NULL);
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (70,'ASSOCIATION',100,0,'abnormalFlagCollection');
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (71,'ASSOCIATION',100,0,NULL);
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (72,'ASSOCIATION',1,1,'test');
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (73,'ASSOCIATION',100,0,NULL);
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (74,'ASSOCIATION',100,0,'laboratoryResultCollection');
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (75,'ASSOCIATION',100,0,NULL);
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (76,'ASSOCIATION',1,1,'specimanType');
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (77,'ASSOCIATION',100,0,NULL);
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (78,'ASSOCIATION',1,1,'facility');
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (79,'ASSOCIATION',100,0,NULL);
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (80,'CONTAINTMENT',1,1,'patientAccountNumber');
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (81,'ASSOCIATION',1,1,NULL);
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (82,'CONTAINTMENT',1,1,'performingFacility');
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (83,'ASSOCIATION',100,0,NULL);
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (84,'ASSOCIATION',100,0,'laboratoryProcedureDetailsColletion');
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (85,'ASSOCIATION',1,1,NULL);
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (86,'CONTAINTMENT',1,0,'patientLocation');
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (87,'ASSOCIATION',100,0,NULL);
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (88,'CONTAINTMENT',100,0,'providerCollection');
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (89,'ASSOCIATION',100,0,NULL);
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (90,'ASSOCIATION',1,0,'vipIndicator');
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (91,'ASSOCIATION',100,0,NULL);
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (92,'ASSOCIATION',1,0,'diagnosticRelatedGroup');
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (93,'ASSOCIATION',100,0,NULL);
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (94,'ASSOCIATION',1,0,'facilityDischargeDisposition');
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (95,'ASSOCIATION',100,0,NULL);
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (96,'ASSOCIATION',1,0,'financialClass');
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (97,'ASSOCIATION',100,0,NULL);
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (98,'CONTAINTMENT',1,1,'insuredRelationShipToPatient');
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (99,'ASSOCIATION',100,0,NULL);
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (100,'CONTAINTMENT',1,0,'nameOfInsured');
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (101,'ASSOCIATION',100,0,NULL);
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (102,'CONTAINTMENT',100,1,'insuranceCollection');
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (103,'ASSOCIATION',1,1,NULL);
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (104,'CONTAINTMENT',1,1,'facility');
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (105,'ASSOCIATION',100,0,NULL);
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (106,'ASSOCIATION',1,0,'principalInvestigator');
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (107,'ASSOCIATION',100,0,NULL);
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (108,'CONTAINTMENT',100,0,'clinicalTrailCollection');
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (109,'ASSOCIATION',100,0,NULL);
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (110,'ASSOCIATION',1,0,'dischargeService');
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (111,'ASSOCIATION',100,0,NULL);
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (112,'ASSOCIATION',1,0,'admittingService');
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (113,'ASSOCIATION',1,1,NULL);
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (114,'ASSOCIATION',1,1,'patientAccountNumber');
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (115,'ASSOCIATION',1,1,NULL);
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (116,'CONTAINTMENT',1,1,'provider');
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (117,'ASSOCIATION',100,0,NULL);
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (118,'ASSOCIATION',100,0,'code');
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (119,'ASSOCIATION',100,0,NULL);
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (120,'ASSOCIATION',100,0,'type');
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (121,'ASSOCIATION',1,1,NULL);
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (122,'CONTAINTMENT',1,1,'type');
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (123,'ASSOCIATION',1,1,NULL);
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (124,'CONTAINTMENT',1,1,'code');
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (125,'ASSOCIATION',100,0,NULL);
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (126,'CONTAINTMENT',100,0,'procedureCollection');
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (127,'ASSOCIATION',100,0,NULL);
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (128,'ASSOCIATION',1,0,'hippaNotified');
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (129,'ASSOCIATION',100,0,NULL);
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (130,'ASSOCIATION',100,0,'infectionControlCodeCollection');
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (131,'ASSOCIATION',100,0,NULL);
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (132,'ASSOCIATION',1,0,'dischargeDisposition');
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (133,'ASSOCIATION',100,0,NULL);
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (134,'CONTAINTMENT',1,1,'optOutIndicator');
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (135,'ASSOCIATION',100,0,NULL);
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (136,'CONTAINTMENT',1,1,'hipaaNotified');
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (137,'ASSOCIATION',100,0,NULL);
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (138,'CONTAINTMENT',1,1,'vipIndicator');
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (139,'ASSOCIATION',100,0,NULL);
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (140,'CONTAINTMENT',1,1,'dischargeService');
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (141,'ASSOCIATION',100,0,NULL);
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (142,'CONTAINTMENT',100,0,'infectionControlCodes');
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (143,'ASSOCIATION',100,0,NULL);
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (144,'CONTAINTMENT',1,1,'facilityDischargeDisposition');
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (145,'ASSOCIATION',100,0,NULL);
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (146,'CONTAINTMENT',1,1,'financialClass');
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (147,'ASSOCIATION',100,0,NULL);
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (148,'CONTAINTMENT',1,0,'diagnosticRelatedGroup');
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (149,'ASSOCIATION',100,0,NULL);
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (150,'CONTAINTMENT',1,1,'dischargeDisposition');
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (151,'ASSOCIATION',100,0,NULL);
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (152,'CONTAINTMENT',1,1,'patientType');
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (153,'ASSOCIATION',100,0,NULL);
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (154,'CONTAINTMENT',1,1,'admittingService');
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (155,'ASSOCIATION',1,1,NULL);
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (156,'CONTAINTMENT',1,1,'patientClass');
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (157,'ASSOCIATION',100,0,NULL);
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (158,'ASSOCIATION',1,0,'patientClass');
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (159,'ASSOCIATION',100,0,NULL);
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (160,'ASSOCIATION',1,0,'patientType');
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (161,'ASSOCIATION',100,0,NULL);
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (162,'ASSOCIATION',100,0,'type');
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (163,'ASSOCIATION',100,0,NULL);
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (164,'ASSOCIATION',100,0,'code');
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (165,'ASSOCIATION',100,0,NULL);
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (166,'CONTAINTMENT',1,1,'type');
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (167,'ASSOCIATION',100,0,NULL);
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (168,'CONTAINTMENT',1,1,'code');
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (169,'ASSOCIATION',100,0,NULL);
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (170,'CONTAINTMENT',100,0,'diagnosesCollection');
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (171,'ASSOCIATION',100,0,NULL);
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (172,'ASSOCIATION',1,0,'optOutIndicator');
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (173,'ASSOCIATION',100,0,NULL);
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (174,'ASSOCIATION',100,1,'encounterDetailsCollection');
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (175,'ASSOCIATION',100,0,NULL);
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (176,'ASSOCIATION',1,1,'personUpi');
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (177,'ASSOCIATION',100,0,NULL);
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (178,'ASSOCIATION',1,1,'medicalRecordNumber');
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (179,'ASSOCIATION',100,0,NULL);
insert into `dyextn_role` (`IDENTIFIER`,`ASSOCIATION_TYPE`,`MAX_CARDINALITY`,`MIN_CARDINALITY`,`NAME`) values (180,'ASSOCIATION',1,1,'facility');

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

insert into `dyextn_rule` (`IDENTIFIER`,`NAME`,`IS_IMPLICIT`,`CATEGORY_ATTR_ID`,`ATTRIBUTE_ID`) values (1,'textLength','',NULL,3);
insert into `dyextn_rule` (`IDENTIFIER`,`NAME`,`IS_IMPLICIT`,`CATEGORY_ATTR_ID`,`ATTRIBUTE_ID`) values (2,'textLength','',NULL,4);
insert into `dyextn_rule` (`IDENTIFIER`,`NAME`,`IS_IMPLICIT`,`CATEGORY_ATTR_ID`,`ATTRIBUTE_ID`) values (3,'textLength','',NULL,5);
insert into `dyextn_rule` (`IDENTIFIER`,`NAME`,`IS_IMPLICIT`,`CATEGORY_ATTR_ID`,`ATTRIBUTE_ID`) values (4,'textLength','',NULL,9);
insert into `dyextn_rule` (`IDENTIFIER`,`NAME`,`IS_IMPLICIT`,`CATEGORY_ATTR_ID`,`ATTRIBUTE_ID`) values (5,'textLength','',NULL,10);
insert into `dyextn_rule` (`IDENTIFIER`,`NAME`,`IS_IMPLICIT`,`CATEGORY_ATTR_ID`,`ATTRIBUTE_ID`) values (6,'textLength','',NULL,14);
insert into `dyextn_rule` (`IDENTIFIER`,`NAME`,`IS_IMPLICIT`,`CATEGORY_ATTR_ID`,`ATTRIBUTE_ID`) values (7,'textLength','',NULL,15);
insert into `dyextn_rule` (`IDENTIFIER`,`NAME`,`IS_IMPLICIT`,`CATEGORY_ATTR_ID`,`ATTRIBUTE_ID`) values (8,'textLength','',NULL,16);
insert into `dyextn_rule` (`IDENTIFIER`,`NAME`,`IS_IMPLICIT`,`CATEGORY_ATTR_ID`,`ATTRIBUTE_ID`) values (9,'textLength','',NULL,17);
insert into `dyextn_rule` (`IDENTIFIER`,`NAME`,`IS_IMPLICIT`,`CATEGORY_ATTR_ID`,`ATTRIBUTE_ID`) values (10,'textLength','',NULL,18);
insert into `dyextn_rule` (`IDENTIFIER`,`NAME`,`IS_IMPLICIT`,`CATEGORY_ATTR_ID`,`ATTRIBUTE_ID`) values (11,'textLength','',NULL,19);
insert into `dyextn_rule` (`IDENTIFIER`,`NAME`,`IS_IMPLICIT`,`CATEGORY_ATTR_ID`,`ATTRIBUTE_ID`) values (12,'textLength','',NULL,20);
insert into `dyextn_rule` (`IDENTIFIER`,`NAME`,`IS_IMPLICIT`,`CATEGORY_ATTR_ID`,`ATTRIBUTE_ID`) values (13,'date','',NULL,44);
insert into `dyextn_rule` (`IDENTIFIER`,`NAME`,`IS_IMPLICIT`,`CATEGORY_ATTR_ID`,`ATTRIBUTE_ID`) values (14,'date','',NULL,45);
insert into `dyextn_rule` (`IDENTIFIER`,`NAME`,`IS_IMPLICIT`,`CATEGORY_ATTR_ID`,`ATTRIBUTE_ID`) values (15,'textLength','',NULL,46);
insert into `dyextn_rule` (`IDENTIFIER`,`NAME`,`IS_IMPLICIT`,`CATEGORY_ATTR_ID`,`ATTRIBUTE_ID`) values (16,'textLength','',NULL,47);
insert into `dyextn_rule` (`IDENTIFIER`,`NAME`,`IS_IMPLICIT`,`CATEGORY_ATTR_ID`,`ATTRIBUTE_ID`) values (17,'textLength','',NULL,48);
insert into `dyextn_rule` (`IDENTIFIER`,`NAME`,`IS_IMPLICIT`,`CATEGORY_ATTR_ID`,`ATTRIBUTE_ID`) values (18,'date','',NULL,49);
insert into `dyextn_rule` (`IDENTIFIER`,`NAME`,`IS_IMPLICIT`,`CATEGORY_ATTR_ID`,`ATTRIBUTE_ID`) values (19,'date','',NULL,50);
insert into `dyextn_rule` (`IDENTIFIER`,`NAME`,`IS_IMPLICIT`,`CATEGORY_ATTR_ID`,`ATTRIBUTE_ID`) values (20,'textLength','',NULL,61);
insert into `dyextn_rule` (`IDENTIFIER`,`NAME`,`IS_IMPLICIT`,`CATEGORY_ATTR_ID`,`ATTRIBUTE_ID`) values (21,'textLength','',NULL,62);
insert into `dyextn_rule` (`IDENTIFIER`,`NAME`,`IS_IMPLICIT`,`CATEGORY_ATTR_ID`,`ATTRIBUTE_ID`) values (22,'textLength','',NULL,75);
insert into `dyextn_rule` (`IDENTIFIER`,`NAME`,`IS_IMPLICIT`,`CATEGORY_ATTR_ID`,`ATTRIBUTE_ID`) values (23,'textLength','',NULL,76);
insert into `dyextn_rule` (`IDENTIFIER`,`NAME`,`IS_IMPLICIT`,`CATEGORY_ATTR_ID`,`ATTRIBUTE_ID`) values (24,'textLength','',NULL,77);
insert into `dyextn_rule` (`IDENTIFIER`,`NAME`,`IS_IMPLICIT`,`CATEGORY_ATTR_ID`,`ATTRIBUTE_ID`) values (25,'textLength','',NULL,78);
insert into `dyextn_rule` (`IDENTIFIER`,`NAME`,`IS_IMPLICIT`,`CATEGORY_ATTR_ID`,`ATTRIBUTE_ID`) values (26,'textLength','',NULL,82);
insert into `dyextn_rule` (`IDENTIFIER`,`NAME`,`IS_IMPLICIT`,`CATEGORY_ATTR_ID`,`ATTRIBUTE_ID`) values (27,'textLength','',NULL,85);
insert into `dyextn_rule` (`IDENTIFIER`,`NAME`,`IS_IMPLICIT`,`CATEGORY_ATTR_ID`,`ATTRIBUTE_ID`) values (28,'number','',NULL,94);
insert into `dyextn_rule` (`IDENTIFIER`,`NAME`,`IS_IMPLICIT`,`CATEGORY_ATTR_ID`,`ATTRIBUTE_ID`) values (29,'number','',NULL,95);
insert into `dyextn_rule` (`IDENTIFIER`,`NAME`,`IS_IMPLICIT`,`CATEGORY_ATTR_ID`,`ATTRIBUTE_ID`) values (30,'textLength','',NULL,96);
insert into `dyextn_rule` (`IDENTIFIER`,`NAME`,`IS_IMPLICIT`,`CATEGORY_ATTR_ID`,`ATTRIBUTE_ID`) values (31,'textLength','',NULL,109);
insert into `dyextn_rule` (`IDENTIFIER`,`NAME`,`IS_IMPLICIT`,`CATEGORY_ATTR_ID`,`ATTRIBUTE_ID`) values (32,'date','',NULL,119);
insert into `dyextn_rule` (`IDENTIFIER`,`NAME`,`IS_IMPLICIT`,`CATEGORY_ATTR_ID`,`ATTRIBUTE_ID`) values (33,'date','',NULL,120);
insert into `dyextn_rule` (`IDENTIFIER`,`NAME`,`IS_IMPLICIT`,`CATEGORY_ATTR_ID`,`ATTRIBUTE_ID`) values (34,'number','',NULL,121);
insert into `dyextn_rule` (`IDENTIFIER`,`NAME`,`IS_IMPLICIT`,`CATEGORY_ATTR_ID`,`ATTRIBUTE_ID`) values (35,'textLength','',NULL,122);
insert into `dyextn_rule` (`IDENTIFIER`,`NAME`,`IS_IMPLICIT`,`CATEGORY_ATTR_ID`,`ATTRIBUTE_ID`) values (36,'textLength','',NULL,123);
insert into `dyextn_rule` (`IDENTIFIER`,`NAME`,`IS_IMPLICIT`,`CATEGORY_ATTR_ID`,`ATTRIBUTE_ID`) values (37,'textLength','',NULL,126);
insert into `dyextn_rule` (`IDENTIFIER`,`NAME`,`IS_IMPLICIT`,`CATEGORY_ATTR_ID`,`ATTRIBUTE_ID`) values (38,'textLength','',NULL,127);
insert into `dyextn_rule` (`IDENTIFIER`,`NAME`,`IS_IMPLICIT`,`CATEGORY_ATTR_ID`,`ATTRIBUTE_ID`) values (39,'textLength','',NULL,137);
insert into `dyextn_rule` (`IDENTIFIER`,`NAME`,`IS_IMPLICIT`,`CATEGORY_ATTR_ID`,`ATTRIBUTE_ID`) values (40,'number','',NULL,138);
insert into `dyextn_rule` (`IDENTIFIER`,`NAME`,`IS_IMPLICIT`,`CATEGORY_ATTR_ID`,`ATTRIBUTE_ID`) values (41,'number','',NULL,139);
insert into `dyextn_rule` (`IDENTIFIER`,`NAME`,`IS_IMPLICIT`,`CATEGORY_ATTR_ID`,`ATTRIBUTE_ID`) values (42,'textLength','',NULL,140);
insert into `dyextn_rule` (`IDENTIFIER`,`NAME`,`IS_IMPLICIT`,`CATEGORY_ATTR_ID`,`ATTRIBUTE_ID`) values (43,'textLength','',NULL,150);
insert into `dyextn_rule` (`IDENTIFIER`,`NAME`,`IS_IMPLICIT`,`CATEGORY_ATTR_ID`,`ATTRIBUTE_ID`) values (44,'date','',NULL,153);
insert into `dyextn_rule` (`IDENTIFIER`,`NAME`,`IS_IMPLICIT`,`CATEGORY_ATTR_ID`,`ATTRIBUTE_ID`) values (45,'textLength','',NULL,154);
insert into `dyextn_rule` (`IDENTIFIER`,`NAME`,`IS_IMPLICIT`,`CATEGORY_ATTR_ID`,`ATTRIBUTE_ID`) values (46,'textLength','',NULL,155);
insert into `dyextn_rule` (`IDENTIFIER`,`NAME`,`IS_IMPLICIT`,`CATEGORY_ATTR_ID`,`ATTRIBUTE_ID`) values (47,'textLength','',NULL,163);
insert into `dyextn_rule` (`IDENTIFIER`,`NAME`,`IS_IMPLICIT`,`CATEGORY_ATTR_ID`,`ATTRIBUTE_ID`) values (48,'textLength','',NULL,168);
insert into `dyextn_rule` (`IDENTIFIER`,`NAME`,`IS_IMPLICIT`,`CATEGORY_ATTR_ID`,`ATTRIBUTE_ID`) values (49,'textLength','',NULL,169);
insert into `dyextn_rule` (`IDENTIFIER`,`NAME`,`IS_IMPLICIT`,`CATEGORY_ATTR_ID`,`ATTRIBUTE_ID`) values (50,'textLength','',NULL,170);
insert into `dyextn_rule` (`IDENTIFIER`,`NAME`,`IS_IMPLICIT`,`CATEGORY_ATTR_ID`,`ATTRIBUTE_ID`) values (51,'textLength','',NULL,173);
insert into `dyextn_rule` (`IDENTIFIER`,`NAME`,`IS_IMPLICIT`,`CATEGORY_ATTR_ID`,`ATTRIBUTE_ID`) values (52,'textLength','',NULL,174);
insert into `dyextn_rule` (`IDENTIFIER`,`NAME`,`IS_IMPLICIT`,`CATEGORY_ATTR_ID`,`ATTRIBUTE_ID`) values (53,'textLength','',NULL,194);
insert into `dyextn_rule` (`IDENTIFIER`,`NAME`,`IS_IMPLICIT`,`CATEGORY_ATTR_ID`,`ATTRIBUTE_ID`) values (54,'textLength','',NULL,195);
insert into `dyextn_rule` (`IDENTIFIER`,`NAME`,`IS_IMPLICIT`,`CATEGORY_ATTR_ID`,`ATTRIBUTE_ID`) values (55,'textLength','',NULL,196);
insert into `dyextn_rule` (`IDENTIFIER`,`NAME`,`IS_IMPLICIT`,`CATEGORY_ATTR_ID`,`ATTRIBUTE_ID`) values (56,'textLength','',NULL,197);
insert into `dyextn_rule` (`IDENTIFIER`,`NAME`,`IS_IMPLICIT`,`CATEGORY_ATTR_ID`,`ATTRIBUTE_ID`) values (57,'textLength','',NULL,198);
insert into `dyextn_rule` (`IDENTIFIER`,`NAME`,`IS_IMPLICIT`,`CATEGORY_ATTR_ID`,`ATTRIBUTE_ID`) values (58,'textLength','',NULL,199);
insert into `dyextn_rule` (`IDENTIFIER`,`NAME`,`IS_IMPLICIT`,`CATEGORY_ATTR_ID`,`ATTRIBUTE_ID`) values (59,'textLength','',NULL,200);
insert into `dyextn_rule` (`IDENTIFIER`,`NAME`,`IS_IMPLICIT`,`CATEGORY_ATTR_ID`,`ATTRIBUTE_ID`) values (60,'textLength','',NULL,201);
insert into `dyextn_rule` (`IDENTIFIER`,`NAME`,`IS_IMPLICIT`,`CATEGORY_ATTR_ID`,`ATTRIBUTE_ID`) values (61,'number','',NULL,202);
insert into `dyextn_rule` (`IDENTIFIER`,`NAME`,`IS_IMPLICIT`,`CATEGORY_ATTR_ID`,`ATTRIBUTE_ID`) values (62,'textLength','',NULL,208);
insert into `dyextn_rule` (`IDENTIFIER`,`NAME`,`IS_IMPLICIT`,`CATEGORY_ATTR_ID`,`ATTRIBUTE_ID`) values (63,'textLength','',NULL,209);
insert into `dyextn_rule` (`IDENTIFIER`,`NAME`,`IS_IMPLICIT`,`CATEGORY_ATTR_ID`,`ATTRIBUTE_ID`) values (64,'date','',NULL,210);
insert into `dyextn_rule` (`IDENTIFIER`,`NAME`,`IS_IMPLICIT`,`CATEGORY_ATTR_ID`,`ATTRIBUTE_ID`) values (65,'date','',NULL,211);
insert into `dyextn_rule` (`IDENTIFIER`,`NAME`,`IS_IMPLICIT`,`CATEGORY_ATTR_ID`,`ATTRIBUTE_ID`) values (66,'textLength','',NULL,212);
insert into `dyextn_rule` (`IDENTIFIER`,`NAME`,`IS_IMPLICIT`,`CATEGORY_ATTR_ID`,`ATTRIBUTE_ID`) values (67,'textLength','',NULL,213);
insert into `dyextn_rule` (`IDENTIFIER`,`NAME`,`IS_IMPLICIT`,`CATEGORY_ATTR_ID`,`ATTRIBUTE_ID`) values (68,'number','',NULL,229);
insert into `dyextn_rule` (`IDENTIFIER`,`NAME`,`IS_IMPLICIT`,`CATEGORY_ATTR_ID`,`ATTRIBUTE_ID`) values (69,'date','',NULL,230);
insert into `dyextn_rule` (`IDENTIFIER`,`NAME`,`IS_IMPLICIT`,`CATEGORY_ATTR_ID`,`ATTRIBUTE_ID`) values (70,'textLength','',NULL,246);
insert into `dyextn_rule` (`IDENTIFIER`,`NAME`,`IS_IMPLICIT`,`CATEGORY_ATTR_ID`,`ATTRIBUTE_ID`) values (71,'textLength','',NULL,247);
insert into `dyextn_rule` (`IDENTIFIER`,`NAME`,`IS_IMPLICIT`,`CATEGORY_ATTR_ID`,`ATTRIBUTE_ID`) values (72,'textLength','',NULL,248);
insert into `dyextn_rule` (`IDENTIFIER`,`NAME`,`IS_IMPLICIT`,`CATEGORY_ATTR_ID`,`ATTRIBUTE_ID`) values (73,'date','',NULL,249);
insert into `dyextn_rule` (`IDENTIFIER`,`NAME`,`IS_IMPLICIT`,`CATEGORY_ATTR_ID`,`ATTRIBUTE_ID`) values (74,'date','',NULL,250);
insert into `dyextn_rule` (`IDENTIFIER`,`NAME`,`IS_IMPLICIT`,`CATEGORY_ATTR_ID`,`ATTRIBUTE_ID`) values (75,'number','',NULL,251);
insert into `dyextn_rule` (`IDENTIFIER`,`NAME`,`IS_IMPLICIT`,`CATEGORY_ATTR_ID`,`ATTRIBUTE_ID`) values (76,'date','',NULL,252);
insert into `dyextn_rule` (`IDENTIFIER`,`NAME`,`IS_IMPLICIT`,`CATEGORY_ATTR_ID`,`ATTRIBUTE_ID`) values (77,'date','',NULL,253);
insert into `dyextn_rule` (`IDENTIFIER`,`NAME`,`IS_IMPLICIT`,`CATEGORY_ATTR_ID`,`ATTRIBUTE_ID`) values (78,'number','',NULL,276);
insert into `dyextn_rule` (`IDENTIFIER`,`NAME`,`IS_IMPLICIT`,`CATEGORY_ATTR_ID`,`ATTRIBUTE_ID`) values (79,'textLength','',NULL,277);
insert into `dyextn_rule` (`IDENTIFIER`,`NAME`,`IS_IMPLICIT`,`CATEGORY_ATTR_ID`,`ATTRIBUTE_ID`) values (80,'textLength','',NULL,294);

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

insert into `dyextn_select_control` (`IDENTIFIER`,`SEPARATOR_STRING`) values (5,NULL);
insert into `dyextn_select_control` (`IDENTIFIER`,`SEPARATOR_STRING`) values (6,NULL);
insert into `dyextn_select_control` (`IDENTIFIER`,`SEPARATOR_STRING`) values (11,NULL);
insert into `dyextn_select_control` (`IDENTIFIER`,`SEPARATOR_STRING`) values (17,NULL);
insert into `dyextn_select_control` (`IDENTIFIER`,`SEPARATOR_STRING`) values (22,NULL);
insert into `dyextn_select_control` (`IDENTIFIER`,`SEPARATOR_STRING`) values (23,NULL);
insert into `dyextn_select_control` (`IDENTIFIER`,`SEPARATOR_STRING`) values (24,NULL);
insert into `dyextn_select_control` (`IDENTIFIER`,`SEPARATOR_STRING`) values (25,NULL);
insert into `dyextn_select_control` (`IDENTIFIER`,`SEPARATOR_STRING`) values (32,NULL);
insert into `dyextn_select_control` (`IDENTIFIER`,`SEPARATOR_STRING`) values (33,NULL);
insert into `dyextn_select_control` (`IDENTIFIER`,`SEPARATOR_STRING`) values (34,NULL);
insert into `dyextn_select_control` (`IDENTIFIER`,`SEPARATOR_STRING`) values (35,NULL);
insert into `dyextn_select_control` (`IDENTIFIER`,`SEPARATOR_STRING`) values (36,NULL);
insert into `dyextn_select_control` (`IDENTIFIER`,`SEPARATOR_STRING`) values (37,NULL);
insert into `dyextn_select_control` (`IDENTIFIER`,`SEPARATOR_STRING`) values (38,NULL);
insert into `dyextn_select_control` (`IDENTIFIER`,`SEPARATOR_STRING`) values (39,NULL);
insert into `dyextn_select_control` (`IDENTIFIER`,`SEPARATOR_STRING`) values (40,NULL);
insert into `dyextn_select_control` (`IDENTIFIER`,`SEPARATOR_STRING`) values (41,NULL);
insert into `dyextn_select_control` (`IDENTIFIER`,`SEPARATOR_STRING`) values (42,NULL);
insert into `dyextn_select_control` (`IDENTIFIER`,`SEPARATOR_STRING`) values (43,NULL);
insert into `dyextn_select_control` (`IDENTIFIER`,`SEPARATOR_STRING`) values (44,NULL);
insert into `dyextn_select_control` (`IDENTIFIER`,`SEPARATOR_STRING`) values (45,NULL);
insert into `dyextn_select_control` (`IDENTIFIER`,`SEPARATOR_STRING`) values (54,NULL);
insert into `dyextn_select_control` (`IDENTIFIER`,`SEPARATOR_STRING`) values (65,NULL);
insert into `dyextn_select_control` (`IDENTIFIER`,`SEPARATOR_STRING`) values (66,NULL);
insert into `dyextn_select_control` (`IDENTIFIER`,`SEPARATOR_STRING`) values (67,NULL);
insert into `dyextn_select_control` (`IDENTIFIER`,`SEPARATOR_STRING`) values (68,NULL);
insert into `dyextn_select_control` (`IDENTIFIER`,`SEPARATOR_STRING`) values (69,NULL);
insert into `dyextn_select_control` (`IDENTIFIER`,`SEPARATOR_STRING`) values (70,NULL);
insert into `dyextn_select_control` (`IDENTIFIER`,`SEPARATOR_STRING`) values (71,NULL);
insert into `dyextn_select_control` (`IDENTIFIER`,`SEPARATOR_STRING`) values (72,NULL);
insert into `dyextn_select_control` (`IDENTIFIER`,`SEPARATOR_STRING`) values (73,NULL);
insert into `dyextn_select_control` (`IDENTIFIER`,`SEPARATOR_STRING`) values (74,NULL);
insert into `dyextn_select_control` (`IDENTIFIER`,`SEPARATOR_STRING`) values (75,NULL);
insert into `dyextn_select_control` (`IDENTIFIER`,`SEPARATOR_STRING`) values (76,NULL);
insert into `dyextn_select_control` (`IDENTIFIER`,`SEPARATOR_STRING`) values (77,NULL);
insert into `dyextn_select_control` (`IDENTIFIER`,`SEPARATOR_STRING`) values (78,NULL);
insert into `dyextn_select_control` (`IDENTIFIER`,`SEPARATOR_STRING`) values (79,NULL);
insert into `dyextn_select_control` (`IDENTIFIER`,`SEPARATOR_STRING`) values (80,NULL);
insert into `dyextn_select_control` (`IDENTIFIER`,`SEPARATOR_STRING`) values (84,NULL);
insert into `dyextn_select_control` (`IDENTIFIER`,`SEPARATOR_STRING`) values (85,NULL);
insert into `dyextn_select_control` (`IDENTIFIER`,`SEPARATOR_STRING`) values (86,NULL);
insert into `dyextn_select_control` (`IDENTIFIER`,`SEPARATOR_STRING`) values (87,NULL);
insert into `dyextn_select_control` (`IDENTIFIER`,`SEPARATOR_STRING`) values (88,NULL);
insert into `dyextn_select_control` (`IDENTIFIER`,`SEPARATOR_STRING`) values (89,NULL);
insert into `dyextn_select_control` (`IDENTIFIER`,`SEPARATOR_STRING`) values (90,NULL);
insert into `dyextn_select_control` (`IDENTIFIER`,`SEPARATOR_STRING`) values (91,NULL);
insert into `dyextn_select_control` (`IDENTIFIER`,`SEPARATOR_STRING`) values (92,NULL);
insert into `dyextn_select_control` (`IDENTIFIER`,`SEPARATOR_STRING`) values (93,NULL);
insert into `dyextn_select_control` (`IDENTIFIER`,`SEPARATOR_STRING`) values (94,NULL);
insert into `dyextn_select_control` (`IDENTIFIER`,`SEPARATOR_STRING`) values (95,NULL);
insert into `dyextn_select_control` (`IDENTIFIER`,`SEPARATOR_STRING`) values (96,NULL);
insert into `dyextn_select_control` (`IDENTIFIER`,`SEPARATOR_STRING`) values (97,NULL);
insert into `dyextn_select_control` (`IDENTIFIER`,`SEPARATOR_STRING`) values (98,NULL);
insert into `dyextn_select_control` (`IDENTIFIER`,`SEPARATOR_STRING`) values (99,NULL);
insert into `dyextn_select_control` (`IDENTIFIER`,`SEPARATOR_STRING`) values (100,NULL);
insert into `dyextn_select_control` (`IDENTIFIER`,`SEPARATOR_STRING`) values (101,NULL);
insert into `dyextn_select_control` (`IDENTIFIER`,`SEPARATOR_STRING`) values (102,NULL);
insert into `dyextn_select_control` (`IDENTIFIER`,`SEPARATOR_STRING`) values (103,NULL);
insert into `dyextn_select_control` (`IDENTIFIER`,`SEPARATOR_STRING`) values (118,NULL);
insert into `dyextn_select_control` (`IDENTIFIER`,`SEPARATOR_STRING`) values (119,NULL);
insert into `dyextn_select_control` (`IDENTIFIER`,`SEPARATOR_STRING`) values (125,NULL);
insert into `dyextn_select_control` (`IDENTIFIER`,`SEPARATOR_STRING`) values (126,NULL);
insert into `dyextn_select_control` (`IDENTIFIER`,`SEPARATOR_STRING`) values (127,NULL);
insert into `dyextn_select_control` (`IDENTIFIER`,`SEPARATOR_STRING`) values (128,NULL);
insert into `dyextn_select_control` (`IDENTIFIER`,`SEPARATOR_STRING`) values (129,NULL);
insert into `dyextn_select_control` (`IDENTIFIER`,`SEPARATOR_STRING`) values (131,NULL);
insert into `dyextn_select_control` (`IDENTIFIER`,`SEPARATOR_STRING`) values (132,NULL);
insert into `dyextn_select_control` (`IDENTIFIER`,`SEPARATOR_STRING`) values (133,NULL);
insert into `dyextn_select_control` (`IDENTIFIER`,`SEPARATOR_STRING`) values (137,NULL);
insert into `dyextn_select_control` (`IDENTIFIER`,`SEPARATOR_STRING`) values (138,NULL);
insert into `dyextn_select_control` (`IDENTIFIER`,`SEPARATOR_STRING`) values (139,NULL);
insert into `dyextn_select_control` (`IDENTIFIER`,`SEPARATOR_STRING`) values (140,NULL);
insert into `dyextn_select_control` (`IDENTIFIER`,`SEPARATOR_STRING`) values (141,NULL);
insert into `dyextn_select_control` (`IDENTIFIER`,`SEPARATOR_STRING`) values (142,NULL);
insert into `dyextn_select_control` (`IDENTIFIER`,`SEPARATOR_STRING`) values (148,NULL);
insert into `dyextn_select_control` (`IDENTIFIER`,`SEPARATOR_STRING`) values (149,NULL);
insert into `dyextn_select_control` (`IDENTIFIER`,`SEPARATOR_STRING`) values (150,NULL);
insert into `dyextn_select_control` (`IDENTIFIER`,`SEPARATOR_STRING`) values (151,NULL);
insert into `dyextn_select_control` (`IDENTIFIER`,`SEPARATOR_STRING`) values (152,NULL);
insert into `dyextn_select_control` (`IDENTIFIER`,`SEPARATOR_STRING`) values (153,NULL);
insert into `dyextn_select_control` (`IDENTIFIER`,`SEPARATOR_STRING`) values (154,NULL);
insert into `dyextn_select_control` (`IDENTIFIER`,`SEPARATOR_STRING`) values (158,NULL);
insert into `dyextn_select_control` (`IDENTIFIER`,`SEPARATOR_STRING`) values (159,NULL);
insert into `dyextn_select_control` (`IDENTIFIER`,`SEPARATOR_STRING`) values (160,NULL);
insert into `dyextn_select_control` (`IDENTIFIER`,`SEPARATOR_STRING`) values (161,NULL);

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
insert into `dyextn_string_concept_value` (`IDENTIFIER`,`VALUE`) values (12,'');
insert into `dyextn_string_concept_value` (`IDENTIFIER`,`VALUE`) values (15,'');
insert into `dyextn_string_concept_value` (`IDENTIFIER`,`VALUE`) values (16,'');
insert into `dyextn_string_concept_value` (`IDENTIFIER`,`VALUE`) values (17,'');
insert into `dyextn_string_concept_value` (`IDENTIFIER`,`VALUE`) values (20,'');
insert into `dyextn_string_concept_value` (`IDENTIFIER`,`VALUE`) values (21,'');
insert into `dyextn_string_concept_value` (`IDENTIFIER`,`VALUE`) values (22,'');
insert into `dyextn_string_concept_value` (`IDENTIFIER`,`VALUE`) values (23,'');
insert into `dyextn_string_concept_value` (`IDENTIFIER`,`VALUE`) values (24,'');
insert into `dyextn_string_concept_value` (`IDENTIFIER`,`VALUE`) values (25,'');
insert into `dyextn_string_concept_value` (`IDENTIFIER`,`VALUE`) values (26,'');
insert into `dyextn_string_concept_value` (`IDENTIFIER`,`VALUE`) values (27,'');
insert into `dyextn_string_concept_value` (`IDENTIFIER`,`VALUE`) values (28,'');
insert into `dyextn_string_concept_value` (`IDENTIFIER`,`VALUE`) values (29,'');
insert into `dyextn_string_concept_value` (`IDENTIFIER`,`VALUE`) values (32,'');
insert into `dyextn_string_concept_value` (`IDENTIFIER`,`VALUE`) values (33,'');
insert into `dyextn_string_concept_value` (`IDENTIFIER`,`VALUE`) values (34,'');
insert into `dyextn_string_concept_value` (`IDENTIFIER`,`VALUE`) values (35,'');
insert into `dyextn_string_concept_value` (`IDENTIFIER`,`VALUE`) values (36,'');
insert into `dyextn_string_concept_value` (`IDENTIFIER`,`VALUE`) values (37,'');
insert into `dyextn_string_concept_value` (`IDENTIFIER`,`VALUE`) values (38,'');
insert into `dyextn_string_concept_value` (`IDENTIFIER`,`VALUE`) values (40,'');
insert into `dyextn_string_concept_value` (`IDENTIFIER`,`VALUE`) values (41,'');
insert into `dyextn_string_concept_value` (`IDENTIFIER`,`VALUE`) values (42,'');
insert into `dyextn_string_concept_value` (`IDENTIFIER`,`VALUE`) values (43,'');
insert into `dyextn_string_concept_value` (`IDENTIFIER`,`VALUE`) values (44,'');
insert into `dyextn_string_concept_value` (`IDENTIFIER`,`VALUE`) values (45,'');
insert into `dyextn_string_concept_value` (`IDENTIFIER`,`VALUE`) values (46,'');
insert into `dyextn_string_concept_value` (`IDENTIFIER`,`VALUE`) values (47,'');
insert into `dyextn_string_concept_value` (`IDENTIFIER`,`VALUE`) values (48,'');
insert into `dyextn_string_concept_value` (`IDENTIFIER`,`VALUE`) values (49,'');
insert into `dyextn_string_concept_value` (`IDENTIFIER`,`VALUE`) values (50,'');
insert into `dyextn_string_concept_value` (`IDENTIFIER`,`VALUE`) values (51,'');
insert into `dyextn_string_concept_value` (`IDENTIFIER`,`VALUE`) values (52,'');
insert into `dyextn_string_concept_value` (`IDENTIFIER`,`VALUE`) values (53,'');
insert into `dyextn_string_concept_value` (`IDENTIFIER`,`VALUE`) values (54,'');
insert into `dyextn_string_concept_value` (`IDENTIFIER`,`VALUE`) values (55,'');
insert into `dyextn_string_concept_value` (`IDENTIFIER`,`VALUE`) values (56,'');
insert into `dyextn_string_concept_value` (`IDENTIFIER`,`VALUE`) values (57,'');
insert into `dyextn_string_concept_value` (`IDENTIFIER`,`VALUE`) values (60,'');
insert into `dyextn_string_concept_value` (`IDENTIFIER`,`VALUE`) values (61,'');
insert into `dyextn_string_concept_value` (`IDENTIFIER`,`VALUE`) values (63,'');
insert into `dyextn_string_concept_value` (`IDENTIFIER`,`VALUE`) values (64,'');
insert into `dyextn_string_concept_value` (`IDENTIFIER`,`VALUE`) values (65,'');
insert into `dyextn_string_concept_value` (`IDENTIFIER`,`VALUE`) values (70,'');
insert into `dyextn_string_concept_value` (`IDENTIFIER`,`VALUE`) values (71,'');

/*Table structure for table `dyextn_string_type_info` */

CREATE TABLE `dyextn_string_type_info` (
  `IDENTIFIER` bigint(20) NOT NULL,
  `MAX_SIZE` int(11) default NULL,
  PRIMARY KEY  (`IDENTIFIER`),
  KEY `FKDA35FE02E5294FA3` (`IDENTIFIER`),
  CONSTRAINT `FKDA35FE02E5294FA3` FOREIGN KEY (`IDENTIFIER`) REFERENCES `dyextn_attribute_type_info` (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `dyextn_string_type_info` */

insert into `dyextn_string_type_info` (`IDENTIFIER`,`MAX_SIZE`) values (1,255);
insert into `dyextn_string_type_info` (`IDENTIFIER`,`MAX_SIZE`) values (2,255);
insert into `dyextn_string_type_info` (`IDENTIFIER`,`MAX_SIZE`) values (3,255);
insert into `dyextn_string_type_info` (`IDENTIFIER`,`MAX_SIZE`) values (5,255);
insert into `dyextn_string_type_info` (`IDENTIFIER`,`MAX_SIZE`) values (6,255);
insert into `dyextn_string_type_info` (`IDENTIFIER`,`MAX_SIZE`) values (8,255);
insert into `dyextn_string_type_info` (`IDENTIFIER`,`MAX_SIZE`) values (9,255);
insert into `dyextn_string_type_info` (`IDENTIFIER`,`MAX_SIZE`) values (10,255);
insert into `dyextn_string_type_info` (`IDENTIFIER`,`MAX_SIZE`) values (11,255);
insert into `dyextn_string_type_info` (`IDENTIFIER`,`MAX_SIZE`) values (12,255);
insert into `dyextn_string_type_info` (`IDENTIFIER`,`MAX_SIZE`) values (13,255);
insert into `dyextn_string_type_info` (`IDENTIFIER`,`MAX_SIZE`) values (14,255);
insert into `dyextn_string_type_info` (`IDENTIFIER`,`MAX_SIZE`) values (25,255);
insert into `dyextn_string_type_info` (`IDENTIFIER`,`MAX_SIZE`) values (26,255);
insert into `dyextn_string_type_info` (`IDENTIFIER`,`MAX_SIZE`) values (27,255);
insert into `dyextn_string_type_info` (`IDENTIFIER`,`MAX_SIZE`) values (32,255);
insert into `dyextn_string_type_info` (`IDENTIFIER`,`MAX_SIZE`) values (33,255);
insert into `dyextn_string_type_info` (`IDENTIFIER`,`MAX_SIZE`) values (38,255);
insert into `dyextn_string_type_info` (`IDENTIFIER`,`MAX_SIZE`) values (39,255);
insert into `dyextn_string_type_info` (`IDENTIFIER`,`MAX_SIZE`) values (40,255);
insert into `dyextn_string_type_info` (`IDENTIFIER`,`MAX_SIZE`) values (41,255);
insert into `dyextn_string_type_info` (`IDENTIFIER`,`MAX_SIZE`) values (43,255);
insert into `dyextn_string_type_info` (`IDENTIFIER`,`MAX_SIZE`) values (45,255);
insert into `dyextn_string_type_info` (`IDENTIFIER`,`MAX_SIZE`) values (52,255);
insert into `dyextn_string_type_info` (`IDENTIFIER`,`MAX_SIZE`) values (58,255);
insert into `dyextn_string_type_info` (`IDENTIFIER`,`MAX_SIZE`) values (65,255);
insert into `dyextn_string_type_info` (`IDENTIFIER`,`MAX_SIZE`) values (66,255);
insert into `dyextn_string_type_info` (`IDENTIFIER`,`MAX_SIZE`) values (67,255);
insert into `dyextn_string_type_info` (`IDENTIFIER`,`MAX_SIZE`) values (68,255);
insert into `dyextn_string_type_info` (`IDENTIFIER`,`MAX_SIZE`) values (70,255);
insert into `dyextn_string_type_info` (`IDENTIFIER`,`MAX_SIZE`) values (73,255);
insert into `dyextn_string_type_info` (`IDENTIFIER`,`MAX_SIZE`) values (77,255);
insert into `dyextn_string_type_info` (`IDENTIFIER`,`MAX_SIZE`) values (80,255);
insert into `dyextn_string_type_info` (`IDENTIFIER`,`MAX_SIZE`) values (81,255);
insert into `dyextn_string_type_info` (`IDENTIFIER`,`MAX_SIZE`) values (84,255);
insert into `dyextn_string_type_info` (`IDENTIFIER`,`MAX_SIZE`) values (86,255);
insert into `dyextn_string_type_info` (`IDENTIFIER`,`MAX_SIZE`) values (87,255);
insert into `dyextn_string_type_info` (`IDENTIFIER`,`MAX_SIZE`) values (88,255);
insert into `dyextn_string_type_info` (`IDENTIFIER`,`MAX_SIZE`) values (90,255);
insert into `dyextn_string_type_info` (`IDENTIFIER`,`MAX_SIZE`) values (91,255);
insert into `dyextn_string_type_info` (`IDENTIFIER`,`MAX_SIZE`) values (97,255);
insert into `dyextn_string_type_info` (`IDENTIFIER`,`MAX_SIZE`) values (98,255);
insert into `dyextn_string_type_info` (`IDENTIFIER`,`MAX_SIZE`) values (99,255);
insert into `dyextn_string_type_info` (`IDENTIFIER`,`MAX_SIZE`) values (100,255);
insert into `dyextn_string_type_info` (`IDENTIFIER`,`MAX_SIZE`) values (101,255);
insert into `dyextn_string_type_info` (`IDENTIFIER`,`MAX_SIZE`) values (102,255);
insert into `dyextn_string_type_info` (`IDENTIFIER`,`MAX_SIZE`) values (103,255);
insert into `dyextn_string_type_info` (`IDENTIFIER`,`MAX_SIZE`) values (104,255);
insert into `dyextn_string_type_info` (`IDENTIFIER`,`MAX_SIZE`) values (107,255);
insert into `dyextn_string_type_info` (`IDENTIFIER`,`MAX_SIZE`) values (108,255);
insert into `dyextn_string_type_info` (`IDENTIFIER`,`MAX_SIZE`) values (111,255);
insert into `dyextn_string_type_info` (`IDENTIFIER`,`MAX_SIZE`) values (112,255);
insert into `dyextn_string_type_info` (`IDENTIFIER`,`MAX_SIZE`) values (122,255);
insert into `dyextn_string_type_info` (`IDENTIFIER`,`MAX_SIZE`) values (123,255);
insert into `dyextn_string_type_info` (`IDENTIFIER`,`MAX_SIZE`) values (124,255);
insert into `dyextn_string_type_info` (`IDENTIFIER`,`MAX_SIZE`) values (135,255);
insert into `dyextn_string_type_info` (`IDENTIFIER`,`MAX_SIZE`) values (141,255);

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

insert into `dyextn_table_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`ABSTRACT_ENTITY_ID`) values (1,'CONSRT_323',2);
insert into `dyextn_table_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`ABSTRACT_ENTITY_ID`) values (6,'CONSRT_318',7);
insert into `dyextn_table_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`ABSTRACT_ENTITY_ID`) values (7,'CONSRT_14',8);
insert into `dyextn_table_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`ABSTRACT_ENTITY_ID`) values (12,'CONSRT_127',13);
insert into `dyextn_table_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`ABSTRACT_ENTITY_ID`) values (22,'CONSRT_145',23);
insert into `dyextn_table_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`ABSTRACT_ENTITY_ID`) values (23,'CONSRT_168',24);
insert into `dyextn_table_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`ABSTRACT_ENTITY_ID`) values (24,'CONSRT_283',25);
insert into `dyextn_table_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`ABSTRACT_ENTITY_ID`) values (27,'CONSRT_232',28);
insert into `dyextn_table_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`ABSTRACT_ENTITY_ID`) values (30,'CONSRT_214',31);
insert into `dyextn_table_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`ABSTRACT_ENTITY_ID`) values (33,'CONSRT_206',34);
insert into `dyextn_table_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`ABSTRACT_ENTITY_ID`) values (36,'CONSRT_185',37);
insert into `dyextn_table_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`ABSTRACT_ENTITY_ID`) values (39,'CONSRT_111',40);
insert into `dyextn_table_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`ABSTRACT_ENTITY_ID`) values (51,'CONSRT_158',52);
insert into `dyextn_table_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`ABSTRACT_ENTITY_ID`) values (52,'CONSRT_298',53);
insert into `dyextn_table_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`ABSTRACT_ENTITY_ID`) values (59,'CONSRT_162',60);
insert into `dyextn_table_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`ABSTRACT_ENTITY_ID`) values (64,'CONSRT_24',65);
insert into `dyextn_table_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`ABSTRACT_ENTITY_ID`) values (65,'CONSRT_310',66);
insert into `dyextn_table_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`ABSTRACT_ENTITY_ID`) values (68,'CONSRT_246',69);
insert into `dyextn_table_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`ABSTRACT_ENTITY_ID`) values (71,'CONSRT_210',72);
insert into `dyextn_table_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`ABSTRACT_ENTITY_ID`) values (86,'CONSRT_314',87);
insert into `dyextn_table_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`ABSTRACT_ENTITY_ID`) values (88,'CONSRT_306',89);
insert into `dyextn_table_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`ABSTRACT_ENTITY_ID`) values (90,'CONSRT_302',91);
insert into `dyextn_table_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`ABSTRACT_ENTITY_ID`) values (92,'CONSRT_291',93);
insert into `dyextn_table_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`ABSTRACT_ENTITY_ID`) values (97,'CONSRT_287',98);
insert into `dyextn_table_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`ABSTRACT_ENTITY_ID`) values (99,'CONSRT_279',100);
insert into `dyextn_table_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`ABSTRACT_ENTITY_ID`) values (101,'CONSRT_275',102);
insert into `dyextn_table_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`ABSTRACT_ENTITY_ID`) values (103,'CONSRT_271',104);
insert into `dyextn_table_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`ABSTRACT_ENTITY_ID`) values (105,'CONSRT_138',106);
insert into `dyextn_table_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`ABSTRACT_ENTITY_ID`) values (107,'CONSRT_236',108);
insert into `dyextn_table_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`ABSTRACT_ENTITY_ID`) values (112,'CONSRT_123',113);
insert into `dyextn_table_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`ABSTRACT_ENTITY_ID`) values (115,'CONSRT_102',116);
insert into `dyextn_table_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`ABSTRACT_ENTITY_ID`) values (123,'CONSRT_74',124);
insert into `dyextn_table_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`ABSTRACT_ENTITY_ID`) values (131,'CONSRT_59',132);
insert into `dyextn_table_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`ABSTRACT_ENTITY_ID`) values (133,'CONSRT_119',134);
insert into `dyextn_table_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`ABSTRACT_ENTITY_ID`) values (134,'CONSRT_84',135);
insert into `dyextn_table_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`ABSTRACT_ENTITY_ID`) values (145,'CONSRT_46',146);
insert into `dyextn_table_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`ABSTRACT_ENTITY_ID`) values (148,'CONSRT_54',149);
insert into `dyextn_table_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`ABSTRACT_ENTITY_ID`) values (157,'CONSRT_115',158);
insert into `dyextn_table_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`ABSTRACT_ENTITY_ID`) values (160,'CONSRT_197',161);
insert into `dyextn_table_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`ABSTRACT_ENTITY_ID`) values (171,'CONSRT_96',172);
insert into `dyextn_table_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`ABSTRACT_ENTITY_ID`) values (175,'CONSRT_267',176);
insert into `dyextn_table_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`ABSTRACT_ENTITY_ID`) values (177,'CONSRT_92',178);
insert into `dyextn_table_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`ABSTRACT_ENTITY_ID`) values (179,'CONSRT_80',180);
insert into `dyextn_table_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`ABSTRACT_ENTITY_ID`) values (181,'CONSRT_2',182);
insert into `dyextn_table_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`ABSTRACT_ENTITY_ID`) values (187,'CONSRT_263',188);
insert into `dyextn_table_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`ABSTRACT_ENTITY_ID`) values (190,'CONSRT_250',191);
insert into `dyextn_table_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`ABSTRACT_ENTITY_ID`) values (205,'CONSRT_218',206);
insert into `dyextn_table_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`ABSTRACT_ENTITY_ID`) values (215,'CONSRT_202',216);
insert into `dyextn_table_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`ABSTRACT_ENTITY_ID`) values (220,'CONSRT_179',221);
insert into `dyextn_table_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`ABSTRACT_ENTITY_ID`) values (223,'CONSRT_193',224);
insert into `dyextn_table_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`ABSTRACT_ENTITY_ID`) values (232,'CONSRT_154',233);
insert into `dyextn_table_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`ABSTRACT_ENTITY_ID`) values (235,'CONSRT_150',236);
insert into `dyextn_table_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`ABSTRACT_ENTITY_ID`) values (238,'CONSRT_70',239);
insert into `dyextn_table_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`ABSTRACT_ENTITY_ID`) values (262,'CONSRT_32',263);
insert into `dyextn_table_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`ABSTRACT_ENTITY_ID`) values (265,'CONSRT_36',266);
insert into `dyextn_table_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`ABSTRACT_ENTITY_ID`) values (268,'CONSRT_40',269);
insert into `dyextn_table_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`ABSTRACT_ENTITY_ID`) values (270,'CONSRT_66',271);
insert into `dyextn_table_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`ABSTRACT_ENTITY_ID`) values (279,'CONSRT_50',280);
insert into `dyextn_table_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`ABSTRACT_ENTITY_ID`) values (282,'CONSRT_20',283);
insert into `dyextn_table_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`ABSTRACT_ENTITY_ID`) values (284,'CONSRT_189',285);
insert into `dyextn_table_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`ABSTRACT_ENTITY_ID`) values (286,'CONSRT_228',287);
insert into `dyextn_table_properties` (`IDENTIFIER`,`CONSTRAINT_NAME`,`ABSTRACT_ENTITY_ID`) values (288,'CONSRT_241',289);

/*Table structure for table `dyextn_tagged_value` */

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
insert into `dyextn_tagged_value` (`IDENTIFIER`,`T_KEY`,`T_VALUE`,`ABSTRACT_METADATA_ID`) values (3,'PackageName','edu.wustl.cider.domain',1);

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

insert into `dyextn_textfield` (`IDENTIFIER`,`NO_OF_COLUMNS`,`IS_PASSWORD`,`IS_URL`) values (1,20,'\0','\0');
insert into `dyextn_textfield` (`IDENTIFIER`,`NO_OF_COLUMNS`,`IS_PASSWORD`,`IS_URL`) values (2,20,'\0','\0');
insert into `dyextn_textfield` (`IDENTIFIER`,`NO_OF_COLUMNS`,`IS_PASSWORD`,`IS_URL`) values (3,20,'\0','\0');
insert into `dyextn_textfield` (`IDENTIFIER`,`NO_OF_COLUMNS`,`IS_PASSWORD`,`IS_URL`) values (4,20,'\0','\0');
insert into `dyextn_textfield` (`IDENTIFIER`,`NO_OF_COLUMNS`,`IS_PASSWORD`,`IS_URL`) values (7,20,'\0','\0');
insert into `dyextn_textfield` (`IDENTIFIER`,`NO_OF_COLUMNS`,`IS_PASSWORD`,`IS_URL`) values (8,10,NULL,NULL);
insert into `dyextn_textfield` (`IDENTIFIER`,`NO_OF_COLUMNS`,`IS_PASSWORD`,`IS_URL`) values (9,10,NULL,NULL);
insert into `dyextn_textfield` (`IDENTIFIER`,`NO_OF_COLUMNS`,`IS_PASSWORD`,`IS_URL`) values (10,20,'\0','\0');
insert into `dyextn_textfield` (`IDENTIFIER`,`NO_OF_COLUMNS`,`IS_PASSWORD`,`IS_URL`) values (12,20,'\0','\0');
insert into `dyextn_textfield` (`IDENTIFIER`,`NO_OF_COLUMNS`,`IS_PASSWORD`,`IS_URL`) values (13,10,NULL,NULL);
insert into `dyextn_textfield` (`IDENTIFIER`,`NO_OF_COLUMNS`,`IS_PASSWORD`,`IS_URL`) values (14,10,NULL,NULL);
insert into `dyextn_textfield` (`IDENTIFIER`,`NO_OF_COLUMNS`,`IS_PASSWORD`,`IS_URL`) values (15,20,'\0','\0');
insert into `dyextn_textfield` (`IDENTIFIER`,`NO_OF_COLUMNS`,`IS_PASSWORD`,`IS_URL`) values (16,20,'\0','\0');
insert into `dyextn_textfield` (`IDENTIFIER`,`NO_OF_COLUMNS`,`IS_PASSWORD`,`IS_URL`) values (18,20,'\0','\0');
insert into `dyextn_textfield` (`IDENTIFIER`,`NO_OF_COLUMNS`,`IS_PASSWORD`,`IS_URL`) values (19,20,'\0','\0');
insert into `dyextn_textfield` (`IDENTIFIER`,`NO_OF_COLUMNS`,`IS_PASSWORD`,`IS_URL`) values (20,20,'\0','\0');
insert into `dyextn_textfield` (`IDENTIFIER`,`NO_OF_COLUMNS`,`IS_PASSWORD`,`IS_URL`) values (21,10,NULL,NULL);
insert into `dyextn_textfield` (`IDENTIFIER`,`NO_OF_COLUMNS`,`IS_PASSWORD`,`IS_URL`) values (26,20,'\0','\0');
insert into `dyextn_textfield` (`IDENTIFIER`,`NO_OF_COLUMNS`,`IS_PASSWORD`,`IS_URL`) values (27,20,'\0','\0');
insert into `dyextn_textfield` (`IDENTIFIER`,`NO_OF_COLUMNS`,`IS_PASSWORD`,`IS_URL`) values (30,20,'\0','\0');
insert into `dyextn_textfield` (`IDENTIFIER`,`NO_OF_COLUMNS`,`IS_PASSWORD`,`IS_URL`) values (31,20,'\0','\0');
insert into `dyextn_textfield` (`IDENTIFIER`,`NO_OF_COLUMNS`,`IS_PASSWORD`,`IS_URL`) values (48,20,'\0','\0');
insert into `dyextn_textfield` (`IDENTIFIER`,`NO_OF_COLUMNS`,`IS_PASSWORD`,`IS_URL`) values (49,20,'\0','\0');
insert into `dyextn_textfield` (`IDENTIFIER`,`NO_OF_COLUMNS`,`IS_PASSWORD`,`IS_URL`) values (50,20,'\0','\0');
insert into `dyextn_textfield` (`IDENTIFIER`,`NO_OF_COLUMNS`,`IS_PASSWORD`,`IS_URL`) values (53,20,'\0','\0');
insert into `dyextn_textfield` (`IDENTIFIER`,`NO_OF_COLUMNS`,`IS_PASSWORD`,`IS_URL`) values (55,20,'\0','\0');
insert into `dyextn_textfield` (`IDENTIFIER`,`NO_OF_COLUMNS`,`IS_PASSWORD`,`IS_URL`) values (56,20,'\0','\0');
insert into `dyextn_textfield` (`IDENTIFIER`,`NO_OF_COLUMNS`,`IS_PASSWORD`,`IS_URL`) values (57,20,'\0','\0');
insert into `dyextn_textfield` (`IDENTIFIER`,`NO_OF_COLUMNS`,`IS_PASSWORD`,`IS_URL`) values (58,20,'\0','\0');
insert into `dyextn_textfield` (`IDENTIFIER`,`NO_OF_COLUMNS`,`IS_PASSWORD`,`IS_URL`) values (59,20,'\0','\0');
insert into `dyextn_textfield` (`IDENTIFIER`,`NO_OF_COLUMNS`,`IS_PASSWORD`,`IS_URL`) values (60,20,'\0','\0');
insert into `dyextn_textfield` (`IDENTIFIER`,`NO_OF_COLUMNS`,`IS_PASSWORD`,`IS_URL`) values (61,20,'\0','\0');
insert into `dyextn_textfield` (`IDENTIFIER`,`NO_OF_COLUMNS`,`IS_PASSWORD`,`IS_URL`) values (62,20,'\0','\0');
insert into `dyextn_textfield` (`IDENTIFIER`,`NO_OF_COLUMNS`,`IS_PASSWORD`,`IS_URL`) values (63,20,'\0','\0');
insert into `dyextn_textfield` (`IDENTIFIER`,`NO_OF_COLUMNS`,`IS_PASSWORD`,`IS_URL`) values (64,20,'\0','\0');
insert into `dyextn_textfield` (`IDENTIFIER`,`NO_OF_COLUMNS`,`IS_PASSWORD`,`IS_URL`) values (83,10,NULL,NULL);
insert into `dyextn_textfield` (`IDENTIFIER`,`NO_OF_COLUMNS`,`IS_PASSWORD`,`IS_URL`) values (104,20,'\0','\0');
insert into `dyextn_textfield` (`IDENTIFIER`,`NO_OF_COLUMNS`,`IS_PASSWORD`,`IS_URL`) values (105,20,'\0','\0');
insert into `dyextn_textfield` (`IDENTIFIER`,`NO_OF_COLUMNS`,`IS_PASSWORD`,`IS_URL`) values (106,20,'\0','\0');
insert into `dyextn_textfield` (`IDENTIFIER`,`NO_OF_COLUMNS`,`IS_PASSWORD`,`IS_URL`) values (109,10,NULL,NULL);
insert into `dyextn_textfield` (`IDENTIFIER`,`NO_OF_COLUMNS`,`IS_PASSWORD`,`IS_URL`) values (110,20,'\0','\0');
insert into `dyextn_textfield` (`IDENTIFIER`,`NO_OF_COLUMNS`,`IS_PASSWORD`,`IS_URL`) values (111,20,'\0','\0');
insert into `dyextn_textfield` (`IDENTIFIER`,`NO_OF_COLUMNS`,`IS_PASSWORD`,`IS_URL`) values (112,20,'\0','\0');
insert into `dyextn_textfield` (`IDENTIFIER`,`NO_OF_COLUMNS`,`IS_PASSWORD`,`IS_URL`) values (113,20,'\0','\0');
insert into `dyextn_textfield` (`IDENTIFIER`,`NO_OF_COLUMNS`,`IS_PASSWORD`,`IS_URL`) values (114,20,'\0','\0');
insert into `dyextn_textfield` (`IDENTIFIER`,`NO_OF_COLUMNS`,`IS_PASSWORD`,`IS_URL`) values (115,20,'\0','\0');
insert into `dyextn_textfield` (`IDENTIFIER`,`NO_OF_COLUMNS`,`IS_PASSWORD`,`IS_URL`) values (116,20,'\0','\0');
insert into `dyextn_textfield` (`IDENTIFIER`,`NO_OF_COLUMNS`,`IS_PASSWORD`,`IS_URL`) values (117,20,'\0','\0');
insert into `dyextn_textfield` (`IDENTIFIER`,`NO_OF_COLUMNS`,`IS_PASSWORD`,`IS_URL`) values (120,20,'\0','\0');
insert into `dyextn_textfield` (`IDENTIFIER`,`NO_OF_COLUMNS`,`IS_PASSWORD`,`IS_URL`) values (121,20,'\0','\0');
insert into `dyextn_textfield` (`IDENTIFIER`,`NO_OF_COLUMNS`,`IS_PASSWORD`,`IS_URL`) values (122,20,'\0','\0');
insert into `dyextn_textfield` (`IDENTIFIER`,`NO_OF_COLUMNS`,`IS_PASSWORD`,`IS_URL`) values (124,10,NULL,NULL);
insert into `dyextn_textfield` (`IDENTIFIER`,`NO_OF_COLUMNS`,`IS_PASSWORD`,`IS_URL`) values (130,20,'\0','\0');
insert into `dyextn_textfield` (`IDENTIFIER`,`NO_OF_COLUMNS`,`IS_PASSWORD`,`IS_URL`) values (134,20,'\0','\0');
insert into `dyextn_textfield` (`IDENTIFIER`,`NO_OF_COLUMNS`,`IS_PASSWORD`,`IS_URL`) values (135,20,'\0','\0');
insert into `dyextn_textfield` (`IDENTIFIER`,`NO_OF_COLUMNS`,`IS_PASSWORD`,`IS_URL`) values (136,20,'\0','\0');
insert into `dyextn_textfield` (`IDENTIFIER`,`NO_OF_COLUMNS`,`IS_PASSWORD`,`IS_URL`) values (145,10,NULL,NULL);
insert into `dyextn_textfield` (`IDENTIFIER`,`NO_OF_COLUMNS`,`IS_PASSWORD`,`IS_URL`) values (146,20,'\0','\0');
insert into `dyextn_textfield` (`IDENTIFIER`,`NO_OF_COLUMNS`,`IS_PASSWORD`,`IS_URL`) values (147,20,'\0','\0');
insert into `dyextn_textfield` (`IDENTIFIER`,`NO_OF_COLUMNS`,`IS_PASSWORD`,`IS_URL`) values (155,20,'\0','\0');
insert into `dyextn_textfield` (`IDENTIFIER`,`NO_OF_COLUMNS`,`IS_PASSWORD`,`IS_URL`) values (156,20,'\0','\0');

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

insert into `intra_model_association` (`ASSOCIATION_ID`,`DE_ASSOCIATION_ID`) values (1,56);
insert into `intra_model_association` (`ASSOCIATION_ID`,`DE_ASSOCIATION_ID`) values (2,81);
insert into `intra_model_association` (`ASSOCIATION_ID`,`DE_ASSOCIATION_ID`) values (3,80);
insert into `intra_model_association` (`ASSOCIATION_ID`,`DE_ASSOCIATION_ID`) values (4,74);
insert into `intra_model_association` (`ASSOCIATION_ID`,`DE_ASSOCIATION_ID`) values (5,71);
insert into `intra_model_association` (`ASSOCIATION_ID`,`DE_ASSOCIATION_ID`) values (6,68);
insert into `intra_model_association` (`ASSOCIATION_ID`,`DE_ASSOCIATION_ID`) values (7,64);
insert into `intra_model_association` (`ASSOCIATION_ID`,`DE_ASSOCIATION_ID`) values (8,59);
insert into `intra_model_association` (`ASSOCIATION_ID`,`DE_ASSOCIATION_ID`) values (9,55);
insert into `intra_model_association` (`ASSOCIATION_ID`,`DE_ASSOCIATION_ID`) values (10,51);
insert into `intra_model_association` (`ASSOCIATION_ID`,`DE_ASSOCIATION_ID`) values (11,42);
insert into `intra_model_association` (`ASSOCIATION_ID`,`DE_ASSOCIATION_ID`) values (12,39);
insert into `intra_model_association` (`ASSOCIATION_ID`,`DE_ASSOCIATION_ID`) values (13,36);
insert into `intra_model_association` (`ASSOCIATION_ID`,`DE_ASSOCIATION_ID`) values (14,33);
insert into `intra_model_association` (`ASSOCIATION_ID`,`DE_ASSOCIATION_ID`) values (15,30);
insert into `intra_model_association` (`ASSOCIATION_ID`,`DE_ASSOCIATION_ID`) values (16,27);
insert into `intra_model_association` (`ASSOCIATION_ID`,`DE_ASSOCIATION_ID`) values (17,207);
insert into `intra_model_association` (`ASSOCIATION_ID`,`DE_ASSOCIATION_ID`) values (18,275);
insert into `intra_model_association` (`ASSOCIATION_ID`,`DE_ASSOCIATION_ID`) values (19,273);
insert into `intra_model_association` (`ASSOCIATION_ID`,`DE_ASSOCIATION_ID`) values (20,270);
insert into `intra_model_association` (`ASSOCIATION_ID`,`DE_ASSOCIATION_ID`) values (21,293);
insert into `intra_model_association` (`ASSOCIATION_ID`,`DE_ASSOCIATION_ID`) values (22,292);
insert into `intra_model_association` (`ASSOCIATION_ID`,`DE_ASSOCIATION_ID`) values (23,125);
insert into `intra_model_association` (`ASSOCIATION_ID`,`DE_ASSOCIATION_ID`) values (24,291);
insert into `intra_model_association` (`ASSOCIATION_ID`,`DE_ASSOCIATION_ID`) values (25,290);
insert into `intra_model_association` (`ASSOCIATION_ID`,`DE_ASSOCIATION_ID`) values (26,282);
insert into `intra_model_association` (`ASSOCIATION_ID`,`DE_ASSOCIATION_ID`) values (27,279);
insert into `intra_model_association` (`ASSOCIATION_ID`,`DE_ASSOCIATION_ID`) values (28,268);
insert into `intra_model_association` (`ASSOCIATION_ID`,`DE_ASSOCIATION_ID`) values (29,265);
insert into `intra_model_association` (`ASSOCIATION_ID`,`DE_ASSOCIATION_ID`) values (30,262);
insert into `intra_model_association` (`ASSOCIATION_ID`,`DE_ASSOCIATION_ID`) values (31,232);
insert into `intra_model_association` (`ASSOCIATION_ID`,`DE_ASSOCIATION_ID`) values (32,228);
insert into `intra_model_association` (`ASSOCIATION_ID`,`DE_ASSOCIATION_ID`) values (33,226);
insert into `intra_model_association` (`ASSOCIATION_ID`,`DE_ASSOCIATION_ID`) values (34,223);
insert into `intra_model_association` (`ASSOCIATION_ID`,`DE_ASSOCIATION_ID`) values (35,222);
insert into `intra_model_association` (`ASSOCIATION_ID`,`DE_ASSOCIATION_ID`) values (36,84);
insert into `intra_model_association` (`ASSOCIATION_ID`,`DE_ASSOCIATION_ID`) values (37,22);
insert into `intra_model_association` (`ASSOCIATION_ID`,`DE_ASSOCIATION_ID`) values (38,12);
insert into `intra_model_association` (`ASSOCIATION_ID`,`DE_ASSOCIATION_ID`) values (39,220);
insert into `intra_model_association` (`ASSOCIATION_ID`,`DE_ASSOCIATION_ID`) values (40,162);
insert into `intra_model_association` (`ASSOCIATION_ID`,`DE_ASSOCIATION_ID`) values (41,219);
insert into `intra_model_association` (`ASSOCIATION_ID`,`DE_ASSOCIATION_ID`) values (42,215);
insert into `intra_model_association` (`ASSOCIATION_ID`,`DE_ASSOCIATION_ID`) values (43,205);
insert into `intra_model_association` (`ASSOCIATION_ID`,`DE_ASSOCIATION_ID`) values (44,204);
insert into `intra_model_association` (`ASSOCIATION_ID`,`DE_ASSOCIATION_ID`) values (45,193);
insert into `intra_model_association` (`ASSOCIATION_ID`,`DE_ASSOCIATION_ID`) values (46,192);
insert into `intra_model_association` (`ASSOCIATION_ID`,`DE_ASSOCIATION_ID`) values (47,190);
insert into `intra_model_association` (`ASSOCIATION_ID`,`DE_ASSOCIATION_ID`) values (48,187);
insert into `intra_model_association` (`ASSOCIATION_ID`,`DE_ASSOCIATION_ID`) values (49,186);
insert into `intra_model_association` (`ASSOCIATION_ID`,`DE_ASSOCIATION_ID`) values (50,185);
insert into `intra_model_association` (`ASSOCIATION_ID`,`DE_ASSOCIATION_ID`) values (51,184);
insert into `intra_model_association` (`ASSOCIATION_ID`,`DE_ASSOCIATION_ID`) values (52,183);
insert into `intra_model_association` (`ASSOCIATION_ID`,`DE_ASSOCIATION_ID`) values (53,235);
insert into `intra_model_association` (`ASSOCIATION_ID`,`DE_ASSOCIATION_ID`) values (54,238);
insert into `intra_model_association` (`ASSOCIATION_ID`,`DE_ASSOCIATION_ID`) values (55,241);
insert into `intra_model_association` (`ASSOCIATION_ID`,`DE_ASSOCIATION_ID`) values (56,167);
insert into `intra_model_association` (`ASSOCIATION_ID`,`DE_ASSOCIATION_ID`) values (57,166);
insert into `intra_model_association` (`ASSOCIATION_ID`,`DE_ASSOCIATION_ID`) values (58,165);
insert into `intra_model_association` (`ASSOCIATION_ID`,`DE_ASSOCIATION_ID`) values (59,160);
insert into `intra_model_association` (`ASSOCIATION_ID`,`DE_ASSOCIATION_ID`) values (60,157);
insert into `intra_model_association` (`ASSOCIATION_ID`,`DE_ASSOCIATION_ID`) values (61,152);
insert into `intra_model_association` (`ASSOCIATION_ID`,`DE_ASSOCIATION_ID`) values (62,148);
insert into `intra_model_association` (`ASSOCIATION_ID`,`DE_ASSOCIATION_ID`) values (63,145);
insert into `intra_model_association` (`ASSOCIATION_ID`,`DE_ASSOCIATION_ID`) values (64,143);
insert into `intra_model_association` (`ASSOCIATION_ID`,`DE_ASSOCIATION_ID`) values (65,136);
insert into `intra_model_association` (`ASSOCIATION_ID`,`DE_ASSOCIATION_ID`) values (66,133);
insert into `intra_model_association` (`ASSOCIATION_ID`,`DE_ASSOCIATION_ID`) values (67,131);
insert into `intra_model_association` (`ASSOCIATION_ID`,`DE_ASSOCIATION_ID`) values (68,129);
insert into `intra_model_association` (`ASSOCIATION_ID`,`DE_ASSOCIATION_ID`) values (69,117);
insert into `intra_model_association` (`ASSOCIATION_ID`,`DE_ASSOCIATION_ID`) values (70,115);
insert into `intra_model_association` (`ASSOCIATION_ID`,`DE_ASSOCIATION_ID`) values (71,112);
insert into `intra_model_association` (`ASSOCIATION_ID`,`DE_ASSOCIATION_ID`) values (72,111);
insert into `intra_model_association` (`ASSOCIATION_ID`,`DE_ASSOCIATION_ID`) values (73,107);

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

insert into `path` (`PATH_ID`,`FIRST_ENTITY_ID`,`INTERMEDIATE_PATH`,`LAST_ENTITY_ID`) values (1,52,'1',23);
insert into `path` (`PATH_ID`,`FIRST_ENTITY_ID`,`INTERMEDIATE_PATH`,`LAST_ENTITY_ID`) values (2,23,'2',24);
insert into `path` (`PATH_ID`,`FIRST_ENTITY_ID`,`INTERMEDIATE_PATH`,`LAST_ENTITY_ID`) values (3,24,'3',65);
insert into `path` (`PATH_ID`,`FIRST_ENTITY_ID`,`INTERMEDIATE_PATH`,`LAST_ENTITY_ID`) values (4,65,'4',72);
insert into `path` (`PATH_ID`,`FIRST_ENTITY_ID`,`INTERMEDIATE_PATH`,`LAST_ENTITY_ID`) values (5,65,'5',69);
insert into `path` (`PATH_ID`,`FIRST_ENTITY_ID`,`INTERMEDIATE_PATH`,`LAST_ENTITY_ID`) values (6,65,'6',66);
insert into `path` (`PATH_ID`,`FIRST_ENTITY_ID`,`INTERMEDIATE_PATH`,`LAST_ENTITY_ID`) values (7,24,'7',60);
insert into `path` (`PATH_ID`,`FIRST_ENTITY_ID`,`INTERMEDIATE_PATH`,`LAST_ENTITY_ID`) values (8,24,'8',52);
insert into `path` (`PATH_ID`,`FIRST_ENTITY_ID`,`INTERMEDIATE_PATH`,`LAST_ENTITY_ID`) values (9,52,'9',53);
insert into `path` (`PATH_ID`,`FIRST_ENTITY_ID`,`INTERMEDIATE_PATH`,`LAST_ENTITY_ID`) values (10,24,'10',13);
insert into `path` (`PATH_ID`,`FIRST_ENTITY_ID`,`INTERMEDIATE_PATH`,`LAST_ENTITY_ID`) values (11,24,'11',40);
insert into `path` (`PATH_ID`,`FIRST_ENTITY_ID`,`INTERMEDIATE_PATH`,`LAST_ENTITY_ID`) values (12,24,'12',37);
insert into `path` (`PATH_ID`,`FIRST_ENTITY_ID`,`INTERMEDIATE_PATH`,`LAST_ENTITY_ID`) values (13,24,'13',34);
insert into `path` (`PATH_ID`,`FIRST_ENTITY_ID`,`INTERMEDIATE_PATH`,`LAST_ENTITY_ID`) values (14,24,'14',31);
insert into `path` (`PATH_ID`,`FIRST_ENTITY_ID`,`INTERMEDIATE_PATH`,`LAST_ENTITY_ID`) values (15,24,'15',28);
insert into `path` (`PATH_ID`,`FIRST_ENTITY_ID`,`INTERMEDIATE_PATH`,`LAST_ENTITY_ID`) values (16,24,'16',25);
insert into `path` (`PATH_ID`,`FIRST_ENTITY_ID`,`INTERMEDIATE_PATH`,`LAST_ENTITY_ID`) values (17,206,'17',13);
insert into `path` (`PATH_ID`,`FIRST_ENTITY_ID`,`INTERMEDIATE_PATH`,`LAST_ENTITY_ID`) values (18,269,'18',8);
insert into `path` (`PATH_ID`,`FIRST_ENTITY_ID`,`INTERMEDIATE_PATH`,`LAST_ENTITY_ID`) values (19,269,'19',271);
insert into `path` (`PATH_ID`,`FIRST_ENTITY_ID`,`INTERMEDIATE_PATH`,`LAST_ENTITY_ID`) values (20,269,'20',180);
insert into `path` (`PATH_ID`,`FIRST_ENTITY_ID`,`INTERMEDIATE_PATH`,`LAST_ENTITY_ID`) values (21,289,'21',108);
insert into `path` (`PATH_ID`,`FIRST_ENTITY_ID`,`INTERMEDIATE_PATH`,`LAST_ENTITY_ID`) values (22,289,'22',124);
insert into `path` (`PATH_ID`,`FIRST_ENTITY_ID`,`INTERMEDIATE_PATH`,`LAST_ENTITY_ID`) values (23,124,'23',108);
insert into `path` (`PATH_ID`,`FIRST_ENTITY_ID`,`INTERMEDIATE_PATH`,`LAST_ENTITY_ID`) values (24,289,'24',23);
insert into `path` (`PATH_ID`,`FIRST_ENTITY_ID`,`INTERMEDIATE_PATH`,`LAST_ENTITY_ID`) values (25,289,'25',182);
insert into `path` (`PATH_ID`,`FIRST_ENTITY_ID`,`INTERMEDIATE_PATH`,`LAST_ENTITY_ID`) values (26,182,'26',280);
insert into `path` (`PATH_ID`,`FIRST_ENTITY_ID`,`INTERMEDIATE_PATH`,`LAST_ENTITY_ID`) values (27,182,'27',269);
insert into `path` (`PATH_ID`,`FIRST_ENTITY_ID`,`INTERMEDIATE_PATH`,`LAST_ENTITY_ID`) values (28,182,'28',266);
insert into `path` (`PATH_ID`,`FIRST_ENTITY_ID`,`INTERMEDIATE_PATH`,`LAST_ENTITY_ID`) values (29,182,'29',263);
insert into `path` (`PATH_ID`,`FIRST_ENTITY_ID`,`INTERMEDIATE_PATH`,`LAST_ENTITY_ID`) values (30,182,'30',8);
insert into `path` (`PATH_ID`,`FIRST_ENTITY_ID`,`INTERMEDIATE_PATH`,`LAST_ENTITY_ID`) values (31,182,'31',221);
insert into `path` (`PATH_ID`,`FIRST_ENTITY_ID`,`INTERMEDIATE_PATH`,`LAST_ENTITY_ID`) values (32,221,'32',8);
insert into `path` (`PATH_ID`,`FIRST_ENTITY_ID`,`INTERMEDIATE_PATH`,`LAST_ENTITY_ID`) values (33,221,'33',224);
insert into `path` (`PATH_ID`,`FIRST_ENTITY_ID`,`INTERMEDIATE_PATH`,`LAST_ENTITY_ID`) values (34,221,'34',176);
insert into `path` (`PATH_ID`,`FIRST_ENTITY_ID`,`INTERMEDIATE_PATH`,`LAST_ENTITY_ID`) values (35,221,'35',7);
insert into `path` (`PATH_ID`,`FIRST_ENTITY_ID`,`INTERMEDIATE_PATH`,`LAST_ENTITY_ID`) values (36,7,'36',23);
insert into `path` (`PATH_ID`,`FIRST_ENTITY_ID`,`INTERMEDIATE_PATH`,`LAST_ENTITY_ID`) values (37,7,'37',13);
insert into `path` (`PATH_ID`,`FIRST_ENTITY_ID`,`INTERMEDIATE_PATH`,`LAST_ENTITY_ID`) values (38,7,'38',8);
insert into `path` (`PATH_ID`,`FIRST_ENTITY_ID`,`INTERMEDIATE_PATH`,`LAST_ENTITY_ID`) values (39,182,'39',161);
insert into `path` (`PATH_ID`,`FIRST_ENTITY_ID`,`INTERMEDIATE_PATH`,`LAST_ENTITY_ID`) values (40,161,'40',108);
insert into `path` (`PATH_ID`,`FIRST_ENTITY_ID`,`INTERMEDIATE_PATH`,`LAST_ENTITY_ID`) values (41,182,'41',216);
insert into `path` (`PATH_ID`,`FIRST_ENTITY_ID`,`INTERMEDIATE_PATH`,`LAST_ENTITY_ID`) values (42,182,'42',206);
insert into `path` (`PATH_ID`,`FIRST_ENTITY_ID`,`INTERMEDIATE_PATH`,`LAST_ENTITY_ID`) values (43,182,'43',108);
insert into `path` (`PATH_ID`,`FIRST_ENTITY_ID`,`INTERMEDIATE_PATH`,`LAST_ENTITY_ID`) values (44,182,'44',191);
insert into `path` (`PATH_ID`,`FIRST_ENTITY_ID`,`INTERMEDIATE_PATH`,`LAST_ENTITY_ID`) values (45,191,'45',13);
insert into `path` (`PATH_ID`,`FIRST_ENTITY_ID`,`INTERMEDIATE_PATH`,`LAST_ENTITY_ID`) values (46,191,'46',8);
insert into `path` (`PATH_ID`,`FIRST_ENTITY_ID`,`INTERMEDIATE_PATH`,`LAST_ENTITY_ID`) values (47,182,'47',188);
insert into `path` (`PATH_ID`,`FIRST_ENTITY_ID`,`INTERMEDIATE_PATH`,`LAST_ENTITY_ID`) values (48,182,'48',104);
insert into `path` (`PATH_ID`,`FIRST_ENTITY_ID`,`INTERMEDIATE_PATH`,`LAST_ENTITY_ID`) values (49,182,'49',100);
insert into `path` (`PATH_ID`,`FIRST_ENTITY_ID`,`INTERMEDIATE_PATH`,`LAST_ENTITY_ID`) values (50,182,'50',89);
insert into `path` (`PATH_ID`,`FIRST_ENTITY_ID`,`INTERMEDIATE_PATH`,`LAST_ENTITY_ID`) values (51,182,'51',7);
insert into `path` (`PATH_ID`,`FIRST_ENTITY_ID`,`INTERMEDIATE_PATH`,`LAST_ENTITY_ID`) values (52,182,'52',2);
insert into `path` (`PATH_ID`,`FIRST_ENTITY_ID`,`INTERMEDIATE_PATH`,`LAST_ENTITY_ID`) values (53,182,'53',233);
insert into `path` (`PATH_ID`,`FIRST_ENTITY_ID`,`INTERMEDIATE_PATH`,`LAST_ENTITY_ID`) values (54,182,'54',236);
insert into `path` (`PATH_ID`,`FIRST_ENTITY_ID`,`INTERMEDIATE_PATH`,`LAST_ENTITY_ID`) values (55,182,'55',239);
insert into `path` (`PATH_ID`,`FIRST_ENTITY_ID`,`INTERMEDIATE_PATH`,`LAST_ENTITY_ID`) values (56,106,'56',116);
insert into `path` (`PATH_ID`,`FIRST_ENTITY_ID`,`INTERMEDIATE_PATH`,`LAST_ENTITY_ID`) values (57,116,'57',108);
insert into `path` (`PATH_ID`,`FIRST_ENTITY_ID`,`INTERMEDIATE_PATH`,`LAST_ENTITY_ID`) values (58,116,'58',161);
insert into `path` (`PATH_ID`,`FIRST_ENTITY_ID`,`INTERMEDIATE_PATH`,`LAST_ENTITY_ID`) values (59,116,'59',158);
insert into `path` (`PATH_ID`,`FIRST_ENTITY_ID`,`INTERMEDIATE_PATH`,`LAST_ENTITY_ID`) values (60,116,'60',132);
insert into `path` (`PATH_ID`,`FIRST_ENTITY_ID`,`INTERMEDIATE_PATH`,`LAST_ENTITY_ID`) values (61,132,'61',149);
insert into `path` (`PATH_ID`,`FIRST_ENTITY_ID`,`INTERMEDIATE_PATH`,`LAST_ENTITY_ID`) values (62,132,'62',146);
insert into `path` (`PATH_ID`,`FIRST_ENTITY_ID`,`INTERMEDIATE_PATH`,`LAST_ENTITY_ID`) values (63,132,'63',134);
insert into `path` (`PATH_ID`,`FIRST_ENTITY_ID`,`INTERMEDIATE_PATH`,`LAST_ENTITY_ID`) values (64,134,'64',135);
insert into `path` (`PATH_ID`,`FIRST_ENTITY_ID`,`INTERMEDIATE_PATH`,`LAST_ENTITY_ID`) values (65,135,'65',93);
insert into `path` (`PATH_ID`,`FIRST_ENTITY_ID`,`INTERMEDIATE_PATH`,`LAST_ENTITY_ID`) values (66,132,'66',102);
insert into `path` (`PATH_ID`,`FIRST_ENTITY_ID`,`INTERMEDIATE_PATH`,`LAST_ENTITY_ID`) values (67,116,'67',8);
insert into `path` (`PATH_ID`,`FIRST_ENTITY_ID`,`INTERMEDIATE_PATH`,`LAST_ENTITY_ID`) values (68,116,'68',124);
insert into `path` (`PATH_ID`,`FIRST_ENTITY_ID`,`INTERMEDIATE_PATH`,`LAST_ENTITY_ID`) values (69,116,'69',102);
insert into `path` (`PATH_ID`,`FIRST_ENTITY_ID`,`INTERMEDIATE_PATH`,`LAST_ENTITY_ID`) values (70,106,'70',113);
insert into `path` (`PATH_ID`,`FIRST_ENTITY_ID`,`INTERMEDIATE_PATH`,`LAST_ENTITY_ID`) values (71,106,'71',23);
insert into `path` (`PATH_ID`,`FIRST_ENTITY_ID`,`INTERMEDIATE_PATH`,`LAST_ENTITY_ID`) values (72,106,'72',108);
insert into `path` (`PATH_ID`,`FIRST_ENTITY_ID`,`INTERMEDIATE_PATH`,`LAST_ENTITY_ID`) values (73,106,'73',98);

/*Table structure for table `query` */

CREATE TABLE `query` (
  `IDENTIFIER` bigint(20) NOT NULL,
  `CONSTRAINTS_ID` bigint(20) default NULL,
  PRIMARY KEY  (`IDENTIFIER`),
  UNIQUE KEY `CONSTRAINTS_ID` (`CONSTRAINTS_ID`),
  KEY `FK49D20A886AD86FC` (`IDENTIFIER`),
  KEY `FK49D20A89E2FD9C7` (`CONSTRAINTS_ID`),
  CONSTRAINT `FK49D20A89E2FD9C7` FOREIGN KEY (`CONSTRAINTS_ID`) REFERENCES `query_constraints` (`IDENTIFIER`),
  CONSTRAINT `FK49D20A886AD86FC` FOREIGN KEY (`IDENTIFIER`) REFERENCES `query_abstract_query` (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `query` */

/*Table structure for table `query_abstract_query` */

CREATE TABLE `query_abstract_query` (
  `IDENTIFIER` bigint(20) NOT NULL auto_increment,
  `QUERY_NAME` varchar(255) default NULL,
  `DESCRIPTION` text,
  PRIMARY KEY  (`IDENTIFIER`),
  UNIQUE KEY `QUERY_NAME` (`QUERY_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `query_abstract_query` */

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
  KEY `FK262AEB0BE92C814D` (`EXPRESSION_ID`),
  KEY `FK262AEB0BD635BD31` (`IDENTIFIER`),
  KEY `FK262AEB0B96C7CE5A` (`IDENTIFIER`),
  KEY `FK262AEB0BD006BE44` (`IDENTIFIER`),
  KEY `FK262AEB0B7223B197` (`IDENTIFIER`),
  KEY `FK262AEB0B687BE69E` (`IDENTIFIER`),
  CONSTRAINT `FK262AEB0B687BE69E` FOREIGN KEY (`IDENTIFIER`) REFERENCES `query_operand` (`IDENTIFIER`),
  CONSTRAINT `FK262AEB0B7223B197` FOREIGN KEY (`IDENTIFIER`) REFERENCES `query_operand` (`IDENTIFIER`),
  CONSTRAINT `FK262AEB0B96C7CE5A` FOREIGN KEY (`IDENTIFIER`) REFERENCES `query_operand` (`IDENTIFIER`),
  CONSTRAINT `FK262AEB0BD006BE44` FOREIGN KEY (`IDENTIFIER`) REFERENCES `query_operand` (`IDENTIFIER`),
  CONSTRAINT `FK262AEB0BD635BD31` FOREIGN KEY (`IDENTIFIER`) REFERENCES `query_operand` (`IDENTIFIER`),
  CONSTRAINT `FK262AEB0BE92C814D` FOREIGN KEY (`EXPRESSION_ID`) REFERENCES `query_base_expression` (`IDENTIFIER`)
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

/*Table structure for table `query_composite_query` */

CREATE TABLE `query_composite_query` (
  `IDENTIFIER` bigint(20) NOT NULL,
  `OPERATION_ID` bigint(20) default NULL,
  PRIMARY KEY  (`IDENTIFIER`),
  KEY `FKD453833986AD86FC` (`IDENTIFIER`),
  KEY `FKD453833932224F67` (`OPERATION_ID`),
  CONSTRAINT `FKD453833932224F67` FOREIGN KEY (`OPERATION_ID`) REFERENCES `query_operation` (`IDENTIFIER`),
  CONSTRAINT `FKD453833986AD86FC` FOREIGN KEY (`IDENTIFIER`) REFERENCES `query_abstract_query` (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `query_composite_query` */

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
  KEY `FK2BD705CEE92C814D` (`EXPRESSION_ID`),
  KEY `FK2BD705CEA0A5F4C0` (`CONSTRAINT_ID`),
  CONSTRAINT `FK2BD705CEA0A5F4C0` FOREIGN KEY (`CONSTRAINT_ID`) REFERENCES `query_constraints` (`IDENTIFIER`),
  CONSTRAINT `FK2BD705CEE92C814D` FOREIGN KEY (`EXPRESSION_ID`) REFERENCES `query_base_expression` (`IDENTIFIER`)
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

/*Table structure for table `query_count_view` */

CREATE TABLE `query_count_view` (
  `IDENTIFIER` bigint(20) NOT NULL,
  `COUNT_ENTITY_ID` bigint(20) default NULL,
  PRIMARY KEY  (`IDENTIFIER`),
  KEY `FK4A5C8BECF17325F` (`COUNT_ENTITY_ID`),
  KEY `FK4A5C8BEC89DB039E` (`IDENTIFIER`),
  CONSTRAINT `FK4A5C8BEC89DB039E` FOREIGN KEY (`IDENTIFIER`) REFERENCES `query_result_view` (`IDENTIFIER`),
  CONSTRAINT `FK4A5C8BECF17325F` FOREIGN KEY (`COUNT_ENTITY_ID`) REFERENCES `query_query_entity` (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `query_count_view` */

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

/*Table structure for table `query_data_view` */

CREATE TABLE `query_data_view` (
  `IDENTIFIER` bigint(20) NOT NULL,
  PRIMARY KEY  (`IDENTIFIER`),
  KEY `FK2A3EA74389DB039E` (`IDENTIFIER`),
  CONSTRAINT `FK2A3EA74389DB039E` FOREIGN KEY (`IDENTIFIER`) REFERENCES `query_result_view` (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `query_data_view` */

/*Table structure for table `query_expression` */

CREATE TABLE `query_expression` (
  `IDENTIFIER` bigint(20) NOT NULL,
  `IS_IN_VIEW` tinyint(1) default NULL,
  `IS_VISIBLE` tinyint(1) default NULL,
  `UI_EXPR_ID` int(11) default NULL,
  `QUERY_ENTITY_ID` bigint(20) default NULL,
  PRIMARY KEY  (`IDENTIFIER`),
  KEY `FK1B473A8F635766D8` (`QUERY_ENTITY_ID`),
  KEY `FK1B473A8F40EB75D4` (`IDENTIFIER`),
  CONSTRAINT `FK1B473A8F40EB75D4` FOREIGN KEY (`IDENTIFIER`) REFERENCES `query_base_expression` (`IDENTIFIER`),
  CONSTRAINT `FK1B473A8F635766D8` FOREIGN KEY (`QUERY_ENTITY_ID`) REFERENCES `query_query_entity` (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `query_expression` */

/*Table structure for table `query_formula_rhs` */

CREATE TABLE `query_formula_rhs` (
  `CUSTOM_FORMULA_ID` bigint(20) NOT NULL,
  `RHS_TERM_ID` bigint(20) NOT NULL,
  `POSITION` int(11) NOT NULL,
  PRIMARY KEY  (`CUSTOM_FORMULA_ID`,`POSITION`),
  KEY `FKAE90F94D9A0B7164` (`CUSTOM_FORMULA_ID`),
  KEY `FKAE90F94D3BC37DCB` (`RHS_TERM_ID`),
  CONSTRAINT `FKAE90F94D3BC37DCB` FOREIGN KEY (`RHS_TERM_ID`) REFERENCES `query_base_expression` (`IDENTIFIER`),
  CONSTRAINT `FKAE90F94D9A0B7164` FOREIGN KEY (`CUSTOM_FORMULA_ID`) REFERENCES `query_operand` (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `query_formula_rhs` */

/*Table structure for table `query_inter_model_association` */

CREATE TABLE `query_inter_model_association` (
  `IDENTIFIER` bigint(20) NOT NULL,
  `SOURCE_SERVICE_URL` varchar(255) NOT NULL,
  `TARGET_SERVICE_URL` varchar(255) NOT NULL,
  `SOURCE_ATTRIBUTE_ID` bigint(20) NOT NULL,
  `TARGET_ATTRIBUTE_ID` bigint(20) NOT NULL,
  PRIMARY KEY  (`IDENTIFIER`),
  KEY `FKD70658D15F5AB67E` (`IDENTIFIER`),
  CONSTRAINT `FKD70658D15F5AB67E` FOREIGN KEY (`IDENTIFIER`) REFERENCES `query_model_association` (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `query_inter_model_association` */

/*Table structure for table `query_intersection` */

CREATE TABLE `query_intersection` (
  `IDENTIFIER` bigint(20) NOT NULL,
  PRIMARY KEY  (`IDENTIFIER`),
  KEY `FK2C1FACC0E201AD1D` (`IDENTIFIER`),
  CONSTRAINT `FK2C1FACC0E201AD1D` FOREIGN KEY (`IDENTIFIER`) REFERENCES `query_operation` (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `query_intersection` */

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

/*Table structure for table `query_minus` */

CREATE TABLE `query_minus` (
  `IDENTIFIER` bigint(20) NOT NULL,
  PRIMARY KEY  (`IDENTIFIER`),
  KEY `FK7FD7D5F9E201AD1D` (`IDENTIFIER`),
  CONSTRAINT `FK7FD7D5F9E201AD1D` FOREIGN KEY (`IDENTIFIER`) REFERENCES `query_operation` (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `query_minus` */

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

/*Table structure for table `query_operation` */

CREATE TABLE `query_operation` (
  `IDENTIFIER` bigint(20) NOT NULL auto_increment,
  `OPERAND_ONE` bigint(20) default NULL,
  `OPERAND_TWO` bigint(20) default NULL,
  PRIMARY KEY  (`IDENTIFIER`),
  KEY `FKA13E4E70E4553443` (`OPERAND_ONE`),
  KEY `FKA13E4E70E4554829` (`OPERAND_TWO`),
  CONSTRAINT `FKA13E4E70E4554829` FOREIGN KEY (`OPERAND_TWO`) REFERENCES `query_abstract_query` (`IDENTIFIER`),
  CONSTRAINT `FKA13E4E70E4553443` FOREIGN KEY (`OPERAND_ONE`) REFERENCES `query_abstract_query` (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `query_operation` */

/*Table structure for table `query_output_attribute` */

CREATE TABLE `query_output_attribute` (
  `IDENTIFIER` bigint(20) NOT NULL auto_increment,
  `EXPRESSION_ID` bigint(20) default NULL,
  `ATTRIBUTE_ID` bigint(20) NOT NULL,
  `PARAMETERIZED_QUERY_ID` bigint(20) default NULL,
  `POSITION` int(11) default NULL,
  `DATA_VIEW_ID` bigint(20) default NULL,
  PRIMARY KEY  (`IDENTIFIER`),
  KEY `FK22C9DB75E92C814D` (`EXPRESSION_ID`),
  KEY `FK22C9DB75F961BE22` (`DATA_VIEW_ID`),
  KEY `FK22C9DB75604D4BDA` (`PARAMETERIZED_QUERY_ID`),
  CONSTRAINT `FK22C9DB75604D4BDA` FOREIGN KEY (`PARAMETERIZED_QUERY_ID`) REFERENCES `query_parameterized_query` (`IDENTIFIER`),
  CONSTRAINT `FK22C9DB75E92C814D` FOREIGN KEY (`EXPRESSION_ID`) REFERENCES `query_base_expression` (`IDENTIFIER`),
  CONSTRAINT `FK22C9DB75F961BE22` FOREIGN KEY (`DATA_VIEW_ID`) REFERENCES `query_data_view` (`IDENTIFIER`)
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
  PRIMARY KEY  (`IDENTIFIER`),
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

/*Table structure for table `query_result_view` */

CREATE TABLE `query_result_view` (
  `IDENTIFIER` bigint(20) NOT NULL auto_increment,
  PRIMARY KEY  (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `query_result_view` */

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
  KEY `FK2BF760E8E92C814D` (`EXPRESSION_ID`),
  KEY `FK2BF760E832E875C8` (`IDENTIFIER`),
  CONSTRAINT `FK2BF760E832E875C8` FOREIGN KEY (`IDENTIFIER`) REFERENCES `query_operand` (`IDENTIFIER`),
  CONSTRAINT `FK2BF760E8E92C814D` FOREIGN KEY (`EXPRESSION_ID`) REFERENCES `query_base_expression` (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `query_subexpr_operand` */

/*Table structure for table `query_to_output_terms` */

CREATE TABLE `query_to_output_terms` (
  `QUERY_ID` bigint(20) NOT NULL,
  `OUTPUT_TERM_ID` bigint(20) NOT NULL,
  `POSITION` int(11) NOT NULL,
  PRIMARY KEY  (`QUERY_ID`,`POSITION`),
  UNIQUE KEY `OUTPUT_TERM_ID` (`OUTPUT_TERM_ID`),
  KEY `FK8A70E25691051647` (`QUERY_ID`),
  KEY `FK8A70E2565E5B9430` (`OUTPUT_TERM_ID`),
  CONSTRAINT `FK8A70E2565E5B9430` FOREIGN KEY (`OUTPUT_TERM_ID`) REFERENCES `query_output_term` (`IDENTIFIER`),
  CONSTRAINT `FK8A70E25691051647` FOREIGN KEY (`QUERY_ID`) REFERENCES `query` (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `query_to_output_terms` */

/*Table structure for table `query_to_parameters` */

CREATE TABLE `query_to_parameters` (
  `QUERY_ID` bigint(20) NOT NULL,
  `PARAMETER_ID` bigint(20) NOT NULL,
  `POSITION` int(11) NOT NULL,
  PRIMARY KEY  (`QUERY_ID`,`POSITION`),
  UNIQUE KEY `PARAMETER_ID` (`PARAMETER_ID`),
  KEY `FK8060DAD739F0A314` (`QUERY_ID`),
  KEY `FK8060DAD7F84B9027` (`PARAMETER_ID`),
  CONSTRAINT `FK8060DAD7F84B9027` FOREIGN KEY (`PARAMETER_ID`) REFERENCES `query_parameter` (`IDENTIFIER`),
  CONSTRAINT `FK8060DAD739F0A314` FOREIGN KEY (`QUERY_ID`) REFERENCES `query_parameterized_query` (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `query_to_parameters` */

/*Table structure for table `query_union` */

CREATE TABLE `query_union` (
  `IDENTIFIER` bigint(20) NOT NULL,
  PRIMARY KEY  (`IDENTIFIER`),
  KEY `FK804AC458E201AD1D` (`IDENTIFIER`),
  CONSTRAINT `FK804AC458E201AD1D` FOREIGN KEY (`IDENTIFIER`) REFERENCES `query_operation` (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `query_union` */

/*Table structure for table `query_workflow` */

CREATE TABLE `query_workflow` (
  `IDENTIFIER` bigint(20) NOT NULL auto_increment,
  `USER_ID` bigint(20) default NULL,
  `QUERY_NAME` text,
  `CREATED_ON` date default NULL,
  PRIMARY KEY  (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `query_workflow` */

/*Table structure for table `query_workflow_item` */

CREATE TABLE `query_workflow_item` (
  `IDENTIFIER` bigint(20) NOT NULL auto_increment,
  `QUERY_ID` bigint(20) default NULL,
  `WORKFLOW_ID` bigint(20) default NULL,
  `POSITION` int(11) default NULL,
  PRIMARY KEY  (`IDENTIFIER`),
  KEY `FK63FE103CA19B1E45` (`QUERY_ID`),
  KEY `FK63FE103C40018CD` (`WORKFLOW_ID`),
  CONSTRAINT `FK63FE103C40018CD` FOREIGN KEY (`WORKFLOW_ID`) REFERENCES `query_workflow` (`IDENTIFIER`),
  CONSTRAINT `FK63FE103CA19B1E45` FOREIGN KEY (`QUERY_ID`) REFERENCES `query_abstract_query` (`IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

SET FOREIGN_KEY_CHECKS = 1;

