alter session set "_ORACLE_SCRIPT"=true;
--alter session set container = XEPDB1;
CREATE USER testuser IDENTIFIED BY Welcome01 DEFAULT TABLESPACE "USERS" TEMPORARY TABLESPACE "TEMP";
GRANT "DBA" TO testuser ;
GRANT "CONNECT" TO testuser;
GRANT "RESOURCE" TO testuser;