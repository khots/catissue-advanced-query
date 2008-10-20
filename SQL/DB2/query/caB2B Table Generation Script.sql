
drop table CURATED_PATH; 
drop table CURATED_PATH_TO_PATH;
drop table PATH;
drop table INTER_MODEL_ASSOCIATION;
drop table INTRA_MODEL_ASSOCIATION;
drop table ASSOCIATION;
drop table ID_TABLE;

/*INTERMEDIATE_PATH contains  ASSOCIATION(ASSOCIATION_ID) connected by underscore */
create table PATH(
     PATH_ID           bigint         not null,
     FIRST_ENTITY_ID   bigint         default null,
     INTERMEDIATE_PATH varchar(1000)  default null,
     LAST_ENTITY_ID    bigint         default null,
     primary key (PATH_ID)
    /*  index INDEX1 (FIRST_ENTITY_ID,LAST_ENTITY_ID)*/
);

create table ASSOCIATION(
    ASSOCIATION_ID    bigint    not null,
    ASSOCIATION_TYPE  Decimal(31,0)    not null ,
    primary key (ASSOCIATION_ID)
);

create table INTER_MODEL_ASSOCIATION(
    ASSOCIATION_ID      bigint  not null references ASSOCIATION(ASSOCIATION_ID),
    LEFT_ENTITY_ID      bigint  not null,
    LEFT_ATTRIBUTE_ID   bigint  not null,
    RIGHT_ENTITY_ID     bigint  not null,
    RIGHT_ATTRIBUTE_ID  bigint  not null,
    primary key (ASSOCIATION_ID) 
);
create table INTRA_MODEL_ASSOCIATION(
    ASSOCIATION_ID    bigint    not null references ASSOCIATION(ASSOCIATION_ID),
    DE_ASSOCIATION_ID bigint    not null,
    primary key (ASSOCIATION_ID) 
);
create table ID_TABLE(
    NEXT_ASSOCIATION_ID    bigint    not null,
    primary key (NEXT_ASSOCIATION_ID)
);
create table CURATED_PATH (
	curated_path_Id BIGINT not null,
	entity_ids VARCHAR(1000),
	selected DECIMAL(1,0),
	primary key (curated_path_Id)
);

/*This is mapping table for many-to-many relationship between tables PATH and CURATED_PATH */
create table CURATED_PATH_TO_PATH (
	curated_path_Id BIGINT not null references CURATED_PATH (curated_path_Id),
	path_id BIGINT  not null references PATH (path_id),
	primary key (curated_path_Id,path_id)
);
insert into ID_TABLE(NEXT_ASSOCIATION_ID) values(1);