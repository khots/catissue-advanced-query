create table QUERY_EXECUTION_LOG(CREATIONTIME timestamp, USER_ID integer, STATUS varchar(30), PROJECT_ID integer, QUERY_EXECUTION_ID integer generated always as identity, QUERY_ID integer);
create table QUERY_ITABLE(PATIENT_DEID integer, UPI varchar(24), QUERY_EXECUTION_ID integer);
create index indx_execution_log_id on QUERY_ITABLE(QUERY_EXECUTION_ID);

