create table QUERY_EXECUTION_LOG(QUERY_EXECUTION_ID bigint not null auto_increment, XQUERY_String varchar(32400), QUERY_TYPE varchar(10), 
CREATION_TIME timestamp, IP_ADDRESS varchar(50), PROJECT_ID bigint, QUERY_ID bigint, USER_ID bigint, WORKFLOW_ID bigint, QUERY_STATUS varchar(50), primary key (QUERY_EXECUTION_ID));

create table COUNT_QUERY_EXECUTION_LOG(COUNT_QUERY_EXECUTION_ID bigint, QUERY_COUNT bigint);

create table DATA_QUERY_EXECUTION_LOG(COUNT_QUERY_EXECUTION_ID bigint, DATA_QUERY_EXECUTION_ID bigint, DOWNLOAD_FLAG varchar(10));

create table QUERY_ITABLE(COUNT_QUERY_EXECUTION_ID bigint, PATIENT_DEID bigint, UPI varchar(24), DATE_OF_BIRTH timestamp, VIEW_FLAG varchar(3));

create table QUERY_SECURITY_LOG(QUERY_EXECUTION_ID integer, SECURITY_CODE varchar(32600));

create index indx_cnt_qry_exec_id on QUERY_ITABLE(COUNT_QUERY_EXECUTION_ID);
