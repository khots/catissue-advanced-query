create table QUERY_EXECUTION_LOG(QUERY_EXECUTION_ID integer, XQUERY_String varchar(32500), QUERY_TYPE varchar(10), 
CREATIONTIME timestamp, IP_ADDRESS varchar(50), PROJECT_ID integer, WORKFLOW_ID bigint);


create table COUNT_QUERY_EXECUTION_LOG(COUNT_QUERY_EXECUTION_ID integer generated always as identity, QUERY_ID integer, USER_ID integer, 
QUERY_COUNT integer, QUERY_STATUS varchar(50));


create table DATA_QUERY_EXECUTION_LOG(COUNT_QUERY_EXECUTION_ID integer, DATA_QUERY_EXECUTION_ID integer generated always as identity);

create table QUERY_ITABLE(COUNT_QUERY_EXECUTION_ID integer, PATIENT_DEID integer, UPI varchar(24), DATE_OF_BIRTH timestamp, VIEW_FLAG varchar(10));

create table QUERY_SECURITY_LOG(QUERY_EXECUTION_ID integer, SECURITY_CODE varchar(32600));

create index indx_cnt_qry_exec_id on QUERY_ITABLE(COUNT_QUERY_EXECUTION_ID);
